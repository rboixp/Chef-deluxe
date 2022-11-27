package com.chefdeluxe.app.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chefdeluxe.app.dto.*;
import com.chefdeluxe.app.entidades.Rol;
import com.chefdeluxe.app.entidades.Usuario;
import com.chefdeluxe.app.service.RolService;
import com.chefdeluxe.app.service.UsuarioService;
import com.chefdeluxe.app.utils.Utils;
import com.chefdeluxe.app.seguridad.JwtTokenProvider;

/**
 * Clase GestionUsuariosController
 *
 * Controlador gestiona els usuaris, alta, baixa modificació i consulta
 * 
 */
@RestController
@RequestMapping("/api/users")
public class GestionUsuariosController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private RolService rolService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private Utils utils;

	@PersistenceContext
	EntityManager em;

	/**
	 * End point altaUsuario
	 *
	 * Registra un usuari a la base de dades.
	 */

	@PostMapping("/create/user")
	public ResponseEntity<?> altaUsuario(@RequestBody RegisterDTO registroDTO) {
		
		String msg = validarDTO(registroDTO, SecurityContextHolder.getContext().getAuthentication());
		
		if (!msg.equals("ok")){
			return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
		}

		if (!utils.usuarioEsDelRol("ROLE_ADMIN", SecurityContextHolder.getContext().getAuthentication())) {
			return new ResponseEntity<>("Solo se permiten altas en usuarios administradores", HttpStatus.BAD_REQUEST);
		}

		if (usuarioService.existsByUsername(registroDTO.getUsername())) {
			return new ResponseEntity<>("Ese nombre de usuario ya existe", HttpStatus.BAD_REQUEST);
		}

		if (usuarioService.existsByEmail(registroDTO.getEmail())) {
			return new ResponseEntity<>("Ese email de usuario ya existe", HttpStatus.BAD_REQUEST);
		}

		
		Usuario usuario = new Usuario();
		usuario.setNombre(registroDTO.getNombre());
		usuario.setUsername(registroDTO.getUsername());
		usuario.setEmail(registroDTO.getEmail());
		usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
		usuario.setApellidos(registroDTO.getApellidos());
		usuario.setDireccion(registroDTO.getDireccion());
		usuario.setCodigoPostal(registroDTO.getCodigoPostal());
		usuario.setPoblacion(registroDTO.getPoblacion());
		usuario.setNacionalidad(registroDTO.getNacionalidad());
		usuario.setEdad(registroDTO.getEdad());
		usuario.setTelefono(registroDTO.getTelefono());
		usuario.setIban(registroDTO.getIban());

		Rol roles = rolService.findByRole(registroDTO.getPerfil());
		usuario.setRoles(Collections.singleton(roles));
		usuarioService.save(usuario);
		return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.OK);
	}

	/**
	 * End point deleteUsuario
	 *
	 * esborra un usuari de la base de dades.
	 */
	@DeleteMapping("/delete/user")
	public ResponseEntity<?> deleteUsuario(@RequestParam String usernameOrEmail) {

		Usuario usuario = usuarioService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

		if (utils.usuarioAutorizado(usernameOrEmail, SecurityContextHolder.getContext().getAuthentication())) {
			usuarioService.deleteById(usuario.getId());
			return new ResponseEntity<>("Su usuarioa se ha eliminado exitosamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Solo se permiten bajas del mismo usuario o usuario admin.",
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * End point getUsuario
	 *
	 * Retorna la informació d'un usuari
	 */
	@GetMapping("/get/user")
	public ResponseEntity<?> getUsuario(@RequestParam String usernameOrEmail) {

		if (!utils.usuarioAutorizado(usernameOrEmail, SecurityContextHolder.getContext().getAuthentication())) {
			return new ResponseEntity<>("Solo se permiten consultas  del mismo usuario o usuario admin.",
					HttpStatus.BAD_REQUEST);
		}

		Usuario usuario = usuarioService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO = convertDTO(usuario);
		return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);

	}

	/**
	 * End point getUsuarios
	 *
	 * Retorna una llista amb la informació de tots els usuari
	 */
	@GetMapping("/get/users")
	public ResponseEntity<?> getUsuarios() {

		if (!utils.usuarioEsDelRol("ROLE_ADMIN", SecurityContextHolder.getContext().getAuthentication())) {
			return new ResponseEntity<>("Usuario no es admin ", HttpStatus.BAD_REQUEST);
		}

		UsuarioDTO usuarioDTO = new UsuarioDTO();
		List<UsuarioDTO> usuarioDTOList = new ArrayList();
		List<Usuario> usuarioList = usuarioService.findAll();
		Iterator<Usuario> it = usuarioList.iterator();

		while (it.hasNext()) {
			usuarioDTO = convertDTO(it.next());
			usuarioDTOList.add(usuarioDTO);
		}
		;

		return new ResponseEntity<>(usuarioDTOList, HttpStatus.OK);
	}
	
	/**
	 * End point UpdateUsuario
	 *
	 * Actualiza la password d'un usuari.
	 */
	@PutMapping("/password/user")
	public ResponseEntity<?> cambioPassword(@RequestParam String usernameOrEmail, @RequestParam String newPassword) {

		System.out.println ("/pasword/user");
		System.out.println ("usuari " +usernameOrEmail);
		System.out.println ("newPassword " +newPassword);
		
		Usuario usuarioModificar = usuarioService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);	
		
//		if (!utils.usuarioAutorizado(usernameOrEmail, SecurityContextHolder.getContext().getAuthentication())) {
//			return new ResponseEntity<>("Solo se permiten actualizaciones de datos del mismo usuario o usuario admin.",
//					HttpStatus.BAD_REQUEST);
//		}

		usuarioModificar.setPassword(passwordEncoder.encode(newPassword));
		usuarioService.flush(usuarioModificar);
		return new ResponseEntity<>(convertDTO(usuarioModificar), HttpStatus.OK);
	}

	/**
	 * End point UpdateUsuario
	 *
	 * Actualiza un usuari.
	 */
	@PutMapping("/update/user")
	public ResponseEntity<?> UpdateUsuario(@RequestParam String usernameOrEmail, @RequestBody RegisterDTO registroDTO) {

		Usuario usuarioModificar = usuarioService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

		Usuario usuario = usuarioService.findByUsernameOrEmail(usuarioModificar.getUsername(),
				usuarioModificar.getEmail());		
		
		if (!utils.usuarioAutorizado(usernameOrEmail, SecurityContextHolder.getContext().getAuthentication())) {
			return new ResponseEntity<>("Solo se permiten actualizaciones de datos del mismo usuario o usuario admin.",
					HttpStatus.BAD_REQUEST);
		}

		rolService.findByRole(registroDTO.getPerfil());
		Rol rol = rolService.findByRole(registroDTO.getPerfil());
		Set<Rol> sRole = new HashSet();
		sRole.add(rol);
		usuario.setRoles(sRole);
		usuario.setNombre(registroDTO.getNombre());
		usuario.setUsername(registroDTO.getUsername());
		usuario.setEmail(registroDTO.getEmail());
		usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
		usuario.setApellidos(registroDTO.getApellidos());
		usuario.setDireccion(registroDTO.getDireccion());
		usuario.setCodigoPostal(registroDTO.getCodigoPostal());
		usuario.setPoblacion(registroDTO.getPoblacion());
		usuario.setNacionalidad(registroDTO.getNacionalidad());
		usuario.setEdad(registroDTO.getEdad());
		usuario.setTelefono(registroDTO.getTelefono());
		usuario.setIban(registroDTO.getIban());
		usuarioService.flush(usuario);
		return new ResponseEntity<>(convertDTO(usuario), HttpStatus.OK);
	}

	/**
	 * End point convertDTO
	 *
	 * Li entra dades en format Usuario i les transforma a format UsuarioDTO
	 */
	public UsuarioDTO convertDTO(Usuario usuario) {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setNombre(usuario.getNombre());
		usuarioDTO.setUsername(usuario.getUsername());
		usuarioDTO.setEmail(usuario.getEmail());
		usuarioDTO.setPassword(usuario.getPassword());
		usuarioDTO.setId(usuario.getId());
		usuarioDTO.setApellidos(usuario.getApellidos());
		usuarioDTO.setCodigoPostal(usuario.getCodigoPostal());
		usuarioDTO.setEdad(usuario.getEdad());
		usuarioDTO.setNacionalidad(usuario.getNacionalidad());
		usuarioDTO.setDireccion(usuario.getDireccion());
		usuarioDTO.setPoblacion(usuario.getPoblacion());
		usuarioDTO.setIban(usuario.getIban());
		usuarioDTO.setRoles(usuario.getRoles());
		usuarioDTO.setTelefono(usuario.getTelefono());
		return usuarioDTO;
	}
	
	public String validarDTO (RegisterDTO registroDTO, Authentication authentication) {
		
		if (registroDTO.getPassword() == null) {
			return "Password requerida";
		}		
		if (registroDTO.getUsername() == null) {
			return "Nombre de usuario requerido";
		}	
		if (registroDTO.getEmail() == null) {
			return "Email requerido";
		}	
		
		if (registroDTO.getApellidos().length() > 128 ) {
			return "Maxima longitud apellidos es 128";
		}

		if (registroDTO.getApellidos().length() > 128 ) {
			return "Maxima longitud apellidos es 128";
		}
		
		if (registroDTO.getCodigoPostal().length() > 10 ) {
			return "Maxima longitud codigo postal es 10";
		}
		
		if (registroDTO.getDireccion().length() > 128 ) {
			return "Maxima longitud direccion es 128";
		}
		
		if (registroDTO.getEdad() > 150 ) {
			return "Edad máxima es 150";
		}
		
		if (registroDTO.getEdad() < 18 ) {
			return "Edad mínima es 18";
		}
		
		if (registroDTO.getEmail().length() > 60 ) {
			return "Maxima longitud email es 60";
		}
		
		if (registroDTO.getIban().length() > 60 ) {
			return "Maxima longitud Iban es 60";
		}
		
		if (registroDTO.getNacionalidad().length() > 128 ) {
			return "Maxima longitud nacionalidad es 128";
		}
		
		if (registroDTO.getNombre().length() > 60 ) {
			return "Maxima longitud nombre es 60";
		}
		
		if (registroDTO.getPassword().length() > 128 ) {
			return "Maxima longitud password es 128";
		}
		
		if (registroDTO.getPoblacion().length() > 128 ) {
			return "Maxima longitud poblacion es 128";
		}
		
		if (registroDTO.getUsername().length() > 60 ) {
			return "Maxima longitud nombre de usuario es 60";
		}	
		
		if (registroDTO.getTelefono() == 0 ) {
			return "Telefono obligatorio";
		}
		
		 return "ok" ;
	}

}

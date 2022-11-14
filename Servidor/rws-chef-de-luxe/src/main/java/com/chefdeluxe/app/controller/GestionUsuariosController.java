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
	

	@PersistenceContext
	EntityManager em;
	
	/**
	 * End point altaUsuario
	 *
	 * Registra un usuari  a la base de dades.
	 */
	
	@PostMapping("/create/user")
	public ResponseEntity<?> altaUsuario(@RequestBody RegisterDTO registroDTO){
		
		if (registroDTO.getPassword() == null  ||
				registroDTO.getUsername() == null  ||
				registroDTO.getEmail() == null ) {
				return new ResponseEntity<>("Usuario, mail y password obligatorios",HttpStatus.BAD_REQUEST);
			}
		
		  String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
	      Rol rolAdmin = rolService.findByRole("ROLE_ADMIN");
	      
	      if(!usuarioService.findByUsernameOrEmail(nameJWT,nameJWT).getRoles().contains(rolAdmin)) {
	    	  return new ResponseEntity<>("Solo se permiten altas en usuarios administradores",HttpStatus.BAD_REQUEST);   
	      }

		if(usuarioService.existsByUsername(registroDTO.getUsername())) {
			return new ResponseEntity<>("Ese nombre de usuario ya existe",HttpStatus.BAD_REQUEST); 
		}
		
		if(usuarioService.existsByEmail(registroDTO.getEmail())) {
			return new ResponseEntity<>("Ese email de usuario ya existe",HttpStatus.BAD_REQUEST);
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
		return new ResponseEntity<>("Usuario registrado exitosamente",HttpStatus.OK);
	}
	/**
	 * End point deleteUsuario
	 *
	 * esborra un usuari de la base de dades.
	 */	
	@DeleteMapping("/delete/user")
	public ResponseEntity<?> deleteUsuario(@RequestParam String usernameOrEmail) {

		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolService.findByRole("ROLE_ADMIN");

		Usuario usuarioJWT = usuarioService.findByUsernameOrEmail(nameJWT, nameJWT);

		Usuario usuario = usuarioService.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail);


		if (nameJWT.equals(usuario.getEmail())) {
			usuarioService.deleteById(usuario.getId());
			return new ResponseEntity<>("Su usuarioa se ha eliminado exitosamente", HttpStatus.OK);
		}

		if (usuarioJWT.getRoles().contains(rolAdmin)) {
			usuarioService.deleteById(usuario.getId());
			return new ResponseEntity<>(
					"El usuario " + usernameOrEmail + "  se ha eliminado exitosamente", HttpStatus.OK);
		}

		return new ResponseEntity<>("Solo se permiten bajas del mismo usuario o usuario admin. Usuario " + nameJWT
				+ " rol " + usuario.getRoles(), HttpStatus.BAD_REQUEST);

	}
	
	/**
	 * End point getUsuario
	 *
	 * Retorna la informació d'un usuari
	 */	
	@GetMapping("/get/user")
	public ResponseEntity<?>  getUsuario(@RequestParam String usernameOrEmail){		
		
		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolService.findByRole("ROLE_ADMIN");
		Usuario usuarioJWT = usuarioService.findByUsernameOrEmail(nameJWT, nameJWT);
		
		Usuario usuario = usuarioService.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail);

		if (nameJWT.equals(usuario.getEmail())) {
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO = convertDTO(usuario);
			return new ResponseEntity<> (usuarioDTO, HttpStatus.OK);
			
		}

		if (usuarioJWT.getRoles().contains(rolAdmin)) {
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO = convertDTO(usuario);
			return new ResponseEntity<> (usuarioDTO, HttpStatus.OK);
		}		

		return new ResponseEntity<>("usuario logeado no es admin o es diferente del de la consulta" , HttpStatus.BAD_REQUEST);

	}
	/**
	 * End point getUsuarios
	 *
	 * Retorna una llista amb la informació de tots els usuari
	 */	
	@GetMapping("/get/users")
	public ResponseEntity<?> getUsuarios( ){
		
		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolService.findByRole("ROLE_ADMIN");
		Usuario usuarioJWT = usuarioService.findByUsernameOrEmail(nameJWT, nameJWT);
		
		if (!usuarioJWT.getRoles().contains(rolAdmin)) {
			return new ResponseEntity<>("Usuario no es admin " , HttpStatus.BAD_REQUEST);
		}	
		
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		List<UsuarioDTO> usuarioDTOList = new ArrayList();
		List<Usuario> usuarioList = usuarioService.findAll();
		Iterator<Usuario> it = usuarioList.iterator();
		
		while(it.hasNext()) {
			usuarioDTO = convertDTO(it.next());
			usuarioDTOList.add(usuarioDTO);
			};
		
		return new ResponseEntity<> (usuarioDTOList, HttpStatus.OK);
	}	
	/**
	 * End point UpdateUsuario
	 *
	 * Actualiza un usuari.
	 */	
	@PutMapping("/update/user")
	public ResponseEntity<?> UpdateUsuario(@RequestParam String usernameOrEmail,@RequestBody RegisterDTO registroDTO
			){
		
		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolService.findByRole("ROLE_ADMIN");
		Usuario usuarioJWT = usuarioService.findByUsernameOrEmail(nameJWT, nameJWT);
		Usuario usuarioModificar = usuarioService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
			
		LoginDTO loginDTO =  new LoginDTO();
		loginDTO.setUsernameOrEmail(usernameOrEmail);
		Usuario usuario = usuarioService.findByUsernameOrEmail(usuarioModificar.getUsername(),usuarioModificar.getEmail());
		usuario = usuarioService.findById(usuario.getId());
		
		if (!usuarioJWT.getRoles().contains(rolAdmin) && !nameJWT.equals(usuarioModificar.getEmail())) {
			return new ResponseEntity<>("Usuario no es admin o el usuario del jtw es difrente del usuario a actualziar" , HttpStatus.BAD_REQUEST);
		}
			
		rolService.findByRole(registroDTO.getPerfil());
		Rol rol = rolService.findByRole(registroDTO.getPerfil());
		Set <Rol> sRole = new HashSet();
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
		return new ResponseEntity<>(convertDTO(usuario),HttpStatus.OK);		
	
	}
	/**
	 * End point convertDTO
	 *
	 * Li entra dades en format Usuario i les transforma a format UsuarioDTO
	 */	
	public UsuarioDTO convertDTO (Usuario usuario) {
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
	

}

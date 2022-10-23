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
import com.chefdeluxe.app.repositorio.RolRepositorio;
import com.chefdeluxe.app.repositorio.UsuarioRepositorio;
import com.chefdeluxe.app.seguridad.JwtTokenProvider;

@RestController
@RequestMapping("/api/users")
public class GestionUsuariosController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;	
	
	@Autowired
	private RolRepositorio rolRepositorio;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
		
	@Autowired
	private JwtTokenProvider jwtTokenProvider;	
	

	@PersistenceContext
	EntityManager em;
	

	
	@PostMapping("/create/user")
	public ResponseEntity<?> altaUsuario(@RequestBody RegisterDTO registroDTO){
		
		  String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
	      Rol rolAdmin = rolRepositorio.findByRole("ROLE_ADMIN").get();
	      
	      if(!usuarioRepositorio.findByUsernameOrEmail(nameJWT,nameJWT).get().getRoles().contains(rolAdmin)) {
	    	  return new ResponseEntity<>("Solo se permiten altas en usuarios administradores",HttpStatus.BAD_REQUEST);   
	      }

		if(usuarioRepositorio.existsByUsername(registroDTO.getUsername())) {
			return new ResponseEntity<>("Ese nombre de usuario ya existe",HttpStatus.BAD_REQUEST); 
		}
		
		if(usuarioRepositorio.existsByEmail(registroDTO.getEmail())) {
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
		
		
		Rol roles = rolRepositorio.findByRole(registroDTO.getPerfil()).get();
		usuario.setRoles(Collections.singleton(roles));	
		usuarioRepositorio.save(usuario);
		return new ResponseEntity<>("Usuario registrado exitosamente",HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/user{username}")
	public ResponseEntity<?> deleteUsuario(@RequestBody LoginDTO loginDTO) {

		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolRepositorio.findByRole("ROLE_ADMIN").get();

		Optional<Usuario> usuarioJWT = usuarioRepositorio.findByUsernameOrEmail(nameJWT, nameJWT);

		Optional<Usuario> usuario = usuarioRepositorio.findByUsernameOrEmail(loginDTO.getUsernameOrEmail(),loginDTO.getUsernameOrEmail());


		if (nameJWT.equals(usuario.get().getEmail())) {
			usuarioRepositorio.deleteById(usuario.get().getId());
			return new ResponseEntity<>("Su usuarioase ha eliminado exitosamente", HttpStatus.OK);
		}

		if (usuarioJWT.get().getRoles().contains(rolAdmin)) {
			usuarioRepositorio.deleteById(usuario.get().getId());
			return new ResponseEntity<>(
					"El usuario " + loginDTO.getUsernameOrEmail() + "  se ha eliminado exitosamente", HttpStatus.OK);
		}

		return new ResponseEntity<>("Solo se permiten bajas del mismo usuario o usuario admin. Usuario " + nameJWT
				+ " rol " + usuario.get().getRoles(), HttpStatus.BAD_REQUEST);

	}
	
	@GetMapping("/get/user{username}")
	public ResponseEntity<?>  getUsuario(@RequestBody LoginDTO loginDTO){
		
		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolRepositorio.findByRole("ROLE_ADMIN").get();
		Optional<Usuario> usuarioJWT = usuarioRepositorio.findByUsernameOrEmail(nameJWT, nameJWT);
		
		Optional<Usuario> usuario = usuarioRepositorio.findByUsernameOrEmail(loginDTO.getUsernameOrEmail(),loginDTO.getUsernameOrEmail());
		 if (!usuario.isPresent()) {
			 return new ResponseEntity<>("usuario <"  +loginDTO.getUsernameOrEmail() +"> no encontrado" , HttpStatus.BAD_REQUEST);
		 }
		if (nameJWT.equals(usuario.get().getEmail())) {
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO = convertDTO(usuario.get());
			return new ResponseEntity<> (usuarioDTO, HttpStatus.OK);
			
		}

		if (usuarioJWT.get().getRoles().contains(rolAdmin)) {
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO = convertDTO(usuario.get());
			return new ResponseEntity<> (usuarioDTO, HttpStatus.OK);
		}		

		return new ResponseEntity<>("usuario logeado no es admin o es diferente del de la consulta" , HttpStatus.BAD_REQUEST);

	}
	@GetMapping("/get/users")
	public ResponseEntity<?> getUsuarios( ){
		
		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolRepositorio.findByRole("ROLE_ADMIN").get();
		Optional<Usuario> usuarioJWT = usuarioRepositorio.findByUsernameOrEmail(nameJWT, nameJWT);
		
		if (!usuarioJWT.get().getRoles().contains(rolAdmin)) {
			return new ResponseEntity<>("Usuario no es admin " , HttpStatus.BAD_REQUEST);
		}	
		
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		List<UsuarioDTO> usuarioDTOList = new ArrayList();
		List<Usuario> usuarioList = usuarioRepositorio.findAll();
		Iterator<Usuario> it = usuarioList.iterator();
		
		while(it.hasNext()) {
			usuarioDTO = convertDTO(it.next());
			usuarioDTOList.add(usuarioDTO);
			};
		
		return new ResponseEntity<> (usuarioDTOList, HttpStatus.OK);
	}	
	@PutMapping("/update/user{UsernameOrEmail}")
	public ResponseEntity<?> UpdateUsuario(@RequestBody RegisterDTO registroDTO){
		
		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolRepositorio.findByRole("ROLE_ADMIN").get();
		Optional<Usuario> usuarioJWT = usuarioRepositorio.findByUsernameOrEmail(nameJWT, nameJWT);
			
		LoginDTO loginDTO =  new LoginDTO();
		loginDTO.setUsernameOrEmail(registroDTO.getUsername());
		Optional<Usuario> usuario = usuarioRepositorio.findByUsernameOrEmail(loginDTO.getUsernameOrEmail(),loginDTO.getUsernameOrEmail());
		usuario = usuarioRepositorio.findById(usuario.get().getId());
		
		if (!usuarioJWT.get().getRoles().contains(rolAdmin) && !nameJWT.equals(usuario.get().getEmail())) {
			return new ResponseEntity<>("Usuario no es admin o el usuario del jtw es difrente del usuario a actualziar" , HttpStatus.BAD_REQUEST);
		}
			
		rolRepositorio.findByRole(registroDTO.getPerfil());
		Optional <Rol> rol = rolRepositorio.findByRole(registroDTO.getPerfil());
		Set <Rol> sRole = new HashSet();
		sRole.add(rol.get());
		usuario.get().setRoles(sRole);		

		usuario.get().setNombre(registroDTO.getNombre());
		usuario.get().setUsername(registroDTO.getUsername());
		usuario.get().setEmail(registroDTO.getEmail());
		usuario.get().setPassword(passwordEncoder.encode(registroDTO.getPassword()));	
		usuario.get().setApellidos(registroDTO.getApellidos());
		usuario.get().setDireccion(registroDTO.getDireccion());
		usuario.get().setCodigoPostal(registroDTO.getCodigoPostal());
		usuario.get().setPoblacion(registroDTO.getPoblacion());
		usuario.get().setNacionalidad(registroDTO.getNacionalidad());
		usuario.get().setEdad(registroDTO.getEdad());
		usuario.get().setTelefono(registroDTO.getTelefono());
		usuario.get().setIban(registroDTO.getIban());
		
		usuarioRepositorio.flush();
		return new ResponseEntity<>(convertDTO(usuario.get()),HttpStatus.OK);		
	
	}
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
		return usuarioDTO;
	}
	

}

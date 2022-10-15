package com.chefdeluxe.app.controller;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chefdeluxe.app.dto.*;
import com.chefdeluxe.app.entidades.Rol;
import com.chefdeluxe.app.entidades.Usuario;
import com.chefdeluxe.app.repositorio.RolRepositorio;
import com.chefdeluxe.app.repositorio.UsuarioRepositorio;
import com.chefdeluxe.app.seguridad.JWTAuthResonseDTO;
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
	
	@PostMapping("/create/user")
	public ResponseEntity<?> altaUsuario(@RequestBody RegisterDTO registroDTO){
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
		Rol roles = rolRepositorio.findByRole(registroDTO.getPerfil()).get();
		usuario.setRoles(Collections.singleton(roles));		
		usuarioRepositorio.save(usuario);
		return new ResponseEntity<>("Usuario registrado exitosamente",HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/user{username}")
	public ResponseEntity<?> deleteUsuario(@RequestBody DeleteDTO deleteDTO) {
		
		Optional<Usuario> usuario = usuarioRepositorio.findByUsername(deleteDTO.getUsername());
		if (usuario.isPresent()) {
			if (!usuario.get().getRoles().contains(rolRepositorio.findByRole("ROLE_ADMIN").get())){
				return new ResponseEntity<>("Ese usuario no tiene perfil admin", HttpStatus.BAD_REQUEST);	
			}		
			usuarioRepositorio.deleteByUsername(deleteDTO.getUsername());
			return new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Ese nombre de usuario no existe", HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping("/get/user{username}")
	public Usuario getUsuario(@RequestBody LoginDTO loginDTO){
		
		Optional<Usuario> usuario = usuarioRepositorio.findByUsernameOrEmail(loginDTO.getUsernameOrEmail(),loginDTO.getUsernameOrEmail());
		if (usuario.isPresent()) {
			return usuario.get();
		}else {
			new UsernameNotFoundException("Metodo /get/user{username} Usuario no encontrado con ese username o email : " + loginDTO.getUsernameOrEmail());
		} 
			
		return null;

	}
	@GetMapping("/get/users")
	public ResponseEntity<?> getUsuarios(@RequestBody RegisterDTO registroDTO){

		return new ResponseEntity<>("Usuario leido exitosamente /get/users",HttpStatus.OK);
	}	
	@PutMapping("/update/user{id}")
	public ResponseEntity<?> UpdateUsuario(@RequestBody RegisterDTO registroDTO){
		return new ResponseEntity<>("Usuario actualizado exitosamente",HttpStatus.OK);
	}

}

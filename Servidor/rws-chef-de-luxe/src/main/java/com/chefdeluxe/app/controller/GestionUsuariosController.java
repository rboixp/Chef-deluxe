package com.chefdeluxe.app.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
//import com.chefdeluxe.app.repositorio.UsuariosRolesRepositorio;
import com.chefdeluxe.app.seguridad.JWTAuthResonseDTO;
import com.chefdeluxe.app.seguridad.JwtTokenProvider;

@RestController
@RequestMapping("/api/users")
public class GestionUsuariosController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	//@Autowired
	//private UsuariosRolesRepositorio usuariosRolesRepositorio;	
	
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
		System.out.println("roles" +roles +" Collections.singleton(roles)" +Collections.singleton(roles));
		usuario.setRoles(Collections.singleton(roles));	
		System.out.println("userRoles" +usuario.getRoles()); 
		usuarioRepositorio.save(usuario);
		return new ResponseEntity<>("Usuario registrado exitosamente",HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/user{username}")
	public ResponseEntity<?> deleteUsuario(@RequestBody LoginDTO loginDTO) {
		
		Optional<Usuario> usuario = usuarioRepositorio.findByUsernameOrEmail(loginDTO.getUsernameOrEmail(),loginDTO.getUsernameOrEmail());
		if (usuario.isPresent()) {
			if (!usuario.get().getRoles().contains(rolRepositorio.findByRole("ROLE_ADMIN").get())){
				return new ResponseEntity<>("Ese usuario no tiene perfil admin", HttpStatus.BAD_REQUEST);	
			}	
		//	usuariosRolesRepositorio.deleteById(usuario.get().getId());
			usuarioRepositorio.deleteById(usuario.get().getId());
			return new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Ese nombre de usuario no existe", HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping("/get/user{username}")
	public ResponseEntity<?>  getUsuario(@RequestBody LoginDTO loginDTO){
		
		Optional<Usuario> usuario = usuarioRepositorio.findByUsernameOrEmail(loginDTO.getUsernameOrEmail(),loginDTO.getUsernameOrEmail());
		if (usuario.isPresent()) {
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setNombre(usuario.get().getNombre());
			usuarioDTO.setUsername(usuario.get().getUsername());
			usuarioDTO.setEmail(usuario.get().getEmail());
			usuarioDTO.setPassword(passwordEncoder.encode(usuario.get().getPassword()));
			usuarioDTO.setId(usuario.get().getId());
			return new ResponseEntity<> (usuarioDTO, HttpStatus.OK);
		}else {
			new UsernameNotFoundException("Metodo /get/user{username} Usuario no encontrado con ese username o email : " + loginDTO.getUsernameOrEmail());
		} 
			
		return null;

	}
	@GetMapping("/get/users")
	public ResponseEntity<?> getUsuarios( ){
		List<Usuario> usuarioList = usuarioRepositorio.findAll();
		return new ResponseEntity<> (usuarioList, HttpStatus.OK);
	//	return new ResponseEntity<>("Usuario leido exitosamente /get/users",HttpStatus.OK);
	}	
	@PutMapping("/update/user{id}")
	public ResponseEntity<?> UpdateUsuario(@RequestBody RegisterDTO registroDTO){
		LoginDTO loginDTO =  new LoginDTO();
		loginDTO.setUsernameOrEmail(registroDTO.getUsername());
		Optional<Usuario> usuario = usuarioRepositorio.findByUsernameOrEmail(loginDTO.getUsernameOrEmail(),loginDTO.getUsernameOrEmail());
		if (usuario.isPresent()) {
			
			rolRepositorio.findByRole(registroDTO.getPerfil());
			Optional <Rol> rol = rolRepositorio.findByRole(registroDTO.getPerfil());
			if (rol.isPresent()) {
	//		usuariosRolesRepositorio.updateUserRole(rol.get().getId(),usuario.get().getId());
			}
			else {
				return new ResponseEntity<> ("Metodo /update/user{id} Rol no encontrado: " +rol.get().getRole() ,HttpStatus.BAD_REQUEST);
			}
			usuarioRepositorio.updateUser(
				   registroDTO.getEmail(),
				   passwordEncoder.encode(registroDTO.getPassword()),
			       registroDTO.getNombre(),
			       registroDTO.getUsername(),
			       usuario.get().getId());
			usuario = usuarioRepositorio.findById(usuario.get().getId());
		return new ResponseEntity<>(usuario.get(),HttpStatus.OK);
		}else {
			return new ResponseEntity<> ("Metodo /update/user{id} Usuario no encontrado con ese username o email : " ,HttpStatus.BAD_REQUEST);
		} 
			
	
	}

}

package com.chefdeluxe.app.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chefdeluxe.app.dto.DisponibilidadDTO;
import com.chefdeluxe.app.dto.LoginDTO;
import com.chefdeluxe.app.dto.RegisterDTO;
import com.chefdeluxe.app.dto.UsuarioDTO;
import com.chefdeluxe.app.entidades.Disponibilidad;
import com.chefdeluxe.app.entidades.Rol;
import com.chefdeluxe.app.entidades.Usuario;
import com.chefdeluxe.app.repositorio.DisponibilidadRepositorio;
import com.chefdeluxe.app.repositorio.RolRepositorio;
import com.chefdeluxe.app.repositorio.UsuarioRepositorio;
import com.chefdeluxe.app.seguridad.JwtTokenProvider;

@RestController
@RequestMapping("/api/chef")
public class GestionDisponibilidadController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private
	UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private DisponibilidadRepositorio disponibilidadRepositorio;	
	
	@Autowired
	private RolRepositorio rolRepositorio;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
		
	@Autowired
	private JwtTokenProvider jwtTokenProvider;	
	
	@PersistenceContext
	EntityManager em;
	
	@PostMapping("/disponibilidad/post")
	public ResponseEntity<?> altaDisponibilidad(@RequestBody DisponibilidadDTO disponibilidadDTO){
		
		  String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		  
	      Rol rolAdmin = rolRepositorio.findByRole("ROLE_ADMIN").get();
	      Rol rolChef = rolRepositorio.findByRole("ROLE_CHEF").get();
	      
	      if (!usuarioRepositorio.findByUsernameOrEmail(nameJWT,nameJWT).get().getRoles().contains(rolAdmin)) {
	    	  if (!usuarioRepositorio.findByUsernameOrEmail(nameJWT,nameJWT).get().getRoles().contains(rolChef)) {
	    		  return new ResponseEntity<>("token no valido, no es de chef",HttpStatus.BAD_REQUEST);
	    	  } else {
	    		  if (!usuarioRepositorio.findByUsernameOrEmail(nameJWT,nameJWT).get().getUsername().equals(usuarioRepositorio.findByUsernameOrEmail(disponibilidadDTO.getUsernameOrEmail(),disponibilidadDTO.getUsernameOrEmail()).get().getUsername())) 
	    				  return new ResponseEntity<>("Usuario del token <" 
	    		                  + usuarioRepositorio.findByUsernameOrEmail(nameJWT,nameJWT).get().getUsername()
	    						  + "> no coincide con usuario del Body <"
	    						  + usuarioRepositorio.findByUsernameOrEmail(disponibilidadDTO.getUsernameOrEmail(),disponibilidadDTO.getUsernameOrEmail()).get().getUsername()
	    						  +">",HttpStatus.BAD_REQUEST);  
	    			  }
	      }
	      
        Long userId = usuarioRepositorio.findByUsernameOrEmail(disponibilidadDTO.getUsernameOrEmail(),disponibilidadDTO.getUsernameOrEmail()).get().getId();
		Disponibilidad disponibilidad = new Disponibilidad();
		disponibilidad.setIdUser(userId);
		disponibilidad.setPoblacion(disponibilidadDTO.getPoblacion());
		disponibilidad.setEstado(disponibilidadDTO.getEstado());
		try {
		disponibilidadRepositorio.save(disponibilidad);
		} catch (Exception e) {
			return new ResponseEntity<>("Error insertando registro: \n" +e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
		return new ResponseEntity<>("Disponibilidad dada de alta exitosamente",HttpStatus.OK);
	}
	
	@GetMapping("/disponibilidad/get/username")
	public ResponseEntity<?>  getUsuario(@RequestParam String usernameOrEmail){		
		
		  String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		  
	      Rol rolAdmin = rolRepositorio.findByRole("ROLE_ADMIN").get();
	      Rol rolChef = rolRepositorio.findByRole("ROLE_CHEF").get(); 

	      Long userId = usuarioRepositorio.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail).get().getId();
	      System.out.println("userId " +userId);
	      List<Disponibilidad> diponibilidades = disponibilidadRepositorio.findByIdUser(userId); 
	      
	      List<DisponibilidadDTO> disponibilidadListDTO = new ArrayList();
	      
		 Iterator<Disponibilidad> it = diponibilidades.iterator();
			
			while(it.hasNext()) {
				  Disponibilidad dispo = it.next();
			      DisponibilidadDTO disponibilidadDTO = new DisponibilidadDTO();
			      disponibilidadDTO.setEstado(dispo.getEstado());
			      disponibilidadDTO.setPoblacion(dispo.getPoblacion());
			      disponibilidadDTO.setUsernameOrEmail(usernameOrEmail);
			      disponibilidadDTO.setId(dispo.getId());
			      disponibilidadListDTO.add(disponibilidadDTO);
				};
				
		 return new ResponseEntity<> (disponibilidadListDTO, HttpStatus.OK);			

	}
	@GetMapping("/disponibilidad/get/all")
	public ResponseEntity<?> getListaDisponibilidad( ){
		
		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolRepositorio.findByRole("ROLE_ADMIN").get();
		Optional<Usuario> usuarioJWT = usuarioRepositorio.findByUsernameOrEmail(nameJWT, nameJWT);
		
		if (!usuarioJWT.get().getRoles().contains(rolAdmin)) {
			return new ResponseEntity<>("Usuario <" +nameJWT +"> no es admin " , HttpStatus.BAD_REQUEST);
		}	
		
	      List<Disponibilidad> diponibilidades = disponibilidadRepositorio.findAll();
	      
	      List<DisponibilidadDTO> disponibilidadListDTO = new ArrayList();
	      
			Iterator<Disponibilidad> it = diponibilidades.iterator();
			
			while(it.hasNext()) {
				  Disponibilidad dispo = it.next();
			      DisponibilidadDTO disponibilidadDTO = new DisponibilidadDTO();
			      disponibilidadDTO.setEstado(dispo.getEstado());
			      disponibilidadDTO.setPoblacion(dispo.getPoblacion());
			      disponibilidadDTO.setUsernameOrEmail(usuarioRepositorio.findById(dispo.getId_user()).get().getUsername());
			      disponibilidadDTO.setId(dispo.getId());
			      disponibilidadListDTO.add(disponibilidadDTO); 
				};
		
		return new ResponseEntity<> (disponibilidadListDTO, HttpStatus.OK);
	}	
	@GetMapping("/disponibilidad/get/filtrado")
	public ResponseEntity<?> getListaDisponibilidadEstado( @RequestParam String estado ,@RequestParam String poblacion){
		
	//	String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
	//	Rol rolAdmin = rolRepositorio.findByRole("ROLE_ADMIN").get();
	//	Optional<Usuario> usuarioJWT = usuarioRepositorio.findByUsernameOrEmail(nameJWT, nameJWT);
		
	//	if (!usuarioJWT.get().getRoles().contains(rolAdmin)) {
	//		return new ResponseEntity<>("Usuario <" +nameJWT +"> no es admin " , HttpStatus.BAD_REQUEST);
	//	}	
		
	      List<Disponibilidad> diponibilidades = disponibilidadRepositorio.findAll();
	      
	      List<DisponibilidadDTO> disponibilidadListDTO = new ArrayList();
	      
			Iterator<Disponibilidad> it = diponibilidades.iterator();
			
			while(it.hasNext()) {
				  Disponibilidad dispo = it.next();
			      DisponibilidadDTO disponibilidadDTO = new DisponibilidadDTO();
			      disponibilidadDTO.setEstado(dispo.getEstado());
			      disponibilidadDTO.setPoblacion(dispo.getPoblacion());
			      disponibilidadDTO.setUsernameOrEmail(usuarioRepositorio.findById(dispo.getId_user()).get().getUsername());
			      disponibilidadDTO.setId(dispo.getId());
			      Boolean filtro = (estado.equalsIgnoreCase("todos")    || estado.equalsIgnoreCase   (dispo.getEstado()) ) &&
			    		            (poblacion.equalsIgnoreCase("todos") || poblacion.equalsIgnoreCase(dispo.getPoblacion()) );
			      
			      if (filtro) {disponibilidadListDTO.add(disponibilidadDTO); }

				};
		
		return new ResponseEntity<> (disponibilidadListDTO, HttpStatus.OK);
	}		
	@DeleteMapping("/disponibilidad/delete") 
	public ResponseEntity<?> deleteDisponibilidad(@RequestParam Long id) {

		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolRepositorio.findByRole("ROLE_ADMIN").get();

		Optional<Usuario> usuarioJWT = usuarioRepositorio.findByUsernameOrEmail(nameJWT, nameJWT);


		if (usuarioJWT.get().getRoles().contains(rolAdmin)) {
			disponibilidadRepositorio.deleteById(id);
			return new ResponseEntity<>(
					"el registro de disponibilidad con id <" +id +"> se ha eliminado exitosamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Solo se permite delete para el usuario administrador ", HttpStatus.BAD_REQUEST);
		}
	}
	@PutMapping("/disponibilidad/update/estado")
	public ResponseEntity<?> UpdateDisponibilidad(@RequestParam String usernameOrEmail,@RequestParam String poblacion, @RequestBody DisponibilidadDTO disponibilidadDTO){

		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolRepositorio.findByRole("ROLE_ADMIN").get();
		Optional<Usuario> usuarioJWT = usuarioRepositorio.findByUsernameOrEmail(nameJWT, nameJWT);
		Optional<Usuario> usuario = usuarioRepositorio.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
	
		if (!usuarioJWT.get().getRoles().contains(rolAdmin) && !nameJWT.equals(usuario.get().getEmail())) {
			return new ResponseEntity<>("Usuario no es admin o el usuario del jtw es difrente del usuario a actualziar" , HttpStatus.BAD_REQUEST);
		}

		Optional<Disponibilidad> disponibilidad = disponibilidadRepositorio.findById(usuario.get().getId());


		disponibilidad.get().setEstado(disponibilidadDTO.getEstado());		
		disponibilidadRepositorio.flush();
		disponibilidadDTO.setId(usuario.get().getId());
		disponibilidadDTO.setUsernameOrEmail(usernameOrEmail);
		disponibilidadDTO.setPoblacion(poblacion);
		
		return new ResponseEntity<>(disponibilidadDTO,HttpStatus.OK);		
	
	}	
}

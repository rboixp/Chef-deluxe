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
import com.chefdeluxe.app.service.DisponibilidadService;
import com.chefdeluxe.app.service.RolService;
import com.chefdeluxe.app.service.UsuarioService;
import com.chefdeluxe.app.seguridad.JwtTokenProvider;

/**
 * Clase GestionDisponibilidadController
 *
 * Controlador gestiona la Disponibilitat, alta baixa modificació i consulta
 */

@RestController
@RequestMapping("/api/chef")
public class GestionDisponibilidadController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private
	UsuarioService usuarioService;
	
	@Autowired
	private DisponibilidadService disponibilidadService;	
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
		
	@Autowired
	private JwtTokenProvider jwtTokenProvider;	
	
	@PersistenceContext
	EntityManager em;
	
	/**
	 * End point altaDisponibilidad
	 *
	 * Registra una disponibilitat a la base de dades.
	 */
	
	@PostMapping("/disponibilidad/post")
	public ResponseEntity<?> altaDisponibilidad(@RequestBody DisponibilidadDTO disponibilidadDTO){
		
		  String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		  
	      Rol rolAdmin = rolService.findByRole("ROLE_ADMIN");
	      Rol rolChef = rolService.findByRole("ROLE_CHEF");
	      
	      if (!usuarioService.findByUsernameOrEmail(nameJWT,nameJWT).getRoles().contains(rolAdmin)) {
	    	  if (!usuarioService.findByUsernameOrEmail(nameJWT,nameJWT).getRoles().contains(rolChef)) {
	    		  return new ResponseEntity<>("token no valido, no es de chef",HttpStatus.BAD_REQUEST);
	    	  } else {
	    		  if (!usuarioService.findByUsernameOrEmail(nameJWT,nameJWT).getUsername().equals(usuarioService.findByUsernameOrEmail(disponibilidadDTO.getUsernameOrEmail(),disponibilidadDTO.getUsernameOrEmail()).getUsername())) 
	    				  return new ResponseEntity<>("Usuario del token <" 
	    		                  + usuarioService.findByUsernameOrEmail(nameJWT,nameJWT).getUsername()
	    						  + "> no coincide con usuario del Body <"
	    						  + usuarioService.findByUsernameOrEmail(disponibilidadDTO.getUsernameOrEmail(),disponibilidadDTO.getUsernameOrEmail()).getUsername()
	    						  +">",HttpStatus.BAD_REQUEST);  
	    			  }
	      }
	      
        Long userId = usuarioService.findByUsernameOrEmail(disponibilidadDTO.getUsernameOrEmail(),disponibilidadDTO.getUsernameOrEmail()).getId();
		Disponibilidad disponibilidad = new Disponibilidad();
		disponibilidad.setIdUser(userId);
		disponibilidad.setPoblacion(disponibilidadDTO.getPoblacion());
		disponibilidad.setEstado(disponibilidadDTO.getEstado());
		try {
		disponibilidadService.save(disponibilidad);
		} catch (Exception e) {
			return new ResponseEntity<>("Error insertando registro: \n" +e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
		return new ResponseEntity<>("Disponibilidad dada de alta exitosamente",HttpStatus.OK);
	}
	
	/**
	 * End point getUsuario
	 *
	 * Retorna les disponibilitats d'un usuari.
	 */
	@GetMapping("/disponibilidad/get/username")
	public ResponseEntity<?>  getUsuario(@RequestParam String usernameOrEmail){		
		
		  String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		  
	      Rol rolAdmin = rolService.findByRole("ROLE_ADMIN");
	      Rol rolChef = rolService.findByRole("ROLE_CHEF"); 

	      Long userId = usuarioService.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail).getId();
	      System.out.println("userId " +userId);
	      List<Disponibilidad> diponibilidades = disponibilidadService.findByIdUser(userId); 
	      
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
	/**
	 * End point getUsuario
	 *
	 * Retorna les disponibilitats d'un usuari amb paginacio
	 */
	@GetMapping("/disponibilidad/get/username/paginable")
	public ResponseEntity<?>  getUsuario(@RequestParam String usernameOrEmail, @RequestParam int pageIndex, @RequestParam int pageSize){		
		
		  String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		  
	      Rol rolAdmin = rolService.findByRole("ROLE_ADMIN");
	      Rol rolChef = rolService.findByRole("ROLE_CHEF"); 

	      Long userId = usuarioService.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail).getId();
	      System.out.println("userId " +userId);
	      List<Disponibilidad> diponibilidades = disponibilidadService.findByIdUser(userId, pageIndex, pageSize); 
	      
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
	/**
	 * End point getListaDisponibilidad
	 *
	 * Retorna la llista de disponibilitats.
	 */
	@GetMapping("/disponibilidad/get/all")
	public ResponseEntity<?> getListaDisponibilidad( ){
		
		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolService.findByRole("ROLE_ADMIN");
		Usuario usuarioJWT = usuarioService.findByUsernameOrEmail(nameJWT, nameJWT);
		
		if (!usuarioJWT.getRoles().contains(rolAdmin)) {
			return new ResponseEntity<>("Usuario <" +nameJWT +"> no es admin " , HttpStatus.BAD_REQUEST);
		}	
		
	      List<Disponibilidad> diponibilidades = disponibilidadService.findAll();
	      
	      List<DisponibilidadDTO> disponibilidadListDTO = new ArrayList();
	      
			Iterator<Disponibilidad> it = diponibilidades.iterator();
			
			while(it.hasNext()) {
				  Disponibilidad dispo = it.next();
			      DisponibilidadDTO disponibilidadDTO = new DisponibilidadDTO();
			      disponibilidadDTO.setEstado(dispo.getEstado());
			      disponibilidadDTO.setPoblacion(dispo.getPoblacion());
			      disponibilidadDTO.setUsernameOrEmail(usuarioService.findById(dispo.getIduser()).getUsername());
			      disponibilidadDTO.setId(dispo.getId());
			      disponibilidadListDTO.add(disponibilidadDTO); 
				};
		
		return new ResponseEntity<> (disponibilidadListDTO, HttpStatus.OK);
	}	
	/**
	 * End point getListaDisponibilidad
	 *
	 * Retorna la llista de disponibilitats amb paginacio.
	 */
	
	@GetMapping("/disponibilidad/get/all/paginable")
	public ResponseEntity<?> getListaDisponibilidad(@RequestParam int pageIndex, @RequestParam int pageSize ){
		
		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolService.findByRole("ROLE_ADMIN");
		Usuario usuarioJWT = usuarioService.findByUsernameOrEmail(nameJWT, nameJWT);
		
		if (!usuarioJWT.getRoles().contains(rolAdmin)) {
			return new ResponseEntity<>("Usuario <" +nameJWT +"> no es admin " , HttpStatus.BAD_REQUEST);
		}	
		
	      List<Disponibilidad> diponibilidades = disponibilidadService.findAll(pageIndex,pageSize);
	      
	      List<DisponibilidadDTO> disponibilidadListDTO = new ArrayList();
	      
			Iterator<Disponibilidad> it = diponibilidades.iterator();
			
			while(it.hasNext()) {
				  Disponibilidad dispo = it.next();
			      DisponibilidadDTO disponibilidadDTO = new DisponibilidadDTO();
			      disponibilidadDTO.setEstado(dispo.getEstado());
			      disponibilidadDTO.setPoblacion(dispo.getPoblacion());
			      disponibilidadDTO.setUsernameOrEmail(usuarioService.findById(dispo.getIduser()).getUsername());
			      disponibilidadDTO.setId(dispo.getId());
			      disponibilidadListDTO.add(disponibilidadDTO); 
				};
		
		return new ResponseEntity<> (disponibilidadListDTO, HttpStatus.OK);
	}	
	/**
	 * End point getListaDisponibilidadEstado
	 *
	 * Retorna lla llista de disponibilitats filtrada per estats i poblacio
	 */
	@GetMapping("/disponibilidad/get/filtrado")
	public ResponseEntity<?> getListaDisponibilidadEstado( @RequestParam String estado ,@RequestParam String poblacion){	
		
	      List<Disponibilidad> diponibilidades = disponibilidadService.findAll();
	      
	      List<DisponibilidadDTO> disponibilidadListDTO = new ArrayList();
	      
			Iterator<Disponibilidad> it = diponibilidades.iterator();
			
			while(it.hasNext()) {
				  Disponibilidad dispo = it.next();
			      DisponibilidadDTO disponibilidadDTO = new DisponibilidadDTO();
			      disponibilidadDTO.setEstado(dispo.getEstado());
			      disponibilidadDTO.setPoblacion(dispo.getPoblacion());
			      disponibilidadDTO.setUsernameOrEmail(usuarioService.findById(dispo.getIduser()).getUsername());
			      disponibilidadDTO.setId(dispo.getId());
			      Boolean filtro = (estado.equalsIgnoreCase("todos")    || estado.equalsIgnoreCase   (dispo.getEstado()) ) &&
			    		            (poblacion.equalsIgnoreCase("todos") || poblacion.equalsIgnoreCase(dispo.getPoblacion()) );
			      
			      if (filtro) {disponibilidadListDTO.add(disponibilidadDTO); }

				};
		
		return new ResponseEntity<> (disponibilidadListDTO, HttpStatus.OK);
	}		
	/**
	 * End point deleteDisponibilidad
	 *
	 * Esborra un registre de disponibilitat
	 */
	@DeleteMapping("/disponibilidad/delete") 
	public ResponseEntity<?> deleteDisponibilidad(@RequestParam Long id) {

		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolService.findByRole("ROLE_ADMIN");

		Usuario usuarioJWT = usuarioService.findByUsernameOrEmail(nameJWT, nameJWT);


		if (usuarioJWT.getRoles().contains(rolAdmin)) {
			disponibilidadService.deleteById(id);
			return new ResponseEntity<>(
					"el registro de disponibilidad con id <" +id +"> se ha eliminado exitosamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Solo se permite delete para el usuario administrador ", HttpStatus.BAD_REQUEST);
		}
	}
	/**
	 * End point UpdateDisponibilidad
	 *
	 * Actualitza un registre de disponibilitat
	 */
	@PutMapping("/disponibilidad/update/estado")
	public ResponseEntity<?> UpdateDisponibilidad(@RequestParam String usernameOrEmail,@RequestParam String poblacion, @RequestBody DisponibilidadDTO disponibilidadDTO){

		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolService.findByRole("ROLE_ADMIN");
		Usuario usuarioJWT = usuarioService.findByUsernameOrEmail(nameJWT, nameJWT);
		Usuario usuario = usuarioService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
	
		if (!usuarioJWT.getRoles().contains(rolAdmin) && !nameJWT.equals(usuario.getEmail())) {
			return new ResponseEntity<>("Usuario no es admin o el usuario del jtw es difrente del usuario a actualziar" , HttpStatus.BAD_REQUEST);
		}

		Disponibilidad disponibilidad = disponibilidadService.findByIdUserAndPoblacion(usuario.getId(),poblacion);


		disponibilidad.setEstado(disponibilidadDTO.getEstado());		
		disponibilidadService.flush(disponibilidad);
		disponibilidadDTO.setId(disponibilidad.getId());
		disponibilidadDTO.setUsernameOrEmail(usernameOrEmail);
		disponibilidadDTO.setPoblacion(poblacion);
		
		return new ResponseEntity<>(disponibilidadDTO,HttpStatus.OK);		
	
	}	
}

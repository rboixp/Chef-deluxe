package com.chefdeluxe.app.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chefdeluxe.app.dto.DisponibilidadDTO;
import com.chefdeluxe.app.dto.ReservaDTO;
import com.chefdeluxe.app.entidades.Disponibilidad;
import com.chefdeluxe.app.entidades.Reserva;
import com.chefdeluxe.app.entidades.Rol;
import com.chefdeluxe.app.entidades.Usuario;
import com.chefdeluxe.app.repositorio.DisponibilidadRepositorio;
import com.chefdeluxe.app.repositorio.ReservaRepositorio;
import com.chefdeluxe.app.repositorio.RolRepositorio;
import com.chefdeluxe.app.repositorio.UsuarioRepositorio;
import com.chefdeluxe.app.seguridad.JwtTokenProvider;

@RestController
@RequestMapping("/api/client")
public class GestionReservaController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private
	UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private
	ReservaRepositorio reservaRepositorio;		
	
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
	
	@PostMapping("/reserva/post")
	public ResponseEntity<?> altaReserva(@RequestBody ReservaDTO reservaDTO){
		
		  String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		  
	      Rol rolAdmin = rolRepositorio.findByRole("ROLE_ADMIN").get();
	      Rol rolcliente = rolRepositorio.findByRole("ROLE_CLIENT").get();
	      
	      if (!usuarioRepositorio.findByUsernameOrEmail(nameJWT,nameJWT).get().getRoles().contains(rolAdmin) && 
	    	  !usuarioRepositorio.findByUsernameOrEmail(nameJWT,nameJWT).get().getRoles().contains(rolcliente)  ) {
	    	  return new ResponseEntity<>("token no valido, no es de cliente ni admin",HttpStatus.BAD_REQUEST);
	      }
   	  
        Long clientId = usuarioRepositorio.findByUsernameOrEmail(reservaDTO.getCliente(),reservaDTO.getCliente()).get().getId();
        Long chefId = usuarioRepositorio.findByUsernameOrEmail(reservaDTO.getChef(),reservaDTO.getChef()).get().getId();
        Reserva reserva = new Reserva();
        reserva.setEstado(reservaDTO.getEstado());
        reserva.setIdClient(clientId);
        reserva.setIdChef(chefId);
        reserva.setIncio(reservaDTO.getIncio());
        reserva.setFin(reservaDTO.getFin());
        reserva.setPrecio(reservaDTO.getPrecio());
		reservaRepositorio.save(reserva);
		return new ResponseEntity<>("Reserva dada de alta exitosamente",HttpStatus.OK);
	}
	
	@DeleteMapping("/reserva/delete")
	public ResponseEntity<?> deleteReserva(@RequestParam Long id) {

		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolRepositorio.findByRole("ROLE_ADMIN").get();

		Optional<Usuario> usuarioJWT = usuarioRepositorio.findByUsernameOrEmail(nameJWT, nameJWT);


		if (usuarioJWT.get().getRoles().contains(rolAdmin)) {
			reservaRepositorio.deleteById(id);
			return new ResponseEntity<>(
					"el registro de reserva con id <" +id +"> se ha eliminado exitosamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Solo se permite delete para el usuario administrador ", HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/reserva/get/all")
	public ResponseEntity<?> getListaReserva( ){
		
		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolRepositorio.findByRole("ROLE_ADMIN").get();
		Optional<Usuario> usuarioJWT = usuarioRepositorio.findByUsernameOrEmail(nameJWT, nameJWT);
		
		if (!usuarioJWT.get().getRoles().contains(rolAdmin)  ) {
			return new ResponseEntity<>("Usuario <" +nameJWT +"> no es admin " , HttpStatus.BAD_REQUEST);
		}	
		

	      List<Reserva> reservas = reservaRepositorio.findAll();
	      
	      List<ReservaDTO> ReservaListDTO = new ArrayList();
	      
		  Iterator<Reserva> it = reservas.iterator();
			
			while(it.hasNext()) {
				  Reserva reserva = it.next();
			      ReservaDTO reservaDTO = new ReservaDTO();
			      reservaDTO.setEstado(reserva.getEstado());
			      reservaDTO.setId(reserva.getId());
			      Optional<Usuario> cliente = usuarioRepositorio.findById(reserva.getIdClient());
			      reservaDTO.setCliente(cliente.get().getUsername());
			      Optional<Usuario> chef = usuarioRepositorio.findById(reserva.getIdChef());
			      reservaDTO.setChef(chef.get().getUsername());
			      reservaDTO.setIncio(reserva.getInicio());
			      reservaDTO.setFin(reserva.getInicio());
			      reservaDTO.setPrecio(reserva.getPrecio());
			      ReservaListDTO.add(reservaDTO); 
				};
		
		return new ResponseEntity<> (ReservaListDTO, HttpStatus.OK);
	}	
}

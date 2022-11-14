package com.chefdeluxe.app.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.chefdeluxe.app.dto.ReservaDTO;
import com.chefdeluxe.app.entidades.Disponibilidad;
import com.chefdeluxe.app.entidades.Reserva;
import com.chefdeluxe.app.entidades.Rol;
import com.chefdeluxe.app.entidades.Usuario;
import com.chefdeluxe.app.service.DisponibilidadService;
import com.chefdeluxe.app.service.ReservaService;
import com.chefdeluxe.app.service.RolService;
import com.chefdeluxe.app.service.UsuarioService;
import com.chefdeluxe.app.seguridad.JwtTokenProvider;
import com.chefdeluxe.app.service.DisponibilidadService;
import com.chefdeluxe.app.service.ReservaService;
import com.chefdeluxe.app.service.RolService;
import com.chefdeluxe.app.service.UsuarioService;
/**
 * Clase GestionReservaController
 *
 * Controlador Gsetiona la Reserva, alta baixa modificació i consulta
 */
@RestController
@RequestMapping("/api/client")
public class GestionReservaController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private
	UsuarioService usuarioService;
	
	@Autowired
	private
	ReservaService reservaService;		
	
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
	 * End point altaReserva
	 *
	 * Registra una reserva a la base de dades.
	 */
	
	@PostMapping("/reserva/post")
	public ResponseEntity<?> altaReserva(@RequestBody ReservaDTO reservaDTO){
		
		  String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		  
	      Rol rolAdmin = rolService.findByRole("ROLE_ADMIN");
	      Rol rolcliente = rolService.findByRole("ROLE_CLIENT");
	      
	      if (!usuarioService.findByUsernameOrEmail(nameJWT,nameJWT).getRoles().contains(rolAdmin) && 
	    	  !usuarioService.findByUsernameOrEmail(nameJWT,nameJWT).getRoles().contains(rolcliente)  ) {
	    	  return new ResponseEntity<>("token no valido, no es de cliente ni admin",HttpStatus.BAD_REQUEST);
	      }
   	  
        Long clientId = usuarioService.findByUsernameOrEmail(reservaDTO.getCliente(),reservaDTO.getCliente()).getId();
        Long chefId = usuarioService.findByUsernameOrEmail(reservaDTO.getChef(),reservaDTO.getChef()).getId();
        Reserva reserva = new Reserva();
        reserva.setEstado(reservaDTO.getEstado());
        reserva.setIdClient(clientId);
        reserva.setIdChef(chefId);
        reserva.setIncio(reservaDTO.getIncio());
        reserva.setFin(reservaDTO.getFin());
        reserva.setPrecio(reservaDTO.getPrecio());
		reservaService.save(reserva);
		return new ResponseEntity<>("Reserva dada de alta exitosamente",HttpStatus.OK);
	}
	/**
	 * End point deleteReserva
	 *
	 * Esborra una reserva a la base de dades.
	 */
	@DeleteMapping("/reserva/delete")
	public ResponseEntity<?> deleteReserva(@RequestParam Long id) {

		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolService.findByRole("ROLE_ADMIN");

		Usuario usuarioJWT = usuarioService.findByUsernameOrEmail(nameJWT, nameJWT);


		if (usuarioJWT.getRoles().contains(rolAdmin)) {
			reservaService.deleteById(id);
			return new ResponseEntity<>(
					"el registro de reserva con id <" +id +"> se ha eliminado exitosamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Solo se permite delete para el usuario administrador ", HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * End point getListaReservaId
	 *
	 * retorna una reserva de la base de dades filtrada per id.
	 */
	
	@GetMapping("/reserva/get/id")
	public ResponseEntity<?> getListaReservaId(@RequestParam Long id ){	

		Reserva reserva = reservaService.findById(id);
		
		ReservaDTO reservaDTO = new ReservaDTO();
		reservaDTO.setId(reserva.getId());
		reservaDTO.setEstado(reserva.getEstado());
		reservaDTO.setCliente(usuarioService.findById(reserva.getIdClient()).getUsername());
		reservaDTO.setChef(usuarioService.findById(reserva.getIdChef()).getUsername());
		reservaDTO.setIncio(reserva.getInicio());
		reservaDTO.setFin(reserva.getFin());
		reservaDTO.setPrecio(reserva.getPrecio());
		
		
		return new ResponseEntity<> (reservaDTO, HttpStatus.OK);
	}
	
	/**
	 * Clase getListaReserva
	 *
	 * retorna una llista de reserves.
	 */
	@GetMapping("/reserva/get/all")
	public ResponseEntity<?> getListaReserva( ){
		
		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolService.findByRole("ROLE_ADMIN");
		Usuario usuarioJWT = usuarioService.findByUsernameOrEmail(nameJWT, nameJWT);
		
		if (!usuarioJWT.getRoles().contains(rolAdmin)  ) {
			return new ResponseEntity<>("Usuario <" +nameJWT +"> no es admin " , HttpStatus.BAD_REQUEST);
		}	
		

	      List<Reserva> reservas = reservaService.findAll();
	      
	      List<ReservaDTO> reservaListDTO = new ArrayList();
	      
		  Iterator<Reserva> it = reservas.iterator();
			
			while(it.hasNext()) {
				  Reserva reserva = it.next();
			      ReservaDTO reservaDTO = new ReservaDTO();
			      reservaDTO.setEstado(reserva.getEstado());
			      reservaDTO.setId(reserva.getId());
			      Usuario cliente = usuarioService.findById(reserva.getIdClient());
			      reservaDTO.setCliente(cliente.getUsername());
			      Usuario chef = usuarioService.findById(reserva.getIdChef());
			      reservaDTO.setChef(chef.getUsername());
			      reservaDTO.setIncio(reserva.getInicio());
			      reservaDTO.setFin(reserva.getInicio());
			      reservaDTO.setPrecio(reserva.getPrecio());
			      reservaListDTO.add(reservaDTO); 
				};
		
		return new ResponseEntity<> (reservaListDTO, HttpStatus.OK);
	}	
	
	/**
	 * End point UpdateDisponibilidad
	 *
	 * Actualitza una reserva.
	 */
	@PutMapping("/reserva/update/estado")
	public ResponseEntity<?> UpdateDisponibilidad(@RequestParam String usernameOrEmail,@RequestParam String estado, @RequestParam Long id){

		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolService.findByRole("ROLE_ADMIN");
		Usuario usuarioJWT = usuarioService.findByUsernameOrEmail(nameJWT, nameJWT);
		Usuario usuario = usuarioService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
	
		if (!usuarioJWT.getRoles().contains(rolAdmin) && !nameJWT.equals(usuario.getEmail())) {
			return new ResponseEntity<>("Usuario no es admin o el usuario del jtw es difrente del usuario a actualziar" , HttpStatus.BAD_REQUEST);
		}

		Reserva reserva = reservaService.findById(id);

		reserva.setEstado(estado);		
		reservaService.flush(id,estado);
		
		ReservaDTO reservaDTO = new ReservaDTO();
		reservaDTO.setId(reserva.getId());
		reservaDTO.setEstado(reserva.getEstado());
		reservaDTO.setCliente(usuarioService.findById(reserva.getIdClient()).getUsername());
		reservaDTO.setChef(usuarioService.findById(reserva.getIdChef()).getUsername());
		reservaDTO.setIncio(reserva.getInicio());
		reservaDTO.setFin(reserva.getFin());
		reservaDTO.setPrecio(reserva.getPrecio());

		
		return new ResponseEntity<>(reservaDTO,HttpStatus.OK);		
	
	}	
	/**
	 * Clase getListaReserva
	 *
	 * retorna una llista de reserves d'un chef.
	 */
	@GetMapping("/reserva/get/chef")
	public ResponseEntity<?> getListaReservaChef(long idchef ){
		
		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolService.findByRole("ROLE_ADMIN");
		Usuario usuarioJWT = usuarioService.findByUsernameOrEmail(nameJWT, nameJWT);
		
	      List<Reserva> reservas = reservaService.findByIdChef(idchef);
	      
	      List<ReservaDTO> reservaListDTO = new ArrayList();
	      
		  Iterator<Reserva> it = reservas.iterator();
			
			while(it.hasNext()) {
				  Reserva reserva = it.next();
			      ReservaDTO reservaDTO = new ReservaDTO();
			      reservaDTO.setEstado(reserva.getEstado());
			      reservaDTO.setId(reserva.getId());
			      Usuario cliente = usuarioService.findById(reserva.getIdClient());
			      reservaDTO.setCliente(cliente.getUsername());
			      Usuario chef = usuarioService.findById(reserva.getIdChef());
			      reservaDTO.setChef(chef.getUsername());
			      reservaDTO.setIncio(reserva.getInicio());
			      reservaDTO.setFin(reserva.getInicio());
			      reservaDTO.setPrecio(reserva.getPrecio());
			      reservaListDTO.add(reservaDTO); 
				};
		
		return new ResponseEntity<> (reservaListDTO, HttpStatus.OK);
	}	
	
	/**
	 * Clase getListaReservaClient
	 *
	 * retorna una llista de reserves d'un client
	 */
	@GetMapping("/reserva/get/client/paginable")
	public ResponseEntity<?> getListaReservaClient(String usernameOrEmail,int pageIndex, int pageSize ){
		
		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolService.findByRole("ROLE_ADMIN");
		Usuario usuarioJWT = usuarioService.findByUsernameOrEmail(nameJWT, nameJWT);
		Usuario usuario = usuarioService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
		
		
		List<Reserva> reservas = reservaService.findByIdClient(usuario.getId(), pageIndex, pageSize );

	    List<ReservaDTO> reservaListDTO = new ArrayList();
	      
		  Iterator<Reserva> it = reservas.iterator(); 
			
			while(it.hasNext()) {
				  Reserva reserva = it.next();
			      ReservaDTO reservaDTO = new ReservaDTO();
			      reservaDTO.setEstado(reserva.getEstado());
			      reservaDTO.setId(reserva.getId());
			      Usuario cliente = usuarioService.findById(reserva.getIdClient());
			      reservaDTO.setCliente(cliente.getUsername());
			      Usuario chef = usuarioService.findById(reserva.getIdChef());
			      reservaDTO.setChef(chef.getUsername());
			      reservaDTO.setIncio(reserva.getInicio());
			      reservaDTO.setFin(reserva.getInicio());
			      reservaDTO.setPrecio(reserva.getPrecio());
			      reservaListDTO.add(reservaDTO); 
				};
		
		return new ResponseEntity<> (reservaListDTO, HttpStatus.OK);
	}	
	
	/**
	 * Clase getListaReservaClient
	 *
	 * retorna una llista de reserves d'un client
	 */
	@GetMapping("/reserva/get/chef/paginable")
	public ResponseEntity<?> getListaReservaChef(String usernameOrEmail,int pageIndex, int pageSize ){
		
		String nameJWT = SecurityContextHolder.getContext().getAuthentication().getName();
		Rol rolAdmin = rolService.findByRole("ROLE_ADMIN");
		Usuario usuarioJWT = usuarioService.findByUsernameOrEmail(nameJWT, nameJWT);
		Usuario usuario = usuarioService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
		
		
		List<Reserva> reservas = reservaService.findByIdChef(usuario.getId(), pageIndex, pageSize );
		
	    List<ReservaDTO> reservaListDTO = new ArrayList();
	      
		  Iterator<Reserva> it = reservas.iterator(); 
			
			while(it.hasNext()) {
				  Reserva reserva = it.next();
			      ReservaDTO reservaDTO = new ReservaDTO();
			      reservaDTO.setEstado(reserva.getEstado());
			      reservaDTO.setId(reserva.getId());
			      Usuario cliente = usuarioService.findById(reserva.getIdClient());
			      reservaDTO.setCliente(cliente.getUsername());
			      Usuario chef = usuarioService.findById(reserva.getIdChef());
			      reservaDTO.setChef(chef.getUsername());
			      reservaDTO.setIncio(reserva.getInicio());
			      reservaDTO.setFin(reserva.getInicio());
			      reservaDTO.setPrecio(reserva.getPrecio());
			      reservaListDTO.add(reservaDTO); 
				};
		
		return new ResponseEntity<> (reservaListDTO, HttpStatus.OK);
	}	
}

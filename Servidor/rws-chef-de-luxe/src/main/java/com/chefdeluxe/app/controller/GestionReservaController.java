package com.chefdeluxe.app.controller;

import java.math.BigDecimal;
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
import com.chefdeluxe.app.service.TarifaService;
import com.chefdeluxe.app.service.UsuarioService;
import com.chefdeluxe.app.utils.Utils;
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
	private UsuarioService usuarioService;

	@Autowired
	private TarifaService tarifaService;

	@Autowired
	private ReservaService reservaService;

	@Autowired
	private DisponibilidadService disponibilidadService;

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
	 * End point altaReserva
	 *
	 * Registra una reserva a la base de dades.
	 */

	@PostMapping("/reserva/post")
	public ResponseEntity<?> altaReserva(@RequestBody ReservaDTO reservaDTO) {

		if (!utils.usuarioEsDelRol("ROLE_ADMIN", SecurityContextHolder.getContext().getAuthentication())
				&& !utils.usuarioEsDelRol("ROLE_CLIENT", SecurityContextHolder.getContext().getAuthentication())) {
			return new ResponseEntity<>("token no valido, no es de cliente ni admin", HttpStatus.BAD_REQUEST);
		}

		Long clientId;
		Long chefId;

		try {
			clientId = usuarioService.findByUsernameOrEmail(reservaDTO.getCliente(), reservaDTO.getCliente()).getId();

		} catch (Exception e) {
			return new ResponseEntity<>("Cliente no existe en base de datos" + reservaDTO.getCliente(),
					HttpStatus.BAD_REQUEST);
		}

		try {
			chefId = usuarioService.findByUsernameOrEmail(reservaDTO.getChef(), reservaDTO.getChef()).getId();
		} catch (Exception e) {
			return new ResponseEntity<>("Chef no existe en base de datos" + reservaDTO.getChef(),
					HttpStatus.BAD_REQUEST);
		}

		Reserva reserva = new Reserva();
		reserva.setEstado(reservaDTO.getEstado());
		reserva.setIdClient(clientId);
		reserva.setIdChef(chefId);
		reserva.setIncio(reservaDTO.getIncio());
		reserva.setFin(reservaDTO.getFin());
		reserva.setPrecio(reservaDTO.getPrecio());
		reserva.setComensales(reservaDTO.getComensales());

		long antes = reserva.getInicio().getHours();
		long ahora = reserva.getFin().getHours();
		BigDecimal horas = new BigDecimal(ahora - antes);
		BigDecimal precioHora = new BigDecimal(0);
		try {
			precioHora = tarifaService.findByIdChef(reserva.getIdChef()).getPreciohora();
		} catch (Exception e) {
			precioHora = new BigDecimal(0);
		}

		BigDecimal comensales = new BigDecimal(reserva.getComensales());
		BigDecimal factor = comensales.multiply(horas);
		BigDecimal precio = precioHora.multiply(factor);
		reserva.setPrecio(precio);

		reservaService.save(reserva);
		return new ResponseEntity<>(reserva, HttpStatus.OK);
	}

	/**
	 * End point deleteReserva
	 *
	 * Esborra una reserva a la base de dades.
	 */
	@DeleteMapping("/reserva/delete")
	public ResponseEntity<?> deleteReserva(@RequestParam Long id) {

		String username = usuarioService.findById(reservaService.findById(id).getIdClient()).getUsername();

		if (!utils.usuarioAutorizado(username, SecurityContextHolder.getContext().getAuthentication())) {
			return new ResponseEntity<>(
					"Solo se permite delete para el usuario administrador o el cliente que dio de alta la reserva",
					HttpStatus.BAD_REQUEST);
		}

		reservaService.deleteById(id);
		return new ResponseEntity<>("el registro de reserva con id <" + id + "> se ha eliminado exitosamente",
				HttpStatus.OK);

	}

	/**
	 * End point getListaReservaId
	 *
	 * retorna una reserva de la base de dades filtrada per id.
	 */

	@GetMapping("/reserva/get/id")
	public ResponseEntity<?> getListaReservaId(@RequestParam Long id) {
		Reserva reserva;

		try {
			reserva = reservaService.findById(id);
		} catch (Exception e) {
			return new ResponseEntity<>("Reserva con id <" + id + "> no existe", HttpStatus.BAD_REQUEST);

		}

		String client = usuarioService.findById(reserva.getIdClient()).getUsername();
		String chef = usuarioService.findById(reserva.getIdChef()).getUsername();

		if (!utils.usuarioAutorizado(client, SecurityContextHolder.getContext().getAuthentication())
				&& !utils.usuarioAutorizado(chef, SecurityContextHolder.getContext().getAuthentication())) {
			return new ResponseEntity<>(
					"Solo se permite consultar la reserva al cliente que la realizó, al chef asignado o al admin",
					HttpStatus.BAD_REQUEST);
		}

		ReservaDTO reservaDTO = new ReservaDTO();
		reservaDTO.setId(reserva.getId());
		reservaDTO.setEstado(reserva.getEstado());
		reservaDTO.setCliente(usuarioService.findById(reserva.getIdClient()).getUsername());
		reservaDTO.setChef(usuarioService.findById(reserva.getIdChef()).getUsername());
		reservaDTO.setIncio(reserva.getInicio());
		reservaDTO.setFin(reserva.getFin());
		reservaDTO.setComensales(reserva.getComensales());
		reservaDTO.setPrecio(reserva.getPrecio());
		reservaDTO.setInstruccions("");

		if (reserva.getEstado().equals("confirmado")) {
			Usuario chefuser = usuarioService.findById(reserva.getIdChef());

			reservaDTO.setInstruccions("Debe realizar una transferencia al IBAN " + chefuser.getIban() + " a nombre de "
					+ chefuser.getNombre() + " " + chefuser.getApellidos() + " o un Bizum al telefono "
					+ chefuser.getTelefono() + ". Una vez realizado el pago actualizar la reserva a estado pagado");
		}

		return new ResponseEntity<>(reservaDTO, HttpStatus.OK);
	}

	/**
	 * Clase getListaReserva
	 *
	 * retorna una llista de reserves.
	 */
	@GetMapping("/reserva/get/all")
	public ResponseEntity<?> getListaReserva() {

		if (!utils.usuarioEsDelRol("ROLE_ADMIN", SecurityContextHolder.getContext().getAuthentication())) {
			return new ResponseEntity<>("Usuario no es admin ", HttpStatus.BAD_REQUEST);
		}

		List<Reserva> reservas = reservaService.findAll();

		List<ReservaDTO> reservaListDTO = new ArrayList();

		Iterator<Reserva> it = reservas.iterator();

		while (it.hasNext()) {
			Reserva reserva = it.next();
			ReservaDTO reservaDTO = new ReservaDTO();
			reservaDTO.setEstado(reserva.getEstado());
			reservaDTO.setId(reserva.getId());
			Usuario cliente = usuarioService.findById(reserva.getIdClient());
			reservaDTO.setCliente(cliente.getUsername());
			Usuario chef = usuarioService.findById(reserva.getIdChef());
			reservaDTO.setChef(chef.getUsername());
			reservaDTO.setIncio(reserva.getInicio());
			reservaDTO.setFin(reserva.getFin());
			reservaDTO.setPrecio(reserva.getPrecio());
			reservaDTO.setComensales(reserva.getComensales());
			reservaDTO.setInstruccions("");
			reservaListDTO.add(reservaDTO);
		}
		;

		return new ResponseEntity<>(reservaListDTO, HttpStatus.OK);
	}

	/**
	 * End point UpdateDisponibilidad
	 *
	 * Actualitza una reserva.
	 */
	@PutMapping("/reserva/update/estado")
	public ResponseEntity<?> UpdateReserva(@RequestParam Long id, @RequestParam String estado) {

		Reserva reserva = reservaService.findById(id);

		String usuarioReserva = usuarioService.findById(reserva.getIdChef()).getUsername();

		switch (estado) {
		case "pendiente":
		case "pagado":
			if (!utils.usuarioEsDelRol("ROLE_CLIENT", SecurityContextHolder.getContext().getAuthentication())
					&& !utils.usuarioEsDelRol("ROLE_ADMIN", SecurityContextHolder.getContext().getAuthentication())) {
				return new ResponseEntity<>("Usuario no es cliente ni admin", HttpStatus.BAD_REQUEST);
			}
			;
			break;
		case "rechazado":
		case "confirmado":
		case "conciliado":
			if (!utils.usuarioEsDelRol("ROLE_CHEF", SecurityContextHolder.getContext().getAuthentication())
					&& !utils.usuarioEsDelRol("ROLE_ADMIN", SecurityContextHolder.getContext().getAuthentication())) {
				return new ResponseEntity<>("Usuario no es chef ni admin", HttpStatus.BAD_REQUEST);
			}
			;
			break;
		default:
			return new ResponseEntity<>("Estado de reserva no válido " + estado, HttpStatus.BAD_REQUEST);
		}

		reserva.setEstado(estado);
		reservaService.flush(id, estado);

		ReservaDTO reservaDTO = new ReservaDTO();
		reservaDTO.setId(reserva.getId());
		reservaDTO.setEstado(reserva.getEstado());
		reservaDTO.setCliente(usuarioService.findById(reserva.getIdClient()).getUsername());
		reservaDTO.setChef(usuarioService.findById(reserva.getIdChef()).getUsername());
		reservaDTO.setIncio(reserva.getInicio());
		reservaDTO.setFin(reserva.getFin());
		reservaDTO.setPrecio(reserva.getPrecio());
		reservaDTO.setComensales(reserva.getComensales());
		reservaDTO.setInstruccions("");

		return new ResponseEntity<>(reservaDTO, HttpStatus.OK);

	}

	/**
	 * Clase getListaReserva
	 *
	 * retorna una llista de reserves d'un chef.
	 */
	@GetMapping("/reserva/get/chef")
	public ResponseEntity<?> getListaReservaChef(String usernameOrEmail) {

		String chefUserName = usuarioService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).getUsername();

		if (!utils.usuarioAutorizado(chefUserName, SecurityContextHolder.getContext().getAuthentication())) {
			return new ResponseEntity<>("Usuario no autorizado", HttpStatus.BAD_REQUEST);
		}

		Long idchef = usuarioService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).getId();

		List<Reserva> reservas = reservaService.findByIdChef(idchef);

		List<ReservaDTO> reservaListDTO = new ArrayList();

		Iterator<Reserva> it = reservas.iterator();

		while (it.hasNext()) {
			Reserva reserva = it.next();
			ReservaDTO reservaDTO = new ReservaDTO();
			reservaDTO.setEstado(reserva.getEstado());
			reservaDTO.setId(reserva.getId());
			Usuario cliente = usuarioService.findById(reserva.getIdClient());
			reservaDTO.setCliente(cliente.getUsername());
			Usuario chef = usuarioService.findById(reserva.getIdChef());
			reservaDTO.setChef(chef.getUsername());
			reservaDTO.setIncio(reserva.getInicio());
			reservaDTO.setFin(reserva.getFin());
			reservaDTO.setPrecio(reserva.getPrecio());
			reservaDTO.setComensales(reserva.getComensales());
			reservaDTO.setInstruccions("");
			reservaListDTO.add(reservaDTO);
		}
		;

		return new ResponseEntity<>(reservaListDTO, HttpStatus.OK);
	}

	/**
	 * Clase getListaReservaClient
	 *
	 * retorna una llista de reserves d'un client amb paginació
	 */
	@GetMapping("/reserva/get/client/paginable")

	public ResponseEntity<?> getListaReservaClient(String usernameOrEmail, int pageIndex, int pageSize) {

		Usuario usuario = new Usuario();
		String clientUserName = " ";

		try {
			usuario = usuarioService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
			clientUserName = usuario.getUsername();

		} catch (Exception e) {
			return new ResponseEntity<>("No se encuentra en base de datos el usuario " + usernameOrEmail,
					HttpStatus.BAD_REQUEST);
		}

		if (!utils.usuarioAutorizado(clientUserName, SecurityContextHolder.getContext().getAuthentication())) {
			return new ResponseEntity<>("Usuario no autorizado", HttpStatus.BAD_REQUEST);
		}

		List<Reserva> reservas = reservaService.findByIdClient(usuario.getId(), pageIndex, pageSize);

		List<ReservaDTO> reservaListDTO = new ArrayList();

		Iterator<Reserva> it = reservas.iterator();

		while (it.hasNext()) {
			Reserva reserva = it.next();
			ReservaDTO reservaDTO = new ReservaDTO();
			reservaDTO.setEstado(reserva.getEstado());
			reservaDTO.setId(reserva.getId());
			Usuario cliente = usuarioService.findById(reserva.getIdClient());
			reservaDTO.setCliente(cliente.getUsername());
			Usuario chef = usuarioService.findById(reserva.getIdChef());
			reservaDTO.setChef(chef.getUsername());
			reservaDTO.setIncio(reserva.getInicio());
			reservaDTO.setFin(reserva.getFin());
			reservaDTO.setPrecio(reserva.getPrecio());
			reservaDTO.setComensales(reserva.getComensales());
			reservaDTO.setInstruccions("");
			reservaListDTO.add(reservaDTO);
		}
		;

		return new ResponseEntity<>(reservaListDTO, HttpStatus.OK);
	}

	/**
	 * Clase getListaReservaChef
	 *
	 * retorna una llista de reserves d'un chef amb paginació
	 */
	@GetMapping("/reserva/get/chef/paginable")
	public ResponseEntity<?> getListaReservaChef(String usernameOrEmail, int pageIndex, int pageSize) {

		Usuario usuario = usuarioService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
		String chefUserName = usuario.getUsername();

		if (!utils.usuarioAutorizado(chefUserName, SecurityContextHolder.getContext().getAuthentication())) {
			return new ResponseEntity<>("Usuario no autorizado", HttpStatus.BAD_REQUEST);
		}

		List<Reserva> reservas = reservaService.findByIdChef(usuario.getId(), pageIndex, pageSize);

		List<ReservaDTO> reservaListDTO = new ArrayList();

		Iterator<Reserva> it = reservas.iterator();

		while (it.hasNext()) {
			Reserva reserva = it.next();
			ReservaDTO reservaDTO = new ReservaDTO();
			reservaDTO.setEstado(reserva.getEstado());
			reservaDTO.setId(reserva.getId());
			Usuario cliente = usuarioService.findById(reserva.getIdClient());
			reservaDTO.setCliente(cliente.getUsername());
			Usuario chef = usuarioService.findById(reserva.getIdChef());
			reservaDTO.setChef(chef.getUsername());
			reservaDTO.setIncio(reserva.getInicio());
			reservaDTO.setFin(reserva.getFin());
			reservaDTO.setPrecio(reserva.getPrecio());
			reservaDTO.setComensales(reserva.getComensales());
			reservaDTO.setInstruccions("");
			reservaListDTO.add(reservaDTO);
		}
		;

		return new ResponseEntity<>(reservaListDTO, HttpStatus.OK);
	}
}

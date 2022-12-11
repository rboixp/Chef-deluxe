package com.chefdeluxe.app.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chefdeluxe.app.dto.ReservaDTO;
import com.chefdeluxe.app.dto.TarifaDTO;
import com.chefdeluxe.app.entidades.Menu;
import com.chefdeluxe.app.entidades.Reserva;
import com.chefdeluxe.app.entidades.Rol;
import com.chefdeluxe.app.entidades.Tarifa;
import com.chefdeluxe.app.entidades.Usuario;
import com.chefdeluxe.app.service.MenuService;
import com.chefdeluxe.app.service.RolService;
import com.chefdeluxe.app.service.TarifaService;
import com.chefdeluxe.app.service.UsuarioService;
import com.chefdeluxe.app.utils.Utils;

@RestController
@RequestMapping("api/chef/tarifa")
public class TarifaController {
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RolService rolService;

	@Autowired
	private TarifaService tarifaService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private Utils utils;

	@PersistenceContext
	EntityManager em;

	/**
	 * End point altaTarifa
	 *
	 * Registra una tarifa a la base de dades.
	 */
	@PostMapping("/post")
	public ResponseEntity<?> altaTarifa(@RequestBody TarifaDTO tarifaDTO) {

		Usuario user;

		if (!utils.usuarioEsDelRol("ROLE_ADMIN", SecurityContextHolder.getContext().getAuthentication())
				&& !utils.usuarioEsDelRol("ROLE_CHEF", SecurityContextHolder.getContext().getAuthentication())) {
			return new ResponseEntity<>("token no valido, no es de chef ni admin", HttpStatus.BAD_REQUEST);
		}

		if (tarifaDTO.getIdchef() == 0 && tarifaDTO.getUsernameOrEmail().isBlank()) {
			return new ResponseEntity<>("Falta informar el Id del chef o el usuario o el mail", HttpStatus.BAD_REQUEST);
		} else {

			try {
				if (tarifaDTO.getIdchef() > 0) {
					user = usuarioService.findById(tarifaDTO.getIdchef());
				} else {
					user = usuarioService.findByUsernameOrEmail(tarifaDTO.getUsernameOrEmail(),
							tarifaDTO.getUsernameOrEmail());
				}
			} catch (Exception e) {
				return new ResponseEntity<>("Chef no existe en la base de datos", HttpStatus.BAD_REQUEST);
			}

			Rol rol = rolService.findByRole("ROLE_CHEF");

			if (!user.getRoles().contains(rol)) {
				return new ResponseEntity<>(
						"Chef <" + user.getUsername() + "> no es chef, tiene perfil admin o cliente",
						HttpStatus.BAD_REQUEST);
			}
		}

		if (tarifaDTO.getPrecioHora().equals(0)) {
			return new ResponseEntity<>("Falta informar el precio hora", HttpStatus.BAD_REQUEST);
		}

		try {
			Tarifa tarduplicada = tarifaService.findByIdChef(user.getId());
			if (tarduplicada.getId() > 0) {
				return new ResponseEntity<>("No se puede dar de alta. Chef ya tiene tarifa ", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
		}

		Menu menu = new Menu();

		menu.setIdChef(user.getId());
		menu.setEntrante(tarifaDTO.getEntrante());
		menu.setPrimero(tarifaDTO.getPrimero());
		menu.setSegundo(tarifaDTO.getSegundo());
		menu.setPostre(tarifaDTO.getPostre());
		menu.setCafes(tarifaDTO.getCafes());
		menuService.save(menu);

		Tarifa tarifa = new Tarifa();
		tarifa.setIdChef(user.getId());
		tarifa.setPreciohora(tarifaDTO.getPrecioHora());
		tarifa.setIdMenu(menu.getId());
		tarifaService.save(tarifa);

		return new ResponseEntity<>("tarifa dada de alta exitosamente", HttpStatus.OK);
	}

	/**
	 * End point deleteReserva
	 *
	 * Esborra una tarifa a la base de dades.
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteTarifa(@RequestParam Long id) {

		if (tarifaService.findById(id) == null) {
			return new ResponseEntity<>("No existe este Id <" + id + ">  de tarifa ", HttpStatus.BAD_REQUEST);
		}

		Long idChef = tarifaService.findById(id).getIdChef();

		String username = usuarioService.findById(idChef).getUsername();

		if (!utils.usuarioAutorizado(username, SecurityContextHolder.getContext().getAuthentication())) {
			return new ResponseEntity<>(
					"Solo se permite delete para el usuario administrador o el chef que dio de alta la reserva"
							+ " username <" + username + ">" + " token <"
							+ SecurityContextHolder.getContext().getAuthentication() + ">",
					HttpStatus.BAD_REQUEST);
		}

		if (menuService.findById(tarifaService.findById(id).getIdMenu()) != null) {
			menuService.deleteById(tarifaService.findById(id).getIdMenu());
		}

		tarifaService.deleteById(id);

		return new ResponseEntity<>("el registro de tarifa con id <" + id + "> se ha eliminado exitosamente",
				HttpStatus.OK);

	}

	/**
	 * End point getById
	 *
	 * retorna una tarifa de la base de dades filtrada per id.
	 */

	@GetMapping("/get/id")
	public ResponseEntity<?> getById(@RequestParam Long id) {

		if (tarifaService.findById(id) == null) {
			return new ResponseEntity<>("No existe este Id <" + id + ">  de tarifa ", HttpStatus.BAD_REQUEST);
		}

		Tarifa tarifa = tarifaService.findById(id);

		if (usuarioService.findById(tarifa.getIdChef()) == null) {
			return new ResponseEntity<>("No existe este chef con id <" + id + "> ", HttpStatus.BAD_REQUEST);
		}

		String chef = usuarioService.findById(tarifa.getIdChef()).getUsername();

		if (!utils.usuarioAutorizado(chef, SecurityContextHolder.getContext().getAuthentication())) {
			return new ResponseEntity<>("Solo se permite consultar la tarifa al chef que la realizó o al admin",
					HttpStatus.BAD_REQUEST);
		}

		TarifaDTO tarifaDTO = new TarifaDTO();
		tarifaDTO.setId(tarifa.getId());
		tarifaDTO.setIdchef(tarifa.getIdChef());
		tarifaDTO.setPrecioHora(tarifa.getPreciohora());

		Menu menu = menuService.findById(tarifa.getIdMenu());

		tarifaDTO.setEntrante(menu.getEntrante());
		tarifaDTO.setPrimero(menu.getPrimero());
		tarifaDTO.setSegundo(menu.getSegundo());
		tarifaDTO.setPostre(menu.getPostre());
		tarifaDTO.setCafes(menu.getCafes());
		tarifaDTO.setUsernameOrEmail(chef);

		return new ResponseEntity<>(tarifaDTO, HttpStatus.OK);
	}

	/**
	 * End point getById
	 *
	 * retorna una tarifa de la base de dades filtrada per id.
	 */

	@GetMapping("/get/user")
	public ResponseEntity<?> getByUsernameOrEmail(@RequestParam String usernameOrEmail) {

		Usuario user;
		Tarifa tarifa;
		try {
			user = usuarioService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
		} catch (Exception e) {
			return new ResponseEntity<>("No existe este usuario <" + usernameOrEmail + "> ", HttpStatus.BAD_REQUEST);
		}

		if (!utils.usuarioAutorizado(user.getUsername(), SecurityContextHolder.getContext().getAuthentication())) {
			return new ResponseEntity<>("Solo se permite consultar la tarifa al chef que la realizó o al admin",
					HttpStatus.BAD_REQUEST);
		}
		System.out.println("Chef tarifa" + user.getId());
		tarifa = tarifaService.findByIdChef(user.getId());

		if (tarifa == null) {
			return new ResponseEntity<>("El usuario " + usernameOrEmail + "no tiene tarifas ", HttpStatus.BAD_REQUEST);
		}

		TarifaDTO tarifaDTO = new TarifaDTO();
		tarifaDTO.setId(tarifa.getId());
		tarifaDTO.setUsernameOrEmail(usernameOrEmail);
		tarifaDTO.setIdchef(tarifa.getIdChef());
		tarifaDTO.setPrecioHora(tarifa.getPreciohora());

		Menu menu = menuService.findById(tarifa.getIdMenu());

		tarifaDTO.setEntrante(menu.getEntrante());
		tarifaDTO.setPrimero(menu.getPrimero());
		tarifaDTO.setSegundo(menu.getSegundo());
		tarifaDTO.setPostre(menu.getPostre());
		tarifaDTO.setCafes(menu.getCafes());

		return new ResponseEntity<>(tarifaDTO, HttpStatus.OK);
	}

	/**
	 * Clase getListaTarifas
	 *
	 * retorna una llista de tarifes paginable.
	 */
	@GetMapping("/get/all")
	public ResponseEntity<?> getListaTarifas(int pageIndex, int pageSize) {

		if (!utils.usuarioEsDelRol("ROLE_ADMIN", SecurityContextHolder.getContext().getAuthentication())) {
			return new ResponseEntity<>("Usuario no es admin ", HttpStatus.BAD_REQUEST);
		}

		List<Tarifa> tarifas = tarifaService.findAll(pageIndex, pageSize);

		List<TarifaDTO> tarifaListDTO = new ArrayList();

		Iterator<Tarifa> it = tarifas.iterator();

		while (it.hasNext()) {
			Tarifa tarifa = it.next();
			TarifaDTO tarifaDTO = new TarifaDTO();
			tarifaDTO.setId(tarifa.getId());
			tarifaDTO.setIdchef(tarifa.getIdChef());
			tarifaDTO.setPrecioHora(tarifa.getPreciohora());

			Menu menu = menuService.findById(tarifa.getIdMenu());
			if (menu != null) {
				tarifaDTO.setEntrante(menu.getEntrante());
				tarifaDTO.setPrimero(menu.getPrimero());
				tarifaDTO.setSegundo(menu.getSegundo());
				tarifaDTO.setPostre(menu.getPostre());
				tarifaDTO.setCafes(menu.getCafes());
			}
			tarifaDTO.setUsernameOrEmail(usuarioService.findById(tarifa.getIdChef()).getUsername());

			tarifaListDTO.add(tarifaDTO);
		}
		;

		return new ResponseEntity<>(tarifaListDTO, HttpStatus.OK);
	}

	/**
	 * End point UpdateTarifa
	 *
	 * Actualitza una tarifa.
	 */
	@PutMapping("/update")
	public ResponseEntity<?> UpdateTarifa(@RequestBody TarifaDTO tarifaDTO) {

		long id = tarifaDTO.getId();
		Tarifa tarifa = tarifaService.findById(id);

		if (tarifa == null) {
			return new ResponseEntity<>("No existe este Id <" + id + ">  de tarifa ", HttpStatus.BAD_REQUEST);
		}
		try {
			String usuarioReserva = usuarioService.findById(tarifa.getIdChef()).getUsername();
		} catch (Exception e) {
			return new ResponseEntity<>("idChef <" + tarifa.getIdChef() + "> no existe en base de datos ",
					HttpStatus.BAD_REQUEST);
		}

		if (!utils.usuarioEsDelRol("ROLE_CHEF", SecurityContextHolder.getContext().getAuthentication())
				&& !utils.usuarioEsDelRol("ROLE_ADMIN", SecurityContextHolder.getContext().getAuthentication())) {
			return new ResponseEntity<>("Usuario no es chef ni admin", HttpStatus.BAD_REQUEST);
		}

		Tarifa tarifaUpd = new Tarifa();

		tarifaUpd.setId(tarifaDTO.getId());
		tarifaUpd.setIdChef(tarifa.getIdChef());
		tarifaUpd.setPreciohora(tarifaDTO.getPrecioHora());
		tarifaUpd.setIdMenu(tarifa.getIdMenu());
		tarifaService.save(tarifaUpd);
		tarifaDTO.setId(tarifaUpd.getId());
		tarifaDTO.setIdchef(tarifaUpd.getIdChef());
		tarifaDTO.setPrecioHora(tarifaUpd.getPreciohora());

		Menu menu = menuService.findById(tarifa.getIdMenu());
		if (menu != null) {
			menu.setEntrante(tarifaDTO.getEntrante());
			menu.setPrimero(tarifaDTO.getPrimero());
			menu.setSegundo(tarifaDTO.getSegundo());
			menu.setPostre(tarifaDTO.getPostre());
			menu.setCafes(tarifaDTO.getCafes());
			menuService.save(menu);
		}

		return new ResponseEntity<>("Tarifa y menu actualizados ", HttpStatus.BAD_REQUEST);

	}

}

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

import com.chefdeluxe.app.dto.MenuDTO;
import com.chefdeluxe.app.dto.TarifaDTO;
import com.chefdeluxe.app.entidades.Menu;
import com.chefdeluxe.app.entidades.Rol;
import com.chefdeluxe.app.entidades.Tarifa;
import com.chefdeluxe.app.entidades.Usuario;
import com.chefdeluxe.app.service.MenuService;
import com.chefdeluxe.app.service.RolService;
import com.chefdeluxe.app.service.TarifaService;
import com.chefdeluxe.app.service.UsuarioService;
import com.chefdeluxe.app.utils.Utils;

@RestController
@RequestMapping("api/chef/menu")
public class MenuController {
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
	 * End point altaMenu
	 *
	 * Registra un menu a la base de dades.
	 */
	@PostMapping("/post")
	public ResponseEntity<?> altaMenu(@RequestBody MenuDTO menuDTO) {

		Usuario user;

		if (!utils.usuarioEsDelRol("ROLE_ADMIN", SecurityContextHolder.getContext().getAuthentication())
				&& !utils.usuarioEsDelRol("ROLE_CHEF", SecurityContextHolder.getContext().getAuthentication())) {
			return new ResponseEntity<>("token no valido, no es de chef ni admin", HttpStatus.BAD_REQUEST);
		}

		if (menuDTO.getIdChef() == 0) {
			return new ResponseEntity<>("Falta informar el Id del chef ", HttpStatus.BAD_REQUEST);
		} else {

			try {
				user = usuarioService.findById(menuDTO.getIdChef());

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

		if (menuDTO.getEntrante().isBlank() || menuDTO.getPrimero().isBlank() || menuDTO.getSegundo().isBlank()
				|| menuDTO.getPostre().isBlank() || menuDTO.getCafes().isBlank()) {
			return new ResponseEntity<>("Falta el menu completo ", HttpStatus.BAD_REQUEST);
		}

		try {
			Menu menu = menuService.findByIdChef(user.getId());
			if (menu.getId() > 0) {
				return new ResponseEntity<>("No se puede dar de alta. Chef ya tiene menu ", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
		}

		Menu menu = new Menu();

		menu.setIdChef(user.getId());
		menu.setEntrante(menuDTO.getEntrante());
		menu.setPrimero(menuDTO.getPrimero());
		menu.setSegundo(menuDTO.getSegundo());
		menu.setPostre(menuDTO.getPostre());
		menu.setCafes(menuDTO.getCafes());
		menuService.save(menu);

		return new ResponseEntity<>("Menu dado de alta exitosamente", HttpStatus.OK);
	}

	/**
	 * End point deleteMenu
	 *
	 * Esborra un menu de la base de dades.
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteMenu(@RequestParam Long id) {

		if (menuService.findById(id) == null) {
			return new ResponseEntity<>("No existe este Id <" + id + ">  de menu ", HttpStatus.BAD_REQUEST);
		}

		Long idChef = menuService.findById(id).getIdChef();

		String username = usuarioService.findById(idChef).getUsername();

		if (!utils.usuarioAutorizado(username, SecurityContextHolder.getContext().getAuthentication())) {
			return new ResponseEntity<>(
					"Solo se permite delete para el usuario administrador o el chef que dio de alta el menu"
							+ " username <" + username + ">",HttpStatus.BAD_REQUEST);
					
		}

		menuService.deleteById(menuService.findById(id).getId());

		return new ResponseEntity<>("el registro de menu con id <" + id + "> se ha eliminado exitosamente",
				HttpStatus.OK);

	}

	/**
	 * End point getById
	 *
	 * retorna un menu de la base de dades filtrada per id.
	 */

	@GetMapping("/get/id")
	public ResponseEntity<?> getById(@RequestParam Long id) {

		if (menuService.findById(id) == null) {
			return new ResponseEntity<>("No existe este Id <" + id + ">  de menu ", HttpStatus.BAD_REQUEST);
		}

		Menu menu = menuService.findById(id);

		if (usuarioService.findById(menu.getIdChef()) == null) {
			return new ResponseEntity<>("No existe este chef con id <" + id + "> ", HttpStatus.BAD_REQUEST);
		}

		String chef = usuarioService.findById(menu.getIdChef()).getUsername();

		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setEntrante(menu.getEntrante());
		menuDTO.setPrimero(menu.getPrimero());
		menuDTO.setSegundo(menu.getSegundo());
		menuDTO.setPostre(menu.getPostre());
		menuDTO.setCafes(menu.getCafes());
		menuDTO.setIdChef(menu.getIdChef());
		menuDTO.setId(menu.getId());

		return new ResponseEntity<>(menuDTO, HttpStatus.OK);
	}

	/**
	 * Clase getListaMenu
	 *
	 * retorna una llista de menus paginable.
	 */
	@GetMapping("/get/all")
	public ResponseEntity<?> getListaMenu(int pageIndex, int pageSize) {

		List<Menu> menuList = menuService.findAll(pageIndex, pageSize);

		List<MenuDTO> menuListDTO = new ArrayList();

		Iterator<Menu> it = menuList.iterator();

		while (it.hasNext()) {
			Menu menu = it.next();
			MenuDTO menuDTO = new MenuDTO();
			menuDTO.setEntrante(menu.getEntrante());
			menuDTO.setPrimero(menu.getPrimero());
			menuDTO.setSegundo(menu.getSegundo());
			menuDTO.setPostre(menu.getPostre());
			menuDTO.setCafes(menu.getCafes());
			menuDTO.setId(menu.getId());
			menuDTO.setIdChef(menu.getIdChef());
			menuListDTO.add(menuDTO);
		}
		;

		return new ResponseEntity<>(menuListDTO, HttpStatus.OK);
	}

	/**
	 * End point UpdateMenu
	 *
	 * Actualitza un menu.
	 */
	@PutMapping("/update")
	public ResponseEntity<?> UpdateMenu(@RequestBody MenuDTO menuDTO) {

		long id = menuDTO.getId();
		Menu menu = menuService.findById(id);

		if (menu == null) {
			return new ResponseEntity<>("No existe este Id <" + id + ">  de menu ", HttpStatus.BAD_REQUEST);
		}
		try {
			String usuarioReserva = usuarioService.findById(menu.getIdChef()).getUsername();
		} catch (Exception e) {
			return new ResponseEntity<>("idChef <" + menu.getIdChef() + "> no existe en base de datos ",
					HttpStatus.BAD_REQUEST);
		}

		if (!utils.usuarioEsDelRol("ROLE_CHEF", SecurityContextHolder.getContext().getAuthentication())
				&& !utils.usuarioEsDelRol("ROLE_ADMIN", SecurityContextHolder.getContext().getAuthentication())) {
			return new ResponseEntity<>("Usuario no es chef ni admin", HttpStatus.BAD_REQUEST);
		}

		Menu menuUpd = new Menu();

		menuUpd.setId(menuDTO.getId());
		menuUpd.setIdChef(menuDTO.getIdChef());
		menu.setEntrante(menuDTO.getEntrante());
		menu.setPrimero(menuDTO.getPrimero());
		menu.setSegundo(menuDTO.getSegundo());
		menu.setPostre(menuDTO.getPostre());
		menu.setCafes(menuDTO.getCafes());
		menuService.save(menu);

		return new ResponseEntity<>("Menu actualizado ", HttpStatus.BAD_REQUEST);

	}

}

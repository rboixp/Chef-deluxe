package com.chefdeluxe.app.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.chefdeluxe.app.entidades.Menu;
import com.chefdeluxe.app.entidades.Tarifa;
import com.chefdeluxe.app.repositorio.Menurepositorio;
import com.chefdeluxe.app.repositorio.TarifaRepositorio;

public class MenuServiceTest {

	
	@Mock
	private Menurepositorio menuRepositorio;
	
	@InjectMocks
	private MenuService menuService;
	
	private Menu menu;
	private Optional<Menu> opMenu;
	private List <Menu> menuList;
	private Page<Menu> pageT;
	private Pageable pageable;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);  
		menu = new Menu();
		menu.setIdChef(1);
 		menu.setEntrante("Consome");
 		menu.setPrimero("Ensalada verde");
 		menu.setSegundo("Pollo a la plancha");
 		menu.setPostre("flan");
 		menu.setCafes("todo incluido");
		opMenu =Optional.of(menu);
		menuList = new ArrayList<Menu>();
		menuList.add(menu);	
		menu.setIdChef(2);
 		menu.setEntrante("Sopa");
 		menu.setPrimero("Macarrones");
 		menu.setSegundo("Pavo a la plancha");
 		menu.setPostre("tarta limon");
 		menu.setCafes("no incluido");
		menuList.add(menu);
		pageT = new PageImpl(menuList);
		
	}
	
	@Test
	public void alta_menu() {
		when(menuRepositorio.save(any(Menu.class))).thenReturn(menu);
		menuService.save(menu);
		verify(menuRepositorio).save(menu);
	}
	@Test
	public void findById() {
		when(menuRepositorio.findById(any(Long.class))).thenReturn(opMenu);
		Assertions.assertNotNull(menuService.findById(menu.getId()));
	}
	
	@Test
	public void findByIdChef() {
		when(menuRepositorio.findByIdChef(any(Long.class))).thenReturn(opMenu);
		Assertions.assertNotNull(menuService.findByIdChef(menu.getIdChef()));
	}
	@Test
	public void findAll() {
		when(menuRepositorio.findAll()).thenReturn(menuList);
		Assertions.assertNotNull(menuService.findAll());
	}
	@Test
	public void update() {
		when(menuRepositorio.findById(any(Long.class))).thenReturn(opMenu);
		menuService.save(menu);
		verify(menuRepositorio).save(menu);		

	}
	@Test
	public void deleteById() {
		when(menuRepositorio.findById(any(Long.class))).thenReturn(opMenu);
		Long uno = 1L;
		menuService.deleteById(uno);
		verify(menuRepositorio, times(1)).deleteById(uno);

	}

}


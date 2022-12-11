package com.chefdeluxe.app.repositorio;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.chefdeluxe.app.entidades.Menu;
import com.chefdeluxe.app.entidades.Tarifa;

@DataJpaTest
public class MenuRepositorioTest {
	
	@Autowired
	private Menurepositorio menurepositorio;
	Menu menu = new Menu();
	
	@Test
	public void testSavemenu() {
		menu.setIdChef(1);
 		menu.setEntrante("Consome");
 		menu.setPrimero("Ensalada verde");
 		menu.setSegundo("Pollo a la plancha");
 		menu.setPostre("flan");
 		menu.setCafes("todo incluido");
		menurepositorio.save(menu);
		Assertions.assertTrue( menu.getId() > 0);   
	}

	@Test
	public void testMenubyId() {
		menu.setIdChef(1L);
 		menu.setEntrante("Consome");
 		menu.setPrimero("Ensalada verde");
 		menu.setSegundo("Pollo a la plancha");
 		menu.setPostre("flan");
 		menu.setCafes("todo incluido");
		menurepositorio.save(menu);
		Menu menuget = menurepositorio.findById(menu.getId()).get(); 
		Assertions.assertTrue( !menuget.equals(null));  
	}
	
	@Test
	public void testListatarifaIdChef() {
		menu.setId(99L);
		menu.setIdChef(1L);
 		menu.setEntrante("Consome");
 		menu.setPrimero("Ensalada verde");
 		menu.setSegundo("Pollo a la plancha");
 		menu.setPostre("flan");
 		menu.setCafes("todo incluido");
		menurepositorio.save(menu);
		Menu menuget = menurepositorio.findByIdChef(1L).get(); 
		Assertions.assertTrue( !menuget.equals(null));  
	}
	
	@Test
	public void testListaMenuAll() {	
		
		menu.setId(99);
		menu.setIdChef(1);
 		menu.setEntrante("Consome");
 		menu.setPrimero("Ensalada verde");
 		menu.setSegundo("Pollo a la plancha");
 		menu.setPostre("flan");
 		menu.setCafes("todo incluido");
		menurepositorio.save(menu);
		
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(0, 5, sort);
		
		List<Menu> menuGet = menurepositorio.findAll(pageable).getContent();
	    Assertions.assertTrue( !menuGet.isEmpty());  
	}
	@Test
	public void testDeleteMenu() {	
		menu.setIdChef(1L);
 		menu.setEntrante("Consome");
 		menu.setPrimero("Ensalada verde");
 		menu.setSegundo("Pollo a la plancha");
 		menu.setPostre("flan");
 		menu.setCafes("todo incluido");
		menurepositorio.save(menu);
		menurepositorio.deleteById(menu.getId());
	}	

}

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

import com.chefdeluxe.app.entidades.Tarifa;

@DataJpaTest
public class TarifaRepositorioTest {
	
	@Autowired
	private TarifaRepositorio tarifaRepositorio;
	Tarifa tarifa = new Tarifa();
	
	@Test
	public void testSavetarifa() {
		tarifa.setIdChef(1);
 		tarifa.setPreciohora(new BigDecimal("50.00"));
		tarifaRepositorio.save(tarifa);
		Assertions.assertTrue( tarifa.getId() > 0);   
	}

	@Test
	public void testListatarifindbyId() {
		tarifa.setIdChef(1L);
		tarifa.setPreciohora(new BigDecimal("50.00"));
		tarifaRepositorio.save(tarifa);
		Tarifa tarifaget = tarifaRepositorio.findById(tarifa.getId()).get(); 
		Assertions.assertTrue( !tarifaget.equals(null));  
	}
	
	@Test
	public void testListatarifaIdChef() {
		tarifa.setIdChef(1);
		tarifa.setPreciohora(new BigDecimal("50.00"));
		tarifaRepositorio.save(tarifa);
		Tarifa tarifaget = tarifaRepositorio.findByIdChef(1L).get(); 
		Assertions.assertTrue( !tarifaget.equals(null));  
	}
	
	@Test
	public void testListatarifaAll() {	
		
		tarifa.setIdChef(1);
		tarifa.setPreciohora(new BigDecimal("50.00"));
		tarifaRepositorio.save(tarifa);
		
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(0, 5, sort);
		
		List<Tarifa> tarifaGet = tarifaRepositorio.findAll(pageable).getContent();
	    Assertions.assertTrue( !tarifaGet.isEmpty());  
	}
	@Test
	public void testDeletetarifa() {	
		tarifa.setIdChef(99L);
		tarifa.setPreciohora(new BigDecimal("50.00"));
		tarifaRepositorio.save(tarifa);
	    tarifaRepositorio.deleteById(tarifa.getId());
	}	

}

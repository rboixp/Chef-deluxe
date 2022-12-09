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

import com.chefdeluxe.app.entidades.Reserva;
import com.chefdeluxe.app.entidades.Rol;
import com.chefdeluxe.app.entidades.Tarifa;
import com.chefdeluxe.app.repositorio.TarifaRepositorio;

public class TarifaServiceTest {
	
	@Mock
	private TarifaRepositorio tarifaRepositorio;
	
	@InjectMocks
	private TarifaService tarifaService;
	
	private Tarifa tarifa;
	private Optional<Tarifa> opTarifa;
	private List <Tarifa> tarifaList;
	private Page<Tarifa> pageT;
	private Pageable pageable;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);  
		tarifa = new Tarifa();
		tarifa.setIdChef(1);
		tarifa.setPreciohora(new BigDecimal("50.10"));
		opTarifa =Optional.of(tarifa);
		tarifaList = new ArrayList<Tarifa>();
		tarifaList.add(tarifa);
		tarifa.setIdChef(2);
		tarifa.setPreciohora(new BigDecimal("50.10"));
		tarifaList.add(tarifa);
		pageT = new PageImpl(tarifaList);
		
	}
	
	@Test
	public void alta_tarifa() {
		when(tarifaRepositorio.save(any(Tarifa.class))).thenReturn(tarifa);
		tarifaService.save(tarifa);
		verify(tarifaRepositorio).save(tarifa);
	}
	@Test
	public void findById() {
		when(tarifaRepositorio.findById(any(Long.class))).thenReturn(opTarifa);
		Assertions.assertNotNull(tarifaService.findById(tarifa.getId()));
	}
	
	@Test
	public void findByIdChef() {
		when(tarifaRepositorio.findByIdChef(any(Long.class))).thenReturn(opTarifa);
		Assertions.assertNotNull(tarifaService.findByIdChef(tarifa.getIdChef()));
	}
	@Test
	public void findAll() {
		when(tarifaRepositorio.findAll(pageable)).thenReturn(pageT);
		Assertions.assertNotNull(tarifaService.findAll(1,1));
	}
	@Test
	public void update() {
		when(tarifaRepositorio.findById(any(Long.class))).thenReturn(opTarifa);
		tarifaService.save(tarifa);
		verify(tarifaRepositorio).save(tarifa);		

	}
	@Test
	public void deleteById() {
		when(tarifaRepositorio.findById(any(Long.class))).thenReturn(opTarifa);
		Long uno = 1L;
		tarifaService.deleteById(uno);
		verify(tarifaRepositorio, times(1)).deleteById(uno);

	}

}

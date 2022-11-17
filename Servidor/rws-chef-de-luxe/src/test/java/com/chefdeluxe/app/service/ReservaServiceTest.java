package com.chefdeluxe.app.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.chefdeluxe.app.entidades.Disponibilidad;
import com.chefdeluxe.app.entidades.Reserva;
import com.chefdeluxe.app.entidades.Usuario;
import com.chefdeluxe.app.repositorio.ReservaRepositorio;
import com.chefdeluxe.app.repositorio.ReservaRepositorioTest;
import com.chefdeluxe.app.repositorio.UsuarioRepositorio;

public class ReservaServiceTest {
	
	
	@Mock
	private ReservaRepositorio reservaRepositorio;
	
	@InjectMocks
	private ReservaService reservaService;
	
	private Reserva reserva;
	private Optional<Reserva> opReserva;
	private List <Reserva> reservaList;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);  
		Timestamp time = new Timestamp(2020, 11, 12, 0, 0, 0, 0);
		time.setDate(20220101);
		time.setTime(16);
		
		reserva = new Reserva();
		reserva.setId(1);
		reserva.setEstado("pendiente");
		reserva.setFin(time);
		reserva.setIdChef(1);
		reserva.setIdClient(1);
		reserva.setIncio(time);
		reserva.setPrecio(new BigDecimal("34.12"));
		opReserva =Optional.of(reserva);
		reservaList = new ArrayList<Reserva>();
		reservaList.add(reserva);
		
	}
	@Test
	public void save() {
		when(reservaRepositorio.save(any(Reserva.class))).thenReturn(reserva);
		reservaService.save(reserva);
		verify(reservaRepositorio).save(reserva);
	}
	@Test
	public void findById() {
		when(reservaRepositorio.findById(any(Long.class))).thenReturn(opReserva);
		Assertions.assertNotNull(reservaService.findById(reserva.getId()));
	}
	@Test
	public void findAll() {
		when(reservaRepositorio.findAll()).thenReturn(reservaList);
		Assertions.assertNotNull(reservaService.findAll());
	}
	@Test
	public void flush() {
		when(reservaRepositorio.findById(any(Long.class))).thenReturn(opReserva);
		reservaService.flush(reserva.getId(),reserva.getEstado());
		verify(reservaRepositorio).flush();		


	}
	@Test
	public void deleteById() {
		when(reservaRepositorio.findById(any(Long.class))).thenReturn(opReserva);
		Long uno = 1L;
		reservaService.deleteById(uno);
		verify(reservaRepositorio, times(1)).deleteById(uno);

	}
	
	

}

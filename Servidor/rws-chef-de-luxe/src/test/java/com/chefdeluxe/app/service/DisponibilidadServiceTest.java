package com.chefdeluxe.app.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.chefdeluxe.app.entidades.Rol;
import com.chefdeluxe.app.entidades.Usuario;
import com.chefdeluxe.app.repositorio.DisponibilidadRepositorio;
import com.chefdeluxe.app.repositorio.UsuarioRepositorio;

public class DisponibilidadServiceTest {

	
	@Mock
	private DisponibilidadRepositorio dispoRepo;
	
	@InjectMocks
	private DisponibilidadService disponibilidService;
	
	private Disponibilidad disponibilidad;
	private Optional<Disponibilidad> opDisponibilidad;
	private List <Disponibilidad> disponibilidadList;
	
	@BeforeEach
	void setUp() {
		disponibilidad = new Disponibilidad();
		MockitoAnnotations.openMocks(this);  
		disponibilidad.setId(1);
		disponibilidad.setIdUser(2);	
		disponibilidad.setPoblacion("Barcelona");
		disponibilidad.setEstado("Pendiente");
		opDisponibilidad =Optional.of(disponibilidad);
		disponibilidadList = new ArrayList<Disponibilidad>();
		disponibilidadList.add(disponibilidad);		
	}
	
	@Test
	public void alta_disponibilitat() {
		when(dispoRepo.save(any(Disponibilidad.class))).thenReturn(disponibilidad);
		disponibilidService.save(opDisponibilidad.get());
		verify(dispoRepo).save(opDisponibilidad.get());
	}
	
	@Test
	public void findByIdUser() {
		when(dispoRepo.findByIdUser(any(Long.class))).thenReturn(disponibilidadList);
		Assertions.assertNotNull(disponibilidService.findByIdUser(disponibilidad.getIduser()));
	}
	@Test
	public void findById() {
		when(dispoRepo.findById(any(Long.class))).thenReturn(opDisponibilidad);
		Assertions.assertNotNull(disponibilidService.findById(disponibilidad.getId()));
	}
	@Test
	public void findAll() {
		when(dispoRepo.findAll()).thenReturn(disponibilidadList);
		Assertions.assertNotNull(disponibilidService.findAll());
	}
	@Test
	public void deleteById() {
		Long uno = 1L;
	 	disponibilidService.deleteById(uno);
		verify(dispoRepo, times(1)).deleteById(uno);
	}
	@Test
	public void update() {
		when(dispoRepo.save(any(Disponibilidad.class))).thenReturn(disponibilidad);
		disponibilidService.save(opDisponibilidad.get());
		verify(dispoRepo).save(opDisponibilidad.get());
		
	}
	
	
}

package com.chefdeluxe.app.service;

import static org.mockito.ArgumentMatchers.any;
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
	private DisponibilidadRepositorio disponibilidRepositorio;
	
	@InjectMocks
	private DisponibilidadService disponibilidService;
	
	private Disponibilidad disponibilidad;
	private Optional<Disponibilidad> opDisponibilidad;
	private List <Disponibilidad> disponibilidadList;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);  
		disponibilidad = new Disponibilidad();
		disponibilidad.setId(1);
		disponibilidad.setIdUser(2);	
		disponibilidad.setPoblacion("Barcelona");
		disponibilidad.setEstado("Pendiente");
		opDisponibilidad =Optional.of(disponibilidad);
		disponibilidadList = new ArrayList<Disponibilidad>();
		disponibilidadList.add(disponibilidad);
		
		
	}
	
	@Test
	public void save() {
		when(disponibilidRepositorio.save(any(Disponibilidad.class))).thenReturn(disponibilidad);
		disponibilidService.save(opDisponibilidad.get());
		verify(disponibilidRepositorio).save(opDisponibilidad.get());
	}
	
	@Test
	public void findByIdUser() {
		when(disponibilidRepositorio.findByIdUser(disponibilidad.getIduser())).thenReturn(disponibilidadList);
		Assertions.assertNotNull(disponibilidService.findByIdUser(disponibilidad.getIduser()));
	}
	@Test
	public void findById() {
		when(disponibilidRepositorio.findById(disponibilidad.getId())).thenReturn(opDisponibilidad);
		Assertions.assertNotNull(disponibilidService.findById(disponibilidad.getId()));
	}
	@Test
	public void findAll() {
		when(disponibilidRepositorio.findAll()).thenReturn(disponibilidadList);
		Assertions.assertNotNull(disponibilidService.findAll());
	}
	@Test
	public void deleteById() {
		when(disponibilidRepositorio.findById(disponibilidad.getId())).thenReturn(opDisponibilidad);
		disponibilidService.deleteById(disponibilidad.getId());
		verify(disponibilidRepositorio).deleteById(disponibilidad.getId());
	}
	@Test
	public void flush() {
		when(disponibilidRepositorio.findById(disponibilidad.getId())).thenReturn(opDisponibilidad);
		disponibilidService.flush(opDisponibilidad.get());
		verify(disponibilidRepositorio).flush();
		
	}
	
	
}

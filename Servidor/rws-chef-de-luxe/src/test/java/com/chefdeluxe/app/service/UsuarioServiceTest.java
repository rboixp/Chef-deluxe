package com.chefdeluxe.app.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
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

import com.chefdeluxe.app.entidades.Rol;
import com.chefdeluxe.app.entidades.Usuario;
import com.chefdeluxe.app.repositorio.UsuarioRepositorio;

public class UsuarioServiceTest {

	
	@Mock
	private UsuarioRepositorio usuarioRepositorio;
	
	@InjectMocks
	private UsuarioService usuarioService;
	
	private Usuario usuarioTest;
	private Optional<Usuario> opUser;
	private List <Usuario> usuarioList;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);  
		usuarioTest = new Usuario();
		usuarioTest.setId(1);
		usuarioTest.setNombre("Edgar");
		usuarioTest.setUsername	("Edgar01");	
		usuarioTest.setEmail("edgar01@mail.com");
		usuarioTest.setPassword("edgar1234");
		usuarioTest.setApellidos("Perez Perez");
		usuarioTest.setDireccion("Tarragona 12 Baixos");
		usuarioTest.setCodigoPostal("08001");
		usuarioTest.setPoblacion("Barcelona");
		usuarioTest.setNacionalidad("Espanyola");
		usuarioTest.setEdad(25);
		usuarioTest.setTelefono(645788989);
		usuarioTest.setIban("ES801234123412341234");	
		opUser =Optional.of(usuarioTest);
		usuarioList = new ArrayList<Usuario>();
		usuarioList.add(usuarioTest);
		
		
	}
	@Test
	void alta_usuari(){
		when(usuarioRepositorio.save(any(Usuario.class))).thenReturn(usuarioTest);
		usuarioService.save(usuarioTest);
		verify(usuarioRepositorio).save(usuarioTest);
	}
	@Test
	void findByEmail() {
		when(usuarioRepositorio.findByEmail(any(String.class))).thenReturn(opUser);
		Assertions.assertNotNull(usuarioService.findByEmail(usuarioTest.getEmail()));
	}
	@Test
	void	
	findByUsernameOrEmail() {
		when(usuarioRepositorio.findByUsernameOrEmail(any(String.class),any(String.class))).thenReturn(opUser);
		Assertions.assertNotNull(usuarioService.findByUsernameOrEmail(usuarioTest.getEmail(),usuarioTest.getUsername()));
		
	}
	@Test
	void
	findByUsername() {
		when(usuarioRepositorio.findByUsername(any(String.class))).thenReturn(opUser);
		Assertions.assertNotNull(usuarioService.findByUsername(usuarioTest.getUsername()));
	}
	@Test
	void
	findById() {
		when(usuarioRepositorio.findById(any(Long.class))).thenReturn(opUser);
		Assertions.assertNotNull(usuarioService.findById(usuarioTest.getId()));
		
	}
	@Test
	void
	findAll() {
		when(usuarioRepositorio.findAll()).thenReturn(usuarioList);
		Assertions.assertNotNull(usuarioService.findAll());
		
	}
	@Test
	void
	deleteByUsername() {	
		when(usuarioRepositorio.findByUsername(any(String.class))).thenReturn(opUser);
		usuarioService.deleteByUsername(usuarioTest.getUsername());
		verify(usuarioRepositorio).deleteByUsername(usuarioTest.getUsername());	
		
	}
	@Test
	void
	existsByUsername() {
		when(usuarioRepositorio.existsByUsername(any(String.class))).thenReturn(true);
		Assertions.assertNotNull(usuarioService.existsByUsername(usuarioTest.getUsername()));

	}
	@Test
	void
	existsByEmail() {
		when(usuarioRepositorio.existsByEmail(any(String.class))).thenReturn(true);
		Assertions.assertNotNull(usuarioService.existsByEmail(usuarioTest.getEmail()));

		
	}
	@Test
	void
	deleteById() {
		when(usuarioRepositorio.findById(any(Long.class))).thenReturn(opUser); 
		Long uno = 1L;
		usuarioService.deleteById(uno);
		verify(usuarioRepositorio, times(1)).deleteById(uno);
		
	}
	
	@Test
	void update(){
		when(usuarioRepositorio.save(any(Usuario.class))).thenReturn(usuarioTest);
		usuarioService.save(usuarioTest);
		verify(usuarioRepositorio).save(usuarioTest);
	}
}

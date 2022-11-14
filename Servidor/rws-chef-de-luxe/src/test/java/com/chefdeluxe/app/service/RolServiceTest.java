package com.chefdeluxe.app.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.chefdeluxe.app.entidades.Rol;
import com.chefdeluxe.app.repositorio.RolRepositorio;

public class RolServiceTest {
	
	@Mock
	private RolRepositorio rolRepositorio;
	
	@InjectMocks
	private RolService rolService;
	
	private Rol rol;
	private Optional<Rol> opRol;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);  
		rol = new Rol();
		rol.setId(1);
		rol.setRole("ROLE_ADMIN");		
		opRol =Optional.of(rol);
		
		
	}
	@Test
	void save(){
		when(rolRepositorio.save(any(Rol.class))).thenReturn(rol);
		rolService.save(rol);
		verify(rolRepositorio).save(rol);
	}
	@Test
	void findByRole() {
		when(rolRepositorio.findByRole(rol.getRole())).thenReturn(opRol);
		Assertions.assertNotNull(rolService.findByRole("ROLE_ADMIN"));
	}


}

package com.chefdeluxe.app.repositorio;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.chefdeluxe.app.entidades.Reserva;
import com.chefdeluxe.app.entidades.Rol;

@DataJpaTest
public class RolRepositorioTest {

	@Autowired
	private RolRepositorio rolRepositorio;
	Rol rol = new Rol();

	@Test
	public void TestAltaRol() {

		rol.setRole("ROLE_ADMIN");
		rolRepositorio.save(rol);
		Assertions.assertTrue(rol.getId() > 0);
	}

	@Test
	public void TestfindByRole() {
		
		rol.setRole("ROLE_ADMIN");
		rolRepositorio.save(rol);	
		Assertions.assertTrue(!rolRepositorio.findByRole("ROLE_ADMIN").isEmpty());
		

	}

}

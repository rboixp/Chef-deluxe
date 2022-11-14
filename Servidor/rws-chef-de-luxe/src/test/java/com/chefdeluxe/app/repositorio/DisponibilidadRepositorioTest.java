package com.chefdeluxe.app.repositorio;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.chefdeluxe.app.entidades.Disponibilidad;
import com.chefdeluxe.app.entidades.Usuario;
import com.chefdeluxe.app.repositorio.DisponibilidadRepositorio;
import com.chefdeluxe.app.repositorio.UsuarioRepositorio;

@DataJpaTest
public class DisponibilidadRepositorioTest {

	@Autowired
	private DisponibilidadRepositorio disponibilidadRepositorio;
	Disponibilidad disponibilidad = new Disponibilidad();

	@Test
	public void testSaveDisponibilidad() {
		disponibilidad.setIdUser(1);
		disponibilidad.setPoblacion("Barcelona");
		disponibilidad.setEstado("Pendiente");
		disponibilidadRepositorio.save(disponibilidad);
		Assertions.assertTrue( disponibilidad.getId() > 0);   
	}

	@Test
	public void testListaDisponibilidadUserId() {
		disponibilidad.setIdUser(1);
		disponibilidad.setPoblacion("Barcelona");
		disponibilidad.setEstado("Pendiente");
		disponibilidadRepositorio.save(disponibilidad);
		List<Disponibilidad> diponibilidades = disponibilidadRepositorio.findByIdUser(1L); 
		Assertions.assertTrue( !diponibilidades.isEmpty());  
	}
	
	@Test
	public void testListaDisponibilidadAll() {	
		disponibilidad.setIdUser(1);
		disponibilidad.setPoblacion("Barcelona");
		disponibilidad.setEstado("Pendiente");
		disponibilidadRepositorio.save(disponibilidad);	
		disponibilidad.setIdUser(2);
		disponibilidad.setPoblacion("Madrid");
		disponibilidad.setEstado("Pendiente");
		disponibilidadRepositorio.save(disponibilidad);		
		 List<Disponibilidad> diponibilidades = disponibilidadRepositorio.findAll();
	    Assertions.assertTrue( !diponibilidades.isEmpty());  
	}
	@Test
	public void testDeleteDisponibilidad() {	
		disponibilidad.setIdUser(1);
		disponibilidad.setPoblacion("Barcelona");
		disponibilidad.setEstado("Pendiente");
		disponibilidadRepositorio.save(disponibilidad);
	    disponibilidadRepositorio.deleteById(disponibilidad.getId());
	}	

}

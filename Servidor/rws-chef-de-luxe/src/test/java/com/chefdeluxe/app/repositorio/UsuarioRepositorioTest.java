package com.chefdeluxe.app.repositorio;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.chefdeluxe.app.entidades.Usuario;
import com.chefdeluxe.app.repositorio.UsuarioRepositorio;

@DataJpaTest
public class UsuarioRepositorioTest {
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	Usuario usuarioTest = new Usuario();
	
	
   @Test
	public void testCrearUsuari() {
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
	  usuarioRepositorio.save(usuarioTest);
	  Assertions.assertTrue( usuarioTest.getId() > 0);      
	
	}
   @Test	
	public void findByEmail(){
	    Usuario usuarioTest = new Usuario();
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
	    usuarioRepositorio.save(usuarioTest);
	    usuarioTest = usuarioRepositorio.findByEmail("edgar01@mail.com").get();
		Assertions.assertTrue( usuarioTest.getId() > 0 );      
		   
	   }
   @Test	
	public void findByUsernameOrEmail() {
	    Usuario usuarioTest = new Usuario();
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
	    usuarioRepositorio.save(usuarioTest);
	   usuarioTest = usuarioRepositorio.findByUsernameOrEmail("Edgar01","Edgar01").get();
		Assertions.assertTrue( usuarioTest.getId() > 0 ); 
		

		   usuarioTest = usuarioRepositorio.findByUsernameOrEmail("edgar01@mail.com","edgar01@mail.com").get();
			Assertions.assertTrue( usuarioTest.getId() > 0 ); 
		   
	   }

   @Test	
	public void findByUsername(){
	    Usuario usuarioTest = new Usuario();
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
	    usuarioRepositorio.save(usuarioTest);
	   usuarioTest = usuarioRepositorio.findByUsername("Edgar01").get();
	   Assertions.assertTrue( usuarioTest.getId() > 0 ); 
		   
	   }
   @Test	
	public void existsByUsername() {
	    Usuario usuarioTest = new Usuario();
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
	    usuarioRepositorio.save(usuarioTest);
	   Assertions.assertTrue( usuarioRepositorio.existsByUsername("Edgar01") ); 
	   
   }
   @Test	
	public void  existsByEmail() {
	    Usuario usuarioTest = new Usuario();
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
	    usuarioRepositorio.save(usuarioTest);
	   Assertions.assertTrue( usuarioRepositorio.existsByEmail("edgar01@mail.com")); 
	   
   }
   @Test
	public void deleteByUsername() {
	    Usuario usuarioTest = new Usuario();
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
	    usuarioRepositorio.save(usuarioTest);
	   usuarioRepositorio.deleteByUsername("Edgar01");
	   Assertions.assertTrue( !usuarioRepositorio.existsByUsername("Edgar01") ); 
	   
	   
   }
	

}

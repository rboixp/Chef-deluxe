package com.chefdeluxe.app.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.chefdeluxe.app.entidades.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{
	
	 Usuario save(Usuario usuario);

	 Optional<Usuario> findByEmail(String email);
	
	 Optional<Usuario> findByUsernameOrEmail(String username,String email);
	
	 Optional<Usuario> findByUsername(String username);
	 
	 Optional<Usuario>  findById(long id);
	 
	 List<Usuario> findAll();
	 
	 void deleteByUsername(String username);
	
	 Boolean existsByUsername(String username);
	
	 Boolean existsByEmail(String email);	 
	 
	 void deleteById (long id);
	 
//	 void flush (Usuario usuarioUpd); 
	
	
}

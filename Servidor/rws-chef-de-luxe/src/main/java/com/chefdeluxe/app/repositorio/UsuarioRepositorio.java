package com.chefdeluxe.app.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chefdeluxe.app.entidades.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{

	public Optional<Usuario> findByEmail(String email);
	
	public Optional<Usuario> findByUsernameOrEmail(String username,String email);
	
	public Optional<Usuario> findByUsername(String username);
	
	public Boolean existsByUsername(String username);
	
	public Boolean existsByEmail(String email);
	
	public void deleteByUsername(String username);
	
	@Modifying
	@Query("update usuarios u set u.email = ?1, u.password = ?2, u.nombre = ?3, u.username= ?4)  where u.id = ?5")
	void updateUser(String email, String password, String nombre, String username, Long id);
	
	
}

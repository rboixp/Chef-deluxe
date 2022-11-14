package com.chefdeluxe.app.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chefdeluxe.app.entidades.Rol;

public interface RolRepositorio extends JpaRepository<Rol, Long>{

	public Optional<Rol> findByRole(String role);
	
	public Rol save(Rol rol);
	
	public  void flush();
	
}

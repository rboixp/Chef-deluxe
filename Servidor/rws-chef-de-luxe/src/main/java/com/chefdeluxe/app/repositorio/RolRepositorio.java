package com.chefdeluxe.app.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chefdeluxe.app.entidades.Rol;

public interface RolRepositorio extends JpaRepository<Rol, Long>{

	 Optional<Rol> findByRole(String role);
	
	 Rol save(Rol rol);
	
	 // void flush();
	
}

package com.chefdeluxe.app.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chefdeluxe.app.entidades.Disponibilidad;
import com.chefdeluxe.app.entidades.Usuario;

public interface DisponibilidadRepositorio extends JpaRepository<Disponibilidad, Long>{
	
	public List<Disponibilidad> findByIdUser(Long id_user);
	
	public List<Disponibilidad> findByIdUser(Long id_user,Pageable pageable);
	
	public Page<Disponibilidad> findAll (Pageable pageable);

}

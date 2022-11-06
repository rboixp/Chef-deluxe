package com.chefdeluxe.app.repositorio;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.chefdeluxe.app.entidades.Disponibilidad;
import com.chefdeluxe.app.entidades.Usuario;

public interface DisponibilidadRepositorio extends JpaRepository<Disponibilidad, Long>{
	
	public List<Disponibilidad> findByIdUser(Long id_user);

}

package com.chefdeluxe.app.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chefdeluxe.app.entidades.Reserva;
import com.chefdeluxe.app.entidades.Rol;

public interface ReservaRepositorio extends JpaRepository<Reserva, Long>{

	List<Reserva> findByIdChef(Long id);

	List<Reserva> findByIdClient(Long id);
	
	List<Reserva> findByIdClient(Long idClient, Pageable pageable);
	
	List<Reserva> findByIdChef(Long idChef, Pageable pageable);
}


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

	Reserva save(Reserva reserva); //ok
	
	Optional<Reserva> findById(long id);
	
	List<Reserva> findAll();
	
	//void flush(long id, String estado); //ok
	
	void deleteById(long id);
	
	List<Reserva> findByIdChef(long id);

	List<Reserva> findByIdClient(long id);
	
	List<Reserva> findByIdClient(long idClient, Pageable pageable);
	
	List<Reserva> findByIdChef(long idChef, Pageable pageable);
}


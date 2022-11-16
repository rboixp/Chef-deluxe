package com.chefdeluxe.app.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chefdeluxe.app.entidades.Disponibilidad;
import com.chefdeluxe.app.entidades.Usuario;

public interface DisponibilidadRepositorio extends JpaRepository<Disponibilidad, Long>{
	
	 List<Disponibilidad> findByIdUser(long id_user);
	
	 List<Disponibilidad> findByIdUser(long id_user,Pageable pageable); 
	
	 Page<Disponibilidad> findAll (Pageable pageable);
	
	 Disponibilidad  save(Disponibilidad disponibilidad);
	
	 Optional<Disponibilidad> findById(long id) ;
	 
	 Optional<Disponibilidad> findByIdUserAndPoblacion(long id, String poblacion);
	
	 List<Disponibilidad> findAll();
	
	 void deleteById(long Id);
	
	// void flush(Disponibilidad disponibilidadUpd);

}

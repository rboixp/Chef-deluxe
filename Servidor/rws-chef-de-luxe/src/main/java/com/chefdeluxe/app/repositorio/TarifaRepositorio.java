package com.chefdeluxe.app.repositorio;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.chefdeluxe.app.entidades.Tarifa;


public interface TarifaRepositorio extends JpaRepository <Tarifa, Long> {
	
	Tarifa save(Tarifa tarifa);
	
	Optional<Tarifa>  findById(long id);
	
	Optional<Tarifa>  findByIdChef(long id);
	
	Page<Tarifa> findAll(Pageable pageable);
	
	List<Tarifa> findAll();
	
	void deleteById (long id);
	
	


}

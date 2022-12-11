package com.chefdeluxe.app.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chefdeluxe.app.entidades.Menu;
import com.chefdeluxe.app.entidades.Tarifa;

public interface Menurepositorio extends JpaRepository <Menu, Long> {
	
	Menu save(Menu menu);
	
	Optional<Menu> findById(long id);
	
	Optional<Menu>  findByIdChef(long id);
	
	Page<Menu> findAll(Pageable pageable);
	
	void deleteById (long id);

}

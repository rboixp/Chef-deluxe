package com.chefdeluxe.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.chefdeluxe.app.entidades.Menu;
import com.chefdeluxe.app.entidades.Tarifa;
import com.chefdeluxe.app.repositorio.Menurepositorio;
import com.chefdeluxe.app.repositorio.TarifaRepositorio;

@Service
public class MenuService {
	
	
	@Autowired
	private Menurepositorio menuRepositorio;
	
	public void save(Menu menu) {
		menuRepositorio.save(menu);
	}

	public Menu findById(Long Id) {		
		return  menuRepositorio.findById(Id).isPresent() ? 
				menuRepositorio.findById(Id).get() : null;
	}
	
	public Menu findByIdChef(Long IdChef) {
		return menuRepositorio.findByIdChef(IdChef).isPresent() ? 
				menuRepositorio.findByIdChef(IdChef).get() : null;
	}
	
	public void deleteById(Long id) {
		menuRepositorio.deleteById(id);
	}
	
	public List<Menu> findAll( int pageIndex, int pageSize ){
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
		return menuRepositorio.findAll(pageable).getContent();
		
	}
	
	public List<Menu> findAll(  ){
		return menuRepositorio.findAll();
		
	}

}

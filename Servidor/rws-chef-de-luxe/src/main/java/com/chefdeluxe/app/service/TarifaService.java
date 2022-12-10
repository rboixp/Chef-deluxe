package com.chefdeluxe.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.chefdeluxe.app.entidades.Tarifa;
import com.chefdeluxe.app.repositorio.TarifaRepositorio;

@Service
public class TarifaService {

	@Autowired
	private TarifaRepositorio tarifaRepositorio;
	
	public void save(Tarifa tarifa) {
		tarifaRepositorio.save(tarifa);
	}

	public Tarifa findById(Long Id) {		
		return  tarifaRepositorio.findById(Id).isPresent() ? 
				tarifaRepositorio.findById(Id).get() : null;
	}
	
	public Tarifa findByIdChef(Long IdChef) {
		return tarifaRepositorio.findByIdChef(IdChef).isPresent() ? 
				tarifaRepositorio.findByIdChef(IdChef).get() : null;
	}
	
	public void deleteById(Long id) {
		tarifaRepositorio.deleteById(id);
	}
	
	public List<Tarifa> findAll( int pageIndex, int pageSize ){
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
		return tarifaRepositorio.findAll(pageable).getContent();
		
	}
	
	public List<Tarifa> findAll(  ){
		return tarifaRepositorio.findAll();
		
	}

}

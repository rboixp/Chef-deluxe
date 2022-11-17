package com.chefdeluxe.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.chefdeluxe.app.entidades.Disponibilidad;
import com.chefdeluxe.app.entidades.Rol;
import com.chefdeluxe.app.entidades.Usuario;
import com.chefdeluxe.app.repositorio.DisponibilidadRepositorio;
import com.chefdeluxe.app.repositorio.UsuarioRepositorio;

@Service
public class DisponibilidadService {

	@Autowired
	private DisponibilidadRepositorio disponibilidadRepositorio;

	public void save(Disponibilidad disponibilidad) {
		disponibilidadRepositorio.save(disponibilidad);
	}

	public List<Disponibilidad> findByIdUser(Long userId, int pageIndex, int pageSize) {
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
		return disponibilidadRepositorio.findByIdUser(userId,pageable);
	}
	
	public List<Disponibilidad> findByIdUser(Long userId) {
		return disponibilidadRepositorio.findByIdUser(userId);
	}

	public Disponibilidad findById(Long id) {
		return disponibilidadRepositorio.findById(id).get();
	}
	
	public Disponibilidad findByIdUserAndPoblacion(Long id, String poblacion) {
		return disponibilidadRepositorio.findByIdUserAndPoblacion(id,poblacion).get();
	}	
	

	public List<Disponibilidad> findAll() {
		return disponibilidadRepositorio.findAll();
	}
	public List<Disponibilidad> findAll(int pageIndex, int pageSize) {
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
		return disponibilidadRepositorio.findAll(pageable).getContent();
	}
	public void deleteById(Long id) {
		disponibilidadRepositorio.deleteById(id);
	}

	public void flush(Disponibilidad disponibilidadUpd) {
		Disponibilidad disponibilidad = disponibilidadRepositorio.findById(disponibilidadUpd.getId()).get();
		disponibilidad.setEstado(disponibilidadUpd.getEstado());
		disponibilidadRepositorio.flush();
	}
	

	
	

}

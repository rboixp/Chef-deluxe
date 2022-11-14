package com.chefdeluxe.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.chefdeluxe.app.entidades.Reserva;
import com.chefdeluxe.app.repositorio.ReservaRepositorio;

@Service
public class ReservaService {

	@Autowired
	private ReservaRepositorio reservaRepositorio;

	public void save(Reserva reserva) {
		reservaRepositorio.save(reserva);
	}

	public Reserva findById(Long Id) {
		return reservaRepositorio.findById(Id).get();
	}

	public List<Reserva> findAll() {
		return reservaRepositorio.findAll();
	}

	public void flush(Long Id, String estado) {

		Reserva reserva = reservaRepositorio.findById(Id).get();
		reserva.setEstado(estado);
		reservaRepositorio.flush();
	}

	public void deleteById(Long id) {
		reservaRepositorio.deleteById(id);
	}
	
	public List<Reserva> findByIdChef(Long Id) {
		return reservaRepositorio.findByIdChef(Id);
	}
	
	public List<Reserva> findByIdClient(Long Id) {
		return reservaRepositorio.findByIdClient(Id);
	}
	
	public List<Reserva> findByIdClient(Long idClient, int pageIndex, int pageSize ){
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
		return reservaRepositorio.findByIdClient (idClient, pageable);
		
	}
	
	public List<Reserva> findByIdChef(Long idChef, int pageIndex, int pageSize ){
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
		return reservaRepositorio.findByIdChef (idChef, pageable);
		
	}	
	
}

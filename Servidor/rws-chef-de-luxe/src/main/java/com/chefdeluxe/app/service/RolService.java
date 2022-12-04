package com.chefdeluxe.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chefdeluxe.app.entidades.Disponibilidad;
import com.chefdeluxe.app.entidades.Rol;
import com.chefdeluxe.app.repositorio.RolRepositorio;

@Service
public class RolService {

	@Autowired
	private RolRepositorio rolRepositorio;

	public Rol save(Rol rol) {
		return rolRepositorio.save(rol);
	}

	public Rol findByRole(String rolName) {
		
		Optional roleOptional = rolRepositorio.findByRole(rolName);
		if (roleOptional.isEmpty()) {
			Rol rol = new Rol();
			rol.setRole(rolName);
			rolRepositorio.save(rol);
		}

		return rolRepositorio.findByRole(rolName).get();

	}
	
	public void deleteById(Long Id) {
		rolRepositorio.deleteById(Id);
	}
	
	public void flush(Rol rolInput) {
		Rol rol = rolRepositorio.findById(rolInput.getId()).get();
		rol.setRole(rol.getRole());
		rolRepositorio.flush();
	}

}

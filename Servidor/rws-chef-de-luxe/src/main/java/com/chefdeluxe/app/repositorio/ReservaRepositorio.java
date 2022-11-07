package com.chefdeluxe.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chefdeluxe.app.entidades.Reserva;
import com.chefdeluxe.app.entidades.Rol;

public interface ReservaRepositorio extends JpaRepository<Reserva, Long>{

}

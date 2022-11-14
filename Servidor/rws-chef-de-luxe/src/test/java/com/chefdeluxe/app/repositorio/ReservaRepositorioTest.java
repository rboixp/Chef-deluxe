package com.chefdeluxe.app.repositorio;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.chefdeluxe.app.entidades.Disponibilidad;
import com.chefdeluxe.app.entidades.Reserva;
import com.chefdeluxe.app.entidades.Usuario;
import com.chefdeluxe.app.repositorio.DisponibilidadRepositorio;
import com.chefdeluxe.app.repositorio.ReservaRepositorio;

@DataJpaTest
public class ReservaRepositorioTest {

	@Autowired
	private ReservaRepositorio reservaRepositorio;
	Reserva reserva = new Reserva();

	@Test
	public void testSaveDReserva() {
		Timestamp time = new Timestamp(2020, 11, 12, 0, 0, 0, 0);
		time.setDate(20220101);
		time.setTime(16);

		Reserva reserva = new Reserva();
		reserva.setEstado("Pendiente");
		reserva.setIdClient(1);
		reserva.setIdChef(1);
		reserva.setIncio(time);
		reserva.setFin(time);
		reserva.setPrecio(new BigDecimal("34.12"));
		reservaRepositorio.save(reserva);
		Assertions.assertTrue(reserva.getId() > 0);
	}

	@Test
	public void testListaIdRserva() {
		Timestamp time = new Timestamp(2020, 11, 12, 0, 0, 0, 0);
		time.setDate(20220101);
		time.setTime(16);

		Reserva reserva = new Reserva();
		reserva.setEstado("Pendiente");
		reserva.setIdClient(1);
		reserva.setIdChef(1);
		reserva.setIncio(time);
		reserva.setFin(time);
		reserva.setPrecio(new BigDecimal("34.12"));
		reservaRepositorio.save(reserva);
		Optional<Reserva> reservaOp = reservaRepositorio.findById(reserva.getId());
		Assertions.assertTrue(reservaOp.get().getId() > 0);
	}

	@Test
	public void testListaAllRserva() {
		Timestamp time = new Timestamp(2020, 11, 12, 0, 0, 0, 0);
		time.setDate(20220101);
		time.setTime(16);

		Reserva reserva = new Reserva();
		reserva.setEstado("Pendiente");
		reserva.setIdClient(1);
		reserva.setIdChef(1);
		reserva.setIncio(time);
		reserva.setFin(time);
		reserva.setPrecio(new BigDecimal("34.12"));
		reservaRepositorio.save(reserva);

		reserva.setEstado("Pendiente");
		reserva.setIdClient(2);
		reserva.setIdChef(2);
		reserva.setIncio(time);
		reserva.setFin(time);
		reserva.setPrecio(new BigDecimal("34.12"));
		reservaRepositorio.save(reserva);
		List<Reserva> reservas = reservaRepositorio.findAll();
		Assertions.assertTrue(!reservas.isEmpty());
	}

	@Test
	public void testUpdateRserva() {
		Timestamp time = new Timestamp(2020, 11, 12, 0, 0, 0, 0);
		time.setDate(20220101);
		time.setTime(16);

		Reserva reserva = new Reserva();
		reserva.setEstado("Pendiente");
		reserva.setIdClient(1);
		reserva.setIdChef(1);
		reserva.setIncio(time);
		reserva.setFin(time);
		reserva.setPrecio(new BigDecimal("34.12"));
		reservaRepositorio.save(reserva);
		reserva.setEstado("Confirmado");
		reservaRepositorio.flush();
		Optional<Reserva> reservaOp = reservaRepositorio.findById(reserva.getId());
		Assertions.assertTrue(reservaOp.get().getEstado().equals("Confirmado"));

	}

	@Test
	public void testDeleteRserva() {
		Timestamp time = new Timestamp(2020, 11, 12, 0, 0, 0, 0);
		time.setDate(20220101);
		time.setTime(16);

		Reserva reserva = new Reserva();
		reserva.setEstado("Pendiente");
		reserva.setIdClient(1);
		reserva.setIdChef(1);
		reserva.setIncio(time);
		reserva.setFin(time);
		reserva.setPrecio(new BigDecimal("34.12"));
		reservaRepositorio.save(reserva);
		reservaRepositorio.deleteById(reserva.getId());
	}

}

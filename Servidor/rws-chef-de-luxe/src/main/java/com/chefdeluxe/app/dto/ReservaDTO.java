package com.chefdeluxe.app.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ReservaDTO {
	
	private long id;
	private String estado;
	private String  cliente;	
	private String  chef;
	private Timestamp incio;
	private Timestamp fin;
	private BigDecimal precio;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getChef() {
		return chef;
	}
	public void setChef(String chef) {
		this.chef = chef;
	}
	public Timestamp getIncio() {
		return incio;
	}
	public void setIncio(Timestamp incio) {
		this.incio = incio;
	}
	public Timestamp getFin() {
		return fin;
	}
	public void setFin(Timestamp fin) {
		this.fin = fin;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	

}

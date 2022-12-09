package com.chefdeluxe.app.dto;

import java.math.BigDecimal;

public class TarifaDTO {
	
	private long id;
	private long  idchef;
	private BigDecimal precioHora;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdchef() {
		return idchef;
	}
	public void setIdchef(long idchef) {
		this.idchef = idchef;
	}
	public BigDecimal getPrecioHora() {
		return precioHora;
	}
	public void setPrecioHora(BigDecimal precioHora) {
		this.precioHora = precioHora;
	}
	
	
	
	

}

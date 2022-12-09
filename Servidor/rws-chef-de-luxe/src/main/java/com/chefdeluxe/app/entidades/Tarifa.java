package com.chefdeluxe.app.entidades;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tarifas", uniqueConstraints = { @UniqueConstraint(columnNames = { "idChef" }) })
public class Tarifa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private long idChef;
	
	@Column 
	private BigDecimal preciohora;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdChef() {
		return idChef;
	}

	public void setIdChef(long idChef) {
		this.idChef = idChef;
	}

	public BigDecimal getPreciohora() {
		return preciohora;
	}

	public void setPreciohora(BigDecimal preciohora) {
		this.preciohora = preciohora;
	}
	
	
}

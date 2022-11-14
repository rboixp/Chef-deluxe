package com.chefdeluxe.app.entidades;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.type.DateType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "reserva", uniqueConstraints = { @UniqueConstraint(columnNames = { "idClient" , "inicio" , "fin"}) })

public class Reserva {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 10)
	private String estado;
	
	@Column
	private long idClient;	
	
	@Column
	private long idChef;
	
	@Column 
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="Europe/Berlin")
	private Timestamp inicio;
	
	@Column 
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="Europe/Berlin")
	private Timestamp fin;
	
	@Column 
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

	public long getIdClient() {
		return idClient;
	}

	public void setIdClient(long idClient) {
		this.idClient = idClient;
	}

	public long getIdChef() {
		return idChef;
	}

	public void setIdChef(long idChef) {
		this.idChef = idChef;
	}

	public Timestamp getInicio() {
		return inicio;
	}

	public void setIncio(Timestamp incio) {
		this.inicio = incio;
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

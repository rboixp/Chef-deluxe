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
@Table(name = "menu", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Menu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private long idChef;
	
	@Column(length = 128)
	private String entrante;
	
	@Column(length = 128) 
	private String primero;
	
	@Column(length = 128) 
	private String segundo;
	
	@Column(length = 128) 
	private String postre;
	
	@Column(length = 128) 
	private String cafes;

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

	public String getEntrante() {
		return entrante;
	}

	public void setEntrante(String entrante) {
		this.entrante = entrante;
	}

	public String getPrimero() {
		return primero;
	}

	public void setPrimero(String primero) {
		this.primero = primero;
	}

	public String getSegundo() {
		return segundo;
	}

	public void setSegundo(String segundo) {
		this.segundo = segundo;
	}

	public String getPostre() {
		return postre;
	}

	public void setPostre(String postre) {
		this.postre = postre;
	}

	public String getCafes() {
		return cafes;
	}

	public void setCafes(String cafes) {
		this.cafes = cafes;
	}
	
	

}

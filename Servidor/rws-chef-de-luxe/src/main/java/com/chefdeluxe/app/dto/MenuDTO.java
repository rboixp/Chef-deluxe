package com.chefdeluxe.app.dto;

public class MenuDTO {
	
	private long id;
	private long idChef;
	private String entrante;
	private String primero;
	private String segundo;
	private String postre;
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

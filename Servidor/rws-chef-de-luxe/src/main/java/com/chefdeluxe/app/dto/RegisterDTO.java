package com.chefdeluxe.app.dto;

public class RegisterDTO {

	private String nombre;
	private String username;
	private String email;
	private String password;
	private String perfil;

	public String getNombre() {
		return nombre;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setRol(String perfil) {
		this.perfil = perfil;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}  

	public void setPassword(String password) {
		this.password = password;
	}

	public RegisterDTO() {
		super();
	}

}

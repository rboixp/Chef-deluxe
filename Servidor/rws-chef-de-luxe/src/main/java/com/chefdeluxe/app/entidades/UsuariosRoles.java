package com.chefdeluxe.app.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "usuarios_roles", uniqueConstraints = { @UniqueConstraint(columnNames = { "usuario_id" ,"rol_id"  })})
public class UsuariosRoles {
	@Id
	private Long usuario_id;  
	@Column
	private Long rol_id; 

}

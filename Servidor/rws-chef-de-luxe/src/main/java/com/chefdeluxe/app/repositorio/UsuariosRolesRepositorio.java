package com.chefdeluxe.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.chefdeluxe.app.entidades.UsuariosRoles;

public interface  UsuariosRolesRepositorio extends JpaRepository<UsuariosRoles, Long> {

	@Modifying
	@Transactional
	@Query("update UsuariosRoles u set u.rol_id = ?1  where u.usuario_id = ?2")
	void updateUserRole(Long rol_id , Long usuario_id);
	
	@Modifying
	@Transactional
	@Query("delete from  UsuariosRoles u where u.usuario_id = ?1")
	void deleteUserRole( Long usuario_id);
}

package com.chefdeluxe.app.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.chefdeluxe.app.entidades.Rol;
import com.chefdeluxe.app.entidades.Usuario;
import com.chefdeluxe.app.service.RolService;
import com.chefdeluxe.app.service.UsuarioService;

@Service
public class Utils {
	
	@Autowired
	private UsuarioService usuarioService;	
	
	@Autowired
	private RolService rolService;

	
	public Boolean usuarioEsDelRol(String RolString , String usernameOrEmail) {
		Usuario usuario = usuarioService.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail);
		Rol rol = rolService.findByRole(RolString);
		return usuario.getRoles().contains(rol);
	}
	
	public Boolean usuarioEsDelRol(String RolString , Authentication token) {
		Usuario usuario = usuarioService.findByUsernameOrEmail(token.getName(),token.getName());
		Rol rol = rolService.findByRole(RolString);
		return usuario.getRoles().contains(rol);
	}
	
	public Boolean usuarioAutorizado(String usernameOrEmail , Authentication token) {
		System.out.println("token.getName() " +token.getName());
		Usuario usuarioJWT = usuarioService.findByUsernameOrEmail(token.getName(),token.getName());
		Rol rol = rolService.findByRole("ROLE_ADMIN");
		System.out.println("usernameOrEmail " +usernameOrEmail);
		Usuario usuario = usuarioService.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail);
		return usuarioJWT.getRoles().contains(rol) || usuario.getUsername().equals(usuarioJWT.getUsername());
	}

}

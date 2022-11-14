package com.chefdeluxe.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chefdeluxe.app.entidades.Rol;
import com.chefdeluxe.app.entidades.Usuario;
import com.chefdeluxe.app.repositorio.RolRepositorio;
import com.chefdeluxe.app.repositorio.UsuarioRepositorio;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	public Usuario save(Usuario usuario) {
		usuarioRepositorio.save(usuario);
		return usuario;
	}

	public Usuario findByEmail(String email) {
		return usuarioRepositorio.findByEmail(email).get();

	}

	public Usuario findByUsernameOrEmail(String UsernameOrEmail, String Username) {
		return usuarioRepositorio.findByUsernameOrEmail(UsernameOrEmail, Username).get();
	}

	public Usuario findByUsername(String Username) {

		return usuarioRepositorio.findByUsername(Username).get();
	}
	
	public Usuario findById(Long id) {

		return usuarioRepositorio.findById(id).get();
	}
	
	public List<Usuario> findAll() {

		return usuarioRepositorio.findAll();
	}

	public void deleteByUsername(String username) {
		usuarioRepositorio.deleteByUsername(username);
	}
	
	public boolean existsByUsername (String username) {
	 return usuarioRepositorio.existsByUsername(username);	
	}
	
	public boolean existsByEmail (String email) {
		 return usuarioRepositorio.existsByEmail(email);	
		}
	
	public void deleteById (long id) {
		usuarioRepositorio.deleteById(id);
		
	}
	
	public void flush (Usuario usuarioUpd) {
		Usuario usuario = usuarioRepositorio.findById(usuarioUpd.getId()).get();		
		usuario.setRoles(usuarioUpd.getRoles());
		usuario.setNombre(usuarioUpd.getNombre());
		usuario.setUsername(usuarioUpd.getUsername());
		usuario.setEmail(usuarioUpd.getEmail());
		usuario.setPassword(usuarioUpd.getPassword());	
		usuario.setApellidos(usuarioUpd.getApellidos());
		usuario.setDireccion(usuarioUpd.getDireccion());
		usuario.setCodigoPostal(usuarioUpd.getCodigoPostal());
		usuario.setPoblacion(usuarioUpd.getPoblacion());
		usuario.setNacionalidad(usuarioUpd.getNacionalidad());
		usuario.setEdad(usuarioUpd.getEdad());
		usuario.setTelefono(usuarioUpd.getTelefono());
		usuario.setIban(usuarioUpd.getIban());		
		usuarioRepositorio.flush();
		
	}


}

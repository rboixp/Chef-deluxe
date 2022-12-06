package com.chefdeluxe.app.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chefdeluxe.app.entidades.Usuario;
import com.chefdeluxe.app.dto.LoginDTO;
import com.chefdeluxe.app.dto.RegisterDTO;
import com.chefdeluxe.app.entidades.Rol;
import com.chefdeluxe.app.service.RolService;
import com.chefdeluxe.app.service.UsuarioService;
import com.chefdeluxe.app.seguridad.JWTAuthResonseDTO;
import com.chefdeluxe.app.seguridad.JwtTokenProvider;

/**
 * Clase AuthController
 *
 * Controlador amb alta de usuaris i inici de sessió.
 */
@RestController
@RequestMapping("api/auth")
public class AuthController {
 
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private RolService rolService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	/**
	 * End point authenticateUser
	 *
	 * Inici de sessió retorna un token
	 */

	@PostMapping("/iniciarSesion")
	public ResponseEntity<JWTAuthResonseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.generarToken(authentication);
		return ResponseEntity.ok(new JWTAuthResonseDTO(token));
	}

	/**
	 * End point registrarUsuario
	 *
	 * Registra un usuari a la base de dades.
	 */
	@PostMapping("/registrar")
	public ResponseEntity<?> registrarUsuario(@RequestBody RegisterDTO registroDTO) {

		if (registroDTO.getPassword() == null || registroDTO.getUsername() == null || registroDTO.getEmail() == null) {
			return new ResponseEntity<>("Usuario, mail y password obligatorios", HttpStatus.BAD_REQUEST);
		}

		if (usuarioService.existsByUsername(registroDTO.getUsername())) {
			return new ResponseEntity<>("Ese nombre de usuario ya existe", HttpStatus.BAD_REQUEST);
		}

		if (usuarioService.existsByEmail(registroDTO.getEmail())) {
			return new ResponseEntity<>("Ese email de usuario ya existe", HttpStatus.BAD_REQUEST);
		}

		Usuario usuario = new Usuario();
		usuario.setNombre(registroDTO.getNombre());
		usuario.setUsername(registroDTO.getUsername());
		usuario.setEmail(registroDTO.getEmail());
		usuario.setApellidos(registroDTO.getApellidos());
		usuario.setDireccion(registroDTO.getDireccion());
		usuario.setCodigoPostal(registroDTO.getCodigoPostal());
		usuario.setPoblacion(registroDTO.getPoblacion());
		usuario.setNacionalidad(registroDTO.getNacionalidad());
		usuario.setEdad(registroDTO.getEdad());
		usuario.setTelefono(registroDTO.getTelefono());
		usuario.setIban(registroDTO.getIban());
		usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
		Rol roles = rolService.findByRole(registroDTO.getPerfil());
		usuario.setRoles(Collections.singleton(roles));
		usuarioService.save(usuario);
		return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.OK);
	}
}

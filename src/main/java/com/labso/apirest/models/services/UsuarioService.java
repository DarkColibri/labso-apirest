package com.labso.apirest.models.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.labso.apirest.models.dao.IUsuarioDao;
import com.labso.apirest.models.entity.Usuario;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService{
	
	private Logger logger = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private IUsuarioDao usuarioDao;
	// CLASE UserDetailsService
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// BUSCA USUARIO
		Usuario usuario = usuarioDao.findByUsername(username);
		
		if(usuario == null) {
			logger.error("Error en el login: no existe el usuario '"+username+"' en el sistema!");
			throw new UsernameNotFoundException("Error en el login: no existe el usuario '"+username+"' en el sistema!");
		}
		
		// AUTHORITIES : Se obtienen de los roles.
		// OBTENEMOS Y CONVERTIMOS
		// PASAMOS DE Usuarios A GrantedAuthority
		List<GrantedAuthority> authorities = usuario.getRoles()
				// OBTIENE ROLES (Stream<Role>)
				.stream()
				// role viene del stream
				// Return Stream<SimpleGrantedAuthority>
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				// .map(role -> { 
				//	return new SimpleGrantedAuthority(role.getNombre());
				// })
				// Por cada elemento que estamos recorriendo anteriormente
				.peek(authority -> logger.info("Role: " + authority.getAuthority()))
				// Stream<SimpleGrantedAuthority> >> List<GrantedAuthority>
				.collect(Collectors.toList());
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario findByUsername(String username) {
		return usuarioDao.findByUsername(username);
	}

}

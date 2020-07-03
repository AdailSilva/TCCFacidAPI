package com.adailsilva.tcc.facid.api.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.adailsilva.tcc.facid.api.model.Usuario;
import com.adailsilva.tcc.facid.api.repository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	// public UserDetails loadUserByUsername(String username) throws
	// UsernameNotFoundException {
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		// Validará usuário pelo "email".
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
		// usuarioOptional.orElseThrow(exceptionSupplier);
		// Expressão Lambda:
		Usuario usuario = usuarioOptional
				.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou Senha incorretos."));
		// return new User(username, password, enabled, accountNonExpired,
		// credentialsNonExpired, accountNonLocked,
		// authorities);
		return new User(email, usuario.getSenha(), getPermissoes(usuario));
	}
	
	// Lista de Authorities:
	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		// Carregando permissões dos Usuários:
		usuario.getPermissoes()
				.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));
		return authorities;
	}
	
}

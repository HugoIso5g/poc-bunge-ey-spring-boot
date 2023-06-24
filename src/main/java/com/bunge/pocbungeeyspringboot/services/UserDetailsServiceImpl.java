package com.bunge.pocbungeeyspringboot.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bunge.pocbungeeyspringboot.domain.Usuario;
import com.bunge.pocbungeeyspringboot.repository.UsuarioRepository;
import com.bunge.pocbungeeyspringboot.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> user = repository.findByEmail(email);
		
		if(user.isPresent())
		{
			return new UserSS(user.get().getId(),
							  user.get().getEmail(),
							  user.get().getPassword());
		}
		
		throw new UsernameNotFoundException(email);
	}

}

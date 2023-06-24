package com.bunge.pocbungeeyspringboot.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bunge.pocbungeeyspringboot.domain.Usuario;
import com.bunge.pocbungeeyspringboot.repository.UsuarioRepository;

@Service
public class StarterService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public void staterApplication() {
		
		Usuario user1 = new Usuario();
				user1.setEmail("rfpsaasfin@gmail.com");
				user1.setEnabled(true);
				user1.setUsername("poc");
				user1.setPassword(encoder.encode("pocEy#123"));
				
	    Usuario user2 = new Usuario();
				user2.setEmail("-rfpsaasfin@gmail.com");
				user2.setEnabled(true);
				user2.setUsername("poc");
				user2.setPassword(encoder.encode("pocEy#123"));
				
		Usuario user3 = new Usuario();
				user3.setEmail("fkremer@gmail.com");
				user3.setEnabled(true);
				user3.setUsername("Frederico Wanis");
				user3.setPassword(encoder.encode("pocEy#123"));
		
		repository.saveAll(Arrays.asList(user1,user2,user3));
	}
}

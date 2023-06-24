package com.bunge.pocbungeeyspringboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bunge.pocbungeeyspringboot.domain.LogUsuario;
import com.bunge.pocbungeeyspringboot.domain.dto.LogUsuarioDTO;
import com.bunge.pocbungeeyspringboot.repository.LogUsuarioRepository;

@Service
public class LogUsuarioService {

	@Autowired
	private LogUsuarioRepository repository;
	
	public LogUsuario create(LogUsuarioDTO dto) {
		dto.setId(null);
		LogUsuario log = new LogUsuario(dto);
		return repository.save(log);
	}
	
	public List<LogUsuario> findAll(){
		return repository.findAll();
	}
}

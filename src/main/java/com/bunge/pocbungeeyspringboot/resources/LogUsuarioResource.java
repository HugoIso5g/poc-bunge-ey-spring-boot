package com.bunge.pocbungeeyspringboot.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunge.pocbungeeyspringboot.domain.LogUsuario;
import com.bunge.pocbungeeyspringboot.services.LogUsuarioService;

@RestController
@RequestMapping(value="/log-usuarios")
public class LogUsuarioResource {
	
	@Autowired
	private LogUsuarioService service;
	
	@GetMapping
	public ResponseEntity<List<LogUsuario>> findAll(){
		List<LogUsuario> logs = service.findAll();
		return ResponseEntity.ok(logs);
	}

}

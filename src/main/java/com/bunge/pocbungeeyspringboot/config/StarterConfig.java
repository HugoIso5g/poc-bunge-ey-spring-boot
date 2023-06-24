package com.bunge.pocbungeeyspringboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.bunge.pocbungeeyspringboot.services.StarterService;

@Configuration
@Profile("starter")
public class StarterConfig {

	@Autowired
	private StarterService service;
	
	@Bean
	public boolean beginApplication() {
		this.service.staterApplication();
		System.out.println("Entrei aqui e gravei!");
		return false;
	}
	
}

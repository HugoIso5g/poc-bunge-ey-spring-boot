package com.bunge.pocbungeeyspringboot.domain.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.bunge.pocbungeeyspringboot.domain.Usuario;

public class LogUsuarioDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected Usuario usuario;
	
	protected String fileName;
	
	protected LocalDate data;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}
	
}

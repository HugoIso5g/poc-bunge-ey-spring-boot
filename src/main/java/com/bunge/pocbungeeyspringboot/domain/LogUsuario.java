package com.bunge.pocbungeeyspringboot.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.bunge.pocbungeeyspringboot.domain.dto.LogUsuarioDTO;

@Entity
@Table(name="log_usuarios")
public class LogUsuario implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	private String fileName;
	
	private LocalDate data = LocalDate.now();
	
	public LogUsuario(LogUsuarioDTO dto) {
		super();
		this.id = dto.getId();
		this.usuario = dto.getUsuario();
		this.fileName = dto.getFileName();
		this.data = dto.getData();
	}

	public LogUsuario() {
		super();
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(data, fileName, id, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogUsuario other = (LogUsuario) obj;
		return Objects.equals(data, other.data) && Objects.equals(fileName, other.fileName)
				&& Objects.equals(id, other.id) && Objects.equals(usuario, other.usuario);
	}

}

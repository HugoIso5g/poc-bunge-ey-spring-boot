package com.bunge.pocbungeeyspringboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunge.pocbungeeyspringboot.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{

	Usuario findByEmail(String email);
}

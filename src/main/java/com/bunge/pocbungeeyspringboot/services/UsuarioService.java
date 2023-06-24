package com.bunge.pocbungeeyspringboot.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bunge.pocbungeeyspringboot.domain.Usuario;
import com.bunge.pocbungeeyspringboot.domain.dto.UsuarioDTO;
import com.bunge.pocbungeeyspringboot.repository.UsuarioRepository;
import com.bunge.pocbungeeyspringboot.services.exceptions.DataIntegrityViolationException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepo;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Usuario findById(Integer id) {
		Optional<Usuario> obj = usuarioRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id : " + id, null));
	}
	
	public List<Usuario> findAll(){
		return usuarioRepo.findAll();
	}
	
	public Usuario create(UsuarioDTO dto) {
		dto.setId(null);
		dto.setPassword(encoder.encode(dto.getPassword()));
		Usuario newUser = new Usuario(dto);
		return usuarioRepo.save(newUser);
	}
	
	public Usuario update(Integer id, @Valid UsuarioDTO dto) {
		dto.setId(id);
		Usuario oldUser = findById(id);
		
		if(!dto.getPassword().equals(oldUser.getPassword())) {
			dto.setPassword(encoder.encode(dto.getPassword()));
		}
		
		oldUser = new Usuario(dto);
		return usuarioRepo.save(oldUser);
	}
	
	public void delete(Integer id) {
		@SuppressWarnings("unused")
		Usuario user = findById(id);
		usuarioRepo.deleteById(id);
	}
	
	@SuppressWarnings("unused")
	private void ValidaPorEmail(UsuarioDTO dto) {
		Optional<Usuario> usuario = usuarioRepo.findByEmail(dto.getEmail());
		
		if(usuario.isPresent() && usuario.get().getId() != dto.getId())
		{
			throw new DataIntegrityViolationException("E-mail já cadastrado!");
		}
	}
}

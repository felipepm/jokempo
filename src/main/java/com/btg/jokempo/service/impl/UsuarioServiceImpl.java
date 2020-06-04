package com.btg.jokempo.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.btg.jokempo.dto.UsuarioDto;
import com.btg.jokempo.exception.NegocioException;
import com.btg.jokempo.service.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
	
	private List<UsuarioDto> usuarios = null;

	@Override
	public List<UsuarioDto> listar(UsuarioDto usuarioDto) {
		if (usuarioDto == null) {
			return getUsuarios();
		} else if (usuarioDto.getNome() != null && !usuarioDto.getNome().trim().isEmpty() && usuarioExistentePorNome(usuarioDto)){
			return getUsuarios().stream()
					.filter(empl -> getUsuarios().stream()
					        .anyMatch(usu -> 
					        	usu.getNome().equals(usuarioDto.getNome())))
					.collect(Collectors.toList());
		} else {
			return getUsuarios().stream()
					.filter(empl -> getUsuarios().stream()
					        .anyMatch(usu -> 
					        	usu.getId() == usuarioDto.getId()))
					.collect(Collectors.toList());
		}
	}

	@Override
	public UsuarioDto cadastrar(UsuarioDto usuarioDto) throws NegocioException {
		if(usuarioExistentePorNome(usuarioDto)) {
			throw new NegocioException("Usuário já existente");
		} else {
			UsuarioDto novoUsuarioDto = null;
			if (getUsuarios().isEmpty()) {
				novoUsuarioDto = new UsuarioDto(1, usuarioDto.getNome());
			} else {
				Optional<UsuarioDto> maiorUsuarioDto = getUsuarios().stream()
															.max(Comparator.comparing(UsuarioDto::getId));
				novoUsuarioDto = new UsuarioDto(maiorUsuarioDto.get().getId() + 1, usuarioDto.getNome());
			}
			getUsuarios().add(novoUsuarioDto);
			return novoUsuarioDto;
		}
	}
	
	@Override
	public void excluir(UsuarioDto usuarioDto) throws NegocioException {
		if(usuarioExistentePorId(usuarioDto)) {
			getUsuarios().removeIf(e -> e.getId() == usuarioDto.getId());
		} else {
			throw new NegocioException("Usuário não existe");
		}
	}

	private boolean usuarioExistentePorNome(UsuarioDto usuarioDto) {
		boolean usuarioExistente = false;
		
		usuarioExistente = getUsuarios().stream()
	            				.anyMatch(t -> t.getNome().equals(usuarioDto.getNome()));
		
		return usuarioExistente;
	}
	
	private boolean usuarioExistentePorId(UsuarioDto usuarioDto) {
		boolean usuarioExistente = false;
		
		usuarioExistente = getUsuarios().stream()
	            				.anyMatch(t -> t.getId() == usuarioDto.getId());
		
		return usuarioExistente;
	}

	

	private List<UsuarioDto> getUsuarios() {
		if (usuarios == null) {
			usuarios = new ArrayList<UsuarioDto>();
		}
		return usuarios;
	}
	

}

package com.btg.jokempo.service;

import java.util.List;

import com.btg.jokempo.dto.UsuarioDto;
import com.btg.jokempo.exception.NegocioException;

public interface IUsuarioService {

	List<UsuarioDto> listar(UsuarioDto usuarioDto);

	UsuarioDto cadastrar(UsuarioDto usuarioDto) throws NegocioException;
	
	void excluir(UsuarioDto usuarioDto) throws NegocioException;

}

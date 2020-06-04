package com.btg.jokempo.service;

import java.util.List;

import com.btg.jokempo.dto.JogadaDto;
import com.btg.jokempo.dto.RodadaDto;
import com.btg.jokempo.exception.NegocioException;

public interface IJogoService {

	RodadaDto jogar(JogadaDto jogadaDto) throws NegocioException;
	
	void finalizar() throws NegocioException;
	
	void excluir(RodadaDto rodadaDto) throws NegocioException;
	
	List<RodadaDto> listar(RodadaDto rodadaDto);
}

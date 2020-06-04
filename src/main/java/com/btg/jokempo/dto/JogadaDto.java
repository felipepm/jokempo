package com.btg.jokempo.dto;

import com.btg.jokempo.enumeration.TipoJogadaEnum;

public class JogadaDto {
	
	private UsuarioDto usuario;
	private TipoJogadaEnum tipojogada;
	

	public JogadaDto(UsuarioDto usuario, TipoJogadaEnum tipojogada) {
		this.usuario = usuario;
		this.tipojogada = tipojogada;
	}
	
	public UsuarioDto getUsuario() {
		return usuario;
	}

	public TipoJogadaEnum getTipojogada() {
		return tipojogada;
	}

}

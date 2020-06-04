package com.btg.jokempo.dto;

import java.util.List;

public class RodadaDto {

	private long id;
	private String nome;
	private List<JogadaDto> jogadasVencedoras;
	
	
	public RodadaDto(long id, String nome, List<JogadaDto> jogadasVencedoras) {
		super();
		this.id = id;
		this.nome = nome;
		this.jogadasVencedoras = jogadasVencedoras;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RodadaDto other = (RodadaDto) obj;
		if (id != other.id)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}




	public long getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public List<JogadaDto> getJogadasVencedoras() {
		return jogadasVencedoras;
	}

}

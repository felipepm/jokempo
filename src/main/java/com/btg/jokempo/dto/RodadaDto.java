package com.btg.jokempo.dto;

import java.util.ArrayList;
import java.util.List;

public class RodadaDto {

	private long id;
	private String nome;
	private boolean ativa = true;
	private List<JogadaDto> jogadas;
	
	
	public RodadaDto(long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public void adicionarJogadas(JogadaDto jogadorDto) {
		getJogadas().add(jogadorDto);
	}
	
	public void finalizar() {
		ativa = false;
	}
	
	public List<JogadaDto> definirCampeao() {
		List<JogadaDto> jogadasCampeas = null;
//		JogadaDto jogadaCampea = null;
//		
//		getJogadas().forEach(jog->{
//			if (jogadaCampea == null) {
//				jogadaCampea = jog;
//			}
//		});
		
		for (JogadaDto jogadaDto : getJogadas()) {
			if (jogadasCampeas == null) {
				jogadasCampeas = new ArrayList<JogadaDto>();
				jogadasCampeas.add(jogadaDto);
			}
		}

		return jogadasCampeas;
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
	
	public boolean getAtiva() {
		return ativa;
	}
	
	public List<JogadaDto> getJogadas() {
		if (jogadas == null) {
			jogadas = new ArrayList<JogadaDto>();
		}
		return jogadas;
	}

}

package com.btg.jokempo.service.impl;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.btg.jokempo.dto.JogadaDto;
import com.btg.jokempo.dto.RodadaDto;
import com.btg.jokempo.dto.UsuarioDto;
import com.btg.jokempo.enumeration.TipoJogadaEnum;
import com.btg.jokempo.exception.NegocioException;
import com.btg.jokempo.service.IJogoService;
import com.btg.jokempo.service.IUsuarioService;

@Service
public class JogoServiceImpl implements IJogoService {
	
	private LinkedList<RodadaDto> rodadas = null;
	
	private IUsuarioService usuarioService;

	public JogoServiceImpl(IUsuarioService usuarioService) {
		super();
		this.usuarioService = usuarioService;
	}

	@Override
	public RodadaDto jogar(JogadaDto jogadaDto) throws NegocioException {
		UsuarioDto usuarioValidado = obterUsuario(jogadaDto.getUsuario());
		
		if (getRodadas().isEmpty()) {
			RodadaDto rodadaDto = new RodadaDto(1, "Rodada 1");
			rodadaDto.adicionarJogadas(new JogadaDto(usuarioValidado, jogadaDto.getTipojogada()));
			getRodadas().add(rodadaDto);
		} else if (!getRodadas().getLast().getAtiva()) {
			Optional<RodadaDto> maiorRodadaDto = getRodadas().stream()
					.max(Comparator.comparing(RodadaDto::getId));
			
			RodadaDto rodadaDto = new RodadaDto(maiorRodadaDto.get().getId() + 1, "Rodada " + maiorRodadaDto.get().getId() + 1);
			rodadaDto.adicionarJogadas(new JogadaDto(usuarioValidado, jogadaDto.getTipojogada()));
			getRodadas().add(rodadaDto);
		} else {
			if (jogadaJaExiste(jogadaDto.getTipojogada())) {
				throw new NegocioException("Jogada existente para esse usuário");
			} else {
				getRodadas().getLast().adicionarJogadas(new JogadaDto(usuarioValidado, jogadaDto.getTipojogada()));
			}
		}
	
		return getRodadas().getLast();
	}
	
	private boolean jogadaJaExiste(TipoJogadaEnum tipojogada) {
		boolean jogadaNaoExiste = true;
		
		jogadaNaoExiste = getRodadas().getLast().getJogadas().stream().anyMatch(jog -> jog.getTipojogada() == tipojogada);
		
		return jogadaNaoExiste;
	}

	private UsuarioDto obterUsuario(UsuarioDto usuarioDto) throws NegocioException {		
		List<UsuarioDto> listaUsuarios = usuarioService.listar(usuarioDto);
		
		if (listaUsuarios.isEmpty()) {
			throw new NegocioException("Usuário não existe");
		} else if (listaUsuarios.size() > 1) {
			throw new NegocioException("Multiplos usuários para esse id");
		} else {
			return listaUsuarios.get(0);
		}
	}

	@Override
	public List<JogadaDto> finalizar() throws NegocioException {
		if(existeRodadaAberta()) {
			List<JogadaDto> jogadasCampeas = getRodadas().getLast().definirCampeao();
			return jogadasCampeas;
		} else {
			throw new NegocioException("Usuário não existe");
		}		
	}

	private boolean existeRodadaAberta() {
		boolean existeRodada = false;
		
		if (!getRodadas().isEmpty() && getRodadas().getLast().getAtiva()) {
			existeRodada = true;
		}
		
		return existeRodada;
	}

	private boolean existeRodada(RodadaDto rodadaDto) {
		boolean existeRodada = false;
		
		existeRodada = getRodadas().stream()
				.anyMatch(rod -> rod.getId() == rodadaDto.getId());
		
		return existeRodada;
	}

//	private boolean existeRodadaAberta() {
//		boolean existeRodadaAberta = false;
//		return existeRodadaAberta;
//	}

	@Override
	public void excluir(RodadaDto rodadaDto) throws NegocioException {
		if (existeRodada(rodadaDto)) {
			getRodadas().removeIf(rod -> rod.getId() == rodadaDto.getId());
		} else {
			throw new NegocioException("Usuário não existe");
		}
	}

	@Override
	public List<RodadaDto> listar(RodadaDto rodadaDto) {
		if (rodadaDto == null) {
			return getRodadas();
		} else if (rodadaDto.getNome() != null && !rodadaDto.getNome().trim().isEmpty()){
			return getRodadas().stream()
					.filter(empl -> getRodadas().stream()
					        .anyMatch(usu -> 
					        	usu.getNome().equals(rodadaDto.getNome())))
					.collect(Collectors.toList());
		} else {
			return getRodadas().stream()
					.filter(empl -> getRodadas().stream()
					        .anyMatch(usu -> 
					        	usu.getId() == rodadaDto.getId()))
					.collect(Collectors.toList());
		}
	}
	
	private LinkedList<RodadaDto> getRodadas() {
		if (rodadas == null) {
			rodadas = new LinkedList<RodadaDto>();
		}
		return rodadas;
	}

}

package com.btg.jokempo.service.impl;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
	public RodadaDto jogar(List<JogadaDto> listaJogadasDto) throws NegocioException {
		
		validarJogadas(listaJogadasDto);
		
		Map<TipoJogadaEnum, List<JogadaDto>> listaJogadasAgrupada = listaJogadasDto.stream()
				  .collect(Collectors.groupingBy(JogadaDto::getTipojogada));
		
		if (listaJogadasAgrupada.keySet().size() == 5) {
			throw new NegocioException("Não é possível calcular vencedor");
		} else if (listaJogadasAgrupada.keySet().size() == 1) {
			throw new NegocioException("Não há vencedor");
		} else {
			TipoJogadaEnum tipoJogadaVencedora = null;
			
			for (TipoJogadaEnum jogadaDto : listaJogadasAgrupada.keySet()) {
				switch (jogadaDto) {
					case PEDRA:
						boolean pedraVencedora = listaJogadasDto.stream()
							.noneMatch(tipo -> (tipo.getTipojogada() == TipoJogadaEnum.PAPEL || tipo.getTipojogada() == TipoJogadaEnum.SPOOK));
						
						if (pedraVencedora) {
							tipoJogadaVencedora = TipoJogadaEnum.PEDRA;
						}
					break;
					case PAPEL:
						boolean papelVencedora = listaJogadasDto.stream()
							.noneMatch(tipo -> (tipo.getTipojogada() == TipoJogadaEnum.TESOURA || tipo.getTipojogada() == TipoJogadaEnum.LAGARTO));
					
						if (papelVencedora) {
							tipoJogadaVencedora = TipoJogadaEnum.PAPEL;
						}
					break;
					case TESOURA:
						boolean tesouraVencedora = listaJogadasDto.stream()
							.noneMatch(tipo -> (tipo.getTipojogada() == TipoJogadaEnum.SPOOK || tipo.getTipojogada() == TipoJogadaEnum.PEDRA));
					
						if (tesouraVencedora) {
							tipoJogadaVencedora = TipoJogadaEnum.TESOURA;
						}
					break;
					case SPOOK:
						boolean spookVencedor = listaJogadasDto.stream()
							.noneMatch(tipo -> (tipo.getTipojogada() == TipoJogadaEnum.LAGARTO || tipo.getTipojogada() == TipoJogadaEnum.PAPEL));
					
						if (spookVencedor) {
							tipoJogadaVencedora = TipoJogadaEnum.SPOOK;
						}
					break;
					case LAGARTO:
						boolean lagartoVencedor = listaJogadasDto.stream()
							.noneMatch(tipo -> (tipo.getTipojogada() == TipoJogadaEnum.PEDRA || tipo.getTipojogada() == TipoJogadaEnum.TESOURA));
					
						if (lagartoVencedor) {
							tipoJogadaVencedora = TipoJogadaEnum.LAGARTO;
						}
					break;
					
					default:
					break;
				}
			}
			
			return gravarRodada(listaJogadasAgrupada.get(tipoJogadaVencedora));
		}
	}
	
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
	
	private void validarJogadas(List<JogadaDto> jogadasDto) throws NegocioException {
		Map<UsuarioDto, List<JogadaDto>> listaJogadasAgrupadaUsuario = jogadasDto.stream()
				 .collect(Collectors.groupingBy(JogadaDto::getUsuario));
		
		for (UsuarioDto usuarioDto : listaJogadasAgrupadaUsuario.keySet()) {
			List<JogadaDto> listaJogadasUsuario = listaJogadasAgrupadaUsuario.get(usuarioDto);
			
			if (listaJogadasUsuario.size() > 1) {
				throw new NegocioException("Jogada existente para esse usuário");
			}
		}

		for (JogadaDto jogadaDto : jogadasDto) {
			List<UsuarioDto> listaUsuarios = usuarioService.listar(jogadaDto.getUsuario());
			
			if (listaUsuarios.isEmpty()) {
				throw new NegocioException("Usuário não existe");
			} else if (listaUsuarios.size() > 1) {
				throw new NegocioException("Multiplos usuários para esse id");
			}
		}
	}

	private RodadaDto gravarRodada(List<JogadaDto> jogadasVencedoras) {
		RodadaDto rodadaDto = null;
		
		if (getRodadas().isEmpty()) {
			rodadaDto = new RodadaDto(1, "Rodada 1", jogadasVencedoras);
		} else {
			Optional<RodadaDto> maiorRodadaDto = getRodadas().stream()
					.max(Comparator.comparing(RodadaDto::getId));
			
			rodadaDto = new RodadaDto(maiorRodadaDto.get().getId() + 1, "Rodada " + maiorRodadaDto.get().getId() + 1, jogadasVencedoras);
			
		}
		getRodadas().add(rodadaDto);
		return rodadaDto;
	}

	private boolean existeRodada(RodadaDto rodadaDto) {
		boolean existeRodada = false;
		
		existeRodada = getRodadas().stream()
				.anyMatch(rod -> rod.getId() == rodadaDto.getId());
		
		return existeRodada;
	}

	private LinkedList<RodadaDto> getRodadas() {
		if (rodadas == null) {
			rodadas = new LinkedList<RodadaDto>();
		}
		return rodadas;
	}

}

package com.btg.jokempo.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.btg.jokempo.dto.JogadaDto;
import com.btg.jokempo.dto.RodadaDto;
import com.btg.jokempo.dto.UsuarioDto;
import com.btg.jokempo.enumeration.TipoJogadaEnum;
import com.btg.jokempo.exception.NegocioException;
import com.btg.jokempo.service.impl.JogoServiceImpl;
import com.btg.jokempo.service.impl.UsuarioServiceImpl;

public class JogoServiceTest {

	private IUsuarioService usuarioService;
	private IJogoService jogoService;
	
	@BeforeEach
	private void iniciar() {
		usuarioService = mock(UsuarioServiceImpl.class);
		jogoService = new JogoServiceImpl(usuarioService);
	}

	@Test
	public void deveIncluirUmaJogada() throws NegocioException {
		UsuarioDto usuarioDto = new UsuarioDto(1, "Felipe");

		when(usuarioService.listar(eq(usuarioDto))).thenReturn(Arrays.asList(usuarioDto));
		
		jogoService.jogar(new JogadaDto(usuarioDto, TipoJogadaEnum.PAPEL));
		
		List<RodadaDto> listaRodadas = jogoService.listar(new RodadaDto(1, ""));
		
		Assertions.assertEquals(1, listaRodadas.size());
		Assertions.assertEquals(TipoJogadaEnum.PAPEL, listaRodadas.get(0).getJogadas().get(0).getTipojogada());
		Assertions.assertEquals(1, listaRodadas.get(0).getJogadas().get(0).getUsuario().getId());
		Assertions.assertEquals("Felipe", listaRodadas.get(0).getJogadas().get(0).getUsuario().getNome());
	}
	
	@Test
	public void naoPermiteJogadaDeJogadorNaoCadastrado() {
		Assertions.assertThrows(NegocioException.class, () -> {
			jogoService.jogar(new JogadaDto(new UsuarioDto(1, "Felipe"), TipoJogadaEnum.PAPEL));
		});
	}
	
	@Test
	public void naoPermiteJogadaRepetidaDoMesmoJogador() throws NegocioException {
		UsuarioDto usuarioDto = new UsuarioDto(1, "Felipe");

		when(usuarioService.listar(eq(usuarioDto))).thenReturn(Arrays.asList(usuarioDto));
		
		Assertions.assertThrows(NegocioException.class, () -> {
			jogoService.jogar(new JogadaDto(usuarioDto, TipoJogadaEnum.PAPEL));
			jogoService.jogar(new JogadaDto(usuarioDto, TipoJogadaEnum.PAPEL));
		});
	}
	
	@Test
	public void deveFinalizarUmJogo() throws NegocioException {
		UsuarioDto usuarioDto = new UsuarioDto(1, "Felipe");

		when(usuarioService.listar(eq(usuarioDto))).thenReturn(Arrays.asList(usuarioDto));
		
		jogoService.jogar(new JogadaDto(usuarioDto, TipoJogadaEnum.PAPEL));
		
		List<JogadaDto> jogadasVencedoras = jogoService.finalizar();

		Assertions.assertEquals(1, jogadasVencedoras.size());
		Assertions.assertEquals(TipoJogadaEnum.PAPEL, jogadasVencedoras.get(0).getTipojogada());
		Assertions.assertEquals(1, jogadasVencedoras.get(0).getUsuario().getId());
		Assertions.assertEquals("Felipe", jogadasVencedoras.get(0).getUsuario().getNome());
	}
	
	@Test
	public void naoPermiteFinalizarJogoInexistente() {
		Assertions.assertThrows(NegocioException.class, () -> {
			jogoService.finalizar();
		});
	}
	
	@Test
	public void deveExcluirUmJogo() throws NegocioException {
		UsuarioDto usuarioDto = new UsuarioDto(1, "Felipe");
		
		when(usuarioService.listar(eq(usuarioDto))).thenReturn(Arrays.asList(usuarioDto));
		
		jogoService.jogar(new JogadaDto(usuarioDto, TipoJogadaEnum.PAPEL));
		
		RodadaDto rodadaDto = new RodadaDto(1, "");

		jogoService.excluir(rodadaDto);
		
		List<RodadaDto> listaRodadas = jogoService.listar(new RodadaDto(1, ""));
		
		Assertions.assertEquals(0, listaRodadas.size());
	}
	
	@Test
	public void naoPermiteExcluirJogoInexistente() {
		Assertions.assertThrows(NegocioException.class, () -> {
			jogoService.excluir(new RodadaDto(1, ""));
		});
	}
	
	@Test
	public void deveListarUmRegistro() throws NegocioException {
		UsuarioDto usuarioDto = new UsuarioDto(1, "Felipe");
		
		when(usuarioService.listar(eq(usuarioDto))).thenReturn(Arrays.asList(usuarioDto));
		
		jogoService.jogar(new JogadaDto(usuarioDto, TipoJogadaEnum.PAPEL));
		
		List<RodadaDto> listaRodadas = jogoService.listar(null);
		
		Assertions.assertEquals(1, listaRodadas.size());
		Assertions.assertEquals(1, listaRodadas.get(0).getId());
		Assertions.assertEquals("Rodada 1", listaRodadas.get(0).getNome());
	}
	
	@Test
	public void deveListarNenhumRegistro() {
		List<RodadaDto> listaRodadas = jogoService.listar(null);
		
		Assertions.assertEquals(0, listaRodadas.size());
	}
}

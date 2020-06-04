package com.btg.jokempo.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
	public void naoPermiteJogadaDeJogadorNaoCadastrado() {
		Assertions.assertThrows(NegocioException.class, () -> {
			List<JogadaDto> listaJogadasDto = new ArrayList<JogadaDto>();
			listaJogadasDto.add(new JogadaDto(new UsuarioDto(1, "Felipe"), TipoJogadaEnum.PAPEL));
			
			jogoService.jogar(listaJogadasDto);
		});
	}
	
	@Test
	public void naoPermiteJogadaRepetidaDoMesmoJogador() throws NegocioException {
		UsuarioDto usuarioDto = new UsuarioDto(1, "Felipe");

		when(usuarioService.listar(eq(usuarioDto))).thenReturn(Arrays.asList(usuarioDto));
		
		Assertions.assertThrows(NegocioException.class, () -> {
			List<JogadaDto> listaJogadasDto = new ArrayList<JogadaDto>();
			listaJogadasDto.add(new JogadaDto(new UsuarioDto(1, "Felipe"), TipoJogadaEnum.PAPEL));
			listaJogadasDto.add(new JogadaDto(new UsuarioDto(1, "Felipe"), TipoJogadaEnum.PAPEL));
			
			jogoService.jogar(listaJogadasDto);
		});
	}
	
	@Test
	public void naoPermiteUsuarioJogueMaisDeUmaVez() throws NegocioException {
		UsuarioDto usuarioDto = new UsuarioDto(1, "Felipe");

		when(usuarioService.listar(eq(usuarioDto))).thenReturn(Arrays.asList(usuarioDto));
		
		Assertions.assertThrows(NegocioException.class, () -> {
			List<JogadaDto> listaJogadasDto = new ArrayList<JogadaDto>();
			listaJogadasDto.add(new JogadaDto(new UsuarioDto(1, "Felipe"), TipoJogadaEnum.PAPEL));
			listaJogadasDto.add(new JogadaDto(new UsuarioDto(1, "Felipe"), TipoJogadaEnum.TESOURA));
			
			jogoService.jogar(listaJogadasDto);
		});
	}

	@Test
	public void deveExcluirUmJogo() throws NegocioException {
		UsuarioDto usuarioDtoFelipe = new UsuarioDto(1, "Felipe");
		UsuarioDto usuarioDtoCarlos = new UsuarioDto(2, "Carlos");
		
		when(usuarioService.listar(eq(usuarioDtoFelipe))).thenReturn(Arrays.asList(usuarioDtoFelipe));
		when(usuarioService.listar(eq(usuarioDtoCarlos))).thenReturn(Arrays.asList(usuarioDtoCarlos));
		
		List<JogadaDto> listaJogadasDto = new ArrayList<JogadaDto>();
		listaJogadasDto.add(new JogadaDto(usuarioDtoFelipe, TipoJogadaEnum.PAPEL));
		listaJogadasDto.add(new JogadaDto(usuarioDtoCarlos, TipoJogadaEnum.SPOOK));
		
		jogoService.jogar(listaJogadasDto);
		
		RodadaDto rodadaDto = new RodadaDto(1, "", null);

		jogoService.excluir(rodadaDto);
		
		List<RodadaDto> listaRodadas = jogoService.listar(new RodadaDto(1, "", null));
		
		Assertions.assertEquals(0, listaRodadas.size());
	}
	
	@Test
	public void naoPermiteExcluirJogoInexistente() {
		Assertions.assertThrows(NegocioException.class, () -> {
			jogoService.excluir(new RodadaDto(1, "", null));
		});
	}
	
	@Test
	public void deveListarUmRegistro() throws NegocioException {
		UsuarioDto usuarioDtoFelipe = new UsuarioDto(1, "Felipe");
		UsuarioDto usuarioDtoCarlos = new UsuarioDto(2, "Carlos");
		
		when(usuarioService.listar(eq(usuarioDtoFelipe))).thenReturn(Arrays.asList(usuarioDtoFelipe));
		when(usuarioService.listar(eq(usuarioDtoCarlos))).thenReturn(Arrays.asList(usuarioDtoCarlos));
		
		List<JogadaDto> listaJogadasDto = new ArrayList<JogadaDto>();
		listaJogadasDto.add(new JogadaDto(usuarioDtoFelipe, TipoJogadaEnum.PAPEL));
		listaJogadasDto.add(new JogadaDto(usuarioDtoCarlos, TipoJogadaEnum.SPOOK));
		
		jogoService.jogar(listaJogadasDto);
		
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

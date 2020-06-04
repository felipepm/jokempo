package com.btg.jokempo.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.btg.jokempo.dto.JogadaDto;
import com.btg.jokempo.dto.RodadaDto;
import com.btg.jokempo.dto.UsuarioDto;
import com.btg.jokempo.enumeration.TipoJogadaEnum;
import com.btg.jokempo.exception.NegocioException;
import com.btg.jokempo.service.IJogoService;
import com.btg.jokempo.service.impl.JogoServiceImpl;

public class JogoControllerTest {

	private JogoController jogoController;
	private IJogoService jogoService;
	
	@BeforeEach
	private void iniciar() {
		jogoService = mock(JogoServiceImpl.class);
		jogoController = new JogoController(jogoService);
	}
	
	@Test
	public void deveListarUmRegistro() {
		RodadaDto rodadaDtoConsulta = new RodadaDto(1, null, null);
		RodadaDto rodadaDtoRetorno = new RodadaDto(1, "Rodada 1", Arrays.asList(new JogadaDto(new UsuarioDto(1, "Felipe"), TipoJogadaEnum.LAGARTO)));

		when(jogoService.listar(eq(rodadaDtoConsulta))).thenReturn(Arrays.asList(rodadaDtoRetorno));
		
		List<RodadaDto> listaRodadas = jogoController.listar(1, null);
		
		Assertions.assertEquals(1, listaRodadas.size());
		Assertions.assertEquals(1, listaRodadas.get(0).getId());
		Assertions.assertEquals("Rodada 1", listaRodadas.get(0).getNome());
	}
	
	@Test
	public void deveCadastrarRodada() throws NegocioException {
		List<JogadaDto> listaJogadasDto = new ArrayList<JogadaDto>();
		listaJogadasDto.add(new JogadaDto(new UsuarioDto(1, "Felipe"), TipoJogadaEnum.PAPEL));
		RodadaDto rodadaDtoRetorno = new RodadaDto(1, "Rodada 1", Arrays.asList(new JogadaDto(new UsuarioDto(1, "Felipe"), TipoJogadaEnum.LAGARTO)));

		when(jogoService.jogar(eq(listaJogadasDto))).thenReturn(rodadaDtoRetorno);
		
		ResponseEntity<Object> cadastroResponseEntity = jogoController.jogar(Arrays.asList(new JogadaDto(new UsuarioDto(1, "Felipe"), TipoJogadaEnum.LAGARTO)));
		
		Assertions.assertEquals(200, cadastroResponseEntity.getStatusCodeValue());
	}
	
	@Test
	public void deveExcluirRodada() throws NegocioException {
		RodadaDto rodadaDto = new RodadaDto(1, "", null);
		
		doNothing().when(jogoService).excluir(eq(rodadaDto));
		
		ResponseEntity<Object> exclusaoResponseEntity = jogoController.excluir(1);
		
		Assertions.assertEquals(200, exclusaoResponseEntity.getStatusCodeValue());
	}
}

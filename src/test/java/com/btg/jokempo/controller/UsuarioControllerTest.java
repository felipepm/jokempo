package com.btg.jokempo.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.btg.jokempo.dto.UsuarioDto;
import com.btg.jokempo.exception.NegocioException;
import com.btg.jokempo.service.IUsuarioService;
import com.btg.jokempo.service.impl.UsuarioServiceImpl;

public class UsuarioControllerTest {

	private UsuarioController usuarioController;
	private IUsuarioService usuarioService;
	
	@BeforeEach
	private void iniciar() {
		usuarioService = mock(UsuarioServiceImpl.class);
		usuarioController = new UsuarioController(usuarioService);
	}

	@Test
	public void deveListarUmRegistro() {
		UsuarioDto usuarioDtoConsulta = new UsuarioDto(1, null);
		UsuarioDto usuarioDtoRetorno = new UsuarioDto(1, "Felipe");

		when(usuarioService.listar(eq(usuarioDtoConsulta))).thenReturn(Arrays.asList(usuarioDtoRetorno));
		
		List<UsuarioDto> listaUsuarios = usuarioController.listar(1, null);
		
		Assertions.assertEquals(1, listaUsuarios.size());
		Assertions.assertEquals(1, listaUsuarios.get(0).getId());
		Assertions.assertEquals("Felipe", listaUsuarios.get(0).getNome());
	}
	
	@Test
	public void deveCadastrarUsuario() throws NegocioException {
		UsuarioDto usuarioDtoInclusao = new UsuarioDto(0, "Felipe");
		UsuarioDto usuarioDtoRetorno = new UsuarioDto(1, "Felipe");
		
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		when(usuarioService.cadastrar(eq(usuarioDtoInclusao))).thenReturn(usuarioDtoRetorno);
		
		ResponseEntity<Object> cadastroResponseEntity = usuarioController.cadastrar(usuarioDtoInclusao);
		
		Assertions.assertEquals(201, cadastroResponseEntity.getStatusCodeValue());
		Assertions.assertEquals("/1", cadastroResponseEntity.getHeaders().getLocation().getPath());
	}
	
	@Test
	public void deveExcluirUsuario() throws NegocioException {
		UsuarioDto usuarioDto = new UsuarioDto(0, "Felipe");
		
		doNothing().when(usuarioService).excluir(eq(usuarioDto));
		
		ResponseEntity<Object> exclusaoResponseEntity = usuarioController.excluir(1);
		
		Assertions.assertEquals(200, exclusaoResponseEntity.getStatusCodeValue());
	}

}

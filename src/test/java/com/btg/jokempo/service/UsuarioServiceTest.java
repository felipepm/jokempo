package com.btg.jokempo.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.btg.jokempo.dto.UsuarioDto;
import com.btg.jokempo.exception.NegocioException;
import com.btg.jokempo.service.impl.UsuarioServiceImpl;

public class UsuarioServiceTest {
	
	private UsuarioDto usuarioDto = null;
	private UsuarioServiceImpl usuarioService = null;

	@BeforeEach
	private void iniciar() {
		usuarioDto  = new UsuarioDto(1, "Felipe");
		usuarioService = new UsuarioServiceImpl();
	}
	
	@Test
	public void deveIncluirUmRegistro() throws NegocioException {
		usuarioService.cadastrar(usuarioDto);
		List<UsuarioDto> listaUsuarios = usuarioService.listar(null);
		Assertions.assertEquals(1, listaUsuarios.size());
		Assertions.assertEquals("Felipe", listaUsuarios.get(0).getNome());
	}
	
	@Test
	public void deveIncluirMultiplosRegistros() throws NegocioException {
		usuarioService.cadastrar(usuarioDto);
		usuarioService.cadastrar(new UsuarioDto(2, "Melo"));
		List<UsuarioDto> listaUsuarios = usuarioService.listar(null);
		Assertions.assertEquals(2, listaUsuarios.size());
		Assertions.assertEquals("Felipe", listaUsuarios.get(0).getNome());
		Assertions.assertEquals(1, listaUsuarios.get(0).getId());
		Assertions.assertEquals("Melo", listaUsuarios.get(1).getNome());
		Assertions.assertEquals(2, listaUsuarios.get(1).getId());
	}

	@Test
	public void naoPermiteIncluirRegistroDuplicado() {
		Assertions.assertThrows(NegocioException.class, () -> {
			usuarioService.cadastrar(usuarioDto);
			usuarioService.cadastrar(usuarioDto);
		});
	}
	
	@Test
	public void deveExcluirUmRegistro() throws NegocioException {
		usuarioService.cadastrar(usuarioDto);
		usuarioService.excluir(new UsuarioDto(1, ""));
		List<UsuarioDto> listaUsuarios = usuarioService.listar(null);
		Assertions.assertEquals(0, listaUsuarios.size());
	}
	
	@Test
	public void deveExcluirMultiplosRegistros() throws NegocioException {
		usuarioService.cadastrar(usuarioDto);
		usuarioService.cadastrar(new UsuarioDto(2, "Melo"));
		usuarioService.excluir(new UsuarioDto(1, ""));
		usuarioService.excluir(new UsuarioDto(2, null));
		List<UsuarioDto> listaUsuarios = usuarioService.listar(null);
		Assertions.assertEquals(0, listaUsuarios.size());
	}
	
	@Test
	public void naoPermiteExcluirRegistroInexistente() {
		Assertions.assertThrows(NegocioException.class, () -> {
			usuarioService.excluir(usuarioDto);
		});
	}
	
	@Test
	public void deveListarUmRegistro() throws NegocioException {
		usuarioService.cadastrar(usuarioDto);
		List<UsuarioDto> listaUsuarios = usuarioService.listar(null);
		Assertions.assertEquals(1, listaUsuarios.size());
		Assertions.assertEquals("Felipe", listaUsuarios.get(0).getNome());
	}
	
	@Test
	public void deveListarNenhumRegistro() {
		List<UsuarioDto> listaUsuarios = usuarioService.listar(null);
		Assertions.assertEquals(0, listaUsuarios.size());
	}
}

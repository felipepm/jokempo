package com.btg.jokempo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.btg.jokempo.dto.UsuarioDto;
import com.btg.jokempo.exception.NegocioException;
import com.btg.jokempo.service.IUsuarioService;

@RestController
@RequestMapping(path = "/usuario")
public class UsuarioController {
	
	private IUsuarioService usuarioService;
	
	public UsuarioController(IUsuarioService usuarioService) {
		super();
		this.usuarioService = usuarioService;
	}

	@GetMapping(name="/", produces="application/json")
	public List<UsuarioDto> listar(@RequestParam(value="id",   required=false) long id,
								   @RequestParam(value="nome", required=false) String nome) {
		if (id == 0 && (nome == null || nome.trim().isEmpty())) {
			return usuarioService.listar(null);
		} else {
			return usuarioService.listar(new UsuarioDto(id, nome));
		}
	}
	
	@PostMapping(name="/", consumes="application/json", produces="application/json")
	public ResponseEntity<Object> cadastrar(@RequestBody UsuarioDto usuarioDto) {
		try {
			UsuarioDto novoUsuarioDto = usuarioService.cadastrar(usuarioDto);
			
			//Create resource location
	        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
	                                    .path("/{id}")
	                                    .buildAndExpand(novoUsuarioDto.getId())
	                                    .toUri();
	        
	        return ResponseEntity.created(location).build();
		} catch (NegocioException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> excluir(long id) {
		try {
			UsuarioDto usuarioDto = new UsuarioDto(id, "");
		
			usuarioService.excluir(usuarioDto);
			
			return ResponseEntity.ok().build();
		} catch (NegocioException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
}

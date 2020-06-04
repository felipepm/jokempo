package com.btg.jokempo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btg.jokempo.dto.JogadaDto;
import com.btg.jokempo.dto.RodadaDto;
import com.btg.jokempo.exception.NegocioException;
import com.btg.jokempo.service.IJogoService;

@RestController
@RequestMapping(path = "/jogo")
public class JogoController {

	private IJogoService jogoService;
	
	public JogoController(IJogoService jogoService) {
		super();
		this.jogoService = jogoService;
	}
	
	@PostMapping(name="/", consumes="application/json", produces="application/json")
	public ResponseEntity<Object> jogar(@RequestBody List<JogadaDto> listaJogadasDto) {
		try {
			RodadaDto rodadaDto = jogoService.jogar(listaJogadasDto);
			
			return ResponseEntity.ok(rodadaDto);
		} catch (NegocioException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> excluir(long id) {
		try {
			RodadaDto rodadaDto = new RodadaDto(id, "", null);
		
			jogoService.excluir(rodadaDto);
			
			return ResponseEntity.ok().build();
		} catch (NegocioException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping(name="/{id}/{nome}", produces="application/json")
	public List<RodadaDto> listar(@PathVariable(value="id",   required=false) long id,
								  @PathVariable(value="nome", required=false) String nome) {
		if (id == 0 && (nome == null || nome.trim().isEmpty())) {
			return jogoService.listar(null);
		} else {
			return jogoService.listar(new RodadaDto(id, nome, null));
		}
	}
}

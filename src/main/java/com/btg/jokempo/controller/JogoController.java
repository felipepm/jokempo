package com.btg.jokempo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btg.jokempo.service.IJogoService;

@RestController
@RequestMapping(path = "/jogo")
public class JogoController {

	private IJogoService jogoService;
	
	public JogoController(IJogoService jogoService) {
		super();
		this.jogoService = jogoService;
	}
}

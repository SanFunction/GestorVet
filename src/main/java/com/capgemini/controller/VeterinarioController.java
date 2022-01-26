package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.entities.Veterinario;
import com.capgemini.services.IVeterinarioService;

@RestController
@RequestMapping(value = "/veterinario")
@CrossOrigin(origins = "http://localhost:4200")
public class VeterinarioController {

	@Autowired
	private IVeterinarioService veterinarioService;
	
	@GetMapping
	@Transactional(readOnly = true)
	public ResponseEntity<List<Veterinario>> findAll(){
		
		ResponseEntity<List<Veterinario>> responseEntity = null;

		List<Veterinario> veterinarios = null;

		veterinarios = veterinarioService.listaVeterinario();

		if(veterinarios.size() > 0) {

			responseEntity = new ResponseEntity<List<Veterinario>>(veterinarios, HttpStatus.OK);
		} else {

			responseEntity = new ResponseEntity<List<Veterinario>>(HttpStatus.NO_CONTENT);
		}

		return responseEntity ;
	}
}

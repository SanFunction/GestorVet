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

import com.capgemini.entities.Especie;
import com.capgemini.services.IEspecieService;

@RestController
@RequestMapping(value = "/especie")
@CrossOrigin(origins = "http://localhost:4200")
public class EspecieController {

	@Autowired
	private IEspecieService especieService;
	
	@GetMapping
	@Transactional(readOnly = true)
	public ResponseEntity<List<Especie>> findAll(){
		
		ResponseEntity<List<Especie>> responseEntity = null;

		List<Especie> especies = null;

		especies = especieService.listaEspecies();

		if(especies.size() > 0) {

			responseEntity = new ResponseEntity<List<Especie>>(especies, HttpStatus.OK);
		} else {

			responseEntity = new ResponseEntity<List<Especie>>(HttpStatus.NO_CONTENT);
		}

		return responseEntity ;
	}
}

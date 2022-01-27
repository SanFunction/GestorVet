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

import com.capgemini.entities.Estado;
import com.capgemini.services.IEstadoService;



@RestController
@RequestMapping(value = "/estado")
@CrossOrigin(origins = "http://localhost:4200")
public class EstadoController {
	
	@Autowired
	private IEstadoService estadoService;
	
	@GetMapping
	@Transactional(readOnly = true)
	public ResponseEntity<List<Estado>> findAll(){
		
		ResponseEntity<List<Estado>> responseEntity = null;

		List<Estado> estado = null;

		estado = estadoService.listaEstado();

		if(estado.size() > 0) {

			responseEntity = new ResponseEntity<List<Estado>>(estado, HttpStatus.OK);
		} else {

			responseEntity = new ResponseEntity<List<Estado>>(HttpStatus.NO_CONTENT);
		}

		return responseEntity ;
	}
}

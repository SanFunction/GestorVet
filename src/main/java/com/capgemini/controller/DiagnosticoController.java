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

import com.capgemini.entities.Diagnostico;
import com.capgemini.services.IDiagnosticoService;

@RestController
@RequestMapping(value = "/diagnostico")
@CrossOrigin(origins = "http://localhost:4200")
public class DiagnosticoController {
	
	@Autowired
	private IDiagnosticoService diagnosticoService;
	
	@GetMapping
	@Transactional(readOnly = true)
	public ResponseEntity<List<Diagnostico>> findAll(){
		
		ResponseEntity<List<Diagnostico>> responseEntity = null;

		List<Diagnostico> diagnostico = null;

		diagnostico = diagnosticoService.listaDiagnostico();

		if(diagnostico.size() > 0) {

			responseEntity = new ResponseEntity<List<Diagnostico>>(diagnostico, HttpStatus.OK);
		} else {

			responseEntity = new ResponseEntity<List<Diagnostico>>(HttpStatus.NO_CONTENT);
		}

		return responseEntity ;
	}

}

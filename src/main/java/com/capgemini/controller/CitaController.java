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

import com.capgemini.entities.Cita;
import com.capgemini.services.ICitaService;

@RestController
@RequestMapping(value = "/cita")
@CrossOrigin(origins = "http://localhost:4200")
public class CitaController {
	
	@Autowired
	private ICitaService citaService;
	
	@GetMapping
	@Transactional(readOnly = true)
	public ResponseEntity<List<Cita>> findAll(){
		
		ResponseEntity<List<Cita>> responseEntity = null;

		List<Cita> citas = null;

		citas = citaService.listaCitas();

		if(citas.size() > 0) {

			responseEntity = new ResponseEntity<List<Cita>>(citas, HttpStatus.OK);
		} else {

			responseEntity = new ResponseEntity<List<Cita>>(HttpStatus.NO_CONTENT);
		}

		return responseEntity ;
	}
}

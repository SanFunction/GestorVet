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

import com.capgemini.entities.Tratamiento;
import com.capgemini.services.ITratamientoService;

@RestController
@RequestMapping(value = "/tratamiento")
@CrossOrigin(origins = "http://localhost:4200")
public class TratamientoController {

	@Autowired
	private ITratamientoService tratamientoService;
	
	@GetMapping
	@Transactional(readOnly = true)
	public ResponseEntity<List<Tratamiento>> findAll(){
		
		ResponseEntity<List<Tratamiento>> responseEntity = null;

		List<Tratamiento> tratamiento = null;

		tratamiento = tratamientoService.listaTratamiento();

		if(tratamiento.size() > 0) {

			responseEntity = new ResponseEntity<List<Tratamiento>>(tratamiento, HttpStatus.OK);
		} else {

			responseEntity = new ResponseEntity<List<Tratamiento>>(HttpStatus.NO_CONTENT);
		}

		return responseEntity ;
	}
}

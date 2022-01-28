package com.capgemini.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.entities.Cita;
import com.capgemini.entities.Mascota;
import com.capgemini.services.IMascotaService;

@RestController
@RequestMapping(value = "/mascota")
@CrossOrigin(origins = "http://localhost:4200")
public class MascotaController {

	@Autowired
	private IMascotaService mascotaService;

	@GetMapping
	@Transactional(readOnly = true)
	public ResponseEntity<List<Mascota>> findAll() {

		ResponseEntity<List<Mascota>> responseEntity = null;

		List<Mascota> mascotas = null;

		mascotas = mascotaService.listaMascota();

		if (mascotas.size() > 0) {

			responseEntity = new ResponseEntity<List<Mascota>>(mascotas, HttpStatus.OK);
		} else {

			responseEntity = new ResponseEntity<List<Mascota>>(HttpStatus.NO_CONTENT);
		}

		return responseEntity;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Mascota> findById(@PathVariable(name = "id") String id) {

		ResponseEntity<Mascota> responseEntity = null;

		Mascota mascota = null;

		mascota = mascotaService.getMascota(id);

		if (mascota != null) {
			responseEntity = new ResponseEntity<Mascota>(mascota, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<Mascota>(HttpStatus.NO_CONTENT);
		}
		return responseEntity;
	}

}

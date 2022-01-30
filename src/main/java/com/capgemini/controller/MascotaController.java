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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	//añadir una mascota
	@PostMapping
	public ResponseEntity<Map<String,Object>> guardar(@Valid @RequestBody Mascota mascota, BindingResult result){

		Map<String, Object> responseAsMap = new HashMap<>();

		ResponseEntity<Map<String,Object>> responseEntity=null;

		List<String> errores = null;		

		if(result.hasErrors()) {

			errores = new ArrayList<>();

			for (ObjectError error : result.getAllErrors()) {
				errores.add(error.getDefaultMessage());
			}

			// salimos informando al qu erealizo la peticion (request) de los errores
			// que han tenido lugar
			responseAsMap.put("errores", errores);
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.BAD_REQUEST);

			return responseEntity;
		}

		//Si no hay errores, entondes persistimos (guardamos) el producto 

		try {
			if(mascota != null) {
				mascotaService.addMascota(mascota);
				responseAsMap.put("mascota", mascota);
				responseAsMap.put("mensaje", "La mascota con id " + mascota.getId() + 
						" se ha guardado exitosamente");
				responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);
			} else {
				responseAsMap.put("mensaje", "La mascota no se ha podido guardar en la base de datos");
				responseEntity = new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (DataAccessException e) {
			// TODO: handle exception
			responseAsMap.put("mensaje", "Error fatal, no se ha podido guadar la mascota");
			responseAsMap.put("error", e.getMostSpecificCause());
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;

	}
	
	//actualizar una mascota
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> actualizar(@PathVariable(name= "id") String id, @Valid @RequestBody Mascota mascota, BindingResult result){
		
		Map<String, Object> responseAsMap = new HashMap<>();		
		ResponseEntity<Map<String, Object>> responseEntity = null;		
		List<String> errores = null; 
		
		if (result.hasErrors()) { 									
			errores = new ArrayList<>(); 			
			for( ObjectError error : result.getAllErrors()) { 
				errores.add(error.getDefaultMessage());
			}			
			
			responseAsMap.put("errores", errores);			
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.BAD_REQUEST); //Nos pide que le digamos el estado ya que REST es responseEntity Status
			
			return responseEntity;
		}
		
		try {
			//MODIFICACION PARA QUE FUNCIONE EL PUT
			
			Mascota mascotaDB = mascotaService.getMascota(id);
			
			if(mascotaDB != null) {
				mascota.setId(Long.parseLong(id));
				mascotaService.addMascota(mascota);
				responseAsMap.put("mascota", mascota);				
				responseAsMap.put("mensaje", "La mascota con id " + mascota.getId() + " se ha actualizado exitosamente!!!");
				responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.OK);
			} else{
				responseAsMap.put("mensaje", "La mascota no se ha podido actualizar en la BD");
				responseEntity = new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}			
				
		} catch (DataAccessException e) {
			responseAsMap.put("mensaje", "Error fatal, no se ha podido actualizar la mascota");
			responseAsMap.put("error", e.getMostSpecificCause());
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		
		return responseEntity;
		
	}
	
	//borrar una mascota
	@DeleteMapping("/{id}")
	public ResponseEntity<Mascota> eliminar(@PathVariable(name = "id") String id){

		ResponseEntity<Mascota> responseEntity = null;		

		try {
			Mascota mascotaDB = mascotaService.getMascota(id);
			
			if(mascotaDB != null) {
				mascotaService.deleteMascota(mascotaDB);
				responseEntity = new ResponseEntity<Mascota>(HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<Mascota>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
	
		} catch (DataAccessException e) {
			// TODO: handle exception
			responseEntity = new ResponseEntity<Mascota>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;

	}

}

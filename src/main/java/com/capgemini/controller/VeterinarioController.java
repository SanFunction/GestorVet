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
	
	//BUSQUEDA DE VETERINARIO POR ID
	
	@GetMapping("/{id}")
	public ResponseEntity<Veterinario> findById(@PathVariable(name = "id") String id) {

		ResponseEntity<Veterinario> responseEntity = null;

		Veterinario veterinario= null;

		veterinario = veterinarioService.getVeterinario(id);

		if (veterinario != null) {
			responseEntity = new ResponseEntity<Veterinario>(veterinario, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<Veterinario>(HttpStatus.NO_CONTENT);
		}
		return responseEntity;
	}
	
	//añadir una Cita
	@PostMapping
	public ResponseEntity<Map<String,Object>> guardar(@Valid @RequestBody Veterinario veterinario, BindingResult result){

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
			if(veterinario != null) {
				veterinarioService.addVeterinario(veterinario);
				responseAsMap.put("Veterinario", veterinario);
				responseAsMap.put("mensaje", "El Veterinario con id " +veterinario.getId() + 
						" se ha guardado exitosamente");
				responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);
			} else {
				responseAsMap.put("mensaje", "El veterinario no se ha podido guardar en la base de datos");
				responseEntity = new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (DataAccessException e) {
			// TODO: handle exception
			responseAsMap.put("Mensaje", "Error fatal, no se ha podido guadar el veterinario");
			responseAsMap.put("Error", e.getMostSpecificCause());
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;

	}	
	
	//Borrado dando el id
	@DeleteMapping("/{id}")
	public ResponseEntity<Veterinario> eliminar(@PathVariable(name = "id") String id){

		ResponseEntity<Veterinario> responseEntity = null;		

		try {
			Veterinario veterinarioDB = veterinarioService.getVeterinario(id);
			
			if(veterinarioDB != null) {
				veterinarioService.deleteVeterinario(veterinarioDB);
				responseEntity = new ResponseEntity<Veterinario>(HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<Veterinario>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
	
		} catch (DataAccessException e) {
			// TODO: handle exception
			responseEntity = new ResponseEntity<Veterinario>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;

	}
	
	// Método que actualiza una cita
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> actualizar(@PathVariable(name= "id") String id, @Valid @RequestBody Veterinario veterinario, BindingResult result){
		
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
			
			Veterinario veterinarioDB = veterinarioService.getVeterinario(id);
			
			if(veterinarioDB != null) {
				veterinario.setId(Long.parseLong(id));
				veterinarioService.addVeterinario(veterinario);
				responseAsMap.put("Veterinario", veterinario);				
				responseAsMap.put("mensaje", "El Veterinario con id " + veterinario.getId() + " se ha actualizado exitosamente!!!");
				responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.OK);
			} else{
				responseAsMap.put("mensaje", "El Veterinario no se ha podido actualizar en la BD");
				responseEntity = new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}			
				
		} catch (DataAccessException e) {
			responseAsMap.put("mensaje", "Error fatal, no se ha podido actualizar el Veterinario");
			responseAsMap.put("error", e.getMostSpecificCause());
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		
		return responseEntity;
	
	}
}

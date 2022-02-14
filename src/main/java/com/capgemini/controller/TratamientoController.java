package com.capgemini.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.capgemini.entities.Tratamiento;
import com.capgemini.services.ITratamientoService;

@RestController
@RequestMapping(value = "/tratamiento")
@CrossOrigin(origins = "http://localhost:4200")
public class TratamientoController {

	@Autowired
	private ITratamientoService tratamientoService;
	
	// Pedimos una lista de tratamientos
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
	
	//Añadimos un tratamiento
	@PostMapping
	public ResponseEntity<Map<String,Object>> guardar(@RequestBody Tratamiento tratamiento, BindingResult result){

		Map<String, Object> responseAsMap = new HashMap<>();

		ResponseEntity<Map<String,Object>> responseEntity=null;

		List<String> errores = null;		

		if(result.hasErrors()) {

			errores = new ArrayList<>();

			for (ObjectError error : result.getAllErrors()) {
				errores.add(error.getDefaultMessage());
			}

			responseAsMap.put("errores", errores);
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.BAD_REQUEST);

			return responseEntity;
		}

		try {
			if(tratamiento != null) {
				tratamientoService.addTratamiento(tratamiento);
				responseAsMap.put("tratamiento", tratamiento);
				responseAsMap.put("mensaje", "El tratamiento se ha añadido correctamente");
				responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);
			} else {
				responseAsMap.put("mensaje", "El tratamiento se ha podido guardar en la base de datos");
				responseEntity = new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (DataAccessException e) {
			responseAsMap.put("mensaje", "Error! no se ha podido guadar el tratamiento");
			responseAsMap.put("error", e.getMostSpecificCause());
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;

	}
	
	// Borramos un tratamiento
	@DeleteMapping("/{id}")
	public ResponseEntity<Tratamiento> eliminar(@PathVariable(name = "id") String id){

		ResponseEntity<Tratamiento> responseEntity = null;		

		try {
			Tratamiento tratamientoDB = tratamientoService.getTratamiento(id);
			
			if(tratamientoDB != null) {
				tratamientoService.deleteTratamiento(tratamientoDB);
				responseEntity = new ResponseEntity<Tratamiento>(HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<Tratamiento>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
	
		} catch (DataAccessException e) {
			responseEntity = new ResponseEntity<Tratamiento>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;

	}
	
	// Actualizamos un tratamiento
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> actualizar(@PathVariable(name= "id") String id, @RequestBody Tratamiento tratamiento, BindingResult result){
		
		Map<String, Object> responseAsMap = new HashMap<>();		
		ResponseEntity<Map<String, Object>> responseEntity = null;		
		List<String> errores = null; 
		
		if (result.hasErrors()) { 									
			errores = new ArrayList<>(); 			
			for( ObjectError error : result.getAllErrors()) { 
				errores.add(error.getDefaultMessage());
			}			
			
			responseAsMap.put("errores", errores);			
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.BAD_REQUEST); 
			
			return responseEntity;
		}
		
		try {
			
			Tratamiento tratamientoDB = tratamientoService.getTratamiento(id);
			
			if(tratamientoDB != null) {
				tratamiento.setId(Long.parseLong(id));
				tratamientoService.addTratamiento(tratamiento);
				responseAsMap.put("tratamiento", tratamiento);				
				responseAsMap.put("Mensaje", "El tratamiento se ha actualizado correctamente");
				responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.OK);
			} else{
				responseAsMap.put("Mensaje", "El tratamiento no se ha podido actualizar en la BD");
				responseEntity = new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}			
				
		} catch (DataAccessException e) {
			responseAsMap.put("Mensaje", "Error! no se ha podido actualizar el tratamiento");
			responseAsMap.put("Error", e.getMostSpecificCause());
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		
		return responseEntity;
		
	}
}

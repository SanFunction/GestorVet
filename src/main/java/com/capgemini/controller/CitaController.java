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

import com.capgemini.entities.Cita;
import com.capgemini.entities.Cliente;
import com.capgemini.services.ICitaService;

@RestController
@RequestMapping(value = "/cita")
@CrossOrigin(origins = "http://localhost:4200")
public class CitaController {

	@Autowired
	private ICitaService citaService;

	//listado completo de los elementos
	@GetMapping
	@Transactional(readOnly = true)
	public ResponseEntity<List<Cita>> findAll() {

		ResponseEntity<List<Cita>> responseEntity = null;

		List<Cita> citas = null;

		citas = citaService.listaCitas();
		
		if (citas.size() > 0) {

			responseEntity = new ResponseEntity<List<Cita>>(citas, HttpStatus.OK);
		} else {

			responseEntity = new ResponseEntity<List<Cita>>(HttpStatus.NO_CONTENT);
		}

		return responseEntity;
	}
	
	//busqueda de un producto en concreto por el id
	@GetMapping("/{id}")
	public ResponseEntity<Cita> findById(@PathVariable(name = "id") String id) {

		ResponseEntity<Cita> responseEntity = null;

		Cita cita= null;

		cita = citaService.getCita(id);

		if (cita != null) {
			responseEntity = new ResponseEntity<Cita>(cita, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<Cita>(HttpStatus.NO_CONTENT);
		}
		return responseEntity;
	}
	
	//obtener la citas odernadas por fecha
	@GetMapping("/ordenadas/{id}")
	public ResponseEntity<List<Cita>> citasOrdenas(@PathVariable(name = "id") String id) {

		ResponseEntity<List<Cita>> responseEntity = null;

		List<Cita> citas = null;

		citas = citaService.listaCitas();
		
		List<Cita> citaLista = new ArrayList<>();
		
		citaLista = citas;
	
		citaLista.sort((o1, o2) -> o1.getFecha().compareTo(o2.getFecha()));
		
//		citaLista.add(citaService);
//		
//		citaLista.stream().filter(x => x.get)

		if (citas != null) {
			responseEntity = new ResponseEntity<List<Cita>>(citas, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<List<Cita>>(HttpStatus.NO_CONTENT);
		}
		return responseEntity;
	}
	
	//añadir una Cita
	@PostMapping
	public ResponseEntity<Map<String,Object>> guardar(@RequestBody Cita cita, BindingResult result){

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
			if(cita != null) {
				citaService.addCita(cita);
				responseAsMap.put("cita", cita);
				responseAsMap.put("mensaje", "La cita se ha añadido correctamente");
				responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);
			} else {
				responseAsMap.put("mensaje", "La cita no se ha podido guardar en la base de datos");
				responseEntity = new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (DataAccessException e) {
			// TODO: handle exception
			responseAsMap.put("mensaje", "Error! no se ha podido guadar la cita");
			responseAsMap.put("error", e.getMostSpecificCause());
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;

	}	
	
	//Borrado dando el id
	@DeleteMapping("/{id}")
	public ResponseEntity<Cita> eliminar(@PathVariable(name = "id") String id){

		ResponseEntity<Cita> responseEntity = null;		

		try {
			Cita citaDB = citaService.getCita(id);
			
			if(citaDB != null) {
				citaService.deleteCita(citaDB);
				responseEntity = new ResponseEntity<Cita>(HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<Cita>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
	
		} catch (DataAccessException e) {
			// TODO: handle exception
			responseEntity = new ResponseEntity<Cita>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;

	}
	
	// Método que actualiza una cita
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> actualizar(@PathVariable(name= "id") String id, @RequestBody Cita cita, BindingResult result){
		
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
			
			Cita citaDB = citaService.getCita(id);
			
			if(citaDB != null) {
				cita.setId(Long.parseLong(id));
				citaService.addCita(cita);
				responseAsMap.put("cita", cita);				
				responseAsMap.put("Mensaje", "La cita se ha actualizado correctamente");
				responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.OK);
			} else{
				responseAsMap.put("Mensaje", "La cita no se ha podido actualizar en la BD");
				responseEntity = new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}			
				
		} catch (DataAccessException e) {
			responseAsMap.put("Mensaje", "Error! no se ha podido actualizar la cita");
			responseAsMap.put("Error", e.getMostSpecificCause());
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		
		return responseEntity;
		
	}

}

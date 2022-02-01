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
	
	//busqueda de un producto en concreto por el id
	@GetMapping("/{id}")
	public ResponseEntity<Diagnostico> findById(@PathVariable(name = "id") String id) {

		ResponseEntity<Diagnostico> responseEntity = null;

		Diagnostico diagnostico= null;

		diagnostico = diagnosticoService.getDiagnostico(id);

		if (diagnostico != null) {
			responseEntity = new ResponseEntity<Diagnostico>(diagnostico, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<Diagnostico>(HttpStatus.NO_CONTENT);
		}
		return responseEntity;
	}
	
	//añadir una Cita
	@PostMapping
	public ResponseEntity<Map<String,Object>> guardar(@Valid @RequestBody Diagnostico diagnostico, BindingResult result){

		Map<String, Object> responseAsMap = new HashMap<>();

		ResponseEntity<Map<String,Object>> responseEntity=null;

		List<String> errores = null;		

		if(result.hasErrors()) {

			errores = new ArrayList<>();

			for (ObjectError error : result.getAllErrors()) {
				errores.add(error.getDefaultMessage());
			}
			responseAsMap.put("Errores", errores);
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.BAD_REQUEST);

			return responseEntity;
		}

		//Si no hay errores, entondes persistimos (guardamos) el producto 

		try {
			if(diagnostico != null) {
				diagnosticoService.addDiagnostico(diagnostico);
				responseAsMap.put("Diagnostico", diagnostico);
				responseAsMap.put("Mensaje", "El diagnostico se ha guardado correctamente");
				responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);
			} else {
				responseAsMap.put("Error con el guardado:", "El diagnostico no se ha podido guardar en la base de datos");
				responseEntity = new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (DataAccessException e) {
			// TODO: handle exception
			responseAsMap.put("Mensaje", "Error! no se ha podido guadar la cita");
			responseAsMap.put("Error", e.getMostSpecificCause());
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;

	}	
	
	//Borrado dando el id
	@DeleteMapping("/{id}")
	public ResponseEntity<Diagnostico> eliminar(@PathVariable(name = "id") String id){

		ResponseEntity<Diagnostico> responseEntity = null;		

		try {
			Diagnostico diagnosticoDB = diagnosticoService.getDiagnostico(id);
			
			if(diagnosticoDB != null) {
				diagnosticoService.deleteDiagnostico(diagnosticoDB);
				responseEntity = new ResponseEntity<Diagnostico>(HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<Diagnostico>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
	
		} catch (DataAccessException e) {
			// TODO: handle exception
			responseEntity = new ResponseEntity<Diagnostico>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;

	}
	
	// Método que actualiza una cita
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> actualizar(@PathVariable(name= "id") String id, @Valid @RequestBody Diagnostico diagnostico, BindingResult result){
		
		Map<String, Object> responseAsMap = new HashMap<>();		
		ResponseEntity<Map<String, Object>> responseEntity = null;		
		List<String> errores = null; 
		
		if (result.hasErrors()) { 									
			errores = new ArrayList<>(); 			
			for( ObjectError error : result.getAllErrors()) { 
				errores.add(error.getDefaultMessage());
			}			
			
			responseAsMap.put("Errores", errores);			
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.BAD_REQUEST); 
			
			return responseEntity;
		}
		
		try {
			//MODIFICACION PARA QUE FUNCIONE EL PUT
			Diagnostico diagnosticoDB = diagnosticoService.getDiagnostico(id);
			
			if(diagnosticoDB != null) {
				diagnostico.setId(Long.parseLong(id));
				diagnosticoService.addDiagnostico(diagnostico);
				responseAsMap.put("Diagnostico", diagnostico);				
				responseAsMap.put("Mensaje", "El diagnostico se ha actualizado correctamente");
				responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.OK);
			} else{
				responseAsMap.put("Mensaje", "El diagnostico no se ha podido actualizar en la Base de Datos");
				responseEntity = new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}			
				
		} catch (DataAccessException e) {
			responseAsMap.put("Mensaje", "Error! no se ha podido actualizar el diagnostico");
			responseAsMap.put("Error", e.getMostSpecificCause());
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		
		return responseEntity;
		
	}

}

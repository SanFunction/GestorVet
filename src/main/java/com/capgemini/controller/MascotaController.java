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

import com.capgemini.entities.Cliente;
import com.capgemini.entities.Diagnostico;
import com.capgemini.entities.Mascota;
import com.capgemini.services.IClienteService;
import com.capgemini.services.IMascotaService;

@RestController
@RequestMapping(value = "/mascota")
@CrossOrigin(origins = "http://localhost:4200")
public class MascotaController {

	@Autowired
	private IMascotaService mascotaService;
	
	@Autowired
	private IClienteService clienteService;

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
	
	//mostrar diagnosticos de una mascota dado el id de la mascota
	@GetMapping("diagnostico/{id}")
	public ResponseEntity<List<Diagnostico>> getDiagnosticosById(@PathVariable(name = "id") String id) {

		ResponseEntity<List<Diagnostico>> responseEntity = null;

		Mascota mascota = null;

		mascota = mascotaService.getMascota(id);
		
		List<Diagnostico> diagnosticosMascota = mascota.getDiagnostico(); 

		if (diagnosticosMascota != null) {
			responseEntity = new ResponseEntity<List<Diagnostico>>(diagnosticosMascota, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<List<Diagnostico>>(HttpStatus.NO_CONTENT);
		}
		return responseEntity;
	}
	
	//añadir una mascota
	@PostMapping
	public ResponseEntity<Map<String,Object>> guardar(@RequestBody Mascota mascota, BindingResult result){

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
			if(mascota != null) {
				mascotaService.addMascota(mascota);
				responseAsMap.put("Mascota", mascota);
				responseAsMap.put("Mensaje", "La mascota: " + mascota.getNombre() + 
						" se ha guardado correctamente");
				responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);
			} else {
				responseAsMap.put("Mensaje", "La mascota no se ha podido guardar en la base de datos");
				responseEntity = new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (DataAccessException e) {
			// TODO: handle exception
			responseAsMap.put("Mensaje", "Error! no se ha podido guadar la mascota");
			responseAsMap.put("Error", e.getMostSpecificCause());
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;

	}
	
	//actualizar una mascota
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> actualizar(@PathVariable(name= "id") String id, @RequestBody Mascota mascota, BindingResult result){
		
		Map<String, Object> responseAsMap = new HashMap<>();		
		ResponseEntity<Map<String, Object>> responseEntity = null;		
		List<String> errores = null; 
		
		if (result.hasErrors()) { 									
			errores = new ArrayList<>(); 			
			for( ObjectError error : result.getAllErrors()) { 
				errores.add(error.getDefaultMessage());
			}			
			
			responseAsMap.put("Errores", errores);			
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.BAD_REQUEST); //Nos pide que le digamos el estado ya que REST es responseEntity Status
			
			return responseEntity;
		}
		
		try {
			//MODIFICACION PARA QUE FUNCIONE EL PUT
			
			Mascota mascotaDB = mascotaService.getMascota(id);
			
			if(mascotaDB != null) {
				mascota.setId(Long.parseLong(id));
				mascotaService.addMascota(mascota);
				responseAsMap.put("Mascota", mascota);				
				responseAsMap.put("Mensaje", "La mascota: " + mascota.getNombre() + " se ha actualizado correctamente");
				responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.OK);
			} else{
				responseAsMap.put("Mensaje", "La mascota no se ha podido actualizar en la Base de Datos");
				responseEntity = new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}			
				
		} catch (DataAccessException e) {
			responseAsMap.put("Mensaje", "Error! no se ha podido actualizar la mascota");
			responseAsMap.put("Error", e.getMostSpecificCause());
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

package com.capgemini.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.View;
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
import com.capgemini.services.IClienteService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(value = "/cliente")
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {

	@Autowired
	private IClienteService clienteService;

	@GetMapping
	@Transactional(readOnly = true)
	public ResponseEntity<List<Cliente>> findAll() {

		ResponseEntity<List<Cliente>> responseEntity = null;

		List<Cliente> clientes = null;

		clientes = clienteService.listaClientes();

		if (clientes.size() > 0) {

			responseEntity = new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
		} else {

			responseEntity = new ResponseEntity<List<Cliente>>(HttpStatus.NO_CONTENT);
		}

		return responseEntity;
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> findById(@PathVariable(name = "id") String id) {

		ResponseEntity<Cliente> responseEntity = null;

		Cliente cliente = null;

		cliente = clienteService.getCliente(id);

		if (cliente != null) {
			responseEntity = new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<Cliente>(HttpStatus.NO_CONTENT);
		}
		return responseEntity;
	}
	
	//añadir un Cliente
	@PostMapping
	public ResponseEntity<Map<String,Object>> guardar(@RequestBody Cliente cliente, BindingResult result){

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
			responseAsMap.put("Errores", errores);
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.BAD_REQUEST);

			return responseEntity;
		}

		// BLOQUE TRY CATH 

		try {
			if(cliente != null) {
				clienteService.addCliente(cliente);
				responseAsMap.put("Cliente", cliente);
				responseAsMap.put("Mensaje", "El cliente :" + cliente.getNombre() + 
						" se ha guardado correctamente");
				responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);
			} else {
				responseAsMap.put("Mensaje", "El cliente no se ha podido guardar en la base de datos");
				responseEntity = new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (DataAccessException e) {
			// TODO: handle exception
			responseAsMap.put("Mensaje", "Error! no se ha podido guadar el cliente");
			responseAsMap.put("Error", e.getMostSpecificCause());
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;

	}
	
	// ACTUALIZAR CLIENTE
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> actualizar(@PathVariable(name= "id") String id, @RequestBody Cliente cliente, BindingResult result){
		
		Map<String, Object> responseAsMap = new HashMap<>();		
		ResponseEntity<Map<String, Object>> responseEntity = null;		
		List<String> errores = null; 
		
		if (result.hasErrors()) { 									
			errores = new ArrayList<>(); 			
			for( ObjectError error : result.getAllErrors()) { 
				errores.add(error.getDefaultMessage());
			}			
			
			responseAsMap.put("Hay un error :-(", errores);			
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
			
			return responseEntity;
		}
		
		try {
			
			Cliente clienteDB = clienteService.getCliente(id);
			
			if(clienteDB != null) {
				cliente.setId(Long.parseLong(id));
				clienteService.addCliente(cliente);
				responseAsMap.put("Cliente", cliente);				
				responseAsMap.put("Mensaje", "El cliente: " + cliente.getNombre() + " se ha actualizado correctamente");
				responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.OK);
			} else{
				responseAsMap.put("Mensaje", "El cliente no se ha podido actualizar en la Base de Datos");
				responseEntity = new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}			
		} catch (DataAccessException e) {
			responseAsMap.put("Mensaje", "Error! no se ha podido actualizar el cliente");
			responseAsMap.put("Error", e.getMostSpecificCause());
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return responseEntity;
		
	}
	
	//Borrado dando el ID
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Cliente> eliminar(@PathVariable(name = "id") String id){

		ResponseEntity<Cliente> responseEntity = null;		

		try {
			Cliente clienteDB = clienteService.getCliente(id);
			
			if(clienteDB != null) {
			clienteService.deleteCliente(clienteDB);
				responseEntity = new ResponseEntity<Cliente>(HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<Cliente>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
	
		} catch (DataAccessException e) {
			// TODO: handle exception
			responseEntity = new ResponseEntity<Cliente>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;

	}
	
}

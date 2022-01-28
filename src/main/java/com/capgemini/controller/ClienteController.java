package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.entities.Cliente;
import com.capgemini.entities.Mascota;
import com.capgemini.services.IClienteService;

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
}

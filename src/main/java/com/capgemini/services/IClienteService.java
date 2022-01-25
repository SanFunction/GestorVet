package com.capgemini.services;

import java.util.List;

import com.capgemini.entities.Cliente;

public interface IClienteService {

	public List<Cliente> listaClientes();
	public Cliente getCliente(String id);
	public void addCliente(Cliente clienteNuevo);
	public void deleteCliente(Cliente clienteBorrar);
}

package com.capgemini.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.daos.IClienteDao;
import com.capgemini.entities.Cliente;
import com.capgemini.services.IClienteService;

@Service
public class ClienteServiceImpl  implements IClienteService{

	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	public List<Cliente> listaClientes() {
		return clienteDao.findAll();
	}

	@Override
	public Cliente getCliente(String id) {
		return clienteDao.findById(Long.parseLong(id)).get();
	}

	@Override
	public Cliente addCliente(Cliente clienteNuevo) {
		return clienteDao.save(clienteNuevo);
	}

	@Override
	public void deleteCliente(Cliente clienteBorrar) {
		clienteDao.delete(clienteBorrar);
	}

}

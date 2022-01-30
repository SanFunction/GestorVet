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
		// TODO Auto-generated method stub
		return clienteDao.findAll();
	}

	@Override
	public Cliente getCliente(String id) {
		// TODO Auto-generated method stub
		return clienteDao.findById(Long.parseLong(id)).get();
	}

	@Override
	public Cliente addCliente(Cliente clienteNuevo) {
		// TODO Auto-generated method stub
		return clienteDao.save(clienteNuevo);
	}

	@Override
	public void deleteCliente(Cliente clienteBorrar) {
		// TODO Auto-generated method stub
		clienteDao.delete(clienteBorrar);
	}

}

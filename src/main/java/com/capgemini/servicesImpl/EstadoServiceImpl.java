package com.capgemini.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.daos.IEstadoDao;
import com.capgemini.entities.Estado;
import com.capgemini.services.IEstadoService;

@Service
public class EstadoServiceImpl implements IEstadoService {

	@Autowired
	private IEstadoDao estadoDAO;
	
	@Override
	public List<Estado> listaEstado() {
		
		return estadoDAO.findAll();
	}

	@Override
	public Estado getEstado(String id) {
		
		return estadoDAO.getById(Long.parseLong(id));
	}

	@Override
	public void addEstado(Estado estadoNuevo) {
		estadoDAO.save(estadoNuevo);

	}

	@Override
	public void deleteEstado(Estado estadoBorrar) {
		estadoDAO.delete(estadoBorrar);

	}

}

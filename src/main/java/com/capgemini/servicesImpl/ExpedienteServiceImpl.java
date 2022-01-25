package com.capgemini.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.daos.IExpedienteDao;
import com.capgemini.entities.Expediente;
import com.capgemini.services.IExpedienteService;

@Service
public class ExpedienteServiceImpl implements IExpedienteService {

	@Autowired
	private IExpedienteDao expedienteDAO;
	
	@Override
	public List<Expediente> listaExpediente() {
		
		return expedienteDAO.findAll();
	}

	@Override
	public Expediente getExpediente(String id) {
		
		return expedienteDAO.getById(Long.parseLong(id));
	}

	@Override
	public void addExpediente(Expediente expedienteNuevo) {
		expedienteDAO.save(expedienteNuevo);

	}

	@Override
	public void deleteExpediente(Expediente expedienteBorrar) {
		expedienteDAO.delete(expedienteBorrar);
		
	}



}

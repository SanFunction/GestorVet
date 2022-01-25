package com.capgemini.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.daos.ITratamientoDao;
import com.capgemini.entities.Tratamiento;
import com.capgemini.services.ITratamientoService;

@Service
public class TratamientoServiceImpl implements ITratamientoService {

	@Autowired
	ITratamientoDao tratamientoDAO;
	
	@Override
	public List<Tratamiento> listaTratamiento() {
		
		return tratamientoDAO.findAll();
	}

	@Override
	public Tratamiento getTratamiento(String id) {
		
		return tratamientoDAO.getById(Long.parseLong(id));
	}

	@Override
	public void addTratamiento(Tratamiento tratamientoNuevo) {
		tratamientoDAO.save(tratamientoNuevo);

	}

	@Override
	public void deleteTratamiento(Tratamiento tratamientoBorrar) {
		tratamientoDAO.delete(tratamientoBorrar);

	}

}

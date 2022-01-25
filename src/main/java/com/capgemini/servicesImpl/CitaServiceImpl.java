package com.capgemini.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.daos.ICitaDao;
import com.capgemini.entities.Cita;
import com.capgemini.services.ICitaService;

@Service
public class CitaServiceImpl implements ICitaService{

	@Autowired
	private ICitaDao citaDao;
	
	@Override
	public List<Cita> listaCitas() {
		// TODO Auto-generated method stub
		return citaDao.findAll();
	}

	@Override
	public Cita getCita(String id) {
		// TODO Auto-generated method stub
		return citaDao.getById(Long.parseLong(id));
	}

	@Override
	public void addCita(Cita citaNueva) {
		// TODO Auto-generated method stub
		citaDao.save(citaNueva);
	}

	@Override
	public void deleteCita(Cita citaBorrar) {
		// TODO Auto-generated method stub
		citaDao.delete(citaBorrar);
	}

}

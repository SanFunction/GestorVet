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
		return citaDao.findAll();
	}

	@Override
	public Cita getCita(String id) {
		return citaDao.findById(Long.parseLong(id)).get();
	}

	@Override
	public Cita addCita(Cita citaNueva) {
		return citaDao.save(citaNueva);
	}

	@Override
	public void deleteCita(Cita citaBorrar) {
		citaDao.delete(citaBorrar);
	}


}

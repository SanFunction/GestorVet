package com.capgemini.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.daos.IEspecieDao;
import com.capgemini.entities.Especie;
import com.capgemini.services.IEspecieService;

@Service
public class EspecieServiceImpl implements IEspecieService{

	@Autowired
	private IEspecieDao especieDao;
	
	@Override
	public List<Especie> listaEspecies() {
		return especieDao.findAll();
	}

	@Override
	public Especie getEspecie(String id) {
		return especieDao.getById(Long.parseLong(id));
	}

	@Override
	public Especie addEspecie(Especie especieNueva) {
		return especieDao.save(especieNueva);
	}

	@Override
	public void deleteEspecie(Especie especieBorrar) {
		especieDao.delete(especieBorrar);
	}

}

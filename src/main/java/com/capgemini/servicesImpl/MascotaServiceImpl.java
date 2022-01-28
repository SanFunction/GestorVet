package com.capgemini.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.daos.IMascotaDao;
import com.capgemini.entities.Mascota;
import com.capgemini.services.IMascotaService;

@Service
public class MascotaServiceImpl implements IMascotaService{

	@Autowired
	private IMascotaDao mascotaDao;
	
	@Override
	public List<Mascota> listaMascota() {
		// TODO Auto-generated method stub
		return mascotaDao.findAll();
	}

	@Override
	public Mascota getMascota(String id) {
		// TODO Auto-generated method stub
		return mascotaDao.findById(Long.parseLong(id)).get();
	}

	@Override
	public void addMascota(Mascota mascotaNueva) {
		// TODO Auto-generated method stub
		mascotaDao.save(mascotaNueva);
	}

	@Override
	public void deleteMascota(Mascota mascotaBorrar) {
		// TODO Auto-generated method stub
		mascotaDao.delete(mascotaBorrar);
	}

}

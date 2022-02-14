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
		return mascotaDao.findAll();
	}

	@Override
	public Mascota getMascota(String id) {
		return mascotaDao.findById(Long.parseLong(id)).get();
	}

	@Override
	public Mascota addMascota(Mascota mascotaNueva) {
		return mascotaDao.save(mascotaNueva);
	}

	@Override
	public void deleteMascota(Mascota mascotaBorrar) {
		mascotaDao.delete(mascotaBorrar);
	}

}

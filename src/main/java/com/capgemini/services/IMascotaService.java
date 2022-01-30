package com.capgemini.services;

import java.util.List;

import com.capgemini.entities.Mascota;

public interface IMascotaService {

	public List<Mascota> listaMascota();
	public Mascota getMascota(String id);
	public Mascota addMascota(Mascota mascotaNueva);
	public void deleteMascota(Mascota mascotaBorrar);
}

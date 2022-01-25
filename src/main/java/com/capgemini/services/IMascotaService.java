package com.capgemini.services;

import java.util.List;

import com.capgemini.entities.Mascota;

public interface IMascotaService {

	public List<Mascota> listaEspecie();
	public Mascota getMascota(String id);
	public void addMascota(Mascota mascotaNueva);
	public void deleteMascota(Mascota mascotaBorrar);
}

package com.capgemini.services;

import java.util.List;

import com.capgemini.entities.Cita;

public interface ICitaService {

	public List<Cita> listaCitas();
	public Cita getCita(String id);
	public void addCita(Cita citaNueva);
	public void deleteCita(Cita citaBorrar);
}

package com.capgemini.services;

import java.util.List;

import com.capgemini.entities.Cita;
import com.capgemini.entities.Cliente;

public interface ICitaService {

	public List<Cita> listaCitas();
	public Cita getCita(String id);
	public Cita addCita(Cita citaNueva);
	public void deleteCita(Cita citaBorrar);
}

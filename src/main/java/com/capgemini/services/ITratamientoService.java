package com.capgemini.services;

import java.util.List;

import com.capgemini.entities.Tratamiento;


public interface ITratamientoService {
	public List<Tratamiento> listaTratamiento();
	public Tratamiento getTratamiento(String id);
	public void addTratamiento(Tratamiento tratamientoNuevo);
	public void deleteTratamiento (Tratamiento tratamientoBorrar);
}

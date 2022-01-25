package com.capgemini.services;

import java.util.List;

import com.capgemini.entities.Estado;


public interface IEstadoService {
	public List<Estado> listaEstado();
	public Estado getEstado(String id);
	public void addEstado(Estado estadoNuevo);
	public void deleteEstado (Estado estadoBorrar);
}

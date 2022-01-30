package com.capgemini.services;

import java.util.List;

import com.capgemini.entities.Especie;

public interface IEspecieService {

	public List<Especie> listaEspecies();
	public Especie getEspecie(String id);
	public Especie addEspecie(Especie especieNueva);
	public void deleteEspecie(Especie especieBorrar);
}

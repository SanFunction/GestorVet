package com.capgemini.services;

import java.util.List;

import com.capgemini.entities.Veterinario;


public interface IVeterinarioService {
	public List<Veterinario> listaVeterinario();
	public Veterinario getVeterinario(String id);
	public void addVeterinario(Veterinario veterinarioNuevo);
	public void deleteVeterinario (Veterinario veterinarioBorrar);
}

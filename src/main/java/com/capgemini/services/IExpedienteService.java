package com.capgemini.services;

import java.util.List;

import com.capgemini.entities.Expediente;



public interface IExpedienteService {
	public List<Expediente> listaExpediente();
	public Expediente getExpediente(String id);
	public void addExpediente(Expediente expedienteNuevo);
	public void deleteExpediente (Expediente expedienteBorrar);
}

package com.capgemini.services;

import java.util.List;

import com.capgemini.entities.Diagnostico;


public interface IDiagnosticoService {
	public List<Diagnostico> listaDiagnostico();
	public Diagnostico getDiagnostico(String id);
	public void addDiagnostico(Diagnostico diagnosticoNuevo);
	public void deleteDiagnostico (Diagnostico diagnosticoBorrar);
}

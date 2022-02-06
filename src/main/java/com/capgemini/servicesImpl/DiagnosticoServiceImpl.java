package com.capgemini.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.daos.IDiagnosticoDao;
import com.capgemini.entities.Diagnostico;
import com.capgemini.services.IDiagnosticoService;

@Service
public class DiagnosticoServiceImpl implements IDiagnosticoService {

	@Autowired
	private IDiagnosticoDao diagnosticoDAO;

	@Override
	public List<Diagnostico> listaDiagnostico() {

		return diagnosticoDAO.findAll();
	}

	@Override
	public Diagnostico getDiagnostico(String id) {

		return diagnosticoDAO.findById(Long.parseLong(id)).get();
	}

	@Override
	public void addDiagnostico(Diagnostico diagnosticoNuevo) {

		diagnosticoDAO.save(diagnosticoNuevo);
	}

	@Override
	public void deleteDiagnostico(Diagnostico diagnosticoBorrar) {

		diagnosticoDAO.delete(diagnosticoBorrar);
	}

}

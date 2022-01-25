package com.capgemini.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.daos.IVeterinarioDao;
import com.capgemini.entities.Veterinario;
import com.capgemini.services.IVeterinarioService;

@Service
public class VeterinarioServiceImpl implements IVeterinarioService {

	@Autowired
	private IVeterinarioDao veterinarioDAO;
	
	@Override
	public List<Veterinario> listaVeterinario() {
		
		return veterinarioDAO.findAll();
	}

	@Override
	public Veterinario getVeterinario(String id) {
		
		return veterinarioDAO.getById(Long.parseLong(id));
	}

	@Override
	public void addVeterinario(Veterinario veterinarioNuevo) {
		
		veterinarioDAO.save(veterinarioNuevo);

	}

	@Override
	public void deleteVeterinario(Veterinario veterinarioBorrar) {
		
		veterinarioDAO.delete(veterinarioBorrar);

	}

}

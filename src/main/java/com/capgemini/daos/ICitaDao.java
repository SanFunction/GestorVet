package com.capgemini.daos;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import com.capgemini.entities.Cita;

@Repository
public interface ICitaDao extends JpaRepository<Cita, Long>  {
	
}

package com.capgemini.daos;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import com.capgemini.entities.Cita;

@Repository
public interface ICitaDao extends JpaRepository<Cita, Long>  {
	
//	@Query(value = "select p from Cliente p left join fetch p.cita where p.id = :id") //Los dos puntos es como en las consultas SQL el símbolo de " ? "
//	public Cita finById(long id);
	
}

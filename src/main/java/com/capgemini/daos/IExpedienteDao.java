package com.capgemini.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.Expediente;

@Repository
public interface ExpedienteDao extends JpaRepository<Expediente, Long>  {

}
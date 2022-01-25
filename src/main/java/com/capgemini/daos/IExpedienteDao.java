package com.capgemini.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.Expediente;

@Repository
public interface IExpedienteDao extends JpaRepository<Expediente, Long>  {

}
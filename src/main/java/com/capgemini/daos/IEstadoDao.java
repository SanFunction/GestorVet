package com.capgemini.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.Estado;

@Repository
public interface IEstadoDao extends JpaRepository<Estado, Long>  {

}

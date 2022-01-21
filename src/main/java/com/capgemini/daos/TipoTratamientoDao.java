package com.capgemini.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.TipoTratamiento;

@Repository
public interface TipoTratamientoDao extends JpaRepository<TipoTratamiento, Long>  {

}
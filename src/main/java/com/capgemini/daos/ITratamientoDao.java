package com.capgemini.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.Tratamiento;

@Repository
public interface ITratamientoDao extends JpaRepository<Tratamiento, Long>  {

}

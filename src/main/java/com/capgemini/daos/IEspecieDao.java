package com.capgemini.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.Especie;

@Repository
public interface IEspecieDao extends JpaRepository<Especie, Long>  {

}
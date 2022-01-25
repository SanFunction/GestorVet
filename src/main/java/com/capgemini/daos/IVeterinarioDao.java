package com.capgemini.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.Veterinario;

@Repository
public interface IVeterinarioDao extends JpaRepository<Veterinario, Long>  {

}
package com.capgemini.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.Cliente;

@Repository
public interface IClienteDao extends JpaRepository<Cliente, Long>  {

}
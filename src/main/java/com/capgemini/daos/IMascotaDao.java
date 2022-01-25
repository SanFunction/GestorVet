package com.capgemini.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.Mascota;

@Repository
public interface IMascotaDao extends JpaRepository<Mascota, Long>  {

}
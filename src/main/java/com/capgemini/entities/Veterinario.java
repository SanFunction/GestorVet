package com.capgemini.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="veterinario")
public class Veterinario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	//@NotEmpty(message = "El campo no puede estar vacio")
	private String nombre;
	
	@NotNull
	//@NotEmpty(message = "El campo no puede estar vacio")
	private String apellidos;
	
	@OneToMany(fetch =FetchType.LAZY,cascade = CascadeType.ALL,
			mappedBy ="veterinario")
    private List<Diagnostico> diagnostico;

	
}

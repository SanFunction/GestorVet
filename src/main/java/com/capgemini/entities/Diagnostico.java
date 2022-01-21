package com.capgemini.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@Table(name="Diagnostico")
public class Diagnostico implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NotEmpty(message = "El campo no puede estar vacio")
	private String enfermedad;
	
	@ManyToOne
	private Veterinario veterinario;
	
	@ManyToOne
	private Expediente expediente;

	@OneToMany(mappedBy= "idDiagnostico")
	private List<Tratamiento> tratamientos;

	
	
	
}

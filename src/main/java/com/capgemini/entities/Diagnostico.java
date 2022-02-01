package com.capgemini.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "diagnostico")
public class Diagnostico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotEmpty(message = "El campo no puede estar vacio")
	private String enfermedad;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	//@JsonManagedReference
	@JsonBackReference(value = "masco-diag")
	private Mascota mascota;

	private String tratamiento;

	private String veterinario;
	
	private Date fecha;
	
	private String estado;
}

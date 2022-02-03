package com.capgemini.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="mascota")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Mascota implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NotEmpty(message = "El campo nombre no puede estar vacio")
	@Size(min = 4, max = 100, message = "El nombre entre 4 y 100 caracteres")
	private String nombre;
	
	@Size(max = 255, message = "No puede exceder 255 caracteres")
	private String raza;
	
	@NotNull
	@NotEmpty(message = "Debe indicar un color")
	@Size(max = 255, message = "No puede exceder 255 caracteres")
	private String color;
	
	@NotNull
	@NotEmpty(message = "Se debe indicar edad")
	private String edad;
	
	@NotNull
	@NotEmpty(message = "Debe indicar un peso")
	private String peso;
	
	private String especie;
	
	private String foto;
	
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "mascota")
	private List<Diagnostico> diagnostico;
	
	
	@NotNull
	@NotEmpty(message = "Debe seleccionar cliente")
	@ManyToOne
	private Cliente cliente;
	

}

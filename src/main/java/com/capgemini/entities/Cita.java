package com.capgemini.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



	@Entity
	@Table(name="cita")
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
public class Cita implements Serializable{


		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		@NotNull
		@NotEmpty(message = "El campo nombre no puede estar vacio")
		private Date fecha;
		
		@NotNull
		@NotEmpty(message = "El campo  no puede estar vacio")
		@Size(max = 255, message = "No puede exceder 255 caracteres")
		private String anotaciones;
		
		@NotNull
		@NotEmpty(message = "Debe seleccionar cliente")
		@ManyToOne(fetch =FetchType.LAZY,
		cascade = CascadeType.PERSIST)
		private Cliente cliente;	
	
		
		
	
	
}

package com.examtest.Model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "ciudadanos")	//Entidad mapeada a la tabla ciudadanos
public class ciudadano {
	
	//Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	// Estrategia de generacion de key id autoincremental
	private Long id;
	
	@NotBlank(message = "El nombre es obligatorio")
	private String nombre;
	
	@NotNull(message = "La fecha de nacimiento es obligatoria")
	@Past(message = "La fecha de nacimiento debe ser anterior al dia de hoy")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate fecha_nacimiento;
	
	@NotBlank(message = "El curp es obligatorio")
	@Size(min = 18, max = 18, message = "El CURP debe tener 18 caracteres")
	private String curp;
	
	@NotBlank(message = "El número de celular es obligatorio")
    @Pattern(regexp = "^\\d{10}", message = "El número de celular deben ser 10 dígitos numéricos")
	//regular expression, "\d" -> any digit (0-9), "{10}" -> quantify 10 elements, "\\" -> two backslashes represent single backlash in regexp
	private String celular;
	
	//Uno a Muchos
	@OneToMany(mappedBy = "ciudadano", cascade = CascadeType.ALL)
	//mappedBy -> relación bidireccional, el lado "muchos" es el propietario de la relación, utiliza la foreign key "ciudadano" en la clase domicilio
	//CascadeType.ALL -> las operaciones en persistencia  realizadas a ciudadano se propagaran automaticamente a sus domicilios asociados
	@JsonManagedReference	// Se usa en el lado "uno" para indicar que es la referencia manejada
	private List<domicilio> domicilios;
	
	//Constructor
	public ciudadano(Long id, String nombre, LocalDate fecha_nacimiento, String curp, String celular, List<domicilio> domicilios) {
		this.id = id;
		this.nombre = nombre;
		this.fecha_nacimiento = fecha_nacimiento;
		this.curp = curp;
		this.celular = celular;
		this.domicilios = domicilios;
	}

	//Empty constructor
	public ciudadano() {
		
	}

	//Getters & Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public LocalDate getFecha_nacimiento() {
		return fecha_nacimiento;
	}
	public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public List<domicilio> getDomicilios() {
		return domicilios;
	}
	public void setDomicilios(List<domicilio> domicilios) {
		this.domicilios = domicilios;
	}

	//toString
	@Override
	public String toString() {
		return "ciudadano [id=" + id + ", nombre=" + nombre + ", fechaNacimiento=" + fecha_nacimiento + ", curp=" + curp
				+ ", celular=" + celular + "]";
	}

}

package com.examtest.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "ciudadanos")	//Ligarlo a la tabla ciudadanos
public class ciudadano {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	//Attributes
	private Long id;
	@NotBlank(message = "El nombre es obligatorio")
	private String nombre;
	@NotNull(message = "La fecha de nacimiento es obligatoria")
	@Past(message = "La fecha de nacimiento debe ser anterior al dia de hoy")
	private LocalDate fecha_nacimiento;
	@NotBlank(message = "El curp es obligatorio")
	@Size(min = 18, max = 18, message = "El CURP debe tener 18 caracteres")
	private String curp;
	@NotBlank(message = "El número de celular es obligatorio")
    @Pattern(regexp = "^\\d{10}", message = "El número de celular deben ser 10 dígitos numéricos")
	//regular expression, "\d" -> any digit (0-9), "{10}" -> quantify 10 elements, "\\" -> two backslashes represent single backlash in regexp
	private String celular;
	
	//Constructor
	public ciudadano(Long id, String nombre, LocalDate fecha_nacimiento, String curp, String celular) {
		this.id = id;
		this.nombre = nombre;
		this.fecha_nacimiento = fecha_nacimiento;
		this.curp = curp;
		this.celular = celular;
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
	public LocalDate getFechaNacimiento() {
		return fecha_nacimiento;
	}
	public void setFechaNacimiento(LocalDate fecha_nacimiento) {
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

	//toString
	@Override
	public String toString() {
		return "ciudadano [id=" + id + ", nombre=" + nombre + ", fechaNacimiento=" + fecha_nacimiento + ", curp=" + curp
				+ ", celular=" + celular + "]";
	}

}

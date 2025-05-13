package com.examtest.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class ciudadano {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	//Attributes
	private Long id;
	private String nombre, direccion;
	
	//Constructor
	public ciudadano(Long id, String nombre, String direccion) {
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
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
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	//toString
	@Override
	public String toString() {
		return "ciudadano [id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + "]";
	}
	
}

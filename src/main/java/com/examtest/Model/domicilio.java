package com.examtest.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "domicilios") //Entidad mapeada a la tabla domicilios
public class domicilio {

	// Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	// Estrategia de generacion de key id autoincremental
    private Long id;
	
	private String calle, colonia, ciudad, estado, cp;
	private int numero;

	//Muchos a uno
	@ManyToOne
	@JoinColumn(name = "ciudadano_id", nullable = false)
	//JoinColumn -> Se usa junto con ManyToOne para especificar la foreign key que se utiliza para la relación
	//name -> especifica el nombre de la columna de la foreign key
	//nullable = false -> asegura que cada domicilio debe estar asociado a un ciudadano
	@JsonBackReference	// Se usa en el lado "muchos" para indicar que es quien maneja la referencia, y evitar bucles infinitos
	private ciudadano ciudadano;
	
	//Constructor
	public domicilio(Long id, ciudadano ciudadano, String calle, String colonia, String ciudad, String estado,
			String cp, int numero) {
		this.id = id;
		this.ciudadano = ciudadano;
		this.calle = calle;
		this.colonia = colonia;
		this.ciudad = ciudad;
		this.estado = estado;
		this.cp = cp;
		this.numero = numero;
	}

	//Empty constructor
	public domicilio() {
	
	}

	//Getters & Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ciudadano getCiudadano() {
		return ciudadano;
	}
	public void setCiudadano(ciudadano ciudadano) {
		this.ciudadano = ciudadano;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}

	//toString
	@Override
	public String toString() {
		return "domicilio [id=" + id + ", ciudadano=" + ciudadano + ", calle=" + calle + ", colonia=" + colonia
				+ ", ciudad=" + ciudad + ", estado=" + estado + ", cp=" + cp + ", numero=" + numero + "]";
	}
		
}

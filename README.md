● Descripción del ambiente de desarrollo (IDE, SGBD, herramientas, versiones, etc.)
  IDE: Spring Tools Suite 4.30
  DB: PostgreSQL
  Herramientas: Java 21.0.7, Apache Maven 4.0.0, Spring Framework 3.4.5, Git, GitHub, Hibernate, Postman, Docker, TablePlus
  
● Script de creación de las tablas de base de datos.

CREATE TABLE ciudadanos (
	id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	nombre TEXT NOT NULL,
	fecha_nacimiento TEXT NOT NULL,
	curp TEXT NOT NULL UNIQUE,
	celular TEXT NOT NULL
);

CREATE TABLE domicilios (
	id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	ciudadano_id BIGINT NOT NULL,
	calle TEXT NOT NULL,
	numero INT NOT NULL,
	colonia TEXT NOT NULL,
	ciudad TEXT NOT NULL,
	estado TEXT NOT NULL,
	cp TEXT NOT NULL,
	FOREIGN KEY (ciudadano_id) REFERENCES ciudadanos (id) ON DELETE CASCADE
);

● Video corto mostrando la aplicación en funcionamiento desde Postman o Insomnia, con una breve explicación del código y su estructura MVC.
https://drive.google.com/file/d/1KxPmfNLNjxMlGfy61qTtiLXK5ypdXjLH/view?usp=sharing

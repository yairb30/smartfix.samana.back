package com.smartfixsamana.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lista_repuestos")
public class ListaRepuesto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private int id;
	private String nombre;
	private String detalles;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

}

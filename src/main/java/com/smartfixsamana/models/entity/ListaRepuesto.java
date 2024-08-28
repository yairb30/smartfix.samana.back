package com.smartfixsamana.models.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "lista_repuestos")
public class ListaRepuesto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	private String nombre;
	private String detalles;

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

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	private static final long serialVersionUID = 1L;

}

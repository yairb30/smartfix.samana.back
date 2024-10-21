package com.smartfixsamana.models.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "celulares")
public class Celular implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private long id;

	@NotBlank
	private String marca;

	@NotBlank
	private String modelo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	private static final long serialVersionUID = 1L;

}

package com.smartfixsamana.models.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "reparaciones")
public class Reparacion {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private long id;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "celular_id")
	private Celular celular;

	private String estado;
	private LocalDate fechaEntrega;
	private LocalDate fechaEstimadaEntrega;

	public long getId() {
		return id;
	}

	public void setId(long idReparacion) {
		this.id = idReparacion;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Celular getCelular() {
		return celular;
	}

	public void setCelular(Celular celular) {
		this.celular = celular;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDate getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(LocalDate fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public LocalDate getFechaEstimadaEntrega() {
		return fechaEstimadaEntrega;
	}

	public void setFechaEstimadaEntrega(LocalDate fechaEstimadaEntrega) {
		this.fechaEstimadaEntrega = fechaEstimadaEntrega;
	}

}

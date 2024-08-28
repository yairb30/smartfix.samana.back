package com.smartfixsamana.models.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "reparaciones")
public class Reparacion implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private long id;

	@ManyToOne()
	@JoinColumn(name = "cliente_id")
	private Cliente clienteId;

	@ManyToOne()
	@JoinColumn(name = "celular_id")
	private Celular celularId;

	private String problema;

	private String estado;

	@Column(name = "fecha_ingreso")
	private LocalDate fechaIngreso;

	@Column(name = "fecha_estimada_entrega")
	private LocalDate fechaEstimadaEntrega;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Cliente getClienteId() {
		return clienteId;
	}

	public void setClienteId(Cliente cliente) {
		this.clienteId = cliente;
	}

	public Celular getCelularId() {
		return celularId;
	}

	public void setCelularId(Celular celular) {
		this.celularId = celular;
	}

	public String getProblema() {
		return problema;
	}

	public void setProblema(String problema) {
		this.problema = problema;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDate fechaEntrega) {
		this.fechaIngreso = fechaEntrega;
	}

	public LocalDate getFechaEstimadaEntrega() {
		return fechaEstimadaEntrega;
	}

	public void setFechaEstimadaEntrega(LocalDate fechaEstimadaEntrega) {
		this.fechaEstimadaEntrega = fechaEstimadaEntrega;
	}
	private static final long serialVersionUID = 1L;

}

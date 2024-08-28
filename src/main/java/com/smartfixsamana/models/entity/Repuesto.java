package com.smartfixsamana.models.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "repuestos")
public class Repuesto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private long id;

	@ManyToOne()
	@JoinColumn(name = "lista_repuesto_id")
	private ListaRepuesto elegirRepuesto;

	@ManyToOne
	@JoinColumn(name = "celular_id")
	private Celular elegirCelular;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Celular getElegirCelular() {
		return elegirCelular;
	}

	public void setElegirCelular(Celular elegirCelular) {
		this.elegirCelular = elegirCelular;
	}

	public ListaRepuesto getElegirRepuesto() {
		return elegirRepuesto;
	}

	public void setElegirRepuesto(ListaRepuesto listaRepuesto) {
		this.elegirRepuesto = listaRepuesto;
	}

	private static final long serialVersionUID = 1L;

}

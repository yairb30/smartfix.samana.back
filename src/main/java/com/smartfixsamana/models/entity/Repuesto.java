package com.smartfixsamana.models.entity;

import jakarta.persistence.*;

@Entity
public class Repuesto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private long idRepusto;

	@ManyToOne
	@JoinColumn(name = "idListaRepuesto")
	private ListaRepuesto listaRepuesto;

	@ManyToOne
	@JoinColumn(name = "idCelular")
	private Celular celular;

	public long getIdRepusto() {
		return idRepusto;
	}

	public void setIdRepusto(long idRepusto) {
		this.idRepusto = idRepusto;
	}

	public Celular getCelular() {
		return celular;
	}

	public void setCelular(Celular celular) {
		this.celular = celular;
	}

	public ListaRepuesto getListaRepuesto() {
		return listaRepuesto;
	}

	public void setListaRepuesto(ListaRepuesto listaRepuesto) {
		this.listaRepuesto = listaRepuesto;
	}

}

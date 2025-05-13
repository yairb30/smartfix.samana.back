package com.smartfixsamana.models.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "parts")
public class Part implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private long id;

	@ManyToOne()
	@JoinColumn(name = "part_catalog_id")
	@NotNull
	private PartCatalog partCatalog;

	@ManyToOne
	@JoinColumn(name = "phone_id")
	@NotNull
	private Phone phone;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public PartCatalog getPartCatalog() {
		return partCatalog;
	}

	public void setPartCatalog(PartCatalog partCatalog) {
		this.partCatalog = partCatalog;
	}

	private static final long serialVersionUID = 1L;

}

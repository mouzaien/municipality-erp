package com.bkeryah.entities.licences;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "BLD_LIC_BUILDING_TYPES")
public class BldLicBuildingTypes {
	@Id
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NAME")
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
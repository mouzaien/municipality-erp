package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LIC_HLT_STATIONS")
public class HealthLicenceCenter {

	@Id
	@Column(name = "ST_ID")
	private int id;
	@Column(name = "ST_NAME", nullable = true)
	private String name;
	@Column(name = "ST_TYPE", nullable = true)
	private String type;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

	
	
}
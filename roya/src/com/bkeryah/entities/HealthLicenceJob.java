package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LIC_HELTH_JOBS")
public class HealthLicenceJob {

	@Id
	@Column(name = "ID")
	private int id;
	@Column(name = "NAME", nullable = true)
	private String name;
	@Column(name = "CLASSIFICATION", nullable = true)
	private Integer classification;
	
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
	public Integer getClassification() {
		return classification;
	}
	public void setClassification(Integer classification) {
		this.classification = classification;
	}
	
	
}

package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class EstablishmentId implements Serializable {
	@Column(name = "SCHOOL_OR_COLLEGE")
	private String school;
	@Column(name = "LOCATION_NAME")
	private String location;

	public EstablishmentId(String school, String location) {

		this.school = school;
		this.location = location;

	}

	public EstablishmentId() {
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}

package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "XXX_HR_CITY")
public class HRCity implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private CityID id;
	@Column(name = "CITY_EN_NAME")
	private String cityEnName;
	
	@Column(name = "CITY_AR_NAME")
	private String cityArName;

	public CityID getId() {
		return id;
	}

	public void setId(CityID id) {
		this.id = id;
	}

	public String getCityEnName() {
		return cityEnName;
	}

	public void setCityEnName(String cityEnName) {
		this.cityEnName = cityEnName;
	}

	public String getCityArName() {
		return cityArName;
	}

	public void setCityArName(String cityArName) {
		this.cityArName = cityArName;
	}

}

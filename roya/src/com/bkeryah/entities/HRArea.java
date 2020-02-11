package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "XXX_HR_AREA")
public class HRArea  {
	
	@Id
	@Column(name = "COUNTRY_CODE")
	private String countryCode;
	
	@Column(name = "AREA_CODE")
	private String areaCode;
	
	@Column(name = "AREA_EN_NAME")
	private String areaEnName;
	
	@Column(name = "AREA_AR_NAME")
	private String areaArName;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaEnName() {
		return areaEnName;
	}

	public void setAreaEnName(String areaEnName) {
		this.areaEnName = areaEnName;
	}

	public String getAreaArName() {
		return areaArName;
	}

	public void setAreaArName(String areaArName) {
		this.areaArName = areaArName;
	}

	
}

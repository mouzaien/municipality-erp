package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class CityID implements Serializable {
	
	@Column(name = "COUNTRY_CODE")
	private String countryCode;
	
	@Column(name = "AREA_CODE")
	private String areaCode;
	
	@Column(name = "CITY_CODE")
	private String cityCode;
	
	public CityID(String countryCode,String areaCode,String cityCode) {
		
		this.countryCode=countryCode;
		this.areaCode=areaCode;
		this.cityCode=cityCode;
	}
	public CityID() {}

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

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

}

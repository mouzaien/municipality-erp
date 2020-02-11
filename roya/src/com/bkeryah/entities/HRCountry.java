package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "XXX_HR_COUNTRY")
public class HRCountry  {
	
	@Id
	@Column(name = "COUNTRY_CODE")
	private String countryCode;
	@Column(name = "COUNTRY_EN_NAME")
	private String countryEnName;
	@Column(name = "COUNTRY_AR_NAME")
	private String countryArName;
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryEnName() {
		return countryEnName;
	}
	public void setCountryEnName(String countryEnName) {
		this.countryEnName = countryEnName;
	}
	public String getCountryArName() {
		return countryArName;
	}
	public void setCountryArName(String countryArName) {
		this.countryArName = countryArName;
	}

}

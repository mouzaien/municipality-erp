package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "XXX_HR_ESTABLISHMENTS")
public class Establishment {
	@Id
	@Column(name="id")
	private Integer id;
	@Column(name = "SCHOOL_OR_COLLEGE")
	private String school;
	@Column(name = "LOCATION_NAME")
	private String location;
	@Column(name = "COUNTRY")
	private String country;
	@Column(name = "AREA")
	private String are;
	@Column(name = "CITY")
	private String city;
	@Column(name = "ADDRESS_LINE1")
	private String address1;
	@Column(name = "ADDRESS_LINE2")
	private String address2;
	@Column(name = "PO_BOX")
	private String pbox;
	@Column(name = "POSTAL_CODE")
	private String postalCode;
	
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAre() {
		return are;
	}
	public void setAre(String are) {
		this.are = are;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getPbox() {
		return pbox;
	}
	public void setPbox(String pbox) {
		this.pbox = pbox;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}

}

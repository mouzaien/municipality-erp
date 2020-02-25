package com.bkeryah.hr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="XXX_HR_TRAIN_CAT_02")
public class HrTrainCat02 {
	@Id
	@Column(name ="LOCATION_AR_NAME")
	private String locArName;	
	
	@Column(name ="LOCATION_EN_NAME")
	private String locEnName;										
	
	@Column(name ="COUNTRY")
	private String country;												
	
	@Column(name ="AREA")
	private String area;									
	
	@Column(name ="CITY")
	private String city;										
	
	@Column(name ="ADDRESS_LINE_1")
	private String addrLine1;												
	
	@Column(name ="ADDRESS_LINE_2")
	private String addrLine2;										
	
	@Column(name ="PO_BOX")
	private String pBox;												
	
	@Column(name ="POSTAL_CODE")
	private String posCode;

	public String getLocArName() {
		return locArName;
	}

	public void setLocArName(String locArName) {
		this.locArName = locArName;
	}

	public String getLocEnName() {
		return locEnName;
	}

	public void setLocEnName(String locEnName) {
		this.locEnName = locEnName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddrLine1() {
		return addrLine1;
	}

	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}

	public String getAddrLine2() {
		return addrLine2;
	}

	public void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
	}

	public String getpBox() {
		return pBox;
	}

	public void setpBox(String pBox) {
		this.pBox = pBox;
	}

	public String getPosCode() {
		return posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}	
	
	
}

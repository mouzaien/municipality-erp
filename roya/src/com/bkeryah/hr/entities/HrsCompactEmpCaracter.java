package com.bkeryah.hr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "HRS_COMPACT_EMP_CARACTER")
public class HrsCompactEmpCaracter {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "POWER_POINTS")
	private String powerPoints;
	@Column(name = "DEV_POINTS")
	private String devPoints;
	@Column(name = "HRS_COMPACT_PERFORMID")
	private Integer hrsCompactPerformid;
	
	
	
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPowerPoints() {
		return powerPoints;
	}
	public void setPowerPoints(String powerPoints) {
		this.powerPoints = powerPoints;
	}
	public String getDevPoints() {
		return devPoints;
	}
	public void setDevPoints(String devPoints) {
		this.devPoints = devPoints;
	}
	public Integer getHrsCompactPerformid() {
		return hrsCompactPerformid;
	}
	public void setHrsCompactPerformid(Integer hrsCompactPerformid) {
		this.hrsCompactPerformid = hrsCompactPerformid;
	}
	
	
	
	

}

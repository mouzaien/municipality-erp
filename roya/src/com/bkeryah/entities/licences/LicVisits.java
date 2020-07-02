package com.bkeryah.entities.licences;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "LIC_VISITS")
public class LicVisits {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;

	@Column(name = "LIC_ID")
	private Integer licId;

	@Column(name = "VISITS_ID")
	private Integer visitId;

	@Column(name = "H_DATE")
	private String hDate;

	@Column(name = "G_DATE")
	private Date gDate;
	
	@Formula("(select u.NAME from LIC_VISITS_TYPES u where u.ID =VISITS_ID)")
	private String visitName;
	
	@Formula("(select u.TYPE from LIC_VISITS_TYPES u where u.ID =VISITS_ID)")
	private String visitType;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLicId() {
		return licId;
	}

	public void setLicId(Integer licId) {
		this.licId = licId;
	}

	public Integer getVisitId() {
		return visitId;
	}

	public void setVisitId(Integer visitId) {
		this.visitId = visitId;
	}

	public String gethDate() {
		return hDate;
	}

	public void sethDate(String hDate) {
		this.hDate = hDate;
	}

	public Date getgDate() {
		return gDate;
	}

	public void setgDate(Date gDate) {
		this.gDate = gDate;
	}

	public String getVisitName() {
		return visitName;
	}

	public void setVisitName(String visitName) {
		this.visitName = visitName;
	}

	public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

	
}

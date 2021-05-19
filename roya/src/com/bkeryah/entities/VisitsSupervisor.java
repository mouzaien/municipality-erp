package com.bkeryah.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "VISITS_SUPERVISOR")
public class VisitsSupervisor {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "VISIT_ID")
	private Integer visitId;
	
	@Column(name = "SUPERVISOR_ID")
	private Integer supervisorId;

	@Column(name = "SECTION_ID")
	private Integer sectionId;
	
	@Formula("(select w.NAME from LIC_SECTION w where w.ID = SECTION_ID)")
	private String sectionName;
	
	@Formula("(select v.H_DATE from LIC_VISITS v where v.ID = VISIT_ID)")
	private String hijDate;
	
	@Formula("(select v.G_DATE from LIC_VISITS v where v.ID = VISIT_ID)")
	private Date gDate;
	
	@Formula("(select v.LIC_ID from LIC_VISITS v where v.ID = VISIT_ID)")
	private Integer licId;
	
	@Formula("(select s.user_id from SUPERVISORS s where s.id = SUPERVISOR_ID)")
	private Integer userId;
	
	@Transient 
	public String licNo;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVisitId() {
		return visitId;
	}

	public void setVisitId(Integer visitId) {
		this.visitId = visitId;
	}

	public Integer getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Integer supervisorId) {
		this.supervisorId = supervisorId;
	}

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getHijDate() {
		return hijDate;
	}

	public void setHijDate(String hijDate) {
		this.hijDate = hijDate;
	}

	public Integer getLicId() {
		return licId;
	}

	public void setLicId(Integer licId) {
		this.licId = licId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getgDate() {
		return gDate;
	}

	public void setgDate(Date gDate) {
		this.gDate = gDate;
	}

	public String getLicNo() {
		return licNo;
	}

	public void setLicNo(String licNo) {
		this.licNo = licNo;
	}

	
}

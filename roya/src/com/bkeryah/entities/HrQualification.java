package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "XXX_HR_QUALIFICATIONS")
public class HrQualification {
	

	@Id
	@Column(name = "QUALIFICATION_NAME")
	private String qualifName;
	@Column(name = "QUALIFICATION_TYPE")
	private String qualifType;
	@Column(name = "MAJORITY")
	private String majority;
	@Column(name = "SPECIALTY")
	private String speciality;
	@Column(name = "STUDY_TYPE")
	private String studyType;
	@Column(name = "STUDY_YEARS")
	private String studyYears;
	public String getQualifName() {
		return qualifName;
	}
	public void setQualifName(String qualifName) {
		this.qualifName = qualifName;
	}
	public String getQualifType() {
		return qualifType;
	}
	public void setQualifType(String qualifType) {
		this.qualifType = qualifType;
	}
	public String getMajority() {
		return majority;
	}
	public void setMajority(String majority) {
		this.majority = majority;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getStudyType() {
		return studyType;
	}
	public void setStudyType(String studyType) {
		this.studyType = studyType;
	}
	public String getStudyYears() {
		return studyYears;
	}
	public void setStudyYears(String studyYears) {
		this.studyYears = studyYears;
	}

}

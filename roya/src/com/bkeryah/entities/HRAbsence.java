package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="XXX_HR_CONTACTS")
public class HRAbsence {
	
	@Id
	@Column(name ="ABSENCE_TYPE")
	private String absenceType;
		
	@Column(name ="ABSENCE_TYPE_EN_NAME")
	private String absenceEnName;
		
	@Column(name ="ABSENCE_TYPE_AR_NAME")
	private String absenceArName;

	public String getAbsenceType() {
		return absenceType;
	}

	public void setAbsenceType(String absenceType) {
		this.absenceType = absenceType;
	}

	public String getAbsenceEnName() {
		return absenceEnName;
	}

	public void setAbsenceEnName(String absenceEnName) {
		this.absenceEnName = absenceEnName;
	}

	public String getAbsenceArName() {
		return absenceArName;
	}

	public void setAbsenceArName(String absenceArName) {
		this.absenceArName = absenceArName;
	}

}

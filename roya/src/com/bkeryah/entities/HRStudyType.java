package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="XXX_HR_STUDY_TYPE")
public class HRStudyType {
	
	@Id
	@Column(name ="STUDY_TYPE_CODE")
	private String typeCode;
	
	@Column(name ="STUDY_TYPE_EN_NAME")
	private String typeEnName;
	
	@Column(name ="STUDY_TYPE_AR_NAME")
	private String typeArName;

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeEnName() {
		return typeEnName;
	}

	public void setTypeEnName(String typeEnName) {
		this.typeEnName = typeEnName;
	}

	public String getTypeArName() {
		return typeArName;
	}

	public void setTypeArName(String typeArName) {
		this.typeArName = typeArName;
	}

}

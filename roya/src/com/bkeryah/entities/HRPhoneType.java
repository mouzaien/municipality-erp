package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="XXX_HR_PHONE_TYPE")
public class HRPhoneType {
	
	@Id
	@Column(name ="PHONE_TYPE_CODE")
	private String typeCode;
	
	@Column(name ="PHONE_TYPE_EN_NAME")
	private String typeEnName;
	
	@Column(name ="PHONE_TYPE_AR_NAME")
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

package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="XXX_HR_BLOOD_TYPE")
public class HRBlood {

	
	@Id
	@Column(name ="BLOOD_TYPE_CODE")
	private String bloodCode;
		
	@Column(name ="BLOOD_TYPE_EN_NAME")
	private String bloodEnName;
		
	@Column(name ="BLOOD_TYPE_AR_NAME")
	private String bloodArName;

	public String getBloodCode() {
		return bloodCode;
	}

	public void setBloodCode(String bloodCode) {
		this.bloodCode = bloodCode;
	}

	public String getBloodEnName() {
		return bloodEnName;
	}

	public void setBloodEnName(String bloodEnName) {
		this.bloodEnName = bloodEnName;
	}

	public String getBloodArName() {
		return bloodArName;
	}

	public void setBloodArName(String bloodArName) {
		this.bloodArName = bloodArName;
	}
}

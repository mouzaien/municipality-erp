package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="XXX_HR_RELIGION")
public class HRReligion {

	
	@Id
	@Column(name ="RELIGION_CODE")
	private String relCode;
		
	@Column(name ="RELIGION_EN_NAME")
	private String relEnName;
		
	@Column(name ="RELIGION_AR_NAME")
	private String relArName;

	public String getRelCode() {
		return relCode;
	}

	public void setRelCode(String relCode) {
		this.relCode = relCode;
	}

	public String getRelEnName() {
		return relEnName;
	}

	public void setRelEnName(String relEnName) {
		this.relEnName = relEnName;
	}

	public String getRelArName() {
		return relArName;
	}

	public void setRelArName(String relArName) {
		this.relArName = relArName;
	}

}

package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name ="XXX_HR_QLF_SPECIALITY")
public class HRQlfSpeciality {

	
	@Id
	@Column(name ="QLF_SPC_CODE")
	private String qlfSpcCode;
		
	@Column(name ="QLF_SPC_EN_NAME")
	private String qlfSpcEnName;
		
	@Column(name ="QLF_SPC_AR_NAME")
	private String qlfSpcArName;

	public String getQlfSpcCode() {
		return qlfSpcCode;
	}

	public void setQlfSpcCode(String qlfSpcCode) {
		this.qlfSpcCode = qlfSpcCode;
	}

	public String getQlfSpcEnName() {
		return qlfSpcEnName;
	}

	public void setQlfSpcEnName(String qlfSpcEnName) {
		this.qlfSpcEnName = qlfSpcEnName;
	}

	public String getQlfSpcArName() {
		return qlfSpcArName;
	}

	public void setQlfSpcArName(String qlfSpcArName) {
		this.qlfSpcArName = qlfSpcArName;
	}
}

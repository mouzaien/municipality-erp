package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="XXX_HR_QLF_MAJORS")
public class HRQlfMajors {

	@Id
	@Column(name ="QLF_MAJOR_CODE")
	private String qlfMajorCode;
		
	@Column(name ="QLF_MAJOR_EN_NAME")
	private String qlfMajorEnName;
		
	@Column(name ="QLF_MAJOR_AR_NAME")
	private String qlfMajorArName;

	public String getQlfMajorCode() {
		return qlfMajorCode;
	}

	public void setQlfMajorCode(String qlfMajorCode) {
		this.qlfMajorCode = qlfMajorCode;
	}

	public String getQlfMajorEnName() {
		return qlfMajorEnName;
	}

	public void setQlfMajorEnName(String qlfMajorEnName) {
		this.qlfMajorEnName = qlfMajorEnName;
	}

	public String getQlfMajorArName() {
		return qlfMajorArName;
	}

	public void setQlfMajorArName(String qlfMajorArName) {
		this.qlfMajorArName = qlfMajorArName;
	}

}

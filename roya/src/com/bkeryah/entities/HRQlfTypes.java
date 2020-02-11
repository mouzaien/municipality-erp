package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="XXX_HR_QLF_TYPES")
public class HRQlfTypes {

	@Id
	@Column(name ="QLF_TYPE_CODE")
	private String qlfTypeCode;
		
	@Column(name ="QLF_TYPE_EN_NAME")
	private String qlfTypeEnName;
		
	@Column(name ="QLF_TYPE_AR_NAME")
	private String qlfTypeArName;

	public String getQlfTypeCode() {
		return qlfTypeCode;
	}

	public void setQlfTypeCode(String qlfTypeCode) {
		this.qlfTypeCode = qlfTypeCode;
	}

	public String getQlfTypeEnName() {
		return qlfTypeEnName;
	}

	public void setQlfTypeEnName(String qlfTypeEnName) {
		this.qlfTypeEnName = qlfTypeEnName;
	}

	public String getQlfTypeArName() {
		return qlfTypeArName;
	}

	public void setQlfTypeArName(String qlfTypeArName) {
		this.qlfTypeArName = qlfTypeArName;
	}
}

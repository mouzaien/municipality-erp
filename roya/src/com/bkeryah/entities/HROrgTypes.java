package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "XXX_HR_ORG_TYPES")
public class HROrgTypes {
	@Id
	@Column(name ="ORG_TYPE_CODE")
	private String orgTypeCode;
	
	@Column(name ="ORG_TYPE_EN_NAME")
	private String orgTypeEnName;
	
	@Column(name ="ORG_TYPE_AR_NAME")
	private String orgTypeArName;

	public String getOrgTypeCode() {
		return orgTypeCode;
	}

	public void setOrgTypeCode(String orgTypeCode) {
		this.orgTypeCode = orgTypeCode;
	}

	public String getOrgTypeEnName() {
		return orgTypeEnName;
	}

	public void setOrgTypeEnName(String orgTypeEnName) {
		this.orgTypeEnName = orgTypeEnName;
	}

	public String getOrgTypeArName() {
		return orgTypeArName;
	}

	public void setOrgTypeArName(String orgTypeArName) {
		this.orgTypeArName = orgTypeArName;
	}
}

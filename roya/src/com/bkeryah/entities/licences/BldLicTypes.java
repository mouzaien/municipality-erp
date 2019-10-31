package com.bkeryah.entities.licences;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "BLD_LIC_TYPES")
public class BldLicTypes {

	@Id
	@Column(name = "BLD_LIC_TYPE_ID")
	private Integer bldLicTypeId;
	@Column(name = "BLD_LIC_TYPE")
	private String bldLicType;

	public Integer getBldLicTypeId() {
		return bldLicTypeId;
	}

	public void setBldLicTypeId(Integer bldLicTypeId) {
		this.bldLicTypeId = bldLicTypeId;
	}

	public String getBldLicType() {
		return bldLicType;
	}

	public void setBldLicType(String bldLicType) {
		this.bldLicType = bldLicType;
	}
}
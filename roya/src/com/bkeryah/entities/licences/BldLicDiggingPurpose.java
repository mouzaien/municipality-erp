package com.bkeryah.entities.licences;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BLD_LIC_DIGGING_PURPOSE")
public class BldLicDiggingPurpose {
	@Id
	@Column(name = "ID")
	private Integer id;
	@Column(name = "PURPOSE")
	private String purpose;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
}
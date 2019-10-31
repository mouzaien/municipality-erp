package com.bkeryah.entities.licences;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BldLicArchiveId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "LIC_ID")
	private Integer licId;
	@Column(name = "ARC_ID")
	private Integer arcId;

	public Integer getLicId() {
		return licId;
	}

	public void setLicId(Integer licId) {
		this.licId = licId;
	}

	public Integer getArcId() {
		return arcId;
	}

	public void setArcId(Integer arcId) {
		this.arcId = arcId;
	}
}
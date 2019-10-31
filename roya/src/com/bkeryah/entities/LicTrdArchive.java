package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "LIC_TRD_ARCHIVE")
public class LicTrdArchive {
	
	@Id
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
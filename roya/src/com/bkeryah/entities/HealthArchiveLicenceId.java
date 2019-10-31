package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class HealthArchiveLicenceId implements Serializable {

	@ManyToOne
	private ArcRecords arcRecord;
	@ManyToOne
	private HealthMasterLicence healthMasterLicence;
	
	public ArcRecords getArcRecord() {
		return arcRecord;
	}
	public void setArcRecord(ArcRecords arcRecord) {
		this.arcRecord = arcRecord;
	}
	public HealthMasterLicence getHealthMasterLicence() {
		return healthMasterLicence;
	}
	public void setHealthMasterLicence(HealthMasterLicence healthMasterLicence) {
		this.healthMasterLicence = healthMasterLicence;
	}
	
}

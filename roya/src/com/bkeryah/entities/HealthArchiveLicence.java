package com.bkeryah.entities;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "LIC_HLT_ARCHIVE")
@AssociationOverrides({
	@AssociationOverride(name = "id.arcRecord", 
		joinColumns = @JoinColumn(name = "ARC_ID")),
	@AssociationOverride(name = "id.healthMasterLicence", 
		joinColumns = @JoinColumn(name = "LIC_ID")) })
	public class HealthArchiveLicence {
	@EmbeddedId
	private HealthArchiveLicenceId id;

	public HealthArchiveLicenceId getId() {
		return id;
	}

	public void setId(HealthArchiveLicenceId id) {
		this.id = id;
	}
	
}

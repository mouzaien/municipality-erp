package com.bkeryah.entities.licences;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "BLD_LIC_ARCHIVE")
public class BldLicArchive {
	@EmbeddedId
	private BldLicArchiveId id;

	public BldLicArchiveId getId() {
		return id;
	}

	public void setId(BldLicArchiveId id) {
		this.id = id;
	}
}
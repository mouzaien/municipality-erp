package com.bkeryah.entities.licences;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BLD_LIC_PCS")
public class BldLicPcs {
	@EmbeddedId
	private BldLicPcsId id;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "BLD_LIC_ID", referencedColumnName = "LIC_NEW_ID", insertable = false, updatable = false)
	private BldLicNew bldLicNew;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "BLD_LIC_ID", referencedColumnName = "LIC_ATT_ID", insertable = false, updatable = false)
	private BldLicAttch bldLicAttch;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "BLD_LIC_ID", referencedColumnName = "LIC_HANGOVER_ID", insertable = false, updatable = false)
	private BldLicHangover bldLicHangover;
	
	public BldLicPcs() {
		
	}

	public BldLicPcs(Integer licNewId) {
		id = new BldLicPcsId();
		id.setLicId(licNewId);
	}

	public BldLicPcsId getId() {
		return id;
	}

	public void setId(BldLicPcsId id) {
		this.id = id;
	}

	public BldLicNew getBldLicNew() {
		return bldLicNew;
	}

	public void setBldLicNew(BldLicNew bldLicNew) {
		this.bldLicNew = bldLicNew;
	}

	public BldLicAttch getBldLicAttch() {
		return bldLicAttch;
	}

	public void setBldLicAttch(BldLicAttch bldLicAttch) {
		this.bldLicAttch = bldLicAttch;
	}

	public BldLicHangover getBldLicHangover() {
		return bldLicHangover;
	}

	public void setBldLicHangover(BldLicHangover bldLicHangover) {
		this.bldLicHangover = bldLicHangover;
	}
}
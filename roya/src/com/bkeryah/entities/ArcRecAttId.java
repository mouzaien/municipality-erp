package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class ArcRecAttId implements Serializable {

	@Column(name = "RREC_ID")
	private int recordId;// record Id

	@Column(name = "ATT_ID")
	private int attachId;// attachId
	
	public ArcRecAttId() {

	}

	public ArcRecAttId(int recordId, int attachId) {
		super();
		this.recordId = recordId;
		this.attachId = attachId;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public int getAttachId() {
		return attachId;
	}

	public void setAttachId(int attachId) {
		this.attachId = attachId;
	}

}

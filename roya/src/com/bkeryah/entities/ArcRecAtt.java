package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ARC_REC_ATT")
public class ArcRecAtt {
	
	@Column(name="RREC_ID",insertable=false,updatable=false)
	private int recordId;//record Id
//	@Id
//	@Column(name="ATT_ID")
//	private int attachId;//attachId
	@EmbeddedId
	private ArcRecAttId id;
	@Column(name="IS_ADDED")
	private Integer isAdded;
	@ManyToOne
	@JoinColumn(name="ATT_ID" ,referencedColumnName="ID" ,insertable = false,updatable = false)
	private ArcAttach attachment;
//	@ManyToOne
//	@JoinColumn(name = "RREC_ID" ,referencedColumnName="ID" ,insertable=false, updatable=false)
//	ArcRecords arcRecords;
	public ArcRecAttId getId() {
		return id;
	}
	public void setId(ArcRecAttId id) {
		this.id = id;
	}
	public Integer getIsAdded() {
		return isAdded;
	}
	public void setIsAdded(Integer isAdded) {
		this.isAdded = isAdded;
	}
	public ArcAttach getAttachment() {
		return attachment;
	}
	public void setAttachment(ArcAttach attachment) {
		this.attachment = attachment;
	}
//	public ArcRecords getArcRecords() {
//		return arcRecords;
//	}
//	public void setArcRecords(ArcRecords arcRecords) {
//		this.arcRecords = arcRecords;
//	} 
	public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	
	
	
	
	
}

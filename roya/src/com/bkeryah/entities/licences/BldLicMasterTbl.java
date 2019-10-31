package com.bkeryah.entities.licences;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BLD_LIC_MASTER_TBL")
public class BldLicMasterTbl {

	@Id
	@Column(name = "BLD_REQ_ID")
	private Integer bldReqId;
	@Column(name = "BLD_REQ_TYP")
	private Integer bldReqTyp;
	@Column(name = "BLD_REQ_REC_ID")
	private Integer bldReqRecId;
	@Column(name = "BLD_REQ_STS")
	private Integer bldReqSts;
	@Column(name = "BLD_REQ_NAT_NO")
	private String bldReqNatNo;

	public Integer getBldReqId() {
		return bldReqId;
	}

	public void setBldReqId(Integer bldReqId) {
		this.bldReqId = bldReqId;
	}

	public Integer getBldReqTyp() {
		return bldReqTyp;
	}

	public void setBldReqTyp(Integer bldReqTyp) {
		this.bldReqTyp = bldReqTyp;
	}

	public Integer getBldReqRecId() {
		return bldReqRecId;
	}

	public void setBldReqRecId(Integer bldReqRecId) {
		this.bldReqRecId = bldReqRecId;
	}

	public Integer getBldReqSts() {
		return bldReqSts;
	}

	public void setBldReqSts(Integer bldReqSts) {
		this.bldReqSts = bldReqSts;
	}

	public String getBldReqNatNo() {
		return bldReqNatNo;
	}

	public void setBldReqNatNo(String bldReqNatNo) {
		this.bldReqNatNo = bldReqNatNo;
	}
}
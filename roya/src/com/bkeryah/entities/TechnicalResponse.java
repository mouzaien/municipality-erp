package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Technical_RESPONSE")
public class TechnicalResponse {
	
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID" , nullable = false)
	private Integer technicalResponseId;
	@Column(name = "INV_NO", nullable = false)
	private Integer ddeId;
	@Column(name = "TECHNICAL_ID", nullable = false)
	private Integer TechnicalID;
	
	@Column(name = "NOTE")
	private String note;

	public TechnicalResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TechnicalResponse(Integer technicalResponseId, Integer ddeId, Integer technicalID, String note) {
		super();
		this.technicalResponseId = technicalResponseId;
		this.ddeId = ddeId;
		TechnicalID = technicalID;
		this.note = note;
	}

	public Integer getTechnicalResponseId() {
		return technicalResponseId;
	}

	public void setTechnicalResponseId(Integer technicalResponseId) {
		this.technicalResponseId = technicalResponseId;
	}

	public Integer getDdeId() {
		return ddeId;
	}

	public void setDdeId(Integer ddeId) {
		this.ddeId = ddeId;
	}

	public Integer getTechnicalID() {
		return TechnicalID;
	}

	public void setTechnicalID(Integer technicalID) {
		TechnicalID = technicalID;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	
	
	

}

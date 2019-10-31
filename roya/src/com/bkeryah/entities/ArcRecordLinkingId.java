package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ArcRecordLinkingId implements Serializable{

	@Column(name = "REC1")
	private Integer newIncomeNumber;
	
	@Column(name = "REC2")
	private Integer oldIncomeNumber;

	
	
	
	
	public ArcRecordLinkingId() {
		super();
	}

	public ArcRecordLinkingId(Integer newIncomeNumber, Integer oldIncomeNumber) {
		super();
		this.newIncomeNumber = newIncomeNumber;
		this.oldIncomeNumber = oldIncomeNumber;
	}

	public Integer getNewIncomeNumber() {
		return newIncomeNumber;
	}

	public void setNewIncomeNumber(Integer newIncomeNumber) {
		this.newIncomeNumber = newIncomeNumber;
	}

	public Integer getOldIncomeNumber() {
		return oldIncomeNumber;
	}

	public void setOldIncomeNumber(Integer oldIncomeNumber) {
		this.oldIncomeNumber = oldIncomeNumber;
	}
	
	
	
}

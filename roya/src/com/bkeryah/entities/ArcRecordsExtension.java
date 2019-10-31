package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="ARC_RECORDS_EXTENSION")
public class ArcRecordsExtension {

	@Id
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NATIONAL_ID")
	private String nationalId;
	@Column(name = "LETTER_FROM_TYP")
	private Integer letterFromType;
//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "arcRecordsExtension")
//	private ArcRecords arcRecords;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNationalId() {
		return nationalId;
	}
	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}
	public Integer getLetterFromType() {
		return letterFromType;
	}
	public void setLetterFromType(Integer letterFromType) {
		this.letterFromType = letterFromType;
	}
//	public ArcRecords getArcRecords() {
//		return arcRecords;
//	}
//	public void setArcRecords(ArcRecords arcRecords) {
//		this.arcRecords = arcRecords;
//	}
}

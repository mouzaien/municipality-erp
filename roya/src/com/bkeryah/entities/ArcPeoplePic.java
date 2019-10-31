package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ARC_PEOPLE_PIC")
public class ArcPeoplePic {

	@Id
	@Column(name = "NATNO", nullable = true)
	private Long nationalNumber; 
	
	@Column(name = "PIC", nullable = true)
	@Lob
	private byte[] picture;
	@OneToOne
	@JoinColumn(name="NATNO",referencedColumnName="NAT_NO",insertable=false,updatable=false)
	private ArcPeople arcPeople;
	
	public Long getNationalNumber() {
		return nationalNumber;
	}
	public void setNationalNumber(Long nationalNumber) {
		this.nationalNumber = nationalNumber;
	}
	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	public ArcPeople getArcPeople() {
		return arcPeople;
	}
	public void setArcPeople(ArcPeople arcPeople) {
		this.arcPeople = arcPeople;
	}

	
}

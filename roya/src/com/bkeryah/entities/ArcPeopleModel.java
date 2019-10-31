package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ARC_PEOPLE")
public class ArcPeopleModel {
	@Id
	@Column(name = "NAT_NO", nullable = false)
	private Long nationalId;
	@Column(name = "FST_NAME", nullable = false)
	private String firstName;
	@Column(name = "SND_NAME", nullable = false)
	private String seconedName;
	@Column(name = "TRD_NAME", nullable = true)
	private String thirdName;
	@Column(name = "FTH_NAME", nullable = true)
	private String fourthName;
	@Column(name = "ID_SOURCE", nullable = false)
	private String idSource;
	@Column(name = "ID_DATE", nullable = false)
	private String idDate;
	@Column(name = "EMAIL", nullable = true)
	private String email;
	@Column(name = "ADDRESS", nullable = true)
	private String address;
	@Column(name = "MOBILE_NO", nullable = false)
	private String mobileNumber;
	@Transient
	private String completeName;

	public ArcPeopleModel() {

	}

	public ArcPeopleModel(ArcPeople arcPeople) {
		if (arcPeople != null) {
			this.nationalId = arcPeople.getNationalId();
			this.firstName = arcPeople.getFirstName();
			this.seconedName = arcPeople.getSeconedName();
			this.thirdName = arcPeople.getThirdName();
			this.fourthName = arcPeople.getFourthName();
		}
	}

	public String getCompleteName() {
		completeName = ((firstName == null) ? "" : firstName) + " " + ((seconedName == null) ? "" : seconedName) + " "
				+ ((thirdName == null) ? "" : thirdName) + " " + ((fourthName == null) ? "" : fourthName);
		return completeName;
	}

	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}

	public Long getNationalId() {
		return nationalId;
	}

	public void setNationalId(Long nationalId) {
		this.nationalId = nationalId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSeconedName() {
		return seconedName;
	}

	public void setSeconedName(String seconedName) {
		this.seconedName = seconedName;
	}

	public String getThirdName() {
		return thirdName;
	}

	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}

	public String getFourthName() {
		return fourthName;
	}

	public void setFourthName(String fourthName) {
		this.fourthName = fourthName;
	}

	public String getIdSource() {
		return idSource;
	}

	public void setIdSource(String idSource) {
		this.idSource = idSource;
	}

	public String getIdDate() {
		return idDate;
	}

	public void setIdDate(String idDate) {
		this.idDate = idDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}

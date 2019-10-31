package com.bkeryah.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ARC_PEOPLE")
public class ArcPeople {
	@Id
	@Column(name = "NAT_NO")
	private Long nationalId;
	@Column(name = "FST_NAME")
	private String firstName;
	@Column(name = "SND_NAME")
	private String seconedName;
	@Column(name = "TRD_NAME")
	private String thirdName;
	@Column(name = "FTH_NAME")
	private String fourthName;
	@Column(name = "ADDRESS")
	private String address;
	@Column(name = "HOME_NO")
	private String homeNumber;
	@Column(name = "WORK_NO")
	private String workNumber;
	@Column(name = "MOBILE_NO")
	private String mobileNumber;
	@Column(name = "PO_BOX")
	private String postalBox;
	@Column(name = "POSTAL_CODE")
	private String postalCode;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "NATIONALITY")
	private Integer nationalityId;
	@Column(name = "ID_SOURCE")
	private String idSource;
	@Column(name = "ID_DATE")
	private String idDate;
	@Column(name = "ID_TYPE")
	private Integer idType;
	@Column(name = "DATE_OF_BIRTH")
	private String dateOfBirth;
	@Column(name = "PAPER_TYPE")
	private Integer paperType;
	@Column(name = "SEX")
	private String gender;
	@Column(name = "FAX")
	private String faxNumber;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "arcPeople")
	private Set<HealthMasterLicence> healthMasterLicences;
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "arcPeople")
	private ArcPeoplePic arcPeoplePic;
	@ManyToOne
	@JoinColumn(name = "PAPER_TYPE", referencedColumnName = "ID", insertable = false, updatable = false)
	private ArcPeoplePapers arcPeoplePapers;
//	@ManyToOne
//	@JoinColumn(name = "NATIONALITY", referencedColumnName = "ID", insertable = false, updatable = false)
//	private Nationality nationality;
	@Transient
	private String completeName;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHomeNumber() {
		return homeNumber;
	}

	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
	}

	public String getWorkNumber() {
		return workNumber;
	}

	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPostalBox() {
		return postalBox;
	}

	public void setPostalBox(String postalBox) {
		this.postalBox = postalBox;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getNationalId() {
		return nationalId;
	}

	public void setNationalId(Long nationalId) {
		this.nationalId = nationalId;
	}

	public Integer getNationalityId() {
		return nationalityId;
	}

	public void setNationalityId(Integer nationalityId) {
		this.nationalityId = nationalityId;
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

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public ArcPeoplePic getArcPeoplePic() {
		return arcPeoplePic;
	}

	public void setArcPeoplePic(ArcPeoplePic arcPeoplePic) {
		this.arcPeoplePic = arcPeoplePic;
	}

	public void setPaperType(Integer paperType) {
		this.paperType = paperType;
	}

	public ArcPeoplePapers getArcPeoplePapers() {
		return arcPeoplePapers;
	}

	public void setArcPeoplePapers(ArcPeoplePapers arcPeoplePapers) {
		this.arcPeoplePapers = arcPeoplePapers;
	}

	public String getCompleteName() {
		completeName = ((firstName == null) ? "" : firstName) + " " + ((seconedName == null) ? "" : seconedName) + " "
				+ ((thirdName == null) ? "" : thirdName) + " " + ((fourthName == null) ? "" : fourthName);
		return completeName;
	}

	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}

//	public Nationality getNationality() {
//		return nationality;
//	}
//
//	public void setNationality(Nationality nationality) {
//		this.nationality = nationality;
//	}

	public Set<HealthMasterLicence> getHealthMasterLicences() {
		return healthMasterLicences;
	}

	public void setHealthMasterLicences(Set<HealthMasterLicence> healthMasterLicences) {
		this.healthMasterLicences = healthMasterLicences;
	}

	@Override
	public String toString() {
		return "natNo : " + nationalId + " fname :" + firstName;
	}

	public Integer getPaperType() {
		return paperType;
	}
}

package com.bkeryah.entities.licences;

import java.io.InputStream;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "BLD_LIC_DIGGING")
public class BldLicDigging {
	@Id
	@Column(name = "LIC_DIGGING_ID")
	private Integer licDiggingId;
	@Column(name = "LIC_DIGGING_H_DATE")
	private String licDiggingHDate;
	@Column(name = "LIC_DIGGING_G_DATE")
	private Date licDiggingGDate;
	@Column(name = "LIC_DIGGING_APL_OWNER")
	private String licDiggingAplOwner;
	@Column(name = "LIC_DIGGING_APL_MOBILE")
	private String licDiggingAplMobile;
	@Column(name = "LIC_DIGGING_CONTRACTOR_ID")
	private Integer licDiggingContractorId;
	@Column(name = "LIC_DIGGING_STREET_DESC")
	private String licDiggingStreetDesc;
	@Column(name = "LIC_DIGGING_DIG_PURP_ID")
	private Integer licDiggingDigPurpId;
	@Column(name = "LIC_DIGGING_NUMBER")
	private String licDiggingNumber;
	@Column(name = "LIC_DIGGING_ISSUE_DATE")
	private String licDiggingIssueDate;
	@Column(name = "LIC_DIGGING_END_DATE")
	private String licDiggingEndDate;
	@Column(name = "LIC_DIGGING_DIG_LENGTH")
	private Integer licDiggingDigLength;
	@Column(name = "LIC_DIGGING_DIG_WIDTH")
	private Integer licDiggingDigWidth;
	@Column(name = "LIC_DIGGING_DIG_DEPTH")
	private Integer licDiggingDigDepth;
	@Column(name = "LIC_DIGGING_STREET_WIDTH")
	private Integer licDiggingStreetWidth;
	@Column(name = "LIC_DIGGING_DIG_NOTE")
	private String licDiggingDigNote;
	@Lob
	@Column(name = "LIC_DIGGING_DIG_GENERAL_KROKI")
	private byte[] licDiggingDigGeneralKroki;
	@Lob
	@Column(name = "LIC_DIGGING_DIG_LAND_KROKI")
	private byte[] licDiggingDigLandKroki;
	@Column(name = "LIC_DIGGING_DIG_WATCHER_ID")
	private Integer licDiggingDigWatcherId;
	@Column(name = "LIC_DIGGING_WROTE_BY")
	private Integer licDiggingWroteBy;
	@Column(name = "LIC_DIGGING_WROTE_IN")
	private String licDiggingWroteIn;
	@Column(name = "LIC_DIGGING_MARKED_BY")
	private Integer licDiggingMarkedBy;
	@Column(name = "LIC_DIGGING_MARKED_IN")
	private String licDiggingMarkedIn;
	@Column(name = "LIC_DIGGING_SIGNED_BY")
	private Integer licDiggingSignedBy;
	@Column(name = "LIC_DIGGING_SIGNED_IN")
	private String licDiggingSignedIn;
	@Column(name = "LIC_DIGGING_ZONE")
	private String licDiggingZone;
	@Column(name = "LIC_DIGGING_STREET")
	private String licDiggingStreet;
	@Column(name = "LIC_DIGGING_ISSUE_TYP")
	private String licDiggingIssueTyp;
	@Column(name = "LIC_DIGG_PRIV_ID")
	private Integer licDiggPrivId;

	public Integer getLicDiggingId() {
		return licDiggingId;
	}

	public void setLicDiggingId(Integer licDiggingId) {
		this.licDiggingId = licDiggingId;
	}

	public String getLicDiggingHDate() {
		return licDiggingHDate;
	}

	public void setLicDiggingHDate(String licDiggingHDate) {
		this.licDiggingHDate = licDiggingHDate;
	}

	public Date getLicDiggingGDate() {
		return licDiggingGDate;
	}

	public void setLicDiggingGDate(Date licDiggingGDate) {
		this.licDiggingGDate = licDiggingGDate;
	}

	public String getLicDiggingAplOwner() {
		return licDiggingAplOwner;
	}

	public void setLicDiggingAplOwner(String licDiggingAplOwner) {
		this.licDiggingAplOwner = licDiggingAplOwner;
	}

	public String getLicDiggingAplMobile() {
		return licDiggingAplMobile;
	}

	public void setLicDiggingAplMobile(String licDiggingAplMobile) {
		this.licDiggingAplMobile = licDiggingAplMobile;
	}

	public Integer getLicDiggingContractorId() {
		return licDiggingContractorId;
	}

	public void setLicDiggingContractorId(Integer licDiggingContractorId) {
		this.licDiggingContractorId = licDiggingContractorId;
	}

	public String getLicDiggingStreetDesc() {
		return licDiggingStreetDesc;
	}

	public void setLicDiggingStreetDesc(String licDiggingStreetDesc) {
		this.licDiggingStreetDesc = licDiggingStreetDesc;
	}

	public Integer getLicDiggingDigPurpId() {
		return licDiggingDigPurpId;
	}

	public void setLicDiggingDigPurpId(Integer licDiggingDigPurpId) {
		this.licDiggingDigPurpId = licDiggingDigPurpId;
	}

	public String getLicDiggingNumber() {
		return licDiggingNumber;
	}

	public void setLicDiggingNumber(String licDiggingNumber) {
		this.licDiggingNumber = licDiggingNumber;
	}

	public String getLicDiggingIssueDate() {
		return licDiggingIssueDate;
	}

	public void setLicDiggingIssueDate(String licDiggingIssueDate) {
		this.licDiggingIssueDate = licDiggingIssueDate;
	}

	public String getLicDiggingEndDate() {
		return licDiggingEndDate;
	}

	public void setLicDiggingEndDate(String licDiggingEndDate) {
		this.licDiggingEndDate = licDiggingEndDate;
	}

	public Integer getLicDiggingDigLength() {
		return licDiggingDigLength;
	}

	public void setLicDiggingDigLength(Integer licDiggingDigLength) {
		this.licDiggingDigLength = licDiggingDigLength;
	}

	public Integer getLicDiggingDigWidth() {
		return licDiggingDigWidth;
	}

	public void setLicDiggingDigWidth(Integer licDiggingDigWidth) {
		this.licDiggingDigWidth = licDiggingDigWidth;
	}

	public Integer getLicDiggingDigDepth() {
		return licDiggingDigDepth;
	}

	public void setLicDiggingDigDepth(Integer licDiggingDigDepth) {
		this.licDiggingDigDepth = licDiggingDigDepth;
	}

	public Integer getLicDiggingStreetWidth() {
		return licDiggingStreetWidth;
	}

	public void setLicDiggingStreetWidth(Integer licDiggingStreetWidth) {
		this.licDiggingStreetWidth = licDiggingStreetWidth;
	}

	public String getLicDiggingDigNote() {
		return licDiggingDigNote;
	}

	public void setLicDiggingDigNote(String licDiggingDigNote) {
		this.licDiggingDigNote = licDiggingDigNote;
	}

	public byte[] getLicDiggingDigGeneralKroki() {
		return licDiggingDigGeneralKroki;
	}

	public void setLicDiggingDigGeneralKroki(byte[] licDiggingDigGeneralKroki) {
		this.licDiggingDigGeneralKroki = licDiggingDigGeneralKroki;
	}

	public byte[] getLicDiggingDigLandKroki() {
		return licDiggingDigLandKroki;
	}

	public void setLicDiggingDigLandKroki(byte[] licDiggingDigLandKroki) {
		this.licDiggingDigLandKroki = licDiggingDigLandKroki;
	}

	public Integer getLicDiggingDigWatcherId() {
		return licDiggingDigWatcherId;
	}

	public void setLicDiggingDigWatcherId(Integer licDiggingDigWatcherId) {
		this.licDiggingDigWatcherId = licDiggingDigWatcherId;
	}

	public Integer getLicDiggingWroteBy() {
		return licDiggingWroteBy;
	}

	public void setLicDiggingWroteBy(Integer licDiggingWroteBy) {
		this.licDiggingWroteBy = licDiggingWroteBy;
	}

	public String getLicDiggingWroteIn() {
		return licDiggingWroteIn;
	}

	public void setLicDiggingWroteIn(String licDiggingWroteIn) {
		this.licDiggingWroteIn = licDiggingWroteIn;
	}

	public Integer getLicDiggingMarkedBy() {
		return licDiggingMarkedBy;
	}

	public void setLicDiggingMarkedBy(Integer licDiggingMarkedBy) {
		this.licDiggingMarkedBy = licDiggingMarkedBy;
	}

	public String getLicDiggingMarkedIn() {
		return licDiggingMarkedIn;
	}

	public void setLicDiggingMarkedIn(String licDiggingMarkedIn) {
		this.licDiggingMarkedIn = licDiggingMarkedIn;
	}

	public Integer getLicDiggingSignedBy() {
		return licDiggingSignedBy;
	}

	public void setLicDiggingSignedBy(Integer licDiggingSignedBy) {
		this.licDiggingSignedBy = licDiggingSignedBy;
	}

	public String getLicDiggingSignedIn() {
		return licDiggingSignedIn;
	}

	public void setLicDiggingSignedIn(String licDiggingSignedIn) {
		this.licDiggingSignedIn = licDiggingSignedIn;
	}

	public String getLicDiggingZone() {
		return licDiggingZone;
	}

	public void setLicDiggingZone(String licDiggingZone) {
		this.licDiggingZone = licDiggingZone;
	}

	public String getLicDiggingStreet() {
		return licDiggingStreet;
	}

	public void setLicDiggingStreet(String licDiggingStreet) {
		this.licDiggingStreet = licDiggingStreet;
	}

	public String getLicDiggingIssueTyp() {
		return licDiggingIssueTyp;
	}

	public void setLicDiggingIssueTyp(String licDiggingIssueTyp) {
		this.licDiggingIssueTyp = licDiggingIssueTyp;
	}

	public Integer getLicDiggPrivId() {
		return licDiggPrivId;
	}

	public void setLicDiggPrivId(Integer licDiggPrivId) {
		this.licDiggPrivId = licDiggPrivId;
	}
}
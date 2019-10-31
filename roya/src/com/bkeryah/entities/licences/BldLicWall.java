package com.bkeryah.entities.licences;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Lob;

import java.io.InputStream;
import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;

@Entity
@Table(name = "BLD_LIC_WALL")
public class BldLicWall {

	@Id
	@Column(name = "LIC_WALL_ID")
	private Integer licWallId;
	@Column(name = "LIC_WALL_WEST_BORDER")
	private String licWallWestBorder;
	@Column(name = "LIC_WALL_NORTH_DIM")
	private String licWallNorthDim;
	@Column(name = "LIC_WALL_EAST_DIM")
	private String licWallEastDim;
	@Column(name = "LIC_WALL_SOUTH_DIM")
	private String licWallSouthDim;
	@Column(name = "LIC_WALL_WEST_DIM")
	private String licWallWestDim;
	@Column(name = "LIC_WALL_WATCHER_ID")
	private Integer licWallWatcherId;
	@Column(name = "LIC_WALL_WROTE_BY")
	private Integer licWallWroteBy;
	@Column(name = "LIC_WALL_WROTE_IN")
	private String licWallWroteIn;
	@Column(name = "LIC_WALL_MARKED_BY")
	private Integer licWallMarkedBy;
	@Column(name = "LIC_WALL_MARKED_IN")
	private String licWallMarkedIn;
	@Column(name = "LIC_WALL_SIGNED_BY")
	private Integer licWallSignedBy;
	@Column(name = "LIC_WALL_SIGNED_IN")
	private String licWallSignedIn;
	@Column(name = "LIC_WALL_BILL_NUMBER")
	private String licWallBillNumber;
	@Column(name = "LIC_WALL_STS")
	private Integer licWallSts;
	@Column(name = "LIC_WALL_GENERAL_KROKI")
	private byte[]  licWallGeneralKroki;
	@Column(name = "LIC_WALL_LAND_KROKI")
	private byte[]  licWallLandKroki;
	@Column(name = "LIC_WALL_ADDRESS")
	private String licWallAddress;
	@Column(name = "LIC_WALL_REQ_TYPE")
	private String licWallReqType;
	@Column(name = "LIC_WALL_H_DATE")
	private String licWallHDate;
	@Column(name = "LIC_WALL_G_DATE")
	private Date licWallGDate;
	@Column(name = "LIC_WALL_APL_OWNER")
	private String licWallAplOwner;
	@Column(name = "LIC_WALL_APL_MOBILE")
	private String licWallAplMobile;
	@Column(name = "LIC_WALL_BLD_LIC_NO")
	private String licWallBldLicNo;
	@Column(name = "LIC_WALL_BLD_LIC_DATE")
	private String licWallBldLicDate;
	@Column(name = "LIC_WALL_BLD_LIC_SOURCE")
	private String licWallBldLicSource;
	@Column(name = "LIC_WALL_SAK_NO")
	private String licWallSakNo;
	@Column(name = "LIC_WALL_SAK_DATE")
	private String licWallSakDate;
	@Column(name = "LIC_WALL_SAK_SOURCE")
	private String licWallSakSource;
	@Column(name = "LIC_WALL_PLN_NO")
	private String licWallPlnNo;
	@Column(name = "LIC_WALL_LAND_MAPPED_Y_N")
	private String licWallLandMappedYN;
	@Column(name = "LIC_WALL_LENGTH")
	private Integer licWallLength;
	@Column(name = "LIC_WALL_HEIGHT")
	private Integer licWallHeight;
	@Column(name = "LIC_WALL_NOTES")
	private String licWallNotes;
	@Column(name = "LIC_WALL_ENG_OFFICE_ID")
	private Integer licWallEngOfficeId;
	@Column(name = "LIC_WALL_MATERIAL")
	private String licWallMaterial;
	@Column(name = "LIC_WALL_NUMBER")
	private String licWallNumber;
	@Column(name = "LIC_WALL_ISSUE_DATE")
	private String licWallIssueDate;
	@Column(name = "LIC_WALL_END_DATE")
	private String licWallEndDate;
	@Column(name = "LIC_WALL_LAND_AREA")
	private Integer licWallLandArea;
	@Column(name = "LIC_WALL_MAX_HEIGHT")
	private Integer licWallMaxHeight;
	@Column(name = "LIC_WALL_MAN_HEIGHT")
	private Integer licWallManHeight;
	@Column(name = "LIC_WALL_NORTH_BORDER")
	private String licWallNorthBorder;
	@Column(name = "LIC_WALL_EAST_BORDER")
	private String licWallEastBorder;
	@Column(name = "LIC_WALL_SOUTH_BORDER")
	private String licWallSouthBorder;

	public Integer getLicWallId() {
		return licWallId;
	}

	public void setLicWallId(Integer licWallId) {
		this.licWallId = licWallId;
	}

	public String getLicWallWestBorder() {
		return licWallWestBorder;
	}

	public void setLicWallWestBorder(String licWallWestBorder) {
		this.licWallWestBorder = licWallWestBorder;
	}

	public String getLicWallNorthDim() {
		return licWallNorthDim;
	}

	public void setLicWallNorthDim(String licWallNorthDim) {
		this.licWallNorthDim = licWallNorthDim;
	}

	public String getLicWallEastDim() {
		return licWallEastDim;
	}

	public void setLicWallEastDim(String licWallEastDim) {
		this.licWallEastDim = licWallEastDim;
	}

	public String getLicWallSouthDim() {
		return licWallSouthDim;
	}

	public void setLicWallSouthDim(String licWallSouthDim) {
		this.licWallSouthDim = licWallSouthDim;
	}

	public String getLicWallWestDim() {
		return licWallWestDim;
	}

	public void setLicWallWestDim(String licWallWestDim) {
		this.licWallWestDim = licWallWestDim;
	}

	public Integer getLicWallWatcherId() {
		return licWallWatcherId;
	}

	public void setLicWallWatcherId(Integer licWallWatcherId) {
		this.licWallWatcherId = licWallWatcherId;
	}

	public Integer getLicWallWroteBy() {
		return licWallWroteBy;
	}

	public void setLicWallWroteBy(Integer licWallWroteBy) {
		this.licWallWroteBy = licWallWroteBy;
	}

	public String getLicWallWroteIn() {
		return licWallWroteIn;
	}

	public void setLicWallWroteIn(String licWallWroteIn) {
		this.licWallWroteIn = licWallWroteIn;
	}

	public Integer getLicWallMarkedBy() {
		return licWallMarkedBy;
	}

	public void setLicWallMarkedBy(Integer licWallMarkedBy) {
		this.licWallMarkedBy = licWallMarkedBy;
	}

	public String getLicWallMarkedIn() {
		return licWallMarkedIn;
	}

	public void setLicWallMarkedIn(String licWallMarkedIn) {
		this.licWallMarkedIn = licWallMarkedIn;
	}

	public Integer getLicWallSignedBy() {
		return licWallSignedBy;
	}

	public void setLicWallSignedBy(Integer licWallSignedBy) {
		this.licWallSignedBy = licWallSignedBy;
	}

	public String getLicWallSignedIn() {
		return licWallSignedIn;
	}

	public void setLicWallSignedIn(String licWallSignedIn) {
		this.licWallSignedIn = licWallSignedIn;
	}

	public String getLicWallBillNumber() {
		return licWallBillNumber;
	}

	public void setLicWallBillNumber(String licWallBillNumber) {
		this.licWallBillNumber = licWallBillNumber;
	}

	public Integer getLicWallSts() {
		return licWallSts;
	}

	public void setLicWallSts(Integer licWallSts) {
		this.licWallSts = licWallSts;
	}

	public byte[]  getLicWallGeneralKroki() {
		return licWallGeneralKroki;
	}

	public void setLicWallGeneralKroki(byte[]  licWallGeneralKroki) {
		this.licWallGeneralKroki = licWallGeneralKroki;
	}

	public byte[]  getLicWallLandKroki() {
		return licWallLandKroki;
	}

	public void setLicWallLandKroki(byte[]  licWallLandKroki) {
		this.licWallLandKroki = licWallLandKroki;
	}

	public String getLicWallAddress() {
		return licWallAddress;
	}

	public void setLicWallAddress(String licWallAddress) {
		this.licWallAddress = licWallAddress;
	}

	public String getLicWallReqType() {
		return licWallReqType;
	}

	public void setLicWallReqType(String licWallReqType) {
		this.licWallReqType = licWallReqType;
	}

	public String getLicWallHDate() {
		return licWallHDate;
	}

	public void setLicWallHDate(String licWallHDate) {
		this.licWallHDate = licWallHDate;
	}

	public Date getLicWallGDate() {
		return licWallGDate;
	}

	public void setLicWallGDate(Date licWallGDate) {
		this.licWallGDate = licWallGDate;
	}

	public String getLicWallAplOwner() {
		return licWallAplOwner;
	}

	public void setLicWallAplOwner(String licWallAplOwner) {
		this.licWallAplOwner = licWallAplOwner;
	}

	public String getLicWallAplMobile() {
		return licWallAplMobile;
	}

	public void setLicWallAplMobile(String licWallAplMobile) {
		this.licWallAplMobile = licWallAplMobile;
	}

	public String getLicWallBldLicNo() {
		return licWallBldLicNo;
	}

	public void setLicWallBldLicNo(String licWallBldLicNo) {
		this.licWallBldLicNo = licWallBldLicNo;
	}

	public String getLicWallBldLicDate() {
		return licWallBldLicDate;
	}

	public void setLicWallBldLicDate(String licWallBldLicDate) {
		this.licWallBldLicDate = licWallBldLicDate;
	}

	public String getLicWallBldLicSource() {
		return licWallBldLicSource;
	}

	public void setLicWallBldLicSource(String licWallBldLicSource) {
		this.licWallBldLicSource = licWallBldLicSource;
	}

	public String getLicWallSakNo() {
		return licWallSakNo;
	}

	public void setLicWallSakNo(String licWallSakNo) {
		this.licWallSakNo = licWallSakNo;
	}

	public String getLicWallSakDate() {
		return licWallSakDate;
	}

	public void setLicWallSakDate(String licWallSakDate) {
		this.licWallSakDate = licWallSakDate;
	}

	public String getLicWallSakSource() {
		return licWallSakSource;
	}

	public void setLicWallSakSource(String licWallSakSource) {
		this.licWallSakSource = licWallSakSource;
	}

	public String getLicWallPlnNo() {
		return licWallPlnNo;
	}

	public void setLicWallPlnNo(String licWallPlnNo) {
		this.licWallPlnNo = licWallPlnNo;
	}

	public String getLicWallLandMappedYN() {
		return licWallLandMappedYN;
	}

	public void setLicWallLandMappedYN(String licWallLandMappedYN) {
		this.licWallLandMappedYN = licWallLandMappedYN;
	}

	public Integer getLicWallLength() {
		return licWallLength;
	}

	public void setLicWallLength(Integer licWallLength) {
		this.licWallLength = licWallLength;
	}

	public Integer getLicWallHeight() {
		return licWallHeight;
	}

	public void setLicWallHeight(Integer licWallHeight) {
		this.licWallHeight = licWallHeight;
	}

	public String getLicWallNotes() {
		return licWallNotes;
	}

	public void setLicWallNotes(String licWallNotes) {
		this.licWallNotes = licWallNotes;
	}

	public Integer getLicWallEngOfficeId() {
		return licWallEngOfficeId;
	}

	public void setLicWallEngOfficeId(Integer licWallEngOfficeId) {
		this.licWallEngOfficeId = licWallEngOfficeId;
	}

	public String getLicWallMaterial() {
		return licWallMaterial;
	}

	public void setLicWallMaterial(String licWallMaterial) {
		this.licWallMaterial = licWallMaterial;
	}

	public String getLicWallNumber() {
		return licWallNumber;
	}

	public void setLicWallNumber(String licWallNumber) {
		this.licWallNumber = licWallNumber;
	}

	public String getLicWallIssueDate() {
		return licWallIssueDate;
	}

	public void setLicWallIssueDate(String licWallIssueDate) {
		this.licWallIssueDate = licWallIssueDate;
	}

	public String getLicWallEndDate() {
		return licWallEndDate;
	}

	public void setLicWallEndDate(String licWallEndDate) {
		this.licWallEndDate = licWallEndDate;
	}

	public Integer getLicWallLandArea() {
		return licWallLandArea;
	}

	public void setLicWallLandArea(Integer licWallLandArea) {
		this.licWallLandArea = licWallLandArea;
	}

	public Integer getLicWallMaxHeight() {
		return licWallMaxHeight;
	}

	public void setLicWallMaxHeight(Integer licWallMaxHeight) {
		this.licWallMaxHeight = licWallMaxHeight;
	}

	public Integer getLicWallManHeight() {
		return licWallManHeight;
	}

	public void setLicWallManHeight(Integer licWallManHeight) {
		this.licWallManHeight = licWallManHeight;
	}

	public String getLicWallNorthBorder() {
		return licWallNorthBorder;
	}

	public void setLicWallNorthBorder(String licWallNorthBorder) {
		this.licWallNorthBorder = licWallNorthBorder;
	}

	public String getLicWallEastBorder() {
		return licWallEastBorder;
	}

	public void setLicWallEastBorder(String licWallEastBorder) {
		this.licWallEastBorder = licWallEastBorder;
	}

	public String getLicWallSouthBorder() {
		return licWallSouthBorder;
	}

	public void setLicWallSouthBorder(String licWallSouthBorder) {
		this.licWallSouthBorder = licWallSouthBorder;
	}
}
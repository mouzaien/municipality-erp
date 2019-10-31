package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

public class FinTemporaryBookingRequest implements Serializable {

	
	@Column(name = "TEMPORAYBOOKINGID")
	private int temporaryBookingId;// record Id

	@Column(name = "YEARID")
	private int year;

	@Column(name = "TEMPORAYBOOKINGSTATUSFLAG")
	private int bookingStatus;

	@Column(name = "TEMPORAYBOOKINGNUMBER")
	private int bookingNumber;
	
	@Column(name = "TEMPORAYBOOKINGDATE")
	private String dateHij;
	
	@Column(name = "TEMPORAYBOOKINGTOTALAMOUNT")
	private int bookingAmount;
	
	@Column(name = "BENIFICARYID")
	private int benifitId;
	
	@Column(name = "TEMPORAYBOOKINGSPENTAMOUNT")
	private int spentAmount;
	
	
	@Column(name = "REQUESTINGUNITID")
	private int requestUnitId;
	
	@Column(name = "BENIFICARYUNITID")
	private int benefitUnitId;
	
	@Column(name = "BENIFICARYLOCATIONID")
	private int locationId;
	     
	@Column(name = "DOCUMENTNUMBER")
	private String documentNumber;
	
	@Column(name = "DOCUMENTDATE")
	private String documentDateHij;
	

	@Column(name = "REPLAYDOCUMENTNUMBER")
	private String replayDocumentNumber;
	
	@Column(name = "REPLAYDOCUMENTDATE")
	private String replayDocumentDateHij;
	
	
	@Column(name = "ISINSTALLMENT")
	private String onePayPackage;
	
	@Column(name = "NOTES")
	private String notes;
	
	@Column(name = "CLOSINGREASON")
	private String closingReason;
	
	@Column(name = "CREATEDBY")
	private String createdBy;
	
	@Column(name = "CREATEDDATE")
	private String createDateHij;
	
	@Column(name = "MODIFIEDBY")
	private String modifiedBy;
	
	@Column(name = "MODIFIEDDATE")
	private String mdifiedDateHij;
	
	
	@Column(name = "CASHEARNINGFLAG")
	private int bookingType;
	@Column(name = "DOC_TYP")
	private int documentType;

	public int getTemporaryBookingId() {
		return temporaryBookingId;
	}
	public void setTemporaryBookingId(int temporaryBookingId) {
		this.temporaryBookingId = temporaryBookingId;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(int bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public int getBookingNumber() {
		return bookingNumber;
	}
	public void setBookingNumber(int bookingNumber) {
		this.bookingNumber = bookingNumber;
	}
	public String getDateHij() {
		return dateHij;
	}
	public void setDateHij(String dateHij) {
		this.dateHij = dateHij;
	}
	public int getBookingAmount() {
		return bookingAmount;
	}
	public void setBookingAmount(int bookingAmount) {
		this.bookingAmount = bookingAmount;
	}
	public int getBenifitId() {
		return benifitId;
	}
	public void setBenifitId(int benifitId) {
		this.benifitId = benifitId;
	}
	public int getSpentAmount() {
		return spentAmount;
	}
	public void setSpentAmount(int spentAmount) {
		this.spentAmount = spentAmount;
	}
	public int getRequestUnitId() {
		return requestUnitId;
	}
	public void setRequestUnitId(int requestUnitId) {
		this.requestUnitId = requestUnitId;
	}
	public int getBenefitUnitId() {
		return benefitUnitId;
	}
	public void setBenefitUnitId(int benefitUnitId) {
		this.benefitUnitId = benefitUnitId;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getDocumentDateHij() {
		return documentDateHij;
	}
	public void setDocumentDateHij(String documentDateHij) {
		this.documentDateHij = documentDateHij;
	}
	public String getReplayDocumentNumber() {
		return replayDocumentNumber;
	}
	public void setReplayDocumentNumber(String replayDocumentNumber) {
		this.replayDocumentNumber = replayDocumentNumber;
	}
	public String getReplayDocumentDateHij() {
		return replayDocumentDateHij;
	}
	public void setReplayDocumentDateHij(String replayDocumentDateHij) {
		this.replayDocumentDateHij = replayDocumentDateHij;
	}
	public String getOnePayPackage() {
		return onePayPackage;
	}
	public void setOnePayPackage(String onePayPackage) {
		this.onePayPackage = onePayPackage;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getClosingReason() {
		return closingReason;
	}
	public void setClosingReason(String closingReason) {
		this.closingReason = closingReason;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreateDateHij() {
		return createDateHij;
	}
	public void setCreateDateHij(String createDateHij) {
		this.createDateHij = createDateHij;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getMdifiedDateHij() {
		return mdifiedDateHij;
	}
	public void setMdifiedDateHij(String mdifiedDateHij) {
		this.mdifiedDateHij = mdifiedDateHij;
	}
	public int getBookingType() {
		return bookingType;
	}
	public void setBookingType(int bookingType) {
		this.bookingType = bookingType;
	}
	public int getDocumentType() {
		return documentType;
	}
	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	} 

	
	
	

}

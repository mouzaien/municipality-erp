package com.bkeryah.bean;

import java.io.InputStream;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class TrdModelClass {

	private int licApplicationId;
	private String licNumber;
	private String licStartDate;
	private String licEndDate;
	private String licExtractionType;
	private String licOwnerId;
	private String licOwnerName;
	private String licMainActivity;
	private String licSubActivities;
	private String licTrdName;
	private String licBuildingOwner;
	private String licTrdAddress;
	private int licTrdArea;
	private int licTotalAdvArea;
	private String licSpecialNumber;
	private int licValidityYears;
	private String licBillstatus;
	private int licApplicationCreatedBy;
	private String licApplicationCreatedByName;
	private String licApplicationCreatedIn;
	private boolean marked;
	private int licApplicationMarkedBy;
	private String licApplicationMarkedByName;
	private String licApplicationMarkedIn;
	private boolean signed;
	private int licApplicationSignedBy;
	private String licApplicationSignedByName;
	private String licApplicationSignedIn;
	private InputStream licTrdPic;
	
	public int getLicApplicationId() {
		return licApplicationId;
	}
	public void setLicApplicationId(int licApplicationId) {
		this.licApplicationId = licApplicationId;
	}
	public String getLicNumber() {
		return licNumber;
	}
	public void setLicNumber(String licNumber) {
		this.licNumber = licNumber;
	}
	public String getLicStartDate() {
		return licStartDate;
	}
	public void setLicStartDate(String licStartDate) {
		this.licStartDate = licStartDate;
	}
	public String getLicEndDate() {
		return licEndDate;
	}
	public void setLicEndDate(String licEndDate) {
		this.licEndDate = licEndDate;
	}
	public String getLicExtractionType() {
		return licExtractionType;
	}
	public void setLicExtractionType(String licExtractionType) {
		this.licExtractionType = licExtractionType;
	}
	public String getLicOwnerId() {
		return licOwnerId;
	}
	public void setLicOwnerId(String licOwnerId) {
		this.licOwnerId = licOwnerId;
	}
	public String getLicOwnerName() {
		return licOwnerName;
	}
	public void setLicOwnerName(String licOwnerName) {
		this.licOwnerName = licOwnerName;
	}
	public String getLicMainActivity() {
		return licMainActivity;
	}
	public void setLicMainActivity(String licMainActivity) {
		this.licMainActivity = licMainActivity;
	}
	public String getLicSubActivities() {
		return licSubActivities;
	}
	public void setLicSubActivities(String licSubActivities) {
		this.licSubActivities = licSubActivities;
	}
	public String getLicTrdName() {
		return licTrdName;
	}
	public void setLicTrdName(String licTrdName) {
		this.licTrdName = licTrdName;
	}
	public String getLicBuildingOwner() {
		return licBuildingOwner;
	}
	public void setLicBuildingOwner(String licBuildingOwner) {
		this.licBuildingOwner = licBuildingOwner;
	}
	public String getLicTrdAddress() {
		return licTrdAddress;
	}
	public void setLicTrdAddress(String licTrdAddress) {
		this.licTrdAddress = licTrdAddress;
	}
	public int getLicTrdArea() {
		return licTrdArea;
	}
	public void setLicTrdArea(int licTrdArea) {
		this.licTrdArea = licTrdArea;
	}
	public int getLicTotalAdvArea() {
		return licTotalAdvArea;
	}
	public void setLicTotalAdvArea(int licTotalAdvArea) {
		this.licTotalAdvArea = licTotalAdvArea;
	}
	public String getLicSpecialNumber() {
		return licSpecialNumber;
	}
	public void setLicSpecialNumber(String licSpecialNumber) {
		this.licSpecialNumber = licSpecialNumber;
	}
	public int getLicValidityYears() {
		return licValidityYears;
	}
	public void setLicValidityYears(int licValidityYears) {
		this.licValidityYears = licValidityYears;
	}
	public String getLicBillstatus() {
		return licBillstatus;
	}
	public void setLicBillstatus(String licBillstatus) {
		this.licBillstatus = licBillstatus;
	}
	public int getLicApplicationCreatedBy() {
		return licApplicationCreatedBy;
	}
	public void setLicApplicationCreatedBy(int licApplicationCreatedBy) {
		this.licApplicationCreatedBy = licApplicationCreatedBy;
	}
	public String getLicApplicationCreatedByName() {
		return licApplicationCreatedByName;
	}
	public void setLicApplicationCreatedByName(String licApplicationCreatedByName) {
		this.licApplicationCreatedByName = licApplicationCreatedByName;
	}
	public String getLicApplicationCreatedIn() {
		return licApplicationCreatedIn;
	}
	public void setLicApplicationCreatedIn(String licApplicationCreatedIn) {
		this.licApplicationCreatedIn = licApplicationCreatedIn;
	}
	public boolean isMarked() {
		return marked;
	}
	public void setMarked(boolean marked) {
		this.marked = marked;
	}
	public int getLicApplicationMarkedBy() {
		return licApplicationMarkedBy;
	}
	public void setLicApplicationMarkedBy(int licApplicationMarkedBy) {
		this.licApplicationMarkedBy = licApplicationMarkedBy;
	}
	public String getLicApplicationMarkedByName() {
		return licApplicationMarkedByName;
	}
	public void setLicApplicationMarkedByName(String licApplicationMarkedByName) {
		this.licApplicationMarkedByName = licApplicationMarkedByName;
	}
	public String getLicApplicationMarkedIn() {
		return licApplicationMarkedIn;
	}
	public void setLicApplicationMarkedIn(String licApplicationMarkedIn) {
		this.licApplicationMarkedIn = licApplicationMarkedIn;
	}
	public boolean isSigned() {
		return signed;
	}
	public void setSigned(boolean signed) {
		this.signed = signed;
	}
	public int getLicApplicationSignedBy() {
		return licApplicationSignedBy;
	}
	public void setLicApplicationSignedBy(int licApplicationSignedBy) {
		this.licApplicationSignedBy = licApplicationSignedBy;
	}
	public String getLicApplicationSignedByName() {
		return licApplicationSignedByName;
	}
	public void setLicApplicationSignedByName(String licApplicationSignedByName) {
		this.licApplicationSignedByName = licApplicationSignedByName;
	}
	public String getLicApplicationSignedIn() {
		return licApplicationSignedIn;
	}
	public void setLicApplicationSignedIn(String licApplicationSignedIn) {
		this.licApplicationSignedIn = licApplicationSignedIn;
	}
	public InputStream getLicTrdPic() {
		return licTrdPic;
	}
	public void setLicTrdPic(InputStream licTrdPic) {
		this.licTrdPic = licTrdPic;
	}
	public StreamedContent getLicTrdPicContent() {
		return new DefaultStreamedContent(licTrdPic, "image/jpeg");
	}
	
	
	
	
}

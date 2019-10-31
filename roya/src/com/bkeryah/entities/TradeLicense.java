package com.bkeryah.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "LIC_TRD_MASTER_FILE")
public class TradeLicense {
	@Id
	@Column(name = "ID")
	private Integer id;

	@Column(name = "LIC_NO")
	private String number;

	@Column(name = "LIC_DT_BGN_H")
	private String beginHDate;

	@Column(name = "LIC_DT_END_H")
	private String endHdate;

	@Column(name = "MHL_ID")
	private String mhlId;
	
	@Column(name = "OWN_TYP")
	private Integer ownTyp;
	
	@Column(name = "END_CONTRACT_DT_H")
	private String contractEndHDate;
	
	@Column(name = "TRD_RECORD_NO")
	private String trdRecordNo;
	@Column(name = "TRD_RECORD_DT_H")
	private String trdRecordDtH;
	@Column(name = "TRD_RECORD_PLC")
	private String trdRecordPlc;
	
	
	@Column(name = "TRD_NAME")
	private String name;
	
	@Column(name = "LIC_YRS_NUM")
	private Integer years;
	
	@Column(name = "APL_DT_HJ")
	private String applicationHDate;
	
	@Column(name = "APL_OWNER")
	private String  ownerNationalId;
	
	@Column(name = "WATCHING_ID")
	private Integer watchingId;
	
	@Column(name = "LIC_DT")
	private String date;
	
	@Column(name = "AQR_OWNER_NAME")
	private String buildingOwnerName;
	
	@Column(name = "LIC_STS")
	private Integer status;
	
	@Column(name = "LIC_BLDNO")
	private String licBldno;
	
	@Column(name = "MAIN_ACTV")
	private Integer mainActivity;
	@Column(name = "MHL_ADDRS")
	private String address;
	@Column(name = "NOTES")
	private String notes;
	@Column(name = "WROTE_BY")
	private Integer wroteBy;
	@Column(name = "WROTE_IN")
	private String wroteIn;
	@Column(name = "MARKED_BY")
	private Integer markedBy;
	@Column(name = "MARKED_IN")
	private String markedIn;
	@Column(name = "SIGNED_BY")
	private Integer signedBy;
	@Column(name = "SIGNED_IN")
	private String signedIn;
	@Column(name = "PRINTED_BY")
	private Integer printedBy;
	@Column(name = "PRINTED_IN")
	private String printedIn;
	
	
	@Column(name = "TRD_AREA")
	private Float area;
	@Column(name = "ADV_AREA")
	private Float advArea;
	
	@Column(name = "SAK_NO")
	private String sakNumber;
	@Column(name = "SAK_SOURCE")
	private String sakSource;
	@Column(name = "IS_AGENT_Y_N")
	private String isAgentYN;
	@Column(name = "AGENT_NO")
	private String agentNo;
	@Column(name = "AGENT_DATE")
	private String agentDate;
	@Column(name = "AGENT_SOURCE")
	private String agentSource;
	@Column(name = "AGENT_ID")
	private String agentId;
	@Column(name = "RENTAL_NAME")
	private String rentalName;
	@Column(name = "RENTAL_ADDRS")
	private String rentalAddress;
	@Column(name = "FINE")
	private Double fine;
	@Column(name = "IS_ACTIVE_Y_N")
	private String isActiveYN;
	@Column(name = "OLD_ID")
	private Integer oldId;
	@Column(name = "LIC_CANCEL_DATE")
	private String cancellationDate;
	@Column(name = "JOB")
	private Integer job;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getBeginHDate() {
		return beginHDate;
	}
	public void setBeginHDate(String beginHDate) {
		this.beginHDate = beginHDate;
	}
	public String getEndHdate() {
		return endHdate;
	}
	public void setEndHdate(String endHdate) {
		this.endHdate = endHdate;
	}
	public String getMhlId() {
		return mhlId;
	}
	public void setMhlId(String mhlId) {
		this.mhlId = mhlId;
	}
	public Integer getOwnTyp() {
		return ownTyp;
	}
	public void setOwnTyp(Integer ownTyp) {
		this.ownTyp = ownTyp;
	}
	public String getContractEndHDate() {
		return contractEndHDate;
	}
	public void setContractEndHDate(String contractEndHDate) {
		this.contractEndHDate = contractEndHDate;
	}
	public String getTrdRecordNo() {
		return trdRecordNo;
	}
	public void setTrdRecordNo(String trdRecordNo) {
		this.trdRecordNo = trdRecordNo;
	}
	public String getTrdRecordDtH() {
		return trdRecordDtH;
	}
	public void setTrdRecordDtH(String trdRecordDtH) {
		this.trdRecordDtH = trdRecordDtH;
	}
	public String getTrdRecordPlc() {
		return trdRecordPlc;
	}
	public void setTrdRecordPlc(String trdRecordPlc) {
		this.trdRecordPlc = trdRecordPlc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getYears() {
		return years;
	}
	public void setYears(Integer years) {
		this.years = years;
	}
	public String getApplicationHDate() {
		return applicationHDate;
	}
	public void setApplicationHDate(String applicationHDate) {
		this.applicationHDate = applicationHDate;
	}
	public String getOwnerNationalId() {
		return ownerNationalId;
	}
	public void setOwnerNationalId(String ownerNationalId) {
		this.ownerNationalId = ownerNationalId;
	}
	public Integer getWatchingId() {
		return watchingId;
	}
	public void setWatchingId(Integer watchingId) {
		this.watchingId = watchingId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getBuildingOwnerName() {
		return buildingOwnerName;
	}
	public void setBuildingOwnerName(String buildingOwnerName) {
		this.buildingOwnerName = buildingOwnerName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getLicBldno() {
		return licBldno;
	}
	public void setLicBldno(String licBldno) {
		this.licBldno = licBldno;
	}
	public Integer getMainActivity() {
		return mainActivity;
	}
	public void setMainActivity(Integer mainActivity) {
		this.mainActivity = mainActivity;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Integer getWroteBy() {
		return wroteBy;
	}
	public void setWroteBy(Integer wroteBy) {
		this.wroteBy = wroteBy;
	}
	public String getWroteIn() {
		return wroteIn;
	}
	public void setWroteIn(String wroteIn) {
		this.wroteIn = wroteIn;
	}
	public Integer getMarkedBy() {
		return markedBy;
	}
	public void setMarkedBy(Integer markedBy) {
		this.markedBy = markedBy;
	}
	public String getMarkedIn() {
		return markedIn;
	}
	public void setMarkedIn(String markedIn) {
		this.markedIn = markedIn;
	}
	public Integer getSignedBy() {
		return signedBy;
	}
	public void setSignedBy(Integer signedBy) {
		this.signedBy = signedBy;
	}
	public String getSignedIn() {
		return signedIn;
	}
	public void setSignedIn(String signedIn) {
		this.signedIn = signedIn;
	}
	public Integer getPrintedBy() {
		return printedBy;
	}
	public void setPrintedBy(Integer printedBy) {
		this.printedBy = printedBy;
	}
	public String getPrintedIn() {
		return printedIn;
	}
	public void setPrintedIn(String printedIn) {
		this.printedIn = printedIn;
	}
	public Float getArea() {
		return area;
	}
	public void setArea(Float area) {
		this.area = area;
	}
	public Float getAdvArea() {
		return advArea;
	}
	public void setAdvArea(Float advArea) {
		this.advArea = advArea;
	}
	public String getSakNumber() {
		return sakNumber;
	}
	public void setSakNumber(String sakNumber) {
		this.sakNumber = sakNumber;
	}
	public String getSakSource() {
		return sakSource;
	}
	public void setSakSource(String sakSource) {
		this.sakSource = sakSource;
	}
	public String getIsAgentYN() {
		return isAgentYN;
	}
	public void setIsAgentYN(String isAgentYN) {
		this.isAgentYN = isAgentYN;
	}
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	public String getAgentDate() {
		return agentDate;
	}
	public void setAgentDate(String agentDate) {
		this.agentDate = agentDate;
	}
	public String getAgentSource() {
		return agentSource;
	}
	public void setAgentSource(String agentSource) {
		this.agentSource = agentSource;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getRentalName() {
		return rentalName;
	}
	public void setRentalName(String rentalName) {
		this.rentalName = rentalName;
	}
	public String getRentalAddress() {
		return rentalAddress;
	}
	public void setRentalAddress(String rentalAddress) {
		this.rentalAddress = rentalAddress;
	}
	public Double getFine() {
		return fine;
	}
	public void setFine(Double fine) {
		this.fine = fine;
	}
	public String getIsActiveYN() {
		return isActiveYN;
	}
	public void setIsActiveYN(String isActiveYN) {
		this.isActiveYN = isActiveYN;
	}
	public Integer getOldId() {
		return oldId;
	}
	public void setOldId(Integer oldId) {
		this.oldId = oldId;
	}
	public String getCancellationDate() {
		return cancellationDate;
	}
	public void setCancellationDate(String cancellationDate) {
		this.cancellationDate = cancellationDate;
	}
	public Integer getJob() {
		return job;
	}
	public void setJob(Integer job) {
		this.job = job;
	}
	
	
	
}
package com.bkeryah.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TDR_INV_GNRL_REQ")
public class ExchangeRequest {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "INV_G_R_NO")
	private Integer generalrequestNumber;
	@Column(name = "INV_G_R_DATE")
	private String generalrequestDate;
	@Column(name = "SECTIONID")
	private Integer sectionId;
	@Column(name = "R_TO")
	private Integer requestTo;
	@Column(name = "INV_G_R_STATE")
	private Integer generalRequestState;
	@Column(name = "USERID")
	private Integer userId;
	@Column(name = "YEARID")
	private Integer yearId;
	@Column(name = "NOTES")
	private String notes;
	@Column(name = "TYPE_REQ")
	private Integer requestType;
	@Column(name = "ACCEPT_Y_N")
	private String status;
	@Column(name = "SERIALNUMBER")
	private Integer serialNumber;
	@Transient
	private Integer stockNo;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "exchangeRequest", cascade = CascadeType.ALL)
	private List<ExchangeRequestDetails> exchangeRequestDetailsList;
	@Transient
	private ArcUsers arcUser;
	@Transient
	private String empName;

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Integer getGeneralrequestNumber() {
		return generalrequestNumber;
	}

	public void setGeneralrequestNumber(Integer generalrequestNumber) {
		this.generalrequestNumber = generalrequestNumber;
	}

	public String getGeneralrequestDate() {
		return generalrequestDate;
	}

	public void setGeneralrequestDate(String generalrequestDate) {
		this.generalrequestDate = generalrequestDate;
	}

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public Integer getRequestTo() {
		return requestTo;
	}

	public void setRequestTo(Integer requestTo) {
		this.requestTo = requestTo;
	}

	public Integer getGeneralRequestState() {
		return generalRequestState;
	}

	public void setGeneralRequestState(Integer generalRequestState) {
		this.generalRequestState = generalRequestState;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getYearId() {
		return yearId;
	}

	public void setYearId(Integer yearId) {
		this.yearId = yearId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getRequestType() {
		return requestType;
	}

	public ArcUsers getArcUser() {
		return arcUser;
	}

	public void setArcUser(ArcUsers arcUser) {
		this.arcUser = arcUser;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setRequestType(Integer requestType) {
		this.requestType = requestType;
	}

	public void setExchangeRequestDetailsList(List<ExchangeRequestDetails> exchangeRequestDetailsList) {
		this.exchangeRequestDetailsList = exchangeRequestDetailsList;
	}

	public List<ExchangeRequestDetails> getExchangeRequestDetailsList() {
		return exchangeRequestDetailsList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getStockNo() {
		return stockNo;
	}

	public void setStockNo(Integer stockNo) {
		this.stockNo = stockNo;
	}

}

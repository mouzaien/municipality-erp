package com.bkeryah.penalties;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "REQ_FINES_MASTER")
public class ReqFinesMaster {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "FINE_NO")
	private Integer fineNo;
	@Column(name = "FINE_DATE")
	private String fineDate;
	@Column(name = "F_DEPT_NO")
	private String fDeptNo;
	@Column(name = "F_NAME")
	private String fName;
	@Column(name = "F_ID_NO")
	private String fIdNo;
	@Column(name = "F_ID_ISSUE_DATE")
	private String fIdIssueDate;
	@Column(name = "F_ID_SOURCE")
	private String fIdSource;
	@Column(name = "F_LICENCE_NO")
	private String fLicenceNo;
	@Column(name = "F_ADDRESS")
	private String fAddress;
	@Column(name = "F_KEYCODE")
	private String fKeycode;
	@Column(name = "F_MAP_NO")
	private String fMapNo;
	@Column(name = "F_BLOCK_NO")
	private String fBlockNo;
	@Column(name = "F_DISTRICT_NO")
	private String fDistrictNo;
	@Column(name = "F_FINE_DESC")
	private String fFineDesc;
	@Column(name = "F_SUPERVISOR_CODE")
	private String fSupervisorCode;
	@Column(name = "F_REPORT_NO")
	private String fReportNo;
	@Column(name = "F_LETTER_NO")
	private String fLetterNo;
	@Column(name = "F_CLOSE_DATE")
	private String fCloseDate;
	@Column(name = "F_REOPEN_DATE")
	private String fReopenDate;
	@Column(name = "F_TRADE_MARK_NAME")
	private String fTradeMarkName;
	@Column(name = "F_LIC_START_DATE")
	private String fLicStartDate;
	@Column(name = "F_LIC_END_DATE")
	private String fLicEndDate;
	@Column(name = "F_FINE_CASE")
	private Long fFineCase;
	@Column(name = "ACTIVITY_CODE")
	private Integer activityCode;
	@Column(name = "ACTIVITY_TYPE")
	private Integer activityType;
	@Column(name = "BUILD_TYPE")
	private Long buildType;
	@Column(name = "DATA_ENTRY")
	private Long dataEntry;
	@Column(name = "ENTRY_DATE")
	private String entryDate;
	@Column(name = "MGR_DEPEND_DATE")
	private String mgrDependDate;
	@Column(name = "MGR_DEPEND_ID")
	private Long mgrDependId;
	//0:normal penality, 1:from notification that need agreement
	@Column(name = "TYPE")
	private Integer type;
	@OneToMany(mappedBy = "reqFinesMaster", fetch=FetchType.EAGER)
	private List<ReqFinesDetails> reqFinesDetailsList;
	//@Transient
	@Formula("(select d.DEPT_NAME from WRK_DEPT d where d.id = F_DEPT_NO)")
	private String deptName;
	@Transient
	private String supervisorName;
	@Transient
	private Double totalValue;
	
	@Transient
	private Integer billNo;
	
	public Integer getFineNo() {
		return fineNo;
	}

	public void setFineNo(Integer fineNo) {
		this.fineNo = fineNo;
	}

	public String getFineDate() {
		return fineDate;
	}

	public void setFineDate(String fineDate) {
		this.fineDate = fineDate;
	}

	public String getfDeptNo() {
		return fDeptNo;
	}

	public void setfDeptNo(String fDeptNo) {
		this.fDeptNo = fDeptNo;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getfIdNo() {
		return fIdNo;
	}

	public void setfIdNo(String fIdNo) {
		this.fIdNo = fIdNo;
	}

	public String getfIdIssueDate() {
		return fIdIssueDate;
	}

	public void setfIdIssueDate(String fIdIssueDate) {
		this.fIdIssueDate = fIdIssueDate;
	}

	public String getfIdSource() {
		return fIdSource;
	}

	public void setfIdSource(String fIdSource) {
		this.fIdSource = fIdSource;
	}

	public String getfLicenceNo() {
		return fLicenceNo;
	}

	public void setfLicenceNo(String fLicenceNo) {
		this.fLicenceNo = fLicenceNo;
	}

	public String getfAddress() {
		return fAddress;
	}

	public void setfAddress(String fAddress) {
		this.fAddress = fAddress;
	}

	public String getfKeycode() {
		return fKeycode;
	}

	public void setfKeycode(String fKeycode) {
		this.fKeycode = fKeycode;
	}

	public String getfMapNo() {
		return fMapNo;
	}

	public void setfMapNo(String fMapNo) {
		this.fMapNo = fMapNo;
	}

	public String getfBlockNo() {
		return fBlockNo;
	}

	public void setfBlockNo(String fBlockNo) {
		this.fBlockNo = fBlockNo;
	}

	public String getfDistrictNo() {
		return fDistrictNo;
	}

	public void setfDistrictNo(String fDistrictNo) {
		this.fDistrictNo = fDistrictNo;
	}

	public String getfFineDesc() {
		return fFineDesc;
	}

	public void setfFineDesc(String fFineDesc) {
		this.fFineDesc = fFineDesc;
	}

	public String getfSupervisorCode() {
		return fSupervisorCode;
	}

	public void setfSupervisorCode(String fSupervisorCode) {
		this.fSupervisorCode = fSupervisorCode;
	}

	public String getfReportNo() {
		return fReportNo;
	}

	public void setfReportNo(String fReportNo) {
		this.fReportNo = fReportNo;
	}

	public String getfLetterNo() {
		return fLetterNo;
	}

	public void setfLetterNo(String fLetterNo) {
		this.fLetterNo = fLetterNo;
	}

	public String getfCloseDate() {
		return fCloseDate;
	}

	public void setfCloseDate(String fCloseDate) {
		this.fCloseDate = fCloseDate;
	}

	public String getfReopenDate() {
		return fReopenDate;
	}

	public void setfReopenDate(String fReopenDate) {
		this.fReopenDate = fReopenDate;
	}

	public String getfTradeMarkName() {
		return fTradeMarkName;
	}

	public void setfTradeMarkName(String fTradeMarkName) {
		this.fTradeMarkName = fTradeMarkName;
	}

	public String getfLicStartDate() {
		return fLicStartDate;
	}

	public void setfLicStartDate(String fLicStartDate) {
		this.fLicStartDate = fLicStartDate;
	}

	public String getfLicEndDate() {
		return fLicEndDate;
	}

	public void setfLicEndDate(String fLicEndDate) {
		this.fLicEndDate = fLicEndDate;
	}

	public Long getfFineCase() {
		return fFineCase;
	}

	public void setfFineCase(Long fFineCase) {
		this.fFineCase = fFineCase;
	}

	public Integer getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(Integer activityCode) {
		this.activityCode = activityCode;
	}

	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	public Long getBuildType() {
		return buildType;
	}

	public void setBuildType(Long buildType) {
		this.buildType = buildType;
	}

	public Long getDataEntry() {
		return dataEntry;
	}

	public void setDataEntry(Long dataEntry) {
		this.dataEntry = dataEntry;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getMgrDependDate() {
		return mgrDependDate;
	}

	public void setMgrDependDate(String mgrDependDate) {
		this.mgrDependDate = mgrDependDate;
	}

	public Long getMgrDependId() {
		return mgrDependId;
	}

	public void setMgrDependId(Long mgrDependId) {
		this.mgrDependId = mgrDependId;
	}

	public List<ReqFinesDetails> getReqFinesDetailsList() {
		return reqFinesDetailsList;
	}

	public void setReqFinesDetailsList(List<ReqFinesDetails> reqFinesDetailsList) {
		this.reqFinesDetailsList = reqFinesDetailsList;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	public Double getTotalValue() {
		double sum = 0;
		if(reqFinesDetailsList != null){
			for(ReqFinesDetails det : reqFinesDetailsList)
				sum += det.getFineTotalValue();
		}
		return sum;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getBillNo() {
		return billNo;
	}

	public void setBillNo(Integer billNo) {
		this.billNo = billNo;
	}

}
package com.bkeryah.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.bkeryah.hr.entities.HrsGovJobSeries;

@Entity
@Table(name = "HRS_JOB_HISTORICAL")
public class HrsJobHistorical {

//	 @Id
	// @GenericGenerator(name = "generator", strategy = "increment")
	// @GeneratedValue(generator = "generator")
	
//	private Integer empno;
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "JOB_SEQ_NO")
	private Integer jobSequnce;
	@Column(name = "EMPNO")
	private Integer employerNumber;
	@Column(name = "SERIAL")
	private Integer serial;
	@Column(name = "RANKCOD")
	private Integer rankCode;
	@Column(name = "CLSSCOD")
	private Integer classCode;
	@Column(name = "INCOMNO")
	private Integer incomeNumber;
	@Column(name = "DEPTCOD")
	private Integer departmentNumber;
	@Column(name = "JOBNO")
	private Integer jobNumber;
	@Column(name = "JOBYEAR")
	private Integer jobYear;

	
	@Column(name = "EXEDATE")
	private String exeDate;
	@Column(name = "BSCSQL")
	private Integer basicSalary;
	@Column(name = "TRANS")
	private Integer transportSalary;
	@Column(name = "MANDIN")
	private Integer mandatoryIn;
	@Column(name = "MANDOUT")
	private Integer mandatoryOut;
	@Column(name = "INCOMEYEAR")
	private Integer incomeYear;
	@Column(name = "EXCNO")
	private String executeNumber;
	@Column(name = "EXCYEAR")
	private Integer executeYear;
	@Column(name = "JOBSTS")
	private Integer jobStatus;
	@Column(name = "JOBACTN")
	private Integer jobAction;
	@Column(name = "EXCDATE")
	private String executedate;
	@Column(name = "CBY")
	private Integer createdBy;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CBY", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private ArcUsers arcUser;
	@Column(name = "CIN")
	private Date createDate;

	@Column(name = "JOBCOD")
	private String jobCode;
	@Column(name = "REF_SEQ")
	private Integer refSeq;
	@ManyToOne
	@JoinColumn(name = "JOBCOD", referencedColumnName = "ID", insertable = false, updatable = false)
	private HrsGovJob4 govJob4;

	public Integer getJobSequnce() {
		return jobSequnce;
	}
	public void setJobSequnce(Integer jobSequnce) {
		this.jobSequnce = jobSequnce;
	}
	public Integer getEmployerNumber() {
		return employerNumber;
	}
	public void setEmployerNumber(Integer employerNumber) {
		this.employerNumber = employerNumber;
	}
	public Integer getSerial() {
		return serial;
	}
	public void setSerial(Integer serial) {
		this.serial = serial;
	}
	public Integer getRankCode() {
		return rankCode;
	}
	public void setRankCode(Integer rankCode) {
		this.rankCode = rankCode;
	}
	public Integer getClassCode() {
		return classCode;
	}
	public void setClassCode(Integer classCode) {
		this.classCode = classCode;
	}
	public Integer getIncomeNumber() {
		return incomeNumber;
	}
	public void setIncomeNumber(Integer incomeNumber) {
		this.incomeNumber = incomeNumber;
	}
	public Integer getDepartmentNumber() {
		return departmentNumber;
	}
	public void setDepartmentNumber(Integer departmentNumber) {
		this.departmentNumber = departmentNumber;
	}
	public Integer getJobNumber() {
		return jobNumber;
	}
	public void setJobNumber(Integer jobNumber) {
		this.jobNumber = jobNumber;
	}
	public Integer getJobYear() {
		return jobYear;
	}
	public void setJobYear(Integer jobYear) {
		this.jobYear = jobYear;
	}
	public String getExeDate() {
		return exeDate;
	}
	public void setExeDate(String exeDate) {
		this.exeDate = exeDate;
	}
	public Integer getBasicSalary() {
		return basicSalary;
	}
	public void setBasicSalary(Integer basicSalary) {
		this.basicSalary = basicSalary;
	}
	public Integer getTransportSalary() {
		return transportSalary;
	}
	public void setTransportSalary(Integer transportSalary) {
		this.transportSalary = transportSalary;
	}
	public Integer getMandatoryIn() {
		return mandatoryIn;
	}
	public void setMandatoryIn(Integer mandatoryIn) {
		this.mandatoryIn = mandatoryIn;
	}
	public Integer getMandatoryOut() {
		return mandatoryOut;
	}
	public void setMandatoryOut(Integer mandatoryOut) {
		this.mandatoryOut = mandatoryOut;
	}
	public Integer getIncomeYear() {
		return incomeYear;
	}
	public void setIncomeYear(Integer incomeYear) {
		this.incomeYear = incomeYear;
	}
	public String getExecuteNumber() {
		return executeNumber;
	}
	public void setExecuteNumber(String executeNumber) {
		this.executeNumber = executeNumber;
	}
	public Integer getExecuteYear() {
		return executeYear;
	}
	public void setExecuteYear(Integer executeYear) {
		this.executeYear = executeYear;
	}
	public Integer getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(Integer jobStatus) {
		this.jobStatus = jobStatus;
	}
	public Integer getJobAction() {
		return jobAction;
	}
	public void setJobAction(Integer jobAction) {
		this.jobAction = jobAction;
	}
	public String getExecutedate() {
		return executedate;
	}
	public void setExecutedate(String executedate) {
		this.executedate = executedate;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public ArcUsers getArcUser() {
		return arcUser;
	}
	public void setArcUser(ArcUsers arcUser) {
		this.arcUser = arcUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	public HrsGovJob4 getGovJob4() {
		return govJob4;
	}
	public void setGovJob4(HrsGovJob4 govJob4) {
		this.govJob4 = govJob4;
	}
	public Integer getRefSeq() {
		return refSeq;
	}
	public void setRefSeq(Integer refSeq) {
		this.refSeq = refSeq;
	}

	
}

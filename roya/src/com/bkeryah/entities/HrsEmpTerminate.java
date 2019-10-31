package com.bkeryah.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "HRS_EMP_TRMN")
public class HrsEmpTerminate {

	@Id
//	@GenericGenerator(name = "generator", strategy = "increment")
//	@GeneratedValue(generator = "generator")
//	@Column(name = "ID")
//	private Integer id;
	@Column(name = "EMPNO")
	private Integer employeNumber;
	@Column(name = "INCOMENO")
	private Integer incomeNumber;
	@Column(name = "INCOMEYEAR")
	private Integer incomeYear;
	@Column(name = "EXSNO")
	private Integer executedNumber;
	@Column(name = "EXCYEAR")
	private Integer executedYear;
	@Column(name = "TRMNRSN")
	private Integer terminateReason;
	@Column(name = "PAYSTS")
	private Integer payStatus;
	@Column(name = "PAYTYP")
	private Integer payType;
	@Column(name = "MNTH")
	private Integer terminatedMonth;
	@Column(name = "NATVAL")
	private Integer natevalue;
	@Column(name = "CBY")
	private Integer createdBy;
	@Column(name = "INCOMEDT")
	private String incomeDateHijri;
	@Column(name = "EXCDT")
	private String executedDate;
	@Column(name = "NOTE")
	private String terminateNote;
	@Column(name = "RWRDI")
	private String rewardGive;
	@Column(name = "VAEI")
	private String vacationGive;
	@Column(name = "TRNSI")
	private String salaryGive;
	@Column(name = "CIN")
	private Date createdIn;
	@OneToOne
	@JoinColumn(name = "EMPNO", referencedColumnName = "EMPNO", insertable = false, updatable = false)
	private HrsMasterFile employer;
	
	public Integer getEmployeNumber() {
		return employeNumber;
	}
	public void setEmployeNumber(Integer employeNumber) {
		this.employeNumber = employeNumber;
	}
	public Integer getIncomeNumber() {
		return incomeNumber;
	}
	public void setIncomeNumber(Integer incomeNumber) {
		this.incomeNumber = incomeNumber;
	}
	public Integer getIncomeYear() {
		return incomeYear;
	}
	public void setIncomeYear(Integer incomeYear) {
		this.incomeYear = incomeYear;
	}
	public Integer getExecutedNumber() {
		return executedNumber;
	}
	public void setExecutedNumber(Integer executedNumber) {
		this.executedNumber = executedNumber;
	}
	public Integer getExecutedYear() {
		return executedYear;
	}
	public void setExecutedYear(Integer executedYear) {
		this.executedYear = executedYear;
	}
	public Integer getTerminateReason() {
		return terminateReason;
	}
	public void setTerminateReason(Integer terminateReason) {
		this.terminateReason = terminateReason;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Integer getTerminatedMonth() {
		return terminatedMonth;
	}
	public void setTerminatedMonth(Integer terminatedMonth) {
		this.terminatedMonth = terminatedMonth;
	}
	public Integer getNatevalue() {
		return natevalue;
	}
	public void setNatevalue(Integer natevalue) {
		this.natevalue = natevalue;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public String getIncomeDateHijri() {
		return incomeDateHijri;
	}
	public void setIncomeDateHijri(String incomeDateHijri) {
		this.incomeDateHijri = incomeDateHijri;
	}
	public String getExecutedDate() {
		return executedDate;
	}
	public void setExecutedDate(String executedDate) {
		this.executedDate = executedDate;
	}
	public String getTerminateNote() {
		return terminateNote;
	}
	public void setTerminateNote(String terminateNote) {
		this.terminateNote = terminateNote;
	}
	public String getRewardGive() {
		return rewardGive;
	}
	public void setRewardGive(String rewardGive) {
		this.rewardGive = rewardGive;
	}
	public String getVacationGive() {
		return vacationGive;
	}
	public void setVacationGive(String vacationGive) {
		this.vacationGive = vacationGive;
	}
	public String getSalaryGive() {
		return salaryGive;
	}
	public void setSalaryGive(String salaryGive) {
		this.salaryGive = salaryGive;
	}
	public Date getCreatedIn() {
		return createdIn;
	}
	public void setCreatedIn(Date createdIn) {
		this.createdIn = createdIn;
	}
//	public Integer getId() {
//		return id;
//	}
//	public void setId(Integer id) {
//		this.id = id;
//	}
	public HrsMasterFile getEmployer() {
		return employer;
	}
	public void setEmployer(HrsMasterFile employer) {
		this.employer = employer;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

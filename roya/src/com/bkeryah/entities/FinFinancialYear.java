package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIN_FINANCIALYEAR")
public class FinFinancialYear implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "YEARID")
	private int YEARID;
	@Column(name = "YEARNAME")
	private String yearName;
	@Column(name = "STARTDATE")
	private String startDateHij;
	@Column(name = "ENDDATE")
	private String endDateHij;

	@Column(name = "YEARSTATUS")
	private int yearStatus;

	@Column(name = "CURRENTPERIODNUMBER")
	private Integer currentPeriodNumber;
	@Column(name = "ACCOUNTSCHARTSTATUS")
	private Integer accountsChartStatus;
	@Column(name = "BUDGETSTATUS")
	private Integer budgetStatus;
	@Column(name = "EXPENSESLASTDATE")
	private String expensesLastDate;
	@Column(name = "SETTLEMENTLASTDATE")
	private String setTlementLastDate;
	@Column(name = "GEORGANYEAR")
	private String georganYear;
	@Column(name = "CREATEDBY")
	private String createdBy;
	@Column(name = "CREATEDDATE")
	private String createdDate;
	@Column(name = "MODIFIEDBY")
	private String modifiedBy;
	@Column(name = "MODIFIEDDATE")
	private String modifiedDate;
	
	public int getYEARID() {
		return YEARID;
	}
	public void setYEARID(int yEARID) {
		YEARID = yEARID;
	}
	public String getYearName() {
		return yearName;
	}
	public void setYearName(String yearName) {
		this.yearName = yearName;
	}
	public String getStartDateHij() {
		return startDateHij;
	}
	public void setStartDateHij(String startDateHij) {
		this.startDateHij = startDateHij;
	}
	public String getEndDateHij() {
		return endDateHij;
	}
	public void setEndDateHij(String endDateHij) {
		this.endDateHij = endDateHij;
	}
	public int getYearStatus() {
		return yearStatus;
	}
	public void setYearStatus(int yearStatus) {
		this.yearStatus = yearStatus;
	}
	public Integer getCurrentPeriodNumber() {
		return currentPeriodNumber;
	}
	public void setCurrentPeriodNumber(Integer currentPeriodNumber) {
		this.currentPeriodNumber = currentPeriodNumber;
	}
	public Integer getAccountsChartStatus() {
		return accountsChartStatus;
	}
	public void setAccountsChartStatus(Integer accountsChartStatus) {
		this.accountsChartStatus = accountsChartStatus;
	}
	public Integer getBudgetStatus() {
		return budgetStatus;
	}
	public void setBudgetStatus(Integer budgetStatus) {
		this.budgetStatus = budgetStatus;
	}
	public String getExpensesLastDate() {
		return expensesLastDate;
	}
	public void setExpensesLastDate(String expensesLastDate) {
		this.expensesLastDate = expensesLastDate;
	}
	public String getSetTlementLastDate() {
		return setTlementLastDate;
	}
	public void setSetTlementLastDate(String setTlementLastDate) {
		this.setTlementLastDate = setTlementLastDate;
	}
	public String getGeorganYear() {
		return georganYear;
	}
	public void setGeorganYear(String georganYear) {
		this.georganYear = georganYear;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	
	
		
	
}

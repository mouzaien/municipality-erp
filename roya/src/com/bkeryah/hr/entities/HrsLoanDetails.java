package com.bkeryah.hr.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "HRS_LOAN_DETAILS")
public class HrsLoanDetails {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "LOAN_DET_ID")
	private Integer loanDetId;
	@Column(name = "LOAN_ID")
	private Integer loanId;
	@Column(name = "LOAN_DET_STS_P_U")
	private String loanDetStsPU;
	@Column(name = "LOAN_DET_MONTH")
	private Integer loanDetMonth;
	@Column(name = "LOAN_DET_YEAR")
	private Integer loanDetYear;
	@Column(name = "LOAN_PAID_MONTLY")
	private Integer loanPaidMontly;
	@Column(name = "UP_BY")
	private Integer upBy;

	public Integer getLoanDetId() {
		return loanDetId;
	}

	public void setLoanDetId(Integer loanDetId) {
		this.loanDetId = loanDetId;
	}

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public String getLoanDetStsPU() {
		return loanDetStsPU;
	}

	public void setLoanDetStsPU(String loanDetStsPU) {
		this.loanDetStsPU = loanDetStsPU;
	}

	public Integer getLoanDetMonth() {
		return loanDetMonth;
	}

	public void setLoanDetMonth(Integer loanDetMonth) {
		this.loanDetMonth = loanDetMonth;
	}

	public Integer getLoanDetYear() {
		return loanDetYear;
	}

	public void setLoanDetYear(Integer loanDetYear) {
		this.loanDetYear = loanDetYear;
	}

	public Integer getLoanPaidMontly() {
		return loanPaidMontly;
	}

	public void setLoanPaidMontly(Integer loanPaidMontly) {
		this.loanPaidMontly = loanPaidMontly;
	}

	public Integer getUpBy() {
		return upBy;
	}

	public void setUpBy(Integer upBy) {
		this.upBy = upBy;
	}
}
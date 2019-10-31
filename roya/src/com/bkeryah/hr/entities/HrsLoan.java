package com.bkeryah.hr.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import com.bkeryah.entities.ArcUsersExtension;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Column;

@Entity
@Table(name = "HRS_LOAN")
public class HrsLoan {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "LOAN_ID")
	private Integer loanId;
	@Column(name = "LOAN_EMPNO")
	private Integer loanEmpno;
	@Column(name = "LOAN_VALUE")
	private Integer loanValue;
	@Column(name = "LOAN_TYPE_ID")
	private Integer loanTypeId;
	@Column(name = "LOAN_STRT_MONTH")
	private Integer loanStrtMonth;
	@Column(name = "LOAN_STRT_YEAR")
	private Integer loanStrtYear;
	@Column(name = "LOAN_MONTHLY_PAYMENT")
	private Integer loanMonthlyPayment;
	@Column(name = "LOAN_STS_A_S_E")
	private String loanStsASE;
	@Column(name = "LOAN_MONTHLY_COUNT")
	private Integer loanMonthlyCount;
	@Column(name = "LOAN_FIRST_PAYMENT")
	private Integer loanFirstPayment;
	@Column(name = "LOAN_LAST_PAYMENT")
	private Integer loanLastPayment;
	@Column(name = "LOAN_NAME")
	private String loanName;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LOAN_TYPE_ID", referencedColumnName = "LOAN_TYPE_ID", insertable = false, updatable = false)
	private HrsLoanType bank;

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public Integer getLoanEmpno() {
		return loanEmpno;
	}

	public void setLoanEmpno(Integer loanEmpno) {
		this.loanEmpno = loanEmpno;
	}

	public Integer getLoanValue() {
		return loanValue;
	}

	public void setLoanValue(Integer loanValue) {
		this.loanValue = loanValue;
	}

	public Integer getLoanTypeId() {
		return loanTypeId;
	}

	public void setLoanTypeId(Integer loanTypeId) {
		this.loanTypeId = loanTypeId;
	}

	public Integer getLoanStrtMonth() {
		return loanStrtMonth;
	}

	public void setLoanStrtMonth(Integer loanStrtMonth) {
		this.loanStrtMonth = loanStrtMonth;
	}

	public Integer getLoanStrtYear() {
		return loanStrtYear;
	}

	public void setLoanStrtYear(Integer loanStrtYear) {
		this.loanStrtYear = loanStrtYear;
	}

	public Integer getLoanMonthlyPayment() {
		return loanMonthlyPayment;
	}

	public void setLoanMonthlyPayment(Integer loanMonthlyPayment) {
		this.loanMonthlyPayment = loanMonthlyPayment;
	}

	public String getLoanStsASE() {
		return loanStsASE;
	}

	public void setLoanStsASE(String loanStsASE) {
		this.loanStsASE = loanStsASE;
	}

	public Integer getLoanMonthlyCount() {
		return loanMonthlyCount;
	}

	public void setLoanMonthlyCount(Integer loanMonthlyCount) {
		this.loanMonthlyCount = loanMonthlyCount;
	}

	public Integer getLoanFirstPayment() {
		return loanFirstPayment;
	}

	public void setLoanFirstPayment(Integer loanFirstPayment) {
		this.loanFirstPayment = loanFirstPayment;
	}

	public Integer getLoanLastPayment() {
		return loanLastPayment;
	}

	public void setLoanLastPayment(Integer loanLastPayment) {
		this.loanLastPayment = loanLastPayment;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public HrsLoanType getBank() {
		return bank;
	}

	public void setBank(HrsLoanType bank) {
		this.bank = bank;
	}
}
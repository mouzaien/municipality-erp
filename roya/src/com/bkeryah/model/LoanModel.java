package com.bkeryah.model;

/**
 * @author Haitham
 *
 */

public class LoanModel {
	
	private Integer rowId;
	private Integer empNo;
	private String empName;
	private String loanName;
	private Integer paidMonthly;
	private String bankName;
	private Integer natNo;
	public Integer getRowId() {
		return rowId;
	}
	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}
	public Integer getEmpNo() {
		return empNo;
	}
	public void setEmpNo(Integer empNo) {
		this.empNo = empNo;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public Integer getPaidMonthly() {
		return paidMonthly;
	}
	public void setPaidMonthly(Integer paidMonthly) {
		this.paidMonthly = paidMonthly;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Integer getNatNo() {
		return natNo;
	}
	public void setNatNo(Integer natNo) {
		this.natNo = natNo;
	}
	

}

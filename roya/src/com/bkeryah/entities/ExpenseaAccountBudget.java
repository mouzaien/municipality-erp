package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

public class ExpenseaAccountBudget implements Serializable {

	
	
	@Column(name = "EXPENSESACCOUNTID")
	private int expenseAccountId;// record Id


	@Column(name = "CASHAMOUNT")
	private int cashAmount;// record Id

	@Column(name = "UPDATEDCASHAMOUNT")
	private int updateCashAmount;// record Id

	@Column(name = "EARNINGAMOUNT")
	private int earningAmount;// record Id

	@Column(name = "UPDATEDEARNINGAMOUNT")
	private int updateEarningAmount;// record Id

	@Column(name = "TOTALCASHBOOKINGAMOUNT")
	private int totalCashBookingAmount;// record Id

	@Column(name = "TOTALEARNINGBOOKINGAMOUNT")
	private int totalEarningBookingAmount;// record Id

	@Column(name = "TOTALTEMPORARYBOOKINGAMOUNT")
	private int totalTemporaryBookingAmount;// record Id

	
	
	
	@Column(name = "TOTALCASHSPENTAMOUNT")
	private int totalCashSpentAmount;// record Id

	@Column(name = "TOTALEARNINGSPENTAMOUNT")
	private int totalEarningSpentAmount;// record Id

	@Column(name = "TOTALCAREBOOKINGAMOUNT")
	private int totalCareSpentAmount;// record Id

	@Column(name = "TOTALTEMPORARYBOOKINGAMOUNT_S")
	private int totalTemporaryBookingAmountstatus;// record Id

	@Column(name = "CREATEDBY")
	private String createddate;// record Id
	@Column(name = "CREATEDDATE")
	private String createdDate;// record Id

	@Column(name = "MODIFIEDBY")
	private String modifiedBy;// record Id

	@Column(name = "MODIFIEDDATE")
	private String modifiedDate;// record Id

	public int getExpenseAccountId() {
		return expenseAccountId;
	}

	public void setExpenseAccountId(int expenseAccountId) {
		this.expenseAccountId = expenseAccountId;
	}

	public int getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(int cashAmount) {
		this.cashAmount = cashAmount;
	}

	public int getUpdateCashAmount() {
		return updateCashAmount;
	}

	public void setUpdateCashAmount(int updateCashAmount) {
		this.updateCashAmount = updateCashAmount;
	}

	public int getEarningAmount() {
		return earningAmount;
	}

	public void setEarningAmount(int earningAmount) {
		this.earningAmount = earningAmount;
	}

	public int getUpdateEarningAmount() {
		return updateEarningAmount;
	}

	public void setUpdateEarningAmount(int updateEarningAmount) {
		this.updateEarningAmount = updateEarningAmount;
	}

	public int getTotalCashBookingAmount() {
		return totalCashBookingAmount;
	}

	public void setTotalCashBookingAmount(int totalCashBookingAmount) {
		this.totalCashBookingAmount = totalCashBookingAmount;
	}

	public int getTotalEarningBookingAmount() {
		return totalEarningBookingAmount;
	}

	public void setTotalEarningBookingAmount(int totalEarningBookingAmount) {
		this.totalEarningBookingAmount = totalEarningBookingAmount;
	}

	public int getTotalTemporaryBookingAmount() {
		return totalTemporaryBookingAmount;
	}

	public void setTotalTemporaryBookingAmount(int totalTemporaryBookingAmount) {
		this.totalTemporaryBookingAmount = totalTemporaryBookingAmount;
	}

	public int getTotalCashSpentAmount() {
		return totalCashSpentAmount;
	}

	public void setTotalCashSpentAmount(int totalCashSpentAmount) {
		this.totalCashSpentAmount = totalCashSpentAmount;
	}

	public int getTotalEarningSpentAmount() {
		return totalEarningSpentAmount;
	}

	public void setTotalEarningSpentAmount(int totalEarningSpentAmount) {
		this.totalEarningSpentAmount = totalEarningSpentAmount;
	}

	public int getTotalCareSpentAmount() {
		return totalCareSpentAmount;
	}

	public void setTotalCareSpentAmount(int totalCareSpentAmount) {
		this.totalCareSpentAmount = totalCareSpentAmount;
	}

	public int getTotalTemporaryBookingAmountstatus() {
		return totalTemporaryBookingAmountstatus;
	}

	public void setTotalTemporaryBookingAmountstatus(int totalTemporaryBookingAmountstatus) {
		this.totalTemporaryBookingAmountstatus = totalTemporaryBookingAmountstatus;
	}

	public String getCreateddate() {
		return createddate;
	}

	public void setCreateddate(String createddate) {
		this.createddate = createddate;
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

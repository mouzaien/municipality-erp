package com.bkeryah.entities.investment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "CONTRACTS_FEES")
public class ContractsFees {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "CONTRACT_ID")
	private Integer contractId;// رقم العقد
	@Column(name = "FACT_ID")
	private String factId;// رقم الفاتورة
	@Column(name = "DUE_H_DATE")
	private String dueHDate;// تاريخ الإستحقاق هجري
	@Column(name = "DUE_G_DATE")
	private Date dueGDate;// تاريخ الإستحقاق ميلادي
	@Column(name = "STATUS")
	private Integer status; // غير مفوتر 1 و غير مدفوع 2 و 3 مدفوع
	@Column(name = "OLD_FEES")
	private Integer oldFees; // عدد الأقساط السابقة
	@Column(name = "DISCOUNT_EXCEPTIONEL")
	private double discountExceptionel; // خصم استثنائي بالريال
	@Column(name = "DISCOUNT_DURATION")
	private Integer discountduration; // مدة الخصم بالأيام
	@Column(name = "DISCOUNT_AMOUNT")
	private double discountAmount; // إجمالي قيمة الخصم الكلي للقسط الواحد

	@Formula("(select c.CONTRACT_NUM from CONTRACT_DIRECT c where c.ID = CONTRACT_ID)")
	private Integer contractNumber;

	@Transient
	private boolean checkfees;
	@Transient
	private boolean canRenew = true;
	@Transient
	private boolean delete = false;

	@Transient
	private String oldFactId;//
	@Transient
	private Integer countOfRows;//

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public String getFactId() {
		return factId;
	}

	public void setFactId(String factId) {
		this.factId = factId;
	}

	public String getDueHDate() {
		return dueHDate;
	}

	public void setDueHDate(String dueHDate) {
		this.dueHDate = dueHDate;
	}

	public Date getDueGDate() {
		return dueGDate;
	}

	public void setDueGDate(Date dueGDate) {
		this.dueGDate = dueGDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOldFees() {
		return oldFees;
	}

	public void setOldFees(Integer oldFees) {
		this.oldFees = oldFees;
	}

	public double getDiscountExceptionel() {
		return discountExceptionel;
	}

	public void setDiscountExceptionel(double discountExceptionel) {
		this.discountExceptionel = discountExceptionel;
	}

	public Integer getDiscountduration() {
		return discountduration;
	}

	public void setDiscountduration(Integer discountduration) {
		this.discountduration = discountduration;
	}

	public Integer getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(Integer contractNumber) {
		this.contractNumber = contractNumber;
	}

	public boolean isCheckfees() {
		return checkfees;
	}

	public void setCheckfees(boolean checkfees) {
		this.checkfees = checkfees;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public boolean isCanRenew() {
		if (status == 3)
			canRenew = false;
		else
			canRenew = true;
		return canRenew;
	}

	public void setCanRenew(boolean canRenew) {
		this.canRenew = canRenew;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public String getOldFactId() {
		return oldFactId;
	}

	public void setOldFactId(String oldFactId) {
		this.oldFactId = oldFactId;
	}

	public Integer getCountOfRows() {
		return countOfRows;
	}

	public void setCountOfRows(Integer countOfRows) {
		this.countOfRows = countOfRows;
	}

}
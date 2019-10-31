package com.bkeryah.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "PAY_BILL_DETAILS")
public class PayBillDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//PAY_bill_details_seq.nextval
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
	@SequenceGenerator(name = "G1", sequenceName = "PAY_bill_details_seq" ,allocationSize=1)
	@Column(name = "ID")
	private Integer id;
	@Column(name = "BILL_NO")
	private Integer billNumber;
	@Column(name = "PAY_MASTER")
	private Integer payMaster;
	@Column(name = "PAY_DETAILS")
	private Integer payDetails;
	@Column(name = "AMOUNT")
	private Double amount;
	@Column(name = "CREATED_IN")
	private Date createdIn;
	@Column(name = "CREATED_BY")
	private Integer createdBy;
	@Column(name = "BILL_J_YEAR")
	private Integer billGYear;
	@Column(name = "BILL_H_YEAR")
	private Integer billHYear;
	@ManyToOne
	
	@JoinColumn(name = "BILL_NO", referencedColumnName = "BILL_NO", insertable = false, updatable = false)
	private PayLicBills payLicBill;
	@Transient
	private String itemLabel;
	@Transient
	private String detailAccount;
	
	public PayBillDetails(Integer payMaster, Integer payDetails, Double amount ,String detailAccount) {
		super();
		this.payMaster = payMaster;
		this.payDetails = payDetails;
		this.amount = amount;
		this.detailAccount = detailAccount;
	}
	
	
	public PayBillDetails(PayBillDetails payBillDetails) {
		super();
		this.billNumber = payBillDetails.getBillNumber();
		this.payMaster = payBillDetails.getPayMaster();
		this.payDetails = payBillDetails.getPayDetails();
		this.amount = payBillDetails.getAmount();
		this.createdIn = payBillDetails.getCreatedIn();
		this.createdBy = payBillDetails.getCreatedBy();
		this.billGYear = payBillDetails.getBillGYear();
		this.billHYear = payBillDetails.getBillHYear();
		this.payLicBill = payBillDetails.getPayLicBill();
		this.itemLabel = payBillDetails.getItemLabel();
	}
	public PayBillDetails() {
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(Integer billNumber) {
		this.billNumber = billNumber;
	}
	public Integer getPayMaster() {
		return payMaster;
	}
	public void setPayMaster(Integer payMaster) {
		this.payMaster = payMaster;
	}
	public Integer getPayDetails() {
		return payDetails;
	}
	public void setPayDetails(Integer payDetails) {
		this.payDetails = payDetails;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getCreatedIn() {
		return createdIn;
	}
	public void setCreatedIn(Date createdIn) {
		this.createdIn = createdIn;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getBillGYear() {
		return billGYear;
	}
	public void setBillGYear(Integer billGYear) {
		this.billGYear = billGYear;
	}
	public Integer getBillHYear() {
		return billHYear;
	}
	public void setBillHYear(Integer billHYear) {
		this.billHYear = billHYear;
	}
	public PayLicBills getPayLicBill() {
		return payLicBill;
	}
	public void setPayLicBill(PayLicBills payLicBill) {
		this.payLicBill = payLicBill;
	}
	public String getItemLabel() {
		return itemLabel;
	}
	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}


	public String getDetailAccount() {
		return detailAccount;
	}


	public void setDetailAccount(String detailAccount) {
		this.detailAccount = detailAccount;
	}

}

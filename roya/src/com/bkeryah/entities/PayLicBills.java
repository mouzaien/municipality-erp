package com.bkeryah.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import utilities.Utils;

@Entity
@Table(name = "PAY_LIC_BILLS")
public class PayLicBills implements Serializable {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "BILL_NO")
	private Integer billNumber;
	// penalty Id رقم المخالفة
	@Column(name = "LIC_NO")
	private Integer licenceNumber;
	@Column(name = "LIC_TYPE")
	private String licenceType;
	@Column(name = "BILL_STATUS")
	private Integer billStatus = 0;
	@Column(name = "BILL_PAY_TYPE")
	private String billPayType;
	@Column(name = "COLLECT_BY")
	private Long collectBy;
	@Column(name = "COLLECTION_SR")
	private String collectionSR;
	@Column(name = "PAY_DATE_H")
	private String payHijriDate;
	@Column(name = "PAY_DATE_G")
	private Date payDate;
	@Column(name = "PAY_AMOUNT")
	private Double payAmount;
	@Column(name = "BILL_O_NAME")
	private String billOwnerName;
	@Column(name = "BILL_DATE")
	private String billDate;
	@Column(name = "BANK_CODE")
	private Integer bankCode;
	@Column(name = "CHEQUE_NO")
	private Long chequeNumber;
	@Column(name = "PAY_DESC")
	private String payDescription;
	@Column(name = "PAY_DATE_FROM")
	private String payDateFrom;
	@Column(name = "PAY_DATE_TO")
	private String payDateTo;
	@Column(name = "PAY_INSTALL_NO")
	private Long payInstallNumber; // phone number
	@Column(name = "CHEQUE_DATE")
	private String chequeDate;
	@Column(name = "PAID_BY")
	private String paidBy;
	@Column(name = "BAYAN")
	private String bayan;

	@Column(name = "APL_OWNER")
	private String aplOwner;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "payLicBill")
	private Set<PayBillDetails> payBillDetails;

	@Transient
	private String sadadStatus;

	public PayLicBills(PayLicBills payLicBills) {
		super();
		this.billNumber = payLicBills.getBillNumber();
		this.licenceNumber = payLicBills.getLicenceNumber();
		this.licenceType = payLicBills.getLicenceType();
		this.billStatus = payLicBills.getBillStatus();
		this.billPayType = payLicBills.getBillPayType();
		this.collectBy = payLicBills.getCollectBy();
		this.collectionSR = payLicBills.getCollectionSR();
		this.payHijriDate = payLicBills.getPayHijriDate();
		this.payDate = payLicBills.getPayDate();
		this.payAmount = payLicBills.getPayAmount();
		this.billOwnerName = payLicBills.getBillOwnerName();
		this.billDate = payLicBills.getBillDate();
		this.bankCode = payLicBills.getBankCode();
		this.chequeNumber = payLicBills.getChequeNumber();
		this.payDescription = payLicBills.getPayDescription();
		this.payDateFrom = payLicBills.getPayDateFrom();
		this.payDateTo = payLicBills.getPayDateTo();
		this.payInstallNumber = payLicBills.getPayInstallNumber();
		this.chequeDate = payLicBills.getChequeDate();
		this.paidBy = payLicBills.getPaidBy();
	}

	public PayLicBills() {
	}

	public Integer getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(Integer billNumber) {
		this.billNumber = billNumber;
	}

	public Integer getLicenceNumber() {
		return licenceNumber;
	}

	public void setLicenceNumber(Integer licenceNumber) {
		this.licenceNumber = licenceNumber;
	}

	public String getLicenceType() {
		return licenceType;
	}

	public void setLicenceType(String licenceType) {
		this.licenceType = licenceType;
	}

	public Integer getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(Integer billStatus) {
		this.billStatus = billStatus;
	}

	public String getBillPayType() {
		return billPayType;
	}

	public void setBillPayType(String billPayType) {
		this.billPayType = billPayType;
	}

	public Long getCollectBy() {
		return collectBy;
	}

	public void setCollectBy(Long collectBy) {
		this.collectBy = collectBy;
	}

	public String getCollectionSR() {
		return collectionSR;
	}

	public void setCollectionSR(String collectionSR) {
		this.collectionSR = collectionSR;
	}

	public String getPayHijriDate() {
		return payHijriDate;
	}

	public void setPayHijriDate(String payHijriDate) {
		this.payHijriDate = payHijriDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public String getBillOwnerName() {
		return billOwnerName;
	}

	public void setBillOwnerName(String billOwnerName) {
		this.billOwnerName = billOwnerName;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public Integer getBankCode() {
		return bankCode;
	}

	public void setBankCode(Integer bankCode) {
		this.bankCode = bankCode;
	}

	public Long getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(Long chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public String getPayDescription() {
		return payDescription;
	}

	public void setPayDescription(String payDescription) {
		this.payDescription = payDescription;
	}

	public String getPayDateFrom() {
		return payDateFrom;
	}

	public void setPayDateFrom(String payDateFrom) {
		this.payDateFrom = payDateFrom;
	}

	public String getPayDateTo() {
		return payDateTo;
	}

	public void setPayDateTo(String payDateTo) {
		this.payDateTo = payDateTo;
	}

	public Long getPayInstallNumber() {
		return payInstallNumber;
	}

	public void setPayInstallNumber(Long payInstallNumber) {
		this.payInstallNumber = payInstallNumber;
	}

	public String getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(String chequeDate) {
		this.chequeDate = chequeDate;
	}

	public String getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}

	public Set<PayBillDetails> getPayBillDetails() {
		return payBillDetails;
	}

	public void setPayBillDetails(Set<PayBillDetails> payBillDetails) {
		this.payBillDetails = payBillDetails;
	}

	public String getSadadStatus() {
		if (billStatus != null)
			return billStatus == 1 ? Utils.loadMessagesFromFile("paid") : Utils.loadMessagesFromFile("not.paid");
		else
			return "unknown";
	}

	public void setSadadStatus(String sadadStatus) {
		this.sadadStatus = sadadStatus;
	}

	public String getAplOwner() {
		return aplOwner;
	}

	public void setAplOwner(String aplOwner) {
		this.aplOwner = aplOwner;
	}

	public String getBayan() {
		return bayan;
	}

	public void setBayan(String bayan) {
		this.bayan = bayan;
	}

}

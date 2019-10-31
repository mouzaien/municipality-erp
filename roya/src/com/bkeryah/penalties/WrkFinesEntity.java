package com.bkeryah.penalties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WRK_FINES")
public class WrkFinesEntity {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "FN_ID_NO")
	private Double fnIdNo;
	@Column(name = "FN_BILL_NUMBER")
	private Double fnBillNo;
	@Column(name = "FN_CODE")
	private String fnCode;
	@Column(name = "FN_ID_NAME")
	private String fnIdName;
	@Column(name = "AMOUNT")
	private String amount;
	@Column(name = "NOTES")
	private String notes;
	@Column(name = "FN_DATE")
	private Integer fnDate;
	@Column(name = "BILL_STATUS")
	private Integer billSatatus;
	@Column(name = "FN_SENT_STATUS")
	private Integer fnSentStatus;

	@Transient
	private String fnBillNoStr;
	@Transient
	private String fnIdNoStr;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getFnIdNo() {
		return fnIdNo;
	}

	public void setFnIdNo(Double fnIdNo) {
		this.fnIdNo = fnIdNo;
	}

	public String getFnCode() {
		return fnCode;
	}

	public void setFnCode(String fnCode) {
		this.fnCode = fnCode;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getFnDate() {
		return fnDate;
	}

	public void setFnDate(Integer fnDate) {
		this.fnDate = fnDate;
	}

	public Integer getBillSatatus() {
		return billSatatus;
	}

	public void setBillSatatus(Integer billSatatus) {
		this.billSatatus = billSatatus;
	}

	public Integer getFnSentStatus() {
		return fnSentStatus;
	}

	public void setFnSentStatus(Integer fnSentStatus) {
		this.fnSentStatus = fnSentStatus;
	}

	public String getFnIdName() {
		return fnIdName;
	}

	public void setFnIdName(String fnIdName) {
		this.fnIdName = fnIdName;
	}

	public Double getFnBillNo() {
		return fnBillNo;
	}

	public void setFnBillNo(Double fnBillNo) {
		this.fnBillNo = fnBillNo;
	}

	public String getFnBillNoStr() {
		return fnBillNo.toString();
	}

	public String getFnIdNoStr() {
		return fnIdNo.toString();
	}

}

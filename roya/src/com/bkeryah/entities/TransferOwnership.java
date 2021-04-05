package com.bkeryah.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "TRANSFER_OWNERSHIP")
public class TransferOwnership {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name = "TRANSFER_DATE")
	@DateTimeFormat (pattern="dd/MM/yyyy")
	private Date transferGDate;

	@Column(name = "TRANSFER_HDATE")
	private String transferHDate;

	@Column(name = "FROM_USER")
	private Integer fromUser;

	@Column(name = "TO_USER")
	private Integer toUser;

	@Column(name = "SERIAL_NUMBER")
	private Integer serialNumber;

	@Column(name = "ART_ID")
	private Integer articleId;

	@Column(name = "NOTES")
	private String notes;

	@Column(name = "ART_CODE")
	private String articleCode;

	@Column(name = "STR_NO")
	private Integer strNo;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "QTY")
	private Integer qty;
	
	@Column(name = "EXCH_MASTER_ID")
	private Integer exchMasterId;
	
	@Formula("(select u.EMPNAME from ARC_USERS u where u.user_id=FROM_USER)")
	private String employeeNameFrom;
	
	@Formula("(select u.EMPNAME from ARC_USERS u where u.user_id=TO_USER)")
	private String employeeNameTo;
	
	@Transient
	private String artName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getTransferGDate() {
		return transferGDate;
	}

	public void setTransferGDate(Date transferGDate) {
		this.transferGDate = transferGDate;
	}

	public String getTransferHDate() {
		return transferHDate;
	}

	public void setTransferHDate(String transferHDate) {
		this.transferHDate = transferHDate;
	}

	public Integer getFromUser() {
		return fromUser;
	}

	public void setFromUser(Integer fromUser) {
		this.fromUser = fromUser;
	}

	public Integer getToUser() {
		return toUser;
	}

	public void setToUser(Integer toUser) {
		this.toUser = toUser;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Integer getStrNo() {
		return strNo;
	}

	public void setStrNo(Integer strNo) {
		this.strNo = strNo;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public String getArtName() {
		return artName;
	}

	public void setArtName(String artName) {
		this.artName = artName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getExchMasterId() {
		return exchMasterId;
	}

	public void setExchMasterId(Integer exchMasterId) {
		this.exchMasterId = exchMasterId;
	}

	public String getEmployeeNameFrom() {
		return employeeNameFrom;
	}

	public void setEmployeeNameFrom(String employeeNameFrom) {
		this.employeeNameFrom = employeeNameFrom;
	}

	public String getEmployeeNameTo() {
		return employeeNameTo;
	}

	public void setEmployeeNameTo(String employeeNameTo) {
		this.employeeNameTo = employeeNameTo;
	}

}
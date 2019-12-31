package com.bkeryah.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "STORE_TEMPORARY_RECEIPT_MASTER")
public class StoreTemporeryReceiptMaster {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "DOCUMENT_TYPE")
	private Integer document;
	@Column(name = "DOCUMENT_NUMBER")
	private Integer documentNumber;
	@Column(name = "DOCUMENT_H_DATE")
	private String documentHDate;
	@Column(name = "DOCUMENT_G_DATE")
	private Date documentGDate;
	@Column(name = "RECEIPT_H_DATE")
	private String receiptHDate;
	@Column(name = "RECEIPT_G_DATE")
	private Date receiptGDate;

	@Column(name = "SPECIAL_NUM")
	private Integer specialNumber;
	@Column(name = "SUPPLIERID")
	private Integer supplierId;
	@Column(name = "STORE_NO")
	private Integer strNo;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDocument() {
		return document;
	}
	public void setDocument(Integer document) {
		this.document = document;
	}
	public Integer getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(Integer documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getDocumentHDate() {
		return documentHDate;
	}
	public void setDocumentHDate(String documentHDate) {
		this.documentHDate = documentHDate;
	}
	public Date getDocumentGDate() {
		return documentGDate;
	}
	public void setDocumentGDate(Date documentGDate) {
		this.documentGDate = documentGDate;
	}
	public String getReceiptHDate() {
		return receiptHDate;
	}
	public void setReceiptHDate(String receiptHDate) {
		this.receiptHDate = receiptHDate;
	}
	public Date getReceiptGDate() {
		return receiptGDate;
	}
	public void setReceiptGDate(Date receiptGDate) {
		this.receiptGDate = receiptGDate;
	}
	public Integer getSpecialNumber() {
		return specialNumber;
	}
	public void setSpecialNumber(Integer specialNumber) {
		this.specialNumber = specialNumber;
	}
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	public Integer getStrNo() {
		return strNo;
	}
	public void setStrNo(Integer strNo) {
		this.strNo = strNo;
	}

}

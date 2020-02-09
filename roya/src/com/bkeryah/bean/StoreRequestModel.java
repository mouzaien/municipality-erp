package com.bkeryah.bean;

import java.io.Serializable;
import java.util.List;

public class StoreRequestModel implements Serializable {

	private Integer articleId;
	private Integer qtyInput;
	private Integer qtyOutput;
	private Integer qtyAvailable;
	private Integer qtyReserved;
	private String articleName;
	private String articleCode;
	private String serialNumber;
	private Integer transactionCode;
	private String transactionName;
	private String articleUnite;
	private Integer specialNumber;
	private String transactionDate;

	private String supplierName;
	private String requesterName;
	private Integer storeNo;
	private List<StoreRequestModel> historyList;

	public StoreRequestModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StoreRequestModel(Integer articleId, Integer qtyInput, Integer qtyOutput, Integer qtyAvailable,
			Integer qtyReserved, String articleName, String articleCode, String articleUnite) {
		super();
		this.articleId = articleId;
		this.qtyInput = qtyInput;
		this.qtyOutput = qtyOutput;
		this.qtyAvailable = qtyAvailable;
		this.qtyReserved = qtyReserved;
		this.articleName = articleName;
		this.articleCode = articleCode;
		this.articleUnite = articleUnite;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getQtyInput() {
		return qtyInput;
	}

	public void setQtyInput(Integer qtyInput) {
		this.qtyInput = qtyInput;
	}

	public Integer getQtyOutput() {
		return qtyOutput;
	}

	public void setQtyOutput(Integer qtyOutput) {
		this.qtyOutput = qtyOutput;
	}

	public Integer getQtyAvailable() {
		return qtyAvailable;
	}

	public void setQtyAvailable(Integer qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}

	public Integer getQtyReserved() {
		return qtyReserved;
	}

	public void setQtyReserved(Integer qtyReserved) {
		this.qtyReserved = qtyReserved;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public Integer getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(Integer transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public List<StoreRequestModel> getHistoryList() {
		return historyList;
	}

	public void setHistoryList(List<StoreRequestModel> historyList) {
		this.historyList = historyList;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getArticleUnite() {
		return articleUnite;
	}

	public void setArticleUnite(String articleUnite) {
		this.articleUnite = articleUnite;
	}

	public Integer getSpecialNumber() {
		return specialNumber;
	}

	public void setSpecialNumber(Integer specialNumber) {
		this.specialNumber = specialNumber;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getRequesterName() {
		return requesterName;
	}

	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}

	public Integer getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(Integer storeNo) {
		this.storeNo = storeNo;
	}

}

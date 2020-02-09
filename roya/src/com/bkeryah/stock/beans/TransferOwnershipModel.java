package com.bkeryah.stock.beans;

public class TransferOwnershipModel {

	private Integer transferOwnerId;
	private Integer articleId;
	private String articleName;
	private String articleCode;
	private String notes;
	private String articleUnit;
	private Integer fromUserId;
	private Integer toUserId;
	private String fromUserName;
	private String toUserName;
	private Integer qty;
	private Integer strNo;
	private Integer serialNumber;
	private Integer exchMasterId;
	
	public Integer getTransferOwnerId() {
		return transferOwnerId;
	}
	public void setTransferOwnerId(Integer transferOwnerId) {
		this.transferOwnerId = transferOwnerId;
	}
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
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
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getArticleUnit() {
		return articleUnit;
	}
	public void setArticleUnit(String articleUnit) {
		this.articleUnit = articleUnit;
	}
	public Integer getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}
	public Integer getToUserId() {
		return toUserId;
	}
	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public Integer getStrNo() {
		return strNo;
	}
	public void setStrNo(Integer strNo) {
		this.strNo = strNo;
	}
	public Integer getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
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
	
}
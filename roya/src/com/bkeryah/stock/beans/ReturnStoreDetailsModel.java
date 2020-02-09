package com.bkeryah.stock.beans;

public class ReturnStoreDetailsModel {

	private Integer returnStoreId;
	private Integer articleId;
	private Integer qty;
	private String notes;
	private String articleName;
	private String articleCode;
	private String articleUnit;
	private String reason;
	private Integer retrunReason;
	private Integer exchMasterId;
	private String exchMasterDate;

	public Integer getReturnStoreId() {
		return returnStoreId;
	}

	public void setReturnStoreId(Integer returnStoreId) {
		this.returnStoreId = returnStoreId;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getReason() {
		switch (retrunReason) {
		case 1:
			reason = "إنتهاء الغرض";
			break;
		case 2:
			reason = "فائض";
			break;
		case 3:
			reason = "عدم الصلاحية";
			break;
		case 4:
			reason = "تالف";
			break;

		default:
			break;
		}
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getArticleUnit() {
		return articleUnit;
	}

	public void setArticleUnit(String articleUnit) {
		this.articleUnit = articleUnit;
	}

	public Integer getRetrunReason() {
		return retrunReason;
	}

	public void setRetrunReason(Integer retrunReason) {
		this.retrunReason = retrunReason;
	}

	public Integer getExchMasterId() {
		return exchMasterId;
	}

	public void setExchMasterId(Integer exchMasterId) {
		this.exchMasterId = exchMasterId;
	}

	public String getExchMasterDate() {
		return exchMasterDate;
	}

	public void setExchMasterDate(String exchMasterDate) {
		this.exchMasterDate = exchMasterDate;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

}

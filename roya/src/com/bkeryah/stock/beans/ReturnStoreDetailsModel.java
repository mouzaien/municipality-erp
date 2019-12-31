package com.bkeryah.stock.beans;

public class ReturnStoreDetailsModel {

	private Integer returnStoreId;
	private Integer articleId;
	private Integer qty;
	private String notes;
	private String articleName;
	private String articleUnit;
	private String reason;
	private Integer retrunReason;

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

}

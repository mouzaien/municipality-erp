package com.bkeryah.stock.beans;

public class StoreTemporeryReceiptDetailsModel {
	private Integer id;
	private Integer temporaryReceiptMasterId;
	private Integer articleId;
	private Integer qty;
	private String notes;
	private String unit;
	private Integer articleStatus;
	private String articleName;
	private String articleUnit;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTemporaryReceiptMasterId() {
		return temporaryReceiptMasterId;
	}

	public void setTemporaryReceiptMasterId(Integer temporaryReceiptMasterId) {
		this.temporaryReceiptMasterId = temporaryReceiptMasterId;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getArticleStatus() {
		return articleStatus;
	}

	public void setArticleStatus(Integer articleStatus) {
		this.articleStatus = articleStatus;
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
}

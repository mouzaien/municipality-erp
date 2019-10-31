package com.bkeryah.bean;

import java.io.Serializable;

public class InventoryModel implements Serializable {

	private int inventoryRecordId;
	private int inventoryMasterId;
	private int articleId;
	private String articleName;
	private String articleCode;
	private Integer qteActuel;
	private Integer lastGardQty;
	private Integer stock;

	public InventoryModel(int inventoryRecordId, int inventoryMasterId, int articleId, String articleName,
			Integer qteActuel) {
		super();
		this.inventoryRecordId = inventoryRecordId;
		this.inventoryMasterId = inventoryMasterId;
		this.articleId = articleId;
		this.articleName = articleName;
		this.qteActuel = qteActuel;
	}

	public InventoryModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getInventoryRecordId() {
		return inventoryRecordId;
	}

	public void setInventoryRecordId(int inventoryRecordId) {
		this.inventoryRecordId = inventoryRecordId;
	}

	public int getInventoryMasterId() {
		return inventoryMasterId;
	}

	public void setInventoryMasterId(int inventoryMasterId) {
		this.inventoryMasterId = inventoryMasterId;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public Integer getQteActuel() {
		return qteActuel;
	}

	public void setQteActuel(Integer qteActuel) {
		this.qteActuel = qteActuel;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public Integer getLastGardQty() {
		return lastGardQty;
	}

	public void setLastGardQty(Integer lastGardQty) {
		this.lastGardQty = lastGardQty;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

}

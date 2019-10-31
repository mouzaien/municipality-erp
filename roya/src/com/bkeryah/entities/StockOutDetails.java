package com.bkeryah.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "WHS_STOCK_OUT_DETAILS")
public class StockOutDetails {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer stockOutDetailsId;
	@Column(name = "ARTICLE_ID")
	private Integer articleId;
	@Column(name = "QTY")
	private Integer qty;
	@Column(name = "PRICE")
	private Integer price;
	@Column(name = "TOTAL")
	private Integer total;
	@Column(name = "NOTES")
	private String notes;
	@Column(name = "ARTICLE_STATUS")
	private Integer articleStatus;
	@Column(name = "STOCKMASTER_OUT")
	private Integer stockMasterOut;

	public StockOutDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StockOutDetails(Integer stockOutDetailsId, Integer articleId, Integer qty, Integer price, Integer total,
			String notes, Integer articleStatus, Integer stockMasterOut) {
		super();
		this.stockOutDetailsId = stockOutDetailsId;
		this.articleId = articleId;
		this.qty = qty;
		this.price = price;
		this.total = total;
		this.notes = notes;
		this.articleStatus = articleStatus;
		this.stockMasterOut = stockMasterOut;
	}

	public Integer getStockOutDetailsId() {
		return stockOutDetailsId;
	}

	public void setStockOutDetailsId(Integer stockOutDetailsId) {
		this.stockOutDetailsId = stockOutDetailsId;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getArticleStatus() {
		return articleStatus;
	}

	public void setArticleStatus(Integer articleStatus) {
		this.articleStatus = articleStatus;
	}

	public Integer getStockMasterOut() {
		return stockMasterOut;
	}

	public void setStockMasterOut(Integer stockMasterOut) {
		this.stockMasterOut = stockMasterOut;
	}

}
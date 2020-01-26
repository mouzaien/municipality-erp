package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WHS_STOCK_IN_DETAILS")
public class StockInDetails {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer stockInDetailsId;
	@Column(name = "ARTICLE_ID")
	private Integer articleId;
	@Column(name = "QTY")
	private Integer qty;
	@Column(name = "PRICE")
	private Float price;
	@Column(name = "TOTAL")
	private Float total;
	@Column(name = "NOTES")
	private String notes;
	@Column(name = "ARTICLE_STATUS")
	private Integer articleStatus;
	@Column(name = "STOCKMASTER_IN")
	private Integer stockmasterIn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STOCKMASTER_IN", referencedColumnName = "ID", insertable = false, updatable = false)
	private StockEntryMaster stockEntryMaster;

	@ManyToOne()
	@JoinColumn(name = "ARTICLE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private Article article;

	public StockInDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	@javax.persistence.Transient
	private String articleName;
	@javax.persistence.Transient
	private String uniteName;
	@javax.persistence.Transient
	private String articleStatusName;
	@Transient
	private String articleCode;
	@Transient
	private String articleUnit;
	@Transient
	private Float sum = 0.00f;

	public StockInDetails(Integer stockInDetailsId, Integer articleId, Integer qty, Float price, Float total,
			String notes, Integer articleStatus, Integer stockmasterIn) {
		super();
		this.stockInDetailsId = stockInDetailsId;
		this.articleId = articleId;
		this.qty = qty;
		this.price = price;
		this.total = total;
		this.notes = notes;
		this.articleStatus = articleStatus;
		this.stockmasterIn = stockmasterIn;
	}

	public Integer getStockInDetailsId() {
		return stockInDetailsId;
	}

	public void setStockInDetailsId(Integer stockInDetailsId) {
		this.stockInDetailsId = stockInDetailsId;
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

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
		if (price != null && qty != null) {
			total = price * qty;
			sum = sum + total;
		}
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
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

	public Integer getStockmasterIn() {
		return stockmasterIn;
	}

	public void setStockmasterIn(Integer stockmasterIn) {
		this.stockmasterIn = stockmasterIn;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getUniteName() {
		return uniteName;
	}

	public void setUniteName(String uniteName) {
		this.uniteName = uniteName;
	}

	public String getArticleStatusName() {
		return articleStatusName;
	}

	public void setArticleStatusName(String articleStatusName) {
		this.articleStatusName = articleStatusName;
		if (articleStatusName != null) {
			switch (articleStatusName) {
			case "1":
				articleUnit = "جديد";
				break;
			case "2":
				articleUnit = "مستعمل";
				break;
			case "3":
				articleUnit = "تالف";
				break;
			case "4":
				articleUnit = "منح";
				break;
			case "5":
				articleUnit = "تالف";
				break;

			default:
				break;
			}
		}
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public Float getSum() {
		return sum;
	}

	public void setSum(Float sum) {
		this.sum = sum;
	}

	public String getArticleUnit() {
		return articleUnit;
	}

	public void setArticleUnit(String articleUnit) {
		this.articleUnit = articleUnit;
	}

}

package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "STORE_TEMPORARY_RECEIPT_DETAILS")
public class StoreTemporeryReceiptDetails {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "TEMP_RECEIPT_MASTER_ID")
	private Integer temporaryReceiptMasterId;
	@Column(name = "ARTICLE_ID")
	private Integer articleId;
	@Column(name = "QTY")
	private Integer qty;
	@Column(name = "NOTES")
	private String notes;
	@Column(name = "ARTICLE_STATUS")
	private Integer articleStatus;
	@Formula("(SELECT A.NAME FROM ARTICLE A WHERE A.ID = ARTICLE_ID)")
	private String articleName;

	@Transient
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

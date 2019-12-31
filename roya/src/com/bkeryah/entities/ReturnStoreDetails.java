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
@Table(name = "RETURN_STORE_DETAILS")
public class ReturnStoreDetails {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer Id;
	@Column(name = "RET_ID")
	private Integer returnStoreId;
	@Column(name = "ART_ID")
	private Integer articleId;
	@Column(name = "QTY")
	private Integer qty;
	@Column(name = "NOTES")
	private String notes;
	@Formula("(select a.name from ARTICLE a where a.id = ART_ID)")
	private String articleName;
	// @Formula("(select a.code from ARTICLE a where a.id = articleId)")
	// private String articleCode;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		this.Id = id;
	}

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

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	// public String getArticleCode() {
	// return articleCode;
	// }
	//
	// public void setArticleCode(String articleCode) {
	// this.articleCode = articleCode;
	// }

}

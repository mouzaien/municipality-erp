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
	@Column(name = "EXCH_MASTER_ID")
	private Integer exchMasterId;
	@Column(name = "NOTES")
	private String notes;
	@Formula("(select a.name from ARTICLE a where a.id = ART_ID)")
	private String articleName;
	
	@Column(name = "ART_CODE")
	private String articleCode;
//	@Formula("(select a.Item_Unite_Id from ARTICLE a where a.id = ART_ID)")
//	private Integer item_Unite_Id;
//	@Formula("(select i.name from TDR_ITEMUNIT i where i.id = item_Unite_Id)")
//	private String articleUnit;
	
	
	@Formula("(select a.code from ARTICLE a where a.id = ART_ID)")
	private String artCode;
	
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

	public Integer getExchMasterId() {
		return exchMasterId;
	}

	public void setExchMasterId(Integer exchMasterId) {
		this.exchMasterId = exchMasterId;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public String getArtCode() {
		return artCode;
	}

	public void setArtCode(String artCode) {
		this.artCode = artCode;
	}

//	public String getArticleUnit() {
//		return articleUnit;
//	}
//
//	public void setArticleUnit(String articleUnit) {
//		this.articleUnit = articleUnit;
//	}
//
//	public Integer getItem_Unite_Id() {
//		return item_Unite_Id;
//	}
//
//	public void setItem_Unite_Id(Integer item_Unite_Id) {
//		this.item_Unite_Id = item_Unite_Id;
//	}

	// public String getArticleCode() {
	// return articleCode;
	// }
	//
	// public void setArticleCode(String articleCode) {
	// this.articleCode = articleCode;
	// }

}
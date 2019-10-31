package com.bkeryah.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Column;

@Entity
@Table(name = "WHS_GARD_DETAIL")
public class InventoryRecord {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "GARDDETID")
	private Integer inventoryRecordId;
	@Column(name = "GARDID")
	private Integer inventoryMasterId;
	@Column(name = "TENDERITEMID")
	private Integer articleId;
	@Column(name = "GARD_ACTUAL")
	private Integer inventoryActual;
	@Column(name = "GARD_DAFTER")
	private Integer gardDafter;
	@Column(name = "GARD_ADD")
	private Integer gardAdd;
	@Column(name = "GARD_MINUS")
	private Integer inventoryMinus;
	@Column(name = "NOTES")
	private String notes;
	@Column(name = "QTY_CALCULATED")
	private Integer inventoryCalculated;

	public Integer getInventoryCalculated() {
		return inventoryCalculated;
	}

	public void setInventoryCalculated(Integer inventoryCalculated) {
		this.inventoryCalculated = inventoryCalculated;
	}

	@ManyToOne
	@JoinColumn(name = "TENDERITEMID", referencedColumnName = "ID", insertable = false, updatable = false)
	private Article article;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GARDID", referencedColumnName = "GARDID", insertable = false, updatable = false)
	private InventoryMaster inventoryMaster;

	@javax.persistence.Transient
	private String articleName;
	@javax.persistence.Transient
	private String uniteName;

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

	@javax.persistence.Transient

	public Integer getInventoryRecordId() {
		return inventoryRecordId;
	}

	public void setInventoryRecordId(Integer inventoryRecordId) {
		this.inventoryRecordId = inventoryRecordId;
	}

	public Integer getInventoryMasterId() {
		return inventoryMasterId;
	}

	public void setInventoryMasterId(Integer inventoryMasterId) {
		this.inventoryMasterId = inventoryMasterId;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getInventoryActual() {
		return inventoryActual;
	}

	public void setInventoryActual(Integer inventoryActual) {
		this.inventoryActual = inventoryActual;
	}

	public Integer getGardDafter() {
		return gardDafter;
	}

	public void setGardDafter(Integer gardDafter) {
		this.gardDafter = gardDafter;
	}

	public Integer getGardAdd() {
		return gardAdd;
	}

	public void setGardAdd(Integer gardAdd) {
		this.gardAdd = gardAdd;
	}

	public Integer getInventoryMinus() {
		return inventoryMinus;
	}

	public void setInventoryMinus(Integer inventoryMinus) {
		this.inventoryMinus = inventoryMinus;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public InventoryMaster getInventoryMaster() {
		return inventoryMaster;
	}

	public void setInventoryMaster(InventoryMaster inventoryMaster) {
		this.inventoryMaster = inventoryMaster;
	}

}

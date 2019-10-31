package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TDR_PROCUREMENT_ITEM")
public class ProcurementDetails {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "TENDER_ITEM_ID")
	private Integer tenderItemId;
	@Column(name = "QUANTITY")
	private Integer quantity;
	@Column(name = "NOTES")
	private String notes;
	@Column(name = "PROCUREMENT_ID")
	private Integer procurementId;
	@ManyToOne
	@JoinColumn(name = "TENDER_ITEM_ID", referencedColumnName = "TENDERITEMID", insertable = false, updatable = false)
	private TenderItems TenderItem;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROCUREMENT_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private Procurement procurement;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTenderItemId() {
		return tenderItemId;
	}

	public void setTenderItemId(Integer tenderItemId) {
		this.tenderItemId = tenderItemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getProcurementId() {
		return procurementId;
	}

	public void setProcurementId(Integer procurementId) {
		this.procurementId = procurementId;
	}

	public TenderItems getTenderItem() {
		return TenderItem;
	}

	public void setTenderItem(TenderItems tenderItem) {
		TenderItem = tenderItem;
	}

	public Procurement getProcurement() {
		return procurement;
	}

	public void setProcurement(Procurement procurement) {
		this.procurement = procurement;
	}

}
package com.bkeryah.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TDR_TENDERITEMS")
public class TenderItems {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "TENDERITEMID")
	private Integer id;
	@Column(name = "TENDERITEMNAME")
	private String name;
	@Column(name = "ITEMUNITID")
	private Integer itemUnitId;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "CREATEDBY")
	private String createdBy;
	@Column(name = "CREATEDDATE")
	private String createdDate;
	@Column(name = "MODIFIEDBY")
	private String modifiedBy;
	@Column(name = "MODIFIEDDATE")
	private String modifiedDate;
	@Column(name = "GRPITMID2")
	private Integer grpitmid2;
	@Column(name = "CATCODE")
	private String catCode;
	@Column(name = "ITMNATURE")
	private Integer itmNature;
	@Column(name = "ITMTYPNO")
	private Integer itmTypNo;
	@Column(name = "ITMCLASS")
	private Integer itmClass;
	@Column(name = "HD_COUNT")
	private Integer hdCount;
	@ManyToOne
	@JoinColumn(name = "ITEMUNITID", insertable = false, updatable = false)
	private ItemUnite unite;
//	@OneToMany(mappedBy = "TenderItem", fetch = FetchType.LAZY)
//	private Set<ExchangeRequestDetails> exchangeRequestDetailsList;
	@OneToMany(mappedBy = "tenderItem", fetch = FetchType.LAZY)
	private Set<ActualDisbursementDetails> actualDisbursementDetails;
	@OneToMany(mappedBy = "TenderItem", fetch = FetchType.LAZY)
	private Set<ProcurementDetails> procurementDetailsList;

	public Integer getItemUnitId() {
		return itemUnitId;
	}

	public void setItemUnitId(Integer itemUnitId) {
		this.itemUnitId = itemUnitId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getGrpitmid2() {
		return grpitmid2;
	}

	public void setGrpitmid2(Integer grpitmid2) {
		this.grpitmid2 = grpitmid2;
	}

	public String getCatCode() {
		return catCode;
	}

	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}

	public Integer getItmNature() {
		return itmNature;
	}

	public void setItmNature(Integer itmNature) {
		this.itmNature = itmNature;
	}

	public Integer getItmTypNo() {
		return itmTypNo;
	}

	public void setItmTypNo(Integer itmTypNo) {
		this.itmTypNo = itmTypNo;
	}

	public Integer getItmClass() {
		return itmClass;
	}

	public void setItmClass(Integer itmClass) {
		this.itmClass = itmClass;
	}

	public Integer getHdCount() {
		return hdCount;
	}

	public void setHdCount(Integer hdCount) {
		this.hdCount = hdCount;
	}

//	public Set<ExchangeRequestDetails> getExchangeRequestDetailsList() {
//		return exchangeRequestDetailsList;
//	}
//
//	public void setExchangeRequestDetailsList(Set<ExchangeRequestDetails> exchangeRequestDetailsList) {
//		this.exchangeRequestDetailsList = exchangeRequestDetailsList;
//	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ItemUnite getUnite() {
		return unite;
	}

	public void setUnite(ItemUnite unite) {
		this.unite = unite;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ProcurementDetails> getProcurementDetailsList() {
		return procurementDetailsList;
	}

	public void setProcurementDetailsList(Set<ProcurementDetails> procurementDetailsList) {
		this.procurementDetailsList = procurementDetailsList;
	}

}

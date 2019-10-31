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
@Table(name = "TDR_INV_REQ_DET")
public class ExchangeRequestDetails {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "INV_R_DET_NO")
	private Integer requestDetailsNumber;
	@Column(name = "ITEMID")
	private Integer itemId;
	@Column(name = "COUNT")
	private Integer count ;
	@Column(name = "INV_R_NO")
	private Integer requestNumber;
	@Column(name = "NOTES")
	private String notes;
	@Column(name = "INV_G_R_NO")
	private Integer generalRequestNumber;
	@Column(name = "IS_CONFIRM_Y_N")
	private String confirmYN;
	@Column(name = "CONFIRM_W_S")
	private String confirmWS;
	@Column(name = "IS_AVLBL_Y_N")
	private String isAvlblYN;
	@Column(name = "INIT_COUNT")
	private Integer exchangeAtcualyCount;
	@Column(name = "ACTUAL_COUNT_GURANTEE")
	private Integer guranteeActualyCount;
	// @ManyToOne()
	// @JoinColumn(name = "ITEMID", referencedColumnName = "TENDERITEMID",
	// insertable = false, updatable = false)
	// private TenderItems TenderItem;
	@ManyToOne()
	@JoinColumn(name = "ITEMID", referencedColumnName = "ID", insertable = false, updatable = false)
	private Article article;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INV_G_R_NO", referencedColumnName = "INV_G_R_NO", insertable = false, updatable = false)
	private ExchangeRequest exchangeRequest;

	@Transient
	private Integer qtyAvailable;
	@Transient
	private Integer qtyReserved;

	public Integer getQtyAvailable() {
		return qtyAvailable;
	}

	public void setQtyAvailable(Integer qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}

	public Integer getQtyReserved() {
		return qtyReserved;
	}

	public void setQtyReserved(Integer qtyReserved) {
		this.qtyReserved = qtyReserved;
	}

	public Integer getRequestDetailsNumber() {
		return requestDetailsNumber;
	}

	public void setRequestDetailsNumber(Integer requestDetailsNumber) {
		this.requestDetailsNumber = requestDetailsNumber;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(Integer requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getGeneralRequestNumber() {
		return generalRequestNumber;
	}

	public void setGeneralRequestNumber(Integer generalRequestNumber) {
		this.generalRequestNumber = generalRequestNumber;
	}

	public String getConfirmYN() {
		return confirmYN;
	}

	public void setConfirmYN(String confirmYN) {
		this.confirmYN = confirmYN;
	}

	public String getConfirmWS() {
		return confirmWS;
	}

	public void setConfirmWS(String confirmWS) {
		this.confirmWS = confirmWS;
	}

	public String getIsAvlblYN() {
		return isAvlblYN;
	}

	public void setIsAvlblYN(String isAvlblYN) {
		this.isAvlblYN = isAvlblYN;
	}

	public String getTenderItemName() {
		return tenderItemName;
	}

	public void setTenderItemName(String tenderItemName) {
		this.tenderItemName = tenderItemName;
	}

	public String getUniteName() {
		return uniteName;
	}

	public void setUniteName(String uniteName) {
		this.uniteName = uniteName;
	}

	// public TenderItems getTenderItem() {
	// return TenderItem;
	// }
	//
	// public void setTenderItem(TenderItems tenderItem) {
	// TenderItem = tenderItem;
	// }

	public ExchangeRequest getExchangeRequest() {
		return exchangeRequest;
	}

	public void setExchangeRequest(ExchangeRequest exchangeRequest) {
		this.exchangeRequest = exchangeRequest;
	}

	@javax.persistence.Transient
	private String tenderItemName;
	@javax.persistence.Transient
	private String uniteName;
	@javax.persistence.Transient

	private String transientId;

	public Integer getExchangeAtcualyCount() {
		return exchangeAtcualyCount;
	}

	public void setExchangeAtcualyCount(Integer exchangeAtcualyCount) {

		this.exchangeAtcualyCount = exchangeAtcualyCount;
	}

	public Integer getGuranteeActualyCount() {
		return guranteeActualyCount;
	}

	public void setGuranteeActualyCount(Integer guranteeActualyCount) {
		this.guranteeActualyCount = guranteeActualyCount;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

}

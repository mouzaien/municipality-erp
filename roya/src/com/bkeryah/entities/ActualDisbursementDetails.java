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
@Table(name = "WHS_ITEMS_APPLICATION_DETAILS")
public class ActualDisbursementDetails {

	@Id
	@Column(name = "RCTDETNO")
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	private Integer id;
	@Column(name = "ITMSTS")
	private Integer status;
	@Column(name = "ITMSRL")
	private Integer itmsrl;
	@Column(name = "TRNQTY")
	private Integer quantity;
	@Column(name = "EMPNO")
	private String employeeNo;
	@Column(name = "TENDERITEMID")
	private Integer tenderItemId;
	@Column(name = "RCTNO")
	private Integer ActualDisbursementId;
	@Column(name = "CIN")
	private Integer createdIn;
	@Column(name = "COUT")
	private Integer cout;
	@Column(name = "DETAILS")
	private String details;
	@Column(name = "PRICE")
	private double unitPrice;
	@Column(name = "PRICES")
	private double totalPrice;
	@Transient
	private String tenderName;
	@Transient
	private String tenderUnite;
	public String getTenderName() {
		return tenderName;
	}

	public void setTenderName(String tenderName) {
		this.tenderName = tenderName;
	}

	public String getTenderUnite() {
		return tenderUnite;
	}

	public void setTenderUnite(String tenderUnite) {
		this.tenderUnite = tenderUnite;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TENDERITEMID", referencedColumnName = "TENDERITEMID", insertable = false, updatable = false)
	private TenderItems tenderItem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RCTNO", updatable = false, insertable = false)
	ActualDisbursement actualDisbursement;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getItmsrl() {
		return itmsrl;
	}

	public void setItmsrl(Integer itmsrl) {
		this.itmsrl = itmsrl;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public Integer getTenderItemId() {
		return tenderItemId;
	}

	public void setTenderItemId(Integer tenderItemId) {
		this.tenderItemId = tenderItemId;
	}

	public Integer getActualDisbursementId() {
		return ActualDisbursementId;
	}

	public void setActualDisbursementId(Integer actualDisbursementId) {
		ActualDisbursementId = actualDisbursementId;
	}

	public Integer getCreatedIn() {
		return createdIn;
	}

	public void setCreatedIn(Integer createdIn) {
		this.createdIn = createdIn;
	}

	public Integer getCout() {
		return cout;
	}

	public void setCout(Integer cout) {
		this.cout = cout;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public ActualDisbursement getActualDisbursement() {
		return actualDisbursement;
	}

	public void setActualDisbursement(ActualDisbursement actualDisbursement) {
		this.actualDisbursement = actualDisbursement;
	}

	public TenderItems getTenderItem() {
		return tenderItem;
	}

	public void setTenderItem(TenderItems tenderItem) {
		this.tenderItem = tenderItem;
	}

}

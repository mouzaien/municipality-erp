package com.bkeryah.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "FINE_REBOUND_MASTER")
public class FineReboundMaster {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "FINE_ID")
	private Integer fineId;
	@Column(name = "FINE_H_DATE")
	private String fineHDate;
	@Column(name = "FINE_APL_OWNER_ID")
	private Long fineAplOwnerId;
	@Column(name = "FINE_APL_OWNER_NAME")
	private String fineAplOwnerName;
	@Column(name = "FINE_INSTRUMENT_NO")
	private String fineInstrumentNo;
	@Column(name = "FINE_INSTRUMENT_DATE")
	private String fineInstrumentDate;
	@Column(name = "FINE_INSTRUMENT_SOURCE")
	private String fineInstrumentSource;
	@Column(name = "FINE_PLAN_NO")
	private String finePlanNo;
	@Column(name = "FINE_PC_NO")
	private String finePcNo;
	@Column(name = "FINE_REBOUND_DESC")
	private String fineReboundDesc;
	@Column(name = "FINE_GROUND_METER")
	private Integer fineGroundMeter;
	@Column(name = "FINE_FLOOR_METER")
	private Integer fineFloorMeter;
	@Column(name = "FINE_MIN_TAX")
	private Integer fineMinTax;
	@Column(name = "FINE_MAX_TAX")
	private Integer fineMaxTax;
	@Column(name = "FINE_METER_COST")
	private Integer fineMeterCost;
	@Column(name = "FINE_TAX_TYPE")
	private Integer fineTaxType;
	@Column(name = "FINE_CREATED_BY")
	private Integer fineCreatedBy;
	@Column(name = "FINE_CREATED_IN")
	private String fineCreatedIn;
	@Column(name = "FINE_SIGNED_BY")
	private Integer fineSignedBy;
	@Column(name = "FINE_SIGNED_IN")
	private String fineSignedIn;
	@Column(name = "FINE_SIGNED_NOTE")
	private String fineSignedNote;

	public Integer getFineId() {
		return fineId;
	}

	public void setFineId(Integer fineId) {
		this.fineId = fineId;
	}

	public String getFineHDate() {
		return fineHDate;
	}

	public void setFineHDate(String fineHDate) {
		this.fineHDate = fineHDate;
	}

	public Long getFineAplOwnerId() {
		return fineAplOwnerId;
	}

	public void setFineAplOwnerId(Long fineAplOwnerId) {
		this.fineAplOwnerId = fineAplOwnerId;
	}

	public String getFineAplOwnerName() {
		return fineAplOwnerName;
	}

	public void setFineAplOwnerName(String fineAplOwnerName) {
		this.fineAplOwnerName = fineAplOwnerName;
	}

	public String getFineInstrumentNo() {
		return fineInstrumentNo;
	}

	public void setFineInstrumentNo(String fineInstrumentNo) {
		this.fineInstrumentNo = fineInstrumentNo;
	}

	public String getFineInstrumentDate() {
		return fineInstrumentDate;
	}

	public void setFineInstrumentDate(String fineInstrumentDate) {
		this.fineInstrumentDate = fineInstrumentDate;
	}

	public String getFineInstrumentSource() {
		return fineInstrumentSource;
	}

	public void setFineInstrumentSource(String fineInstrumentSource) {
		this.fineInstrumentSource = fineInstrumentSource;
	}

	public String getFinePlanNo() {
		return finePlanNo;
	}

	public void setFinePlanNo(String finePlanNo) {
		this.finePlanNo = finePlanNo;
	}

	public String getFinePcNo() {
		return finePcNo;
	}

	public void setFinePcNo(String finePcNo) {
		this.finePcNo = finePcNo;
	}

	public String getFineReboundDesc() {
		return fineReboundDesc;
	}

	public void setFineReboundDesc(String fineReboundDesc) {
		this.fineReboundDesc = fineReboundDesc;
	}

	public Integer getFineGroundMeter() {
		return fineGroundMeter;
	}

	public void setFineGroundMeter(Integer fineGroundMeter) {
		this.fineGroundMeter = fineGroundMeter;
	}

	public Integer getFineFloorMeter() {
		return fineFloorMeter;
	}

	public void setFineFloorMeter(Integer fineFloorMeter) {
		this.fineFloorMeter = fineFloorMeter;
	}

	public Integer getFineMinTax() {
		return fineMinTax;
	}

	public void setFineMinTax(Integer fineMinTax) {
		this.fineMinTax = fineMinTax;
	}

	public Integer getFineMaxTax() {
		return fineMaxTax;
	}

	public void setFineMaxTax(Integer fineMaxTax) {
		this.fineMaxTax = fineMaxTax;
	}

	public Integer getFineMeterCost() {
		return fineMeterCost;
	}

	public void setFineMeterCost(Integer fineMeterCost) {
		this.fineMeterCost = fineMeterCost;
	}

	public Integer getFineTaxType() {
		return fineTaxType;
	}

	public void setFineTaxType(Integer fineTaxType) {
		this.fineTaxType = fineTaxType;
	}

	public Integer getFineCreatedBy() {
		return fineCreatedBy;
	}

	public void setFineCreatedBy(Integer fineCreatedBy) {
		this.fineCreatedBy = fineCreatedBy;
	}

	public String getFineCreatedIn() {
		return fineCreatedIn;
	}

	public void setFineCreatedIn(String fineCreatedIn) {
		this.fineCreatedIn = fineCreatedIn;
	}

	public Integer getFineSignedBy() {
		return fineSignedBy;
	}

	public void setFineSignedBy(Integer fineSignedBy) {
		this.fineSignedBy = fineSignedBy;
	}

	public String getFineSignedIn() {
		return fineSignedIn;
	}

	public void setFineSignedIn(String fineSignedIn) {
		this.fineSignedIn = fineSignedIn;
	}

	public String getFineSignedNote() {
		return fineSignedNote;
	}

	public void setFineSignedNote(String fineSignedNote) {
		this.fineSignedNote = fineSignedNote;
	}
	
}
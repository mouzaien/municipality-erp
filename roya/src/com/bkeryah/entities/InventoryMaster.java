package com.bkeryah.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name = "WHS_GARD_MASTER")
public class InventoryMaster {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "GARDID")
	private Integer inventoryId;
	@Column(name = "GARD_DATE")
	private String inventoryDate;
	@Column(name = "SPECIAL_NO")
	private String specialNo;
	@Column(name = "GARD_START_DATE")
	private String inventoryStartDate;
	@Column(name = "GARD_END_DATE")
	private String inventoryEndDate;
	@Column(name = "STRNO")
	private Integer strno;
	@Column(name = "CREATEDBY")
	private Integer createdby;
	@Column(name = "CREATEDDATE")
	private String createddate;
	@Column(name = "YEARID")
	private Integer yearid;
	@Column(name = "GARD_TYPE")
	private String inventoryType;
	@Column(name = "INVENTORY_BLOCKED")
	private Integer inventoryBlocked;

	public Integer getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Integer inventoryId) {
		this.inventoryId = inventoryId;
	}

	public String getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(String inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	public String getSpecialNo() {
		return specialNo;
	}

	public void setSpecialNo(String specialNo) {
		this.specialNo = specialNo;
	}

	public String getInventoryStartDate() {
		return inventoryStartDate;
	}

	public void setInventoryStartDate(String inventoryStartDate) {
		this.inventoryStartDate = inventoryStartDate;
	}

	public String getInventoryEndDate() {
		return inventoryEndDate;
	}

	public void setInventoryEndDate(String inventoryEndDate) {
		this.inventoryEndDate = inventoryEndDate;
	}

	public String getCreateddate() {
		return createddate;
	}

	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}

	public String getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}

	public Integer getInventoryBlocked() {
		return inventoryBlocked;
	}

	public void setInventoryBlocked(Integer inventoryBlocked) {
		this.inventoryBlocked = inventoryBlocked;
	}

	public Integer getStrno() {
		return strno;
	}

	public void setStrno(Integer strno) {
		this.strno = strno;
	}

	public Integer getYearid() {
		return yearid;
	}

	public void setYearid(Integer yearid) {
		this.yearid = yearid;
	}

	public Integer getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}
}

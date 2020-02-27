package com.bkeryah.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "RETURN_STORE")
public class ReturnStore {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer returnStoreId;
	@Column(name = "RET_DATE")
	private Date returnGDate;
	@Column(name = "RET_HDATE")
	private String returnHDate;
	@Column(name = "WRHOUSE_ID")
	private Integer wrhouseId;
	@Column(name = "REASON")
	private Integer reason;
	@Column(name = "SERIAL_NUMBER")
	private Integer serialNumber;
	@Column(name = "STR_NO")
	private Integer strNo;
	@Column(name = "STATUS_Y_N")
	private String status;

	public Integer getReturnStoreId() {
		return returnStoreId;
	}

	public void setReturnStoreId(Integer returnStoreId) {
		this.returnStoreId = returnStoreId;
	}

	public Date getReturnGDate() {
		return returnGDate;
	}

	public void setReturnGDate(Date returnGDate) {
		this.returnGDate = returnGDate;
	}

	public String getReturnHDate() {
		return returnHDate;
	}

	public void setReturnHDate(String returnHDate) {
		this.returnHDate = returnHDate;
	}

	public Integer getWrhouseId() {
		return wrhouseId;
	}

	public void setWrhouseId(Integer wrhouseId) {
		this.wrhouseId = wrhouseId;
	}

	public Integer getReason() {
		return reason;
	}

	public void setReason(Integer reason) {
		this.reason = reason;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Integer getStrNo() {
		return strNo;
	}

	public void setStrNo(Integer strNo) {
		this.strNo = strNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

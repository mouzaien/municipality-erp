package com.bkeryah.licences.models;

import flexjson.JSON;

public class BldLicNewModel {
	private String licNewAplOwnerId;
	private String licNewNumber;
	private String licNewBillNumber;
	private Integer status;

	public BldLicNewModel(String licNewAplOwnerId, String licNewNumber, String licNewBillNumber, Integer status) {
		this.licNewAplOwnerId = licNewAplOwnerId;
		this.licNewNumber = licNewNumber;
		this.licNewBillNumber = licNewBillNumber;
		this.status = status;
	}

	@JSON(include = true)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@JSON(include = true)
	public String getLicNewNumber() {
		return licNewNumber;
	}

	@JSON(include = true)
	public String getLicNewAplOwnerId() {
		return licNewAplOwnerId;
	}

	public void setLicNewAplOwnerId(String licNewAplOwnerId) {
		this.licNewAplOwnerId = licNewAplOwnerId;
	}

	@JSON(include = true)
	public String getLicNewBillNumber() {
		return licNewBillNumber;
	}

	public void setLicNewBillNumber(String licNewBillNumber) {
		this.licNewBillNumber = licNewBillNumber;
	}

	public void setLicNewNumber(String licNewNumber) {
		this.licNewNumber = licNewNumber;
	}
}

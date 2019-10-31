package com.bkeryah.licences.models;

public class LicModelResult extends ResultModel {

	private Integer LicNumber;

	public LicModelResult(Integer idModel, String success) {
		super(idModel, success);
	}

	public Integer getLicNumber() {
		return LicNumber;
	}

	public void setLicNumber(Integer licNumber) {
		LicNumber = licNumber;
	}

	@Override
	public String toString() {
		return message;
	}
}

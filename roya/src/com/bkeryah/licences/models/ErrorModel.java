package com.bkeryah.licences.models;

public class ErrorModel {
	private String error;

	public ErrorModel(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return error;
	}
}

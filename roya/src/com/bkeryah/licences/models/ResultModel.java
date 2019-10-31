package com.bkeryah.licences.models;

public class ResultModel {
	protected String message;
	protected Integer idModel;

	public ResultModel(Integer idModel, String success) {
		this.idModel = idModel;
		this.message = success;
	}

	public Integer getIdModel() {
		return idModel;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setIdModel(Integer idModel) {
		this.idModel = idModel;
	}

	@Override
	public String toString() {
		return message;
	}
}

package com.bkeryah.licences;

import javax.servlet.http.HttpServletRequest;

import com.bkeryah.licences.models.ErrorModel;

import utilities.Utils;

public class ErrorExecutor extends AbstractExecutor {
	public ErrorExecutor(HttpServletRequest request) {
		super(request);
	}

	@Override
	public Object execute() {
		if (error != null)
			return new ErrorModel(error);
		return new ErrorModel(Utils.loadMessagesFromFile("request.error"));
	}

}

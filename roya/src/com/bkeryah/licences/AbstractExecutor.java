package com.bkeryah.licences;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.bkeryah.service.IDataAccessService;

import utilities.Utils;

public abstract class AbstractExecutor {
	private static ApplicationContext applicationContext;
	protected IDataAccessService dataAccessService;
	protected final Logger logger = Logger.getLogger(this.getClass());
	protected Integer reqType;
	protected String error;
	protected String emptyError = " can't be empty";
	protected String notMunchedError = " not Munched";
	protected String reedingError = "error reading your param ";
	protected String malFormedError = " must be a number";
	{
		applicationContext = SpringContext.getApplicationContext();
		dataAccessService = (IDataAccessService) applicationContext.getBean(IDataAccessService.class);

	}

	public AbstractExecutor(HttpServletRequest request) {
		logger.info("Initialization " + request.getRequestURI() + " suser :" + request.getRemoteHost());
		try {
			reqType = getIntegerParam(request, "request", true);
		} catch (ParamRequestException e) {
			error = e.getMessage();
		}
	}

	protected String getStringParam(HttpServletRequest request, String paramName, boolean isNeeded)
			throws ParamRequestException {
		String param = request.getParameter(paramName);
		if (param == null) {
			if (isNeeded)
				throw new ParamRequestException(
						Utils.loadMessagesFromFile("input.error") + Utils.loadMessagesFromFile(paramName));
		} else if (param.length() == 0 && isNeeded)
			throw new ParamRequestException(
					Utils.loadMessagesFromFile(paramName) + Utils.loadMessagesFromFile("input.empty"));
		return param;
	}

	protected Integer getIntegerParam(HttpServletRequest request, String paramName, boolean isNeeded)
			throws ParamRequestException {
		String param = request.getParameter(paramName);
		Integer intParam;
		if (param == null)
			if (isNeeded)
				throw new ParamRequestException(
						Utils.loadMessagesFromFile("input.error") + Utils.loadMessagesFromFile(paramName));
		try {
			intParam = Integer.parseInt(param);
		} catch (Exception e) {
			throw new ParamRequestException(
					Utils.loadMessagesFromFile(paramName) + Utils.loadMessagesFromFile("input.number"));
		}
		return intParam;
	}

	public AbstractExecutor() {
	}

	public Integer getReqType() {
		return reqType;
	}

	public void setReqType(Integer reqType) {
		this.reqType = reqType;
	}

	public abstract Object execute();

}

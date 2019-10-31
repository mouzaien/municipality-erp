package com.bkeryah.licences;

import javax.servlet.http.HttpServletRequest;

import com.bkeryah.mails.MailTypeEnum;

public class BkServicesFactory {
	private static BkServicesFactory instance = null;

	private BkServicesFactory() {
	}

	public static synchronized BkServicesFactory getInstance() {
		if (instance == null) {
			instance = new BkServicesFactory();
		}
		return instance;
	}

	public AbstractExecutor getExecutor(final HttpServletRequest request) {
		String requestType = request.getParameter("request");
		if (requestType != null) {
			LicencesTypeEnum mailType = LicencesTypeEnum.getValue(requestType);
			switch (mailType) {
			case ERROR:
				return new ErrorExecutor(request);
			case BUILDLICNEW:
				return new BldLicNewExecutor(request);
			case BUILDLICEXT:
				return new BldLicNewExecutor(request);
			case ARCPEOPLE:
				return new ArcPeopleExecutor(request);
			case BLDLICLIST:
				return new BldLicListExecutor(request);

			default:
				return new ErrorExecutor(request);
			}
		}
		return new ErrorExecutor(request);
	}
}

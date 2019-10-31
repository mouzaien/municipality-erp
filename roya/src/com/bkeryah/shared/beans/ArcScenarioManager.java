package com.bkeryah.shared.beans;

import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.service.IDataAccessService;

import utilities.Utils;

public class ArcScenarioManager {
	public MailTypeEnum typeId = MailTypeEnum.EXCHANGEREQUEST;

	public MailTypeEnum getTypeId() {
		return typeId;
	}

	public void setTypeId(MailTypeEnum typeId) {
		this.typeId = typeId;
	}

	public boolean isCurrUserSignAutorized(IDataAccessService dataAccessService, Integer appID) {
		boolean isSignedAutorized = false;
		Integer wrkId = dataAccessService.getWrkIdByArc(appID);
		try {
			final int FIRST_STEP = 1;
			int fromOfModelStep = 0;
			if ((typeId.getValue() == MailTypeEnum.VACATION.getValue())
					&& (dataAccessService.getWrkCountSigned(appID) == FIRST_STEP)) {
				fromOfModelStep = dataAccessService.findUserFromHrsSigns(wrkId).getUserId();
			} else {
				fromOfModelStep = dataAccessService.getNextScenarioUserId(typeId.getValue(), wrkId, appID, 1);
			}
			if (fromOfModelStep == 0) {
				return false;
			}
			Integer currentUserId = Utils.findCurrentUser().getUserId();
			if (currentUserId == fromOfModelStep)
				isSignedAutorized = true;
			else {
				isSignedAutorized = false;
				System.out.println("current user is :" + currentUserId
						+ " and autorized user id that must have signed before is " + fromOfModelStep);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSignedAutorized;
	}

}

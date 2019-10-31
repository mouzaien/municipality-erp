package com.bkeryah.mails;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

public class TransactionExecutor extends MailExecutor<Object> {

	public TransactionExecutor(MailTypeEnum mailType) {
		super(mailType);
		setModelStatus(true);
	}

	@Override
	public void execute(IDataAccessService dataAccessService) {
		isModel = false;
		hasComment = false;
	}

	@Override
	public String saveAction(IDataAccessService dataAccessService) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String acceptAction() {
		return "mails";

	}

	@Override
	public String refuseAction() {
		try {
			// int userTo = 0;
			// employeeVacation.setVacationStatus(MyConstants.NO);
			// refuseModel(userTo, wrkApplicationId, employeeVacation);
			MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}

	@Override
	public Employer loadEmployerDataByRecordId(int arcRecordId) {

		return new Employer();
	}

	@Override
	public Object loadData(UserMailObj userMailClass) {
		// TODO Auto-generated method stub
		isModel = false;
		hasComment = false;
		return null;
	}

	@Override
	public void prepareData() {
		// TODO Auto-generated method stub

	}

}

package com.bkeryah.mails;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.entities.HrMedicalRequest;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

public class MedicalReportRequestExcuter extends MailExecutor<HrMedicalRequest> {
	
	private HrMedicalRequest medicalRequestInfo = new HrMedicalRequest();
//	private String type = "MEDICALREPORT";
//	private boolean insertMode;
	private boolean actionMode;
	private Integer recordId;
	private Integer docType;
	private boolean authorized;
	
	public MedicalReportRequestExcuter(MailTypeEnum mailType) {
		super(mailType);
	}

	@Override
	public void execute() {
		contentHtml = "./model3.xhtml";
		page = "medicalReport.xhtml";
		super.execute();
	}

	@Override
	public void execute(IDataAccessService dataAccessService) {
		modelContentHtml = "medicalReport.xhtml";
		this.dataAccessService = dataAccessService;

		setActionMode(false);
	}

	@Override
	public String saveAction(IDataAccessService dataAccessService) {
		try {
			String date = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("includeform:startDate");
			if (date != null && !date.equals(null) && !date.isEmpty()) {
				medicalRequestInfo.setRequestDate(date);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, Utils.loadMessagesFromFile("date.required"), Utils.loadMessagesFromFile("date.required")));
				return "";
			}

			Integer currentUser = Utils.findCurrentUser().getUserId();
			dataAccessService.addNewHrMedicalRequest(medicalRequestInfo, currentUser, currentUser);
			medicalRequestInfo = new HrMedicalRequest();
//			FacesContext.getCurrentInstance().addMessage(null,
//					new FacesMessage(FacesMessage.SEVERITY_INFO, "تم ارسال طلبك", "تم ارسال طلبك"));
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.execution"));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("saveAction medicalReport	" + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, Utils.loadMessagesFromFile("error.operation"), Utils.loadMessagesFromFile("error.operation")));
			return null;
		}

	}

	@Override
	public HrMedicalRequest loadData(UserMailObj userMailClass) {
		isCurrUserSignAutorized(userMailClass);
		docType = userMailClass.getWrkType();
		authorized = isSignedAutorized;
		reportName = MyConstants.REPORT_MEDICAL;
		setActionMode(true);
		wrkApplicationId = new WrkApplicationId(Integer.parseInt(userMailClass.getWrkId()), userMailClass.getStepId());
		recordId = Integer.parseInt(userMailClass.getAppId());
		setMedicalRequestInfo(dataAccessService.getMedicalRequestInfoByArchRecordId(recordId));
		return null;
	}

	@Override
	public String acceptAction() {
		if (isSignedAutorized) {
			try {
				dataAccessService.acceptActionforHrmedicalReport(recordId, docType, medicalRequestInfo);
				MsgEntry.addAcceptFlashInfoMessage();
				return "mails";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(" error in accept in medical report " + e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, Utils.loadMessagesFromFile("error.operation"), Utils.loadMessagesFromFile("error.operation")));
				return null;
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Utils.loadMessagesFromFile("not.authorized"), Utils.loadMessagesFromFile("not.authorized")));
			return "mails";
		}
	}

	@Override
	public String refuseAction() {
		try {
			medicalRequestInfo.setAcceptStatus("N");
			WrkApplication newApplication = createNewWrkAppForRefuse();
			try {
				refuseModel(newApplication, medicalRequestInfo);
				MsgEntry.addRefuseFlashInfoMessage();
			} catch (Exception e) {
				logger.error("refuseAction for medical  :" + e.getMessage());
				return null;
			}
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}
	
	/**
	 * Print report
	 */
	public String printMedicalReportAction() {
		String reportName = "/reports/medical_report.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("record_id", recordId);
		HrMedicalRequest medical = dataAccessService.getMedicalRequestInfoByArchRecordId(recordId);
		parameters.put("day", Utils.getDayForHigriDate(medical.getRequestDate()));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	@Override
	public Employer loadEmployerDataByRecordId(int selectedId) {
		return null;
	}

	@Override
	public void prepareData() {
	}
	
	/**
	 * Getters and setters
	 * */

	public boolean isActionMode() {
		return actionMode;
	}

	public void setActionMode(boolean actionMode) {
		this.actionMode = actionMode;
	}

	public HrMedicalRequest getMedicalRequestInfo() {
		return medicalRequestInfo;
	}

	public void setMedicalRequestInfo(HrMedicalRequest medicalRequestInfo) {
		this.medicalRequestInfo = medicalRequestInfo;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Integer getDocType() {
		return docType;
	}

	public void setDocType(Integer docType) {
		this.docType = docType;
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	public WrkApplicationId getWrkApplicationId() {
		return wrkApplicationId;
	}

	public void setWrkApplicationId(WrkApplicationId wrkApplicationId) {
		this.wrkApplicationId = wrkApplicationId;
	}

}
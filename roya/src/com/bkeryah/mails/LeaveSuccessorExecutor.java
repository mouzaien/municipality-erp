package com.bkeryah.mails;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.EmployeeInitiation;
import com.bkeryah.entities.HrEmployeeVacation;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ViewScoped
public class LeaveSuccessorExecutor extends MailExecutor<EmployeeInitiation> {

	// private ArcUsers v = new ArcUsers();
	private HrEmployeeVacation employeeVacation = new HrEmployeeVacation();
	private EmployeeInitiation employeeInitiation = new EmployeeInitiation();
//	HijriCalendarUtil hutil = new HijriCalendarUtil();
	private String currentUserName;
	private boolean disabledButton;
	private boolean disabledButtonForError;
	private Integer docType;
	private boolean authorized;
	private Integer recordId;
	private Integer employeeNumber;

	private boolean actionMode;

	public LeaveSuccessorExecutor(MailTypeEnum mailType) {
		super(mailType);
	}

	public HrEmployeeVacation getEmployeeVacation() {
		return employeeVacation;
	}

	public void setEmployeeVacation(HrEmployeeVacation employeeVacation) {
		this.employeeVacation = employeeVacation;
	}

	public EmployeeInitiation getEmployeeInitiation() {
		return employeeInitiation;
	}

	public void setEmployeeInitiation(EmployeeInitiation employeeInitiation) {
		this.employeeInitiation = employeeInitiation;
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}

	@Override
	public void execute() {
		contentHtml = "./model3.xhtml";
		page = "initiationWorkAfterVacation.xhtml";
		super.execute();
	}

	@Override
	public void execute(IDataAccessService dataAccessService) {
		modelContentHtml = "initiationWorkAfterVacation.xhtml";

		employeeNumber = Utils.findCurrentUser().getEmployeeNumber();
		this.dataAccessService = dataAccessService;
		// currentUserName = Utils.findCurrentUser().getEmployeeName();
		// employeeInitiation.setIntaionHigriDate(HijriCalendarUtil.findCurrentHijriDate());
		// employeeInitiation.setIntaionDay(Utils.getDayForCurrentDate());
		// Integer lastvacationId = dataAccessService
		// .getLastVacationForCurrentId(Utils.findCurrentUser().getEmployeeNumber());

		//
		// if (lastvacationId != null) {
		// employeeVacation = dataAccessService.getVacationById(lastvacationId);
		// // modelContentHtml = "initiationWorkAfterVacation.xhtml";
		// setDisabledButtonForError(false);
		//
		// } else {
		// setDisabledButtonForError(true);
		//
		// // modelContentHtml = "error.xhtml";
		// }



	}

	public void updatDay() {

		employeeInitiation.setIntaionDay(Utils.getDayForHigriDate(employeeInitiation.getIntaionHigriDate()));
	}

	@Override
	public String saveAction(IDataAccessService dataAccessService) {

		// employeeInitiation.setIntaionDay(Utils.getDayForHigriDate(employeeInitiation.getIntaionHigriDate()));
		employeeInitiation.setVacationId(employeeVacation.getId());     
		if (employeeInitiation.getIntaionDay() !=Utils.loadMessagesFromFile("friday") && employeeInitiation.getIntaionDay() != Utils.loadMessagesFromFile("saturday")) {
			try {

				Integer id = dataAccessService.newEmpInitaion(employeeInitiation, employeeVacation.getId());

				// employeeInitiation = new EmployeeInitiation();
				setDisabledButton(true);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						
						Utils.loadMessagesFromFile("request.sucess"),Utils.loadMessagesFromFile("request.sucess")));

				return "mails";
			} catch (Exception e) {
				logger.error("error in send Mobashra " + e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, Utils.loadMessagesFromFile("direct.vacation.error"),Utils.loadMessagesFromFile("direct.vacation.error")));
				return "";
			}

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							Utils.loadMessagesFromFile("date.invalid.vacation"),
							Utils.loadMessagesFromFile("date.invalid.vacation")));
			return null;
		}
		
	}

	@Override
	public EmployeeInitiation loadData(UserMailObj userMailClass) {

		isCurrUserSignAutorized(userMailClass);
		docType = userMailClass.getWrkType();
		authorized = isSignedAutorized;
		setActionMode(true);
		reportName = MyConstants.REPORT_EMP_INIT;
		recordId = Integer.parseInt(userMailClass.getAppId());
		wrkApplicationId = new WrkApplicationId(Integer.parseInt(userMailClass.getWrkId()), userMailClass.getStepId());
		Integer fromUserId = dataAccessService.getUserIdFromWorkAppByIdAndStepId(recordId, 1);
		ArcUsers fromUser = (ArcUsers) dataAccessService.findEntityById(ArcUsers.class, fromUserId);
		currentUserName = fromUser.getEmployeeName();
		setEmployeeInitiation(dataAccessService.getEmployeeInitiationByArchrecorId(recordId));
		setEmployeeVacation((HrEmployeeVacation) dataAccessService.findEntityById(HrEmployeeVacation.class,
				employeeInitiation.getVacationId()));
		return null;
	}

	@Override
	public String acceptAction() {
		if (authorized) {

			try {
				employeeInitiation.setAcceptStatus("y");

				dataAccessService.acceptActionForEmployeeInitiation(recordId, docType, employeeVacation.getId(),
						employeeInitiation);
				// dataAccessService.updateObject(employeeVacation);
				MsgEntry.addAcceptFlashInfoMessage();
				return "mails";
			} catch (Exception e) {
				logger.error("error in accept L mobashraa" + e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						Utils.loadMessagesFromFile("error.processing")	, Utils.loadMessagesFromFile("error.processing")));
				e.printStackTrace();

				return null;
			}
		} else {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Utils.loadMessagesFromFile("error.processing"),Utils.loadMessagesFromFile("error.processing") ));
			return null;
		}

		// return "mails";
	}

	@Override
	public String refuseAction() {
		try {
			// int userTo = 0;
			employeeInitiation.setAcceptStatus("N");
			employeeVacation.setHaveInitiation(0);

//			WrkApplication oldWrkApp = (WrkApplication) dataAccessService.findWrkApplicationById(wrkApplicationId);
			// int userTo =
			// dataAccessService.getUserIdFromWorkAppByIdAndStepId(oldWrkApp.getArcRecordId(),
			// 1);

			/**** begin create new application **/
			WrkApplication newApplication = createNewWrkAppForRefuse();
			/*** end **/
			// refuseModel(newApplication, employeeInitiation);
			// employeeVacation.setVacationStatus(MyConstants.NO);
			refuseModel(newApplication, employeeInitiation);
			dataAccessService.updateObject(employeeVacation);
			MsgEntry.addRefuseFlashInfoMessage();
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("error in accept L mobashra " + e.getMessage());
			return "";
		}
	}

	@Override
	public Employer loadEmployerDataByRecordId(int selectedId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void prepareData() {
		currentUserName = Utils.findCurrentUser().getEmployeeName();
		employeeInitiation.setIntaionHigriDate(HijriCalendarUtil.findCurrentHijriDate());
		employeeInitiation.setIntaionDay(Utils.getDayForCurrentDate());
		Integer lastvacationId = dataAccessService.getLastVacationForCurrentId(employeeNumber);
		//EmployeeInitiation LastEmployeeInitiation = dataAccessService.getLastEmployeeInitiation();


		if (lastvacationId != null) {
			employeeVacation = dataAccessService.getVacationById(lastvacationId);
			// modelContentHtml = "initiationWorkAfterVacation.xhtml";
			setDisabledButtonForError(false);

		} else {
			setDisabledButtonForError(true);

			// modelContentHtml = "error.xhtml";
		}


	}

	public void setDisabledButton(boolean disabledButton) {
		this.disabledButton = disabledButton;
	}

	public boolean isDisabledButtonForError() {
		return disabledButtonForError;
	}

	public void setDisabledButtonForError(boolean disabledButtonForError) {
		this.disabledButtonForError = disabledButtonForError;
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

	public boolean isActionMode() {
		return actionMode;
	}

	public void setActionMode(boolean actionMode) {
		this.actionMode = actionMode;
	}

	public boolean isDisabledButton() {
		return disabledButton;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Integer getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(Integer employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public WrkApplicationId getWrkApplicationId() {
		return wrkApplicationId;
	}

	public void setWrkApplicationId(WrkApplicationId wrkApplicationId) {
		this.wrkApplicationId = wrkApplicationId;
	}

}
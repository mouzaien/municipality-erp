package com.bkeryah.mails;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.faces.context.FacesContext;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.HrsUserAbsent;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

/**
 * This class is used for authorization's model
 * 
 *	@author habib
 */
public class SpecialAutorisationExecutor extends MailExecutor<HrsUserAbsent> {

	private String autorizationDay;
	private String autorizationDate;
	private String leavingHour;
	private String leavingMinute;
	private String returnHour;
	private String returnMinute;
	private String reason;
	private HrsUserAbsent authorization;
	private int autorizationNB;
	

	public SpecialAutorisationExecutor(MailTypeEnum mailType) {
		super(mailType);
	}

	@Override
	public void execute(IDataAccessService dataAccessService) {
		if(getModeScreen() == MyConstants.INSERT_MODE)
			modelContentHtml = "sp_autorization_model.xhtml";
		else
			modelContentHtml = "sp_autorization_model_view.xhtml";
		this.dataAccessService = dataAccessService;
	}

	@Override
	public String saveAction(IDataAccessService dataAccessService) {
		if (!checkFields())
			return "";
		ArcUsers user = Utils.findCurrentUser();

		try {
			String title = MessageFormat.format(Utils.loadMessagesFromFile("authorization.title"),
					(user.getEmployeeName()==null)?user.getFirstName():user.getEmployeeName(), HijriCalendarUtil.findCurrentHijriDate());
			HrsUserAbsent userAbsent = new HrsUserAbsent(user.getUserId(), user.getEmployeeNumber(), autorizationDate,
					leavingHour + ":" + leavingMinute, returnHour + ":" + returnMinute, MyConstants.PERMISSION,
					Utils.convertHijriDateToGregorian(autorizationDate), reason);
			dataAccessService.saveAuthorizationOrMission(userAbsent, title, MailTypeEnum.SPECIAL_AUTORIZATION.getValue(), false);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("accept.record"));
			resetFields();
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}

	}

	/**
	 * Reset the screen's data
	 */
	private void resetFields() {
		autorizationDay = null;
		autorizationDate = null;
		reason = null;
		leavingHour = null;
		leavingMinute = null;
		returnHour = null;
		returnMinute = null;
		authorization = null;
	}

	/**
	 * Validate screen's fields
	 * 
	 * @return
	 */
	private boolean checkFields() {
		boolean valid = true;
		
		// Check required fields
		if (!checkRequiredFields())
			valid = false;
		else {
			//TODO
//			Integer period = null;
//			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//			try {
//				period = Utils.getDiffDays(HijriCalendarUtil.ConvertHijriTogeorgianDate(autorizationDate), sdf.format(new Date()));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			if((period > 1) || (period < -3)){
//				valid = false;
//				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.authorization.period"));
//			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				if(!HijriCalendarUtil.ConvertHijriTogeorgianDate(autorizationDate).equals(sdf.format(new Date()))){
					valid = false;
					MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.some.day"));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			// Check that the leaving time is before return time
			if (!Utils.checkValidTime(Integer.parseInt(leavingHour), Integer.parseInt(returnHour),
					Integer.parseInt(leavingMinute), Integer.parseInt(returnMinute)))
				valid = false;
			// Check that the authorization time is 2 hours
			else if (!Utils.checkEcartTime(Integer.parseInt(leavingHour), Integer.parseInt(returnHour),
					Integer.parseInt(leavingMinute), Integer.parseInt(returnMinute), 2))
				valid = false;
			// Check that the authorization's number is less or equal 3
			else if (dataAccessService.getAuthorizationsNumber(Utils.findCurrentUser().getUserId(),
					autorizationDate.substring(3, 5), autorizationDate.substring(6, 10)) >= 3) {
				MsgEntry.addErrorMessage(MsgEntry.ERROR_AUTHORIZATION_NB);
				valid = false;
			}
		}
		return valid;
	}

	/**
	 * Check required fields
	 * 
	 * @return
	 */
	private boolean checkRequiredFields() {
		boolean valid = true;
		String date = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("startDate");
		if(!date.isEmpty()){
			autorizationDate = date;
		} else {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.date"));
			return false;
		}
		if ((autorizationDate == null) || (autorizationDate.trim().equals(""))) {
			MsgEntry.addErrorAspectToComponent("autorizationDate");
			valid = false;
		}
		if ((reason == null) || (reason.trim().equals(""))) {
			MsgEntry.addErrorAspectToComponent("reason");
			valid = false;
		}
		if ((leavingHour == null) || (leavingHour.trim().equals(""))) {
			MsgEntry.addErrorAspectToComponent("leavingHour");
			valid = false;
		}
		if ((leavingMinute == null) || (leavingMinute.trim().equals(""))) {
			MsgEntry.addErrorAspectToComponent("leavingMinute");
			valid = false;
		}
		if ((returnHour == null) || (returnHour.trim().equals(""))) {
			MsgEntry.addErrorAspectToComponent("returnHour");
			valid = false;
		}
		if ((returnMinute == null) || (returnMinute.trim().equals(""))) {
			MsgEntry.addErrorAspectToComponent("returnMinute");
			valid = false;
		}
		if (!valid)
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("required.fields"));
		return valid;
	}
	
	@Override
	public HrsUserAbsent loadData(UserMailObj userMailClass) {
		isCurrUserSignAutorized(userMailClass);
		wrkApplicationId = new WrkApplicationId(Integer.parseInt(userMailClass.getWrkId()), userMailClass.getStepId());
		authorization = dataAccessService
				.loadAuthorizationOrMissionByRecordId(Integer.parseInt(userMailClass.getAppId().trim()));
		if (authorization != null && authorization.getId() != 0) {
			setAuthorizationValues(authorization);
		}
		return authorization;
	}

	/**
	 * Set authorization model values in consult screen
	 * @param authorization
	 */
	private void setAuthorizationValues(HrsUserAbsent authorization) {
		Calendar cal = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		Date date = null;
		try {
			date = format.parse(authorization.getJabsDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(date);
		autorizationDay = "" + cal.get(Calendar.DAY_OF_WEEK);
		autorizationDate = authorization.getAbseDate();
		leavingHour = authorization.getAbsForm().substring(0, authorization.getAbsForm().indexOf(":"));
		leavingMinute = authorization.getAbsForm().substring(authorization.getAbsForm().indexOf(":") + 1);
		returnHour = authorization.getAbsTo().substring(0, authorization.getAbsTo().indexOf(":"));
		returnMinute = authorization.getAbsTo().substring(authorization.getAbsTo().indexOf(":") + 1);
		reason = authorization.getReason();
		autorizationNB = dataAccessService.getAuthorizationsNumber(authorization.getUserId(), HijriCalendarUtil.findCurrentMonth(), HijriCalendarUtil.findCurrentYear());
	}
	
	@Override
	public String acceptAction() {
		try {
			dataAccessService.acceptAuthorizationOrMission(authorization, wrkApplicationId,
					MailTypeEnum.SPECIAL_AUTORIZATION.getValue());
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("accept.record"));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			authorization.setAccept(null);
			return "";
		}
	}

	@Override
	public String refuseAction() {
		try {
			authorization.setAccept(MyConstants.NO);
			/**** begin create new application **/
			WrkApplication newApplication = createNewWrkAppForRefuse();
			/*** end **/
			refuseModel(newApplication, authorization);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("refuse.record"));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}

	@Override
	public Employer loadEmployerDataByRecordId(int arcRecordId) {
		return dataAccessService.loadEmployerFromAuthorizationOrMission(arcRecordId);
	}
	
	@Override
	public void prepareData() {
		autorizationNB = dataAccessService.getAuthorizationsNumber(Utils.findCurrentUser().getUserId(), HijriCalendarUtil.findCurrentMonth(), HijriCalendarUtil.findCurrentYear());
	}

	public String getLeavingHour() {
		return leavingHour;
	}

	public void setLeavingHour(String leavingHour) {
		this.leavingHour = leavingHour;
	}

	public String getLeavingMinute() {
		return leavingMinute;
	}

	public void setLeavingMinute(String leavingMinute) {
		this.leavingMinute = leavingMinute;
	}

	public String getReturnHour() {
		return returnHour;
	}

	public void setReturnHour(String returnHour) {
		this.returnHour = returnHour;
	}

	public String getReturnMinute() {
		return returnMinute;
	}

	public void setReturnMinute(String returnMinute) {
		this.returnMinute = returnMinute;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public String getAutorizationDay() {
		return autorizationDay;
	}

	public void setAutorizationDay(String autorizationDay) {
		this.autorizationDay = autorizationDay;
	}

	public String getAutorizationDate() {
		return autorizationDate;
	}

	public void setAutorizationDate(String autorizationDate) {
		this.autorizationDate = autorizationDate;
	}

	

	public WrkApplicationId getWrkApplicationId() {
		return wrkApplicationId;
	}

	public void setWrkApplicationId(WrkApplicationId wrkApplicationId) {
		this.wrkApplicationId = wrkApplicationId;
	}

	public boolean isAccepted() {
		return (!isReadOnly() || ("Y".equalsIgnoreCase(authorization.getAccept())));
	}

	public int getAutorizationNB() {
		return autorizationNB;
	}

	public void setAutorizationNB(int autorizationNB) {
		this.autorizationNB = autorizationNB;
	}

}

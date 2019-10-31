package com.bkeryah.mails;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.HrsUserAbsent;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.fng.entities.AutorizationSettings;
import com.bkeryah.fng.entities.FngTimeTable;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

/**
 * This class is used for authorization's model
 * 
 * @author habib
 */
// @ViewScoped
public class AutorisationExecutor extends MailExecutor<HrsUserAbsent> {

	private String autorizationDay;
	private String autorizationDate;
	private String leavingHour;
	private String leavingMinute;
	private String returnHour;
	private String returnMinute;
	private String reason;
	private HrsUserAbsent authorization;
	private int autorizationNB;
	// private FngTimeTable workTime;
	private AutorizationSettings autorization = new AutorizationSettings();
	private boolean higriMode;
	private String higriDate;
	private Date grigDate;
	private List<HrsUserAbsent> absent;
	private Integer employerId;
	private String deptId;
	private ArcUsers Currentuser;
	private boolean manager = false;

	private List<User> users = new ArrayList<User>();

	public AutorisationExecutor(MailTypeEnum mailType) {
		super(mailType);

	}

	// @PostConstruct
	// public void init() {
	// autorization = dataAccessService.loadAutorization();
	// }

	@Override
	public void execute(IDataAccessService dataAccessService) {
		autorization = dataAccessService.loadAutorization();
		Currentuser = Utils.findCurrentUser();
		deptId = Currentuser.getUserDept().getId().toString();
		if (Currentuser.getWrkRoleId().equals(MyConstants.MANAGER_ROLE)) {
			manager = true;
		}
		users = dataAccessService.getAllEmployeesInDept(Integer.parseInt(deptId));
		if (getModeScreen() == MyConstants.INSERT_MODE)
			modelContentHtml = "autorization_model.xhtml";
		else
			modelContentHtml = "autorization_model_view.xhtml";
		this.dataAccessService = dataAccessService;
	}

	@Override
	public String saveAction(IDataAccessService dataAccessService) {
		if (!checkFields())
			return "";
		ArcUsers user = new ArcUsers();
		HrsUserAbsent userAbsent = new HrsUserAbsent();
		String title;
		try {

			if (employerId != null) {
				user = dataAccessService.findUserById(employerId);
				title = MessageFormat.format(Utils.loadMessagesFromFile("authorization.title"),
						(user.getEmployeeName() == null) ? user.getFirstName() : user.getEmployeeName(),
						HijriCalendarUtil.findCurrentHijriDate());
				userAbsent = new HrsUserAbsent(user.getUserId(), user.getEmployeeNumber(), autorizationDate,
						leavingHour + ":" + leavingMinute, returnHour + ":" + returnMinute, MyConstants.PERMISSION,
						Utils.convertHijriDateToGregorian(autorizationDate), reason);
			} else {
				user = Utils.findCurrentUser();
				title = MessageFormat.format(Utils.loadMessagesFromFile("authorization.title"),
						(user.getEmployeeName() == null) ? user.getFirstName() : user.getEmployeeName(),
						HijriCalendarUtil.findCurrentHijriDate());
				userAbsent = new HrsUserAbsent(user.getUserId(), user.getEmployeeNumber(), autorizationDate,
						leavingHour + ":" + leavingMinute, returnHour + ":" + returnMinute, MyConstants.PERMISSION,
						Utils.convertHijriDateToGregorian(autorizationDate), reason);
			}

			dataAccessService.saveAuthorizationOrMission(userAbsent, title, MailTypeEnum.AUTORIZATION.getValue(),
					false);
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
		Integer currUserId = Utils.findCurrentUser().getUserId();
		List<HrsUserAbsent> allEmpApsent = dataAccessService.getAuthorizationsHoursNumber(currUserId,
				HijriCalendarUtil.findCurrentHijriDate().substring(3, 5),
				HijriCalendarUtil.findCurrentHijriDate().substring(6, 10));
		if (!checkRequiredFields())
			valid = false;
		else {
			// TODO
			// Integer period = null;
			// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			// try {
			// period =
			// Utils.getDiffDays(HijriCalendarUtil.ConvertHijriTogeorgianDate(autorizationDate),
			// sdf.format(new Date()));
			// } catch (ParseException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// if((period > 1) || (period < -3)){
			// valid = false;
			// MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.authorization.period"));
			// }

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				if (!HijriCalendarUtil.ConvertHijriTogeorgianDate(autorizationDate).equals(sdf.format(new Date()))) {
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

			// else if (!Utils.checkEcartTime(Integer.parseInt(leavingHour),
			// Integer.parseInt(returnHour),
			// Integer.parseInt(leavingMinute), Integer.parseInt(returnMinute),
			// 2))
			else if (!Utils.checkEcartTime(Integer.parseInt(leavingHour), Integer.parseInt(returnHour),
					Integer.parseInt(leavingMinute), Integer.parseInt(returnMinute), autorization.getAutorDayHoursMin(),
					autorization.getAutorDayHoursMax()))
				valid = false;
			// Check that the authorization's number is less or equal 3
			// else if
			// (dataAccessService.getAuthorizationsNumber(Utils.findCurrentUser().getUserId(),
			// autorizationDate.substring(3, 5), autorizationDate.substring(6,
			// 10)) >= 3) {
			// MsgEntry.addErrorMessage(MsgEntry.ERROR_AUTHORIZATION_NB);
			// valid = false;
			// }

			// Check that the authorization's hours number is less or equal

			else if (allEmpApsent != null) {

				long diff = 0;
				long autorMonthHours = (long) (autorization.getAutorMonthHours() * 60);
				if (allEmpApsent != null) {
					absent = allEmpApsent;
					System.out.println("print");
					System.out.println("print" + HijriCalendarUtil.findCurrentHijriDate().substring(6, 10));
					System.out.println("print" + HijriCalendarUtil.findCurrentHijriDate().substring(3, 5));
					System.out.println("autorMonthHours" + autorMonthHours);
					for (HrsUserAbsent abs : absent) {
						System.out.println("________________________");
						System.out.println("Date --> " + abs.getAbseDate() + ", From --> " + abs.getAbsForm()
								+ ", To --> " + abs.getAbsTo() + ", Type -->" + abs.getAbsType() + "......");
						diff += calcAbsentTmie(abs.getAbsForm(), abs.getAbsTo());
						System.out.println(" Difference to " + absent.indexOf(abs) + " = " + diff);

					}
					String leave = leavingHour + ":" + leavingMinute;
					String returnt = returnHour + ":" + returnMinute;
					diff += calcAbsentTmie(leave, returnt);
					System.out.println(" Time in this day = " + calcAbsentTmie(leave, returnt));
					System.out.println(" All Absent Time = " + diff);
					if (diff > autorMonthHours) {
						MsgEntry.addErrorMessage(MsgEntry.ERROR_AUTHORIZATION_NB);
						valid = false;
					}
				}

			}
			// if(!checkValidTime()){
			// MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.autorization.time"));
			// valid = false;
			// }
		}
		return valid;
	}

	public long calcAbsentTmie(String from, String to) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		long difference = 0, sumOfTmies = 0;
		try {
			Date dFrom = sdf.parse(from);
			Date dTo = sdf.parse(to);
			difference = dTo.getTime() - dFrom.getTime();
			long diffMinutes = difference / (60 * 1000) % 60;
			long diffHours = difference / (60 * 60 * 1000) % 24;
			sumOfTmies = (diffHours * 60) + diffMinutes;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return sumOfTmies;
	}

	public int calAutorationsHoursNumber() {
		return 0;
	}

	// private boolean checkValidTime() {
	// boolean valid = true;
	// Integer beginWorkHour =
	// Integer.parseInt(workTime.getStartWork().substring(0, 2));
	// Integer beginWorkMin =
	// Integer.parseInt(workTime.getStartWork().substring(3, 5));
	// Integer endWorkHour =
	// Integer.parseInt(workTime.getFingerEndClosed().substring(0, 2));
	// Integer endWorkMin =
	// Integer.parseInt(workTime.getFingerEndClosed().substring(3, 5));
	// if((Integer.parseInt(leavingHour)<beginWorkHour) ||
	// ((Integer.parseInt(leavingHour)==beginWorkHour)&&(Integer.parseInt(leavingMinute)<beginWorkMin)))
	// valid = false;
	// if((Integer.parseInt(returnHour)>endWorkHour) ||
	// ((Integer.parseInt(returnHour)==endWorkHour)&&(Integer.parseInt(returnMinute)>endWorkMin)))
	// valid = false;
	// return valid;
	// }

	/**
	 * Check required fields
	 * 
	 * @return
	 */
	private boolean checkRequiredFields() {
		boolean valid = true;
		// String date =
		// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
		// .get("includeform:VACASTRT");

		String date;
		try {
			if (higriMode) {
				grigDate = Utils.convertHDateToGDate(higriDate);
			} else {
				higriDate = Utils.grigDatesConvert(grigDate);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		date = higriDate;
		if (date!=null && !date.isEmpty()) {
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
	 * 
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
		autorizationNB = dataAccessService.getAuthorizationsNumber(authorization.getUserId(),
				HijriCalendarUtil.findCurrentMonth(), HijriCalendarUtil.findCurrentYear());
	}

	@Override
	public String acceptAction() {
		try {
			if (dataAccessService.getAuthorizationsNumber(authorization.getUserId(), autorizationDate.substring(3, 5),
					autorizationDate.substring(6, 10)) < 3) {
				dataAccessService.acceptAuthorizationOrMission(authorization, wrkApplicationId,
						MailTypeEnum.AUTORIZATION.getValue());
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("accept.record"));
				return "mails";
			} else {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation.autNumber"));
				authorization.setAccept(null);
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			authorization.setAccept(null);
			return "mails";
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
		autorizationNB = dataAccessService.getAuthorizationsNumber(Utils.findCurrentUser().getUserId(),
				HijriCalendarUtil.findCurrentMonth(), HijriCalendarUtil.findCurrentYear());
		// workTime =
		// dataAccessService.loadTimeShiftByUserId(Utils.findCurrentUser().getUserId());
	}

	public void loadEmployerData() {
		try {
			setEmployer(dataAccessService.loadEmployerByUser(dataAccessService.loadUserById(employerId)));
			employer.setId(employerId);
			prepareData();
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + " :"
					+ Utils.loadMessagesFromFile("error.emplyerNumber"));
			return;
		}
	}

	public void loadEmployersByDept(AjaxBehaviorEvent event) {
		users = dataAccessService.getAllEmployeesInDept(Integer.parseInt(deptId));
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

	public HrsUserAbsent getAuthorization() {
		return authorization;
	}

	public void setAuthorization(HrsUserAbsent authorization) {
		this.authorization = authorization;
	}

	public boolean isHigriMode() {
		return higriMode;
	}

	public void setHigriMode(boolean higriMode) {
		this.higriMode = higriMode;
	}

	public String getHigriDate() {
		return higriDate;
	}

	public void setHigriDate(String higriDate) {
		this.higriDate = higriDate;
	}

	public Date getGrigDate() {
		return grigDate;
	}

	public void setGrigDate(Date grigDate) {
		this.grigDate = grigDate;
	}

	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public ArcUsers getCurrentuser() {
		return Currentuser;
	}

	public void setCurrentuser(ArcUsers currentuser) {
		Currentuser = currentuser;
	}

	public boolean isManager() {
		return manager;
	}

	public void setManager(boolean manager) {
		this.manager = manager;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	// public FngTimeTable getWorkTime() {
	// return workTime;
	// }
	//
	// public void setWorkTime(FngTimeTable workTime) {
	// this.workTime = workTime;
	// }

}

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

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.transaction.annotation.Transactional;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.HrsUserAbsent;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

/**
 * This class is used for mission's model
 * 
 * @author habib
 *
 */
public class MissionExecutor extends MailExecutor<HrsUserAbsent> {

	public MissionExecutor(MailTypeEnum mailType) {
		super(mailType);
	}

	private String missionDay;
	private String missionDate;
	private String leavingHour;
	private String leavingMinute;
	private String returnHour;
	private String returnMinute;
	private String reason;

	private boolean higriMode;
	private String higriDate;
	private Date grigDate;

	private HrsUserAbsent mission;
	private SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

	private Integer employerId;
	private String deptId;
	private ArcUsers Currentuser;
	private boolean manager = false;

	private List<User> users = new ArrayList<User>();

	@Override
	public void execute(IDataAccessService dataAccessService) {
		Currentuser = Utils.findCurrentUser();
		deptId = Currentuser.getUserDept().getId().toString();
		if (Currentuser.getWrkRoleId().equals(MyConstants.MANAGER_ROLE)) {
			manager = true;
		}
		users = dataAccessService.getAllEmployeesInDept(Integer.parseInt(deptId));

		if (getModeScreen() == MyConstants.INSERT_MODE)
			modelContentHtml = "mission_model.xhtml";
		else
			modelContentHtml = "mission_model_view.xhtml";
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
				title = MessageFormat.format(Utils.loadMessagesFromFile("mission.title"),
						(user.getEmployeeName() == null) ? user.getFirstName() : user.getEmployeeName(),
						HijriCalendarUtil.findCurrentHijriDate());
				userAbsent = new HrsUserAbsent(user.getUserId(), user.getEmployeeNumber(), missionDate,
						leavingHour + ":" + leavingMinute, returnHour + ":" + returnMinute, MyConstants.TASK,
						Utils.convertHijriDateToGregorian(missionDate), reason);
			} else {
				user = Utils.findCurrentUser();

				title = MessageFormat.format(Utils.loadMessagesFromFile("mission.title"),
						(user.getEmployeeName() == null) ? user.getFirstName() : user.getEmployeeName(),
						HijriCalendarUtil.findCurrentHijriDate());
				userAbsent = new HrsUserAbsent(user.getUserId(), user.getEmployeeNumber(), missionDate,
						leavingHour + ":" + leavingMinute, returnHour + ":" + returnMinute, MyConstants.TASK,
						Utils.convertHijriDateToGregorian(missionDate), reason);
			}
			dataAccessService.saveAuthorizationOrMission(userAbsent, title, MyConstants.MISSION_TYPE, false);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("accept.record"));
			resetFields();
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}

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

	/**
	 * Reset the screen's data
	 */
	private void resetFields() {
		missionDay = null;
		missionDate = null;
		reason = null;
		leavingHour = null;
		leavingMinute = null;
		returnHour = null;
		returnMinute = null;
		mission = null;
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
			// Check that the leaving time is before return time
			if (!Utils.checkValidTime(Integer.parseInt(leavingHour), Integer.parseInt(returnHour),
					Integer.parseInt(leavingMinute), Integer.parseInt(returnMinute)))
				valid = false;
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
		if (date != null && !date.isEmpty()) {
			missionDate = date;
		} else {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.miss.date"));
			return false;
		}
		try {
			String currentHijriDate = HijriCalendarUtil.findCurrentHijriDate();
			Date upperDate = formatDate.parse(HijriCalendarUtil
					.ConvertHijriTogeorgianDate(HijriCalendarUtil.addDaysToHijriDate(currentHijriDate, 1)));
			Date underDate = formatDate.parse(HijriCalendarUtil.ConvertHijriTogeorgianDate(currentHijriDate));
			Date chekDate = formatDate.parse(HijriCalendarUtil.ConvertHijriTogeorgianDate(missionDate));
			if (chekDate.compareTo(upperDate) > 0 || chekDate.compareTo(underDate) < 0) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.miss.date"));
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if ((missionDate == null) || (missionDate.trim().equals("")) || (missionDate.isEmpty())) {
			MsgEntry.addErrorAspectToComponent("missionDate");
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
		mission = dataAccessService
				.loadAuthorizationOrMissionByRecordId(Integer.parseInt(userMailClass.getAppId().trim()));
		if (mission != null && mission.getId() != 0)
			setMissionValues(mission);
		return mission;
	}

	/**
	 * Set mission model values in consult screen
	 * 
	 * @param mission
	 */
	private void setMissionValues(HrsUserAbsent mission) {
		Calendar cal = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		Date date = null;
		try {
			date = format.parse(mission.getJabsDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(date);
		missionDay = "" + cal.get(Calendar.DAY_OF_WEEK);
		missionDate = mission.getAbseDate();
		leavingHour = mission.getAbsForm().substring(0, mission.getAbsForm().indexOf(":"));
		leavingMinute = mission.getAbsForm().substring(mission.getAbsForm().indexOf(":") + 1);
		returnHour = mission.getAbsTo().substring(0, mission.getAbsTo().indexOf(":"));
		returnMinute = mission.getAbsTo().substring(mission.getAbsTo().indexOf(":") + 1);
		reason = mission.getReason();
	}

	@Override
	public String acceptAction() {
		try {
			dataAccessService.acceptAuthorizationOrMission(mission, wrkApplicationId,
					MailTypeEnum.WORKMISSION.getValue());
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("accept.record"));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			mission.setAccept(null);
			return "mails";
		}
	}

	@Override
	public String refuseAction() {
		try {
			mission.setAccept(MyConstants.NO);

			/**** begin create new application **/
			WrkApplication newApplication = createNewWrkAppForRefuse();
			/*** end **/
			refuseModel(newApplication, mission);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("refuse.record"));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}

	@Override
	@Transactional
	public Employer loadEmployerDataByRecordId(int arcRecordId) {
		return dataAccessService.loadEmployerFromAuthorizationOrMission(arcRecordId);
	}

	public boolean isAccepted() {
		return (!isReadOnly() || (MyConstants.YES.equalsIgnoreCase(mission.getAccept())));
	}

	@Override
	public void prepareData() {

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

	public String getMissionDay() {
		return missionDay;
	}

	public void setMissionDay(String missionDay) {
		this.missionDay = missionDay;
	}

	public String getMissionDate() {
		return missionDate;
	}

	public void setMissionDate(String missionDate) {
		this.missionDate = missionDate;
	}

	public WrkApplicationId getWrkApplicationId() {
		return wrkApplicationId;
	}

	public void setWrkApplicationId(WrkApplicationId wrkApplicationId) {
		this.wrkApplicationId = wrkApplicationId;
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

	public HrsUserAbsent getMission() {
		return mission;
	}

	public void setMission(HrsUserAbsent mission) {
		this.mission = mission;
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

}

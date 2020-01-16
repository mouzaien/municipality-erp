package com.bkeryah.fng.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;

import com.bkeryah.dao.FngVacation;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.HrsUserAbsent;
import com.bkeryah.entities.WrkDept;
import com.bkeryah.fng.entities.AutorizationSettings;
import com.bkeryah.fng.entities.TstFinger;
import com.bkeryah.model.AbsentModel;
import com.bkeryah.model.User;
import com.bkeryah.model.VacationModel;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class FngMonitoringBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	protected static final Logger logger = Logger.getLogger(FngMonitoringBean.class);

	// private TestBean testBean;
	private List<TstFinger> tstFingersList = new ArrayList<>();
	private Map<Integer, FngVacation> FngVacationMap = new HashMap<>();
	private List<TstFinger> tstFingersTotal = new ArrayList<>();
	private List<TstFinger> lstFingerSevenHours = new ArrayList<>();
	private List<TstFinger> lstFingertochange = new ArrayList<>();
	private Map<Integer, TstFinger> MapFingersTotal = new HashMap<>();
	private Map<Integer, TstFinger> MapFingersTotalNotWorkDay = new HashMap<>();
	private List<TstFinger> tstFingersDetail = new ArrayList<>();
	private List<HrsUserAbsent> userAbsentsList = new ArrayList<>();
	private SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	private SimpleDateFormat formatInOut = new SimpleDateFormat("HH:mm:ss");
	private SimpleDateFormat inFormatDate = new SimpleDateFormat("dd/MM/yyyy");
	private List<TstFinger> FingersListFiltred = new ArrayList<>();
	private boolean allEmp = false;
	private String typeAuth = "";
	private String deptId;
	private List<String> deptIds = new ArrayList<String>();
	private List<Integer> userIdList = new ArrayList<>();
	private List<User> usersSelected = new ArrayList<User>();
	private List<User> users = new ArrayList<User>();
	private Integer empId;
	private User employe;
	private String startDate;
	private String endDate;
	private boolean disabled = true;
	private String autorizationDate;
	private String leavingTime;
	private String returnTime;
	private Integer authorizationsNumber;
	private Integer radioNB = 1;
	private boolean fngDirMode = false;
	private boolean reporterMode = false;
	private boolean higriMode;
	private boolean higriMode2;
	private ArcUsers Currentuser;
	private Map<Integer, List<VacationModel>> usersVacations;
	private List<VacationModel> vacations = new ArrayList<VacationModel>();
	private Date mstartDate;
	private Date mEndDate;
	private String startGeorDate;
	private String endGeorDate;
	private Map<Integer, AbsentModel> userAbsents = new HashMap<Integer, AbsentModel>();
	private AutorizationSettings autorization = new AutorizationSettings();
	private String absDate;
	private Date absDate_G;
	// private Date absDate;
	private Date absFrom;
	private Date absTo;
	private List<WrkDept> depatmentList;

	private boolean total;
	// // Ø§Ù„Ù„Ø¨ÙŠØ§Ù†Ø§Øª Ø¨ØªØ§Ø¹ Ø§Ù„Ø¯ÙˆØ§Ù… Ø§Ù„Ø§Ø³Ø§Ø³ÙŠ
	// private List<FngUserTempShift> fngUserTempShiftLst = new
	// ArrayList<FngUserTempShift>();
	// // Ø§Ù„Ù„Ø¨ÙŠØ§Ù†Ø§Øª Ø¨ØªØ§Ø¹ ØªØ³Ø¬Ù„ Ø§Ù„Ø¨ØµÙ…Ø© Ø§Ùˆ Ø§Ù„ØªØ¨ØµÙŠÙ…
	// private List<FngCheckInOut> FngCheckInOutLst = new
	// ArrayList<FngCheckInOut>();

	@PostConstruct
	public void init() {
		// userAbsents =
		// dataAccessService.getHrsUserAbsents(HijriCalendarUtil.findCurrentGeorgianYear());
		autorization = dataAccessService.loadAutorization();
		Currentuser = Utils.findCurrentUser();
		deptId = Currentuser.getUserDept().getId().toString();
		if ((Currentuser.getWrkRoleId().equals(MyConstants.MANAGER_ROLE)
				&& (Currentuser.getWrkSectionId().equals(MyConstants.DEPT_FINGER)))
				|| Currentuser.getUserId().equals(MyConstants.SUPPORT_USER_ID)) {
			fngDirMode = true;
		}
		if ((Currentuser.getWrkRoleId().equals(MyConstants.MANAGER_ROLE))
				|| Currentuser.getUserId().equals(MyConstants.SUPPORT_USER_ID)) {
			reporterMode = true;
		}
		// else if (Currentuser.getWrkRoleId().equals(MyConstants.MANAGER_ROLE)
		// && Currentuser.getWrkSectionId().equals(MyConstants.HR_DEPT))
		// reporterMode = true;
		// users =
		// dataAccessService.getAllEmployeesInDept(Integer.parseInt(deptId));
	}

	public void onRowSelect(AjaxBehaviorEvent event) {
		if (usersSelected != null && usersSelected.size() == 1) {
			employe = usersSelected.get(0);
			disabled = false;
		} else {
			disabled = true;
		}
	}

	public void loadEmployersByDept(AjaxBehaviorEvent event) {
		users = new ArrayList<User>();
		List<User> usersList = new ArrayList<User>();
		for (String id : deptIds) {
			usersList = new ArrayList<User>();
			usersList = dataAccessService.getAllEmployeesInDept(Integer.parseInt(id));
			users.addAll(usersList);
		}
	}

	public void onRowUnselect(UnselectEvent event) {
		if (usersSelected != null && usersSelected.size() == 1) {
			employe = usersSelected.get(0);
			disabled = false;
		} else {
			disabled = true;
		}
	}

	public String calcdelay(String xEntry, String xExit, String entryOffice, String exiteOffice, String openEntry,
			String closeEntry, String openExit, String closeExit, String valStartDelay) {
		String resultDelay = "";
		String Delay = "";
		try {

			int abs = 0;
			Date TentryOffice = format.parse(entryOffice);
			Date TexitOffice = format.parse(exiteOffice);
			int TvalStartDelay = Integer.parseInt(valStartDelay);
			// add maxDelay to officeEntry(Exp: 7:30 + 45)
			Calendar calDelay = Calendar.getInstance();
			calDelay.setTime(TentryOffice);
			calDelay.add(Calendar.MINUTE, TvalStartDelay);
			Date TcalDelay = calDelay.getTime();
			// time enter finger
			Date TXentry = null;
			if (xEntry != null && xEntry != "")
				TXentry = formatInOut.parse(xEntry);
			// time open finger for enter
			Date TopenEntry = format.parse(openEntry);
			// time close entry finger
			Date TcloseEntry = format.parse(closeEntry);
			// time exit finger
			Date TxExit = null;
			if (xExit != null && xExit != "")
				TxExit = formatInOut.parse(xExit);
			// time open finger for exit
			Date TopenExit = format.parse(openExit);
			// time close finger for exit
			Date TcloseExit = format.parse(closeExit);

			long difference = 0;

			if (xEntry != null && xEntry != "") {
				// IF finger enter in valid interval (between begin openfinger
				// and calDelay)
				if (TXentry.compareTo(TopenEntry) >= 0 && TcalDelay.after(TXentry)) {
					// IF finger exit not found
					if (xExit == null || xExit == "") {
						// Delay = "02:00:00";
						Delay = formatInOut.format(autorization.getNotFingerOut());
					}
					// IF finger exit in valid interval
					else if (xExit != null && TxExit.compareTo(TopenExit) >= 0 && TcloseExit.compareTo(TxExit) >= 0) {
						// best way
						Delay = "0";
					}

				}

				// IF finger enter is delayed but it is before closeEnterFinger
				if (TcalDelay.before(TXentry) || TcalDelay.equals(TXentry)) {
					// IF finger exit not found
					if (xExit == null || xExit == "") {
						// difference between start and end job day
						long diffDay = TexitOffice.getTime() - TentryOffice.getTime();
						long diffDaySeconds = diffDay / 1000 % 60;
						long diffDayMinutes = diffDay / (60 * 1000) % 60;
						long diffDayHours = diffDay / (60 * 60 * 1000) % 24;

						// calc delay finger
						difference = TXentry.getTime() - TopenEntry.getTime();
						long diffSecondsDelay = difference / 1000 % 60;
						long diffMinutesDelay = difference / (60 * 1000) % 60;
						long diffHoursDelay = difference / (60 * 60 * 1000) % 24;

						// IF:NbreHours<Delay
						long diffInterval = diffDay - difference;
						long limitMinute = diffInterval / (60 * 1000);
						// long limit =diffInterval / (60 * 60 * 1000) % 24;
						if (limitMinute > 120) {
							long diffTotalHour = diffHoursDelay + 2;
							Delay = diffTotalHour + ":" + diffMinutesDelay + ":" + diffSecondsDelay;
						}

						else
							Delay = diffDayHours + ":" + diffDayMinutes + ":" + diffDaySeconds;

					}
					// IF finger exit in valid interval
					if (xExit != "" && xExit != null && TxExit.compareTo(TopenExit) >= 0
							&& TcloseExit.compareTo(TxExit) >= 0) {
						// best way
						// delay= diff(openEntry ,xEntry)
						difference = TXentry.getTime() - TentryOffice.getTime();
						long diffSeconds = difference / 1000 % 60;
						long diffMinutes = difference / (60 * 1000) % 60;
						long diffHours = difference / (60 * 60 * 1000) % 24;
						Delay = diffHours + ":" + diffMinutes + ":" + diffSeconds;
					}

				}
			}
			// IF finger enter not found
			if (xEntry == null || xEntry == "") {
				if (xExit != null && xExit != "") {
					difference = TxExit.getTime() - TentryOffice.getTime();
					long diffSeconds = difference / 1000 % 60;
					long diffMinutes = difference / (60 * 1000) % 60;
					long diffHours = difference / (60 * 60 * 1000) % 24;
					Delay = diffHours + ":" + diffMinutes + ":" + diffSeconds;
				}
			}
			if ((xExit == null || xExit == "") && (xEntry == null || xEntry == ""))
				abs = 1;
			resultDelay = "delay:" + Delay + "abs:" + abs;

		} catch (Exception e) {
			logger.error("calcdelay" + e.getCause());
			MsgEntry.addErrorMessage(" calcdelay" + utilities.Utils.loadMessagesFromFile("error.operation"));
		}

		return Delay;
	}

	private long getAbsenceTotal(String newAbst) {

		long hours = Long.parseLong(newAbst.split(":")[0]);
		long minutes = Long.parseLong(newAbst.split(":")[1]);
		long seconds = Long.parseLong(newAbst.split(":")[2]);
		long delay = TimeUnit.HOURS.toMillis(hours) + TimeUnit.MINUTES.toMillis(minutes)
				+ TimeUnit.SECONDS.toMillis(seconds);

		return delay;
	}

	public void onTabChange(TabChangeEvent event) {
		TabView tabView = (TabView) event.getComponent();
		int activeTab = tabView.getChildren().indexOf(event.getTab());
	}

	public void correctUserFngs() {
		try {
			readInputDates();
			dataAccessService.correctUserFngs(usersSelected.get(0).getUserId(), startGeorDate, endGeorDate);
		} catch (Exception e) {
			logger.error("correctUserFngs" + e.getMessage());

		}

	}

	public String showFinger() throws Exception {
		radioNB = 1;
		MapFingersTotal.clear();
		tstFingersTotal.clear();
		lstFingertochange.clear();
		lstFingerSevenHours.clear();
		MapFingersTotalNotWorkDay.clear();
		String lstUsers = readInputDates();

		try {
			lstUsers = getSelectedEmps(lstUsers);

			////////
			String start = null;
			String end = null;

			start = Utils.convertDateToString(mstartDate);
			end = Utils.convertDateToString(mEndDate);
			/////////////////////////
			usersVacations = getUserVacs(lstUsers, start);
			tstFingersList = dataAccessService.loadAllFingerByusersIdAndDate(lstUsers, start, end, allEmp, higriMode);
			FingersListFiltred = new ArrayList<>(tstFingersList);
			tstFingersDetail = dataAccessService.loadAllDetailFingerByusersIdAndDate(lstUsers, start, end, higriMode);
			userAbsentsList = dataAccessService.getAllAbsent(lstUsers, start, end);

			// Change to HijriDate for details list
			updateFngStatus();
			updateDelay();
			updateTotalAbsent();
			updateUsersVacs();
			updateNotWorking();
			updateTotalNotWorkingDays();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("showFinger" + e.getMessage());
			MsgEntry.addErrorMessage(utilities.Utils.loadMessagesFromFile("error.operation"));
		}
		return "";
	}

	private String readInputDates() {
		String lstUsers = "";
		try {
			if (higriMode) {

				mstartDate = Utils.convertHDateToGDate(startGeorDate);

				mEndDate = Utils.convertHDateToGDate(endGeorDate);
			} else {
				startGeorDate = Utils.grigDatesConvert(mstartDate);
				endGeorDate = Utils.grigDatesConvert(mEndDate);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstUsers;
	}
	// //////// old Method for read Dates; ////////////
	// private String readInputDates() throws ParseException {
	// startGeorDate = null;
	// endGeorDate = null;
	// String lstUsers = "";
	// if (higriMode) {
	// startDate =
	// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
	// .get("startDate");
	// endDate =
	// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("notifDate");
	// startGeorDate = HijriCalendarUtil.ConvertHijriTogeorgianDate(startDate);
	// endGeorDate = HijriCalendarUtil.ConvertHijriTogeorgianDate(endDate);
	// } else {
	// startGeorDate = inFormatDate.format(mstartDate);
	// endGeorDate = inFormatDate.format(mEndDate);
	// }
	// return lstUsers;
	// }

	private void updateDelay() throws ParseException {
		for (TstFinger fng : tstFingersList) {
			try {
				String calcdelay = "?";
				// First calc delay
				calcdelay = calcdelay(fng.getClockin(), fng.getClockout(), fng.getInTime(), fng.getOutTime(),
						fng.getBintime(), fng.getEintime(), fng.getBouttime(), fng.getEouttime(), fng.getLatetime());
				fng.setDelayCalc(calcdelay);

				// IF Absnet
				if ((fng.getClockin() == null && fng.getClockout() == null && fng.getTypeDoc() == 3)
						|| (fng.getClockin() == "" && fng.getClockout() == "" && fng.getTypeDoc() == 3))
					fng.setAbsent("1");
				// IF authorization
				calcAllDelay(fng, calcdelay);

				if (!MapFingersTotal.containsKey(fng.getUserid())) {
					if (fng.getDelayCalc() != "" && fng.getDelayCalc() != null && fng.getDelayCalc() != "0") {
						fng.setTotalAbst(getAbsenceTotal(fng.getDelayCalc()));
						MapFingersTotal.put(fng.getUserid(), fng);

					} else {
						fng.setTotalAbst(0);
						MapFingersTotal.put(fng.getUserid(), fng);

					}
				} else if (fng.getDelayCalc() != "" && fng.getDelayCalc() != null && fng.getDelayCalc() != "0") {
					long total = MapFingersTotal.get(fng.getUserid()).getTotalAbst()
							+ getAbsenceTotal(fng.getDelayCalc());
					fng.setTotalAbst(total);
					MapFingersTotal.put(fng.getUserid(), fng);
				}

			} catch (Exception e) {
				logger.error("updateDelay  vdate" + fng.getVdate() + " " + fng.getUserid() + "  :" + e.getMessage());
				MsgEntry.addErrorMessage(utilities.Utils.loadMessagesFromFile("error.operation"));
			}

		}
	}

	private void updateTotalAbsent() {
		for (Integer UserId : MapFingersTotal.keySet()) {
			TstFinger finger = MapFingersTotal.get(UserId);
			long totalAbst = finger.getTotalAbst();
			long TSecondsDelay = totalAbst / 1000 % 60;
			long TMinutesDelay = totalAbst / (60 * 1000) % 60;
			long THoursDelay = TimeUnit.MILLISECONDS.toHours(totalAbst);
			finger.setTotalAbstInDate(THoursDelay + ":" + TMinutesDelay + ":" + TSecondsDelay);
			tstFingersTotal.add(finger);
			// fill the master list for total finger
			lstFingertochange.add(finger);
			if (totalAbst >= TimeUnit.HOURS.toMillis(7)) {
				// AbsentModel absentModel =
				// userAbsents.get(finger.getUserid());
				// if (absentModel != null) {
				// finger.setAbsDays(absentModel.getDays());
				// finger.setAbsHours(absentModel.getHours());
				// finger.setAbsMinutes(absentModel.getMinutes());
				// }
				lstFingerSevenHours.add(finger);
			}

		}
	}

	private void updateTotalNotWorkingDays() {
		for (Integer UserId : MapFingersTotalNotWorkDay.keySet()) {
			TstFinger finger = MapFingersTotalNotWorkDay.get(UserId);
			for (TstFinger tstFinger : lstFingertochange) {
				if (tstFinger.getUserid().equals(finger.getUserid())) {
					tstFinger.setNotWorkingDays(finger.getNotWorkingDays());
					tstFinger.setNote(finger.getNote());
				}
			}
			for (TstFinger tstFinger : tstFingersTotal) {
				if (tstFinger.getUserid().equals(finger.getUserid())) {
					tstFinger.setNotWorkingDays(finger.getNotWorkingDays());
					tstFinger.setNote(finger.getNote());
				}
			}

		}
	}

	private void updateNotWorking() {
		for (TstFinger fng : tstFingersList) {
			// //ForNotWorkingDays

			if (!MapFingersTotalNotWorkDay.containsKey(fng.getUserid()) && fng.getAbsent() != null
					&& fng.getAbsent().equals("1")) {

				fng.setNotWorkingDays(Long.parseLong(fng.getAbsent()));
				fng.setNote(fng.getHigriDate());
				MapFingersTotalNotWorkDay.put(fng.getUserid(), fng);
			} else if (MapFingersTotalNotWorkDay.containsKey(fng.getUserid()) && fng.getAbsent() != null
					&& fng.getAbsent().equals("1")) {
				long totalNotWorkingDays = MapFingersTotalNotWorkDay.get(fng.getUserid()).getNotWorkingDays()
						+ Long.parseLong(fng.getAbsent());
				fng.setNotWorkingDays(totalNotWorkingDays);

				String totalNote = MapFingersTotalNotWorkDay.get(fng.getUserid()).getNote() + "-"
						+ (fng.getHigriDate());
				fng.setNote(totalNote);

				MapFingersTotalNotWorkDay.put(fng.getUserid(), fng);
			}
		}
	}

	private void updateFngStatus() throws ParseException {
		for (TstFinger tstFinger : tstFingersDetail) {

			String date = HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(tstFinger.getPic1());
			tstFinger.setDateFinger(date);
			tstFinger.setHigriDate(date);
			for (TstFinger tstFinger2 : tstFingersList) {
				if (tstFinger.getTimeFinger().equals(tstFinger2.getClockin())
						&& tstFinger.getDateFinger().equals(tstFinger2.getHigriDate())) {
					tstFinger.setStatusFinger("ØªÙ… Ø¥Ø­ØªØ³Ø§Ø¨Ù‡Ø§ Ø¨ØµÙ…Ø© Ø¯Ø®ÙˆÙ„");
					break;
				}

				else if (tstFinger.getTimeFinger().equals(tstFinger2.getClockout())
						&& tstFinger.getDateFinger().equals(tstFinger2.getHigriDate()))

				{
					tstFinger.setStatusFinger("ØªÙ… Ø¥Ø­ØªØ³Ø§Ø¨Ù‡Ø§ Ø¨ØµÙ…Ø© Ø®Ø±ÙˆØ¬");
					break;
				} else if (tstFinger.getDateFinger().equals(tstFinger2.getHigriDate())) {
					tstFinger.setStatusFinger("Ù„Ù… ÙŠØªÙ… Ø§Ø­ØªØ³Ø§Ø¨ Ù‡Ø°Ù‡ Ø§Ù„Ø¨ØµÙ…Ø©");
					break;
				}
			}
		}
	}

	private void updateUsersVacs() {
		Date vdate = null;
		try {
			for (TstFinger tstFinger : tstFingersList) {
				List<VacationModel> userVacs = usersVacations.get(tstFinger.getUserid());
				if (!(userVacs == null)) {
					for (VacationModel vacationModel : userVacs) {
						vdate = tstFinger.getVdate();
						if (!(vdate == null) && !(vacationModel.getDateBegin() == null)
								&& !(vacationModel.getDateEnd() == null))
							if (vdate.compareTo(vacationModel.getDateBegin()) >= 0
									&& vdate.compareTo(vacationModel.getDateEnd()) <= 0) {
								tstFinger.setTypeDoc(3);
								tstFinger.setVacaEnd(vacationModel.getDateEnd());
								tstFinger.setVacaStart(vacationModel.getDateEnd());
								tstFinger.setVacName(vacationModel.getVacName());
								tstFinger.setAbsent("");
							}
					}

				}

			}
		} catch (Exception e) {
			logger.error("updateUsersVacs  vdate" + vdate + "  :" + e.getMessage());
			MsgEntry.addErrorMessage(utilities.Utils.loadMessagesFromFile("error.operation"));
		}
	}

	@SuppressWarnings("null")
	private Map<Integer, List<VacationModel>> getUserVacs(String lstUsers, String startGeorDate) {
		Map<Integer, List<VacationModel>> usersAllVacs = new HashMap<>();
		vacations = dataAccessService.getUsersVacations(lstUsers, startGeorDate, allEmp);
		if (!(vacations == null) && !vacations.isEmpty()) {
			for (VacationModel vacationModel : vacations) {
				List<VacationModel> userVacs = usersAllVacs.get(vacationModel.getUserId());
				if (!(userVacs == null) && !userVacs.isEmpty()) {
					userVacs.add(vacationModel);
				} else {
					userVacs = new ArrayList<VacationModel>();
					userVacs.add(vacationModel);
					usersAllVacs.put(vacationModel.getUserId(), userVacs);
				}
			}
		}
		return usersAllVacs;
	}

	private String getSelectedEmps(String lstUsers) {
		if (usersSelected.size() == users.size() && deptId.equals("-1")) {
			allEmp = true;
		} else {
			for (User user : usersSelected) {

				if (lstUsers == "")
					lstUsers += user.getUserId().toString();
				else {
					lstUsers += "," + user.getUserId().toString();
				}

			}
		}
		if ((!fngDirMode && !reporterMode) || allEmp)
			lstUsers = Currentuser.getUserId().toString();
		return lstUsers;
	}

	private void calcAllDelay(TstFinger fng, String calcdelay) throws ParseException {
		Date clockout = null;
		Date clockin = null;
		if (fng.getInTime() != null && fng.getOutTime() != null && fng.getBintime() != null && fng.getEintime() != null
				&& fng.getBouttime() != null && fng.getEouttime() != null) {
			if (fng.getTypeDoc().equals(2)) {
				Date binTime = format.parse(fng.getBintime());
				Date bouTime = format.parse(fng.getBouttime());
				Date einTime = format.parse(fng.getEintime());
				Date eouttime = format.parse(fng.getEouttime());
				if (fng.getClockout() != null)
					clockout = format.parse(fng.getClockout());
				if (fng.getClockin() != null)
					clockin = format.parse(fng.getClockin());
				if (fng.getAbsFrom() != null && fng.getAbsTo() != null && calcdelay != "0" && calcdelay != "") {

					Date absFromTime = format.parse(fng.getAbsFrom());
					Date absToTime = format.parse(fng.getAbsTo());
					// Date Datedelay = format.parse("12:00");
					// Hour //Minute // second for delay
					long hours = Long.parseLong(calcdelay.split(":")[0]);
					long minutes = Long.parseLong(calcdelay.split(":")[1]);
					long seconds = Long.parseLong(calcdelay.split(":")[2]);
					long delay = TimeUnit.HOURS.toMillis(hours) + TimeUnit.MINUTES.toMillis(minutes)
							+ TimeUnit.SECONDS.toMillis(seconds);
					// calc duration of authorization
					long differenceAuthorizarion = absToTime.getTime() - absFromTime.getTime();
					if ((binTime.compareTo(absFromTime) <= 0 && absFromTime.compareTo(einTime) < 0
							&& absToTime.compareTo(eouttime) <= 0)) {
						// IF delay >timeAuthorization
						if (clockout == null)
							delay = delay - TimeUnit.HOURS.toMillis(2);
						if (delay > differenceAuthorizarion) {
							long NewDiffDelay = delay - differenceAuthorizarion;
							setDelay(fng, NewDiffDelay);
						} else if ((clockin != null
								&& (absFromTime.compareTo(clockin) <= 0 && clockin.compareTo(absToTime) < 0))
								|| (clockout != null && absFromTime.compareTo(clockout) <= 0
										&& clockout.compareTo(absToTime) < 0)) {
							setDelay(fng, 0);

						} else
							setDelay(fng, delay);
					}
				}
			}
		}
	}

	private void setDelay(TstFinger fng, long NewDiffDelay) {
		long diffSecondsNewDelay = NewDiffDelay / 1000 % 60;
		long diffMinutesNewDelay = NewDiffDelay / (60 * 1000) % 60;
		long diffHoursNewDelay = NewDiffDelay / (60 * 60 * 1000) % 24;
		String NewDelay = diffHoursNewDelay + ":" + diffMinutesNewDelay + ":" + diffSecondsNewDelay;
		fng.setDelayCalc(NewDelay);
	}

	public String printDetailsReport() {
		String reportName;
		if (total) {
			reportName = "/reports/fingerprint_list2.jasper";
		} else {
			reportName = "/reports/fng_pres_abs.jasper";
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		// parameters.put("fromDate", (higriMode) ? startDate : startGeorDate);
		// parameters.put("toDate", (higriMode) ? endDate : endGeorDate);

		parameters.put("fromDate", (higriMode) ? startGeorDate : mstartDate);
		parameters.put("toDate", (higriMode) ? endGeorDate : mEndDate);

		parameters.put("now", HijriCalendarUtil.findCurrentHijriWithTimeStamp());
		parameters.put("LOGO_DIR", FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath(Utils.loadMessagesFromFile("report.logo")));
		parameters.put("name_customer", dataAccessService.findSystemProperty("CUSTOMER_NAME"));
		Utils.printPdfReportFromListDataSource(reportName, parameters, tstFingersList);
		return "";
	}

	public String printGeneralReport() {
		List<TstFinger> tstFingersTotalForPrint = new ArrayList<>();
		for (TstFinger finger : lstFingertochange) {
			TstFinger fPrint = new TstFinger();
			fPrint.setUserName(finger.getUserName());
			fPrint.setUserid(finger.getUserid());
			fPrint.setTotalAbstInDate(finger.getTotalAbstInDate());
			fPrint.setNotWorkingDays(finger.getNotWorkingDays());
			fPrint.setDeptName(finger.getDeptName());
			fPrint.setTypeDoc(finger.getId().getUserid());
			fPrint.setNote(finger.getNote());
			if (radioNB == 3)
				fPrint.setTotalAbstInDate(null);
			else if (radioNB.equals(2) || radioNB.equals(5)) {
				fPrint.setNotWorkingDays(null);
				fPrint.setNote(null);
			}

			if (fPrint.getTotalAbstInDate() != null || fPrint.getNotWorkingDays() != null)
				tstFingersTotalForPrint.add(fPrint);
		}

		String reportName = "/reports/general_fingerprint2.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("fromDate", (higriMode) ? startDate : startGeorDate);
		parameters.put("toDate", (higriMode) ? endDate : endGeorDate);
		parameters.put("now", HijriCalendarUtil.findCurrentHijriWithTimeStamp());
		parameters.put("LOGO_DIR", FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath(Utils.loadMessagesFromFile("report.logo")));
		parameters.put("name_customer", dataAccessService.findSystemProperty("CUSTOMER_NAME"));
		Utils.printPdfReportFromListDataSource(reportName, parameters, tstFingersTotalForPrint);
		return "";
	}

	public String printModelReport() {
		String reportName = "/reports/empty_fingerprint_list.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("fromDate", (higriMode) ? startDate : startGeorDate);
		parameters.put("toDate", (higriMode) ? endDate : endGeorDate);
		parameters.put("now", HijriCalendarUtil.findCurrentHijriWithTimeStamp());
		parameters.put("LOGO_DIR", FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath(Utils.loadMessagesFromFile("report.logo")));
		parameters.put("name_customer", dataAccessService.findSystemProperty("CUSTOMER_NAME"));
		Utils.printPdfReportFromListDataSource(reportName, parameters, tstFingersList);
		return "";
	}

	public void showAutorizationNB() {
		autorizationDate = null;
		if (employe != null) {
			String currentHijriDate = utilities.HijriCalendarUtil.findCurrentHijriDate();
			authorizationsNumber = dataAccessService.getAuthorizationsNumber(employe.getUserId(),
					currentHijriDate.substring(3, 5), currentHijriDate.substring(6, 10));
		}

	}

	public void savespecialAutorisation(AjaxBehaviorEvent ae) {
		try {

			// String exithtmlTime =
			// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
			// .get("time2");
			// String returnhtmlTime =
			// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
			// .get("time3");
			// HrsUserAbsent userAbsent = new HrsUserAbsent(employe.getUserId(),
			// autorizationDate,
			// employe.getEmployeeNumber(), exithtmlTime, returnhtmlTime,
			// typeAuth,
			// Utils.convertHijriDateToGregorian(autorizationDate),
			// MyConstants.YES);

			if (usersSelected != null && usersSelected.size() == 1) {
				employe = usersSelected.get(0);
			}

			SimpleDateFormat hourSDF = new SimpleDateFormat("HH:mm");
			String exithtmlTime = hourSDF.format(absFrom);
			String returnhtmlTime = hourSDF.format(absTo);
			System.out.println("from " + exithtmlTime + "  to " + returnhtmlTime);
			// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			// String grigAbs = sdf.format(absDate);
			// System.out.println(
			// "Selected Date datatype after convert to String------>>>> " +
			// grigAbs.getClass().getName());
			// System.out.println("Selected Date value after convert to
			// String------>>>> " + grigAbs);
			HrsUserAbsent userAbsent = new HrsUserAbsent();
			// userAbsent = new HrsUserAbsent(employe.getUserId(),
			// HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(absDate),
			// employe.getEmployeeNumber(),
			// exithtmlTime, returnhtmlTime, typeAuth, absDate,
			// MyConstants.YES);
			if (higriMode2) {
				userAbsent = new HrsUserAbsent(employe.getUserId(), absDate, employe.getEmployeeNumber(), exithtmlTime,
						returnhtmlTime, typeAuth, absDate, MyConstants.YES);
			} else {
				absDate = Utils.grigDatesConvert(absDate_G);
				userAbsent = new HrsUserAbsent(employe.getUserId(), absDate, employe.getEmployeeNumber(), exithtmlTime,
						returnhtmlTime, typeAuth, absDate, MyConstants.YES);
			}

			dataAccessService.createHrsEmpAbsent(userAbsent);
			System.out.println(userAbsent.getAbseDate());
			MsgEntry.addAcceptFlashInfoMessage(utilities.Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}

	}

	/**
	 * for edit column DatatablePrime
	 **/
	public void onRowEdit(RowEditEvent event) {

		try {
			HrsUserAbsent absent = ((HrsUserAbsent) event.getObject());
			dataAccessService.updateObject(absent);
			MsgEntry.addAcceptFlashInfoMessage("ØªÙ… ØªØ¹Ø¯ÙŠÙ„ Ø§Ù„Ù…ÙˆØ§Ø¯ Ø¨Ù†Ø¬Ø§Ø­");
		} catch (Exception e) {
			MsgEntry.addErrorMessage("Ø®Ø·Ø§ Ù�Ù‰ ØªÙ†Ù�ÙŠØ° Ø§Ù„Ø¹Ù…Ù„ÙŠØ©");
			e.printStackTrace();
		}

	}

	public void onRowCancel(RowEditEvent event) {

		MsgEntry.addAcceptFlashInfoMessage("ØªÙ… Ø¥Ù„ØºØ§Ø¡ Ø§Ù„Ø¹Ù…Ù„ÙŠØ©");

	}

	public void onRowDelete(HrsUserAbsent userAbsent) {

		try {

			userAbsent.setAccept("D");
			dataAccessService.updateObject(userAbsent);
			// TableNB = userAbsentsList.size();
			userAbsentsList.remove(userAbsent);
			MsgEntry.addAcceptFlashInfoMessage("ØªÙ… Ø­Ø°Ù� Ø§Ù„Ø¥Ø³ØªØ¦Ø°Ø§Ù†");
		} catch (Exception e) {
			MsgEntry.addErrorMessage("Ø®Ø·Ø§ Ù�Ù‰ ØªÙ†Ù�ÙŠØ° Ø§Ù„Ø¹Ù…Ù„ÙŠØ©");
			e.printStackTrace();
		}

	}

	public void onRadioChange() {
		FingersListFiltred.clear();
		lstFingertochange.clear();
		TabView tabView = (TabView) FacesContext.getCurrentInstance().getViewRoot().findComponent("includeform:bb");
		if (radioNB == 1) {
			tabView.setActiveIndex(0);// getActiveIndex();
			FingersListFiltred = new ArrayList<>(tstFingersList);
			lstFingertochange = new ArrayList<>(tstFingersTotal);
		}

		else if (radioNB == 2) {
			tabView.setActiveIndex(0);// getActiveIndex();
			for (TstFinger tstFinger : tstFingersList) {
				if (tstFinger.getDelayCalc() != null && !tstFinger.getDelayCalc().equals("0")
						&& !tstFinger.getDelayCalc().equals(""))
					FingersListFiltred.add(tstFinger);
			}
			lstFingertochange = new ArrayList<>(tstFingersTotal);

		} else if (radioNB == 3) {
			tabView.setActiveIndex(0);// getActiveIndex();
			for (TstFinger tstFinger : tstFingersList) {
				if (tstFinger.getAbsent() != null && tstFinger.getAbsent().equals("1"))
					FingersListFiltred.add(tstFinger);
			}
			lstFingertochange = new ArrayList<>(tstFingersTotal);

		} else if (radioNB == 4) {
			tabView.setActiveIndex(0);// getActiveIndex();
			for (TstFinger tstFinger : tstFingersList) {
				if ((tstFinger.getDelayCalc() != null && !tstFinger.getDelayCalc().equals("0")
						&& !tstFinger.getDelayCalc().equals(""))
						|| (tstFinger.getAbsent() != null && tstFinger.getAbsent().equals("1")))
					FingersListFiltred.add(tstFinger);
			}
			lstFingertochange = new ArrayList<>(tstFingersTotal);

		} else if (radioNB == 5) {

			tabView.setActiveIndex(1);// getActiveIndex();

			for (TstFinger tstFinger : lstFingerSevenHours) {

				lstFingertochange.add(tstFinger);
			}

		}

	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed",
					"Old: " + oldValue + ", New:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public List<WrkDept> getDepatmentList() {
		if (Currentuser.getUserId() == dataAccessService.getPropertiesValue("FINGER_MGR")) {
			if ((depatmentList == null) || (depatmentList.isEmpty()))
				return dataAccessService.findAllDepartments();
			return depatmentList;
		} else if (Currentuser.getWrkRoleId() == 2) {
			if ((depatmentList == null) || (depatmentList.isEmpty()))
				return dataAccessService.findDepartmentById(Currentuser.getDeptId());
			return depatmentList;
		} else
			return null;
	}

	public void setDepatmentList(List<WrkDept> depatmentList) {
		this.depatmentList = depatmentList;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		if (empId == 0)
			allEmp = true;
		else
			allEmp = false;
		this.empId = empId;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public User getEmploye() {
		return employe;
	}

	public void setEmploye(User employe) {
		this.employe = employe;
	}

	public List<TstFinger> getTstFingersList() {
		return tstFingersList;
	}

	public void setTstFingersList(List<TstFinger> tstFingersList) {
		this.tstFingersList = tstFingersList;
	}

	public List<Integer> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<Integer> userIdList) {
		this.userIdList = userIdList;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Map<Integer, FngVacation> getFngVacationMap() {
		return FngVacationMap;
	}

	public void setFngVacationMap(Map<Integer, FngVacation> fngVacationMap) {
		FngVacationMap = fngVacationMap;
	}

	public List<TstFinger> getTstFingersDetail() {
		return tstFingersDetail;
	}

	public void setTstFingersDetail(List<TstFinger> tstFingersDetail) {
		this.tstFingersDetail = tstFingersDetail;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getAutorizationDate() {
		return autorizationDate;
	}

	public void setAutorizationDate(String autorizationDate) {
		this.autorizationDate = autorizationDate;
	}

	public String getLeavingTime() {
		return leavingTime;
	}

	public void setLeavingTime(String leavingTime) {
		this.leavingTime = leavingTime;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public Integer getAuthorizationsNumber() {
		return authorizationsNumber;
	}

	public void setAuthorizationsNumber(Integer authorizationsNumber) {
		this.authorizationsNumber = authorizationsNumber;
	}

	public List<HrsUserAbsent> getUserAbsentsList() {
		return userAbsentsList;
	}

	public void setUserAbsentsList(List<HrsUserAbsent> userAbsentsList) {
		this.userAbsentsList = userAbsentsList;
	}

	public Integer getRadioNB() {
		return radioNB;
	}

	public void setRadioNB(Integer radioNB) {
		this.radioNB = radioNB;
	}

	public List<TstFinger> getFingersListFiltred() {
		return FingersListFiltred;
	}

	public void setFingersListFiltred(List<TstFinger> fingersListFiltred) {
		FingersListFiltred = fingersListFiltred;
	}

	public List<TstFinger> getTstFingersTotal() {
		return tstFingersTotal;
	}

	public void setTstFingersTotal(List<TstFinger> tstFingersTotal) {
		this.tstFingersTotal = tstFingersTotal;
	}

	public Map<Integer, TstFinger> getMapFingersTotal() {
		return MapFingersTotal;
	}

	public void setMapFingersTotal(Map<Integer, TstFinger> mapFingersTotal) {
		MapFingersTotal = mapFingersTotal;
	}

	public List<TstFinger> getLstFingerSevenHours() {
		return lstFingerSevenHours;
	}

	public void setLstFingerSevenHours(List<TstFinger> lstFingerSevenHours) {
		this.lstFingerSevenHours = lstFingerSevenHours;
	}

	public List<TstFinger> getLstFingertochange() {
		return lstFingertochange;
	}

	public void setLstFingertochange(List<TstFinger> lstFingertochange) {
		this.lstFingertochange = lstFingertochange;
	}

	public String getTypeAuth() {
		return typeAuth;
	}

	public void setTypeAuth(String typeAuth) {
		this.typeAuth = typeAuth;
	}

	public boolean isAllEmp() {
		return allEmp;
	}

	public void setAllEmp(boolean allEmp) {
		this.allEmp = allEmp;
	}

	public Map<Integer, TstFinger> getMapFingersTotalNotWorkDay() {
		return MapFingersTotalNotWorkDay;
	}

	public void setMapFingersTotalNotWorkDay(Map<Integer, TstFinger> mapFingersTotalNotWorkDay) {
		MapFingersTotalNotWorkDay = mapFingersTotalNotWorkDay;
	}

	public boolean isFngDirMode() {
		return fngDirMode;
	}

	public void setFngDirMode(boolean fngDirMode) {
		this.fngDirMode = fngDirMode;
	}

	public boolean isReporterMode() {
		return reporterMode;
	}

	public void setReporterMode(boolean reporterMode) {
		this.reporterMode = reporterMode;
	}

	public List<User> getUsersSelected() {
		return usersSelected;
	}

	public void setUsersSelected(List<User> usersSelected) {
		this.usersSelected = usersSelected;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Date getmEndDate() {
		return mEndDate;
	}

	public void setmEndDate(Date mEndDate) {
		this.mEndDate = mEndDate;
	}

	public Date getMstartDate() {
		return mstartDate;
	}

	public void setMstartDate(Date mstartDate) {
		this.mstartDate = mstartDate;
	}

	public boolean isHigriMode() {
		return higriMode;
	}

	public void setHigriMode(boolean higriMode) {
		this.higriMode = higriMode;
	}

	public Date getAbsFrom() {
		return absFrom;
	}

	public void setAbsFrom(Date absFrom) {
		this.absFrom = absFrom;
	}

	public Date getAbsTo() {
		return absTo;
	}

	public void setAbsTo(Date absTo) {
		this.absTo = absTo;
	}

	public String getEndGeorDate() {
		return endGeorDate;
	}

	public void setEndGeorDate(String endGeorDate) {
		this.endGeorDate = endGeorDate;
	}

	public String getStartGeorDate() {
		return startGeorDate;
	}

	public void setStartGeorDate(String startGeorDate) {
		this.startGeorDate = startGeorDate;
	}

	public boolean isHigriMode2() {
		return higriMode2;
	}

	public void setHigriMode2(boolean higriMode2) {
		this.higriMode2 = higriMode2;
	}

	public String getAbsDate() {
		return absDate;
	}

	public void setAbsDate(String absDate) {
		this.absDate = absDate;
	}

	public Date getAbsDate_G() {
		return absDate_G;
	}

	public void setAbsDate_G(Date absDate_G) {
		this.absDate_G = absDate_G;
	}

	public boolean isTotal() {
		return total;
	}

	public void setTotal(boolean total) {
		this.total = total;
	}

	public List<String> getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(List<String> deptIds) {
		this.deptIds = deptIds;
	}

}

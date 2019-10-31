package com.bkeryah.fng.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.HrsEmpTerminate;
import com.bkeryah.entities.HrsMasterFile;
import com.bkeryah.fng.entities.FngEmpAbsent;
import com.bkeryah.fng.entities.FngStatusAbsence;
import com.bkeryah.fng.entities.FngTypeAbsence;
import com.bkeryah.hr.entities.Sys012;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class FngAbsenceBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;

	private Integer deptId;
	private List<ArcUsers> employersList;
	private FngEmpAbsent fngEmpAbsent = new FngEmpAbsent();
	private List<FngEmpAbsent> fngEmpAbsentFilter = new ArrayList<FngEmpAbsent>();
	private List<FngEmpAbsent> fngEmpAbsentList = new ArrayList<FngEmpAbsent>();;
	private Employer employer = new Employer();
	private List<FngTypeAbsence> TypeAbsencesList;
	private List<FngStatusAbsence> StatusAbsencesList;
	private boolean visible;
	private List<HrsMasterFile> employers;

	private Integer printedYears;
	private Integer printedMonths;

	private boolean higriMode;
	private String absComment;
	private Date absComment_G;

	@PostConstruct
	public void init() {
		employers = dataAccessService.loadHrsMasterEmployers();
		TypeAbsencesList = dataAccessService.loadAllTypeAbsence();
		StatusAbsencesList = dataAccessService.loadAllStatusAbsence();
		fngEmpAbsentList = dataAccessService.loadAllFngEmpAbsent();
		String time1 = "16:00:00";
		String time2 = "19:00:00";
		calcdelay("10:10:00", "16:20:00", "08:00:00", "14:30:00", "07:30:00", "14:00:00", "15:00:00", "40");
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date1;
		try {
			date1 = format.parse(time1);
			Date date2 = format.parse(time2);

			Calendar cal = Calendar.getInstance();
			cal.setTime(date2);
			cal.add(Calendar.MINUTE, 10);
			Date newTime = cal.getTime();

			long difference = date2.getTime() - date1.getTime();

			long diffSeconds = difference / 1000 % 60;
			long diffMinutes = difference / (60 * 1000) % 60;
			long diffHours = difference / (60 * 60 * 1000) % 24;
			// long diffDays = difference / (24 * 60 * 60 * 1000);

			// System.out.println(date2 +">>>>>>"+newTime.getHours() +
			// ":"+newTime.getMinutes());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String calcdelay(String xEntry, String xExit, String entryOffice, String exiteOffice, String openEntry,
			String openExit, String closeExit, String valStartDelay) {
		try {

			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			String Delay = "";
			int abs = 0;
			Date TentryOffice = format.parse(entryOffice);
			int TvalStartDelay = Integer.parseInt(valStartDelay);
			// add maxDelay to officeEntry(Exp: 7:30 + 45)
			Calendar calDelay = Calendar.getInstance();
			calDelay.setTime(TentryOffice);
			calDelay.add(Calendar.MINUTE, TvalStartDelay);
			Date TcalDelay = calDelay.getTime();
			// time enter finger
			Date TXentry = format.parse(xEntry);
			// time open finger for enter
			Date TopenEntry = format.parse(openEntry);
			// time exit finger
			Date TxExit = format.parse(xExit);
			// time open finger for exit
			Date TopenExit = format.parse(openExit);
			// time close finger for exit
			Date TcloseExit = format.parse(closeExit);

			long difference = 0;

			long diffSeconds = difference / 1000 % 60;
			long diffMinutes = difference / (60 * 1000) % 60;
			long diffHours = difference / (60 * 60 * 1000) % 24;

			if (TXentry.compareTo(TopenEntry) >= 0 && TcalDelay.after(TXentry)) {
				if (xExit == null) {
					Delay = "02:00:00";
				} else if (xExit != null && TxExit.compareTo(TopenExit) >= 0 && TcloseExit.compareTo(TxExit) >= 0) {
					// best way
					Delay = "0";
				} else if (xExit != null && TxExit.compareTo(TcloseExit) > 0) {
					Delay = "02:00:00";
				}
			}
			if (TcalDelay.before(TXentry)) {
				if (xExit == null) {
					// if(diff(openEntry ,xEntry)<4)
					// {
					// delay= diff(openEntry ,xEntry)+2
					// }
					// else
					// {
					// delay = diff(openEntry)
					// }

				}
				if (xExit != null && TxExit.compareTo(TopenExit) >= 0 && TcloseExit.compareTo(TxExit) >= 0) {
					// best way
					// delay= diff(openEntry ,xEntry)
					difference = TXentry.getTime() - TopenEntry.getTime();
					Delay = diffHours + ":" + diffMinutes + ":" + diffSeconds;
				} else if (xExit != null && TxExit.compareTo(TcloseExit) > 0) {
					abs = 1;
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	public void loadEmployersByDept() {
		setEmployersList(dataAccessService.getAllActiveEmployeesInDept(deptId));
	}

	public void loadEmployerData() {
		employer = dataAccessService.loadEmployerByUser(fngEmpAbsent.getEmpno());

	}

	private boolean checkFields(FngEmpAbsent absent) {
		boolean valid = true;
		// Check required fields

		// Check that the date
		if (absent.getEmpno() == null || absent.getEmpno() == 0) {
			valid = false;
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("emp.invalid"));
		}

		return valid;
	}

	public String saveAbsence() {
		try {
			if (!checkFields(fngEmpAbsent))
				return "";
			else {
				// fngEmpAbsent.setAbsComment(Utils.convertDateToString(fngEmpAbsent.getAbsCommentGrig()));
				if (higriMode) {
					absComment_G = Utils.convertHDateToGDate(absComment);
				} else {

					absComment = Utils.grigDatesConvert(absComment_G);
				}
				fngEmpAbsent.setAbsCommentGrig(absComment_G);
				fngEmpAbsent.setAbsComment(absComment);
				dataAccessService.saveAbsence(fngEmpAbsent);
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
		fngEmpAbsentList = dataAccessService.loadAllFngEmpAbsent();
		return "";
	}

	public void showPrint() {
		if ((printedMonths != null) && (printedYears != 0)) {
			visible = true;
		} else
			visible = false;
	}

	public String printReport() {
		String reportName = "/reports/absent_delay.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("year", printedYears);
		parameters.put("month", printedMonths);
		parameters.put("monthName",
				((Sys012) dataAccessService.findEntityById(Sys012.class, fngEmpAbsent.getAbsmonth())).getNameAr());
		parameters.put("now", HijriCalendarUtil.findCurrentHijriWithTimeStamp());
		parameters.put("LOGO_DIR", FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath(Utils.loadMessagesFromFile("report.logo")));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public void addFngEmpAbsent() {
		fngEmpAbsent = new FngEmpAbsent();
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public List<ArcUsers> getEmployersList() {
		return employersList;
	}

	public void setEmployersList(List<ArcUsers> employersList) {
		this.employersList = employersList;
	}

	public FngEmpAbsent getFngEmpAbsent() {
		return fngEmpAbsent;
	}

	public void setFngEmpAbsent(FngEmpAbsent fngEmpAbsent) {
		this.fngEmpAbsent = fngEmpAbsent;
	}

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public List<FngTypeAbsence> getTypeAbsencesList() {
		return TypeAbsencesList;
	}

	public void setTypeAbsencesList(List<FngTypeAbsence> typeAbsencesList) {
		TypeAbsencesList = typeAbsencesList;
	}

	public List<FngStatusAbsence> getStatusAbsencesList() {
		return StatusAbsencesList;
	}

	public void setStatusAbsencesList(List<FngStatusAbsence> statusAbsencesList) {
		StatusAbsencesList = statusAbsencesList;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public List<HrsMasterFile> getEmployers() {
		return employers;
	}

	public void setEmployers(List<HrsMasterFile> employers) {
		this.employers = employers;
	}

	public List<FngEmpAbsent> getFngEmpAbsentList() {
		System.out.println("----fngEmpAbsentList size-----" + fngEmpAbsentList.size());
		return fngEmpAbsentList;
	}

	public void setFngEmpAbsentList(List<FngEmpAbsent> fngEmpAbsentList) {
		this.fngEmpAbsentList = fngEmpAbsentList;
	}

	public List<FngEmpAbsent> getFngEmpAbsentFilter() {
		return fngEmpAbsentFilter;
	}

	public void setFngEmpAbsentFilter(List<FngEmpAbsent> fngEmpAbsentFilter) {
		this.fngEmpAbsentFilter = fngEmpAbsentFilter;
	}

	public Integer getPrintedYears() {
		return printedYears;
	}

	public void setPrintedYears(Integer printedYears) {
		this.printedYears = printedYears;
	}

	public Integer getPrintedMonths() {
		return printedMonths;
	}

	public void setPrintedMonths(Integer printedMonths) {
		this.printedMonths = printedMonths;
	}

	public boolean isHigriMode() {
		return higriMode;
	}

	public void setHigriMode(boolean higriMode) {
		this.higriMode = higriMode;
	}

	public String getAbsComment() {
		return absComment;
	}

	public void setAbsComment(String absComment) {
		this.absComment = absComment;
	}

	public Date getAbsComment_G() {
		return absComment_G;
	}

	public void setAbsComment_G(Date absComment_G) {
		this.absComment_G = absComment_G;
	}

}

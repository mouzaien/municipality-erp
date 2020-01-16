package com.bkeryah.mails;

import java.io.Serializable;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.HrEmployeeVacation;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.hr.entities.HrsVacSold;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

/**
 * This class is used for vacation's model
 * 
 * @author habib
 */
@ViewScoped
public class VacationExecutor extends MailExecutor<HrEmployeeVacation> implements Serializable {

	private static final long serialVersionUID = 1L;
	private HrEmployeeVacation employeeVacation = null;
	private boolean acceptVisible;
	private boolean disableList;
	// private boolean vacanyEndDateFlag;
	private Integer departmentId;
	private List<ArcUsers> employersList;
	private Integer employerId;

	private boolean higriMode;
	private String higriDate;
	private Date grigDate;
	private HrsVacSold hrsVacSold;
	private Integer empNo;

	public VacationExecutor(MailTypeEnum mailType) {
		super(mailType);
	}

	/**
	 * Load for forced vacation
	 * 
	 * @return
	 */
	private String loadForcedVacationData() {
		employeeVacation = new HrEmployeeVacation();
		employeeVacation.setProcType(2);
		employeeVacation.setVacationType(MyConstants.FORCED_VACATION);
		if (employer.getEmpNB() != null)
			employeeVacation.setUsedForcedPeriod(dataAccessService.findForcedVacationScore(employer.getEmpNB()));
		return "VacEtrari";
	}

	/**
	 * Load normal vacation
	 * 
	 * @return
	 */
	private String loadNormalVacationData() {
		employeeVacation = new HrEmployeeVacation();
		ArcUsers employee = dataAccessService.loadUserById(employerId);
		hrsVacSold = dataAccessService.loadHrsVacSoldById(employee.getEmployeeNumber());

		employeeVacation.setProcType(2);
	//	int currendDebit = dataAccessService.getTotalVacationPeriod(employer.getEmpNB(),
	//			HijriCalendarUtil.findCurrentHijriDate());
		int currendDebit = 0;
		//		dataAccessService.getTotalVacationPeriod(employer.getEmpNB(),
		//		HijriCalendarUtil.findCurrentHijriDate());
		// dataAccessService.loadNormalVacationNew(employer.getEmpNB(),
		// employer.getFirstApplicationDate(),
		// employer.getBirthDateGerige(), employer.getId(), currendDebit,
		// employeeVacation);
		dataAccessService.loadNormalVacationNew(employer.getEmpNB(), employer.getFirstApplicationDate(),
				employer.getBirthDateGerige(), employer.getId(), currendDebit, employeeVacation, hrsVacSold);

		return "VacNormal1";
	}

	/**
	 * Calculate return date
	 */
	
	
	public boolean checkStartDate(HrEmployeeVacation employeeVacation)
		{
			boolean valid=true;
			if ((employeeVacation.getHigriVacationStart() != null)
					&& (!employeeVacation.getHigriVacationStart().trim().equals(""))) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				try {
					Calendar c = Calendar.getInstance();
					c.setTime(new Date());
				//	c.add(Calendar.DATE, -1);
					c.add(Calendar.DAY_OF_MONTH,-1);
					Date mydt = sdf.parse(sdf.format(c.getTime()));
					
					if (((employerId == null)||(employerId == Utils.findCurrentUser().getUserId())) && (sdf.parse(HijriCalendarUtil.ConvertHijriTogeorgianDate(employeeVacation.getHigriVacationStart())))
							.before(mydt)) {
						MsgEntry.addErrorMessage(MsgEntry.ERROR_BEFORE_LAST_DAY);
						 valid=false;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			return valid;
		}
	 
	public void checkdate() {
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
		employeeVacation.setHigriVacationStart(higriDate);
		if(employeeVacation.getHigriVacationStart()!=null&&checkStartDate(employeeVacation)&&employeeVacation.getVacationPeriod()!=null)
					{
		
		if (((employeeVacation.getVacationPeriod() != null) && (employeeVacation.getVacationPeriod() != 0))
				&& (checkDays()) && (employeeVacation.getHigriVacationStart() != null)
				&& (!employeeVacation.getHigriVacationStart().trim().equals(""))) {
			employeeVacation.setHigriVacationEnd(HijriCalendarUtil.addDaysToHijriDate(
					employeeVacation.getHigriVacationStart(), employeeVacation.getVacationPeriod() - 1));
			// setVacanyEndDateFlag(true);
	//	}}else;
		}
		}
	}

	/**
	 * Print letter report
	 */
	public String printReport() {
		String reportName = "/reports/normal_vacation.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("seqid", employeeVacation.getId());
		parameters.put("compName", dataAccessService.findSystemProperty("CUSTOMER_NAME"));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	/**
	 * Validate and load vacation data
	 * 
	 * @return
	 */
	public boolean checkDays() {
		if (typeId.equals(MailTypeEnum.VACNEEDED))
			return validateForcedVacationDays();
		else {
			boolean valid = validateNormalVacationDays();
			employeeVacation.setCostVacation(Utils
					.formatDouble((double) (employer.getBasicSalary() * employeeVacation.getVacationPeriod() / 30)));
			employeeVacation.setHDatevacationFrom(dataAccessService.getLastServiceDate(employer.getEmpNB()));
			if ((employeeVacation.getHDatevacationFrom() != null)
					&& (!employeeVacation.getHDatevacationFrom().trim().equals("")))
				employeeVacation.setHDateVacationTo(Utils.calculateEndServiceDate(
						employeeVacation.getHDatevacationFrom(), employeeVacation.getVacationPeriod()));
			return valid;
		}
	}

	/**
	 * Validate forced vacation data
	 * 
	 * @return
	 */
	private boolean validateForcedVacationDays() {
		// try {
		// Integer.parseInt(employeeVacation.getVacationPeriod().toString());
		// } catch (Exception e) {
		// MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.number"));
		// return false;
		// }
		if (employeeVacation.getVacationPeriod() > employeeVacation.getRemainForcedPeriod()) {
			MsgEntry.addErrorMessage(MsgEntry.ERROR_INFERIOR_VACATION_DAYS);
			return false;
		}
		return true;
	}

	/**
	 * Validate normal vacation data
	 * 
	 * @return
	 */
	private boolean validateNormalVacationDays() {
		boolean valid = true;
		if (employeeVacation.getVacationPeriod() > employeeVacation.getRemainNormalYearlyPeriod()) {
			MsgEntry.addErrorMessage(MsgEntry.ERROR_INFERIOR_VACATION_DAYS);
			valid = false;
		} else if (employeeVacation.getVacationPeriod() >= 30) {
			setDisableList(false);
		} else {
			employeeVacation.setVactionCalc(MyConstants.VACATION_WITHOUT_SALARY);
			setDisableList(true);
		}
		// if (((employerId == null)||(employerId ==
		// Utils.findCurrentUser().getUserId())) &&
		// (employeeVacation.getVacationPeriod() < 5)) {
		// MsgEntry.addErrorMessage(MsgEntry.ERROR_INFERIOR_FIVE_DAYS);
		// valid = false;
		// }
		if (employeeVacation.getVacationPeriod() < 5) {
			if (((employerId == null) || employerId == Utils.findCurrentUser().getUserId())
					&& (employeeVacation.getPeriodLessFive() + employeeVacation.getVacationPeriod() > 10)) {
				MsgEntry.addErrorMessage(MsgEntry.ERROR_INFERIOR_FIVE_DAYS);
				// System.err.println("=اكبر من 5" +
				// dataAccessService.checkPeriodVacation(employer.getEmpNB()));
				valid = false;
			}
		}
		if ((employeeVacation.getHigriVacationStart() != null)
				&& (!employeeVacation.getHigriVacationStart().trim().equals(""))) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				// c.add(Calendar.DATE, -1);
				c.add(Calendar.DAY_OF_MONTH,-1);
				Date mydt = sdf.parse(sdf.format(c.getTime()));

				if (((employerId == null) || (employerId == Utils.findCurrentUser().getUserId())) && (sdf
						.parse(HijriCalendarUtil.ConvertHijriTogeorgianDate(employeeVacation.getHigriVacationStart())))
								.before(mydt)) {
					// MsgEntry.addErrorMessage(MsgEntry.ERROR_BEFORE_LAST_DAY);
					// valid = false;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return valid;
	}

	/**
	 * Get remain vacation period
	 * 
	 * @return
	 */
	private Integer getRemainPeriod() {
		if (typeId.equals(MailTypeEnum.VACNEEDED))
			return employeeVacation.getRemainForcedPeriod();
		else
			return employeeVacation.getRemainNormalYearlyPeriod();
	}

	/**
	 * Save forced vacation
	 * 
	 * @return
	 */
	private String sendForcedVacation() {
		try {
			String title = MessageFormat.format(Utils.loadMessagesFromFile("forced.vacation.title"), employer.getName(),
					HijriCalendarUtil.findCurrentHijriDate());
			dataAccessService.saveVacation(employer, employeeVacation, title, MailTypeEnum.VACNEEDED.getValue(), false,
					null);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("accept.record"));
			resetFields();
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("sendForcedVacation	" + e.getMessage());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}

	}

	/**
	 * Save normal vacation
	 * 
	 * @return
	 */
	private String sendNormalVacation() {
		try {
			String title = MessageFormat.format(Utils.loadMessagesFromFile("normal.vacation.title"), employer.getName(),
					HijriCalendarUtil.findCurrentHijriDate());
			dataAccessService.saveVacation(employer, employeeVacation, title, MailTypeEnum.VACATION.getValue(), false,
					null);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("accept.record"));
			resetFields();
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("sendNormalVacation	" + e.getMessage());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}

	}

	@Override
	public void execute(IDataAccessService dataAccessService) {
		employerId=Utils.findCurrentUser().getUserId();

		this.dataAccessService = dataAccessService;
		acceptVisible = false;
		if (typeId.equals(MailTypeEnum.VACNEEDED)) {
			if (getModeScreen() == MyConstants.INSERT_MODE)
				modelContentHtml = "VacEtrari.xhtml";
			else
				modelContentHtml = "VacEtrari_view.xhtml";
			contentModel = this;
			reportName = MyConstants.REPORT_FORCED_VACATION;

		} else {
			if (getModeScreen() == MyConstants.INSERT_MODE)
				modelContentHtml = "VacNormal.xhtml";
			else
				modelContentHtml = "VacNormal_view.xhtml";
			contentModel = this;
			reportName = MyConstants.REPORT_NORMAL_VACATION;
		}
	}

	@Override
	public String saveAction(IDataAccessService dataAccessService) {

		if (!checkFields())
			return "";
		if (!checkDays())
			return "";
		if (typeId.equals(MailTypeEnum.VACNEEDED))
			return sendForcedVacation();
		else if (typeId.equals(MailTypeEnum.VACATION))
			return sendNormalVacation();
		else
			return "";
	}

	/**
	 * Reset the screen's data
	 */
	private void resetFields() {
		employeeVacation = new HrEmployeeVacation();
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
			// Check that the date
			if (employeeVacation.getVacationPeriod() > getRemainPeriod()) {
				valid = false;
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.max.period"));
			}
			// TODO correct 31/12 is true
			/*
			 * if (Integer.parseInt(employeeVacation.getHigriVacationStart().
			 * substring(6, 10)) != Integer
			 * .parseInt(employeeVacation.getHigriVacationEnd().substring(6,
			 * 10))) { valid = false;
			 * MsgEntry.addErrorMessage(Utils.loadMessagesFromFile(
			 * "error.new.year")); }
			 */

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
		if ((employeeVacation.getVacationPeriod() == null)||(employeeVacation.getVacationPeriod() == 0)
				|| ((employeeVacation.getVacationPeriod() + "").trim().equals(""))) {
			MsgEntry.addErrorAspectToComponent("vacprd");
			valid = false;
		}
		if ((employeeVacation.getHigriVacationStart() == null)
				|| (employeeVacation.getHigriVacationStart().trim().equals(""))) {
			MsgEntry.addErrorAspectToComponent("VACASTRT");
			valid = false;
		}

		if (!valid)
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("required.fields"));
		if ((employeeVacation.getHigriVacationEnd() == null)
				|| (employeeVacation.getHigriVacationEnd().trim().equals(""))) {
			valid = false;
		}
		return valid;
	}

	@Override
	public HrEmployeeVacation loadData(UserMailObj userMailClass) {
		isCurrUserSignAutorized(userMailClass);
		wrkApplicationId = new WrkApplicationId(Integer.parseInt(userMailClass.getWrkId()), userMailClass.getStepId());
		int appId = Integer.parseInt(userMailClass.getAppId());
		employeeVacation = this.dataAccessService.findVacationByArcRecordId(appId);
		documentId = employeeVacation.getId();
		if (typeId.equals(MailTypeEnum.VACNEEDED)) {
			employeeVacation.setUsedForcedPeriod(dataAccessService.findForcedVacationScore(employer.getEmpNB()));
		} else {
			dataAccessService.loadNormalVacation(employer.getEmpNB(), employer.getFirstApplicationDate(),
					employer.getBirthDateGerige(), utilities.Utils.findCurrentUser().getUserId(), employeeVacation);
		}
		// vacanyEndDateFlag = true;
		printDocument(reportName, documentId);
		return employeeVacation;
	}

	@Override
	public String acceptAction() {
		try {
			hrsVacSold = dataAccessService.loadHrsVacSoldById(employeeVacation.getEmployeeNumber());
			dataAccessService.acceptVacation(wrkApplicationId, employeeVacation, typeId.getValue(), hrsVacSold);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("accept.record"));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("acceptAction vacation  ApplicationID :" + wrkApplicationId.getApplicationId() + "  "
					+ e.getMessage());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}

	@Override
	public String refuseAction() {
		try {
			employeeVacation.setVacationStatus(MyConstants.NO);
			/**** begin create new application **/
			WrkApplication newApplication = createNewWrkAppForRefuse();
			/*** end **/
			refuseModel(newApplication, employeeVacation);
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
		ArcUsers user = dataAccessService.findUserFromHrsSigns(arcRecordId);
		Employer employer = dataAccessService.loadEmployerByUser(user);
		return employer;
	}

	@Override
	public void prepareData() {
		if (typeId.equals(MailTypeEnum.VACNEEDED)) {
			loadForcedVacationData();
		} else {
			if (employeeVacation == null)
				employeeVacation = new HrEmployeeVacation();
			loadNormalVacationData();
			disableList = false;
			employeeVacation.setVactionCalc(MyConstants.VACATION_WITHOUT_SALARY);
		}
	}

	/**
	 * Activate accept button
	 * 
	 * @return
	 */
	public boolean isAcceptRendered() {
		if (((employeeVacation != null) && (employeeVacation.getVacationStatus() != null)
				&& (employeeVacation.getVacationStatus().equals(MyConstants.YES))) || (!isReadOnly()))
			return false;
		return true;
	}

	/**
	 * Load last vacation date
	 * 
	 * @return
	 */
	public String getLastVacationDate() {
		return MessageFormat.format(Utils.loadMessagesFromFile("last.normal.vacation"),
				employeeVacation.getLastVacationStart(), employeeVacation.getLastVacationEnd());
	}

	/**
	 * Load last vacation service
	 * 
	 * @return
	 */
	public String getLastVacationService() {
		return MessageFormat.format(Utils.loadMessagesFromFile("last.normal.vacation"),
				employeeVacation.getLastVacationFrom(), employeeVacation.getLastVacationTo());
	}

	public void loadEmployersByDept() {
		// not run now (stopped)
		// setEmployersList(dataAccessService.getAllActiveEmployeesInDept(departmentId));
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

	public boolean isAcceptVisible() {
		return acceptVisible;
	}

	public void setAcceptVisible(boolean acceptVisible) {
		this.acceptVisible = acceptVisible;
	}

	public HrEmployeeVacation getEmployeeVacation() {
		return employeeVacation;
	}

	// public boolean isVacanyEndDateFlag() {
	// return vacanyEndDateFlag;
	// }
	//
	// public void setVacanyEndDateFlag(boolean vacanyEndDateFlag) {
	// this.vacanyEndDateFlag = vacanyEndDateFlag;
	// }

	public void setEmployeeVacation(HrEmployeeVacation employeeVacation) {
		this.employeeVacation = employeeVacation;
	}

	public boolean isDisableList() {
		return disableList;
	}

	public void setDisableList(boolean disableList) {
		this.disableList = disableList;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public List<ArcUsers> getEmployersList() {
		return employersList;
	}

	public void setEmployersList(List<ArcUsers> list) {
		this.employersList = list;
	}

	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
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

	public HrsVacSold getHrsVacSold() {
		return hrsVacSold;
	}

	public void setHrsVacSold(HrsVacSold hrsVacSold) {
		this.hrsVacSold = hrsVacSold;
	}

	public Integer getEmpNo() {
		return empNo;
	}

	public void setEmpNo(Integer empNo) {
		this.empNo = empNo;
	}
}

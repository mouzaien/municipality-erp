package com.bkeryah.managedBean.hr;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.bkeryah.entities.HrsMasterFile;
import com.bkeryah.hr.entities.HrsEmpOvertime;
import com.bkeryah.hr.entities.Sys012;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class OvertimeBean {

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private HrsEmpOvertime overtime = new HrsEmpOvertime();
	private Employer employer = new Employer();
	private List<HrsMasterFile> employersList;
	private Integer employerNB;
	private String month;
	private List<HrsEmpOvertime> overtimeList;
	private List<HrsEmpOvertime> filteredOvertimeList;
	private boolean updateMode;
	private boolean higriMode;
	private String dateFrom;
	private Date dateFrom_G;
	private String dateTo;
	private Date dateTo_G;

	@PostConstruct
	public void init() {
		employersList = dataAccessService.loadActiveEmpsHasSalary();
	}

	public void loadEmployer() {
		setEmployer(dataAccessService.loadEmployerByUser(employerNB));
		loadEmployerOvertimes();
	}

	public void loadOvertimeDetails() {
		month = ((Sys012) dataAccessService.findEntityById(Sys012.class, overtime.getMonthOvertime())).getNameAr();
		updateMode = false;
	}

	public void calculateNormalOvertime() {
		double sal = 0;
		double trans = 0;
		if (employer.getBasicSalary() != null) {
			if ((overtime.getNormalDays() != null) && (overtime.getNormalHours() != null)) {
				sal = Utils.formatDouble(
						employer.getBasicSalary() * overtime.getNormalDays() * overtime.getNormalHours() / (155d));
				if (employer.getCategoryId() == 1 || employer.getCategoryId() == 2 || employer.getCategoryId() == 4) {
					if (employer.getTrans() != null && sal != 0) {
						trans = Utils.formatDouble(employer.getTrans() * overtime.getNormalDays() / 30);

						overtime.setNormalValue(sal);
						overtime.setTrans(trans);
						calculateTotal();
					}
				}
			}
		}
	}

	private void calculateTotal() {
		if (overtime.getNormalValue() != null)
			overtime.setDeservedTotlal(overtime.getNormalValue());
		if (overtime.getHolidaysValue() != null)
			overtime.setDeservedTotlal(overtime.getDeservedTotlal() + overtime.getHolidaysValue());
		if (overtime.getFeastValue() != null)
			overtime.setDeservedTotlal(overtime.getDeservedTotlal() + overtime.getFeastValue());
	}

	public void saveNewDates() {
		overtime.setToDate(Utils.convertDateToString(overtime.getToGrigDate()));
		overtime.setFromDate(Utils.convertDateToString(overtime.getFromGrigDate()));
	}

	public void sortDates() {
		try {
			if (!higriMode) {

				dateFrom = Utils.grigDatesConvert(dateFrom_G);
				dateTo = Utils.grigDatesConvert(dateTo_G);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		overtime.setFromDate(dateFrom);
		overtime.setToDate(dateTo);
	}

	public void calculateHolidayOvertime() {
		double sal = 0;
		double trans = 0;

		if (employer.getBasicSalary() != null) {
			if ((overtime.getHolidays() != null) && (overtime.getHolidaysHours() != null))

				sal = Utils.formatDouble(
						employer.getBasicSalary() * overtime.getHolidays() * overtime.getHolidaysHours() / (155d));
			if (employer.getCategoryId() == 1 || employer.getCategoryId() == 2 || employer.getCategoryId() == 4) {
				trans = Utils.formatDouble(employer.getBasicSalary() * overtime.getHolidays() / 30);
			}
			overtime.setHolidaysValue(sal);
			overtime.setTrans(trans);
			calculateTotal();
		}

	}

	public void calculateAidOvertime() {
		double sal = 0;
		double trans = 0;
		if (employer.getBasicSalary() != null) {
			if ((overtime.getFeastDays() != null) && (overtime.getFeastHours() != null)) {
				sal = Utils.formatDouble(
						employer.getBasicSalary() * overtime.getFeastDays() * overtime.getFeastHours() / (155d));
				if (employer.getCategoryId() == 1 || employer.getCategoryId() == 2 || employer.getCategoryId() == 4) {
					trans = Utils.formatDouble(employer.getBasicSalary() * overtime.getFeastDays() / 30);
				}
				overtime.setFeastValue(sal);
				overtime.setTrans(trans);
				calculateTotal();
			}
		}
	}

	private void loadEmployerOvertimes() {
		overtimeList = dataAccessService.loadEmployerOvertimes(employer.getEmpNB());
	}

	public void saveOvertime() {
		try {
			overtime.setEmployeeNumber(employerNB);
			overtime.setCategory(employer.getCategoryId());
			// saveNewDates();
			sortDates();
			dataAccessService.saveOvertime(overtime);
			overtimeList = null;
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}

	public void updateOvertime() {
		try {
			dataAccessService.updateOvertime(overtime);
			overtimeList = null;
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}

	public void addOvertime() {

	}

	public void activateUpdateMode() {
		employerNB = employer.getEmpNB();
		updateMode = true;
	}

	/**
	 * Print report
	 */
	public String printReportAction() {
		String reportName = "/reports/loan.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		// parameters.put("loanTypeId", bankId);
		// parameters.put("year", year);
		// parameters.put("month", month);
		// parameters.put("bankName",
		// ((HrsLoanType)dataAccessService.findEntityById(HrsLoanType.class,
		// bankId)).getLoanTypeName());
		// parameters.put("monthName",
		// ((Sys012)dataAccessService.findEntityById(Sys012.class,
		// month)).getNameAr());
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public List<HrsMasterFile> getEmployersList() {
		return employersList;
	}

	public void setEmployersList(List<HrsMasterFile> employersList) {
		this.employersList = employersList;
	}

	public Integer getEmployerNB() {
		return employerNB;
	}

	public void setEmployerNB(Integer employerNB) {
		this.employerNB = employerNB;
	}

	public HrsEmpOvertime getOvertime() {
		return overtime;
	}

	public void setOvertime(HrsEmpOvertime overtime) {
		this.overtime = overtime;
	}

	public List<HrsEmpOvertime> getOvertimeList() {
		return overtimeList;
	}

	public void setOvertimeList(List<HrsEmpOvertime> overtimeList) {
		this.overtimeList = overtimeList;
	}

	public List<HrsEmpOvertime> getFilteredOvertimeList() {
		return filteredOvertimeList;
	}

	public void setFilteredOvertimeList(List<HrsEmpOvertime> filteredOvertimeList) {
		this.filteredOvertimeList = filteredOvertimeList;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public boolean isUpdateMode() {
		return updateMode;
	}

	public void setUpdateMode(boolean updateMode) {
		this.updateMode = updateMode;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public Date getDateTo_G() {
		return dateTo_G;
	}

	public void setDateTo_G(Date dateTo_G) {
		this.dateTo_G = dateTo_G;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateFrom_G() {
		return dateFrom_G;
	}

	public void setDateFrom_G(Date dateFrom_G) {
		this.dateFrom_G = dateFrom_G;
	}

	public boolean isHigriMode() {
		return higriMode;
	}

	public void setHigriMode(boolean higriMode) {
		this.higriMode = higriMode;
	}

}

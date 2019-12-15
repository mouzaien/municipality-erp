package com.bkeryah.managedBean.hr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.bkeryah.entities.HrsEmpHistorical;
import com.bkeryah.entities.HrsEmpTerminate;
import com.bkeryah.entities.HrsJobHistorical;
import com.bkeryah.entities.HrsMasterFile;
import com.bkeryah.hr.entities.HrsLoan;
import com.bkeryah.hr.entities.HrsLoanDetails;
import com.bkeryah.hr.entities.HrsLoanType;
import com.bkeryah.hr.entities.Sys012;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class LeavingEmployerBean {

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private HrsEmpTerminate empTerminate = new HrsEmpTerminate();
	private List<HrsEmpTerminate> empTerminateList;
	private List<HrsEmpTerminate> filteredEmpTerminates;
	private Integer employerNB;
	private Integer workDays;
	private Integer workMonths;
	private Integer workYears;
	private boolean hasReward;
	private boolean hasSalary;
	private boolean hasVacation;
	private Integer rewardValue;
	private Integer vacation;
	private Integer vacationValue;

	private HrsLoan loan = new HrsLoan();
	// private Employer employer = new Employer();
	private List<HrsMasterFile> employersList;
	private HrsMasterFile employer;

	private List<HrsLoan> loanList;
	private List<HrsLoanDetails> loanDetailsList;
	private List<HrsLoanDetails> filteredloanDetails;
	private boolean stoppedLoan;
	private Double monthCount;
	private Integer year;
	private Integer month;
	private Integer bankId;

	private boolean higriMode;

	private String incomeDate;
	private Date incomeDate_G;
	private String executedDate;
	private Date executedDate_G;
	List<HrsMasterFile> activeEmployersList;
	private HrsEmpHistorical empHistorical;
	private HrsJobHistorical jobHistorical;

	@PostConstruct
	public void init() {
		empTerminateList = dataAccessService.loadAllEmpTerminates();
		  activeEmployersList = dataAccessService.loadActiveEmpsHasSalary();
	}

	public void loadEmployerData() {
		// setEmployer(dataAccessService.loadEmployerByUser(employerNB));
		loadLoans();
	}

	public void loadEmployer() throws ParseException {
		employer = (HrsMasterFile) dataAccessService.findEntityById(HrsMasterFile.class, employerNB);
		empHistorical = dataAccessService.getHRsEmpHistoricalByEmpNo(employerNB);
		  jobHistorical = dataAccessService.loadLastHistoricalJob(empHistorical.getJobNumber(), empHistorical.getJobcode());
		if (employer.getFirstApplicationDate() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			int daysNB = HijriCalendarUtil.findDatesDifferenceInDays(employer.getFirstApplicationDate(),
					HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(sdf.format(new Date())));
			workYears = daysNB / 360;
			workMonths = (daysNB - workYears * 360) / 30;
			workDays = daysNB - (workYears * 360) - (workMonths * 30);
		}
	}

	private void loadLoans() {
		// loanList = dataAccessService.loadLoans(employer.getEmpNB());
	}

	public void loadLoanDetails() {
		stoppedLoan = (loan.getLoanStsASE().equals("S"));
		loanDetailsList = dataAccessService.loadLoanDetails(loan.getLoanId());
	}

	public void addLeavingEmloyer() {
		empTerminate = new HrsEmpTerminate();
	}

	public void saveLeavingEmloyer() {
		try {
			empTerminate.setEmployeNumber(getEmployerNB());
			sortDates();
			empTerminate.setVacationGive((hasVacation) ? MyConstants.YES : MyConstants.NO);
			empTerminate.setSalaryGive((hasSalary) ? MyConstants.YES : MyConstants.NO);
			empTerminate.setRewardGive((hasReward) ? MyConstants.YES : MyConstants.NO);
			
			dataAccessService.saveLeavingEmployer(empTerminate,empHistorical,jobHistorical);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}

	public void sortDates() {

		try {
			if (!higriMode) {
				incomeDate = Utils.grigDatesConvert(incomeDate_G);
				executedDate = Utils.grigDatesConvert(executedDate_G);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		empTerminate.setIncomeDateHijri(incomeDate);
		empTerminate.setExecutedDate(executedDate);

	}

	public void calculateTotal() {
		empTerminate.setNatevalue( (rewardValue == null ? 0 :rewardValue ) + (vacationValue==null ? 0 :vacationValue));
	}

	public void stopLoan() {
		try {
			loan.setLoanStsASE("S");
			dataAccessService.updateObject(loan);
			loadLoans();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}

	public void updateLoanDetails() {
		try {
			dataAccessService.updateLoanDetails(loanDetailsList);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}

	public void calculateMonthNB() {
		if ((loan.getLoanMonthlyPayment() != null) && (loan.getLoanMonthlyPayment() != 0))
			setMonthCount((((double) (loan.getLoanValue() - loan.getLoanFirstPayment() - loan.getLoanLastPayment()))
					/ (double) loan.getLoanMonthlyPayment()) + 2);
		// loan.setLoanMonthlyCount((loan.getLoanValue()-loan.getLoanFirstPayment()-loan.getLoanLastPayment())/loan.getLoanMonthlyPayment());
	}

	public void printLoan() {
		month = null;
		year = null;
		bankId = null;
	}

	/**
	 * Print report
	 */
	public String printReportAction() {
		String reportName = "/reports/loan.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("loanTypeId", bankId);
		parameters.put("year", year);
		parameters.put("month", month);
		parameters.put("bankName",
				((HrsLoanType) dataAccessService.findEntityById(HrsLoanType.class, bankId)).getLoanTypeName());
		parameters.put("monthName", ((Sys012) dataAccessService.findEntityById(Sys012.class, month)).getNameAr());
		parameters.put("compName", dataAccessService.findSystemProperty("CUSTOMER_NAME"));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public HrsLoan getLoan() {
		return loan;
	}

	public void setLoan(HrsLoan loan) {
		this.loan = loan;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public HrsMasterFile getEmployer() {
		return employer;
	}

	public void setEmployer(HrsMasterFile employer) {
		this.employer = employer;
	}

	public List<HrsMasterFile> getEmployersList() {
		return employersList;
	}

	public void setEmployersList(List<HrsMasterFile> employersList) {
		this.employersList = employersList;
	}

	public List<HrsLoan> getLoanList() {
		return loanList;
	}

	public void setLoanList(List<HrsLoan> loanList) {
		this.loanList = loanList;
	}

	public Integer getEmployerNB() {
		return employerNB;
	}

	public void setEmployerNB(Integer employerNB) {
		this.employerNB = employerNB;
	}

	public List<HrsLoanDetails> getLoanDetailsList() {
		return loanDetailsList;
	}

	public void setLoanDetailsList(List<HrsLoanDetails> loanDetailsList) {
		this.loanDetailsList = loanDetailsList;
	}

	public List<HrsLoanDetails> getFilteredloanDetails() {
		return filteredloanDetails;
	}

	public void setFilteredloanDetails(List<HrsLoanDetails> filteredloanDetails) {
		this.filteredloanDetails = filteredloanDetails;
	}

	public boolean isStoppedLoan() {
		return stoppedLoan;
	}

	public void setStoppedLoan(boolean stoppedLoan) {
		this.stoppedLoan = stoppedLoan;
	}

	public Double getMonthCount() {
		return monthCount;
	}

	public void setMonthCount(Double monthCount) {
		this.monthCount = monthCount;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public HrsEmpTerminate getEmpTerminate() {
		return empTerminate;
	}

	public void setEmpTerminate(HrsEmpTerminate empTerminate) {
		this.empTerminate = empTerminate;
	}

	public List<HrsEmpTerminate> getEmpTerminateList() {
		return empTerminateList;
	}

	public void setEmpTerminateList(List<HrsEmpTerminate> empTerminateList) {
		this.empTerminateList = empTerminateList;
	}

	public List<HrsEmpTerminate> getFilteredEmpTerminates() {
		return filteredEmpTerminates;
	}

	public void setFilteredEmpTerminates(List<HrsEmpTerminate> filteredEmpTerminates) {
		this.filteredEmpTerminates = filteredEmpTerminates;
	}

	public Integer getWorkDays() {
		return workDays;
	}

	public void setWorkDays(Integer workDays) {
		this.workDays = workDays;
	}

	public Integer getWorkMonths() {
		return workMonths;
	}

	public void setWorkMonths(Integer workMonths) {
		this.workMonths = workMonths;
	}

	public Integer getWorkYears() {
		return workYears;
	}

	public void setWorkYears(Integer workYears) {
		this.workYears = workYears;
	}

	public boolean isHasReward() {
		return hasReward;
	}

	public void setHasReward(boolean hasReward) {
		this.hasReward = hasReward;
	}

	public boolean isHasSalary() {
		return hasSalary;
	}

	public void setHasSalary(boolean hasSalary) {
		this.hasSalary = hasSalary;
	}

	public boolean isHasVacation() {
		return hasVacation;
	}

	public void setHasVacation(boolean hasVacation) {
		this.hasVacation = hasVacation;
	}

	public Integer getRewardValue() {
		return rewardValue;
	}

	public void setRewardValue(Integer rewardValue) {
		this.rewardValue = rewardValue;
	}

	public Integer getVacation() {
		return vacation;
	}

	public void setVacation(Integer vacation) {
		this.vacation = vacation;
	}

	public Integer getVacationValue() {
		return vacationValue;
	}

	public void setVacationValue(Integer vacationValue) {
		this.vacationValue = vacationValue;
	}

	public boolean isHigriMode() {
		return higriMode;
	}

	public void setHigriMode(boolean higriMode) {
		this.higriMode = higriMode;
	}

	public String getIncomeDate() {
		return incomeDate;
	}

	public void setIncomeDate(String incomeDate) {
		this.incomeDate = incomeDate;
	}

	public Date getIncomeDate_G() {
		return incomeDate_G;
	}

	public void setIncomeDate_G(Date incomeDate_G) {
		this.incomeDate_G = incomeDate_G;
	}

	public String getExecutedDate() {
		return executedDate;
	}

	public void setExecutedDate(String executedDate) {
		this.executedDate = executedDate;
	}

	public Date getExecutedDate_G() {
		return executedDate_G;
	}

	public void setExecutedDate_G(Date executedDate_G) {
		this.executedDate_G = executedDate_G;
	}

	public List<HrsMasterFile> getActiveEmployersList() {
		return activeEmployersList;
	}

	public void setActiveEmployersList(List<HrsMasterFile> activeEmployersList) {
		this.activeEmployersList = activeEmployersList;
	}

	public HrsEmpHistorical getEmpHistorical() {
		return empHistorical;
	}

	public void setEmpHistorical(HrsEmpHistorical empHistorical) {
		this.empHistorical = empHistorical;
	}

	public HrsJobHistorical getJobHistorical() {
		return jobHistorical;
	}

	public void setJobHistorical(HrsJobHistorical jobHistorical) {
		this.jobHistorical = jobHistorical;
	}

}

package com.bkeryah.managedBean.hr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.HrsMasterFile;
import com.bkeryah.hr.entities.HrsLoan;
import com.bkeryah.hr.entities.HrsLoanDetails;
import com.bkeryah.hr.entities.HrsLoanType;
import com.bkeryah.hr.entities.Sys012;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class LoanBean {

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private HrsLoan loan = new HrsLoan();
	private Employer employer = new Employer();
	private List<HrsMasterFile> employersList;
	private Integer employerNB;
	private List<HrsLoan> loanList;
	private List<HrsLoanDetails> loanDetailsList;
	private List<HrsLoanDetails> filteredloanDetails;
	private boolean stoppedLoan;
	private Double monthCount;
	private Integer year;
	private Integer month;
	private Integer bankId;
	
	@PostConstruct
	public void init(){
//		employersList = dataAccessService.getAllLoanEmployees();
		employersList = dataAccessService.getAllEmployeesActive();
	}
	
	public void loadEmployerData() {
//		ArcUsers user = (ArcUsers) dataAccessService.findEntityById(ArcUsers.class, userId);
		setEmployer(dataAccessService.loadEmployerByUser(employerNB));
		loadLoans();
	}
	
	public void loadEmployer() {
		setEmployer(dataAccessService.loadEmployerByUser(employerNB));
	}

	private void loadLoans() {
		loanList = dataAccessService.loadLoans(employer.getEmpNB());
	}
	
	public void loadLoanDetails(){
		stoppedLoan = (loan.getLoanStsASE().equals("S"));
		loanDetailsList = dataAccessService.loadLoanDetails(loan.getLoanId());
	}
	
	public void addLoan(){
		loan = new HrsLoan();
	}
	
	public void saveLoan(){
		try{
			loan.setLoanEmpno(employerNB);
			loan.setLoanStsASE("A");
			loan.setLoanMonthlyCount(monthCount.intValue());
			dataAccessService.saveLoan(loan);
			loanList=null;
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}
	
	public void stopLoan(){
		try{
			loan.setLoanStsASE("S");
			dataAccessService.updateObject(loan);
			loadLoans();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}
	
	public void updateLoanDetails(){
		try{
			dataAccessService.updateLoanDetails(loanDetailsList);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}
	
	public void calculateMonthNB(){
		if((loan.getLoanMonthlyPayment() != null) && (loan.getLoanMonthlyPayment() != 0))
			setMonthCount((((double)(loan.getLoanValue()-loan.getLoanFirstPayment()-loan.getLoanLastPayment()))/(double)loan.getLoanMonthlyPayment())+2);
//		loan.setLoanMonthlyCount((loan.getLoanValue()-loan.getLoanFirstPayment()-loan.getLoanLastPayment())/loan.getLoanMonthlyPayment());
	}
	
	public void printLoan(){
		month=null;
		year=null;
		bankId=null;
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
		parameters.put("bankName", ((HrsLoanType)dataAccessService.findEntityById(HrsLoanType.class, bankId)).getLoanTypeName());
		parameters.put("monthName", ((Sys012)dataAccessService.findEntityById(Sys012.class, month)).getNameAr());
		parameters.put("LOGO_DIR", FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath(Utils.loadMessagesFromFile("report.logo")));
		parameters.put("compName", Utils.loadMessagesFromFile("comp.name"));
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

}

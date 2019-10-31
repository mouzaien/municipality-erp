package com.bkeryah.managedBean.hr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;

import com.bkeryah.entities.HrsEmpHistorical;
import com.bkeryah.entities.HrsMasterFile;
import com.bkeryah.hr.entities.EmpMoveType;
import com.bkeryah.hr.entities.HrsJobCreation;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ViewScoped
// public class PromotionBean extends GrigAndHijriCalender {
public class PromotionBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private HrsEmpHistorical empHistorical;
	private EmpMoveType empMoveType;
	private List<EmpMoveType> empMoveTypeList = new ArrayList<EmpMoveType>();
	private HrsEmpHistorical newJob = new HrsEmpHistorical();
	private List<HrsEmpHistorical> empHistoricalList = new ArrayList<HrsEmpHistorical>();
	private List<HrsEmpHistorical> filteredEmpList = new ArrayList<HrsEmpHistorical>();
	private Employer employer = new Employer();
	private List<HrsMasterFile> employersList = new ArrayList<HrsMasterFile>();
	private Integer employerNB;
	private List<HrsJobCreation> jobsList;
	private Integer jobId;
	private List<Integer> recType = new ArrayList<Integer>();

	private boolean higriMode;

	private String incomeDate_H;
	private Date incomeDate_G;
	private String executeDate_H;
	private Date executeDate_G;

	@PostConstruct
	public void init() {
		employersList = dataAccessService.loadActiveEmpsHasSalary();
		empMoveTypeList = dataAccessService.loadMoveType();

	}

	// public void showEmployees(ActionEvent ae) {
	// RequestContext.getCurrentInstance().openDialog("promotionDialog2",
	// getDialogOptions(), null);
	// }
	//
	// public Map<String, Object> getDialogOptions() {
	// Map<String, Object> options = new HashMap<>();
	// options.put("resizable", false);
	// options.put("draggable", true);
	// options.put("modal", true);
	// options.put("height", 400);
	// options.put("contentHeight", "100%");
	// return options;
	// }

	public void loadJobs(AjaxBehaviorEvent abe) {
		if (employerNB != null && employerNB > 0) {
			empHistoricalList.clear();
			empHistoricalList = dataAccessService.loadPromotionJobs(employerNB);

		}
	}

	public void loadPromotionDetails() {
		empHistorical.setOldJobName(dataAccessService.loadHrsGovJob(empHistorical.getOldJobCode()).getJobName());
	}

	public void loadJob() {
		newJob = dataAccessService.loadJobCreationData(getJobId(), empHistorical.getBasicSalary());
	}

	public void loadLastJob() {
		empHistorical = dataAccessService.getHRsEmpHistoricalByEmpNo(employerNB);
		jobsList = dataAccessService.loadHrJob(empHistorical.getCATegoryId(), MyConstants.JOBEMPTY,
				empHistorical.getRankNumber() + 1);
		System.out.println("size ---->" + jobsList.size());
	}

	public void print() {
		System.out.println("incomeDate_H" + incomeDate_H);
		System.out.println("incomeDate_G" + incomeDate_G);
		System.out.println("executeDate_H" + executeDate_H);
		System.out.println("executeDate_G" + executeDate_G);
	}

	public void savePromotion() {
		try {

			// empHistorical.setIncomDate(Utils.convertDateToString(empHistorical.getIncomDateGrig()));
			// empHistorical.setExecuteDate(Utils.convertDateToString(empHistorical.getExecuteDateGrig()));

			sortDate();
			dataAccessService.savePromotion(empHistorical, newJob);
			empHistoricalList = null;
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		}

		catch (

		Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}

	public void sortDate() {
		try {
			if (higriMode) {
				incomeDate_G = Utils.convertHDateToGDate(incomeDate_H);
				incomeDate_H = Utils.convertDateToString(incomeDate_G);
			} else {
				incomeDate_H = Utils.convertDateToString(incomeDate_G);
				executeDate_H = Utils.grigDatesConvert(executeDate_G);
			}

			newJob.setIncomDate(incomeDate_H);
			newJob.setExecuteDate(executeDate_H);
		} catch (Exception e) {
			// TODO: handle exception
		}
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

	public HrsEmpHistorical getEmpHistorical() {
		return empHistorical;
	}

	public void setEmpHistorical(HrsEmpHistorical empHistorical) {
		this.empHistorical = empHistorical;
	}

	public List<HrsJobCreation> getJobsList() {
		return jobsList;
	}

	public void setJobsList(List<HrsJobCreation> jobsList) {
		this.jobsList = jobsList;
	}

	public HrsEmpHistorical getNewJob() {
		return newJob;
	}

	public void setNewJob(HrsEmpHistorical newJob) {
		this.newJob = newJob;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public List<HrsEmpHistorical> getEmpHistoricalList() {
		return empHistoricalList;
	}

	public void setEmpHistoricalList(List<HrsEmpHistorical> empHistoricalList) {
		this.empHistoricalList = empHistoricalList;
	}

	public List<HrsEmpHistorical> getFilteredEmpList() {
		return filteredEmpList;
	}

	public void setFilteredEmpList(List<HrsEmpHistorical> filteredEmpList) {
		this.filteredEmpList = filteredEmpList;
	}

	public List<Integer> getRecType() {
		return recType;
	}

	public void setRecType(List<Integer> recType) {
		this.recType = recType;
	}

	public EmpMoveType getEmpMoveType() {
		return empMoveType;
	}

	public void setEmpMoveType(EmpMoveType empMoveType) {
		this.empMoveType = empMoveType;
	}

	public List<EmpMoveType> getEmpMoveTypeList() {
		return empMoveTypeList;
	}

	public void setEmpMoveTypeList(List<EmpMoveType> empMoveTypeList) {
		this.empMoveTypeList = empMoveTypeList;
	}

	public boolean isHigriMode() {
		return higriMode;
	}

	public void setHigriMode(boolean higriMode) {
		this.higriMode = higriMode;
	}

	public String getIncomeDate_H() {
		return incomeDate_H;
	}

	public void setIncomeDate_H(String incomeDate_H) {
		this.incomeDate_H = incomeDate_H;
	}

	public Date getIncomeDate_G() {

		return incomeDate_G;

	}

	public void setIncomeDate_G(Date incomeDate_G) {
		this.incomeDate_G = incomeDate_G;
	}

	public String getExecuteDate_H() {
		return executeDate_H;
	}

	public void setExecuteDate_H(String executeDate_H) {
		this.executeDate_H = executeDate_H;
	}

	public Date getExecuteDate_G() {
		return executeDate_G;
	}

	public void setExecuteDate_G(Date executeDate_G) {
		this.executeDate_G = executeDate_G;
	}

}

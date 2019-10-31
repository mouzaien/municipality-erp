package com.bkeryah.managedBean.hr;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.bkeryah.entities.HrsEmpHistorical;
import com.bkeryah.entities.HrsGovJob4;
import com.bkeryah.hr.entities.HrsJobCreation;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class JobsBean {

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private HrsJobCreation job = new HrsJobCreation();
	private HrsJobCreation selectejob = new HrsJobCreation();
	private HrsJobCreation oldJob = new HrsJobCreation();
	private List<HrsJobCreation> jobsList;
	private List<HrsJobCreation> filteredJobs;
	private List<HrsGovJob4> jobsLabelList;
	private boolean consultMode;
	private boolean addMode;
	// For salkh operation
	private boolean changeMode;
	// For tahwir operation
	private boolean tahwirMode;
	private boolean damageMode;
	private Integer year;
	private List<HrsEmpHistorical> primesList;
	private List<HrsEmpHistorical> filteredPrimes;
	private boolean updateMode;
	private static final Integer EMPTY_JOB = 1;
	private static final String BKERYAH_CODE = "942200012";

	@PostConstruct
	public void init() {
		loadJobs();

	}

	public void loadJobs() {
		jobsList = dataAccessService.loadAllJobs();
		System.out.print("jopList Size == " + jobsList.size());
	}

	public void calculatePrimes() {
		primesList = null;
		if (validateYear())
			primesList = dataAccessService.calculatePrimes();
	}

	private boolean validateYear() {
		if (year == null) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.enter.year"));
			return false;
		} else if (dataAccessService.findYearPrime(year) != null) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.calculated.prime"));
			return false;
		}
		return true;
	}

	public void savePrimes() {
		try {
			dataAccessService.savePrimes(primesList, year);
			primesList = null;
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}

	public void loadSelectedJob() {
		loadJobsByCategory();
		oldJob = new HrsJobCreation(job);
		consultMode = true;
		addMode = false;
		changeMode = false;
		tahwirMode = false;
		damageMode = false;
		updateMode = false;
	}

	public void loadJobsByCategory() {
		jobsLabelList = dataAccessService.loadJobsByCategory(job.getCategoryId(),
				(updateMode) ? null : job.getRankCode());
		///////// balabel ///////
		// jobsLabelList =
		// dataAccessService.loadJobsByCategory(job.getCategoryId(),
		// (addMode) ? null : job.getRankCode());
	}

	public void activateUpdateFields() {
		try {
			if (job.getJobDate() != null) {
				job.setGirgJobDate(new SimpleDateFormat("dd/MM/yyyy").parse(job.getJobDate()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		consultMode = false;
		updateMode = true;
		loadJobsByCategory();
	}

	public void activateChangeFields() {
		consultMode = false;
		changeMode = true;
	}

	public void activateTahwirFields() {
		consultMode = false;
		tahwirMode = true;
	}

	public void activateDamageFields() {
		consultMode = false;
		damageMode = true;
	}

	public void saveJob() {
		try {
			job.setJobDate(Utils.convertDateToString(job.getGirgJobDate()));
			if (addMode)
				dataAccessService.saveJob(job);
			else if (changeMode)
				dataAccessService.updateJob(job, oldJob, 2);
			else if (tahwirMode)
				dataAccessService.updateJob(job, oldJob, 4);
			else if (damageMode)
				dataAccessService.updateJob(job, oldJob, 5);
			else
				dataAccessService.updateJob(job, oldJob, 5);

			loadJobs();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}

	public void addJob() {
		job = new HrsJobCreation();
		job.setJobstatus(EMPTY_JOB);
		job.setDepatmentCode(BKERYAH_CODE);
		consultMode = false;
		addMode = true;
		changeMode = false;
		tahwirMode = false;
		damageMode = false;
		updateMode = false;
	}

	public void loadHistricalJob() {

		selectejob = (HrsJobCreation) dataAccessService.loadSelectedJob(job.getCategoryId(), MyConstants.JOBEMPTY);
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public HrsJobCreation getJob() {
		return job;
	}

	public void setJob(HrsJobCreation job) {
		this.job = job;
	}

	public List<HrsGovJob4> getJobsLabelList() {
		return jobsLabelList;
	}

	public void setJobsLabelList(List<HrsGovJob4> jobsLabelList) {
		this.jobsLabelList = jobsLabelList;
	}

	public List<HrsJobCreation> getJobsList() {
		return jobsList;
	}

	public void setJobsList(List<HrsJobCreation> jobsList) {
		this.jobsList = jobsList;
	}

	public List<HrsJobCreation> getFilteredJobs() {
		return filteredJobs;
	}

	public void setFilteredJobs(List<HrsJobCreation> filteredJobs) {
		this.filteredJobs = filteredJobs;
	}

	public boolean isConsultMode() {
		return consultMode;
	}

	public void setConsultMode(boolean consultMode) {
		this.consultMode = consultMode;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}

	public HrsJobCreation getOldJob() {
		return oldJob;
	}

	public void setOldJob(HrsJobCreation oldJob) {
		this.oldJob = oldJob;
	}

	public boolean isChangeMode() {
		return changeMode;
	}

	public void setChangeMode(boolean changeMode) {
		this.changeMode = changeMode;
	}

	public boolean isTahwirMode() {
		return tahwirMode;
	}

	public void setTahwirMode(boolean tahwirMode) {
		this.tahwirMode = tahwirMode;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public List<HrsEmpHistorical> getPrimesList() {
		return primesList;
	}

	public void setPrimesList(List<HrsEmpHistorical> primesList) {
		this.primesList = primesList;
	}

	public List<HrsEmpHistorical> getFilteredPrimes() {
		return filteredPrimes;
	}

	public void setFilteredPrimes(List<HrsEmpHistorical> filteredPrimes) {
		this.filteredPrimes = filteredPrimes;
	}

	public boolean isDamageMode() {
		return damageMode;
	}

	public void setDamageMode(boolean damageMode) {
		this.damageMode = damageMode;
	}

	public boolean isUpdateMode() {
		return updateMode;
	}

	public void setUpdateMode(boolean updateMode) {
		this.updateMode = updateMode;
	}

	public HrsJobCreation getSelectejob() {
		return selectejob;
	}

	public void setSelectejob(HrsJobCreation selectejob) {
		this.selectejob = selectejob;
	}

}

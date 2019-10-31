package com.bkeryah.hr.managedBeans;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.HrsGovJob4;
import com.bkeryah.hr.entities.HrsGovJobWrks;
import com.bkeryah.hr.entities.Sys037;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class ContractorJobBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private Integer categoryId;
	private HrsGovJobWrks hrsGovJobWrks;
	private String serialJob;
	private String title;
	private List<HrsGovJob4> jobsList;
	private List<HrsGovJob4> filteredJobsList;
	private List<Sys037> jobCategorieslist;
	private HrsGovJob4 job = new HrsGovJob4();
	
	@PostConstruct
	public void init(){
		if ((jobCategorieslist == null) || (jobCategorieslist.isEmpty())){
			jobCategorieslist = dataAccessService.loadAllJobCategories();
			jobCategorieslist.remove(0);
		}
	}
	
	public void loadJobsByCategory(){
		setJobsList(dataAccessService.loadJobsByCategory(categoryId, null));
	}
	
	public void addJob(){
		job = new HrsGovJob4();
		job.setCreateDate(new Date());
		job.setCreatedBy(Utils.findCurrentUser().getUserId());
	}
	
//	public  String saveJob(){
//		try {
//			dataAccessService.save(job);
//			MsgEntry.addInfoMessage(MsgEntry.SUCCESS_SAVE_CONTRACTOR);
//			title = null;
//			serialJob=null;
//		} catch (Exception e) {
//			MsgEntry.addInfoMessage(MsgEntry.ERROR);
//		} 
//		return null;
//	}
	
	public void onRowEdit(RowEditEvent event) {
		HrsGovJob4 job =  (HrsGovJob4) event.getObject();
		try{
			dataAccessService.updateObject(job);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
    }
	
	public void saveJob(){
		boolean valid = true;
		try{
			if ((job.getCATegoryId() == null)
					|| (job.getCATegoryId() == 0)) {
				valid = false;
			}else if ((job.getJobCode() == null)
					|| (job.getJobCode().trim().equals(""))) {
				valid = false;
			}else if ((job.getJobName() == null)
					|| (job.getJobName().trim().equals(""))) {
				valid = false;
			}
			if (!valid){
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("required.fields"));
				return;
			}
			dataAccessService.saveObject(job);
			if((categoryId == job.getCATegoryId()) && (jobsList != null))
				jobsList.add(job);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			job = new HrsGovJob4();
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('job_dlg').hide();");
		} catch (Exception e) {
			e.printStackTrace();
			if(valid)
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}
     
    public void onRowCancel(RowEditEvent event) {
    	
    }
	
	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public HrsGovJobWrks getHrsGovJobWrks() {
		return hrsGovJobWrks;
	}

	public void setHrsGovJobWrks(HrsGovJobWrks hrsGovJobWrks) {
		this.hrsGovJobWrks = hrsGovJobWrks;
	}

	public String getSerialJob() {
		return serialJob;
	}

	public void setSerialJob(String serialJob) {
		this.serialJob = serialJob;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<HrsGovJob4> getJobsList() {
		return jobsList;
	}

	public void setJobsList(List<HrsGovJob4> jobsList) {
		this.jobsList = jobsList;
	}

	public List<HrsGovJob4> getFilteredJobsList() {
		return filteredJobsList;
	}

	public void setFilteredJobsList(List<HrsGovJob4> filteredJobsList) {
		this.filteredJobsList = filteredJobsList;
	}

	public List<Sys037> getJobCategorieslist() {
		return jobCategorieslist;
	}

	public void setJobCategorieslist(List<Sys037> jobCategorieslist) {
		this.jobCategorieslist = jobCategorieslist;
	}

	public HrsGovJob4 getJob() {
		return job;
	}

	public void setJob(HrsGovJob4 job) {
		this.job = job;
	}
}

package com.bkeryah.penalties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import com.bkeryah.entities.ArcPeopleModel;
import com.bkeryah.entities.CodesFines;
import com.bkeryah.entities.HealthLicenceJob;
import com.bkeryah.entities.PayMaster;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class PenalityJobsBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<HealthLicenceJob> hlthLicJobsList = new ArrayList<HealthLicenceJob>();
	HealthLicenceJob addJob = new HealthLicenceJob();

	@PostConstruct
	private void init() {
		hlthLicJobsList = dataAccessService.getAllHealthLicenceJobsList();

	}

	public void addSection() {
		try {
			dataAccessService.save(addJob);
			hlthLicJobsList = dataAccessService.getAllHealthLicenceJobsList();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

		}
	}

	public void onRowEdit(RowEditEvent event) {
		try {
			HealthLicenceJob code = new HealthLicenceJob();
			code = ((HealthLicenceJob) event.getObject());
			dataAccessService.updateObject(code);
			hlthLicJobsList = dataAccessService.getAllHealthLicenceJobsList();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			Utils.updateUIComponent("form-lic:fines");
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

		}
	}

	public void deleteSection(HealthLicenceJob sec) {
		try {
			dataAccessService.deleteObject(sec);
			hlthLicJobsList = dataAccessService.getAllHealthLicenceJobsList();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

		}
	}

	public void onRowCancel(RowEditEvent event) {
		MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("cancel"));
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<HealthLicenceJob> getHlthLicJobsList() {
		return hlthLicJobsList;
	}

	public void setHlthLicJobsList(List<HealthLicenceJob> hlthLicJobsList) {
		this.hlthLicJobsList = hlthLicJobsList;
	}

	public HealthLicenceJob getAddJob() {
		return addJob;
	}

	public void setAddJob(HealthLicenceJob addJob) {
		this.addJob = addJob;
	}



}

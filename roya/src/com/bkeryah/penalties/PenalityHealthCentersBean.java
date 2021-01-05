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
import com.bkeryah.entities.HealthLicenceCenter;
import com.bkeryah.entities.PayMaster;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class PenalityHealthCentersBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<HealthLicenceCenter> hlthLicCenterList = new ArrayList<HealthLicenceCenter>();
	HealthLicenceCenter addHealth = new HealthLicenceCenter();

	@PostConstruct
	private void init() {
		hlthLicCenterList = dataAccessService.getAllHealthLicenceCentersList();

	}

	public void addSection() {
		try {
			dataAccessService.save(addHealth);
			hlthLicCenterList = dataAccessService.getAllHealthLicenceCentersList();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

		}
	}

	public void onRowEdit(RowEditEvent event) {
		try {
			HealthLicenceCenter code = new HealthLicenceCenter();
			code = ((HealthLicenceCenter) event.getObject());
			dataAccessService.updateObject(code);
			hlthLicCenterList = dataAccessService.getAllHealthLicenceCentersList();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			Utils.updateUIComponent("form-lic:fines");
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

		}
	}

	public void deleteSection(HealthLicenceCenter sec) {
		try {
			dataAccessService.deleteObject(sec);
			hlthLicCenterList = dataAccessService.getAllHealthLicenceCentersList();
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

	public List<HealthLicenceCenter> getHlthLicCenterList() {
		return hlthLicCenterList;
	}

	public void setHlthLicCenterList(List<HealthLicenceCenter> hlthLicCenterList) {
		this.hlthLicCenterList = hlthLicCenterList;
	}

	public HealthLicenceCenter getAddHealth() {
		return addHealth;
	}

	public void setAddHealth(HealthLicenceCenter addHealth) {
		this.addHealth = addHealth;
	}

}

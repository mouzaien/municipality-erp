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
import com.bkeryah.entities.PayMaster;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class PenalitySectionsBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<LicSection> licSectionList = new ArrayList<LicSection>();
	LicSection addLicSection = new LicSection();

	@PostConstruct
	private void init() {
		licSectionList = dataAccessService.gatAllLicSectionList();

	}

	public void addSection() {
		try {
			dataAccessService.save(addLicSection);
			licSectionList = dataAccessService.gatAllLicSectionList();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

		}
	}

	public void onRowEdit(RowEditEvent event) {
		try {
			LicSection code = new LicSection();
			code = ((LicSection) event.getObject());
			dataAccessService.updateObject(code);
			licSectionList = dataAccessService.gatAllLicSectionList();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			Utils.updateUIComponent("form-lic:fines");
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

		}
	}

	public void deleteSection(LicSection sec) {
		try {
			dataAccessService.deleteObject(sec);
			licSectionList = dataAccessService.gatAllLicSectionList();
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


	public List<LicSection> getLicSectionList() {
		return licSectionList;
	}

	public void setLicSectionList(List<LicSection> licSectionList) {
		this.licSectionList = licSectionList;
	}

	public LicSection getAddLicSection() {
		return addLicSection;
	}

	public void setAddLicSection(LicSection addLicSection) {
		this.addLicSection = addLicSection;
	}
}

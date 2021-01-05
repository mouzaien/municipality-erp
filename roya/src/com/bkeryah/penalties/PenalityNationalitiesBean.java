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
import com.bkeryah.entities.SysNationality;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class PenalityNationalitiesBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<SysNationality> listNationality;
	SysNationality addNat = new SysNationality();

	@PostConstruct
	private void init() {
		listNationality = dataAccessService.loadNationalEmploye();

	}

	public void addSection() {
		try {
			dataAccessService.save(addNat);
			listNationality = dataAccessService.loadNationalEmploye();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

		}
	}

	public void onRowEdit(RowEditEvent event) {
		try {
			SysNationality code = new SysNationality();
			code = ((SysNationality) event.getObject());
			dataAccessService.updateObject(code);
			listNationality = dataAccessService.loadNationalEmploye();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			Utils.updateUIComponent("form-lic:fines");
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

		}
	}

	public void deleteSection(SysNationality sec) {
		try {
			dataAccessService.deleteObject(sec);
			listNationality = dataAccessService.loadNationalEmploye();
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

	public List<SysNationality> getListNationality() {
		return listNationality;
	}

	public void setListNationality(List<SysNationality> listNationality) {
		this.listNationality = listNationality;
	}

	public SysNationality getAddNat() {
		return addNat;
	}

	public void setAddNat(SysNationality addNat) {
		this.addNat = addNat;
	}



}

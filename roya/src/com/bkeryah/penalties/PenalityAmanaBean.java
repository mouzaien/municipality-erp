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
public class PenalityAmanaBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<Amana> amanaList = new ArrayList<Amana>();
	Amana addAmana = new Amana();

	@PostConstruct
	private void init() {
		amanaList = dataAccessService.getAllLicAmanaList();

	}

	public void addSection() {
		try {
			dataAccessService.save(addAmana);
			amanaList = dataAccessService.getAllLicAmanaList();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

		}
	}

	public void onRowEdit(RowEditEvent event) {
		try {
			Amana code = new Amana();
			code = ((Amana) event.getObject());
			dataAccessService.updateObject(code);
			amanaList = dataAccessService.getAllLicAmanaList();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			Utils.updateUIComponent("form-lic:fines");
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

		}
	}

	public void deleteSection(Amana sec) {
		try {
			dataAccessService.deleteObject(sec);
			amanaList = dataAccessService.getAllLicAmanaList();
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



	public Amana getAddAmana() {
		return addAmana;
	}

	public void setAddAmana(Amana addAmana) {
		this.addAmana = addAmana;
	}

	public List<Amana> getAmanaList() {
		return amanaList;
	}

	public void setAmanaList(List<Amana> amanaList) {
		this.amanaList = amanaList;
	}
}

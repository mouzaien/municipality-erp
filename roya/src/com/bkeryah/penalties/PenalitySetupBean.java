package com.bkeryah.penalties;

import java.util.ArrayList;
import java.util.List;

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
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class PenalitySetupBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<ReqFinesSetup> codesFines;
	private List<ReqFinesSetup> codesFineSetup;
	private List<ReqFinesSetup> codesFinesList = new ArrayList<ReqFinesSetup>();

	@PostConstruct
	private void init() {
		codesFines = dataAccessService.getCodesFines();
		ReqFinesSetup CodesFiner = new ReqFinesSetup();
		codesFinesList.add(CodesFiner);
	}

	public void addRowPenality(ActionEvent actionEvent) {
		ReqFinesSetup codesFine = new ReqFinesSetup();
		codesFines.add(codesFine);
	}

	public void onRowEdit(RowEditEvent event) {
		ReqFinesSetup code = new ReqFinesSetup();
		code = ((ReqFinesSetup) event.getObject());
		dataAccessService.save(code);
		MsgEntry.addInfoMessage(MsgEntry.SUCCESS_SAVE);
	}

	public void onRowCancel(RowEditEvent event) {
		dataAccessService.deleteObject(event.getObject());
		codesFines.remove(event.getObject());
		MsgEntry.addInfoMessage(MsgEntry.SUCCESS_DELETE);
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<ReqFinesSetup> getCodesFinesList() {
		return codesFinesList;
	}

	public void setCodesFinesList(List<ReqFinesSetup> codesFinesList) {
		this.codesFinesList = codesFinesList;
	}

	public List<ReqFinesSetup> getCodesFineSetup() {
		return codesFineSetup;
	}

	public void setCodesFineSetup(List<ReqFinesSetup> codesFineSetup) {
		this.codesFineSetup = codesFineSetup;
	}

	public List<ReqFinesSetup> getCodesFines() {
		return codesFines;
	}

	public void setCodesFines(List<ReqFinesSetup> codesFines) {
		this.codesFines = codesFines;
	}
}

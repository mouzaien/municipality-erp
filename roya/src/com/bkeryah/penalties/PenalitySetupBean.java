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
public class PenalitySetupBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<ReqFinesSetup> codesFines;
	private List<ReqFinesSetup> codesFineSetup;
	private List<ReqFinesSetup> codesFinesList = new ArrayList<ReqFinesSetup>();
	private List<PayMaster> itemsList = new ArrayList<PayMaster>();;
	private ReqFinesSetup finesetup = new ReqFinesSetup();

	@PostConstruct
	private void init() {
		codesFines = dataAccessService.getCodesFines();
		ReqFinesSetup CodesFiner = new ReqFinesSetup();
		codesFinesList.add(CodesFiner);
		itemsList = dataAccessService.loadAllPayMasters();
		
	}

	public String printPenalityReport() {
		try {
			String reportName = "/reports/penalitiesListtest.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			Utils.printPdfReport(reportName, parameters);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return "";
	}
	public void addPenality() {
		try {
			dataAccessService.save(finesetup);
			codesFines = dataAccessService.getCodesFines();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

		}
	}

	public void onRowEdit(RowEditEvent event) {
		try {
			ReqFinesSetup code = new ReqFinesSetup();
			code = ((ReqFinesSetup) event.getObject());
			dataAccessService.updateObject(code);
			codesFines = dataAccessService.getCodesFines();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			Utils.updateUIComponent("includeform:fines");
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

	public ReqFinesSetup getFinesetup() {
		return finesetup;
	}

	public void setFinesetup(ReqFinesSetup finesetup) {
		this.finesetup = finesetup;
	}

	public List<PayMaster> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<PayMaster> itemsList) {
		this.itemsList = itemsList;
	}
}

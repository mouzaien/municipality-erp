package com.bkeryah.penalties;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.RowEditEvent;

import com.bkeryah.bean.InventoryModel;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class PenaltiesBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<WrkFinesEntity> wrkFines;
	private List<WrkFinesEntity> filteredWrkFines;
	private WrkFinesEntity wrkFiner = new WrkFinesEntity();

	public WrkFinesEntity getWrkFiner() {
		return wrkFiner;
	}

	public void setWrkFiner(WrkFinesEntity wrkFiner) {
		this.wrkFiner = wrkFiner;
	}

	@PostConstruct
	public void init() {
		wrkFines = dataAccessService.getAllWrkFinesEntity();
	}

	public void addpenalty() {
		try {
			wrkFiner.setFnSentStatus(0);
			dataAccessService.save(wrkFiner);
			wrkFines = dataAccessService.getAllWrkFinesEntity();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.getStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

		}

	}

	public void sendFinerToAmana(ActionEvent event) {
		dataAccessService.save(wrkFiner);
		wrkFines = dataAccessService.getAllWrkFinesEntity();

	}

	public void onRowEdit(RowEditEvent event) {
		try {
			wrkFiner = ((WrkFinesEntity) event.getObject());
			dataAccessService.updateObject(wrkFiner);
			wrkFines = dataAccessService.getAllWrkFinesEntity();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.getStackTrace();
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

	public List<WrkFinesEntity> getWrkFines() {
		return wrkFines;
	}

	public void setWrkFines(List<WrkFinesEntity> wrkFines) {
		this.wrkFines = wrkFines;
	}

	public List<WrkFinesEntity> getFilteredWrkFines() {
		return filteredWrkFines;
	}

	public void setFilteredWrkFines(List<WrkFinesEntity> filteredWrkFines) {
		this.filteredWrkFines = filteredWrkFines;
	}
}

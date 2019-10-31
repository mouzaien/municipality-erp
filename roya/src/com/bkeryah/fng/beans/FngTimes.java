package com.bkeryah.fng.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.RowEditEvent;

import com.bkeryah.fng.entities.FngTimeTable;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;


@ManagedBean  (name = "fngTimes" )
@ViewScoped
public class FngTimes{
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<FngTimeTable> timeTable ;
	private List<FngTimeTable> filterTimeTable;
	private FngTimeTable shift;

	@PostConstruct
	public void init() {
		timeTable = dataAccessService.loadTimeShift();

	}
	public void addRowVacation(ActionEvent actionEvent) {
		FngTimeTable timeTablesObject = new FngTimeTable();
		timeTable.add(timeTablesObject);
	}
	public void onRowEdit(RowEditEvent event) {

		if (((FngTimeTable) event.getObject()).getTimeShiftId() == null){
			dataAccessService.saveObject((FngTimeTable) event.getObject());
			MsgEntry.addInfoMessage(MsgEntry.SUCCESS_SAVE_TIMETABLESHIFT);

		}
		else{

			dataAccessService.updateObject((FngTimeTable) event.getObject());
			MsgEntry.addInfoMessage(MsgEntry.SUCCESS_UPDATE_TIMETABLESHIFT);
		}
		
	}

	public void onRowCancel(RowEditEvent event) {

	}
	public void addShift(){
		 shift = new FngTimeTable();
	}
	public void saveFngTimeTable(){
		try{
			dataAccessService.save(shift);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}
	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<FngTimeTable> getTimeTable() {
		return timeTable;
	}

	public void setTimeTable(List<FngTimeTable> timeTable) {
		this.timeTable = timeTable;
	}
	public List<FngTimeTable> getFilterTimeTable() {
		return filterTimeTable;
	}
	public void setFilterTimeTable(List<FngTimeTable> filterTimeTable) {
		this.filterTimeTable = filterTimeTable;
	}
	public FngTimeTable getShift() {
		return shift;
	}
	public void setShift(FngTimeTable shift) {
		this.shift = shift;
	}



}

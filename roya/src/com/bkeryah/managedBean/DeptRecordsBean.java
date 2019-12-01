package com.bkeryah.managedBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.entities.ArcRecords;
import com.bkeryah.entities.DeptArcRecords;
import com.bkeryah.entities.RecDepts;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class DeptRecordsBean {

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	@ManagedProperty(value = "#{loadingDataBean}")
	private LoadingDataBean loadingDataBean;
	private Integer deptId;
	private List<ArcRecords> recordsList;
	private List<DeptArcRecords> deptRecords = new ArrayList<DeptArcRecords>();
	private List<ArcRecords> selectedRecordsList;
	private List<ArcRecords> filteredRecordsList;
	private List<DeptArcRecords> selectedRecords;
	private List<DeptArcRecords> filteredRecords;

	private List<RecDepts> recDeptsList;
	private Integer mode = 0;
	private boolean canPrint;

	@PostConstruct
	public void init() {
		deptId = Utils.findCurrentUser().getDeptId();
		recDeptsList = dataAccessService.getAllRecDepts();
		loadDeptRecs();
	}

	public void loadDeptRecs() {
		if (!deptRecords.equals(null) && deptRecords.size() > 0)
			deptRecords.clear();
		deptRecords = dataAccessService.loadDeptRecords(deptId, mode);
	}

	public void onRowEdit(RowEditEvent event) {
		((DeptArcRecords) event.getObject()).setModified("red");
		// FacesContext context = FacesContext.getCurrentInstance();
		onDeptChange();

	}

	public void onRadioChange() {
		loadDeptRecs();
		onDeptChange();
	}

	public void onDeptChange() {
		if (mode.equals(1) && deptRecords.size() > 0) {
			canPrint = true;
		} else {
			if (selectedRecords == null || selectedRecords.size() == 0)
				canPrint = false;
			else {
				canPrint = true;
				for (DeptArcRecords arcRecord : selectedRecords) {
					if (arcRecord.getReceiverDeptId().equals(0))
						canPrint = false;
				}

			}
		}

	}

	/**
	 * Consult selected mail
	 * 
	 * @param selectedOutBoxMail
	 */
	public void viewDetails(DeptArcRecords deptRecords) {
		UserMailObj selectedInbox = new UserMailObj();
		selectedInbox.setAppId(deptRecords.getArcId().toString());
		selectedInbox.setWrkId(deptRecords.getWrkId().toString());
		selectedInbox.setStepId(deptRecords.getStepId());
		selectedInbox.setHasComment(0);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedOutBoxMail", selectedInbox);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("models/outBox_definition.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onRowCancel(RowEditEvent event) {
		onDeptChange();

	}

	/**
	 * save receiver deparment and print
	 * 
	 * @return
	 */
	public String saveAndPrint() {
		if ((selectedRecords == null) || (selectedRecords.isEmpty())) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.select.records"));
			return "";
		}
		for (DeptArcRecords records : selectedRecords) {
			if (records.getReceiverDeptId() == 0) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
				return "";
			}
			String DeptName = dataAccessService.findReciverNameById(records.getReceiverDeptId());
			records.setReceiverDepName(DeptName);
		}
		if (mode == 0)
			savePrintedDepartmentRecords();
		printModelReport();
		loadDeptRecs();
		return "";
	}

	/**
	 * save records printed of department
	 */
	private void savePrintedDepartmentRecords() {
		dataAccessService.saveArcRecordsList(selectedRecords);
	}

	/**
	 * print report of empty record list
	 * 
	 * @return
	 */
	private String printModelReport() {
		String reportName = "/reports/empty_records_list.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("now", HijriCalendarUtil.findCurrentHijriWithTimeStamp());
		Utils.printPdfReportFromListDataSource(reportName, parameters,
				((selectedRecords == null) || (selectedRecords.isEmpty())) ? deptRecords : selectedRecords);
		return "";
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public List<ArcRecords> getRecordsList() {
		return recordsList;
	}

	public void setRecordsList(List<ArcRecords> recordsList) {
		this.recordsList = recordsList;
	}

	public List<ArcRecords> getFilteredRecordsList() {
		return filteredRecordsList;
	}

	public void setFilteredRecordsList(List<ArcRecords> filteredRecordsList) {
		this.filteredRecordsList = filteredRecordsList;
	}

	public List<ArcRecords> getSelectedRecordsList() {
		return selectedRecordsList;
	}

	public void setSelectedRecordsList(List<ArcRecords> selectedRecordsList) {
		this.selectedRecordsList = selectedRecordsList;
		onDeptChange();
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public List<RecDepts> getRecDeptsList() {
		return recDeptsList;
	}

	public void setRecDeptsList(List<RecDepts> recDeptsList) {
		this.recDeptsList = recDeptsList;
	}

	public LoadingDataBean getLoadingDataBean() {
		return loadingDataBean;
	}

	public void setLoadingDataBean(LoadingDataBean loadingDataBean) {
		this.loadingDataBean = loadingDataBean;
	}

	public boolean isCanPrint() {
		return canPrint;
	}

	public void setCanPrint(boolean canPrint) {
		this.canPrint = canPrint;
	}

	public List<DeptArcRecords> getSelectedRecords() {
		return selectedRecords;
	}

	public void setSelectedRecords(List<DeptArcRecords> selectedRecords) {
		this.selectedRecords = selectedRecords;
	}

	public List<DeptArcRecords> getFilteredRecords() {
		return filteredRecords;
	}

	public void setFilteredRecords(List<DeptArcRecords> filteredRecords) {
		this.filteredRecords = filteredRecords;
	}

	public List<DeptArcRecords> getDeptRecords() {
		return deptRecords;
	}

	public void setDeptRecords(List<DeptArcRecords> deptRecords) {
		this.deptRecords = deptRecords;
	}

}

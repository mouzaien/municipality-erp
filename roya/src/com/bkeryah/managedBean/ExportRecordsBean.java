/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import com.bkeryah.bean.ArcRecordClass;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

/**
 *
 * @author darwiesh
 */
@ManagedBean
@RequestScoped
public class ExportRecordsBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	DataAccess da = new DataAccessImpl();
	@ManagedProperty(value="#{dataAccessService}")
	private IDataAccessService dataAccessService;
    Map<String, String> ReturnMap = new LinkedHashMap<String, String>();
    private int selectedIncomeNo;
    private ArcRecordClass arcRecordClass = new ArcRecordClass();
    private String reportServerIp;
    private String Wrk_ID_NUMBER;
    private String barcodeLink;
    private boolean recordHasComment;

    public boolean isRecordHasComment() {
        return recordHasComment;
    }

    public void setRecordHasComment(boolean recordHasComment) {
        this.recordHasComment = recordHasComment;
    }

    public String getBarcodeLink() {
        return barcodeLink;
    }

    public void setBarcodeLink(String barcodeLink) {
        this.barcodeLink = barcodeLink;
    }

    public String getWrk_ID_NUMBER() {
        return Wrk_ID_NUMBER;
    }

    public void setWrk_ID_NUMBER(String Wrk_ID_NUMBER) {
        this.Wrk_ID_NUMBER = Wrk_ID_NUMBER;
    }

    public String getReportServerIp() {
        return this.dataAccessService.findSystemProperty("REPORT_SERVER_IP");
    }

    public void setReportServerIp(String reportServerIp) {
        this.reportServerIp = reportServerIp;
    }

    public ArcRecordClass getArcRecordClass() {
        return arcRecordClass;
    }

    public void setArcRecordClass(ArcRecordClass arcRecordClass) {
        this.arcRecordClass = arcRecordClass;
    }

    public int getSelectedIncomeNo() {
        return selectedIncomeNo;
    }

    public void setSelectedIncomeNo(int selectedIncomeNo) {
        this.selectedIncomeNo = selectedIncomeNo;
    }

    public Map<String, String> getReturnMap() {
        return this.dataAccessService.findrecordsMap();
    }

    public void setReturnMap(Map<String, String> ReturnMap) {
        this.ReturnMap = ReturnMap;
    }

    /**
     * Creates a new instance of exportRecordsBean
     */
    public ExportRecordsBean() {
        this.Wrk_ID_NUMBER = "#";
    }

    public void showRecordDetailsBtnAction(ActionEvent actionEvent) {
        this.arcRecordClass = this.dataAccessService.getRecordbyIcomeNUmber(this.selectedIncomeNo + "");
        this.arcRecordClass.setRecHDate(Utils.convertDateToArabic(this.arcRecordClass.getRecHDate()));
        this.Wrk_ID_NUMBER = this.arcRecordClass.getLastRecord().getWrkApplicationId();
        this.Wrk_ID_NUMBER = "http://" + this.getReportServerIp() + "/reports/rwservlet?report&report=d:\\archiving\\reports\\COMMENT2.rdf&p_1=" + this.arcRecordClass.getLastRecord().getWrkApplicationId();
        this.barcodeLink = "http://" + this.getReportServerIp() + "/reports/rwservlet?report&report=d:\\archiving\\reports\\barcode.rdf&p_1=" + this.arcRecordClass.getArcId();
        if (this.arcRecordClass.getLastRecord().isHasComment().equalsIgnoreCase("Y")) {
            this.recordHasComment = true;
        } else {
            this.recordHasComment = false;
        }
    }

    public void exportRecordBtnAction(ActionEvent actionEvent) {
        MsgEntry.showModalDialog(this.dataAccessService.addNewExportedRecord(this.selectedIncomeNo+"", Utils.findCurrentUser().getUserId()));
    }

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}
}

package com.bkeryah.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.bkeryah.entities.ArcRecords;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class PrintBarcode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer incomeNumber;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private boolean checkOutComing;

	public String printBarcodeReport() {
		ArcRecords arcRec = new ArcRecords();
		String reportName = "/reports/inbarcode.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("compName", dataAccessService.findSystemProperty("CUSTOMER_NAME"));

		if (checkOutComing) {
			arcRec = dataAccessService.findRecordByOutComeNo(incomeNumber);
			reportName = "/reports/outbarcode.jasper";
		} else
			arcRec = dataAccessService.findRecordByIncomeNumber(incomeNumber);
		if (arcRec == null)
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("data.error"));
		else {
			parameters.put("p1", arcRec.getId());
			Utils.printPdfReport(reportName, parameters);
		}
		return "";
	}

	public void checkAllListnerForManagers() {
		System.out.println(checkOutComing);
	}

	public Integer getIncomeNumber() {
		return incomeNumber;
	}

	public void setIncomeNumber(Integer incomeNumber) {
		this.incomeNumber = incomeNumber;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public boolean isCheckOutComing() {
		return checkOutComing;
	}

	public void setCheckOutComing(boolean checkOutComing) {
		this.checkOutComing = checkOutComing;
	}

}

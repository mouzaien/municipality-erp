package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.*;

import com.bkeryah.entities.FinFinancialYear;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class FiscalYearBean {
	private static Logger logger = LogManager.getLogger(ItemUniteBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private FinFinancialYear year = new FinFinancialYear();
	private List<FinFinancialYear> yearList = new ArrayList<FinFinancialYear>();
	private String yearName;
	private String startDateHij;
	private String endDateHij;
	private int yearStatus;

	private List<FinFinancialYear> filteredyearList;

	@PostConstruct
	public void init() {
		yearList = dataAccessService.findAllYear();
	}

	// add الاضافة
	public void saveYear() {
		try {
			if (year != null) {
				year.setYearName(yearName);
				year.setStartDateHij(startDateHij);
				year.setEndDateHij(endDateHij);
				year.setYearStatus(yearStatus);
				dataAccessService.save(year);
				yearList = dataAccessService.findAllYear();
			}
			MsgEntry.addInfoMessage("تمت الإضافة");

		} catch (Exception e) {
			MsgEntry.addErrorMessage("خطأ: لم تتم الإضافة");
			e.printStackTrace();

		}

	}

	// Edit تعديل
	public void updateYear() {
		try {
			if (year != null) {
				dataAccessService.updateObject(year);
				year = new FinFinancialYear();
				// logger.info("Update User: id: " + unite.getId());
			}
			MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ العملية");
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	// حــــــــــــــذف
	public void deleteYear() {
		try {
			if (year != null) {
				dataAccessService.deleteObject(year);
			}
			MsgEntry.addInfoMessage("تم الحذف");
		} catch (Exception e) {
			MsgEntry.addErrorMessage("لم يتم الحذف");
		}
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<FinFinancialYear> getYearList() {
		return yearList;
	}

	public void setYearList(List<FinFinancialYear> yearList) {
		this.yearList = yearList;
	}

	public String getYearName() {
		return yearName;
	}

	public void setYearName(String yearName) {
		this.yearName = yearName;
	}

	public String getStartDateHij() {
		return startDateHij;
	}

	public void setStartDateHij(String startDateHij) {
		this.startDateHij = startDateHij;
	}

	public String getEndDateHij() {
		return endDateHij;
	}

	public void setEndDateHij(String endDateHij) {
		this.endDateHij = endDateHij;
	}

	public List<FinFinancialYear> getFilteredyearList() {
		return filteredyearList;
	}

	public void setFilteredyearList(List<FinFinancialYear> filteredyearList) {
		this.filteredyearList = filteredyearList;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		FiscalYearBean.logger = logger;
	}

	public FinFinancialYear getYear() {
		return year;
	}

	public void setYear(FinFinancialYear year) {
		this.year = year;
	}

	public int getYearStatus() {
		return yearStatus;
	}

	public void setYearStatus(int yearStatus) {
		this.yearStatus = yearStatus;
	}
}

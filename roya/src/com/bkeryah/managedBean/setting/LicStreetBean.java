package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.entities.LicCities;
import com.bkeryah.entities.LicStreet;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class LicStreetBean {
	private static Logger logger = Logger.getLogger(SectionsBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private LicStreet street = new LicStreet();
	private List<LicStreet> streetList = new ArrayList<LicStreet>();
	private List<LicStreet> filteredstreetList;
	private String title;

	@PostConstruct
	public void init() {
		streetList = dataAccessService.findAllStreet();
	}

	// Edit تعديل
	public void updateStreet() {
		try {
			if (street != null) {
				dataAccessService.updateObject(street);
				street = new LicStreet();
				logger.info("Update User: id: " + street.getId());
			}
			MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ العملية");
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	// حــــــــــــــذف
	public void deleteStreet() {
		try {
			if (street != null) {
				dataAccessService.deleteObject(street);
			}
			MsgEntry.addInfoMessage("تم الحذف");
		} catch (Exception e) {

			MsgEntry.addErrorMessage("لم يتم الحذف");
		}
	}

	// add الاضافة
	public void saveStreet() {
		try {
			if (street != null) {
				street = new LicStreet();
				street.setTitle(title);
				dataAccessService.save(street);
				streetList = dataAccessService.findAllStreet();
			}
			MsgEntry.addInfoMessage("تم الإضافة");

		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		LicStreetBean.logger = logger;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public LicStreet getStreet() {
		return street;
	}

	public void setStreet(LicStreet street) {
		this.street = street;
	}

	public List<LicStreet> getStreetList() {
		return streetList;
	}

	public void setStreetList(List<LicStreet> streetList) {
		this.streetList = streetList;
	}

	public List<LicStreet> getFilteredstreetList() {
		return filteredstreetList;
	}

	public void setFilteredstreetList(List<LicStreet> filteredstreetList) {
		this.filteredstreetList = filteredstreetList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}

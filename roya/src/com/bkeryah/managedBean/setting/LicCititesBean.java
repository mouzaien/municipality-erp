package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.entities.LicCities;
import com.bkeryah.fuel.entities.CarBrand;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class LicCititesBean {
	private static Logger logger = Logger.getLogger(SectionsBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private LicCities cities = new LicCities();
	private List<LicCities> citiesList = new ArrayList<LicCities>();
	private List<LicCities> filteredcitiesList;
	private String name;

	@PostConstruct
	public void init() {
		citiesList = dataAccessService.findAllCities();
	}

	// حــــــــــــــذف
	public void deleteCities() {
		try {
			if (cities != null) {
				dataAccessService.deleteObject(cities);
			}
			MsgEntry.addInfoMessage("تم الحذف");
		} catch (Exception e) {

			MsgEntry.addErrorMessage("لم يتم الحذف");
		}
	}


	// add الاضافة
	public void saveCities() {
		try {
			if (cities != null) {
				cities = new LicCities();
				cities.setName(name);
				dataAccessService.save(cities);
				citiesList = dataAccessService.findAllCities();
			}
			MsgEntry.addInfoMessage("تم الإضافة");

		} catch (Exception e) {
			MsgEntry.addErrorMessage("خطأ:- لم تتم الأضـافة");
		}

	}

	// Edit تعديل
	public void updateCities() {
		try {
			if (cities != null) {
				dataAccessService.updateObject(cities);
				cities = new LicCities();
				logger.info("Update User: id: " + cities.getId());
			}
			MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ العملية");
		} catch (Exception e) {
			MsgEntry.addErrorMessage("لم يتم التعديل");
		}

	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		LicCititesBean.logger = logger;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public LicCities getCities() {
		return cities;
	}

	public void setCities(LicCities cities) {
		this.cities = cities;
	}

	public List<LicCities> getCitiesList() {
		return citiesList;
	}

	public void setCitiesList(List<LicCities> citiesList) {
		this.citiesList = citiesList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LicCities> getFilteredcitiesList() {
		return filteredcitiesList;
	}

	public void setFilteredcitiesList(List<LicCities> filteredcitiesList) {
		this.filteredcitiesList = filteredcitiesList;
	}
}

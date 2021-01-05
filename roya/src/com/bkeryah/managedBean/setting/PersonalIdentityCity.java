package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.entities.NationalIdPlaces;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class PersonalIdentityCity {

	private static Logger logger = Logger.getLogger(SectionsBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private NationalIdPlaces idCity = new NationalIdPlaces();
	private List<NationalIdPlaces> personIdCityList = new ArrayList<NationalIdPlaces>();
	private List<NationalIdPlaces> filterPersonIdCityList = new ArrayList<NationalIdPlaces>();
	private String cityName = new String();

	@PostConstruct
	public void init() {
		personIdCityList = dataAccessService.getallNationalIdPlaces();
	}

	// Edit تعديل
	public void updateIdCity() {
		try {
			if (idCity != null) {
				idCity.setPlaceName(cityName);
				dataAccessService.updateObject(idCity);
				idCity = new NationalIdPlaces();
				logger.info("Update User: id: " + idCity.getPlaceId());
				personIdCityList = dataAccessService.getallNationalIdPlaces();
				cityName = new String();
			}
			MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ العملية");
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	// حــــــــــــــذف
	public void deleteIdCity() {
		try {
			if (idCity != null) {
				dataAccessService.deleteObject(idCity);
				personIdCityList = dataAccessService.getallNationalIdPlaces();
			}
			MsgEntry.addInfoMessage("تم الحذف");
		} catch (Exception e) {

			MsgEntry.addErrorMessage("لم يتم الحذف");
		}
	}

	// add الاضافة
	public void saveIdCity() {
		try {
			if (idCity != null) {
				// idCity.setTypeName(typeName);
				dataAccessService.save(idCity);
				personIdCityList = dataAccessService.getallNationalIdPlaces();
				cityName = new String();
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
		PersonalIdentityCity.logger = logger;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public NationalIdPlaces getIdCity() {
		return idCity;
	}

	public void setIdCity(NationalIdPlaces idCity) {
		this.idCity = idCity;
	}

	public List<NationalIdPlaces> getPersonIdCityList() {
		return personIdCityList;
	}

	public void setPersonIdCityList(List<NationalIdPlaces> personIdCityList) {
		this.personIdCityList = personIdCityList;
	}

	public List<NationalIdPlaces> getFilterPersonIdCityList() {
		return filterPersonIdCityList;
	}

	public void setFilterPersonIdCityList(List<NationalIdPlaces> filterPersonIdCityList) {
		this.filterPersonIdCityList = filterPersonIdCityList;
	}

	public String getCityName() {
		if (idCity.getPlaceName() != null)
			cityName = idCity.getPlaceName();
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}

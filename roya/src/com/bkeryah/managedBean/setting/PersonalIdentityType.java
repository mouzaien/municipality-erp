package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.entities.LicStreet;
import com.bkeryah.entities.NationalIdType;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class PersonalIdentityType {

	private static Logger logger = Logger.getLogger(SectionsBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private NationalIdType idType = new NationalIdType();
	private List<NationalIdType> personIdTypeList = new ArrayList<NationalIdType>();
	private List<NationalIdType> filterPersonIdTypeList = new ArrayList<NationalIdType>();
	private String typeName = new String();

	@PostConstruct
	public void init() {
		personIdTypeList = dataAccessService.getAllNationalIdTypes();
	}

	// Edit تعديل
	public void updateIdType() {
		try {
			if (idType != null) {
				idType.setTypeName(typeName);
				dataAccessService.updateObject(idType);
				idType = new NationalIdType();
				logger.info("Update User: id: " + idType.getTypeId());
				personIdTypeList = dataAccessService.getAllNationalIdTypes();
				typeName = new String();
			}
			MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ العملية");
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	// حــــــــــــــذف
	public void deleteIdType() {
		try {
			if (idType != null) {
				dataAccessService.deleteObject(idType);
				personIdTypeList = dataAccessService.getAllNationalIdTypes();
			}
			MsgEntry.addInfoMessage("تم الحذف");
		} catch (Exception e) {

			MsgEntry.addErrorMessage("لم يتم الحذف");
		}
	}

	// add الاضافة
	public void saveIdType() {
		try {
			if (idType != null) {
				// idType.setTypeName(typeName);
				dataAccessService.save(idType);
				personIdTypeList = dataAccessService.getAllNationalIdTypes();
				typeName = new String();
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
		PersonalIdentityType.logger = logger;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public NationalIdType getIdType() {
		return idType;
	}

	public void setIdType(NationalIdType idType) {
		this.idType = idType;
	}

	public List<NationalIdType> getPersonIdTypeList() {
		return personIdTypeList;
	}

	public void setPersonIdTypeList(List<NationalIdType> personIdTypeList) {
		this.personIdTypeList = personIdTypeList;
	}

	public List<NationalIdType> getFilterPersonIdTypeList() {
		return filterPersonIdTypeList;
	}

	public void setFilterPersonIdTypeList(List<NationalIdType> filterPersonIdTypeList) {
		this.filterPersonIdTypeList = filterPersonIdTypeList;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}

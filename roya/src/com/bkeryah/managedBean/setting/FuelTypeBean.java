package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.fuel.entities.FuelType;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class FuelTypeBean {
	private static Logger logger = Logger.getLogger(Departments.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private FuelType type = new FuelType();
	private List<FuelType> typeList = new ArrayList<FuelType>();
	private String name;
	private List<FuelType> filteredtypeList;

	@PostConstruct
	public void init() {
		typeList = dataAccessService.findAllFuelType();
	}

	// حــــــــــــــذف
	public void deleteType() {
		try {
			if (type != null) {
				dataAccessService.deleteObject(type);
			}
			MsgEntry.addInfoMessage("تم الحذف");
		} catch (Exception e) {

			MsgEntry.addErrorMessage("لم يتم الحذف");
		}
	}

	// add الاضافة
	public void saveType() {
		try {
			if (type != null) {
				type = new FuelType();
				type.setName(name);
				dataAccessService.save(type);
				typeList = dataAccessService.findAllFuelType();
			}
			MsgEntry.addInfoMessage("تم الإضافة");

		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	// Edit تعديل
	public void updateType() {
		try {
			if (type != null) {
				dataAccessService.updateObject(type);
				type = new FuelType();
				logger.info("Update User: id: " + type.getId());
			}
			MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ العملية");
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public FuelType getType() {
		return type;
	}

	public void setType(FuelType type) {
		this.type = type;
	}

	public List<FuelType> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<FuelType> typeList) {
		this.typeList = typeList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FuelType> getFilteredtypeList() {
		return filteredtypeList;
	}

	public void setFilteredtypeList(List<FuelType> filteredtypeList) {
		this.filteredtypeList = filteredtypeList;
	}

}
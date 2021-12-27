package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.*;

import com.bkeryah.fuel.entities.VehicleType;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class vehicleTypeBean {
	private static Logger logger = LogManager.getLogger(vehicleTypeBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private VehicleType type = new VehicleType();
	private List<VehicleType> typeList = new ArrayList<VehicleType>();
	private String name;
	private List<VehicleType> filteredtypeList;

	@PostConstruct
	public void init() {
		typeList = dataAccessService.findAllVehicleType();
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
				type = new VehicleType();
				type.setName(name);
				dataAccessService.save(type);
				typeList = dataAccessService.findAllVehicleType();
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
				type = new VehicleType();
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

	public VehicleType getType() {
		return type;
	}

	public void setType(VehicleType type) {
		this.type = type;
	}

	public List<VehicleType> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<VehicleType> typeList) {
		this.typeList = typeList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<VehicleType> getFilteredtypeList() {
		return filteredtypeList;
	}

	public void setFilteredtypeList(List<VehicleType> filteredtypeList) {
		this.filteredtypeList = filteredtypeList;
	}

}

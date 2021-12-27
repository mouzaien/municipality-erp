package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.*;

import com.bkeryah.entities.NationalIdType;
import com.bkeryah.entities.investment.BuildingType;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class BuildingTypeBeen {

	private static Logger logger = LogManager.getLogger(SectionsBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private BuildingType buildingType = new BuildingType();
	private List<BuildingType> buildingTypeList = new ArrayList<BuildingType>();
	private List<BuildingType> filterBuildingTypeList = new ArrayList<BuildingType>();
	private String typeName = new String();

	@PostConstruct
	public void init() {
		buildingTypeList = dataAccessService.loadAllBuildingType();
	}

	// Edit تعديل
	public void updateBuildingType() {
		try {
			if (buildingType != null) {
				buildingType.setName(typeName);
				dataAccessService.updateObject(buildingType);
				buildingType = new BuildingType();
				logger.info("Update User: id: " + buildingType.getId());
				buildingTypeList = dataAccessService.loadAllBuildingType();
				typeName = new String();
			}
			MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ العملية");
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	// حــــــــــــــذف
	public void deleteBuildingType() {
		try {
			if (buildingType != null) {
				dataAccessService.deleteObject(buildingType);
				buildingTypeList = dataAccessService.loadAllBuildingType();
			}
			MsgEntry.addInfoMessage("تم الحذف");
		} catch (Exception e) {

			MsgEntry.addErrorMessage("لم يتم الحذف");
		}
	}

	// add الاضافة
	public void saveBuildingType() {
		try {
			if (buildingType != null) {
				// idType.setTypeName(typeName);
				dataAccessService.save(buildingType);
				buildingTypeList = dataAccessService.loadAllBuildingType();
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
		BuildingTypeBeen.logger = logger;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public BuildingType getBuildingType() {
		return buildingType;
	}

	public void setBuildingType(BuildingType buildingType) {
		this.buildingType = buildingType;
	}

	public List<BuildingType> getBuildingTypeList() {
		return buildingTypeList;
	}

	public void setBuildingTypeList(List<BuildingType> buildingTypeList) {
		this.buildingTypeList = buildingTypeList;
	}

	public List<BuildingType> getFilterBuildingTypeList() {
		return filterBuildingTypeList;
	}

	public void setFilterBuildingTypeList(List<BuildingType> filterBuildingTypeList) {
		this.filterBuildingTypeList = filterBuildingTypeList;
	}

	public String getTypeName() {
		if (buildingType.getName() != null)
			typeName = buildingType.getName();
		return typeName;

	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}

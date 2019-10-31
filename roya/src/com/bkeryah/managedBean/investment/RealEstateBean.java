package com.bkeryah.managedBean.investment;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.entities.investment.BuildingType;
import com.bkeryah.entities.investment.RealEstate;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class RealEstateBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private Integer realEstateId;
	private List<RealEstate> realEstatesList;
	private List<RealEstate> filteredRealEstatesList;
	private BuildingType buildingType = new BuildingType();
	private List<BuildingType> buildingTypeList = new ArrayList<BuildingType>();
	private RealEstate realEstate = new RealEstate();
	private boolean addMode;
	private static final Logger logger = Logger.getLogger(RealEstateBean.class);

	@PostConstruct
	public void init() {
		realEstatesList = dataAccessService.loadAllRealEstates();
		buildingTypeList = dataAccessService.loadAllBuildingType();
	}

	public void addRealEstate() {
		realEstate = new RealEstate();
		addMode = true;
	}

	public void loadSelectedRealEstate() {
		addMode = false;
	}

	public void save() {
		try {
			dataAccessService.save(realEstate);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			realEstatesList.add(realEstate);
			realEstate = new RealEstate();
			logger.info("add realEstate: id: " + realEstate.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("add realEstate: id: " + realEstate.getId());
		}
	}

	public void update() {
		try {
			dataAccessService.updateObject(realEstate);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			realEstate = new RealEstate();
			logger.info("update realEstate: id: " + realEstate.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("update realEstate: id: " + realEstate.getId());
		}
	}

	public void deleteRealEstate() {
		try {
			dataAccessService.deleteObject(realEstate);
			realEstatesList.remove(realEstate);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			logger.info("delete realEstate: id: " + realEstate.getId());
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("delete realEstate: id: " + realEstate.getId());
		}
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public Integer getRealEstateId() {
		return realEstateId;
	}

	public void setRealEstateId(Integer realEstateId) {
		this.realEstateId = realEstateId;
	}

	public List<RealEstate> getRealEstatesList() {
		return realEstatesList;
	}

	public void setRealEstatesList(List<RealEstate> realEstatesList) {
		this.realEstatesList = realEstatesList;
	}

	public List<RealEstate> getFilteredRealEstatesList() {
		return filteredRealEstatesList;
	}

	public void setFilteredRealEstatesList(List<RealEstate> filteredRealEstatesList) {
		this.filteredRealEstatesList = filteredRealEstatesList;
	}

	public RealEstate getRealEstate() {
		return realEstate;
	}

	public void setRealEstate(RealEstate realEstate) {
		this.realEstate = realEstate;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
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

}

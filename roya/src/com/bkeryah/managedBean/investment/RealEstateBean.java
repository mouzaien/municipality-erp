package com.bkeryah.managedBean.investment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.bkeryah.entities.LicDistrict;
import com.bkeryah.entities.LicStreet;
import com.bkeryah.entities.investment.BuildingType;
import com.bkeryah.entities.investment.ContractComponents;
import com.bkeryah.entities.investment.ContractType;
import com.bkeryah.entities.investment.RealEstate;
import com.bkeryah.entities.investment.SiteType;
import com.bkeryah.fuel.entities.Car;
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
	private String activityName;
	private ContractType activityType = new ContractType();
	private List<ContractType> contractTypesList;
	private String siteName;
	private SiteType siteType = new SiteType();
	private List<SiteType> siteTypesList;
	private LicStreet street = new LicStreet();
	private String streetName;
	private String districtName;
	private List<LicStreet> streetList = new ArrayList<LicStreet>();
	private LicDistrict district = new LicDistrict();
	private List<LicDistrict> districtList = new ArrayList<LicDistrict>();
	private List<ContractComponents> contractComponentList;
	private List<ContractComponents> allContractComponentList;
	private ContractComponents contractComponent = new ContractComponents();
	private Integer activityIdFilter = -1;
	private Integer siteIdFilter = -1;
	private String componentsFilter = "-1";
	private String streetFilter = "-1";
	private Integer buildIdFilter = -1;
	private Integer districtFilter = -1;
	private String newComponetsName;
	private Integer addedactivityId;

	private static final Logger logger = Logger.getLogger(RealEstateBean.class);

	@PostConstruct
	public void init() {
		realEstatesList = dataAccessService.loadAllRealEstates();
		buildingTypeList = dataAccessService.loadAllBuildingType();
		contractTypesList = dataAccessService.loadAllContractTypes();
		allContractComponentList = dataAccessService.loadAllContractComponents();
		siteTypesList = dataAccessService.loadAllSiteTypes();
		streetList = dataAccessService.findAllStreet();
		districtList = dataAccessService.findAllDistrict();
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
//			realEstate.setComponents(contractComponent.getName());
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

	public String printAllRealEstateAction() {
		String reportName = "/reports/all_real_estate_report.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("activityId", activityIdFilter);
		parameters.put("siteId", siteIdFilter);
		parameters.put("components", componentsFilter);
		parameters.put("buildId", buildIdFilter);
		parameters.put("street", streetFilter);
		parameters.put("districtId", districtFilter);
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public void getContractComponentsListById() {

		if (realEstate.getActivityTypeId() != null)
			contractComponent = dataAccessService
					.getContractComponentsById(Integer.parseInt(realEstate.getActivityTypeId()));
		else {
			System.out.println("realEstate.getActivityTypeId() is NULL");
		}
	}

	public void loadDialogData(RealEstate realEstateItem) {
		if (realEstateItem.getActivityTypeId() != null)
			contractComponentList = dataAccessService
					.getContractComponentsListByActivityId(Integer.parseInt(realEstateItem.getActivityTypeId()));
		else {
			System.out.println("realEstateItem.getActivityTypeId() is NULL");
		}
	}

	public void onRowSelect(SelectEvent event) {
		realEstate = new RealEstate();
		addMode = false;
		realEstate = (RealEstate) event.getObject();
		loadDialogData(realEstate);
	}

	public void onRowUnselect(UnselectEvent event) {
		realEstate = (RealEstate) event.getObject();
		FacesMessage msg = new FacesMessage("Car Unselected", realEstate.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void getContractComponentsByActivityId() {

		contractComponentList = dataAccessService
				.getContractComponentsListByActivityId(Integer.parseInt(realEstate.getActivityTypeId()));
	}

	public void loadRealEstatesListUsingFiltter() {

		// realEstatesList =
		// dataAccessService.getRealEstatesListByActivityIdAndSiteId(activityId,
		// siteId);

		// if (componentsFilter.isEmpty() || componentsFilter == null ||
		// componentsFilter == "n")
		// componentsFilter = null;
		// if (streetFilter.isEmpty() || streetFilter == null || streetFilter ==
		// "n")
		// streetFilter = null;
		realEstatesList = dataAccessService.getRealEstatesListByAllfliters(activityIdFilter, siteIdFilter,
				componentsFilter, streetFilter, buildIdFilter, districtFilter);

	}

	public void saveActivity() {

		try {
			if (activityName != null) {
				activityType.setName(activityName);
				dataAccessService.save(activityType);
				contractTypesList = dataAccessService.loadAllContractTypes();
				activityName = new String();
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}

	public void saveComponents() {

		try {
			if (newComponetsName != null && addedactivityId != null && realEstate.getActivityTypeId() != null) {
				contractComponent.setName(newComponetsName);
				contractComponent.setContractTypeId(addedactivityId);
				dataAccessService.save(contractComponent);
				getContractComponentsByActivityId();
				newComponetsName = new String();
				addedactivityId = -1;
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			} else {
				MsgEntry.addErrorMessage(
						"تأكد من إدخال مكونات النشاط في شاشة إضافة مكونات نشاط واختيار نوع النشاط في شاشة أضف العقار ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

		}
	}

	public void saveSite() {

		try {
			if (siteName != null) {
				siteType.setName(siteName);
				dataAccessService.save(siteType);
				siteTypesList = dataAccessService.loadAllSiteTypes();
				siteName = new String();
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

		}
	}

	public void saveStreet() {

		try {
			if (streetName != null) {
				street.setTitle(streetName);
				dataAccessService.save(street);
				streetList = dataAccessService.findAllStreet();
				streetName = new String();
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

		}
	}

	public void saveDistict() {
		try {
			if (districtName != null) {
				district.setName(districtName);
				dataAccessService.save(district);
				districtList = dataAccessService.findAllDistrict();
				districtName = new String();
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
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

	public ContractType getActivityType() {
		return activityType;
	}

	public void setActivityType(ContractType activityType) {
		this.activityType = activityType;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public List<ContractType> getContractTypesList() {
		if ((contractTypesList == null) || (contractTypesList.isEmpty()))
			contractTypesList = dataAccessService.loadAllContractTypes();
		return contractTypesList;
	}

	public void setContractTypesList(List<ContractType> contractTypesList) {
		this.contractTypesList = contractTypesList;
	}

	public List<SiteType> getSiteTypesList() {
		if ((siteTypesList == null) || (siteTypesList.isEmpty()))
			siteTypesList = dataAccessService.loadAllSiteTypes();
		return siteTypesList;
	}

	public void setSiteTypesList(List<SiteType> siteTypesList) {
		this.siteTypesList = siteTypesList;
	}

	public SiteType getSiteType() {
		return siteType;
	}

	public void setSiteType(SiteType siteType) {
		this.siteType = siteType;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public LicStreet getStreet() {
		return street;
	}

	public void setStreet(LicStreet street) {
		this.street = street;
	}

	public List<LicStreet> getStreetList() {
		if ((streetList == null) || (streetList.isEmpty()))
			streetList = dataAccessService.findAllStreet();
		return streetList;
	}

	public void setStreetList(List<LicStreet> streetList) {
		this.streetList = streetList;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public LicDistrict getDistrict() {
		return district;
	}

	public void setDistrict(LicDistrict district) {
		this.district = district;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public List<LicDistrict> getDistrictList() {
		if ((streetList == null) || (streetList.isEmpty()))
			streetList = dataAccessService.findAllStreet();
		return districtList;
	}

	public void setDistrictList(List<LicDistrict> districtList) {
		this.districtList = districtList;
	}

	public List<ContractComponents> getContractComponentList() {
		return contractComponentList;
	}

	public void setContractComponentList(List<ContractComponents> contractComponentList) {
		contractComponentList = contractComponentList;
	}

	public ContractComponents getContractComponent() {
		return contractComponent;
	}

	public void setContractComponent(ContractComponents contractComponent) {
		this.contractComponent = contractComponent;
	}

	public Integer getActivityIdFilter() {
		return activityIdFilter;
	}

	public void setActivityIdFilter(Integer activityIdFilter) {
		this.activityIdFilter = activityIdFilter;
	}

	public Integer getSiteIdFilter() {
		return siteIdFilter;
	}

	public void setSiteIdFilter(Integer siteIdFilter) {
		this.siteIdFilter = siteIdFilter;
	}

	public String getComponentsFilter() {
		return componentsFilter;
	}

	public void setComponentsFilter(String componentsFilter) {
		this.componentsFilter = componentsFilter;
	}

	public String getStreetFilter() {
		return streetFilter;
	}

	public void setStreetFilter(String streetFilter) {
		this.streetFilter = streetFilter;
	}

	public Integer getBuildIdFilter() {
		return buildIdFilter;
	}

	public void setBuildIdFilter(Integer buildIdFilter) {
		this.buildIdFilter = buildIdFilter;
	}

	public Integer getDistrictFilter() {
		return districtFilter;
	}

	public void setDistrictFilter(Integer districtFilter) {
		this.districtFilter = districtFilter;
	}

	public List<ContractComponents> getAllContractComponentList() {
		return allContractComponentList;
	}

	public void setAllContractComponentList(List<ContractComponents> allContractComponentList) {
		this.allContractComponentList = allContractComponentList;
	}

	public String getNewComponetsName() {
		return newComponetsName;
	}

	public void setNewComponetsName(String newComponetsName) {
		this.newComponetsName = newComponetsName;
	}

	public Integer getAddedactivityId() {
		return addedactivityId;
	}

	public void setAddedactivityId(Integer addedactivityId) {
		this.addedactivityId = addedactivityId;
	}

}

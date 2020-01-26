package com.bkeryah.managedBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.springframework.util.CollectionUtils;

import com.bkeryah.bean.WrkLetterFromClass;
import com.bkeryah.entities.ArcPeopleModel;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.HrsMasterFile;
import com.bkeryah.entities.HrsSalaryScale;
import com.bkeryah.entities.MasterFile;
import com.bkeryah.entities.NationalIdPlaces;
import com.bkeryah.entities.NationalIdType;
import com.bkeryah.entities.Nationality;
import com.bkeryah.entities.SysTitle;
import com.bkeryah.entities.UserRoles;
import com.bkeryah.entities.WrkCommentType;
import com.bkeryah.entities.WrkDept;
import com.bkeryah.entities.WrkRoles;
import com.bkeryah.entities.investment.Clause;
import com.bkeryah.entities.investment.ContractCancelReason;
import com.bkeryah.entities.investment.ContractDirectType;
import com.bkeryah.entities.investment.ContractType;
import com.bkeryah.entities.investment.IntroContract;
import com.bkeryah.entities.investment.InvNewspaper;
import com.bkeryah.entities.investment.Investor;
import com.bkeryah.entities.investment.RealEstate;
import com.bkeryah.entities.investment.SiteType;
import com.bkeryah.entities.licences.BldLicBuildingUsage;
import com.bkeryah.entities.licences.BldPaperTypes;
import com.bkeryah.entities.licences.LicAgents;
import com.bkeryah.fuel.entities.CarBrand;
import com.bkeryah.fuel.entities.FuelType;
import com.bkeryah.fuel.entities.VehicleType;
import com.bkeryah.hr.entities.HrsLoanType;
import com.bkeryah.hr.entities.Sys012;
import com.bkeryah.hr.entities.Sys018;
import com.bkeryah.hr.entities.Sys037;
import com.bkeryah.hr.entities.Sys038;
import com.bkeryah.hr.entities.Sys051;
import com.bkeryah.hr.entities.Sys059;
import com.bkeryah.hr.entities.Sys112;
import com.bkeryah.model.User;
import com.bkeryah.penalties.LicTrdMasterFile;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.support.entities.RequestStatus;

import utilities.CodeLabel;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ApplicationScoped
public class LoadingDataBean {

	private List<CodeLabel> billCategoriesList;
	private List<CodeLabel> hoursList;
	private Map<String, String> fontSizeList = new LinkedHashMap<>();
	private List<WrkCommentType> commentTypes;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<WrkDept> depatmentList;
	private Integer departmentId;
	private List<User> employersList;
	private List<UserRoles> userRoleList;
	private List<BldPaperTypes> buildingPaperTypeList;
	private List<BldLicBuildingUsage> buildingUsageList;
	private List<LicAgents> engineeringOfficeList;
	private List<ArcPeopleModel> arcPeoples;
	private List<Sys037> jobCategorieslist;
	private List<HrsSalaryScale> jobRankslist;
	private List<Sys112> departmentslist;
	private List<Sys038> jobStatuslist;
	private List<HrsLoanType> loanTypeslist;
	private List<Sys012> monthsList;
	private List<SysTitle> titlesList;
	private List<WrkRoles> wrkRolesList;
	private List<NationalIdType> nationalityTypeList;
	private List<Nationality> nationalityList;
	private List<NationalIdPlaces> nationalIdPlacesList;
	private List<HrsMasterFile> activeEmployersList;
	private List<Sys059> reasonsTerminateList;
	private List<Sys051> payTypesList;
	private List<Sys018> payStatusList;
	private Map<Integer, User> usersMap;
	private List<User> usersDeptList;
	private Map<Integer, MasterFile> employersMap;
	private Map<Integer, RequestStatus> statusMap;
	private List<RequestStatus> statusMapList;
	private List<InvNewspaper> newspapersList;
	private List<Investor> investorsList;
	private List<SiteType> siteTypesList;
	private List<ContractType> contractTypesList;
	private List<User> employersListInDept;
	private List<Clause> clausesList;
	private List<CarBrand> carBrandsList;
	private List<VehicleType> vehicleTypeList;
	private List<FuelType> fuelTypeList;
	private List<RealEstate> realEstateList;
	private List<IntroContract> introContractList;
	private List<ContractCancelReason> reasonsList;
	private List<ContractDirectType> contractDirectTypesList;
	private List<LicTrdMasterFile> licencesList;
	List<WrkLetterFromClass> listLetterFrom;

	public List<RequestStatus> getStatusMapList() {
		return statusMapList;
	}

	public void setStatusMapList(List<RequestStatus> statusMapList) {
		this.statusMapList = statusMapList;
	}

	@PostConstruct
	public void init() {
		loadReqsMap();
	}

	public Map<Integer, RequestStatus> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(Map<Integer, RequestStatus> statusMap) {
		this.statusMap = statusMap;
	}

	public List<ArcPeopleModel> getArcPeoples() {
		if (arcPeoples == null)
			arcPeoples = dataAccessService.loadArcPeoples();
		return arcPeoples;
	}

	public void setArcPeoples(List<ArcPeopleModel> arcPeoples) {
		this.arcPeoples = arcPeoples;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public List<User> getEmployersList() {
		if ((employersList == null) || (employersList.isEmpty())) {
			employersList = dataAccessService.getAllUsers();
			return employersList;
		}
		return employersList;
	}

	public void setEmployersList(List<User> employersList) {
		this.employersList = employersList;
	}

	public void loadEmployersByDept() {
		employersListInDept = dataAccessService.getAllEmployeesInDept(departmentId);
	}

	public List<CodeLabel> getHoursList() {
		if ((hoursList != null) && (!hoursList.isEmpty()))
			return hoursList;
		hoursList = new ArrayList<>();
		String time = (Utils.loadMessagesFromFile("day.night").split(","))[0];
		for (int i = 1; i <= 11; i++) {
			hoursList.add(new CodeLabel(String.format("%02d", i) + "", i + " " + time));
		}
		time = (Utils.loadMessagesFromFile("day.night").split(","))[1];
		hoursList.add(new CodeLabel("12", "12 " + time));
		time = (Utils.loadMessagesFromFile("day.night").split(","))[2];
		for (int i = 13; i <= 23; i++) {
			hoursList.add(new CodeLabel(String.format("%02d", i) + "", (i - 12) + " " + time));
		}
		hoursList.add(new CodeLabel("24", "12 " + (Utils.loadMessagesFromFile("day.night").split(","))[0]));
		return hoursList;
	}

	public void setHoursList(List<CodeLabel> hoursList) {
		this.hoursList = hoursList;
	}

	public List<CodeLabel> getBillCategoriesList() {
		if ((billCategoriesList != null) && (!billCategoriesList.isEmpty()))
			return billCategoriesList;
		billCategoriesList = new ArrayList<>();
		String[] categories = Utils.loadMessagesFromFile("bill.categories").split(",");
		for (int i = 1; i <= categories.length; i++) {
			billCategoriesList.add(new CodeLabel(i + "", categories[i - 1]));
		}
		return billCategoriesList;
	}

	public void setBillCategoriesList(List<CodeLabel> billCategoriesList) {
		this.billCategoriesList = billCategoriesList;
	}

	public Map<String, String> getFontSizeList() {
		if ((fontSizeList != null) && (!fontSizeList.isEmpty()))
			return fontSizeList;
		for (int i = 9; i <= 18; i++) {
			fontSizeList.put("" + i, "" + i);
		}
		return fontSizeList;
	}

	public void setFontSizeList(Map<String, String> fontSizeList) {
		this.fontSizeList = fontSizeList;
	}

	public List<WrkCommentType> getCommentTypes() {
		if ((commentTypes == null) || (commentTypes.isEmpty()))
			return dataAccessService.getCommentTypes();
		return commentTypes;
	}

	public void setCommentTypes(List<WrkCommentType> commentTypes) {
		this.commentTypes = commentTypes;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<WrkDept> getDepatmentList() {
		if ((depatmentList == null) || (depatmentList.isEmpty()))
			return dataAccessService.findAllDepartments();
		return depatmentList;
	}

	public void setDepatmentList(List<WrkDept> depatmentList) {
		this.depatmentList = depatmentList;
	}

	public List<UserRoles> getUserRoleList() {
		if ((userRoleList == null) || (userRoleList.isEmpty()))
			return dataAccessService.loadAllRoles();
		return userRoleList;
	}

	public void setUserRoleList(List<UserRoles> userRoleList) {
		this.userRoleList = userRoleList;
	}

	public List<BldPaperTypes> getBuildingPaperTypeList() {
		if ((buildingPaperTypeList == null) || (buildingPaperTypeList.isEmpty()))
			return dataAccessService.loadAllPaperTypes();
		return buildingPaperTypeList;
	}

	public void setBuildingPaperTypeList(List<BldPaperTypes> buildingPaperTypeList) {
		this.buildingPaperTypeList = buildingPaperTypeList;
	}

	public List<BldLicBuildingUsage> getBuildingUsageList() {
		if ((buildingUsageList == null) || (buildingUsageList.isEmpty()))
			return dataAccessService.loadAllBuildingUsages();
		return buildingUsageList;
	}

	public void setBuildingUsageList(List<BldLicBuildingUsage> buildingUsageList) {
		this.buildingUsageList = buildingUsageList;
	}

	public List<LicAgents> getEngineeringOfficeList() {
		if ((engineeringOfficeList == null) || (engineeringOfficeList.isEmpty()))
			return dataAccessService.loadAllEngineeringOffices();
		return engineeringOfficeList;
	}

	public void setEngineeringOfficeList(List<LicAgents> engineeringOfficeList) {
		this.engineeringOfficeList = engineeringOfficeList;
	}

	public List<Sys037> getJobCategorieslist() {
		if ((jobCategorieslist == null) || (jobCategorieslist.isEmpty()))
			return dataAccessService.loadAllJobCategories();
		return jobCategorieslist;
	}

	public void setJobCategorieslist(List<Sys037> jobCategorieslist) {
		this.jobCategorieslist = jobCategorieslist;
	}

	public List<HrsSalaryScale> getJobRankslist() {
		if ((jobRankslist == null) || (jobRankslist.isEmpty()))
			return dataAccessService.loadAllJobRanks();
		return jobRankslist;
	}

	public void setJobRankslist(List<HrsSalaryScale> jobRankslist) {
		this.jobRankslist = jobRankslist;
	}

	public List<Sys112> getDepartmentslist() {
		if ((departmentslist == null) || (departmentslist.isEmpty()))
			return dataAccessService.loadAllDepartments();
		return departmentslist;
	}

	public void setDepartmentslist(List<Sys112> departmentslist) {
		this.departmentslist = departmentslist;
	}

	public List<Sys038> getJobStatuslist() {
		if ((jobStatuslist == null) || (jobStatuslist.isEmpty()))
			return dataAccessService.loadAllJobStatus();
		return jobStatuslist;
	}

	public void setJobStatuslist(List<Sys038> jobStatuslist) {
		this.jobStatuslist = jobStatuslist;
	}

	public List<HrsLoanType> getLoanTypeslist() {
		if ((loanTypeslist == null) || (loanTypeslist.isEmpty()))
			return dataAccessService.loadAllLoanTypes();
		return loanTypeslist;
	}

	public void setLoanTypeslist(List<HrsLoanType> loanTypeslist) {
		this.loanTypeslist = loanTypeslist;
	}

	public List<Sys012> getMonthsList() {
		if ((monthsList == null) || (monthsList.isEmpty()))
			return dataAccessService.loadAllMonths();
		return monthsList;
	}

	public void setMonthsList(List<Sys012> monthsList) {
		this.monthsList = monthsList;
	}

	public List<SysTitle> getTitlesList() {
		if ((titlesList == null) || (titlesList.isEmpty()))
			return dataAccessService.loadAllTitles();
		return titlesList;
	}

	public void setTitlesList(List<SysTitle> titlesList) {
		this.titlesList = titlesList;
	}

	public List<WrkRoles> getWrkRolesList() {
		if ((wrkRolesList == null) || (wrkRolesList.isEmpty()))
			try {
				wrkRolesList = dataAccessService.getallRoles();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return wrkRolesList;
	}

	public void setWrkRolesList(List<WrkRoles> wrkRolesList) {
		this.wrkRolesList = wrkRolesList;
	}

	public List<NationalIdType> getNationalityTypeList() {
		if ((nationalityTypeList == null) || (nationalityTypeList.isEmpty()))
			nationalityTypeList = dataAccessService.getAllNationalIdTypes();
		return nationalityTypeList;
	}

	public void setNationalityTypeList(List<NationalIdType> nationalityTypeList) {
		this.nationalityTypeList = nationalityTypeList;
	}

	public List<Nationality> getNationalityList() {
		if ((nationalityList == null) || (nationalityList.isEmpty()))
			nationalityList = dataAccessService.getallNationalities();
		return nationalityList;
	}

	public void setNationalityList(List<Nationality> nationalityList) {
		this.nationalityList = nationalityList;
	}

	public List<NationalIdPlaces> getNationalIdPlacesList() {
		if ((nationalIdPlacesList == null) || (nationalIdPlacesList.isEmpty()))
			nationalIdPlacesList = dataAccessService.getallNationalIdPlaces();
		return nationalIdPlacesList;
	}

	public void setNationalIdPlacesList(List<NationalIdPlaces> nationalIdPlacesList) {
		this.nationalIdPlacesList = nationalIdPlacesList;
	}

	public List<HrsMasterFile> getActiveEmployersList() {
		if ((activeEmployersList == null) || (activeEmployersList.isEmpty()))
			activeEmployersList = dataAccessService.loadActiveEmpsHasSalary();
		return activeEmployersList;
	}

	public void setActiveEmployersList(List<HrsMasterFile> activeEmployersList) {
		this.activeEmployersList = activeEmployersList;
	}

	public List<Sys059> getReasonsTerminateList() {
		if ((reasonsTerminateList == null) || (reasonsTerminateList.isEmpty()))
			reasonsTerminateList = dataAccessService.loadAllTerminateReasons();
		return reasonsTerminateList;
	}

	public void setReasonsTerminateList(List<Sys059> reasonsTerminateList) {
		this.reasonsTerminateList = reasonsTerminateList;
	}

	public List<Sys051> getPayTypesList() {
		if ((payTypesList == null) || (payTypesList.isEmpty()))
			payTypesList = dataAccessService.loadAllPayTypes();
		return payTypesList;
	}

	public void setPayTypesList(List<Sys051> payTypesList) {
		this.payTypesList = payTypesList;
	}

	public List<Sys018> getPayStatusList() {
		if ((payStatusList == null) || (payStatusList.isEmpty()))
			payStatusList = dataAccessService.loadAllPayStatus();
		return payStatusList;
	}

	public void setPayStatusList(List<Sys018> payStatusList) {
		this.payStatusList = payStatusList;
	}

	public Map<Integer, User> getUsersMap() {
		if ((usersMap == null) || (usersMap.isEmpty()))
			usersMap = dataAccessService.loadUsers();
		return usersMap;
	}

	public void setUsersMap(Map<Integer, User> usersMap) {
		this.usersMap = usersMap;
	}

	public Map<Integer, MasterFile> getEmployersMap() {
		if ((employersMap == null) || (employersMap.isEmpty()))
			employersMap = dataAccessService.loadMasterFiles();
		return employersMap;
	}

	public void setEmployersMap(Map<Integer, MasterFile> employersMap) {
		this.employersMap = employersMap;
	}

	public List<InvNewspaper> getNewspapersList() {
		if ((newspapersList == null) || (newspapersList.isEmpty()))
			newspapersList = dataAccessService.loadNewspapers();
		return newspapersList;
	}

	public void setNewspapersList(List<InvNewspaper> newspapersList) {
		this.newspapersList = newspapersList;
	}

	public List<Investor> getInvestorsList() {
		// if ((investorsList == null) || (investorsList.isEmpty()))
		investorsList = dataAccessService.loadAllInvestors();
		return investorsList;
	}

	public void setInvestorsList(List<Investor> investorsList) {
		this.investorsList = investorsList;
	}

	public List<User> getUsersDeptList() {
		return usersDeptList;
	}

	public void setUsersDeptList(List<User> usersDeptList) {
		this.usersDeptList = usersDeptList;
	}

	public List<User> getUsersSectionMap(Integer secId) {
		ArcUsers currentUser = Utils.findCurrentUser();
		Map<Integer, User> users = usersMap;
		Integer currUserId = currentUser.getUserId();
		if ((usersDeptList != null) && (!usersDeptList.isEmpty()))
			usersDeptList.clear();
		if ((usersMap == null) || (usersMap.isEmpty()))
			users = dataAccessService.loadUsers();
		else
			users = usersMap;
		Collection<User> allUsers = users.values();
		usersDeptList = new ArrayList<>();
		for (User user : allUsers) {
			Integer userSectionId = user.getWrkSectionId();
			if (!(userSectionId == null) && userSectionId.equals(secId) && currUserId != user.getUserId())
				usersDeptList.add(user);
			// if (!(userSectionId == null)) {
			// switch (userSectionId) {
			// case MyConstants.DEV_SEC_BOSS:
			// if (currUserId != user.getUserId()) {
			// usersDeptList.add(user);
			// break;
			// }
			// case MyConstants.NETWORK_SEC_BOSS:
			// if (currUserId != user.getUserId()) {
			// usersDeptList.add(user);
			// break;
			// }
			// case MyConstants.IT_SEC_BOSS:
			// if (currUserId != user.getUserId()) {
			// usersDeptList.add(user);
			// break;
			// }
			//
			// }
			// }
		}
		return usersDeptList;
	}

	public void loadReqsMap() {
		if ((statusMap == null) || (statusMap.isEmpty())) {
			statusMap = dataAccessService.getUserReqStatus();
			statusMapList = new ArrayList<>();
			for (RequestStatus reqStat : statusMap.values()) {
				statusMapList.add(reqStat);
			}
		}
	}

	public List<SiteType> getSiteTypesList() {
		if ((siteTypesList == null) || (siteTypesList.isEmpty()))
			siteTypesList = dataAccessService.loadAllSiteTypes();
		return siteTypesList;
	}

	public void setSiteTypesList(List<SiteType> siteTypesList) {
		this.siteTypesList = siteTypesList;
	}

	public List<ContractType> getContractTypesList() {
		if ((contractTypesList == null) || (contractTypesList.isEmpty()))
			contractTypesList = dataAccessService.loadAllContractTypes();
		return contractTypesList;
	}

	public void setContractTypesList(List<ContractType> contractTypesList) {
		this.contractTypesList = contractTypesList;
	}

	public List<Clause> getClausesList() {
		if ((clausesList == null) || (clausesList.isEmpty()))
			clausesList = dataAccessService.loadAllClauses();
		return clausesList;
	}

	public void setClausesList(List<Clause> clausesList) {
		this.clausesList = clausesList;
	}

	public List<User> getEmployersListInDept() {
		return employersListInDept;
	}

	public void setEmployersListInDept(List<User> employersListInDept) {
		this.employersListInDept = employersListInDept;
	}

	public List<CarBrand> getCarBrandsList() {
		if ((carBrandsList == null) || (carBrandsList.isEmpty()))
			carBrandsList = dataAccessService.loadAllCarBrands();
		return carBrandsList;
	}

	public void setCarBrandsList(List<CarBrand> carBrandsList) {
		this.carBrandsList = carBrandsList;
	}

	public List<VehicleType> getVehicleTypeList() {
		if ((vehicleTypeList == null) || (vehicleTypeList.isEmpty()))
			vehicleTypeList = dataAccessService.loadAllVehicleTypes();
		return vehicleTypeList;
	}

	public void setVehicleTypeList(List<VehicleType> vehicleTypeList) {
		this.vehicleTypeList = vehicleTypeList;
	}

	public List<FuelType> getFuelTypeList() {
		if ((fuelTypeList == null) || (fuelTypeList.isEmpty()))
			fuelTypeList = dataAccessService.loadAllFuelTypes();
		return fuelTypeList;
	}

	public void setFuelTypeList(List<FuelType> fuelTypeList) {
		this.fuelTypeList = fuelTypeList;
	}

	public List<RealEstate> getRealEstateList() {
		// if ((realEstateList == null) || (realEstateList.isEmpty()))
		realEstateList = dataAccessService.loadAllRealEstates();
		return realEstateList;
	}

	public void setRealEstateList(List<RealEstate> realEstateList) {
		this.realEstateList = realEstateList;
	}

	public List<IntroContract> getIntroContractList() {
		if ((introContractList == null) || (introContractList.isEmpty()))
			introContractList = dataAccessService.loadAllIntroContracts();
		return introContractList;
	}

	public void setIntroContractList(List<IntroContract> introContractList) {
		this.introContractList = introContractList;
	}

	public List<ContractCancelReason> getReasonsList() {
		if ((reasonsList == null) || (reasonsList.isEmpty()))
			reasonsList = dataAccessService.loadAllContractCancelReasons();
		return reasonsList;
	}

	public void setReasonsList(List<ContractCancelReason> reasonsList) {
		this.reasonsList = reasonsList;
	}

	public List<ContractDirectType> getContractDirectTypesList() {
		if ((contractDirectTypesList == null) || (contractDirectTypesList.isEmpty()))
			contractDirectTypesList = dataAccessService.loadAllContractDirectTypes();
		return contractDirectTypesList;
	}

	public void setContractDirectTypesList(List<ContractDirectType> contractDirectTypesList) {
		this.contractDirectTypesList = contractDirectTypesList;
	}

	public List<LicTrdMasterFile> getLicencesList() {
		if (CollectionUtils.isEmpty(licencesList))
			licencesList = dataAccessService.loadLicences();
		return licencesList;
	}

	public void setLicencesList(List<LicTrdMasterFile> licencesList) {
		this.licencesList = licencesList;
	}

	public List<WrkLetterFromClass> getListLetterFrom() {
		listLetterFrom = dataAccessService.findAllWrkLetterFroms();
		return listLetterFrom;
	}

	public void setListLetterFrom(List<WrkLetterFromClass> listLetterFrom) {
		this.listLetterFrom = listLetterFrom;
	}
}

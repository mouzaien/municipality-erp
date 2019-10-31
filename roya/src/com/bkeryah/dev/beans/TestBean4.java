package com.bkeryah.dev.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.bkeryah.dao.IStockServiceDao;
import com.bkeryah.entities.ArcPeople;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.Charging;
import com.bkeryah.entities.NationalIdPlaces;
import com.bkeryah.entities.NationalIdType;
import com.bkeryah.entities.Nationality;
import com.bkeryah.entities.PayBank;
import com.bkeryah.entities.WrkRoles;
import com.bkeryah.penalties.LicTrdMasterFile;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.testssss.EmployeeForDropDown;

@ManagedBean
@ViewScoped
public class TestBean4 {

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;

	@ManagedProperty(value = "#{stockServiceDao}")
	private IStockServiceDao stockServiceDao;

	private List<EmployeeForDropDown> employeesList = new ArrayList<>();
	private List<EmployeeForDropDown> supplierList = new ArrayList<>();
	private List<EmployeeForDropDown> articleList = new ArrayList<>();
	private List<EmployeeForDropDown> employeesList2 = new ArrayList<>();
	private List<EmployeeForDropDown> penalitiesList = new ArrayList<>();
	private EmployeeForDropDown employee = new EmployeeForDropDown();
	private EmployeeForDropDown supplier = new EmployeeForDropDown();
	private EmployeeForDropDown article = new EmployeeForDropDown();
	private EmployeeForDropDown penality = new EmployeeForDropDown();

	private List<Charging> charges = new ArrayList<Charging>();
	private Map<String, Integer> employeeMap;

	private Map<String, Integer> supplierMap;
	private Map<String, Integer> articleMap;
	private Map<String, Integer> penalityMap;
	private List<WrkRoles> roles = new ArrayList<>();
	private List<PayBank> banks = new ArrayList<>();
	private List<ArcUsers> users;
	private ArcPeople peopleInfo = new ArcPeople();
	private List<NationalIdType> nationalityTypeList = new ArrayList<>();
	private List<Nationality> nationalityList = new ArrayList<>();
	private List<NationalIdPlaces> nationalIdPlacesList = new ArrayList<>();
	private String res;
	private List<LicTrdMasterFile> myList;
	private List<String> acList;

	public List<String> loadpenal(String query) {
		acList = new ArrayList<>();
		if (myList != null) {
			for (LicTrdMasterFile lic : myList) {
				if (((lic.getLicNo() != null) && (lic.getLicNo().contains(query)))
						|| ((lic.getTrdName() != null) && (lic.getTrdName().contains(query))))
					acList.add(lic.getLicNo() + "|" + lic.getTrdName());
			}
		}
		return acList;
	}

	private int strNo;

	@PostConstruct
	public void init() {
		myList = dataAccessService.getTrdMasterFileList();
		setEmployeeMap(dataAccessService.getEmployeeInfo());
		setSupplierMap(stockServiceDao.getAllNameSuppliers());
		setArticleMap(stockServiceDao.getAllNameArticles());
		setPenalityMap(dataAccessService.getAllNamePenalities());
		for (Entry<String, Integer> entry : employeeMap.entrySet()) {

			if (entry.getKey() != null && entry.getValue() != null) {
				employee.setName(entry.getKey());
				employee.setId(entry.getValue());
				employeesList.add(employee);
				employee = new EmployeeForDropDown();
			} else {
				continue;
			}

		}

		// forSupplier
		for (Entry<String, Integer> entry : supplierMap.entrySet()) {

			if (entry.getKey() != null && entry.getValue() != null) {
				System.out.println("-------start if--------"+entry.getKey());
				supplier.setName(entry.getKey());
				supplier.setId(entry.getValue());
				supplierList.add(supplier);
				supplier = new EmployeeForDropDown();
				System.out.println("-------end if--------");
			} else {
				System.out.println("-------else--------");
				continue;
			}

		}
		// forArticles
		for (Entry<String, Integer> entry : articleMap.entrySet()) {

			if (entry.getKey() != null && entry.getValue() != null) {
				article.setName(entry.getKey());
				article.setId(entry.getValue());
				articleList.add(article);
				article = new EmployeeForDropDown();
			} else {
				continue;
			}

		}
		// forPenalities
		for (Entry<String, Integer> entry : penalityMap.entrySet()) {

			if (entry.getKey() != null && entry.getValue() != null) {
				penality.setName(entry.getKey());
				penality.setId(entry.getValue());
				penalitiesList.add(penality);
				penality = new EmployeeForDropDown();
			} else {
				continue;
			}

		}

	}

	public List<EmployeeForDropDown> getArticleList(int strNo) {
		this.strNo = strNo;

		return articleList;
	}

	public void setArticleList(List<EmployeeForDropDown> articleList) {
		this.articleList = articleList;
	}

	public EmployeeForDropDown getArticle() {
		return article;
	}

	public void setArticle(EmployeeForDropDown article) {
		this.article = article;
	}

	public Map<String, Integer> getArticleMap() {
		return articleMap;
	}

	public void setArticleMap(Map<String, Integer> articleMap) {
		this.articleMap = articleMap;
	}

	public List<EmployeeForDropDown> getSupplierList() {
		return supplierList;
	}

	public void setSupplierList(List<EmployeeForDropDown> supplierList) {
		this.supplierList = supplierList;
	}

	public EmployeeForDropDown getSupplier() {
		return supplier;
	}

	public void setSupplier(EmployeeForDropDown supplier) {
		this.supplier = supplier;
	}

	public IStockServiceDao getStockServiceDao() {
		return stockServiceDao;
	}

	public void setStockServiceDao(IStockServiceDao stockServiceDao) {
		this.stockServiceDao = stockServiceDao;
	}

	public Map<String, Integer> getSupplierMap() {
		return supplierMap;
	}

	public void setSupplierMap(Map<String, Integer> supplierMap) {
		this.supplierMap = supplierMap;
	}

	public void cc() {

		String startDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("bts-ex-4");

	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<Charging> getCharges() {
		return charges;
	}

	public void setCharges(List<Charging> charges) {
		this.charges = charges;
	}

	public Map<String, Integer> getEmployeeMap() {
		return employeeMap;
	}

	public void setEmployeeMap(Map<String, Integer> employeeMap) {
		this.employeeMap = employeeMap;
	}

	public List<EmployeeForDropDown> getEmployeesList() {
		return employeesList;
	}

	public void setEmployeesList(List<EmployeeForDropDown> employeesList) {
		this.employeesList = employeesList;
	}

	public EmployeeForDropDown getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeForDropDown employee) {
		this.employee = employee;
	}

	public List<WrkRoles> getRoles() {
		return roles;
	}

	public void setRoles(List<WrkRoles> roles) {
		this.roles = roles;
	}

	public List<PayBank> getBanks() {
		return banks;
	}

	public void setBanks(List<PayBank> banks) {
		this.banks = banks;
	}

	public List<ArcUsers> getUsers() {
		return users;
	}

	public void setUsers(List<ArcUsers> users) {
		this.users = users;
	}

	public ArcPeople getPeopleInfo() {
		return peopleInfo;
	}

	public List<EmployeeForDropDown> getEmployeesList2() {
		return employeesList2;
	}

	public void setEmployeesList2(List<EmployeeForDropDown> employeesList2) {
		this.employeesList2 = employeesList2;
	}

	public void setPeopleInfo(ArcPeople peopleInfo) {
		this.peopleInfo = peopleInfo;
	}

	public List<NationalIdType> getNationalityTypeList() {
		return nationalityTypeList;
	}

	public void setNationalityTypeList(List<NationalIdType> nationalityTypeList) {
		this.nationalityTypeList = nationalityTypeList;
	}

	public List<Nationality> getNationalityList() {
		return nationalityList;
	}

	public void setNationalityList(List<Nationality> nationalityList) {
		this.nationalityList = nationalityList;
	}

	public List<NationalIdPlaces> getNationalIdPlacesList() {
		return nationalIdPlacesList;
	}

	public void setNationalIdPlacesList(List<NationalIdPlaces> nationalIdPlacesList) {
		this.nationalIdPlacesList = nationalIdPlacesList;
	}

	public List<EmployeeForDropDown> getPenalitiesList() {
		return penalitiesList;
	}

	public void setPenalitiesList(List<EmployeeForDropDown> penalitiesList) {
		this.penalitiesList = penalitiesList;
	}

	public EmployeeForDropDown getPenality() {
		return penality;
	}

	public void setPenality(EmployeeForDropDown penality) {
		this.penality = penality;
	}

	public Map<String, Integer> getPenalityMap() {
		return penalityMap;
	}

	public void setPenalityMap(Map<String, Integer> penalityMap) {
		this.penalityMap = penalityMap;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public List<LicTrdMasterFile> getMyList() {
		return myList;
	}

	public void setMyList(List<LicTrdMasterFile> myList) {
		this.myList = myList;
	}

	public List<String> getAcList() {
		return acList;
	}

	public void setAcList(List<String> acList) {
		this.acList = acList;
	}

}

package com.bkeryah.stock.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.bkeryah.bean.UserContactClass;
import com.bkeryah.dao.IStockServiceDao;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.TransferOwnership;
import com.bkeryah.entities.TransferOwnershipDetails;
import com.bkeryah.entities.WhsWarehouses;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.Utils;

@ManagedBean(name = "searchTransferBean")
@ViewScoped
public class SearchTransferOwnershipBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	@ManagedProperty(value = "#{stockServiceDao}")
	private IStockServiceDao stockServiceDao;
	private List<WhsWarehouses> allWareHouses = new ArrayList<WhsWarehouses>();
	private List<User> employersList;

	private ArcUsers currentUser;
	private Integer employerId;
	private Integer strNo;
	private List<TransferOwnership> transSaveList = new ArrayList<>();
	private List<TransferOwnership> transFilterList;
	private boolean hideStores;
	private List<UserContactClass> listOfEmpsInDept;
	private List<TransferOwnershipDetails> tsOnDtls = new ArrayList<>();
	private TransferOwnership transOb;

	@PostConstruct
	public void init() {
//		Integer[] numbers = new Integer[] { 1, 2, 1, 3, 4, 4 };
//		Set<Integer> allItems = new HashSet<>();
//		Set<Integer> duplicates = Arrays.stream(numbers)
//		        .filter(n -> allItems.add(n)) //Set.add() returns false if the item was already in the set.
//		        .collect(Collectors.toSet());
//		System.out.println(duplicates); // [1, 4]
		employersList = new ArrayList<>();
		currentUser = Utils.findCurrentUser();
		employerId = currentUser.getUserId();
		hideStores = false;
		Integer usrId = dataAccessService.getPropertiesValue("MOSTAWDA3_ID");
		if (usrId == currentUser.getUserId()) { // if manager of stores
			setAllWareHouses(stockServiceDao.getStoreWharehouses(1));
			employersList = dataAccessService.getAllUsers();
			hideStores = true;
		} else { // if store dean user
			setAllWareHouses(stockServiceDao.getStoreDeanWharehouses(currentUser.getUserId()));

			if (allWareHouses != null && allWareHouses.size() >= 1) {
				hideStores = true;
				employersList = dataAccessService.getAllUsers();
			}
		}

		if (allWareHouses != null && allWareHouses.size() >= 1) {
			// store boss or store dean or manager of stores
			strNo = allWareHouses.get(0).getStoreNumber();
			transSaveList = dataAccessService.loadAllTransferOwnerByEmpId(employerId, strNo);
			if (transSaveList == null || transSaveList.size() == 0) {
				transSaveList = dataAccessService.loadAllTransferOwnerByEmpId(employerId, null);
			}
		} else {
			// if normal user or manager
			hideStores = false;
			employersList = new ArrayList<User>();
			employersList = dataAccessService.getAllEmployeesByManager(currentUser.getUserId());
			User usr = (User) dataAccessService.findEntityById(User.class, currentUser.getUserId());
			employersList.add(usr);
			transSaveList = dataAccessService.loadAllTransferOwnerByEmpId(currentUser.getUserId(), null);
		}

	}

	public void transfilter() {
		if (hideStores)
			transSaveList = dataAccessService.loadAllTransferOwnerByEmpId(employerId, strNo);
		if (hideStores && employerId == null &&(transSaveList == null || transSaveList.size() == 0)) {
			transSaveList = dataAccessService.loadAllTransferOwnerByEmpId(currentUser.getUserId(), null);
		}
	}

	public void loadTransDtls(TransferOwnership trans) {
		transOb = trans;
		tsOnDtls = dataAccessService.loadTransferOwnerDetails(trans.getId());
	}

	public String printTransfer() {
		String reportName = "/reports/transferOwnership_Request.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("transId", transOb.getId());
		parameters.put("record_id", -1);
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public List<TransferOwnership> getTransSaveList() {
		return transSaveList;
	}

	public void setTransSaveList(List<TransferOwnership> transSaveList) {
		this.transSaveList = transSaveList;
	}

	public List<WhsWarehouses> getAllWareHouses() {
		return allWareHouses;
	}

	public void setAllWareHouses(List<WhsWarehouses> allWareHouses) {
		this.allWareHouses = allWareHouses;
	}

	public List<User> getEmployersList() {
		return employersList;
	}

	public void setEmployersList(List<User> employersList) {
		this.employersList = employersList;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public IStockServiceDao getStockServiceDao() {
		return stockServiceDao;
	}

	public void setStockServiceDao(IStockServiceDao stockServiceDao) {
		this.stockServiceDao = stockServiceDao;
	}

	public ArcUsers getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(ArcUsers currentUser) {
		this.currentUser = currentUser;
	}

	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public Integer getStrNo() {
		return strNo;
	}

	public void setStrNo(Integer strNo) {
		this.strNo = strNo;
	}

	public boolean isHideStores() {
		return hideStores;
	}

	public void setHideStores(boolean hideStores) {
		this.hideStores = hideStores;
	}

	public List<UserContactClass> getListOfEmpsInDept() {
		return listOfEmpsInDept;
	}

	public void setListOfEmpsInDept(List<UserContactClass> listOfEmpsInDept) {
		this.listOfEmpsInDept = listOfEmpsInDept;
	}

	public List<TransferOwnershipDetails> getTsOnDtls() {
		return tsOnDtls;
	}

	public void setTsOnDtls(List<TransferOwnershipDetails> tsOnDtls) {
		this.tsOnDtls = tsOnDtls;
	}

	public List<TransferOwnership> getTransFilterList() {
		return transFilterList;
	}

	public void setTransFilterList(List<TransferOwnership> transFilterList) {
		this.transFilterList = transFilterList;
	}

	public TransferOwnership getTransOb() {
		return transOb;
	}

	public void setTransOb(TransferOwnership transOb) {
		this.transOb = transOb;
	}
}
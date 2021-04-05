package com.bkeryah.stock.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bkeryah.bean.UserContactClass;
import com.bkeryah.bean.UserMailObj;
import com.bkeryah.dao.IStockServiceDao;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.Article;
import com.bkeryah.entities.HrScenario;
import com.bkeryah.entities.HrsSigns;
import com.bkeryah.entities.ReturnStore;
import com.bkeryah.entities.ReturnStoreDetails;
import com.bkeryah.entities.TransferOwnership;
import com.bkeryah.entities.TransferOwnershipDetails;
import com.bkeryah.entities.WhsWarehouses;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.managedBean.reqfin.newReplaceFinBean;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean(name = "searchReturnBean")
@ViewScoped
public class SearchReturnBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	@ManagedProperty(value = "#{stockServiceDao}")
	private IStockServiceDao stockServiceDao;
	private List<WhsWarehouses> allWareHouses = new ArrayList<WhsWarehouses>();
	private List<User> employersList;

	private ArcUsers currentUser;
	private Integer employerId;
	private Integer strNo;
	private List<ReturnStore> returnSaveList = new ArrayList<>();
	private List<ReturnStore> returnFilterList;
	private boolean hideStores;
	private List<UserContactClass> listOfEmpsInDept;
	private List<ReturnStoreDetails> returnDtls = new ArrayList<>();
	private ReturnStore returnOb;

	@PostConstruct
	public void init() {

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
			returnSaveList = dataAccessService.loadAllReturnStoreByEmpId(employerId, strNo);
			if (returnSaveList == null || returnSaveList.size() == 0) {
				returnSaveList = dataAccessService.loadAllReturnStoreByEmpId(currentUser.getUserId(), null);
			}
		} else {
			// if normal user or manager
			hideStores = false;
			employersList = new ArrayList<User>();
			employersList = dataAccessService.getAllEmployeesByManager(currentUser.getUserId());
			User usr = (User) dataAccessService.findEntityById(User.class, currentUser.getUserId());
			employersList.add(usr);
			returnSaveList = dataAccessService.loadAllReturnStoreByEmpId(currentUser.getUserId(), null);
		}

	}

	public void returnStorefilter() {
		if (hideStores)
			returnSaveList = dataAccessService.loadAllReturnStoreByEmpId(employerId, strNo);
		if (hideStores && employerId == null && (returnSaveList == null || returnSaveList.size() == 0)) {
			returnSaveList = dataAccessService.loadAllReturnStoreByEmpId(currentUser.getUserId(), null);
		}
	}

	public void loadRetDtls(ReturnStore trans) {
		returnOb = trans;
		returnDtls = dataAccessService.getReturnStoreDetailsById(trans.getReturnStoreId());
	}

	public String printReturnStore() {
		String reportName = "/reports/document_returned.jrxml";
		String strName = ((WhsWarehouses) dataAccessService.findEntityById(WhsWarehouses.class, returnOb.getStrNo()))
				.getStoreName();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("docId", returnOb.getReturnStoreId());
		parameters.put("STORE_NAME", strName);
		Utils.printPdfReport(reportName, parameters);
		return "";
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

	public List<ReturnStore> getReturnSaveList() {
		return returnSaveList;
	}

	public void setReturnSaveList(List<ReturnStore> returnSaveList) {
		this.returnSaveList = returnSaveList;
	}

	public List<ReturnStore> getReturnFilterList() {
		return returnFilterList;
	}

	public void setReturnFilterList(List<ReturnStore> returnFilterList) {
		this.returnFilterList = returnFilterList;
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

	public List<ReturnStoreDetails> getReturnDtls() {
		return returnDtls;
	}

	public void setReturnDtls(List<ReturnStoreDetails> returnDtls) {
		this.returnDtls = returnDtls;
	}

	public ReturnStore getReturnOb() {
		return returnOb;
	}

	public void setReturnOb(ReturnStore returnOb) {
		this.returnOb = returnOb;
	}

}
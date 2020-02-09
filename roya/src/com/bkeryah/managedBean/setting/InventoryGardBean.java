package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.bkeryah.dao.IStockServiceDao;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.ArticleGroup;
import com.bkeryah.entities.InventoryMaster;
import com.bkeryah.entities.WhsWarehouses;
import com.bkeryah.service.IDataAccessService;

import utilities.Utils;

@ManagedBean(name = "inventoryGardBean")
@ViewScoped
public class InventoryGardBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	@ManagedProperty(value = "#{stockServiceDao}")
	private IStockServiceDao stockServiceDao;
	private List<InventoryMaster> inventoriesList = new ArrayList<InventoryMaster>();
	private List<InventoryMaster> inventoriesTableList = new ArrayList<InventoryMaster>();
	private InventoryMaster InvGard;
	private ArcUsers currentUser;
	private List<WhsWarehouses> allWareHouses = new ArrayList<WhsWarehouses>();
	private Integer strNo = 0;
	private Integer gardId;
	private boolean blockGard;

	@PostConstruct
	public void init() {
		currentUser = Utils.findCurrentUser();
		allWareHouses = dataAccessService.getAllStores();
		// inventoriesList = dataAccessService.findAllInventoryMaster();
	}

	public List<InventoryMaster> loadInventories() {
		inventoriesList = dataAccessService.getInventoriesByStrNo(strNo);
		if (inventoriesList != null) {
			blockGard = true;
		}
		inventoriesTableList = new ArrayList<InventoryMaster>();
		return inventoriesList;
	}

	public List<InventoryMaster> loadTableInventoriesData() {
		inventoriesTableList = dataAccessService.findInventoryMasterByGard_strNO(gardId, strNo);
		return inventoriesTableList;
	}

	public String getStoreById(Integer id) {
		WhsWarehouses store = dataAccessService.loadStoreIdById(id);
		return store.getStoreName();
	}

	public String getUserById(Integer userId) {
		ArcUsers usr = dataAccessService.findUserById(userId);
		return usr.getFirstName();
	}

	public void onRowEdit(RowEditEvent event) {
		InventoryMaster selectedItem = (InventoryMaster) event.getObject();
		FacesMessage msg = new FacesMessage("تم حفظ التعديل");
		dataAccessService.updateObject(selectedItem);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("تم إلغاء التعديل");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<InventoryMaster> getInventoriesList() {
		return inventoriesList;
	}

	public void setInventoriesList(List<InventoryMaster> inventoriesList) {
		this.inventoriesList = inventoriesList;
	}

	public InventoryMaster getInvGard() {
		return InvGard;
	}

	public void setInvGard(InventoryMaster invGard) {
		InvGard = invGard;
	}

	public List<WhsWarehouses> getAllWareHouses() {
		return allWareHouses;
	}

	public void setAllWareHouses(List<WhsWarehouses> allWareHouses) {
		this.allWareHouses = allWareHouses;
	}

	public IStockServiceDao getStockServiceDao() {
		return stockServiceDao;
	}

	public void setStockServiceDao(IStockServiceDao stockServiceDao) {
		this.stockServiceDao = stockServiceDao;
	}

	public Integer getStrNo() {
		return strNo;
	}

	public void setStrNo(Integer strNo) {
		this.strNo = strNo;
	}

	public Integer getGardId() {
		return gardId;
	}

	public void setGardId(Integer gardId) {
		this.gardId = gardId;
	}

	public List<InventoryMaster> getInventoriesTableList() {
		return inventoriesTableList;
	}

	public void setInventoriesTableList(List<InventoryMaster> inventoriesTableList) {
		this.inventoriesTableList = inventoriesTableList;
	}

	public boolean isBlockGard() {
		return blockGard;
	}

	public void setBlockGard(boolean blockGard) {
		this.blockGard = blockGard;
	}
}

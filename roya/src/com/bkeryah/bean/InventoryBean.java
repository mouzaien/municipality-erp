package com.bkeryah.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.bkeryah.dao.IStockServiceDao;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.InventoryMaster;
import com.bkeryah.entities.WhsWarehouses;
import com.bkeryah.service.IDataAccessService;

import net.bootsfaces.component.message.Message;
import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class InventoryBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	@ManagedProperty(value = "#{stockServiceDao}")
	private IStockServiceDao stockServiceDao;
	private InventoryMaster inventoryMaster = new InventoryMaster();
	private List<InventoryMaster> inventoriesList = new ArrayList<InventoryMaster>();
	private List<InventoryMaster> inventoryiesListFilter;
	private ArcUsers currentUser;
	private Integer currentUserId;
	private List<WhsWarehouses> allWareHouses = new ArrayList<WhsWarehouses>();
	private Integer strNo = 0;
	private boolean stockIsBlocked;
	private boolean updateMode;
	private Integer inventoryStatus;
	private String inventroyDate;

	@PostConstruct
	public void init() {
		currentUser = Utils.findCurrentUser();
		currentUserId = currentUser.getUserId();
		inventoriesList = dataAccessService.getInventoriesByStrNo(1);
		setAllWareHouses(stockServiceDao.getStoreDeanWharehouses(currentUser.getUserId()));
	}

	public void loadAllInventoriesList() {
		for (WhsWarehouses whsWarehouses : allWareHouses) {
			if (whsWarehouses.getStoreNumber() == strNo)
				stockIsBlocked = whsWarehouses.getInvIsBlocked().equals(1);
		}
		inventoriesList = dataAccessService.getInventoriesByStrNo(strNo);

		refreshPage();
	}

	public void addInventory() {

		try {
			String[] parts = inventoryMaster.getInventoryDate().split("/");
			Integer dd = Integer.parseInt(parts[0]);
			Integer MM = Integer.parseInt(parts[1]);
			Integer yyyy = Integer.parseInt(parts[2]);
			inventoryMaster.setCreatedby(currentUserId);
			inventoryMaster.setInventoryStartDate(inventoryMaster.getInventoryDate());
			inventoryMaster.setInventoryEndDate(inventoryMaster.getInventoryDate());
			inventoryMaster.setCreateddate(inventoryMaster.getInventoryDate());
			inventoryMaster.setYearid(yyyy);
			if (updateMode) {
				dataAccessService.updateObject(inventoryMaster);
			} else {
				dataAccessService.save(inventoryMaster);
			}
			inventoriesList = dataAccessService.getInventoriesByStrNo(strNo);
			refreshPage();
		} catch (Exception e) {
			MsgEntry.addErrorMessage("قد يكون هناك جرد أخر مفتوح علي هذا المستودع");
		}
	}

	public void loadSelectedInventory(InventoryMaster selectedItme) {
		updateMode = true;
		inventoryMaster = (InventoryMaster) dataAccessService.findEntityById(InventoryMaster.class,
				selectedItme.getInventoryId());

		// inventoryMaster = new InventoryMaster();
		// inventoryMaster.setCreatedby(selectedItme.getCreatedby());
		// inventoryMaster.setCreateddate(selectedItme.getCreateddate());
		// inventoryMaster.setInventoryBlocked(selectedItme.getInventoryBlocked());
		// inventoryMaster.setStrno(selectedItme.getStrno());
		// inventoryMaster.setInventoryDate(selectedItme.getInventoryDate());
		// inventoryMaster.setInventoryStartDate(selectedItme.getInventoryStartDate());
		// inventoryMaster.setInventoryEndDate(selectedItme.getInventoryEndDate());

	}

	public String refreshPage() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('vtWidget').clearFilters()");
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

	public Integer getStrNo() {
		return strNo;
	}

	public void setStrNo(Integer strNo) {
		this.strNo = strNo;
	}

	public boolean isStockIsBlocked() {
		return stockIsBlocked;
	}

	public void setStockIsBlocked(boolean stockIsBlocked) {
		this.stockIsBlocked = stockIsBlocked;
	}

	public Integer getInventoryStatus() {
		return inventoryStatus;
	}

	public void setInventoryStatus(Integer inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}

	public String getInventroyDate() {
		return inventroyDate;
	}

	public void setInventroyDate(String inventroyDate) {
		this.inventroyDate = inventroyDate;
	}

	public InventoryMaster getInventoryMaster() {
		return inventoryMaster;
	}

	public void setInventoryMaster(InventoryMaster inventoryMaster) {
		this.inventoryMaster = inventoryMaster;
	}

	public List<InventoryMaster> getInventoriesList() {
		return inventoriesList;
	}

	public void setInventoriesList(List<InventoryMaster> inventoriesList) {
		this.inventoriesList = inventoriesList;
	}

	public List<InventoryMaster> getInventoryiesListFilter() {
		return inventoryiesListFilter;
	}

	public void setInventoryiesListFilter(List<InventoryMaster> inventoryiesListFilter) {
		this.inventoryiesListFilter = inventoryiesListFilter;
	}

	public boolean isUpdateMode() {
		return updateMode;
	}

	public void setUpdateMode(boolean updateMode) {
		this.updateMode = updateMode;
	}

}

package com.bkeryah.stock.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.entities.WhsWarehouses;
import com.bkeryah.managedBean.param.UsersParamBean;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean(name = "whsWarehouseBean")
@ViewScoped
public class WhsWarehouseBean {
	private static Logger logger = Logger.getLogger(UsersParamBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<WhsWarehouses> warehousesList;
	private List<User> usersList;
	private WhsWarehouses addEditWhsWarehouses;
	private WhsWarehouses selectedWhsWarehouses;
	private Boolean storeBlock;
	private String stName;
	private String stType;
	private Integer stBossId;
	private Integer stDeanId;

	@PostConstruct
	public void init() {
		warehousesList = dataAccessService.getWhsWarehouses();
		usersList = dataAccessService.getAllUsers();
		addEditWhsWarehouses = new WhsWarehouses();
		selectedWhsWarehouses = new WhsWarehouses();
	}

	public List<WhsWarehouses> getWarehousesList() {
		return warehousesList;
	}

	public void savewhs() {

		if (addEditWhsWarehouses.getStoreNumber() == null) {
			if (!addEditWhsWarehouses.getStoreName().isEmpty() && addEditWhsWarehouses.getStoreName() != null) {
				try {
					// addEditWhsWarehouses.setStoreNumber(new Integer(0));
					//RequestContext context = RequestContext.getCurrentInstance();
					dataAccessService.save(addEditWhsWarehouses);
					Utils.updateUIComponent("form:whsDT");
					Utils.closeDialog("whsdlAdd");
					warehousesList = dataAccessService.getWhsWarehouses();
					MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				} catch (Exception e) {
					e.getStackTrace();
					System.out.println(e.getMessage());
					MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
					Utils.closeDialog("whsdlAdd");

				}

			}

		} else {
			if (!stName.isEmpty() && stName != null) {
				try {
					addEditWhsWarehouses.setStoreBossId(stBossId);
					addEditWhsWarehouses.setStoreDeanId(stDeanId);
					addEditWhsWarehouses.setStoreName(stName);
					addEditWhsWarehouses.setStrType(Integer.parseInt(stType));
					
					dataAccessService.updateObject(addEditWhsWarehouses);
					System.out.println("edit >>>" + addEditWhsWarehouses.getStoreName());
					 Utils.closeDialog("whsdlEdit");
					//addEditWhsWarehouses.setStoreNumber(new Integer(0));
					MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
					Utils.updateUIComponent("form:whsDT");
					warehousesList = dataAccessService.getWhsWarehouses();
				} catch (Exception e) {
					e.getStackTrace();
					System.out.println(e.getMessage());
					MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
					Utils.closeDialog("whsdlEdit");

				}
			}
		}

	}

	public void deletewhs() {
		try {
			dataAccessService.deleteObject(selectedWhsWarehouses);

			System.out.println(
					"whs Deleted With Id: " + selectedWhsWarehouses.getStoreNumber() + "  Successfully From Database");

			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			logger.info("delete whs: id: " + selectedWhsWarehouses.getStoreNumber());
			warehousesList = dataAccessService.getWhsWarehouses();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("delete whs: id: " + selectedWhsWarehouses.getStoreNumber());
		}

	}

	public void showdlAdd() {
		addEditWhsWarehouses = new WhsWarehouses();
		Utils.openDialog("whsdlAdd");

	}

	public void showdledit(WhsWarehouses sh) {
		addEditWhsWarehouses = selectedWhsWarehouses;
		addEditWhsWarehouses = sh;
		this.stDeanId = sh.getStoreDeanId();
		this.stBossId=  sh.getStoreBossId();
		this.stName = sh.getStoreName();
		this.stType = String.valueOf(sh.getStrType());
		System.out.println((selectedWhsWarehouses==null ? null :selectedWhsWarehouses.getStoreName())+">>>>>>>>>" + addEditWhsWarehouses.getStoreName());
		// if(addEditWhsWarehouses.block)
		Utils.openDialog("whsdlEdit");

	}

	public void showdlDelete(WhsWarehouses sh) {
		// addEditWhsWarehouses = selectedWhsWarehouses;
		selectedWhsWarehouses = sh;
		System.out.println(">>>>>>>>>" + addEditWhsWarehouses.getStoreName());
		// if(addEditWhsWarehouses.block)
		Utils.openDialog("confirmDialog");

	}

	public void closedledit() {
		// if(addEditWhsWarehouses.block)
		Utils.closeDialog("whsdlEdit");

	}

	public void setWarehousesList(List<WhsWarehouses> warehousesList) {
		this.warehousesList = warehousesList;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<User> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
	}

	public WhsWarehouses getAddEditWhsWarehouses() {
		return addEditWhsWarehouses;
	}

	public void setAddEditWhsWarehouses(WhsWarehouses addEditWhsWarehouses) {
		this.addEditWhsWarehouses = addEditWhsWarehouses;
	}

	public WhsWarehouses getSelectedWhsWarehouses() {
		return selectedWhsWarehouses;
	}

	public void setSelectedWhsWarehouses(WhsWarehouses selectedWhsWarehouses) {
		this.selectedWhsWarehouses = selectedWhsWarehouses;
	}

	public Boolean getStoreBlock() {
		return storeBlock;
	}

	public void setStoreBlock(Boolean storeBlock) {
		this.storeBlock = storeBlock;
	}

	public String getStName() {
		return stName;
	}

	public void setStName(String stName) {
		this.stName = stName;
	}

	public String getStType() {
		return stType;
	}

	public void setStType(String stType) {
		this.stType = stType;
	}

	public Integer getStBossId() {
		return stBossId;
	}

	public void setStBossId(Integer stBossId) {
		this.stBossId = stBossId;
	}

	public Integer getStDeanId() {
		return stDeanId;
	}

	public void setStDeanId(Integer stDeanId) {
		this.stDeanId = stDeanId;
	}

}

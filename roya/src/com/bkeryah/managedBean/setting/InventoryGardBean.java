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
	private List<InventoryMaster> inventoriesList = new ArrayList<InventoryMaster>();
	private InventoryMaster InvGard;

	@PostConstruct
	public void init() {
		inventoriesList = dataAccessService.findAllInventoryMaster();
	}

	public String getStoreById(Integer id) {
		WhsWarehouses store=dataAccessService.loadStoreIdById(id);
		return store.getStoreName();
	}

	public String getUserById(Integer userId) {
		ArcUsers usr=dataAccessService.findUserById(userId);
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
}

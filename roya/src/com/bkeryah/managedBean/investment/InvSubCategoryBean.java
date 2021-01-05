package com.bkeryah.managedBean.investment;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.investment.ContractMainCategory;
import com.bkeryah.entities.investment.ContractSubcategory;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class InvSubCategoryBean {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private static final Logger logger = Logger.getLogger(ContractDirectBean.class);
	private List<ContractSubcategory> contractSubCategoryList = new ArrayList<ContractSubcategory>();
	private List<ContractSubcategory> filteredCategories = new ArrayList<ContractSubcategory>();
	private ContractSubcategory subCategory = new ContractSubcategory();
	private boolean delete = false;
	private List<ContractMainCategory> contractMainCategoryList = new ArrayList<ContractMainCategory>();
	private Integer mainCategoryId;

	@PostConstruct
	public void init() {
		contractSubCategoryList = dataAccessService.loadAllContractSubcategory();
		contractMainCategoryList = dataAccessService.loadAllContractMainCategory();

	}

	public void saveSubCategory() {
		try {
			if (subCategory != null && mainCategoryId != null) {
				subCategory.setContMainCategoryid(mainCategoryId);
				dataAccessService.save(subCategory);
				contractSubCategoryList = dataAccessService.loadAllContractSubcategory();
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				setSubCategory(null);
			} else {
				MsgEntry.addErrorMessage("ادخل التصنيفالرئيسي و الفرعي قبل الحفظ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}

	public static void updateUIComponent(String ComponentName) {

	}

	public void cancelDelete() {
		delete = false;
	}

	public void deleteAllowed(ContractSubcategory category) {
		RequestContext context = RequestContext.getCurrentInstance();
		// context.;
		//// context.update("");
		// delete = true;
		subCategory = category;
	}

	public void deleteSubCategory() {
		try {
			delete = true;
			if (subCategory != null && subCategory.getId() != null) {

				dataAccessService.deleteObject(subCategory);
				dataAccessService.deleteContractMainCategory(subCategory.getId());
				contractSubCategoryList = dataAccessService.loadAllContractSubcategory();
				Utils.updateUIComponent(":includeform:LicLst");
				MsgEntry.addErrorMessage("تم الحذف" + subCategory.getId());
				delete = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage("لم يتم الحذف! ");
		}
		contractSubCategoryList = dataAccessService.loadAllContractSubcategory();
		// return "";
	}

	public void onRowEdit(RowEditEvent event) {
		ContractSubcategory mainCat = (ContractSubcategory) event.getObject();
		dataAccessService.updateObject(mainCat);
		FacesMessage msg = new FacesMessage(" تم تعديل ", mainCat.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		ContractSubcategory mainCat = (ContractSubcategory) event.getObject();
		MsgEntry.addErrorMessage("تم الإلغاء");
	}
	//////////////////////////////// setters and getters
	//////////////////////////////// //////////////////////////////////////////////////////////////////////////

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<ContractSubcategory> getContractSubCategoryList() {
		return contractSubCategoryList;
	}

	public void setContractSubCategoryList(List<ContractSubcategory> contractSubCategoryList) {
		this.contractSubCategoryList = contractSubCategoryList;
	}

	public List<ContractSubcategory> getFilteredCategories() {
		return filteredCategories;
	}

	public void setFilteredCategories(List<ContractSubcategory> filteredCategories) {
		this.filteredCategories = filteredCategories;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ContractSubcategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(ContractSubcategory subCategory) {
		this.subCategory = subCategory;
	}

	public List<ContractMainCategory> getContractMainCategoryList() {
		return contractMainCategoryList;
	}

	public void setContractMainCategoryList(List<ContractMainCategory> contractMainCategoryList) {
		this.contractMainCategoryList = contractMainCategoryList;
	}

	public Integer getMainCategoryId() {
		return mainCategoryId;
	}

	public void setMainCategoryId(Integer mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}

}

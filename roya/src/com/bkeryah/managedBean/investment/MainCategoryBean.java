package com.bkeryah.managedBean.investment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.type.SerializationException;
import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.investment.ContractMainCategory;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class MainCategoryBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<ContractMainCategory> contractMainCategoryList = new ArrayList<ContractMainCategory>();
	private List<ContractMainCategory> contractMainCategoryListFilter = new ArrayList<ContractMainCategory>();
	private ContractMainCategory mainCategory = new ContractMainCategory();
	private boolean delete = false;

	@PostConstruct
	public void init() {
		contractMainCategoryList = dataAccessService.loadAllContractMainCategory();
	}

	public void saveMainCategory() {
		try {
			if (mainCategory != null) {
				dataAccessService.save(mainCategory);
				contractMainCategoryList = dataAccessService.loadAllContractMainCategory();
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				setMainCategory(null);
			} else {
				MsgEntry.addErrorMessage("يجب إدخال اسم التصنيف قبل الحفظ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}

	public void cancelDelete() {
		delete = false;
	}

	public void allowDelete(RowEditEvent event) {

		ContractMainCategory mainCat = (ContractMainCategory) event.getObject();
		delete = true;
		mainCategory = mainCat;
	}

	public String deleteMainCategory() {
		try {
			delete = true;
			if (mainCategory != null) {
				dataAccessService.deleteObject(mainCategory);
				MsgEntry.addAcceptFlashInfoMessage("تم الحذف");
				setMainCategory(null);
				delete = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage("لم يتم الحذف! ... تأكد من ان التصنيف ليس عليه عقود");
		}
		contractMainCategoryList = dataAccessService.loadAllContractMainCategory();
		return "";
	}

	public void onRowEdit(RowEditEvent event) {
		try {
			ContractMainCategory obj = (ContractMainCategory) event.getObject();
			dataAccessService.updateObject(obj);
			MsgEntry.addAcceptFlashInfoMessage("تم التعديل");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onRowCancel(RowEditEvent event) {
		ContractMainCategory obj = (ContractMainCategory) event.getObject();
		MsgEntry.addErrorMessage("تم الإلغاء");
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<ContractMainCategory> getContractMainCategoryList() {
		return contractMainCategoryList;
	}

	public void setContractMainCategoryList(List<ContractMainCategory> contractMainCategoryList) {
		this.contractMainCategoryList = contractMainCategoryList;
	}

	public List<ContractMainCategory> getContractMainCategoryListFilter() {
		return contractMainCategoryListFilter;
	}

	public void setContractMainCategoryListFilter(List<ContractMainCategory> contractMainCategoryListFilter) {
		this.contractMainCategoryListFilter = contractMainCategoryListFilter;
	}

	public ContractMainCategory getMainCategory() {
		return mainCategory;
	}

	public void setMainCategory(ContractMainCategory mainCategory) {
		this.mainCategory = mainCategory;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}
}

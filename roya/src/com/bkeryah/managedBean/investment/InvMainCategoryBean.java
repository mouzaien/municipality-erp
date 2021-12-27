package com.bkeryah.managedBean.investment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.*;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.investment.ContractMainCategory;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class InvMainCategoryBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private static final Logger logger = LogManager.getLogger(ContractDirectBean.class);
	private List<ContractMainCategory> contractMainCategoryList = new ArrayList<ContractMainCategory>();
	private List<ContractMainCategory> filteredCategories = new ArrayList<ContractMainCategory>();
	private ContractMainCategory mainCategory = new ContractMainCategory();
	private boolean delete = false;

	@PostConstruct
	public void init() {
		contractMainCategoryList = dataAccessService.loadAllContractMainCategory();
	}

	public void saveMainCategory() {
		try {
			if (mainCategory != null && mainCategory.getName() != null && mainCategory.getName().trim().length() > 2) {
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

	public static void updateUIComponent(String ComponentName) {

	}

	public void cancelDelete() {
		delete = false;
	}

	public void deleteAllowed(ContractMainCategory category) {
		RequestContext context = RequestContext.getCurrentInstance();
		// context.;
		//// context.update("");
		// delete = true;
		mainCategory = category;
	}

	public void deleteMainCategory() {
		try {
			delete = true;
			if (mainCategory != null && mainCategory.getId() != null) {

				// dataAccessService.deleteObject(mainCategory);
				dataAccessService.deleteContractMainCategory(mainCategory.getId());
				contractMainCategoryList = dataAccessService.loadAllContractMainCategory();
				Utils.updateUIComponent(":includeform:LicLst");
				MsgEntry.addErrorMessage("تم الحذف" + mainCategory.getId());
				delete = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage("لم يتم الحذف! ... تأكد من ان التصنيف ليس عليه عقود و ليس تحته تصنيف فرعي");
		}
		contractMainCategoryList = dataAccessService.loadAllContractMainCategory();
		// return "";
	}

	public void onRowEdit(RowEditEvent event) {
		ContractMainCategory mainCat = (ContractMainCategory) event.getObject();
		dataAccessService.updateObject(mainCat);
		FacesMessage msg = new FacesMessage(" تم تعديل ", mainCat.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		ContractMainCategory mainCat = (ContractMainCategory) event.getObject();
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

	public List<ContractMainCategory> getContractMainCategoryList() {
		return contractMainCategoryList;
	}

	public void setContractMainCategoryList(List<ContractMainCategory> contractMainCategoryList) {
		this.contractMainCategoryList = contractMainCategoryList;
	}

	public List<ContractMainCategory> getFilteredCategories() {
		return filteredCategories;
	}

	public void setFilteredCategories(List<ContractMainCategory> filteredCategories) {
		this.filteredCategories = filteredCategories;
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

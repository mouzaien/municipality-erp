package com.bkeryah.managedBean.investment;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.investment.IntroContract;
import com.bkeryah.entities.investment.SiteType;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class ContractIntroBean {
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private static final Logger logger = Logger.getLogger(ContractDirectBean.class);
	private String introName;
	private String introDescription;
	private IntroContract introdaction = new IntroContract();
	private List<IntroContract> introContractList;
	private boolean delete = false;

	@PostConstruct
	public void init() {
		introContractList = dataAccessService.loadAllIntroContracts();
	}

	public void saveIntrodaction() {
		try {
			if (introName != null && introDescription != null) {
				introdaction.setName(introName);
				introdaction.setDescription(introDescription);
				dataAccessService.save(introdaction);
				introContractList = dataAccessService.loadAllIntroContracts();
				introName = new String();
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}

	public void cancelDelete() {
		delete = false;
	}

	public void deleteIntrodaction() {
		try {
			delete = true;
			if (introdaction != null && introdaction.getId() != null) {

				// dataAccessService.deleteObject(mainCategory);
				dataAccessService.deleteIntroContract(introdaction.getId());
				introContractList = dataAccessService.loadAllIntroContracts();
				Utils.updateUIComponent(":includeform:LicLst");
				MsgEntry.addErrorMessage(" تم الحذف " + introdaction.getName());
				delete = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage("لم يتم الحذف! ... تأكد من ان التصنيف ليس عليه عقود و ليس تحته تصنيف فرعي");
		}
		introContractList = dataAccessService.loadAllIntroContracts();
		// return "";
	}

	public void onRowEdit(RowEditEvent event) {
		IntroContract intro = (IntroContract) event.getObject();
		dataAccessService.updateObject(intro);
		FacesMessage msg = new FacesMessage(" تم تعديل ", intro.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		IntroContract intro = (IntroContract) event.getObject();
		MsgEntry.addErrorMessage("تم الإلغاء");
	}

	///////////////////// setters and getters/////////////////////
	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public String getIntroName() {
		return introName;
	}

	public void setIntroName(String introName) {
		this.introName = introName;
	}

	public String getIntroDescription() {
		return introDescription;
	}

	public void setIntroDescription(String introDescription) {
		this.introDescription = introDescription;
	}

	public IntroContract getIntrodaction() {
		return introdaction;
	}

	public void setIntrodaction(IntroContract introdaction) {
		this.introdaction = introdaction;
	}

	public List<IntroContract> getIntroContractList() {
		return introContractList;
	}

	public void setIntroContractList(List<IntroContract> introContractList) {
		this.introContractList = introContractList;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

}

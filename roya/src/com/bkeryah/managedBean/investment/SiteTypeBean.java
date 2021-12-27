package com.bkeryah.managedBean.investment;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.*;
import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.investment.SiteType;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class SiteTypeBean {
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private static final Logger logger = LogManager.getLogger(ContractDirectBean.class);
	private SiteType siteType = new SiteType();
	private List<SiteType> siteTypesList = new ArrayList<SiteType>();
	private String siteTypeName;
	private boolean delete = false;

	@PostConstruct
	public void init() {
		siteTypesList = dataAccessService.loadAllSiteTypes();
	}

	public void saveSite() {

		try {
			if (siteTypeName != null) {
				siteType.setName(siteTypeName);
				dataAccessService.save(siteType);
				siteTypesList = dataAccessService.loadAllSiteTypes();
				siteTypeName = new String();
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

	public void deleteSiteType() {
		try {
			delete = true;
			if (siteType != null && siteType.getId() != null) {

				// dataAccessService.deleteObject(mainCategory);
				dataAccessService.deleteSiteType(siteType.getId());
				siteTypesList = dataAccessService.loadAllSiteTypes();
				Utils.updateUIComponent(":includeform:LicLst");
				MsgEntry.addErrorMessage("تم الحذف" + siteType.getId());
				delete = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage("لم يتم الحذف! ... تأكد من ان التصنيف ليس عليه عقود و ليس تحته تصنيف فرعي");
		}
		siteTypesList = dataAccessService.loadAllSiteTypes();
		// return "";
	}

	public void onRowEdit(RowEditEvent event) {
		SiteType site = (SiteType) event.getObject();
		dataAccessService.updateObject(site);
		FacesMessage msg = new FacesMessage(" تم تعديل ", site.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		SiteType site = (SiteType) event.getObject();
		MsgEntry.addErrorMessage("تم الإلغاء");
	}

	///////////////////// setters and getters/////////////////////
	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public SiteType getSiteType() {
		return siteType;
	}

	public void setSiteType(SiteType siteType) {
		this.siteType = siteType;
	}

	public List<SiteType> getSiteTypesList() {
		return siteTypesList;
	}

	public void setSiteTypesList(List<SiteType> siteTypesList) {
		this.siteTypesList = siteTypesList;
	}

	public String getSiteTypeName() {
		return siteTypeName;
	}

	public void setSiteTypeName(String siteTypeName) {
		this.siteTypeName = siteTypeName;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}
}

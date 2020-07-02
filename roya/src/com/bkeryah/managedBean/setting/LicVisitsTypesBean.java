package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.bkeryah.entities.licences.LicVisitsTypes;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class LicVisitsTypesBean {
	private static Logger logger = Logger.getLogger(ItemUniteBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private LicVisitsTypes visits = new LicVisitsTypes();
	private List<LicVisitsTypes> visitsList = new ArrayList<LicVisitsTypes>();
	private String name;
	private String visitsDesc;
	private Integer type;
	private Integer daysNo;
	private List<LicVisitsTypes> filteredvisitList;

	// //days
	// private int value = 50;
	//
	// public int getValue() {
	// return value;
	// }
	//
	// public void setValue(int value) {
	// this.value = value;
	// }
	//
	// public void onChange() {
	// FacesContext.getCurrentInstance().addMessage(null,
	// new FacesMessage(FacesMessage.SEVERITY_INFO, "You have selected: " +
	// value, null));
	// }

	@PostConstruct
	public void init() {
		visitsList = dataAccessService.findAllLicVisits();
	}

	// addالاضافة
	public void saveVisits() {
		try {
			if (visits != null) {
				visits = new LicVisitsTypes();
				visits.setName(name);
				visits.setType(type);
				visits.setDaysNo(daysNo);
				visits.setVisitsDesc(visitsDesc);
				dataAccessService.save(visits);
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				visitsList = dataAccessService.findAllLicVisits();

			}

		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();

		}

	}

	// Edite
	public void updateVisits() {
		try {
			if (visits != null) {
				dataAccessService.updateObject(visits);
				visits = new LicVisitsTypes();
				logger.info("Update User: id: " + visits.getId());
				MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ العملية");
			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage("لم يتم التعديل");
		}

	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		LicVisitsTypesBean.logger = logger;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public LicVisitsTypes getVisits() {
		return visits;
	}

	public void setVisits(LicVisitsTypes visits) {
		this.visits = visits;
	}

	public List<LicVisitsTypes> getVisitsList() {
		return visitsList;
	}

	public void setVisitsList(List<LicVisitsTypes> visitsList) {
		this.visitsList = visitsList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// public String getVisitsDesc() {
	// return visitsDesc;
	// }
	//
	// public void setVisitsDesc(String visitsDesc) {
	// this.visitsDesc = visitsDesc;
	// }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<LicVisitsTypes> getFilteredvisitList() {
		return filteredvisitList;
	}

	public void setFilteredvisitList(List<LicVisitsTypes> filteredvisitList) {
		this.filteredvisitList = filteredvisitList;
	}

	public String getVisitsDesc() {
		return visitsDesc;
	}

	public void setVisitsDesc(String visitsDesc) {
		this.visitsDesc = visitsDesc;
	}

	public Integer getDaysNo() {
		return daysNo;
	}

	public void setDaysNo(Integer daysNo) {
		this.daysNo = daysNo;
	}

}

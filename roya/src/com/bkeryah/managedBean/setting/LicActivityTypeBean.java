package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.entities.LicActivityTypeRy;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;

@ManagedBean
@ViewScoped
public class LicActivityTypeBean {
	private static Logger logger = Logger.getLogger(SectionsBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private LicActivityTypeRy activity = new LicActivityTypeRy();
	private List<LicActivityTypeRy> activityList = new ArrayList<LicActivityTypeRy>();
	private List<LicActivityTypeRy> filteredactivityList;
	private String name;

	@PostConstruct
	public void init() {
		activityList = dataAccessService.getAllLicActivityTypeList();
	}

	// حــــــــــــــذف
	public void deleteActivity() {
		try {
			if (activity != null) {
				dataAccessService.deleteObject(activity);
			}
			MsgEntry.addInfoMessage("تم الحذف");
		} catch (Exception e) {

			MsgEntry.addErrorMessage("لم يتم الحذف");
		}
	}

	// add الاضافة
	public void saveActivity() {
		try {
			if (activity != null) {
				activity = new LicActivityTypeRy();
				activity.setName(name);
				dataAccessService.save(activity);
				activityList = dataAccessService.getAllLicActivityTypeList();
			}
			MsgEntry.addInfoMessage("تم الإضافة");

		} catch (Exception e) {
			MsgEntry.addErrorMessage("خطأ:- لم تتم الأضـافة");
		}

	}

	// Edit تعديل
	public void updateActivity() {
		try {
			if (activity != null) {
				dataAccessService.updateObject(activity);
				activity = new LicActivityTypeRy();
				logger.info("Update User: id: " + activity.getId());
			}
			MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ العملية");
		} catch (Exception e) {
			MsgEntry.addErrorMessage("لم يتم التعديل");
		}

	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		LicActivityTypeBean.logger = logger;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public LicActivityTypeRy getActivity() {
		return activity;
	}

	public void setActivity(LicActivityTypeRy activity) {
		this.activity = activity;
	}

	public List<LicActivityTypeRy> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<LicActivityTypeRy> activityList) {
		this.activityList = activityList;
	}

	public List<LicActivityTypeRy> getFilteredactivityList() {
		return filteredactivityList;
	}

	public void setFilteredactivityList(List<LicActivityTypeRy> filteredactivityList) {
		this.filteredactivityList = filteredactivityList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.*;
import com.bkeryah.entities.LicDistrict;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;

@ManagedBean
@ViewScoped
public class LicDistrictBean {
	private static Logger logger = LogManager.getLogger(SectionsBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private LicDistrict district = new LicDistrict();
	private List<LicDistrict> distictList = new ArrayList<LicDistrict>();
	private List<LicDistrict> filtereddistictList;
	private String name;

	@PostConstruct
	public void init() {
		distictList = dataAccessService.findAllDistrict();
	}

	// حــــــــــــــذف
	public void deleteDistrict() {
		try {
			if (district != null) {
				dataAccessService.deleteObject(district);
			}
			MsgEntry.addInfoMessage("تم الحذف");
		} catch (Exception e) {

			MsgEntry.addErrorMessage("لم يتم الحذف");
		}
	}

	// add الاضافة
	public void saveDistrict() {
		try {
			if (district != null) {
				district = new LicDistrict();
				district.setName(name);
				dataAccessService.save(district);
				distictList = dataAccessService.findAllDistrict();
			}
			MsgEntry.addInfoMessage("تم الإضافة");

		} catch (Exception e) {
			MsgEntry.addErrorMessage("خطأ:- لم تتم الأضـافة");
		}

	}

	// Edit تعديل
	public void updateDistrict() {
		try {
			if (district != null) {
				dataAccessService.updateObject(district);
				district = new LicDistrict();
				logger.info("Update User: id: " + district.getId());
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
		LicDistrictBean.logger = logger;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public LicDistrict getDistrict() {
		return district;
	}

	public void setDistrict(LicDistrict district) {
		this.district = district;
	}

	public List<LicDistrict> getDistictList() {
		return distictList;
	}

	public void setDistictList(List<LicDistrict> distictList) {
		this.distictList = distictList;
	}

	public List<LicDistrict> getFiltereddistictList() {
		return filtereddistictList;
	}

	public void setFiltereddistictList(List<LicDistrict> filtereddistictList) {
		this.filtereddistictList = filtereddistictList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

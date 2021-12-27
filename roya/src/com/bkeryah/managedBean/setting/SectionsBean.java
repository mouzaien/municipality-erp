package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.*;

import com.bkeryah.entities.WrkDept;
import com.bkeryah.entities.WrkSection;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class SectionsBean {
	private static Logger logger = LogManager.getLogger(SectionsBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private WrkSection section = new WrkSection();
	private List<WrkSection> secList = new ArrayList<WrkSection>();
	private String sectionName;
	private List<WrkDept> filteredsecList;

	@PostConstruct
	public void init() {
		secList = dataAccessService.findAllSec();
	}

	// Edit تعديل
	public void updateSection() {
		try {
			if (section != null) {
				dataAccessService.updateObject(section);
				section = new WrkSection();
				logger.info("Update User: id: " + section.getId());
			}
			MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ العملية");
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	// حــــــــــــــذف
	public void deleteSection() {
		try {
			if (section != null) {
				dataAccessService.deleteObject(section);
			}
			MsgEntry.addInfoMessage("تم الحذف");
		} catch (Exception e) {

			MsgEntry.addErrorMessage("لم يتم الحذف");
		}
	}

	// add الاضافة
	public void saveSection() {
		try {
			if (section != null) {
				section = new WrkSection();
				section.setSectionName(sectionName);
				dataAccessService.save(section);
				secList = dataAccessService.findAllSec();
			}
			MsgEntry.addInfoMessage("تم الإضافة");

		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public WrkSection getSection() {
		return section;
	}

	public void setSection(WrkSection section) {
		this.section = section;
	}

	public List<WrkSection> getSecList() {
		return secList;
	}

	public void setSecList(List<WrkSection> secList) {
		this.secList = secList;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public List<WrkDept> getFilteredsecList() {
		return filteredsecList;
	}

	public void setFilteredsecList(List<WrkDept> filteredsecList) {
		this.filteredsecList = filteredsecList;
	}
}

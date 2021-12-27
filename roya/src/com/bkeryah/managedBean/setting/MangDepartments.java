package com.bkeryah.managedBean.setting;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.logging.log4j.*;
import org.primefaces.context.RequestContext;

import java.util.ArrayList;
import java.util.List;
import com.bkeryah.entities.WrkDept2;
import com.bkeryah.entities.WrkDept;
import com.bkeryah.entities.WrkSection;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ViewScoped
@ManagedBean
public class MangDepartments {
	private static Logger logger = LogManager.getLogger(MangDepartments.class);

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAceessService;
	private WrkDept dema = new WrkDept();
	private List<WrkDept> demaList = new ArrayList<WrkDept>();
	private WrkSection secForUpdate = new WrkSection();
	private List<WrkSection> secList = new ArrayList<WrkSection>();
	private List<WrkSection> wrkSecList;
	private String deptNameE;
	private String deptIsActive;
	private List<WrkDept> filtereddemaList;
	private Integer deptId;
	private Integer selectdDeptId;
	private Integer id;
	private Integer sectionId;
	private String sectionName;
	private String depName;
	private String deptSection;
	private boolean enableAdding;

	@PostConstruct
	public void init() {
		demaList = dataAceessService.findAllDema();
		// filtereddemaList = dataAceessService.findAllDepartments2();
		// for (WrkDept2 wrkDept : filtereddemaList) {
		// System.out.println(wrkDept.getDeptName());
		// }
		secList = dataAceessService.findAllSec();

	}

	// Edit تعديل
	public void updateDema() {
		try {
			if (dema != null) {
				dataAceessService.updateObject(dema);
				dema = new WrkDept();
				logger.info("Update User: id: " + dema.getId());
			}
			MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ العملية");
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	public void showDeleteDL(WrkSection sectionSetNullDept) {
		sectionSetNullDept.setDeptId(null);
		secForUpdate = sectionSetNullDept;
		Utils.openDialog("Mo_ss");

	}

	// حــــــــــــــذف
	// بنحذف الادارة للقسم
	// deptid in WrkSection table will be Zeroooooooooooooooo
	public void deleteSection() {
		try {

			dataAceessService.updateObject(secForUpdate);
			MsgEntry.addAcceptFlashInfoMessage("تم الحذف");
			Utils.closeDialog("Mo_ss");
			wrkSecList = dataAceessService.getwrksectionByDepId(selectdDeptId);
			RequestContext context = RequestContext.getCurrentInstance();
			context.update("includeform:inboxdt");
		} catch (Exception e) {
			MsgEntry.addErrorMessage("لم يتم الحذف");
		}
	}

	// add الاضافة
	public void saveDema() {
		try {
			selectdDeptId = new Integer(selectdDeptId);
			if (sectionId != null && selectdDeptId != null) {

				WrkSection sectionData = new WrkSection();
				sectionData = dataAceessService.findSectionById(sectionId);
				sectionData.setDeptId(selectdDeptId);
				// dema = new WrkDept2();
				// dema.setDeptName(deptNameE);
				// dema.setDeptIsActive(deptIsActive);
				// dema.setDeptSection(deptSection);
				//// dema.setDepName(depName);
				dataAceessService.updateObject(sectionData);
				RequestContext context = RequestContext.getCurrentInstance();
				context.update("includeform:inboxdt");
				MsgEntry.addInfoMessage("تم الإضافة");
				wrkSecList = dataAceessService.getwrksectionByDepId(selectdDeptId);
				// demaList = dataAceessService.findAllDepartments2();
			}

		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	public void loadSectionsByDeptId(AjaxBehaviorEvent E) {

		wrkSecList = dataAceessService.getwrksectionByDepId(selectdDeptId);
		if (wrkSecList != null) {
			enableAdding = true;
		} else {
			enableAdding = false;
		}
		for (WrkSection wrksec : wrkSecList) {
			System.out.println("SectionName : " + wrksec.getSectionName());
		}
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("includeform:inboxdt");
		context = RequestContext.getCurrentInstance();
		context.update("includeform:addbtn");

	}

	public IDataAccessService getDataAceessService() {
		return dataAceessService;
	}

	public void setDataAceessService(IDataAccessService dataAceessService) {
		this.dataAceessService = dataAceessService;
	}

	public WrkDept getDema() {
		return dema;
	}

	public void setDema(WrkDept dema) {
		this.dema = dema;
	}

	public List<WrkDept> getDemaList() {
		return demaList;
	}

	public void setDemaList(List<WrkDept> demaList) {
		this.demaList = demaList;
	}

	public List<WrkSection> getSecList() {
		return secList;
	}

	public void setSecList(List<WrkSection> secList) {
		this.secList = secList;
	}

	public String getDeptNameE() {
		return deptNameE;
	}

	public void setDeptNameE(String deptNameE) {
		this.deptNameE = deptNameE;
	}

	public String getDeptIsActive() {
		return deptIsActive;
	}

	public void setDeptIsActive(String deptIsActive) {
		this.deptIsActive = deptIsActive;
	}

	public List<WrkDept> getFiltereddemaList() {
		return filtereddemaList;
	}

	public void setFiltereddemaList(List<WrkDept> filtereddemaList) {
		this.filtereddemaList = filtereddemaList;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getDeptSection() {
		return deptSection;
	}

	public void setDeptSection(String deptSection) {
		this.deptSection = deptSection;
	}

	public Integer getSelectdDeptId() {
		return selectdDeptId;
	}

	public void setSelectdDeptId(Integer selectdDeptId) {
		this.selectdDeptId = selectdDeptId;
	}

	public List<WrkSection> getWrkSecList() {
		return wrkSecList;
	}

	public void setWrkSecList(List<WrkSection> wrkSecList) {
		this.wrkSecList = wrkSecList;
	}

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public WrkSection getSecForUpdate() {
		return secForUpdate;
	}

	public void setSecForUpdate(WrkSection secForUpdate) {
		this.secForUpdate = secForUpdate;
	}

	public boolean isEnableAdding() {
		return enableAdding;
	}

	public void setEnableAdding(boolean enableAdding) {
		this.enableAdding = enableAdding;
	}

}

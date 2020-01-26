package com.bkeryah.managedBean.setting;

/**
 * @author thapet
 *
 */
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.entities.WrkDept;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class Departments {
	private static Logger logger = Logger.getLogger(Departments.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private WrkDept department = new WrkDept();
	private List<WrkDept> depList = new ArrayList<WrkDept>();
	private String deptNameE;
	private String deptIsActive;
	private List<WrkDept> filtereddepList;

	@PostConstruct
	public void init() {
		depList = dataAccessService.findAllDema();
	}

	// Edit تعديل
	public void updateDepartment() {
		try {
			if (department != null) {
				dataAccessService.updateObject(department);
				department = new WrkDept();
				logger.info("Update User: id: " + department.getId());
			}
			MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ العملية");
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	// حــــــــــــــذف
	public void deleteDepartment() {
		try {
			if (department != null) {
				dataAccessService.deleteObject(department);
				// // department = new WrkDept2();

			}
			MsgEntry.addInfoMessage("تم الحذف");
		} catch (Exception e) {

			MsgEntry.addErrorMessage("لم يتم الحذف");
		}
	}

	// add الاضافة
	public void saveDepartment() {
		try {
			if (department != null) {
				department = new WrkDept();
				department.setDeptName(deptNameE);
				department.setDeptIsActive(deptIsActive);
				dataAccessService.save(department);
				depList = dataAccessService.findAllDema();
			}
			MsgEntry.addInfoMessage("تم الإضافة");

		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public WrkDept getDepartment() {
		return department;
	}

	public void setDepartment(WrkDept department) {
		this.department = department;
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

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		Departments.logger = logger;
	}

	public List<WrkDept> getDepList() {
		return depList;
	}

	public void setDepList(List<WrkDept> depList) {
		this.depList = depList;
	}

	public List<WrkDept> getFiltereddepList() {
		return filtereddepList;
	}

	public void setFiltereddepList(List<WrkDept> filtereddepList) {
		this.filtereddepList = filtereddepList;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

}

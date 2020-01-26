package com.bkeryah.managedBean.setting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.entities.HrsUserAbsent;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class PromotionAskedBean {
	private static Logger logger = Logger.getLogger(PromotionAskedBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private User emp = new User();
	private List<User> empList = new ArrayList<User>();
	private HrsUserAbsent prom = new HrsUserAbsent();
	private List<HrsUserAbsent> promList = new ArrayList<HrsUserAbsent>();
	private String firstName;
	private Integer userId;
	private Integer empNo;
	private Date absForm;
	private Date absTo;
	private boolean higriMode;
	private Date abseDate;
	private Date jabsDate;
	private String employeeName;
	private List<HrsUserAbsent> filteredpromList;
	private String hirDate;

	@PostConstruct
	public void init() {
		empList = dataAccessService.getAllUsers();
		promList = dataAccessService.findAllAllProm();
	}

	/// Insert1
	public void saveProm() {
		try {
			// sortDates();
			if (prom != null) {
				prom.setUserId(prom.getUserIdT());
				if (prom.getUserId() != null) {
					emp = dataAccessService.getUserById(prom.getUserId());
					// prom.setEmployeeName(emp.getFirstName());
					prom.setEmpNo(emp.getEmployeeNumber());
				}
				SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
				prom.setAbsForm(localDateFormat.format(prom.getAbsFormT()));
				prom.setAbsTo(localDateFormat.format(prom.getAbsToT()));
				// prom.setAbseDate(Utils.grigDatesConvert(jabsDate));
				if (higriMode == true) {
					Date gDate = Utils.convertHDateToGDate(hirDate);
					prom.setJabsDate(localDateFormat.format(gDate));
					prom.setAbseDate(hirDate); // هجري
				} else {
					localDateFormat = new SimpleDateFormat("dd/MM/yyyy");
					prom.setJabsDate(localDateFormat.format(prom.getJabsDateT())); // ميلادي
					// convert from g to h
					prom.setAbseDate(Utils.grigDatesConvert(prom.getJabsDateT()));
				}
				// if (prom.getUserId() != null) {
				dataAccessService.saveObject(prom);
				promList = dataAccessService.findAllAllProm();
				MsgEntry.addInfoMessage("تم الإضافة");

				// }

			}

		} catch (Exception e) {
			MsgEntry.addErrorMessage("خطأ:لم يتم الإضافة");
			e.printStackTrace();
		}

	}

	public void update() throws Exception {
		this.jabsDate = Utils.convertGregStringToDate(prom.getJabsDate()); // ميلادي
		this.hirDate = prom.getAbseDate();
		System.out.println("prom.getAbseDate()>>>>>>" + jabsDate);
		this.absForm = Utils.convertStringHourToTimeDate(prom.getAbsForm());
		this.absTo = Utils.convertStringHourToTimeDate(prom.getAbsTo());
		// this.prom= Utils.convertDateToString());
		Utils.openDialog("update_dlg");

	}

	// Update
	public void updateProm() {
		try {
			if (prom != null) {
				SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
				prom.setAbsForm(localDateFormat.format(absForm));
				prom.setAbsTo(localDateFormat.format(absTo));

				localDateFormat = new SimpleDateFormat("dd/MM/yyyy");
				if (higriMode == true) {
					Date gDate = Utils.convertHDateToGDate(hirDate);
					prom.setJabsDate(localDateFormat.format(gDate));
					prom.setAbseDate(hirDate); // هجري

				} else {
					prom.setJabsDate(localDateFormat.format(jabsDate)); // ميلادي
					// convert from g to h
					prom.setAbseDate(Utils.grigDatesConvert(jabsDate));
				}
				dataAccessService.updateObject(prom);
				prom = new HrsUserAbsent();
				logger.info("update User:id:" + prom.getId());
				MsgEntry.addInfoMessage("تم التعديل");
				absForm = null;
				absTo = null;
				jabsDate = new Date();

			}

		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
			e.printStackTrace();
		}

	}

	// delete
	public void deleteProm() {
		try {
			if (prom != null) {
				dataAccessService.deleteObject(prom);
				MsgEntry.addInfoMessage("تم الحذف");
			}

		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
			e.printStackTrace();
		}

	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		PromotionAskedBean.logger = logger;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public User getEmp() {
		return emp;
	}

	public void setEmp(User emp) {
		this.emp = emp;
	}

	public List<User> getEmpList() {
		return empList;
	}

	public void setEmpList(List<User> empList) {
		this.empList = empList;
	}

	public HrsUserAbsent getProm() {
		return prom;
	}

	public void setProm(HrsUserAbsent prom) {
		this.prom = prom;
	}

	public List<HrsUserAbsent> getPromList() {
		return promList;
	}

	public void setPromList(List<HrsUserAbsent> promList) {
		this.promList = promList;
	}

	public List<HrsUserAbsent> getFilteredpromList() {
		return filteredpromList;
	}

	public void setFilteredpromList(List<HrsUserAbsent> filteredpromList) {
		this.filteredpromList = filteredpromList;
	}

	public Integer getEmpNo() {
		return empNo;
	}

	public void setEmpNo(Integer empNo) {
		this.empNo = empNo;
	}

	// public String getAbseDate() {
	// return abseDate;
	// }
	//
	// public void setAbseDate(String abseDate) {
	// this.abseDate = abseDate;
	// }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getJabsDate() {
		return jabsDate;
	}

	public void setJabsDate(Date jabsDate) {
		this.jabsDate = jabsDate;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public boolean isHigriMode() {
		return higriMode;
	}

	public void setHigriMode(boolean higriMode) {
		this.higriMode = higriMode;
	}

	// public boolean isUpdateMode() {
	// return updateMode;
	// }
	//
	// public void setUpdateMode(boolean updateMode) {
	// this.updateMode = updateMode;
	// }

	public void setAbsForm(Date absForm) {
		this.absForm = absForm;
	}

	public void setAbsTo(Date absTo) {
		this.absTo = absTo;
	}

	public Date getAbsForm() {
		return absForm;
	}

	public Date getAbsTo() {
		return absTo;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getAbseDate() {
		return abseDate;
	}

	public void setAbseDate(Date abseDate) {
		this.abseDate = abseDate;
	}

	public String getHirDate() {
		hirDate = prom.getAbseDate();
		return hirDate;
	}

	public void setHirDate(String hirDate) {
		this.hirDate = hirDate;
	}

}

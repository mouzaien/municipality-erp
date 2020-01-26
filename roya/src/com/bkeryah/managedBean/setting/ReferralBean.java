package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.entities.WrkRefrentionalSetting;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class ReferralBean {
	private static Logger logger = Logger.getLogger(AttachmentsBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private WrkRefrentionalSetting referral = new WrkRefrentionalSetting();
	private List<WrkRefrentionalSetting> referralList = new ArrayList<WrkRefrentionalSetting>();
//	private User users = new User();
	private List<User> userList = new ArrayList<User>();
	private List<WrkRefrentionalSetting> filteredreferralList;
	private String DeptName;
	private String DeptTitle;
	private String employeeName;
	private String firstName;
	private Integer managerId;

	@PostConstruct
	public void init() {
		referralList = dataAccessService.findAllReferral();
		userList = dataAccessService.getAllUsers();
	}

	// Edit
	public void updateReferral() {
		try {
			if (referral != null) {
				dataAccessService.updateObject(referral);
				referral = new WrkRefrentionalSetting();
				logger.info("Update User: id: " + referral.getId());
			}
			MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ العملية");
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	// DElETE
	public void deleteReferral() {
		try {
			if (referral != null) {
				dataAccessService.deleteObject(referral);
				referral = new WrkRefrentionalSetting();

			}
			MsgEntry.addInfoMessage("تم الحذف");
		} catch (Exception e) {

			MsgEntry.addErrorMessage("لم يتم الحذف");
		}
	}

	// add
	public void saveReferral() {
		try {
			if (referral != null) {
				referral = new WrkRefrentionalSetting();
				referral.setDeptName(DeptName);
				referral.setDeptTitle(DeptTitle);
				referral.setManagerId(managerId); 
				dataAccessService.save(referral);
				referralList = dataAccessService.findAllReferral();
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

	public WrkRefrentionalSetting getReferral() {
		return referral;
	}

	public void setReferral(WrkRefrentionalSetting referral) {
		this.referral = referral;
	}

	public List<WrkRefrentionalSetting> getReferralList() {
		return referralList;
	}

	public void setReferralList(List<WrkRefrentionalSetting> referralList) {
		this.referralList = referralList;
	}

	public List<WrkRefrentionalSetting> getFilteredreferralList() {
		return filteredreferralList;
	}

	public void setFilteredreferralList(List<WrkRefrentionalSetting> filteredreferralList) {
		this.filteredreferralList = filteredreferralList;
	}

	public String getDeptName() {
		return DeptName;
	}

	public void setDeptName(String deptName) {
		DeptName = deptName;
	}

	public String getDeptTitle() {
		return DeptTitle;
	}

	public void setDeptTitle(String deptTitle) {
		DeptTitle = deptTitle;
	}

//	public User getUsers() {
//		return users;
//	}
//
//	public void setUsers(User users) {
//		this.users = users;
//	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}

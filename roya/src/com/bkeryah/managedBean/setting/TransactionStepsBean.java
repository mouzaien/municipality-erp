package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.*;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class TransactionStepsBean {
	private static Logger logger = LogManager.getLogger(WrCommentBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<WrkApplication> stepsList = new ArrayList<WrkApplication>();
	private User users = new User();
	private List<User> userList = new ArrayList<User>();
	private WrkApplication steps = new WrkApplication();
	private List<WrkApplication> filteredstepsList;
	// private Integer applicationIsVisible;
	private Integer userId;

	@PostConstruct
	public void init() {
		stepsList = dataAccessService.findAllSteps();
		userList = dataAccessService.getAllUsers();
	}

	public void updateSteps() {
		try {
			if (steps != null) {

				dataAccessService.updateObject(steps);
				steps = new WrkApplication();
				MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ العملية");
			}

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

	public List<WrkApplication> getStepsList() {
		return stepsList;
	}

	public void setStepsList(List<WrkApplication> stepsList) {
		this.stepsList = stepsList;
	}

	public WrkApplication getSteps() {
		return steps;
	}

	public void setSteps(WrkApplication steps) {
		this.steps = steps;
	}

	public List<WrkApplication> getFilteredstepsList() {
		return filteredstepsList;
	}

	public void setFilteredstepsList(List<WrkApplication> filteredstepsList) {
		this.filteredstepsList = filteredstepsList;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;

	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
}
package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.UserRoles;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class UserRoleBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private Integer employerId;
	private List<String> selectedRoles;
	private ArcUsers selectedUser;
	protected static final Logger logger = Logger.getLogger(UserRoleBean.class);

	@PostConstruct
	public void init() {

	}
	
	public void loadUserRoles(){
		selectedRoles = new ArrayList<>();
		selectedUser = dataAccessService.loadUserById(employerId);
		if(selectedUser.getRoles() != null){
			for(UserRoles role : selectedUser.getRoles()){
				selectedRoles.add(""+role.getId());
			}
		}
		
	}
	
	public String saveAction() {
		try{
			dataAccessService.updateUserRoles(selectedUser, selectedRoles);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("saveAction	" + e.getMessage());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}	
	
	

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public List<String> getSelectedRoles() {
		return selectedRoles;
	}

	public void setSelectedRoles(List<String> selectedRoles) {
		this.selectedRoles = selectedRoles;
	}

	public ArcUsers getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(ArcUsers selectedUser) {
		this.selectedUser = selectedUser;
	}
}

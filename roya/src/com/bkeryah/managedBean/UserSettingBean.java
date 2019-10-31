/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.bkeryah.bean.SysTitleClass;
import com.bkeryah.bean.UserClass;
import com.bkeryah.bean.WrkDeptClass;
import com.bkeryah.bean.WrkJobClass;
import com.bkeryah.bean.WrkRolesClass;
import com.bkeryah.bean.WrkSectionClass;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

/**
 *
 * @author Ibrahimdarwiesh
 */
@ManagedBean
@ViewScoped
public class UserSettingBean implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//    private DataAccess da = new DataAccessImpl();
	@ManagedProperty(value="#{dataAccessService}")
	private IDataAccessService dataAccessService;
    private List<UserClass> allUsers = new ArrayList<UserClass>();
    private UserClass selectedUser = new UserClass();
    
    Map<String, String> DeptsMap = new HashMap<String, String>();
    Map<String, String> RolesMap = new HashMap<String, String>();
    Map<String, String> titlesMap = new HashMap<String, String>();
    Map<String, String> ManagersMap = new HashMap<String, String>();
    Map<String, String> SectionsMap = new HashMap<String, String>();
    Map<String, String> JobssMap = new HashMap<String, String>();
    Map<String, String> EditSectionsMap = new HashMap<String, String>();
    Map<String, String> EditJobssMap = new HashMap<String, String>();
    
    private String NewUserLoginName;
    private String NewUserPassword;
    private String NewUserFirstName;
    private String NewUserLastName;
    private int NewUserTitleId;
    private int NewUserJobId;
    private int NewUserDeptId;
    private int NewUserManagerId;
    private int NewUserWrkRoleId;
    private int NewSectionId;
    private String NewUserMobileNumber;
    private String newPwdString;
    private int EditNewUserJobId;
    private int EditNewUserDeptId;
    private int EditNewUserManagerId;
    private int EditNewUserWrkRoleId;
    private int EditNewSectionId;
    private String EditnewFirstName;
    private String EditnewLastName;
    private String EditnewArName;
    private String EditnewEnName;
    public String getNewPwdString() {
        return newPwdString;
    }

    public String getEditnewFirstName() {
        return EditnewFirstName;
    }

    public void setEditnewFirstName(String EditnewFirstName) {
        this.EditnewFirstName = EditnewFirstName;
    }

    public String getEditnewLastName() {
        return EditnewLastName;
    }

    public void setEditnewLastName(String EditnewLastName) {
        this.EditnewLastName = EditnewLastName;
    }

    public String getEditnewArName() {
        return EditnewArName;
    }

    public void setEditnewArName(String EditnewArName) {
        this.EditnewArName = EditnewArName;
    }

    public String getEditnewEnName() {
        return EditnewEnName;
    }

    public void setEditnewEnName(String EditnewEnName) {
        this.EditnewEnName = EditnewEnName;
    }

    
    public void setNewPwdString(String newPwdString) {
        this.newPwdString = newPwdString;
    }

    public Map<String, String> getEditSectionsMap() {
        return EditSectionsMap;
    }

    public void setEditSectionsMap(Map<String, String> EditSectionsMap) {
        this.EditSectionsMap = EditSectionsMap;
    }

    public Map<String, String> getEditJobssMap() {
        return EditJobssMap;
    }

    public void setEditJobssMap(Map<String, String> EditJobssMap) {
        this.EditJobssMap = EditJobssMap;
    }

    public int getEditNewUserJobId() {
        return EditNewUserJobId;
    }

    public void setEditNewUserJobId(int EditNewUserJobId) {
        this.EditNewUserJobId = EditNewUserJobId;
    }

    public int getEditNewUserDeptId() {
        return EditNewUserDeptId;
    }

    public void setEditNewUserDeptId(int EditNewUserDeptId) {
        this.EditNewUserDeptId = EditNewUserDeptId;
    }

    public int getEditNewUserManagerId() {
        return EditNewUserManagerId;
    }

    public void setEditNewUserManagerId(int EditNewUserManagerId) {
        this.EditNewUserManagerId = EditNewUserManagerId;
    }

    public int getEditNewUserWrkRoleId() {
        return EditNewUserWrkRoleId;
    }

    public void setEditNewUserWrkRoleId(int EditNewUserWrkRoleId) {
        this.EditNewUserWrkRoleId = EditNewUserWrkRoleId;
    }

    public int getEditNewSectionId() {
        return EditNewSectionId;
    }

    public void setEditNewSectionId(int EditNewSectionId) {
        this.EditNewSectionId = EditNewSectionId;
    }

    /**
     * Creates a new instance of UserSettingBean
     */
    public List<UserClass> getAllUsers() {
        return this.dataAccessService.findAllUsers();
    }

    public void setAllUsers(List<UserClass> allUsers) {
        this.allUsers = allUsers;
    }

    public UserClass getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(UserClass selectedUser) {
        this.selectedUser = selectedUser;
    }

    public Map<String, String> getDeptsMap() {
        List<WrkDeptClass> list = this.dataAccessService.findAllDepartment();
        Map<String, String> ReturnMap = new LinkedHashMap<String, String>();
        for (WrkDeptClass item : list) {
            ReturnMap.put(item.getDeptName(), String.valueOf(item.getDeptId()));
        }
        return ReturnMap;
    }

    public void setDeptsMap(Map<String, String> DeptsMap) {
        this.DeptsMap = DeptsMap;
    }

    public Map<String, String> getRolesMap() {
        List<WrkRolesClass> list = this.dataAccessService.findAllRoles();
        Map<String, String> ReturnMap = new LinkedHashMap<String, String>();
        for (WrkRolesClass item : list) {
            ReturnMap.put(item.getRoleName(), String.valueOf(item.getRoleId()));
        }
        return ReturnMap;
    }

    public void setRolesMap(Map<String, String> RolesMap) {
        this.RolesMap = RolesMap;
    }

    public Map<String, String> getTitlesMap() {
        List<SysTitleClass> list = this.dataAccessService.findAllSysTitles();
        Map<String, String> ReturnMap = new LinkedHashMap<String, String>();
        for (SysTitleClass item : list) {
            ReturnMap.put(item.getTitle(), String.valueOf(item.getTitleId()));
        }
        return ReturnMap;
    }

    public void setTitlesMap(Map<String, String> titlesMap) {
        this.titlesMap = titlesMap;
    }

    public Map<String, String> getManagersMap() {
        List<UserClass> list = this.dataAccessService.findAllUsers();
        Map<String, String> ReturnMap = new LinkedHashMap<String, String>();
        for (UserClass item : list) {
            ReturnMap.put(item.getVusers_real_name(), String.valueOf(item.getVuser_id()));
        }
        return ReturnMap;
    }

    public void setManagersMap(Map<String, String> ManagersMap) {
        this.ManagersMap = ManagersMap;
    }

    public Map<String, String> getSectionsMap() {
        return SectionsMap;
    }

    public void setSectionsMap(Map<String, String> SectionsMap) {
        this.SectionsMap = SectionsMap;
    }

    public Map<String, String> getJobssMap() {
        return JobssMap;
    }

    public void setJobssMap(Map<String, String> JobssMap) {
        this.JobssMap = JobssMap;
    }

    public int getNewSectionId() {
        return NewSectionId;
    }

    public void setNewSectionId(int NewSectionId) {
        this.NewSectionId = NewSectionId;
    }

    public int getNewUserDeptId() {
        return NewUserDeptId;
    }

    public void setNewUserDeptId(int NewUserDeptId) {
        this.NewUserDeptId = NewUserDeptId;
    }

    public String getNewUserLoginName() {
        return NewUserLoginName;
    }

    public void setNewUserLoginName(String NewUserLoginName) {
        this.NewUserLoginName = NewUserLoginName;
    }

    public String getNewUserPassword() {
        return NewUserPassword;
    }

    public void setNewUserPassword(String NewUserPassword) {
        this.NewUserPassword = NewUserPassword;
    }

    public String getNewUserFirstName() {
        return NewUserFirstName;
    }

    public void setNewUserFirstName(String NewUserFirstName) {
        this.NewUserFirstName = NewUserFirstName;
    }

    public String getNewUserLastName() {
        return NewUserLastName;
    }

    public void setNewUserLastName(String NewUserLastName) {
        this.NewUserLastName = NewUserLastName;
    }

    public int getNewUserTitleId() {
        return NewUserTitleId;
    }

    public void setNewUserTitleId(int NewUserTitleId) {
        this.NewUserTitleId = NewUserTitleId;
    }

    public int getNewUserJobId() {
        return NewUserJobId;
    }

    public void setNewUserJobId(int NewUserJobId) {
        this.NewUserJobId = NewUserJobId;
    }

    public int getNewUserManagerId() {
        return NewUserManagerId;
    }

    public void setNewUserManagerId(int NewUserManagerId) {
        this.NewUserManagerId = NewUserManagerId;
    }

    public int getNewUserWrkRoleId() {
        return NewUserWrkRoleId;
    }

    public void setNewUserWrkRoleId(int NewUserWrkRoleId) {
        this.NewUserWrkRoleId = NewUserWrkRoleId;
    }

    public String getNewUserMobileNumber() {
        return NewUserMobileNumber;
    }

    public void setNewUserMobileNumber(String NewUserMobileNumber) {
        this.NewUserMobileNumber = NewUserMobileNumber;
    }

    /**
     * ************************************************
     */
    public UserSettingBean() {
//        if (this.da == null) {
//            this.da = new DataAccessImpl();
//
//        }
    }

    public void createUserBtnAction(ActionEvent actionEvent) {
        Utils.openDialog("createUserDlg");
    }

    public void createUserfooterBtnAction(ActionEvent actionEvent) {
    	Utils.closeDialog("createUserDlg");
    }

    public void editUserBtnAction(ActionEvent actionEvent) {
        //this.da.showMessage(this.selectedUser.getVuser_id() + "");
    	Utils.openDialog("editUserDlg");
    }

    public void editUserfooterBtnAction(ActionEvent actionEvent) {
    	Utils.closeDialog("editUserDlg");
    }

    public void moveUserBtnAction(ActionEvent actionEvent) {
        MsgEntry.addInfoMessage(selectedUser.getVuser_id() + "");
        Utils.openDialog("moveUserDlg");
    }

    public void moveUserfooterBtnAction(ActionEvent actionEvent) {
    	Utils.closeDialog("moveUserDlg");
    }

    public void favUserBtnAction(ActionEvent actionEvent) {
    	 MsgEntry.addInfoMessage(this.selectedUser.getVuser_id() + "");
        Utils.openDialog("favUserDlg");
    }

    public void favUserfooterBtnAction(ActionEvent actionEvent) {
    	Utils.closeDialog("favUserDlg");
    }

    public void deptListChangeEvent(AjaxBehaviorEvent ajaxBehaviorEvent) {
        List<WrkSectionClass> list = this.dataAccessService.findAllSectionsByDept(this.NewUserDeptId);
        Map<String, String> ReturnMap = new LinkedHashMap<String, String>();
        for (WrkSectionClass item : list) {
            ReturnMap.put(item.getWrkSectionName(), String.valueOf(item.getWrkSectionId()));
        }
        this.JobssMap = null;
        this.SectionsMap = ReturnMap;
    }

    public void deptEditListChangeEvent(AjaxBehaviorEvent ajaxBehaviorEvent) {
        List<WrkSectionClass> list = this.dataAccessService.findAllSectionsByDept(this.EditNewUserDeptId);
        System.err.println(list.size() + "--KOKOWAWA---<<");
        Map<String, String> ReturnMap = new LinkedHashMap<String, String>();
        for (WrkSectionClass item : list) {
            ReturnMap.put(item.getWrkSectionName(), String.valueOf(item.getWrkSectionId()));
        }
        this.EditJobssMap = null;
        this.EditSectionsMap = ReturnMap;
    }

    public void sectionListChangeEvent(AjaxBehaviorEvent ajaxBehaviorEvent) {
        List<WrkJobClass> list = this.dataAccessService.findAllJobsBySection(this.NewSectionId);
        Map<String, String> ReturnMap = new LinkedHashMap<String, String>();
        for (WrkJobClass item : list) {
            ReturnMap.put(item.getJobName(), String.valueOf(item.getJobId()));
        }
        this.JobssMap = ReturnMap;
    }

    public void sectionEditListChangeEvent(AjaxBehaviorEvent ajaxBehaviorEvent) {
        List<WrkJobClass> list = this.dataAccessService.findAllJobsBySection(this.EditNewSectionId);
        Map<String, String> ReturnMap = new LinkedHashMap<String, String>();
        for (WrkJobClass item : list) {
            ReturnMap.put(item.getJobName(), String.valueOf(item.getJobId()));
        }
        this.EditJobssMap = ReturnMap;
    }

    public void saveUserBtnAction(ActionEvent actionEvent) {
        System.err.println(
                this.NewUserLoginName + this.NewUserPassword + this.NewUserFirstName + this.NewUserLastName
                + this.NewUserTitleId + this.NewUserJobId + this.NewUserDeptId + this.NewUserManagerId + this.NewUserWrkRoleId
                + this.NewSectionId + this.NewUserMobileNumber);

        int returNewUserId = this.dataAccessService.createNewUser(this.NewUserLoginName, this.NewUserPassword, this.NewUserFirstName, this.NewUserLastName,
                this.NewUserTitleId, this.NewUserJobId, this.NewUserDeptId, this.NewUserManagerId, this.NewUserWrkRoleId,
                this.NewSectionId, this.NewUserMobileNumber);
        if (returNewUserId > 0) {
            this.NewUserLoginName = null;
            this.NewUserPassword = null;
            this.NewUserFirstName = null;
            this.NewUserLastName = null;
            this.NewUserTitleId = 0;
            this.NewUserJobId = 0;
            this.NewUserDeptId = 0;
            this.NewUserManagerId = 0;
            this.NewUserWrkRoleId = 0;
            this.NewSectionId = 0;
            this.NewUserMobileNumber = null;

            //this.da.showMessage("تم حفظ المستخدم بنجاح");
            Utils.closeDialog("createUserDlg");
            MsgEntry.showModalDialog(MsgEntry.SUCCESS_SAVE_USER);
        } else {
            MsgEntry.addErrorMessage(MsgEntry.ERROR_SAVE_USER);
        }
    }

    public void updateUserpasswordBtnAction(ActionEvent actionEvent) {
        if (this.selectedUser.getVuser_id() < 0) {
            MsgEntry.addErrorMessage(MsgEntry.ERROR_CHOOSE_USER);
        } else {
        	MsgEntry.addInfoMessage(this.dataAccessService.updateUserPassword(this.selectedUser.getVuser_id(), this.newPwdString));
            this.newPwdString = null;
        }
    }

    public void updateUserDeptInfo(ActionEvent actionEvent) {
        if (this.selectedUser.getVuser_id() > 0) {
            MsgEntry.showModalDialog(this.dataAccessService.updateUserInfo(this.selectedUser.getVuser_id(),
                    this.EditNewUserManagerId, this.EditNewUserWrkRoleId, this.EditNewUserDeptId, this.EditNewSectionId, this.EditNewUserJobId));
            this.EditNewUserManagerId = 0;
            this.EditNewUserWrkRoleId = 0;
            this.EditNewUserDeptId = 0;
            this.EditNewSectionId = 0;
            this.EditNewUserJobId = 0;
        } else {
            MsgEntry.addErrorMessage(MsgEntry.ERROR_CHOOSE_USER);
        }
    }
    public void updateUserNameBtnAction(ActionEvent actionEvent){
        MsgEntry.showModalDialog(this.dataAccessService.updateUserNameInfo(this.selectedUser.getVuser_id(), this.EditnewFirstName, this.EditnewLastName, this.EditnewArName, this.EditnewEnName));
        
    }

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}
}

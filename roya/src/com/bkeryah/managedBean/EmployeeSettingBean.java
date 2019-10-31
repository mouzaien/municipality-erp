/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import com.bkeryah.bean.UserFolderClass;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

/**
 *
 * @author darwiesh
 */
@ManagedBean
@ViewScoped
public class EmployeeSettingBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Creates a new instance of EmployeeSettingBean
     */
	@ManagedProperty(value="#{dataAccessService}")
	private IDataAccessService dataAccessService;
    //private Map<String, String> userFolders = new LinkedHashMap<String, String>();
    private List<UserFolderClass> userFolders  = new ArrayList<UserFolderClass>();
//    private DataAccess da = new DataAccessImpl();
    private String NewFolderName ;
    private UserFolderClass selectedFolder;
    private String NewPassword;
    private String newSignPassword;
    
    public EmployeeSettingBean() {
//        if (this.da ==null){
//         da = new DataAccessImpl();   
//        }
    }

    public String getNewSignPassword() {
        return newSignPassword;
    }

    public void setNewSignPassword(String newSignPassword) {
        this.newSignPassword = newSignPassword;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String NewPassword) {
        this.NewPassword = NewPassword;
    }

    public UserFolderClass getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(UserFolderClass selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    public List<UserFolderClass> getUserFolders() {
        userFolders = this.dataAccessService.findAllUserFolders(Utils.findCurrentUser().getUserId());
        return userFolders;
    }

    public void setUserFolders(List<UserFolderClass> userFolders) {
        this.userFolders = userFolders;
    }

    public String getNewFolderName() {
        return NewFolderName;
    }

    public void setNewFolderName(String NewFolderName) {
        this.NewFolderName = NewFolderName;
    }

   public void saveFolder(ActionEvent actionEvent){
       this.dataAccessService.addNewUserFolder(this.NewFolderName, Utils.findCurrentUser().getUserId());
   } 
   public  void deleteFolderAction(ActionEvent actionEvent){
       MsgEntry.showModalDialog(this.dataAccessService.deleteUserFolder(selectedFolder.getFolderId()));
   }
    
   public  void changeUserPassword(ActionEvent actionEvent){
       if (this.NewPassword == null || this.NewPassword.length()<=0){
           MsgEntry.addErrorMessage(MsgEntry.ERROR_PWD);
       }
       else{
           
           MsgEntry.addInfoMessage(this.dataAccessService.updateUserPassword(Utils.findCurrentUser().getUserId(), this.NewPassword));
       }
   }
   
      public  void changeUserSignPassword(ActionEvent actionEvent){
       if (this.newSignPassword == null || this.newSignPassword.length()<=0){
           MsgEntry.addErrorMessage(MsgEntry.ERROR_PWD);
       }
       else{
           
           MsgEntry.addInfoMessage(this.dataAccessService.updateUserSignPassword(Utils.findCurrentUser().getUserId(), this.newSignPassword));
       }
   }

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}
    
}

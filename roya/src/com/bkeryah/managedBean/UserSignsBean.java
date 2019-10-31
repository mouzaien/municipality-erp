/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;

import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;

/**
 *
 * @author darwiesh
 */
@ManagedBean
@ViewScoped
public class UserSignsBean implements Serializable {

    /**
	 * 
	 */
	@ManagedProperty(value="#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private static final long serialVersionUID = 1L;
	private Map<String, String> allUserMap = new HashMap<String, String>();
//    private DataAccess da = new DataAccessImpl();
    private int selectedUser;
    private String SignLink;
    private String markLink;
    private String SignRefLink;

    public int getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(int selectedUser) {
        this.selectedUser = selectedUser;
    }

    public Map<String, String> getAllUserMap() {
    	if((allUserMap == null) || (allUserMap.isEmpty()))
    		allUserMap = this.dataAccessService.findUsersMap();
        return allUserMap;
    }

    public void setAllUserMap(Map<String, String> allUserMap) {
        this.allUserMap = allUserMap;
    }

    public String getSignLink() {
        return SignLink;
    }

    public void setSignLink(String SignLink) {
        this.SignLink = SignLink;
    }

    public String getMarkLink() {
        return markLink;
    }

    public void setMarkLink(String markLink) {
        this.markLink = markLink;
    }

    public String getSignRefLink() {
        return SignRefLink;
    }

    public void setSignRefLink(String SignRefLink) {
        this.SignRefLink = SignRefLink;
    }

    /**
     * Creates a new instance of UserSignsBean
     */
    public UserSignsBean() {
    }

    public void showSignesBtnAction(ActionEvent actionEvent) {
        MsgEntry.addErrorMessage("" + this.selectedUser);
        this.SignLink = "http://" + this.dataAccessService.findSystemProperty("APPS_SERVER") + ":8080/b/usersign?uid=" + this.selectedUser + "&styp=1";//;
        this.markLink = "http://" + this.dataAccessService.findSystemProperty("APPS_SERVER") + ":8080/b/usersign?uid=" + this.selectedUser + "&styp=2";//;
        this.SignRefLink = "http://" + this.dataAccessService.findSystemProperty("APPS_SERVER") + ":8080/b/usersign?uid=" + this.selectedUser + "&styp=3";//;
    }

    public void uploadSign(FileUploadEvent event) {
        if (this.selectedUser == 0) {
        	MsgEntry.addErrorMessage(MsgEntry.ERROR_SELECT_USER);
        } else {
            try {

            	MsgEntry.addInfoMessage(this.dataAccessService.updateUserSign(this.selectedUser, event.getFile().getInputstream()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    public void uploadMark(FileUploadEvent event) {
        if (this.selectedUser == 0) {
        	MsgEntry.addErrorMessage(MsgEntry.ERROR_SELECT_USER);
        }else {
            try {

            	MsgEntry.addInfoMessage(this.dataAccessService.updateUserMark(this.selectedUser, event.getFile().getInputstream()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void uploadRefSign(FileUploadEvent event) {
        if (this.selectedUser == 0) {
        	MsgEntry.addErrorMessage(MsgEntry.ERROR_SELECT_USER);
        }else {
            try {

            	MsgEntry.addInfoMessage(this.dataAccessService.updateUserRefSign(this.selectedUser, event.getFile().getInputstream()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

}

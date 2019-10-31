/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.dao.DataAccess;
import com.bkeryah.dao.DataAccessImpl;

import utilities.Utils;

/**
 *
 * @author Ibrahimdarwiesh
 */
@ManagedBean(name = "userarchivingbean")
@ViewScoped
public class UserArchivingBean implements Serializable {

    private List<UserMailObj> UserMail;
    private List<UserMailObj> selectedUserMail;
    private DataAccess da = new DataAccessImpl();

    public List<UserMailObj> getUserMail() {
        this.UserMail=da.findEmployeeInbox(null, null, Utils.findCurrentUser().getUserId());
        return UserMail;
    }

    public void setUserMail(List<UserMailObj> UserMail) {
        this.UserMail = UserMail;
    }

    public List<UserMailObj> getSelectedUserMail() {
        return selectedUserMail;
    }

    public void setSelectedUserMail(List<UserMailObj> selectedUserMail) {
        this.selectedUserMail = selectedUserMail;
    }

    /**
     * Creates a new instance of UserArchivingBean
     */
   

    /**
     * ***********************************************************************
     */
    public UserArchivingBean() {
        if (this.da == null) {
            this.da = new DataAccessImpl();
        }        
    }
    public  void showselected(ActionEvent actionEvent){
        System.err.println(""+this.selectedUserMail.size());
    }

}

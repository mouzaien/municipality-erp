/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import utilities.MsgEntry;

/**
 *
 * @author darwiesh
 */
@ManagedBean
@ViewScoped
public class UserRecordSearch {

    /**
     * Creates a new instance of UserRecordSearch
     */
//    DataAccess da = new DataAccessImpl();
    public UserRecordSearch() {
//        if(this.da == null){
//            da = new DataAccessImpl();
//        }
    }
    public void searchBtnAction(ActionEvent actionEvent){
        MsgEntry.showModalDialog("اهلا بوائل");
    }
    
}

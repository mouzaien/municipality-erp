/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import com.bkeryah.bean.WrkChargingClass;
import com.bkeryah.dao.DataAccess;
import com.bkeryah.dao.DataAccessImpl;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

/**
 *
 * @author darwiesh
 */
@ManagedBean
@ViewScoped
public class ChargingBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, String> AllUserList = new HashMap<String, String>();
    private Map<String, String> AllUserRoles = new HashMap<String, String>();
    private List<WrkChargingClass> chargingList = new ArrayList<WrkChargingClass>();
//    private DataAccess da = new DataAccessImpl();
    @ManagedProperty(value="#{dataAccessService}")
	private IDataAccessService dataAccessService;
    private int whoIscharged;
    private int whoIsNotHere;
    private String chargingName;
    private String chargingStartDate;
    private String chargingEndDate;
    private int chargingPrivilage;

    /**
     * Creates a new instance of ChargingBean
     */
    public ChargingBean() {
//        if (da == null) {
//            da = new DataAccessImpl();
//        }
    }
    ////////////////////////////////////

    public int getWhoIscharged() {
        return whoIscharged;
    }

    public void setWhoIscharged(int whoIscharged) {
        this.whoIscharged = whoIscharged;
    }

    public int getWhoIsNotHere() {
        return whoIsNotHere;
    }

    public void setWhoIsNotHere(int whoIsNotHere) {
        this.whoIsNotHere = whoIsNotHere;
    }

    public String getChargingName() {
        return chargingName;
    }

    public void setChargingName(String chargingName) {
        this.chargingName = chargingName;
    }

    public String getChargingStartDate() {
        return chargingStartDate;
    }

    public void setChargingStartDate(String chargingStartDate) {
        this.chargingStartDate = chargingStartDate;
    }

    public String getChargingEndDate() {
        return chargingEndDate;
    }

    public void setChargingEndDate(String chargingEndDate) {
        this.chargingEndDate = chargingEndDate;
    }

    public int getChargingPrivilage() {
        return chargingPrivilage;
    }

    public void setChargingPrivilage(int chargingPrivilage) {
        this.chargingPrivilage = chargingPrivilage;
    }

    public Map<String, String> getAllUserRoles() {
        return this.dataAccessService.findUsersRolesMap();
    }

    public void setAllUserRoles(Map<String, String> AllUserRoles) {
        this.AllUserRoles = AllUserRoles;
    }

    public Map<String, String> getAllUserList() {

        return this.dataAccessService.findUsersMap();
    }

    public void setAllUserList(Map<String, String> AllUserList) {
        this.AllUserList = AllUserList;
    }

    public List<WrkChargingClass> getChargingList() {
        chargingList = this.dataAccessService.findAllChargingByStatus(1);
        return chargingList;
    }

    public void setChargingList(List<WrkChargingClass> chargingList) {
        this.chargingList = chargingList;
    }

    public void createNewCharg(ActionEvent actionEvent) {
//        RequestContext context = RequestContext.getCurrentInstance();
        Utils.openDialog("NewChargdialog");
    }

    public void CreateChargingBtn(ActionEvent actionEvent) {
        MsgEntry.showModalDialog(
                this.dataAccessService.addNewCharging(this.whoIscharged, this.whoIsNotHere, this.chargingName, this.chargingStartDate, this.chargingEndDate, this.chargingPrivilage));
        this.whoIscharged = 0;
        this.whoIsNotHere = 0;
        this.chargingName = null;
        this.chargingStartDate = null;
        this.chargingEndDate = null;
        this.chargingPrivilage = 0;

    }

    public void whoIsNotHereListChanged(AjaxBehaviorEvent ajaxBehaviorEvent) {

        String tempName = this.dataAccessService.findEmployeeName(this.whoIsNotHere) + " المكلف ";
        this.chargingName = tempName;
    }

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}
}

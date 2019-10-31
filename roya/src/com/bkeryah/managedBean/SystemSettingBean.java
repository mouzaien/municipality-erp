/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import com.bkeryah.bean.ArcApplicationTypeClass;
import com.bkeryah.bean.ReferralSettingClass;
import com.bkeryah.bean.SpecialAddressClass;
import com.bkeryah.bean.UserClass;
import com.bkeryah.bean.WrkLetterFromClass;
import com.bkeryah.bean.WrkLetterToClass;
import com.bkeryah.bean.WrkPurposeClass;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

/**
 *
 * @author darwiesh
 */
@ManagedBean
@ViewScoped
public class SystemSettingBean implements Serializable {

    /**
     * Creates a new instance of SystemSettingBean
     */
	@ManagedProperty(value="#{dataAccessService}")
	private IDataAccessService dataAccessService;
    private List<WrkLetterFromClass> letterFromList = new ArrayList<WrkLetterFromClass>();
    private List<WrkLetterToClass> letterToList = new ArrayList<WrkLetterToClass>();
    private List<ArcApplicationTypeClass> ArcAppTypeList = new ArrayList<ArcApplicationTypeClass>();
    private List<ReferralSettingClass> ReferralManagerList = new ArrayList<ReferralSettingClass>();
    private List<WrkPurposeClass> WrkPurposeList = new ArrayList<WrkPurposeClass>();
    private int SelectedUserId;
    Map<String, String> allUsers = new LinkedHashMap<String, String>();
    List<SpecialAddressClass> selectedUserFavouritList = new ArrayList<SpecialAddressClass>();
    private int selectedNewSpecialaddress;
//    private DataAccess da = new DataAccessImpl();
    private String newletterFrom;
    private String newletterTo;
    private String newArcTyp;
    private String newwrkPurpose;
    private int selectRefUserId;
    private String selectRefUserName;

    public String getSelectRefUserName() {
        return selectRefUserName;
    }

    public void setSelectRefUserName(String selectRefUserName) {
        this.selectRefUserName = selectRefUserName;
    }

    public int getSelectRefUserId() {
        return selectRefUserId;
    }

    public void setSelectRefUserId(int selectRefUserId) {
        this.selectRefUserId = selectRefUserId;
    }

    public String getNewwrkPurpose() {
        return newwrkPurpose;
    }

    public void setNewwrkPurpose(String newwrkPurpose) {
        this.newwrkPurpose = newwrkPurpose;
    }

    public String getNewArcTyp() {
        return newArcTyp;
    }

    public void setNewArcTyp(String newArcTyp) {
        this.newArcTyp = newArcTyp;
    }

    public String getNewletterFrom() {
        return newletterFrom;
    }

    public void setNewletterFrom(String newletterFrom) {
        this.newletterFrom = newletterFrom;
    }

    public String getNewletterTo() {
        return newletterTo;
    }

    public void setNewletterTo(String newletterTo) {
        this.newletterTo = newletterTo;
    }

    public int getSelectedNewSpecialaddress() {
        return selectedNewSpecialaddress;
    }

    public void setSelectedNewSpecialaddress(int selectedNewSpecialaddress) {
        this.selectedNewSpecialaddress = selectedNewSpecialaddress;
    }

    public List<SpecialAddressClass> getSelectedUserFavouritList() {
        return selectedUserFavouritList;
    }

    public void setSelectedUserFavouritList(List<SpecialAddressClass> selectedUserFavouritList) {
        this.selectedUserFavouritList = selectedUserFavouritList;
    }

    public int getSelectedUserId() {
        return SelectedUserId;
    }

    public void setSelectedUserId(int SelectedUserId) {
        this.SelectedUserId = SelectedUserId;
    }

    public Map<String, String> getAllUsers() {
        this.allUsers.clear();
        List<UserClass> list = this.dataAccessService.findAllUsers();
        Map<String, String> returnMap = new LinkedHashMap<String, String>();
        for (UserClass item : list) {
            returnMap.put(item.getVusers_real_name(), String.valueOf(item.getVuser_id()));
        }
        return returnMap;
    }

    public void setAllUsers(Map<String, String> allUsers) {
        this.allUsers = allUsers;
    }

    public List<WrkPurposeClass> getWrkPurposeList() {
        this.WrkPurposeList = this.dataAccessService.findAllWrkPurpose(Utils.findCurrentUser().getUserId());
        return WrkPurposeList;
    }

    public void setWrkPurposeList(List<WrkPurposeClass> WrkPurposeList) {
        this.WrkPurposeList = WrkPurposeList;
    }

    public List<ReferralSettingClass> getReferralManagerList() {
        this.ReferralManagerList = this.dataAccessService.findAllReferralSetting();
        return ReferralManagerList;
    }

    public void setReferralManagerList(List<ReferralSettingClass> ReferralManagerList) {
        this.ReferralManagerList = ReferralManagerList;
    }

    public List<ArcApplicationTypeClass> getArcAppTypeList() {
        this.ArcAppTypeList = this.dataAccessService.findAllApplicationTypes();
        return ArcAppTypeList;
    }

    public void setArcAppTypeList(List<ArcApplicationTypeClass> ArcAppTypeList) {
        this.ArcAppTypeList = ArcAppTypeList;
    }

//    public DataAccess getDa() {
//        return da;
//    }
//
//    public void setDa(DataAccess da) {
//        this.da = da;
//    }

    public List<WrkLetterFromClass> getLetterFromList() {
        this.letterFromList = this.dataAccessService.findAllWrkLetterFroms();
        return letterFromList;
    }

    public void setLetterFromList(List<WrkLetterFromClass> letterFromList) {
        this.letterFromList = letterFromList;
    }

    public List<WrkLetterToClass> getLetterToList() {
        this.letterToList = this.dataAccessService.findAllAWrkLetterTos();
        return letterToList;
    }

    public void setLetterToList(List<WrkLetterToClass> letterToList) {
        this.letterToList = letterToList;
    }

    public SystemSettingBean() {
//        if (this.da == null) {
//            this.da = new DataAccessImpl();
//        }
    }

    public void NewLetterFromBtnAction(ActionEvent actionEvent) {
        this.newletterFrom = null;
        Utils.openDialog("incomeContactDlg");
    }

    public void NewAppTypBtnAction(ActionEvent actionEvent) {
        this.newArcTyp = null;
        Utils.openDialog("recordsTypeDlg");
    }

    public void NewPurpBtnAction(ActionEvent actionEvent) {

    	Utils.openDialog("redirectionTypeDlg");
    }

    public void showUserFavourits(ActionEvent actionEvent) {
        if (this.SelectedUserId != 0) {
            this.selectedUserFavouritList = this.dataAccessService.findSpeicalAddressByUser(this.SelectedUserId);
        } else {
            MsgEntry.addErrorMessage(MsgEntry.ERROR_CHOOSE_USER);
        }
    }

    public void addNewSpecialAddress(ActionEvent actionEvent) {

        if (this.selectedNewSpecialaddress == 0 || this.SelectedUserId == 0) {
        	MsgEntry.addErrorMessage(MsgEntry.ERROR_FAVORITE);
        } else {
            String x = dataAccessService.addNewSpecialAddress(this.SelectedUserId, this.selectedNewSpecialaddress);
            MsgEntry.showModalDialog(x);
        }
    }

    public void addNewLetterFromAction(ActionEvent actionEvent) {
        MsgEntry.addInfoMessage(this.newletterFrom);
        System.err.println("" + this.newletterFrom);

        try {
            // this.da.addNewWrkLetterfrom(this.newletterFrom);
            this.newletterFrom = null;
            MsgEntry.addInfoMessage(MsgEntry.SUCCESS_SAVE);
        } catch (Exception e) {
        }

    }

    public void addNewarcTypAction(ActionEvent actionEvent) {
        this.dataAccessService.addNewApplicationType(this.newArcTyp);
        this.newArcTyp = null;
        this.ArcAppTypeList = this.dataAccessService.findAllApplicationTypes();
        MsgEntry.addInfoMessage(MsgEntry.SUCCESS_SAVE);
    }

    public void addNewWrkPurposeBtnAction(ActionEvent actionEvent) {
        this.dataAccessService.addNewPurpose(this.newwrkPurpose);
        this.newwrkPurpose = null;
        this.WrkPurposeList = this.dataAccessService.findAllWrkPurpose(Utils.findCurrentUser().getUserId());
        MsgEntry.addInfoMessage(MsgEntry.SUCCESS_SAVE);

    }

    public void addNewlettertoBtnAction(ActionEvent actionEvent) {
        this.dataAccessService.addNewWrkLetterTo(this.newletterTo);
        this.newletterTo = null;
        this.letterToList = this.dataAccessService.findAllAWrkLetterTos();
        MsgEntry.addInfoMessage(MsgEntry.SUCCESS_SAVE);
    }

    public void addNewRefSettingActionBtn(ActionEvent actionEvent) {
        this.dataAccessService.addNewReferralSetting(this.selectRefUserId, this.selectRefUserName);
        this.selectRefUserId = 0;
        this.selectRefUserName = null;
        MsgEntry.addInfoMessage(MsgEntry.SUCCESS_SAVE);

    }

    public void refUsersListChanged(AjaxBehaviorEvent ajaxBehaviorEvent) {

        String tempName = this.dataAccessService.getEmployeeRealName(this.selectRefUserId);
        this.selectRefUserName = tempName;
    }

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}
}

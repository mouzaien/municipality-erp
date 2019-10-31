/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

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

import org.primefaces.model.LazyDataModel;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.bean.UserMailLazyList;
import com.bkeryah.bean.UserMailObj;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;

/**
 *
 * @author darwiesh
 */
@ManagedBean
@ViewScoped
public class TransferRecordsBean {

    private int sourceSelectedUser;
    private int targetSelectedUser;
    private Map<String, String> allUserMap = new HashMap<String, String>();
//    private DataAccess da = new DataAccessImpl();
    @ManagedProperty(value="#{dataAccessService}")
	private IDataAccessService dataAccessService;
    private LazyDataModel<UserMailObj> sourcemails;
    private LazyDataModel<UserMailObj> Targetmails;
    private List<UserMailObj> selectedInboxs = new ArrayList<UserMailObj>();
    private String ActionReason ;

    public String getActionReason() {
        return ActionReason;
    }

    public void setActionReason(String ActionReason) {
        this.ActionReason = ActionReason;
    }
    
    public Map<String, String> getAllUserMap() {
    	if((allUserMap == null) || (allUserMap.isEmpty()))
    		allUserMap = this.dataAccessService.findUsersMap();
        return allUserMap;
    }

    public void setAllUserMap(Map<String, String> allUserMap) {
        this.allUserMap = allUserMap;
    }

    public int getSourceSelectedUser() {
        return sourceSelectedUser;
    }

    public void setSourceSelectedUser(int sourceSelectedUser) {
        this.sourceSelectedUser = sourceSelectedUser;
    }

    public int getTargetSelectedUser() {
        return targetSelectedUser;
    }

    public void setTargetSelectedUser(int targetSelectedUser) {
        this.targetSelectedUser = targetSelectedUser;
    }

    public LazyDataModel<UserMailObj> getSourcemails() {
        return sourcemails;
    }

    public LazyDataModel<UserMailObj> getTargetmails() {
        return Targetmails;
    }

    public List<UserMailObj> getSelectedInboxs() {
        return selectedInboxs;
    }

    public void setSelectedInboxs(List<UserMailObj> selectedInboxs) {
        this.selectedInboxs = selectedInboxs;
    }

    /**
     * Creates a new instance of TransferRecordsBean
     */
    public TransferRecordsBean() {
    }

    public void TargetUserListChangeEvent(AjaxBehaviorEvent ajaxBehaviorEvent) {

//        List<UserMailObj> tmpMails2 = this.dataAccessService.findEmployeeInbox(this.targetSelectedUser);
//        Targetmails = new UserMailLazyList(tmpMails2);
    }

    public void SourceUserListChangeEvent(AjaxBehaviorEvent ajaxBehaviorEvent) {

//        List<UserMailObj> tmpMails = this.dataAccessService.findEmployeeInbox(this.sourceSelectedUser);
//        sourcemails = new UserMailLazyList(tmpMails);
    }

    public void transferBtnAction(ActionEvent actionEvent) {
        if (this.sourceSelectedUser == this.targetSelectedUser) {
            MsgEntry.showModalDialog(MsgEntry.ERROR_CORRESPONDANCE);
        } else {
            
            for (UserMailObj obj : selectedInboxs) {
                
                dataAccessService.wrkTransferApplication(obj.WrkId, obj.AppId, this.sourceSelectedUser , this.targetSelectedUser, "تمت الاحالة بواسطة نظام التعاملات الالكترونية : " + this.ActionReason, 28);
            }
            MsgEntry.showModalDialog("تم نقل عدد  : " + this.selectedInboxs.size() +" من المعاملات  ");
        }

    }

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}
}

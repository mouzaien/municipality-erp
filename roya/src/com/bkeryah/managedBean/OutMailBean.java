/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import com.bkeryah.bean.ArcAttachmentClass;
import com.bkeryah.bean.UserMailObj;
import com.bkeryah.bean.UserMailLazyList;
import com.bkeryah.bean.WrkCommentsClass;
import com.bkeryah.dao.DataAccess;
import com.bkeryah.dao.DataAccessImpl;

import utilities.HijriCalendarUtil;
import utilities.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.swing.text.Highlighter;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Ibrahimdarwiesh
 */
@ManagedBean(name = "outboxbean")
@ViewScoped
public class OutMailBean implements Serializable {

    private LazyDataModel<UserMailObj> outmails;
    DataAccess da = new DataAccessImpl();
    public UserMailObj selectedOutbox;
    List<ArcAttachmentClass> outattList = new ArrayList<ArcAttachmentClass>();
    List<WrkCommentsClass> outboxComments;
    public LazyDataModel<UserMailObj> getOutmails() {
        return outmails;
    }

    public UserMailObj getSelectedOutbox() {
        return selectedOutbox;
    }

    public void setSelectedOutbox(UserMailObj selectedOutbox) {
        this.selectedOutbox = selectedOutbox;
    }

    public List<ArcAttachmentClass> getOutattList() {
        return outattList;
    }

    public void setOutattList(List<ArcAttachmentClass> outattList) {
        this.outattList = outattList;
    }

    public List<WrkCommentsClass> getOutboxComments() {
        return outboxComments;
    }

    public void setOutboxComments(List<WrkCommentsClass> outboxComments) {
        this.outboxComments = outboxComments;
    }

    
    
    /**
     * Creates a new instance of OutMailBean
     */

//    public OutMailBean() {
//        if (this.da == null) {
//            this.da = new DataAccessImpl();
//        }
     //   List<UserMailObj> outMails = this.da.findEmployeeOutbox(Utils.findCurrentUser().getUserId()و);
//        outmails = new UserMailLazyList(outMails);
//    }

    public void retrieveRecordBtnAction(ActionEvent actionEvent) {
        String retstring = this.da.retrieveRecord(Utils.findCurrentUser().getUserId(),
                this.selectedOutbox.AppId, this.selectedOutbox.WrkId);
        if (retstring.length() > 0) {
            this.da.showErrMessage(retstring);
        }
        if (retstring.length() == 0 || retstring == null) {
            //refreshInbox();
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('outboxDlg').hide()");
            this.da.showMessage("تم أسترجاع المعاملة بنجاح");
        }

    }

    public void outboxSelectRow(SelectEvent selectEvent) {
        UserMailObj outboxmail = (UserMailObj) selectEvent.getObject();

        this.selectedOutbox = outboxmail;
        this.outboxComments = this.da.findCommentsByWrkId(outboxmail.WrkId);
        LoadOutbocAttachment();

    }

    public void LoadOutbocAttachment() {
        this.outattList = this.da.findAttachmentFilesByArcId(this.selectedOutbox.AppId);
    }

}

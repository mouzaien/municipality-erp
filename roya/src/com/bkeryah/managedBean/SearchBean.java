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
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import com.bkeryah.bean.ArcAttachmentClass;
import com.bkeryah.bean.ArcRecordClass;
import com.bkeryah.bean.LastRecordActionClass;
import com.bkeryah.bean.UserClass;
import com.bkeryah.bean.WrkLetterFromClass;
import com.bkeryah.bean.WrkLetterToClass;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

/**
 *
 * @author IbrahimDarwiesh
 */
@ManagedBean
@ViewScoped
public class SearchBean implements Serializable {

    List<ArcRecordClass> ArcList;
    ArcRecordClass selectedArc = new ArcRecordClass();
    String ArcNo;
    String SrchKey;
    @ManagedProperty(value="#{dataAccessService}")
	private IDataAccessService dataAccessService;
//    DataAccess da = new DataAccessImpl();
    Map<String, String> LetterFromMap = new HashMap<String, String>();
    Map<String, String> LetterToMap = new HashMap<String, String>();
    private String selectedLetterTo;
    private String selectedLetterfrom;
    private LastRecordActionClass lastrecordClass = new LastRecordActionClass();
    List<ArcAttachmentClass> attList = new ArrayList<ArcAttachmentClass>();
    String CurrentRecordExplainLink  ;
    private Map<String, String> AllUsersMap = new LinkedHashMap<String, String>();
    private char selectAction;
    private String selectedEmployee;
    boolean hasComment;
    /**
     * Creates a new instance of SearchBean
     */
    public SearchBean() {
//        if (da == null) {
//            da = new DataAccessImpl();
//        }
    }

    public boolean isHasComment() {
        return hasComment;
    }

    public void setHasComment(boolean hasComment) {
        this.hasComment = hasComment;
    }

    public String getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(String selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public char getSelectAction() {
        return selectAction;
    }

    public void setSelectAction(char selectAction) {
        this.selectAction = selectAction;
    }

    public Map<String, String> getAllUsersMap() {
        AllUsersMap.clear();
        List<UserClass> list = this.dataAccessService.findAllUsers();
        for (UserClass item : list) {
            AllUsersMap.put(item.getVusers_real_name(), String.valueOf(item.getVuser_id()));
        }

        return AllUsersMap;
    }

    public void setAllUsersMap(Map<String, String> AllUsersMap) {
        this.AllUsersMap = AllUsersMap;
    }

    public String getCurrentRecordExplainLink() {
        return CurrentRecordExplainLink;
    }

    public void setCurrentRecordExplainLink(String CurrentRecordExplainLink) {
        this.CurrentRecordExplainLink = CurrentRecordExplainLink;
    }

    //this.da.findAttachmentFilesByArcId(this.selectedInbox.AppId);
    public List<ArcAttachmentClass> getAttList() {
        return attList;
    }

    public void setAttList(List<ArcAttachmentClass> attList) {
        this.attList = attList;
    }

    public LastRecordActionClass getLastrecordClass() {
        return lastrecordClass;
    }

    public void setLastrecordClass(LastRecordActionClass lastrecordClass) {
        this.lastrecordClass = lastrecordClass;
    }
    

    public String getSelectedLetterTo() {
        return selectedLetterTo;
    }

    public void setSelectedLetterTo(String selectedLetterTo) {
        this.selectedLetterTo = selectedLetterTo;
    }

    public String getSelectedLetterfrom() {
        return selectedLetterfrom;
    }

    public void setSelectedLetterfrom(String selectedLetterfrom) {
        this.selectedLetterfrom = selectedLetterfrom;
    }

    
    public Map<String, String> getLetterFromMap() {
         List<WrkLetterFromClass> list = this.dataAccessService.findAllWrkLetterFroms();
        Map<String, String> ReturnMap = new LinkedHashMap<String, String>();
        for (WrkLetterFromClass item : list) {
            LetterFromMap.put(item.getWrkLetterFromName(), String.valueOf(item.getWrkLetterfromId()));
        }
        return LetterFromMap;
    }

    public void setLetterFromMap(Map<String, String> LetterFromMap) {
        this.LetterFromMap = LetterFromMap;
    }

    public Map<String, String> getLetterToMap() {
        
         List<WrkLetterToClass> list = this.dataAccessService.findAllAWrkLetterTos();
        Map<String, String> ReturnMap = new LinkedHashMap<String, String>();
        for (WrkLetterToClass item : list) {
            LetterToMap.put(item.getWrkLetterToName(), String.valueOf(item.getWrkLetterToId()));
        }
        return LetterToMap;
    }

    public void setLetterToMap(Map<String, String> LetterToMap) {
        this.LetterToMap = LetterToMap;
    }

    
    
    public String getSrchKey() {
        return SrchKey;
    }

    public void setSrchKey(String SrchKey) {
        this.SrchKey = SrchKey;
    }
    
    
    

    public List<ArcRecordClass> getArcList() {
        return ArcList;
    }

    public void setArcList(List<ArcRecordClass> ArcList) {
        this.ArcList = ArcList;
    }

    public ArcRecordClass getSelectedArc() {
        return selectedArc;
    }

    public void setSelectedArc(ArcRecordClass selectedArc) {
        this.selectedArc = selectedArc;
    }

    public String getArcNo() {
        return ArcNo;
    }

    public void setArcNo(String ArcNo) {
        this.ArcNo = ArcNo;
    }

    public List<ArcRecordClass> findList() {
        return this.ArcList;
    }

    public void SrchBtn(ActionEvent ae) {
        if (this.ArcNo != null || this.ArcNo.length() > 0) {
            this.ArcList = this.dataAccessService.getRecords(this.ArcNo);
        }else{
            this.ArcList =null;
            MsgEntry.addErrorMessage(MsgEntry.ERROR_MISSING_KEY);
        }

    }
    public void SrchCommentBtn(ActionEvent ae) {
        if (this.SrchKey != null || this.SrchKey.length() > 0) {
            this.ArcList = this.dataAccessService.SearchRecordsByCommentdata(this.SrchKey);
        }else{
            this.ArcList =null;
            MsgEntry.addErrorMessage(MsgEntry.ERROR_MISSING_KEY);
        }

    }
    
    public void changeLetterFromList(ValueChangeEvent changeEvent){
    	MsgEntry.addInfoMessage(this.selectedLetterfrom);
        this.ArcList = this.dataAccessService.SearchRecordsByLetterFrom(Integer.parseInt(this.selectedLetterfrom));
    }
     public void changeLetterToList(ValueChangeEvent changeEvent){
    	 MsgEntry.addInfoMessage(this.selectedLetterTo);
        this.ArcList = this.dataAccessService.SearchRecordsByLetterTo(Integer.parseInt(this.selectedLetterTo));
    }
     public void clickrowAction(SelectEvent actionEvent){
         if (this.selectedArc.getLastRecord().isHasComment()=="Y"){
             this.hasComment = true;
         }else{this.hasComment = false;}
         this.attList = this.dataAccessService.findAttachmentFilesByArcId(this.selectedArc.getArcId()+"");
         this.CurrentRecordExplainLink = "http://192.168.155.132/reports/rwservlet?report&report=d:\\archiving\\reports\\r04.rdf&P1="+this.dataAccessService.findArcRecordParam(this.selectedArc.getArcId()+"");
         
     }
     public void sendRecordAction(ActionEvent actionEvent){
         final char nullChar = '\u0000';
         if(this.selectAction== nullChar || this.selectedEmployee == null){
             MsgEntry.addErrorMessage(MsgEntry.ERROR_SELECT_OPERATION); 
         }
         else{ 
             if (this.selectedArc.getLastEmpNumber()== Integer.parseInt(this.selectedEmployee )){
            	 MsgEntry.addErrorMessage(MsgEntry.ERROR_SEND_OPERATION);
             }else{
                 try {
                     this.dataAccessService.SendRecordFromArchieve(this.selectedArc.getArcId()+"", Integer.parseInt(this.selectedEmployee ), this.selectAction+"");
                     MsgEntry.addInfoMessage(MsgEntry.SUCCESS);
                     Utils.closeDialog("recordDlg");
                 } catch (Exception e) {
                 }
             }
         }
     }
     
     public void selectradioComment(ValueChangeEvent valueChangeEvent){
          final char nullChar = '\u0000';
          
         if(this.selectAction== 'C' && this.selectedArc.getLastRecord().isHasComment().equalsIgnoreCase("N") ){
             this.selectAction= nullChar;
             MsgEntry.addErrorMessage(MsgEntry.ERROR_MISSING_SPEECH);
             
             
         }
     }

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

}

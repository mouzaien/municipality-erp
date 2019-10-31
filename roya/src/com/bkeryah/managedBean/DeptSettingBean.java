/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;

import com.bkeryah.bean.WrkDeptClass;
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
public class DeptSettingBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, String> deptMap;
    private Map<String, String> sectMap = new HashMap<String, String>();
    private int selectedDept;
//    private DataAccess da = new DataAccessImpl();
    @ManagedProperty(value="#{dataAccessService}")
	private IDataAccessService dataAccessService;
    private String selectedDeptTxt;
    private List<WrkSectionClass> DeptSections;
    private String NewSectionName;
    private String NewDepartmentArabicName;
    private String NewDepartmentEnglishName;
 
    public String getNewSectionName() {
        return NewSectionName;
    }

    public void setNewSectionName(String NewSectionName) {
        this.NewSectionName = NewSectionName;
    }

    
    public String getNewDepartmentArabicName() {
        return NewDepartmentArabicName;
    }

    public void setNewDepartmentArabicName(String NewDepartmentArabicName) {
        this.NewDepartmentArabicName = NewDepartmentArabicName;
    }

    public String getNewDepartmentEnglishName() {
        return NewDepartmentEnglishName;
    }

    public void setNewDepartmentEnglishName(String NewDepartmentEnglishName) {
        this.NewDepartmentEnglishName = NewDepartmentEnglishName;
    }

    
    
    
    public Map<String, String> getDeptMap() {
        if (deptMap == null) {
            deptMap = new LinkedHashMap<String, String>();
        }
        deptMap.clear();
        for (WrkDeptClass item : this.dataAccessService.findAllDepartment()) {
            deptMap.put(item.getDeptName(), String.valueOf(item.getDeptId()));
        }
        return deptMap;
    }

    public void setDeptMap(Map<String, String> deptMap) {
        this.deptMap = deptMap;
    }

    public Map<String, String> getSectMap() {
        return sectMap;
    }

    public void setSectMap(Map<String, String> sectMap) {
        this.sectMap = sectMap;
    }

    public int getSelectedDept() {
        return selectedDept;
    }

    public void setSelectedDept(int selectedDept) {
        this.selectedDept = selectedDept;
    }

    public String getSelectedDeptTxt() {
        return selectedDeptTxt;
    }

    public void setSelectedDeptTxt(String selectedDeptTxt) {
        this.selectedDeptTxt = selectedDeptTxt;
    }

    public List<WrkSectionClass> getDeptSections() {

        return this.dataAccessService.findAllSectionsByDept(this.selectedDept);
    }

    public void setDeptSections(List<WrkSectionClass> DeptSections) {
        this.DeptSections = DeptSections;
    }

    /**
     * *************************************************
     */
    /**
     * Creates a new instance of DeptSettingBean
     */
    public DeptSettingBean() {
    }

    public void chnageDeptListBox(AjaxBehaviorEvent valueChangeEvent) {
       // this.da.showMessage(this.selectedDept + "");
        this.selectedDeptTxt = MsgEntry.MSG_SELECT_DEPARTMENT + this.dataAccessService.findDepartmentNameById(this.selectedDept);
    }

    public void addSectionBtnAction(ActionEvent actionEvent) {

        System.err.println("kokowawa create section ");
        if (this.selectedDept > 0) {
        	Utils.openDialog("addSectionDlg");
        } else {
            MsgEntry.addErrorMessage(MsgEntry.ERROR_SELECT_DEPARTMENT);
        }
    }

    public void SaveSectionBtnAction(ActionEvent actionEvent) {
        if (this.NewSectionName == null || this.NewSectionName.length() == 0) {
            MsgEntry.addErrorMessage(MsgEntry.ERROR_DEPARTMENT_NAME);
        } else {

        }
    }

    public void addDepartmentBtnAction(ActionEvent actionEvent) {
        System.err.println("add dept called");
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('addDepartmentDlg').show()");
    }
    public  void addNewDeptAction(ActionEvent actionEvent){
        
        if (this.NewDepartmentArabicName==null||this.NewDepartmentEnglishName==null){
            MsgEntry.addErrorMessage("Error");
        }else{
            this.dataAccessService.addNewDept(this.NewDepartmentArabicName, this.NewDepartmentEnglishName);
            Utils.closeDialog("addDepartmentDlg");
            MsgEntry.showModalDialog(MsgEntry.SUCCESS_SAVE_ADMINISTRATION);
        }
        System.err.println("");
    }
    public void addNewSection(ActionEvent actionEvent){
        if (this.NewSectionName == null){
            MsgEntry.addErrorMessage("Error");
        }else{
            this.dataAccessService.addNewsection(this.NewSectionName, this.selectedDept);
            Utils.closeDialog("addSectionDlg");
            MsgEntry.showModalDialog(MsgEntry.SUCCESS_SAVE_DEPARTMENT + this.dataAccessService.findDepartmentNameById(this.selectedDept));
        }
    }

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}
}

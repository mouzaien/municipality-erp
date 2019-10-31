/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import com.bkeryah.dao.DataAccess;
import com.bkeryah.dao.DataAccessImpl;

/**
 *
 * @author IbrahimDarwiesh
 */
@ManagedBean
@ViewScoped
public class EmpServiceBean implements Serializable{

    /**
     * Creates a new instance of EmpServiceBean
     */
    private DataAccess da = new DataAccessImpl();
    private Map<String,String> weekDays;
    private Map<String,String> HoursDays;
    private Map<String,String> Minutes;
    private String selectDayOfWeek ;
    private String CurrentUserName;
    private String CurrentUserDept;
    private String CurrentUserJob;
    private String SelectedMinute;
    private String SelectedHour;
    
    public EmpServiceBean() {
        if (this.da== null){
            DataAccess da = new DataAccessImpl();
        }
    }

    public String getCurrentUserDept() {
        return utilities.Utils.findCurrentUser().getUserDept().getDeptName();
    }

    public void setCurrentUserDept(String CurrentUserDept) {
        this.CurrentUserDept = CurrentUserDept;
    }

    public String getCurrentUserJob() {
        return CurrentUserJob;
    }

    public void setCurrentUserJob(String CurrentUserJob) {
        this.CurrentUserJob = CurrentUserJob;
    }

    
    public String getSelectedMinute() {
        return SelectedMinute;
    }

    public void setSelectedMinute(String SelectedMinute) {
        this.SelectedMinute = SelectedMinute;
    }

    public String getSelectedHour() {
        return SelectedHour;
    }

    public void setSelectedHour(String SelectedHour) {
        this.SelectedHour = SelectedHour;
    }

    
    public Map<String, String> getHoursDays() {
        return utilities.Utils.findHoursMap();
    }

    public void setHoursDays(Map<String, String> HoursDays) {
        this.HoursDays = HoursDays;
    }

    public Map<String, String> getMinutes() {
        return utilities.Utils.findMinutesMap();
    }

    public void setMinutes(Map<String, String> Minutes) {
        this.Minutes = Minutes;
    }

    
    
    public String getCurrentUserName() {
        return utilities.Utils.findCurrentUser().getEmployeeName();
    }

    public void setCurrentUserName(String CurrentUserName) {
        this.CurrentUserName = CurrentUserName;
    }

    
    
    public String getSelectDayOfWeek() {
        return selectDayOfWeek;
    }

    public void setSelectDayOfWeek(String selectDayOfWeek) {
        this.selectDayOfWeek = selectDayOfWeek;
    }

    
    
    public Map<String, String> getWeekDays() {
        weekDays = utilities.Utils.findDaysMap();
        return weekDays;
    }

    public void setWeekDays(Map<String, String> weekDays) {
        this.weekDays = weekDays;
    }
    
    
    
    
    /********************* الاستئذان***************/
     public void OpenPermissionRequestDlg(ActionEvent actionEvent){
         try {

             RequestContext context = RequestContext.getCurrentInstance();
             context.execute("PF('PermRequestDlg').show();");
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
     /************************الاجازة الاعتيادية**********************/
      public void OpenNormalVacRequestDlg(ActionEvent actionEvent){
    	  
//    	  return "e3tiadiavacation";
    	  
         try {

            RequestContext context = RequestContext.getCurrentInstance();
           context.execute("PF('NormalvacRequestDlg').show();");
        } catch (Exception e) {
             e.printStackTrace();
         }
     }
}

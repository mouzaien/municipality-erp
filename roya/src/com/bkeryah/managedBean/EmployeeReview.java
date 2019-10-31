package com.bkeryah.managedBean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.bkeryah.bean.UserClass;
import com.bkeryah.bean.UserStatisticsClass;
import com.bkeryah.dao.DataAccess;
import com.bkeryah.dao.DataAccessImpl;

@ManagedBean
@ViewScoped
public class EmployeeReview {
	DataAccess dataAccess = new DataAccessImpl();
	Map<String, String> allUsers = new LinkedHashMap<String, String>();
	private String StartDate;
	private String EndDate;
	private List<String> selectedUsers ;
	private List<UserStatisticsClass> userStatisticsList ;
	public EmployeeReview(){
		if(dataAccess == null){
			dataAccess = new DataAccessImpl();
		}
	}
	public Map<String, String> getAllUsers() {
		
		 this.allUsers.clear();
	        List<UserClass> list = this.dataAccess.findAllUsers();
	        Map<String, String> returnMap = new LinkedHashMap<String, String>();
	        for (UserClass item : list) {
	            returnMap.put(item.getVusers_real_name(), String.valueOf(item.getVuser_id()));
	        }
	        
	        return returnMap;
	}
	public void setAllUsers(Map<String, String> allUsers) {
		this.allUsers = allUsers;
	}
	
	public String getStartDate() {
		return StartDate;
	}
	public void setStartDate(String startDate) {
		StartDate = startDate;
	}
	public String getEndDate() {
		return EndDate;
	}
	public void setEndDate(String endDate) {
		EndDate = endDate;
	}
	public List<UserStatisticsClass> getUserStatisticsList() {
		return userStatisticsList;
	}
	public void setUserStatisticsList(List<UserStatisticsClass> userStatisticsList) {
		this.userStatisticsList = userStatisticsList;
	}
	public List<String> getSelectedUsers() {
		return selectedUsers;
	}
	public void setSelectedUsers(List<String> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}
	public void findStatistics(ActionEvent actionevent){
		this.userStatisticsList = new ArrayList<UserStatisticsClass>();

		for (String string : selectedUsers) {
			this.userStatisticsList.add(this.dataAccess.findUserStatistics(Integer.parseInt(string), this.StartDate, this.EndDate)) ;
		}
		
	}
	
}

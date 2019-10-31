package com.bkeryah.managedBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.bkeryah.bean.ActionClass;
import com.bkeryah.dao.DataAccess;
import com.bkeryah.dao.DataAccessImpl;

@ManagedBean
@ViewScoped
public class UserLogBean {
	DataAccess da  = new DataAccessImpl();
	Map<String,String> userMap = new HashMap<String,String>();
	List<ActionClass> useractionlist = new ArrayList<ActionClass>();
	String selectedUser ;
	public UserLogBean() {
		if (this.da==null){
			da  = new DataAccessImpl();
		}
	}
	public Map<String, String> getUserMap() {
		return this.da.findUsersMap();
	}
	public void setUserMap(Map<String, String> userMap) {
		this.userMap = userMap;
	}
	public List<ActionClass> getUseractionlist() {
		return useractionlist;
	}
	public void setUseractionlist(List<ActionClass> useractionlist) {
		this.useractionlist = useractionlist;
	}
	public String getSelectedUser() {
		return selectedUser;
	}
	public void setSelectedUser(String selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	public void searchBtnAction( ActionEvent actionEvent) {
		this.useractionlist = this.da.findUserAction(Integer.parseInt(this.selectedUser));
		this.da.storeAction("عرض حركات المستخدم "+this.da.findEmployeeName(Integer.parseInt(this.selectedUser)), ">>"+this.useractionlist.size());
	}
	
	
}

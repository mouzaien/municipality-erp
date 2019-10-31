/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.bean;

import java.io.Serializable;

/**
 *
 * @author IbrahimDarwiesh
 */
public class UserContactClass implements Serializable, Comparable<UserContactClass> {

    private int UserId;
    private String UserName;
    private int UserOrder;
    private int UserRole ;
    
    public UserContactClass() {
    	
	}
    
    public UserContactClass(int userId, String userName) {
		UserId = userId;
		UserName = userName;
	}

	public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public int getUserOrder() {
        return UserOrder;
    }

    public void setUserOrder(int UserOrder) {
        this.UserOrder = UserOrder;
    }

    public int getUserRole() {
        return UserRole;
    }

    public void setUserRole(int UserRole) {
        this.UserRole = UserRole;
    }
    
    public String getUserIdStr() {
        return String.valueOf(UserId);
    }

	@Override
	public int compareTo(UserContactClass o) {
		if(this.getUserId() == o.getUserId())
			return 0;
		return 1;
	}

}

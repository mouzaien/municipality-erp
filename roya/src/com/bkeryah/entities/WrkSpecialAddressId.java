package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class WrkSpecialAddressId  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="USER_ID")
	private int userId;
	@Column(name="IN_LIST")
	private int userInListId;
	public int getUserId() {
		return userId;
	}
	public int getUserInListId() {
		return userInListId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setUserInListId(int userInListId) {
		this.userInListId = userInListId;
	}

}

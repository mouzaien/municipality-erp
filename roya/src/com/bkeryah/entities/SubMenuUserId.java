package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SubMenuUserId   implements Serializable{

	@Column(name="NEW_SUB_MENU_ID")
	private Integer subMenuId;
	@Column(name="USER_ID")
	private Integer userId;
	public Integer getSubMenuId() {
		return subMenuId;
	}
	public void setSubMenuId(Integer subMenuId) {
		this.subMenuId = subMenuId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}

package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MainMenuUserId implements Serializable {
	@Column(name = "MAIN_MENU_ID")
	private Integer mainMenuId;
	@Column(name = "USER_ID")
	private Integer userId;
	public Integer getMainMenuId() {
		return mainMenuId;
	}
	public void setMainMenuId(Integer mainMenuId) {
		this.mainMenuId = mainMenuId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}

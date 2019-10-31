package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class wrkUSerFolderMailId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "WID")
	private int wrkAppId;
	@Column(name = "STEP_ID")
	private int wrkAppStepId;
	@Column(name = "USER_ID")
	private int userId;
	@Column(name = "F_ID")
	private int folderId;
	public int getWrkAppId() {
		return wrkAppId;
	}
	public int getWrkAppStepId() {
		return wrkAppStepId;
	}
	public int getUserId() {
		return userId;
	}
	public int getFolderId() {
		return folderId;
	}
	public void setWrkAppId(int wrkAppId) {
		this.wrkAppId = wrkAppId;
	}
	public void setWrkAppStepId(int wrkAppStepId) {
		this.wrkAppStepId = wrkAppStepId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}
	
	
	
}

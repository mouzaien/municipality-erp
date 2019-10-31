package com.bkeryah.bean;

public class ActionClass {
	private int actionId;
	private String actionGdate;
	private int UserId;
	private String PcId;
	private String action ;
	private String actionDetails;
	private String actionEmpName;
	private String actionHdate;
	public int getActionId() {
		return actionId;
	}
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}
	public String getActionGdate() {
		return actionGdate;
	}
	public void setActionGdate(String actionGdate) {
		this.actionGdate = actionGdate;
	}
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	public String getPcId() {
		return PcId;
	}
	public void setPcId(String pcId) {
		PcId = pcId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getActionDetails() {
		return actionDetails;
	}
	public void setActionDetails(String actionDetails) {
		this.actionDetails = actionDetails;
	}
	public String getActionEmpName() {
		return actionEmpName;
	}
	public void setActionEmpName(String actionEmpName) {
		this.actionEmpName = actionEmpName;
	}
	public String getActionHdate() {
		return actionHdate;
	}
	public void setActionHdate(String actionHdate) {
		this.actionHdate = actionHdate;
	}
	public ActionClass(int actionId, String actionGdate, int userId, String pcId, String action, String actionDetails,
			String actionEmpName, String actionHdate) {
		super();
		this.actionId = actionId;
		this.actionGdate = actionGdate;
		UserId = userId;
		PcId = pcId;
		this.action = action;
		this.actionDetails = actionDetails;
		this.actionEmpName = actionEmpName;
		this.actionHdate = actionHdate;
	}
	public ActionClass() {
		super();
	}
	
	
	
}

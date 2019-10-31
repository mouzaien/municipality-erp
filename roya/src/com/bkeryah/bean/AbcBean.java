package com.bkeryah.bean;

public class AbcBean {

	private Integer userId;
	private String loginName;
	
	public AbcBean(Integer userId, String loginName) {
		super();
		this.userId = userId;
		this.loginName = loginName;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}

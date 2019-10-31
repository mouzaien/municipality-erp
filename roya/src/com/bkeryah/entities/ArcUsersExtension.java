package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ARC_USERS_EXTENSION")
public class ArcUsersExtension implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "USER_ID")
	private Integer userId;
	@Column(name = "USER_DEP_JOB")
	private Integer userDeptJob;
	/*@OneToOne(fetch = FetchType.LAZY, mappedBy = "arcUsersExtension")
	private ArcUsers arcUsers;*/
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getUserDeptJob() {
		return userDeptJob;
	}
	public void setUserDeptJob(Integer userDeptJob) {
		this.userDeptJob = userDeptJob;
	}
	/*public ArcUsers getArcUsers() {
		return arcUsers;
	}
	public void setArcUsers(ArcUsers arcUsers) {
		this.arcUsers = arcUsers;
	}*/
	
}

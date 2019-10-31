package com.bkeryah.support.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "REQUEST_STEPS")
public class RequestStep {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "REQUEST_ID")
	private Integer requestId;
	@Column(name = "STEP_ID")
	private Integer stepId;
	@Column(name = "CREATE_DATE")
	private Date createDate;
	@Column(name = "USER_ID")
	private Integer userId;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Formula("(select us.EMPNAME from ARC_USERS us where us.user_id = USER_ID)")
	private String userName;
	@Formula("(select rs.DESCRIPTION from REQUEST_STATUS rs where rs.ID = STATUS)")
	private String stepStatus;
	
	@ManyToOne
	@JoinColumn(name = "REQUEST_ID", nullable = false, insertable = false, updatable = false)
	private UserRequest requestStep;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStepStatus() {
		return stepStatus;
	}

	public void setStepStatus(String stepStatus) {
		this.stepStatus = stepStatus;
	}

	public UserRequest getRequestStep() {
		return requestStep;
	}

	public void setRequestStep(UserRequest requestStep) {
		this.requestStep = requestStep;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRequestId() {
		return requestId;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	public Integer getStepId() {
		return stepId;
	}

	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
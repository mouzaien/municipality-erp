package com.bkeryah.support.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "USER_REQUESTS")
public class UserRequest {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "USER_ID")
	private Integer userId;
	@Column(name = "USER_DEPT")
	private Integer userDept;
	@Column(name = "SUBJECT")
	private String subject;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "CREATE_DATE")
	private Date createDate;
	@Column(name = "RESOLVED_DATE")
	private Date resolvedDate;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "curr_userId")
	private Integer currUserId;
	@Column(name = "curr_stepId")
	private Integer currStepId;
	@Formula("(select w.dept_name from WRK_dept w where w.id = USER_DEPT)")
	private String fromDeptName;
	@Transient
	private String note;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "requestStep")
	private List<RequestStep> requestSteps;

	public List<RequestStep> getRequestSteps() {
		return requestSteps;
	}

	public void setRequestSteps(List<RequestStep> requestSteps) {
		this.requestSteps = requestSteps;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getCurrUserId() {
		return currUserId;
	}

	public void setCurrUserId(Integer currUserId) {
		this.currUserId = currUserId;
	}

	public Integer getCurrStepId() {
		return currStepId;
	}

	public void setCurrStepId(Integer currStepId) {
		this.currStepId = currStepId;
	}

	@Transient
	private String userName;
	@Transient
	private String currUserName;

	public String getCurrUserName() {
		return currUserName;
	}

	public void setCurrUserName(String currUserName) {
		this.currUserName = currUserName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserDept() {
		return userDept;
	}

	public void setUserDept(Integer userDept) {
		this.userDept = userDept;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getResolvedDate() {
		return resolvedDate;
	}

	public void setResolvedDate(Date resolvedDate) {
		this.resolvedDate = resolvedDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFromDeptName() {
		return fromDeptName;
	}

	public void setFromDeptName(String fromDeptName) {
		this.fromDeptName = fromDeptName;
	}

}

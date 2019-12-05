package com.bkeryah.fng.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "FNG_USERTEMPSHIFT")
public class FngUserTempShift {
	@EmbeddedId
	private FngUserShiftId id;
//	@Column(name = "USERID")
//	private Integer userid;
//	@Column(name = "TIMEID")
//	private Integer timeid;
//	@Column(name = "WORKDATE")
//	private String workdate;
	
	@Column(name = "ISOVERTIME")
	private Integer isovertime = 0;
	public FngUserShiftId getId() {
		return id;
	}

	public void setId(FngUserShiftId id) {
		this.id = id;
	}

	@Transient
	private Integer secondTimeid;
	@Transient
	private String secondTimeName;
	@Formula("(select w.EMPNAME from ARC_USERS w where w.USER_ID = USERID)")
	private String userName;
	@Transient
	private String userDeptName;

	@Formula("(select w.TIMENAME from FNG_TIMETABLE w where w.TIMEID = TIMEID)")
	private String timeName;

	public String getTimeName() {
		return timeName;
	}

	public void setTimeName(String timeName) {
		this.timeName = timeName;
	}

	public Integer getSecondTimeid() {
		return secondTimeid;
	}

	public void setSecondTimeid(Integer secondTimeid) {
		this.secondTimeid = secondTimeid;
	}

	public String getSecondTimeName() {
		return secondTimeName;
	}

	public void setSecondTimeName(String secondTimeName) {
		this.secondTimeName = secondTimeName;
	}

//	public Integer getUserid() {
//		return userid;
//	}
//
//	public void setUserid(Integer userid) {
//		this.userid = userid;
//	}

//	public Integer getTimeid() {
//		return timeid;
//	}
//
//	public void setTimeid(Integer timeid) {
//		this.timeid = timeid;
//	}

//	public String getWorkdate() {
//		return workdate;
//	}
//
//	public void setWorkdate(String workdate) {
//		this.workdate = workdate;
//	}

	public Integer getIsovertime() {
		return isovertime;
	}

	public void setIsovertime(Integer isovertime) {
		this.isovertime = isovertime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserDeptName() {
		return userDeptName;
	}

	public void setUserDeptName(String userDeptName) {
		this.userDeptName = userDeptName;
	}
}
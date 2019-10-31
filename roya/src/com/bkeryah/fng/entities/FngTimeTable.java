package com.bkeryah.fng.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.bkeryah.entities.licences.BldLicArchiveId;

@Entity
@Table(name = "FNG_TIMETABLE")
public class FngTimeTable {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "TIMEID")
	private Integer timeShiftId;
	@Column(name = "TIMENAME")
	private String timeName;
	@Column(name = "INTIME")
	private String startWork;
	@Column(name = "OUTTIME")
	private String endWork;
	@Column(name = "BINTIME")
	private String fingerSatrtOpen;
	@Column(name = "EINTIME")
	private String fingerStartClosed;
	@Column(name = "BOUTTIME")
	private String fingerEndOpen;
	@Column(name = "EOUTTIME")
	private String fingerEndClosed;
	@Column(name = "LATETIME")
	private String lateTimeAllowed;
	@Column(name = "LEAVETIME")
	private String leaveTime;
	@Column(name = "MUSTIN")
	private String mustIn;
	@Column(name = "MUSTOUT")
	private String mustOut;
	@Column(name = "ISFREETIME")
	private String isFreeTime;
	@Column(name = "ISOVERTIME")
	private String isOverTime;
	@Column(name = "WORKDAYS")
	private Integer workDays;
	@Column(name = "LONGTIME")
	private Integer longTime;
	
	public String getTimeName() {
		return timeName;
	}
	public void setTimeName(String timeName) {
		this.timeName = timeName;
	}
	public String getStartWork() {
		return startWork;
	}
	public void setStartWork(String startWork) {
		this.startWork = startWork;
	}
	public String getEndWork() {
		return endWork;
	}
	public void setEndWork(String endWork) {
		this.endWork = endWork;
	}
	public String getFingerSatrtOpen() {
		return fingerSatrtOpen;
	}
	public void setFingerSatrtOpen(String fingerSatrtOpen) {
		this.fingerSatrtOpen = fingerSatrtOpen;
	}
	public String getFingerStartClosed() {
		return fingerStartClosed;
	}
	public void setFingerStartClosed(String fingerStartClosed) {
		this.fingerStartClosed = fingerStartClosed;
	}
	public String getFingerEndOpen() {
		return fingerEndOpen;
	}
	public void setFingerEndOpen(String fingerEndOpen) {
		this.fingerEndOpen = fingerEndOpen;
	}
	public String getFingerEndClosed() {
		return fingerEndClosed;
	}
	public void setFingerEndClosed(String fingerEndClosed) {
		this.fingerEndClosed = fingerEndClosed;
	}
	public String getLateTimeAllowed() {
		return lateTimeAllowed;
	}
	public void setLateTimeAllowed(String lateTimeAllowed) {
		this.lateTimeAllowed = lateTimeAllowed;
	}
	public String getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}
	public String getMustIn() {
		return mustIn;
	}
	public void setMustIn(String mustIn) {
		this.mustIn = mustIn;
	}
	public String getMustOut() {
		return mustOut;
	}
	public void setMustOut(String mustOut) {
		this.mustOut = mustOut;
	}
	public String getIsFreeTime() {
		return isFreeTime;
	}
	public void setIsFreeTime(String isFreeTime) {
		this.isFreeTime = isFreeTime;
	}
	public String getIsOverTime() {
		return isOverTime;
	}
	public void setIsOverTime(String isOverTime) {
		this.isOverTime = isOverTime;
	}
	public Integer getWorkDays() {
		return workDays;
	}
	public void setWorkDays(Integer workDays) {
		this.workDays = workDays;
	}
	public Integer getLongTime() {
		return longTime;
	}
	public void setLongTime(Integer longTime) {
		this.longTime = longTime;
	}
	public Integer getTimeShiftId() {
		return timeShiftId;
	}
	public void setTimeShiftId(Integer timeShiftId) {
		this.timeShiftId = timeShiftId;
	}
	
}
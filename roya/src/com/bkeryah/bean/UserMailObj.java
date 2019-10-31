/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.bean;

import java.io.Serializable;

import javax.persistence.Transient;

import utilities.Utils;

/**
 *
 * @author IbrahimDarwiesh
 */

public class UserMailObj implements Serializable, Comparable {

	public String DateNumber;
	public String WrkId;
	public int StepId;
	public int FromId;
	public int ToId;
	public String wrkSender;
	public String wrkReciever;
	public String AppId;
	public String appTitle;
	public String wrkHdate;
	public String Purpose;
	public int WrkRoleId;
	public String WrkCreateTime;
	public String wrkIncomeNo;
	public String WrkIncomeHDate;
	public int WrkTotalSteps;
	public int WrkAttachCount;
	public int WrkHasComment;
	public String WrkGDate;
	public int IsRead;
	public String WrkColor;
	public Integer hasComment;
	public String firstComment;
	private int wrkType;
	private String nameEmploye;
	private String jobname;
	private int empnumber;
	private boolean checked;
	private int appType;
	public Integer wrkOutcomeNo;
	@Transient
	private String appTypeName;

	public String getConvWrkAttachCount() {
		return Utils.convertTextWithArNumric("" + WrkAttachCount);
	}

	public String getConvWrkIncomeNo() {
		return Utils.convertTextWithArNumric(wrkIncomeNo);
	}

	public String getConvWrkTotalSteps() {
		return Utils.convertTextWithArNumric("" + WrkTotalSteps);
	}

	public String getConvWrkCreateTime() {
		return Utils.convertTextWithArNumric(WrkCreateTime);
	}

	public String getConvWrkHdate() {
		return Utils.convertTextWithArNumric(wrkHdate);
	}

	public String getDateNumber() {
		return DateNumber;
	}

	public void setDateNumber(String dateNumber) {
		DateNumber = dateNumber;
	}

	public String getWrkId() {
		return WrkId;
	}

	public void setWrkId(String wrkId) {
		WrkId = wrkId;
	}

	public int getStepId() {
		return StepId;
	}

	public void setStepId(int stepId) {
		StepId = stepId;
	}

	public int getFromId() {
		return FromId;
	}

	public void setFromId(int fromId) {
		FromId = fromId;
	}

	public int getToId() {
		return ToId;
	}

	public void setToId(int toId) {
		ToId = toId;
	}

	public String getWrkSender() {
		return wrkSender;
	}

	public void setWrkSender(String wrkSender) {
		this.wrkSender = wrkSender;
	}

	public String getWrkReciever() {
		return wrkReciever;
	}

	public void setWrkReciever(String wrkReciever) {
		this.wrkReciever = wrkReciever;
	}

	public String getAppId() {
		return AppId;
	}

	public void setAppId(String appId) {
		AppId = appId;
	}

	public String getAppTitle() {
		return appTitle;
	}

	public void setAppTitle(String appTitle) {
		this.appTitle = appTitle;
	}

	public String getWrkHdate() {
		return wrkHdate;
	}

	public void setWrkHdate(String wrkHdate) {
		this.wrkHdate = wrkHdate;
	}

	public String getPurpose() {
		return Purpose;
	}

	public void setPurpose(String purpose) {
		Purpose = purpose;
	}

	public int getWrkRoleId() {
		return WrkRoleId;
	}

	public void setWrkRoleId(int wrkRoleId) {
		WrkRoleId = wrkRoleId;
	}

	public String getWrkCreateTime() {
		return WrkCreateTime;
	}

	public void setWrkCreateTime(String wrkCreateTime) {
		WrkCreateTime = wrkCreateTime;
	}

	public String getWrkIncomeNo() {
		return wrkIncomeNo;
	}

	public void setWrkIncomeNo(String wrkIncomeNo) {
		this.wrkIncomeNo = wrkIncomeNo;
	}

	public String getWrkIncomeHDate() {
		return WrkIncomeHDate;
	}

	public void setWrkIncomeHDate(String wrkIncomeHDate) {
		WrkIncomeHDate = wrkIncomeHDate;
	}

	public int getWrkTotalSteps() {
		return WrkTotalSteps;
	}

	public void setWrkTotalSteps(int wrkTotalSteps) {
		WrkTotalSteps = wrkTotalSteps;
	}

	public int getWrkAttachCount() {
		return WrkAttachCount;
	}

	public void setWrkAttachCount(int wrkAttachCount) {
		WrkAttachCount = wrkAttachCount;
	}

	public int getWrkHasComment() {
		return WrkHasComment;
	}

	public void setWrkHasComment(int wrkHasComment) {
		WrkHasComment = wrkHasComment;
	}

	public String getWrkGDate() {
		return WrkGDate;
	}

	public void setWrkGDate(String wrkGDate) {
		WrkGDate = wrkGDate;
	}

	public int getIsRead() {
		return IsRead;
	}

	public void setIsRead(int isRead) {
		IsRead = isRead;
	}

	public String getWrkColor() {
		return WrkColor;
	}

	public void setWrkColor(String wrkColor) {
		WrkColor = wrkColor;
	}

	public Integer getHasComment() {
		return hasComment;
	}

	public void setHasComment(Integer hasComment) {
		this.hasComment = hasComment;
	}

	public String getFirstComment() {
		return firstComment;
	}

	public void setFirstComment(String firstComment) {
		this.firstComment = firstComment;
	}

	public int getWrkType() {
		return wrkType;
	}

	public void setWrkType(int wrkType) {
		this.wrkType = wrkType;
	}

	public String getNameEmploye() {
		return nameEmploye;
	}

	public void setNameEmploye(String nameEmploye) {
		this.nameEmploye = nameEmploye;
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public int getEmpnumber() {
		return empnumber;
	}

	public void setEmpnumber(int empnumber) {
		this.empnumber = empnumber;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int getAppType() {
		return appType;
	}

	public void setAppType(int appType) {
		this.appType = appType;
	}

	public String getAppTypeName() {
		return appTypeName;
	}

	public void setAppTypeName(String appTypeName) {
		this.appTypeName = appTypeName;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Integer getWrkOutcomeNo() {
		return wrkOutcomeNo;
	}

	public void setWrkOutcomeNo(Integer wrkOutcomeNo) {
		this.wrkOutcomeNo = wrkOutcomeNo;
	}

}

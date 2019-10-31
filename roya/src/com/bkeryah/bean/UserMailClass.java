///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.bkeryah.bean;
//
//import java.io.Serializable;
//
//import javax.persistence.Transient;
//
//import com.bkeryah.penalties.LicTrdMasterFile;
//
//import utilities.Utils;
//
///**
// *
// * @author IbrahimDarwiesh
// */
//
//public class UserMailClass implements Serializable,Comparable {
//
//	public String DateNumber;
//	public String WrkId;
//	public int StepId;
//	public int FromId;
//	public int ToId;
//	public String wrkSender;
//	public String wrkReciever;
//	public String AppId;
//	public String appTitle;
//	public String wrkHdate;
//	public String Purpose;
//	public int WrkRoleId;
//	public String WrkCreateTime;
//	public String wrkIncomeNo;
//	public String WrkIncomeHDate;
//	public int WrkTotalSteps;
//	public int WrkAttachCount;
//	public int WrkHasComment;
//	public String WrkGDate;
//	public int IsRead;
//	public String WrkColor;
//	public Integer hasComment;
//	public String firstComment;
//	private int wrkType;
//	private String nameEmploye;
//	private String jobname;
//	private int empnumber;
//	private boolean checked;
//	private int appType;
//	@Transient
//	private String appTypeName;
//	
//	
//	
//	
//	public int getWrkType() {
//		return wrkType;
//	}
//
//	public void setWrkType(int wrkType) {
//		this.wrkType = wrkType;
//	}
//
//	public UserMailClass() {
//	}
//
//	public UserMailClass(String DateNumber, String WrkId, int StepId, int FromId, int ToId, String WrkSender,
//			String wrkReciever, String AppId, String AppTitle, String WrkHdate, String Purpose, int WrkRoleId,
//			String WrkCreateTime, String WrkIncomeNo, String WrkIncomeHDate, int WrkTotalSteps, int WrkAttachCount,
//			int WrkHasComment, String WrkGDate, int IsRead, String WrkColor, int wrk_type) {
//		this.DateNumber = DateNumber;
//		this.WrkId = WrkId;
//		this.StepId = StepId;
//		this.FromId = FromId;
//		this.ToId = ToId;
//		this.wrkSender = WrkSender;
//		this.wrkReciever = wrkReciever;
//		this.AppId = AppId;
//		this.appTitle = AppTitle;
//		this.wrkHdate = WrkHdate;
//		this.Purpose = Purpose;
//		this.WrkRoleId = WrkRoleId;
//		this.WrkCreateTime = WrkCreateTime;
//		this.wrkIncomeNo = WrkIncomeNo;
//		this.WrkIncomeHDate = WrkIncomeHDate;
//		this.WrkTotalSteps = WrkTotalSteps;
//		this.WrkAttachCount = WrkAttachCount;
//		this.WrkHasComment = WrkHasComment;
//		this.WrkGDate = WrkGDate;
//		this.IsRead = IsRead;
//		this.WrkColor = WrkColor;
//		this.wrkType = wrk_type;
//	}
//
//	public String getDateNumber() {
//		return DateNumber;
//	}
//
//	public void setDateNumber(String DateNumber) {
//		this.DateNumber = DateNumber;
//	}
//
//	public String getWrkId() {
//		return WrkId;
//	}
//
//	public void setWrkId(String WrkId) {
//		this.WrkId = WrkId;
//	}
//
//	public int getStepId() {
//		return StepId;
//	}
//
//	public void setStepId(int StepId) {
//		this.StepId = StepId;
//	}
//
//	public int getFromId() {
//		return FromId;
//	}
//
//	public void setFromId(int FromId) {
//		this.FromId = FromId;
//	}
//
//	public int getToId() {
//		return ToId;
//	}
//
//	public void setToId(int ToId) {
//		this.ToId = ToId;
//	}
//
//	public String getWrkSender() {
//		return wrkSender;
//	}
//
//	public void setWrkSender(String wrkSender) {
//		this.wrkSender = wrkSender;
//	}
//
//	public String getWrkReciever() {
//		return wrkReciever;
//	}
//
//	public void setWrkReciever(String wrkReciever) {
//		this.wrkReciever = wrkReciever;
//	}
//
//	public String getAppId() {
//		return AppId;
//	}
//
//	public void setAppId(String AppId) {
//		this.AppId = AppId;
//	}
//
//	public String getAppTitle() {
//		return appTitle;
//	}
//
//	public void setAppTitle(String AppTitle) {
//		this.appTitle = AppTitle;
//	}
//
//	public String getWrkHdate() {
//		return wrkHdate;
//	}
//
//	public String getConvWrkHdate() {
//		return Utils.convertTextWithArNumric(wrkHdate);
//	}
//
//	public void setWrkHdate(String WrkHdate) {
//		this.wrkHdate = WrkHdate;
//	}
//
//	public String getPurpose() {
//		return Purpose;
//	}
//
//	public void setPurpose(String Purpose) {
//		this.Purpose = Purpose;
//	}
//
//	public int getWrkRoleId() {
//		return WrkRoleId;
//	}
//
//	public void setWrkRoleId(int WrkRoleId) {
//		this.WrkRoleId = WrkRoleId;
//	}
//
//	public String getWrkCreateTime() {
//		return WrkCreateTime;
//	}
//
//	public String getConvWrkCreateTime() {
//		return Utils.convertTextWithArNumric(WrkCreateTime);
//	}
//
//	public void setWrkCreateTime(String WrkCreateTime) {
//		this.WrkCreateTime = WrkCreateTime;
//	}
//
//	public String getWrkIncomeNo() {
//		return wrkIncomeNo;
//	}
//
//	public String getConvWrkIncomeNo() {
//		return Utils.convertTextWithArNumric(wrkIncomeNo);
//	}
//
//	public void setWrkIncomeNo(String WrkIncomeNo) {
//		this.wrkIncomeNo = WrkIncomeNo;
//	}
//
//	public String getWrkIncomeHDate() {
//		return WrkIncomeHDate;
//	}
//
//	public void setWrkIncomeHDate(String WrkIncomeHDate) {
//		this.WrkIncomeHDate = WrkIncomeHDate;
//	}
//
//	public int getWrkTotalSteps() {
//		return WrkTotalSteps;
//	}
//
//	public String getConvWrkTotalSteps() {
//		return Utils.convertTextWithArNumric("" + WrkTotalSteps);
//	}
//
//	public void setWrkTotalSteps(int WrkTotalSteps) {
//		this.WrkTotalSteps = WrkTotalSteps;
//	}
//
//	public int getWrkAttachCount() {
//		return WrkAttachCount;
//	}
//
//	public String getConvWrkAttachCount() {
//		return Utils.convertTextWithArNumric("" + WrkAttachCount);
//	}
//
//	public void setWrkAttachCount(int WrkAttachCount) {
//		this.WrkAttachCount = WrkAttachCount;
//	}
//
//	public Integer getWrkHasComment() {
//		return WrkHasComment;
//	}
//
//	public void setWrkHasComment(Integer WrkHasComment) {
//		this.WrkHasComment = WrkHasComment;
//	}
//
//	public String getWrkGDate() {
//		return WrkGDate;
//	}
//
//	public void setWrkGDate(String WrkGDate) {
//		this.WrkGDate = WrkGDate;
//	}
//
//	public int getIsRead() {
//		return IsRead;
//	}
//
//	public void setIsRead(int IsRead) {
//		this.IsRead = IsRead;
//	}
//
//	public String getWrkColor() {
//		return WrkColor;
//	}
//
//	public void setWrkColor(String WrkColor) {
//		this.WrkColor = WrkColor;
//	}
//
//	public int getHasComment() {
//		return hasComment;
//	}
//
//	public void setHasComment(int hasComment) {
//		this.hasComment = hasComment;
//	}
//
//	public String getFirstComment() {
//		return firstComment;
//	}
//
//	public void setFirstComment(String firstComment) {
//		this.firstComment = firstComment;
//	}
//
//	public String getNameEmploye() {
//		return nameEmploye;
//	}
//
//	public void setNameEmploye(String nameEmploye) {
//		this.nameEmploye = nameEmploye;
//	}
//
//	public String getJobname() {
//		return jobname;
//	}
//
//	public void setJobname(String jobname) {
//		this.jobname = jobname;
//	}
//
//	public int getEmpnumber() {
//		return empnumber;
//	}
//
//	public void setEmpnumber(int empnumber) {
//		this.empnumber = empnumber;
//	}
//
//	public boolean isChecked() {
//		return checked;
//	}
//
//	public void setChecked(boolean checked) {
//		this.checked = checked;
//	}
//	
//	
//	
//	
//
//	public String getAppTypeName() {
//		return appTypeName;
//	}
//
//	public void setAppTypeName(String appTypeName) {
//		this.appTypeName = appTypeName;
//	}
//
//	@Override
//	public int compareTo(Object obj) {
//		UserMailClass mail = (UserMailClass) obj;
//		if(Integer.parseInt(this.AppId)<Integer.parseInt(mail.AppId))
//			return 1;
//		else if(Integer.parseInt(this.AppId)>Integer.parseInt(mail.AppId))
//			return -1;
//		else
//			return 0;
//	}
//
//	public int getAppType() {
//		return appType;
//	}
//
//	public void setAppType(int appType) {
//		this.appType = appType;
//	}
//	@Override
//	public boolean equals(Object obj) {
//		return ((UserMailClass) obj).getAppId().equals(this.getAppId());
//	}
//	
//}

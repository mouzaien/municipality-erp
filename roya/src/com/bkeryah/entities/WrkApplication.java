package com.bkeryah.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

import utilities.Utils;

@Entity
@Table(name = "WRK_APPLICATION")
public class WrkApplication implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private WrkApplicationId id;
	@Column(name = "APP_ID")
	private Integer arcRecordId;
	@Column(name = "FROM_ID")
	private Integer fromUserId;
	@Column(name = "TO_ID")
	private Integer toUserId;
	@Column(name = "COMM")
	private String applicationUsercomment;
	@Column(name = "REF_ATTACH")
	private Integer refAttach;
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE")
	private Date applicationCreateDate;
	@Column(name = "CREATE_TIME")
	private String applicationCreateTime;
	@Column(name = "STATUS")
	private String applicationStatus;
	@Column(name = "H_DATE")
	private String hijriDate;
	@Column(name = "F_READ")
	private Integer applicationIsRead;
	@Column(name = "APP_PURP")
	private Integer applicationPurpose;
	@Column(name = "FINISH_DATE")
	private String applicationFinishDate;
	@Column(name = "F_REPLY")
	private Integer applicationIsReply;
	@Column(name = "IS_VISIBLE")
	private Integer applicationIsVisible;
	// @Column(name = "USER_DEP_JOB",nullable=true)
	// private Integer applicationUserDeptJob;

	 @Formula("(select u.FNAME from ARC_USERS u where u.USER_ID = FROM_ID )")
//	@Formula("(select u.FNAME from ARC_USERS u where u.FROM_ID = USER_ID )")
	private String fromName;

	@Formula("(select u.FNAME from ARC_USERS u where u.USER_ID = TO_ID )")

//	@Formula("(select u.FNAME from ARC_USERS u where u.TO_ID =USER_ID )")
	private String toName;

	@Transient
	private String userComment;

	@Transient
	private boolean visibleFlag;

	@Transient
	private Integer toCopyId;
	@Transient
	private String applicationUsercommentStr;

	public String getApplicationUsercommentStr() {
		return Utils.convertTextWithArNumric(applicationUsercommentStr);
	}

	public void setApplicationUsercommentStr(String applicationUsercommentStr) {
		this.applicationUsercomment = Utils.convertToEnglishDigits(applicationUsercommentStr);
		this.applicationUsercommentStr = Utils.convertToEnglishDigits(applicationUsercommentStr);
	}

	public boolean isVisibleFlag() {
		return visibleFlag;
	}

	public void setVisibleFlag(boolean visibleFlag) {
		this.visibleFlag = visibleFlag;
	}

	public Integer getArcRecordId() {
		return arcRecordId;
	}

	public void setArcRecordId(Integer arcRecordId) {
		this.arcRecordId = arcRecordId;
	}

	public Integer getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Integer getToUserId() {
		return toUserId;
	}

	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}

	public String getApplicationUsercomment() {
		return applicationUsercomment;
	}

	public void setApplicationUsercomment(String applicationUsercomment) {
		this.applicationUsercomment = applicationUsercomment;
	}

	public Integer getRefAttach() {
		return refAttach;
	}

	public void setRefAttach(Integer refAttach) {
		this.refAttach = refAttach;
	}

	public Date getApplicationCreateDate() {
		return applicationCreateDate;
	}

	public void setApplicationCreateDate(Date applicationCreateDate) {
		this.applicationCreateDate = applicationCreateDate;
	}

	public String getApplicationCreateTime() {
		return applicationCreateTime;
	}

	public void setApplicationCreateTime(String applicationCreateTime) {
		this.applicationCreateTime = applicationCreateTime;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public String getHijriDate() {
		return hijriDate;
	}

	public void setHijriDate(String hijriDate) {
		this.hijriDate = hijriDate;
	}

	public Integer getApplicationIsRead() {
		return applicationIsRead;
	}

	public void setApplicationIsRead(Integer applicationIsRead) {
		this.applicationIsRead = applicationIsRead;
	}

	public Integer getApplicationPurpose() {

		return applicationPurpose == null ? 10 : applicationPurpose;
	}

	public void setApplicationPurpose(Integer applicationPurpose) {
		this.applicationPurpose = applicationPurpose;
	}

	public String getApplicationFinishDate() {
		return applicationFinishDate;
	}

	public void setApplicationFinishDate(String applicationFinishDate) {
		this.applicationFinishDate = applicationFinishDate;
	}

	public Integer getApplicationIsReply() {
		return applicationIsReply;
	}

	public void setApplicationIsReply(Integer applicationIsReply) {
		this.applicationIsReply = applicationIsReply;
	}

	public Integer getApplicationIsVisible() {
		return applicationIsVisible;
	}

	public void setApplicationIsVisible(Integer applicationIsVisible) {
		this.applicationIsVisible = applicationIsVisible;
	}

	public String getUserComment() {
		if (this.getApplicationUsercomment() == null) {
			return "";
		}
		return this.getApplicationUsercomment();// Utils.convertBytesToString(this.getApplicationUsercomment());
	}

	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}

	public WrkApplicationId getId() {
		return id;
	}

	public void setId(WrkApplicationId id) {
		this.id = id;
	}

	public WrkApplication(WrkApplication application) {
		super();
		this.arcRecordId = application.getArcRecordId();
		this.fromUserId = application.getFromUserId();
		this.toUserId = application.getToUserId();
		this.applicationUsercomment = application.getApplicationUsercomment();
		this.refAttach = application.getRefAttach();
		this.applicationCreateDate = application.getApplicationCreateDate();
		this.applicationCreateTime = application.getApplicationCreateTime();
		this.applicationStatus = application.getApplicationStatus();
		this.hijriDate = application.getHijriDate();
		this.applicationIsRead = application.getApplicationIsRead();
		this.applicationPurpose = application.getApplicationPurpose();
		this.applicationFinishDate = application.getApplicationFinishDate();
		this.applicationIsReply = application.getApplicationIsReply();
		this.applicationIsVisible = application.getApplicationIsVisible();
		this.userComment = application.getUserComment();
		this.visibleFlag = application.isVisibleFlag();
		this.applicationUsercommentStr = application.getApplicationUsercommentStr();
	}

	public WrkApplication() {

	}

	public Integer getToCopyId() {
		return toCopyId;
	}

	public void setToCopyId(Integer toCopyId) {
		this.toCopyId = toCopyId;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

}

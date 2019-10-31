package com.bkeryah.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import utilities.Utils;



@Entity
@Table(name="WRK_COMMENT")
public class AbcComment {
	@Id
	@Column(name="APP_ID")
	private Integer appId;
	@Column(name="APP_NO")
	private String appNo;
	@Column(name="APP_date")
	private String appHdate;
	@Column(name="APP_papers")
	private Integer appPapers;
	@Column(name="APP_to")
	private String appTo;
	@Column(name="APP_subject")
	private  String appSubject;
	@Column(name="APP_attach")
	private String appAttach;
	@Column(name="long_comment")
	private String longComment;
	@Column(name="first_Copy")
	private String firstCopy;
	@Column(name="second_Copy")
	private String secondCopy;
	@Column(name="font_Size")
	private Integer fontSize;
	
	@Column(name="wrote_By")     
	private Integer wroteBy;
	
	
	@Column(name="wrote_In")
	private String wroteIn;
	
	
	@Column(name="marked_By")
	private Integer markedBy;
	
	
	@Column(name="marked_In")
	private String markedIn;
	
	
	@Column(name="signed_By")
	private Integer signedBy;
	
	
	@Column(name="signed_In")
	private String signedIn;
	@Column(name="comment_Type")
	private Integer commentType;
	

	@Column(name="printed_By")
	private Integer printedBy;;
	@Column(name="printed_In")
	private String printedIn;
	@Column(name="sign_type_s_n")
	private String signType;
	@Transient
	private String longCommentArabStr;
	
	public String getLongCommentArabStr() {
		if(longComment!=null){
			String st[]= longComment.split("\n");
			String sUpd="";
			for (String string : st) {
				if(string.contains("@"))
				{
					sUpd=	sUpd.concat(string)+"\n";
				}
				else {
					sUpd=	sUpd.concat(Utils.convertTextWithArNumric(string))+"\n";;
				}
			}
			return sUpd;
		}
		return null;
		
	}
	public void setLongCommentArabStr(String longCommentArabStr) {
		longComment = Utils.convertToEnglishDigits(longCommentArabStr);
		String st[]= longComment.split("\n");
		String sUpd="";
		for (String string : st) {
			if(string.contains("@"))
			{
				sUpd=	sUpd.concat(string)+"\n";
			}
			else {
				sUpd=	sUpd.concat(Utils.convertTextWithArNumric(string))+"\n";;
			}
		}
		this.longCommentArabStr = sUpd;
	}

	
	public Integer getWRKAPPID() {
		return appId;
	}
	public void setWRKAPPID(Integer wRKAPPID) {
		appId = wRKAPPID;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getAppHdate() {
		return appHdate;
	}
	public void setAppHdate(String appHdate) {
		this.appHdate = appHdate;
	}
	public Integer getAppPapers() {
		return appPapers;
	}
	public void setAppPapers(Integer appPapers) {
		this.appPapers = appPapers;
	}
	public String getAppTo() {
		return appTo;
	}
	public void setAppTo(String appTo) {
		this.appTo = appTo;
	}
	public String getAppSubject() {
		return appSubject;
	}
	public void setAppSubject(String appSubject) {
		this.appSubject = appSubject;
	}
	public String getAppAttach() {
		return appAttach;
	}
	public void setAppAttach(String appAttach) {
		this.appAttach = appAttach;
	}
	public String getLongComment() {
		return longComment;
	}
	public void setLongComment(String longComment) {
		this.longComment = Utils.convertToEnglishDigits(longComment);
		
	}
	public String getFirstCopy() {
		return firstCopy;
	}
	public void setFirstCopy(String firstCopy) {
		this.firstCopy = firstCopy;
	}
	public String getSecondCopy() {
		return secondCopy;
	}
	public void setSecondCopy(String secondCopy) {
		this.secondCopy = secondCopy;
	}
	public Integer getFontSize() {
		return fontSize;
	}
	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}
	public Integer getWroteBy() {
		return wroteBy;
	}
	public void setWroteBy(Integer wroteBy) {
		this.wroteBy = wroteBy;
	}
	public String getWroteIn() {
		return wroteIn;
	}
	public void setWroteIn(String wroteIn) {
		this.wroteIn = wroteIn;
	}
	public Integer getMarkedBy() {
		return markedBy;
	}
	public void setMarkedBy(Integer markedBy) {
		this.markedBy = markedBy;
	}
	public String getMarkedIn() {
		return markedIn;
	}
	public void setMarkedIn(String markedIn) {
		this.markedIn = markedIn;
	}
	public Integer getSignedBy() {
		return signedBy;
	}
	public void setSignedBy(Integer signedBy) {
		this.signedBy = signedBy;
	}
	public String getSignedIn() {
		return signedIn;
	}
	public void setSignedIn(String signedIn) {
		this.signedIn = signedIn;
	}
	public Integer getCommentType() {
		return commentType;
	}
	public void setCommentType(Integer commentType) {
		this.commentType = commentType;
	}
	public Integer getPrintedBy() {
		return printedBy;
	}
	public void setPrintedBy(Integer printedBy) {
		this.printedBy = printedBy;
	}
	public String getPrintedIn() {
		return printedIn;
	}
	public void setPrintedIn(String printedIn) {
		this.printedIn = printedIn;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public AbcComment() {
		super();
	}
	
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	




	

}

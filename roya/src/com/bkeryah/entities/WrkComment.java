package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import utilities.Utils;

@Entity
@Table(name = "WRK_COMMENT")
public class WrkComment {
	@Id
	@Column(name = "APP_ID")
	private Integer appId;
	@Column(name = "APP_NO")
	private String appNo;
	@Column(name = "APP_date")
	private String appHdate;
	@Column(name = "APP_papers")
	private Integer appPapers;
	@Column(name = "APP_to")
	private String appTo;
	@Column(name = "APP_subject")
	private String appSubject;
	@Column(name = "APP_attach")
	private String appAttach;
	@Column(name = "long_comment")
	private String longComment;
	@Column(name = "first_Copy")
	private String firstCopy;
	@Column(name = "second_Copy")
	private String secondCopy;
	@Column(name = "font_Size")
	private Integer fontSize;

	@Column(name = "wrote_By")
	private Integer wroteBy;
	@Column(name = "pdfComment")
	private String pdfComment;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wrote_By", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private ArcUsers wroteBYUser;

	@Column(name = "wrote_In")
	private String wroteIn;

	@Column(name = "marked_By")
	private Integer markedBy;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "marked_By", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private ArcUsers markedByUser;

	@Column(name = "marked_In")
	private String markedIn;

	@Column(name = "signed_By")
	private Integer signedBy;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "signed_By", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private ArcUsers signedByUser;

	@Column(name = "signed_In")
	private String signedIn;
	@Column(name = "comment_Type")
	private Integer commentType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMMENT_TYPE", referencedColumnName = "ID", insertable = false, updatable = false)
	private WrkCommentType wrkCommentType;
	@Column(name = "printed_By")
	private Integer printedBy;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRINTED_BY", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private ArcUsers printedByUser;
	@Column(name = "printed_In")
	private String printedIn;
	@Column(name = "sign_type_s_n")
	private String signType;
	@Transient
	private String longCommentArabStr;

	public String getLongCommentArabStr() {
		if (longComment != null) {
			String st[] = longComment.split("\n");
			String sUpd = "";
			int count =0;
			for (String string : st) {
				if (string.contains("@")) {
					if(count <st.length-1)
					sUpd = sUpd.concat(string) + "\n";
					else
						sUpd = sUpd.concat(string);	
				} else {
					if(count <st.length-1)
					sUpd = sUpd.concat(Utils.convertTextWithArNumric(string)) + "\n";
					else
						sUpd = sUpd.concat(Utils.convertTextWithArNumric(string));
				}
				count++;
			}
			return sUpd;
		}
		return null;

	}

	public void setLongCommentArabStr(String longCommentArabStr) {
		longComment = Utils.convertToEnglishDigits(longCommentArabStr);
		if (longComment != null) {
			String st[] = longComment.split("\n");
			String sUpd = "";
			int count =0;
			for (String string : st) {
				if (string.contains("@")) {
					if(count <st.length-1)
					sUpd = sUpd.concat(string) + "\n";
					else
						sUpd = sUpd.concat(string);	
				} else {
					if(count <st.length-1)
					sUpd = sUpd.concat(Utils.convertTextWithArNumric(string)) + "\n";
					else
						sUpd = sUpd.concat(Utils.convertTextWithArNumric(string));
				}
				count++;
			}
			this.longCommentArabStr = sUpd;
		}
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "wrkComment")
	private ArcRecords arcRecords;

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
		// return Utils.convertTextWithArNumric(longComment);
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

	public WrkComment() {
		super();
	}

	public ArcRecords getArcRecords() {
		return arcRecords;
	}

	public void setArcRecords(ArcRecords arcRecords) {
		this.arcRecords = arcRecords;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public ArcUsers getWroteBYUser() {
		return wroteBYUser;
	}

	public void setWroteBYUser(ArcUsers wroteBYUser) {
		this.wroteBYUser = wroteBYUser;
	}

	public ArcUsers getMarkedByUser() {
		return markedByUser;
	}

	public void setMarkedByUser(ArcUsers markedByUser) {
		this.markedByUser = markedByUser;
	}

	public ArcUsers getSignedByUser() {
		return signedByUser;
	}

	public void setSignedByUser(ArcUsers signedByUser) {
		this.signedByUser = signedByUser;
	}

	public WrkCommentType getWrkCommentType() {
		return wrkCommentType;
	}

	public void setWrkCommentType(WrkCommentType wrkCommentType) {
		this.wrkCommentType = wrkCommentType;
	}

	public ArcUsers getPrintedByUser() {
		return printedByUser;
	}

	public void setPrintedByUser(ArcUsers printedByUser) {
		this.printedByUser = printedByUser;
	}

	public String getPdfComment() {
		return pdfComment;
	}

	public void setPdfComment(String pdfComment) {
		this.pdfComment = pdfComment;
	}
}

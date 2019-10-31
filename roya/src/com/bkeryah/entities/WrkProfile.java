package com.bkeryah.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "WRK_PROFILE")
public class WrkProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USER_ID")
	private Integer userId;
	@Column(name = "INBOX_TYPE", nullable = true)
	private String inboxType;
//	@Lob
//	@Column(name = "SIGN", nullable = true)
//	private byte[] sign;// التوقيع الشخصي
	@Column(name = "EMP_1", nullable = true)
	private Integer employee1;
	@Column(name = "EMP_2", nullable = true)
	private Integer employee2;
	@Lob
	@Column(name = "SIGN2", nullable = true)
	private byte[] sign2;// التوقيع بالنيابة عن المدير
	@Column(name = "COMMENT_EXE", nullable = true)
	private String commentExe;
	@Column(name = "COMPUTER_NAME", nullable = true)
	private String computerName;
	@Temporal(TemporalType.DATE)
	@Column(name = "PASSDATE", nullable = true)
	private Date passDate;
	@Column(name = "SIGN_PASSWORD", nullable = true)
	private String signPassword;
	@Column(name = "LAST_LOGIN_FROM", nullable = true)
	private String lastLoginForm;
	@Column(name = "LAST_LOGIN_STS_F_S", nullable = true)
	private String lastLoginStatus;
	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_LOGIN_DATE", nullable = true)
	private Date lastLoginDate;
	@Column(name = "PROTECT_SMS_Y_N", nullable = true)
	private String protectSMS;
	@Lob
	@Column(name = "SIGN3", nullable = true)
	private byte[] sign3;// التوقيع بالانابة عن الامين
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "wrkProfile")
	private ArcUsers user;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getInboxType() {
		return inboxType;
	}

	public void setInboxType(String inboxType) {
		this.inboxType = inboxType;
	}
	public int getEmployee1() {
		return employee1;
	}

	public void setEmployee1(int employee1) {
		this.employee1 = employee1;
	}

	public int getEmployee2() {
		return employee2;
	}

	public void setEmployee2(int employee2) {
		this.employee2 = employee2;
	}

	public byte[] getSign2() {
		return sign2;
	}

	public void setSign2(byte[] sign2) {
		this.sign2 = sign2;
	}

	public String getCommentExe() {
		return commentExe;
	}

	public void setCommentExe(String commentExe) {
		this.commentExe = commentExe;
	}

	public String getComputerName() {
		return computerName;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

	public Date getPassDate() {
		return passDate;
	}

	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}

	public String getSignPassword() {
		return signPassword;
	}

	public void setSignPassword(String signPassword) {
		this.signPassword = signPassword;
	}

	public String getLastLoginForm() {
		return lastLoginForm;
	}

	public void setLastLoginForm(String lastLoginForm) {
		this.lastLoginForm = lastLoginForm;
	}

	public String getLastLoginStatus() {
		return lastLoginStatus;
	}

	public void setLastLoginStatus(String lastLoginStatus) {
		this.lastLoginStatus = lastLoginStatus;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getProtectSMS() {
		return protectSMS;
	}

	public void setProtectSMS(String protectSMS) {
		this.protectSMS = protectSMS;
	}

	public byte[] getSign3() {
		return sign3;
	}

	public void setSign3(byte[] sign3) {
		this.sign3 = sign3;
	}

	public ArcUsers getUser() {
		return user;
	}

	public void setUser(ArcUsers user) {
		this.user = user;
	}

}

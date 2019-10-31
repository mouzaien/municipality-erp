package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "HRS_USER_ABSENT")
public class HrsUserAbsent {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private int id;
	@Column(name = "USER_ID", nullable = false)
	private int userId;
	@Column(name = "empno")
	private Integer empNo;
	@Column(name = "ABSDATE")
	private String abseDate;
	@Column(name = "ABSFROM")
	private String absForm;
	@Column(name = "ABSTO")
	private String absTo;
	@Column(name = "ABSTYPE")
	private String absType;
	@Column(name = "JABSDATE")
	private String jabsDate;
	@Column(name = "ACCEPT_Y_N")
	private String accept;
	@Column(name = "REASON")
	private String reason;

	@Transient
	private String userName;

	public HrsUserAbsent() {
		super();
	}

	public HrsUserAbsent(int userId, Integer empNo, String abseDate, String absForm, String absTo, String absType,
			String jabsDate, String reason) {
		super();
		this.userId = userId;
		this.empNo = empNo;
		this.abseDate = abseDate;
		this.absForm = absForm;
		this.absTo = absTo;
		this.absType = absType;
		this.jabsDate = jabsDate;
		this.reason = reason;
	}

	public HrsUserAbsent(int userId, String abseDate, Integer empNo, String absForm, String absTo, String absType,
			String jabsDate, String accept) {
		super();
		this.userId = userId;
		this.empNo = empNo;
		this.abseDate = abseDate;
		this.absForm = absForm;
		this.absTo = absTo;
		this.absType = absType;
		this.jabsDate = jabsDate;
		this.accept = accept;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Integer getEmpNo() {
		return empNo;
	}

	public void setEmpNo(Integer empNo) {
		this.empNo = empNo;
	}

	public String getAbseDate() {
		return abseDate;
	}

	public void setAbseDate(String abseDate) {
		this.abseDate = abseDate;
	}

	public String getAbsForm() {
		return absForm;
	}

	public void setAbsForm(String absForm) {
		this.absForm = absForm;
	}

	public String getAbsTo() {
		return absTo;
	}

	public void setAbsTo(String absTo) {
		this.absTo = absTo;
	}

	public String getAbsType() {
		return absType;
	}

	public void setAbsType(String absType) {
		this.absType = absType;
	}

	public String getJabsDate() {
		return jabsDate;
	}

	public void setJabsDate(String jabsDate) {
		this.jabsDate = jabsDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}

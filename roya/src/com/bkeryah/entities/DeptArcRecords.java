package com.bkeryah.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "DEPT_ARC_RECORDS")
public class DeptArcRecords {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "DONNER")
	private Integer donner;
	@Column(name = "DONNER_DEPT_ID")
	private Integer donnerDeptId;
	@Column(name = "DONNER_DEPT_NAME")
	private String donnerDeptName;
	@Column(name = "SIGN_DATE")
	private Date signDate;
	@Column(name = "ARC_ID")
	private Integer arcId;
	@Column(name = "INCOME_NO")
	private Integer incomeNo;
	@Column(name = "SUBJECT")
	private String subject;
	@Column(name = "ATTACH_NB")
	private Integer attachNb;
	@Column(name = "RECEIVER_DEPT_ID")
	private Integer receiverDeptId;
	@Column(name = "RECEIVER_DEP_NAME")
	private String receiverDepName;
	@Transient
	private Integer wrkId;
	@Transient
	private Integer stepId;
	@Transient
	private String letterFromNo;
	@Transient
	private String letterFromDate;

	@Transient
	private String modified;
	@Transient
	private Integer OutcomeNo;

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDonner() {
		return donner;
	}

	public void setDonner(Integer donner) {
		this.donner = donner;
	}

	public Integer getDonnerDeptId() {
		return donnerDeptId;
	}

	public void setDonnerDeptId(Integer donnerDeptId) {
		this.donnerDeptId = donnerDeptId;
	}

	public String getDonnerDeptName() {
		return donnerDeptName;
	}

	public void setDonnerDeptName(String donnerDeptName) {
		this.donnerDeptName = donnerDeptName;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Integer getArcId() {
		return arcId;
	}

	public void setArcId(Integer arcId) {
		this.arcId = arcId;
	}

	public Integer getIncomeNo() {
		return incomeNo;
	}

	public void setIncomeNo(Integer incomeNo) {
		this.incomeNo = incomeNo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getAttachNb() {
		return attachNb;
	}

	public void setAttachNb(Integer attachNb) {
		this.attachNb = attachNb;
	}

	public Integer getReceiverDeptId() {
		return receiverDeptId;
	}

	public void setReceiverDeptId(Integer receiverDeptId) {
		this.receiverDeptId = receiverDeptId;
	}

	public String getReceiverDepName() {
		return receiverDepName;
	}

	public void setReceiverDepName(String receiverDepName) {
		this.receiverDepName = receiverDepName;
	}

	public Integer getWrkId() {
		return wrkId;
	}

	public void setWrkId(Integer wrkId) {
		this.wrkId = wrkId;
	}

	public Integer getStepId() {
		return stepId;
	}

	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}

	public String getLetterFromNo() {
		return letterFromNo;
	}

	public void setLetterFromNo(String letterFromNo) {
		this.letterFromNo = letterFromNo;
	}

	public String getLetterFromDate() {
		return letterFromDate;
	}

	public void setLetterFromDate(String letterFromDate) {
		this.letterFromDate = letterFromDate;
	}

	public Integer getOutcomeNo() {
		return OutcomeNo;
	}

	public void setOutcomeNo(Integer outcomeNo) {
		OutcomeNo = outcomeNo;
	}

}

package com.bkeryah.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "DEPARTMENT_ARC_RECORDS")
public class DepartmentArcRecords {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "ARCREC_ID")
	private Integer arcrecId;
	@Column(name = "RECEIVER")
	private Integer receiver;
	@Column(name = "SIGN_DATE")
	private Date signDate;
	@Column(name = "DEPT_ID")
	private Integer deptId;
	@Column(name = "ATTACH_NB")
	private Integer attachNB;
	@Column(name = "RECEIVER_DEPT_ID")
	private Integer receiverDeptId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getArcrecId() {
		return arcrecId;
	}

	public void setArcrecId(Integer arcrecId) {
		this.arcrecId = arcrecId;
	}

	public Integer getReceiver() {
		return receiver;
	}

	public void setReceiver(Integer receiver) {
		this.receiver = receiver;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getAttachNB() {
		return attachNB;
	}

	public void setAttachNB(Integer attachNB) {
		this.attachNB = attachNB;
	}

	public Integer getReceiverDeptId() {
		return receiverDeptId;
	}

	public void setReceiverDeptId(Integer receiverDeptId) {
		this.receiverDeptId = receiverDeptId;
	}
}

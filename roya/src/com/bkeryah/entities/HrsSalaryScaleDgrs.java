package com.bkeryah.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="HRS_SALARY_SCALE_DGRS")
public class HrsSalaryScaleDgrs {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name="id")
    private Integer id;
	@Column(name = "CLSSTITLE")
	private String classTitle;
	@Column(name = "ORDERID")
	private Integer  orderId	;
	@Column(name = "CLSSCODE")
	private Integer  classCode	;
	@Column(name = "FISRTSAL")
	private Integer  firstSalary	;
	@Column(name = "CBY")
	private Integer  createdBy	;
	@Column(name = "RANKCODE")
	private Integer  rankCode	;
	@Column(name = "TRANS")
	private Integer  transferSalary	;
	@Column(name = "MANDIN")
	private Integer  mandatorIn	;
	@Column(name = "MANDOUT")
	private Integer  mandatorOut	;
	@Column(name = "CIN")
	private Date  createdIn	;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getClassTitle() {
		return classTitle;
	}
	public void setClassTitle(String classTitle) {
		this.classTitle = classTitle;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getClassCode() {
		return classCode;
	}
	public void setClassCode(Integer classCode) {
		this.classCode = classCode;
	}
	public Integer getFirstSalary() {
		return firstSalary;
	}
	public void setFirstSalary(Integer firstSalary) {
		this.firstSalary = firstSalary;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getRankCode() {
		return rankCode;
	}
	public void setRankCode(Integer rankCode) {
		this.rankCode = rankCode;
	}
	public Integer getTransferSalary() {
		return transferSalary;
	}
	public void setTransferSalary(Integer transferSalary) {
		this.transferSalary = transferSalary;
	}
	public Integer getMandatorIn() {
		return mandatorIn;
	}
	public void setMandatorIn(Integer mandatorIn) {
		this.mandatorIn = mandatorIn;
	}
	public Integer getMandatorOut() {
		return mandatorOut;
	}
	public void setMandatorOut(Integer mandatorOut) {
		this.mandatorOut = mandatorOut;
	}
	public Date getCreatedIn() {
		return createdIn;
	}
	public void setCreatedIn(Date createdIn) {
		this.createdIn = createdIn;
	}
	
}

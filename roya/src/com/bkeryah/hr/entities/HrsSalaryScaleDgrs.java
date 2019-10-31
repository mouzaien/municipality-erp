package com.bkeryah.hr.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "HRS_SALARY_SCALE_DGRS")
public class HrsSalaryScaleDgrs {
	@Column(name = "ID")
	private long id;
	@Column(name = "ORDERID")
	private long orderid;
	@Column(name = "CLSSCODE")
	private long clsscode;
	@Column(name = "CLSSTITLE")
	private String clsstitle;
	@Column(name = "FISRTSAL")
	private long fisrtsal;
	@Column(name = "CBY")
	private long cby;
	@Column(name = "CIN")
	private Date cin;
	@Column(name = "RANKCODE")
	private long rankcode;
	@Column(name = "TRANS")
	private long trans;
	@Column(name = "MANDIN")
	private long mandin;
	@Column(name = "MANDOUT")
	private long mandout;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrderid() {
		return orderid;
	}

	public void setOrderid(long orderid) {
		this.orderid = orderid;
	}

	public long getClsscode() {
		return clsscode;
	}

	public void setClsscode(long clsscode) {
		this.clsscode = clsscode;
	}

	public String getClsstitle() {
		return clsstitle;
	}

	public void setClsstitle(String clsstitle) {
		this.clsstitle = clsstitle;
	}

	public long getFisrtsal() {
		return fisrtsal;
	}

	public void setFisrtsal(long fisrtsal) {
		this.fisrtsal = fisrtsal;
	}

	public long getCby() {
		return cby;
	}

	public void setCby(long cby) {
		this.cby = cby;
	}

	public Date getCin() {
		return cin;
	}

	public void setCin(Date cin) {
		this.cin = cin;
	}

	public long getRankcode() {
		return rankcode;
	}

	public void setRankcode(long rankcode) {
		this.rankcode = rankcode;
	}

	public long getTrans() {
		return trans;
	}

	public void setTrans(long trans) {
		this.trans = trans;
	}

	public long getMandin() {
		return mandin;
	}

	public void setMandin(long mandin) {
		this.mandin = mandin;
	}

	public long getMandout() {
		return mandout;
	}

	public void setMandout(long mandout) {
		this.mandout = mandout;
	}
}

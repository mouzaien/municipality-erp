package com.bkeryah.hr.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "HRS_SALARY_SCALE")
public class HrsSalaryScale {
	@EmbeddedId
	private HrsSalaryScaleId id;
	@Column(name = "RANKTITLE")
	private String ranktitle;
	@Column(name = "BSCSAL")
	private long bscsal;
	@Column(name = "HOURLYPRICE")
	private long hourlyprice;
	@Column(name = "TRANS")
	private long trans;
	@Column(name = "MANDIN")
	private long mandin;
	@Column(name = "MANDOUT")
	private long mandout;
	@Column(name = "YEARLYINC")
	private long yearlyinc;
	@Column(name = "CBY")
	private long cby;
	@Column(name = "CIN")
	private Date cin;
	@Column(name = "CLSSCOUNT")
	private long clsscount;
public HrsSalaryScale() {
	// TODO Auto-generated constructor stub
}
	public HrsSalaryScaleId getId() {
		return id;
	}

	public void setId(HrsSalaryScaleId id) {
		this.id = id;
	}

	public String getRanktitle() {
		return ranktitle;
	}

	public void setRanktitle(String ranktitle) {
		this.ranktitle = ranktitle;
	}

	public long getBscsal() {
		return bscsal;
	}

	public void setBscsal(long bscsal) {
		this.bscsal = bscsal;
	}

	public long getHourlyprice() {
		return hourlyprice;
	}

	public void setHourlyprice(long hourlyprice) {
		this.hourlyprice = hourlyprice;
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

	public long getYearlyinc() {
		return yearlyinc;
	}

	public void setYearlyinc(long yearlyinc) {
		this.yearlyinc = yearlyinc;
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

	public long getClsscount() {
		return clsscount;
	}

	public void setClsscount(long clsscount) {
		this.clsscount = clsscount;
	}

}
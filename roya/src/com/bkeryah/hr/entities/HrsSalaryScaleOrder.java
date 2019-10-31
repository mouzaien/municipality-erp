package com.bkeryah.hr.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "HRS_SALARY_SCALE_ORDR")
public class HrsSalaryScaleOrder {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "DESDATE")
	private String desdate;
	@Column(name = "DESNO")
	private String desno;
	@Column(name = "DESCSOURCE")
	private String descsource;
	@Column(name = "STRTDATE")
	private String strtdate;
	@Column(name = "FLG")
	private Integer flg;
	@Column(name = "CBY")
	private Integer cby;
	@Column(name = "CIN")
	private Date cin;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDesdate() {
		return desdate;
	}

	public void setDesdate(String desdate) {
		this.desdate = desdate;
	}

	public String getDesno() {
		return desno;
	}

	public void setDesno(String desno) {
		this.desno = desno;
	}

	public String getDescsource() {
		return descsource;
	}

	public void setDescsource(String descsource) {
		this.descsource = descsource;
	}

	public String getStrtdate() {
		return strtdate;
	}

	public void setStrtdate(String strtdate) {
		this.strtdate = strtdate;
	}

	public Integer getFlg() {
		return flg;
	}

	public void setFlg(Integer flg) {
		this.flg = flg;
	}

	public Integer getCby() {
		return cby;
	}

	public void setCby(Integer cby) {
		this.cby = cby;
	}

	public Date getCin() {
		return cin;
	}

	public void setCin(Date cin) {
		this.cin = cin;
	}
}
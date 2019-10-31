package com.bkeryah.fng.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "FNG_CHECKINOUT")
public class FngCheckInOut {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "CHECKINOUTID")
	private Integer checkinoutId;
	@Column(name = "USERID")
	private Integer userId;
	@Column(name = "CHECKTIME")
	private String checktime;
	@Column(name = "CHECKTYPE")
	private Integer checktype;
	@Column(name = "SENSORID")
	private String sensorid;
	@Column(name = "CHECKED")
	private Integer checked;
	@Column(name = "PIC")
	private String pic;
	@Column(name = "MODETYP")
	private String modetyp;
	@Column(name = "D1")
	private String d1;
	@Column(name = "D2")
	private String d2;
	@Column(name = "D3")
	private String d3;
	@Column(name = "CHECKTIME24")
	private String checktime24;
	@Column(name = "EMP_PIC")
	private byte[] empPic;
	@Column(name = "U_ID")
	private String cby;
	@Column(name = "CIN")
	private Date cin;
	public Integer getCheckinoutId() {
		return checkinoutId;
	}
	public void setCheckinoutId(Integer checkinoutId) {
		this.checkinoutId = checkinoutId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getChecktime() {
		return checktime;
	}
	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}
	public Integer getChecktype() {
		return checktype;
	}
	public void setChecktype(Integer checktype) {
		this.checktype = checktype;
	}
	public String getSensorid() {
		return sensorid;
	}
	public void setSensorid(String sensorid) {
		this.sensorid = sensorid;
	}
	public Integer getChecked() {
		return checked;
	}
	public void setChecked(Integer checked) {
		this.checked = checked;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getModetyp() {
		return modetyp;
	}
	public void setModetyp(String modetyp) {
		this.modetyp = modetyp;
	}
	public String getD1() {
		return d1;
	}
	public void setD1(String d1) {
		this.d1 = d1;
	}
	public String getD2() {
		return d2;
	}
	public void setD2(String d2) {
		this.d2 = d2;
	}
	public String getD3() {
		return d3;
	}
	public void setD3(String d3) {
		this.d3 = d3;
	}
	public String getChecktime24() {
		return checktime24;
	}
	public void setChecktime24(String checktime24) {
		this.checktime24 = checktime24;
	}
	public byte[] getEmpPic() {
		return empPic;
	}
	public void setEmpPic(byte[] empPic) {
		this.empPic = empPic;
	}
	public String getCby() {
		return cby;
	}
	public void setCby(String cby) {
		this.cby = cby;
	}
	public Date getCin() {
		return cin;
	}
	public void setCin(Date cin) {
		this.cin = cin;
	}
}
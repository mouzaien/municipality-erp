package com.bkeryah.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bkeryah.fng.entities.TstFingerId;

@Entity
@Table(name = "TST_FINGER")
public class Abcd2 extends Object {
	public String getHigriDate() {
		return higriDate;
	}

	public void setHigriDate(String higriDate) {
		this.higriDate = higriDate;
	}

//	@EmbeddedId
//	private TstFingerId id;
	@Column(name = "TMTIME")
	private String tmtime;
	@Column(name = "ONDUTY")
	private String onduty;
	@Column(name = "OFFDUTY")
	private String offduty;
	@Column(name = "CLOCKIN")
	private String clockin;
	@Column(name = "CLOCKOUT")
	private String clockout;
	@Column(name = "DELAY")
	private String delay;
	@Column(name = "EARLY")
	private String early;
	@Column(name = "ABSENT")
	private String absent;
	@Column(name = "EXPDAY")
	private String expday;
	@Column(name = "PIC1")
	private String pic1;
	@Column(name = "PIC2")
	private String pic2;
	@Column(name = "M1")
	private Integer m1;
	@Column(name = "M2")
	private Integer m2;
	
	@Transient
	private String userName;
	
	@Transient
	private String higriDate;
	
	@Transient
	private Integer typeDoc;
	@Transient
	private Integer vacaTyp;

	
	
	
	@Transient
	private String absFrom;
	@Transient
	private String absTo;
	
	@Transient
	private String absType;
	
	
	@Transient
	private Date vacaStart;
	@Transient
	private Date vacaEnd;
	
	@Transient
	private String vacName;
	@Transient
	private String abstName;
	
	//variables for fingerDetails
	@Transient
	private String timeFinger;
	
	@Transient
	private String dateFinger;
	@Transient
	private String delayCalc;
	
	//Variable for calc delay
	
	@Transient
	private String inTime;
	@Transient
	private String outTime;
	
	@Transient
	private String bintime;
	@Transient
	private String eintime;
	
	
	@Transient
	private String bouttime;
	@Transient
	private String eouttime;
	@Transient
	private String latetime;
	private String deptName;
	
	@Transient
	private Integer userid;
	@Transient
	private Date vdate;
	private String fngDate;
	private boolean higriMode;

	public String getDelayCalc() {
		return delayCalc;
	}

	public void setDelayCalc(String delayCalc) {
		this.delayCalc = delayCalc;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTmtime() {
		return tmtime;
	}

	public void setTmtime(String tmtime) {
		this.tmtime = tmtime;
	}

	public String getOnduty() {
		return onduty;
	}

	public void setOnduty(String onduty) {
		this.onduty = onduty;
	}

	public String getOffduty() {
		return offduty;
	}

	public void setOffduty(String offduty) {
		this.offduty = offduty;
	}

	public String getClockin() {
		return clockin;
	}

	public void setClockin(String clockin) {
		this.clockin = clockin;
	}

	public String getClockout() {
		return clockout;
	}

	public void setClockout(String clockout) {
		this.clockout = clockout;
	}

	public String getDelay() {
		return delay;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}

	public String getEarly() {
		return early;
	}

	public void setEarly(String early) {
		this.early = early;
	}

	public String getAbsent() {
		return absent;
	}

	public void setAbsent(String absent) {
		this.absent = absent;
	}

	public String getExpday() {
		return expday;
	}

	public void setExpday(String expday) {
		this.expday = expday;
	}

	public String getPic1() {
		return pic1;
	}

	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}

	public String getPic2() {
		return pic2;
	}

	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}

	public Integer getM1() {
		return m1;
	}

	public void setM1(Integer m1) {
		this.m1 = m1;
	}

	public Integer getM2() {
		return m2;
	}

	public void setM2(Integer m2) {
		this.m2 = m2;
	}

	

	public Integer getVacaTyp() {
		return vacaTyp;
	}

	public void setVacaTyp(Integer vacaTyp) {
		this.vacaTyp = vacaTyp;
	}

	public String getAbsFrom() {
		return absFrom;
	}

	public void setAbsFrom(String absFrom) {
		this.absFrom = absFrom;
	}

	public String getAbsTo() {
		return absTo;
	}

	public void setAbsTo(String absTo) {
		this.absTo = absTo;
	}

	public Date getVacaStart() {
		return vacaStart;
	}

	public void setVacaStart(Date vacaStart) {
		this.vacaStart = vacaStart;
	}

	public Date getVacaEnd() {
		return vacaEnd;
	}

	public void setVacaEnd(Date vacaEnd) {
		this.vacaEnd = vacaEnd;
	}

	public String getAbsType() {
		return absType;
	}

	public void setAbsType(String absType) {
		this.absType = absType;
	}

	public Integer getTypeDoc() {
		return typeDoc;
	}

	public void setTypeDoc(Integer typeDoc) {
		this.typeDoc = typeDoc;
	}

	public String getVacName() {
		return vacName;
	}

	public void setVacName(String vacName) {
		this.vacName = vacName;
	}

	public String getAbstName() {
		return abstName;
	}

	public void setAbstName(String abstName) {
		this.abstName = abstName;
	}

	public String getTimeFinger() {
		return timeFinger;
	}

	public void setTimeFinger(String timeFinger) {
		this.timeFinger = timeFinger;
	}

	public String getDateFinger() {
		return dateFinger;
	}

	public void setDateFinger(String dateFinger) {
		this.dateFinger = dateFinger;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public String getBintime() {
		return bintime;
	}

	public void setBintime(String bintime) {
		this.bintime = bintime;
	}

	public String getEintime() {
		return eintime;
	}

	public void setEintime(String eintime) {
		this.eintime = eintime;
	}

	public String getBouttime() {
		return bouttime;
	}

	public void setBouttime(String bouttime) {
		this.bouttime = bouttime;
	}

	public String getEouttime() {
		return eouttime;
	}

	public void setEouttime(String eouttime) {
		this.eouttime = eouttime;
	}

	public String getLatetime() {
		return latetime;
	}

	public void setLatetime(String latetime) {
		this.latetime = latetime;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

//	public TstFingerId getId() {
//		return id;
//	}
//
//	public void setId(TstFingerId id) {
//		this.id = id;
//	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Date getVdate() {
		return vdate;
	}

	public void setVdate(Date vdate) {
		this.vdate = vdate;
	}

	public String getFngDate() {
		return fngDate;
	}

	public void setFngDate(String fngDate) {
		this.fngDate = fngDate;
	}

	public boolean isHigriMode() {
		return higriMode;
	}

	public void setHigriMode(boolean higriMode) {
		this.higriMode = higriMode;
	}



	

}

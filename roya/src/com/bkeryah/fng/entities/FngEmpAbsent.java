package com.bkeryah.fng.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import utilities.Utils;

import java.util.Date;

import javax.persistence.Column;

@Entity
@Table(name = "HRS_EMP_ABSNT")
public class FngEmpAbsent {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "EMPNO")
	private Integer empno;
	@Column(name = "ABSDT")
	private String absComment;
	@Column(name = "ABSTYPE")
	private Integer abstype;
	@Column(name = "MINS")
	private Integer mins;
	@Column(name = "HOURS")
	private Integer hours;
	@Column(name = "DAYS")
	private Integer days;
	@Column(name = "ABSMONTH")
	private Integer absmonth;
	@Column(name = "ABSYEAR")
	private Integer absyear;
	@Column(name = "ABS")
	private Integer abs;
	@Column(name = "STS")
	private Integer statusAbs;
	@Column(name = "ABSNTSEQ")
	private Integer absntseq;

	@Transient
	private Date absCommentGrig;

	public Integer getEmpno() {
		return empno;
	}

	public void setEmpno(Integer empno) {
		this.empno = empno;
	}

	public Integer getAbstype() {
		return abstype;
	}

	public void setAbstype(Integer abstype) {
		this.abstype = abstype;
	}

	public Integer getMins() {
		return mins;
	}

	public void setMins(Integer mins) {
		this.mins = mins;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getAbsmonth() {
		return absmonth;
	}

	public void setAbsmonth(Integer absmonth) {
		this.absmonth = absmonth;
	}

	public Integer getAbsyear() {
		return absyear;
	}

	public void setAbsyear(Integer absyear) {
		this.absyear = absyear;
	}

	public Integer getAbs() {
		return abs;
	}

	public void setAbs(Integer abs) {
		this.abs = abs;
	}

	public Integer getAbsntseq() {
		return absntseq;
	}

	public void setAbsntseq(Integer absntseq) {
		this.absntseq = absntseq;
	}

	public String getAbsComment() {
		return absComment;
	}

	public void setAbsComment(String absComment) {
		this.absComment = absComment;
		;
	}

	public Integer getStatusAbs() {
		return statusAbs;
	}

	public void setStatusAbs(Integer statusAbs) {
		this.statusAbs = statusAbs;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getAbsCommentGrig() {
		return absCommentGrig;
	}

	public void setAbsCommentGrig(Date absCommentGrig) {
		this.absCommentGrig = absCommentGrig;
	}

}
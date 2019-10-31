package com.bkeryah.hr.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class HrsSalaryScaleId implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "ORDERID")
	private Integer orderid;
	@Column(name = "RANKCODE")
	private Integer rankcode;

	public HrsSalaryScaleId(Integer orderid, Integer rankcode) {
		super();
		this.orderid = orderid;
		this.rankcode = rankcode;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public Integer getRankcode() {
		return rankcode;
	}

	public void setRankcode(Integer rankcode) {
		this.rankcode = rankcode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

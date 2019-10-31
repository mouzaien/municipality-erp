package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class HrsSalaryScaleId implements Serializable {

	@Column(name = "orderId")
	private Integer orderId	;
	@Column(name = "rankCode")
	private Integer  rankCode	;
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getRankCode() {
		return rankCode;
	}
	public void setRankCode(Integer rankCode) {
		this.rankCode = rankCode;
	}
	public HrsSalaryScaleId(Integer orderId, Integer rankCode) {
		super();
		this.orderId = orderId;
		this.rankCode = rankCode;
	}
	public HrsSalaryScaleId() {
		// TODO Auto-generated constructor stub
	}


}

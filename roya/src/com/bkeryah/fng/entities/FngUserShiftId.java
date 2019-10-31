package com.bkeryah.fng.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.bkeryah.fng.beans.EmpPlanificationBean;
import com.bkeryah.managedBean.empSumBean;

@SuppressWarnings("serial")
@Embeddable
public class FngUserShiftId implements Serializable {

	@Column(name = "USERID")
	private Integer userid;
	@Column(name = "TIMEID")
	private Integer timeid;
	@Column(name = "WORKDATE")
	private String workdate;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getTimeid() {
		return timeid;
	}

	public void setTimeid(Integer timeid) {
		this.timeid = timeid;
	}

	public String getWorkdate() {
			return workdate;
	}

	public void setWorkdate(String workdate) {
		this.workdate = workdate;
	}

}

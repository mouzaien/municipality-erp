package com.bkeryah.fng.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TstFingerId implements Serializable {
	
	@Column(name = "USERID")
	private Integer userid;
	@Column(name = "VDATE")
	private Date vdate;
	
	
	
public TstFingerId(Integer userid, Date vdate) {
		

		super();
		this.userid = userid;
		this.vdate = vdate;
	}
	
	
	public TstFingerId() {
	
}


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
	

}

package com.bkeryah.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Column;

@Entity
@Table(name = "WRK_PROFILE_SIGN")
public class WrkProfileSign {
	@Id
	@Column(name = "USER_ID")
	private Integer userId;
	@Lob
	@Column(name = "DDD", nullable = true)
	private byte[] sign;
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "wrkProfileSign")
	private ArcUsers user;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public byte[] getSign() {
		return sign;
	}
	public void setSign(byte[] sign) {
		this.sign = sign;
	}
	public ArcUsers getUser() {
		return user;
	}
	public void setUser(ArcUsers user) {
		this.user = user;
	}

	
}
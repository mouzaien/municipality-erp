package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "LIC_MAIN_ACTIVITY")
public class LicMainActivity {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name="ACTV_ID")
	private Integer actvId;
	@Column(name="ACTV_NM")
	private String actvName;
	@Column(name="flag")
	private Integer flag;
	@Column(name="IS_ENABLE_Y_N")
	private char enable;
	public Integer getActvId() {
		return actvId;
	}
	public void setActvId(Integer actvId) {
		this.actvId = actvId;
	}
	public String getActvName() {
		return actvName;
	}
	public void setActvName(String actvName) {
		this.actvName = actvName;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public char getEnable() {
		return enable;
	}
	public void setEnable(char enable) {
		this.enable = enable;
	}

}

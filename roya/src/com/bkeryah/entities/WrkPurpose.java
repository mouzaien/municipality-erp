package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="WRK_PURPOSE")
public class WrkPurpose {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private int id;
	@Column(name = "P_NAME")
	private String purposeName;
	@Transient
	private String idStr;
	
	
	public String getIdStr() {
		return Integer.valueOf(id).toString();
	}
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}
	public void setId(int id) {
		this.id = id;
	}
	 public int getId() {
		return id;
	}
	 public void setPurposeName(String purposeName) {
		this.purposeName = purposeName;
	}
	 public String getPurposeName() {
		return purposeName;
	}
}

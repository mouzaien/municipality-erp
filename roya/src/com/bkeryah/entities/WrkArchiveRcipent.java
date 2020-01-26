package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "WRK_ARCHIVE_RECIPENTS")
public class WrkArchiveRcipent {

	@Id
	// @GenericGenerator(name = "generator", strategy = "increment")
	// @GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "TITLE")
	private String title;
	@Formula("(select u.EMPNAME from ARC_USERS u where u.user_id = ID )")
	private String managerName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

}

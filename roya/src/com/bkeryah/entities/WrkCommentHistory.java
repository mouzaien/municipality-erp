package com.bkeryah.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;

import java.util.Date;

import javax.persistence.Column;

@Entity
@Table(name = "lETTER_HISTORY")

public class WrkCommentHistory {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;

	@Column(name = "COMM_NUMBER")
	private Integer commNumber;

	@Column(name = "LONG_COMMENT")
	private String longComment;

	@Column(name = "USER_ID")
	private Integer userId;

	@Column(name = "EDIT_DATE")
	private Date editDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCommNumber() {
		return commNumber;
	}

	public void setCommNumber(Integer commNumber) {
		this.commNumber = commNumber;
	}

	public String getLongComment() {
		return longComment;
	}

	public void setLongComment(String longComment) {
		this.longComment = longComment;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

}

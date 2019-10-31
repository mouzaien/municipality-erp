package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="WRK_COMMENT_TYPE")
public class WrkCommentType {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private int id;
	@Column(name="NAME")
	private String commentTypeName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCommentTypeName() {
		return commentTypeName;
	}
	public void setCommentTypeName(String commentTypeName) {
		this.commentTypeName = commentTypeName;
	}
	
	
}

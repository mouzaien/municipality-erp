package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WHS_ARTICLE_STATUS")
public class ArticleStatus {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer articleStatusId;
	@Column(name = "name")
	private String articleStatusName;

	public ArticleStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArticleStatus(Integer articleStatusId, String articleStatusName) {
		super();
		this.articleStatusId = articleStatusId;
		this.articleStatusName = articleStatusName;
	}

	public Integer getArticleStatusId() {
		return articleStatusId;
	}

	public void setArticleStatusId(Integer articleStatusId) {
		this.articleStatusId = articleStatusId;
	}

	public String getArticleStatusName() {
		return articleStatusName;
	}

	public void setArticleStatusName(String articleStatusName) {
		this.articleStatusName = articleStatusName;
	}

}

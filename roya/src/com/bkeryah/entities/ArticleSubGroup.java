package com.bkeryah.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SUB_GROUP_ARTICLE")
public class ArticleSubGroup {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	private int id;
	@Column(name = "group_id")
	private int groupId;
	private String name;
	private String code;
	@JoinColumn(insertable=false,updatable=false,name="group_id")
	@ManyToOne
	private ArticleGroup articleGroup;
	@OneToMany(fetch=FetchType.LAZY,mappedBy="articleSubGroup")
	private List<Article> articles;
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public ArticleGroup getArticleGroup() {
		return articleGroup;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticleGroup(ArticleGroup articleGroup) {
		this.articleGroup = articleGroup;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

}

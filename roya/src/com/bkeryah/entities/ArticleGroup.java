package com.bkeryah.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "GROUP_ARTICLE")
public class ArticleGroup {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	private int id;
	private String name;
	private String code;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="articleGroup")
	private List<ArticleSubGroup> subGroups;
	
	@javax.persistence.Transient
	private String fullName; 
	
	
public String getFullName() {
		return name+"-"+code;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	//	@OneToMany(fetch=FetchType.LAZY)
//	private List<Article> articles;
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

	public List<ArticleSubGroup> getSubGroups() {
		return subGroups;
	}

	public void setSubGroups(List<ArticleSubGroup> subGroups) {
		this.subGroups = subGroups;
	}

//	public List<Article> getArticles() {
//		return articles;
//	}

//	public void setArticles(List<Article> articles) {
//		this.articles = articles;
//	}

}

package com.bkeryah.entities.investment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "INV_NEWS_PAPER")
public class InvNewspaper {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "PAPER_CODE")
	private Integer code;
	@Column(name = "PAPER_NAME")
	private String name;
	@Transient
	private Integer newsId;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return code + "-" + name;
	}

	@Override
	public boolean equals(Object obj) {
		return ((InvNewspaper) obj).getCode().equals(this.code);
	}

	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
}
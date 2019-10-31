package com.bkeryah.entities.investment;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "announcement_news")
public class AnncementNews {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "ANNOUCEMENT_ID")
	private Integer annncementId;
	@Column(name = "ID_NEWS_PAPER")
	private Integer newsId;
	@Formula("(select w.PAPER_NAME from INV_NEWS_PAPER w where  w.PAPER_CODE = ID_NEWS_PAPER)")
	private String newsName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ANNOUCEMENT_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private Announcement announcement;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAnnncementId() {
		return annncementId;
	}

	public void setAnnncementId(Integer annncementId) {
		this.annncementId = annncementId;
	}

	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public String getNewsName() {
		return newsName;
	}

	public void setNewsName(String newsName) {
		this.newsName = newsName;
	}

	public Announcement getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
	}

}
package com.bkeryah.entities.investment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ANNOUCEMENT")
public class Announcement {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "CREATE_DATE")
	private Date createDate;
	@Column(name = "h_create_date")
	private String hCreateDate;
	@Column(name = "h_last_tender_date")
	private String hLastTenderDate;
	@Column(name = "OPEN_ENV_DATE")
	private String openEnvDate;
	@Column(name = "TIME_FLAG")
	private Integer timeFlag;
	@Column(name = "LAST_TENDER_TIME")
	private String lastTenderTime;	
	@OneToMany(fetch= FetchType.LAZY, mappedBy="announcement" , cascade=CascadeType.REMOVE)
	private List<AnnoucementDetails> annDetailsList;
	@Transient
	private String newsPaper;
	@Transient
	private List<InvNewspaper> newspapersList = new ArrayList<InvNewspaper>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "announcement", cascade = CascadeType.REMOVE)
	private List<AnncementNews> anncementNews;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOpenEnvDate() {
		return openEnvDate;
	}

	public void setOpenEnvDate(String openEnvDate) {
		this.openEnvDate = openEnvDate;
	}

	public Integer getTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(Integer timeFlag) {
		this.timeFlag = timeFlag;
	}

	public String gethCreateDate() {
		return hCreateDate;
	}

	public void sethCreateDate(String hCreateDate) {
		this.hCreateDate = hCreateDate;
	}

	public String gethLastTenderDate() {
		return hLastTenderDate;
	}

	public void sethLastTenderDate(String hLastTenderDate) {
		this.hLastTenderDate = hLastTenderDate;
	}

	public String getLastTenderTime() {
		return lastTenderTime;
	}

	public void setLastTenderTime(String lastTenderTime) {
		this.lastTenderTime = lastTenderTime;
	}

	public List<AnnoucementDetails> getAnnDetailsList() {
		return annDetailsList;
	}

	public void setAnnDetailsList(List<AnnoucementDetails> annDetailsList) {
		this.annDetailsList = annDetailsList;
	}

	public List<AnncementNews> getAnncementNews() {
		return anncementNews;
	}

	public void setAnncementNews(List<AnncementNews> anncementNews) {
		this.anncementNews = anncementNews;
	}

	public List<InvNewspaper> getNewspapersList() {
		if(anncementNews != null){
			for (AnncementNews anncementNews : anncementNews) {
				InvNewspaper newsPaper = new InvNewspaper();
				newsPaper.setCode(anncementNews.getNewsId());
				newsPaper.setName(newsPaper.getName());
				newsPaper.setNewsId(anncementNews.getId());
				newspapersList.add(newsPaper);
			}
		}
		return newspapersList;
	}

	public void setNewspapersList(List<InvNewspaper> newspapersList) {
		if (anncementNews != null)
			anncementNews.clear();
		for (InvNewspaper paperNews : newspapersList) {
			AnncementNews newsPaper = new AnncementNews();
			newsPaper.setNewsId(paperNews.getCode());
			newsPaper.setAnnncementId(this.id);
			newsPaper.setId(paperNews.getNewsId());
			anncementNews.add(newsPaper);
		}
		this.newspapersList = newspapersList;
	}

	public String getNewsPaper() {
		return newsPaper;
	}

	public void setNewsPaper(String newsPaper) {
		this.newsPaper = newsPaper;
	}
}
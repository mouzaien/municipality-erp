package com.bkeryah.stock.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.Article;
import com.bkeryah.entities.WhsWarehouses;
import com.bkeryah.service.IDataAccessService;

import utilities.Utils;

@ManagedBean(name = "el3ohadBean")
@ViewScoped
public class El3ohadBean {
	private static Logger logger = Logger.getLogger(El3ohadBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private ArcUsers currentUser;
	private Integer employerId;
	private List<Article> articleList = new ArrayList<Article>();
	private List<Article> articleFilterdList ;
	private Integer artId;

	@PostConstruct
	public void init() {
		currentUser = Utils.findCurrentUser();
		employerId = currentUser.getUserId();
		articleList = dataAccessService.find3ohadByUserId(employerId);

	}

	public String getStoreName(Integer str) {
		String srtName = "";
		WhsWarehouses warehouses = (WhsWarehouses) dataAccessService.findEntityById(WhsWarehouses.class, str);
		if (warehouses != null)
			return warehouses.getStoreName();
		return srtName;
	}

	// method for
	// print Protection card from search_exchange_request بطاقة عهدة
	public String printProtectionCardAllArt() {
		String reportName = "/reports/protection_A3ohad.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		// if (articleFilterdList != null && articleFilterdList.size() > 0) {
		// Utils.printPdfReportFromListDataSource(reportName, parameters,
		// articleFilterdList);
		// return "";
		// }

		parameters.put("artId", -1);
		parameters.put("userId", employerId);
		Utils.printPdfReport(reportName, parameters);
		return "";

	}

	public String printProtectionCardOneArt() {
		String reportName = "/reports/protection_A3ohad.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("artId", artId);
		parameters.put("userId", employerId);
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public ArcUsers getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(ArcUsers currentUser) {
		this.currentUser = currentUser;
	}

	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

	public List<Article> getArticleFilterdList() {
		return articleFilterdList;
	}

	public void setArticleFilterdList(List<Article> articleFilterdList) {
		this.articleFilterdList = articleFilterdList;
	}

	public Integer getArtId() {
		return artId;
	}

	public void setArtId(Integer artId) {
		this.artId = artId;
	}

}

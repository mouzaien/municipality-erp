package com.bkeryah.stock.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.*;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.Article;
import com.bkeryah.entities.WhsWarehouses;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.Utils;

@ManagedBean(name = "el3ohadBean")
@ViewScoped
public class El3ohadBean {
	private static Logger logger = LogManager.getLogger(El3ohadBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private ArcUsers currentUser;
	private Integer employerId;
	private List<Article> articleList = new ArrayList<Article>();
	private List<Article> articleFilterdList;
	private Integer artId;
	private Article art;
	private Integer forDept;
	private boolean showForDept = false;
	private List<User> employers;

	@PostConstruct
	public void init() {
		currentUser = Utils.findCurrentUser();
		employerId = currentUser.getUserId();
		articleList = dataAccessService.find3ohadByUserId(employerId, -1);
		employers = new ArrayList<>();
		employers = dataAccessService.getAllEmployeesByManager(employerId);
		forDept = 1;
		if (employers != null && employers.size() > 0) {
			// for dept 3ohad
			showForDept = true;
			articleList = articleList.stream().filter(i -> i.getForDept() != 2).collect(Collectors.toList());

		}
		System.out.println(4 + 5 + 'g');
	}

	public String getStoreName(Integer str) {
		String srtName = "";
		WhsWarehouses warehouses = (WhsWarehouses) dataAccessService.findEntityById(WhsWarehouses.class, str);
		if (warehouses != null)
			return warehouses.getStoreName();
		return srtName;
	}

	public String filter() {
		if (forDept == 1) {
			articleList = dataAccessService.find3ohadByUserId(employerId, -1);
			articleList = articleList.stream().filter(i -> i.getForDept() != 2).collect(Collectors.toList());
		} else {
			articleList = dataAccessService.find3ohadByUserId(-1, currentUser.getDeptId());
		}
		return "";
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
		parameters.put("masterId", -1);
		parameters.put("STRNO", -1);
		parameters.put("artId", -1);
		parameters.put("forDept", forDept);
		if (forDept == 1) {
			parameters.put("mngDeptId", -1);
			parameters.put("userId", employerId);
		} else {
			parameters.put("mngDeptId", currentUser.getDeptId());
			parameters.put("userId", 0);
		}

		Utils.printPdfReport(reportName, parameters);
		return "";

	}

	public String printProtectionCardOneArt() {
		String reportName = "/reports/protection_A3ohad.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("artId", art.getId());
		parameters.put("userId", employerId);
		parameters.put("STRNO", -1);
		parameters.put("masterId", art.getExchMasterId());
		parameters.put("forDept", forDept);
		if (forDept == 1) {
			parameters.put("mngDeptId", -1);
			parameters.put("userId", employerId);
		} else {
			parameters.put("mngDeptId", currentUser.getDeptId());
			parameters.put("userId", 0);
		}
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

	public Article getArt() {
		return art;
	}

	public void setArt(Article art) {
		this.art = art;
	}

	public Integer getForDept() {
		return forDept;
	}

	public void setForDept(Integer forDept) {
		this.forDept = forDept;
	}

	public boolean isShowForDept() {
		return showForDept;
	}

	public void setShowForDept(boolean showForDept) {
		this.showForDept = showForDept;
	}

	public List<User> getEmployers() {
		return employers;
	}

	public void setEmployers(List<User> employers) {
		this.employers = employers;
	}

}

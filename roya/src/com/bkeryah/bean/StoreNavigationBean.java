package com.bkeryah.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.bkeryah.entities.ArticleGroup;
import com.bkeryah.entities.ArticleSubGroup;
import com.bkeryah.service.IDataAccessService;

@ManagedBean
@SessionScoped
public class StoreNavigationBean {

	private String pageName = "storeDashboard.xhtml";
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private boolean addArticleFlag;
	private boolean addSubArticleGroupFlag;
	private boolean addGroupArticleFlag;
	private List<ArticleGroup> articleGroups;
	private List<ArticleSubGroup> articleSubGroups;

	public StoreNavigationBean() {
	}

	public  String navigate(int pageIndex) {

		switch (pageIndex) {

		case 1:
			setPageName("articlesPage.xhtml");
			addArticleFlag = true;
			addSubArticleGroupFlag = false;
			addGroupArticleFlag = false;
			break;

		case 2:
			setPageName("articleSubGroup.xhtml");
			addArticleFlag = false;
			addSubArticleGroupFlag = true;
			addGroupArticleFlag = false;
			break;
		case 3:
			setPageName("articleGroup.xhtml");
			addArticleFlag = false;
			addSubArticleGroupFlag = false;
			addGroupArticleFlag = true;
			break;
		default:
			setPageName("storeDashboard.xhtml");
			addArticleFlag = false;
			addSubArticleGroupFlag = false;
			addGroupArticleFlag = false;
			break;

		}
		return "store";
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<ArticleGroup> getArticleGroups() {
		return articleGroups;
	}

	public void setArticleGroups(List<ArticleGroup> articleGroups) {
		this.articleGroups = articleGroups;
	}

	public List<ArticleSubGroup> getArticleSubGroups() {
		return articleSubGroups;
	}

	public void setArticleSubGroups(List<ArticleSubGroup> articleSubGroups) {
		this.articleSubGroups = articleSubGroups;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public boolean isAddArticleFlag() {
		return addArticleFlag;
	}

	public boolean isAddSubArticleGroupFlag() {
		return addSubArticleGroupFlag;
	}

	public boolean isAddGroupArticleFlag() {
		return addGroupArticleFlag;
	}

	public void setAddArticleFlag(boolean addArticleFlag) {
		this.addArticleFlag = addArticleFlag;
	}

	public void setAddSubArticleGroupFlag(boolean addSubArticleGroupFlag) {
		this.addSubArticleGroupFlag = addSubArticleGroupFlag;
	}

	public void setAddGroupArticleFlag(boolean addGroupArticleFlag) {
		this.addGroupArticleFlag = addGroupArticleFlag;
	}

}

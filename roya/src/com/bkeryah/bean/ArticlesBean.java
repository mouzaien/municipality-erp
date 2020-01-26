package com.bkeryah.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.Article;
import com.bkeryah.entities.ArticleGroup;
import com.bkeryah.entities.ArticleSubGroup;
import com.bkeryah.entities.ItemUnite;
import com.bkeryah.entities.WhsWarehouses;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class ArticlesBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private Article article = new Article();
	private List<ArticleGroup> articleGroups;
	private List<ArticleSubGroup> articleSubGroups;
	private List<ArticleSubGroup> articleSubGroupsUpdated = new ArrayList<>();
	private List<WhsWarehouses> warehouses = new ArrayList<>();
	private List<Article> articles;
	private List<Article> filterArticles;
	private boolean newFlag;
	private List<ItemUnite> unites;
	private Integer groupId;
	private Integer itemId;
	private String item;
	private String artType = "1";
	private Article selectedItem = new Article();

	@PostConstruct
	public void init() {
		articleGroups = dataAccessService.getAllArticleGroups();
		//articleSubGroups = dataAccessService.getAllArticleSubGroups();
		articles = dataAccessService.getAllArticles(article.getStrNo());
		unites = dataAccessService.getAllUnites();
		warehouses = dataAccessService.getAllStores();
	}

	public void updateSubGroups(){
		articleSubGroups = new ArrayList<>();
		if(groupId != null )
		{
		articleSubGroups = dataAccessService.getAllArticleSubGroupsByGroupId(groupId);
		Utils.updateUIComponent("includeform:sub");
		}
	}
	
	public void newArticle() {
		newFlag = false;
		article = new Article();
	}

	public void updateTableSubGroups(Integer tabelGroupId) {
		articleSubGroups = dataAccessService.getAllArticleSubGroupsByGroupId(tabelGroupId);
	}

	public void addArticle(AjaxBehaviorEvent ev) {
		try {
			article.setGroupId(groupId);
			dataAccessService.addNewArticle(article);
			article = new Article();
			articles = dataAccessService.getAllArticles(article.getStrNo());

			MsgEntry.addInfoMessage("تم اضافة الصنف بنجاح");

		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage("  (رمز مكرر) خطا فى  اضافة الصنف  ");
		}
	}

	public void reloadStrArticles(AjaxBehaviorEvent ev) {
		try {
			if(article != null)
			 articles = dataAccessService.getAllArticles(article.getStrNo());

		} catch (Exception e) {
			MsgEntry.addErrorMessage("  خطإ  ");
		}
	}

	/////////////////// edit by Mohamed balabel/////////////////

	/////////////////// row Edittor///////////////

	public void onRowEdit(RowEditEvent event) {
		selectedItem = (Article) event.getObject();
		dataAccessService.updateObject(selectedItem);
		FacesMessage msg = new FacesMessage("تم حفظ التعديل", selectedItem.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
		articles = dataAccessService.getAllArticles(article.getStrNo());

	}

	public void onRowCancel(RowEditEvent event) {
		selectedItem = (Article) event.getObject();
		FacesMessage msg = new FacesMessage("تم إلغاء التعديل", selectedItem.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	////////////////////////////// Delete ///////////////////////

	public Article deleteArticleById(Integer id) {
		Article article = new Article();
		article = dataAccessService.getArticleById(id);
		System.out.println(article.getName());
		try {
			if (article != null) {
				dataAccessService.deleteObject(article);
				MsgEntry.addInfoMessage("تم حذف " + article.getName() + " بنجاح");
			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage("هذا الصنف لا يمكن حذفه لانه مستخدم في شاشة اخرى");

		}
		articles = dataAccessService.getAllArticles(article.getStrNo());
		return null;
	}

	//////////////////////////// end of edit //////////////////////
	public List<ArticleGroup> getArticleGroups() {
		// articleGroups= dataAccessService.getAllArticleGroups();
		return articleGroups;
	}

	public void setArticleGroups(List<ArticleGroup> articleGroups) {
		this.articleGroups = articleGroups;
	}

	public boolean isNewFlag() {
		return newFlag;
	}

	public void setNewFlag(boolean newFlag) {
		this.newFlag = newFlag;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public Article getArticle() {
		return article;
	}

	public List<ArticleSubGroup> getArticleSubGroups() {
		return articleSubGroups;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public void setArticleSubGroups(List<ArticleSubGroup> articleSubGroups) {
		this.articleSubGroups = articleSubGroups;
	}

	public List<ArticleSubGroup> getArticleSubGroupsUpdated() {
		return articleSubGroupsUpdated;
	}

	public void setArticleSubGroupsUpdated(List<ArticleSubGroup> articleSubGroupsUpdated) {
		this.articleSubGroupsUpdated = articleSubGroupsUpdated;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public List<ItemUnite> getUnites() {
		return unites;
	}

	public void setUnites(List<ItemUnite> unites) {
		this.unites = unites;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public List<WhsWarehouses> getWarehouses() {
		return warehouses;
	}

	public void setWarehouses(List<WhsWarehouses> warehouses) {
		this.warehouses = warehouses;
	}

	public String getArtType() {
		return artType;
	}

	public void setArtType(String artType) {
		this.artType = artType;
	}

	public List<Article> getFilterArticles() {
		return filterArticles;
	}

	public void setFilterArticles(List<Article> filterArticles) {
		this.filterArticles = filterArticles;
	}

	public Article getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Article selectedItem) {
		this.selectedItem = selectedItem;
	}
}

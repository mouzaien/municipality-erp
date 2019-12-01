package com.bkeryah.bean;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.Article;
import com.bkeryah.entities.ArticleGroup;
import com.bkeryah.entities.ArticleSubGroup;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;

@ManagedBean
@ViewScoped
public class ArticleGroupBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private ArticleGroup articleGroup = new ArticleGroup();
	private List<ArticleGroup> articleGroups;
	private List<ArticleGroup> filterArticleGroups;
	private boolean newFlag;

	@PostConstruct
	public void init() {
		articleGroups = dataAccessService.getAllArticleGroups();
	}

	public void newArticleGroup() {

		newFlag = false;
		articleGroup = new ArticleGroup();
	}

	public void addArticleGroup() {
		try {

			dataAccessService.addNewArticleGroup(articleGroup);
			articleGroup = new ArticleGroup();
			articleGroups = dataAccessService.getAllArticleGroups();
			MsgEntry.addInfoMessage("تم اضافة المجموعة بنجاح");

		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage("  رمز مكرر) خطا فى  اضافة المجموعة) ");
		}
	}

	public void updateArticleGroup() {
		try {

			dataAccessService.addNewArticleGroup(articleGroup);
			articleGroup = new ArticleGroup();
			articleGroups = dataAccessService.getAllArticleGroups();
			newFlag = false;
			MsgEntry.addInfoMessage("تم تعديل المجموعة بنجاح");

		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage("  رمز مكرر) خطا فى  اضافة المجموعة) ");
		}
	}

	/////////////////// edit by Mohamed balabel/////////////////

	/////////////////// row Edittor///////////////

	public void onRowEdit(RowEditEvent event) {
		ArticleGroup selectedItem = (ArticleGroup) event.getObject();
		FacesMessage msg = new FacesMessage("تم حفظ التعديل", selectedItem.getName());
		dataAccessService.updateObject(selectedItem);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("تم إلغاء التعديل", ((ArticleGroup) event.getObject()).getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	 

	//////////////////////////// end of edit //////////////////////

	public void showRecord(ArticleGroup g) {
		newFlag = true;
		articleGroup = g;
	}

	public ArticleGroup getArticleGroup() {
		return articleGroup;
	}

	public List<ArticleGroup> getArticleGroups() {
		return articleGroups;
	}

	public void setArticleGroup(ArticleGroup articleGroup) {
		this.articleGroup = articleGroup;
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

	public List<ArticleGroup> getFilterArticleGroups() {
		return filterArticleGroups;
	}

	public void setFilterArticleGroups(List<ArticleGroup> filterArticleGroups) {
		this.filterArticleGroups = filterArticleGroups;
	}

}

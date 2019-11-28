package com.bkeryah.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.ArticleGroup;
import com.bkeryah.entities.ArticleSubGroup;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class ArticleSubGroupBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private ArticleSubGroup articleSubGroup = new ArticleSubGroup();
	private List<ArticleGroup> articleGroups;
	private List<ArticleSubGroup> articleSubGroups;
	private List<ArticleSubGroup> filterArticleSubGroups;
	private boolean newFlag;

	@PostConstruct
	public void init() {
		// articleGroups = dataAccessService.getAllArticleGroups();
		articleSubGroups = dataAccessService.getAllArticleSubGroups();
	}

	public void newArticleGroup() {

		newFlag = false;
		articleSubGroup = new ArticleSubGroup();
	}

	public void addArticleGroup() {
		try {

			dataAccessService.addNewArticleSubGroup(articleSubGroup);
			articleSubGroup = new ArticleSubGroup();
			articleSubGroups = dataAccessService.getAllArticleSubGroups();
			articleGroups = dataAccessService.getAllArticleGroups();
			MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));

		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation")+" "+Utils.loadMessagesFromFile("error.repeated.code"));
		}
	}

	public void updateArticleGroup() {
		try {

			dataAccessService.addNewArticleSubGroup(articleSubGroup);
			articleSubGroup = new ArticleSubGroup();
			articleSubGroups = dataAccessService.getAllArticleSubGroups();
			// articleGroups = dataAccessService.getAllArticleGroups();
			newFlag = false;
			MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));

		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation")+" "+Utils.loadMessagesFromFile("error.repeated.code"));
		}
	}

	/////////////////// edit by Mohamed balabel/////////////////

	/////////////////// row Edittor///////////////

	public void onRowEdit(RowEditEvent event) {
		ArticleSubGroup selectedItem = (ArticleSubGroup) event.getObject();
		dataAccessService.updateObject(selectedItem);
		MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation")+": "+selectedItem.getName());
//		FacesMessage msg = new FacesMessage("تم حفظ التعديل", selectedItem.getName());
//		FacesContext.getCurrentInstance().addMessage(null, msg);
		articleSubGroups = dataAccessService.getAllArticleSubGroups();
	}

	public void onRowCancel(RowEditEvent event) {
		/*FacesMessage msg = new FacesMessage("تم إلغاء التعديل", ((ArticleSubGroup) event.getObject()).getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);*/
	}

	//////////////////////////// end of edit //////////////////////

	public void showRecord(ArticleSubGroup g) {
		newFlag = true;
		articleSubGroup = g;
	}

	public List<ArticleGroup> getArticleGroups() {
		articleGroups = dataAccessService.getAllArticleGroups();
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

	public ArticleSubGroup getArticleSubGroup() {
		return articleSubGroup;
	}

	public List<ArticleSubGroup> getArticleSubGroups() {
		return articleSubGroups;
	}

	public void setArticleSubGroup(ArticleSubGroup articleSubGroup) {
		this.articleSubGroup = articleSubGroup;
	}

	public void setArticleSubGroups(List<ArticleSubGroup> articleSubGroups) {
		this.articleSubGroups = articleSubGroups;
	}

	public List<ArticleSubGroup> getFilterArticleSubGroups() {
		return filterArticleSubGroups;
	}

	public void setFilterArticleSubGroups(List<ArticleSubGroup> filterArticleSubGroups) {
		this.filterArticleSubGroups = filterArticleSubGroups;
	}

}

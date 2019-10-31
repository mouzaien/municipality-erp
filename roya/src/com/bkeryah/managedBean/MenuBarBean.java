package com.bkeryah.managedBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;

import com.bkeryah.service.IDataAccessService;

import utilities.Utils;

@ManagedBean
@ViewScoped
public class MenuBarBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;

	private Long unReadCount;
	private String unreadMailsString;

	@PostConstruct
	public void init() {
		/*unReadCount = dataAccessService.getUnReadMails(Utils.findCurrentUser().getUserId());
		unreadMailsString = Utils.convertTextWithArNumric(unReadCount + "");*/
	}

	public void loadNotifications(AjaxBehaviorEvent event) {
/*
		unReadCount = dataAccessService.getUnReadMails(Utils.findCurrentUser().getUserId());
		unreadMailsString = Utils.convertTextWithArNumric(unReadCount + "");*/

		RequestContext rc = RequestContext.getCurrentInstance();
		rc.execute("PF('dlg2').show();");
		
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public Long getUnReadCount() {
		return unReadCount;
	}

	public void setUnReadCount(Long unReadCount) {
		this.unReadCount = unReadCount;
	}

	public String getUnreadMailsString() {
		return unreadMailsString;
	}

	public void setUnreadMailsString(String unreadMailsString) {
		this.unreadMailsString = unreadMailsString;
	}

}
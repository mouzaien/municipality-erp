package com.bkeryah.managedBean.setting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.entities.WrkComment;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class WrCommentBean {
	private static Logger logger = Logger.getLogger(WrCommentBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<WrkComment> commentList = new ArrayList<WrkComment>();
	private WrkComment comment = new WrkComment();
	private List<WrkComment> filteredcommentList;
	private Integer appId;
	private String appHdate;
	private boolean higriMode;

	@PostConstruct
	public void init() {
		commentList = dataAccessService.findAllComment();

	}

//	public void update() {
//		this.appHdate = comment.getAppHdate();
//		System.out.println("appHdate" + appHdate);
//		Utils.openDialog("job_dlg");
//
//	}

	// Edit تعديل
	public void updateComment() {
		try {
			if (comment != null) {
				comment.setAppHdate(appHdate);
				dataAccessService.updateObject(comment);
				comment = new WrkComment();
//				logger.info("Update User: id: " + comment.getAppId());
			}
			MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ العملية");
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<WrkComment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<WrkComment> commentList) {
		this.commentList = commentList;
	}

	public WrkComment getComment() {
		return comment;
	}

	public void setComment(WrkComment comment) {
		this.comment = comment;
	}

	public List<WrkComment> getFilteredcommentList() {
		return filteredcommentList;
	}

	public void setFilteredcommentList(List<WrkComment> filteredcommentList) {
		this.filteredcommentList = filteredcommentList;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		WrCommentBean.logger = logger;
	}

	public String getAppHdate() {
		return appHdate;
	}

	public void setAppHdate(String appHdate) {
		this.appHdate = appHdate;
	}

	public boolean isHigriMode() {
		return higriMode;
	}

	public void setHigriMode(boolean higriMode) {
		this.higriMode = higriMode;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

}

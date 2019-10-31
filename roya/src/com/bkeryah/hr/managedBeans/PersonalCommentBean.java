package com.bkeryah.hr.managedBeans;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.bkeryah.entities.ArcAttach;
import com.bkeryah.entities.ArcRecords;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.entities.WrkComment;
import com.bkeryah.entities.WrkCommentType;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.model.AttachmentModel;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class PersonalCommentBean {
	private Boolean sendManagerFlag;
	
	private WrkComment wrkComment = new WrkComment();
	private WrkApplication wrkApplication = new WrkApplication();
	private List<WrkCommentType> commentTypes;
	private int purposeId;
	private String commentType;
	private UploadedFile file;
	private ArcRecords arcRecord = new ArcRecords();
	private ArcAttach attachment = new ArcAttach();
	private List<WrkPurpose> wrkPurposes;
	private InputStream stream;
	private String applicationPurpose;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<UploadedFile> files = new ArrayList<UploadedFile>();
	private List<AttachmentModel> attachList = new ArrayList<>();
	private List<ArcAttach> attachs = new ArrayList<ArcAttach>();

	@PostConstruct
	public void init() {
		setWrkPurposes(dataAccessService.getAllPurposes());
		setCommentTypes(dataAccessService.getCommentTypes());
	}

	public String addComment() {

		wrkApplication.setApplicationPurpose(
				(applicationPurpose != null) ? Integer.parseInt(applicationPurpose.trim()) : null);
		wrkComment.setCommentType((commentType != null) ? Integer.parseInt(commentType.trim()) : null);
		for (AttachmentModel att : attachList) {
			ArcAttach attach = new ArcAttach();
			String name = Utils.generateRandomName() + "." + att.getAttachExt();
			attach.setAttachName(name);
			try {
				Utils.saveAttachments(att.getAttachStream(), name);
			} catch (Exception e) {
				e.printStackTrace();
			}
			attach.setAttachOwner(Utils.findCurrentUser().getUserId());
			attach.setAttachDate(new Date());
			attach.setAttachSize((double) att.getAttachSize());
			attach.setAttachType(1);
			attach.setAttachCategory("FILE");
			attachs.add(attach);
		}

		List<Integer> attachIds = dataAccessService.addAttachments(attachs);
		dataAccessService.addComment(wrkComment, wrkApplication, attachIds);

		wrkComment = new WrkComment();
		wrkApplication = new WrkApplication();
		applicationPurpose = "";
		commentType = "";
		MsgEntry.addAcceptFlashInfoMessage(" تم اضافة الخطاب   بنجاح");
		return "mails";
	}

	public void uploadFile(FileUploadEvent event) {

		try {
			AttachmentModel attach = new AttachmentModel();
			attach.setAttachRealName(event.getFile().getFileName());
			attach.setAttachSize(event.getFile().getSize());
			attach.setAttachStream(event.getFile().getInputstream());
			attach.setAttachExt(FilenameUtils.getExtension(event.getFile().getFileName()));
			attachList.add(attach);

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("lpcDlgMessage",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, Utils.loadMessagesFromFile("no.upload"), ""));
		}

	}
	
	

	public void loadDate(Integer wrkApplicationId) {
		wrkComment = dataAccessService.getWrkCommentByAppId(wrkApplicationId);
	}

	public List<AttachmentModel> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<AttachmentModel> attachList) {
		this.attachList = attachList;
	}

	public void setWrkComment(WrkComment wrkComment) {
		this.wrkComment = wrkComment;
	}

	public WrkComment getWrkComment() {
		return wrkComment;
	}

	public int getPurposeId() {
		return purposeId;
	}

	public void setPurposeId(int purposeId) {
		this.purposeId = purposeId;
	}

	public List<WrkPurpose> getWrkPurposes() {
		return wrkPurposes;
	}

	public void setWrkPurposes(List<WrkPurpose> wrkPurposes) {
		this.wrkPurposes = wrkPurposes;
	}

	public List<WrkCommentType> getCommentTypes() {
		return commentTypes;
	}

	public void setCommentTypes(List<WrkCommentType> commentTypes) {
		this.commentTypes = commentTypes;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public UploadedFile getFile() {
		return file;
	}

	public WrkApplication getWrkApplication() {
		return wrkApplication;
	}

	public void setWrkApplication(WrkApplication wrkApplication) {
		this.wrkApplication = wrkApplication;
	}

	public String getApplicationPurpose() {
		return applicationPurpose;
	}

	public Boolean getSendManagerFlag() {
		return sendManagerFlag;
	}

	public void setSendManagerFlag(Boolean sendManagerFlag) {
		this.sendManagerFlag = sendManagerFlag;
	}

	public void setApplicationPurpose(String applicationPurpose) {
		this.applicationPurpose = applicationPurpose;
	}

	public String getCommentType() {
		return commentType;
	}

	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

}

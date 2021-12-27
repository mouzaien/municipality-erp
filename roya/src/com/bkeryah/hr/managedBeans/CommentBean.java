package com.bkeryah.hr.managedBeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.*;

import com.bkeryah.entities.ArcAttach;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.entities.WrkComment;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.model.AttachmentModel;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.shared.beans.Scanner;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class CommentBean extends Scanner {

	protected static final Logger logger = LogManager.getLogger(CommentBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private WrkComment wrkComment = new WrkComment();
	private WrkApplication wrkApplication = new WrkApplication();
	private String commentType;
	private List<WrkPurpose> wrkPurposes;
	private String applicationPurpose;	
	private List<ArcAttach> attachs = new ArrayList<ArcAttach>();
	private boolean managerFlag;
	private String fontSize = "16";
	private Integer attachNB = 0;
	private String sessionKey;
	private String param;
	private boolean personalCommnetFlag;
	private String header;
	private float fSize = 30.75f;
	private String date1;

	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		param = (String) session.getAttribute("p");
		// param = request.getParameter("p");
		if (param == null) {
			setHeader(Utils.loadMessagesFromFile("comment.create"));
			personalCommnetFlag = false;
		} else if (param.equals("personalComment")) {
			setHeader(Utils.loadMessagesFromFile("personal.comment.create"));
			personalCommnetFlag = true;
		}
		session.removeAttribute("p");
		setWrkPurposes(dataAccessService.getAllPurposes());
		if (Utils.findCurrentUser().getWrkRoleId() == 2 || Utils.findCurrentUser().getWrkRoleId() == 1)
			setManagerFlag(true);
	}

	/**
	 * Send comment
	 * @return
	 * @throws IOException
	 */
	public String addComment() throws IOException {
		List<Integer> attachIds = setCommentValues();
		if (!personalCommnetFlag) {
			dataAccessService.addComment(wrkComment, wrkApplication, attachIds);
		} else {
			dataAccessService.addPersonalComment(wrkComment, wrkApplication, attachIds);
		}
		resetData();
		MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("send.comment.success"));
		// FtpTransferFile.deleteFile(fileName);
		return "mails";
	}
	
	/**
	 * set values of comment
	 * @return
	 */
	private List<Integer> setCommentValues(){
		wrkApplication.setApplicationPurpose(
				(applicationPurpose != null) ? Integer.parseInt(applicationPurpose.trim()) : null);
		wrkComment.setCommentType((commentType != null) ? Integer.parseInt(commentType.trim()) : null);
		wrkComment.setFontSize(Integer.parseInt(fontSize));
		wrkComment.setAppPapers(attachNB);
		uploadFilesToTmpDirectory();
		attachs = Utils.SaveAttachementsToFtpServer(attachList);
		for (ArcAttach xx : attachs) {
			xx.setAttachOwner(Utils.findCurrentUser().getUserId());
		}
		return dataAccessService.addAttachments(attachs);
	}
	 
//	public void onDateSelect(SelectEvent event) {
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//		facesContext.addMessage(null,
//				new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
//	}

	/**
	 * Save comment
	 * @return
	 * @throws IOException
	 */
	public String saveComment() throws IOException {
		List<Integer> attachIds = setCommentValues();
		if (!personalCommnetFlag) {
			wrkApplication.setToUserId(Utils.findCurrentUser().getUserId());
			dataAccessService.addComment(wrkComment, wrkApplication, attachIds);
		} else {
			dataAccessService.addPersonalComment(wrkComment, wrkApplication, attachIds);
		}
		resetData();
		MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("save.comment.success"));
		// FtpTransferFile.deleteFile(fileName);
		return "mails";
	}
	
	/**
	 * reset comment data
	 */
	private void resetData(){
		wrkComment = new WrkComment();
		wrkApplication = new WrkApplication();
		applicationPurpose = "";
		commentType = "";
	}

	/**
	 * Show report before save
	 * @return
	 */
	public String viewReportAction() {
		String reportName = "/reports/letter_view_blank.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p1", "");
		parameters.put("textSize", Integer.parseInt(fontSize));
		parameters.put("APP_NO", "");
		// parameters.put("SEC_NAME", "");
		parameters.put("APP_SUBJECT", wrkComment.getAppSubject());
		parameters.put("APP_DATE", HijriCalendarUtil.findCurrentHijriDate());
		parameters.put("APP_PAPERS", attachNB);
		// parameters.put("TNAME", "");
		parameters.put("FIRST_COPY", wrkComment.getFirstCopy());
		parameters.put("LONG_COMMENT", wrkComment.getLongCommentArabStr());
		parameters.put("APP_TO", wrkComment.getAppTo());
		parameters.put("SECOND_COPY", wrkComment.getSecondCopy());
		// parameters.put("WNAME", "");
		Utils.printPdfReport(reportName, parameters);
		return "";
	}
	
//	public void showComment() {
//		RequestContext context = RequestContext.getCurrentInstance();
//		context.execute("PF('NewCommentView').show()");
//	}

//	public void uploadFile(FileUploadEvent event) {
//
//		try {
//			AttachmentModel attach = new AttachmentModel();
//			attach.setAttachRealName(event.getFile().getFileName());
//			attach.setRealName(event.getFile().getFileName());
//			attach.setAttachSize(event.getFile().getSize());
//
//			attach.setAttachStream(event.getFile().getInputstream());
//			attach.setAttachExt(FilenameUtils.getExtension(event.getFile().getFileName()));
//			attach.setRealName(Utils.generateRandomName() + "." + attach.getAttachExt());
//			attachList.add(attach);
//
//		} catch (Exception e) {
//			FacesContext.getCurrentInstance().addMessage("lpcDlgMessage",
//					new FacesMessage(FacesMessage.SEVERITY_ERROR, Utils.loadMessagesFromFile("no.upload"), ""));
//		}
//
//	}
	/**
	 * Getters and setters
	 * */

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
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

//	public int getPurposeId() {
//		return purposeId;
//	}
//
//	public void setPurposeId(int purposeId) {
//		this.purposeId = purposeId;
//	}

	public List<WrkPurpose> getWrkPurposes() {
		return wrkPurposes;
	}

	public void setWrkPurposes(List<WrkPurpose> wrkPurposes) {
		this.wrkPurposes = wrkPurposes;
	}

	// public List<WrkCommentType> getCommentTypes() {
	// return commentTypes;
	// }
	//
	// public void setCommentTypes(List<WrkCommentType> commentTypes) {
	// this.commentTypes = commentTypes;
	// }

//	public void setFile(UploadedFile file) {
//		this.file = file;
//	}
//
//	public UploadedFile getFile() {
//		return file;
//	}

	public WrkApplication getWrkApplication() {
		return wrkApplication;
	}

	public void setWrkApplication(WrkApplication wrkApplication) {
		this.wrkApplication = wrkApplication;
	}

	public String getApplicationPurpose() {
		return applicationPurpose;
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

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public boolean isPersonalCommnetFlag() {
		return personalCommnetFlag;
	}

	public void setPersonalCommnetFlag(boolean personalCommnetFlag) {
		this.personalCommnetFlag = personalCommnetFlag;
	}

//	public boolean isPersonalFlag() {
//		return personalFlag;
//	}
//
//	public void setPersonalFlag(boolean personalFlag) {
//		this.personalFlag = personalFlag;
//	}
	//
	// public boolean isMnagerFlag() {
	// return mnagerFlag;
	// }
	//
	// public void setMnagerFlag(boolean mnagerFlag) {
	// this.mnagerFlag = mnagerFlag;
	// }

	public boolean isManagerFlag() {
		return managerFlag;
	}

	public void setManagerFlag(boolean managerFlag) {
		this.managerFlag = managerFlag;
	}

	public String getFontSize() {
		return fontSize;
	}

	public void setFontSize(String fontSize) {
		switch (fontSize) {

		case "14":
			fSize = (float) 26.75;
			break;
		case "15":
			fSize = (float) 28.75;
			break;
		case "16":
			fSize = (float) 30.75;
			break;
		default:
			fSize = (float) 30.75;
			break;
		}
		this.fontSize = fontSize;
	}

	public Integer getAttachNB() {
		return attachNB;
	}

	public void setAttachNB(Integer attachNB) {
		this.attachNB = attachNB;
	}

	public List<ArcAttach> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<ArcAttach> attachs) {
		this.attachs = attachs;
	}
	
	public String getDate1() {
		return date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public float getfSize() {

		return fSize;
	}

	public void setfSize(float fSize) {
		this.fSize = fSize;
	}
	
	// private String getScannedFile() {
	// String pcName;
	// String fileName = null;
	// try {
	// HttpServletRequest request = (HttpServletRequest)
	// (FacesContext.getCurrentInstance().getExternalContext()
	// .getRequest());
	// pcName = request.getRemoteUser() + "-PC";
	//
	// logger.info("request.getRemoteUser()" + request.getRemoteUser());
	//
	// fileName = "image2_" + pcName + ".pdf";
	//
	// logger.info(" fileName " + fileName);
	//
	// InputStream inputStrScann = FtpTransferFile.getFile(fileName);
	//
	// logger.error("pcName :" + pcName);
	//
	// AttachmentModel attach = new AttachmentModel();
	//
	// attach.setAttachStream(inputStrScann);
	// attach.setAttachExt("pdf");
	//
	// attachList.add(attach);
	//
	// } catch (Exception e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// return fileName;
	// }

}

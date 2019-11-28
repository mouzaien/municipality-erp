package com.bkeryah.managedBean;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;

import com.bkeryah.bean.ArcAttachmentClass;
import com.bkeryah.bean.CommentClass;
import com.bkeryah.bean.UserContactClass;
import com.bkeryah.bean.UserMailObj;
import com.bkeryah.bean.WrkCommentTypeClass;
import com.bkeryah.bean.WrkCommentsClass;
import com.bkeryah.bean.WrkPurposeClass;
import com.bkeryah.dao.ICommonDao;
import com.bkeryah.entities.ArcAttach;
import com.bkeryah.entities.ArcRecAtt;
import com.bkeryah.entities.ArcRecAttId;
import com.bkeryah.entities.ArcRecordLinking;
import com.bkeryah.entities.ArcRecordLinkingId;
import com.bkeryah.entities.ArcRecords;
import com.bkeryah.entities.ArcRecordsLink;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.Charging;
import com.bkeryah.entities.HrMedicalRequest;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.entities.WrkApplicationExtension;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.entities.WrkComment;
import com.bkeryah.entities.WrkCommentType;
import com.bkeryah.entities.WrkInboxFolder;
import com.bkeryah.entities.WrkUserFolderMail;
import com.bkeryah.entities.wrkUSerFolderMailId;
import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.model.AttachmentModel;
import com.bkeryah.service.DataAccessService;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.shared.beans.Scanner;
import com.bkeryah.testssss.EmployeeForDropDown;

import common.Util.EncrypterDecrypter;
import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class MailDefenationBean extends Scanner {

	private UserMailObj selectedInbox;

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	@ManagedProperty(value = "#{commonDao}")
	private ICommonDao commonDao;
	private boolean btnPanelFlag;
	private boolean recordHasComment;
	private boolean copyOfComment;
	private List<WrkCommentsClass> comments = new ArrayList<WrkCommentsClass>();
	private List<ArcAttachmentClass> attList = new ArrayList<ArcAttachmentClass>();
	private String markingInfo;
	private CommentClass commentClass;
	private boolean selectedCommentSigned;
	private String reportServerIp;
	private WrkApplication application = new WrkApplication();
	private int WrkPurp;
	private String signInfo;
	private int wrkReciever;
	private int wrkReciever1;
	private boolean managerFlag;
	private boolean secretFlag;
	private boolean importantFlag;
	private Map<String, String> purposeMap = new LinkedHashMap<String, String>();
	private Map<String, String> empsMapInDept = new LinkedHashMap<String, String>();
	private Map<String, String> mgrMap = new LinkedHashMap<String, String>();
	private List<Integer> WrkCopyEmpRecievers = new ArrayList<Integer>();
	private List<Integer> WrkCopyMngRecievers = new ArrayList<Integer>();
	private String reportsUrl;
	private String CommentAppAttach;
	private WrkComment comment = new WrkComment();
	// private Map<String, String> commentTypes = new LinkedHashMap<String,
	// String>();
	private List<WrkCommentType> commentTypeList;
	private String commentSignType;
	private boolean canMark;
	private boolean canSign;
	private boolean canAmeenSign;
	private int roleId;
	private boolean presidentFlag;
	protected static final Logger logger = Logger.getLogger(DataAccessService.class);
	private String sendToUserName;
	private String SendToUserLastName;
	private List<String> commentCopyReciever;
	private Integer currentUserId;
	private String confirmPassword;
	private List<WrkInboxFolder> userfolders;
	private Map<String, String> commentSignEmployeesList = new LinkedHashMap<String, String>();
	private int selectedUserInSignComment;
	// @ManagedProperty(value = "#{mailsBean}")
	// private MailsBean mailsBean;
	private String folderName;
	private int folderId;
	private boolean checkAllDeptEmpsFlag;
	private boolean checkAllDeptEmpsFlagMngr;
	private ArcUsers currentUser;
	private String fontSize = "16";
	private Integer attachNB;
	private boolean activeTabRemove;
	private boolean commentActive;
	private WrkApplicationId wrkId;
	private String inputHiddenValue;
	private boolean copyLetter;
	private boolean activeUpdate;
	private String printLetter;
	private List<ArcAttach> previousAttachList = new ArrayList<>();
	private Integer NewIncomeNoRefered;
	private Charging chargObj = new Charging();
	private float fSize = 29.75f;
	private boolean enableReturn;
	private WrkApplicationExtension applicationExtension = new WrkApplicationExtension();
	private String notes;
	private boolean hasLetter;
	private WrkComment oldComment;
	private ArcRecords arcRecord = new ArcRecords();
	private boolean isOutcomeNumFlag;
	boolean renderincomflag = false;
	private WrkComment attachComment;
	private List<ArcAttach> attachs = new ArrayList<ArcAttach>();
	private Integer tabActiveIndex = 0;
	private boolean isCommRendred;
	private Integer currArcId;

	public float getfSize() {

		return fSize;
	}

	public void setfSize(float fSize) {
		this.fSize = fSize;
	}

	public String getPrintLetter() {
		encryptWrkId();
		return printLetter;
	}

	public void setPrintLetter(String printLetter) {
		this.printLetter = printLetter;
	}

	@PreDestroy
	public void destroy() {
		logger.info(" maild definition destroyed " + Utils.findCurrentUser().getEmployeeEname());
	}

	// @PostConstruct
	/**
	 * 
	 */
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		UserMailObj selectedMail = (UserMailObj) httpSession.getAttribute("selectedMail");
		// httpSession.removeAttribute("selectedMail");
		encryptWrkId();
		if (selectedMail != null) {
			selectedInbox = selectedMail;
			wrkId = new WrkApplicationId(Integer.parseInt(this.selectedInbox.WrkId), selectedInbox.StepId);
			if (selectedInbox.getWrkIncomeNo() == null) {
				renderincomflag = true;
			}
		} else {
			try {
				context.getExternalContext().redirect("./pages/mailsView.xhtml ");
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (selectedMail.getWrkType() == MailTypeEnum.MODAKARA.getValue()

				|| selectedMail.getWrkType() == MailTypeEnum.COPY.getValue()) {
			activeTabRemove = true;
		} else {
			activeTabRemove = false;
		}

		if (selectedMail.getWrkType() == MailTypeEnum.KITAB.getValue()) {
			if (selectedMail.getWrkIncomeNo() != null)
				oldComment = dataAccessService.loadLinkedArcRecord(selectedMail.getWrkIncomeNo());
			if (oldComment != null) {
				hasLetter = true;
			}
		}

		setCommentActive(false);

		// selectedInbox = (UserMailClass)
		// FacesContext.getCurrentInstance().getExternalContext().getFlash()
		// .get("selectedMail");
		// System.out.println("selected inbox in mail defenition id" +
		// selectedInbox.AppId);
		// FacesContext.getCurrentInstance().getExternalContext().getFlash().remove("selectedMail");
		currentUser = Utils.findCurrentUser();
		currentUserId = currentUser.getUserId();
		userfolders = dataAccessService.getAllfoldersForUser(currentUserId);
		loadcomments();
		roleId = currentUser.getWrkRoleId();

		loadAttachmentByAppId();
		folderId = dataAccessService.getFolderNameForWrkApp(selectedInbox.getStepId(),
				Integer.parseInt(selectedInbox.getWrkId().trim()));
		chargObj = dataAccessService.getChargingemployer(Utils.findCurrentUser().getUserId());

		loadParentWrkComment();
		if (selectedInbox.hasComment == 1) {
			setRecordHasComment(true);
			// selectedCommentSigned = true;
			loadWrkComment();

			List<UserContactClass> signEmployesList = this.dataAccessService
					.findCommentSignEmployeesList(currentUser.getUserId(), this.selectedInbox.WrkId);

			for (UserContactClass item : signEmployesList) {
				commentSignEmployeesList.put(item.getUserName(), String.valueOf(item.getUserId()));
			}

		}
		List<UserContactClass> listOfEmpsInDept = dataAccessService.findAllEmployeesInDept(currentUser.getUserId());

		for (UserContactClass item : listOfEmpsInDept) {
			empsMapInDept.put(item.getUserName(), String.valueOf(item.getUserId()));

		}

		List<WrkPurposeClass> listOfPurposes = dataAccessService.findAllWrkPurpose(currentUser.getUserId());

		List<EmployeeForDropDown> specialEMpList = dataAccessService.getSpecialEmployeeList();

		for (EmployeeForDropDown emp : specialEMpList) {
			if (!empsMapInDept.containsValue(emp.getId())) {

			} else {

				empsMapInDept.put(emp.getName(), String.valueOf(emp.getId()));
			}
			// empsMapInDept.put(emp.getName(), String.valueOf(emp.getId()));
		}

		for (WrkPurposeClass item : listOfPurposes) {
			purposeMap.put(item.getPurpName(), String.valueOf(item.getPurpId()));
		}

		List<UserContactClass> listOfManagers = dataAccessService.findAllManagers(currentUser.getUserId());

		for (UserContactClass item : listOfManagers) {
			mgrMap.put(item.getUserName(), String.valueOf(item.getUserId()));
		}

		try {
			managerFlag = dataAccessService.signedIsAutorized(currentUser.getUserId(), 78);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		commentTypeList = this.dataAccessService.findAllCommentTypes();

		// for (WrkCommentTypeClass item : commentTypeList) {
		// commentTypes.put(item.getWrkCommenttypeName(),
		// String.valueOf(item.getWrkCommenttypeId()));
		// }
		// roleId = currentUser.getWrkRoleId();
		try {
			setManagerFlag(dataAccessService.signedIsAutorized(currentUser.getUserId(), 78));
		} catch (SQLException e) {

			return;
		}
		setSignType();

		if (currentUserId == dataAccessService.getResponsibleId(MyConstants.PRESIDENT))
			presidentFlag = true;
		activeUpdate = false;
		setDefaultValues();

		checkCopyOfLetter();
	}

	private void checkCopyOfLetter() {
		if (selectedInbox.getWrkType() == MailTypeEnum.COPY.getValue()) {
			attachComment = dataAccessService.findWrkCommentByCopyArcId(Integer.parseInt(selectedInbox.getAppId()));
			if (attachComment != null)
				copyOfComment = true;
		}
	}

	private void setSignType() {
		switch (roleId) {
		case 4:
			canMark = true;
			break;
		case 2:
			canMark = true;
			canSign = true;
			managerFlag = true;
			break;

		case 1:
			canAmeenSign = true;
			canSign = true;
			break;
		default:
			break;
		}
	}

	/**
	 * print barcode out
	 * 
	 * @return
	 */
	public String printBarcode() {
		printBarcodeReport(currArcId, 2);
		resetFileds();
		MsgEntry.showModalDialog(Utils.loadMessagesFromFile("redirection.success"));
		return "mails";
	}

	/**
	 * @throws Exception
	 */
	public void encryptWrkId() {
		String wrkIdEncrypted = null;
		try {
			wrkIdEncrypted = EncrypterDecrypter.encrypt(wrkId.getApplicationId().toString());
			printLetter = "http://localhost:8080/BKERY/1/letters/" + wrkIdEncrypted + ".pdf";
		} catch (Exception e) {

		}

	}

	private void setDefaultValues() {
		application.setApplicationPurpose(20);
		wrkReciever = selectedInbox.FromId;
		if ((empsMapInDept.containsValue("" + wrkReciever)) || (empsMapInDept.containsValue("" + wrkReciever)))
			application.setToUserId(wrkReciever);
	}

	public void showComment() {

		RequestContext context = RequestContext.getCurrentInstance();

		context.execute("PF('NewCommentView').show()");
	}

	public void onChangeEventCheckBox() {

	}

	public void loadcomments() {
		List<WrkCommentsClass> srcComments = null;
		String arcIdParent = selectedInbox.getAppId();
		ArcRecordsLink arcLink = dataAccessService.getArcLinkByRecordId(Integer.parseInt(arcIdParent));
		if (arcLink != null) {
			srcComments = dataAccessService.findCommentsByArcId(arcLink.getArcRrecordChildId());
		}
		comments = dataAccessService.findCommentsByWrkId(selectedInbox.WrkId);
		if (srcComments != null && srcComments.size() > 0) {
			srcComments.addAll(comments);
			comments = srcComments;
		}
	}

	public void checkAllListner() {
		if (checkAllDeptEmpsFlag == true) {

			Iterator<Entry<String, String>> entries = empsMapInDept.entrySet().iterator();
			while (entries.hasNext()) {
				Entry<String, String> entry = entries.next();
				String key = entry.getKey();
				String value = entry.getValue();
				WrkCopyEmpRecievers.add(Integer.parseInt(value));

			}

		} else {

			WrkCopyEmpRecievers.clear();

		}
	}

	public void checkAllListnerForManagers() {
		if (checkAllDeptEmpsFlagMngr == true) {

			Iterator<Entry<String, String>> entries = mgrMap.entrySet().iterator();
			while (entries.hasNext()) {
				Entry<String, String> entry = entries.next();
				String key = entry.getKey();
				String value = entry.getValue();
				WrkCopyMngRecievers.add(Integer.parseInt(value));

			}

		} else {

			WrkCopyMngRecievers.clear();

		}
	}

	public void tabChanged(TabChangeEvent event) {
		TabView tv = (TabView) event.getComponent();
		tabActiveIndex = tv.getActiveIndex();
	}

	public void onChangeEventCheckBoxAllMngr() {

	}

	public void addMailToFolder(AjaxBehaviorEvent event) {
		if (folderId != 0) {
			List<WrkUserFolderMail> mails = new ArrayList<>();
			WrkUserFolderMail folderMail = new WrkUserFolderMail();
			wrkUSerFolderMailId folderMailId = new wrkUSerFolderMailId();

			folderMailId.setUserId(currentUserId);
			folderMailId.setWrkAppId(Integer.parseInt(selectedInbox.getWrkId().trim()));
			folderMailId.setWrkAppStepId(selectedInbox.getStepId());
			folderMailId.setFolderId(folderId);
			folderMail.setFolderMailId(folderMailId);
			mails.add(folderMail);
			try {

				WrkUserFolderMail oldRecordForMail = dataAccessService.getFolderMailInfoByMailIdStepNum(
						selectedInbox.getStepId(), Integer.parseInt(selectedInbox.getWrkId().trim()));
				if (oldRecordForMail != null) {

					dataAccessService.deleteMailFomFolder(oldRecordForMail);

				}

				dataAccessService.addMailsToFolder(mails);
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.execution"));
			} catch (Exception e) {
				e.printStackTrace();

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						Utils.loadMessagesFromFile("error.execution"), Utils.loadMessagesFromFile("error.execution")));
			}
		}

	}

	public void loadAttachmentByAppId() {
		attList = dataAccessService.findAttachmentFilesByArcId(selectedInbox.AppId);

	}

	public void loadWrkComment() {
		try {
			commentClass = dataAccessService.getWrkCommentInfoByWrkId(this.selectedInbox.WrkId);
			comment = (WrkComment) dataAccessService.findEntityById(WrkComment.class,
					Integer.parseInt(selectedInbox.WrkId.trim()));
			if (comment.getSignedBy() != null) {
				btnPanelFlag = true;
			}
			if (commentClass.getMarkedBy() > 0) {
				markingInfo = MessageFormat.format(Utils.loadMessagesFromFile("mark.info"),
						dataAccessService.getEmployeeRealName(commentClass.getMarkedBy()),
						Utils.convertDateToArabic(commentClass.getMarkdeIn()));
			} else {
				markingInfo = "**-******* ";
			}
			if (commentClass.getSignedBy() > 0) {
				selectedCommentSigned = true;
				signInfo = MessageFormat.format(Utils.loadMessagesFromFile("sign.info"), commentClass.getSignedIn(),
						this.dataAccessService.getEmployeeRealName(commentClass.getSignedBy()));
				// MsgEntry.addInfoMessage();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadParentWrkComment() {
		MailTypeEnum actualType = MailTypeEnum.parseType(selectedInbox.getWrkType());
		if (actualType == null)
			return;
		switch (actualType) {
		case ACTUALEXCHANGE:
		case PROCUREMENT_MATERIALS:
			Integer parentArchRecordId = dataAccessService
					.getArchRecParentFromLinkByRecordId(Integer.parseInt(selectedInbox.getAppId().trim()));
			Integer wrkIdParent = dataAccessService.getWrkIdByArc(parentArchRecordId);
			List<WrkCommentsClass> wrkCommentParents = dataAccessService.findCommentsByWrkId(wrkIdParent.toString());
			wrkCommentParents.addAll(wrkCommentParents.size(), comments);
			comments = wrkCommentParents;
			break;

		default:
			break;
		}
	}

	public void showCommentTab(AjaxBehaviorEvent event) {
		// comment = new WrkComment();
		recordHasComment = true;
		// InboxDtActiveIndex = 4;
		activeTabRemove = false;
		setCommentActive(true);
		tabActiveIndex = 3;
		isCommRendred = true;
	}

	public String sendRecordToArchive() {
		try {

			dataAccessService.sendRecordToArchive(selectedInbox);
			// mailsBean.getAllInboxList().remove(selectedInbox);
			// mailsBean.setRemoveRecordFlag(true);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.archive.record"));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public void uploadRecordFiles(FileUploadEvent event) {
		try {
			AttachmentModel attach = new AttachmentModel();
			attach.setAttachRealName(event.getFile().getFileName());
			attach.setAttachSize(event.getFile().getSize());
			attach.setAttachStream(event.getFile().getInputstream());
			attach.setAttachExt(FilenameUtils.getExtension(event.getFile().getFileName()));

			ArcAttach arcAttach = new ArcAttach();
			String name = Utils.generateRandomName() + "." + attach.getAttachExt();
			arcAttach.setAttachName(name);
			arcAttach.setAttachOwner(currentUser.getUserId());
			arcAttach.setAttachDate(new Date());
			arcAttach.setAttachSize((double) attach.getAttachSize());
			arcAttach.setAttachType(1);
			arcAttach.setAttachCategory("FILE");
			try {

				Utils.saveAttachments(attach.getAttachStream(), name);
			} catch (Exception e) {
				e.printStackTrace();
			}
			int attId = dataAccessService.save(arcAttach);

			ArcRecAttId arcRecAttId = new ArcRecAttId();
			arcRecAttId.setAttachId(attId);
			arcRecAttId.setRecordId(Integer.parseInt(this.selectedInbox.getAppId()));
			ArcRecAtt arcRecAtt = new ArcRecAtt();
			arcRecAtt.setId(arcRecAttId);
			dataAccessService.saveObject(arcRecAtt);
			loadAttachmentByAppId();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String saveAttchs() {
		uploadFilesToTmpDirectory();
		attachs = Utils.SaveAttachementsToFtpServer(attachList);
		List<Integer> attachIds = dataAccessService.addAttachments(attachs);
		Integer recordId = Integer.parseInt(this.selectedInbox.getAppId());

		for (Integer id : attachIds) {
			ArcRecAtt arcRecAtt = new ArcRecAtt();
			ArcRecAttId arcRecAttId = new ArcRecAttId();
			arcRecAttId.setAttachId(id);
			arcRecAttId.setRecordId(recordId);
			arcRecAtt.setId(arcRecAttId);
			dataAccessService.saveObject(arcRecAtt);

		}
		loadAttachmentByAppId();
		attachList.clear();
		return "";

	}

	public void saveSendTo(AjaxBehaviorEvent event) {
		try {
			ArcUsers x = dataAccessService.getUserNameById(application.getToUserId());
			setSendToUserName(x.getFirstName());
			setSendToUserLastName(x.getLastName());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String wrkSendAction() {

		if (application.getToUserId() == null || application.getToUserId() == 0) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("required.origin.receiver"));
		} else {
			application.setArcRecordId(Integer.parseInt(selectedInbox.AppId));
			try {
				dataAccessService.sendWrkApplication(wrkId, application, secretFlag, WrkCopyEmpRecievers,
						WrkCopyMngRecievers);
				if (arcRecord.isOutcomingNumFlag()) {
					String CurrentHijriDate = HijriCalendarUtil.findCurrentHijriDate();
					arcRecord = dataAccessService.getArcRecord(application.getArcRecordId());
					arcRecord.setOutcomingNo(dataAccessService.createOutcomeNo());
					arcRecord.setIncomeHDate(CurrentHijriDate);
					dataAccessService.updateObject(arcRecord);
				} else {
					arcRecord.setIncomeNo("");
				}
				arcRecord.setImportantFlag(importantFlag);
			} catch (Exception e) {
				logger.error("wrkAppId :" + application.getId().getApplicationId());

			}
			if (arcRecord.getOutcomingNo() != null && arcRecord.getOutcomingNo() > 0) {
				currArcId = application.getArcRecordId();
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('print-barcode-dlg').show()");
			} else {
				resetFileds();
				MsgEntry.showModalDialog(Utils.loadMessagesFromFile("redirection.success"));
				return "mails";
			}

		}
		return "";
	}

	private void resetFileds() {
		secretFlag = false;
		importantFlag = false;
		WrkCopyEmpRecievers = null;
		WrkCopyMngRecievers = null;
		wrkReciever = 0;
		wrkReciever1 = 0;
	}

	public void editWrkCommentBtnAction() {

		setSelectedCommentSigned(false);
		activeUpdate = true;

	}

	/**
	 * Sign comment
	 */
	public void signCommentActionBtn() {
		signComment("S", "error.no.sign.it");
	}

	/**
	 * Sign comment in place of president
	 */
	public void signCommentByNameActionBtn() {
		signComment("N", "error.no.sign.president");
	}

	/**
	 * Sign comment with type
	 * 
	 * @param signType
	 * @param messageKey
	 */
	private void signComment(String signType, String messageKey) {
		if (dataAccessService.EmployeeHasSign(currentUser.getUserId())) {
			if (dataAccessService.WrkAppHasComment(selectedInbox.WrkId)) {
				setCommentSignType(signType);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('confirm-sign-dlg').show()");
			} else {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("save.first"));
			}
		} else {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile(messageKey));
		}
	}

	public String markCommentAction() {
		try {
			if (dataAccessService.WrkAppHasComment(selectedInbox.WrkId)) {

				comment.setMarkedBy(currentUser.getUserId());
				comment.setMarkedIn(HijriCalendarUtil.findCurrentHijriDate());
				commonDao.update(comment);

				// WrkApplicationId wrkId = new
				// WrkApplicationId(Integer.parseInt(this.selectedInbox.WrkId),
				// selectedInbox.StepId);
				WrkApplication application = new WrkApplication();
				application.setArcRecordId(Integer.parseInt(selectedInbox.AppId));
				application.setToUserId(currentUser.getMgrId());
				application.setApplicationPurpose(17);
				application.setApplicationUsercomment(MessageFormat.format(
						Utils.loadMessagesFromFile("mark.comment.complete"), HijriCalendarUtil.findCurrentHijriDate()));
				dataAccessService.redirectRecord(wrkId, application, null, secretFlag);
				MsgEntry.showModalDialog(Utils.loadMessagesFromFile("mark.success"));
				return "mails";
			} else {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("mark.error"));
			}
		} catch (Exception e) {
			logger.error(" markCommentAction  arcRecordsId  :" + Integer.parseInt(selectedInbox.AppId));
		}

		return "";
	}

	public String getConvMarkingInfo() {
		return Utils.convertTextWithArNumric(markingInfo);
	}

	public void commentSaveAction() {
		comment.setWRKAPPID(Integer.parseInt(selectedInbox.WrkId.trim()));
		comment.setAppNo(selectedInbox.getWrkIncomeNo());
		dataAccessService.saveCommentOrUpdate(comment, activeUpdate);
		comment = (WrkComment) dataAccessService.findEntityById(WrkComment.class,
				Integer.parseInt(selectedInbox.WrkId.trim()));
		MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("save.comment.success"));
		// mailsBean.getSelectedInbox().setHasComment(1);
	}

	/**
	 * Save signature
	 * 
	 * @param actionEvent
	 * @return
	 */
	public String signCommentAction() {

		boolean isSigned = currentUser.isSigned();
		if (!isSigned && (confirmPassword == null || confirmPassword.length() <= 0)) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("data.error"));
		} else {
			try {
				if (!isSigned) {
					boolean signedOk = dataAccessService.CheckSignturePassword(currentUser.getUserId(),
							confirmPassword);
					currentUser.setSigned(signedOk);
				}
				if (currentUser.isSigned()) {
					System.out.println("wrkappid"+wrkId.getApplicationId());
					System.out.println("wrkappid"+wrkId.getStepId());
					WrkApplication wrk = commonDao.findWrkApplicationById(wrkId);

					dataAccessService.signComment(wrk, commentSignType, selectedUserInSignComment);

					if (commentCopyReciever != null) {
						for (String userid : commentCopyReciever) {
							dataAccessService.sendCommentCopy(currentUser.getUserId(), Integer.parseInt(userid),
									comment.getWRKAPPID().toString(), selectedInbox.AppId);

						}
					}
					Utils.closeDialog("confirm-sign-dlg");
					MsgEntry.showModalDialog(Utils.loadMessagesFromFile("sign.comment.success"));
					// savePdfComment();
					return "mails";
				} else {
					MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("sign.comment.error"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return "";
	}

	/**
	 * save pdf report
	 */
	private void savePdfComment() {
		String pdfComment = Utils.savePdfWrkComment(comment);
		if (pdfComment.length() > 0) {
			Integer appId = comment.getAppId();
			WrkComment comment = (WrkComment) dataAccessService.findEntityById(WrkComment.class, appId);
			comment.setPdfComment(pdfComment);
			dataAccessService.updateObject(comment);
		}
	}

	public void clearEmpRadioList(ValueChangeEvent valueChangeEvent) {

		application.setToUserId(wrkReciever);

	}

	public void clearMngRadioList(ValueChangeEvent valueChangeEvent) {

		application.setToUserId(wrkReciever);
	}

	/**
	 * Print letter report
	 */
	public String printOldLetterAction() {
		String reportName = "/reports/letter2.jasper";
		Map<String, Object> parameters = populateLetterParameters("" + oldComment.getAppId(), oldComment.getFontSize(),
				0);
		// Utils.printPdfReport(reportName, parameters);
		List<WrkComment> comms = new ArrayList<WrkComment>();
		comms.add(oldComment);
		Utils.printPdfReportFromListDataSource(reportName, parameters, comms);
		return "";
	}

	/**
	 * Print letter report
	 */
	public String printReportAction() {
		String reportName = "/reports/letter2.jasper";
		Map<String, Object> parameters = populateLetterParameters(selectedInbox.getWrkId(), comment.getFontSize(),
				((copyLetter) ? 1 : 0));
		List<WrkComment> comms = new ArrayList<WrkComment>();
		comms.add(comment);
		Utils.printPdfReportFromListDataSource(reportName, parameters, comms);
		return "";
	}

	/**
	 * Print letter report
	 */
	public String printAttachReportAction() {
		String reportName = "/reports/letter2.jasper";
		Map<String, Object> parameters = populateLetterParameters(selectedInbox.getWrkId(), attachComment.getFontSize(),
				((copyLetter) ? 1 : 0));
		List<WrkComment> comms = new ArrayList<WrkComment>();
		comms.add(attachComment);
		Utils.printPdfReportFromListDataSource(reportName, parameters, comms);
		return "";
	}

	private Map<String, Object> populateLetterParameters(String wrkId, Integer fontSize, Integer copy) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		// parameters.put("p1", wrkId);// "259306";
		parameters.put("textSize", fontSize);
		parameters.put("copy", copy);
		parameters.put("SUBREPORT_DIR",
				FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/watan.jpg"));
		dataAccessService.loadLetterReportParameters(parameters, wrkId);
		return parameters;
	}

	/**
	 * Print letter report
	 */
	public String viewReportAction() {
		String reportName = "/reports/letter_view_blank.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p1", (selectedInbox == null) ? "" : selectedInbox.getWrkId());
		parameters.put("textSize", comment.getFontSize());

		parameters.put("APP_NO", comment.getAppNo());
		// parameters.put("SEC_NAME", "");
		parameters.put("APP_SUBJECT", comment.getAppSubject());
		parameters.put("APP_DATE", comment.getAppHdate());
		parameters.put("APP_PAPERS", comment.getAppPapers());
		// parameters.put("TNAME", "");
		parameters.put("FIRST_COPY", comment.getFirstCopy());
		parameters.put("LONG_COMMENT", comment.getLongCommentArabStr());
		parameters.put("APP_TO", comment.getAppTo());
		parameters.put("SECOND_COPY", comment.getSecondCopy());
		// parameters.put("WNAME", "");
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	/**
	 * Print letter report
	 */
	public String printExplanationReportAction() {
		String reportName = "/reports/rep001.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p1", selectedInbox.getWrkId());// "259306";
		parameters.put("wrkDate", selectedInbox.getWrkHdate());
		parameters.put("title", selectedInbox.getAppTitle());
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public String getSignInfo() {
		return signInfo;
	}

	public void setSignInfo(String signInfo) {
		this.signInfo = signInfo;
	}

	/**
	 * Print report
	 */
	public String printIreportFileAction(String url) {
		String reportName = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (url.contains(MyConstants.REPORT_NORMAL_VACATION)) {
			reportName = "/reports/normal_vacation.jasper";
			parameters.put("seqid", Integer.parseInt(url.substring(url.indexOf("HRSQRY011_116.rdf&PVACSEQ=") + 26)));
			parameters.put("compName", dataAccessService.findSystemProperty("CUSTOMER_NAME"));
		} else if (url.contains(MyConstants.REPORT_MEDICAL)) {
			reportName = "/reports/medical_report.jasper";
			parameters.put("record_id", Integer.parseInt(selectedInbox.getWrkId()));
			HrMedicalRequest medical = dataAccessService
					.getMedicalRequestInfoByArchRecordId(Integer.parseInt(selectedInbox.getWrkId()));
			parameters.put("day", Utils.getDayForHigriDate(medical.getRequestDate()));
		}
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	/**
	 * Print report
	 */
	public String printMedicalReportAction(String url) {
		String reportName = "/reports/medical_report.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("record_id", Integer.parseInt(selectedInbox.getWrkId()));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	// public void addMailToFolder(AjaxBehaviorEvent event) {
	//
	// }
	public UserMailObj getSelectedInbox() {
		return selectedInbox;
	}

	public boolean isCommentActive() {
		return commentActive;
	}

	public void setCommentActive(boolean commentActive) {
		this.commentActive = commentActive;
	}

	public boolean isActiveTabRemove() {
		return activeTabRemove;
	}

	public void setActiveTabRemove(boolean activeTabRemove) {
		this.activeTabRemove = activeTabRemove;
	}

	public List<WrkInboxFolder> getUserfolders() {
		return userfolders;
	}

	public void setUserfolders(List<WrkInboxFolder> userfolders) {
		this.userfolders = userfolders;
	}

	public boolean isCheckAllDeptEmpsFlagMngr() {
		return checkAllDeptEmpsFlagMngr;
	}

	public void setCheckAllDeptEmpsFlagMngr(boolean checkAllDeptEmpsFlagMngr) {
		this.checkAllDeptEmpsFlagMngr = checkAllDeptEmpsFlagMngr;
	}

	public ArcUsers getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(ArcUsers currentUser) {
		this.currentUser = currentUser;
	}

	public String getSendToUserName() {
		return sendToUserName;
	}

	public String getSendToUserLastName() {
		return SendToUserLastName;
	}

	public void setSendToUserName(String sendToUserName) {
		this.sendToUserName = sendToUserName;
	}

	public void setSendToUserLastName(String sendToUserLastName) {
		SendToUserLastName = sendToUserLastName;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public boolean isCheckAllDeptEmpsFlag() {
		return checkAllDeptEmpsFlag;
	}

	public void setCheckAllDeptEmpsFlag(boolean checkAllDeptEmpsFlag) {
		this.checkAllDeptEmpsFlag = checkAllDeptEmpsFlag;
	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public int getFolderId() {
		return folderId;
	}

	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}

	public List<String> getCommentCopyReciever() {
		return commentCopyReciever;
	}

	public void setCommentCopyReciever(List<String> commentCopyReciever) {
		this.commentCopyReciever = commentCopyReciever;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public void setSelectedInbox(UserMailObj selectedInbox) {
		this.selectedInbox = selectedInbox;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public boolean isRecordHasComment() {
		return recordHasComment;
	}

	public void setRecordHasComment(boolean recordHasComment) {
		this.recordHasComment = recordHasComment;
	}

	public List<WrkCommentsClass> getComments() {
		return comments;
	}

	public void setComments(List<WrkCommentsClass> comments) {
		this.comments = comments;
	}

	public List<ArcAttachmentClass> getAttList() {
		return attList;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setAttList(List<ArcAttachmentClass> attList) {
		this.attList = attList;
	}

	public boolean isManagerFlag() {
		return managerFlag;
	}

	public WrkComment getComment() {
		return comment;
	}

	public boolean isPresidentFlag() {
		return presidentFlag;
	}

	// public MailsBean getMailsBean() {
	// return mailsBean;
	// }
	//
	// public void setMailsBean(MailsBean mailsBean) {
	// this.mailsBean = mailsBean;
	// }

	public int getCurrentUserId() {
		return currentUserId;
	}

	public void setPresidentFlag(boolean presidentFlag) {
		this.presidentFlag = presidentFlag;
	}

	public void setCurrentUserId(int currentUserId) {
		this.currentUserId = currentUserId;
	}

	public String getCommentSignType() {
		return commentSignType;
	}

	public void setCommentSignType(String commentSignType) {
		this.commentSignType = commentSignType;
	}

	public void setComment(WrkComment comment) {
		this.comment = comment;
	}

	public void setManagerFlag(boolean managerFlag) {
		this.managerFlag = managerFlag;
	}

	public String getCommentAppAttach() {
		return CommentAppAttach;
	}

	public void setCommentAppAttach(String commentAppAttach) {
		CommentAppAttach = commentAppAttach;
	}

	public String getReportsUrl() {
		reportsUrl = "http://" + dataAccessService.findSystemProperty(MyConstants.APP_NAME_NEW)
				+ "/reports/rwservlet?repsrv&report=d:\\archiving\\reports\\COMMENT2.rdf&P_1="
				+ this.selectedInbox.WrkId;
		return reportsUrl;
	}

	public void setReportsUrl(String reportsUrl) {
		this.reportsUrl = reportsUrl;
	}

	public CommentClass getCommentClass() {
		return commentClass;
	}

	public void setCommentClass(CommentClass commentClass) {
		this.commentClass = commentClass;
	}

	public String getMarkingInfo() {
		return markingInfo;
	}

	public void setMarkingInfo(String markingInfo) {
		this.markingInfo = markingInfo;
	}

	public boolean isSelectedCommentSigned() {
		return selectedCommentSigned;
	}

	public List<Integer> getWrkCopyEmpRecievers() {
		return WrkCopyEmpRecievers;
	}

	public List<Integer> getWrkCopyMngRecievers() {
		return WrkCopyMngRecievers;
	}

	public void setWrkCopyEmpRecievers(List<Integer> wrkCopyEmpRecievers) {
		WrkCopyEmpRecievers = wrkCopyEmpRecievers;
	}

	public void setWrkCopyMngRecievers(List<Integer> wrkCopyMngRecievers) {
		WrkCopyMngRecievers = wrkCopyMngRecievers;
	}

	public void setSelectedCommentSigned(boolean selectedCommentSigned) {
		this.selectedCommentSigned = selectedCommentSigned;
	}

	public String getReportServerIp() {
		reportServerIp = dataAccessService.findSystemProperty("REPORT_SERVER_IP");
		return reportServerIp;
	}

	public Map<String, String> getMgrMap() {
		return mgrMap;
	}

	public void setMgrMap(Map<String, String> mgrMap) {
		this.mgrMap = mgrMap;
	}

	public void setReportServerIp(String reportServerIp) {
		this.reportServerIp = reportServerIp;
	}

	public Map<String, String> getEmpsMapInDept() {
		return empsMapInDept;
	}

	public void setEmpsMapInDept(Map<String, String> empsMapInDept) {
		this.empsMapInDept = empsMapInDept;
	}

	public WrkApplication getApplication() {
		return application;
	}

	public int getWrkReciever() {
		return wrkReciever;
	}

	public int getWrkReciever1() {
		return wrkReciever1;
	}

	public Map<String, String> getPurposeMap() {
		return purposeMap;
	}

	public void setWrkReciever(int wrkReciever) {
		this.wrkReciever = wrkReciever;
	}

	public void setWrkReciever1(int wrkReciever1) {
		this.wrkReciever1 = wrkReciever1;
	}

	public void setPurposeMap(Map<String, String> purposeMap) {
		this.purposeMap = purposeMap;
	}

	public int getWrkPurp() {
		return WrkPurp;
	}

	public void setApplication(WrkApplication application) {
		this.application = application;
	}

	public boolean isSecretFlag() {
		return secretFlag;
	}

	public boolean isImportantFlag() {
		return importantFlag;
	}

	// public Map<String, String> getCommentTypes() {
	// return commentTypes;
	// }
	//
	// public void setCommentTypes(Map<String, String> commentTypes) {
	// this.commentTypes = commentTypes;
	// }

	public void setSecretFlag(boolean secretFlag) {
		this.secretFlag = secretFlag;
	}

	public void setImportantFlag(boolean importantFlag) {
		this.importantFlag = importantFlag;
	}

	public boolean isCanMark() {
		return canMark;
	}

	public boolean isCanSign() {
		return canSign;
	}

	public boolean isCanAmeenSign() {
		return canAmeenSign;
	}

	public void setCanMark(boolean canMark) {
		this.canMark = canMark;
	}

	public void setCanSign(boolean canSign) {
		this.canSign = canSign;
	}

	public void setCanAmeenSign(boolean canAmeenSign) {
		this.canAmeenSign = canAmeenSign;
	}

	public void setWrkPurp(int wrkPurp) {
		WrkPurp = wrkPurp;
	}

	public Map<String, String> getCommentSignEmployeesList() {
		return commentSignEmployeesList;
	}

	public int getSelectedUserInSignComment() {
		return selectedUserInSignComment;
	}

	public void setCommentSignEmployeesList(Map<String, String> commentSignEmployeesList) {
		this.commentSignEmployeesList = commentSignEmployeesList;
	}

	public void setSelectedUserInSignComment(int selectedUserInSignComment) {
		this.selectedUserInSignComment = selectedUserInSignComment;
	}

	public boolean isBtnPanelFlag() {
		return btnPanelFlag;
	}

	public void setBtnPanelFlag(boolean btnPanelFlag) {
		this.btnPanelFlag = btnPanelFlag;
	}

	public String getFontSize() {
		return fontSize;
	}

	public void setFontSize(String fontSize) {
		switch (fontSize) {
		case "14":
			fSize = (float) 25.75;
			break;
		case "15":
			fSize = (float) 27.75;
			break;
		case "16":
			fSize = (float) 29.75;
			break;
		default:
			fSize = (float) 29.75;
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

	public String getInputHiddenValue() {
		return (!recordHasComment) ? "show" : "hide";
	}

	public void setInputHiddenValue(String inputHiddenValue) {
		this.inputHiddenValue = inputHiddenValue;
	}

	public boolean isCopyLetter() {
		return copyLetter;
	}

	public void setCopyLetter(boolean copyLetter) {
		this.copyLetter = copyLetter;
	}

	public List<ArcAttach> getPreviousAttachList() {
		return previousAttachList;
	}

	public void setPreviousAttachList(List<ArcAttach> previousAttachList) {
		this.previousAttachList = previousAttachList;
	}

	public Integer getNewIncomeNoRefered() {
		return NewIncomeNoRefered;
	}

	public void setNewIncomeNoRefered(Integer newIncomeNoRefered) {
		NewIncomeNoRefered = newIncomeNoRefered;
	}

	public boolean isEnableReturn() {
		if (Utils.findCurrentUser().getUserId() == 336)
			enableReturn = true;
		return enableReturn;
	}

	public void setEnableReturn(boolean enableReturn) {
		this.enableReturn = enableReturn;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void openLinkingDlg(ActionEvent ae) {
		if (selectedInbox.getWrkIncomeNo() != null
				&& dataAccessService.recordIslinked(Integer.parseInt(selectedInbox.getWrkIncomeNo()))) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ø§Ù„Ù…Ø¹Ø§Ù…Ù„Ø© Ù…Ø±ØªØ¨Ø·Ø© Ø¨Ø§Ù„Ù�Ø¹Ù„", ""));

			return;
		}

		if (selectedInbox.getWrkIncomeNo() == null || selectedInbox.getWrkIncomeNo().length() < 1) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ØªØ£ÙƒØ¯ Ù…Ù† ÙˆØ¬ÙˆØ¯ Ø±Ù‚Ù… ÙˆØ§Ø±Ø¯", ""));

			return;
		}
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('link-dlg').show();");
	}

	public void loadprvAttachament(ActionEvent ae) {
		if (NewIncomeNoRefered > 0) {
			previousAttachList = dataAccessService.getAttachmentByIncomeNumber(NewIncomeNoRefered);
		} else {
			previousAttachList = new ArrayList<>();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ØªØ£ÙƒØ¯ Ù…Ù† ÙˆØ¬ÙˆØ¯ Ø±Ù‚Ù… ÙˆØ§Ø±Ø¯", ""));

		}
	}

	public void linkRecord(ActionEvent ae) {
		if (NewIncomeNoRefered == null || previousAttachList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ØªØ£ÙƒØ¯ Ù…Ù† ÙˆØ¬ÙˆØ¯ Ù…Ø±Ù�Ù‚Ø§Øª", ""));
			return;
		}

		for (ArcAttach arcAttach : previousAttachList) {
			if (arcAttach.getAttachName().equalsIgnoreCase("309") && arcAttach.getId() < 1
					&& arcAttach.getAttachCategory().equalsIgnoreCase("DATA")) {
				dataAccessService.save(arcAttach);
			}

			ArcRecAttId arcRecAttId = new ArcRecAttId(Integer.parseInt(selectedInbox.getAppId()), arcAttach.getId());
			ArcRecAtt arcRecAtt = new ArcRecAtt();
			arcRecAtt.setId(arcRecAttId);
			try {
				dataAccessService.saveObject(arcRecAtt);
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ø­Ø¯Ø« Ø®Ø·Ø£ Ø£Ø«Ù†Ø§Ø¡ Ø§Ù„ØªÙ†Ù�ÙŠØ°", ""));
			}

		}
		ArcRecordLinkingId arcRecordLinkingId = new ArcRecordLinkingId(Integer.parseInt(selectedInbox.getWrkIncomeNo()),
				this.NewIncomeNoRefered);
		ArcRecordLinking arcRecordLinking = new ArcRecordLinking();
		arcRecordLinking.setId(arcRecordLinkingId);
		dataAccessService.saveObject(arcRecordLinking);

		NewIncomeNoRefered = null;
		previousAttachList = new ArrayList<>();
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('link-dlg').hide();");
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "ØªÙ… Ø§Ù„Ø±Ø¨Ø· Ø¨Ù†Ø¬Ø§Ø­", ""));
	}

	public Charging getChargObj() {
		return chargObj;
	}

	public void setChargObj(Charging chargObj) {
		this.chargObj = chargObj;
	}

	public WrkApplicationExtension getApplicationExtension() {
		return applicationExtension;
	}

	public void setApplicationExtension(WrkApplicationExtension applicationExtension) {
		this.applicationExtension = applicationExtension;
	}

	public String returnRequestAction() {
		try {
			dataAccessService.redirectExchangeRequest(wrkId, selectedInbox.getFromId(), notes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return "mails";
	}

	public boolean isHasLetter() {
		return hasLetter;
	}

	public void setHasLetter(boolean hasLetter) {
		this.hasLetter = hasLetter;
	}

	public WrkComment getOldComment() {
		return oldComment;
	}

	public void setOldComment(WrkComment oldComment) {
		this.oldComment = oldComment;
	}

	public ArcRecords getArcRecord() {
		return arcRecord;
	}

	public void setArcRecord(ArcRecords arcRecord) {
		this.arcRecord = arcRecord;
	}

	public boolean isIncomeNumFlag() {
		return isOutcomeNumFlag;
	}

	public void setIncomeNumFlag(boolean isIncomeNumFlag) {
		this.isOutcomeNumFlag = isIncomeNumFlag;
	}

	public boolean isRenderincomflag() {
		return renderincomflag;
	}

	public void setRenderincomflag(boolean renderincomflag) {
		this.renderincomflag = renderincomflag;
	}

	public boolean isCopyOfComment() {
		return copyOfComment;
	}

	public void setCopyOfComment(boolean copyOfComment) {
		this.copyOfComment = copyOfComment;
	}

	public WrkComment getAttachComment() {
		return attachComment;
	}

	public void setAttachComment(WrkComment attachComment) {
		this.attachComment = attachComment;
	}

	public List<AttachmentModel> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<AttachmentModel> attachList) {
		this.attachList = attachList;
	}

	public List<WrkCommentType> getCommentTypeList() {
		return commentTypeList;
	}

	public void setCommentTypeList(List<WrkCommentType> commentTypeList) {
		this.commentTypeList = commentTypeList;
	}

	public Integer getTabActiveIndex() {
		return tabActiveIndex;
	}

	public void setTabActiveIndex(Integer tabActiveIndex) {
		this.tabActiveIndex = tabActiveIndex;
	}

	public boolean isCommRendred() {
		return isCommRendred;
	}

	public void setCommRendred(boolean isCommRendred) {
		this.isCommRendred = isCommRendred;
	}

	public boolean isOutcomeNumFlag() {
		return isOutcomeNumFlag;
	}

	public void setOutcomeNumFlag(boolean isOutcomeNumFlag) {
		this.isOutcomeNumFlag = isOutcomeNumFlag;
	}
}

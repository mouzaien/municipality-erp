
//package com.bkeryah.managedBean;
//
//import java.io.IOException;
//import java.io.Serializable;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.PostConstruct;
//import javax.faces.application.FacesMessage;
//import javax.faces.application.NavigationHandler;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ManagedProperty;
//import javax.faces.bean.SessionScoped;
//import javax.faces.context.FacesContext;
//import javax.faces.event.ActionEvent;
//import javax.faces.event.AjaxBehaviorEvent;
//import javax.faces.event.ValueChangeEvent;
//import javax.servlet.http.HttpSession;
//
//import org.apache.commons.io.FilenameUtils;
//import org.apache.logging.log4j.*;
//import org.primefaces.component.tabview.TabView;
//import org.primefaces.context.RequestContext;
//import org.primefaces.event.FileUploadEvent;
//import org.primefaces.event.SelectEvent;
//import org.primefaces.event.TabChangeEvent;
//import org.primefaces.event.UnselectEvent;
//import org.primefaces.model.LazyDataModel;
//
//import com.bkeryah.bean.ArcAttachmentClass;
//import com.bkeryah.bean.CommentClass;
//import com.bkeryah.bean.UserClass;
//import com.bkeryah.bean.UserContactClass;
//import com.bkeryah.bean.UserFolderClass;
//import com.bkeryah.bean.UserMailClass;
//import com.bkeryah.bean.UserMailLazyList;
//import com.bkeryah.bean.WrkCommentTypeClass;
//import com.bkeryah.bean.WrkCommentsClass;
//import com.bkeryah.bean.WrkPurposeClass;
//import com.bkeryah.dao.ICommonDao;
//import com.bkeryah.entities.ArcAttach;
//import com.bkeryah.entities.ArcRecAtt;
//import com.bkeryah.entities.ArcRecAttId;
//import com.bkeryah.entities.ArcRecords;
//import com.bkeryah.entities.ArcUsers;
//import com.bkeryah.entities.WrkApplication;
//import com.bkeryah.entities.WrkApplicationId;
//import com.bkeryah.entities.WrkComment;
//import com.bkeryah.entities.WrkConfedintialReplies;
//import com.bkeryah.entities.WrkConfedintialRepliesId;
//import com.bkeryah.hr.managedBeans.MainManagedBean;
//import com.bkeryah.mails.MailExecutor;
//import com.bkeryah.mails.MailTypeEnum;
//import com.bkeryah.model.AttachmentModel;
//import com.bkeryah.service.IDataAccessService;
//
//import utilities.HijriCalendarUtil;
//import utilities.MsgEntry;
//import utilities.MyConstants;
//import utilities.Utils;
//
///**
// *
// * @author IbrahimDarwiesh
// */
//@ManagedBean
//@SessionScoped
//public class InboxBean implements Serializable {
//
//	/**
//	 * 
//	 */
//	private static final Logger logger = LogManager.getLogger(InboxBean.class);
//	private static final long serialVersionUID = 1L;
//
//	List<UserMailClass> inboxList;
//	List<UserMailClass> outboxList;
//	public UserMailClass selectedInbox;
//	private List<UserMailClass> selectedInboxs = new ArrayList<UserMailClass>();
//	public UserMailClass selectedOutbox;
//	// public DataAccess dataAccessService = new DataAccessImpl();
//	@ManagedProperty(value = "#{dataAccessService}")
//	private IDataAccessService dataAccessService;
//	@ManagedProperty(value = "#{commonDao}")
//	private ICommonDao commonDao;
//	private LazyDataModel<UserMailClass> mails;
//	private List<UserMailClass> filteredInbox = new ArrayList<>();
//	private LazyDataModel<UserMailClass> outmails;
//	private List<WrkCommentsClass> comments = new ArrayList<WrkCommentsClass>();
//	private List<UserContactClass> InternalDeptEmps = new ArrayList<UserContactClass>();
//	Map<String, String> empMap = new LinkedHashMap<String, String>();
//	Map<String, String> mgrMap = new LinkedHashMap<String, String>();
//	List<ArcAttachmentClass> attList = new ArrayList<ArcAttachmentClass>();
//	List<ArcAttachmentClass> outattList = new ArrayList<ArcAttachmentClass>();
//	Map<String, String> PurpMap = new LinkedHashMap<String, String>();
//	Map<String, String> CommentTypes = new LinkedHashMap<String, String>();
//	Map<String, String> inboxSenderList = new LinkedHashMap<String, String>();
//	private String CommentPrvLink;
//	private String searchKey;
//	private boolean isManager;
//	private boolean president;
//
//	private boolean CanMark;// ØµÙ„Ø§Ø­ÙŠØ© ØªØ£Ø´ÙŠØ± Ø§Ù„Ø®Ø·Ø§Ø¨
//	private boolean CanSign;// ØµÙ„Ø§Ø­ÙŠØ© ØªÙˆÙ‚ÙŠØ¹ Ø§Ù„Ø®Ø·Ø§Ø¨
//	private boolean CanAmeenSign;// ØµÙ„Ø§Ø­ÙŠØ© Ø§Ù„ØªÙˆÙ‚ÙŠØ¹ Ø¨Ø§Ø³Ù…
//									// Ø§Ù…ÙŠÙ† Ø§Ù„Ù…Ù†Ø·Ù‚Ø©
//	private CommentClass CurrentSelectedComment;
//	private String ConfirmPassword;
//	private String commentSignType;
//	private int InboxDtActiveIndex;
//	private boolean isSelectedCommentSigned;
//	private String NewRecordTitle;
//	private String NewRecordComm;
//	private int NewRecordPurp;
//	private boolean NewRecordIsSecret;
//	private boolean NewRecordIsImportant;
//	private boolean NewRecordCreateInocmeYN;
//	private int NewRecordEmpReciever;
//	private int NewRecordMgrReciever;
//	private int NewRecordReciever;
//	private List<Integer> NewRecordEmpCopyReciever;
//	private List<Integer> NewRecordMngCopyReciever;
//	private String NewRecordId;
//	private String FileTypesList;
//	private String CurrentRecordComments;
//	private String Wrk_XXX_App_id;
//	private boolean includeCommentSearch;
//	private boolean ReadUnreadFlag;
//	private int SelectedSender;
//	private String MarkingInfo;
//	private int CurrentInboxCount;
//	private int CurrentReadInboxCount;
//	private int CurrentUnreadInboxCount;
//	private int CurrentOutboxCount;
//	private int CurrentArchivedOutboxCount;
//	private String OutboxSearchKey;
//	private boolean OutboxSearchIncludearchive;
//	private Map<String, String> userFolders = new LinkedHashMap<String, String>();
//	private int SelectedUserFolder;
//	private int selectedFolderId; // not used
//	private Map<String, String> AllUsersMap = new LinkedHashMap<String, String>();
//	private int selectedEmpfromAllUser;
//	private String NewRecordTargetFolder;
//	private boolean showDeptEmployees;
//	private String reportServerIp;
//	private String fileServerIp;
//	private String selectSummAction;
//	private String selectEmployeeForSummaryAction;
//	private String dynamicTabcontent;
//	private String dynamicTabTitle;
//	private MailExecutor mail;
//	private boolean recordHasComment;
//	private boolean isTheFirst = true;
//	@ManagedProperty(value = "#{mainManagedBean}")
//	private MainManagedBean mainManagedBean;
//
//	private String reportsUrl;
//
//	@PostConstruct
//	public void init02() {
//
//	}
//
//	public String getReportsUrl() {
//		reportsUrl = "http://" + dataAccessService.findSystemProperty(MyConstants.APP_NAME)
//				+ "/reports/rwservlet?repsrv&report=d:\\archiving\\reports\\COMMENT2.rdf&P_1="
//				+ this.selectedInbox.WrkId;
//		return reportsUrl;
//	}
//
//	public void setMainManagedBean(MainManagedBean mainManagedBean) {
//		this.mainManagedBean = mainManagedBean;
//	}
//
//	public InboxBean() {
//		if (this.selectedInbox == null) {
//			this.selectedInbox = new UserMailClass();
//			this.selectedInbox.WrkId = "0";
//		}
//		logger.info("Initialization  InboxBean");
//	}
//
//	public void init() {
//
//		inboxList = new ArrayList<UserMailClass>();
//
//		if (dataAccessService == null)
//			return;
//		ArcUsers user = Utils.findCurrentUser();
//		if (user == null)
//			return;
//		List<UserMailClass> inboxMails = this.dataAccessService.findEmployeeInbox(user.getUserId());
//		if ((inboxMails != null) && (!inboxMails.isEmpty())) {
//			this.CurrentReadInboxCount = getNBReadMsgs(inboxMails);
//			this.CurrentUnreadInboxCount = inboxMails.size() - CurrentReadInboxCount;
//			this.CurrentInboxCount = inboxMails.size();
//			mails = new UserMailLazyList(inboxMails);
//		}
//
//		this.CurrentArchivedOutboxCount = this.dataAccessService.EmployeeArchivedArch(user.getUserId());
//		this.showDeptEmployees = false;
//	}
//
//	public String loadMails() {
//		init();
//		return null;
//	}
//
//	public void updateFlag() {
//		setIsSelectedCommentSigned(true);
//	}
//
//	private int getNBReadMsgs(List<UserMailClass> emailsList) {
//		int count = 0;
//		for (UserMailClass email : emailsList) {
//			if (email.getIsRead() == 1)
//				count++;
//		}
//		return count;
//	}
//
//	public String getDynamicTabcontent() {
//		return dynamicTabcontent;
//	}
//
//	public void setDynamicTabcontent(String dynamicTabcontent) {
//		this.dynamicTabcontent = dynamicTabcontent;
//	}
//
//	public String getDynamicTabTitle() {
//		return dynamicTabTitle;
//	}
//
//	public void setDynamicTabTitle(String dynamicTabTitle) {
//		this.dynamicTabTitle = dynamicTabTitle;
//	}
//
//	public String getSelectEmployeeForSummaryAction() {
//		return selectEmployeeForSummaryAction;
//	}
//
//	public void setSelectEmployeeForSummaryAction(String selectEmployeeForSummaryAction) {
//		this.selectEmployeeForSummaryAction = selectEmployeeForSummaryAction;
//	}
//
//	public String getSelectSummAction() {
//		return selectSummAction;
//	}
//
//	public void setSelectSummAction(String selectSummAction) {
//		this.selectSummAction = selectSummAction;
//	}
//
//	public boolean isShowDeptEmployees() {
//		return showDeptEmployees;
//	}
//
//	public void setShowDeptEmployees(boolean showDeptEmployees) {
//		this.showDeptEmployees = showDeptEmployees;
//	}
//
//	public List<UserMailClass> getSelectedInboxs() {
//		return selectedInboxs;
//	}
//
//	public void setSelectedInboxs(List<UserMailClass> selectedInboxs) {
//		this.selectedInboxs = selectedInboxs;
//	}
//
//	public String getReportServerIp() {
//		reportServerIp = this.dataAccessService.findSystemProperty("REPORT_SERVER_IP");
//		return reportServerIp;
//	}
//
//	public void setReportServerIp(String reportServerIp) {
//		this.reportServerIp = reportServerIp;
//	}
//
//	public String getFileServerIp() {
//		fileServerIp = this.dataAccessService.findSystemProperty("FILE_SERVER_IP");
//		return fileServerIp;
//	}
//
//	public void setFileServerIp(String fileServerIp) {
//		this.fileServerIp = fileServerIp;
//	}
//
//	public int getSelectedEmpfromAllUser() {
//		return selectedEmpfromAllUser;
//	}
//
//	public void setSelectedEmpfromAllUser(int selectedEmpfromAllUser) {
//		this.selectedEmpfromAllUser = selectedEmpfromAllUser;
//	}
//
//	public String getNewRecordTargetFolder() {
//		return NewRecordTargetFolder;
//	}
//
//	public void setNewRecordTargetFolder(String NewRecordTargetFolder) {
//		this.NewRecordTargetFolder = NewRecordTargetFolder;
//	}
//
//	public Map<String, String> getAllUsersMap() {
//		AllUsersMap.clear();
//		List<UserClass> list = this.dataAccessService.findAllUsers();
//		for (UserClass item : list) {
//			AllUsersMap.put(item.getVusers_real_name(), String.valueOf(item.getVuser_id()));
//		}
//
//		return AllUsersMap;
//	}
//
//	public void setAllUsersMap(Map<String, String> AllUsersMap) {
//		this.AllUsersMap = AllUsersMap;
//	}
//
//	public int getSelectedFolderId() {
//		return selectedFolderId;
//	}
//
//	public void setSelectedFolderId(int selectedFolderId) {
//		this.selectedFolderId = selectedFolderId;
//	}
//
//	public int getSelectedUserFolder() {
//		return SelectedUserFolder;
//	}
//
//	public void setSelectedUserFolder(int SelectedUserFolder) {
//		this.SelectedUserFolder = SelectedUserFolder;
//	}
//
//	public Map<String, String> getUserFolders() {
//
//		this.userFolders.clear();
//		List<UserFolderClass> list = this.dataAccessService.findAllUserFolders(Utils.findCurrentUser().getUserId());
//		for (UserFolderClass item : list) {
//			userFolders.put(item.getFolderName(), String.valueOf(item.getFolderId()));
//		}
//
//		return userFolders;
//	}
//
//	public void setUserFolders(Map<String, String> userFolders) {
//		this.userFolders = userFolders;
//	}
//
//	public int getCurrentArchivedOutboxCount() {
//		return CurrentArchivedOutboxCount;
//	}
//
//	public void setCurrentArchivedOutboxCount(int CurrentArchivedOutboxCount) {
//		this.CurrentArchivedOutboxCount = CurrentArchivedOutboxCount;
//	}
//
//	public String getOutboxSearchKey() {
//		return OutboxSearchKey;
//	}
//
//	public void setOutboxSearchKey(String OutboxSearchKey) {
//		this.OutboxSearchKey = OutboxSearchKey;
//	}
//
//	public boolean isOutboxSearchIncludearchive() {
//		return OutboxSearchIncludearchive;
//	}
//
//	public void setOutboxSearchIncludearchive(boolean OutboxSearchIncludearchive) {
//		this.OutboxSearchIncludearchive = OutboxSearchIncludearchive;
//	}
//
//	public int getCurrentReadInboxCount() {
//		return CurrentReadInboxCount;
//	}
//
//	public void setCurrentReadInboxCount(int CurrentReadInboxCount) {
//		this.CurrentReadInboxCount = CurrentReadInboxCount;
//	}
//
//	public int getCurrentUnreadInboxCount() {
//		return CurrentUnreadInboxCount;
//	}
//
//	public void setCurrentUnreadInboxCount(int CurrentUnreadInboxCount) {
//		this.CurrentUnreadInboxCount = CurrentUnreadInboxCount;
//	}
//
//	public int getCurrentInboxCount() {
//		return CurrentInboxCount;
//	}
//
//	public void setCurrentInboxCount(int CurrentInboxCount) {
//		this.CurrentInboxCount = CurrentInboxCount;
//	}
//
//	public int getCurrentOutboxCount() {
//		return CurrentOutboxCount;
//	}
//
//	public void setCurrentOutboxCount(int CurrentOutboxCount) {
//		this.CurrentOutboxCount = CurrentOutboxCount;
//	}
//
//	public int getSelectedSender() {
//		return SelectedSender;
//	}
//
//	public String getMarkingInfo() {
//		return MarkingInfo;
//	}
//
//	public String getConvMarkingInfo() {
//		return Utils.convertTextWithArNumric(MarkingInfo);
//	}
//
//	public void setMarkingInfo(String MarkingInfo) {
//		this.MarkingInfo = MarkingInfo;
//	}
//
//	public Map<String, String> getInboxSenderList() {
//		this.inboxSenderList.clear();
//		List<UserContactClass> list = this.dataAccessService.findInboxSenderList(Utils.findCurrentUser().getUserId());
//		inboxSenderList = new LinkedHashMap<String, String>();
//		for (UserContactClass item : list) {
//			inboxSenderList.put(item.getUserName(), String.valueOf(item.getUserId()));
//		}
//		return inboxSenderList;
//	}
//
//	public void setInboxSenderList(Map<String, String> inboxSenderList) {
//		this.inboxSenderList = inboxSenderList;
//	}
//
//	public void setSelectedSender(int SelectedSender) {
//		this.SelectedSender = SelectedSender;
//	}
//
//	public boolean isReadUnreadFlag() {
//		return ReadUnreadFlag;
//	}
//
//	public void setReadUnreadFlag(boolean ReadUnreadFlag) {
//		this.ReadUnreadFlag = ReadUnreadFlag;
//	}
//
//	public boolean isIncludeCommentSearch() {
//		return includeCommentSearch;
//	}
//
//	public void setIncludeCommentSearch(boolean includeCommentSearch) {
//		this.includeCommentSearch = includeCommentSearch;
//	}
//
//	public String getWrk_XXX_App_id() {
//		return Wrk_XXX_App_id;
//	}
//
//	public void setWrk_XXX_App_id(String Wrk_XXX_App_id) {
//		this.Wrk_XXX_App_id = Wrk_XXX_App_id;
//	}
//
//	public String getCurrentRecordComments() {
//		return CurrentRecordComments;
//	}
//
//	public void setCurrentRecordComments(String CurrentRecordComments) {
//		this.CurrentRecordComments = CurrentRecordComments;
//	}
//
//	public String getFileTypesList() {
//		return this.dataAccessService.findAllowedFilesTypes("");
//	}
//
//	public void setFileTypesList(String FileTypesList) {
//		this.FileTypesList = FileTypesList;
//	}
//
//	public String getNewRecordId() {
//		return NewRecordId;
//	}
//
//	public void setNewRecordId(String NewRecordId) {
//		this.NewRecordId = NewRecordId;
//	}
//
//	public int getNewRecordReciever() {
//		return NewRecordReciever;
//	}
//
//	public void setNewRecordReciever(int NewRecordReciever) {
//		this.NewRecordReciever = NewRecordReciever;
//	}
//
//	public String getNewRecordTitle() {
//		return NewRecordTitle;
//	}
//
//	public void setNewRecordTitle(String NewRecordTitle) {
//		this.NewRecordTitle = NewRecordTitle;
//	}
//
//	public String getNewRecordComm() {
//		return NewRecordComm;
//	}
//
//	public void setNewRecordComm(String NewRecordComm) {
//		this.NewRecordComm = NewRecordComm;
//	}
//
//	public int getNewRecordPurp() {
//		return NewRecordPurp;
//	}
//
//	public void setNewRecordPurp(int NewRecordPurp) {
//		this.NewRecordPurp = NewRecordPurp;
//	}
//
//	public boolean isNewRecordIsSecret() {
//		return NewRecordIsSecret;
//	}
//
//	public void setNewRecordIsSecret(boolean NewRecordIsSecret) {
//		this.NewRecordIsSecret = NewRecordIsSecret;
//	}
//
//	public boolean isNewRecordIsImportant() {
//		return NewRecordIsImportant;
//	}
//
//	public void setNewRecordIsImportant(boolean NewRecordIsImportant) {
//		this.NewRecordIsImportant = NewRecordIsImportant;
//	}
//
//	public boolean isNewRecordCreateInocmeYN() {
//		return NewRecordCreateInocmeYN;
//	}
//
//	public void setNewRecordCreateInocmeYN(boolean NewRecordCreateInocmeYN) {
//		this.NewRecordCreateInocmeYN = NewRecordCreateInocmeYN;
//	}
//
//	public int getNewRecordEmpReciever() {
//		return NewRecordEmpReciever;
//	}
//
//	public void setNewRecordEmpReciever(int NewRecordEmpReciever) {
//		this.NewRecordEmpReciever = NewRecordEmpReciever;
//	}
//
//	public int getNewRecordMgrReciever() {
//		return NewRecordMgrReciever;
//	}
//
//	public void setNewRecordMgrReciever(int NewRecordMgrReciever) {
//		this.NewRecordMgrReciever = NewRecordMgrReciever;
//	}
//
//	public List<Integer> getNewRecordEmpCopyReciever() {
//		return NewRecordEmpCopyReciever;
//	}
//
//	public void setNewRecordEmpCopyReciever(List<Integer> NewRecordEmpCopyReciever) {
//		this.NewRecordEmpCopyReciever = NewRecordEmpCopyReciever;
//	}
//
//	public List<Integer> getNewRecordMngCopyReciever() {
//		return NewRecordMngCopyReciever;
//	}
//
//	public void setNewRecordMngCopyReciever(List<Integer> NewRecordMngCopyReciever) {
//		this.NewRecordMngCopyReciever = NewRecordMngCopyReciever;
//	}
//
//	public List<ArcAttachmentClass> getOutattList() {
//		return outattList;
//	}
//
//	public void setOutattList(List<ArcAttachmentClass> outattList) {
//		this.outattList = outattList;
//	}
//
//	/**
//	 * **************************Ø§Ù„Ø¬Ø²Ø¡ Ø§Ù„Ø®Ø§Øµ Ø¨Ø¥Ø­Ø§Ù„Ø©
//	 * Ø§Ù„Ù…Ø¹Ø§Ù…Ù„Ø© ***********************
//	 */
//	private String WrkCommTxt;
//	private boolean IsSecret;
//	private boolean IsImportant;
//	private int WrkPurp;
//	private List<Integer> WrkCopyEmpRecievers = new ArrayList<Integer>();
//	private List<Integer> WrkCopyMngRecievers = new ArrayList<Integer>();
//	private int wrkReciever;
//	private int wrkReciever1;
//
//	public boolean isIsSelectedCommentSigned() {
//		return isSelectedCommentSigned;
//	}
//
//	public void setIsSelectedCommentSigned(boolean isSelectedCommentSigned) {
//		this.isSelectedCommentSigned = isSelectedCommentSigned;
//	}
//
//	public int getInboxDtActiveIndex() {
//		return InboxDtActiveIndex;
//	}
//
//	public void setInboxDtActiveIndex(int InboxDtActiveIndex) {
//		this.InboxDtActiveIndex = InboxDtActiveIndex;
//	}
//
//	public String getCommentSignType() {
//		return commentSignType;
//	}
//
//	public void setCommentSignType(String commentSignType) {
//		this.commentSignType = commentSignType;
//	}
//
//	public String getConfirmPassword() {
//		return ConfirmPassword;
//	}
//
//	public void setConfirmPassword(String ConfirmPassword) {
//		this.ConfirmPassword = ConfirmPassword;
//	}
//
//	public CommentClass getCurrentSelectedComment() {
//		return CurrentSelectedComment;
//	}
//
//	public void setCurrentSelectedComment(CommentClass CurrentSelectedComment) {
//		this.CurrentSelectedComment = CurrentSelectedComment;
//	}
//
//	public String getWrkCommTxt() {
//		return WrkCommTxt;
//	}
//
//	public void setWrkCommTxt(String WrkCommTxt) {
//		this.WrkCommTxt = WrkCommTxt;
//	}
//
//	public boolean isIsSecret() {
//		return IsSecret;
//	}
//
//	public void setIsSecret(boolean IsSecret) {
//		this.IsSecret = IsSecret;
//	}
//
//	public boolean isIsImportant() {
//		return IsImportant;
//	}
//
//	public void setIsImportant(boolean IsImportant) {
//		this.IsImportant = IsImportant;
//	}
//
//	public int getWrkPurp() {
//		return WrkPurp;
//	}
//
//	public void setWrkPurp(int WrkPurp) {
//		this.WrkPurp = WrkPurp;
//	}
//
//	public List<Integer> getWrkCopyEmpRecievers() {
//		return WrkCopyEmpRecievers;
//	}
//
//	public void setWrkCopyEmpRecievers(List<Integer> WrkCopyEmpRecievers) {
//		this.WrkCopyEmpRecievers = WrkCopyEmpRecievers;
//	}
//
//	public List<Integer> getWrkCopyMngRecievers() {
//		return WrkCopyMngRecievers;
//	}
//
//	public void setWrkCopyMngRecievers(List<Integer> WrkCopyMngRecievers) {
//		this.WrkCopyMngRecievers = WrkCopyMngRecievers;
//	}
//
//	public int getWrkReciever() {
//		return wrkReciever;
//	}
//
//	public void setWrkReciever(int wrkReciever) {
//		this.wrkReciever = wrkReciever;
//	}
//
//	public int getWrkReciever1() {
//		return wrkReciever1;
//	}
//
//	public void setWrkReciever1(int wrkReciever1) {
//		this.wrkReciever1 = wrkReciever1;
//	}
//
//	public String wrkSendAction() {
//
//		if (this.wrkReciever == 0 && this.wrkReciever1 == 0) {
//			MsgEntry.addInfoMessage("يجب اختيار جهة ارسال الاصل  ");
//		} else {
//
//			if (this.wrkReciever == 0) {
//				this.wrkReciever = this.wrkReciever1;
//			}
//
//			WrkApplicationId wrkId = new WrkApplicationId(Integer.parseInt(this.selectedInbox.WrkId),
//					this.selectedInbox.StepId);
//
//			WrkApplication application = new WrkApplication();
//			application.setArcRecordId(Integer.parseInt(this.selectedInbox.AppId));
//			application.setToUserId(this.wrkReciever);
//			application.setApplicationPurpose(this.WrkPurp);
//			application.setApplicationUsercomment(this.WrkCommTxt);
//			dataAccessService.redirectRecord(wrkId, application, null
//					,IsSecret);
//
//			// secret Explain
//			
//
//			this.Wrk_XXX_App_id = this.selectedInbox.AppId;
//
//			try {
//				ArcRecords applicationRecord = (ArcRecords) dataAccessService.findEntityById(ArcRecords.class,
//						application.getArcRecordId());
//				ArcRecords newAppRecord = new ArcRecords();
//				newAppRecord.setRecTitle("صورة من " + applicationRecord.getRecTitle());
//				newAppRecord.setCreatedBy(Utils.findCurrentUser().getUserId());
//				newAppRecord.setRecHDate(HijriCalendarUtil.findCurrentHijriDate());
//				newAppRecord.setRecGDate(new Date());
//				newAppRecord.setCreatedIn(new Date());
//				newAppRecord.setApplicationType(0);
//				newAppRecord.setIncomeYear(Integer.parseInt(HijriCalendarUtil.findCurrentYear()));
//				newAppRecord.setLetterFrom(commonDao.findUserSection(Utils.findCurrentUser().getUserId()));
//				Integer newRecId = dataAccessService.save(newAppRecord);
//
//				for (Object empid : WrkCopyEmpRecievers) {
//					int EmployeeId = Integer.parseInt(String.valueOf(empid));
//					application.setToUserId(EmployeeId);
//					application.setId(new WrkApplicationId(commonDao.createWrkId(), 1));
//					application.setArcRecordId(newRecId);
//					dataAccessService.createNewWrkApplication(newRecId, application, null, false, null);
//
//				}
//				for (Object mgrid : WrkCopyMngRecievers) {
//					int ManagerId = Integer.parseInt(String.valueOf(mgrid));
//					application.setToUserId(ManagerId);
//					application.setId(new WrkApplicationId(commonDao.createWrkId(), 1));
//					application.setArcRecordId(newRecId);
//					dataAccessService.createNewWrkApplication(newRecId, application, null, false, null);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			this.selectedInbox = null;
//			this.WrkCommTxt = "";
//			this.IsSecret = false;
//			this.IsImportant = false;
//			this.WrkPurp = 0;
//			this.WrkCopyEmpRecievers = null;
//			this.WrkCopyMngRecievers = null;
//			this.wrkReciever = 0;
//			this.wrkReciever1 = 0;
//			MsgEntry.showModalDialog("تمت الاحالة بنجاح");
//			return "mails";
//		}
//		return "";
//	}
//
//	public String SendRecordToArchive(ActionEvent actionEvent) {
//		WrkApplicationId wrkId = new WrkApplicationId(Integer.parseInt(this.selectedInbox.WrkId),
//				this.selectedInbox.StepId);
//
//		WrkApplication application = new WrkApplication();
//		application.setArcRecordId(Integer.parseInt(this.selectedInbox.AppId));
//		application.setToUserId(85);
//		application.setApplicationPurpose(this.WrkPurp);
//		application.setApplicationUsercomment("أرشفة المعاملة ألكترونيا ");
//		dataAccessService.redirectRecord(wrkId, application, null,IsSecret);
//		this.Wrk_XXX_App_id = this.selectedInbox.AppId;
//
//		try {
//			ArcRecords applicationRecord = (ArcRecords) dataAccessService.findEntityById(ArcRecords.class,
//					application.getArcRecordId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		this.selectedInbox = null;
//		this.WrkCommTxt = "";
//		this.IsSecret = false;
//		this.IsImportant = false;
//		this.WrkPurp = 0;
//		this.WrkCopyEmpRecievers = null;
//		this.WrkCopyMngRecievers = null;
//		this.wrkReciever = 0;
//		this.wrkReciever1 = 0;
//		MsgEntry.showModalDialog(
//				"\u062a\u0645\u062a \u0627\u0644\u0623\u0631\u0634\u0641\u0629 \u0628\u0646\u062c\u0627\u062d");
//		return "mails";
//	}
//
//	public void clearEmpRadioList(ValueChangeEvent valueChangeEvent) {
//		this.wrkReciever = 0;
//	}
//
//	public void clearMngRadioList(ValueChangeEvent valueChangeEvent) {
//		this.wrkReciever1 = 0;
//	}
//	////////////// END OF TRANSFERING RECORD /////////////////
//	/**
//	 * **********************************************************
//	 */
//
//	/**
//	 * *************************************************************************
//	 * ********
//	 */
//	/*
//	 * Ø¹Ù…Ù„ÙŠØ© Ø§Ù„ØªØ¹Ø§Ù…Ù„ Ù…Ø¹ Ø®Ø·Ø§Ø¨ Ø§Ù„ØªØºØ·ÙŠØ© Ø¯Ø§Ø®Ù„
//	 * Ø§Ù„Ù…Ø¹Ø§Ù…Ù„Ø© * ************************* STARTING OF COMMENT ACTION
//	 * --INSERTING --UPDATING *************************
//	 */
//
//	private String CommentTo;
//	private int CommentTyp;
//	private String CommentTopic;
//	private String upperCopy;
//	private String CommentContent;
//	private String lowerCopy;
//	private List<String> CommentCopyReciever;
//	private String CommentAppAttach;
//
//	public String getCommentTo() {
//		return CommentTo;
//	}
//
//	public void setCommentTo(String CommentTo) {
//		this.CommentTo = CommentTo;
//	}
//
//	public int getCommentTyp() {
//		return CommentTyp;
//	}
//
//	public void setCommentTyp(int CommentTyp) {
//		this.CommentTyp = CommentTyp;
//	}
//
//	public String getCommentTopic() {
//		return CommentTopic;
//	}
//
//	public void setCommentTopic(String CommentTopic) {
//		this.CommentTopic = CommentTopic;
//	}
//
//	public String getUpperCopy() {
//		return upperCopy;
//	}
//
//	public void setUpperCopy(String upperCopy) {
//		this.upperCopy = upperCopy;
//	}
//
//	public String getCommentContent() {
//		return CommentContent;
//	}
//
//	public void setCommentContent(String CommentContent) {
//		this.CommentContent = CommentContent;
//	}
//
//	public String getLowerCopy() {
//		return lowerCopy;
//	}
//
//	public void setLowerCopy(String lowerCopy) {
//		this.lowerCopy = lowerCopy;
//	}
//
//	public List<String> getCommentCopyReciever() {
//		return CommentCopyReciever;
//	}
//
//	public void setCommentCopyReciever(List<String> CommentCopyReciever) {
//		this.CommentCopyReciever = CommentCopyReciever;
//	}
//
//	public String getCommentAppAttach() {
//		return CommentAppAttach;
//	}
//
//	public void setCommentAppAttach(String CommentAppAttach) {
//		this.CommentAppAttach = CommentAppAttach;
//	}
//
//	public void commentSaveAction(ActionEvent actionEvent) {
//		if (this.CommentTo == null || this.CommentTo.length() <= 0 || this.CommentTopic == null
//				|| this.CommentTopic.length() <= 0 || this.CommentContent == null || this.CommentContent.length() <= 0
//				|| this.CommentTyp == 0) {
//			MsgEntry.addErrorMessage("أكمل البيانات أولا  ");
//		} else {
//
//			WrkComment comment = new WrkComment();
//			comment.setWRKAPPID(Integer.parseInt(this.selectedInbox.WrkId));
//			comment.setAppTo(this.CommentTo);
//			comment.setAppAttach(this.CommentAppAttach);
//			comment.setAppSubject(this.CommentTopic);
//			comment.setLongComment(this.CommentContent);
//			comment.setFirstCopy(this.upperCopy);
//			comment.setSecondCopy(this.lowerCopy);
//			comment.setCommentType(this.CommentTyp);
//			dataAccessService.saveCommentOrUpdate(comment);
//			setIsSelectedCommentSigned(true);
//			MsgEntry.addInfoMessage("تم حفظ الخطاب بنجاح");
//		}
//	}
//
//	/**
//	 * *************************************************************************
//	 * *****************************
//	 */
//
//	/**
//	 * ***************** New Wrk_COMMENT Insertion ********
//	 */
//	/**
//	 * ******************Ø¹Ù…Ù„ÙŠØ© Ø¥Ù†Ø´Ø§Ø¡ Ø®Ø·Ø§Ø¨ Ø¬Ø¯ÙŠØ¯ Ù…ÙˆØ¬Ù‡
//	 * Ù„Ù„Ù…Ø¯ÙŠØ± Ø§Ù„Ù…Ø¨Ø§Ø´Ø±
//	 */
//	private String NewWrkCommDirectedTo;
//	private String NewWrkCommTopic;
//	private String NewWrkCommHiegherCopy;
//	private String NewWrkCommMainContent;
//	private String NewWrkCommLowerCopy;
//	private int NewWrkCommTyp;
//	private int NewWrkCommPurpose;
//	private String ArcRecordIdOutPut;
//	private int ManagerId;
//	private int newWrkPapers;
//
//	public int getNewWrkPapers() {
//		return newWrkPapers;
//	}
//
//	public void setNewWrkPapers(int newWrkPapers) {
//		this.newWrkPapers = newWrkPapers;
//	}
//
//	public String getArcRecordIdOutPut() {
//		return ArcRecordIdOutPut;
//	}
//
//	public void setArcRecordIdOutPut(String ArcRecordIdOutPut) {
//		this.ArcRecordIdOutPut = ArcRecordIdOutPut;
//	}
//
//	public int getManagerId() {
//		return ManagerId;
//	}
//
//	public void setManagerId(int ManagerId) {
//		this.ManagerId = ManagerId;
//	}
//
//	public String getNewWrkCommDirectedTo() {
//		return NewWrkCommDirectedTo;
//	}
//
//	public void setNewWrkCommDirectedTo(String NewWrkCommDirectedTo) {
//		this.NewWrkCommDirectedTo = NewWrkCommDirectedTo;
//	}
//
//	public String getNewWrkCommTopic() {
//		return NewWrkCommTopic;
//	}
//
//	public void setNewWrkCommTopic(String NewWrkCommTopic) {
//		this.NewWrkCommTopic = NewWrkCommTopic;
//	}
//
//	public String getNewWrkCommHiegherCopy() {
//		return NewWrkCommHiegherCopy;
//	}
//
//	public void setNewWrkCommHiegherCopy(String NewWrkCommHiegherCopy) {
//		this.NewWrkCommHiegherCopy = NewWrkCommHiegherCopy;
//	}
//
//	public String getNewWrkCommMainContent() {
//		return NewWrkCommMainContent;
//	}
//
//	public void setNewWrkCommMainContent(String NewWrkCommMainContent) {
//		this.NewWrkCommMainContent = NewWrkCommMainContent;
//	}
//
//	public String getNewWrkCommLowerCopy() {
//		return NewWrkCommLowerCopy;
//	}
//
//	public void setNewWrkCommLowerCopy(String NewWrkCommLowerCopy) {
//		this.NewWrkCommLowerCopy = NewWrkCommLowerCopy;
//	}
//
//	public int getNewWrkCommTyp() {
//		return NewWrkCommTyp;
//	}
//
//	public void setNewWrkCommTyp(int NewWrkCommTyp) {
//		this.NewWrkCommTyp = NewWrkCommTyp;
//	}
//
//	public int getNewWrkCommPurpose() {
//		return NewWrkCommPurpose;
//	}
//
//	public void setNewWrkCommPurpose(int NewWrkCommPurpose) {
//		this.NewWrkCommPurpose = NewWrkCommPurpose;
//	}
//
//	public void sendNewWrkComment(ActionEvent actionEvent) {
//
//		int CurrEmp = Utils.findCurrentUser().getUserId();
//		int CurrMng = Integer.parseInt(this.dataAccessService.getEmployeeManagerId(String.valueOf(CurrEmp)));
//
//		this.ArcRecordIdOutPut = this.dataAccessService.CreateNewWrkComment(CurrEmp, this.NewWrkCommTopic, CurrMng,
//				this.NewWrkCommPurpose, this.NewWrkCommDirectedTo, this.NewWrkCommMainContent, this.NewWrkCommLowerCopy,
//				this.NewWrkCommTyp, this.NewWrkCommHiegherCopy, this.newWrkPapers);
//		RequestContext.getCurrentInstance().execute("PF('newCommentFileUpload100').upload()");
//		refreshInbox();
//
//		Utils.closeDialog("NewCommentDlg");
//		MsgEntry.showModalDialog("ØªÙ… Ø§Ø±Ø³Ø§Ù„ Ø§Ù„Ø®Ø·Ø§Ø¨ Ø¨Ù†Ø¬Ø§Ø­");
//
//	}
//
//	public void uploadNewWrkCommentFiles(FileUploadEvent event) {
//
//		try {
//			String attachaId = this.dataAccessService.transferAttachment(event.getFile().getInputstream(),
//					event.getFile().getFileName(), Utils.findCurrentUser().getUserId(), this.ArcRecordIdOutPut);
//
//		} catch (Exception e) {
//			MsgEntry.addErrorMessage("Ø®Ø·Ø£ Ù�ÙŠ Ø§Ø±Ø³Ø§Ù„ Ø§Ù„Ù…Ù„Ù� ");
//		}
//
//	}
//
//	/**
//	 * ***************************Ù�Ù†Ù‡Ø§ÙŠØ© Ø¥Ù†Ø´Ø§Ø¡ Ø§Ù„Ø®Ø·Ø§Ø¨
//	 * Ø§Ù„Ø¬Ø¯ÙŠØ¯
//	 *
//	 * @return *********************
//	 */
//	/**
//	 * ******************** sign Comment *****************************
//	 */
//	Map<String, String> CommentSignEmployeesList = new LinkedHashMap<String, String>();
//	private int selectedUserInSignComment;
//
//	public Map<String, String> getCommentSignEmployeesList() {
//		Map<String, String> typMap001 = null;
//		if (selectedInbox != null) {
//			this.CommentSignEmployeesList.clear();
//			List<UserContactClass> list = this.dataAccessService
//					.findCommentSignEmployeesList(Utils.findCurrentUser().getUserId(), this.selectedInbox.WrkId);
//			typMap001 = new LinkedHashMap<String, String>();
//			for (UserContactClass item : list) {
//				typMap001.put(item.getUserName(), String.valueOf(item.getUserId()));
//			}
//		}
//
//		return typMap001;
//	}
//
//	public void setCommentSignEmployeesList(Map<String, String> CommentSignEmployeesList) {
//		this.CommentSignEmployeesList = CommentSignEmployeesList;
//	}
//
//	public int getSelectedUserInSignComment() {
//		return selectedUserInSignComment;
//	}
//
//	public void setSelectedUserInSignComment(int selectedUserInSignComment) {
//		this.selectedUserInSignComment = selectedUserInSignComment;
//	}
//
//	public String commentSignAction(ActionEvent actionEvent) {
//
//		if (this.ConfirmPassword == null || this.ConfirmPassword.length() <= 0 || this.selectedUserInSignComment == 0) {
//			MsgEntry.addErrorMessage("بيانات غير صحيحة . أكمل البيانات أولا");
//		} else {
//			try {
//				WrkComment comment = (WrkComment) dataAccessService.findEntityById(WrkComment.class,
//						Integer.parseInt(selectedInbox.WrkId));
//				if (comment.getSignedBy() != null) {
//
//					Utils.closeDialog("confirm-sign-dlg");
//					MsgEntry.showModalDialog("لا يمكنك توقيع الخطاب .. الخطاب تم توقيعه بالفعل ");
//					return "";
//				}
//
//				if (this.dataAccessService.CheckSignturePassword(Utils.findCurrentUser().getUserId(),
//						this.ConfirmPassword)) {
//
//					WrkApplicationId wrkApplicationId = new WrkApplicationId(Integer.parseInt(this.selectedInbox.WrkId),
//							this.selectedInbox.StepId);
//					WrkApplication wrk = commonDao.findWrkApplicationById(wrkApplicationId);
//
//					dataAccessService.signComment(wrk, this.commentSignType, this.selectedUserInSignComment);
//
//					if (this.CommentCopyReciever != null) {
//						for (String userid : this.CommentCopyReciever) {
//							dataAccessService.sendCommentCopy(Utils.findCurrentUser().getUserId(),
//									Integer.parseInt(userid), this.CurrentSelectedComment.getAppId(),
//									this.selectedInbox.AppId);
//
//						}
//					}
//					Utils.closeDialog("confirm-sign-dlg");
//					this.selectedInbox = null;
//					this.CurrentSelectedComment = null;
//					MsgEntry.showModalDialog("تم توقيع الخطاب");
//					return "mails";
//				} else {
//					MsgEntry.addErrorMessage("خطأ ف يتوقيع الخطاب");
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		}
//		return "";
//	}
//
//	public boolean isIsManager() throws SQLException {
//		this.isManager = false;
//
//		this.isManager = dataAccessService.signedIsAutorized(Utils.findCurrentUser().getUserId(), 78);
//		// int roleId = Utils.findCurrentUser().getWrkRoleId();
//		// if (roleId ==1) {
//		// this.isManager = true;
//		// }
//		return isManager;
//	}
//
//	public void setIsManager(boolean isManager) {
//		this.isManager = isManager;
//	}
//
//	public boolean isPresident() throws SQLException {
//		president = false;
//		Integer presidenId = dataAccessService.getResponsibleId(MyConstants.PRESIDENT);
//		if (Utils.findCurrentUser().getUserId().equals(presidenId))
//			president = true;
//		return president;
//	}
//
//	public void setPresident(boolean isThePresident) {
//		this.president = isThePresident;
//	}
//
//	public boolean isCanMark() {
//		this.CanMark = false;
//		int roleId = Utils.findCurrentUser().getWrkRoleId();
//		if (roleId == 4 || roleId == 2) {
//			this.CanMark = true;
//		}
//		return CanMark;
//	}
//
//	public void setCanMark(boolean CanMark) {
//		this.CanMark = CanMark;
//	}
//
//	public boolean isCanSign() {
//		this.CanSign = false;
//		int roleId = Utils.findCurrentUser().getWrkRoleId();
//		if (roleId == 1 || roleId == 2) {
//			this.CanSign = true;
//		}
//		return CanSign;
//	}
//
//	public void setCanSign(boolean CanSign) {
//		this.CanSign = CanSign;
//	}
//
//	public boolean isCanAmeenSign() {
//		this.CanAmeenSign = false;
//		int roleId = Utils.findCurrentUser().getWrkRoleId();
//		if (roleId == 1) {
//			this.CanAmeenSign = true;
//		}
//		return CanAmeenSign;
//	}
//
//	public void setCanAmeenSign(boolean CanAmeenSign) {
//		this.CanAmeenSign = CanAmeenSign;
//	}
//
//	public Map<String, String> getCommentTypes() {
//		this.CommentTypes.clear();
//		List<WrkCommentTypeClass> list = this.dataAccessService.findAllCommenttTypes();
//		Map<String, String> typMap001 = new LinkedHashMap<String, String>();
//		for (WrkCommentTypeClass item : list) {
//			typMap001.put(item.getWrkCommenttypeName(), String.valueOf(item.getWrkCommenttypeId()));
//		}
//		return typMap001;
//
//	}
//
//	public void setCommentTypes(Map<String, String> CommentTypes) {
//		this.CommentTypes = CommentTypes;
//	}
//
//	public Map<String, String> getPurpMap() {
//		this.PurpMap.clear();
//		List<WrkPurposeClass> list = this.dataAccessService.findAllWrkPurpose();
//		Map<String, String> typMap001 = new LinkedHashMap<String, String>();
//		for (WrkPurposeClass item : list) {
//			typMap001.put(item.getPurpName(), String.valueOf(item.getPurpId()));
//		}
//		return typMap001;
//	}
//
//	public void setPurpMap(Map<String, String> PurpMap) {
//		this.PurpMap = PurpMap;
//	}
//
//	public List<ArcAttachmentClass> getAttList() {
//		return attList;
//	}
//
//	public void setAttList(List<ArcAttachmentClass> attList) {
//		this.attList = attList;
//	}
//
//	public void LoadAttachmentByAppId() {
//		attList = this.dataAccessService.findAttachmentFilesByArcId(this.selectedInbox.AppId);
//
//	}
//
//	public Map<String, String> getMgrMap() {
//		this.mgrMap.clear();
//		List<UserContactClass> list = this.dataAccessService.findAllManagers(Utils.findCurrentUser().getUserId());
//		Map<String, String> typMap001 = new LinkedHashMap<String, String>();
//		for (UserContactClass item : list) {
//			typMap001.put(item.getUserName(), String.valueOf(item.getUserId()));
//		}
//		return typMap001;
//
//	}
//
//	public void setMgrMap(Map<String, String> mgrMap) {
//		this.mgrMap = mgrMap;
//	}
//
//	public Map<String, String> getEmpMap() {
//
//		this.empMap.clear();
//		List<UserContactClass> list = this.dataAccessService
//				.findAllEmployeesInDept(Utils.findCurrentUser().getUserId());
//		Map<String, String> typMap001 = new LinkedHashMap<String, String>();
//		for (UserContactClass item : list) {
//			typMap001.put(item.getUserName(), String.valueOf(item.getUserId()));
//		}
//		return typMap001;
//
//	}
//
//	public void setEmpMap(Map<String, String> empMap) {
//		this.empMap = empMap;
//	}
//
//	public List<UserContactClass> getInternalDeptEmps() {
//		return this.dataAccessService.findAllEmployeesInDept(Utils.findCurrentUser().getUserId());
//	}
//
//	public void setInternalDeptEmps(List<UserContactClass> InternalDeptEmps) {
//		this.InternalDeptEmps = InternalDeptEmps;
//	}
//
//	public LazyDataModel<UserMailClass> getMails() {
//		return mails;
//	}
//
//	public List<UserMailClass> getInboxList() {
//		return this.dataAccessService.findEmployeeInbox(Utils.findCurrentUser().getUserId());
//	}
//
//	public void setInboxList(List<UserMailClass> inboxList) {
//		this.inboxList = inboxList;
//	}
//
//	public List<UserMailClass> getOutboxList() {
//		return this.dataAccessService.findEmployeeOutbox(Utils.findCurrentUser().getUserId());
//	}
//
//	public void setOutboxList(List<UserMailClass> outboxList) {
//		this.outboxList = outboxList;
//	}
//
//	public UserMailClass getSelectedInbox() {
//		return selectedInbox;
//	}
//
//	public void setSelectedInbox(UserMailClass selectedInbox) {
//		this.selectedInbox = selectedInbox;
//	}
//
//	public UserMailClass getSelectedOutbox() {
//		return selectedOutbox;
//	}
//
//	public void setSelectedOutbox(UserMailClass selectedOutbox) {
//		this.selectedOutbox = selectedOutbox;
//	}
//
//	public List<WrkCommentsClass> getComments() {
//		return comments;
//	}
//
//	public void setComments(List<WrkCommentsClass> comments) {
//		this.comments = comments;
//	}
//
//	public void Loadcomments() {
//		this.comments = this.dataAccessService.findCommentsByWrkId(this.selectedInbox.WrkId);
//	}
//
//	public LazyDataModel<UserMailClass> getOutmails() {
//		return outmails;
//	}
//
//	public void setOutmails(LazyDataModel<UserMailClass> outmails) {
//		this.outmails = outmails;
//	}
//
//	public void setCommentPrvLink(String CommentPrvLink) {
//		this.CommentPrvLink = CommentPrvLink;
//	}
//
//	public String viewInboxRow(ActionEvent action) {
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
//		session.setAttribute("arcRecord", selectedInbox.AppId);
//		session.setAttribute("type", selectedInbox.getWrkType());
//		// if old then
//		String page = mainManagedBean.editMail(selectedInbox);
//
//		this.InboxDtActiveIndex = 0;
//		this.isSelectedCommentSigned = false;
//		this.dataAccessService.MakeItRead(String.valueOf(selectedInbox.StepId), selectedInbox.WrkId);
//		recordHasComment = false;
//		if (selectedInbox.hasComment == 1) {
//			recordHasComment = true;
//		}
//		Loadcomments();
//
//		LoadAttachmentByAppId();
//		LoadWrkComment();
//		loadParentWrkComment();
//		// FacesContext context = FacesContext.getCurrentInstance();
//		// context.getExternalContext().getSessionMap().put("amrElnahas",
//		// mailObject.AppId);
//		// else
//
//		return (page);
//	}
//
//	// public void onRowSelect(SelectEvent selectEvent) {
//	// UserMailClass mailObject = (UserMailClass) selectEvent.getObject();
//	//
//	// // if old then
//	// String page = mainManagedBean.editMail(mailObject);
//	//
//	// this.InboxDtActiveIndex = 0;
//	// this.isSelectedCommentSigned = false;
//	// this.dataAccessService.MakeItRead(String.valueOf(mailObject.StepId),
//	// mailObject.WrkId);
//	// recordHasComment = false;
//	// if (mailObject.hasComment == 1) {
//	// recordHasComment = true;
//	// }
//	// Loadcomments();
//	// LoadAttachmentByAppId();
//	// LoadWrkComment();
//	//
//	// // else
//	//
//	// redirect(page);
//	//
//	// }
//
//	private void loadParentWrkComment() {
//		MailTypeEnum actualType = MailTypeEnum.parseType(selectedInbox.getWrkType());
//		if (actualType == null)
//			return;
//		switch (actualType) {
//		case ACTUALEXCHANGE:
//		case PROCUREMENT_MATERIALS:
//			Integer parentArchRecordId = dataAccessService
//					.getArchRecParentFromLinkByRecordId(Integer.parseInt(selectedInbox.getAppId().trim()));
//			Integer wrkIdParent = dataAccessService.getWrkIdByArc(parentArchRecordId);
//			List<WrkCommentsClass> wrkCommentParents = this.dataAccessService
//					.findCommentsByWrkId(wrkIdParent.toString());
//			wrkCommentParents.addAll(wrkCommentParents.size(), this.comments);
//			this.comments = wrkCommentParents;
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	public void redirect(String page) {
//		try {
//			FacesContext.getCurrentInstance().getExternalContext().redirect(page + ".xhtml");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	public void onRowUnselect(UnselectEvent event) {
//
//	}
//
//	public void upload(FileUploadEvent event) {
//
//		try {
//
//			String attachaId = this.dataAccessService.transferAttachment(event.getFile().getInputstream(),
//					event.getFile().getFileName(), Utils.findCurrentUser().getUserId(), "");
//
//		} catch (IOException e) {
//		}
//
//	}
//
//	public void LoadWrkComment() {
//		try {
//			CommentClass commentClass = this.dataAccessService.getWrkCommentInfoByWrkId(this.selectedInbox.WrkId);
//			if (commentClass != null) {
//				this.CommentTo = commentClass.getAppTo();
//				this.CommentTyp = commentClass.getCommentType();
//				this.CommentTopic = commentClass.getAppSubject();
//				this.upperCopy = commentClass.getFirstCopy();
//				this.CommentContent = commentClass.getLongComment();
//				this.lowerCopy = commentClass.getSecondCopy();
//				this.CurrentSelectedComment = commentClass;
//				if (commentClass.getMarkedBy() > 0) {
//					this.MarkingInfo = " تمت عملية تأشير الخطاب بواسطة  "
//							+ this.dataAccessService.getEmployeeRealName(commentClass.getMarkedBy()) + "  بتاريخ  "
//							+ Utils.convertDateToArabic(commentClass.getMarkdeIn());
//				} else {
//					this.MarkingInfo = "**-******* ";
//				}
//				if (commentClass.getSignedBy() > 0) {
//					this.isSelectedCommentSigned = true;
//					MsgEntry.addInfoMessage("ØªÙ… ØªÙˆÙ‚ÙŠØ¹ Ø§Ù„Ø®Ø·Ø§Ø¨ Ø¨ØªØ§Ø±ÙŠØ® " + commentClass.getSignedIn()
//							+ " Ø¨ÙˆØ§Ø³Ø·Ø©   "
//							+ this.dataAccessService.getEmployeeRealName(commentClass.getSignedBy()));
//				}
//			} else {
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void refreshInbox() {
//		List<UserMailClass> Mails2 = dataAccessService.findEmployeeInbox(Utils.findCurrentUser().getUserId());
//		mails = new UserMailLazyList(Mails2);
//		List<UserMailClass> Mails3 = this.dataAccessService.findEmployeeOutbox(Utils.findCurrentUser().getUserId());
//		outmails = new UserMailLazyList(Mails3);
//
//	}
//
//	public void refreshInbox(ActionEvent actionEvent) {
//		this.selectedInbox = null;
//		List<UserMailClass> Mails2 = dataAccessService.findEmployeeInbox(Utils.findCurrentUser().getUserId());
//		mails = new UserMailLazyList(Mails2);
//		// long Unreadcnt = Mails2.stream().filter(p -> p.getIsRead() ==
//		// 0).count();
//		// long Readcnt = Mails2.stream().filter(p -> p.getIsRead() ==
//		// 1).count();
//		List<UserMailClass> Mails3 = this.dataAccessService.findEmployeeOutbox(Utils.findCurrentUser().getUserId());
//		outmails = new UserMailLazyList(Mails3);
//
//	}
//
//	/**
//	 * ********DEARCHING ***********
//	 */
//	public String getSearchKey() {
//		return searchKey;
//	}
//
//	public void setSearchKey(String searchKey) {
//		this.searchKey = searchKey;
//	}
//
//	public void searchInbox(ActionEvent actionEvent) {
//		if (this.searchKey.isEmpty()) {
//			MsgEntry.addErrorMessage("حقل البحث خالي ..");
//		}
//		this.selectedInbox = null;
//		String SearchIncluedComment = "N";
//		if (this.includeCommentSearch == true) {
//			SearchIncluedComment = "Y";
//		}
//		List<UserMailClass> Mails2 = dataAccessService.SearchEmployeeInbox(Utils.findCurrentUser().getUserId(), 0,
//				this.searchKey, SearchIncluedComment);
//
//		mails = new UserMailLazyList(Mails2);
//	}
//
//	/**
//	 * ********************************************
//	 */
//	public void onIdle() {
//
//		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
//		FacesContext context = FacesContext.getCurrentInstance();
//		NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
//		navigationHandler.handleNavigation(context, null, "/faces/pages/timeOut.xhtml?faces-redirect=false");
//	}
//
//	public void onIdle1() {
//		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ØªØ­Ø°ÙŠØ± ",
//				"ÙŠØ¨Ø¯Ùˆ Ù„Ù†Ø§ Ø§Ù†Ùƒ ØªÙˆÙ‚Ù�Øª Ø¹Ù† Ø§Ø³ØªØ®Ø¯Ø§Ù… Ø§Ù„Ù†Ø¸Ø§Ù…  Ùˆ Ù�ÙŠ Ø­Ø§Ù„Ø© Ø§Ù„Ø§Ø³ØªÙ…Ø±Ø§Ø± Ø³ÙŠØªÙ… Ø§Ù„Ø®Ø±ÙˆØ¬ Ù…Ù† Ø§Ù„Ù†Ø¸Ø§Ù… ØªÙ„Ù‚Ø§Ø¦ÙŠØ§ Ùˆ Ø°Ù„Ùƒ Ù„Ù„Ø­Ù�Ø§Ø¸ Ø¹Ù„ÙŠ Ø³Ø±ÙŠØ© Ù…Ø¹Ù„ÙˆÙ…Ø§ØªÙƒ "));
//	}
//
//	public void archiveInboxRecord(ActionEvent actionEvent) {
//
//	}
//
//	/* oubox Action Logic **/
//	List<WrkCommentsClass> outboxComments;
//
//	public List<WrkCommentsClass> getOutboxComments() {
//		return outboxComments;
//	}
//
//	public void setOutboxComments(List<WrkCommentsClass> outboxComments) {
//		this.outboxComments = outboxComments;
//	}
//
//	public void LoadOutboxComments() {
//		this.outboxComments = this.dataAccessService.findCommentsByWrkId(WrkCommTxt);
//	}
//
//	public void outboxSelectRow(SelectEvent selectEvent) {
//		UserMailClass outboxmail = (UserMailClass) selectEvent.getObject();
//
//		this.selectedOutbox = outboxmail;
//		this.outboxComments = this.dataAccessService.findCommentsByWrkId(outboxmail.WrkId);
//		LoadOutbocAttachment();
//
//	}
//
//	public void addToFavourit(ActionEvent actionEvent) {
//		try {
//
//			this.dataAccessService.addRecordToFavourit(this.selectedInbox.WrkId, this.selectedInbox.StepId,
//					Utils.findCurrentUser().getUserId());
//			this.selectedInbox = null;
//			List<UserMailClass> Mails2 = dataAccessService.findEmployeeInbox(Utils.findCurrentUser().getUserId());
//			mails = new UserMailLazyList(Mails2);
//			RequestContext context = RequestContext.getCurrentInstance();
//			context.execute("PF('multiCarDialog').hide()");
//			MsgEntry.addInfoMessage("ØªÙ…Øª Ø¥Ø¶Ø§Ù�Ø© Ø§Ù„Ù…Ø¹Ø§Ù…Ù„Ø© Ø¨Ù†Ø¬Ø§Ø­  ");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void showFavouritinbox(ActionEvent actionEvent) {
//		this.selectedInbox = null;
//
//		List<UserMailClass> Mails2 = dataAccessService.findFavouritInbox(Utils.findCurrentUser().getUserId());
//
//		mails = new UserMailLazyList(Mails2);
//	}
//
//	public void showUnreadInbox(ActionEvent actionEvent) {
//		this.selectedInbox = null;
//
//		List<UserMailClass> Mails2 = dataAccessService.findEmployeeInboxUnread(Utils.findCurrentUser().getUserId());
//		mails = new UserMailLazyList(Mails2);
//	}
//	
//	public void showReadInbox(ActionEvent actionEvent) {
//		this.selectedInbox = null;
//
//		List<UserMailClass> Mails2 = dataAccessService.findEmployeeInboxRead(Utils.findCurrentUser().getUserId());
//		mails = new UserMailLazyList(Mails2);
//	}
//
//	public String markCommentAction(ActionEvent actionEvent) {
//
//		if (this.dataAccessService.WrkAppHasComment(this.selectedInbox.WrkId)) {
//
//			WrkComment wrkComment = (WrkComment) commonDao.findEntityById(WrkComment.class,
//					Integer.parseInt(this.selectedInbox.WrkId));
//			if (wrkComment != null) {
//				wrkComment.setMarkedBy(Utils.findCurrentUser().getUserId());
//				wrkComment.setMarkedIn(HijriCalendarUtil.findCurrentHijriDate());
//				commonDao.update(wrkComment);
//			}
//			WrkApplicationId wrkId = new WrkApplicationId(Integer.parseInt(this.selectedInbox.WrkId),
//					this.selectedInbox.StepId);
//			WrkApplication application = new WrkApplication();
//			application.setArcRecordId(Integer.parseInt(this.selectedInbox.AppId));
//			application.setToUserId(Utils.findCurrentUser().getMgrId());
//			application.setApplicationPurpose(17);
//			application.setApplicationUsercomment("تمت عملية الـتأشير علي الخطاب في "
//					+ HijriCalendarUtil.findCurrentHijriDate() + "  يرجي التوقيع علي الخطاب    ");
//			dataAccessService.redirectRecord(wrkId, application, null,IsSecret);
//			MsgEntry.showModalDialog("تمت عملية التأشير");
//			return "mails";
//		} else {
//			MsgEntry.addErrorMessage("خطأ أثناء التأشير **لا يوجد خطاب ");
//		}
//		return "";
//	}
//
//	public void signCommentActionBtn(ActionEvent actionEvent) {
//		if (this.dataAccessService.EmployeeHasSign(Utils.findCurrentUser().getUserId())) {
//			if (this.dataAccessService.WrkAppHasComment(this.selectedInbox.WrkId)) {
//				this.setCommentSignType("S");
//				RequestContext context = RequestContext.getCurrentInstance();
//				context.execute("PF('confirm-sign-dlg').show()");
//			} else {
//				MsgEntry.addErrorMessage("المدخلات غير صحيحة");
//			}
//		} else {
//			MsgEntry.addErrorMessage("ليس لديك توقيع معتمد بادارة تقنية المعلومات");
//		}
//	}
//
//	public void signCommentByNameActionBtn(ActionEvent actionEvent) {
//		if (this.dataAccessService.EmployeeHasSign(Utils.findCurrentUser().getUserId())) {
//			if (this.dataAccessService.WrkAppHasComment(this.selectedInbox.WrkId)) {
//				this.setCommentSignType("N");
//				RequestContext context = RequestContext.getCurrentInstance();
//				context.execute("PF('confirm-sign-dlg').show()");
//			} else {
//				MsgEntry.addErrorMessage(
//						"Ù„Ø§Ø¨Ø¯ Ù…Ù† ÙˆØ¬ÙˆØ¯ Ø®Ø·Ø§Ø¨ Ùˆ ÙƒÙ„Ù…Ø© Ø³Ø± Ù„Ù„ØªÙˆÙ‚ÙŠØ¹ Ø£ÙˆÙ„Ø§ Ù„ÙƒÙŠ ØªØªÙ… Ø¹Ù…Ù„ÙŠØ© Ø§Ù„ØªØ£Ø´ÙŠØ± ");
//			}
//		} else {
//			MsgEntry.addErrorMessage("Ù„Ø§Ø¨Ø¯ Ù…Ù† ÙˆØ¬ÙˆØ¯ ÙƒÙ„Ù…Ø© Ø³Ø± Ù„Ù„ØªÙˆÙ‚ÙŠØ¹ ");
//		}
//	}
//
//	public void signCommentAmeenActionBtn(ActionEvent actionEvent) {
//		if (this.dataAccessService.EmployeeHasSign(Utils.findCurrentUser().getUserId())) {
//			if (this.dataAccessService.WrkAppHasComment(this.selectedInbox.WrkId)) {
//				this.setCommentSignType("A");
//				RequestContext context = RequestContext.getCurrentInstance();
//				context.execute("PF('confirm-sign-dlg').show()");
//			} else {
//				MsgEntry.addErrorMessage(
//						"Ù„Ø§Ø¨Ø¯ Ù…Ù† ÙˆØ¬ÙˆØ¯ Ø®Ø·Ø§Ø¨ Ùˆ ÙƒÙ„Ù…Ø© Ø³Ø± Ù„Ù„ØªÙˆÙ‚ÙŠØ¹ Ø£ÙˆÙ„Ø§ Ù„ÙƒÙŠ ØªØªÙ… Ø¹Ù…Ù„ÙŠØ© Ø§Ù„ØªØ£Ø´ÙŠØ± ");
//			}
//		} else {
//			MsgEntry.addErrorMessage("Ù„Ø§Ø¨Ø¯ Ù…Ù† ÙˆØ¬ÙˆØ¯ ÙƒÙ„Ù…Ø© Ø³Ø± Ù„Ù„ØªÙˆÙ‚ÙŠØ¹ ");
//		}
//	}
//
//	public void EditWrkCommentBtnAction(ActionEvent actionEvent) {
//
//		this.isSelectedCommentSigned = false;
//
//	}
//
//	public void LoadOutbocAttachment() {
//		this.outattList = this.dataAccessService.findAttachmentFilesByArcId(this.selectedOutbox.AppId);
//
//	}
//
//	public void retrieveRecordBtnAction(ActionEvent actionEvent) {
//		try {
//			String retstring = this.dataAccessService.retrieveRecord(Utils.findCurrentUser().getUserId(),
//					this.selectedOutbox.AppId, this.selectedOutbox.WrkId);
//			List<UserMailClass> Mails2 = dataAccessService.findEmployeeInbox(Utils.findCurrentUser().getUserId());
//			mails = new UserMailLazyList(Mails2);
//			List<UserMailClass> Mails3 = this.dataAccessService.findEmployeeOutbox(Utils.findCurrentUser().getUserId());
//			outmails = new UserMailLazyList(Mails3);
//
//			if (retstring.length() > 0) {
//				MsgEntry.addErrorMessage(retstring);
//			}
//			if (retstring.length() == 0 || retstring == null) {
//				RequestContext context = RequestContext.getCurrentInstance();
//				context.execute("PF('outboxDlg').hide()");
//				MsgEntry.addInfoMessage("ØªÙ… Ø£Ø³ØªØ±Ø¬Ø§Ø¹ Ø§Ù„Ù…Ø¹Ø§Ù…Ù„Ø© Ø¨Ù†Ø¬Ø§Ø­");
//			}
//
//		} catch (Exception e) {
//			MsgEntry.addErrorMessage("Ø­Ø¯Ø« Ø®Ø·Ø£ Ù�ÙŠ Ø§Ø³ØªØ±Ø¬Ø§Ø¹ Ø§Ù„Ù…Ø¹Ø§Ù…Ù„Ø© ");
//		}
//	}
//
//	/**
//	 * *********** calling Jasper report *******************
//	 */
//	private List<String> recievecopyList;
//
//	public List<String> getRecievecopyList() {
//		return recievecopyList;
//	}
//
//	public void setRecievecopyList(List<String> recievecopyList) {
//		this.recievecopyList = recievecopyList;
//	}
//
//	// public void SendInternalRecordBtnAction(ActionEvent actionEvent) {
//	// this.recievecopyList = null;
//	// this.recievecopyList = new ArrayList<>();
//	// int isImportantRecord = 0;
//	// if (!this.NewRecordIsImportant) {
//	// } else {
//	// isImportantRecord = 1;
//	// }
//	// int IsSecret = 0;
//	// if (this.NewRecordIsSecret) {
//	// IsSecret = 1;
//	// }
//	// String createIncomeNo = "N";
//	// if (!this.NewRecordCreateInocmeYN) {
//	// } else {
//	// createIncomeNo = "Y";
//	// }
//	// if (this.NewRecordTitle == null || this.NewRecordTitle.length() == 0 ||
//	// Utils.findCurrentUser().getUserId() == 0
//	// || this.NewRecordReciever == 0 || this.NewRecordComm == null ||
//	// this.NewRecordComm.length() == 0) {
//	// MsgEntry.addErrorMessage("ØªØ£ÙƒØ¯ Ù…Ù† Ø¬Ù‡Ø§Øª Ø¨ÙŠØ§Ù†Ø§Øª
//	// Ø§Ù„Ø§Ø±Ø³Ø§Ù„ Ø£ÙˆÙ„Ø§ ");
//	//
//	// } else {
//	// this.NewRecordId =
//	// this.dataAccessService.SendNewRecord(this.NewRecordTitle,
//	// isImportantRecord,
//	// Utils.findCurrentUser().getUserId(), this.NewRecordReciever,
//	// this.NewRecordComm, this.NewRecordPurp,
//	// IsSecret, createIncomeNo);
//	// this.recievecopyList.add(this.NewRecordId);
//	//
//	// // sending the copies ...
//	// //
//	// try {
//	// if (this.NewRecordEmpCopyReciever != null) {
//	// for (Object empid : this.NewRecordEmpCopyReciever) {
//	// int EmployeeId = Integer.parseInt(String.valueOf(empid));
//	//
//	// String nn = this.dataAccessService.SendNewRecordcopy(this.NewRecordTitle,
//	// isImportantRecord,
//	// Utils.findCurrentUser().getUserId(), EmployeeId, this.NewRecordComm,
//	// this.NewRecordPurp,
//	// IsSecret, "", this.NewRecordId);
//	// this.recievecopyList.add(nn);
//	// // TODO
//	// // this.da.sendRecordSingleCopy(this.selectedInbox.AppId,
//	// // this.selectedInbox.WrkId,
//	// // this.da.findCurrentUser().getUserId(), EmployeeId,
//	// // "", this.WrkPurp, this.WrkCommTxt,
//	// // this.selectedInbox.AppTitle,
//	// // this.selectedInbox.StepId);
//	// }
//	// }
//	// if (this.NewRecordMngCopyReciever != null) {
//	// for (Object mgrid : this.NewRecordMngCopyReciever) {
//	// int _ManagerId = Integer.parseInt(String.valueOf(mgrid));
//	// String mm = this.dataAccessService.SendNewRecordcopy(this.NewRecordTitle,
//	// isImportantRecord,
//	// Utils.findCurrentUser().getUserId(), _ManagerId, this.NewRecordComm,
//	// this.NewRecordPurp,
//	// IsSecret, "", this.NewRecordId);
//	// this.recievecopyList.add(mm);
//	//
//	// // TODO
//	// // this.da.sendRecordSingleCopy(this.selectedInbox.AppId,
//	// // this.selectedInbox.WrkId,
//	// // this.da.findCurrentUser().getUserId(), _ManagerId,
//	// // "", this.WrkPurp, this.WrkCommTxt,
//	// // this.selectedInbox.AppTitle,
//	// // this.selectedInbox.StepId);
//	// }
//	// }
//	// RequestContext.getCurrentInstance().execute("PF('NewRecordUploader').upload()");
//	// // System.err.println("-------->" +
//	// // this.recievecopyList.size());
//	// } catch (Exception e) {
//	// e.printStackTrace();
//	// }
//	//
//	// this.NewRecordTitle = null;
//	// this.NewRecordPurp = 0;
//	// this.NewRecordEmpCopyReciever = null;
//	// this.NewRecordEmpReciever = 0;
//	// this.NewRecordReciever = 0;
//	// this.NewRecordIsSecret = false;
//	// this.NewRecordCreateInocmeYN = false;
//	// this.NewRecordIsImportant = false;
//	//
//	// List<UserMailClass> Mails2 =
//	// dataAccessService.findEmployeeInbox(Utils.findCurrentUser().getUserId());
//	// mails = new UserMailLazyList(Mails2);
//	// List<UserMailClass> Mails3 =
//	// this.dataAccessService.findEmployeeOutbox(Utils.findCurrentUser().getUserId());
//	// outmails = new UserMailLazyList(Mails3);
//	// Utils.closeDialog("NewRecordDlg");
//	// MsgEntry.showModalDialog("ØªÙ… Ø¥Ø±Ø³Ø§Ù„ Ø§Ù„Ù…Ø°ÙƒØ±Ø© Ø§Ù„Ø¯Ø§Ø®Ù„ÙŠØ©
//	// Ø¨Ù†Ø¬Ø§Ø­");
//	// }
//	//
//	// }
//
//	public void NewRecordupload(FileUploadEvent event) {
//
//		try {
//
//			String attachaId = this.dataAccessService.transferAttachment(event.getFile().getInputstream(),
//					event.getFile().getFileName(), Utils.findCurrentUser().getUserId(), this.NewRecordId);
//
//			for (String x : this.recievecopyList) {
//				this.dataAccessService.addExistedAttachToRecord(attachaId, x);
//				// System.err.println("NNNNNNNNNN-->" + attachaId + " : " + x);
//			}
//		} catch (IOException e) {
//
//		}
//
//	}
//
//	public void newRecordEmpRecieverlistChange(ValueChangeEvent valueChangeEvent) {
//		this.NewRecordMgrReciever = 0;
//		this.selectedEmpfromAllUser = 0;
//		this.NewRecordReciever = this.NewRecordEmpReciever;
//	}
//
//	public void newRecordMgrRecieverlistChange(ValueChangeEvent valueChangeEvent) {
//		this.NewRecordEmpReciever = 0;
//		this.selectedEmpfromAllUser = 0;
//		this.NewRecordReciever = this.NewRecordMgrReciever;
//
//	}
//
//	public void AllUserInternalList(AjaxBehaviorEvent valueChangeEvent) {
//
//		this.NewRecordEmpReciever = 0;
//		this.NewRecordMgrReciever = 0;
//		this.NewRecordReciever = this.selectedEmpfromAllUser;
//
//	}
//
//	public List<String> complete(String x) {
//		return this.dataAccessService.findAllWrkCommentAppTo(x);
//	}
//
//	public void returnBtnAction(ActionEvent actionEvent) {
//		List<UserMailClass> Mails2 = dataAccessService.findEmployeeInbox(Utils.findCurrentUser().getUserId());
//		mails = new UserMailLazyList(Mails2);
//
//		String page = "userMail.xhtml";
//
//		try {
//			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		Utils.closeDialog("multiCarDialog");
//
//	}
//
//	public void ShowCommentsReport(ActionEvent actionEvent) throws IOException {
//		this.CurrentRecordComments = "http://oraserver.bkeryah.gov.sa/reports/rwservlet?destype=cache&desformat=pdf&report=d:\\archiving\\reports\\r04.rdf&userid=reports/reports@orcl&server=Bkeryah01&P1="
//				+ this.selectedInbox.WrkId;
//
//	}
//
//	public void uploadRecordFiles(FileUploadEvent event) {
//		try {
//			AttachmentModel attach = new AttachmentModel();
//			attach.setAttachRealName(event.getFile().getFileName());
//			attach.setAttachSize(event.getFile().getSize());
//			attach.setAttachStream(event.getFile().getInputstream());
//			attach.setAttachExt(FilenameUtils.getExtension(event.getFile().getFileName()));
//
//			ArcAttach arcAttach = new ArcAttach();
//			String name = Utils.generateRandomName() + "." + attach.getAttachExt();
//			arcAttach.setAttachName(name);
//			arcAttach.setAttachOwner(Utils.findCurrentUser().getUserId());
//			arcAttach.setAttachDate(new Date());
//			arcAttach.setAttachSize((double) attach.getAttachSize());
//			arcAttach.setAttachType(1);
//			arcAttach.setAttachCategory("FILE");
//			try {
//
//				Utils.saveAttachments(attach.getAttachStream(), name);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			int attId = dataAccessService.save(arcAttach);
//
//			ArcRecAttId arcRecAttId = new ArcRecAttId();
//			arcRecAttId.setAttachId(attId);
//			arcRecAttId.setRecordId(Integer.parseInt(this.selectedInbox.getAppId()));
//			ArcRecAtt arcRecAtt = new ArcRecAtt();
//			arcRecAtt.setId(arcRecAttId);
//			dataAccessService.saveObject(arcRecAtt);
//			LoadAttachmentByAppId();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		try {
//			// this.dataAccessService.transferAttachment(event.getFile().getInputstream(),
//			// event.getFile().getFileName(),
//			// Utils.findCurrentUser().getUserId(),
//			// this.selectedInbox.getAppId());
//			// LoadAttachmentByAppId();
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}
//	}
//
//	public void NavigateToOutBox(ActionEvent actionEvent) {
//		FacesContext context = FacesContext.getCurrentInstance();
//		NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
//		navigationHandler.handleNavigation(context, null, "/faces/pages/outBoxMail.xhtml?faces-redirect=false");
//	}
//
//	public void chnageSenderListBox(AjaxBehaviorEvent valueChangeEvent) {
//		this.selectedInbox = null;
//
//		List<UserMailClass> Mails2 = dataAccessService.findEmployeeInboxBySenderId(Utils.findCurrentUser().getUserId(),
//				this.SelectedSender);
//		mails = new UserMailLazyList(Mails2);
//
//	}
//
//	public void ShowDailyInbox(ActionEvent actionEvent) {
//		this.selectedInbox = null;
//		this.SelectedSender = 0;
//		List<UserMailClass> Mails2 = dataAccessService.findEmployeeInboxDaily(Utils.findCurrentUser().getUserId());
//		mails = new UserMailLazyList(Mails2);
//	}
//
//	public void showArchivedOutBox(ActionEvent actionEvent) {
//		List<UserMailClass> Mails3 = this.dataAccessService
//				.findEmployeeOutboxArchive(Utils.findCurrentUser().getUserId());
//		outmails = new UserMailLazyList(Mails3);
//	}
//
//	public void showOutBox(ActionEvent actionEvent) {
//		List<UserMailClass> Mails3 = this.dataAccessService.findEmployeeOutbox(Utils.findCurrentUser().getUserId());
//		outmails = new UserMailLazyList(Mails3);
//	}
//
//	public void SearchOutboxMail(ActionEvent actionEvent) {
//		String SrchTyp = "N";
//		if (this.OutboxSearchIncludearchive) {
//			SrchTyp = "Y";
//		}
//		List<UserMailClass> Mails3 = this.dataAccessService.searchEmployeeOutbox(Utils.findCurrentUser().getUserId(),
//				this.OutboxSearchKey, SrchTyp);
//		outmails = new UserMailLazyList(Mails3);
//	}
//
//	public void refreshBoxCount(ActionEvent actionEvent) {
//		List<UserMailClass> Mails2 = this.dataAccessService.findEmployeeInbox(Utils.findCurrentUser().getUserId());
//		// this.CurrentUnreadInboxCount = (int) Mails2.stream().filter(p ->
//		// p.getIsRead() == 0).count();
//		// this.CurrentReadInboxCount = (int) Mails2.stream().filter(p ->
//		// p.getIsRead() == 1).count();
//		this.CurrentInboxCount = Mails2.size();
//		List<UserMailClass> Mails3 = this.dataAccessService.findEmployeeOutbox(Utils.findCurrentUser().getUserId());
//		this.CurrentOutboxCount = Mails3.size();
//		this.CurrentArchivedOutboxCount = this.dataAccessService
//				.findEmployeeOutboxArchive(Utils.findCurrentUser().getUserId()).size();
//	}
//
//	public void chnageUserFolderListBox(AjaxBehaviorEvent valueChangeEvent) {
//		this.selectedInbox = null;
//
//		List<UserMailClass> Mails2 = dataAccessService.findEmployeeFolderRecords(Utils.findCurrentUser().getUserId(),
//				this.SelectedUserFolder);
//		mails = new UserMailLazyList(Mails2);
//
//	}
//
//	public void changetargetFolder(AjaxBehaviorEvent abe) {
//
//		String x = this.dataAccessService.MoveRecordToFolder(this.selectedInbox.WrkId, this.selectedInbox.StepId,
//				Utils.findCurrentUser().getUserId(), Integer.parseInt(this.NewRecordTargetFolder));
//		MsgEntry.addInfoMessage(x);
//
//	}
//
//	public void MultipleSelectInboxAction(ActionEvent actionEvent) {
//		this.showDeptEmployees = false;
//		if (this.selectedInboxs.size() > 0) {
//			RequestContext context = RequestContext.getCurrentInstance();
//			context.execute("PF('mselectdlg').show()");
//
//		} else {
//			MsgEntry.addErrorMessage(
//					"\u064a\u062c\u0628 \u062a\u062d\u062f\u064a\u062f \u0645\u0639\u0627\u0645\u0644\u0629 \u0639\u0644\u064a \u0627\u0644\u0623\u0642\u0644");
//		}
//	}
//
//	public void changeSummaryAction(AjaxBehaviorEvent abe) {
//		if (this.selectSummAction.equalsIgnoreCase("A")) {
//			this.showDeptEmployees = false;
//		} else {
//			this.showDeptEmployees = true;
//		}
//	}
//
//	public void MultiselectBtnAction(ActionEvent actionEvent) {
//		// System.err.println(" "+this.selectedInboxs.size()+" --
//		// "+this.selectSummAction+""+this.selectEmployeeForSummaryAction);
//		if (this.selectSummAction.equalsIgnoreCase("A")) {
//			for (UserMailClass obj : selectedInboxs) {
//				dataAccessService.wrkTransferApplication(obj.WrkId, obj.AppId, Utils.findCurrentUser().getUserId(), 85,
//						"", 9);
//
//				List<UserMailClass> Mails2 = dataAccessService.findEmployeeInbox(Utils.findCurrentUser().getUserId());
//				mails = new UserMailLazyList(Mails2);
//				List<UserMailClass> Mails3 = this.dataAccessService
//						.findEmployeeOutbox(Utils.findCurrentUser().getUserId());
//				outmails = new UserMailLazyList(Mails3);
//				Utils.closeDialog("mselectdlg");
//				MsgEntry.showModalDialog("ØªÙ…Øª Ø¹Ù…Ù„ÙŠÙ‡ Ø§Ù„Ø£Ø±Ø´Ù�Ø© Ø¨Ù†Ø¬Ø§Ø­");
//
//			}
//		} else if (this.selectSummAction.equalsIgnoreCase("B")) {
//			if (Integer.parseInt(this.selectEmployeeForSummaryAction) > 0) {
//				for (UserMailClass obj : selectedInboxs) {
//					dataAccessService.wrkTransferApplication(obj.WrkId, obj.AppId, Utils.findCurrentUser().getUserId(),
//							Integer.parseInt(this.selectEmployeeForSummaryAction), "", 19);
//					List<UserMailClass> Mails2 = dataAccessService
//							.findEmployeeInbox(Utils.findCurrentUser().getUserId());
//					mails = new UserMailLazyList(Mails2);
//					List<UserMailClass> Mails3 = this.dataAccessService
//							.findEmployeeOutbox(Utils.findCurrentUser().getUserId());
//					outmails = new UserMailLazyList(Mails3);
//					Utils.closeDialog("mselectdlg");
//					MsgEntry.showModalDialog("ØªÙ…Øª Ø¹Ù…Ù„ÙŠÙ‡ Ø§Ù„Ø§Ø­Ø§Ù„Ø© Ø§Ù„Ù…ØªØ¹Ø¯Ø¯Ø© Ø¨Ù†Ø¬Ø§Ø­");
//				}
//			} else {
//				MsgEntry.addErrorMessage(
//						"Ù‚Ù… Ø¨Ø§Ø®ØªÙŠØ§Ø± Ø§Ù„Ù…ÙˆØ¸Ù� Ø£ÙˆÙ„Ø§ Ù‚Ø¨Ù„ Ø§ØªÙ…Ø§Ù… Ø¹Ù…Ù„ÙŠØ© Ø§Ù„Ø¥Ø±Ø³Ø§Ù„");
//			}
//		}
//
//	}
//
//	public void showCommentTab(AjaxBehaviorEvent event) {
//		recordHasComment = true;
//		InboxDtActiveIndex = 4;
//
//	}
//
//	public IDataAccessService getDataAccessService() {
//		return dataAccessService;
//	}
//
//	public void setDataAccessService(IDataAccessService dataAccessService) {
//		this.dataAccessService = dataAccessService;
//	}
//
//	public String getUnreadInboxCount() {
//		return Utils.convertTextWithArNumric("" + CurrentUnreadInboxCount);
//	}
//
//	public String getReadInboxCount() {
//		return Utils.convertTextWithArNumric("" + CurrentReadInboxCount);
//	}
//
//	public String getInboxCount() {
//		return Utils.convertTextWithArNumric("" + CurrentInboxCount);
//	}
//
//	public String convertText(String param) {
//		return Utils.convertTextWithArNumric(param);
//	}
//
//	public String getArchivedOutboxCount() {
//		return Utils.convertTextWithArNumric("" + CurrentArchivedOutboxCount);
//	}
//
//	public String getOutboxCount() {
//		return Utils.convertTextWithArNumric("" + CurrentOutboxCount);
//	}
//
//	public List<UserMailClass> getFilteredInbox() {
//		return filteredInbox;
//	}
//
//	public void setFilteredInbox(List<UserMailClass> filteredInbox) {
//		this.filteredInbox = filteredInbox;
//	}
//
//	public String getSelectedInboxsSize() {
//		return Utils.convertTextWithArNumric("" + selectedInboxs.size());
//	}
//
//	public boolean isRecordHasComment() {
//		return recordHasComment;
//	}
//
//	public void setRecordHasComment(boolean recordHasComment) {
//		this.recordHasComment = recordHasComment;
//	}
//
//	public ICommonDao getCommonDao() {
//		return commonDao;
//	}
//
//	public void setCommonDao(ICommonDao commonDao) {
//		this.commonDao = commonDao;
//	}
//
//	public boolean isManager() {
//		return isManager;
//	}
//
//	public void setManager(boolean isManager) {
//		this.isManager = isManager;
//	}
//
//	public boolean isSelectedCommentSigned() {
//		return isSelectedCommentSigned;
//	}
//
//	public void setSelectedCommentSigned(boolean isSelectedCommentSigned) {
//		this.isSelectedCommentSigned = isSelectedCommentSigned;
//	}
//
//	public MailExecutor getMail() {
//		return mail;
//	}
//
//	public void setMail(MailExecutor mail) {
//		this.mail = mail;
//	}
//
//	public String getCommentPrvLink() {
//		return CommentPrvLink;
//	}
//
//	public MainManagedBean getMainManagedBean() {
//		return mainManagedBean;
//	}
//
//	public void setMails(LazyDataModel<UserMailClass> mails) {
//		this.mails = mails;
//	}
//
//	public final void onTabChange(final TabChangeEvent event) {
//		TabView tv = (TabView) event.getComponent();
//		// MsgEntry.addErrorMessage(tv.getActiveIndex() + "");
//
//
//		if (tv.getActiveIndex() == 1) {
//			if (isTheFirst) {
//				isTheFirst = false;
//				ArcUsers user = Utils.findCurrentUser();
//				List<UserMailClass> mails = this.dataAccessService.findEmployeeOutbox(user.getUserId());
//				this.CurrentOutboxCount = mails.size();
//				outmails = new UserMailLazyList(mails);
//			}
//		}
//	}
//
//}

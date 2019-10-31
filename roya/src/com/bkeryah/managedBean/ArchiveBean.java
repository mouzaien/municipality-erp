package com.bkeryah.managedBean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.TreeNode;

import com.bkeryah.bean.ArcAttachmentClass;
import com.bkeryah.bean.UserContactClass;
import com.bkeryah.bean.WrkCommentsClass;
import com.bkeryah.bean.WrkLetterFromClass;
import com.bkeryah.bean.WrkPurposeClass;
import com.bkeryah.entities.ArcAttach;
import com.bkeryah.entities.ArcDocumentStruct;
import com.bkeryah.entities.ArcRecAtt;
import com.bkeryah.entities.ArcRecAttId;
import com.bkeryah.entities.ArcRecords;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.entities.WrkComment;
import com.bkeryah.model.AttachmentModel;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.shared.beans.Scanner;

import utilities.FtpTransferFile;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@SessionScoped
public class ArchiveBean extends Scanner implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;

	private String subject;
	private String letter;
	private String letterFromNo;
	private Integer recordSender;
	private Integer incomeNB;
	private Long mobileNB;
	private Map<String, String> letterFromMap;
	private List<ArcRecords> recordsList;
	private List<ArcRecords> filteredRecordsList;
	private TreeNode root;
	private TreeNode[] selectedNodes;
	private Map<ArcDocumentStruct, List<ArcDocumentStruct>> sectionsMap;
	private List<ArcDocumentStruct> sectionsList = new ArrayList<ArcDocumentStruct>();
	// private List<ArcDocumentStruct> selectedSectionsList;
	private List<String> selectedStructIdList;
	private ArcRecords selectedArcRecord;
	private List<WrkCommentsClass> comments = new ArrayList<WrkCommentsClass>();
	private List<ArcAttachmentClass> attList = new ArrayList<ArcAttachmentClass>();
	private WrkApplication application = new WrkApplication();
	private Map<String, String> purposeMap = new LinkedHashMap<String, String>();
	private boolean importantFlag;
	private boolean secretFlag;
	private boolean managerFlag;
	private int wrkReciever;
	private Map<String, String> empsMapInDept = new LinkedHashMap<String, String>();
	private Map<String, String> mgrMap = new LinkedHashMap<String, String>();
	private List<Integer> WrkCopyEmpRecievers = new ArrayList<Integer>();
	private List<Integer> WrkCopyMngRecievers = new ArrayList<Integer>();
	private String reportServerIp;
	private WrkApplication lastWrkApp;
	private boolean recordHasComment;
	private WrkComment comment;
	private List<User> employeesList = new ArrayList<>();
	private int selectedUserId;
	private boolean enableSubject;
	List<AttachmentModel> attachList = new ArrayList<AttachmentModel>();
	private List<ArcAttach> attachs = new ArrayList<ArcAttach>();
	protected static final Logger logger = Logger.getLogger(Scanner.class);
	ArcUsers currentUser = Utils.findCurrentUser();

	private Integer tabActiveIndex = 0;

	public void loadData() {
		enableSubject = false;
		if (currentUser.getWrkRoleId().equals(2) && currentUser.getDeptId().equals(MyConstants.TT_DEPT_ID)) {
			enableSubject = true;
		}
		getViewEmployers();
		resetFields();
		List<WrkLetterFromClass> list = this.dataAccessService.findAllWrkLetterFroms();
		letterFromMap = new LinkedHashMap<String, String>();
		for (WrkLetterFromClass item : list) {
			letterFromMap.put(item.getWrkLetterFromName(), String.valueOf(item.getWrkLetterfromId()));
		}
		if (currentUser.getWrkRoleId() == 1 || currentUser.getWrkSectionId() == MyConstants.TT_DEPT_ID) {
			sectionsList = dataAccessService.loadArcDocumentStructList();

		} else {
			ArcDocumentStruct userSection = (ArcDocumentStruct) dataAccessService
					.findEntityById(ArcDocumentStruct.class, currentUser.getWrkSection().getId());
			sectionsList.clear();
			sectionsList.add(userSection);
		}

		// populateTree();
		// root = createTree();
		selectedStructIdList = new ArrayList<>();
		List<WrkPurposeClass> listOfPurposes = dataAccessService.findAllWrkPurpose(currentUser.getUserId());

		for (WrkPurposeClass item : listOfPurposes) {
			purposeMap.put(item.getPurpName(), String.valueOf(item.getPurpId()));
		}

		List<UserContactClass> listOfEmpsInDept = dataAccessService.findAllEmployeesInDept(currentUser.getUserId());

		for (UserContactClass item : listOfEmpsInDept) {
			empsMapInDept.put(item.getUserName(), String.valueOf(item.getUserId()));
		}

		List<UserContactClass> listOfManagers = dataAccessService.findAllManagers(currentUser.getUserId());

		for (UserContactClass item : listOfManagers) {
			mgrMap.put(item.getUserName(), String.valueOf(item.getUserId()));
		}

		try {
			setManagerFlag(dataAccessService.signedIsAutorized(currentUser.getUserId(), 78));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getViewEmployers() {
		if (currentUser.getWrkRoleId() == 1 || currentUser.getUserId() == MyConstants.SUPPORT_USER_ID
				|| currentUser.getUserId() == MyConstants.TT_DIRECTOR_ID)
			employeesList = dataAccessService.getAllUsers();
		else if (currentUser.getWrkRoleId() == 2) {
			employeesList = dataAccessService.getAllEmployeesInDept(currentUser.getDeptId());
		}
	}

	public void savePdfComment() {
		String pdfComment = Utils.savePdfWrkComment(comment);
		if (pdfComment.length() > 0) {
			Integer appId = comment.getAppId();
			WrkComment comment = (WrkComment) dataAccessService.findEntityById(WrkComment.class, appId);
			comment.setPdfComment(pdfComment);
			dataAccessService.updateObject(comment);
		}
	}

	private ArcDocumentStruct getUserSection(int sectionId) {
		for (ArcDocumentStruct section : sectionsMap.keySet()) {
			if (section == null)
				continue;
			if (section.getStructId() == sectionId)
				return section;
		}
		return (ArcDocumentStruct) dataAccessService.findEntityById(ArcDocumentStruct.class, sectionId);
	}

	public void searchArcRecords() {
		boolean employer = false;
		employer = (currentUser.getWrkRoleId() == 1) || (currentUser.getWrkSectionId() == MyConstants.TT_DEPT_ID);
		if (((letterFromNo == null) || (letterFromNo.trim().equals("")))
				&& ((subject == null) || (subject.trim().equals("")))
				&& ((letter == null) || (letter.trim().equals(""))) && ((recordSender == null) || (recordSender == 0))
				&& ((incomeNB == null) || (incomeNB == 0)) && ((mobileNB == null) || (mobileNB == 0))
				&& ((selectedStructIdList == null) || (selectedStructIdList.isEmpty()))) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.select.one.search"));
			recordsList = null;
			return;
		}
		List<Integer> deptIdList = new ArrayList<Integer>();

		for (String ele : selectedStructIdList) {
			deptIdList.add(new Integer(ele));
		}
		if (deptIdList.size() == 0)
			deptIdList.add(currentUser.getWrkSectionId());
		recordsList = dataAccessService.searchArcRecords(subject, letter, recordSender, incomeNB, letterFromNo,
				deptIdList, employer);
	}

	public String updateSubjectAction() {
		dataAccessService.updateObject(selectedArcRecord);
		MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.update.subject"));
		return "archive";
	}

	public String viewRowData(ArcRecords archive) {
		selectedArcRecord = archive;
		List<WrkApplication> wrkAppList = dataAccessService.getWrkApplicationListByRecordId(selectedArcRecord.getId());
		lastWrkApp = wrkAppList.get(wrkAppList.size() - 1);
		loadcomments();
		loadAttachmentByAppId();
		setComment(
				(WrkComment) dataAccessService.findEntityById(WrkComment.class, lastWrkApp.getId().getApplicationId()));
		if (comment != null)
			setRecordHasComment(true);
		return "archive_details";
	}

	public List<ArcRecords> getFilteredRecordsList() {
		return filteredRecordsList;
	}

	public void setFilteredRecordsList(List<ArcRecords> filteredRecordsList) {
		this.filteredRecordsList = filteredRecordsList;
	}

	public String retrieveRecordBtnAction() {
		try {
			if (lastWrkApp.getApplicationIsRead() != 1) {
				dataAccessService.retrieveRecord(currentUser.getUserId(), "" + selectedArcRecord.getId(),
						"" + lastWrkApp.getId().getApplicationId());
				return "mails";
			} else {
				MsgEntry.addErrorMessage("لا يمكن استرجاع المعاملة ");
				return "";
			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage("Error msg");
			return "";
		}
	}

	private void resetFields() {
		recordsList = null;
		subject = null;
		letter = null;
		incomeNB = null;
		recordSender = null;
		mobileNB = null;
		selectedNodes = null;
	}

	public void loadcomments() {
		setComments(dataAccessService.findCommentsByWrkId("" + lastWrkApp.getId().getApplicationId()));
	}

	public void loadAttachmentByAppId() {
		setAttList(dataAccessService.findAttachmentFilesByArcId("" + selectedArcRecord.getId()));

	}

	/**
	 * Print letter report
	 */
	public String printReportAction() {
		String reportName = "/reports/letter2.jasper";
		Map<String, Object> parameters = populateLetterParameters("" + lastWrkApp.getId().getApplicationId(),
				comment.getFontSize(), 0);
		// Map<String, Object> parameters = new HashMap<String, Object>();
		// parameters.put("p1", lastWrkApp.getId().getApplicationId());//
		// "259306";
		// parameters.put("textSize", comment.getFontSize());
		// parameters.put("copy", 0);
		// parameters.put("SUBREPORT_DIR",
		// FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/watan.jpg"));
		// Utils.printPdfReport(reportName, parameters);
		List<WrkComment> comms = new ArrayList<WrkComment>();
		comms.add(comment);
		Utils.printPdfReportFromListDataSource(reportName, parameters, comms);
		return "";
	}

	private Map<String, Object> populateLetterParameters(String wrkId, Integer fontSize, Integer copy) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("textSize", fontSize);
		parameters.put("copy", copy);
		parameters.put("SUBREPORT_DIR",
				FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/watan.jpg"));
		dataAccessService.loadLetterReportParameters(parameters, wrkId);
		return parameters;
	}

	public void clearMngRadioList(ValueChangeEvent valueChangeEvent) {
		application.setToUserId(wrkReciever);
	}

	public void clearEmpRadioList(ValueChangeEvent valueChangeEvent) {
		application.setToUserId(wrkReciever);
	}

	public String wrkSendAction() {

		if (application.getToUserId() == null || application.getToUserId() == 0) {
			MsgEntry.addErrorMessage("يجب اختيار جهة ارسال الاصل  ");
		} else {

			WrkApplicationId wrkId = new WrkApplicationId(this.selectedArcRecord.getId(),
					dataAccessService.findStepId(selectedArcRecord.getId()));

			application.setArcRecordId(selectedArcRecord.getId());

			try {
				dataAccessService.sendWrkApplication(wrkId, application, secretFlag, WrkCopyEmpRecievers,
						WrkCopyMngRecievers);

			} catch (Exception e) {
				e.printStackTrace();

			}

			secretFlag = false;
			importantFlag = false;

			WrkCopyEmpRecievers = null;
			WrkCopyMngRecievers = null;
			wrkReciever = 0;
			MsgEntry.showModalDialog("تمت الاحالة بنجاح");
			return "archive";
		}
		return "";
	}

	/**
	 * Print letter report
	 */
	public String printExplanationReportAction() {
		String reportName = "/reports/rep001.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p1", "" + lastWrkApp.getId().getApplicationId());
		parameters.put("wrkDate", selectedArcRecord.getRecHDate());
		parameters.put("title", selectedArcRecord.getRecTitle());
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public void deleteAttachment(ArcAttachmentClass attach) {
		ArcRecords arcRec = (ArcRecords) dataAccessService.findEntityById(ArcRecords.class, selectedArcRecord.getId());
		if (arcRec.getFilesNo() != null) {
			arcRec.setFilesNo(arcRec.getFilesNo() - 1);
			dataAccessService.updateObject(arcRec);
		}
		ArcAttach att = new ArcAttach();
		att.setId(Integer.parseInt(attach.getAttId()));
		dataAccessService.deleteObject(att);
		ArcRecAtt arcRecAtt = new ArcRecAtt();
		ArcRecAttId arcRecAttId = new ArcRecAttId();
		arcRecAttId.setAttachId(Integer.parseInt(attach.getAttId()));
		arcRecAttId.setRecordId(selectedArcRecord.getId());
		arcRecAtt.setId(arcRecAttId);
		dataAccessService.deleteObject(arcRecAtt);
		try {
			FtpTransferFile.deleteFile(attach.getAttName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		attList.remove(attach);
	}

	public void tabChanged(TabChangeEvent event) {
		TabView tv = (TabView) event.getComponent();
		tabActiveIndex = tv.getActiveIndex();
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public Integer getRecordSender() {
		return recordSender;
	}

	public void setRecordSender(Integer recordSender) {
		this.recordSender = recordSender;
	}

	public Integer getIncomeNB() {
		return incomeNB;
	}

	public void setIncomeNB(Integer incomeNB) {
		this.incomeNB = incomeNB;
	}

	public Long getMobileNB() {
		return mobileNB;
	}

	public void setMobileNB(Long mobileNB) {
		this.mobileNB = mobileNB;
	}

	public List<ArcRecords> getRecordsList() {
		return recordsList;
	}

	public void setRecordsList(List<ArcRecords> recordsList) {
		this.recordsList = recordsList;
	}

	public Map<String, String> getLetterFromMap() {
		return letterFromMap;
	}

	public void setLetterFromMap(Map<String, String> letterFromMap) {
		this.letterFromMap = letterFromMap;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(TreeNode[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

	public ArcRecords getSelectedArcRecord() {
		return selectedArcRecord;
	}

	public void setSelectedArcRecord(ArcRecords selectedArcRecord) {
		this.selectedArcRecord = selectedArcRecord;
	}

	public List<ArcAttachmentClass> getAttList() {
		return attList;
	}

	public void setAttList(List<ArcAttachmentClass> attList) {
		this.attList = attList;
	}

	public List<WrkCommentsClass> getComments() {
		return comments;
	}

	public void setComments(List<WrkCommentsClass> comments) {
		this.comments = comments;
	}

	public WrkApplication getApplication() {
		return application;
	}

	public void setApplication(WrkApplication application) {
		this.application = application;
	}

	public boolean isImportantFlag() {
		return importantFlag;
	}

	public void setImportantFlag(boolean importantFlag) {
		this.importantFlag = importantFlag;
	}

	public Map<ArcDocumentStruct, List<ArcDocumentStruct>> getSectionsMap() {
		return sectionsMap;
	}

	public void setSectionsMap(Map<ArcDocumentStruct, List<ArcDocumentStruct>> sectionsMap) {
		this.sectionsMap = sectionsMap;
	}

	public List<ArcDocumentStruct> getSectionsList() {
		return sectionsList;
	}

	public void setSectionsList(List<ArcDocumentStruct> sectionsList) {
		this.sectionsList = sectionsList;
	}

	public List<String> getSelectedStructIdList() {
		return selectedStructIdList;
	}

	public void setSelectedStructIdList(List<String> selectedStructIdList) {
		this.selectedStructIdList = selectedStructIdList;
	}

	public Map<String, String> getPurposeMap() {
		return purposeMap;
	}

	public void setPurposeMap(Map<String, String> purposeMap) {
		this.purposeMap = purposeMap;
	}

	public boolean isSecretFlag() {
		return secretFlag;
	}

	public void setSecretFlag(boolean secretFlag) {
		this.secretFlag = secretFlag;
	}

	public boolean isManagerFlag() {
		return managerFlag;
	}

	public void setManagerFlag(boolean managerFlag) {
		this.managerFlag = managerFlag;
	}

	public Map<String, String> getMgrMap() {
		return mgrMap;
	}

	public void setMgrMap(Map<String, String> mgrMap) {
		this.mgrMap = mgrMap;
	}

	public int getWrkReciever() {
		return wrkReciever;
	}

	public void setWrkReciever(int wrkReciever) {
		this.wrkReciever = wrkReciever;
	}

	public Map<String, String> getEmpsMapInDept() {
		return empsMapInDept;
	}

	public void setEmpsMapInDept(Map<String, String> empsMapInDept) {
		this.empsMapInDept = empsMapInDept;
	}

	public List<Integer> getWrkCopyEmpRecievers() {
		return WrkCopyEmpRecievers;
	}

	public void setWrkCopyEmpRecievers(List<Integer> wrkCopyEmpRecievers) {
		WrkCopyEmpRecievers = wrkCopyEmpRecievers;
	}

	public List<Integer> getWrkCopyMngRecievers() {
		return WrkCopyMngRecievers;
	}

	public void setWrkCopyMngRecievers(List<Integer> wrkCopyMngRecievers) {
		WrkCopyMngRecievers = wrkCopyMngRecievers;
	}

	public String getReportServerIp() {
		reportServerIp = dataAccessService.findSystemProperty("REPORT_SERVER_IP");
		return reportServerIp;
	}

	public void setReportServerIp(String reportServerIp) {
		this.reportServerIp = reportServerIp;
	}

	public WrkApplication getLastWrkApp() {
		return lastWrkApp;
	}

	public void setLastWrkApp(WrkApplication lastWrkApp) {
		this.lastWrkApp = lastWrkApp;
	}

	public boolean isRecordHasComment() {
		return recordHasComment;
	}

	public void setRecordHasComment(boolean recordHasComment) {
		this.recordHasComment = recordHasComment;
	}

	public WrkComment getComment() {
		return comment;
	}

	public void setComment(WrkComment comment) {
		this.comment = comment;
	}

	public List<User> getEmployeesList() {
		return employeesList;
	}

	public void setEmployeesList(List<User> employeesList) {
		this.employeesList = employeesList;
	}

	public int getSelectedUserId() {
		return selectedUserId;
	}

	public void setSelectedUserId(int selectedUserId) {
		this.selectedUserId = selectedUserId;
	}

	public String redirectRecord(ActionEvent ae) {
		String result = "archive";
		dataAccessService.retrieveRecordFromArchive(selectedArcRecord.getId(), selectedUserId);
		return result;
	}

	public String getLetterFromNo() {
		return letterFromNo;
	}

	public void setLetterFromNo(String letterFromNo) {
		this.letterFromNo = letterFromNo;
	}

	public boolean isEnableSubject() {
		return enableSubject;
	}

	public void setEnableSubject(boolean enableSubject) {
		this.enableSubject = enableSubject;
	}

	public void NewRecordupload(FileUploadEvent event) {

		try {
			AttachmentModel attach = new AttachmentModel();
			attach.setAttachRealName(event.getFile().getFileName());
			attach.setAttachSize(event.getFile().getSize());
			attach.setAttachStream(event.getFile().getInputstream());
			attach.setAttachExt(FilenameUtils.getExtension(event.getFile().getFileName()));
			attach.setRealName(Utils.generateRandomName() + "." + attach.getAttachExt());

			attachList.add(attach);

		} catch (Exception e) {

		}
	}

	public void saveAttach() {
		attachs = Utils.SaveAttachementsToFtpServer(attachList);
		List<Integer> attachmentIds = dataAccessService.addAttachments(attachs);
		for (Integer id : attachmentIds) {
			ArcRecAtt arcRecAtt = new ArcRecAtt();
			ArcRecAttId arcRecAttId = new ArcRecAttId();
			arcRecAttId.setAttachId(id);
			arcRecAttId.setRecordId(selectedArcRecord.getId());
			arcRecAtt.setId(arcRecAttId);
			dataAccessService.saveObject(arcRecAtt);
			attachList.clear();
			loadAttachmentByAppId();
		}
	}

	public List<AttachmentModel> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<AttachmentModel> attachList) {
		this.attachList = attachList;
	}

	public List<ArcAttach> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<ArcAttach> attachs) {
		this.attachs = attachs;
	}

	public void deleteAttchFile(String deleteAttchFile) {

		try {
			AttachmentModel deletedFile = new AttachmentModel();
			deletedFile.setAttachRealName(deleteAttchFile);
			attachList.remove(deletedFile);
		} catch (Exception e) {
			logger.info("deleteAttchFile " + deleteAttchFile + " :" + e.getMessage());
		}

	}

	public void refreshFilesUploaded(AjaxBehaviorEvent event) {
		try {
			Thread.sleep(2000);
			AttachmentModel attch = Utils.getScannedFile();

			attachList.add(attch);
		} catch (InterruptedException e) {
			logger.info("refreshFilesUploaded  :" + e.getMessage());
		}
	}

	public Integer getTabActiveIndex() {
		return tabActiveIndex;
	}

	public void setTabActiveIndex(Integer tabActiveIndex) {
		this.tabActiveIndex = tabActiveIndex;
	}
}

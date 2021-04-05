package com.bkeryah.stock.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.Article;
import com.bkeryah.entities.HrScenario;
import com.bkeryah.entities.HrsSigns;
import com.bkeryah.entities.ReturnStore;
import com.bkeryah.entities.ReturnStoreDetails;
import com.bkeryah.entities.TransferOwnership;
import com.bkeryah.entities.TransferOwnershipDetails;
import com.bkeryah.entities.WhsWarehouses;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.managedBean.reqfin.newReplaceFinBean;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean(name = "transferBean")
@ViewScoped
public class TransferOwnershipBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private ArcUsers currentUser;
	private Integer employerId;
	private String curUserName;
	private Integer stepNum = new Integer(0);
	private boolean enableAccept;
	private boolean enablePrint;
	private List<WrkPurpose> wrkPurposes;
	private Integer recordId;
	private TransferOwnership transOwnership;
	private TransferOwnership transSave;
	private List<TransferOwnership> transSaveList = new ArrayList<>();
	private List<Article> articleList = new ArrayList<>();
	private String returnHDate;
	private Integer selectedArticleId;
	private String notes;
	private boolean allowAdd = true;
	private boolean hideInputs;
	private Integer serialNum;
	private List<TransferOwnershipModel> transModelList = new ArrayList<>();
	private TransferOwnershipModel transModel;
	private boolean canSave;
	private String wrkAppComment;
	private String applicationPurpose;
	private UserMailObj selectedInbox;
	private WrkApplicationId WrkId;
	private List<TransferOwnershipDetails> tsOnDtls = new ArrayList<>();
	private boolean allowSelect;
	private List<WhsWarehouses> allWareHouses = new ArrayList<WhsWarehouses>();
	private Integer strNo;
	private List<User> employers;

	@PostConstruct
	public void init() {
		transModel = new TransferOwnershipModel();
		transModelList = new ArrayList<TransferOwnershipModel>();
		transOwnership = new TransferOwnership();
		currentUser = Utils.findCurrentUser();

		setAllWareHouses(dataAccessService.getAllStores());
		// employerId = currentUser.getUserId();
		curUserName = currentUser.getEmployeeName();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		String arcRecordId = (String) httpSession.getAttribute("arcRecord");
		httpSession.removeAttribute("arcRecord");
		if (arcRecordId != null) {
			selectedInbox = (UserMailObj) httpSession.getAttribute("selectedMail");
			WrkId = new WrkApplicationId(Integer.parseInt(this.selectedInbox.WrkId), selectedInbox.StepId);
			recordId = Integer.parseInt(arcRecordId.trim());
			transOwnership = dataAccessService.findTransferOwnershipByArchRecordId(recordId);
			wrkPurposes = dataAccessService.getAllPurposes();
			stepNum = dataAccessService.getStepNumberFromHrSign(recordId);
			HrsSigns model = dataAccessService.getHrsSignsByArcId(recordId);
			// employerId = model.getUserId();
			if (transOwnership != null) {
				ArcUsers fromUser = (ArcUsers) dataAccessService.findEntityById(ArcUsers.class,
						transOwnership.getFromUser());
				ArcUsers toUser = (ArcUsers) dataAccessService.findEntityById(ArcUsers.class,
						transOwnership.getToUser());
				transModel.setFromUserId(transOwnership.getFromUser());
				transModel.setFromUserName(fromUser.getEmployeeName());
				Article art = (Article) dataAccessService.findEntityById(Article.class, transOwnership.getArticleId());
				transModel.setArticleCode(transOwnership.getArticleCode());
				transModel.setArticleId(transOwnership.getArticleId());
				transModel.setArticleName(art.getName());
				transModel.setArticleUnit(art.getItemUnite().getName());
				transModel.setNotes(transOwnership.getNotes());
				transModel.setSerialNumber(transOwnership.getSerialNumber());
				transModel.setStrNo(transOwnership.getStrNo());
				transModel.setToUserId(transOwnership.getToUser());
				transModel.setToUserName(toUser.getEmployeeName());
				transModel.setQty(transOwnership.getQty());

				tsOnDtls = dataAccessService.loadTransferOwnerDetails(transOwnership.getId());
				for (TransferOwnershipDetails tsd : tsOnDtls) {
					TransferOwnershipModel transM = new TransferOwnershipModel();
					Article article = (Article) dataAccessService.findEntityById(Article.class, tsd.getArticleId());
					transM.setArticleCode(tsd.getArticleCode());
					transM.setArticleId(tsd.getArticleId());
					transM.setArticleName(article.getName());
					transM.setArticleUnit(article.getItemUnite().getName());
					transM.setNotes(tsd.getNotes());
					transM.setSerialNumber(tsd.getSerialNumber());
					transM.setStrNo(tsd.getStrNo());
					transM.setQty(tsd.getQty());
					transM.setToUserId(transOwnership.getToUser());
					transM.setToUserName(toUser.getEmployeeName());
					transM.setFromUserId(transOwnership.getFromUser());
					transM.setFromUserName(fromUser.getEmployeeName());
					transModelList.add(transM);
				}

			}
		}
		HrScenario scenario = (HrScenario) dataAccessService.findEntityById(HrScenario.class,
				MailTypeEnum.TRANSFER_OWNERSHIP.getValue());

		System.out.println("stepNum >> " + stepNum);
		if (stepNum > 1) {
			hideInputs = true;
		}

		if (stepNum == scenario.getStepsCount() + 1) {
			enableAccept = false;
			enablePrint = true;

		} else {
			enableAccept = true;
			enablePrint = false;

		}

	}

	public void loadEmpAssignedArticles(AjaxBehaviorEvent abe) {
		// articleList = dataAccessService.getArticlesByUserId(employerId);
	}

	public void loadArticlesByStore() {
		if (strNo != null) {
			articleList = dataAccessService.getArticlesByUserIdWithoutCars(currentUser.getUserId());
			employers = new ArrayList<>();
			employers = dataAccessService.getAllEmployeesByManager(currentUser.getUserId());
			if (employers != null && employers.size() > 0) {
				// for dept 3ohad if user is manager
				List<Article> articles = new ArrayList<>();
				articles = dataAccessService.find3ohadByUserId(-1, currentUser.getDeptId());
				articleList.addAll(articles);
			}
			articleList = articleList.stream().filter(i -> i.getStrNo() == strNo).collect(Collectors.toList());
		}

	}

	public void allowAddBtn() {
		// returnHDate != null &&
		if (selectedArticleId != null && employerId != null) {
			System.out.println(">>>ddddddd>>");
			allowAdd = false;
		} else {
			allowAdd = true;
		}
		System.out.println(">>>>>");
		Utils.updateUIComponent("includeform:addBtn");
	}

	public String addItem(AjaxBehaviorEvent event) {
		if (selectedArticleId == null || returnHDate == null || employerId == null) {
			// MsgEntry.addErrorMessage("يجب أدخال البيانات كاملة ");
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("missing.data"));
		} else {
			// if (transModelList.size() > 0) {
			// for (TransferOwnershipModel md : transModelList) {
			// if (md.getArticleId().equals(selectedArticleId)) {
			// //MsgEntry.addErrorMessage("هذا الصنف تم اختياره");
			// MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("art.error"));
			// return "";
			// }
			// }
			// } else {
			for (Article art : articleList) {

				System.out.println("code" + art.getCode());
				if (art.getId() == selectedArticleId) {
					transModel.setArticleCode(art.getCode());
					transModel.setArticleId(art.getId());
					transModel.setArticleUnit(art.getUnitName());
					transModel.setArticleName(art.getName());
					transModel.setStrNo(art.getStrNo());
					transModel.setExchMasterId(art.getExchMasterId());

				}
			}
			try {
				currentUser = Utils.findCurrentUser();
				transModel.setFromUserId(currentUser.getUserId());
				transModel.setFromUserName(currentUser.getEmployeeName());
				ArcUsers toUserDetails = (ArcUsers) dataAccessService.findEntityById(ArcUsers.class, employerId);
				transModel.setToUserId(employerId);
				transModel.setQty(1);
				transModel.setToUserName(toUserDetails.getEmployeeName());
				transModel.setNotes(notes);
				transModel.setSerialNumber(serialNum);
				transModelList.add(transModel);
				canSave = true;
				// Utils.updateUIComponent("includeform:panalSave");

				transModel = new TransferOwnershipModel();

			} catch (Exception ex) {
				System.out.println("exxxxxxxxx");
			}
			// employerId = null;
			// selectedArticleId = null;
			// allowAdd = false;
			if (transModelList.size() < 1) {
				allowSelect = false;
			} else {
				allowSelect = true;
			}
			Utils.updateUIComponent("includeform:itemsTable");
			Utils.updateUIComponent("includeform:empList");
			Utils.updateUIComponent("includeform:hiri1");
		}

		// }
		return "";
	}

	public void removeRecord(TransferOwnershipModel recordItem) {
		transModelList.remove(recordItem);
		if (transModelList.size() < 1) {
			canSave = false;
			Utils.updateUIComponent("includeform:saveBn");
			// Utils.updateUIComponent("includeform:empList");
			// Utils.updateUIComponent("includeform:articles");
		}
		if (transModelList.size() < 1) {
			allowSelect = false;
		} else {
			allowSelect = true;
		}
		Utils.updateUIComponent("includeform:articles");
		Utils.updateUIComponent("includeform:hiri1");
	}

	public String save() {
		if (transModelList.size() > 0) {
			try {
				TransferOwnershipModel item = transModelList.get(0);
				transSave = new TransferOwnership();
				transSave.setArticleId(item.getArticleId());
				transSave.setArticleCode(item.getArticleCode());
				transSave.setStrNo(item.getStrNo());
				transSave.setNotes(item.getNotes());
				transSave.setSerialNumber(item.getSerialNumber());
				transSave.setNotes(item.getNotes());
				transSave.setFromUser(item.getFromUserId());
				transSave.setToUser(item.getToUserId());
				transSave.setTransferHDate(returnHDate);
				transSave.setTransferGDate(Utils.convertHDateToGDate(returnHDate));
				transSave.setArtName(item.getArticleName());
				transSave.setExchMasterId(item.getExchMasterId());
				transSave.setQty(item.getQty());
				transSave.setStatus("R");
				// transSaveList.add(transSave);
				dataAccessService.addTransferOnwerShipItems(transSave, transModelList);
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				return "mails";
			} catch (Exception e) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
				e.printStackTrace();
			}
		}
		return "";
	}

	public String printTransfer() {
		String reportName = "/reports/transferOwnership_Request.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("transId", transOwnership.getId());
		parameters.put("record_id", recordId);
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public String accept() {

		wrkAppComment = Utils.loadMessagesFromFile("accept.operation") + currentUser.getEmployeeName();
		applicationPurpose = "1";
		dataAccessService.acceptTransOwnership(transOwnership, recordId, MailTypeEnum.TRANSFER_OWNERSHIP.getValue(),
				wrkAppComment, Integer.parseInt(applicationPurpose.trim()));
		return "mails";
	}

	public String refuseAction() {
		try {
			applicationPurpose = "2";
			transOwnership.setStatus(MyConstants.NO);
			String wrkCommentRefuse = wrkAppComment;
			dataAccessService.refuseTransOwnership(WrkId, recordId, transOwnership, wrkCommentRefuse,
					Integer.parseInt(applicationPurpose.trim()));
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("refuse.record"));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}

	public ArcUsers getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(ArcUsers currentUser) {
		this.currentUser = currentUser;
	}

	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public String getCurUserName() {
		return curUserName;
	}

	public void setCurUserName(String curUserName) {
		this.curUserName = curUserName;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public Integer getStepNum() {
		return stepNum;
	}

	public void setStepNum(Integer stepNum) {
		this.stepNum = stepNum;
	}

	public boolean isEnableAccept() {
		return enableAccept;
	}

	public void setEnableAccept(boolean enableAccept) {
		this.enableAccept = enableAccept;
	}

	public boolean isEnablePrint() {
		return enablePrint;
	}

	public void setEnablePrint(boolean enablePrint) {
		this.enablePrint = enablePrint;
	}

	public List<WrkPurpose> getWrkPurposes() {
		return wrkPurposes;
	}

	public void setWrkPurposes(List<WrkPurpose> wrkPurposes) {
		this.wrkPurposes = wrkPurposes;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public TransferOwnership getTransOwnership() {
		return transOwnership;
	}

	public void setTransOwnership(TransferOwnership transOwnership) {
		this.transOwnership = transOwnership;
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

	public String getReturnHDate() {
		return returnHDate;
	}

	public void setReturnHDate(String returnHDate) {
		this.returnHDate = returnHDate;
	}

	public Integer getSelectedArticleId() {
		return selectedArticleId;
	}

	public void setSelectedArticleId(Integer selectedArticleId) {
		this.selectedArticleId = selectedArticleId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isAllowAdd() {
		return allowAdd;
	}

	public void setAllowAdd(boolean allowAdd) {
		this.allowAdd = allowAdd;
	}

	public Integer getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(Integer serialNum) {
		this.serialNum = serialNum;
	}

	public List<TransferOwnershipModel> getTransModelList() {
		return transModelList;
	}

	public void setTransModelList(List<TransferOwnershipModel> transModelList) {
		this.transModelList = transModelList;
	}

	public TransferOwnershipModel getTransModel() {
		return transModel;
	}

	public void setTransModel(TransferOwnershipModel transModel) {
		this.transModel = transModel;
	}

	public boolean isCanSave() {
		return canSave;
	}

	public void setCanSave(boolean canSave) {
		this.canSave = canSave;
	}

	public TransferOwnership getTransSave() {
		return transSave;
	}

	public void setTransSave(TransferOwnership transSave) {
		this.transSave = transSave;
	}

	public List<TransferOwnership> getTransSaveList() {
		return transSaveList;
	}

	public void setTransSaveList(List<TransferOwnership> transSaveList) {
		this.transSaveList = transSaveList;
	}

	public boolean isHideInputs() {
		return hideInputs;
	}

	public void setHideInputs(boolean hideInputs) {
		this.hideInputs = hideInputs;
	}

	public String getWrkAppComment() {
		return wrkAppComment;
	}

	public void setWrkAppComment(String wrkAppComment) {
		this.wrkAppComment = wrkAppComment;
	}

	public String getApplicationPurpose() {
		return applicationPurpose;
	}

	public void setApplicationPurpose(String applicationPurpose) {
		this.applicationPurpose = applicationPurpose;
	}

	public UserMailObj getSelectedInbox() {
		return selectedInbox;
	}

	public void setSelectedInbox(UserMailObj selectedInbox) {
		this.selectedInbox = selectedInbox;
	}

	public WrkApplicationId getWrkId() {
		return WrkId;
	}

	public void setWrkId(WrkApplicationId wrkId) {
		WrkId = wrkId;
	}

	public List<TransferOwnershipDetails> getTsOnDtls() {
		return tsOnDtls;
	}

	public void setTsOnDtls(List<TransferOwnershipDetails> tsOnDtls) {
		this.tsOnDtls = tsOnDtls;
	}

	public boolean isAllowSelect() {
		return allowSelect;
	}

	public void setAllowSelect(boolean allowSelect) {
		this.allowSelect = allowSelect;
	}

	public List<WhsWarehouses> getAllWareHouses() {
		return allWareHouses;
	}

	public void setAllWareHouses(List<WhsWarehouses> allWareHouses) {
		this.allWareHouses = allWareHouses;
	}

	public Integer getStrNo() {
		return strNo;
	}

	public void setStrNo(Integer strNo) {
		this.strNo = strNo;
	}

	public List<User> getEmployers() {
		return employers;
	}

	public void setEmployers(List<User> employers) {
		this.employers = employers;
	}
}
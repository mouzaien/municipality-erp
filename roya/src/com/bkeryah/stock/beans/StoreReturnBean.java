package com.bkeryah.stock.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bkeryah.dao.IStockServiceDao;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.Article;
import com.bkeryah.entities.HrScenario;
import com.bkeryah.entities.HrsSigns;
import com.bkeryah.entities.ReturnStore;
import com.bkeryah.entities.ReturnStoreDetails;
import com.bkeryah.entities.SysProperties;
import com.bkeryah.entities.WhsWarehouses;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class StoreReturnBean {
	@ManagedProperty(value = "#{stockServiceDao}")
	private IStockServiceDao stockServiceDao;

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private ArcUsers currentUser;
	private Article article = new Article();
	private List<Article> articleList;

	private ReturnStoreDetailsModel returnStoreModelRecord = new ReturnStoreDetailsModel();
	private ReturnStore returnStore = new ReturnStore();
	private ReturnStoreDetails returnDetailsRecord = new ReturnStoreDetails();
	private List<ReturnStoreDetails> returnitemsDetailsList = new ArrayList<ReturnStoreDetails>();
	private List<ReturnStoreDetailsModel> returnModelDetailsList = new ArrayList<ReturnStoreDetailsModel>();
	private Integer returnedItmesId;
	private Integer articleId;
	private String returnDate;
	private Integer returnReason;
	private Integer qty;
	private String notes;
	private String artSerial;
	private boolean allowAdd;
	private boolean canSave;
	private Integer recordId;
	private String articleName;
	private String artCode;
	private String articleUnit;
	private Integer stepNum;
	private boolean enableAccept;
	private boolean enablePrint;
	private List<WrkPurpose> wrkPurposes;
	private String applicationPurpose;
	private String wrkAppComment;
	private Integer serialNum;
	private Integer employerId;
	private List<WhsWarehouses> allWareHouses = new ArrayList<WhsWarehouses>();
	private Integer strNo = 0;
	private Integer exchMasterId;
	private boolean allowAccept;
	private SysProperties prop;
	private List<SysProperties> propsList = new ArrayList<SysProperties>();
	private boolean enableEmpsList;

	@PostConstruct
	public void init() {
		currentUser = Utils.findCurrentUser();
		employerId = currentUser.getUserId();
		articleList = dataAccessService.getArticlesByUserId(employerId);
		setAllWareHouses(stockServiceDao.getStoreWharehouses(2));
		// prop = (SysProperties)
		// dataAccessService.findEntityById(SysProperties.class,
		// MyConstants.WAREHOUSE_MANAGER);
		propsList = dataAccessService.getDeansIdInSysProperties();
		for (SysProperties sysProp : propsList) {
			System.out.println(sysProp.getValue());
			if (sysProp.getValue().equals(currentUser.getUserId().toString())) {
				enableEmpsList = true;
			}
		}
		// forPageView
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		String arcRecordId = (String) httpSession.getAttribute("arcRecord");
		httpSession.removeAttribute("arcRecord");
		if (arcRecordId != null) {
			recordId = Integer.parseInt(arcRecordId.trim());
			returnStore = (ReturnStore) dataAccessService.getReturnStoreByArchRecordId(recordId);
			HrsSigns model = dataAccessService.getHrsSignsByArcId(recordId);
			employerId = model.getUserId();
			returnitemsDetailsList = dataAccessService.getReturnStoreDetailsById(returnStore.getReturnStoreId());
			wrkPurposes = dataAccessService.getAllPurposes();
			stepNum = dataAccessService.getStepNumberFromHrSign(recordId);
			int i = 0;
			for (ReturnStoreDetails returnItemDetails : returnitemsDetailsList) {
				returnStoreModelRecord = new ReturnStoreDetailsModel();
				returnStoreModelRecord.setArticleCode(returnItemDetails.getArticleCode());
				returnStoreModelRecord.setArticleId(returnItemDetails.getArticleId());
				returnStoreModelRecord.setArticleName(returnItemDetails.getArticleName());
				System.out.println(articleId);
				Article art = (Article) dataAccessService.findEntityById(Article.class,
						returnItemDetails.getArticleId());
				if (art != null)
					// returnStoreModelRecord.setArticleCode(art.getCode());
					returnStoreModelRecord.setQty(returnItemDetails.getQty());
				returnStoreModelRecord.setNotes(returnItemDetails.getNotes());
				returnStoreModelRecord.setRetrunReason(returnStore.getReason());
				// returnStoreModelRecord.setArticleUnit(returnItemDetails.getArticleUnit());
				returnModelDetailsList.add(i, returnStoreModelRecord);
				i++;
			}
			HrScenario scenario = (HrScenario) dataAccessService.findEntityById(HrScenario.class,
					MailTypeEnum.RETURNED_ITEMS_INVENTORY.getValue());

			if (stepNum == scenario.getStepsCount() + 1) {
				enableAccept = false;
				enablePrint = true;
			} else {
				enableAccept = true;
				enablePrint = false;
			}

		}
		if (returnStore.getSerialNumber() != null) {
			allowAccept = true;
		}
	}

	public void loadEmpAssignedArticles(AjaxBehaviorEvent abe) {
		articleList = dataAccessService.getArticlesByUserId(employerId);
	}

	public String accept() {
		if (serialNum != null) {
			wrkAppComment = "";
			applicationPurpose = "1";
			dataAccessService.acceptReturnStore(returnStore, recordId, MailTypeEnum.RETURNED_ITEMS_INVENTORY.getValue(),
					wrkAppComment, Integer.parseInt(applicationPurpose.trim()));
			return "mails";
		} else {
			MsgEntry.addErrorMessage("يجب أدخال االرقم التسلسلي بشكل صحيح");
			return "";
		}
	}

	public void loadArticle(AjaxBehaviorEvent event) {
		article = (Article) dataAccessService.findEntityById(Article.class, articleId);
		if (article != null) {
			// for show Article Code (Serial ) اظهار الرمز
			this.setArtSerial(article.getCode());

			Utils.updateUIComponent("includeform:ser");
			// ser
		}

		allowAddBtn();
	}

	public void allowAddBtn() {
		if (articleId != null) {
			allowAdd = true;
		} else {
			allowAdd = false;
		}
	}

	public void addItem(AjaxBehaviorEvent event) {
		if (articleId == null || qty == null || returnDate == null) {
			MsgEntry.addErrorMessage("يجب أدخال البيانات بشكل صحيح");
		} else {
			for (Article art : articleList) {
				System.out.println("code" + art.getCode());
				if (art.getId() == articleId) {

					this.articleName = art.getName();
					this.articleUnit = art.getUnitName();
					this.exchMasterId = art.getExchMasterId();

				}
			}
			returnStoreModelRecord.setArticleCode(artSerial);
			returnStoreModelRecord.setArticleId(articleId);
			returnStoreModelRecord.setQty(qty);
			returnStoreModelRecord.setNotes(notes);
			returnStoreModelRecord.setRetrunReason(returnReason);
			returnStoreModelRecord.setArticleName(articleName);
			returnStoreModelRecord.setArticleUnit(articleUnit);
			returnStoreModelRecord.setExchMasterId(exchMasterId);
			returnModelDetailsList.add(returnStoreModelRecord);
			canSave = true;
			returnStoreModelRecord = new ReturnStoreDetailsModel();

		}

	}

	public String save() {
		try {
			for (ReturnStoreDetailsModel item : returnModelDetailsList) {
				returnDetailsRecord.setArticleId(item.getArticleId());
				returnDetailsRecord.setArticleCode(item.getArticleCode());
				returnDetailsRecord.setQty(item.getQty());
				returnDetailsRecord.setNotes(item.getNotes());
				returnDetailsRecord.setArticleName(item.getArticleName());
				returnDetailsRecord.setExchMasterId(item.getExchMasterId());
				returnitemsDetailsList.add(returnDetailsRecord);
				returnDetailsRecord = new ReturnStoreDetails();
			}
			returnStore.setReason(returnReason);
			returnStore.setReturnHDate(returnDate);
			returnStore.setWrhouseId(article.getStrNo());
			returnStore.setStrNo(strNo);
			returnStore.setReturnGDate(Utils.convertHDateToGDate(returnDate));
			if (returnitemsDetailsList.size() > 0) {
				returnedItmesId = dataAccessService.addRturnedStoreItems(returnStore, employerId,
						returnitemsDetailsList);
				returnStore = new ReturnStore();
				returnitemsDetailsList = new ArrayList<ReturnStoreDetails>();
				MsgEntry.addAcceptFlashInfoMessage("تم ارسال طلبك بنجاح");
			}

		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
		return "mails";
	}

	public void removeRecord(ReturnStoreDetailsModel recordItem) {
		returnModelDetailsList.remove(recordItem);
		if (returnitemsDetailsList.size() == 0)
			canSave = false;
	}

	public String print_documents_return() {
		String reportName = "/reports/document_returned.jrxml";
		String strName = ((WhsWarehouses) dataAccessService.findEntityById(WhsWarehouses.class, returnStore.getStrNo()))
				.getStoreName();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("docId", returnStore.getReturnStoreId());
		parameters.put("STORE_NAME", strName);
		System.out.println(returnStore.getReturnStoreId());
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public void updateSerialNum() {
		if (serialNum != null) {
			returnStore.setSerialNumber(serialNum);
			dataAccessService.updateObject(returnStore);
			allowAccept = true;
			MsgEntry.addInfoMessage("تم الحفظ");
		}
	}

	public void cancleUpdateSerialNum() {
		serialNum = returnStore.getSerialNumber();
		MsgEntry.addErrorMessage("تم إلغاء الحفظ");
	}

	public IStockServiceDao getStockServiceDao() {
		return stockServiceDao;
	}

	public void setStockServiceDao(IStockServiceDao stockServiceDao) {
		this.stockServiceDao = stockServiceDao;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

	public Integer getItem() {
		return articleId;
	}

	public void setItem(Integer articleId) {
		this.articleId = articleId;
	}

	public String getReturnDate() {
		if (returnStore.getReturnHDate() != null)
			returnDate = returnStore.getReturnHDate();
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public Integer getReturnReason() {
		if (returnStore.getReason() != null)
			returnReason = returnStore.getReason();
		return returnReason;
	}

	public void setReturnReason(Integer returnReason) {
		this.returnReason = returnReason;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public ReturnStoreDetails getReturnDetailsRecord() {
		return returnDetailsRecord;
	}

	public void setReturnDetailsRecord(ReturnStoreDetails returnDetailsRecord) {
		this.returnDetailsRecord = returnDetailsRecord;
	}

	public List<ReturnStoreDetails> getReturnitemsDetailsList() {
		return returnitemsDetailsList;
	}

	public void setReturnitemsDetailsList(List<ReturnStoreDetails> returnitemsDetailsList) {
		this.returnitemsDetailsList = returnitemsDetailsList;
	}

	public ReturnStore getReturnStore() {
		return returnStore;
	}

	public void setReturnStore(ReturnStore returnStore) {
		this.returnStore = returnStore;
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

	public ReturnStoreDetailsModel getReturnStoreModelRecord() {
		return returnStoreModelRecord;
	}

	public void setReturnStoreModelRecord(ReturnStoreDetailsModel returnStoreModelRecord) {
		this.returnStoreModelRecord = returnStoreModelRecord;
	}

	public List<ReturnStoreDetailsModel> getReturnModelDetailsList() {
		return returnModelDetailsList;
	}

	public void setReturnModelDetailsList(List<ReturnStoreDetailsModel> returnModelDetailsList) {
		this.returnModelDetailsList = returnModelDetailsList;
	}

	public boolean isCanSave() {
		return canSave;
	}

	public void setCanSave(boolean canSave) {
		this.canSave = canSave;
	}

	public Integer getReturnedItmesId() {
		return returnedItmesId;
	}

	public void setReturnedItmesId(Integer returnedItmesId) {
		this.returnedItmesId = returnedItmesId;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getArticleUnit() {
		return articleUnit;
	}

	public void setArticleUnit(String articleUnit) {
		this.articleUnit = articleUnit;
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

	public String getApplicationPurpose() {
		return applicationPurpose;
	}

	public void setApplicationPurpose(String applicationPurpose) {
		this.applicationPurpose = applicationPurpose;
	}

	public String getWrkAppComment() {
		return wrkAppComment;
	}

	public void setWrkAppComment(String wrkAppComment) {
		this.wrkAppComment = wrkAppComment;
	}

	public Integer getSerialNum() {
		if (returnStore.getSerialNumber() != null) {
			serialNum = returnStore.getSerialNumber();
		}
		return serialNum;
	}

	public void setSerialNum(Integer serialNum) {
		this.serialNum = serialNum;
	}

	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
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

	public Integer getExchMasterId() {
		return exchMasterId;
	}

	public void setExchMasterId(Integer exchMasterId) {
		this.exchMasterId = exchMasterId;
	}

	public String getArtSerial() {
		return artSerial;
	}

	public void setArtSerial(String artSerial) {
		this.artSerial = artSerial;
	}

	public String getArtCode() {
		return artCode;
	}

	public void setArtCode(String artCode) {
		this.artCode = artCode;
	}

	public boolean isAllowAccept() {
		return allowAccept;
	}

	public void setAllowAccept(boolean allowAccept) {
		this.allowAccept = allowAccept;
	}

	public boolean isEnableEmpsList() {
		return enableEmpsList;
	}

	public void setEnableEmpsList(boolean enableEmpsList) {
		this.enableEmpsList = enableEmpsList;
	}
}

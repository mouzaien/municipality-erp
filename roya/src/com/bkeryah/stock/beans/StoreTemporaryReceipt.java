package com.bkeryah.stock.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.dao.IStockServiceDao;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.Article;
import com.bkeryah.entities.ArticleStatus;
import com.bkeryah.entities.DocumentType;
import com.bkeryah.entities.HrScenario;
import com.bkeryah.entities.StockEntryMaster;
import com.bkeryah.entities.StockInDetails;
import com.bkeryah.entities.StoreTemporeryReceiptDetails;
import com.bkeryah.entities.StoreTemporeryReceiptMaster;
import com.bkeryah.entities.WhsWarehouses;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.fuel.entities.Car;
import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.testssss.EmployeeForDropDown;

import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class StoreTemporaryReceipt {

	@ManagedProperty(value = "#{stockServiceDao}")
	private IStockServiceDao stockServiceDao;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;

	private ArcUsers currentUser;
	private Article article = new Article();
	private List<Article> articleList;
	private List<EmployeeForDropDown> supplierList = new ArrayList<>();
	private Map<String, Integer> supplierMap;
	private List<WhsWarehouses> allWareHouses = new ArrayList<WhsWarehouses>();
	private EmployeeForDropDown oneSupplier = new EmployeeForDropDown();
	private Integer document;
	private Integer documentNumber;
	private String documentHDate;
	private Date documentGDate;
	private String receiptHDate;
	private Date receiptGDate;
	private Integer specialNum;
	private Integer strNo = 0;
	private Integer selectedWareHouse = 10;// المستودع المختار
	private ArticleStatus articleStatus = new ArticleStatus();
	private boolean blockSupplier;
	private Integer supplierId;
	private Integer recordId;
	private Integer qty;
	private Integer selectedStatus;
	private String notes;
	private List<ArticleStatus> allArticlesStatus = new ArrayList<ArticleStatus>();
	private StoreTemporeryReceiptMaster temporeryReceipt = new StoreTemporeryReceiptMaster();
	private List<WrkPurpose> wrkPurposes;
	private Integer stepNum;
	private Integer articleId;
	private boolean enableAccept;
	private boolean enablePrint;
	private StoreTemporeryReceiptMaster storeTemporeryReceiptMaster = new StoreTemporeryReceiptMaster();
	private StoreTemporeryReceiptDetails temporeryReceiptDetails = new StoreTemporeryReceiptDetails();
	private List<StoreTemporeryReceiptDetails> temporeryReceiptDetailsList = new ArrayList<StoreTemporeryReceiptDetails>();
	private StoreTemporeryReceiptDetailsModel temporeryReceiptModelRecord = new StoreTemporeryReceiptDetailsModel();
	private List<StoreTemporeryReceiptDetailsModel> temporeryReceiptDetailsModelList = new ArrayList<StoreTemporeryReceiptDetailsModel>();
	private String articleName;
	private String articleUnit;
	private boolean canSave;
	private boolean allowAdd;
	private Integer storeTemporeryReceiptId;
	private String applicationPurpose;
	private String wrkAppComment;
	private List<DocumentType> docType = new ArrayList<DocumentType>();
	private String strName;
	private String supplierName;
	private String docTypeName;
	private String status;
	private String beginDate;
	private String finishDate;
	private List<StoreTemporeryReceiptMaster> tempreceiptsList = new ArrayList<StoreTemporeryReceiptMaster>();
	private List<StoreTemporeryReceiptMaster> filterTempreceiptsList;
	private List<StoreTemporeryReceiptDetails> strTempReceiptDetails = new ArrayList<StoreTemporeryReceiptDetails>();
	private UserMailObj selectedInbox;
	private WrkApplicationId WrkId;

	@PostConstruct
	public void init() {
		currentUser = Utils.findCurrentUser();
		allArticlesStatus = stockServiceDao.getAllArticleStatus();
		docType = dataAccessService.getAllDocumentType();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		String arcRecordId = (String) httpSession.getAttribute("arcRecord");
		httpSession.removeAttribute("arcRecord");
		if (arcRecordId != null) {
			recordId = Integer.parseInt(arcRecordId.trim());
			storeTemporeryReceiptMaster = (StoreTemporeryReceiptMaster) dataAccessService
					.getStoreTemporeryReceiptMasterByArchRecordId(recordId);
			temporeryReceiptDetailsList = dataAccessService
					.getStoreTemporeryReceiptDetailsById(storeTemporeryReceiptMaster.getId());
			wrkPurposes = dataAccessService.getAllPurposes();
			stepNum = dataAccessService.getStepNumberFromHrSign(recordId);
			selectedInbox = (UserMailObj) httpSession.getAttribute("selectedMail");
			if (selectedInbox != null)
				WrkId = new WrkApplicationId(Integer.parseInt(this.selectedInbox.WrkId), selectedInbox.StepId);
			int i = 0;
			for (StoreTemporeryReceiptDetails tempReceiptList : temporeryReceiptDetailsList) {
				temporeryReceiptModelRecord = new StoreTemporeryReceiptDetailsModel();
				article = (Article) dataAccessService.findEntityById(Article.class, tempReceiptList.getArticleId());
				temporeryReceiptModelRecord.setArticleId(tempReceiptList.getArticleId());
				temporeryReceiptModelRecord.setArticleStatus(tempReceiptList.getArticleStatus());
				temporeryReceiptModelRecord.setArticleUnit(article.getItemUnite().getName());
				temporeryReceiptModelRecord.setArticleName(tempReceiptList.getArticleName());
				temporeryReceiptModelRecord.setNotes(tempReceiptList.getNotes());
				temporeryReceiptModelRecord.setQty(tempReceiptList.getQty());
				temporeryReceiptDetailsModelList.add(i, temporeryReceiptModelRecord);
				i++;
			}
			HrScenario scenario = (HrScenario) dataAccessService.findEntityById(HrScenario.class,
					MailTypeEnum.TEMPORERY_RECEIPT.getValue());
			if (stepNum == scenario.getStepsCount() + 1) {
				enableAccept = false;
				enablePrint = true;
			} else {
				enableAccept = true;
				enablePrint = false;
			}
		}
		allWareHouses = stockServiceDao.getStoreDeanWharehouses(currentUser.getUserId());
		supplierMap = stockServiceDao.getAllNameSuppliers();
		for (Entry<String, Integer> entry : supplierMap.entrySet()) {
			if (entry.getKey() != null && entry.getValue() != null) {
				oneSupplier.setName(entry.getKey());
				oneSupplier.setId(entry.getValue());
				supplierList.add(oneSupplier);
				oneSupplier = new EmployeeForDropDown();
			} else {
				continue;
			}
		}
	}

	public void addItem(AjaxBehaviorEvent event) {
		if (articleId == null || qty == null || documentHDate == null || receiptHDate == null) {
			MsgEntry.addErrorMessage("يجب أدخال البيانات بشكل صحيح");
		} else {
			for (Article art : articleList) {
				if (art.getId() == articleId) {
					this.articleName = art.getName();
					this.articleUnit = art.getItemUnite().getName();
				}
			}
			temporeryReceiptModelRecord.setArticleId(articleId);
			temporeryReceiptModelRecord.setQty(qty);
			temporeryReceiptModelRecord.setNotes(notes);
			temporeryReceiptModelRecord.setArticleName(articleName);
			temporeryReceiptModelRecord.setArticleUnit(articleUnit);
			temporeryReceiptModelRecord.setArticleStatus(selectedStatus);
			temporeryReceiptDetailsModelList.add(temporeryReceiptModelRecord);
			canSave = true;
			blockSupplier = true;
			temporeryReceiptModelRecord = new StoreTemporeryReceiptDetailsModel();
		}
	}

	public void loadArticle(AjaxBehaviorEvent event) {
		if (articleId != null) {
			article = (Article) dataAccessService.findEntityById(Article.class, articleId);
			allowAddBtn();
			setCarsDetails();
		}
	}

	public void setCarsDetails() {

		// article = (Article) dataAccessService.findEntityById(Article.class,
		// requestDetailsRecord.getItemId());
		if (article != null) {

			// load car for رقم الهيكل ورقم البابا ورقم اللوحة ف الملاحظات
			List<Car> carDetails = dataAccessService.loadCarDetailsByArtId(article.getId());
			this.setNotes("");
			Utils.updateUIComponent("includeform:desc");
			if (carDetails != null && carDetails.size() > 0) {
				// String str=carDetails.getChassisNumber()==null?
				// "":carDetails.getChassisNumber()+"رقم الهيكل"+
				// carDetails.getNumDoor()==null?
				// "":carDetails.getNumDoor()+"رقم الباب "+
				// carDetails.getMatricule()==null?
				// "":carDetails.getMatricule()+"رقم اللوحة ";
				//
				String str = "رقم الهيكل || " + carDetails.get(0).getChassisNumber() + "  رقم الباب || "
						+ carDetails.get(0).getNumDoor() + "رقم اللوحة || " + carDetails.get(0).getMatricule();
				this.setNotes(str);
				Utils.updateUIComponent("includeform:desc");

			}
		}
	}

	public void allowAddBtn() {
		if (articleId != null) {
			allowAdd = true;
		} else {
			allowAdd = false;
		}
	}

	public String accept() {
		wrkAppComment = "";
		applicationPurpose = "1";
		try {
			masterData();
			dataAccessService.updateObject(storeTemporeryReceiptMaster);
			dataAccessService.acceptStoreTemporeryReceipt(storeTemporeryReceiptMaster, recordId,
					MailTypeEnum.TEMPORERY_RECEIPT.getValue(), wrkAppComment,
					Integer.parseInt(applicationPurpose.trim()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "mails";
	}

	public String refuseAction() {
		try {
			applicationPurpose = "2";
			storeTemporeryReceiptMaster.setStatus(MyConstants.NO);
			String wrkCommentRefuse = wrkAppComment;
			dataAccessService.refuseStoreTemporeryReceipt(WrkId, recordId, storeTemporeryReceiptMaster,
					wrkCommentRefuse, Integer.parseInt(applicationPurpose.trim()));
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("refuse.record"));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}

	public String save() {
		try {
			for (StoreTemporeryReceiptDetailsModel item : temporeryReceiptDetailsModelList) {
				temporeryReceiptDetails.setArticleId(item.getArticleId());
				temporeryReceiptDetails.setQty(item.getQty());
				temporeryReceiptDetails.setNotes(item.getNotes());
				temporeryReceiptDetails.setArticleStatus(item.getArticleStatus());
				temporeryReceiptDetailsList.add(temporeryReceiptDetails);
				temporeryReceiptDetails = new StoreTemporeryReceiptDetails();
			}
			masterData();
			if (temporeryReceiptDetailsList.size() > 0) {
				storeTemporeryReceiptId = dataAccessService.addStoreTemporeryReceiptItems(storeTemporeryReceiptMaster,
						currentUser.getUserId(), temporeryReceiptDetailsList);
				for (StoreTemporeryReceiptDetails item : temporeryReceiptDetailsList) {
					item.setTemporaryReceiptMasterId(storeTemporeryReceiptMaster.getId());
					dataAccessService.updateObject(item);
				}
				storeTemporeryReceiptMaster = new StoreTemporeryReceiptMaster();
				temporeryReceiptDetailsList = new ArrayList<StoreTemporeryReceiptDetails>();
				MsgEntry.addAcceptFlashInfoMessage("تم ارسال طلبك بنجاح");
			}

		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}

		return "mails";
	}

	private void masterData() throws Exception {
		storeTemporeryReceiptMaster.setDocument(document);
		storeTemporeryReceiptMaster.setDocumentNumber(documentNumber);
		storeTemporeryReceiptMaster.setDocumentHDate(documentHDate);
		storeTemporeryReceiptMaster.setDocumentGDate(Utils.convertHDateToGDate(documentHDate));
		storeTemporeryReceiptMaster.setReceiptHDate(receiptHDate);
		storeTemporeryReceiptMaster.setReceiptGDate(Utils.convertHDateToGDate(receiptHDate));
		storeTemporeryReceiptMaster.setStrNo(article.getStrNo());
		storeTemporeryReceiptMaster.setSpecialNumber(specialNum);
		storeTemporeryReceiptMaster.setSupplierId(supplierId);
	}

	public void removeRecord() {
		temporeryReceiptDetailsModelList.remove(temporeryReceiptModelRecord);
		if (temporeryReceiptDetailsModelList.size() == 0) {
			canSave = false;
			blockSupplier = false;
		}
	}

	public String print_temporary_receipt_notice() {
		String reportName = "/reports/temporary_receipt_notice.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("TEMP_MSTR_ID", storeTemporeryReceiptMaster.getId());
		parameters.put("STORE_NAME", strName);
		parameters.put("SUPPLIER_NAME", getSupplierName());
		parameters.put("DOCUMENT_TYPE", getDocTypeName());
		parameters.put("DOCUMENT_NUMBER", storeTemporeryReceiptMaster.getDocumentNumber());
		parameters.put("DOCUMENT_H_DATE", storeTemporeryReceiptMaster.getDocumentHDate());
		parameters.put("SPECIAL_NUMBER", storeTemporeryReceiptMaster.getSpecialNumber());
		parameters.put("RECEIPT_H_DATE", storeTemporeryReceiptMaster.getReceiptHDate());
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public String printtemporaryReceiptA4Action() {
		String reportName = "/reports/temporary_receipt_notice.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("TEMP_MSTR_ID", temporeryReceipt.getId());
		WhsWarehouses str = (WhsWarehouses) dataAccessService.findEntityById(WhsWarehouses.class, strNo);
		parameters.put("STORE_NAME", str.getStoreName());
		parameters.put("SUPPLIER_NAME", temporeryReceipt.getSupplierName());
		DocumentType item = (DocumentType) dataAccessService.findEntityById(DocumentType.class,
				temporeryReceipt.getDocument());
		parameters.put("DOCUMENT_TYPE", item.getDocumentName());
		parameters.put("DOCUMENT_NUMBER", temporeryReceipt.getDocumentNumber());
		parameters.put("DOCUMENT_H_DATE", temporeryReceipt.getDocumentHDate());
		parameters.put("SPECIAL_NUMBER", temporeryReceipt.getSpecialNumber());
		parameters.put("RECEIPT_H_DATE", temporeryReceipt.getReceiptHDate());
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public void loadAllTempReceiptList() {
		tempreceiptsList = dataAccessService.searchTempReceipts(beginDate, finishDate, strNo);
	}

	public void searchMemoReceipts(AjaxBehaviorEvent e) {
		if (beginDate.length() == 0)
			beginDate = null;
		if (finishDate.length() == 0)
			finishDate = null;
		if ((beginDate != null) && (finishDate != null)
				&& (Utils.reverseDateToNumber(beginDate) > Utils.reverseDateToNumber(finishDate))) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.coherent.dates"));
			return;
		}

		tempreceiptsList = dataAccessService.searchTempReceipts(beginDate, finishDate, strNo);

		// exchangeRequestList =
		// dataAccessService.searchExchangeRequests(beginDate, finishDate,
		// strNo);

		// return "";
	}

	public void loadSelectedTempoReceipt(StoreTemporeryReceiptMaster selectedTemp) {

		// temporeryReceipt = (StoreTemporeryReceiptMaster) dataAccessService
		// .findEntityById(StoreTemporeryReceiptMaster.class,
		// selectedMemo.getId());

		temporeryReceiptDetailsModelList = dataAccessService.getTempReceiptDetailsList(selectedTemp.getId());
		//
		temporeryReceipt = dataAccessService.getStoreTemporeryReceiptMasterByArchRecordId(selectedTemp.getRecordId());
		strTempReceiptDetails = new ArrayList<StoreTemporeryReceiptDetails>();

		for (StoreTemporeryReceiptDetailsModel tempReceiptDetails : temporeryReceiptDetailsModelList) {
			articleStatus = (ArticleStatus) dataAccessService.findEntityById(ArticleStatus.class,
					tempReceiptDetails.getArticleStatus());
			StoreTemporeryReceiptDetails tempDetalis = new StoreTemporeryReceiptDetails();
			tempDetalis.setQty(tempReceiptDetails.getQty());
			tempDetalis.setNotes(tempReceiptDetails.getNotes());
			tempDetalis.setArticleName(tempReceiptDetails.getArticleName());
			tempDetalis.setArticleUnit(tempReceiptDetails.getArticleUnit());
			tempDetalis.setArticleStatus(tempReceiptDetails.getArticleStatus());
			temporeryReceipt.setSupplierName(tempReceiptDetails.getSupplierName());
			strTempReceiptDetails.add(tempDetalis);
		}

	}

	public void updateNameAfterChoiseArticle(AjaxBehaviorEvent event) {
		articleList = stockServiceDao.getAllArticlesByStrno(selectedWareHouse);
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

	public ArcUsers getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(ArcUsers currentUser) {
		this.currentUser = currentUser;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

	public List<EmployeeForDropDown> getSupplierList() {
		return supplierList;
	}

	public void setSupplierList(List<EmployeeForDropDown> supplierList) {
		this.supplierList = supplierList;
	}

	public Map<String, Integer> getSupplierMap() {
		return supplierMap;
	}

	public void setSupplierMap(Map<String, Integer> supplierMap) {
		this.supplierMap = supplierMap;
	}

	public List<WhsWarehouses> getAllWareHouses() {
		return allWareHouses;
	}

	public void setAllWareHouses(List<WhsWarehouses> allWareHouses) {
		this.allWareHouses = allWareHouses;
	}

	public EmployeeForDropDown getOneSupplier() {
		return oneSupplier;
	}

	public void setOneSupplier(EmployeeForDropDown oneSupplier) {
		this.oneSupplier = oneSupplier;
	}

	public Integer getDocument() {
		if (storeTemporeryReceiptMaster.getDocument() != null)
			document = storeTemporeryReceiptMaster.getDocument();
		return document;
	}

	public void setDocument(Integer document) {
		this.document = document;
	}

	public Integer getDocumentNumber() {
		if (storeTemporeryReceiptMaster.getDocumentNumber() != null)
			documentNumber = storeTemporeryReceiptMaster.getDocumentNumber();
		return documentNumber;
	}

	public void setDocumentNumber(Integer documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getDocumentHDate() {
		if (storeTemporeryReceiptMaster.getDocumentHDate() != null)
			documentHDate = storeTemporeryReceiptMaster.getDocumentHDate();
		return documentHDate;
	}

	public void setDocumentHDate(String documentHDate) {
		this.documentHDate = documentHDate;
	}

	public String getReceiptHDate() {
		if (storeTemporeryReceiptMaster.getReceiptHDate() != null)
			receiptHDate = storeTemporeryReceiptMaster.getReceiptHDate();
		return receiptHDate;
	}

	public void setReceiptHDate(String receiptHDate) {
		this.receiptHDate = receiptHDate;
	}

	public Integer getSpecialNum() {
		if (storeTemporeryReceiptMaster.getSpecialNumber() != null)
			specialNum = storeTemporeryReceiptMaster.getSpecialNumber();
		return specialNum;
	}

	public void setSpecialNum(Integer specialNum) {
		this.specialNum = specialNum;
	}

	public Integer getStrNo() {
		if (storeTemporeryReceiptMaster.getStrNo() != null) {
			strNo = storeTemporeryReceiptMaster.getStrNo();
		}
		return strNo;
	}

	public void setStrNo(Integer strNo) {
		this.strNo = strNo;
	}

	public Integer getSelectedWareHouse() {
		if (storeTemporeryReceiptMaster.getStrNo() != null) {
			selectedWareHouse = storeTemporeryReceiptMaster.getStrNo();
		}
		return selectedWareHouse;
	}

	public void setSelectedWareHouse(Integer selectedWareHouse) {
		this.selectedWareHouse = selectedWareHouse;
	}

	public ArticleStatus getArticleStatus() {
		return articleStatus;
	}

	public void setArticleStatus(ArticleStatus articleStatus) {
		this.articleStatus = articleStatus;
	}

	public boolean isBlockSupplier() {
		return blockSupplier;
	}

	public void setBlockSupplier(boolean blockSupplier) {
		this.blockSupplier = blockSupplier;
	}

	public Integer getSupplierId() {
		if (storeTemporeryReceiptMaster.getSupplierId() != null) {
			supplierId = storeTemporeryReceiptMaster.getSupplierId();
		}
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Date getDocumentGDate() {
		return documentGDate;
	}

	public void setDocumentGDate(Date documentGDate) {
		this.documentGDate = documentGDate;
	}

	public Date getReceiptGDate() {
		return receiptGDate;
	}

	public void setReceiptGDate(Date receiptGDate) {
		this.receiptGDate = receiptGDate;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public StoreTemporeryReceiptMaster getTemporeryReceipt() {
		return temporeryReceipt;
	}

	public void setTemporeryReceipt(StoreTemporeryReceiptMaster temporeryReceipt) {
		this.temporeryReceipt = temporeryReceipt;
	}

	public List<WrkPurpose> getWrkPurposes() {
		return wrkPurposes;
	}

	public void setWrkPurposes(List<WrkPurpose> wrkPurposes) {
		this.wrkPurposes = wrkPurposes;
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

	public StoreTemporeryReceiptMaster getStoreTemporeryReceiptMaster() {
		return storeTemporeryReceiptMaster;
	}

	public void setStoreTemporeryReceiptMaster(StoreTemporeryReceiptMaster storeTemporeryReceiptMaster) {
		this.storeTemporeryReceiptMaster = storeTemporeryReceiptMaster;
	}

	public StoreTemporeryReceiptDetails getTemporeryReceiptDetails() {
		return temporeryReceiptDetails;
	}

	public void setTemporeryReceiptDetails(StoreTemporeryReceiptDetails temporeryReceiptDetails) {
		this.temporeryReceiptDetails = temporeryReceiptDetails;
	}

	public List<StoreTemporeryReceiptDetails> getTemporeryReceiptDetailsList() {
		return temporeryReceiptDetailsList;
	}

	public void setTemporeryReceiptDetailsList(List<StoreTemporeryReceiptDetails> temporeryReceiptDetailsList) {
		this.temporeryReceiptDetailsList = temporeryReceiptDetailsList;
	}

	public List<StoreTemporeryReceiptDetailsModel> getTemporeryReceiptDetailsModelList() {
		return temporeryReceiptDetailsModelList;
	}

	public void setTemporeryReceiptDetailsModelList(
			List<StoreTemporeryReceiptDetailsModel> temporeryReceiptDetailsModelList) {
		this.temporeryReceiptDetailsModelList = temporeryReceiptDetailsModelList;
	}

	public StoreTemporeryReceiptDetailsModel getTemporeryReceiptModelRecord() {
		return temporeryReceiptModelRecord;
	}

	public void setTemporeryReceiptModelRecord(StoreTemporeryReceiptDetailsModel temporeryReceiptModelRecord) {
		this.temporeryReceiptModelRecord = temporeryReceiptModelRecord;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getSelectedStatus() {
		return selectedStatus;
	}

	public void setSelectedStatus(Integer selectedStatus) {
		this.selectedStatus = selectedStatus;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<ArticleStatus> getAllArticlesStatus() {
		return allArticlesStatus;
	}

	public void setAllArticlesStatus(List<ArticleStatus> allArticlesStatus) {
		this.allArticlesStatus = allArticlesStatus;
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

	public boolean isCanSave() {
		return canSave;
	}

	public void setCanSave(boolean canSave) {
		this.canSave = canSave;
	}

	public boolean isAllowAdd() {
		return allowAdd;
	}

	public void setAllowAdd(boolean allowAdd) {
		this.allowAdd = allowAdd;
	}

	public Integer getStoreTemporeryReceiptId() {
		return storeTemporeryReceiptId;
	}

	public void setStoreTemporeryReceiptId(Integer storeTemporeryReceiptId) {
		this.storeTemporeryReceiptId = storeTemporeryReceiptId;
	}

	public List<DocumentType> getDocType() {
		return docType;
	}

	public void setDocType(List<DocumentType> docType) {
		this.docType = docType;
	}

	public String getStrName() {
		if (storeTemporeryReceiptMaster.getStrNo() != null) {
			List<WhsWarehouses> stocks = stockServiceDao.getAllWareHouses();
			for (WhsWarehouses item : stocks) {
				if (storeTemporeryReceiptMaster.getStrNo().equals(item.getStoreNumber())) {
					strName = item.getStoreName();
				}
			}
		}
		return strName;

	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getSupplierName() {
		for (Entry<String, Integer> entry : supplierMap.entrySet()) {
			if (entry.getKey() != null && entry.getValue() != null
					&& storeTemporeryReceiptMaster.getSupplierId() != null) {
				if (entry.getValue().equals(storeTemporeryReceiptMaster.getSupplierId()))
					supplierName = entry.getKey();
			} else {
				continue;
			}
		}
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getDocTypeName() {
		for (DocumentType item : docType) {

			if (item.getId().equals(storeTemporeryReceiptMaster.getDocument())) {
				docTypeName = item.getDocumentName();
			}
		}
		return docTypeName;
	}

	public void setDocTypeName(String docTypeName) {
		this.docTypeName = docTypeName;
	}

	public String getStatus() {
		switch (document) {
		case 1:
			status = "تعميد";
			break;
		case 2:
			status = "مناقصة";
			break;
		case 3:
			status = "فاتورة";
			break;
		case 4:
			status = "تعميم";
			break;
		case 5:
			status = "لجنة";
			break;
		case 6:
			status = "رجيع";
			break;
		case 7:
			status = "هبة";
			break;
		case 8:
			status = "طلب صرف مواد";
			break;
		case 9:
			status = "بدون مستند";
			break;

		default:
			break;
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

	public List<StoreTemporeryReceiptMaster> getTempreceiptsList() {
		return tempreceiptsList;
	}

	public void setTempreceiptsList(List<StoreTemporeryReceiptMaster> tempreceiptsList) {
		this.tempreceiptsList = tempreceiptsList;
	}

	public List<StoreTemporeryReceiptMaster> getFilterTempreceiptsList() {
		return filterTempreceiptsList;
	}

	public void setFilterTempreceiptsList(List<StoreTemporeryReceiptMaster> filterTempreceiptsList) {
		this.filterTempreceiptsList = filterTempreceiptsList;
	}

	public List<StoreTemporeryReceiptDetails> getStrTempReceiptDetails() {
		return strTempReceiptDetails;
	}

	public void setStrTempReceiptDetails(List<StoreTemporeryReceiptDetails> strTempReceiptDetails) {
		this.strTempReceiptDetails = strTempReceiptDetails;
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
}

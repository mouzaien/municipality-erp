package com.bkeryah.stock.beans;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import org.primefaces.event.RowEditEvent;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.dao.IStockServiceDao;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.Article;
import com.bkeryah.entities.ArticleStatus;
import com.bkeryah.entities.FinEntity;
import com.bkeryah.entities.FinFinancialYear;
import com.bkeryah.entities.HrScenario;
import com.bkeryah.entities.StockEntryMaster;
import com.bkeryah.entities.StockInDetails;
import com.bkeryah.entities.StoreTemporeryReceiptDetails;
import com.bkeryah.entities.StoreTemporeryReceiptMaster;
import com.bkeryah.entities.SysProperties;
import com.bkeryah.entities.WhsWarehouses;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.model.MemoReceiptModel;
import com.bkeryah.model.StockEntryModel;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.testssss.EmployeeForDropDown;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean()
@ViewScoped
public class StockEntryBean {

	@ManagedProperty(value = "#{stockServiceDao}")
	private IStockServiceDao stockServiceDao;

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;

	private Article article = new Article();
	private List<FinFinancialYear> allfinYears = new ArrayList<FinFinancialYear>();
	private List<ArticleStatus> allArticlesStatus = new ArrayList<ArticleStatus>();
	private List<WhsWarehouses> allWareHouses = new ArrayList<WhsWarehouses>();
	private FinFinancialYear finYear = new FinFinancialYear();
	private FinEntity supplier = new FinEntity();

	private List<EmployeeForDropDown> supplierList = new ArrayList<>();
	private Map<String, Integer> supplierMap;
	private EmployeeForDropDown oneSupplier = new EmployeeForDropDown();

	private StockInDetails stockInDetails = new StockInDetails();
	private List<StockInDetails> StockInDetailList = new ArrayList<>();
	private ArticleStatus articleStatus = new ArticleStatus();
	private StockEntryMaster stockEntryMaster = new StockEntryMaster();
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	private Integer currentFinYear;
	private Integer itemId;
	private Integer selectedStatus;
	private Integer selectedWareHouse = 10;
	private Integer stockInMasterId;
	private boolean allowAdd = true;
	private ArcUsers currentUser;
	private Integer recordId;

	private boolean enablePrint;
	private boolean enableAccept;
	private String applicationPurpose;
	private String wrkAppComment;
	private List<WrkPurpose> wrkPurposes;
	private Integer stepNum;
	private List<Article> articles;

	private Integer supplierId;
	private Float articlesTotalPrice = 0.00f;
	private MemoReceiptModel memoReceiptModel;
	public Integer SerialNum;
	private boolean allPrint;
	private Integer strNo = 0;
	private List<StockEntryMaster> memoReceiptList = new ArrayList<StockEntryMaster>();
	private List<StockEntryMaster> filterMemoReceiptList;
	private String beginDate;
	private String finishDate;
	private StockEntryMaster memoReceipt = new StockEntryMaster();

	private boolean blockSupplier;
	private boolean checkReceipt = true;
	private Integer stockNoticeNo;
	private String notifDate;
	private Integer tempMstrid;
	private StoreTemporeryReceiptMaster tempMstr = new StoreTemporeryReceiptMaster();
	private List<StoreTemporeryReceiptMaster> tempMstrList = new ArrayList<StoreTemporeryReceiptMaster>();
	private List<StoreTemporeryReceiptDetails> rcptStockInDetailList = new ArrayList<StoreTemporeryReceiptDetails>();
	private float total = 0f;
	private SysProperties prop;
	private boolean enableEditMgr;
	private boolean serialPrintFlag;
	private UserMailObj selectedInbox;
	private WrkApplicationId WrkId;

	@PostConstruct
	public void init() {
		currentUser = Utils.findCurrentUser();
		List<WhsWarehouses> deanStores = stockServiceDao.getStoreDeanWharehouses(currentUser.getUserId());
		for (WhsWarehouses wrs : deanStores) {
			System.out.println(wrs.getStoreName() + " ---- " + wrs.getStoreNumber());
			tempMstrList.addAll(dataAccessService.getStrTempRcptMstrByStrNo(wrs.getStoreNumber()));

		}
		// tempMstrList = dataAccessService.getAllStrTempRcptMstr();

		for (StoreTemporeryReceiptMaster wrs : tempMstrList) {
			System.out.println(wrs.getDocumentNumber() + "----------" + wrs.getDocumentName());
		}
		setAllWareHouses(stockServiceDao.getStoreDeanWharehouses(currentUser.getUserId()));
		prop = (SysProperties) dataAccessService.findEntityById(SysProperties.class, MyConstants.WAREHOUSE_MANAGER);
		if (prop.getValue().equals(currentUser.getUserId().toString())) {
			enableEditMgr = true;
		}
		// forPageView
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		String arcRecordId = (String) httpSession.getAttribute("arcRecord");
		// articles = stockServiceDao.getAllArticlesByStrno(selectedWareHouse);
		if (arcRecordId != null) {
			recordId = Integer.parseInt(arcRecordId.trim());
			stockEntryMaster = (StockEntryMaster) dataAccessService.getStockEntryMasterByArchRecordId(recordId);
			getMemoReceiptDetails();
			wrkPurposes = dataAccessService.getAllPurposes();
			stepNum = dataAccessService.getStepNumberFromHrSign(recordId);
			selectedInbox = (UserMailObj) httpSession.getAttribute("selectedMail");
			if (selectedInbox != null)
				WrkId = new WrkApplicationId(Integer.parseInt(this.selectedInbox.WrkId), selectedInbox.StepId);
			for (StockInDetails stockInDetails : stockEntryMaster.getStockInDetailsList()) {
				articleStatus = (ArticleStatus) dataAccessService.findEntityById(ArticleStatus.class,
						stockInDetails.getArticleStatus());
				stockInDetails.setArticleName(stockInDetails.getArticle().getName());
				stockInDetails.setUniteName(stockInDetails.getArticle().getItemUnite().getName());
				stockInDetails.setArticleStatusName(articleStatus.getArticleStatusName());
			}
			ArcUsers user = null;
			try {
				user = dataAccessService.getUserNameById(stockEntryMaster.getUserId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			stockEntryMaster.setArcUser(user);
			HrScenario scenario = (HrScenario) dataAccessService.findEntityById(HrScenario.class,
					MailTypeEnum.MEMO_RECEIPT.getValue());
			if (stepNum == scenario.getStepsCount() + 1) {
				enableAccept = false;
				enablePrint = true;
			} else {
				enableAccept = true;
				enablePrint = false;
			}
			if (stockEntryMaster.getSerialNumber() != null) {
				allPrint = true;
			} else {
				allPrint = false;
			}

		}
		// forRealPage
		setAllfinYears(stockServiceDao.getAllFinancialYear());
		setAllArticlesStatus(stockServiceDao.getAllArticleStatus());
		setFinYear(stockServiceDao.getFinancialYearById(5));
		setCurrentFinYear(finYear.getYEARID());

		// for dropdownList suppliers
		// setSupplierMap(stockServiceDao.getAllNameSuppliers());
		setSupplierMap(stockServiceDao.getAllNameSuppliers());
		// for (Entry<String, Integer> entry : supplierMap.entrySet()) {
		//
		// if (entry.getKey() != null && entry.getValue() != null) {
		// EmployeeForDropDown sup = new EmployeeForDropDown();
		// sup.setName(entry.getKey());
		// sup.setId(entry.getValue());
		// supplierList.add(sup);
		// sup = new EmployeeForDropDown();
		// } else {
		// continue;
		// }
		//
		// }

		// forSupplier
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
		if (stockEntryMaster.getStockFinEntryNo() != null) {
			serialPrintFlag = true;
		}
	}

	public void getMemoReceiptDetails() {
		memoReceiptModel = stockServiceDao.getMemoReceiptDetails(stockEntryMaster.getStockEntryMasterId());
	}

	public void updateNameAfterChoiseArticle(AjaxBehaviorEvent event) {

		articles = stockServiceDao.getAllArticlesByStrno(selectedWareHouse);
	}

	public void loadAllMemoReceiptList() {
		memoReceiptList = dataAccessService.searchMemoReceipts(beginDate, finishDate, strNo);
	}

	public void loadSelectedMemoReceipt(StockEntryMaster selectedMemo) {
		// memoReceipt = (StockEntryMaster)
		// dataAccessService.findEntityById(StockEntryMaster.class,
		// selectedMemo.getStockEntryMasterId());
		memoReceiptModel = stockServiceDao.getMemoReceiptDetails(selectedMemo.getStockEntryMasterId());

		memoReceipt = (StockEntryMaster) dataAccessService
				.getStockEntryMasterByArchRecordId(selectedMemo.getRecordId());
		for (StockInDetails stockInDetails : memoReceipt.getStockInDetailsList()) {
			articleStatus = (ArticleStatus) dataAccessService.findEntityById(ArticleStatus.class,
					stockInDetails.getArticleStatus());
			stockInDetails.setArticleName(stockInDetails.getArticle().getName());
			stockInDetails.setArticleCode(stockInDetails.getArticle().getCode());
			stockInDetails.setUniteName(stockInDetails.getArticle().getItemUnite().getName());
		}
		memoReceipt.setStockFinEntryNo(selectedMemo.getStockFinEntryNo());
		memoReceipt.setSupplierName(selectedMemo.getSupplierName());
		memoReceipt.setStockFinEntryHdate(selectedMemo.getStockFinEntryHdate());
	}

	public void addInStockItem(AjaxBehaviorEvent event) {
		if ((supplierId == null) || selectedWareHouse == null) {
			MsgEntry.addErrorMessage("من فضلك اختر المستودع والمورد");
			return;
		}

		try {
			stockInDetails.setArticleId(itemId);
			article = (Article) dataAccessService.findEntityById(Article.class, itemId);
			articleStatus = (ArticleStatus) dataAccessService.findEntityById(ArticleStatus.class, selectedStatus);
			stockInDetails.setArticleStatus(selectedStatus);
			stockInDetails.setUniteName(article.getItemUnite().getName());
			stockInDetails.setArticleName(article.getName());
			stockInDetails.setArticleCode(article.getCode());
			stockInDetails.setArticleStatusName(articleStatus.getArticleStatusName());
			StockInDetailList.add(stockInDetails);
			blockSupplier = true;
			float total = stockInDetails.getPrice() * stockInDetails.getQty();
			stockInDetails.setTotal(total);
			changeTotalPrice();
			stockInDetails = new StockInDetails();
			article = new Article();
			// if (StockInDetailList.size() == 12)
			// allowAdd = false;

		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage("خطا في العملية");
		}

	}

	public void updatestockNoticeNo() {
		if (isCheckReceipt()) {
			stockNoticeNo = null;
		}
	}

	public void onRowEdit(RowEditEvent event) {
		StockInDetails selectedItem = (StockInDetails) event.getObject();
		articlesTotalPrice += selectedItem.getTotal();
		System.out.print("--------------" + articlesTotalPrice);
	}

	public void onRowCancel(RowEditEvent event) {
		StockInDetails selectedItem = (StockInDetails) event.getObject();
		articlesTotalPrice -= selectedItem.getTotal();
		selectedItem.setPrice(0.00f);
	}

	public void loadReceiptDetailsList() {
		tempMstr = (StoreTemporeryReceiptMaster) dataAccessService.getStrTemrReceiptMstrById(tempMstrid);
		rcptStockInDetailList = dataAccessService.getStoreTemporeryReceiptDetailsById(tempMstrid);
		StockInDetailList = new ArrayList<StockInDetails>();
		int i = 0;
		for (StoreTemporeryReceiptDetails item : rcptStockInDetailList) {
			stockInDetails = new StockInDetails();
			stockInDetails.setArticleId(item.getArticleId());
			stockInDetails.setArticleName(dataAccessService.getArticleById(item.getArticleId()).getName());
			stockInDetails.setArticleCode(dataAccessService.getArticleById(item.getArticleId()).getCode());
			stockInDetails.setArticleStatus(item.getArticleStatus());
			stockInDetails.setQty(item.getQty());
			stockInDetails.setQty(item.getQty());
			stockInDetails.setNotes(item.getNotes());
			stockInDetails.setUniteName(dataAccessService.getArticleById(item.getArticleId()).getItemUnite().getName());
			StockInDetailList.add(i, stockInDetails);

		}
		updateData();
	}

	public void changeTotalPrice() {
		Float total = 0f;
		articlesTotalPrice = 0f;
		for (int i = 0; i < StockInDetailList.size(); i++) {
			if (StockInDetailList.get(i).getPrice() != null) {
				StockInDetailList.get(i)
						.setTotal(StockInDetailList.get(i).getPrice() * StockInDetailList.get(i).getQty());
			}
			total = StockInDetailList.get(i).getTotal();
			articlesTotalPrice += total;

		}
	}

	public void removeRecord(StockInDetails stockInDetails) {
		StockInDetailList.remove(stockInDetails);
		changeTotalPrice();
		// if (StockInDetailList.size() < 12)
		// allowAdd = true;
		if (StockInDetailList.size() == 0)
			blockSupplier = false;
	}

	public String printMemoReportAction() {
		String reportName = "/reports/memoReceipt.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("memoReceiptNo", stockEntryMaster.getStockEntryMasterId());// "259306";
		parameters.put("SUBREPORT_DIR", FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/reports/memoReceiptDetails.jasper"));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public String printMemoReport_A4_Action() {
		String reportName = "/reports/memoReceiptNew_A4_report.jrxml";
		return printMemo(reportName, memoReceipt);
	}

	public String printMemoReport() {
		String reportName = "/reports/memoReceiptNew.jrxml";
		return printMemo(reportName, stockEntryMaster);
	}

	private String printMemo(String reportName, StockEntryMaster entryMaster) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		List<StockInDetails> memoEntryDetails = entryMaster.getStockInDetailsList();
		List<StockEntryModel> stockEntryDetails = new ArrayList<StockEntryModel>();
		Integer eleNbr = 0;
		float detTotal = 0f;
		Integer count = 11;
		Integer num = 0;

		Integer ndx = 1;
		float total = 0f;
		for (StockInDetails entryDet : memoEntryDetails) {
			StockEntryModel stockEntryModel = new StockEntryModel();
			eleNbr++;
			num++;
			if (eleNbr.equals(count)) {
				total = detTotal + entryDet.getTotal();
				stockEntryModel.setTotal(total);
				total = detTotal + entryDet.getTotal();
				StockEntryModel stockEntryModelSub = new StockEntryModel();
				stockEntryModelSub.setNum(num);
				stockEntryModelSub.setArtName("ماقبله");
				// stockEntryModel.setAmountInLetters(dataAccessService.getAmountInLetters(total.toString()));
				num++;
				// System.out.println("1 "
				// +stockEntryModel.getAmountInLetters());
				stockEntryModelSub.setTotal(detTotal);
				stockEntryModelSub.setTotalPrice(detTotal);
				stockEntryModelSub.setStrName(memoReceiptModel.getStrName());
				stockEntryModelSub.setSuppName(memoReceiptModel.getSuppName());
				stockEntryModelSub.setEntryByNo(memoReceiptModel.getEntryByNo());
				stockEntryModelSub.setEntryByDAte(memoReceiptModel.getEntryByDAte());
				stockEntryModelSub.setEntryNoticeNo(memoReceiptModel.getEntryNoticeNo());
				stockEntryModelSub.setEntryNoticeDate(memoReceiptModel.getEntryNoticeDate());
				stockEntryModel.setReqName(memoReceiptModel.getReqName());
				stockEntryModel.setREQ_SIGN_DATE(memoReceiptModel.getReqSignDate());
				stockEntryModel.setStoreDeanName(memoReceiptModel.getStoreDeanName());
				stockEntryModel.setSTORE_DEAN_SIGN_DATE(memoReceiptModel.getStoreDeanSignDate());
				stockEntryModel.setStoreMgr(memoReceiptModel.getStoreMgr());
				stockEntryModel.setSTORE_DEAN_SIGN_DATE(memoReceiptModel.getStoreDeanSignDate());
				stockEntryModel.setNotes(entryDet.getNotes());
				stockEntryModelSub.setIndex(ndx++);
				stockEntryDetails.add(stockEntryModelSub);
				eleNbr = 1;
				count = 10;

			} else {
				// count++;
			}
			detTotal += entryDet.getTotal();
			stockEntryModel.setNum(num);
			total = detTotal;
			stockEntryModel.setTotal(detTotal);
			stockEntryModel.setArtCode(entryDet.getArticle().getCode());
			stockEntryModel.setArtName(entryDet.getArticleName());
			stockEntryModel.setUnitPrice(entryDet.getPrice());
			stockEntryModel.setTotalPrice(entryDet.getTotal());
			stockEntryModel.setQty(entryDet.getQty());
			stockEntryModel.setUnitName(entryDet.getUniteName());
			stockEntryModel.setStrName(memoReceiptModel.getStrName());
			stockEntryModel.setSuppName(memoReceiptModel.getSuppName());
			stockEntryModel.setEntryByNo(memoReceiptModel.getEntryByNo());
			stockEntryModel.setEntryByDAte(memoReceiptModel.getEntryByDAte());
			stockEntryModel.setEntryNoticeNo(memoReceiptModel.getEntryNoticeNo());
			stockEntryModel.setEntryNoticeDate(memoReceiptModel.getEntryNoticeDate());
			stockEntryModel.setReqName(memoReceiptModel.getReqName());
			stockEntryModel.setREQ_SIGN_DATE(memoReceiptModel.getReqSignDate());
			stockEntryModel.setStoreDeanName(memoReceiptModel.getStoreDeanName());
			stockEntryModel.setSTORE_DEAN_SIGN_DATE(memoReceiptModel.getStoreDeanSignDate());
			stockEntryModel.setStoreMgr(memoReceiptModel.getStoreMgr());
			stockEntryModel.setSTORE_DEAN_SIGN_DATE(memoReceiptModel.getStoreDeanSignDate());
			stockEntryModel.setNotes(entryDet.getNotes());
			stockEntryModel.setAmountInLetters(dataAccessService.getAmountInLetters(detTotal + ""));
			stockEntryModel.setIndex(ndx++);

			// System.out.println(" 2 " +stockEntryModel.getAmountInLetters());
			stockEntryDetails.add(stockEntryModel);

		}
		int nbr = 10 - ((stockEntryDetails.size() - ((stockEntryDetails.size() / 10) * 10)));
		if (nbr > 0) {
			for (int i = 0; i < nbr; i++) {
				StockEntryModel stockEntryModel = new StockEntryModel();
				stockEntryModel.setTotal(total);
				stockEntryModel.setAmountInLetters(dataAccessService.getAmountInLetters(detTotal + ""));
				stockEntryDetails.add(stockEntryModel);
				stockEntryModel.setReqName(memoReceiptModel.getReqName());
				stockEntryModel.setREQ_SIGN_DATE(memoReceiptModel.getReqSignDate());
				stockEntryModel.setStoreDeanName(memoReceiptModel.getStoreDeanName());
				stockEntryModel.setSTORE_DEAN_SIGN_DATE(memoReceiptModel.getStoreDeanSignDate());
				stockEntryModel.setStoreMgr(memoReceiptModel.getStoreMgr());
				stockEntryModel.setSTORE_DEAN_SIGN_DATE(memoReceiptModel.getStoreDeanSignDate());
			}
		}
		Utils.printPdfReportFromListDataSource(reportName, parameters, stockEntryDetails);
		return "";
	}

	public void updateData() {
		if (tempMstrid != null) {
			selectedWareHouse = tempMstr.getStrNo();
			notifDate = tempMstr.getReceiptHDate();
			supplierId = tempMstr.getSupplierId();
		}
	}

	public void save(AjaxBehaviorEvent abe) {
		try {
			String selectedSupplier = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("supplier");
			String hdate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("startDate");
			String hNotifdate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("notifDate");
			String hbuydate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("buyDate");
			// supplier = (FinEntity)
			// dataAccessService.findEntityById(FinEntity.class,
			// Integer.parseInt(selectedSupplier.trim()));

			try {
				supplier = (FinEntity) dataAccessService.findEntityById(FinEntity.class, supplierId);
				stockEntryMaster.setStockFinEntryHdate(hdate);
				stockEntryMaster.setStockNoticeDate(hNotifdate);
				stockEntryMaster.setStockBuyDate(hbuydate);
				stockEntryMaster.setStockFinYearid(currentFinYear);
				stockEntryMaster.setStockNo(selectedWareHouse);
				stockEntryMaster.setStockDocutypeNo(3);
				if (stockNoticeNo != null) {
					stockEntryMaster.setStockNoticeNo(stockNoticeNo);
				} else {
					stockEntryMaster.setStockNoticeNo(tempMstr.getSpecialNumber());
				}
				stockEntryMaster.setStockSupplierId(supplier.getFinEntityId());
				stockEntryMaster.setUserId(currentUser.getUserId());
				stockEntryMaster.setStockFinEntryGdate(df.parse(HijriCalendarUtil.ConvertHijriTogeorgianDate(hdate)));
			} catch (ParseException e1) {
				MsgEntry.addErrorMessage("خطا فى إدخال البيانات");
				e1.printStackTrace();
			}

			if (StockInDetailList.size() > 0) {

				try {
					stockInMasterId = stockServiceDao.addStockIn(stockEntryMaster, currentUser.getUserId(),
							StockInDetailList);
					stockInDetails = new StockInDetails();
					StockInDetailList = new ArrayList<>();
					article = new Article();
					MsgEntry.addAcceptFlashInfoMessage("تم ارسال طلبك بنجاح");
				} catch (Exception e) {
					MsgEntry.addErrorMessage("خطا فى تنفيذ العملية");
					e.printStackTrace();

				}
			} else {
				MsgEntry.addErrorMessage("من فضلك اضف على الاقل واحد صنف");
			}
		} catch (NumberFormatException e) {
			MsgEntry.addErrorMessage("خطا فى تنفيذ العملية");
			e.printStackTrace();
		}

	}

	public String save() {
		try {
			String selectedSupplier = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("supplier");
			String hdate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("startDate");
			String hNotifdate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("notifDate");
			String hbuydate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("buyDate");
			// supplier = (FinEntity)
			// dataAccessService.findEntityById(FinEntity.class,
			// Integer.parseInt(selectedSupplier.trim()));

			try {
				supplier = (FinEntity) dataAccessService.findEntityById(FinEntity.class, supplierId);
				stockEntryMaster.setStockFinEntryHdate(hdate);
				stockEntryMaster.setStockNoticeDate(hNotifdate);
				stockEntryMaster.setStockBuyDate(hbuydate);
				stockEntryMaster.setStockFinYearid(currentFinYear);
				stockEntryMaster.setStockNo(selectedWareHouse);
				stockEntryMaster.setStockDocutypeNo(3);
				stockEntryMaster.setStockSupplierId(supplier.getFinEntityId());
				stockEntryMaster.setUserId(currentUser.getUserId());
				stockEntryMaster.setStockFinEntryGdate(df.parse(HijriCalendarUtil.ConvertHijriTogeorgianDate(hdate)));
			} catch (ParseException e1) {
				MsgEntry.addErrorMessage("خطا فى إدخال البيانات");
				e1.printStackTrace();
				return "";
			}

			if (StockInDetailList.size() > 0) {

				try {
					if (StockInDetailList.get(0).getPrice() != null
							&& StockInDetailList.get(StockInDetailList.size() - 1) != null) {
						stockInMasterId = stockServiceDao.addStockIn(stockEntryMaster, currentUser.getUserId(),
								StockInDetailList);
						stockInDetails = new StockInDetails();
						StockInDetailList = new ArrayList<>();
						article = new Article();
						MsgEntry.addAcceptFlashInfoMessage("تم ارسال طلبك بنجاح");
					} else {
						MsgEntry.addErrorMessage("يجب تعديل أسعار الأصناف");

						return "";
					}
				} catch (Exception e) {
					MsgEntry.addErrorMessage("خطا فى تنفيذ العملية");
					e.printStackTrace();

				}
			} else {
				MsgEntry.addErrorMessage("من فضلك اضف على الاقل واحد صنف");
			}
		} catch (NumberFormatException e) {
			MsgEntry.addErrorMessage("خطا فى تنفيذ العملية");
			return "";
		}
		return "mails";

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

		memoReceiptList = dataAccessService.searchMemoReceipts(beginDate, finishDate, strNo);

		// exchangeRequestList =
		// dataAccessService.searchExchangeRequests(beginDate, finishDate,
		// strNo);

		// return "";
	}

	public String accept() {

		// String items = "";
		// for (ExchangeRequestDetails detail :
		// request.getExchangeRequestDetailsList()) {
		// items = items + detail.getArticle().getName() + " الكمية: " +
		// detail.getExchangeAtcualyCount() + "\n";
		// }
		// String userComment = wrkAppComment + " الأصناف:" + items;
		wrkAppComment = "";
		applicationPurpose = "1";
		dataAccessService.acceptStockEntry(stockEntryMaster, recordId, MailTypeEnum.MEMO_RECEIPT.getValue(),
				wrkAppComment, Integer.parseInt(applicationPurpose.trim()));
		return "mails";
	}

	public String refuseAction() {
		try {
			applicationPurpose = "2";
			stockEntryMaster.setStockEntryStatus(MyConstants.NO);
			String wrkCommentRefuse = wrkAppComment;
			dataAccessService.refuseMemoReceipt(WrkId, recordId, stockEntryMaster, wrkCommentRefuse,
					Integer.parseInt(applicationPurpose.trim()));
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("refuse.record"));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}

	public void updateSerialNum() {
		// if (stockEntryMaster.getSerialNumber() == null) {
		// stockEntryMaster.setSerialNumber(SerialNum);
		// dataAccessService.save(stockEntryMaster);
		// } else {
		// stockEntryMaster.setSerialNumber(SerialNum);
		dataAccessService.updateObject(stockEntryMaster);
		serialPrintFlag = true;
		// }
		// MsgEntry.addInfoMessage("تم الحفظ");
		System.out.println("New serial number is:---  " + stockEntryMaster.getSerialNumber() + " ---");
	}

	public void cancleUpdateSerialNum() {

		SerialNum = stockEntryMaster.getSerialNumber();
		// MsgEntry.addErrorMessage("تم إلغاء الحفظ");
		System.out.println("New serial number is:---  " + stockEntryMaster.getSerialNumber() + " ---");
	}

	public void showPrintBtn() {
		if (SerialNum != null) {
			allPrint = true;
		} else {
			allPrint = false;
		}
	}

	public String getApplicationPurpose() {
		return applicationPurpose;
	}

	public void setApplicationPurpose(String applicationPurpose) {
		this.applicationPurpose = applicationPurpose;
	}

	public boolean isEnableAccept() {
		return enableAccept;
	}

	public void setEnableAccept(boolean enableAccept) {
		this.enableAccept = enableAccept;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public ArcUsers getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(ArcUsers currentUser) {
		this.currentUser = currentUser;
	}

	public Integer getStockInMasterId() {
		return stockInMasterId;
	}

	public void setStockInMasterId(Integer stockInMasterId) {
		this.stockInMasterId = stockInMasterId;
	}

	public StockEntryMaster getStockEntryMaster() {
		return stockEntryMaster;
	}

	public void setStockEntryMaster(StockEntryMaster stockEntryMaster) {
		this.stockEntryMaster = stockEntryMaster;
	}

	public Integer getSelectedWareHouse() {
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

	public Integer getSelectedStatus() {
		return selectedStatus;
	}

	public void setSelectedStatus(Integer selectedStatus) {
		this.selectedStatus = selectedStatus;
	}

	public boolean isAllowAdd() {
		return allowAdd;
	}

	public void setAllowAdd(boolean allowAdd) {
		this.allowAdd = allowAdd;
	}

	public List<StockInDetails> getStockInDetailList() {
		return StockInDetailList;
	}

	public void setStockInDetailList(List<StockInDetails> stockInDetailList) {
		StockInDetailList = stockInDetailList;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public StockInDetails getStockInDetails() {
		return stockInDetails;
	}

	public void setStockInDetails(StockInDetails stockInDetails) {
		this.stockInDetails = stockInDetails;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getCurrentFinYear() {
		return currentFinYear;
	}

	public void setCurrentFinYear(Integer currentFinYear) {
		this.currentFinYear = currentFinYear;
	}

	public List<ArticleStatus> getAllArticlesStatus() {
		return allArticlesStatus;
	}

	public void setAllArticlesStatus(List<ArticleStatus> allArticlesStatus) {
		this.allArticlesStatus = allArticlesStatus;
	}

	public Map<String, Integer> getSupplierMap() {
		return supplierMap;
	}

	public void setSupplierMap(Map<String, Integer> supplierMap) {
		this.supplierMap = supplierMap;
	}

	public FinEntity getSupplier() {
		return supplier;
	}

	public void setSupplier(FinEntity supplier) {
		this.supplier = supplier;
	}

	public List<WhsWarehouses> getAllWareHouses() {
		return allWareHouses;
	}

	public void setAllWareHouses(List<WhsWarehouses> allWareHouses) {
		this.allWareHouses = allWareHouses;
	}

	public FinFinancialYear getFinYear() {
		return finYear;
	}

	public void setFinYear(FinFinancialYear finYear) {
		this.finYear = finYear;
	}

	public List<FinFinancialYear> getAllfinYears() {
		return allfinYears;
	}

	public void setAllfinYears(List<FinFinancialYear> allfinYears) {
		this.allfinYears = allfinYears;
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

	public String getWrkAppComment() {
		return wrkAppComment;
	}

	public void setWrkAppComment(String wrkAppComment) {
		this.wrkAppComment = wrkAppComment;
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

	public boolean isEnablePrint() {
		return enablePrint;
	}

	public void setEnablePrint(boolean enablePrint) {
		this.enablePrint = enablePrint;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public List<EmployeeForDropDown> getSupplierList() {
		return supplierList;
	}

	public void setSupplierList(List<EmployeeForDropDown> supplierList) {
		this.supplierList = supplierList;
	}

	public EmployeeForDropDown getOneSupplier() {
		return oneSupplier;
	}

	public void setOneSupplier(EmployeeForDropDown oneSupplier) {
		this.oneSupplier = oneSupplier;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Float getArticlesTotalPrice() {
		return articlesTotalPrice;
	}

	public void setArticlesTotalPrice(Float articlesTotalPrice) {
		this.articlesTotalPrice = articlesTotalPrice;
	}

	public Integer getSerialNum() {
		if (stockEntryMaster.getSerialNumber() != null) {
			SerialNum = stockEntryMaster.getSerialNumber();
		}
		return SerialNum;
	}

	public void setSerialNum(Integer serialNum) {
		SerialNum = serialNum;
	}

	public boolean isAllPrint() {
		return allPrint;
	}

	public void setAllPrint(boolean allPrint) {
		this.allPrint = allPrint;
	}

	public Integer getStrNo() {
		return strNo;
	}

	public void setStrNo(Integer strNo) {
		this.strNo = strNo;
	}

	public List<StockEntryMaster> getMemoReceiptList() {
		return memoReceiptList;
	}

	public void setMemoReceiptList(List<StockEntryMaster> memoReceiptList) {
		this.memoReceiptList = memoReceiptList;
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

	public List<StockEntryMaster> getFilterMemoReceiptList() {
		return filterMemoReceiptList;
	}

	public void setFilterMemoReceiptList(List<StockEntryMaster> filterMemoReceiptList) {
		this.filterMemoReceiptList = filterMemoReceiptList;
	}

	public StockEntryMaster getMemoReceipt() {
		return memoReceipt;
	}

	public void setMemoReceipt(StockEntryMaster memoReceipt) {
		this.memoReceipt = memoReceipt;
	}

	public boolean isBlockSupplier() {
		return blockSupplier;
	}

	public void setBlockSupplier(boolean blockSupplier) {
		this.blockSupplier = blockSupplier;
	}

	public boolean isCheckReceipt() {
		return checkReceipt;
	}

	public void setCheckReceipt(boolean checkReceipt) {
		this.checkReceipt = checkReceipt;
	}

	public List<StoreTemporeryReceiptMaster> getTempMstrList() {
		return tempMstrList;
	}

	public void setTempMstrList(List<StoreTemporeryReceiptMaster> tempMstrList) {
		this.tempMstrList = tempMstrList;
	}

	public Integer getStockNoticeNo() {
		return stockNoticeNo;
	}

	public void setStockNoticeNo(Integer stockNoticeNo) {
		this.stockNoticeNo = stockNoticeNo;
	}

	public StoreTemporeryReceiptMaster getTempMstr() {
		return tempMstr;
	}

	public void setTempMstr(StoreTemporeryReceiptMaster tempMstr) {
		this.tempMstr = tempMstr;
	}

	public List<StoreTemporeryReceiptDetails> getRcptStockInDetailList() {
		return rcptStockInDetailList;
	}

	public void setRcptStockInDetailList(List<StoreTemporeryReceiptDetails> rcptStockInDetailList) {
		this.rcptStockInDetailList = rcptStockInDetailList;
	}

	public Integer getTempMstrid() {
		return tempMstrid;
	}

	public void setTempMstrid(Integer tempMstrid) {
		this.tempMstrid = tempMstrid;
	}

	public String getNotifDate() {
		return notifDate;
	}

	public void setNotifDate(String notifDate) {
		this.notifDate = notifDate;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public boolean isEnableEditMgr() {
		return enableEditMgr;
	}

	public void setEnableEditMgr(boolean enableEditMgr) {
		this.enableEditMgr = enableEditMgr;
	}

	public boolean isSerialPrintFlag() {
		return serialPrintFlag;
	}

	public void setSerialPrintFlag(boolean serialPrintFlag) {
		this.serialPrintFlag = serialPrintFlag;
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

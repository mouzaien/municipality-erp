package com.bkeryah.shared.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.bkeryah.bean.StoreRequestModel;
import com.bkeryah.bean.UserMailObj;
import com.bkeryah.dao.IStockServiceDao;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.Article;
import com.bkeryah.entities.ExchangeRequest;
import com.bkeryah.entities.ExchangeRequestDetails;
import com.bkeryah.entities.HrScenario;
import com.bkeryah.entities.ItemUnite;
import com.bkeryah.entities.TechnicalResponse;
import com.bkeryah.entities.TechnicalUsers;
import com.bkeryah.entities.WhsWarehouses;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.fuel.entities.Car;
import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class ExchangeRequestBean extends ArcScenarioManager {

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private ExchangeRequest request = new ExchangeRequest();
	private List<ExchangeRequestDetails> exchangeRequestDetails = new ArrayList<>();
	private List<ExchangeRequestDetails> deletedExchangeRequestDetails = new ArrayList<>();
	private List<TechnicalResponse> technicalResponseList = new ArrayList<>();
	private ItemUnite unite = new ItemUnite();
	private ExchangeRequestDetails requestDetailsRecord = new ExchangeRequestDetails();
	private TechnicalResponse technicalResponse = new TechnicalResponse();
	private List<ExchangeRequest> exchangeRequestList;
	private StoreRequestModel storeRequestModel = new StoreRequestModel();
	private List<StoreRequestModel> storeRequestModelList = new ArrayList<>();
	private List<ExchangeRequest> filteredExchangeRequestList;
	private String beginDate;
	private String finishDate;
	private List<Article> articleList;
	private Article article = new Article();
	// private Integer itemId;
	private Integer recordId;
	private ArcUsers currentUser;
	private boolean acceptFlag;
	private Integer exchangeRequestId;
	private String wrkAppComment;
	private List<WrkPurpose> wrkPurposes;
	private String applicationPurpose;
	private boolean allowAdd = true;
	private boolean refuseFlag;
	private String wrkReceiver;
	private WrkApplicationId wrkId;
	private UserMailObj selectedInbox;
	private boolean technicalUser;
	private List<TechnicalUsers> technicalList = new ArrayList<TechnicalUsers>();
	private boolean warehouseManager;
	private String notes;
	private Integer stepNum;
	private boolean enableUpdateQty;
	private boolean enableAccept;
	private boolean enableReservation;
	private boolean enableReturn;
	private boolean enableTransfert;
	private boolean enableRefuse;
	private boolean enableRecive;
	private boolean enablePrint;
	private boolean lastStep;
	public boolean signedAutorized = false;
	public Integer SerialNum;
	private String wrkCommentRefuse;

	private String responseText;
	private String appRefusePurpose;
	private List<StoreRequestModel> articlesStockList;
	private StoreRequestModel articleStore;
	// private Integer stockNo;
	private Integer strNo = -1;
	private List<WhsWarehouses> allWareHouses = new ArrayList<WhsWarehouses>();
	private List<WhsWarehouses> wareHousesList = new ArrayList<WhsWarehouses>();
	private boolean stockIsBlocked;
	private boolean canSave;
	private Integer selectedItemId;

	@ManagedProperty(value = "#{stockServiceDao}")
	private IStockServiceDao stockServiceDao;

	private boolean checkType;
	private Integer artType = 0;
	private Integer employerId;
	private boolean chckRtrnArt;
	private WhsWarehouses wrHouse = new WhsWarehouses();
	private boolean serialPrintFlag;

	@PostConstruct
	public void init() {
		typeId = MailTypeEnum.EXCHANGEREQUEST;
		currentUser = Utils.findCurrentUser();
		employerId = currentUser.getUserId();
		setAllWareHouses(stockServiceDao.getStoreDeanWharehouses(currentUser.getUserId()));
		wareHousesList = stockServiceDao.getStoreWharehouses(null);
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		String arcRecordId = (String) httpSession.getAttribute("arcRecord");
		httpSession.removeAttribute("arcRecord");
		// test if in view mode or insert mode
		if (allWareHouses != null && allWareHouses.size() >= 1) {
			strNo = allWareHouses.get(0).getStoreNumber();
		}
		if (arcRecordId != null) {
			selectedInbox = (UserMailObj) httpSession.getAttribute("selectedMail");
			setWrkId(new WrkApplicationId(Integer.parseInt(this.selectedInbox.WrkId), selectedInbox.StepId));
			wrkPurposes = dataAccessService.getAllPurposes();
			recordId = Integer.parseInt(arcRecordId.trim());
			request = (ExchangeRequest) dataAccessService.getExchangeRequestByArchRecordId(recordId);
			// int strNo =
			// request.getExchangeRequestDetailsList().get(0).getArticle().getStrNo();
			// request.setStockNo(strNo);
			String stateExchange = request.getStatus();
			Integer storeDeanId = dataAccessService.loadStoreIdById(request.getStockNo()).getStoreDeanId();
			int fromId = dataAccessService.getUserIdFromWorkAppByIdAndStepId(recordId, 1);
			stepNum = dataAccessService.getStepNumberFromHrSign(recordId);
			ArcUsers fromUser = (ArcUsers) dataAccessService.findEntityById(ArcUsers.class, fromId);
			Integer mgrId = fromUser.getMgrId();

			updateQtyies(request.getStockNo());

			technicalResponseList = dataAccessService.getTechnicalResponsesByRecordId(recordId);
			getResponseMessage();

			HrScenario scenario = (HrScenario) dataAccessService.findEntityById(HrScenario.class, 237);
			technicalList = dataAccessService.loadNewTechnicalUsers();
			for (TechnicalUsers technicalUsers : technicalList) {

				if (technicalUsers.getRoleId().equals(currentUser.getUserId())) {
					technicalUser = true;
					break;
				}
			}

			if (mgrId.equals(currentUser.getUserId()) && stepNum == 1) {
				enableAccept = true;
				enableRefuse = true;
			} else if (currentUser.getUserId().equals(dataAccessService.getPropertiesValue(MyConstants.WAREHOUSE_MGR))
					&& stepNum == scenario.getStepsCount() - 3) {
				enableAccept = true;
				enableRefuse = true;
				enableTransfert = true;
			} else if ((currentUser.getUserId()
					.equals(dataAccessService.getPropertiesValue(MyConstants.WAREHOUSE_RESP_AUTHORITY)))
					|| ((currentUser.getUserId().equals(storeDeanId)) && stepNum == scenario.getStepsCount() - 2)) {
				enableAccept = true;
				enableRefuse = true;
			} else if (technicalUser) {
				enableReturn = true;
			} else if ((stateExchange == null && fromId == currentUser.getUserId())
					|| (stateExchange != null && !stateExchange.trim().equals(MyConstants.YES)
							&& !stateExchange.trim().equals(MyConstants.NO) && fromId == currentUser.getUserId())) {
				enableReservation = true;

			} else if (((currentUser.getUserId().equals(storeDeanId)) && stepNum != scenario.getStepsCount() - 2
					&& stateExchange != null && stateExchange.trim().equals(MyConstants.RESERVED))) {
				enableRecive = true;
				enableRefuse = true;
			} else if ((currentUser.getUserId().equals(storeDeanId)) && stateExchange != null
					&& stateExchange.trim().equals(MyConstants.YES)) {
				enablePrint = true;
				enableRecive = false;
				enableRefuse = false;
			} else if ((stateExchange != null) && (stateExchange.trim().equals(MyConstants.RESERVED)))
				enableRecive = true;
			ArcUsers user = null;
			try {
				user = dataAccessService.getUserNameById(request.getUserId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setArcUser(user);

		} else {

			// articleList = dataAccessService.getAllArticles();

		}
		if (request.getSerialNumber() != null) {
			serialPrintFlag = true;
		}
	}

	private void getResponseMessage() {
		if (technicalResponseList.size() > 0) {

			switch (technicalResponseList.size()) {
			case 1:
				responseText = "هذة المعاملة مرت على العضو الفني مرة واحدة";
				break;
			case 2:
				responseText = "هذة المعاملة مرت على العضو الفني مرتان";
				break;
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:

				responseText = "هذة المعاملة مرت على العضو الفني " + technicalResponseList.size() + "مرات";
				break;
			default:
				responseText = "هذة المعاملة مرت على العضو الفني " + technicalResponseList.size() + "مرة";
				break;
			}
		} else {
			responseText = "";
		}
	}

	private void updateQtyies(Integer strNo) {
		for (ExchangeRequestDetails exchangeDetail : request.getExchangeRequestDetailsList()) {
			storeRequestModelList = dataAccessService.getTransactionsQty(exchangeDetail.getArticle().getId(), strNo);

			if (storeRequestModelList.size() > 0) {
				StoreRequestModel storeRequestModel = (StoreRequestModel) dataAccessService
						.getTransactionsQty(exchangeDetail.getArticle().getId(), strNo).get(0);
				exchangeDetail.setQtyAvailable(storeRequestModel.getQtyAvailable());
				exchangeDetail.setQtyReserved(storeRequestModel.getQtyReserved());
			}
		}
	}

	public String accept() {
		List<ExchangeRequestDetails> reqDetails = request.getExchangeRequestDetailsList();

		for (ExchangeRequestDetails reqDet : reqDetails) {
			if (!cheackQtyDetailsOfRequst(reqDet.getCount(), reqDet.getItemId(), request.getStockNo())) {
				return "";
			}
		}
		// strNo = request.getStockNo();
		dataAccessService.updateObject(request);
		if (loadQtyDetailsOfRequst()) {
			String items = "";
			for (ExchangeRequestDetails detail : request.getExchangeRequestDetailsList()) {
				items = items + detail.getArticle().getName() + " الكمية: " + detail.getExchangeAtcualyCount() + "\n";
			}
			String userComment = wrkAppComment + " الأصناف:" + items;

			dataAccessService.acceptActionforExchangeRequest(recordId, MailTypeEnum.EXCHANGEREQUEST.getValue(), request,
					userComment, Integer.parseInt(applicationPurpose.trim()), enableAccept,
					deletedExchangeRequestDetails);
			return "mails";
		}
		return "";
	}

	public String refuseAction() {
		try {
			request.setStatus(MyConstants.NO);
			wrkCommentRefuse = wrkAppComment;
			dataAccessService.refuseExchangeRequest(wrkId, recordId, request, wrkCommentRefuse,
					Integer.parseInt(applicationPurpose.trim()));
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("refuse.record"));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}

	public String actualyExchangeNavi() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("exchangeRequestRecordId", recordId);
		return "actualyExchange";
	}

	public String guranteeNavi() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("exchangeRequestRecordId", recordId);
		return "gurantee";
	}

	public String save() {
		// if (cheackQtyDetailsOfRequst()) {
		request.setGeneralrequestDate(HijriCalendarUtil.findCurrentHijriDate());
		request.setSectionId(Utils.findCurrentUser().getWrkSectionId());
		request.setRequestTo(1);
		request.setYearId(5);
		request.setGeneralRequestState(1);
		request.setUserId(Utils.findCurrentUser().getUserId());
		// request.setStockNo(strNo);
		if (exchangeRequestDetails.size() > 0) {

			try {
				exchangeRequestId = dataAccessService.addExchangeRequest(request, currentUser.getUserId(),
						exchangeRequestDetails);
				request = new ExchangeRequest();
				exchangeRequestDetails = new ArrayList<>();
				article = new Article();
				MsgEntry.addAcceptFlashInfoMessage("تم ارسال طلبك بنجاح");
			} catch (Exception e) {
				MsgEntry.addErrorMessage("خطا فى تنفيذ العملية");
				e.printStackTrace();

			}
		} else {
			MsgEntry.addErrorMessage("من فضلك اضف على الاقل واحد صنف");
		}
		return "mails";
		// }
		// return "";

	}

	public String redirectAction() {
		if (!wrkReceiver.isEmpty() && Integer.parseInt(wrkReceiver) != 0) {
			try {
				dataAccessService.redirectExchangeRequest(wrkId, Integer.parseInt(wrkReceiver),
						(warehouseManager) ? notes : null);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return "mails";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"اختر جهة ارسال المعاملة ", " اختر جهة ارسال المعاملة"));
			return "";

		}
	}

	public String returnRequestAction() {
		try {
			technicalResponse.setTechnicalID(currentUser.getUserId());
			technicalResponse.setDdeId(recordId);
			technicalResponse.setNote(notes);
			dataAccessService.addNewTechnicalResponse(technicalResponse);

			dataAccessService.redirectExchangeRequest(wrkId,
					Integer.parseInt(dataAccessService.getPropertiesValueById(MyConstants.WAREHOUSE_MANAGER)), notes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return "mails";
	}

	public String reserveRequestAction() {
		try {
			request.setStatus(MyConstants.RESERVED);
			// dataAccessService.updateObject(request);
			dataAccessService.acceptActionforExchangeRequest(recordId, MailTypeEnum.EXCHANGEREQUEST.getValue(), request,
					null, null, false, null);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return "mails";
	}

	public String verifiedQte() {
		for (ExchangeRequestDetails det : request.getExchangeRequestDetailsList()) {
			if (det.getCount() < det.getExchangeAtcualyCount()) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.date"));
				return "";
			}
		}

		return "mails";

	}

	public String deliverRequestAction() {
		if (SerialNum != null && SerialNum > 0) {
			try {
				request.setSerialNumber(SerialNum);
				dataAccessService.updateObject(request);
				dataAccessService.acceptActionforExchangeRequest(recordId, MailTypeEnum.EXCHANGEREQUEST.getValue(),
						request, null, null, false, null);

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

			return "mails";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"يجب إدخال الرقم التسلسلي لهذا الطلب ", "يجب إدخال الرقم التسلسلي لهذا الطلب"));
			return "";
		}
	}

	public void addTenderItem(AjaxBehaviorEvent event) {
		// strNo = article.getStrNo();
		if (requestDetailsRecord.getItemId().equals(0)) {
			MsgEntry.addErrorMessage(" اختر الصنف اولا	");
			return;
		}
		if (!cheackQtyDetailsOfRequst(requestDetailsRecord.getCount(), requestDetailsRecord.getItemId(),
				request.getStockNo())) {
			return;
		}
		canSave = true;
		StoreRequestModel artSt = new StoreRequestModel();
		requestDetailsRecord.setExchangeAtcualyCount(requestDetailsRecord.getCount());
		requestDetailsRecord.setTenderItemName(article.getName());
		try {
			requestDetailsRecord.setUniteName(article.getItemUnite().getName());

			exchangeRequestDetails.add(requestDetailsRecord);
			requestDetailsRecord = new ExchangeRequestDetails();
			article = new Article();
			if (exchangeRequestDetails.size() == 12)
				allowAdd = false;
		} catch (Exception e) {
			canSave = false;
			MsgEntry.addErrorMessage(" اختر الصنف اولا	");
		}

	}

	public void removeRecord(ExchangeRequestDetails requestDetails) {
		exchangeRequestDetails.remove(requestDetails);
		if (exchangeRequestDetails.size() < 12)
			allowAdd = true;
		if (exchangeRequestDetails.size() == 0) {
			canSave = false;
			chckRtrnArt = false;
		}
	}

	// method for
	// print exchange_report from search_exchange_request_view

	public void changeType() {
		// isCheckType();
		// System.out.println("الحالة " + artType);
	}

	public String printExchangeReportAction() {
		Integer strNo = request.getStockNo();
		System.out.println("request.getStrNo()" + request.getStockNo());
		Integer arcstrtype = request.getExchangeRequestDetailsList().get(0).getArticle().getArtType();
		WhsWarehouses whsWarehouses = new WhsWarehouses();
		System.out.println(">>>>>><<<<<<>>" + strNo);
		/// get store to know it's type
		if (strNo != null) { /// طلب رجيع

			whsWarehouses = dataAccessService.loadStoreIdById(strNo);
			System.out.println(">>>>>>>>" + whsWarehouses.getStrType());

		}

		if (whsWarehouses.getStrType() == 2) { /// طلب رجيع
			String reportName = "/reports/exchange_return_items_document.jrxml";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("exchange_req_no", request.getGeneralrequestNumber());
			parameters.put("STORE_NAME", whsWarehouses.getStoreName());
			parameters.put("SUBREPORT_DIR", FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/reports/exchange_return_items_document.jasper"));
			Utils.printPdfReport(reportName, parameters);
		}

		else {/// طلب عادي
			String reportName = "/reports/exchange_report.jrxml";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("exchange_req_no", request.getGeneralrequestNumber());// "259306";
			parameters.put("SUBREPORT_DIR", FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/reports/sub_exchange_report2.jasper"));
			Utils.printPdfReport(reportName, parameters);
		}
		return "";
	}

	public String printExchangeReport_A4_Action() {
		String reportName = "/reports/exchange_Request_A4_Report.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("exchange_req_no", request.getGeneralrequestNumber());// "259306";
		parameters.put("SUBREPORT_DIR", FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/reports/sub_exchange_report2_A4_Model.jasper"));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	// method for
	// print Protection card from search_exchange_request بطاقة عهدة
	public String printProtectionCardReportAction() {
		String reportName = "/reports/protection_card.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userId", request.getUserId());
		parameters.put("artId", -1);
		// parameters.put("exchange_req_no",
		// request.getGeneralrequestNumber());// "259306";
		// parameters.put("exchange_req_no", 455);// "259306";
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	// method for
	// print artical card from search_exchange_request بطاقة صنف
	public String printArticalCardReportAction() {
		String reportName = "/reports/artical_card.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		Integer availablebQty = 0;
		parameters.put("item_Id", selectedItemId);// "259306";
		List<StoreRequestModel> strReqModelList = new ArrayList<StoreRequestModel>();
		List<StoreRequestModel> artHistory = articleStore.getHistoryList();
		for (StoreRequestModel requestdets : artHistory) {
			StoreRequestModel requestModel = new StoreRequestModel();

			requestModel.setTransactionCode(requestdets.getTransactionCode());

			requestModel.setArticleId(articleStore.getArticleId());
			requestModel.setArticleCode(articleStore.getArticleCode());
			requestModel.setArticleName(articleStore.getArticleName());
			requestModel.setArticleUnite(articleStore.getArticleUnite());
			requestModel.setSpecialNumber(requestdets.getSpecialNumber());
			requestModel.setTransactionDate(requestdets.getTransactionDate());
			requestModel.setTransactionCode(requestdets.getTransactionCode());
			requestModel.setSupplierName(requestdets.getSupplierName());
			requestModel.setRequesterName(requestdets.getRequesterName());
			switch (requestModel.getTransactionCode()) {
			case 2:
			case 5://// memoRecript
				requestModel.setQtyInput(requestdets.getQtyOutput());
				availablebQty += requestdets.getQtyOutput();
				requestModel.setQtyAvailable(availablebQty);
				strReqModelList.add(requestModel);
				break;
			case 3:
			case 6:// requestChange
				requestModel.setQtyOutput(requestdets.getQtyOutput());
				availablebQty = availablebQty - requestModel.getQtyOutput();
				requestModel.setQtyAvailable(availablebQty);
				strReqModelList.add(requestModel);
				break;
			case 1:// inventory
				availablebQty = requestdets.getQtyOutput();
				requestModel.setQtyAvailable(availablebQty);
				strReqModelList.add(requestModel);
				break;
			}
		}
		Utils.printPdfReportFromListDataSource(reportName, parameters, strReqModelList);
		return "";
	}

	/*
	 * printSearch_ArticleReportAction
	 **/
	public String printSearch_ArticleReportAction() {
		String reportName = "/reports/search_article.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("articleId", -1);
		parameters.put("strnumber", strNo);// "259306";

		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public void removeRequestDetails(ExchangeRequestDetails requestDetails) {
		// dataAccessService.deleteObject(requestDetails);
		request.getExchangeRequestDetailsList().remove(requestDetails);
		deletedExchangeRequestDetails.add(requestDetails);
	}

	public void updateUnite(AjaxBehaviorEvent event) {
		try {
			if (requestDetailsRecord.getItemId() != null && (requestDetailsRecord.getItemId() > 0)) {
				article = (Article) dataAccessService.findEntityById(Article.class, requestDetailsRecord.getItemId());

				if (request.getStockNo() != null) {
					stockIsBlocked = true;
					// load car for رقم الهيكل ورقم البابا ورقم اللوحة ف
					// الملاحظات
					System.out.println("article.getId() " + article.getId());
					List<Car> carDetails = dataAccessService.loadCarDetailsByArtId(article.getId());
					requestDetailsRecord.setNotes("");
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
						requestDetailsRecord.setNotes(str);
						System.out.println("requestDetailsRecord nootes" + requestDetailsRecord.getNotes());
						System.out.println("nootes" + str);
						str = "";

						Utils.updateUIComponent("includeform:desc");

					} else {

					}
				}

				// request.setStockNo(article.getStrNo());
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"من فضلك اختر  الصنف المطلوب", "من فضلك اختر  الصنف المطلوب"));
			}
		} catch (NullPointerException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"من فضلك اختر  الصنف المطلوب", "من فضلك اختر  الصنف المطلوب"));
			e.printStackTrace();
		}

	}

	public void searchExchangeRequests(AjaxBehaviorEvent e) {
		if (beginDate.length() == 0)
			beginDate = null;
		if (finishDate.length() == 0)
			finishDate = null;
		if ((beginDate != null) && (finishDate != null)
				&& (Utils.reverseDateToNumber(beginDate) > Utils.reverseDateToNumber(finishDate))) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.coherent.dates"));
			return;
		}
		exchangeRequestList = dataAccessService.searchExchangeRequests(beginDate, finishDate, strNo, artType,
				employerId);
		// return "";
	}

	public void searchArticles() {
		articlesStockList = dataAccessService.getTransactionsQty(-1, strNo);
		// return "";
	}

	// make check on QTY (Is QTY is enough or Not )
	public boolean loadQtyDetailsOfRequst() {
		StoreRequestModel storeRequestModelQty = new StoreRequestModel();
		List<ExchangeRequestDetails> reqDetails = request.getExchangeRequestDetailsList();
		for (ExchangeRequestDetails exchangeDetail : reqDetails) {
			StoreRequestModel storeRequestDb = dataAccessService
					.getTransactionsQty(exchangeDetail.getArticle().getId(), request.getStockNo()).get(0);
			storeRequestModelQty = storeRequestDb;

			if ((exchangeDetail.getExchangeAtcualyCount() > (storeRequestModelQty.getQtyAvailable()
					- storeRequestModelQty.getQtyReserved()))
					|| exchangeDetail.getExchangeAtcualyCount() > exchangeDetail.getCount()) {
				MsgEntry.addErrorMessage("رصيد صنف  " + storeRequestModelQty.getArticleName()
						+ "  غير كافي او العدد النهائي للصرف اكثر من المطلوب");
				return false;
			}
		}
		return true;
	}

	public boolean cheackQtyDetailsOfRequst(Integer qty, Integer artId, Integer storeId) {
		StoreRequestModel storeRequestModelQty = new StoreRequestModel();
		storeRequestModelQty = (dataAccessService.getTransactionsQty(artId, storeId).get(0));
		if (qty > (storeRequestModelQty.getQtyAvailable() - storeRequestModelQty.getQtyReserved())) {
			MsgEntry.addErrorMessage("رصيد صنف  " + storeRequestModelQty.getArticleName() + " غير كافي");
			return false;
		}

		return true;
	}

	public void updateQties() {
		List<ExchangeRequestDetails> reqDetails = request.getExchangeRequestDetailsList();
		for (ExchangeRequestDetails reqDet : reqDetails) {
			if (reqDet.getExchangeAtcualyCount() > (reqDet.getQtyAvailable() - reqDet.getQtyReserved())) {
				reqDet.setExchangeAtcualyCount(reqDet.getQtyAvailable() - reqDet.getQtyReserved());
				MsgEntry.addErrorMessage("رصيد صنف  " + reqDet.getArticle().getName() + " غير كافي");
				return;
			}
		}
	}

	public void loadSelectedExchangeRequest(ExchangeRequest selectedRequest) {
		request = (ExchangeRequest) dataAccessService.findEntityById(ExchangeRequest.class,
				selectedRequest.getGeneralrequestNumber());
		request.setEmpName(selectedRequest.getEmpName());
		updateQtyies(request.getStockNo());
		// RequestContext context = RequestContext.getCurrentInstance();
		// context.execute("PF('exchange_dlg').show()");
	}

	public void updateSerialNum() {

		request.setSerialNumber(SerialNum);
		dataAccessService.updateObject(request);
		serialPrintFlag = true;

	}

	public void cancleUpdateSerialNum() {

		SerialNum = request.getSerialNumber();
		// MsgEntry.addErrorMessage("تم إلغاء الحفظ");
		System.out.println("New serial number is:---  " + request.getSerialNumber() + " ---");
	}

	public void loadAllStoreRequestModel() {
		// articlesList = dataAccessService.getAllArticles();
		// loadAllInventoryList();
		// for (Article article : wha) {
		//
		// }
		for (WhsWarehouses whsWarehouses : allWareHouses) {
			if (whsWarehouses.getStoreNumber() == request.getStockNo())
				stockIsBlocked = whsWarehouses.getInvIsBlocked().equals(1);
		}
		searchArticles();

		refreshPage();
	}

	public void loadAllexchangeRequestList() {
		// articlesList = dataAccessService.getAllArticles();
		// loadAllInventoryList();
		// for (Article article : wha) {
		//
		// }
		// for (WhsWarehouses whsWarehouses : allWareHouses) {
		// if (whsWarehouses.getStoreNumber() == strNo)
		// stockIsBlocked = whsWarehouses.getInvIsBlocked().equals(1);
		// }
		isCheckType();
		exchangeRequestList = dataAccessService.searchExchangeRequests(beginDate, finishDate, strNo, artType,
				employerId);

		// refreshPage();
	}

	public void loadSelectedArticle(StoreRequestModel articleItem) {
		articleStore = new StoreRequestModel();
		selectedItemId = articleItem.getArticleId();
		articleStore.setArticleCode(articleItem.getArticleCode());
		articleStore.setArticleName(articleItem.getArticleName());
		articleStore.setArticleUnite(articleItem.getArticleUnite());
		articleStore.setHistoryList(dataAccessService.getArticleHistory(articleItem.getArticleId(), strNo));
	}

	public String refreshPage() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('vtWidget').clearFilters()");
		return "";

	}

	public void updateArticlesList() {
		// request.setStockNo(request.getStockNo());
		if (wareHousesList != null) {
			for (WhsWarehouses whs : wareHousesList) {
				if (request.getStockNo().equals(whs.getStoreNumber())) {
					setWrHouse(whs);
				}
			}
			articleList = new ArrayList<Article>();
			if (wrHouse.getStrType().equals(2)) {
				articleList = dataAccessService.getAllReturnStoreArticles(request.getStockNo());
				chckRtrnArt = true;
			} else {
				articleList = stockServiceDao.getAllArticlesByStrno(request.getStockNo());
				chckRtrnArt = false;
			}
		}
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public ExchangeRequest getRequest() {
		return request;
	}

	public void setRequest(ExchangeRequest request) {
		this.request = request;
	}

	public List<ExchangeRequestDetails> getExchangeRequestDetails() {
		return exchangeRequestDetails;
	}

	public void setExchangeRequestDetails(List<ExchangeRequestDetails> exchangeRequestDetails) {
		this.exchangeRequestDetails = exchangeRequestDetails;
	}

	public ExchangeRequestDetails getRequestDetailsRecord() {
		return requestDetailsRecord;
	}

	public void setRequestDetailsRecord(ExchangeRequestDetails requestDetailsRecord) {
		this.requestDetailsRecord = requestDetailsRecord;
	}

	public TechnicalResponse getTechnicalResponse() {
		return technicalResponse;
	}

	public void setTechnicalResponse(TechnicalResponse technicalResponse) {
		this.technicalResponse = technicalResponse;
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public ItemUnite getUnite() {
		return unite;
	}

	public void setUnite(ItemUnite unite) {
		this.unite = unite;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
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

	public boolean isAcceptFlag() {
		return acceptFlag;
	}

	public List<WrkPurpose> getWrkPurposes() {
		return wrkPurposes;
	}

	public void setWrkPurposes(List<WrkPurpose> wrkPurposes) {
		this.wrkPurposes = wrkPurposes;
	}

	public String getWrkAppComment() {
		return wrkAppComment;
	}

	public void setWrkAppComment(String wrkAppComment) {
		this.wrkAppComment = wrkAppComment;
	}

	public void setAcceptFlag(boolean acceptFlag) {
		this.acceptFlag = acceptFlag;
	}

	public Integer getExchangeRequestId() {
		return exchangeRequestId;
	}

	public void setExchangeRequestId(Integer exchangeRequestId) {
		this.exchangeRequestId = exchangeRequestId;
	}

	public String getApplicationPurpose() {
		return applicationPurpose;
	}

	public void setApplicationPurpose(String applicationPurpose) {
		this.applicationPurpose = applicationPurpose;
	}

	public boolean isAllowAdd() {
		return allowAdd;
	}

	public void setAllowAdd(boolean allowAdd) {
		this.allowAdd = allowAdd;
	}

	public boolean isRefuseFlag() {
		return refuseFlag;
	}

	public void setRefuseFlag(boolean refuseFlag) {
		this.refuseFlag = refuseFlag;
	}

	public String getWrkReceiver() {
		return wrkReceiver;
	}

	public void setWrkReceiver(String wrkReceiver) {
		this.wrkReceiver = wrkReceiver;
	}

	public WrkApplicationId getWrkId() {
		return wrkId;
	}

	public void setWrkId(WrkApplicationId wrkId) {
		this.wrkId = wrkId;
	}

	public boolean isTechnicalUser() {
		return technicalUser;
	}

	public void setTechnicalUser(boolean technicalUser) {
		this.technicalUser = technicalUser;
	}

	public boolean isWarehouseManager() {
		return warehouseManager;
	}

	public void setWarehouseManager(boolean warehouseManager) {
		this.warehouseManager = warehouseManager;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getStepNum() {

		return stepNum;
	}

	public Integer getSerialNum() {
		if (request.getSerialNumber() != null) {
			SerialNum = request.getSerialNumber();
		}
		return SerialNum;
	}

	public void setSerialNum(Integer serialNum) {

		SerialNum = serialNum;
	}

	public void setStepNum(Integer stepNum) {
		this.stepNum = stepNum;
	}

	public boolean isEnableUpdateQty() {
		return enableUpdateQty;
	}

	public void setEnableUpdateQty(boolean enableUpdateQty) {
		this.enableUpdateQty = enableUpdateQty;
	}

	public List<ExchangeRequestDetails> getDeletedExchangeRequestDetails() {
		return deletedExchangeRequestDetails;
	}

	public void setDeletedExchangeRequestDetails(List<ExchangeRequestDetails> deletedExchangeRequestDetails) {
		this.deletedExchangeRequestDetails = deletedExchangeRequestDetails;
	}

	public boolean isDisableAccept() {
		return enableAccept;
	}

	public void setDisableAccept(boolean disableAccept) {
		this.enableAccept = disableAccept;
	}

	public boolean isEnableReservation() {
		return enableReservation;
	}

	public void setEnableReservation(boolean enableReservation) {
		this.enableReservation = enableReservation;
	}

	public boolean isLastStep() {
		return lastStep;
	}

	public void setLastStep(boolean lastStep) {
		this.lastStep = lastStep;
	}

	public boolean isSignedAutorized() {
		signedAutorized = isCurrUserSignAutorized(this.dataAccessService, this.recordId);
		return signedAutorized;
	}

	public void setSignedAutorized(boolean signedAutorized) {
		this.signedAutorized = signedAutorized;
	}

	public boolean isEnableAccept() {
		return enableAccept;
	}

	public void setEnableAccept(boolean enableAccept) {
		this.enableAccept = enableAccept;
	}

	public boolean isEnableReturn() {
		return enableReturn;
	}

	public void setEnableReturn(boolean enableReturn) {
		this.enableReturn = enableReturn;
	}

	public boolean isEnableTransfert() {
		return enableTransfert;
	}

	public void setEnableTransfert(boolean enableTransfert) {
		this.enableTransfert = enableTransfert;
	}

	public boolean isEnableRefuse() {
		return enableRefuse;
	}

	public void setEnableRefuse(boolean enableRefuse) {
		this.enableRefuse = enableRefuse;
	}

	public boolean isEnableRecive() {
		return enableRecive;
	}

	public void setEnableRecive(boolean enableRecive) {
		this.enableRecive = enableRecive;
	}

	@Override
	public boolean isCurrUserSignAutorized(IDataAccessService dataAccessService, Integer appID) {
		return super.isCurrUserSignAutorized(this.dataAccessService, this.recordId);
	}

	public String getWrkCommentRefuse() {
		return wrkCommentRefuse;
	}

	public void setWrkCommentRefuse(String wrkCommentRefuse) {
		this.wrkCommentRefuse = wrkCommentRefuse;
	}

	public String getAppRefusePurpose() {
		return appRefusePurpose;
	}

	public void setAppRefusePurpose(String appRefusePurpose) {
		this.appRefusePurpose = appRefusePurpose;
	}

	public boolean isEnablePrint() {
		return enablePrint;
	}

	public void setEnablePrint(boolean enablePrint) {
		this.enablePrint = enablePrint;
	}

	public List<ExchangeRequest> getExchangeRequestList() {
		return exchangeRequestList;
	}

	public void setExchangeRequestList(List<ExchangeRequest> exchangeRequestList) {
		this.exchangeRequestList = exchangeRequestList;
	}

	public List<ExchangeRequest> getFilteredExchangeRequestList() {
		return filteredExchangeRequestList;
	}

	public void setFilteredExchangeRequestList(List<ExchangeRequest> filteredExchangeRequestList) {
		this.filteredExchangeRequestList = filteredExchangeRequestList;
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

	public StoreRequestModel getStoreRequestModel() {
		return storeRequestModel;
	}

	public void setStoreRequestModel(StoreRequestModel storeRequestModel) {
		this.storeRequestModel = storeRequestModel;
	}

	public List<TechnicalResponse> getTechnicalResponseList() {
		return technicalResponseList;
	}

	public void setTechnicalResponseList(List<TechnicalResponse> technicalResponseList) {
		this.technicalResponseList = technicalResponseList;
	}

	public List<StoreRequestModel> getArticlesStockList() {
		return articlesStockList;
	}

	public void setArticlesStockList(List<StoreRequestModel> articlesStockList) {
		this.articlesStockList = articlesStockList;
	}

	public StoreRequestModel getArticleStore() {
		return articleStore;
	}

	public void setArticleStore(StoreRequestModel articleStore) {
		this.articleStore = articleStore;
	}

	public List<StoreRequestModel> getStoreRequestModelList() {
		return storeRequestModelList;
	}

	public void setStoreRequestModelList(List<StoreRequestModel> storeRequestModelList) {
		this.storeRequestModelList = storeRequestModelList;
	}

	public List<TechnicalUsers> getTechnicalList() {
		return technicalList;
	}

	public void setTechnicalList(List<TechnicalUsers> technicalList) {
		this.technicalList = technicalList;
	}

	// public Integer getStrNo() {
	// return strNo;
	// }
	//
	// public void setStrNo(Integer strNo) {
	// this.strNo = strNo;
	// }

	public List<WhsWarehouses> getAllWareHouses() {
		return allWareHouses;
	}

	public void setAllWareHouses(List<WhsWarehouses> allWareHouses) {
		this.allWareHouses = allWareHouses;
	}

	public boolean isStockIsBlocked() {
		return stockIsBlocked;
	}

	public void setStockIsBlocked(boolean stockIsBlocked) {
		this.stockIsBlocked = stockIsBlocked;
	}

	public IStockServiceDao getStockServiceDao() {
		return stockServiceDao;
	}

	public void setStockServiceDao(IStockServiceDao stockServiceDao) {
		this.stockServiceDao = stockServiceDao;
	}

	public boolean isCanSave() {
		return canSave;
	}

	public void setCanSave(boolean canSave) {
		this.canSave = canSave;
	}

	public Integer getSelectedItemId() {
		return selectedItemId;
	}

	public void setSelectedItemId(Integer selectedItemId) {
		this.selectedItemId = selectedItemId;
	}

	public boolean isCheckType() {
		if (checkType) {
			setArtType(2);
		} else {
			setArtType(1);
		}
		return checkType;
	}

	public void setCheckType(boolean checkType) {
		this.checkType = checkType;
	}

	public Integer getArtType() {
		return artType;
	}

	public void setArtType(Integer artType) {
		this.artType = artType;
	}

	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public boolean isChckRtrnArt() {
		return chckRtrnArt;
	}

	public void setChckRtrnArt(boolean chckRtrnArt) {
		this.chckRtrnArt = chckRtrnArt;
	}

	public List<WhsWarehouses> getWareHousesList() {
		return wareHousesList;
	}

	public void setWareHousesList(List<WhsWarehouses> wareHousesList) {
		this.wareHousesList = wareHousesList;
	}

	public WhsWarehouses getWrHouse() {
		return wrHouse;
	}

	public void setWrHouse(WhsWarehouses wrHouse) {
		this.wrHouse = wrHouse;
	}

	public Integer getStrNo() {
		return strNo;
	}

	public void setStrNo(Integer strNo) {
		this.strNo = strNo;
	}

	public boolean isSerialPrintFlag() {
		return serialPrintFlag;
	}

	public void setSerialPrintFlag(boolean serialPrintFlag) {
		this.serialPrintFlag = serialPrintFlag;
	}

}

package com.bkeryah.shared.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bkeryah.entities.ActualDisbursement;
import com.bkeryah.entities.ActualDisbursementDetails;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.ExchangeRequest;
import com.bkeryah.entities.ExchangeRequestDetails;
import com.bkeryah.entities.FinFinancialYear;
import com.bkeryah.entities.ItemUnite;
import com.bkeryah.entities.TenderItems;
import com.bkeryah.entities.WhsWarehouses;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class ActualyExchangeBean {

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;

	private ExchangeRequest request = new ExchangeRequest();

	private List<ExchangeRequestDetails> exchangeRequestDetails = new ArrayList<>();

	private ItemUnite unite = new ItemUnite();
	private ActualDisbursementDetails selectedRecord;
	private List<ActualDisbursementDetails> actualDisbursementDetailsList = new ArrayList<>();
	private ActualDisbursementDetails actualDisbursementDetailsRecord = new ActualDisbursementDetails();
	private ActualDisbursement actualDisbursementrequest = new ActualDisbursement();
	private ExchangeRequestDetails requestDetailsRecord = new ExchangeRequestDetails();
	private List<TenderItems> items;

	private TenderItems tenderItem = new TenderItems();

	private Integer itemId;
	private Integer recordId;
	private ArcUsers currentUser;
	private boolean acceptFlag;
	private Integer exchangeRequestRecordId;
	private Integer arcRecordIdForLoad;
	private List<WrkApplication> wrkApplicationsForOld;
	private List<WhsWarehouses> stores;
	private List<FinFinancialYear> finYears;
	private String deptName;
	private String nameOfRecipientEmployee;
	private boolean secodFlagForAccept;
	private String wrkAppComment;
	private List<WrkPurpose> wrkPurposes;
	private String applicationPurpose;

	@PostConstruct
	public void init() {
		currentUser = Utils.findCurrentUser();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		exchangeRequestRecordId = (Integer) httpSession.getAttribute("exchangeRequestRecordId");
		ArcUsers user = null;
		try {
			user = dataAccessService.getUserNameById(request.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setArcUser(user);
		// test if in view mode or insert mode
		if (exchangeRequestRecordId != null) {
			// Integer requestId = Integer.parseInt(exchangeRequestId);

			request = (ExchangeRequest) dataAccessService.getExchangeRequestByArchRecordId(exchangeRequestRecordId);

			exchangeRequestDetails = request.getExchangeRequestDetailsList();
			deptName = request.getArcUser().getWrkSection().getSectionName();
			nameOfRecipientEmployee = request.getArcUser().getFirstName();
			actualDisbursementrequest.setDepartment(request.getArcUser().getWrkSection().getDeptId() + "");
			actualDisbursementrequest.setRecipientEmployee(request.getArcUser().getUserId() + "");
			for (ExchangeRequestDetails exchangeRequestrecord : exchangeRequestDetails) {
				if (exchangeRequestrecord.getExchangeAtcualyCount() > 0) {

					actualDisbursementDetailsRecord.setQuantity(exchangeRequestrecord.getExchangeAtcualyCount());
					TenderItems item = (TenderItems) dataAccessService.findEntityById(TenderItems.class,
							exchangeRequestrecord.getItemId());
					actualDisbursementDetailsRecord.setTenderName(item.getName());
					actualDisbursementDetailsRecord.setTenderItemId(exchangeRequestrecord.getItemId());
					actualDisbursementDetailsRecord.setTenderUnite(item.getUnite().getName());

					actualDisbursementDetailsList.add(actualDisbursementDetailsRecord);
					actualDisbursementDetailsRecord = new ActualDisbursementDetails();
				}
				stores = dataAccessService.getAllStores();
				finYears = dataAccessService.getAllFinYears();
			}
		} else {
			String arcRecordId = (String) httpSession.getAttribute("arcRecord");
			arcRecordIdForLoad = Integer.parseInt(arcRecordId.trim());
			actualDisbursementrequest = dataAccessService.getActualExchangeRequestByArchRecordId(arcRecordIdForLoad);
			// System.out.println("tender item name: "
			// +
			// actualDisbursementrequest.getActualDisbursementDetails().get(0).getTenderItem().getName());
			// wrkApplicationsForOld=dataAccessService.getWrkApplicationListByRecordId(arcRecordIdForLoad);
			Integer parentRecordId = dataAccessService.getArchRecParentFromLinkByRecordId(arcRecordIdForLoad);

			request = (ExchangeRequest) dataAccessService.getExchangeRequestByArchRecordId(parentRecordId);
			WhsWarehouses warehouse = (WhsWarehouses) dataAccessService.findEntityById(WhsWarehouses.class,
					actualDisbursementrequest.getStoreId());
			actualDisbursementrequest.setDeptName(request.getArcUser().getWrkSection().getSectionName());
			actualDisbursementrequest.setUserName(request.getArcUser().getFirstName());
			actualDisbursementrequest.setStoreName(warehouse.getStoreName());
			
			wrkPurposes = dataAccessService.getAllPurposes();
			
			WrkApplication application =dataAccessService.getWrkApplicationRecordById(arcRecordIdForLoad);
			
			Integer userToId =dataAccessService.getNextScenarioUserId(MailTypeEnum.ACTUALEXCHANGE.getValue(), application.getId().getApplicationId(), arcRecordIdForLoad, 2);
			
			if (userToId==0)
				setSecodFlagForAccept(true);
		}

	}

	// public void removeRecord(ActualDisbursementDetails Details) {
	// exchangeRequestDetails.remove(Details);
	// }
	public String accept() {
		
		dataAccessService.acceptActionforActualExchange(arcRecordIdForLoad, MailTypeEnum.ACTUALEXCHANGE.getValue(),
				actualDisbursementrequest.getActualDisbursementDetails(), request.getUserId(), wrkAppComment, Integer.parseInt(applicationPurpose.trim()));

		return "mails";
	}



	public String save() {
		String date = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("startDate");

		if (date != null && !date.equals(null) && !date.isEmpty()) {
			actualDisbursementrequest.setDate(date);
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "من فضلك ادخل التاريخ", "من فضلك ادخل التاريخ"));
			return "";
		}

		actualDisbursementrequest.setGeneralrequestNumber(request.getGeneralrequestNumber());
		actualDisbursementrequest.setDocuTypNo(request.getGeneralrequestNumber());
		try {
			Integer requestId = dataAccessService.addActualyExchangRequest(actualDisbursementrequest,
					request.getUserId(), actualDisbursementDetailsList, exchangeRequestRecordId);

			MsgEntry.addAcceptFlashInfoMessage("تم ارسال طلبك بنجاح");
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage("خطا فى تنفيذ العملية");

			return " ";
		}

	}



	public void updatePrice(ActualDisbursementDetails actualDisbursementDetails) {
		actualDisbursementDetails
				.setTotalPrice(actualDisbursementDetails.getQuantity() * actualDisbursementDetails.getUnitPrice());


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

	public String getWrkAppComment() {
		return wrkAppComment;
	}

	public List<WrkPurpose> getWrkPurposes() {
		return wrkPurposes;
	}

	public String getApplicationPurpose() {
		return applicationPurpose;
	}

	public void setWrkAppComment(String wrkAppComment) {
		this.wrkAppComment = wrkAppComment;
	}

	public void setWrkPurposes(List<WrkPurpose> wrkPurposes) {
		this.wrkPurposes = wrkPurposes;
	}

	public void setApplicationPurpose(String applicationPurpose) {
		this.applicationPurpose = applicationPurpose;
	}

	public ExchangeRequestDetails getRequestDetailsRecord() {
		return requestDetailsRecord;
	}

	public void setRequestDetailsRecord(ExchangeRequestDetails requestDetailsRecord) {
		this.requestDetailsRecord = requestDetailsRecord;
	}

	public List<TenderItems> getItems() {
		return items;
	}

	public void setItems(List<TenderItems> items) {
		this.items = items;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public ItemUnite getUnite() {
		return unite;
	}

	public void setUnite(ItemUnite unite) {
		this.unite = unite;
	}

	public TenderItems getTenderItem() {
		return tenderItem;
	}

	public void setTenderItem(TenderItems tenderItem) {
		this.tenderItem = tenderItem;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public ActualDisbursementDetails getSelectedRecord() {
		return selectedRecord;
	}

	public void setSelectedRecord(ActualDisbursementDetails selectedRecord) {
		this.selectedRecord = selectedRecord;
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

	public List<ActualDisbursementDetails> getActualDisbursementDetailsList() {
		return actualDisbursementDetailsList;
	}

	public void setActualDisbursementDetailsList(List<ActualDisbursementDetails> actualDisbursementDetailsList) {
		this.actualDisbursementDetailsList = actualDisbursementDetailsList;
	}

	public ActualDisbursementDetails getActualDisbursementDetailsRecord() {
		return actualDisbursementDetailsRecord;
	}

	public boolean isSecodFlagForAccept() {
		return secodFlagForAccept;
	}

	public void setSecodFlagForAccept(boolean secodFlagForAccept) {
		this.secodFlagForAccept = secodFlagForAccept;
	}

	public List<WrkApplication> getWrkApplicationsForOld() {
		return wrkApplicationsForOld;
	}

	public String getNameOfRecipientEmployee() {
		return nameOfRecipientEmployee;
	}

	public void setNameOfRecipientEmployee(String nameOfRecipientEmployee) {
		this.nameOfRecipientEmployee = nameOfRecipientEmployee;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public List<WhsWarehouses> getStores() {
		return stores;
	}

	public void setStores(List<WhsWarehouses> stores) {
		this.stores = stores;
	}

	public List<FinFinancialYear> getFinYears() {
		return finYears;
	}

	public void setFinYears(List<FinFinancialYear> finYears) {
		this.finYears = finYears;
	}

	public void setWrkApplicationsForOld(List<WrkApplication> wrkApplicationsForOld) {
		this.wrkApplicationsForOld = wrkApplicationsForOld;
	}

	public Integer getArcRecordIdForLoad() {
		return arcRecordIdForLoad;
	}

	public void setArcRecordIdForLoad(Integer arcRecordIdForLoad) {
		this.arcRecordIdForLoad = arcRecordIdForLoad;
	}

	public void setActualDisbursementDetailsRecord(ActualDisbursementDetails actualDisbursementDetailsRecord) {
		this.actualDisbursementDetailsRecord = actualDisbursementDetailsRecord;
	}

	public Integer getExchangeRequestRecordId() {
		return exchangeRequestRecordId;
	}

	public void setExchangeRequestRecordId(Integer exchangeRequestRecordId) {
		this.exchangeRequestRecordId = exchangeRequestRecordId;
	}

	public ActualDisbursement getActualDisbursementrequest() {
		return actualDisbursementrequest;
	}

	public void setActualDisbursementrequest(ActualDisbursement actualDisbursementrequest) {
		this.actualDisbursementrequest = actualDisbursementrequest;
	}

	public boolean isAcceptFlag() {
		return acceptFlag;
	}

	public void setAcceptFlag(boolean acceptFlag) {
		this.acceptFlag = acceptFlag;
	}

}

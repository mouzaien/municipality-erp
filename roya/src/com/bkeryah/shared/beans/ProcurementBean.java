package com.bkeryah.shared.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.ExchangeRequest;
import com.bkeryah.entities.ExchangeRequestDetails;
import com.bkeryah.entities.ItemUnite;
import com.bkeryah.entities.Procurement;
import com.bkeryah.entities.ProcurementDetails;
import com.bkeryah.entities.TenderItems;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class ProcurementBean {

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
//	private List<ProcurementDetails> procurementDetailsList;
	private ExchangeRequest request = new ExchangeRequest();
	private Procurement procurement = new Procurement();
	private ItemUnite unite = new ItemUnite();
	private List<TenderItems> items;
	private TenderItems tenderItem = new TenderItems();
	private Integer itemId;
	private Integer recordId;
	private ArcUsers currentUser;
	private boolean acceptFlag;
	private String wrkAppComment;
	private List<WrkPurpose> wrkPurposes;
	private String applicationPurpose;
	

	@PostConstruct
	public void init() {
		currentUser = Utils.findCurrentUser();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		recordId = (Integer) httpSession.getAttribute("exchangeRequestRecordId");
		// test if in view mode or insert mode
		if (recordId != null) {
			acceptFlag = false;
			request = (ExchangeRequest) dataAccessService.getExchangeRequestByArchRecordId(recordId);
			procurement.setProcurementDetailsList(new ArrayList<ProcurementDetails>());
			for(ExchangeRequestDetails details : request.getExchangeRequestDetailsList()){
				if(details.getGuranteeActualyCount() > 0)
					procurement.getProcurementDetailsList().add(castExchangeDetailsToProcurementDetails(details));
			}
		}else{
			recordId = Integer.parseInt((String) httpSession.getAttribute("arcRecord"));
			procurement = dataAccessService.loadProcurementByArcRecord(recordId);
			wrkPurposes = dataAccessService.getAllPurposes();
			acceptFlag = true;
		}
	}
	
	/**
	 * Cast an ExchangeRequestDetails's object to a ProcurementDetails
	 * @param exchangeDetails
	 * @return
	 */
	private ProcurementDetails castExchangeDetailsToProcurementDetails(ExchangeRequestDetails exchangeDetails) {
		ProcurementDetails procurementDetails = new ProcurementDetails();
		procurementDetails.setNotes(exchangeDetails.getNotes());
		procurementDetails.setQuantity(exchangeDetails.getGuranteeActualyCount());
		procurementDetails.setTenderItemId(exchangeDetails.getItemId());
		//procurementDetails.setTenderItem(exchangeDetails.getTenderItem());
		return procurementDetails;
	}

	/**
	 * Save procurement
	 * @return
	 */
	public String saveAction() {
			try {
				dataAccessService.saveProcurement(procurement, recordId);
				MsgEntry.addAcceptFlashInfoMessage();
				HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
				session.setAttribute("exchangeRequestRecordId", null);
			} catch (Exception e) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
				e.printStackTrace();

			}
		
		return "mails";
	}
	
	/**
	 * Accept procurement
	 * @return
	 */
	public String acceptAction() {
		try {
			dataAccessService.acceptProcurement(procurement, recordId, wrkAppComment, Integer.parseInt(applicationPurpose.trim()));
			MsgEntry.addAcceptFlashInfoMessage();
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
		return "mails";
	}
	
	public String refuseAction() {
		return "";
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

	public void setAcceptFlag(boolean acceptFlag) {
		this.acceptFlag = acceptFlag;
	}

//	public List<ProcurementDetails> getProcurementDetailsList() {
//		return procurementDetailsList;
//	}
//
//	public void setProcurementDetailsList(List<ProcurementDetails> procurementDetailsList) {
//		this.procurementDetailsList = procurementDetailsList;
//	}

	public Procurement getProcurement() {
		return procurement;
	}

	public void setProcurement(Procurement procurement) {
		this.procurement = procurement;
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

	public String getApplicationPurpose() {
		return applicationPurpose;
	}

	public void setApplicationPurpose(String applicationPurpose) {
		this.applicationPurpose = applicationPurpose;
	}

}

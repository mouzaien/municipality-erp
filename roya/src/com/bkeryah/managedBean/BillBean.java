package com.bkeryah.managedBean;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.bkeryah.entities.PayBillDetails;
import com.bkeryah.entities.PayLicBills;
import com.bkeryah.entities.PayMaster;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@SessionScoped
public class BillBean {

	@ManagedProperty(value="#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private PayLicBills payLicBill = new PayLicBills();
	private List<PayBillDetails> detailsBillList = new ArrayList<>();
	private List<PayMaster> itemsList;
	private String selectedItemId;
	private PayBillDetails selectedItem;
	private Double amount;
	private boolean consultMode;
	private Double totalAmountBill;
	private String url;
	
	/**
	 * Search bill
	 * */
	private List<PayLicBills> billsList;
	private List<PayLicBills> filteredBills;
	
	
	/**
	 * Instantiate a new bill
	 * @return
	 */
	public String addBill(){
		consultMode = false;
		payLicBill = new PayLicBills();
		selectedItemId = null;
		detailsBillList = new ArrayList<>();
		totalAmountBill = 0.0;
		return "bill";
	}
	
	/**
	 * Save a bill
	 * @return
	 */
	public void saveBillAction(){
		if (!checkFields())
			return ;
		try{
			Set<PayBillDetails> detailsSet = new HashSet<PayBillDetails>(detailsBillList);
			payLicBill.setPayBillDetails(detailsSet);
			dataAccessService.saveBill(payLicBill);
			consultMode = true;
			FacesContext.getCurrentInstance().getExternalContext().redirect("bill.xhtml");
//			resetFields();
			
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}
	
	private void resetFields() {
		payLicBill = new PayLicBills();
		detailsBillList = new ArrayList<>();
		totalAmountBill = 0.0;
	}

	/**
	 * Change the expired bill
	 * @return
	 */
	public String changeBillAction(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date billExpireDate = null;
		try {
			billExpireDate = sdf.parse(HijriCalendarUtil.ConvertHijriTogeorgianDate(HijriCalendarUtil.addDaysToHijriDate(payLicBill.getBillDate(), 7)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(1==payLicBill.getBillStatus()){
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("paid.bill"));
			return "";
		} else if (billExpireDate.after(new Date())){
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("not.expired.bill"));
			return "";
		}
		try {
			payLicBill = dataAccessService.changeBillNumber(payLicBill);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.change.bill"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
		return "";
	}
	
	/**
	 * Validate screen's fields
	 * 
	 * @return
	 */
	private boolean checkFields() {
		boolean valid = true;
		// Check required fields
		if ((payLicBill.getBillOwnerName() == null) || (payLicBill.getBillOwnerName().trim().equals(""))) {
			valid = false;
		}
//		if ((payLicBill.getPayInstallNumber() == null) || (payLicBill.getPayInstallNumber() == 0)) {
//			valid = false;
//		}
		if (!valid)
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("required.fields"));
		else if((detailsBillList == null) || (detailsBillList.isEmpty())){
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.details.bill"));
			valid = false;
		}
		return valid;
	}
	
	/**
	 * Add an item to the bill
	 */
	public void addNewItem(){
		if(amount == 0){
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.amount"));
			return;
		}
		PayBillDetails payBillDetails = new PayBillDetails();
		payBillDetails.setAmount(amount);
		payBillDetails.setPayDetails(Integer.parseInt(selectedItemId));
		PayMaster pMaster = getSelectedPayMaster(Integer.parseInt(selectedItemId));
		payBillDetails.setItemLabel(pMaster.getName());
		payBillDetails.setPayMaster(Integer.parseInt(selectedItemId));
		detailsBillList.add(payBillDetails);
		totalAmountBill += amount;
		selectedItemId = null;
		amount = null;
		payLicBill.setPayAmount(totalAmountBill);
		if(payLicBill.getPayInstallNumber() == 0)
			payLicBill.setPayInstallNumber(null);
	}
	
	/**
	 * Find the selected payMaster
	 * @param selectedId
	 * @return
	 */
	private PayMaster getSelectedPayMaster(int selectedId) {
		if((getItemsList() != null) && (!getItemsList().isEmpty())){
			for(PayMaster item : getItemsList()){
				if(item.getAccountNumber() == selectedId)
					return item;
			}
		}
		return null;
	}
	
	/**
	 * Load bill data
	 */
	public void loadSelectedBill() {
		totalAmountBill = 0.0;
		consultMode = true;
		detailsBillList = dataAccessService.loadBillDetails(payLicBill.getBillNumber());
		payLicBill.setPayBillDetails(new HashSet<>(detailsBillList));
		totalAmountBill = payLicBill.getPayAmount();
		if (!detailsBillList.isEmpty()) {
			for (PayBillDetails details : detailsBillList) {
				PayMaster selectedPayMaster = getSelectedPayMaster(details.getPayDetails());
				if (selectedPayMaster != null)
					details.setItemLabel(selectedPayMaster.getName());
			}
		}
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("bill.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Load bills list
	 */
	public void loadAllBills(){
		billsList = dataAccessService.loadAllBills();
	}
	
	/**
	 * Get the reporting URL
	 * @return
	 */
	public String getUrl() {
		return dataAccessService.printDocument("pay003", payLicBill.getBillNumber(), "billno");
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public PayLicBills getPayLicBill() {
		return payLicBill;
	}

	public void setPayLicBill(PayLicBills payLicBill) {
		this.payLicBill = payLicBill;
	}

	public List<PayBillDetails> getDetailsBillList() {
		return detailsBillList;
	}

	public void setDetailsBillList(List<PayBillDetails> detailsBillList) {
		this.detailsBillList = detailsBillList;
	}

	public String getSelectedItemId() {
		return selectedItemId;
	}

	public void setSelectedItemId(String selectedItemId) {
		this.selectedItemId = selectedItemId;
	}

	public PayBillDetails getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(PayBillDetails selectedItem) {
		this.selectedItem = selectedItem;
		if(detailsBillList != null){
			detailsBillList.remove(selectedItem);
			totalAmountBill -= selectedItem.getAmount();
		}
	}

	public List<PayMaster> getItemsList() {
		if ((itemsList == null) || (itemsList.isEmpty()))
			 itemsList = dataAccessService.loadAllPayMasters();
		return itemsList;
	}

	public void setItemsList(List<PayMaster> itemsList) {
		this.itemsList = itemsList;
	}



	public List<PayLicBills> getBillsList() {
		return billsList;
	}

	public void setBillsList(List<PayLicBills> billsList) {
		this.billsList = billsList;
	}

	public List<PayLicBills> getFilteredBills() {
		return filteredBills;
	}

	public void setFilteredBills(List<PayLicBills> filteredBills) {
		this.filteredBills = filteredBills;
	}

	public boolean isConsultMode() {
		return consultMode;
	}

	public void setConsultMode(boolean consultMode) {
		this.consultMode = consultMode;
	}


	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getTotalAmountBill() {
		return totalAmountBill;
	}

	public void setTotalAmountBill(Double totalAmountBill) {
		this.totalAmountBill = totalAmountBill;
	}

	public String printBillReport() {
		String reportName = "/reports/bill.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p1", payLicBill.getBillNumber()+"");
		parameters.put("SUBREPORT_DIR", FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/reports/bill_detail.jasper"));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}
	
	
}
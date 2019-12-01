package com.bkeryah.managedBean.reqfin;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.bkeryah.entities.PayBillDetails;
import com.bkeryah.entities.PayLicBills;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.shared.beans.Scanner;

import groovyjarjarcommonscli.ParseException;
import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class newReplaceFinBean extends Scanner implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private Integer oldBill;
	PayLicBills payLicBill = null;
	private List<PayBillDetails> paylicDetail = null; 
	Date expireDate ;
	Integer newBill;
	@PostConstruct
	public void init() {
		payLicBill = null;
		paylicDetail = null;
	}
	

	



	public Integer getOldBill() {
		return oldBill;
	}



	public void setOldBill(Integer oldBill) {
		this.oldBill = oldBill;
	}



	public Integer getNewBill() {
		return newBill;
	}






	public void setNewBill(Integer newBill) {
		this.newBill = newBill;
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






	public List<PayBillDetails> getPaylicDetail() {
		return paylicDetail;
	}



	public void setPaylicDetail(List<PayBillDetails> paylicDetail) {
		this.paylicDetail = paylicDetail;
	}



	public Date getExpireDate() {
		return expireDate;
	}



	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}



	public String printt() throws java.text.ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date billExpireDate = null;
		payLicBill = dataAccessService.getBillbyBillNumber(getOldBill());
		billExpireDate = sdf.parse(HijriCalendarUtil.ConvertHijriTogeorgianDate(HijriCalendarUtil.addDaysToHijriDate(payLicBill.getBillDate(),30)));
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
		 String reportName = "/reports/bill.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("p1", payLicBill.getBillNumber());
			parameters.put("SUBREPORT_DIR", FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/reports/bill_detail.jasper"));
			Utils.printPdfReport(reportName, parameters);
			return "";
//		if (getOldBill() == null){
//
//			}
//Integer billsadad = dataAccessService.billstatus(getOldBill());
//		if (billsadad > 0 )
//
//		Integer countBill = dataAccessService.getCountBill(getOldBill());
//		if (countBill > 0)
//			 paylic = dataAccessService.getBillbyBillNumber(getOldBill());
//		if (paylic.getBillStatus() == 1)
//
//		
//		paylicDetail = dataAccessService.getBillDetail(getOldBill());
//		expireDate =paylicDetail.get(0).getCreatedIn();
//		
//		
//		Calendar c = Calendar.getInstance();
//		c.setTime((expireDate));
//		c.add(Calendar.DATE, 30);  // number of days to add
//		
//		if(new Date().before(c.getTime()))
//
//		 newBill = dataAccessService.replaceOldBillToNew(getOldBill(),118);
//		
//		
//		
//		 String reportName = "/reports/bill.jasper";
//			Map<String, Object> parameters = new HashMap<String, Object>();
//			parameters.put("p1", newBill);
//			Utils.printPdfReport(reportName, parameters);
//			return "";
//		
		
	}

	
}

package com.bkeryah.managedBean.investment;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.bkeryah.entities.investment.ContractMessage;
import com.bkeryah.service.IDataAccessService;

import service.ISmsService;
import service.SmsService;
import sms.sender.ResponseTypeEnum;
import utilities.MsgEntry;

@ManagedBean
@ViewScoped
public class ContractMessageBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<ContractMessage> contractMsgList = new ArrayList<ContractMessage>();
	private List<ContractMessage> filterContractMsgList;
	ISmsService smsService = new SmsService();
	private String phoneNo = new String();
	private String messageTxt = new String();
	private String tradRecod = new String();

	@PostConstruct
	public void inti() {
		contractMsgList = dataAccessService.getAllContractMessagesList();
	}

	public void sendNewSmsMessage() {
		try {
			smsService = new SmsService();
			String msg = "صاحب السجل رقم : " + tradRecod + "\n" + messageTxt;
			ResponseTypeEnum RESPONSE = smsService.sendMessage(phoneNo, URLEncoder.encode(msg, "UTF-8"));
			System.out.println(messageTxt);
			if (RESPONSE.getError() != 1) {
				MsgEntry.addErrorMessage("لم يتم الأرسال ... ويرجى مراجعة مدير التقنيةوالتأكد من رصيد باقة الرسائل");
			} else {
				MsgEntry.addInfoMessage("تم الإرسال");
			}
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage("لم يتم الإرسال");
		}
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<ContractMessage> getContractMsgList() {
		return contractMsgList;
	}

	public void setContractMsgList(List<ContractMessage> contractMsgList) {
		this.contractMsgList = contractMsgList;
	}

	public List<ContractMessage> getFilterContractMsgList() {
		return filterContractMsgList;
	}

	public void setFilterContractMsgList(List<ContractMessage> filterContractMsgList) {
		this.filterContractMsgList = filterContractMsgList;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getMessageTxt() {
		return messageTxt;
	}

	public void setMessageTxt(String messageTxt) {
		this.messageTxt = messageTxt;
	}

	public String getTradRecod() {
		return tradRecod;
	}

	public void setTradRecod(String tradRecod) {
		this.tradRecod = tradRecod;
	}

}

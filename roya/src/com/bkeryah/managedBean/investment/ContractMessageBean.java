package com.bkeryah.managedBean.investment;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.bkeryah.entities.investment.ContractMessage;
import com.bkeryah.service.IDataAccessService;

@ManagedBean
@ViewScoped
public class ContractMessageBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<ContractMessage> contractMsgList = new ArrayList<ContractMessage>();
	private List<ContractMessage> filterContractMsgList;

	@PostConstruct
	public void inti() {
		contractMsgList = dataAccessService.getAllContractMessagesList();
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

}

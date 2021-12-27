package com.bkeryah.managedBean.investment;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.*;
import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.investment.ContractType;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;

@ManagedBean
@ViewScoped
public class ContractTypeBeen {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private static final Logger logger = LogManager.getLogger(ContractTypeBeen.class);
	private ContractType contType = new ContractType();
	private List<ContractType> ContractTypeList;
	private List<ContractType> ContractTypeListfilter;

	@PostConstruct
	public void init() {
		ContractTypeList = dataAccessService.loadAllContractTypes();
	}

	public void save() {
		try {
			if (contType != null) {
				dataAccessService.save(contType);
				MsgEntry.addAcceptFlashInfoMessage("تم الحفظ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage("تم الغاء الحفظ");

		}
	}

	public void onRowEdit(RowEditEvent event) {
		try {
			ContractType obj = (ContractType) event.getObject();
			dataAccessService.updateObject(obj);
			MsgEntry.addAcceptFlashInfoMessage("تم التعديل");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onRowCancel(RowEditEvent event) {
		ContractType obj = (ContractType) event.getObject();
		MsgEntry.addErrorMessage("تم الإلغاء");
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public ContractType getContType() {
		return contType;
	}

	public void setContType(ContractType contType) {
		this.contType = contType;
	}

	public List<ContractType> getContractTypeList() {
		return ContractTypeList;
	}

	public void setContractTypeList(List<ContractType> contractTypeList) {
		ContractTypeList = contractTypeList;
	}

	public List<ContractType> getContractTypeListfilter() {
		return ContractTypeListfilter;
	}

	public void setContractTypeListfilter(List<ContractType> contractTypeListfilter) {
		ContractTypeListfilter = contractTypeListfilter;
	}

}

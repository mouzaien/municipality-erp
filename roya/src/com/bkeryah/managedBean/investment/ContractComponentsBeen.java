package com.bkeryah.managedBean.investment;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.*;
import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.investment.ContractComponents;
import com.bkeryah.entities.investment.ContractType;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;

@ManagedBean
@ViewScoped
public class ContractComponentsBeen {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private static final Logger logger = LogManager.getLogger(ContractTypeBeen.class);
	private ContractComponents contComponent = new ContractComponents();
	private List<ContractComponents> ContractComponentList;
	private List<ContractComponents> ContractComponentfilter;
	private List<ContractType> ContractTypeList;

	@PostConstruct
	public void init() {
		ContractComponentList = dataAccessService.loadAllContractComponents();
		ContractTypeList = dataAccessService.loadAllContractTypes();
	}

	public List<ContractType> getContractTypeList() {
		return ContractTypeList;
	}

	public void setContractTypeList(List<ContractType> contractTypeList) {
		ContractTypeList = contractTypeList;
	}

	public void save() {
		try {
			if (contComponent != null) {
				dataAccessService.save(contComponent);
				MsgEntry.addAcceptFlashInfoMessage("تم الحفظ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage("تم الغاء الحفظ");

		}
	}

	public void onRowEdit(RowEditEvent event) {
		try {
			ContractComponents obj = (ContractComponents) event.getObject();
			dataAccessService.updateObject(obj);
			MsgEntry.addAcceptFlashInfoMessage("تم التعديل");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onRowCancel(RowEditEvent event) {
		ContractComponents obj = (ContractComponents) event.getObject();
		MsgEntry.addErrorMessage("تم الإلغاء");
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public ContractComponents getContComponent() {
		return contComponent;
	}

	public void setContComponent(ContractComponents contComponent) {
		this.contComponent = contComponent;
	}

	public List<ContractComponents> getContractComponentList() {
		return ContractComponentList;
	}

	public void setContractComponentList(List<ContractComponents> contractComponentList) {
		ContractComponentList = contractComponentList;
	}

	public List<ContractComponents> getContractComponentfilter() {
		return ContractComponentfilter;
	}

	public void setContractComponentfilter(List<ContractComponents> contractComponentfilter) {
		ContractComponentfilter = contractComponentfilter;
	}
}

package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.logging.log4j.*;

import com.bkeryah.entities.DocumentsType;
import com.bkeryah.entities.WrkDept;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class AttachmentsBean {
	private static Logger logger = LogManager.getLogger(AttachmentsBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private DocumentsType documnt = new DocumentsType();
	private List<DocumentsType> documntList = new ArrayList<DocumentsType>();
	private List<DocumentsType> filtereddocumntList;
	private String name;

	@PostConstruct
	public void init() {
		documntList = dataAccessService.findAllDecmount();
	}

	// Edit
	public void updateDeocumnt() {
		try {
			if (documnt != null) {
				dataAccessService.updateObject(documnt);
				documnt = new DocumentsType();
				logger.info("Update User: id: " + documnt.getId());
			}
			MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ العملية");
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	// DElETE
	public void deleteDocumnt() {
		try {
			if (documnt != null) {
				dataAccessService.deleteObject(documnt);
				documnt = new DocumentsType();

			}
			MsgEntry.addInfoMessage("تم الحذف");
		} catch (Exception e) {

			MsgEntry.addErrorMessage("لم يتم الحذف");
		}
	}

	// add
	public void saveDocumnt() {
		try {
			if (documnt != null) {
				documnt = new DocumentsType();
				documnt.setName(name);
				dataAccessService.save(documnt);
				documntList = dataAccessService.findAllDecmount();
			}
			MsgEntry.addInfoMessage("تم الإضافة");

		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public DocumentsType getDocumnt() {
		return documnt;
	}

	public void setDocumnt(DocumentsType documnt) {
		this.documnt = documnt;
	}

	public List<DocumentsType> getDocumntList() {
		return documntList;
	}

	public void setDocumntList(List<DocumentsType> documntList) {
		this.documntList = documntList;
	}

	public List<DocumentsType> getFiltereddocumntList() {
		return filtereddocumntList;
	}

	public void setFiltereddocumntList(List<DocumentsType> filtereddocumntList) {
		this.filtereddocumntList = filtereddocumntList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

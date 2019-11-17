/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.managedBean;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.codehaus.plexus.util.FileUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import com.bkeryah.bean.ArcApplicationTypeClass;
import com.bkeryah.bean.WrkArchiveRecipentClass;
import com.bkeryah.bean.WrkLetterFromClass;
import com.bkeryah.bean.WrkLetterToClass;
import com.bkeryah.bean.WrkPurposeClass;
import com.bkeryah.dao.CommonDao;
import com.bkeryah.dao.ICommonDao;
import com.bkeryah.entities.ArcAttach;
import com.bkeryah.entities.ArcRecAtt;
import com.bkeryah.entities.ArcRecAttId;
import com.bkeryah.entities.ArcRecordLinking;
import com.bkeryah.entities.ArcRecordLinkingId;
import com.bkeryah.entities.ArcRecords;
import com.bkeryah.entities.ArcRecordsExtension;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.DocumentsType;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.entities.WrkLetterFrom;
import com.bkeryah.model.AttachmentModel;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.shared.beans.Scanner;

import utilities.MsgEntry;
import utilities.Utils;

/**
 *
 * @author Ibrahimdarwiesh
 */
/**
 * @author wael
 *
 */
@ManagedBean
@ViewScoped
public class NewIncomeBean extends Scanner implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Creates a new instance of NewIncomeBean
	 */
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;

	private ICommonDao commonDao = new CommonDao();
	// Map<String, String> ApplicatioTypeMap = new HashMap<String, String>();
	// Map<String, String> LetterFromMap = new HashMap<String, String>();
	// Map<String, String> LetterToMap = new HashMap<String, String>();
	// Map<String, String> LetterSendToMap = new HashMap<String, String>();
	// Map<String, String> LetterSendPurposeMap = new HashMap<String, String>();

	private ArcRecords newRecord = new ArcRecords();
	private WrkApplication newWrkApplication = new WrkApplication();

	private String NewArcRecordId;
	private String NewIncomeTitle;
	private int NewIncomeApplicationType;
	private int NewIncomeReciever;
	private int NewIncomeSender;
	private String NewIncomeNoFromSender;
	private String NewIncomeDateFromSender;
	private Integer NewIncomeNoRefered;
	private String NewIncomeMobileNo;
	private boolean NewIncomeIsImportant;
	private String NewIncomeComment;
	private int NewIncomeSendTo;
	private int NewIncomeSendToPurpose;
	private List<InputStream> uplodedList;
	private boolean citizenFlag;
	private List<Integer> copyList = new ArrayList<>();
	private ArcRecordsExtension recordsExtension = new ArcRecordsExtension();
	private String newIncomeFrom;
	private int newIncomeFromId;
	private List<ArcAttach> previousAttachList = new ArrayList<>();
	List<ArcApplicationTypeClass> listApplicationType;
	List<WrkLetterToClass> listLetterTo;
	List<WrkArchiveRecipentClass> listWrkArchiveRecipt;
	List<WrkPurposeClass> listWrkPurpose;
	List<DocumentsType> documentsTypes;
	private ArcUsers currentUser;

	/**
	 * Print letter report
	 */
	public String printBarcodeReport() {
		printBarcodeReport(Integer.parseInt(NewArcRecordId), 1);
		return "";
	}

	public List<InputStream> getUplodedList() {
		return uplodedList;
	}

	public void setUplodedList(List<InputStream> uplodedList) {
		this.uplodedList = uplodedList;
	}

	@PostConstruct
	public void init() {
		currentUser = Utils.findCurrentUser();
		//documentsTypes = dataAccessService.getAllDocumentsType();
	}

	/**
	 * *****************************************************************
	 */
	private boolean savedClicked;

	public String SaveIncome() {
		savedClicked = true;
		String letterFormDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("startDate");
		if (!letterFormDate.isEmpty()) {
			newRecord.setLetterFromDate(letterFormDate);
		} else {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.date"));
		}
		if ((newRecord.getLetterFromDate() == null) || (newRecord.getLetterFromDate().trim().equals(""))) {
			MsgEntry.addErrorAspectToComponent("autorizationDate");
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.date"));
		}
		try {

			if (this.NewIncomeNoRefered != null && this.NewIncomeNoRefered > 0) {

				boolean result = dataAccessService.isIncomeExist(this.NewIncomeNoRefered);
				if (!result) {
					MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("letter.refer.not.found"));
				}
			}
			uploadFilesToTmpDirectory();
			Integer toUserId = newWrkApplication.getToUserId();
			if (toUserId == null || toUserId.equals(0))
				newWrkApplication.setToUserId(currentUser.getUserId());
			dataAccessService.saveNewIncome(newRecord, newWrkApplication, attachList, copyList, true);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.send.outcome"));
			NewArcRecordId = "" + newRecord.getId();
			if (this.NewIncomeNoRefered != null && this.NewIncomeNoRefered > 0) {
				ArcRecordLinkingId arcRecordLinkingId = new ArcRecordLinkingId(
						Integer.parseInt(newRecord.getIncomeNo()), this.NewIncomeNoRefered);
				ArcRecordLinking arcRecordLinking = new ArcRecordLinking();
				arcRecordLinking.setId(arcRecordLinkingId);
				dataAccessService.saveObject(arcRecordLinking);
			}
			if (!previousAttachList.isEmpty()) {
				for (ArcAttach arcAttach : previousAttachList) {

					if (arcAttach.getAttachName().equalsIgnoreCase("309") && arcAttach.getId() < 1
							&& arcAttach.getAttachCategory().equalsIgnoreCase("DATA")) {
						dataAccessService.save(arcAttach);
					}

					ArcRecAttId arcRecAttId = new ArcRecAttId(Integer.parseInt(NewArcRecordId), arcAttach.getId());
					ArcRecAtt arcRecAtt = new ArcRecAtt();
					arcRecAtt.setId(arcRecAttId);
					dataAccessService.saveObject(arcRecAtt);

				}
			}

			// resetFields();

			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('print-barcode-dlg').show()");
			
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
		return "";
	}

	@SuppressWarnings("null")
	public String addtransAttachs() {
		try {
			uploadFilesToTmpDirectory();
			// dataAccessService.addtransAttachs(newRecord.getId(), null,
			// attachList);
			Integer toUserId = newWrkApplication.getToUserId();
			if (toUserId != null || !(toUserId.equals(currentUser.getUserId()))) {

				dataAccessService.saveNewIncome(newRecord, newWrkApplication, attachList, copyList, false);

				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.send.outcome"));
				NewArcRecordId = "" + newRecord.getId();
				if (this.NewIncomeNoRefered != null && this.NewIncomeNoRefered > 0) {
					ArcRecordLinkingId arcRecordLinkingId = new ArcRecordLinkingId(
							Integer.parseInt(newRecord.getIncomeNo()), this.NewIncomeNoRefered);
					ArcRecordLinking arcRecordLinking = new ArcRecordLinking();
					arcRecordLinking.setId(arcRecordLinkingId);
					dataAccessService.saveObject(arcRecordLinking);
				}

				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("success.operation"));
				resetFields();
			} else {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			}

		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
		return "mails";
	}

	private void resetFields() {
		newRecord = new ArcRecords();
		newWrkApplication = new WrkApplication();
		attachList = new ArrayList<>();
		NewIncomeNoRefered = null;
		NewIncomeNoFromSender = null;
		NewIncomeIsImportant = false;
		previousAttachList = new ArrayList<>();
		copyList = new ArrayList<>();

	}

	public void showAddFromDlg(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('add-from-dlg').show();");
	}

	public void saveNewFrom(ActionEvent ae) {
		WrkLetterFrom lf = new WrkLetterFrom();
		lf.setLetterFromName(newIncomeFrom);
		newIncomeFromId = dataAccessService.save(lf);
		newIncomeFrom = "";

		newRecord.setLetterFrom(newIncomeFromId);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('add-from-dlg').hide();");
	}

	public void changeReferencedLetter() {
		if (this.NewIncomeNoRefered != null && this.NewIncomeNoRefered > 0) {
			previousAttachList = dataAccessService.getAttachmentByIncomeNumber(NewIncomeNoRefered);
		}

	}

	/**
	 * getter and setter
	 */

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public ArcRecords getNewRecord() {
		return newRecord;
	}

	public void setNewRecord(ArcRecords newRecord) {
		this.newRecord = newRecord;
	}

	public WrkApplication getNewWrkApplication() {
		return newWrkApplication;
	}

	public void setNewWrkApplication(WrkApplication newWrkApplication) {
		this.newWrkApplication = newWrkApplication;
	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public List<AttachmentModel> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<AttachmentModel> attachList) {
		this.attachList = attachList;
	}

	public String getNewArcRecordId() {
		return NewArcRecordId;
	}

	public void setNewArcRecordId(String NewArcRecordId) {
		this.NewArcRecordId = NewArcRecordId;
	}

	public String getNewIncomeTitle() {
		return NewIncomeTitle;
	}

	public void setNewIncomeTitle(String NewIncomeTitle) {
		this.NewIncomeTitle = NewIncomeTitle;
	}

	public int getNewIncomeApplicationType() {
		return NewIncomeApplicationType;
	}

	public void setNewIncomeApplicationType(int NewIncomeApplicationType) {
		this.NewIncomeApplicationType = NewIncomeApplicationType;
	}

	public int getNewIncomeReciever() {
		return NewIncomeReciever;
	}

	public void setNewIncomeReciever(int NewIncomeReciever) {
		this.NewIncomeReciever = NewIncomeReciever;
	}

	public int getNewIncomeSender() {
		return NewIncomeSender;
	}

	public void setNewIncomeSender(int NewIncomeSender) {
		this.NewIncomeSender = NewIncomeSender;
	}

	public String getNewIncomeNoFromSender() {
		return NewIncomeNoFromSender;
	}

	public void setNewIncomeNoFromSender(String NewIncomeNoFromSender) {
		this.NewIncomeNoFromSender = NewIncomeNoFromSender;
	}

	public String getNewIncomeDateFromSender() {
		return NewIncomeDateFromSender;
	}

	public void setNewIncomeDateFromSender(String NewIncomeDateFromSender) {
		this.NewIncomeDateFromSender = NewIncomeDateFromSender;
	}

	public List<Integer> getCopyList() {
		return copyList;
	}

	public ArcRecordsExtension getRecordsExtension() {
		return recordsExtension;
	}

	public void setRecordsExtension(ArcRecordsExtension recordsExtension) {
		this.recordsExtension = recordsExtension;
	}

	public void setCopyList(List<Integer> copyList) {
		this.copyList = copyList;
	}

	public boolean isCitizenFlag() {
		return citizenFlag;
	}

	public void setCitizenFlag(boolean citizenFlag) {
		this.citizenFlag = citizenFlag;
	}

	public Integer getNewIncomeNoRefered() {
		return NewIncomeNoRefered;
	}

	public void setNewIncomeNoRefered(Integer NewIncomeNoRefered) {
		this.NewIncomeNoRefered = NewIncomeNoRefered;
	}

	public String getNewIncomeMobileNo() {
		return NewIncomeMobileNo;
	}

	public void setNewIncomeMobileNo(String NewIncomeMobileNo) {
		this.NewIncomeMobileNo = NewIncomeMobileNo;
	}

	public boolean isNewIncomeIsImportant() {
		return NewIncomeIsImportant;
	}

	public void setNewIncomeIsImportant(boolean NewIncomeIsImportant) {
		this.NewIncomeIsImportant = NewIncomeIsImportant;
	}

	public String getNewIncomeComment() {
		return NewIncomeComment;
	}

	public void setNewIncomeComment(String NewIncomeComment) {
		this.NewIncomeComment = NewIncomeComment;
	}

	public int getNewIncomeSendTo() {
		return NewIncomeSendTo;
	}

	public void setNewIncomeSendTo(int NewIncomeSendTo) {
		this.NewIncomeSendTo = NewIncomeSendTo;
	}

	public int getNewIncomeSendToPurpose() {
		return NewIncomeSendToPurpose;
	}

	public void setNewIncomeSendToPurpose(int NewIncomeSendToPurpose) {
		this.NewIncomeSendToPurpose = NewIncomeSendToPurpose;
	}

	public String getNewIncomeFrom() {
		return newIncomeFrom;
	}

	public void setNewIncomeFrom(String newIncomeFrom) {
		this.newIncomeFrom = newIncomeFrom;
	}

	public int getNewIncomeFromId() {
		return newIncomeFromId;
	}

	public void setNewIncomeFromId(int newIncomeFromId) {
		this.newIncomeFromId = newIncomeFromId;
	}

	public List<ArcAttach> getPreviousAttachList() {
		return previousAttachList;
	}

	public void setPreviousAttachList(List<ArcAttach> previousAttachList) {
		this.previousAttachList = previousAttachList;
	}

	/**
	 * return list of application type
	 * 
	 * @return
	 */
	public List<ArcApplicationTypeClass> getListApplicationType() {
		return dataAccessService.findAllApplicationTypes();
	}

	public void setListApplicationType(List<ArcApplicationTypeClass> listApplicationType) {
		this.listApplicationType = listApplicationType;
	}

	/**
	 * 
	 * load list of letter from
	 * 
	 * @return
	 */

	/**
	 * load list letter to
	 * 
	 * @return
	 */
	public List<WrkLetterToClass> getListLetterTo() {
		return dataAccessService.findAllAWrkLetterTos();
	}

	public void setListLetterTo(List<WrkLetterToClass> listLetterTo) {
		this.listLetterTo = listLetterTo;
	}

	/**
	 * load list of wrk archive reciept
	 * 
	 * @return
	 */
	public List<WrkArchiveRecipentClass> getListWrkArchiveRecipt() {
		return dataAccessService.findAllWrkArchiveRecipents();
	}

	public void setListWrkArchiveRecipt(List<WrkArchiveRecipentClass> listWrkArchiveRecipt) {
		this.listWrkArchiveRecipt = listWrkArchiveRecipt;
	}

	/**
	 * load wrk purpose list
	 * 
	 * @return
	 */
	public List<WrkPurposeClass> getListWrkPurpose() {
		return dataAccessService.findAllWrkPurpose(Utils.findCurrentUser().getUserId());
	}

	public void setListWrkPurpose(List<WrkPurposeClass> listWrkPurpose) {
		this.listWrkPurpose = listWrkPurpose;
	}

	public List<DocumentsType> getDocumentsTypes() {
		return documentsTypes;
	}

	public void setDocumentsTypes(List<DocumentsType> documentsTypes) {
		this.documentsTypes = documentsTypes;
	}

	public boolean isSavedClicked() {
		return savedClicked;
	}

	public void setSavedClicked(boolean savedClicked) {
		this.savedClicked = savedClicked;
	}

}

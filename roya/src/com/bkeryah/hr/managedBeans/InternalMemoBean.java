package com.bkeryah.hr.managedBeans;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.bkeryah.bean.UserContactClass;
import com.bkeryah.entities.ArcAttach;
import com.bkeryah.entities.ArcRecords;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.DocumentsType;
import com.bkeryah.entities.WrkApplication;
import com.bkeryah.entities.WrkLetterFrom;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.entities.WrkRefrentionalSetting;
import com.bkeryah.model.AttachmentModel;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.shared.beans.Scanner;
import com.bkeryah.testssss.EmployeeForDropDown;

import utilities.MsgEntry;
import utilities.Utils;

/**
 * This class is responsible for internal memory
 * 
 * @author Ghoyouth
 *
 */
@ManagedBean
@ViewScoped
public class InternalMemoBean extends Scanner {
	protected static final Logger logger = Logger.getLogger(InternalMemoBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private WrkApplication wrkApplication = new WrkApplication();
	private List<WrkPurpose> wrkPurposes;
	private ArcRecords arcRecord = new ArcRecords();
	private List<WrkRefrentionalSetting> allManagers = new ArrayList<WrkRefrentionalSetting>();
	private List<ArcAttach> attachs = new ArrayList<ArcAttach>();
	private String applicationPurpose;
	private int wrkReciever;
	private boolean checkAllDeptEmpsFlag;
	private boolean checkAllDeptEmpsFlagMngr;
	private List<String> WrkCopyEmpRecievers = new ArrayList<String>();
	private List<String> WrkCopyMngRecievers = new ArrayList<String>();
	private AttachmentModel selectedItem;
	private List<UserContactClass> listOfEmpsInDept;
	private List<UserContactClass> managersList;
	private ArcUsers currentUser;
	private Integer currArcId;
	private List<DocumentsType> documentsTypes;
	private String newIncomeFrom;

	// /**
	// * Initializing data
	// */
	//
	public void init() {
		attachList = new ArrayList<>();
		setCurrentUser(Utils.findCurrentUser());
		setWrkPurposes(dataAccessService.getAllPurposes());
		documentsTypes = dataAccessService.getAllDocumentsType();
		setAllManagers(dataAccessService.getAllManagersFromWFS());
		listOfEmpsInDept = dataAccessService.findAllEmployeesInDept(currentUser.getUserId());
		List<EmployeeForDropDown> specialEMpList = dataAccessService.getSpecialEmployeeList();
		for (EmployeeForDropDown emp : specialEMpList) {
			if (!listOfEmpsInDept.contains(emp.getId()))
				listOfEmpsInDept.add(new UserContactClass(emp.getId(), emp.getName()));
		}
		managersList = dataAccessService.findAllManagers(currentUser.getUserId());
	}

	/**
	 * Check all managers
	 */
	public void checkAllListnerForManagers() {
		if (checkAllDeptEmpsFlagMngr == true) {
			WrkCopyMngRecievers = managersList.stream().map(UserContactClass::getUserIdStr)
					.collect(Collectors.toList());
		} else {
			WrkCopyMngRecievers.clear();
		}
	}

	/**
	 * Check all employees
	 */
	public void checkAllListner() {
		if (checkAllDeptEmpsFlag == true) {
			WrkCopyEmpRecievers = listOfEmpsInDept.stream().map(UserContactClass::getUserIdStr)
					.collect(Collectors.toList());//
			// empMapInDept.entrySet().stream().map(cont
			// cont.getValue()).collect(Collectors.toList());
		} else {
			WrkCopyEmpRecievers.clear();
		}
	}

	//
	/**
	 * Select manager receiver
	 */
	public void selectMngReceiver() {
		wrkApplication.setToUserId(wrkReciever);
	}

	/**
	 * Select employee receiver
	 */
	public void selectEmpReceiver() {
		wrkApplication.setToUserId(wrkReciever);
	}

	public void upload() {
		if (file != null) {
			FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	/**
	 * Save internal memory
	 *
	 * @return
	 */
	private boolean savedClicked;

	public String saveMemo() {
		if (wrkApplication.getToUserId() != null && wrkApplication.getToUserId() != 0) {
			uploadFilesToTmpDirectory();
			attachs = Utils.SaveAttachementsToFtpServer(attachList);

			wrkApplication.setApplicationPurpose(
					(applicationPurpose != null) ? Integer.parseInt(applicationPurpose.trim()) : null);
			try {
				List<String> copyListInteger = new ArrayList<String>();
				copyListInteger.addAll(WrkCopyEmpRecievers);
				copyListInteger.addAll(WrkCopyMngRecievers);

				dataAccessService.saveMemo(wrkApplication, arcRecord, attachs, copyListInteger, false);

				currArcId = arcRecord.getId();
				// * print barcode
//				if (arcRecord.isOutcomingNumFlag()) {
//					RequestContext context = RequestContext.getCurrentInstance();
//					context.execute("PF('print-barcode-dlg').show()");
//					logger.info("saveMemo success");
//				} else {
//					logger.info("saveMemo success");
//					return "mails";
//				}
			} catch (Exception e) {
				logger.error("error saveMemo" + arcRecord.getId() + " " + e.toString());
				return "";
			} finally {
//				 wrkApplication = new WrkApplication();
//				 arcRecord = new ArcRecords();
			}
			savedClicked = !savedClicked;
			return "mails";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Utils.loadMessagesFromFile("select.receiver"), Utils.loadMessagesFromFile("select.receiver")));
			return "";
		}
		
	}

	// /***
	// * check outcoming
	// */

	public void updateButtons(AjaxBehaviorEvent event) {
		savedClicked = !savedClicked;
	}

	//
	// /**
	// * save to be sent after
	// */
	public String saveMemBefore() {
		uploadFilesToTmpDirectory();
		attachs = Utils.SaveAttachementsToFtpServer(attachList);
		wrkApplication.setToUserId(currentUser.getUserId());
		wrkApplication.setApplicationPurpose(
				(applicationPurpose != null) ? Integer.parseInt(applicationPurpose.trim()) : null);
		try {
			List<String> copyListInteger = new ArrayList<String>();

			dataAccessService.saveMemo(wrkApplication, arcRecord, attachs, copyListInteger, true);

			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			wrkApplication.setToUserId(null);
			currArcId = arcRecord.getId();
			// * print barcode
			if (arcRecord.isOutcomingNumFlag()) {
				arcRecord.setOutcomingNumFlag(false);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('print-barcode-dlg').show()");
				logger.info("saveMemo success");
			} else {
				logger.info("saveMemo success");
			}
		} catch (Exception e) {
			logger.error("saveMemo" + arcRecord.getId() + " " + e.toString());
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("error.operation"));
		} finally {
		}
		savedClicked = !savedClicked;
		return "";
	}

	/**
	 * add new letter from
	 */
	public void saveNewFrom(ActionEvent ae) {
		WrkLetterFrom lf = new WrkLetterFrom();
		lf.setLetterFromName(newIncomeFrom);
		arcRecord.setLetterFrom(dataAccessService.save(lf));
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('add-from-dlg').hide();");
	}

	/**
	 * Getters and setters
	 */

	public WrkApplication getWrkApplication() {
		return wrkApplication;
	}

	public void setWrkApplication(WrkApplication wrkApplication) {
		this.wrkApplication = wrkApplication;
	}

	//
	public ArcRecords getArcRecord() {
		return arcRecord;
	}

	public void setArcRecord(ArcRecords arcRecord) {
		this.arcRecord = arcRecord;
	}

	//
	public List<WrkPurpose> getWrkPurposes() {
		return wrkPurposes;
	}

	//
	public void setWrkPurposes(List<WrkPurpose> wrkPurposes) {
		this.wrkPurposes = wrkPurposes;
	}

	public String getApplicationPurpose() {
		return applicationPurpose;
	}

	public void setApplicationPurpose(String applicationPurpose) {
		this.applicationPurpose = applicationPurpose;
	}

	public List<AttachmentModel> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<AttachmentModel> attachList) {
		this.attachList = attachList;
	}

	public List<WrkRefrentionalSetting> getAllManagers() {
		return allManagers;
	}

	public void setAllManagers(List<WrkRefrentionalSetting> allManagers) {
		this.allManagers = allManagers;
	}

	public List<ArcAttach> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<ArcAttach> attachs) {
		this.attachs = attachs;
	}

	public ArcUsers getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(ArcUsers currentUser) {
		this.currentUser = currentUser;
	}

	public AttachmentModel getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(AttachmentModel selectedItem) {
		this.selectedItem = selectedItem;
		if (attachList != null) {
			attachList.remove(selectedItem);
		}
	}

	public List<UserContactClass> getListOfEmpsInDept() {
		return listOfEmpsInDept;
	}

	public void setListOfEmpsInDept(List<UserContactClass> listOfEmpsInDept) {
		this.listOfEmpsInDept = listOfEmpsInDept;
	}

	//
	public List<UserContactClass> getManagersList() {
		return managersList;
	}

	public void setManagersList(List<UserContactClass> managersList) {
		this.managersList = managersList;
	}

	//
	public int getWrkReciever() {
		return wrkReciever;
	}

	public void setWrkReciever(int wrkReciever) {
		this.wrkReciever = wrkReciever;
	}

	//
	public List<String> getWrkCopyEmpRecievers() {
		return WrkCopyEmpRecievers;
	}

	public List<String> getWrkCopyMngRecievers() {
		return WrkCopyMngRecievers;
	}

	public void setWrkCopyEmpRecievers(List<String> wrkCopyEmpRecievers) {
		WrkCopyEmpRecievers = wrkCopyEmpRecievers;
	}

	public void setWrkCopyMngRecievers(List<String> wrkCopyMngRecievers) {
		WrkCopyMngRecievers = wrkCopyMngRecievers;
	}

	public boolean isCheckAllDeptEmpsFlag() {
		return checkAllDeptEmpsFlag;
	}

	public boolean isCheckAllDeptEmpsFlagMngr() {
		return checkAllDeptEmpsFlagMngr;
	}

	public void setCheckAllDeptEmpsFlag(boolean checkAllDeptEmpsFlag) {
		this.checkAllDeptEmpsFlag = checkAllDeptEmpsFlag;
	}

	public void setCheckAllDeptEmpsFlagMngr(boolean checkAllDeptEmpsFlagMngr) {
		this.checkAllDeptEmpsFlagMngr = checkAllDeptEmpsFlagMngr;
	}

	public List<DocumentsType> getDocumentsTypes() {
		return documentsTypes;
	}

	public void setDocumentsTypes(List<DocumentsType> documentsTypes) {
		this.documentsTypes = documentsTypes;
	}

	public String getNewIncomeFrom() {
		return newIncomeFrom;
	}

	public void setNewIncomeFrom(String newIncomeFrom) {
		this.newIncomeFrom = newIncomeFrom;
	}

	public boolean isSavedClicked() {
		return savedClicked;
	}

	public void setSavedClicked(boolean savedClicked) {
		this.savedClicked = savedClicked;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	/**
	 * print barcode out
	 *
	 * @return
	 */
	public String printBarcode() {
		printBarcodeReport(currArcId, 2);
		return "";
	}
}

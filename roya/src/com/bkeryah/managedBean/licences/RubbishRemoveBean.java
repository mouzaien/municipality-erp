package com.bkeryah.managedBean.licences;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

import com.bkeryah.bean.ArcAttachmentClass;
import com.bkeryah.entities.ArcPeople;
import com.bkeryah.entities.ArcPeopleModel;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.PayBillDetails;
import com.bkeryah.entities.PayLicBills;
import com.bkeryah.entities.licences.BldLicHangover;
import com.bkeryah.entities.licences.BldLicPcs;
import com.bkeryah.model.AttachmentModel;
import com.bkeryah.penalties.LicTrdMasterFile;
import com.bkeryah.penalties.ReqFinesDetails;
import com.bkeryah.penalties.ReqFinesMaster;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.shared.beans.Scanner;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class RubbishRemoveBean extends Scanner implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private BldLicHangover bldLicHangover = new BldLicHangover();
	private List<ArcPeopleModel> filteredArcPeoples;
	private ArcPeopleModel selectedPeople;
	private List<ArcUsers> usersList;
	private boolean consultMode;
	private List<BldLicHangover> certificatesList;
	private List<BldLicHangover> filteredCertificates;
	private PayLicBills newBill;
	private List<PayBillDetails> billDetailList;
	private String urlCertificate;
	private boolean activatePrint;
	private List<ArcAttachmentClass> attList = new ArrayList<ArcAttachmentClass>();

//	@PostConstruct
//	public void init() {
//		usersList = dataAccessService.getAllActiveEmployeesInDept(MyConstants.PLANNING_DEPT_ID);
//		certificatesList = dataAccessService.loadAllRubbishCertificate();
//		FacesContext context = FacesContext.getCurrentInstance();
//		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
//		HttpSession httpSession = HttpRequest.getSession(false);
//		String arcRecordId = (String) httpSession.getAttribute("arcRecord");
//		httpSession.removeAttribute("arcRecord");
//		// test if in view mode or insert mode
//		if (arcRecordId != null) {
//			recordId = Integer.parseInt(arcRecordId);
//			newBuilding = dataAccessService.getNewBuildingLicenceByArcRecordId(recordId);
//			owner = (ArcPeople) dataAccessService.loadArcPeople(Long.parseLong(newBuilding.getLicNewAplOwnerId()));
//			wrkPurposes = dataAccessService.getAllPurposes();
//			usersList = dataAccessService.getAllActiveEmployeesInDept(MyConstants.PLANNING_DEPT_ID);
//			planningManager = (Utils.findCurrentUser().getUserId() == dataAccessService.getPropertiesValue(MyConstants.PLANNING_DEPT_MGR));
//			engineeringOffice = (Utils.findCurrentUser().getUserId() == dataAccessService.getUserIdFromWorkAppByIdAndStepId(recordId, 1));
//			
//			Integer acceptCount = dataAccessService.getHrsSignNextStep(recordId);
//			HrsScenarioDocument scenario = dataAccessService.getDestinationModel(MyConstants.NEW_BUILDING_TYPE, acceptCount+1);
//			if ((scenario != null) && (scenario.getSigned() == 1))
//				beforeLastStep = true;
//			else if(newBuilding.getLicNewBillNumber() != null){
//				PayLicBills bill = dataAccessService.loadBillByLicNo(newBuilding.getLicNewId());
//				if((scenario == null) && (bill.getBillStatus() == 1 ))
//					activatePrint = true;
//			}
//						
//		}else{
//			newBuilding.setPieces(new ArrayList<BldLicPcs>());
//		}
//	}
	
	public void onRowSelect(SelectEvent event) {
		selectedPeople = (ArcPeopleModel) event.getObject();
	}
	
	public void addPiece(){
		bldLicHangover.getPieces().add(new BldLicPcs(bldLicHangover.getLicHangoverId()));
	}
	
	public void loadAllCertificates(){
		certificatesList = dataAccessService.loadAllRubbishCertificate();
	}
	
	public String chooseCertificate(BldLicHangover certificate) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("rubbishCertificateId", certificate.getLicHangoverId());
		return "remove_rubbish_certificate";
	}
	
	public void loadSelectedCertificate(){
		usersList = dataAccessService.getAllActiveEmployeesInDept(MyConstants.PLANNING_DEPT_ID);
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		Integer rubbishCertificateId = (Integer) httpSession.getAttribute("rubbishCertificateId");
		if(rubbishCertificateId != null){
			httpSession.removeAttribute("rubbishCertificateId");
			bldLicHangover = dataAccessService.loadRubbishCertificate(rubbishCertificateId);
			consultMode = true;
			selectedPeople = new ArcPeopleModel((ArcPeople) dataAccessService.loadArcPeople(Long.parseLong(bldLicHangover.getLicHangoverAplOwner())));
			PayLicBills bill = dataAccessService.loadBillByLicNo(bldLicHangover.getLicHangoverId());
			if((bill != null) && (bill.getBillStatus() == 1 ))
				activatePrint = true;
			attList = dataAccessService.findAttachmentFilesByArcId(""+dataAccessService.getRecIdMasterTBLByLicenceId(bldLicHangover.getLicHangoverId()));
		}else{
			bldLicHangover.setPieces(new ArrayList<BldLicPcs>());
		}
	}
	
	public String checkLic() {
		bldLicHangover.setLicHangoverAplOwner(selectedPeople.getNationalId().toString());
		return "";
	}
	
	public String saveRubbishRemoveCertificateAction(){
		try{
			if(consultMode){
				dataAccessService.updateRubbishRemoveCertificate(bldLicHangover, billDetailList, attachList);
			}else{
				String title = MessageFormat.format(Utils.loadMessagesFromFile("rubbish.certificate.title"), selectedPeople.getCompleteName(), HijriCalendarUtil.findCurrentHijriDate());
				generateBill();
				dataAccessService.saveRubbishRemoveCertificate(bldLicHangover, title, newBill, billDetailList, attachList);
				resetFields();
			}
			
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("saveRubbishRemoveCertificateAction	" + e.getMessage());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}
	
	private void resetFields() {
		bldLicHangover = new BldLicHangover();
		selectedPeople = new ArcPeopleModel();
		newBill = new PayLicBills();
		billDetailList = new ArrayList<>();
		attachList = new ArrayList<>();
	}

	private void calcBillDetails(){
		billDetailList = new ArrayList<>();
		PayBillDetails det = new PayBillDetails();
		PayBillDetails det1 = new PayBillDetails();
		switch (bldLicHangover.getLicHangoverBldUse()) {
		case 1://sakani
			
			det.setPayMaster(1422106);
			det.setPayDetails(1422106);
			det.setAmount(200d);
			billDetailList.add(det);
			
			
			det1.setPayMaster(1422109);
			det1.setPayDetails(1422109);
			det1.setAmount(20d);
			billDetailList.add(det1);
			break;
			
		case 2://tijari
			det.setPayMaster(1422106);
			det.setPayDetails(1422106);
			det.setAmount(400d);
			billDetailList.add(det);
			
			det1.setPayMaster(1422109);
			det1.setPayDetails(1422109);
			det1.setAmount(20d);
			billDetailList.add(det1);
			break;
			
		case 11://sakani tijari
			det.setPayMaster(1422106);
			det.setPayDetails(1422106);
			det.setAmount(300d);
			billDetailList.add(det);
			
			det1.setPayMaster(1422109);
			det1.setPayDetails(1422109);
			det1.setAmount(20d);
			billDetailList.add(det1);
			break;

		default:
			break;
		}		
	}
	
	private void generateBill() {
		double sum = 0.0;
		calcBillDetails();
		newBill = new PayLicBills();
		newBill.setBillOwnerName(selectedPeople.getCompleteName());
		newBill.setLicenceNumber(bldLicHangover.getLicHangoverId());
		
		newBill.setLicenceType("B");
		newBill.setBillStatus(0);
		newBill.setBillPayType("C");
		
		for (PayBillDetails det : billDetailList) {
			sum += det.getAmount();
		}
		newBill.setPayAmount(sum);
	}
	
	public void uploadFile(FileUploadEvent event) {

		try {
			AttachmentModel attach = new AttachmentModel();
			attach.setAttachRealName(event.getFile().getFileName());
			attach.setRealName(event.getFile().getFileName());
			attach.setAttachSize(event.getFile().getSize());

			attach.setAttachStream(event.getFile().getInputstream());
			attach.setAttachExt(FilenameUtils.getExtension(event.getFile().getFileName()));
			attach.setRealName(Utils.generateRandomName() + "." + attach.getAttachExt());
			attachList.add(attach);

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("lpcDlgMessage",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, Utils.loadMessagesFromFile("no.upload"), ""));
		}

	}

	public String uploadFileFromScanner() {

		File file = new File("C:\\Windows\\Archiving\\image.pdf");
		FileInputStream fis = null;

		try {

			fis = new FileInputStream(file);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		try {
			AttachmentModel attach = new AttachmentModel();
			attach.setAttachRealName(file.getName());
			attach.setAttachSize(file.length());

			attach.setAttachStream(fis);
			attach.setAttachExt(FilenameUtils.getExtension("mage.pdf"));
			attachList.add(attach);

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("lpcDlgMessage",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, Utils.loadMessagesFromFile("no.upload"), ""));
		}

		return "";
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public BldLicHangover getBldLicHangover() {
		return bldLicHangover;
	}

	public void setBldLicHangover(BldLicHangover bldLicHangover) {
		this.bldLicHangover = bldLicHangover;
	}

	public List<ArcPeopleModel> getFilteredArcPeoples() {
		return filteredArcPeoples;
	}

	public void setFilteredArcPeoples(List<ArcPeopleModel> filteredArcPeoples) {
		this.filteredArcPeoples = filteredArcPeoples;
	}

	public ArcPeopleModel getSelectedPeople() {
		return selectedPeople;
	}

	public void setSelectedPeople(ArcPeopleModel selectedPeople) {
		this.selectedPeople = selectedPeople;
	}

	public List<ArcUsers> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<ArcUsers> usersList) {
		this.usersList = usersList;
	}

	public boolean isConsultMode() {
		return consultMode;
	}

	public void setConsultMode(boolean consultMode) {
		this.consultMode = consultMode;
	}

	public List<BldLicHangover> getCertificatesList() {
		return certificatesList;
	}

	public void setCertificatesList(List<BldLicHangover> certificatesList) {
		this.certificatesList = certificatesList;
	}

	public List<BldLicHangover> getFilteredCertificates() {
		return filteredCertificates;
	}

	public void setFilteredCertificates(List<BldLicHangover> filteredCertificates) {
		this.filteredCertificates = filteredCertificates;
	}

	public PayLicBills getNewBill() {
		return newBill;
	}

	public void setNewBill(PayLicBills newBill) {
		this.newBill = newBill;
	}

	public List<PayBillDetails> getBillDetailList() {
		return billDetailList;
	}

	public void setBillDetailList(List<PayBillDetails> billDetailList) {
		this.billDetailList = billDetailList;
	}

	public String getUrlCertificate() {
		if(bldLicHangover.getLicHangoverId() == null)
			return "";
		return dataAccessService.printDocument("BLD019.rdf", bldLicHangover.getLicHangoverId(), "P1");
	}

	public void setUrlCertificate(String urlCertificate) {
		this.urlCertificate = urlCertificate;
	}

	public boolean isActivatePrint() {
		return activatePrint;
	}

	public void setActivatePrint(boolean activatePrint) {
		this.activatePrint = activatePrint;
	}
	
	public List<AttachmentModel> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<AttachmentModel> attachList) {
		this.attachList = attachList;
	}

	public List<ArcAttachmentClass> getAttList() {
		return attList;
	}

	public void setAttList(List<ArcAttachmentClass> attList) {
		this.attList = attList;
	}
}

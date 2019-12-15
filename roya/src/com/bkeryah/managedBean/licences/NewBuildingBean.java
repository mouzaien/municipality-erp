package com.bkeryah.managedBean.licences;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.io.FilenameUtils;
import org.codehaus.plexus.util.IOUtil;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.bkeryah.entities.ArcPeople;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.HrsScenarioDocument;
import com.bkeryah.entities.PayBillDetails;
import com.bkeryah.entities.PayLicBills;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.entities.licences.BldLicNew;
import com.bkeryah.entities.licences.BldLicPcs;
import com.bkeryah.model.AttachmentModel;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.shared.beans.Scanner;

import utilities.CalcFeesUtil;
import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class NewBuildingBean extends Scanner implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private BldLicNew newBuilding = new BldLicNew();
	private ArcPeople owner = new ArcPeople();
	private List<WrkPurpose> wrkPurposes;
	private String applicationPurpose;
	private List<ArcUsers> usersList;
	private int recordId;
	private boolean engineeringOffice;
	private boolean planningManager;
	private boolean activatePrint;
	private boolean beforeLastStep;
	private boolean lastStep;
	private PayLicBills newBill = new PayLicBills();
	private List<PayBillDetails> selectedDetailList = new ArrayList<>();
//	private List<PayBillDetails> buildingResult;
	private String newBillNmber;
	private String url;
	private String wrkAppComment;
	private StreamedContent image = null;
	private StreamedContent krokiImage = null;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		String arcRecordId = (String) httpSession.getAttribute("arcRecord");
		httpSession.removeAttribute("arcRecord");
		// test if in view mode or insert mode
		if (arcRecordId != null) {
			recordId = Integer.parseInt(arcRecordId);
			newBuilding = dataAccessService.getNewBuildingLicenceByArcRecordId(recordId);
//			newBuilding.setPieces(new ArrayList<BldLicPcs>());
			owner = (ArcPeople) dataAccessService.loadArcPeople(Long.parseLong(newBuilding.getLicNewAplOwnerId()));
			wrkPurposes = dataAccessService.getAllPurposes();
			usersList = dataAccessService.getAllActiveEmployeesInDept(MyConstants.PLANNING_DEPT_ID);
			planningManager = (Utils.findCurrentUser().getUserId() == dataAccessService.getPropertiesValue(MyConstants.PLANNING_DEPT_MGR));
			engineeringOffice = (Utils.findCurrentUser().getUserId() == dataAccessService.getUserIdFromWorkAppByIdAndStepId(recordId, 1));
			
			Integer acceptCount = dataAccessService.getHrsSignNextStep(recordId);
			HrsScenarioDocument scenario = dataAccessService.getDestinationModel(MyConstants.NEW_BUILDING_TYPE, acceptCount+1);
			if (scenario == null)
				lastStep = true;
			else if (scenario.getSigned() == 1)
				beforeLastStep = true;
			
			if(newBuilding.getLicNewBillNumber() != null){
				PayLicBills bill = dataAccessService.loadBillByLicNo(newBuilding.getLicNewId());
				if((scenario == null) && (bill.getBillStatus() == 1 ))
					activatePrint = true;
			}
						
		}else{
			newBuilding.setPieces(new ArrayList<BldLicPcs>());
		}
	}
	
	public String saveAction(){
		try{
//			String hdate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("startDate");
//			newBuilding.setLicNewSakDate(hdate);
			String title = MessageFormat.format(Utils.loadMessagesFromFile("new.building.title"),
					HijriCalendarUtil.findCurrentHijriDate());
			dataAccessService.saveNewBuilding(newBuilding, title, attachList);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			resetFields();
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("saveAction	" + e.getMessage());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}
	
	public String acceptAction(){
		try{
			if((planningManager) && ((attachList == null) || (attachList.isEmpty()))){
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.add.kroki"));
				return "";
			}
			if(beforeLastStep)
				saveBill();
			dataAccessService.acceptNewBuilding(recordId, newBuilding, attachList, !planningManager);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			resetFields();
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("saveAction	" + e.getMessage());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}
	
	public String refuseAction() {
		try {
//			newBuilding.(MyConstants.NO);
//			/**** begin create new application **/
//			WrkApplication newApplication = createNewWrkAppForRefuse();
//			/*** end **/
//			refuseModel(newApplication, employeeVacation);
//			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("refuse.record"));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}
	
	private void saveBill() {
		double sum = 0.0;
		calculateLicenceFees();
		newBill.setBillOwnerName(owner.getCompleteName());
		newBill.setLicenceNumber(newBuilding.getLicNewId());
		for (PayBillDetails billDetail : selectedDetailList) {
			sum = sum + billDetail.getAmount();
		}
		newBill.setPayAmount(sum);
		setNewBillNmber(dataAccessService.saveBill(newBill, selectedDetailList).toString());
		newBuilding.setLicNewBillNumber(newBillNmber);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('PrintBillDlgVar').show();");

	}
	
	public void calculateArea(){
		if((newBuilding.getLicNewRedroomArea() != null) && (newBuilding.getLicNewBldGroundArea() != null) && (newBuilding.getLicNewBldRepeatingNum() != null) && (newBuilding.getLicNewBldRepeatingArea() != null) && (newBuilding.getLicNewAttGroundArea() != null) && (newBuilding.getLicNewAttUpperArea() != null))
			newBuilding.setLicNewBldTotalArea(newBuilding.getLicNewRedroomArea() + newBuilding.getLicNewBldGroundArea()+ (newBuilding.getLicNewBldRepeatingNum()*newBuilding.getLicNewBldRepeatingArea())+newBuilding.getLicNewAttGroundArea()+newBuilding.getLicNewAttUpperArea());
	}
	
	public void calculateLicenceFees() {
		switch (newBuilding.getLicNewBldUse().intValue()) {
		//sacany
		case 1:
			selectedDetailList = CalcFeesUtil.calculateBuildingFees(1, 1, 1,
					newBuilding.getLicNewBldTotalArea(), newBuilding.getLicNewWallTotalLength());
			break;
			//sacany tijari
		case 11:
			selectedDetailList = CalcFeesUtil.calculateBuildingFees(1, 1, 2,
					newBuilding.getLicNewBldTotalArea(), newBuilding.getLicNewWallTotalLength());
			break;
			//tijari
		case 2:
			selectedDetailList = CalcFeesUtil.calculateBuildingFees(1, 1, 3,
					newBuilding.getLicNewBldTotalArea(), newBuilding.getLicNewWallTotalLength());
			break;
			//binaa hokomi
		case 12:
			selectedDetailList = CalcFeesUtil.calculateTelecomTowerFees(newBuilding.getLicNewRequestType(), 1);
			break;

			//sarraf
		case 14:
			selectedDetailList = CalcFeesUtil.calculateATMFees(1, 0, 0,0);
			break;
			//borj jawal
		case 15:
			selectedDetailList = CalcFeesUtil.calculateTelecomTowerFees(newBuilding.getLicNewRequestType(), 1);
			break;
		default:
			break;
		}
	}
	
	public String printBarcodeReport() {

		String reportName = "/reports/bill.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p1", newBillNmber);
		parameters.put("name_customer", dataAccessService.findSystemProperty("CUSTOMER_NAME"));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}
	
	private void resetFields() {
		newBuilding = new BldLicNew();
		attachList = new ArrayList<>();
		owner = new ArcPeople();
	}

	public void loadFromArcPeople(){
		if((newBuilding.getLicNewAplOwnerId() != null) && (!newBuilding.getLicNewAplOwnerId().trim().equals("")))
			owner = (ArcPeople) dataAccessService.loadArcPeople(Long.parseLong(newBuilding.getLicNewAplOwnerId()));
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
	
	public void enterImage(FileUploadEvent e) {
		try {
			UploadedFile file = e.getFile();
			InputStream inputStream = file.getInputstream();
			byte[] pBlob = IOUtil.toByteArray(inputStream);
			Blob bild;
			bild = new SerialBlob(pBlob);
			InputStream stream = bild.getBinaryStream();
			image = new DefaultStreamedContent(stream, "image/jpg");
			newBuilding.setLicNewGeneralKroki(pBlob);
		} catch (Exception ex) {

		}
	}
	
	public void enterBldKrokiImage(FileUploadEvent e) {
		try {
			UploadedFile file = e.getFile();
			InputStream inputStream = file.getInputstream();
			byte[] pBlob = IOUtil.toByteArray(inputStream);
			Blob bild;
			bild = new SerialBlob(pBlob);
			InputStream stream = bild.getBinaryStream();
			krokiImage = new DefaultStreamedContent(stream, "image/jpg");
			newBuilding.setLicNewBldKroki(pBlob);
		} catch (Exception ex) {

		}
	}
	
	public void addPiece(){
		newBuilding.getPieces().add(new BldLicPcs(newBuilding.getLicNewId()));
	}
	
	public void addDimensions(){
	}
	
	public StreamedContent getImage() {
		if (newBuilding.getLicNewGeneralKroki() != null) {
			byte[] pBlob = newBuilding.getLicNewGeneralKroki();
			Blob bild;
			try {
				bild = new SerialBlob(pBlob);
				InputStream stream = bild.getBinaryStream();
				image = new DefaultStreamedContent(stream, "image/jpg");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return image;
	}

	public void setImage(StreamedContent image) {
		this.image = image;
	}
	
	public StreamedContent getKrokiImage() {
		if (newBuilding.getLicNewBldKroki() != null) {
			byte[] pBlob = newBuilding.getLicNewBldKroki();
			Blob bild;
			try {
				bild = new SerialBlob(pBlob);
				InputStream stream = bild.getBinaryStream();
				krokiImage = new DefaultStreamedContent(stream, "image/jpg");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return krokiImage;
	}

	public void setKrokiImage(StreamedContent krokiImage) {
		this.krokiImage = krokiImage;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public BldLicNew getNewBuilding() {
		return newBuilding;
	}

	public void setNewBuilding(BldLicNew newBuilding) {
		this.newBuilding = newBuilding;
	}

	public ArcPeople getOwner() {
		return owner;
	}

	public void setOwner(ArcPeople owner) {
		this.owner = owner;
	}
	
	public List<AttachmentModel> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<AttachmentModel> attachList) {
		this.attachList = attachList;
	}

	public List<WrkPurpose> getWrkPurposes() {
		return wrkPurposes;
	}

	public void setWrkPurposes(List<WrkPurpose> wrkPurposes) {
		this.wrkPurposes = wrkPurposes;
	}

	public String getApplicationPurpose() {
		return applicationPurpose;
	}

	public void setApplicationPurpose(String applicationPurpose) {
		this.applicationPurpose = applicationPurpose;
	}

	public List<ArcUsers> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<ArcUsers> usersList) {
		this.usersList = usersList;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public boolean isEngineeringOffice() {
		return engineeringOffice;
	}

	public void setEngineeringOffice(boolean engineeringOffice) {
		this.engineeringOffice = engineeringOffice;
	}

	public boolean isPlanningManager() {
		return planningManager;
	}

	public void setPlanningManager(boolean planningManager) {
		this.planningManager = planningManager;
	}

	public boolean isActivatePrint() {
		return activatePrint;
	}

	public void setActivatePrint(boolean activatePrint) {
		this.activatePrint = activatePrint;
	}

	public PayLicBills getNewBill() {
		return newBill;
	}

	public void setNewBill(PayLicBills newBill) {
		this.newBill = newBill;
	}

	public List<PayBillDetails> getSelectedDetailList() {
		return selectedDetailList;
	}

	public void setSelectedDetailList(List<PayBillDetails> selectedDetailList) {
		this.selectedDetailList = selectedDetailList;
	}

//	public List<PayBillDetails> getBuildingResult() {
//		return buildingResult;
//	}
//
//	public void setBuildingResult(List<PayBillDetails> buildingResult) {
//		this.buildingResult = buildingResult;
//	}

	public String getNewBillNmber() {
		return newBillNmber;
	}

	public void setNewBillNmber(String newBillNmber) {
		this.newBillNmber = newBillNmber;
	}

	public boolean isBeforeLastStep() {
		return beforeLastStep;
	}

	public void setBeforeLastStep(boolean beforeLastStep) {
		this.beforeLastStep = beforeLastStep;
	}
	
	/**
	 * Get the reporting URL
	 * @return
	 */
	public String getUrl() {
		String repName = "";
		if((newBuilding.getLicNewBldUse() == 2) || (newBuilding.getLicNewBldUse() == 11) || (newBuilding.getLicNewBldUse() == 12))
			repName = "BLD021_TRD_java";
		else
			repName = "BLD021";
		return dataAccessService.printDocument(repName, 11830/*newBuilding.getLicNewId()*/, "P1");
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getWrkAppComment() {
		return wrkAppComment;
	}

	public void setWrkAppComment(String wrkAppComment) {
		this.wrkAppComment = wrkAppComment;
	}

	public boolean isLastStep() {
		return lastStep;
	}

	public void setLastStep(boolean lastStep) {
		this.lastStep = lastStep;
	}
}

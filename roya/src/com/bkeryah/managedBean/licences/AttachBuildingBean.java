package com.bkeryah.managedBean.licences;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import com.bkeryah.entities.ArcPeople;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.HrsScenarioDocument;
import com.bkeryah.entities.PayBillDetails;
import com.bkeryah.entities.PayLicBills;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.entities.licences.BldLicAttch;
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
public class AttachBuildingBean  extends Scanner implements Serializable{
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private BldLicAttch attachBld = new BldLicAttch();
	private ArcPeople owner = new ArcPeople();
	private List<WrkPurpose> wrkPurposes;
	private String applicationPurpose;
	private List<ArcUsers> usersList;
	private boolean engineeringOffice;
	private boolean planningManager;
	private boolean activatePrint;
	private boolean beforeLastStep;
	private int recordId;
	private PayLicBills newBill = new PayLicBills();
	private List<PayBillDetails> selectedDetailList = new ArrayList<>();
//	private List<PayBillDetails> buildingResult;
	private String newBillNmber;
	private String url;
	private String wrkAppComment;
	
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
			attachBld = dataAccessService.getAttachBuildingLicenceByArcRecordId(recordId);
			owner = (ArcPeople) dataAccessService.loadArcPeople(Long.parseLong(attachBld.getLicAttAplOwner()));
			wrkPurposes = dataAccessService.getAllPurposes();
			usersList = dataAccessService.getAllActiveEmployeesInDept(MyConstants.PLANNING_DEPT_ID);
			planningManager = (Utils.findCurrentUser().getUserId() == dataAccessService.getPropertiesValue(MyConstants.PLANNING_DEPT_MGR));
			engineeringOffice = (Utils.findCurrentUser().getUserId() == dataAccessService.getUserIdFromWorkAppByIdAndStepId(recordId, 1));
			
			Integer acceptCount = dataAccessService.getHrsSignNextStep(recordId);
			HrsScenarioDocument scenario = dataAccessService.getDestinationModel(MyConstants.ATTACH_BUILDING_TYPE, acceptCount+1);
			if ((scenario != null) && (scenario.getSigned() == 1))
				beforeLastStep = true;
			else if(attachBld.getLicAttBillNumber() != null){
				PayLicBills bill = dataAccessService.loadBillByLicNo(attachBld.getLicAttId());
				if((scenario == null) && (bill.getBillStatus() == 1 ))
					activatePrint = true;
			}
						
		}else{
			attachBld.setPieces(new ArrayList<BldLicPcs>());
		}
	}
	
	public void addPiece(){
		attachBld.getPieces().add(new BldLicPcs(attachBld.getLicAttId()));
	}
	
	public void loadFromArcPeople(){
		if((attachBld.getLicAttAplOwner() != null) && (!attachBld.getLicAttAplOwner().trim().equals("")))
			owner = (ArcPeople) dataAccessService.loadArcPeople(Long.parseLong(attachBld.getLicAttAplOwner()));
	}
	private void resetFields() {
		attachBld = new BldLicAttch();
		attachList = new ArrayList<>();
		owner = new ArcPeople();
	}
	public String saveAction(){
		try{
			String title = MessageFormat.format(Utils.loadMessagesFromFile("attach.building.title"),
				HijriCalendarUtil.findCurrentHijriDate());
			dataAccessService.saveAttachBuilding(attachBld, title, attachList);
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
			dataAccessService.acceptAttachBuilding(recordId, attachBld, attachList, !planningManager);
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
	
	private void saveBill() {
		double sum = 0.0;
		calculateLicenceFees();
		newBill.setBillOwnerName(owner.getCompleteName());
		newBill.setLicenceNumber(attachBld.getLicAttId());
		for (PayBillDetails billDetail : selectedDetailList) {
			sum = sum + billDetail.getAmount();
		}
		newBill.setPayAmount(sum);
		setNewBillNmber(dataAccessService.saveBill(newBill, selectedDetailList).toString());
		attachBld.setLicAttBillNumber(newBillNmber);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('PrintBillDlgVar').show();");

	}
	
	public void calculateLicenceFees() {
		switch (attachBld.getLicAttBldUse().intValue()) {
		//sacany
		case 1:
			selectedDetailList = CalcFeesUtil.calculateBuildingFees(1, 1, 1,
					attachBld.getLicAttTotalAreaWAtt(), 0);
			break;
			//sacany tijari
		case 11:
			selectedDetailList = CalcFeesUtil.calculateBuildingFees(1, 1, 2,
					attachBld.getLicAttTotalAreaWAtt(), 0);
			break;
			//tijari
		case 2:
			selectedDetailList = CalcFeesUtil.calculateBuildingFees(1, 1, 3,
					attachBld.getLicAttTotalAreaWAtt(), 0);
			break;
			//binaa hokomi
		case 12:
			selectedDetailList = CalcFeesUtil.calculateTelecomTowerFees(Integer.parseInt(attachBld.getLicAttReqType()), 1);
			break;

			//sarraf
		case 14:
			selectedDetailList = CalcFeesUtil.calculateATMFees(1, 0, 0,0);
			break;
			//borj jawal
		case 15:
			selectedDetailList = CalcFeesUtil.calculateTelecomTowerFees(Integer.parseInt(attachBld.getLicAttReqType()), 1);
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
	
	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}
	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}
	public BldLicAttch getAttachBld() {
		return attachBld;
	}
	public void setAttachBld(BldLicAttch attachBld) {
		this.attachBld = attachBld;
	}
	public ArcPeople getOwner() {
		return owner;
	}
	public void setOwner(ArcPeople owner) {
		this.owner = owner;
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

	public boolean isBeforeLastStep() {
		return beforeLastStep;
	}

	public void setBeforeLastStep(boolean beforeLastStep) {
		this.beforeLastStep = beforeLastStep;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
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

	public String getNewBillNmber() {
		return newBillNmber;
	}

	public void setNewBillNmber(String newBillNmber) {
		this.newBillNmber = newBillNmber;
	}

	public String getUrl() {
		return dataAccessService.printDocument("BLD023_java", attachBld.getLicAttId(), "P1");
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
}

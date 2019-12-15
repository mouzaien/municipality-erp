package com.bkeryah.managedBean.licences;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
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
import com.bkeryah.entities.licences.BldLicWall;
import com.bkeryah.model.AttachmentModel;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.shared.beans.Scanner;

import utilities.CalcFeesUtil;
import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean(name = "NewBuildingWallBean")
@ViewScoped
public class NewBuildingWallBean extends Scanner implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private BldLicWall newBuilding = new BldLicWall();
	private ArcPeople owner = new ArcPeople();
	private List<ArcUsers> usersList;
	private StreamedContent image = null;
	private int recordId;
	public List<PayBillDetails> buildingResult;
	List<PayBillDetails> selectedDetailList = new ArrayList<>();
	PayLicBills newBill = new PayLicBills();
	String newBillNmber = "";
	String url;
	boolean activatePrint;
	boolean activateAccept;

	boolean beforeLastStep;
	Integer acceptCount;

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
			newBuilding = dataAccessService.getNewBuildingWallLicenceByArcRecordId(recordId);
			byte[] pBlob = newBuilding.getLicWallGeneralKroki();
			Blob bild;
			try {
				bild = new SerialBlob(pBlob);
				InputStream stream = bild.getBinaryStream();
				image = new DefaultStreamedContent(stream, "image/jpg");
			} catch (Exception e) {
				e.printStackTrace();
			}
			owner = (ArcPeople) dataAccessService.loadArcPeople(Long.parseLong(newBuilding.getLicWallAplOwner()));
			usersList = dataAccessService.getAllActiveEmployeesInDept(MyConstants.PLANNING_DEPT_ID);
			acceptCount = dataAccessService.getHrsSignNextStep(recordId);
			if (acceptCount < 5)
				activateAccept = true;
			HrsScenarioDocument scenario = dataAccessService.getDestinationModel(MyConstants.NEW_BUILDING_WALL,
					acceptCount + 1);
			if ((scenario != null) && (scenario.getSigned() == 1))
				beforeLastStep = true;
			else if (newBuilding.getLicWallBillNumber() != null) {
				PayLicBills bill = dataAccessService.loadBillByLicNo(newBuilding.getLicWallId());
				if ((scenario == null) && (bill.getBillStatus() == 1))
					activatePrint = true;
			}
		}
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
			newBuilding.setLicWallGeneralKroki(pBlob);
		} catch (Exception ex) {

		}
	}

	public String saveAction() {
		try {
			// String hdate =
			// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("startDate");
			// newBuilding.setLicNewSakDate(hdate);
			String title = MessageFormat.format(Utils.loadMessagesFromFile("new.building.wall.title"),
					HijriCalendarUtil.findCurrentHijriDate());

			dataAccessService.saveNewBuildingWall(newBuilding, title, attachList);
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

	private void resetFields() {
		newBuilding = new BldLicWall();
		attachList = new ArrayList<>();
		owner = new ArcPeople();
	}

	public void loadFromArcPeople() {
		if ((newBuilding.getLicWallAplOwner() != null) && (!newBuilding.getLicWallAplOwner().trim().equals("")))
			owner = (ArcPeople) dataAccessService.loadArcPeople(Long.parseLong(newBuilding.getLicWallAplOwner()));
	}

	public String acceptAction() {
		try {
			// newBuilding.setLicWallGeneralKroki(IOUtil.toByteArray(image.getStream()));
			dataAccessService.acceptNewBuildingWall(recordId, newBuilding, attachList);
			if (acceptCount == 3)
				saveBill();
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

	//
	// public void saveBill(ActionEvent ae) {
	// buildingResult = CalcFeesUtil.calculateBuildingFees(1, 1, 1,
	// 0, 100);
	//
	// RequestContext context = RequestContext.getCurrentInstance();
	// newBill.setBillOwnerName("");
	//
	// selectedDetailList = buildingResult;
	// if (selectedDetailList == null || selectedDetailList.size() > 0) {
	// context.execute("PF('AddBillDlgVar').show();");
	// } else {
	// MsgEntry.addErrorMessage("تأكد من البيانات أولا");
	// }
	//
	// }
	//

	public void saveBill() {
		double sum = 0.0;
		buildingResult = CalcFeesUtil.calculateBuildingFees(1, 1, 1, 0, newBuilding.getLicWallLength());
		selectedDetailList = buildingResult;
		for (PayBillDetails billDetail : selectedDetailList) {
			sum = sum + billDetail.getAmount();
		}
		newBill.setPayAmount(sum);
		newBill.setLicenceNumber(newBuilding.getLicWallId());
		newBillNmber = dataAccessService.saveBill(newBill, selectedDetailList).toString();
		RequestContext context = RequestContext.getCurrentInstance();
		// context.execute("PF('AddBillDlgVar').hide();");

		context.execute("PF('PrintBillDlgVar').show();");

	}

	public String printBarcodeReport() {

		String reportName = "/reports/bill.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p1", newBillNmber);
		parameters.put("name_customer", dataAccessService.findSystemProperty("CUSTOMER_NAME"));
		Utils.printPdfReport(reportName, parameters);
		return "";
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

	public BldLicWall getNewBuilding() {
		return newBuilding;
	}

	public void setNewBuilding(BldLicWall newBuilding) {
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

	public List<ArcUsers> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<ArcUsers> usersList) {
		this.usersList = usersList;
	}

	public StreamedContent getImage() {
		if (newBuilding.getLicWallGeneralKroki() != null) {
			byte[] pBlob = newBuilding.getLicWallGeneralKroki();
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

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public List<PayBillDetails> getBuildingResult() {
		return buildingResult;
	}

	public void setBuildingResult(List<PayBillDetails> buildingResult) {
		this.buildingResult = buildingResult;
	}

	public List<PayBillDetails> getSelectedDetailList() {
		return selectedDetailList;
	}

	public void setSelectedDetailList(List<PayBillDetails> selectedDetailList) {
		this.selectedDetailList = selectedDetailList;
	}

	public PayLicBills getNewBill() {
		return newBill;
	}

	public void setNewBill(PayLicBills newBill) {
		this.newBill = newBill;
	}

	public String getNewBillNmber() {
		return newBillNmber;
	}

	public void setNewBillNmber(String newBillNmber) {
		this.newBillNmber = newBillNmber;
	}

	public String getUrl() {
		String repName = "";
		// if((newBuilding.getLicNewBldUse() == 2) ||
		// (newBuilding.getLicNewBldUse() == 11) ||
		// (newBuilding.getLicNewBldUse() == 12))
		repName = "BLD022_java";
		// else
		// repName = "BLD021";
		return dataAccessService.printDocument(repName, newBuilding.getLicWallId(), "P1");
	}

	public void setUrl(String url) {
		this.url = url;
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

	public Integer getAcceptCount() {
		return acceptCount;
	}

	public void setAcceptCount(Integer acceptCount) {
		this.acceptCount = acceptCount;
	}

	public boolean isActivateAccept() {
		return activateAccept;
	}

	public void setActivateAccept(boolean activateAccept) {
		this.activateAccept = activateAccept;
	}

}

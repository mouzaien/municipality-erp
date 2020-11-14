package com.bkeryah.projects;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
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
import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.io.FilenameUtils;
import org.codehaus.plexus.util.IOUtil;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.bkeryah.entities.HealthLicenceCenter;
import com.bkeryah.entities.HealthLicenceJob;
import com.bkeryah.entities.HealthMasterLicence;
import com.bkeryah.entities.HrsScenarioDocument;
import com.bkeryah.entities.SysNationality;
import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.penalties.LicTrdMasterFile;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class HealthCertificateBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private HealthMasterLicence healthCertificate = new HealthMasterLicence();
	private Integer recordId;
	private String url;
	private boolean hideAccept;
	private List<HealthLicenceJob> hlthLicJobsList = new ArrayList<HealthLicenceJob>();
	private List<HealthLicenceCenter> hlthLicCenterList = new ArrayList<HealthLicenceCenter>();
	private Integer healthCertificateID;
	private List<LicTrdMasterFile> allLicencesList;
	private List<SysNationality> listNationality;
	private SysNationality nationality = new SysNationality();
	private String newNationality = new String();
	private String licNo;
	private String licTrdName;
	private boolean enaplePrint;
	private byte[] image;
	private String uploadedFileName;
	private String newHlthLicJob = new String();
	private HealthLicenceJob hlthLicJob = new HealthLicenceJob();
	private Integer helthApplId;
	private List<HealthMasterLicence> healthLicList = new ArrayList<HealthMasterLicence>();

	// private StreamedContent streamedcontent;
	@PostConstruct
	public void init() {

		hlthLicJobsList = dataAccessService.getAllHealthLicenceJobsList();
		hlthLicCenterList = dataAccessService.getAllHealthLicenceCentersList();
		allLicencesList = dataAccessService.getAllLicencesList();
		listNationality = dataAccessService.loadNationalEmploye();
		healthLicList = dataAccessService.getAllHealthCertificate();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(false);
		String arcRecordId = (String) httpSession.getAttribute("arcRecord");
		// test if in view mode or insert mode
		if (arcRecordId != null) {
			recordId = Integer.parseInt(arcRecordId.trim());
			healthCertificate = (HealthMasterLicence) dataAccessService.getHealthCertificateRecordId(recordId);
			hideAccept = hideAcceptButton();
		} else {

		}

	}

	public void loadhealthLicListByApplId() {
		if (helthApplId != null && helthApplId != -1) {
			healthCertificate = dataAccessService.getHealthCertificateByApplId(helthApplId);
			enaplePrint = true;
		} else {
			healthCertificate = new HealthMasterLicence();
			enaplePrint = false;
		}
	}

	public void updateTrdName() {
		// String trdName =
		// dataAccessService.getLicencesByLicNo(healthCertificate.getLicenceId().toString()).getTrdName();
		// healthCertificate.setTrdLicenceName(licNo);
		setLicTrdName(dataAccessService.getLicencesByLicNo(licNo).getTrdName());
	}

	public void fileUpload(FileUploadEvent event) {

		uploadedFileName = event.getFile().getFileName();

		String fileExtention = FilenameUtils.getExtension(uploadedFileName);
		try {
			if (fileExtention.equalsIgnoreCase("jpeg") || fileExtention.equalsIgnoreCase("jpg")
					|| fileExtention.equalsIgnoreCase("png")) {
				image = event.getFile().getContents();
				MsgEntry.addInfoMessage("تم الارفاق");
			} else {
				uploadedFileName = new String();
				MsgEntry.addErrorMessage("غير مسموح إلا بإرفاق صور فقط");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveHlthLicJobs() {

		if (newHlthLicJob != null) {
			hlthLicJob.setName(newHlthLicJob);
			dataAccessService.save(hlthLicJob);
			MsgEntry.addInfoMessage("تم الحفظ");
			hlthLicJobsList = dataAccessService.getAllHealthLicenceJobsList();
			newHlthLicJob = new String();
		} else {
			MsgEntry.addErrorMessage("يجب ادخل اسم المهنة");

		}
	}

	public void saveNationality() {

		if (newNationality != null) {
			nationality.setNationalityName(newNationality);
			dataAccessService.save(nationality);
			MsgEntry.addInfoMessage("تم الحفظ");
			listNationality = dataAccessService.loadNationalEmploye();
			newNationality = new String();
		} else {
			MsgEntry.addErrorMessage("يجب ادخل اسم الجنسية");

		}
	}

	private boolean hideAcceptButton() {
		Integer acceptCount = dataAccessService.getHrsSignNextStep(recordId);
		HrsScenarioDocument scenario = dataAccessService.getDestinationModel(MailTypeEnum.HEALTH_LICENCE.getValue(),
				acceptCount);
		if (scenario == null)
			return true;
		else {
			return false;
		}
	}

	public String printReportAction() {
		String reportName = "/reports/helt_certification_test.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("appl_id", healthCertificate.getApplicationId());
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public void save() {

	}

	public HealthCertificateBean() {

	}

	public void loadData() {

	}

	public void accept() {

	}

	public void refuse() {

	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public HealthMasterLicence getHealthCertificate() {
		return healthCertificate;
	}

	public void setHealthCertificate(HealthMasterLicence healthCertificate) {
		this.healthCertificate = healthCertificate;
	}

	public StreamedContent getStreamedcontent() {
		byte[] myPicture = dataAccessService.loadArcPeoplePic(healthCertificate.getArcPeople().getNationalId());// Integer.parseInt(id));2125472791L

		InputStream inputStream = new ByteArrayInputStream(myPicture);
		try {
			byte[] pBlob = IOUtil.toByteArray(inputStream);
			Blob bild;
			bild = new SerialBlob(pBlob);
			InputStream stream = bild.getBinaryStream();
			return new DefaultStreamedContent(stream, "image/jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

		// return new
		// DefaultStreamedContent(selectedTrdApplication.getLicTrdPic(),
		// "image/jpeg");
	}

	// public String printDocumentAction(){
	// return dataAccessService.printDocument("pay003",
	// payLicBill.getBillNumber(), "billno");
	// }

	public String acceptHealthLicenceAction() {
		try {

			// dataAccessService.acceptHealthLicence(recordId,
			// MailTypeEnum.HEALTHCERTIFICATE.getValue(),
			// healthCertificate);
			if (image != null) {
				healthCertificate.setStatus(MyConstants.YES);
				healthCertificate.setOwnerImage(image);
				String subject = Utils.loadMessagesFromFile("subject.health.certificate");
				dataAccessService.saveHealthCertificate(healthCertificate, subject,
						MailTypeEnum.HEALTHCERTIFICATE.getValue(), false);
				// dataAccessService.save(healthCertificate);
				enaplePrint = true;
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("accept.record"));
			} else {
				MsgEntry.addErrorMessage("يجب تحديد صورة صاحب الطلب");
			}
			// return "mails";
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}

	public String getUrl() {
		if (url == null) {
			String encodedString = dataAccessService.encrypt("" + healthCertificate.getApplicationId());
			url = dataAccessService.printDocumentByNameAndParams("rh44",
					"userid=reports/reports@ORCL&P_1=" + encodedString);
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isHideAccept() {
		return hideAccept;
	}

	public void setHideAccept(boolean hideAccept) {
		this.hideAccept = hideAccept;
	}

	// public void setStreamedcontent(StreamedContent streamedcontent) {
	// this.streamedcontent = streamedcontent;
	// }

	public String printCertificateReport() {
		String reportName = "/reports/HealthCerificate.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p1", recordId);
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public List<HealthLicenceJob> getHlthLicJobsList() {
		return hlthLicJobsList;
	}

	public void setHlthLicJobsList(List<HealthLicenceJob> hlthLicJobsList) {
		this.hlthLicJobsList = hlthLicJobsList;
	}

	public List<HealthLicenceCenter> getHlthLicCenterList() {
		return hlthLicCenterList;
	}

	public void setHlthLicCenterList(List<HealthLicenceCenter> hlthLicCenterList) {
		this.hlthLicCenterList = hlthLicCenterList;
	}

	public Integer getHealthCertificateID() {
		return healthCertificateID;
	}

	public void setHealthCertificateID(Integer healthCertificateID) {
		this.healthCertificateID = healthCertificateID;
	}

	public List<LicTrdMasterFile> getAllLicencesList() {
		return allLicencesList;
	}

	public void setAllLicencesList(List<LicTrdMasterFile> allLicencesList) {
		this.allLicencesList = allLicencesList;
	}

	public String getLicNo() {
		return licNo;
	}

	public void setLicNo(String licNo) {
		this.licNo = licNo;
	}

	public String getLicTrdName() {
		return licTrdName;
	}

	public void setLicTrdName(String licTrdName) {
		this.licTrdName = licTrdName;
	}

	public List<SysNationality> getListNationality() {
		return listNationality;
	}

	public void setListNationality(List<SysNationality> listNationality) {
		this.listNationality = listNationality;
	}

	public boolean isEnaplePrint() {
		return enaplePrint;
	}

	public void setEnaplePrint(boolean enaplePrint) {
		this.enaplePrint = enaplePrint;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}

	public String getNewHlthLicJob() {
		return newHlthLicJob;
	}

	public void setNewHlthLicJob(String newHlthLicJob) {
		this.newHlthLicJob = newHlthLicJob;
	}

	public HealthLicenceJob getHlthLicJob() {
		return hlthLicJob;
	}

	public void setHlthLicJob(HealthLicenceJob hlthLicJob) {
		this.hlthLicJob = hlthLicJob;
	}

	public SysNationality getNationality() {
		return nationality;
	}

	public void setNationality(SysNationality nationality) {
		this.nationality = nationality;
	}

	public String getNewNationality() {
		return newNationality;
	}

	public void setNewNationality(String newNationality) {
		this.newNationality = newNationality;
	}

	public Integer getHelthApplId() {
		return helthApplId;
	}

	public void setHelthApplId(Integer helthApplId) {
		this.helthApplId = helthApplId;
	}

	public List<HealthMasterLicence> getHealthLicList() {
		return healthLicList;
	}

	public void setHealthLicList(List<HealthMasterLicence> healthLicList) {
		this.healthLicList = healthLicList;
	}
}

package com.bkeryah.projects;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;

import org.codehaus.plexus.util.IOUtil;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.bkeryah.entities.ArcRecords;
import com.bkeryah.entities.HealthMasterLicence;
import com.bkeryah.entities.HrsScenarioDocument;
import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class HealthCertificateBean  implements Serializable{

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private HealthMasterLicence healthCertificate = new HealthMasterLicence();
	private Integer recordId;
	private String url;
	private boolean hideAccept;

//	private StreamedContent streamedcontent;
	@PostConstruct
	public void init() {
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
	
	private boolean hideAcceptButton(){
		Integer acceptCount = dataAccessService.getHrsSignNextStep(recordId);
		HrsScenarioDocument scenario = dataAccessService.getDestinationModel(MailTypeEnum.HEALTH_LICENCE.getValue(), acceptCount);
		if(scenario ==null)
			return true;
		else{
			return false;
		}
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
		byte[] myPicture = dataAccessService.loadArcPeoplePic(healthCertificate.getArcPeople().getNationalId());//Integer.parseInt(id));2125472791L
        
        InputStream inputStream = new ByteArrayInputStream(myPicture);
		try {
			byte[] pBlob = IOUtil.toByteArray(inputStream);
			Blob bild;
			bild = new SerialBlob(pBlob);
			InputStream stream = bild.getBinaryStream();
			return new DefaultStreamedContent(stream, "image/jpg");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			}
		return null;

		// return new
		// DefaultStreamedContent(selectedTrdApplication.getLicTrdPic(),
		// "image/jpeg");
	}
	
//	public String printDocumentAction(){
//		return dataAccessService.printDocument("pay003", payLicBill.getBillNumber(), "billno");
//	}

	public String acceptHealthLicenceAction(){
		try{
			dataAccessService.acceptHealthLicence(recordId, MailTypeEnum.HEALTH_LICENCE.getValue(), healthCertificate);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("accept.record"));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}

	public String getUrl() {
		if(url == null){
			String encodedString = dataAccessService.encrypt(""+healthCertificate.getApplicationId());
			url = dataAccessService.printDocumentByNameAndParams("rh44", "userid=reports/reports@ORCL&P_1="+encodedString);
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

//	public void setStreamedcontent(StreamedContent streamedcontent) {
//		this.streamedcontent = streamedcontent;
//	}

	public String printCertificateReport() {
		String reportName = "/reports/HealthCerificate.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p1", recordId);
		Utils.printPdfReport(reportName, parameters);
		return "";
	}
	
	
}

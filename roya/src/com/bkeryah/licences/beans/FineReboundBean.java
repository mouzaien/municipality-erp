package com.bkeryah.licences.beans;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.bkeryah.entities.ArcRecAtt;
import com.bkeryah.entities.ArcRecAttId;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.ExchangeRequest;
import com.bkeryah.entities.ExchangeRequestDetails;
import com.bkeryah.entities.FineReboundMaster;
import com.bkeryah.entities.HrsScenarioDocument;
import com.bkeryah.entities.ProcurementDetails;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.model.AttachmentModel;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.Utils;

/**
 * @author Abdelkarim
 *
 */
@ManagedBean
@ViewScoped
public class FineReboundBean {
	  
	@ManagedProperty(value="#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private FineReboundMaster fineReboundMaster = new FineReboundMaster();
	private Double sommeMeter;
	private Double halfMeterCost;
	private Double quarterMeterCost;
	private Double fineQuarterCost;
	private Double fineHalfCost;
	private Double fineQuarterCostWithMin;
	private Double fineHalfCostWithMax;
	private boolean lastStep;
	private List<UploadedFile> files = new ArrayList<UploadedFile>();
	private List<AttachmentModel> attachList = new ArrayList<>();
	private ArcUsers currentUser;
	private Integer recordId;
	private List<WrkPurpose> wrkPurposes;
	private String applicationPurpose;
	private boolean hideAccept;
	private String acceptMessage;
	private String url;
	private String fineTaxTypeLabel;
	
	@PostConstruct
	public void init() {
		currentUser = Utils.findCurrentUser();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		String arcRecordId = (String) httpSession.getAttribute("arcRecord");
		// test if in view mode or insert mode
		if (arcRecordId != null) {
			recordId = Integer.parseInt(arcRecordId);
			fineReboundMaster = dataAccessService.getFineReboundMasterByArcRecordId(recordId);
			wrkPurposes = dataAccessService.getAllPurposes();
			Integer acceptCount = dataAccessService.getHrsSignNextStep(recordId);
			HrsScenarioDocument scenario = dataAccessService.getDestinationModel(MailTypeEnum.FINE_REBOUND.getValue(), acceptCount);
			if(scenario !=null)
				lastStep = (dataAccessService.getDestinationModel(MailTypeEnum.FINE_REBOUND.getValue(), acceptCount).getSigned() == 1);
			else{
				acceptMessage = MessageFormat.format(Utils.loadMessagesFromFile("accept.fine.rebound"), getFineTaxTypeLabel(), fineReboundMaster.getFineSignedIn());
				setHideAccept(true);
			}
			fillTable();
		}
	}

	/**
	 * Save the fine rebound
	 */
	public void saveAction() {
		try {
			if(!checkFields())
				return;
//			String date = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("startDate");
			fineReboundMaster.setFineHDate(HijriCalendarUtil.findCurrentHijriDate());
			dataAccessService.saveFineReboundMaster(fineReboundMaster, attachList);
			resetFields();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.save.rebound.fine"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}
	
    private boolean checkFields() {
    	boolean valid = true;
		if ((fineReboundMaster.getFineAplOwnerId() == null) || (fineReboundMaster.getFineAplOwnerId() == 0)) {
			valid = false;
		}
		if ((fineReboundMaster.getFineAplOwnerName() == null) || (fineReboundMaster.getFineAplOwnerName().trim().equals(""))) {
			valid = false;
		}
		if ((fineReboundMaster.getFineInstrumentNo() == null) || (fineReboundMaster.getFineInstrumentNo().trim().equals(""))) {
			valid = false;
		}
		if ((fineReboundMaster.getFineInstrumentSource() == null) || (fineReboundMaster.getFineInstrumentSource().trim().equals(""))) {
			valid = false;
		}
		if ((fineReboundMaster.getFineReboundDesc() == null) || (fineReboundMaster.getFineReboundDesc().trim().equals(""))) {
			valid = false;
		}
		if ((fineReboundMaster.getFineGroundMeter() == null) || (fineReboundMaster.getFineGroundMeter() == 0)) {
			valid = false;
		}
		if ((fineReboundMaster.getFineFloorMeter() == null) || (fineReboundMaster.getFineFloorMeter() == 0)) {
			valid = false;
		}
		if ((fineReboundMaster.getFineMinTax() == null) || (fineReboundMaster.getFineMinTax() == 0)) {
			valid = false;
		}
		if ((fineReboundMaster.getFineMaxTax() == null) || (fineReboundMaster.getFineMaxTax() == 0)) {
			valid = false;
		}
		if ((fineReboundMaster.getFineMeterCost() == null) || (fineReboundMaster.getFineMeterCost() == 0)) {
			valid = false;
		}
		if (!valid)
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("required.fields"));
		return valid;
	}

	/**
     * Reset fields
     */
    private void resetFields() {
		fineReboundMaster = new FineReboundMaster();
		sommeMeter = null;
		halfMeterCost = null;
		quarterMeterCost = null;
		fineQuarterCost = null;
		fineHalfCost = null;
		fineQuarterCostWithMin = null;
		fineHalfCostWithMax = null;
	}
    
	/**
	 * Accept the fine rebound
	 * @return
	 */
	public String acceptAction() {
		if(lastStep && (fineReboundMaster.getFineTaxType() == null)){
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("choose.limit"));
			return "";
		}
		dataAccessService.acceptFineRebound(recordId, fineReboundMaster, MailTypeEnum.FINE_REBOUND.getValue());
		MsgEntry.addAcceptFlashInfoMessage();
		return  "mails";
	}
	
	/**
	 * Add the selected attachment
	 * @param event
	 */
	public void uploadFile(FileUploadEvent event) {

		try {
			AttachmentModel attach = new AttachmentModel();
			attach.setAttachRealName(event.getFile().getFileName());
			attach.setAttachSize(event.getFile().getSize());
			attach.setAttachStream(event.getFile().getInputstream());
			attach.setAttachExt(FilenameUtils.getExtension(event.getFile().getFileName()));
			attachList.add(attach);
			files.add(event.getFile());

		} catch (Exception e) {

		}

	}
	
	/**
	 * Listener to calculate fields
	 */
	public void fillTable(){
		sommeMeter = (double) (fineReboundMaster.getFineGroundMeter()+fineReboundMaster.getFineFloorMeter());
		halfMeterCost = fineReboundMaster.getFineMeterCost()*0.5;
		quarterMeterCost = fineReboundMaster.getFineMeterCost()*0.25;
		fineQuarterCost = sommeMeter * quarterMeterCost;
		fineHalfCost = sommeMeter * halfMeterCost;
		fineQuarterCostWithMin = quarterMeterCost + fineReboundMaster.getFineMinTax();
		fineHalfCostWithMax = halfMeterCost +  fineReboundMaster.getFineMaxTax();
		
	}

	public List<UploadedFile> getFiles() {
		return files;
	}

	public void setFiles(List<UploadedFile> files) {
		this.files = files;
	}

	public List<AttachmentModel> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<AttachmentModel> attachList) {
		this.attachList = attachList;
	}
	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}
	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}
	public FineReboundMaster getFineReboundMaster() {
		return fineReboundMaster;
	}
	public void setFineReboundMaster(FineReboundMaster fineReboundMaster) {
		this.fineReboundMaster = fineReboundMaster;
	}
	public Double getSommeMeter() {
		return sommeMeter;
	}
	public void setSommeMeter(Double sommeMeter) {
		this.sommeMeter = sommeMeter;
	}
	public Double getHalfMeterCost() {
		return halfMeterCost;
	}
	public void setHalfMeterCost(Double halfMeterCost) {
		this.halfMeterCost = halfMeterCost;
	}
	public Double getQuarterMeterCost() {
		return quarterMeterCost;
	}
	public void setQuarterMeterCost(Double quarterMeterCost) {
		this.quarterMeterCost = quarterMeterCost;
	}
	public Double getFineQuarterCost() {
		return fineQuarterCost;
	}
	public void setFineQuarterCost(Double fineQuarterCost) {
		this.fineQuarterCost = fineQuarterCost;
	}
	public Double getFineHalfCost() {
		return fineHalfCost;
	}
	public void setFineHalfCost(Double fineHalfCost) {
		this.fineHalfCost = fineHalfCost;
	}
	public Double getFineQuarterCostWithMin() {
		return fineQuarterCostWithMin;
	}
	public void setFineQuarterCostWithMin(Double fineQuarterCostWithMin) {
		this.fineQuarterCostWithMin = fineQuarterCostWithMin;
	}
	public Double getFineHalfCostWithMax() {
		return fineHalfCostWithMax;
	}
	public void setFineHalfCostWithMax(Double fineHalfCostWithMax) {
		this.fineHalfCostWithMax = fineHalfCostWithMax;
	}
	public boolean isLastStep() {
		return lastStep;
	}
	public void setLastStep(boolean lastStep) {
		this.lastStep = lastStep;
	}

	public ArcUsers getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(ArcUsers currentUser) {
		this.currentUser = currentUser;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
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

	public boolean isHideAccept() {
		return hideAccept;
	}

	public void setHideAccept(boolean hideAccept) {
		this.hideAccept = hideAccept;
	}

	public String getAcceptMessage() {
		return acceptMessage;
	}

	public void setAcceptMessage(String acceptMessage) {
		this.acceptMessage = acceptMessage;
	}
	
	/**
	 * Get the reporting URL
	 * @return
	 */
	public String getUrl() {
		return dataAccessService.printDocument("Fine001", fineReboundMaster.getFineId(), "P1");
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setFineTaxTypeLabel(String fineTaxTypeLabel) {
		this.fineTaxTypeLabel = fineTaxTypeLabel;
	}

	public String getFineTaxTypeLabel() {
		if(fineReboundMaster.getFineTaxType() != null){
			if(fineReboundMaster.getFineTaxType() == 0)
				return Utils.loadMessagesFromFile("min.cost");
			else if(fineReboundMaster.getFineTaxType() == 1)
				return Utils.loadMessagesFromFile("max.cost");
		}
		return "";
	}
	
}

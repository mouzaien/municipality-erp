package com.bkeryah.managedBean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.bkeryah.bean.ArcAttachmentClass;
import com.bkeryah.bean.UserMailObj;
import com.bkeryah.bean.WrkCommentsClass;
import com.bkeryah.entities.ArcAttach;
import com.bkeryah.entities.ArcRecAtt;
import com.bkeryah.entities.ArcRecAttId;
import com.bkeryah.entities.HrMedicalRequest;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.entities.WrkComment;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.shared.beans.Scanner;

import utilities.FtpTransferFile;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class OutBoxDefenationBean extends Scanner {

	private UserMailObj selectedOutbox;

	private List<WrkCommentsClass> outboxComments;
	private List<ArcAttachmentClass> outattList = new ArrayList<ArcAttachmentClass>();
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private boolean recordHasComment;
	private String reportsUrl;
	private WrkComment comment;
	private WrkApplicationId wrkId;
	private List<ArcAttach> attachs = new ArrayList<ArcAttach>();
	private boolean isModel;

	@PostConstruct
	public void init() {
		selectedOutbox = (UserMailObj) FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.get("selectedOutBoxMail");
		outboxComments = this.dataAccessService.findCommentsByWrkId(selectedOutbox.WrkId);
		outattList = this.dataAccessService.findAttachmentFilesByArcId(selectedOutbox.AppId);
		if ((selectedOutbox.hasComment != null) && (selectedOutbox.hasComment == 1)) {
			setRecordHasComment(true);
			comment = (WrkComment) dataAccessService.findEntityById(WrkComment.class,
					Integer.parseInt(selectedOutbox.getWrkId().trim()));
		}
		if (selectedOutbox.getWrkType() != 143 && selectedOutbox.getWrkType() != 242 && selectedOutbox.getWrkType() != 0)
			isModel = true;
		wrkId = new WrkApplicationId(Integer.parseInt(this.selectedOutbox.WrkId), selectedOutbox.StepId);
	}

	public String retrieveRecordBtnAction() {
		try {

			if (selectedOutbox.getIsRead() != 1) {

				dataAccessService.retrieveRecord(Utils.findCurrentUser().getUserId(), selectedOutbox.AppId,
						selectedOutbox.WrkId);
				return "mails";
			} else {
				MsgEntry.addErrorMessage("لا يمكن استرجاع المعاملة ");
				return "";
			}

		} catch (Exception e) {
			MsgEntry.addErrorMessage("Error msg");
			return "";
		}
	}

	/**
	 * Print letter report
	 */
	public String printReportAction() {
		String reportName = "/reports/letter2.jasper";
		Map<String, Object> parameters = populateLetterParameters(selectedOutbox.getWrkId(), comment.getFontSize(), 0);
		List<WrkComment> comms = new ArrayList<WrkComment>();
		comms.add(comment);
		Utils.printPdfReportFromListDataSource(reportName, parameters, comms);
		return "";
	}

	private Map<String, Object> populateLetterParameters(String wrkId, Integer fontSize, Integer copy) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		// parameters.put("p1", wrkId);// "259306";
		parameters.put("textSize", fontSize);
		parameters.put("copy", copy);
		parameters.put("SUBREPORT_DIR",
				FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/watan.jpg"));
		dataAccessService.loadLetterReportParameters(parameters, wrkId);
		return parameters;
	}

	/**
	 * Print letter report
	 */
	public String printExplanationReportAction() {
		String reportName = "/reports/rep001.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p1", selectedOutbox.getWrkId());// "259306";
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	/**
	 * Print report
	 */
	public String printIreportFileAction(String url) {
		String reportName = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (url.contains(MyConstants.REPORT_MEDICAL)) {
			reportName = "/reports/medical_report.jasper";
			parameters.put("record_id", Integer.parseInt(selectedOutbox.getAppId()));
			HrMedicalRequest medical = dataAccessService
					.getMedicalRequestInfoByArchRecordId(Integer.parseInt(selectedOutbox.getAppId()));
			parameters.put("day", Utils.getDayForHigriDate(medical.getRequestDate()));
		}
		/*
		 * else if(url.contains(MyConstants.REPORT_SALAR)) { reportName =
		 * "/reports/hr_letter.jasper"; parameters.put("record_id",
		 * Integer.parseInt(selectedInbox.getAppId())); }
		 */
		// int appId = Integer.parseInt(selectedInbox.getAppId());
		// HrEmployeeVacation employeeVacation =
		// this.dataAccessService.findVacationByArcRecordId(appId);
		// parameters.put("seqid",
		// Integer.parseInt(url.substring(url.indexOf("HRSQRY011_116.rdf&PVACSEQ=")+26)));//employeeVacation.getId());
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public StreamedContent getFile(String fileName) {
		return super.getFile(fileName);
	}

	public void saveAttchs(ActionEvent e) {
		uploadFilesToTmpDirectory();
		attachs = Utils.SaveAttachementsToFtpServer(attachList);
		List<Integer> attachIds = dataAccessService.addAttachments(attachs);
		Integer recordId = Integer.parseInt(this.selectedOutbox.getAppId());

		for (Integer id : attachIds) {
			ArcRecAtt arcRecAtt = new ArcRecAtt();
			ArcRecAttId arcRecAttId = new ArcRecAttId();
			arcRecAttId.setAttachId(id);
			arcRecAttId.setRecordId(recordId);
			arcRecAtt.setId(arcRecAttId);
			dataAccessService.saveObject(arcRecAtt);

		}
		outattList = this.dataAccessService.findAttachmentFilesByArcId(selectedOutbox.AppId);
	}

	public String returnRequestAction() {
		try {
			String notes = Utils.loadMessagesFromFile("recovery.trans.notes");
			dataAccessService.redirectExchangeRequest(wrkId, selectedOutbox.FromId, notes);
		} catch (Exception e) {
			return null;
		}
		return "mails";
	}

	public UserMailObj getSelectedOutbox() {
		return selectedOutbox;
	}

	public List<WrkCommentsClass> getOutboxComments() {
		return outboxComments;
	}

	public List<ArcAttachmentClass> getOutattList() {
		return outattList;
	}

	public void setSelectedOutbox(UserMailObj selectedOutbox) {
		this.selectedOutbox = selectedOutbox;
	}

	public void setOutboxComments(List<WrkCommentsClass> outboxComments) {
		this.outboxComments = outboxComments;
	}

	public void setOutattList(List<ArcAttachmentClass> outattList) {
		this.outattList = outattList;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public boolean isRecordHasComment() {
		return recordHasComment;
	}

	public void setRecordHasComment(boolean recordHasComment) {
		this.recordHasComment = recordHasComment;
	}

	public String getReportsUrl() {
		reportsUrl = "http://" + dataAccessService.findSystemProperty(MyConstants.APP_NAME_NEW)
				+ "/reports/rwservlet?repsrv&report=d:\\archiving\\reports\\COMMENT2.rdf&P_1="
				+ this.selectedOutbox.WrkId;
		return reportsUrl;
	}

	public void setReportsUrl(String reportsUrl) {
		this.reportsUrl = reportsUrl;
	}

	public WrkComment getComment() {
		return comment;
	}

	public void setComment(WrkComment comment) {
		this.comment = comment;
	}

	public String printCertificateReport() {
		String reportName = "/reports/HealthCerificate.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p1", Integer.parseInt(selectedOutbox.getAppId()));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public boolean isModel() {
		return isModel;
	}

	public void setModel(boolean isModel) {
		this.isModel = isModel;
	}
}

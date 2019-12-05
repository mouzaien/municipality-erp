package com.bkeryah.shared.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.bkeryah.entities.ArcAttach;
import com.bkeryah.model.AttachmentModel;

import utilities.FtpTransferFile;
import utilities.Utils;

public class Scanner {
	protected List<AttachmentModel> attachList = new ArrayList<>();
	protected static final Logger logger = Logger.getLogger(Scanner.class);
	private boolean activeSend;
	private boolean activeUploadScanner;
	public UploadedFile file;

	public void refreshFilesUploaded(AjaxBehaviorEvent event) {
		try {
			Thread.sleep(2000);
			AttachmentModel attch = Utils.getScannedFile();
			if (attch != null)
				attachList.add(attch);
			setActiveSend(true);
			activeUploadScanner = false;
		} catch (InterruptedException e) {
			logger.info("refreshFilesUploaded  :" + e.getMessage());
		}
	}

	public void deleteAttchFile(String deleteAttchFile) {

		try {
			AttachmentModel deletedFile = new AttachmentModel();
			deletedFile.setAttachRealName(deleteAttchFile);
			attachList.remove(deletedFile);
			if ((attachList == null) || (attachList.isEmpty()))
				setActiveSend(false);
		} catch (Exception e) {
			logger.info("deleteAttchFile " + deleteAttchFile + " :" + e.getMessage());
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
			attach.setRealName(Utils.generateRandomName() + ".pdf" + attach.getAttachExt());
			attachList.add(attach);
			setActiveSend(true);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("lpcDlgMessage",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, Utils.loadMessagesFromFile("no.upload"), ""));
		}

	}

	/* upload file to temp directory */
	public boolean uploadFile(List<String> attchFilesNames) {
		if (attchFilesNames != null)
			for (String fileName : attchFilesNames) {
				try {
					AttachmentModel attach = new AttachmentModel();
					attach.setRealName(fileName);
					attach.setAttachExt("pdf");
					String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/").replace('\\',
							'/') + "pages/action/Dynamsoft_Upload/" + fileName + '.' + attach.getAttachExt();
					File file = new File(path);
					FileInputStream attachStream = new FileInputStream(file);
					attach.setAttachStream(attachStream);
					// attach.setAttachExt(FilenameUtils.getExtension(fileName));
					attach.setRealName(fileName + "." + attach.getAttachExt());
					attach.setAttachRealName(fileName + "." + attach.getAttachExt());
					attachList.add(attach);
					setActiveSend(true);
				} catch (Exception e) {
				//	FacesContext.getCurrentInstance().addMessage("lpcDlgMessage",
				//			new FacesMessage(FacesMessage.SEVERITY_ERROR, Utils.loadMessagesFromFile("no.upload"), ""));
					return false;
				}
			}
		return true;

	}

	public List<ArcAttach> getAttchsUploaded(List<AttachmentModel> attachList) {
		List<ArcAttach> myAttachs = new ArrayList<ArcAttach>();
		List<String> attchFilesNames = getAttchsNames();
		boolean result = uploadFile(attchFilesNames);
		if (result) {
			boolean attachResult = uploadAttachs(attachList);
			if (attachResult)
				for (AttachmentModel att : attachList) {
					ArcAttach attach = new ArcAttach();

					attach.setAttachName(att.getRealName());
					try {
						attach.setAttachOwner(Utils.findCurrentUser().getUserId());
						attach.setAttachDate(new Date());

						attach.setAttachSize((double) att.getAttachSize());
						attach.setAttachType(1);
						att.getAttachStream();
						attach.setAttachCategory("FILE");
						myAttachs.add(attach);
					} catch (Exception e) {

						e.printStackTrace();
						myAttachs.clear();
					}
				}
		}
		return myAttachs;

	}

	private boolean uploadAttachs(List<AttachmentModel> attachList) {
		return Utils.uploadAttachedFiles(attachList);
	}

	public StreamedContent getFile(String fileName) {
		System.out.println(fileName);
		InputStream stream = null;
		StreamedContent file = null;
		try {
			stream = FtpTransferFile.getFile(fileName);
			file = new DefaultStreamedContent(stream, "application/pdf", fileName);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// if (stream != null)
			// try {
			// stream.close();
			// } catch (IOException e) {
			// logger.error("getFile :" + fileName + " " +
			// e.getCause().getMessage());
			// }
		}
		return file;
	}

	protected String printBarcodeReport(Integer arcRecordId, Integer outOrIntNo) {
		String reportName = "/reports/inbarcode.jasper";
		if (outOrIntNo.equals(2))
			reportName = "/reports/outbarcode.jasper";

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p1", arcRecordId);
		parameters.put("compName", Utils.loadMessagesFromFile("comp.name"));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	protected List<String> getAttchsNames() {
		try {
			String attchs = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("includeform:mylst");
			
			List lst=Arrays.asList(attchs.split(";"));
			
			
			return Arrays.asList(attchs.split(";"));
		} catch (Exception e) {
		}
		return null;
	}

	//
	// /***
	// * upload file
	// */
	public void handleFileUpload(UploadedFile file) {

		try {
			AttachmentModel attach = new AttachmentModel();
			attach.setAttachRealName(file.getFileName());
			attach.setAttachSize(file.getSize());
			attach.setAttachStream(file.getInputstream());
			attach.setAttachExt(FilenameUtils.getExtension(file.getFileName()));
			attach.setRealName(Utils.generateRandomName() + "." + attach.getAttachExt());
			attachList.add(attach);

		} catch (Exception e) {

		}
	}

	public void handleFileUpload(FileUploadEvent event) {

		try {
			AttachmentModel attach = new AttachmentModel();
			attach.setAttachRealName(event.getFile().getFileName());
			attach.setAttachSize(event.getFile().getSize());
			attach.setAttachStream(event.getFile().getInputstream());
			attach.setAttachExt(FilenameUtils.getExtension(event.getFile().getFileName()));
			attach.setRealName(Utils.generateRandomName() + "." + attach.getAttachExt());
			attachList.add(attach);

		} catch (Exception e) {

		}
	}

	protected void uploadFilesToTmpDirectory() {

		List<String> attchFilesNames = getAttchsNames();
		uploadFile(attchFilesNames);
	}

	public boolean isActiveSend() {
		return activeSend;
	}

	public void setActiveSend(boolean activeSend) {
		this.activeSend = activeSend;
	}

	public boolean isActiveUploadScanner() {
		return activeUploadScanner;
	}

	public void setActiveUploadScanner(boolean activeUploadScanner) {
		this.activeUploadScanner = activeUploadScanner;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
		if(this.file.getFileName().length()>0)
		handleFileUpload(file);
	}
}

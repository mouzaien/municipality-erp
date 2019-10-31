package com.bkeryah.hr.managedBeans;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;

import com.bkeryah.entities.ArcAttach;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.hr.entities.HrsCompactEmpCaracter;
import com.bkeryah.hr.entities.HrsCompactPerformance;
import com.bkeryah.hr.entities.HrsGeneralAppreciation;
import com.bkeryah.model.AttachmentModel;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.shared.beans.Scanner;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class GeneralAppreciationBean extends Scanner{
	
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	
	
	private List<HrsCompactEmpCaracter> hrsCompactEmpCaracterLst = new ArrayList<>();
	private HrsCompactEmpCaracter hrsCompactEmpCaracter =new HrsCompactEmpCaracter(); 
	protected List<AttachmentModel> attachList = new ArrayList<>();
	private HrsCompactPerformance compactPerformance = new HrsCompactPerformance();
	private HrsGeneralAppreciation generalAppreciation =new HrsGeneralAppreciation();
	private Integer arcRcordId;
	private List<ArcUsers> employersList;
	private List<ArcAttach> attachs = new ArrayList<ArcAttach>();
	private String Rating ="";
	private List<HrsCompactEmpCaracter> compactEmpCaractersList =new ArrayList<>();
	private boolean Visible=false;
	private Integer performanceId;
	private List<WrkPurpose> wrkPurposes;
	private String applicationPurpose;
	private String wrkAppComment;
	private boolean VisiblePrint=false;
	private String applicationPurposeRefuse;
	private String wrkAppCommentRefuse;
	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(false);
		
		
		
		String arcRecordId = (String) httpSession.getAttribute("arcRecord");
		// test if in view mode or insert mode
		if (arcRecordId != null) {
			arcRcordId = Integer.parseInt(arcRecordId.trim());
			wrkPurposes = dataAccessService.getAllPurposes();
			//compactPerformance = (HrsCompactPerformance) dataAccessService.getHrsCompactPerformByArcID(arcRcordId);
			generalAppreciation= (HrsGeneralAppreciation) dataAccessService.getHrsGeneralAppreciationByArcID(arcRcordId);
			compactPerformance =(HrsCompactPerformance) dataAccessService.getHrsCompactPerformByPerformanceID(generalAppreciation.getHrsCompactPerformanceid());

			if(compactPerformance.getStatus()==6)
				VisiblePrint=true;
			compactEmpCaractersList = dataAccessService.getCompactEmpCaractersListByPerformId(generalAppreciation.getHrsCompactPerformanceid());
		} else {
			performanceId = (Integer) httpSession.getAttribute("performanceId");
			if(performanceId != null){
				httpSession.removeAttribute("performanceId");
				compactPerformance = (HrsCompactPerformance) dataAccessService.getHrsCompactPerformByPerformanceID(performanceId);
				generalAppreciation= (HrsGeneralAppreciation) dataAccessService.getHrsGeneralAppreciationByPerformanceID(performanceId);
			}
			
			
			
			
			
		}
		
		if (compactPerformance.getEmpid() != null) {
			try {
				compactPerformance.setEmployer(dataAccessService.getUserNameById(compactPerformance.getEmpid()));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if((generalAppreciation!=null && generalAppreciation.getHrsCompactRatingId()==1)||(generalAppreciation!=null && generalAppreciation.getHrsCompactRatingId()==5))
			Visible=true;
		
		Rating= dataAccessService.getHrsCompactRatingById(generalAppreciation.getHrsCompactRatingId()).getName();
		loadEmployersByDept();
		loadEmployerData();
	}
	
	
	
	public void loadEmployersByDept() {
		setEmployersList(dataAccessService.getAllActiveEmployeesInDept(compactPerformance.getDeptid()));
	}

	public void loadEmployerData() {
		dataAccessService.loadEmployerJobData(compactPerformance);
	}
	
public void addCaracter(AjaxBehaviorEvent event) {
		
		try {
			hrsCompactEmpCaracterLst.add(hrsCompactEmpCaracter);
			hrsCompactEmpCaracter = new HrsCompactEmpCaracter();
			
			

			
			
		} catch (Exception e) {
			MsgEntry.addErrorMessage("اليانات المدخلة غير صحيحة");
		}

	}
	
	
	public void removeRecord(HrsCompactEmpCaracter hrsCompactEmpCaracter) {
		hrsCompactEmpCaracterLst.remove(hrsCompactEmpCaracter);
		
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
	
	
	public String savecharter()
	{
		attachs = Utils.SaveAttachementsToFtpServer(attachList);
		for (ArcAttach xx : attachs) {
			xx.setAttachOwner(Utils.findCurrentUser().getUserId());

		}
		List<Integer> attachIds = dataAccessService.addAttachments(attachs);
		String title = MessageFormat.format(Utils.loadMessagesFromFile("general.appre.title.Model"),
				compactPerformance.getEmpName());
		try {
			dataAccessService.saveGeneralAppreciation(generalAppreciation, hrsCompactEmpCaracterLst, title, compactPerformance.getEmpid(), attachIds);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
		return null;
	
	}
	
	public String accept() {
		try {
			
			dataAccessService.acceptModelAppreciation(generalAppreciation,  wrkAppComment,Integer.parseInt(applicationPurpose.trim()),arcRcordId);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
		return "mails";
	}
	
	
	public String printAppreciation() {
		String reportName = "/reports/generalAppreciation.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("performanceId", compactPerformance.getId());
		parameters.put("SUBREPORT_DIR1", FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/reports/sub_power_points.jasper"));
		parameters.put("SUBREPORT_DIR2", FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/reports/sub_notes.jasper"));
		Utils.printPdfReport(reportName, parameters);
		return "";
		
	}
	
	public String refuse() {
		try {
			
			dataAccessService.refuseAppreciation(generalAppreciation, wrkAppCommentRefuse, Integer.parseInt(applicationPurposeRefuse.trim()), arcRcordId);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
		return "mails";
	}
	
	
	

	public Integer getArcRcordId() {
		return arcRcordId;
	}



	public void setArcRcordId(Integer arcRcordId) {
		this.arcRcordId = arcRcordId;
	}



	public List<ArcUsers> getEmployersList() {
		return employersList;
	}



	public void setEmployersList(List<ArcUsers> employersList) {
		this.employersList = employersList;
	}



	public HrsCompactPerformance getCompactPerformance() {
		return compactPerformance;
	}

	public void setCompactPerformance(HrsCompactPerformance compactPerformance) {
		this.compactPerformance = compactPerformance;
	}

	public List<HrsCompactEmpCaracter> getHrsCompactEmpCaracterLst() {
		return hrsCompactEmpCaracterLst;
	}

	public void setHrsCompactEmpCaracterLst(List<HrsCompactEmpCaracter> hrsCompactEmpCaracterLst) {
		this.hrsCompactEmpCaracterLst = hrsCompactEmpCaracterLst;
	}

	public HrsCompactEmpCaracter getHrsCompactEmpCaracter() {
		return hrsCompactEmpCaracter;
	}

	public void setHrsCompactEmpCaracter(HrsCompactEmpCaracter hrsCompactEmpCaracter) {
		this.hrsCompactEmpCaracter = hrsCompactEmpCaracter;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}
	public List<AttachmentModel> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<AttachmentModel> attachList) {
		this.attachList = attachList;
	}



	public HrsGeneralAppreciation getGeneralAppreciation() {
		return generalAppreciation;
	}



	public void setGeneralAppreciation(HrsGeneralAppreciation generalAppreciation) {
		this.generalAppreciation = generalAppreciation;
	}



	public boolean isVisible() {
		return Visible;
	}



	public void setVisible(boolean visible) {
		Visible = visible;
	}



	public List<ArcAttach> getAttachs() {
		return attachs;
	}



	public void setAttachs(List<ArcAttach> attachs) {
		this.attachs = attachs;
	}



	public String getRating() {
		return Rating;
	}



	public void setRating(String rating) {
		Rating = rating;
	}



	public List<HrsCompactEmpCaracter> getCompactEmpCaractersList() {
		return compactEmpCaractersList;
	}



	public void setCompactEmpCaractersList(List<HrsCompactEmpCaracter> compactEmpCaractersList) {
		this.compactEmpCaractersList = compactEmpCaractersList;
	}



	public Integer getPerformanceId() {
		return performanceId;
	}



	public void setPerformanceId(Integer performanceId) {
		this.performanceId = performanceId;
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



	public String getWrkAppComment() {
		return wrkAppComment;
	}



	public void setWrkAppComment(String wrkAppComment) {
		this.wrkAppComment = wrkAppComment;
	}

	public String getApplicationPurposeRefuse() {
		return applicationPurposeRefuse;
	}



	public void setApplicationPurposeRefuse(String applicationPurposeRefuse) {
		this.applicationPurposeRefuse = applicationPurposeRefuse;
	}



	public String getWrkAppCommentRefuse() {
		return wrkAppCommentRefuse;
	}



	public void setWrkAppCommentRefuse(String wrkAppCommentRefuse) {
		this.wrkAppCommentRefuse = wrkAppCommentRefuse;
	}



	public boolean isVisiblePrint() {
		return VisiblePrint;
	}



	public void setVisiblePrint(boolean visiblePrint) {
		VisiblePrint = visiblePrint;
	}

		
}

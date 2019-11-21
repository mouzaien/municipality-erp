package com.bkeryah.projects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bkeryah.applicationStatus.ProjectStatus;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.Project;
import com.bkeryah.entities.ProjectContract;
import com.bkeryah.entities.ProjectExtract;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.shared.beans.ArcScenarioManager;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class ExtractProjectBean extends ArcScenarioManager {

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private ProjectExtract projectExtract = new ProjectExtract();
	private String projectNumber;
	private List<Project> projects = new ArrayList<>();
	private List<ProjectContract> projectContracts = new ArrayList<>();
	private String contractId;
	private ProjectStatus ProjectStatus;
	private ArcUsers currentUser;
	private boolean showAcceptFlag;
	private boolean viewModeFlag;
	private Integer arcRcordId;
	private String wrkAppComment;
	private List<WrkPurpose> wrkPurposes;
	private String applicationPurpose;
	private String url;
	private boolean flag;

	@PostConstruct
	public void init() {
		typeId = MailTypeEnum.PROJECTeXTRACT;
		currentUser = Utils.findCurrentUser();
		wrkPurposes = dataAccessService.getAllPurposes();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(false);
		String arcRecordId = (String) httpSession.getAttribute("arcRecord");
		// test if in view mode or insert mode
		if (arcRecordId != null) {
			arcRcordId = Integer.parseInt(arcRecordId.trim());

			projectExtract = (ProjectExtract) dataAccessService.getProjectExtractByArchRecordId(arcRcordId);

			if (projectExtract.getCreatedBy().equals(currentUser.getUserId())) {
				showAcceptFlag = false;
			} else {
				showAcceptFlag = true;
			}
		} else {

			projects = dataAccessService.getallProjects();

		}

	}

	public void save() {
		try {
			projectExtract.setCreatedBy(currentUser.getUserId());
			projectExtract.setCreatedIn(HijriCalendarUtil.findCurrentHijriDate());

			projectExtract.setContractId(Integer.parseInt(contractId.trim()));

			dataAccessService.addNewProjectExtract(projectExtract, currentUser.getUserId());
			MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ طلبك بنجاح");
			projectExtract = new ProjectExtract();
			projectNumber = "";
			contractId = "";
			System.out.println("TMAM");
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage("خطا فى التنفيذ");
		}

	}

	public String accept() {
		try {
			dataAccessService.acceptActionforProjectExtract(arcRcordId, 236, wrkAppComment,
					Integer.parseInt(applicationPurpose.trim()));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage("خطا فى تنفيذ العملية");
			return "";
		}

	}

	public void refuse() {

	}

	public void updateContractOwners(AjaxBehaviorEvent event) {

		setProjectContracts(dataAccessService.getallProjectContract(Integer.parseInt(projectNumber.trim())));

	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public ProjectExtract getProjectExtract() {
		return projectExtract;
	}

	public void setProjectExtract(ProjectExtract projectExtract) {
		this.projectExtract = projectExtract;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public List<ProjectContract> getProjectContracts() {
		return projectContracts;
	}

	public void setProjectContracts(List<ProjectContract> projectContracts) {
		this.projectContracts = projectContracts;
	}

	public ProjectStatus getProjectStatus() {
		return ProjectStatus;
	}

	public void setProjectStatus(ProjectStatus projectStatus) {
		ProjectStatus = projectStatus;
	}

	public ArcUsers getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(ArcUsers currentUser) {
		this.currentUser = currentUser;
	}

	public Integer getArcRcordId() {
		return arcRcordId;
	}

	public void setArcRcordId(Integer arcRcordId) {
		this.arcRcordId = arcRcordId;
	}

	public boolean isShowAcceptFlag() {
		return showAcceptFlag;
	}

	public void setShowAcceptFlag(boolean showAcceptFlag) {
		this.showAcceptFlag = showAcceptFlag;
	}

	public boolean isViewModeFlag() {
		return viewModeFlag;
	}

	public void setViewModeFlag(boolean viewModeFlag) {
		this.viewModeFlag = viewModeFlag;
	}

	public String getWrkAppComment() {
		return wrkAppComment;
	}

	public List<WrkPurpose> getWrkPurposes() {
		return wrkPurposes;
	}

	public String getApplicationPurpose() {
		return applicationPurpose;
	}

	public void setWrkAppComment(String wrkAppComment) {
		this.wrkAppComment = wrkAppComment;
	}

	public void setWrkPurposes(List<WrkPurpose> wrkPurposes) {
		this.wrkPurposes = wrkPurposes;
	}

	public void setApplicationPurpose(String applicationPurpose) {
		this.applicationPurpose = applicationPurpose;
	}

	public String getUrl() {
		return dataAccessService.printProjectExtractDocument(projectExtract.getId());
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String printExtractReportAction() {
		String reportName = "/reports/ExtractReport.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("e_No", this.projectExtract.getId());
		Utils.printPdfReport(reportName, parameters);
		return "";
	}
	

	@Override
	public boolean isCurrUserSignAutorized(IDataAccessService dataAccessService, Integer appID) {
		return super.isCurrUserSignAutorized(this.dataAccessService, this.arcRcordId);
	}

	public boolean isFlag() {
		flag=projectExtract.getCreatedBy()==currentUser.getUserId()?false:true;
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}

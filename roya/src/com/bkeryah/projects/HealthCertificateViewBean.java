package com.bkeryah.projects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.*;

import com.bkeryah.bean.UserMailObj;
import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.HealthLicenceJob;
import com.bkeryah.entities.HealthMasterLicence;
import com.bkeryah.entities.HrScenario;
import com.bkeryah.entities.HrsSigns;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class HealthCertificateViewBean {
	protected static final Logger logger = LogManager.getLogger(HealthCertificateViewBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private String recordId;
	private boolean canSave = true;
	private ArcUsers currentUser;
	private UserMailObj selectedInbox;
	private WrkApplicationId WrkId;
	private int arcRecordId;
	private List<WrkPurpose> wrkPurposes;
	private Integer stepNum;
	private Integer fineSetupId;
	// private ReqFinesMaster finesMaster = new ReqFinesMaster();
	private boolean enablePrint;
	private String applicationPurpose;
	private String wrkAppComment;
	private HealthMasterLicence healthCertificate = new HealthMasterLicence();
	private String ownerApllType;
	private String jobTitle;
	private String gender;
	private Integer paramId;

	@PostConstruct
	private void init() {
		currentUser = Utils.findCurrentUser();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		recordId = (String) httpSession.getAttribute("arcRecord");
		// httpSession.removeAttribute("arcRecord");
		if (recordId != null) {
			selectedInbox = (UserMailObj) httpSession.getAttribute("selectedMail");
			WrkId = new WrkApplicationId(Integer.parseInt(this.selectedInbox.WrkId), selectedInbox.StepId);
			arcRecordId = Integer.parseInt(recordId.trim());
			wrkPurposes = dataAccessService.getAllPurposes();
			stepNum = dataAccessService.getStepNumberFromHrSign(arcRecordId);
			HrsSigns model = dataAccessService.getHrsSignsByArcId(arcRecordId);
			healthCertificate = dataAccessService.findHealthMasterLicenceRecordId(arcRecordId);
		}
		if (healthCertificate != null) {
			System.out.println("Health Application Id >>>>" + healthCertificate.getApplicationId());
			// set all data
			// and get details
		}
		HrScenario scenario = (HrScenario) dataAccessService.findEntityById(HrScenario.class,
				MailTypeEnum.HEALTHCERTIFICATE.getValue());
		if (scenario != null)
			if (stepNum == scenario.getStepsCount() + 1) {
				enablePrint = true;
			} else {
				enablePrint = false;
			}

		// activityTypes = dataAccessService.getAllLicActivityTypeList();
		// 2 = dept id صحة البيئة
		// supervisors = dataAccessService.getAllSupervisor(2);
		// codesFines = dataAccessService.getCodesFines();
		// ReqFinesSetup reqFinesSetup = new ReqFinesSetup();
		// codesFinesList.add(reqFinesSetup);
	}

	public String accept() {

		wrkAppComment = Utils.loadMessagesFromFile("accept.operation") + currentUser.getEmployeeName();
		applicationPurpose = "1";
		dataAccessService.acceptHealthCertificate(healthCertificate, arcRecordId,
				MailTypeEnum.HEALTHCERTIFICATE.getValue(), wrkAppComment, Integer.parseInt(applicationPurpose.trim()));
		return "mails";
	}

	public String refuseAction() {
		try {
			applicationPurpose = "2";
			healthCertificate.setStatus(MyConstants.NO);
			String wrkCommentRefuse = wrkAppComment;
			dataAccessService.refuseHealthCertificate(WrkId, arcRecordId, healthCertificate, wrkCommentRefuse,
					Integer.parseInt(applicationPurpose.trim()));
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("refuse.record"));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}

	public String printCertificateCardReport() {
		String reportName = "/reports/test_health_card.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("appl_id", healthCertificate.getApplicationId());
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public Integer getFineSetupId() {
		return fineSetupId;
	}

	public void setFineSetupId(Integer fineSetupId) {
		this.fineSetupId = fineSetupId;
	}

	public boolean isCanSave() {
		return canSave;
	}

	public void setCanSave(boolean canSave) {
		this.canSave = canSave;
	}

	public UserMailObj getSelectedInbox() {
		return selectedInbox;
	}

	public void setSelectedInbox(UserMailObj selectedInbox) {
		this.selectedInbox = selectedInbox;
	}

	public WrkApplicationId getWrkId() {
		return WrkId;
	}

	public void setWrkId(WrkApplicationId wrkId) {
		WrkId = wrkId;
	}

	public int getArcRecordId() {
		return arcRecordId;
	}

	public void setArcRecordId(int arcRecordId) {
		this.arcRecordId = arcRecordId;
	}

	public List<WrkPurpose> getWrkPurposes() {
		return wrkPurposes;
	}

	public void setWrkPurposes(List<WrkPurpose> wrkPurposes) {
		this.wrkPurposes = wrkPurposes;
	}

	public Integer getStepNum() {
		return stepNum;
	}

	public void setStepNum(Integer stepNum) {
		this.stepNum = stepNum;
	}

	public boolean isEnablePrint() {
		return enablePrint;
	}

	public void setEnablePrint(boolean enablePrint) {
		this.enablePrint = enablePrint;
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

	public ArcUsers getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(ArcUsers currentUser) {
		this.currentUser = currentUser;
	}

	public HealthMasterLicence getHealthCertificate() {
		return healthCertificate;
	}

	public void setHealthCertificate(HealthMasterLicence healthCertificate) {
		this.healthCertificate = healthCertificate;
	}

	public String getOwnerApllType() {
		if (healthCertificate.getApplicationType() != null) {
			switch (healthCertificate.getApplicationType()) {
			case 0:
				ownerApllType = "سجل مدني / هوية";
				break;
			case 1:
				ownerApllType = "سجل تجاري";
				break;
			case 2:
				ownerApllType = "إقامة";
				break;
			case 3:
				ownerApllType = "جواز سفر";
				break;

			default:
				break;
			}
		}
		return ownerApllType;
	}

	public void setOwnerApllType(String ownerApllType) {
		this.ownerApllType = ownerApllType;
	}

	public String getJobTitle() {
		HealthCertificateBean healthCertifi = new HealthCertificateBean();
		List<HealthLicenceJob> jobs = healthCertifi.getHlthLicJobsList();
		for (HealthLicenceJob job : jobs) {
			if (healthCertificate.getJobId().equals(job.getId()))
				jobTitle = job.getName();
		}
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getGender() {
		if (healthCertificate.getGender() != null) {
			switch (healthCertificate.getGender()) {
			case 1:
				gender = "ذكر";
				break;
			case 2:
				gender = "أنثى";
				break;
			default:
				break;
			}
		}

		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getParamId() {
		return paramId;
	}

	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}
}

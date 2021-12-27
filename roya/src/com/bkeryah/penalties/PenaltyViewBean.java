package com.bkeryah.penalties;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.bkeryah.entities.HrScenario;
import com.bkeryah.entities.HrsSigns;
import com.bkeryah.entities.LicActivityTypeRy;
import com.bkeryah.entities.WrkApplicationId;
import com.bkeryah.entities.WrkPurpose;
import com.bkeryah.mails.MailTypeEnum;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import service.SmsService;
import sms.sender.ResponseTypeEnum;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class PenaltyViewBean {
	protected static final Logger logger = LogManager.getLogger(PenaltyViewBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<User> supervisors;
	private List<ReqFinesSetup> codesFinesList = new ArrayList<ReqFinesSetup>();
	private Integer supervisorId;
	public ReqFinesMaster reqFinesMaster;
	private List<ReqFinesDetails> reqFinesDetailsList = new ArrayList<ReqFinesDetails>();
	private String recordId;
	private LicTrdMasterFile trdMasterFile;
	private boolean canSave = true;
	private List<LicActivityTypeRy> activityTypes;
	private Integer fineSetupId;
	private UserMailObj selectedInbox;
	private WrkApplicationId WrkId;
	private int arcRecordId;
	private List<WrkPurpose> wrkPurposes;
	private Integer stepNum;
	private ReqFinesMaster finesMaster = new ReqFinesMaster();
	private boolean enablePrint;
	private String applicationPurpose;
	private String wrkAppComment;
	private ArcUsers currentUser;
	private HrScenario scenario;
	private ReqFinesSetup selectedCodeFiner = new ReqFinesSetup();
	private List<ReqFinesSetup> codesFines;
	private boolean hideAddBtn = false;

	@PostConstruct
	private void init() {
		// لائحة العقوبات
		codesFines = dataAccessService.getCodesFines();
		currentUser = Utils.findCurrentUser();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		recordId = (String) httpSession.getAttribute("arcRecord");
		httpSession.removeAttribute("arcRecord");
		if (recordId != null) {
			selectedInbox = (UserMailObj) httpSession.getAttribute("selectedMail");
			WrkId = new WrkApplicationId(Integer.parseInt(this.selectedInbox.WrkId), selectedInbox.StepId);
			arcRecordId = Integer.parseInt(recordId.trim());
			wrkPurposes = dataAccessService.getAllPurposes();
			stepNum = dataAccessService.getStepNumberFromHrSign(arcRecordId);
			HrsSigns model = dataAccessService.getHrsSignsByArcId(arcRecordId);
			finesMaster = dataAccessService.findPenaltyByArchRecordId(arcRecordId);
		}
		if (finesMaster != null) {
			System.out.println(">>>>>>" + finesMaster.getFineNo());
			// set all data
			// and get details
		}
		scenario = (HrScenario) dataAccessService.findEntityById(HrScenario.class, MailTypeEnum.PENALTY.getValue());
		if (scenario != null) {
			if (stepNum == scenario.getStepsCount() + 1) {
				enablePrint = true;
			} else {
				enablePrint = false;
			}
			// for manager edit fines
			if (stepNum == scenario.getStepsCount() - 1) {
				hideAddBtn = true;
			} else {
				hideAddBtn = false;
			}
		}
		// activityTypes = dataAccessService.getAllLicActivityTypeList();
		// 2 = dept id صحة البيئة
		// supervisors = dataAccessService.getAllSupervisor(2);
		// codesFines = dataAccessService.getCodesFines();
		// ReqFinesSetup reqFinesSetup = new ReqFinesSetup();
		// codesFinesList.add(reqFinesSetup);
	}

	public String accept() {
		try {
			wrkAppComment = Utils.loadMessagesFromFile("accept.operation") + currentUser.getEmployeeName();
			applicationPurpose = "1";
			dataAccessService.acceptPenalty(finesMaster, arcRecordId, MailTypeEnum.PENALTY.getValue(), wrkAppComment,
					Integer.parseInt(applicationPurpose.trim()));

		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
		return "mails";
	}

	public String refuseAction() {
		try {
			applicationPurpose = "2";
			finesMaster.setStatus(MyConstants.NO);
			String wrkCommentRefuse = wrkAppComment;
			dataAccessService.refusePenalty(WrkId, arcRecordId, finesMaster, wrkCommentRefuse,
					Integer.parseInt(applicationPurpose.trim()));
			// NotifFinesMastR notifMstr = new NotifFinesMastR();
			// notifMstr.setPenaltyIsRecoreded("N");
			// dataAccessService.updateObject(notifMstr);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("refuse.record"));
			return "mails";
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			return "";
		}
	}

	public String printPenalty() {
		try {
			String reportName = "/reports/penality.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fineNo", finesMaster.getFineNo());
			parameters.put("supervisor", finesMaster.getSupervisorNameView());
			parameters.put("LOGO_DIR", FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath(Utils.loadMessagesFromFile("report.logo")));
			Utils.printPdfReport(reportName, parameters);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return "";
	}

	public void deleteFiner(ReqFinesDetails reqFinesSetup) {
		if (finesMaster != null && finesMaster.getReqFinesDetailsList() != null
				&& finesMaster.getReqFinesDetailsList().size() > 1) {
			try {
				dataAccessService.deleteObject(reqFinesSetup);
				finesMaster = dataAccessService.findPenaltyByArchRecordId(arcRecordId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("removed.....");
		}
	}

	public void calcFineValuesSum() {
		Integer fineValue = selectedCodeFiner.getFineValue();
		Integer fineNbr = selectedCodeFiner.getFineNbr();
		if (fineValue != null && fineNbr != null && fineValue > 0 && fineNbr > 0) {
			selectedCodeFiner.setFineValSum(fineValue * fineNbr);
			System.out.println(selectedCodeFiner.getFineValSum());

			// calcFineSum();
		}
	}

	public String addCodeFiner() {
		calcFineValuesSum();
		ReqFinesDetails reqFinesDetails = addNewReqFinesDetails(selectedCodeFiner);
		if (reqFinesDetails != null) {
			reqFinesDetails.setFineNo(finesMaster.getFineNo());
			try {
				dataAccessService.saveObject(reqFinesDetails);
				finesMaster = dataAccessService.findPenaltyByArchRecordId(arcRecordId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("saved.....");
			selectedCodeFiner = new ReqFinesSetup();
		}
		return "";
	}

	private ReqFinesDetails addNewReqFinesDetails(ReqFinesSetup codeFines) {
		ReqFinesDetails reqFinesDetails = new ReqFinesDetails();
		reqFinesDetails.setFineCode(codeFines.getId());
		reqFinesDetails.setFineCount(codeFines.getFineNbr());
		reqFinesDetails.setFineValue(codeFines.getFineValue());
		reqFinesDetails.setTypeValue(Integer.parseInt(codeFines.getTypeValue()));
		reqFinesDetails.setRepeat(Integer.parseInt(codeFines.getRepeat()));
		reqFinesDetails.setNotifyDTLsId(codeFines.getNotifyDTLsId());
		reqFinesDetails.setFineDesc2(codeFines.getNotes());
		return reqFinesDetails;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<ReqFinesSetup> getCodesFinesList() {
		return codesFinesList;
	}

	public void setCodesFinesList(List<ReqFinesSetup> codesFinesList) {
		this.codesFinesList = codesFinesList;
	}

	public Integer getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Integer supervisorId) {
		this.supervisorId = supervisorId;
	}

	public List<User> getSupervisors() {
		return supervisors;
	}

	public void setSupervisors(List<User> supervisors) {
		this.supervisors = supervisors;
	}

	public ReqFinesMaster getReqFinesMaster() {
		return reqFinesMaster;
	}

	public void setReqFinesMaster(ReqFinesMaster reqFinesMaster) {
		this.reqFinesMaster = reqFinesMaster;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public LicTrdMasterFile getTrdMasterFile() {
		return trdMasterFile;
	}

	public void setTrdMasterFile(LicTrdMasterFile trdMasterFile) {
		this.trdMasterFile = trdMasterFile;
	}

	public List<LicActivityTypeRy> getActivityTypes() {
		return activityTypes;
	}

	public void setActivityTypes(List<LicActivityTypeRy> activityTypes) {
		this.activityTypes = activityTypes;
	}

	public List<ReqFinesDetails> getReqFinesDetailsList() {
		return reqFinesDetailsList;
	}

	public void setReqFinesDetailsList(List<ReqFinesDetails> reqFinesDetailsList) {
		this.reqFinesDetailsList = reqFinesDetailsList;
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

	public ReqFinesMaster getFinesMaster() {
		return finesMaster;
	}

	public void setFinesMaster(ReqFinesMaster finesMaster) {
		this.finesMaster = finesMaster;
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

	public HrScenario getScenario() {
		return scenario;
	}

	public void setScenario(HrScenario scenario) {
		this.scenario = scenario;
	}

	public ReqFinesSetup getSelectedCodeFiner() {
		return selectedCodeFiner;
	}

	public void setSelectedCodeFiner(ReqFinesSetup selectedCodeFiner) {
		this.selectedCodeFiner = selectedCodeFiner;
	}

	public List<ReqFinesSetup> getCodesFines() {
		return codesFines;
	}

	public void setCodesFines(List<ReqFinesSetup> codesFines) {
		this.codesFines = codesFines;
	}

	public boolean isHideAddBtn() {
		return hideAddBtn;
	}

	public void setHideAddBtn(boolean hideAddBtn) {
		this.hideAddBtn = hideAddBtn;
	}
}

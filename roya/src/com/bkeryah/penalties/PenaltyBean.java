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
import org.primefaces.event.SelectEvent;
import org.springframework.util.CollectionUtils;

import com.bkeryah.entities.LicActivityTypeRy;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class PenaltyBean {
	protected static final Logger logger = LogManager.getLogger(PenaltyBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private Integer arcPeople;
	private Integer activityType;
	private List<ActivityType> activitiesTypes;
	private List<User> supervisors;
	private List<ReqFinesSetup> codesFines;
	private List<ReqFinesSetup> codesFinesList = new ArrayList<ReqFinesSetup>();
	private Integer supervisorId;
	private List<LicTrdMasterFile> arcPeoples;
	private List<LicTrdMasterFile> filteredArcPeoples;
	public ReqFinesMaster reqFinesMaster;
	private ReqFinesSetup selectedCodeFiner = new ReqFinesSetup();
	private LicTrdMasterFile selectedPeople;
	private List<ReqFinesDetails> reqFinesDetailsList = new ArrayList<ReqFinesDetails>();
	private double FineSum = 0;
	private String licenceId;
	private LicTrdMasterFile trdMasterFile;
	private boolean fineForLic;
	private boolean canSave = true;
	private List<LicTrdMasterFile> licencesList;
	private List<LicActivityTypeRy> activityTypes;
	private Integer fineSetupId;
	private NotifFinesMastR notifMstr;
	private boolean fineByNot = false;
	
	@PostConstruct
	private void init() {
		reqFinesMaster = new ReqFinesMaster();
		notifMstr = new NotifFinesMastR();
		trdMasterFile = new LicTrdMasterFile();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		notifMstr = (NotifFinesMastR) httpSession.getAttribute("notifMstr");
		httpSession.removeAttribute("notifMstr");
		if (notifMstr != null) {

			trdMasterFile = (LicTrdMasterFile) dataAccessService.findEntityById(LicTrdMasterFile.class,
					notifMstr.getLicenceId());
			if (trdMasterFile != null) {
				initReqFinesMaster();
				penaltyDetails();
				fineByNot = true;
				canSave = false;
			}
		}
		if (notifMstr == null) {
			notifMstr = new NotifFinesMastR();
		}
		activityTypes = dataAccessService.getAllLicActivityTypeList();
		System.out.println(activityTypes.size());
		//load Supervisors in dept by user log in dept id 
		supervisors = dataAccessService.getAllSupervisor(Utils.findCurrentUser().getDeptId());
		// لائحة العقوبات
		codesFines = dataAccessService.getCodesFines();
		// ReqFinesSetup reqFinesSetup = new ReqFinesSetup();
		// codesFinesList.add(reqFinesSetup);

	}

	public void checkLic() {
		if (selectedPeople != null) {
			createtReqFinesMaster();
		}
	}

	private void initReqFinesMaster() {
		reqFinesMaster = new ReqFinesMaster();
		reqFinesMaster.setfIdNo(trdMasterFile.getAplOwner());
		reqFinesMaster.setfName(trdMasterFile.getLicOwnerName());
		reqFinesMaster.setfTradeMarkName(trdMasterFile.getTrdName());
		reqFinesMaster.setfAddress(trdMasterFile.getLicAdress());
		reqFinesMaster.setfLicStartDate(trdMasterFile.getLicBeginDate());
		reqFinesMaster.setfLicEndDate(trdMasterFile.getLicEndDate());
		reqFinesMaster.setfLicenceNo(trdMasterFile.getLicNo());
		reqFinesMaster.setPhoneNumber(trdMasterFile.getPhoneNumber());
		reqFinesMaster.setFineDate(HijriCalendarUtil.findCurrentHijriDate());
		reqFinesMaster.setfDeptNo(Utils.findCurrentUser().getDeptId().toString());
		reqFinesMaster.setDeptName(Utils.findCurrentUser().getUserDept().getDeptName());
		reqFinesMaster.setActivityType(trdMasterFile.getActivtyType());
		reqFinesMaster.setNotifyDate(notifMstr.getNotifDate());
		reqFinesMaster.setNotifyMasterId(notifMstr.getId());
		supervisorId = notifMstr.getSupervisorId();

		System.out.println("active type " + reqFinesMaster.getActivityType());
		reqFinesMaster.setEntryDate(HijriCalendarUtil.findCurrentHijriDate());
		if (trdMasterFile.getMhlId() != null) {
			reqFinesMaster.setfKeycode(Utils.getFormatKeyCode(trdMasterFile.getMhlId()));
		}

	}

	public void penaltyDetails() {

		if (notifMstr.getNotifFinesDetailsList() != null && notifMstr.getNotifFinesDetailsList().size() > 0) {
			for (NotifFinesDetL item : notifMstr.getNotifFinesDetailsList()) {
				ReqFinesSetup obj = (ReqFinesSetup) dataAccessService.findEntityById(ReqFinesSetup.class,
						item.getFineSetupId());
				obj.setRepeat(item.getDuplicated().toString());
				obj.setTypeValue(item.getUpDown().toString());
				obj.setFineValue(item.getUpDown() == 1 ? obj.getFineSupermumValue() : obj.getFineMinimumValue());
				obj.setNotifyDTLsId(item.getId());
				codesFinesList.add(obj);
			}
		}

	}

	public String save() {
		if (supervisorId != null && reqFinesMaster.getfIdNo() != null) {
			try {

				reqFinesMaster.setfSupervisorCode(supervisorId.toString());
				reqFinesMaster.setStatus("R");
				setDetailsFiner();
				calcFineSum();
				reqFinesMaster.setTotalValue(FineSum);
				// reqFinesMaster.setActivityType(activityType);
				dataAccessService.saveLicencePenalty(reqFinesMaster, reqFinesDetailsList, true);

				codesFinesList.clear();
				reqFinesDetailsList.clear();
				reqFinesDetailsList = new ArrayList<>();
				codesFinesList = new ArrayList<ReqFinesSetup>();
				reqFinesMaster = new ReqFinesMaster();
				// ReqFinesSetup reqFinesSetup = new ReqFinesSetup();
				// codesFinesList.add(reqFinesSetup);
				if (notifMstr != null && notifMstr.getId() != null) {
					notifMstr.setPenaltyIsRecoreded("Y");
					dataAccessService.updateObject(notifMstr);
				}
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));

			} catch (Exception e) {
				logger.error("Penalty save " + e.getMessage());
				e.printStackTrace();
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
				MsgEntry.addErrorMessage("ادخل العدد");
			}
		} else {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.supervisor"));

		}
		return "";
	}

	public void addFiner() {
		// reqFinesSetup.setFineCode(selectedCodeFiner.getFineCode());
		// reqFinesSetup.setId(selectedCodeFiner.getId());
		// reqFinesSetup.setFineDesc(selectedCodeFiner.getFineDesc());
		// reqFinesSetup.setVisible(false);
		//
		// reqFinesSetup.setFineDesc(reqFinesSetup.getFineDesc());
		ReqFinesDetails reqFinesDetails = addNewReqFinesDetails(selectedCodeFiner);
		reqFinesDetailsList.add(reqFinesDetails);
	}

	private void createtReqFinesMaster() {
		reqFinesMaster = new ReqFinesMaster();
		reqFinesMaster.setfIdNo(selectedPeople.getAplOwner());
		reqFinesMaster.setfName(selectedPeople.getLicOwnerName());
		reqFinesMaster.setfTradeMarkName(selectedPeople.getTrdName());
		reqFinesMaster.setfAddress(selectedPeople.getLicAdress());
		reqFinesMaster.setfLicStartDate(selectedPeople.getLicBeginDate());
		reqFinesMaster.setfLicEndDate(selectedPeople.getLicEndDate());
		reqFinesMaster.setfLicenceNo(selectedPeople.getLicNo());
		reqFinesMaster.setPhoneNumber(selectedPeople.getPhoneNumber());
		reqFinesMaster.setFineDate(HijriCalendarUtil.findCurrentHijriDate());
		reqFinesMaster.setfDeptNo(Utils.findCurrentUser().getDeptId().toString());
		reqFinesMaster.setDeptName(Utils.findCurrentUser().getUserDept().getDeptName());
		reqFinesMaster.setActivityType(selectedPeople.getActivtyType());
		System.out.println("active type " + reqFinesMaster.getActivityType());
		reqFinesMaster.setEntryDate(HijriCalendarUtil.findCurrentHijriDate());
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

	public void updateFiner(ReqFinesSetup reqFinesSetup) {
		reqFinesSetup.setFineCode(selectedCodeFiner.getFineCode());
		reqFinesSetup.setFineDesc(selectedCodeFiner.getFineDesc());
		reqFinesSetup.setFineMinimumValue(selectedCodeFiner.getFineMinimumValue());
		reqFinesSetup.setFineSupermumValue(selectedCodeFiner.getFineSupermumValue());
	}

	public void deleteFiner(ReqFinesSetup reqFinesSetup) {
		if (codesFinesList.size() > 0) {
			codesFinesList.remove(reqFinesSetup);
			// System.out.println("size......."+reqFinesDetailsList.size());
			// ReqFinesDetails reqFinesDetails =
			// addNewReqFinesDetails(selectedCodeFiner);
			// reqFinesDetailsList.remove(reqFinesDetails);
			// System.out.println("removed....."+reqFinesDetailsList.size());
		}
		if (codesFinesList.size() < 1) {
			canSave = true;
		} else {
			canSave = false;
		}

	}

	public void setDetailsFiner() {
		if (codesFinesList.size() > 0) {
			for (ReqFinesSetup reqFinesSetup : codesFinesList) {
				ReqFinesDetails reqFinesDetails = addNewReqFinesDetails(reqFinesSetup);
				reqFinesDetailsList.add(reqFinesDetails);
			}
		}

	}

	public void calcFineValSum(ReqFinesSetup reqFinesSetup) {
		Integer fineValue = reqFinesSetup.getFineValue();
		Integer fineNbr = reqFinesSetup.getFineNbr();
		if (fineValue != null && fineNbr != null && fineValue > 0 && fineNbr > 0) {
			reqFinesSetup.setFineValSum(fineValue * fineNbr);
			// canSave = true;
			// calcFineSum();
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
		codesFinesList.add(selectedCodeFiner);

		if (codesFinesList.size() > 0) {
			canSave = false;
		}
		// addFiner();
		selectedCodeFiner = new ReqFinesSetup();
		return "";
	}

	public String printReportPenalityAction() {
		try {
			String reportName = "/reports/penality_report.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fine_no", notifMstr.getId());
			parameters.put("now", HijriCalendarUtil.findCurrentHijriDate());
			// parameters.put("SUBREPORT_DIR",
			// FacesContext.getCurrentInstance().getExternalContext()
			// .getRealPath("/reports/sub_penality_report.jasper"));
			Utils.printPdfReport(reportName, parameters);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
	}

	public String returnToLicence() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("licenceNo", licenceId);
		return "licence_penalities";
	}

	public void calcFineSum() {
		FineSum = 0;
		for (ReqFinesSetup finer : codesFinesList) {
			FineSum += finer.getFineValSum();
		}

	}

	public void onRowSelect(LicTrdMasterFile people) {
		selectedPeople = people;
		if (selectedPeople != null)
			createtReqFinesMaster();
		System.out.println(">>>>>>" + selectedPeople.getAplOwner());

	}

	public List<ActivityType> getActivitiesTypes() {
		return activitiesTypes;
	}

	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	public void setActivitiesTypes(List<ActivityType> activitiesTypes) {
		this.activitiesTypes = activitiesTypes;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public LicTrdMasterFile getSelectedPeople() {
		return selectedPeople;
	}

	public void setSelectedPeople(LicTrdMasterFile selectedPeople) {
		this.selectedPeople = selectedPeople;
	}

	public List<LicTrdMasterFile> getFilteredArcPeoples() {
		return filteredArcPeoples;
	}

	public void setFilteredArcPeoples(List<LicTrdMasterFile> filteredArcPeoples) {
		this.filteredArcPeoples = filteredArcPeoples;
	}

	public List<LicTrdMasterFile> getArcPeoples() {
		return arcPeoples;
	}

	public void setArcPeoples(List<LicTrdMasterFile> arcPeoples) {
		this.arcPeoples = arcPeoples;
	}

	public ReqFinesSetup getSelectedCodeFiner() {
		return selectedCodeFiner;
	}

	public void setSelectedCodeFiner(ReqFinesSetup selectedCodeFiner) {
		this.selectedCodeFiner = selectedCodeFiner;
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

	public List<ReqFinesSetup> getCodesFines() {
		return codesFines;
	}

	public void setCodesFines(List<ReqFinesSetup> codesFines) {
		this.codesFines = codesFines;
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

	public Integer getArcPeople() {
		return arcPeople;
	}

	public void setArcPeople(Integer arcPeople) {
		this.arcPeople = arcPeople;
	}

	public void onRowSelectCancel(SelectEvent event) {
		// selectedPeople = (LicTrdMasterFile) event.getObject();
	}

	public boolean isFineForLic() {
		return fineForLic;
	}

	public void setFineForLic(boolean fineForLic) {
		this.fineForLic = fineForLic;
	}

	public String getLicenceId() {
		return licenceId;
	}

	public void setLicenceId(String licenceId) {
		this.licenceId = licenceId;
	}

	public LicTrdMasterFile getTrdMasterFile() {
		return trdMasterFile;
	}

	public void setTrdMasterFile(LicTrdMasterFile trdMasterFile) {
		this.trdMasterFile = trdMasterFile;
	}

	public List<LicTrdMasterFile> getLicencesList() {
		if (CollectionUtils.isEmpty(licencesList))
			licencesList = dataAccessService.getAllLicencesList();
		return licencesList;
	}

	public void setLicencesList(List<LicTrdMasterFile> licencesList) {
		this.licencesList = licencesList;
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

	public NotifFinesMastR getNotifMstr() {
		return notifMstr;
	}

	public void setNotifMstr(NotifFinesMastR notifMstr) {
		this.notifMstr = notifMstr;
	}

	public boolean isFineByNot() {
		return fineByNot;
	}

	public void setFineByNot(boolean fineByNot) {
		this.fineByNot = fineByNot;
	}

	public double getFineSum() {
		return FineSum;
	}

	public void setFineSum(double fineSum) {
		FineSum = fineSum;
	}

	
}

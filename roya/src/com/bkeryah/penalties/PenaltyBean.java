package com.bkeryah.penalties;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
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
	protected static final Logger logger = Logger.getLogger(PenaltyBean.class);
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

	@PostConstruct
	private void init() {
		reqFinesMaster = new ReqFinesMaster();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		licenceId = (String) httpSession.getAttribute("licenceNo");
		httpSession.removeAttribute("licenceNo");
		if (licenceId != null) {
			fineForLic = true;
			trdMasterFile = dataAccessService.findLicByLicId(licenceId);
			initReqFinesMaster();
		}
		activityTypes = dataAccessService.getAllLicActivityTypeList();
		System.out.println(activityTypes.size());
		activitiesTypes = new ArrayList<ActivityType>();
		activitiesTypes.add(new ActivityType(0, Utils.loadMessagesFromFile("sihi")));
		activitiesTypes.add(new ActivityType(1, Utils.loadMessagesFromFile("tijari")));
		// 2 = dept id صحة البيئة
		supervisors = dataAccessService.getAllSupervisor(2);
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
		reqFinesMaster.setfLicenceNo(licenceId);
		reqFinesMaster.setFineDate(HijriCalendarUtil.findCurrentHijriDate());
		reqFinesMaster.setfDeptNo(Utils.findCurrentUser().getDeptId().toString());
		reqFinesMaster.setfAddress(trdMasterFile.getLicAdress());
		reqFinesMaster.setDeptName(Utils.findCurrentUser().getUserDept().getDeptName());
		reqFinesMaster.setfName(trdMasterFile.getLicOwnerName());
		reqFinesMaster.setfIdNo(trdMasterFile.getAplOwner());
		reqFinesMaster.setfTradeMarkName(trdMasterFile.getTrdName());
		reqFinesMaster.setEntryDate(HijriCalendarUtil.findCurrentHijriDate());
		reqFinesMaster.setfKeycode(Utils.getFormatKeyCode(trdMasterFile.getMhlId()));
	}

	public double getFineSum() {
		return FineSum;
	}

	public void setFineSum(double fineSum) {
		FineSum = fineSum;
	}

	public String save() {
		if (supervisorId != null && reqFinesMaster.getfIdNo() != null) {
			try {
				reqFinesMaster.setTotalValue(FineSum);
				reqFinesMaster.setfSupervisorCode(supervisorId.toString());
				reqFinesMaster.setStatus("Y");
				setDetailsFiner();
				calcFineSum();
				// reqFinesMaster.setActivityType(activityType);
				dataAccessService.saveLicencePenalty(reqFinesMaster, reqFinesDetailsList, true);
				codesFinesList.clear();
				codesFinesList = new ArrayList<ReqFinesSetup>();
				reqFinesMaster = new ReqFinesMaster();
				// ReqFinesSetup reqFinesSetup = new ReqFinesSetup();
				// codesFinesList.add(reqFinesSetup);
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));

			} catch (Exception e) {
				logger.error("Penalty save " + e.getMessage());
				e.printStackTrace();
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			}
		}
		else {
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
			calcFineSum();
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
}

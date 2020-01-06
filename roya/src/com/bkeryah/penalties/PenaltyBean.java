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

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
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
	private List<ArcUsers> supervisors;
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

	@PostConstruct
	private void init() {
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
		activitiesTypes = new ArrayList<ActivityType>();
		activitiesTypes.add(new ActivityType(0, Utils.loadMessagesFromFile("sihi")));
		activitiesTypes.add(new ActivityType(1, Utils.loadMessagesFromFile("tijari")));
	//	supervisors = dataAccessService.getAllUsers();
		supervisors = dataAccessService.getAllActiveEmployeesInDept(Utils.findCurrentUser().getDeptId());
		codesFines = dataAccessService.getCodesFines();
		ReqFinesSetup reqFinesSetup = new ReqFinesSetup();
		codesFinesList.add(reqFinesSetup);
			
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
		try {
			reqFinesMaster.setTotalValue(FineSum);
			reqFinesMaster.setActivityType(activityType);
			reqFinesMaster.setfSupervisorCode(supervisorId.toString());
			reqFinesMaster.setfAddress(selectedPeople.getLicAdress());
			reqFinesMaster.setfTradeMarkName(selectedPeople.getTrdName());
			dataAccessService.saveLicencePenalty(reqFinesMaster, reqFinesDetailsList, true);
			codesFinesList.clear();
			codesFinesList = new ArrayList<ReqFinesSetup>();
			reqFinesMaster = new ReqFinesMaster();
			ReqFinesSetup reqFinesSetup = new ReqFinesSetup();
			codesFinesList.add(reqFinesSetup);
			return "";
		} catch (Exception e) {
			logger.error("Penalty save " + e.getMessage());
		}
		return "";
	}

	public void addFiner(ReqFinesSetup reqFinesSetup) {
		reqFinesSetup.setFineCode(selectedCodeFiner.getFineCode());
		reqFinesSetup.setId(selectedCodeFiner.getId());
		reqFinesSetup.setFineDesc(selectedCodeFiner.getFineDesc());
		reqFinesSetup.setVisible(false);
		reqFinesSetup.setFineDesc(reqFinesSetup.getFineDesc());
		ReqFinesDetails reqFinesDetails = addNewReqFinesDetails(reqFinesSetup);
		reqFinesDetailsList.add(reqFinesDetails);

		ReqFinesSetup newReqFinesSetup = new ReqFinesSetup();
		codesFinesList.add(newReqFinesSetup);
	}

	private void createtReqFinesMaster() {
		reqFinesMaster = new ReqFinesMaster();
		reqFinesMaster.setfIdNo(selectedPeople.getAplOwner());
		reqFinesMaster.setfLicenceNo(selectedPeople.getLicNo());
		reqFinesMaster.setfAddress(selectedPeople.getLicAdress());
		reqFinesMaster.setfName(selectedPeople.getTrdName());
		reqFinesMaster.setFineDate(HijriCalendarUtil.findCurrentHijriDate());
		reqFinesMaster.setfDeptNo(Utils.findCurrentUser().getDeptId().toString());
		reqFinesMaster.setDeptName(Utils.findCurrentUser().getUserDept().getDeptName());
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
		codesFinesList.remove(reqFinesSetup);
		if (codesFinesList.size() > 1)
			calcFineSum();
	}

	public void calcFineValSum(ReqFinesSetup reqFinesSetup) {
		Integer fineValue = reqFinesSetup.getFineValue();
		Integer fineNbr = reqFinesSetup.getFineNbr();
		if (fineValue != null && fineNbr != null && fineValue > 0 && fineNbr > 0) {
			reqFinesSetup.setFineValSum(fineValue * fineNbr);
			calcFineSum();
		}
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

	public List<ArcUsers> getSupervisors() {
		return supervisors;
	}

	public void setSupervisors(List<ArcUsers> supervisors) {
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

	public void onRowSelect(SelectEvent event) {
		selectedPeople = (LicTrdMasterFile) event.getObject();
	}

}

package com.bkeryah.penalties;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.Highlighter;

import org.apache.http.util.TextUtils;
import org.apache.logging.log4j.*;
import org.primefaces.event.SelectEvent;

import com.bkeryah.entities.ArcPeopleModel;
import com.bkeryah.entities.CodesFines;
import com.bkeryah.model.User;
import com.bkeryah.service.DataAccessService;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class LicPenaltyBean {
	protected static final Logger logger = LogManager.getLogger(LicPenaltyBean.class);

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private Integer arcPeople;
	private List<User> supervisors;
	private List<ReqFinesSetup> codesFines;
	private List<ReqFinesDetails> reqFinesDetailsList = new ArrayList<ReqFinesDetails>();
	private String licenceId;
	private String codeFine;
	private List<ReqFinesSetup> codesFinesList = new ArrayList<ReqFinesSetup>();
	private Integer itemId;
	private Integer supervisorId;
	private List<ArcPeopleModel> arcPeoples;
	private List<ArcPeopleModel> filteredArcPeoples;
	private LicTrdMasterFile trdMasterFile;
	public ReqFinesMaster reqFinesMaster = new ReqFinesMaster();
	private LicTrdMasterFile licTrdMasterFile;
	private ReqFinesSetup selectedCodeFiner = new ReqFinesSetup();
	private LicTrdMasterFile selectedLic;
	private List<ActivityType> activitiesTypes = new ArrayList<>();
	private Integer activityType;

	public List<ActivityType> getActivitiesTypes() {
		return activitiesTypes;
	}

	public void setActivitiesTypes(List<ActivityType> activitiesTypes) {
		this.activitiesTypes = activitiesTypes;
	}

	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	public void onRowSelect(SelectEvent event) {
		selectedLic = (LicTrdMasterFile) event.getObject();

	}

	public String addLicencePenality() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("licenceNo", licenceId);
		return "LicPenalty";
	}

	@PostConstruct
	private void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		licenceId = (String) httpSession.getAttribute("licenceNo");
		httpSession.removeAttribute("licenceNo");
		if (licenceId != null) {
			trdMasterFile = dataAccessService.findLicByLicId(licenceId);
			initReqFinesMaster();
		}
		supervisors = dataAccessService.getAllUsers();
		codesFines = dataAccessService.getCodesFines();
		activitiesTypes.add(new ActivityType(1, Utils.loadMessagesFromFile("tijari")));
		activitiesTypes.add(new ActivityType(0, Utils.loadMessagesFromFile("sihi")));
		ReqFinesSetup CodesFiner = new ReqFinesSetup();
		codesFinesList.add(CodesFiner);
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

	public String save() {
		try {
			reqFinesMaster.setActivityType(activityType);
			reqFinesMaster.setfSupervisorCode(supervisorId.toString());
			dataAccessService.saveLicencePenalty(reqFinesMaster, reqFinesDetailsList, true);
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
			session.setAttribute("licenceNo", licenceId);
			return "licence_penalities";
		} catch (Exception e) {
			logger.error("licPenalty save " + licenceId + " error " + e.getMessage());
		}
		return null;
	}


	public String checkLic() {

		reqFinesMaster = dataAccessService.checkLicLst(trdMasterFile.getLicNo());
		return "";
	}

	public String returnToLicence() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("licenceNo", licenceId);
		return "licence_penalities";
	}

	public void addFiner(ReqFinesSetup codeFines) {
		codeFines.setVisible(false);
		codeFines.setFineDesc(codeFines.getFineDesc());
		ReqFinesDetails reqFinesDetails = addNewReqFinesDetails(codeFines);
		reqFinesDetailsList.add(reqFinesDetails);
		codeFines = new ReqFinesSetup();
		codesFinesList.add(codeFines);

	}

	private ReqFinesDetails addNewReqFinesDetails(ReqFinesSetup codeFines) {
		ReqFinesDetails reqFinesDetails = new ReqFinesDetails();
		reqFinesDetails.setFineCode(codeFines.getId());
		reqFinesDetails.setFineCount(codeFines.getFineNbr());
		reqFinesDetails.setFineValue(codeFines.getFineValue());
		return reqFinesDetails;
	}

	public void updateFiner(ReqFinesSetup codeFines) {
		codeFines.setId(codeFines.getId());
		codeFines.setFineCode(selectedCodeFiner.getFineCode());
		codeFines.setFineDesc(selectedCodeFiner.getFineDesc());
		codeFines.setFineMinimumValue(selectedCodeFiner.getFineMinimumValue());
		codeFines.setFineSupermumValue(selectedCodeFiner.getFineSupermumValue());


	}

	public void deleteFiner(ReqFinesSetup codeFines) {
		codesFinesList.remove(codeFines);
	}

	public void calcFineValSum(ReqFinesSetup codeFines) {


		if (codeFines.getFineValue() != null && codeFines.getFineNbr() != null)
			codeFines.setFineValSum(codeFines.getFineValue() * codeFines.getFineNbr());

	}

	public String getLicenceId() {
		return licenceId;
	}

	public void setLicenceId(String licenceId) {
		this.licenceId = licenceId;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public LicTrdMasterFile getSelectedLic() {
		return selectedLic;
	}

	public void setSelectedLic(LicTrdMasterFile selectedLic) {
		this.selectedLic = selectedLic;
	}

	public List<ArcPeopleModel> getFilteredArcPeoples() {
		return filteredArcPeoples;
	}

	public void setFilteredArcPeoples(List<ArcPeopleModel> filteredArcPeoples) {
		this.filteredArcPeoples = filteredArcPeoples;
	}

	public List<ArcPeopleModel> getArcPeoples() {
		return arcPeoples;
	}

	public void setArcPeoples(List<ArcPeopleModel> arcPeoples) {
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

	public String getCodeFine() {
		return codeFine;
	}

	public void setCodeFine(String codeFine) {
		this.codeFine = codeFine;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public List<User> getSupervisors() {
		return supervisors;
	}

	public void setSupervisors(List<User> supervisors) {
		this.supervisors = supervisors;
	}

	public LicTrdMasterFile getLicTrdMasterFile() {
		return licTrdMasterFile;
	}

	public void setLicTrdMasterFile(LicTrdMasterFile licTrdMasterFile) {
		this.licTrdMasterFile = licTrdMasterFile;
	}

	public ReqFinesMaster getReqFinesMaster() {
		return reqFinesMaster;
	}

	public void setReqFinesMaster(ReqFinesMaster reqFinesMaster) {
		this.reqFinesMaster = reqFinesMaster;
	}

	public LicTrdMasterFile getTrdMasterFile() {
		return trdMasterFile;
	}

	public void setTrdMasterFile(LicTrdMasterFile trdMasterFile) {
		this.trdMasterFile = trdMasterFile;
	}

	public Integer getArcPeople() {
		return arcPeople;
	}

	public void setArcPeople(Integer arcPeople) {
		this.arcPeople = arcPeople;
	}

}

package com.bkeryah.penalties;

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

import org.primefaces.event.SelectEvent;

import com.bkeryah.entities.ArcPeopleModel;
import com.bkeryah.entities.LicMainActivity;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;

@ManagedBean
@ViewScoped
public class SearchPenaltyBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private String licenceId;
	private Boolean isActive;
	private Integer paid;
	private Boolean isSelected;
	private Integer itemId;
	private LicTrdMasterFile licTrdMasterFile;
	// private ReqFinesMaster reqfinesMaster;
	private ReqFinesDetails reqFinesDetails;
	private List<ReqFinesDetails> lstReqFinesDetails;
	private List<LicTrdMasterFile> licTrdMasterFileList;
	private List<ReqFinesMaster> reqFinesMasterList;
	private LicTrdMasterFile trdMasterFile;
	private ReqFinesMaster reqFinesMaster;
	private Map<Integer, LicTrdMasterFile> licTrdMasterFileList2 = new HashMap<Integer, LicTrdMasterFile>();
	private String licenceNo;
	private boolean withNationalId;
	private List<ArcPeopleModel> filteredArcPeoples;
	private ArcPeopleModel selectedPeople;
	private List<ReqFinesMaster> filteredList;
	private List<ReqFinesDetails> filteredDetailList;
	private List<LicMainActivity> activities;

	@PostConstruct
	private void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		licenceNo = (String) httpSession.getAttribute("licenceNo");
		httpSession.removeAttribute("licenceNo");
		if (licenceNo != null) {
			trdMasterFile = dataAccessService.findLicByLicId(licenceNo);
			loadFinesMasterList();
		}else{
			withNationalId = true;
		}
		activities=dataAccessService.loadActivities();
	}

	public String addLicencePenality() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("licenceNo", licenceNo);
		return "penalty";
	}

	public void updateNameAfterChoiseArticle(AjaxBehaviorEvent event) {
		String selectedLic = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("selectLic");

		if (reqFinesMasterList != null)
			reqFinesMasterList.clear();
		try {
			if (selectedLic != null && !selectedLic.equals(null) && !selectedLic.isEmpty()) {
				itemId = Integer.parseInt(selectedLic.trim());
				trdMasterFile = (LicTrdMasterFile) dataAccessService.findEntityById(LicTrdMasterFile.class, itemId);
				isActive = true;
				isSelected = false;
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"من فضلك اختر  الصنف المطلوب", "من فضلك اختر  الصنف المطلوب"));

			}
		} catch (NullPointerException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"من فضلك اختر  الصنف المطلوب", "من فضلك اختر  الصنف المطلوب"));
			e.printStackTrace();
		}

	}

	private void loadFinesMasterList() {

		try {
			reqFinesMasterList = dataAccessService.findLstFinesMasterByLicId(trdMasterFile.getLicNo());
		} catch (Exception e) {
			MsgEntry.addErrorMessage("خطا في العملية");
		}

	}

	public String DetailLic(ReqFinesMaster det) {
		reqFinesMaster=det;
		String numberLic = reqFinesMaster.getfLicenceNo();//trdMasterFile.getLicNo();
		licTrdMasterFile = dataAccessService.findLicByLicId(numberLic);
		Integer numFine = reqFinesMaster.getFineNo();
		reqFinesDetails = dataAccessService.findFinesDetailsByFno(numFine);
		lstReqFinesDetails = dataAccessService.findLstFinesDetailsByFineNO(numFine);
		// reqfinesMaster = dataAccessService.findFinesMasterByLicId(numberLic);
		return null;
	}
	
	public void onRowSelect(SelectEvent event) {
		selectedPeople = (ArcPeopleModel) event.getObject();
	}
	
	public String checkLic() {
		reqFinesMaster = new ReqFinesMaster();
		reqFinesMaster.setfIdNo(selectedPeople.getNationalId().toString());
		reqFinesMaster.setfName(selectedPeople.getCompleteName());
		reqFinesMasterList = dataAccessService.findListFinesMasterByNationalId(reqFinesMaster.getfIdNo());
		return "";
	}
	
	public void update() {
		dataAccessService.updateObject(trdMasterFile);
		MsgEntry.addInfoMessage("تم التعديل");
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getPaid() {
		return paid;
	}

	public void setPaid(Integer paid) {
		this.paid = paid;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public LicTrdMasterFile getLicTrdMasterFile() {
		return licTrdMasterFile;
	}

	public void setLicTrdMasterFile(LicTrdMasterFile licTrdMasterFile) {
		this.licTrdMasterFile = licTrdMasterFile;
	}

	// public ReqFinesMaster getReqfinesMaster() {
	// return reqfinesMaster;
	// }
	//
	//
	//
	//
	//
	// public void setReqfinesMaster(ReqFinesMaster reqfinesMaster) {
	// this.reqfinesMaster = reqfinesMaster;
	// }

	public ReqFinesDetails getReqFinesDetails() {
		return reqFinesDetails;
	}

	public void setReqFinesDetails(ReqFinesDetails reqFinesDetails) {
		this.reqFinesDetails = reqFinesDetails;
	}

	public List<ReqFinesDetails> getLstReqFinesDetails() {
		return lstReqFinesDetails;
	}

	public void setLstReqFinesDetails(List<ReqFinesDetails> lstReqFinesDetails) {
		this.lstReqFinesDetails = lstReqFinesDetails;
	}

	public List<LicTrdMasterFile> getLicTrdMasterFileList() {
		return licTrdMasterFileList;
	}

	public void setLicTrdMasterFileList(List<LicTrdMasterFile> licTrdMasterFileList) {
		this.licTrdMasterFileList = licTrdMasterFileList;
	}

	public List<ReqFinesMaster> getReqFinesMasterList() {
		return reqFinesMasterList;
	}

	public void setReqFinesMasterList(List<ReqFinesMaster> reqFinesMasterList) {
		this.reqFinesMasterList = reqFinesMasterList;
	}

	public LicTrdMasterFile getTrdMasterFile() {
		return trdMasterFile;
	}

	public void setTrdMasterFile(LicTrdMasterFile trdMasterFile) {
		this.trdMasterFile = trdMasterFile;
	}

	public ReqFinesMaster getReqFinesMaster() {
		return reqFinesMaster;
	}

	public void setReqFinesMaster(ReqFinesMaster reqFinesMaster) {
		this.reqFinesMaster = reqFinesMaster;
		if (reqFinesMaster != null) {
			this.reqFinesMaster.setDeptName(
					dataAccessService.findDepartmentNameById(Integer.parseInt(reqFinesMaster.getfDeptNo())));
			this.reqFinesMaster.setSupervisorName(dataAccessService
					.loadUserById(Integer.parseInt(reqFinesMaster.getfSupervisorCode())).getEmployeeName());
		}
	}

	public Map<Integer, LicTrdMasterFile> getLicTrdMasterFileList2() {
		return licTrdMasterFileList2;
	}

	public void setLicTrdMasterFileList2(Map<Integer, LicTrdMasterFile> licTrdMasterFileList2) {
		this.licTrdMasterFileList2 = licTrdMasterFileList2;
	}

	public String getLicenceNo() {
		return licenceNo;
	}

	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}

	public boolean isWithNationalId() {
		return withNationalId;
	}

	public void setWithNationalId(boolean withNationalId) {
		this.withNationalId = withNationalId;
	}

	public List<ArcPeopleModel> getFilteredArcPeoples() {
		return filteredArcPeoples;
	}

	public void setFilteredArcPeoples(List<ArcPeopleModel> filteredArcPeoples) {
		this.filteredArcPeoples = filteredArcPeoples;
	}

	public ArcPeopleModel getSelectedPeople() {
		return selectedPeople;
	}

	public void setSelectedPeople(ArcPeopleModel selectedPeople) {
		this.selectedPeople = selectedPeople;
	}

	public List<ReqFinesMaster> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<ReqFinesMaster> filteredList) {
		this.filteredList = filteredList;
	}

	public List<ReqFinesDetails> getFilteredDetailList() {
		return filteredDetailList;
	}

	public void setFilteredDetailList(List<ReqFinesDetails> filteredDetailList) {
		this.filteredDetailList = filteredDetailList;
	}

	public List<LicMainActivity> getActivities() {
		return activities;
	}

	public void setActivities(List<LicMainActivity> activities) {
		this.activities = activities;
	}

}

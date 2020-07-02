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

import com.bkeryah.bean.ArcAttachmentClass;
import com.bkeryah.entities.LicActivityTypeRy;
import com.bkeryah.entities.LicCities;
import com.bkeryah.entities.LicDistrict;
import com.bkeryah.entities.LicStreet;
import com.bkeryah.entities.licences.LicVisitsTypes;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class LicencesListBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<LicTrdMasterFile> licencesList;
	private List<LicTrdMasterFile> filteredLicences;
	private Integer period;
	private Integer typeLicence;
	private List<ArcAttachmentClass> attList = new ArrayList<ArcAttachmentClass>();
	private boolean updateMode;
	private LicTrdMasterFile licence = new LicTrdMasterFile();
	private List<LicTrdMasterFile> allLicencesList;
	private List<LicVisitsTypes> licVisits;
	private List<LicCities> citiesList;
	private List<LicStreet> streetsList;
	private List<LicDistrict> districtsList;
	private List<LicActivityTypeRy> activityList;
	private List<LicSection> licSectionList;
	private List<LicDepartment> licDepartmentList;

	@PostConstruct
	private void init() {
		licencesList = dataAccessService.getTrdMasterFileList();
		typeLicence = 1;
		allLicencesList = dataAccessService.getAllLicencesList();
		licVisits = dataAccessService.findAllLicVisits();
		citiesList = dataAccessService.findAllCities();
		streetsList = dataAccessService.findAllStreet();
		districtsList = dataAccessService.findAllDistrict();
		activityList = dataAccessService.getAllLicActivityTypeList();
		licSectionList = dataAccessService.gatAllLicSectionList();
		licDepartmentList = dataAccessService.gatAllLicDepartmentList();
	}

	public void loadLicences() {
		licencesList = dataAccessService.getTrdMasterFileList();
	}

	public void allowAdding() {
		licence = new LicTrdMasterFile();
		updateMode = false;
		// Utils.openDialog("addingLicence_dlg");
	}

	// public String loadLicencePenalities(LicTrdMasterFile licence) {
	//
	// FacesContext facesContext = FacesContext.getCurrentInstance();
	// HttpSession session = (HttpSession)
	// facesContext.getExternalContext().getSession(true);
	// session.setAttribute("licenceNo", licence.getLicNo());
	// return "licence_penalities";
	// }
	public void loadLicencePenalities(LicTrdMasterFile licenceItemTable) {
		updateMode = true;
		licence = licenceItemTable;
		// licence.setLicNo(licenceItemTable.getLicNo());
		// licence.set(licenceItemTable.);
		// licence.set(licenceItemTable.);
		// licence.setLicNo(licenceItemTable.);
		// licence.setLicNo(licenceItemTable.);
		// licence.setLicNo(licenceItemTable.);
		// licence.setLicNo(licenceItemTable.);
		// licence.setLicNo(licenceItemTable.);
		// licence.setLicNo(licenceItemTable.);
		// licence.setLicNo(licenceItemTable.);
		// licence.setLicNo(licenceItemTable.);

		// FacesContext facesContext = FacesContext.getCurrentInstance();
		// HttpSession session = (HttpSession)
		// facesContext.getExternalContext().getSession(true);
		// session.setAttribute("licenceNo", licence.getLicNo());
		// return "licence_penalities";

	}

	public String visits(LicTrdMasterFile licenceItemTable) {
		licence = licenceItemTable;
		licence.setLicNo(licenceItemTable.getLicNo());
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		httpSession.setAttribute("licence", licence);
		return "visits";
	}

	public void searchLicences() {
		licencesList = dataAccessService.getTrdMasterFileListByPeriodType(typeLicence, period);
	}

	public void resetFields() {
		period = null;
		licencesList = null;
	}

	public String saveLicence() {
		dataAccessService.save(licence);
		MsgEntry.addAcceptFlashInfoMessage("تم الحفظ");

		return "";
	}

	// public String updateLicence(LicTrdMasterFile licence) {
	public String updateLicence() {
		dataAccessService.updateObject(licence);
		MsgEntry.addAcceptFlashInfoMessage("تم التعديل");
		return "";
	}

	/**
	 * Print letter report
	 */
	public String printReportAction(LicTrdMasterFile licenceItem) {
		String reportName = "/reports/licences_report.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("licNo", licenceItem.getLicNo());
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public void loadAttachmentByLicenceId() {
		attList = dataAccessService.findAttachmentFilesByLicenceId(licence.getId());
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<LicTrdMasterFile> getLicencesList() {
		return licencesList;
	}

	public void setLicencesList(List<LicTrdMasterFile> licencesList) {
		this.licencesList = licencesList;
	}

	public List<LicTrdMasterFile> getFilteredLicences() {
		return filteredLicences;
	}

	public void setFilteredLicences(List<LicTrdMasterFile> filteredLicences) {
		this.filteredLicences = filteredLicences;
	}

	public LicTrdMasterFile getLicence() {
		return licence;
	}

	public void setLicence(LicTrdMasterFile licence) {
		this.licence = licence;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Integer getTypeLicence() {
		return typeLicence;
	}

	public void setTypeLicence(Integer typeLicence) {
		this.typeLicence = typeLicence;
	}

	public List<ArcAttachmentClass> getAttList() {
		return attList;
	}

	public void setAttList(List<ArcAttachmentClass> attList) {
		this.attList = attList;
	}

	public boolean isUpdateMode() {
		return updateMode;
	}

	public void setUpdateMode(boolean updateMode) {
		this.updateMode = updateMode;
	}

	public List<LicTrdMasterFile> getAllLicencesList() {
		return allLicencesList;
	}

	public void setAllLicencesList(List<LicTrdMasterFile> allLicencesList) {
		this.allLicencesList = allLicencesList;
	}

	public List<LicVisitsTypes> getLicVisits() {
		return licVisits;
	}

	public void setLicVisits(List<LicVisitsTypes> licVisits) {
		this.licVisits = licVisits;
	}

	public List<LicCities> getCitiesList() {
		return citiesList;
	}

	public void setCitiesList(List<LicCities> citiesList) {
		this.citiesList = citiesList;
	}

	public List<LicStreet> getStreetsList() {
		return streetsList;
	}

	public void setStreetsList(List<LicStreet> streetsList) {
		this.streetsList = streetsList;
	}

	public List<LicDistrict> getDistrictsList() {
		return districtsList;
	}

	public void setDistrictsList(List<LicDistrict> districtsList) {
		this.districtsList = districtsList;
	}

	public List<LicActivityTypeRy> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<LicActivityTypeRy> activityList) {
		this.activityList = activityList;
	}

	public List<LicSection> getLicSectionList() {
		return licSectionList;
	}

	public void setLicSectionList(List<LicSection> licSectionList) {
		this.licSectionList = licSectionList;
	}

	public List<LicDepartment> getLicDepartmentList() {
		return licDepartmentList;
	}

	public void setLicDepartmentList(List<LicDepartment> licDepartmentList) {
		this.licDepartmentList = licDepartmentList;
	}

}

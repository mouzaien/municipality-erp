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
import javax.servlet.http.HttpSession;

import com.bkeryah.bean.ArcAttachmentClass;
import com.bkeryah.entities.LicMainActivity;
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
	private LicTrdMasterFile licence;
	private Integer period;
	private Integer typeLicence;
	
	private List<ArcAttachmentClass> attList = new ArrayList<ArcAttachmentClass>();
	private LicTrdMasterFile marketLicense=new LicTrdMasterFile();
	private List<LicMainActivity> activities;

	@PostConstruct
	private void init() {
		licencesList = dataAccessService.getTrdMasterFileList();
		typeLicence = 1;
		activities=dataAccessService.loadActivities();
	}

	public void loadLicences() {
		licencesList = dataAccessService.getTrdMasterFileList();
	}

	public String loadLicencePenalities(LicTrdMasterFile licence) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("licenceNo", licence.getLicNo());
		return "licence_penalities";
	}

	public void searchLicences() {
		licencesList = dataAccessService.getTrdMasterFileListByPeriodType(typeLicence, period);
	}
	public void editLicence() {
		
	}
	public void addLicence() {
		dataAccessService.save(marketLicense);
		MsgEntry.addInfoMessage("تم الاضافة");
	}

	public void resetFields() {
		period = null;
		licencesList = null;
	}
	
	/**
	 * Print letter report
	 */
	public String printReportAction() {
		String reportName = "/reports/licences_list.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
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

	public LicTrdMasterFile getMarketLicense() {
		return marketLicense;
	}

	public void setMarketLicense(LicTrdMasterFile marketLicense) {
		this.marketLicense = marketLicense;
	}

	public List<LicMainActivity> getActivities() {
		return activities;
	}

	public void setActivities(List<LicMainActivity> activities) {
		this.activities = activities;
	}

}

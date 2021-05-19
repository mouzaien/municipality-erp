package com.bkeryah.penalties;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import com.bkeryah.entities.Supervisor;
import com.bkeryah.entities.VisitsSupervisor;
import com.bkeryah.entities.licences.LicVisits;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

/**
 * @author Amr Alkady
 *
 */
@ManagedBean(name = "supervisorVisitsBean")
@ViewScoped
public class SupervisorVisitsBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;

	private List<LicTrdMasterFile> licences;
	private List<LicVisits> Visits = new ArrayList<>();
	private List<String> supervisorIds = new ArrayList<>();
	private List<String> supervisorAddIds = new ArrayList<>();
	private List<Supervisor> supervisors;
	private List<Supervisor> supervisorList;
	private List<LicSection> licSectionList = new ArrayList<>();
	private List<VisitsSupervisor> visitsSupervisors = new ArrayList<>();
	private Integer sectionId;
	private Integer sectionAddId;
	private String beginDate;
	private String endDate;
	private Integer supervisorId;
	private VisitsSupervisor obForTransfer;
	private Integer secId;
	private List<LicDepartment> licDepartmentList = new ArrayList<>();

	@PostConstruct
	private void init() {
		supervisors = dataAccessService.getAllSupervisors();
		licSectionList = dataAccessService.gatAllLicSectionList();
		// licDepartmentList = dataAccessService.gatAllLicDepartmentList();
		try {
			beginDate = Utils.grigDatesConvert(new Date());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadlicDepartments(AjaxBehaviorEvent event) {
		licDepartmentList = dataAccessService.getAllLicDepartmentBySection(sectionAddId);
	}

	public void loadVisitsBySuper(AjaxBehaviorEvent event) {
		// if (!(licDepartmentList.size() > 0)) {
		licDepartmentList = dataAccessService.getAllLicDepartmentBySection(secId);
		// }
		visitsSupervisors = new ArrayList<>();
		List<VisitsSupervisor> Vis = new ArrayList<VisitsSupervisor>();
		if (!(supervisorIds.size() > 0)) {
			Vis = dataAccessService.getAllVisitsBySuperId(null, secId);
			visitsSupervisors.addAll(Vis);
		}
		for (String id : supervisorIds) {
			Vis = new ArrayList<VisitsSupervisor>();
			Vis = dataAccessService.getAllVisitsBySuperId(Integer.parseInt(id), secId);
			visitsSupervisors.addAll(Vis);
		}

	}

	/**
	 * @return
	 */
	public String save() {
		if (sectionAddId != null && supervisorAddIds != null && beginDate != null && endDate != null) {
			licences = dataAccessService.getAllLicsBySectionId(sectionAddId);
			if (licences != null && licences.size() > 0) {
				List<LicVisits> Vis = new ArrayList<LicVisits>();
				for (LicTrdMasterFile licence : licences) {
					Vis = new ArrayList<LicVisits>();
					try {
						beginDate = Utils.grigDatesConvert(new Date());
						System.out.println("" + beginDate);
						Vis = dataAccessService.getAllVisitsByLicIdBetweenDates(licence.getId(), beginDate, endDate);
						if (Vis != null) {
							Visits.addAll(Vis);
						}
					} catch (Exception e) {
						e.printStackTrace();
						//
					}
					// delete visits for disActive Supervisors
					// deleteVisitsForDisactiveSupervisors from
					// VISITS_SUPERVISORS Table
					try {
						dataAccessService.deleteVisitsForDisactiveSupervisors(licence.getId(), beginDate, endDate);
					} catch (Exception e) {
						e.printStackTrace();
						//
					}
				}

				/// توزيع الزيارات على المراقبين

				if (Visits != null && Visits.size() > 0) {
					VisitsSupervisor visSupAdd = new VisitsSupervisor();
					int j = 0;
					for (int i = 0; i < Visits.size(); i++) {
						try {
							visSupAdd = new VisitsSupervisor();
							visSupAdd.setVisitId(Visits.get(i).getId());
							visSupAdd.setSupervisorId(Integer.parseInt(supervisorAddIds.get(j)));
							visSupAdd.setSectionId(sectionAddId);
							dataAccessService.save(visSupAdd);
						} catch (Exception e) {
							System.out.println(e.getMessage());
							e.printStackTrace();
						}
						if (supervisorAddIds.size() == j + 1) {
							j = 0;
						} else {
							j++;
						}

					}
					MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
					supervisorIds = supervisorAddIds;
					loadVisitsALL();
				} else {
					MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.visits"));
				}
			}
		}
		return "";
	}

	public void showDlg() {
		supervisors = dataAccessService.getAllSupervisors();
		Utils.openDialog("add_dlg");
	}

	public void transfer() {
		if (supervisorId != null) {
			try {
				obForTransfer.setSupervisorId(supervisorId);
				dataAccessService.updateObject(obForTransfer);
				loadVisitsALL();
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			}
		}
		// return "";
	}

	public void loadVisitsALL() {
		visitsSupervisors = new ArrayList<>();
		List<VisitsSupervisor> Vis = new ArrayList<VisitsSupervisor>();
		for (String id : supervisorIds) {
			Vis = new ArrayList<VisitsSupervisor>();
			Vis = dataAccessService.getAllVisitsBySuperId(Integer.parseInt(id), secId);
			visitsSupervisors.addAll(Vis);
		}

	}

	public void setSup(VisitsSupervisor vs) {
		obForTransfer = vs;
		Utils.openDialog("trans_dlg");
	}

	public String getLicData(Integer licId) {
		if (licId != null) {
			LicTrdMasterFile lic = (LicTrdMasterFile) dataAccessService.findEntityById(LicTrdMasterFile.class, licId);
			if (lic != null) {
				return lic.getLicNo();
			}
		}
		return null;
	}

	public String printVisitsList() {
		String reportName = "/reports/visits_for_supervisors.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (supervisorIds.size() == 1) {
			for (Supervisor sur : supervisors) {
				Integer id = Integer.parseInt(supervisorIds.get(0));
				if (id.equals(sur.getId()))
					parameters.put("supervisorName", sur.getUserName());
			}
		}
		for (VisitsSupervisor visit : visitsSupervisors) {
			visit.setLicNo(getLicData(visit.getLicId()));
		}
		Utils.printPdfReportFromListDataSource(reportName, parameters, visitsSupervisors);
		return "";
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<String> getSupervisorIds() {
		return supervisorIds;
	}

	public void setSupervisorIds(List<String> supervisorIds) {
		this.supervisorIds = supervisorIds;
	}

	public List<Supervisor> getSupervisors() {
		return supervisors;
	}

	public void setSupervisors(List<Supervisor> supervisors) {
		this.supervisors = supervisors;
	}

	public List<LicSection> getLicSectionList() {
		return licSectionList;
	}

	public void setLicSectionList(List<LicSection> licSectionList) {
		this.licSectionList = licSectionList;
	}

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public List<String> getSupervisorAddIds() {
		return supervisorAddIds;
	}

	public void setSupervisorAddIds(List<String> supervisorAddIds) {
		this.supervisorAddIds = supervisorAddIds;
	}

	public Integer getSectionAddId() {
		return sectionAddId;
	}

	public void setSectionAddId(Integer sectionAddId) {
		this.sectionAddId = sectionAddId;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<VisitsSupervisor> getVisitsSupervisors() {
		return visitsSupervisors;
	}

	public void setVisitsSupervisors(List<VisitsSupervisor> visitsSupervisors) {
		this.visitsSupervisors = visitsSupervisors;
	}

	public List<Supervisor> getSupervisorList() {
		return supervisorList;
	}

	public void setSupervisorList(List<Supervisor> supervisorList) {
		this.supervisorList = supervisorList;
	}

	public List<LicTrdMasterFile> getLicences() {
		return licences;
	}

	public void setLicences(List<LicTrdMasterFile> licences) {
		this.licences = licences;
	}

	public List<LicVisits> getVisits() {
		return Visits;
	}

	public void setVisits(List<LicVisits> visits) {
		Visits = visits;
	}

	public Integer getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Integer supervisorId) {
		this.supervisorId = supervisorId;
	}

	public VisitsSupervisor getObForTransfer() {
		return obForTransfer;
	}

	public void setObForTransfer(VisitsSupervisor obForTransfer) {
		this.obForTransfer = obForTransfer;
	}

	public Integer getSecId() {
		return secId;
	}

	public void setSecId(Integer secId) {
		this.secId = secId;
	}

	public List<LicDepartment> getLicDepartmentList() {
		return licDepartmentList;
	}

	public void setLicDepartmentList(List<LicDepartment> licDepartmentList) {
		this.licDepartmentList = licDepartmentList;
	}

}

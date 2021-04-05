package com.bkeryah.penalties;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.maven.wagon.providers.ssh.interactive.NullInteractiveUserInfo;
import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.Supervisor;
import com.bkeryah.entities.VisitsSupervisor;
import com.bkeryah.entities.licences.LicVisits;
import com.bkeryah.entities.licences.LicVisitsTypes;
import com.bkeryah.managedBean.reqfin.newReplaceFinBean;
import com.bkeryah.model.User;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean(name = "licencesVisitsBean")
@ViewScoped
public class LicencesVisitsBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private LicTrdMasterFile licence;
	private List<LicTrdMasterFile> licenceList = new ArrayList<>();
	private LicVisits Visit = new LicVisits();
	private List<LicVisits> Visits = new ArrayList<LicVisits>();
	private Integer daysNo;
	private Integer visitId;
	private String beginDate;
	private String endDate;
	private List<LicVisitsTypes> licVisits;
	private List<String> licencesIds = new ArrayList<>();
	private List<String> licencesAddIds = new ArrayList<>();
	private Date current;
	private List<LicSection> licSectionList = new ArrayList<>();;
	private List<LicDepartment> licDepartmentList = new ArrayList<>();;
	private Integer sectionId;
	private Integer deparmentId;
	private List<Supervisor> supervisors;
	private Integer supervisorId;

	@PostConstruct
	private void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		licence = (LicTrdMasterFile) httpSession.getAttribute("licence");
		// httpSession.removeAttribute("licence");
		if (licence != null) {
			Visits = dataAccessService.getAllVisitsByLicId(licence.getId());
			deparmentId = licence.getLicDeparment();
			sectionId = licence.getLicSection();
			licencesIds.add(licence.getId().toString());
			licenceList.add(licence);
			licDepartmentList = dataAccessService.getAllLicDepartmentBySection(sectionId);
		}
		licVisits = dataAccessService.findAllLicVisits();
		licSectionList = dataAccessService.gatAllLicSectionList();
		supervisors = dataAccessService.getAllSupervisors();

	}

	public String loadLics() {
		// if(!(licDepartmentList.size()>0)){
		licDepartmentList = dataAccessService.getAllLicDepartmentBySection(sectionId);
		// }
		if (sectionId != null && deparmentId != null) {
			Visits = new ArrayList<LicVisits>();
			licenceList = new ArrayList<>();
			licenceList = dataAccessService.getAllLicencesListBySection(sectionId, deparmentId);
		}

		return "";

	}

	public void showDlg() {
		// licenceList = dataAccessService.getAllLicencesList();
		sectionId = null;
		deparmentId = null;
		licenceList = new ArrayList<>();
		Utils.openDialog("add_dlg");
	}

	public String getLicData(Integer licId) {
		if (licId != null) {
			LicSection licSec = (LicSection) dataAccessService.findEntityById(LicSection.class, licId);
			if (licSec != null) {
				return licSec.getName();
			}
		}
		return null;
	}

	public String save() {
		try {
			String start = Utils.convertHijriDateToGregorian(beginDate);
			SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
			current = format1.parse(start);
			if (supervisorId != null) {
				if (licencesAddIds != null && licencesAddIds.size() > 0) {
					for (String id : licencesAddIds) {
						Visit.setgDate(current);
						Visit.sethDate(beginDate);
						Visit.setVisitId(visitId);
						Visit.setLicId(Integer.parseInt(id));
						Integer vId = dataAccessService.save(Visit);
						VisitsSupervisor visSupAdd = new VisitsSupervisor();
						visSupAdd.setVisitId(vId);
						visSupAdd.setSupervisorId(supervisorId);
						visSupAdd.setSectionId(sectionId);
						dataAccessService.save(visSupAdd);
						MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));

					}
				}
			}
			// } else {
			//
			// if (visitId != null) {
			// LicVisitsTypes vt = (LicVisitsTypes)
			// dataAccessService.findEntityById(LicVisitsTypes.class,
			// visitId);
			// if (vt != null) {
			// Visit.setgDate(current);
			// Visit.sethDate(beginDate);
			// Visit.setVisitId(visitId);
			// daysNo = vt.getDaysNo();
			// if (licencesAddIds != null) {
			// for (String id : licencesAddIds) {
			// Visit.setLicId(Integer.parseInt(id));
			//
			// if (vt.getType().equals(0)) { // periodic
			// // convert start date to g and insert
			// checkVisits(Integer.parseInt(id));
			// Calendar calendar1 = Calendar.getInstance();
			// calendar1.setTime(current);
			// calendar1.add(Calendar.YEAR, 1);
			// Date end = calendar1.getTime();
			// while (current.before(end)) {
			// try {
			// dataAccessService.save(Visit);
			// } catch (Exception e) {
			// System.out.println(e.getMessage());
			// e.printStackTrace();
			// }
			// Calendar calendar = Calendar.getInstance();
			// calendar.setTime(current);
			// calendar.add(Calendar.DATE, daysNo);
			// current = calendar.getTime();
			// Visit.setgDate(current);
			// Visit.sethDate(Utils.grigDatesConvert(current));
			// }
			// MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			// // loadLics();
			// } else {
			// try {
			// dataAccessService.save(Visit);
			// MsgEntry.addAcceptFlashInfoMessage(
			// Utils.loadMessagesFromFile("success.operation"));
			// // loadVisits();
			// } catch (Exception e) {
			// System.out.println(e.getMessage());
			// e.printStackTrace();
			// }
			// }
			//
			// }
			// }
			// }
			// }
			// }

			if (licencesAddIds.size() > 0) {
				loadVisits();
				beginDate = null;
				visitId = null;
			}
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

		}

		return "";

	}

	public String remove(LicVisits Visit) {
		if (Visit != null) {
			try {
				dataAccessService.deleteObject(Visit);
				if (licence != null) {
					Visits = dataAccessService.getAllVisitsByLicId(licence.getId());
				}
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));

			}

		}
		return "";
	}

	public void onRowEdit(RowEditEvent event) {
		LicVisits vi = (LicVisits) event.getObject();
		try {
			String start = Utils.convertHijriDateToGregorian(vi.gethDate());
			SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
			Date current = format1.parse(start);
			vi.setgDate(current);
			dataAccessService.updateObject(vi);
			if (licence != null) {
				Visits = dataAccessService.getAllVisitsByLicId(licence.getId());
			}
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			beginDate = null;
			visitId = null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			beginDate = null;
			visitId = null;
		}
	}

	public void loadVisitsByLic(AjaxBehaviorEvent event) {
		Visits = new ArrayList<LicVisits>();
		List<LicVisits> Vis = new ArrayList<LicVisits>();
		for (String id : licencesIds) {
			Vis = new ArrayList<LicVisits>();
			Vis = dataAccessService.getAllVisitsByLicId(Integer.parseInt(id));
			Visits.addAll(Vis);
		}

	}

	public void loadVisits() {
		Visits = new ArrayList<LicVisits>();
		List<LicVisits> Vis = new ArrayList<LicVisits>();
		for (String id : licencesAddIds) {
			Vis = new ArrayList<LicVisits>();
			Vis = dataAccessService.getAllVisitsByLicId(Integer.parseInt(id));
			Visits.addAll(Vis);
		}

	}

	public void checkVisits(Integer licId) {
		try {
			int del = dataAccessService.deleteVisitByDateAndLicNo(new Date(), licId);
			if (del > 0) {
				current = new Date();
				Visit.setgDate(current);
				Visit.sethDate(Utils.grigDatesConvert(current));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	public void onRowCancel(RowEditEvent event) {
		beginDate = null;
		visitId = null;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public LicTrdMasterFile getLicence() {
		return licence;
	}

	public void setLicence(LicTrdMasterFile licence) {
		this.licence = licence;
	}

	public LicVisits getVisit() {
		return Visit;
	}

	public void setVisit(LicVisits visit) {
		Visit = visit;
	}

	public List<LicVisits> getVisits() {
		return Visits;
	}

	public void setVisits(List<LicVisits> visits) {
		Visits = visits;
	}

	public Integer getDaysNo() {
		return daysNo;
	}

	public void setDaysNo(Integer daysNo) {
		this.daysNo = daysNo;
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

	public List<LicVisitsTypes> getLicVisits() {
		return licVisits;
	}

	public void setLicVisits(List<LicVisitsTypes> licVisits) {
		this.licVisits = licVisits;
	}

	public Integer getVisitId() {
		return visitId;
	}

	public void setVisitId(Integer visitId) {
		this.visitId = visitId;
	}

	public List<LicTrdMasterFile> getLicenceList() {
		return licenceList;
	}

	public void setLicenceList(List<LicTrdMasterFile> licenceList) {
		this.licenceList = licenceList;
	}

	public List<String> getLicencesIds() {
		return licencesIds;
	}

	public void setLicencesIds(List<String> licencesIds) {
		this.licencesIds = licencesIds;
	}

	public List<String> getLicencesAddIds() {
		return licencesAddIds;
	}

	public void setLicencesAddIds(List<String> licencesAddIds) {
		this.licencesAddIds = licencesAddIds;
	}

	public Date getCurrent() {
		return current;
	}

	public void setCurrent(Date current) {
		this.current = current;
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

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public Integer getDeparmentId() {
		return deparmentId;
	}

	public void setDeparmentId(Integer deparmentId) {
		this.deparmentId = deparmentId;
	}

	public List<Supervisor> getSupervisors() {
		return supervisors;
	}

	public void setSupervisors(List<Supervisor> supervisors) {
		this.supervisors = supervisors;
	}

	public Integer getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Integer supervisorId) {
		this.supervisorId = supervisorId;
	}

}

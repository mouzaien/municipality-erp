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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.event.RowEditEvent;

import com.bkeryah.entities.licences.LicVisits;
import com.bkeryah.entities.licences.LicVisitsTypes;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean(name = "licencesVisitsBean")
@ViewScoped
public class LicencesVisitsBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private LicTrdMasterFile licence;
	private LicVisits Visit = new LicVisits();
	private List<LicVisits> Visits = new ArrayList<LicVisits>();
	private Integer daysNo;
	private Integer visitId;
	private String beginDate;
	private String endDate;
	private List<LicVisitsTypes> licVisits;

	@PostConstruct
	private void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest HttpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = HttpRequest.getSession(false);
		licence = (LicTrdMasterFile) httpSession.getAttribute("licence");
		// httpSession.removeAttribute("licence");
		if (licence != null) {
			Visits = dataAccessService.getAllVisitsByLicId(licence.getId());
		}
		licVisits = dataAccessService.findAllLicVisits();
	}

	public String save() {
		try {
			if (visitId != null) {
				LicVisitsTypes vt = (LicVisitsTypes) dataAccessService.findEntityById(LicVisitsTypes.class, visitId);
				if (vt != null) {
					String start = Utils.convertHijriDateToGregorian(beginDate);
					SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
					Date current = format1.parse(start);
					Visit.setgDate(current);
					Visit.sethDate(beginDate);
					Visit.setVisitId(visitId);
					Visit.setLicId(licence.getId());
					daysNo = vt.getDaysNo();
					if (vt.getType().equals("0")) { // periodic
						// convert start date to g and insert
						Calendar calendar1 = Calendar.getInstance();
						calendar1.setTime(current);
						calendar1.add(Calendar.YEAR, 1);
						Date end = calendar1.getTime();
						while (current.before(end)) {
							dataAccessService.save(Visit);
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(current);
							calendar.add(Calendar.DATE, daysNo);
							current = calendar.getTime();
							Visit.setgDate(current);
							Visit.sethDate(Utils.grigDatesConvert(current));
						}
						MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));

					} else {
						dataAccessService.save(Visit);
						MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
					}
				}
			}

			if (licence != null) {
				Visits = dataAccessService.getAllVisitsByLicId(licence.getId());
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

}

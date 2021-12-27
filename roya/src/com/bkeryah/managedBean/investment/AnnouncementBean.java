package com.bkeryah.managedBean.investment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.*;

import com.bkeryah.entities.investment.AnncementNews;
import com.bkeryah.entities.investment.AnnoucementDetails;
import com.bkeryah.entities.investment.Announcement;
import com.bkeryah.entities.investment.InvNewspaper;
import com.bkeryah.entities.investment.RealEstate;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class AnnouncementBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private Integer newspaperId;
	private List<RealEstate> realEstatesList = new ArrayList<>();
	private boolean morning;
	private Integer hour;
	private Integer realEstateId;
	private String realEstateName;
	private String area;
	private Integer copyValue;
	private Announcement announcement = new Announcement();
	private AnnoucementDetails announcementDetail = new AnnoucementDetails();
	private Announcement selectedAnnouncement = new Announcement();
	private Integer selectAnnoucementId;
	private List<Announcement> announcements = new ArrayList<Announcement>();
	private List<AnnoucementDetails> annoucementDetails = new ArrayList<AnnoucementDetails>();
	private List<RealEstate> realEstates = new ArrayList<RealEstate>();
	protected static final Logger logger = LogManager.getLogger(AnnouncementBean.class);
	private SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy HH:mm");
	private List<InvNewspaper> newspapersList;
	private boolean addMode;

	@PostConstruct
	public void init() {
		loadAnnouncemnts();
		realEstates = dataAccessService.loadAllRealEstates();
	}

	public void addAnnouncement() {
		announcement.setAnnDetailsList(new ArrayList<AnnoucementDetails>());
		announcement.setAnncementNews(new ArrayList<AnncementNews>());
		addMode = true;
	}

	private void loadAnnouncemnts() {
		announcements = dataAccessService.findAllAnnouncements();
		setAnncNewsName();
	}

	private void setAnncNewsName() {
		for (Announcement annoucement : announcements) {
			setNewsPapersName(annoucement);
		}
	}

	private void setNewsPapersName(Announcement annoucement) {
		annoucement.setNewsPaper("");
		for (AnncementNews newsPaper : annoucement.getAnncementNews()) {
			String newsName = annoucement.getNewsPaper();
			if (newsName == null || newsName.length() == 0)
				annoucement.setNewsPaper(newsPaper.getNewsName());
			else
				annoucement.setNewsPaper(newsName + "-" + (newsPaper.getNewsName()));
		}
	}

	public void loadAnnouncemntsDetails() {
		addMode = false;
		announcement.setAnnDetailsList(dataAccessService.loadAnnoucementDetails(announcement.getId()));
		setNewsPapersName(announcement);
		morning = (announcement.getTimeFlag() == 1);
	}

	public void save() {
		try {
			Date createDate = new Date();
			String hLastTenderDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("startDate");
			String openEnvDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("notifDate");
			announcement.setCreateDate(createDate);
			announcement.sethCreateDate(HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(format.format(createDate)));
			announcement.sethLastTenderDate(hLastTenderDate);
			announcement.setTimeFlag(morning ? 1 : 0);
			announcement.setOpenEnvDate(openEnvDate);
			announcement.setLastTenderTime("" + hour);
			populateAnnouncementNewsPapers();
			dataAccessService.saveAnnouncement(announcement);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			loadAnnouncemnts();
			reset();
		} catch (ParseException e) {
			logger.error("announcement save" + e.getMessage());
			MsgEntry.addErrorMessage(utilities.Utils.loadMessagesFromFile("error.operation"));
		}

	}

	private void populateAnnouncementNewsPapers() {
		announcement.setAnncementNews(new ArrayList<AnncementNews>());
		for (InvNewspaper newsp : announcement.getNewspapersList()) {
			AnncementNews paper = new AnncementNews();
			paper.setNewsId(newsp.getCode());
			announcement.getAnncementNews().add(paper);
		}
	}

//	public void updateRealName() {
//		for (RealEstate realEstate : realEstates) {
//		}
//	}

	public void updateAnnouncement(Announcement announcement) {
		dataAccessService.saveAnnouncement(announcement);
		loadAnnouncemnts();
	}

	public void addRealState() {
		AnnoucementDetails annoucementDetails = new AnnoucementDetails();
		annoucementDetails.setArea(area);
		annoucementDetails.setCopyValue(copyValue);
		annoucementDetails.setRealStateId(realEstateId);
		annoucementDetails.setName(realEstateName);
		annoucementDetails.setStatus(0);
		announcement.getAnnDetailsList().add(annoucementDetails);
		resetRealEstate();
	}

	private void resetRealEstate() {
		area = null;
		copyValue = null;
		realEstateId = null;
		realEstateName = null;
	}

	private void reset() {
		announcement = new Announcement();
	}

	public void deleteInvestment(RealEstate invItem) {
		realEstatesList.remove(invItem);
	}

	public String printReport() {
		String reportName = "/reports/announcement.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("announcement_id", selectedAnnouncement.getId());
		parameters.put("tender_day", Utils.getDayForHigriDate(selectedAnnouncement.gethLastTenderDate()));
		parameters.put("open_day", Utils.getDayForHigriDate(selectedAnnouncement.getOpenEnvDate()));
		parameters.put("SUBREPORT_DIR",
				FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/sub_announcement.jasper"));
		parameters.put("picture_footer",
				FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/watan.jpg"));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public Announcement getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public Integer getNewspaperId() {
		return newspaperId;
	}

	public void setNewspaperId(Integer newspaperId) {
		this.newspaperId = newspaperId;
	}

	public List<RealEstate> getRealEstatesList() {
		return realEstatesList;
	}

	public void setRealEstatesList(List<RealEstate> realEstatesList) {
		this.realEstatesList = realEstatesList;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getRealEstateId() {
		return realEstateId;
	}

	public void setRealEstateId(Integer realEstateId) {
		this.realEstateId = realEstateId;
	}

	public String getRealEstateName() {
		return realEstateName;
	}

	public void setRealEstateName(String realEstateName) {
		this.realEstateName = realEstateName;
	}

	public List<Announcement> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(List<Announcement> announcements) {
		this.announcements = announcements;
	}

	public List<RealEstate> getRealEstates() {
		return realEstates;
	}

	public void setRealEstates(List<RealEstate> realEstates) {
		this.realEstates = realEstates;
	}

	public List<AnnoucementDetails> getAnnoucementDetails() {
		return annoucementDetails;
	}

	public void setAnnoucementDetails(List<AnnoucementDetails> annoucementDetails) {
		this.annoucementDetails = annoucementDetails;
	}

	public AnnoucementDetails getAnnouncementDetail() {
		return announcementDetail;
	}

	public void setAnnouncementDetail(AnnoucementDetails announcementDetail) {
		this.announcementDetail = announcementDetail;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getCopyValue() {
		return copyValue;
	}

	public void setCopyValue(Integer copyValue) {
		this.copyValue = copyValue;
	}

	public Announcement getSelectedAnnouncement() {
		return selectedAnnouncement;
	}

	public void setSelectedAnnouncement(Announcement selectedAnnouncement) {
		this.selectedAnnouncement = selectedAnnouncement;
	}

	public boolean isMorning() {
		return morning;
	}

	public void setMorning(boolean morning) {
		this.morning = morning;
	}

	public Integer getSelectAnnoucementId() {
		return selectAnnoucementId;
	}

	public void setSelectAnnoucementId(Integer selectAnnoucementId) {
		this.selectAnnoucementId = selectAnnoucementId;
	}

	public List<InvNewspaper> getNewspapersList() {
		return newspapersList;
	}

	public void setNewspapersList(List<InvNewspaper> newspapersList) {
		this.newspapersList = newspapersList;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}

	// public List<InvNewspaper> getNewspapersList() {
	// return newspapersList;
	// }
	//
	// public void setNewspapersList(List<InvNewspaper> newspapersList) {
	// this.newspapersList = newspapersList;
	// }

}

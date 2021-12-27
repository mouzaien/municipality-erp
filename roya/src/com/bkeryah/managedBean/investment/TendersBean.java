package com.bkeryah.managedBean.investment;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.*;

import com.bkeryah.entities.investment.AnnoucementDetails;
import com.bkeryah.entities.investment.Tender;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class TendersBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private Integer annoucementDetailsId;
	// private List<RealEstate> realEstatesList;
	private List<AnnoucementDetails> annoucementDetailsList;
	private List<Tender> tendersList;
	private Double area;
	private Tender tender = new Tender();
	private boolean checked;
	private static final Logger logger = LogManager.getLogger(TendersBean.class);

	// @PostConstruct
	// public void init() {
	// annoucementDetailsList =
	// dataAccessService.loadAnnouncementDetailsByStatus();
	// }

	public void loadNotAssignedAnnDetails() {
		Integer[] status = { 0 };
		annoucementDetailsList = dataAccessService.loadAnnouncementDetailsByStatus(status);
		for (AnnoucementDetails item : annoucementDetailsList) {
			System.out.println("annoucementDetailsList111 item : --->>> " + item.toString());
		}
	}

	public void loadAnnouncementDetailsHavingTenders() {
		Integer[] status = { 0, 1 };
		// annoucementDetailsList =
		// dataAccessService.loadAnnouncementDetailsByStatus(status);
		annoucementDetailsList = dataAccessService.loadAnnouncementDetailsHavingTenders(status);
		for (AnnoucementDetails item : annoucementDetailsList) {
			System.out.println("annoucementDetailsList2222 item : --->>> " + item);
		}
	}

	public void addTender() {
		// tender.setRealEstateId(realEstateId);
		tender = new Tender();
	}

	public void loadTenders() {
		loadTendersList();
	}

	public void loadAssignedTenders() {
		loadTendersList();
		if (tendersList != null)
			updateStatusTender();
	}

	private void updateStatusTender() {
		for (Tender tdr : tendersList) {
			if (tdr.getStatus() == 1)
				tdr.setChecked(true);
		}
	}

	private void loadTendersList() {
		tendersList = null;
		if (annoucementDetailsId != 0)
			tendersList = dataAccessService.loadTendersByAnnouncementDetailsId(annoucementDetailsId);
	}

	public void save() {
		try {
			String tenderDate = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("startDate");
			tender.setTenderDate(tenderDate);
			tender.setStatus(0);
			dataAccessService.save(tender);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			if (tender.getAnnouncementDetailsId() == annoucementDetailsId) {
				if (tendersList == null)
					tendersList = new ArrayList<>();
				tender = (Tender) dataAccessService.findEntityById(Tender.class, tender.getId());
				tendersList.add(tender);
			}
			logger.info("save tender: id: " + tender.getId());
			tender = new Tender();
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("save tender: id: " + tender.getId());
		}
	}

	public void checkTenders(Tender selectedTender) {
		checked = false;
		if (selectedTender.isChecked()) {
			checked = true;
			for (Tender tdr : tendersList) {
				if (tdr.getId() == selectedTender.getId())
					continue;
				tdr.setChecked(false);
			}
		}
	}

	public void selectTender() {
		try {
			if (!checked)
				return;
			dataAccessService.selectTender(tendersList);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			logger.info("select tender: announcemetDetailId: " + annoucementDetailsId);
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("select tender: announcemetDetailId: " + annoucementDetailsId);
		}
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	// public List<RealEstate> getRealEstatesList() {
	// return realEstatesList;
	// }
	//
	// public void setRealEstatesList(List<RealEstate> realEstatesList) {
	// this.realEstatesList = realEstatesList;
	// }

	public List<Tender> getTendersList() {
		return tendersList;
	}

	public void setTendersList(List<Tender> tendersList) {
		this.tendersList = tendersList;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Tender getTender() {
		return tender;
	}

	public void setTender(Tender tender) {
		this.tender = tender;
	}

	public List<AnnoucementDetails> getAnnoucementDetailsList() {
		return annoucementDetailsList;
	}

	public void setAnnoucementDetailsList(List<AnnoucementDetails> annoucementDetailsList) {
		this.annoucementDetailsList = annoucementDetailsList;
	}

	public Integer getAnnoucementDetailsId() {
		return annoucementDetailsId;
	}

	public void setAnnoucementDetailsId(Integer annoucementDetailsId) {
		this.annoucementDetailsId = annoucementDetailsId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}

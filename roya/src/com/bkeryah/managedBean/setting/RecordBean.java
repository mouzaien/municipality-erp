package com.bkeryah.managedBean.setting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.entities.ArcApplicationType;
import com.bkeryah.entities.ArcRecords;
import com.bkeryah.service.IDataAccessService;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class RecordBean {
	private static Logger logger = Logger.getLogger(RecordBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<ArcRecords> recordList = new ArrayList<ArcRecords>();
	private List<ArcApplicationType> typeList = new ArrayList<ArcApplicationType>();
	private ArcRecords record = new ArcRecords();
	private List<ArcRecords> filteredrecordList;
	private Date recGDate;
	private String recHDate;
	private boolean higriMode;
	private String hirDate;
	private String recTitle;
	private Integer id;
 private String type;
	@PostConstruct
	public void init() {
		recordList = dataAccessService.findAllRecord();
		typeList = dataAccessService.findAllArc();
	}

	public void update() {
		this.recHDate = record.getRecHDate();
		this.recGDate = record.getRecGDate();
		System.out.println("recHDate" + recHDate);
		Utils.openDialog("job_dlg");

	}

	// update
	// Edit تعديل
	public void updateRecord() {
		try {
			if (record != null) {
				// SimpleDateFormat localDateFormate = new
				// SimpleDateFormat("dd/mm/yyyy");
				// record.setRecHDate(localDateFormate.format(recHDate));
				record.setRecGDate(recGDate);
				if (higriMode == true) {
					record.setRecHDate(recHDate);// هجري
					Date gDate = Utils.convertHDateToGDate(recHDate);
					record.setRecGDate(gDate);

				} else {
					record.setRecGDate(recGDate);// ميلادي
					// convert from g to h
					String hDate = Utils.grigDatesConvert(recGDate);
					record.setRecHDate(hDate);

				}
				dataAccessService.updateObject(record);
				record = new ArcRecords();
				// logger.info("Update User: id: " + record.getId());
			}
			MsgEntry.addAcceptFlashInfoMessage("تم تنفيذ العملية");
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation") + e.getMessage());
		}

	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<ArcRecords> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<ArcRecords> recordList) {
		this.recordList = recordList;
	}

	public ArcRecords getRecord() {
		return record;
	}

	public void setRecord(ArcRecords record) {
		this.record = record;
	}

	public Date getRecGDate() {
		return recGDate;
	}

	public void setRecGDate(Date recGDate) {
		this.recGDate = recGDate;
	}

	public String getRecHDate() {
		return recHDate;
	}

	public void setRecHDate(String recHDate) {
		this.recHDate = recHDate;
	}

	public boolean isHigriMode() {
		return higriMode;
	}

	public void setHigriMode(boolean higriMode) {
		this.higriMode = higriMode;
	}

	public String getHirDate() {
		return hirDate;
	}

	public void setHirDate(String hirDate) {
		this.hirDate = hirDate;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		RecordBean.logger = logger;
	}

	public String getRecTitle() {
		return recTitle;
	}

	public void setRecTitle(String recTitle) {
		this.recTitle = recTitle;
	}

	public List<ArcRecords> getFilteredrecordList() {
		return filteredrecordList;
	}

	public void setFilteredrecordList(List<ArcRecords> filteredrecordList) {
		this.filteredrecordList = filteredrecordList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<ArcApplicationType> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<ArcApplicationType> typeList) {
		this.typeList = typeList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
 
}

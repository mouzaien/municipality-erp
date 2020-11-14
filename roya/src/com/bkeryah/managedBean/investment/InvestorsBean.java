package com.bkeryah.managedBean.investment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.bkeryah.entities.investment.Investor;
import com.bkeryah.entities.investment.InvestorIdentityType;
import com.bkeryah.entities.investment.InvestorStatus;
import com.bkeryah.entities.investment.InvestorType;
import com.bkeryah.entities.investment.RealEstate;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class InvestorsBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<Investor> investorsList;
	private List<Investor> filteredInvestorsList;
	private Investor investor = new Investor();

	private InvestorType investorType;
	private InvestorIdentityType investorIdentityType;
	private InvestorStatus investorStatus;
	private List<InvestorType> investorTypeList = new ArrayList<InvestorType>();
	private List<InvestorIdentityType> investorIdentityTypeList = new ArrayList<InvestorIdentityType>();
	private List<InvestorStatus> investorStatusList = new ArrayList<InvestorStatus>();

	private boolean addMode = true;
	private static final Logger logger = Logger.getLogger(InvestorsBean.class);

	private Integer idType;

	private boolean higriMode;
	private String selectedDate;
	private Date selectedDate_G;

	private Integer investorId = -1;
	private List<Investor> investorsListfiltter = new ArrayList<Investor>();

	private Integer trdRecord = -1;
	private String mobile = "-1";

	@PostConstruct
	public void init() {
		investorTypeList = dataAccessService.loadAllInvestorType();
		investorIdentityTypeList = dataAccessService.loadAllInvestorIdentityType();
		investorStatusList = dataAccessService.loadAllInvestorStatus();
		investorsListfiltter = dataAccessService.loadAllInvestors();
		loadInvestors();
	}

	public String printAllInvestorsReportAction() {
		String reportName = "/reports/all_investors_report.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("inv_id", investorId);
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public void loadInvestors() {
		investorsList = dataAccessService.loadAllInvestors();
	}

	public String loadInvestorsById() {
		investorsList = dataAccessService.loadAllInvestorsById(investorId);
		return "";
	}

	public void loadInvestor() {
		setAddMode(false);
	}

	public void addInvestor() {
		investor = new Investor();
		setAddMode(true);
	}

	public void onRowSelect(SelectEvent event) {
		investor = new Investor();
		investor = (Investor) event.getObject();
	}

	public void onRowUnselect(UnselectEvent event) {
	}

	public void save() {
		try {
			sortDates();

			// if (selectedDate != null) {
			// System.out.println("Selected Date is ------>>>> " +
			// selectedDate);
			// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			//
			// investor.setCreateDate(selectedDate);
			// System.out.println("Selected Date datatype ------>>>> " +
			// selectedDate.getClass().getName());
			// String girg = sdf.format(selectedDate);
			// System.out.println(
			// "Selected Date datatype after convert to String------>>>> " +
			// girg.getClass().getName());
			// System.out.println("Selected Date value after convert to
			// String------>>>> " + girg);
			// investor.setHigriCreateDate(HijriCalendarUtil.ConvertgeorgianDatetoHijriDate(girg));
			// }
			// String createDate =
			// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
			// .get("startDate");

			// investor.setInvestorIdType(idType);
			// investor.setHigriCreateDate(createDate);
			// if (createDate != null) {
			// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			// investor.setCreateDate(sdf.parse(HijriCalendarUtil.ConvertHijriTogeorgianDate(createDate)));
			// }
			if (addMode) {
				dataAccessService.save(investor);
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				investorsList.add(investor);
				investor = new Investor();
				logger.info("add investor: trade record: " + investor.getTradeRecord());
			} else {

				dataAccessService.updateObject(investor);
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			}
		} catch (

		Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("add investor: trade record: " + investor.getTradeRecord());
		}
	}

	public void sortDates() {

		try {
			if (higriMode) {
				selectedDate_G = Utils.convertHDateToGDate(selectedDate);
			} else {
				selectedDate = Utils.grigDatesConvert(selectedDate_G);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		investor.setHigriCreateDate(selectedDate);
		investor.setCreateDate(selectedDate_G);
	}

	public void deleteInvestor() {
		try {
			if (investor != null) {
				dataAccessService.deleteObject(investor);
				investorsList.remove(investor);
				MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				logger.info("delete investor: trade record: " + investor.getTradeRecord());
			}
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("delete investor: trade record: " + investor.getTradeRecord());
		}
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<Investor> getInvestorsList() {
		return investorsList;
	}

	public void setInvestorsList(List<Investor> investorsList) {
		this.investorsList = investorsList;
	}

	public List<Investor> getFilteredInvestorsList() {
		return filteredInvestorsList;
	}

	public void setFilteredInvestorsList(List<Investor> filteredInvestorsList) {
		this.filteredInvestorsList = filteredInvestorsList;
	}

	public Investor getInvestor() {
		return investor;
	}

	public void setInvestor(Investor investor) {
		this.investor = investor;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}

	public InvestorType getInvestorType() {
		return investorType;
	}

	public void setInvestorType(InvestorType investorType) {
		this.investorType = investorType;
	}

	public List<InvestorType> getInvestorTypeList() {
		return investorTypeList;
	}

	public void setInvestorTypeList(List<InvestorType> investorTypeList) {
		this.investorTypeList = investorTypeList;
	}

	public List<InvestorIdentityType> getInvestorIdentityTypeList() {
		return investorIdentityTypeList;
	}

	public void setInvestorIdentityTypeList(List<InvestorIdentityType> investorIdentityTypeList) {
		this.investorIdentityTypeList = investorIdentityTypeList;
	}

	public List<InvestorStatus> getInvestorStatusList() {
		return investorStatusList;
	}

	public void setInvestorStatusList(List<InvestorStatus> investorStatusList) {
		this.investorStatusList = investorStatusList;
	}

	public InvestorIdentityType getInvestorIdentityType() {
		return investorIdentityType;
	}

	public void setInvestorIdentityType(InvestorIdentityType investorIdentityType) {
		this.investorIdentityType = investorIdentityType;
	}

	public InvestorStatus getInvestorStatus() {
		return investorStatus;
	}

	public void setInvestorStatus(InvestorStatus investorStatus) {
		this.investorStatus = investorStatus;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public boolean isHigriMode() {
		return higriMode;
	}

	public void setHigriMode(boolean higriMode) {
		this.higriMode = higriMode;
	}

	public String getSelectedDate() {
		selectedDate = investor.getHigriCreateDate();
		return selectedDate;
	}

	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}

	public Date getSelectedDate_G() {
		selectedDate_G = investor.getCreateDate();
		return selectedDate_G;
	}

	public void setSelectedDate_G(Date selectedDate_G) {
		this.selectedDate_G = selectedDate_G;
	}

	public Integer getInvestorId() {
		return investorId;
	}

	public void setInvestorId(Integer investorId) {
		this.investorId = investorId;
	}

	public List<Investor> getInvestorsListfiltter() {
		return investorsListfiltter;
	}

	public void setInvestorsListfiltter(List<Investor> investorsListfiltter) {
		this.investorsListfiltter = investorsListfiltter;
	}

	public Integer getTrdRecord() {
		return trdRecord;
	}

	public void setTrdRecord(Integer trdRecord) {
		this.trdRecord = trdRecord;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}

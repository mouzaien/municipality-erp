package com.bkeryah.hr.managedBeans;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.StreamedContent;

import com.bkeryah.entities.SysCategoryEmployer;
import com.bkeryah.hr.entities.HrsEmpOvertime;
import com.bkeryah.hr.entities.Sys012;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean(name = "hrsOverTime")
@SessionScoped
public class HrsOverTimeBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private int catid;
	private Integer pmonth;
	private int pyear;
	private List<SysCategoryEmployer> listCategory;
	private int frommonth;
	private int tomonth;
	private List<HrsEmpOvertime> listovertime;
	private List<HrsEmpOvertime> filterovtime;
	List<Sys012> listMonth;
	private StreamedContent file;
	private Double summSalary = 0d;
	private Double summTrans = 0.00d;
	private Double summTotal = 0d;
	private String strSumSalary;
	private String strSummTrans;
	private String strSummTotal;
	private static DecimalFormat format = new DecimalFormat(".##");
	private boolean cantPrint;
	private Integer numReport;
	private static final Logger logger = Logger.getLogger(HrsOverTimeBean.class);

	@PostConstruct
	public void init() {
		// pyear = Integer.parseInt(HijriCalendarUtil.findCurrentYear());
		pyear = HijriCalendarUtil.findCurrentGeorgianYear();
		listCategory = dataAccessService.loadCategoryEmployer();
		listMonth = dataAccessService.loadMonthsHijri();
	}

	public void retriveOverTime() {
		summTotal = 0d;
		strSumSalary = "";
		strSummTrans = "";
		strSummTotal = "";
		summSalary = 0d;
		summTrans = 0d;
		listovertime = dataAccessService.retriveOverTime(getCatid(), getFrommonth(), getTomonth(), getPyear());
		if (listovertime != null && listovertime.size() > 0)
			cantPrint = true;
		else
			cantPrint = false;
		for (HrsEmpOvertime hrsEmpOvertime : listovertime) {
			summSalary = summSalary + Double.parseDouble(hrsEmpOvertime.getSalary());
			summTrans = summTrans + Double.parseDouble(hrsEmpOvertime.getTransMoney());
		}
		summTotal = summSalary + summTrans;
		strSumSalary = format.format(summSalary);
		strSummTrans = format.format(summTrans);
		strSummTotal = format.format(summTotal);
	}

	public void searchSalary() {

	}

	public void onRowEdit(RowEditEvent event) {

		if (((HrsEmpOvertime) event.getObject()).getId() != null) {
			dataAccessService.updateObject((HrsEmpOvertime) event.getObject());
			MsgEntry.addInfoMessage(MsgEntry.SUCCESS_SAVE_TIMETABLESHIFT);

		}
	}

	public void onRowCancel(RowEditEvent event) {
		if (((HrsEmpOvertime) event.getObject()).getId() != null) {
		}
	}

	public void deleteUserOverTime(HrsEmpOvertime selectedOvertime) {
		try {
			dataAccessService.deleteObject(selectedOvertime);
			listovertime.remove(selectedOvertime);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			logger.info("delete overTime  empId: " + selectedOvertime.getEmployeeNumber());
		} catch (Exception ex) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			logger.error("delete overTime empID: " + selectedOvertime.getEmployeeNumber());
		}
	}

	public String printReportAction() {
		switch (numReport) {
		case 1:
			printReportOvertime();
			break;
		case 2:
			printAnnouncementReportAction();
			break;
		default:
			break;
		}
		return "";
	}

	private void printReportOvertime() {
		String pmonth = null;
		String reportName = "/reports/overtime.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("compName", dataAccessService.findSystemProperty("CUSTOMER_NAME"));
		parameters.put("year", pyear);
		// listMonth.get(index)
		if (frommonth <= 12)
			pmonth = Utils.loadMessagesFromFile("about.month") + listMonth.get(frommonth - 1).getNameGr();
		if (frommonth == 13)
			pmonth = Utils.loadMessagesFromFile("eid.fitr");
		else if (frommonth == 14)
			pmonth = Utils.loadMessagesFromFile("eid.adha");

		parameters.put("month", pmonth);
		parameters.put("salary", strSumSalary);
		parameters.put("trans", strSummTrans);
		parameters.put("total", strSummTotal);
		parameters.put("libTotal", Utils.getAmountInLetters(strSummTotal));
		parameters.put("LOGO_DIR", FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath(Utils.loadMessagesFromFile("report.logo")));
		Utils.printPdfReportFromListDataSource(reportName, parameters, listovertime);

	}

	private void printAnnouncementReportAction() {
		String reportName = "/reports/overtime_employers.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("compName", dataAccessService.findSystemProperty("CUSTOMER_NAME"));
		parameters.put("year", pyear);
		parameters.put("min_month", frommonth);
		parameters.put("max_month", tomonth);
		parameters.put("category", catid);
		parameters.put("LOGO_DIR", FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath(Utils.loadMessagesFromFile("report.logo")));
		Utils.printPdfReport(reportName, parameters);
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public int getCatid() {
		return catid;
	}

	public void setCatid(int catid) {
		this.catid = catid;
	}

	public List<SysCategoryEmployer> getListCategory() {
		return listCategory;
	}

	public void setListCategory(List<SysCategoryEmployer> listCategory) {
		this.listCategory = listCategory;
	}

	public Integer getPmonth() {
		return pmonth;
	}

	public void setPmonth(Integer pmonth) {
		this.pmonth = pmonth;
	}

	public int getPyear() {
		return pyear;
	}

	public void setPyear(int pyear) {
		this.pyear = pyear;
	}

	public List<Sys012> getListMonth() {
		return listMonth;
	}

	public void setListMonth(List<Sys012> listMonth) {
		this.listMonth = listMonth;
	}

	public int getFrommonth() {
		return frommonth;
	}

	public void setFrommonth(int frommonth) {
		this.frommonth = frommonth;
	}

	public int getTomonth() {
		return tomonth;
	}

	public void setTomonth(int tomonth) {
		this.tomonth = tomonth;
	}

	public List<HrsEmpOvertime> getListovertime() {
		return listovertime;
	}

	public void setListovertime(List<HrsEmpOvertime> listovertime) {
		this.listovertime = listovertime;
	}

	public List<HrsEmpOvertime> getFilterovtime() {
		return filterovtime;
	}

	public void setFilterovtime(List<HrsEmpOvertime> filterovtime) {
		this.filterovtime = filterovtime;
	}

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public Double getSummSalary() {
		return summSalary;
	}

	public void setSummSalary(Double summSalary) {
		this.summSalary = summSalary;
	}

	public Double getSummTrans() {
		return summTrans;
	}

	public void setSummTrans(Double summTrans) {
		this.summTrans = summTrans;
	}

	public Double getSummTotal() {
		return summTotal;
	}

	public void setSummTotal(Double summTotal) {
		this.summTotal = summTotal;
	}

	public String getStrSumSalary() {
		return strSumSalary;
	}

	public void setStrSumSalary(String strSumSalary) {
		this.strSumSalary = strSumSalary;
	}

	public String getStrSummTrans() {
		return strSummTrans;
	}

	public void setStrSummTrans(String strSummTrans) {
		this.strSummTrans = strSummTrans;
	}

	public String getStrSummTotal() {
		return strSummTotal;
	}

	public void setStrSummTotal(String strSummTotal) {
		this.strSummTotal = strSummTotal;
	}

	public boolean isCantPrint() {
		return cantPrint;
	}

	public void setCantPrint(boolean cantPrint) {
		this.cantPrint = cantPrint;
	}

	public Integer getNumReport() {
		return numReport;
	}

	public void setNumReport(Integer numReport) {
		this.numReport = numReport;
	}

}

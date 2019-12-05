package com.bkeryah.hr.managedBeans;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.bkeryah.entities.HrsMasterFile;
import com.bkeryah.entities.SysCategoryEmployer;
import com.bkeryah.hr.entities.HrsSalary;
import com.bkeryah.hr.entities.Sys012;
import com.bkeryah.service.IDataAccessService;

import utilities.FtpTransferFile;
import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

@ManagedBean(name = "hrsSalaryBean")
@ViewScoped
public class HrsSalaryBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<HrsMasterFile> employers;
	private HrsMasterFile employer;
	private Integer employerId;
	private int catid;
	private Integer pmonth;
	private StreamedContent file;
	private int pyear;
	private List<SysCategoryEmployer> listCategory;
	private List<HrsSalary> listSalary;
	private List<HrsSalary> filterSalary;
	private List<Sys012> listMonth;
	private Integer action = 1;

	public StreamedContent getFile() {
		return file;
	}

	@PostConstruct
	public void init() {
		pyear = HijriCalendarUtil.findCurrentGeorgianYear();
		listCategory = dataAccessService.loadCategoryEmployer();
		employers = dataAccessService.loadActiveEmpsHasSalary();
		listMonth = dataAccessService.loadMonthsHijri();
	}

	public void calcSalary() {
		dataAccessService.calcSalary(getCatid(), getPmonth(), getPyear());
		listSalary = dataAccessService.loadListSalary(getCatid(), getPmonth(), getPyear());
	}

	public void searchSalary() {
		listSalary = dataAccessService.loadListSalary(getCatid(), getPmonth(), getPyear());
	}

	public void onRowEdit(RowEditEvent event) {
		dataAccessService.updateObject((HrsSalary) event.getObject());
		MsgEntry.addInfoMessage(MsgEntry.SUCCESS_UPDATE_VACATION);
	}

	public void onRowCancel(RowEditEvent event) {
	}

	public String printIreportIssueSal() {
		String reportName = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		reportName = (getCatid() == 4) ? "/reports/hrs_salary_saudi.jasper" : "/reports/hrs_salary.jasper";
		parameters.put("compName", Utils.loadMessagesFromFile("comp.name"));
		parameters.put("mon", getPmonth());
		parameters.put("year", getPyear());
		parameters.put("catcode", getCatid());
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public String printIreportIssueFull() {
		String reportName = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		reportName = "/reports/hrs_salary_all.jasper";
		parameters.put("compName", Utils.loadMessagesFromFile("comp.name"));
		parameters.put("month", getPmonth());
		parameters.put("year", getPyear());
		Utils.printPdfReport(reportName, parameters);
		return "";
	}

	public void dowloadSalariesFile(int type) throws IOException {
		dataAccessService.exportSalaryFile(catid, pyear, pmonth, type);
		String salarfile = "xx.txt";
		if (type == 1)
			salarfile = MyConstants.SALARFILE;
		else
			salarfile = MyConstants.SALAROVERTIME;
		InputStream stream = FtpTransferFile.getFile(salarfile);
		//FtpTransferFile.deleteFile(salarfile);
		file = new DefaultStreamedContent(stream, "txt", salarfile);
	}

	public List<HrsMasterFile> getEmployers() {
		return employers;
	}

	public List<HrsSalary> getListSalary() {
		return listSalary;
	}

	public void setListSalary(List<HrsSalary> listSalary) {
		this.listSalary = listSalary;
	}

	public void setEmployers(List<HrsMasterFile> employers) {
		this.employers = employers;
	}

	public HrsMasterFile getEmployer() {
		return employer;
	}

	public void setEmployer(HrsMasterFile employer) {
		this.employer = employer;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
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

	public List<HrsSalary> getFilterSalary() {
		return filterSalary;
	}

	public void setFilterSalary(List<HrsSalary> filterSalary) {
		this.filterSalary = filterSalary;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

}

package com.bkeryah.dev.beans;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.bkeryah.entities.DeputationTraining;
import com.bkeryah.entities.HrsMasterFile;
import com.bkeryah.entities.Type;
import com.bkeryah.hr.entities.Sys012;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.service.IDataAccessService;

import utilities.FtpTransferFile;
import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.MyConstants;
import utilities.Utils;

//import antlr.collections.List;

//import javax.enterprise.context.SessionScoped;
@ManagedBean
@ViewScoped

public class DeputationTrainingBean {

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;

	private Employer employer = new Employer();
	private List<HrsMasterFile> employersList;
	private Integer employerNB;
	private Integer month;

//	private Integer no_of_days;
	private Integer trans;
	private Integer train;
	private String type = "1";
	private String end_date;
	private List<DeputationTraining> list;
//	private String purpose = "";
	private DeputationTraining dep_train;
	private StreamedContent file;

	private List<Sys012> listMonth;
	private Integer year=1441;
//	private String startDate;

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<HrsMasterFile> getEmployersList() {
		return employersList;
	}

	public void setEmployersList(List<HrsMasterFile> employersList) {
		this.employersList = employersList;
	}

	public Integer getEmployerNB() {
		return employerNB;
	}

	public void setEmployerNB(Integer employerNB) {
		this.employerNB = employerNB;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getTrain() {

		return train;
	}

	public void setTrain(Integer y) {
		this.train = y;
	}

//	public String getPurpose() {
//		return purpose;
//	}
//
//	public void setPurpose(String purpose) {
//		this.purpose = purpose;
//	}

	public Integer getTrans() {
		return trans;
	}

	public void setTrans(Integer trans) {
		this.trans = trans;
	}

//	public Integer getNo_of_days() {
//		return no_of_days;
//	}
//
//	public void setNo_of_days(Integer no_of_days) {
//		this.no_of_days = no_of_days;
//	}

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public DeputationTrainingBean() {
		employerNB=0;
		
	}

	@PostConstruct
	public void init() {
		employersList = dataAccessService.loadActiveEmpsHasSalary();
		loadStatus();
		listMonth = dataAccessService.loadMonthsHijri();
		dep_train = new DeputationTraining();
	}

	public void loadEmployer() {
		setEmployer(dataAccessService.loadEmployerByUser(employerNB));
		dep_train = new DeputationTraining();
//		no_of_days = null;
		train = null;
		trans = null;
//		purpose = null;

	}

	public void change() {
		if (employer.getTrans() == null || dep_train.getDayscount() == null)
			;

		else
			trans = (employer.getTrans() * dep_train.getDayscount()) / 30;
		if (employer.getBasicSalary() != null && dep_train.getDayscount() != null)
			train = (employer.getBasicSalary() * dep_train.getDayscount()) / 30;
		calculateDate();
	}

	public void changeType() {

	}
	
	public void calculateDate() {
		if ((dep_train.getDayscount() != null) && (dep_train.getDayscount() != 0) && (dep_train.getHij_st_date() != null) && (!dep_train.getHij_st_date().trim().equals("")))
				end_date = HijriCalendarUtil.addDaysToHijriDate(dep_train.getHij_st_date(), dep_train.getDayscount() - 1);
	}

	public void saveRequest() {
//		dep_train = new DeputationTraining();
		dep_train.setEmp_no(employer.getId());
//		dep_train.setDayscount(no_of_days);
//		String start_date = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
//				.get("startDate");
		DateFormat format1 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		DateFormat format2 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		Date st_date = null;
		Date final_date = null;
		month=Integer.parseInt(dep_train.getHij_st_date().substring(3, 5));
		year=Integer.parseInt(dep_train.getHij_st_date().substring(6,10));
		try {
			st_date = format1.parse(HijriCalendarUtil.ConvertHijriTogeorgianDate(dep_train.getHij_st_date()));

			final_date = format2.parse(HijriCalendarUtil.ConvertHijriTogeorgianDate(end_date));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		dep_train.setSt_date(st_date);
		dep_train.setEnd_date(final_date);
		dep_train.setHij_st_date(dep_train.getHij_st_date());
		dep_train.setHij_end_date(end_date);
//		dep_train.setMission(purpose);
		dep_train.setEmp_type(employer.getEmployee_type());
		dep_train.setMaster_empno(employerNB);
		dep_train.setMonth(month);
		dep_train.setYear(year);

		if (Integer.parseInt(type) == 0) {
			if (train != null)
				dep_train.setTrain_allowance(train);
			if (trans != null)
				dep_train.setTrans_allowance(trans);
			dep_train.setType(Type.training);
		} else if (Integer.parseInt(type) == 1)
			if (trans != null) {
				dep_train.setTrans_allowance(trans);
				dep_train.setType(Type.deputation);
			}
		try {
			dataAccessService.saveOperation(dep_train);
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
			dep_train = new DeputationTraining();
			employerNB = null;
			employer = new Employer();
//			no_of_days=0;
//			startDate=null;
			end_date=null;
//			purpose=null;
			trans= null;
			train= null;
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		} 
		

	}

	public void dowloadDepTrainFile() throws IOException {
		
		dataAccessService.exportDepTrainFile(employer.getId(),month,year);
	
		String salarfile = "xx.txt";

		salarfile = MyConstants.deputation_training;

		InputStream stream = FtpTransferFile.getFile(salarfile);
		FtpTransferFile.deleteFile(salarfile);
		file = new DefaultStreamedContent(stream, "txt", salarfile);
	}

	public void loadStatus() {
		if(employerNB==0)
			{
			employer.setId(0);
			loadMonthYearStatus();
			//list = dataAccessService.loadStatus(employer.getId());
			
			}
		else
		{
		setEmployer(dataAccessService.loadEmployerByUser(employerNB));
		loadMonthYearStatus();
		//list = dataAccessService.loadStatus(employer.getId());
		}
		

	}
	public void loadMonthYearStatus() {
		
		list = dataAccessService.loadStatus(employer.getId(), month, year);
		
	}

	public DeputationTraining getDep_train() {
		return dep_train;
	}

	public void setDep_train(DeputationTraining dep_train) {
		this.dep_train = dep_train;
	}

	public String getEnd_date() {
		//calculateDate();
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public List<DeputationTraining> getList() {
		return list;
	}

	public void setList(List<DeputationTraining> list) {
		this.list = list;
	}

	public StreamedContent getFile() {
		return file;
	}



	public List<Sys012> getListMonth() {
		return listMonth;
	}

	public void setListMonth(List<Sys012> listMonth) {
		this.listMonth = listMonth;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

//	public String getStartDate() {
//		return startDate;
//	}
//
//	public void setStartDate(String startDate) {
//		this.startDate = startDate;
//	}

}

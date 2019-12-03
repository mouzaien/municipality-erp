package com.bkeryah.dev.beans;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.bkeryah.entities.HrsMasterFile;
import com.bkeryah.entities.RewardInfo;
import com.bkeryah.hr.entities.Sys012;
import com.bkeryah.hr.managedBeans.Employer;
import com.bkeryah.service.IDataAccessService;

import utilities.FtpTransferFile;
import utilities.HijriCalendarUtil;
import utilities.MyConstants;

@ManagedBean
@ViewScoped

public class RewardBean {
	
	
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	
	
	private Employer employer = new Employer();
	private Integer employerNB;
	private Integer month;
	private Integer year=1441;
	private Integer amount;
	private String reward_type;
	private List<HrsMasterFile> employersList;
	private List<Sys012> listMonth;
	private StreamedContent file;
	
	private RewardInfo rewardinfo;
	
	private List<RewardInfo> list;
	
	public RewardBean()
	{
		employerNB=0;
	}
		
	
	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}
	
	@PostConstruct
	public void init() {
		employersList = dataAccessService.loadActiveEmpsHasSalary();
		loadStatus();
		listMonth = dataAccessService.loadMonthsHijri();
	}
	
	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
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
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getReward_type() {
		return reward_type;
	}
	public void setReward_type(String reward_type) {
		this.reward_type = reward_type;
	}

	public List<HrsMasterFile> getEmployersList() {
		return employersList;
	}

	public void setEmployersList(List<HrsMasterFile> employersList) {
		this.employersList = employersList;
	}
	public void loadEmployer() {
		setEmployer(dataAccessService.loadEmployerByUser(employerNB));

	}

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public List<Sys012> getListMonth() {
		return listMonth;
	}

	public void setListMonth(List<Sys012> listMonth) {
		this.listMonth = listMonth;
	}


	
	public void saveReward() {
		rewardinfo=new RewardInfo();
		rewardinfo.setMonth(month);
		rewardinfo.setAmount(amount);
		rewardinfo.setType(reward_type);
		rewardinfo.setYear(year);
		rewardinfo.setEmp_no(employer.getId());
		rewardinfo.setMaster_empno(employerNB);
		try {
		dataAccessService.saveReward(rewardinfo);
		month=null;
		amount=null;
		year=null;
		reward_type=null;
		}catch(org.springframework.dao.DataIntegrityViolationException e) {
			System.out.println(e.getMessage());
		}
	}
	public void loadStatus() {
		
		if(employerNB==0)
		{
		employer.setId(0);
		loadMonthYearStatus();
		//list = dataAccessService.loadRewards(employer.getId());
		
		}
		else {
		
		loadEmployer();
		list=dataAccessService.loadRewards(employer.getId());
		loadMonthYearStatus();
		//list = dataAccessService.loadRewards(employer.getId());
		}
	}
public void loadMonthYearStatus() {
		
		list = dataAccessService.loadRewards(employer.getId(), month, year);
		
	}

	public List<RewardInfo> getList() {
		return list;
	}

	public void setList(List<RewardInfo> list) {
		this.list = list;
	}
	
	
	
public void dowloadRewardFile() throws IOException {
		
		dataAccessService.exportRewardFile(employer.getId(),month,year);
		String salarfile = "xx.txt";

		salarfile = MyConstants.reward;

		InputStream stream = FtpTransferFile.getFile(salarfile);
		FtpTransferFile.deleteFile(salarfile);
		file = new DefaultStreamedContent(stream, "txt", salarfile);
	}

	

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}
	
	

}

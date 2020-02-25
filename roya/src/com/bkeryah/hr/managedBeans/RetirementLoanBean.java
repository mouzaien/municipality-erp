package com.bkeryah.hr.managedBeans;

import java.util.ArrayList;

/**
 * @author Haitham
 *
 */

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.bkeryah.hr.entities.HrsLoanType;
import com.bkeryah.model.LoanModel;
import com.bkeryah.model.RetirementModel;
import com.bkeryah.service.IDataAccessService;

@ManagedBean
@ViewScoped
public class RetirementLoanBean {
	
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private String year;
	private Integer month;
	private Integer bankType;
	private List<HrsLoanType> banksType;
	private List<LoanModel> usersLoan=new ArrayList<LoanModel>();
	private List<RetirementModel> usersRetirement;
	private Integer flag=0;
	private int total;
	
	
	@PostConstruct
	public void init() {
		banksType=dataAccessService.loadAllLoanTypes();
		
	}
	

	
	
	public void loanAction(){
		usersLoan.clear();
		flag = 1;
		usersLoan=dataAccessService.loadUsersLoan(Integer.parseInt(year), month, bankType);
			for(int i=0;i<usersLoan.size();i++) {
						total+=usersLoan.get(i).getPaidMonthly();
					}
			LoanModel totalNoead=new LoanModel();
			totalNoead.setPaidMonthly(total);
			totalNoead.setBankName("المجموع ");
			usersLoan.add(totalNoead);
	}
	public void RetirementAction(){
		flag=2;
		usersRetirement=dataAccessService.loadRetirement();
		
		
		
	}
	




	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}




	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}




	public String getYear() {
		return year;
	}




	public void setYear(String year) {
		this.year = year;
	}




	public Integer getMonth() {
		return month;
	}




	public void setMonth(Integer month) {
		this.month = month;
	}




	public Integer getBankType() {
		return bankType;
	}




	public void setBankType(Integer bankType) {
		this.bankType = bankType;
	}




	public List<HrsLoanType> getBanksType() {
		return banksType;
	}




	public void setBanksType(List<HrsLoanType> banksType) {
		this.banksType = banksType;
	}




	public List<LoanModel> getUsersLoan() {
		return usersLoan;
	}




	public void setUsersLoan(List<LoanModel> usersLoan) {
		this.usersLoan = usersLoan;
	}




	public List<RetirementModel> getUsersRetirement() {
		return usersRetirement;
	}




	public void setUsersRetirement(List<RetirementModel> usersRetirement) {
		this.usersRetirement = usersRetirement;
	}




	public Integer getFlag() {
		return flag;
	}




	public void setFlag(Integer flag) {
		this.flag = flag;
	}




	public int getTotal() {
		return total;
	}




	public void setTotal(int total) {
		this.total = total;
	}
}
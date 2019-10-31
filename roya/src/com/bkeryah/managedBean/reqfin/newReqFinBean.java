package com.bkeryah.managedBean.reqfin;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import com.bkeryah.entities.ArcPeople;
import com.bkeryah.entities.PayLicBills;
import com.bkeryah.model.User;
import com.bkeryah.penalties.LicTrdMasterFile;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.shared.beans.Scanner;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class newReqFinBean extends Scanner implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private Integer fromNumber;
	private Integer toNumber;
	private String fromDate;
	private String toDate;
	private Integer checkNumber;
	private Long identifierNumber;
	private String licenceNumber;
	private Integer StatusPayed;
	private ArcPeople listEmployer ;	
	private LicTrdMasterFile listlicence;
	private List<User> users;
	
	
	@PostConstruct
	public void init() {
		 users = dataAccessService.getAllUsers();
	}
	public void loadLicwnceByNumber(){
		listlicence = dataAccessService.loadLicwnceByNumber(getLicenceNumber());
	}
	public void loadnameEmployerbynationid(){
		int nationalIsFound = dataAccessService.nationalIsFoundarcpeople(getIdentifierNumber());
		if(nationalIsFound > 0 ) {
			 listEmployer = dataAccessService.loadnamebynationid(getIdentifierNumber());
//
			
		}
		else{

			 MsgEntry.addErrorMessage(MsgEntry.NATIONALNOFOUND);	
	}
	}
	
	public LicTrdMasterFile getListlicence() {
		return listlicence;
	}
	public void setListlicence(LicTrdMasterFile listlicence) {
		this.listlicence = listlicence;
	}

	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}
	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}
	public Integer getFromNumber() {
		return fromNumber;
	}
	public void setFromNumber(Integer fromNumber) {
		this.fromNumber = fromNumber;
	}
	public Integer getToNumber() {
		return toNumber;
	}
	public void setToNumber(Integer toNumber) {
		this.toNumber = toNumber;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public Integer getCheckNumber() {
		return checkNumber;
	}
	public void setCheckNumber(Integer checkNumber) {
		this.checkNumber = checkNumber;
	}
	public Long getIdentifierNumber() {
		return identifierNumber;
	}
	public void setIdentifierNumber(Long identifierNumber) {
		this.identifierNumber = identifierNumber;
	}
	public String getLicenceNumber() {
		return licenceNumber;
	}
	public void setLicenceNumber(String licenceNumber) {
		this.licenceNumber = licenceNumber;
	}
	public Integer getStatusPayed() {
		return StatusPayed;
	}
	public void setStatusPayed(Integer statusPayed) {
		StatusPayed = statusPayed;
	}
//	public String printt(){
//		
//		 String repName = "";
//		  String parameter = "";
//		   repName = "req013";
//			  String vf_id_no;
//			  String vf_lic_no;
//			  String vf_supr;
//			  String vf_fine_case;
//			  String   vfine_date;
//			  String  vfine_no;
//
//
//
//
//
//
//
//	
//		   
//		   
//		   if (getFromNumber() != null && getToNumber() != null && getFromNumber() != 0 && getToNumber() != 0)
//		    vfine_no =" and fine_no between "+ getFromNumber() + " and " + getToNumber();
//		 
//		   else
//			   vfine_no =" and fine_no >=1" ;
//		   
//		   if (getFromDate() != null && getToDate() != null && !getFromDate().trim().equals("") && !getToDate().trim().equals("")) 
//		      vfine_date =" and    substr(fine_date,7,4)||substr(fine_date,4,2)||substr(fine_date,1,2)  >="+
//				  getFromDate().substring(6, 10) + getFromDate().substring(3, 5)+getFromDate().substring(0, 2) 
//				  + " and substr(fine_date,7,4)||substr(fine_date,4,2)||substr(fine_date,1,2)  <=" + 
//				  getToDate().substring(6, 10) + getToDate().substring(3, 5)+getToDate().substring(0, 2);
//		   else
//			   vfine_date = "";
//		   if  (getIdentifierNumber() == null || getIdentifierNumber() == 0){
//			   vf_id_no = "";
//			   
//		   }
//		  else{
//			  vf_id_no =  " and F_ID_NO=" +  "'"+ getIdentifierNumber() + "'";
//			  }
//		 
//		  
//		  if  (getLicenceNumber() == null || getLicenceNumber().trim().equals("") ) {
//		 	vf_lic_no = "";}
//		  else
//		  	vf_lic_no = " and F_LICENCE_NO="+  "'"+getLicenceNumber()+ "'";
//		  if  (getCheckNumber() == null || getCheckNumber() ==0) {
//			 	vf_supr = ""  ;
// 
//		  }
//		  else
//		  	vf_supr= " and F_SUPERVISOR_CODE="+ getCheckNumber();
//
//		  if  (getStatusPayed() == null)
//		 	vf_fine_case= " ";
//		  else
//		  	vf_fine_case = " and F_FINE_CASE="+ getStatusPayed();
//		  
//		 parameter = vfine_no + ' ' + vfine_date + ' ' + vf_id_no + ' ' + vf_lic_no + ' ' + vf_supr   + ' ' + vf_fine_case;
//
//		 try {
//			FacesContext.getCurrentInstance().getExternalContext().redirect(dataAccessService.printDocument(repName, parameter, "cond"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		 return"";
//	}
	
	public String printt(){
		String condition = "";
		if (getFromNumber() != null && getFromNumber() != 0)
			condition +=" and FD.fine_no >= "+ getFromNumber();
		if (getToNumber() != null && getToNumber() != 0)
			condition +=" and FD.fine_no <= "+ getToNumber();
		if (getFromDate() != null && !getFromDate().trim().equals("")) 
			condition +=" and substr(M.fine_date,7,4)||substr(M.fine_date,4,2)||substr(M.fine_date,1,2)  >="+
					getFromDate().substring(6, 10) + getFromDate().substring(3, 5)+getFromDate().substring(0, 2);  
		if (getToDate() != null && !getToDate().trim().equals("")) 
			condition +=" and substr(M.fine_date,7,4)||substr(M.fine_date,4,2)||substr(M.fine_date,1,2)  <=" + 
					getToDate().substring(6, 10) + getToDate().substring(3, 5)+getToDate().substring(0, 2);
		if  (getIdentifierNumber() != null && getIdentifierNumber() != 0)
			condition +=" and M.F_ID_NO=" +  "'"+ getIdentifierNumber() + "'";
		
		if  (getLicenceNumber() != null && !getLicenceNumber().trim().equals("") )
			condition +=" and M.F_LICENCE_NO="+  "'"+getLicenceNumber()+ "'";
		if  (getCheckNumber() != null && getCheckNumber() !=0) 
			condition +=" and M.F_SUPERVISOR_CODE="+ getCheckNumber();
		if  (getStatusPayed() != null)
			condition +=" and M.F_FINE_CASE="+ getStatusPayed();
		String reportName = "/reports/penalities_list.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("now", HijriCalendarUtil.findCurrentHijriWithTimeStamp());
		parameters.put("condition", condition);
		parameters.put("LOGO_DIR", FacesContext.getCurrentInstance().getExternalContext().getRealPath(Utils.loadMessagesFromFile("report.logo")));
		Utils.printPdfReport(reportName, parameters);			
		return"";
	}
	
	public String getUrl() {
		  return "";
	}
	public void setUrl(String url) {
	}

	public ArcPeople getListEmployer() {
		return listEmployer;
	}

	public void setListEmployer(ArcPeople listEmployer) {
		this.listEmployer = listEmployer;
	}
	
}

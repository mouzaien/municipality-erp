package com.bkeryah.managedBean;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.bkeryah.service.IDataAccessService;

import utilities.Utils;

@ManagedBean
@ViewScoped
public class NationalIdBean {

	@ManagedProperty(value="#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private String nationalId;
	private String name;
	private String nameStr;
	private String requestType = "1";
	private Integer userId;
	private String url;

	public void loadName(){
		long id = Long.parseLong(nationalId);
		name = dataAccessService.loadNameByNationalId(id);
//		byte ptext[];
//		try {
//			ptext = name.getBytes("UTF-8");
//			setNameStr(new String(ptext, "UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		   
	}
	/*public void printReportAction(){
		url = dataAccessService.printNationalIdDocument(nationalId, name, requestType);
		//urlReporter= "http://reportServerIp/reports/rwservlet?repsrv&report=d:\\archiving\\reports\\r247.rdf&P1="+name+"&P2="+nationalId+"&P3="+requestType+"&P4="+Utils.findCurrentUser().getUserId();

	}*/
	
	/**
	 * Print letter report
	 */
	public String printReportAction() {
		String reportName = "/reports/NationalPrint.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("national_id", nationalId);
		parameters.put("C_name", name);
		parameters.put("print_type", (requestType.equals("1"))?Utils.loadMessagesFromFile("new.local.licence"):Utils.loadMessagesFromFile("renew.local.licence"));
		parameters.put("current_user", Utils.findCurrentUser().getUserId());
		Utils.printPdfReport(reportName, parameters);
		return "";
	}
	
	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}
	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}
	public String getNationalId() {
		return nationalId;
	}
	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public Integer getUserId() {
		return Utils.findCurrentUser().getUserId();
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNameStr() {
		return nameStr;
	}

	public void setNameStr(String nameStr) {
		this.nameStr = nameStr;
	}

	public String getUrl() {
		return dataAccessService.printNationalIdDocument(nationalId, name, requestType);
	}

	public void setUrl(String url) {
		this.url = url;
	}
	

}
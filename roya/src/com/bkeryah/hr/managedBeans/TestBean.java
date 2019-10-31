package com.bkeryah.hr.managedBeans;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean(name="testt")
@ViewScoped
public class TestBean {
	
	
	
	private String xEntry;
	private String xExit;
	private String entryOffice;
	private String exiteOffice;
	private String openEntry;
	private String closeEntry;
	private String openExit;
	private String closeExit;
	private String valStartDelay;
	
	
	String time1 = "16:00:00";
	String time2 = "19:00:00";


	
	
public void init() {
		
		//calcdelay("08:10", "14:20", "08:00", "14:30", "07:30", "14:00", "15:00", "40");
	}




public void showDelay() {
	try {
		//calcdelay(xEntry,xExit,entryOffice,exiteOffice,openEntry,closeEntry,openExit,closeExit, valStartDelay);
	} catch (Exception e) {
		e.printStackTrace();
		MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
	}
//	return "";
}
	
	public void test(ActionEvent event){
		

	}

	public String getxEntry() {
		return xEntry;
	}

	public void setxEntry(String xEntry) {
		this.xEntry = xEntry;
	}

	public String getxExit() {
		return xExit;
	}

	public void setxExit(String xExit) {
		this.xExit = xExit;
	}

	public String getEntryOffice() {
		return entryOffice;
	}

	public void setEntryOffice(String entryOffice) {
		this.entryOffice = entryOffice;
	}

	public String getExiteOffice() {
		return exiteOffice;
	}

	public void setExiteOffice(String exiteOffice) {
		this.exiteOffice = exiteOffice;
	}

	public String getOpenEntry() {
		return openEntry;
	}

	public void setOpenEntry(String openEntry) {
		this.openEntry = openEntry;
	}

	public String getCloseEntry() {
		return closeEntry;
	}

	public void setCloseEntry(String closeEntry) {
		this.closeEntry = closeEntry;
	}

	public String getOpenExit() {
		return openExit;
	}

	public void setOpenExit(String openExit) {
		this.openExit = openExit;
	}

	public String getValStartDelay() {
		return valStartDelay;
	}

	public void setValStartDelay(String valStartDelay) {
		this.valStartDelay = valStartDelay;
	}
	public String getCloseExit() {
		return closeExit;
	}
	public void setCloseExit(String closeExit) {
		this.closeExit = closeExit;
	}

}

package com.bkeryah.testssss;

import java.io.IOException;

import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.bkeryah.managedBean.LoginBean;

@ManagedBean(name = "logTest")
@SessionScoped
public class LogTest {
	
	@ManagedProperty(value="#{loginBean}")
	private LoginBean loginB;

	
//	@ManagedProperty(value="#{sessionbean}")
//	private Sessionbean sessionbean;
	
	
	
//	public Sessionbean getSessionbean() {
//		return sessionbean;
//	}
//
//	public void setSessionbean(Sessionbean sessionbean) {
//		this.sessionbean = sessionbean;
//	}

	public LoginBean getLoginB() {
		return loginB;
	}

	public void setLoginB(LoginBean loginB) { 
		this.loginB = loginB;
	}

	public LogTest() {

	}

	public String outPut() throws IOException {


		loginB.login();
		return "mails";

	}

	public String x() {



		try {
			loginB.login();
			
			//Ssessionbean.outPut();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "mails";
	}

	@PreDestroy
	public void y() {

	}
}

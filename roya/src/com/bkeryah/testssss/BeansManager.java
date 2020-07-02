package com.bkeryah.testssss;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class BeansManager {

	static ApplicationContext context;

	static {
		String path = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/WEB-INF/applicationContext.xml");
		context = new FileSystemXmlApplicationContext(path);
	}

	// final static ApplicationContext context = new
	// FileSystemXmlApplicationContext
	// ("D:/BK_TEST/WebContent/WEB-INF/spring-Customer.xml");

	public synchronized static Object getBean(String name) {
		Object Mybean = context.getBean(name);
		return Mybean;
	}

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.bkeryah.licences.SpringContext;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.testssss.BeansManager;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 *
 * @author Ibrahim Darwiesh 10/10/2014
 */
public class DataBaseConnectionClass {

	private static ApplicationContext applicationContext;
	private static com.mchange.v2.c3p0.ComboPooledDataSource dataSource;
	static {
		applicationContext = SpringContext.getApplicationContext();

	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			dataSource = (ComboPooledDataSource) applicationContext.getBean("DataSource");
			conn = dataSource.getConnection();

		} catch (Exception e) {
			System.err.println("CRITICAL :::: ERROR GETTING DATABASE CONNECTION" + e.getMessage());

		}
		return conn;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		DataBaseConnectionClass.applicationContext = applicationContext;
	}
}

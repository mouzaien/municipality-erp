package com.bkeryah.managedBean;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import utilities.Utils;

@ManagedBean
@ApplicationScoped
public class ApplicationBean implements Serializable {

	public String toAr(String text){
		return Utils.convertTextWithArNumric(text);
	}
}

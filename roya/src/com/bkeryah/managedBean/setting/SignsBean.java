package com.bkeryah.managedBean.setting;
/**
 * @author thapet
 *
 */

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.bkeryah.entities.HrsSigns;
import com.bkeryah.service.IDataAccessService;

@ManagedBean
@ViewScoped
public class SignsBean {
	private static Logger logger = Logger.getLogger(AttachmentsBean.class);
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private HrsSigns signs = new HrsSigns();
	private List<HrsSigns> signsList = new ArrayList<HrsSigns>();
	private List<HrsSigns> filteredsignsList;

	@PostConstruct
	public void init() {
		signsList = dataAccessService.findAllSigns();
	}

	public List<HrsSigns> getSignsList() {
		return signsList;
	}

	public void setSignsList(List<HrsSigns> signsList) {
		this.signsList = signsList;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public HrsSigns getSigns() {
		return signs;
	}

	public void setSigns(HrsSigns signs) {
		this.signs = signs;
	}

	public List<HrsSigns> getFilteredsignsList() {
		return filteredsignsList;
	}

	public void setFilteredsignsList(List<HrsSigns> filteredsignsList) {
		this.filteredsignsList = filteredsignsList;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		SignsBean.logger = logger;
	}

}

package com.bkeryah.managedBean.setting;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.bkeryah.fng.entities.AutorizationSettings;
import com.bkeryah.service.IDataAccessService;

@ManagedBean
@ViewScoped
public class AutorizationSettingsBean {

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private AutorizationSettings autorization = new AutorizationSettings();
	private boolean updateMode;

	private List<Integer> hours = new ArrayList<Integer>();
	private List<Integer> minutes = new ArrayList<Integer>();

	@PostConstruct
	public void init() {
		fullNotFingerOutTime();
		autorization = dataAccessService.loadAutorization();
		if (autorization != null) {
			updateMode = true;
		} else {
			autorization = new AutorizationSettings();
			updateMode = false;
		}
	}

	public void save() {
		if (updateMode) {
			dataAccessService.updateObject(autorization);
			System.out.println("Updated");
		} else {
			dataAccessService.save(autorization);
			System.out.println("Saved");
		}
	}

	public void fullNotFingerOutTime() {
		for (int i = 0; i < 7; i++) {
			hours.add(i, i + 1);
		}
		for (int i = 0; i < 59; i++) {
			minutes.add(i, i + 1);
		}
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public AutorizationSettings getAutorization() {
		return autorization;
	}

	public void setAutorization(AutorizationSettings autorization) {
		this.autorization = autorization;
	}

	public boolean isUpdateMode() {
		return updateMode;
	}

	public void setUpdateMode(boolean updateMode) {
		this.updateMode = updateMode;
	}

	public AutorizationSettings getAuthoration() {
		return autorization;
	}

	public void setAuthoration(AutorizationSettings authoration) {
		this.autorization = authoration;
	}

	public List<Integer> getHours() {
		return hours;
	}

	public void setHours(List<Integer> hours) {
		this.hours = hours;
	}

	public List<Integer> getMinutes() {
		return minutes;
	}

	public void setMinutes(List<Integer> minutes) {
		this.minutes = minutes;
	}

}
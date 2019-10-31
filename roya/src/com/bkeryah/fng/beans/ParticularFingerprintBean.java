package com.bkeryah.fng.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.bkeryah.fng.entities.FngCheckInOut;
import com.bkeryah.service.IDataAccessService;

import utilities.HijriCalendarUtil;
import utilities.MsgEntry;
import utilities.Utils;

@ManagedBean
@ViewScoped
public class ParticularFingerprintBean {

	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private FngCheckInOut check = new FngCheckInOut();
	private String checkDate;
	private Integer seconds;
	private Integer minutes;
	private Integer hours;
	private boolean afternoon;
	// private Date selectedDate;

	private boolean higriMode;
	private String selectedDate;
	private Date selectedDate_G;

	public void cancel() {
		reset();
	}

	private void reset() {
		check = new FngCheckInOut();
		checkDate = null;
		seconds = null;
		minutes = null;
		hours = null;
	}

	public void saveFingerprint() {
		try {
			// checkDate =
			// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
			// .get("startDate");
			// check.setD1(HijriCalendarUtil.ConvertHijriTogeorgianDate(checkDate));
			// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			// String grigDate = sdf.format(selectedDate);\
			if (higriMode) {
				selectedDate_G = Utils.convertHDateToGDate(selectedDate);
			} else {

				selectedDate = Utils.grigDatesConvert(selectedDate_G);
			}

			String dateToSort = Utils.convertDateToString(selectedDate_G);
			check.setD1(dateToSort);
			check.setD2(concatTime());
			check.setD3(afternoon ? Utils.loadMessagesFromFile("afternoon") : Utils.loadMessagesFromFile("morning"));
			check.setChecktime(check.getD1() + " " + check.getD2() + " " + check.getD3());
			check.setCby("" + Utils.findCurrentUser().getUserId());
			check.setChecked(1);
			check.setCin(new Date());
			check.setModetyp("m");
			check.setPic("xx");
			dataAccessService.save(check);
			reset();
			MsgEntry.addAcceptFlashInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			e.printStackTrace();
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
		}
	}

	private String concatTime() {
		return Utils.formatTwoDigits(hours) + ":" + Utils.formatTwoDigits(minutes) + ":"
				+ Utils.formatTwoDigits(seconds);
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public FngCheckInOut getCheck() {
		return check;
	}

	public void setCheck(FngCheckInOut check) {
		this.check = check;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public Integer getSeconds() {
		return seconds;
	}

	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public boolean isAfternoon() {
		return afternoon;
	}

	public void setAfternoon(boolean afternoon) {
		this.afternoon = afternoon;
	}

	public boolean isHigriMode() {
		return higriMode;
	}

	public void setHigriMode(boolean higriMode) {
		this.higriMode = higriMode;
	}

	public String getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}

	public Date getSelectedDate_G() {
		return selectedDate_G;
	}

	public void setSelectedDate_G(Date selectedDate_G) {
		this.selectedDate_G = selectedDate_G;
	}

}

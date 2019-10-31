package com.bkeryah.fng.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "AUTORIZATION_SETTINGS")
public class AutorizationSettings {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer Id;
	@Column(name = "AUTOR_MONTH_HOURS")
	private Integer autorMonthHours;
	@Column(name = "AUTOR_DAY_HOURS_MIN")
	private Integer autorDayHoursMin;
	@Column(name = "AUTOR_DAY_HOURS_MAX")
	private Integer autorDayHoursMax;
	@Column(name = "AUTOR_PER_DAY")
	private Integer autorPerDay;
	@Column(name = "NOT_FINGER_OUT")
	private Date notFingerOut;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getAutorDayHoursMin() {
		return autorDayHoursMin;
	}

	public void setAutorDayHoursMin(Integer autorDayHoursMin) {
		this.autorDayHoursMin = autorDayHoursMin;
	}

	public Integer getAutorDayHoursMax() {
		return autorDayHoursMax;
	}

	public void setAutorDayHoursMax(Integer autorDayHoursMax) {
		this.autorDayHoursMax = autorDayHoursMax;
	}

	public Integer getAutorPerDay() {
		return autorPerDay;
	}

	public void setAutorPerDay(Integer autorPerDay) {
		this.autorPerDay = autorPerDay;
	}

	public Date getNotFingerOut() {
		return notFingerOut;
	}

	public void setNotFingerOut(Date notFingerOut) {
		this.notFingerOut = notFingerOut;
	}

	public Integer getAutorMonthHours() {
		return autorMonthHours;
	}

	public void setAutorMonthHours(Integer autorMonthHours) {
		this.autorMonthHours = autorMonthHours;
	}
}
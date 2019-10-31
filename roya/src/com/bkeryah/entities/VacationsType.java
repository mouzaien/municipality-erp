package com.bkeryah.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "sys052")
public class VacationsType implements Serializable {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NAME")
	private String vacation_Name;
	@Column(name = "PERIOD")
	private String period_Vacation;
	@Column(name = "TRANSF")
	private String vacation_Trans;
	@Column(name = "NOTES")
	private String vacation_Note;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getVacation_Name() {
		return vacation_Name;
	}
	public void setVacation_Name(String vacation_Name) {
		this.vacation_Name = vacation_Name;
	}
	public String getPeriod_Vacation() {
		return period_Vacation;
	}
	public void setPeriod_Vacation(String period_Vacation) {
		this.period_Vacation = period_Vacation;
	}
	public String getVacation_Trans() {
		return vacation_Trans;
	}
	public void setVacation_Trans(String vacation_Trans) {
		this.vacation_Trans = vacation_Trans;
	}
	public String getVacation_Note() {
		return vacation_Note;
	}
	public void setVacation_Note(String vacation_Note) {
		this.vacation_Note = vacation_Note;
	}
	
	
}

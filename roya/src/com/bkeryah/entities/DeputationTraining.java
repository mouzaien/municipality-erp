package com.bkeryah.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ManagedProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bkeryah.service.IDataAccessService;



@Entity
@Table(name = "deputation_training")
public class DeputationTraining implements Serializable {

	/**
	 * 
	 */
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "no_of_days", nullable = false)
	private Integer dayscount;
	@Column(name = "mission", nullable = false)
	private String mission;
	@Column(name = "dep_type", nullable = false)
	private Type type;
	@Column(name = "transport_allowance",nullable = true)
	private Integer trans_allowance;
	@Column(name = "training_allowance", nullable = true)
	private Integer train_allowance;
	@Column(name = "start_date", nullable = false)
	private Date st_date;
	@Column(name = "end_date",nullable = false)
	private Date end_date;
	@Column(name = "higri_start_date", nullable = false)
	private String hij_st_date;
	@Column(name = "higri_end_date",nullable = false)
	private String hij_end_date;
	@Column(name = "employee_no",nullable = false)
	private Integer emp_no;
	@Column(name = "employee_type")
	private Integer emp_type;
	@Column(name = " master_empno")
	private Integer master_empno;
	@Transient
	private String type_of_mission;
	@Column(name="op_month")
	private Integer month;
	@Column(name="op_year")
	private Integer year;
	
	@Formula("(select w.NAME from sys037 w where w.id = employee_type)")
	private String category_name;
	
	
//	@ManyToOne
//	@JoinColumn(name = "employee_no", referencedColumnName = "USER_ID", insertable = false, updatable = false)
//	ArcUsers dep_train_employee;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDayscount() {
		return dayscount;
	}
	public void setDayscount(Integer dayscount) {
		this.dayscount = dayscount;
	}
	public String getMission() {
		return mission;
	}
	public void setMission(String mission) {
		this.mission = mission;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Integer getTrans_allowance() {
		return trans_allowance;
	}
	public void setTrans_allowance(Integer trans_allowance) {
		this.trans_allowance = trans_allowance;
	}
	public Integer getTrain_allowance() {
		return train_allowance;
	}
	public void setTrain_allowance(Integer train_allowance) {
		this.train_allowance = train_allowance;
	}
	public Date getSt_date() {
		return st_date;
	}
	public void setSt_date(Date st_date) {
		this.st_date = st_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public Integer getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(Integer emp_no) {
		this.emp_no = emp_no;
	}
//	public ArcUsers getDep_train_employee() {
//		return dep_train_employee;
//	}
//	public void setDep_train_employee(ArcUsers dep_train_employee) {
//		this.dep_train_employee = dep_train_employee;
//	}
	public String getHij_st_date() {
		return hij_st_date;
	}
	public void setHij_st_date(String hij_st_date) {
		this.hij_st_date = hij_st_date;
	}
	public String getHij_end_date() {
		return hij_end_date;
	}
	public void setHij_end_date(String hij_end_date) {
		this.hij_end_date = hij_end_date;
	}
	public String getType_of_mission() {
		if(type==type.training)type_of_mission="تدريب";
		if(type==type.deputation)type_of_mission="انتداب";
		return type_of_mission;
	}
	public void setType_of_mission(String type_of_mission) {
		this.type_of_mission = type_of_mission;
	}
	

	public Integer getEmp_type() {
		return emp_type;
	}
	public void setEmp_type(Integer emp_type) {
		this.emp_type = emp_type;
	}
	public String getCategory_name() {
		
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public Integer getMaster_empno() {
		return master_empno;
	}
	public void setMaster_empno(Integer master_empno) {
		this.master_empno = master_empno;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	
}
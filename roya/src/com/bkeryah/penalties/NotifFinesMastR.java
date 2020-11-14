package com.bkeryah.penalties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "NOTIF_FINES_MASTR")
public class NotifFinesMastR implements Serializable {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "OBSERVATION")
	private String observation;
	
	@Column(name = "SUBERVISOR_ID")
	private Integer supervisorId;
	
	@Column(name = "LIC_ID")
	private Integer licenceId;
	
	@Column(name = "NOTIF_TIME")
	private String notifTime;
	
	
	@Column(name = "NOTIF_DATE")
	private Date notifDate;

	@Column(name = "EVALUATION")
	private Integer evaluation;
	
	@Column(name = "RECORD_PENALTY_Y_N")
	private String penaltyIsRecoreded;
	
	@OneToMany(mappedBy = "notifFinesMaster", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<NotifFinesDetL> notifFinesDetailsList;
	
	@Formula("(select u.LIC_NO from lic_trd_master_file u where u.ID =LIC_ID)")
	private String licNo;
	
	@Formula("(select u.AQR_OWNER_NAME from lic_trd_master_file u where u.ID =LIC_ID)")
	private String licOwner;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Integer getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Integer supervisorId) {
		this.supervisorId = supervisorId;
	}

	public String getNotifTime() {
		return notifTime;
	}

	public void setNotifTime(String notifTime) {
		this.notifTime = notifTime;
	}

	public Date getNotifDate() {
		return notifDate;
	}

	public void setNotifDate(Date notifDate) {
		this.notifDate = notifDate;
	}

	public Integer getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Integer evaluation) {
		this.evaluation = evaluation;
	}

	public Integer getLicenceId() {
		return licenceId;
	}

	public void setLicenceId(Integer licenceId) {
		this.licenceId = licenceId;
	}

	public List<NotifFinesDetL> getNotifFinesDetailsList() {
		return notifFinesDetailsList;
	}

	public void setNotifFinesDetailsList(List<NotifFinesDetL> notifFinesDetailsList) {
		this.notifFinesDetailsList = notifFinesDetailsList;
	}

	public String getLicNo() {
		return licNo;
	}

	public void setLicNo(String licNo) {
		this.licNo = licNo;
	}

	public String getLicOwner() {
		return licOwner;
	}

	public void setLicOwner(String licOwner) {
		this.licOwner = licOwner;
	}

	public String getPenaltyIsRecoreded() {
		return penaltyIsRecoreded;
	}

	public void setPenaltyIsRecoreded(String penaltyIsRecoreded) {
		this.penaltyIsRecoreded = penaltyIsRecoreded;
	}
	

}
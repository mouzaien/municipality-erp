package com.bkeryah.penalties;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "NOTIF_FINES_DTLS")
public class NotifFinesDetL implements Serializable {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;

	@Column(name = "MASTER_ID")
	private Integer masterId;

	@Column(name = "OBSERVATION")
	private String observation;

	@Column(name = "FINE_ROLE")
	private Integer fineRole;

	@Column(name = "NOT_URGENT")
	private Integer notUrgent;

	@Column(name = "URGENT")
	private Integer urgent;

	@Column(name = "COMPLETED")
	private Integer complete;

	@Column(name = "NOT_COMPLETED")
	private Integer notComplete;
	
	@Column(name = "DUPLICATED")
	private Integer duplicated;

	@Column(name = "UPDOWN")
	private Integer upDown;
	
	@Column(name = "SETUP_ID")
	private Integer fineSetupId;
	
	@ManyToOne
	@JoinColumn(name = "MASTER_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private NotifFinesMastR notifFinesMaster;
	
	@Formula("(select u.TYPES from LIC_FINES_ROLES u where u.ID =FINE_ROLE)")
	private String fineRoleType;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMasterId() {
		return masterId;
	}

	public void setMasterId(Integer masterId) {
		this.masterId = masterId;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Integer getFineRole() {
		return fineRole;
	}

	public void setFineRole(Integer fineRole) {
		this.fineRole = fineRole;
	}

	public Integer getNotUrgent() {
		return notUrgent;
	}

	public void setNotUrgent(Integer notUrgent) {
		this.notUrgent = notUrgent;
	}

	public Integer getUrgent() {
		return urgent;
	}

	public void setUrgent(Integer urgent) {
		this.urgent = urgent;
	}

	public Integer getComplete() {
		return complete;
	}

	public void setComplete(Integer complete) {
		this.complete = complete;
	}

	public Integer getNotComplete() {
		return notComplete;
	}

	public void setNotComplete(Integer notComplete) {
		this.notComplete = notComplete;
	}

	public NotifFinesMastR getNotifFinesMaster() {
		return notifFinesMaster;
	}

	public void setNotifFinesMaster(NotifFinesMastR notifFinesMaster) {
		this.notifFinesMaster = notifFinesMaster;
	}

	public String getFineRoleType() {
		return fineRoleType;
	}

	public void setFineRoleType(String fineRoleType) {
		this.fineRoleType = fineRoleType;
	}

	public Integer getDuplicated() {
		return duplicated;
	}

	public void setDuplicated(Integer duplicated) {
		this.duplicated = duplicated;
	}

	public Integer getUpDown() {
		return upDown;
	}

	public void setUpDown(Integer upDown) {
		this.upDown = upDown;
	}

	public Integer getFineSetupId() {
		return fineSetupId;
	}

	public void setFineSetupId(Integer fineSetupId) {
		this.fineSetupId = fineSetupId;
	}

}

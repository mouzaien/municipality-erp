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
@Table(name = "NOTIF_FINES_MASTER")
public class NotifFinesMaster implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NOTIF_ID")
	private Integer notifId;
	@Column(name = "FINE_ID")
	private Integer fineId;
	@Column(name = "NOTIF_DATE")
	private Date notifDate;
	@Column(name = "SUPERVISOR_EMPNO")
	private Integer supervisorEmpNo;
	@Column(name = "OBSERVATION")
	private String observation;
	@Column(name = "LIC_NO")
	private String licenceNo;
	// 0:new, 1: saved penality 2: cancel
	@Column(name = "STATUS")
	private Integer status;
	@OneToMany(mappedBy = "notifFinesMaster", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<NotifFinesDetails> notifFinesDetailsList;
	@Formula("(select d.DEPT_NAME from WRK_DEPT d, ARC_USERS us where US.EMPNO=SUPERVISOR_EMPNO and US.DEPT_ID = d.id)")
	private String deptName;
	@Formula("(select d.trd_name from LIC_TRD_MASTER_FILE d where d.lic_no = LIC_NO and d.IS_ACTIVE_Y_N='Y' and d.LIC_STS=3)")
	private String fName;
	@Formula("(select US.EMPNAME from ARC_USERS US where US.EMPNO=SUPERVISOR_EMPNO)")
	private String supervisorName;

	public Date getNotifDate() {
		return notifDate;
	}

	public void setNotifDate(Date notifDate) {
		this.notifDate = notifDate;
	}

	public Integer getSupervisorEmpNo() {
		return supervisorEmpNo;
	}

	public void setSupervisorEmpNo(Integer supervisorEmpNo) {
		this.supervisorEmpNo = supervisorEmpNo;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getLicenceNo() {
		return licenceNo;
	}

	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}

	public Integer getNotifId() {
		return notifId;
	}

	public void setNotifId(Integer notifId) {
		this.notifId = notifId;
	}

	public List<NotifFinesDetails> getNotifFinesDetailsList() {
		return notifFinesDetailsList;
	}

	public void setNotifFinesDetailsList(List<NotifFinesDetails> notifFinesDetailsList) {
		this.notifFinesDetailsList = notifFinesDetailsList;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFineId() {
		return fineId;
	}

	public void setFineId(Integer fineId) {
		this.fineId = fineId;
	}
}
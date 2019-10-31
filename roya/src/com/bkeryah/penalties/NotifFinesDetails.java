package com.bkeryah.penalties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "NOTIF_FINES_DETAILS")
public class NotifFinesDetails {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NOTIF_DETAIL_ID")
	private Integer notifDetaild;
	@Column(name = "NOTIF_ID")
	private Integer notifId;
	@Column(name = "ORDER_ID")
	private Integer orderId;
	@Column(name = "EVALUATION")
	private Integer evaluation;
	@Column(name = "OBSERVATION")
	private String observation;
//	@Column(name = "FINE_CODE")
//	private Integer fineCode;
	@ManyToOne
	@JoinColumn(name = "NOTIF_ID", referencedColumnName = "NOTIF_ID", insertable = false, updatable = false)
	private NotifFinesMaster notifFinesMaster;
	@ManyToOne
	@JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDERITEM", insertable = false, updatable = false)
	private FineSection fineSection;
	@Transient
	private Integer fineCount;
	@Transient
	private Integer fineValue;
	@Transient
	private double fineTotalValue;
	@Transient
	private String evaluationValue;

	public Integer getNotifDetaild() {
		return notifDetaild;
	}

	public void setNotifDetaild(Integer notifDetaild) {
		this.notifDetaild = notifDetaild;
	}

	public Integer getNotifId() {
		return notifId;
	}

	public void setNotifId(Integer notifId) {
		this.notifId = notifId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Integer evaluation) {
		this.evaluation = evaluation;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public NotifFinesMaster getNotifFinesMaster() {
		return notifFinesMaster;
	}

	public void setNotifFinesMaster(NotifFinesMaster notifFinesMaster) {
		this.notifFinesMaster = notifFinesMaster;
	}

	public FineSection getFineSection() {
		return fineSection;
	}

	public void setFineSection(FineSection fineSection) {
		this.fineSection = fineSection;
	}

	public double getFineTotalValue() {
		if (fineValue != null && fineCount != null)
			return fineValue * fineCount;
		return 0;
	}

	public void setFineTotalValue(double fineTotalValue) {
		this.fineTotalValue = fineTotalValue;
	}

	public Integer getFineCount() {
		return fineCount;
	}

	public void setFineCount(Integer fineCount) {
		this.fineCount = fineCount;
	}

	public Integer getFineValue() {
		return fineValue;
	}

	public void setFineValue(Integer fineValue) {
		this.fineValue = fineValue;
	}

	public String getEvaluationValue() {
		return evaluation == 3 ? "حرج" : "غير حرج";
	}

	public void setEvaluationValue(String evaluationValue) {
		this.evaluationValue = evaluationValue;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	public Integer getFineCode() {
//		return fineCode;
//	}
//
//	public void setFineCode(Integer fineCode) {
//		this.fineCode = fineCode;
//	}

}

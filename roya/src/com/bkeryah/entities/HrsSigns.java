package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "HRS_SIGNS")
public class HrsSigns implements Serializable {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "sign_id")
	private Integer id;
	@Column(name = "doc_id")
	private Integer docId;
	@Column(name = "arc_record_id")
	private Integer arcRecordId;
	@Column(name = "sign_is_visible")
	private boolean visible;
	@Column(name = "sign_user_id")
	private Integer userId;
	@Column(name = "sign_date")
	private String date;
	@Column(name = "SIGN_TIME")
	private String signTime;
	@Column(name = "SIGN_NOTE")
	private String signNote;
	@Column(name = "SIGN_STEP_ID")
	private Integer stepId;
	@Column(name = "SIGN_JOB_NAME")
	private String jobName;
	@Column(name = "MODEL_ID")
	private Integer modelId;
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "arc_record_id", referencedColumnName = "ID", insertable = false, updatable = false)
	private ArcRecords arcRecords;
	@NotFound(action = NotFoundAction.IGNORE) 
	@ManyToOne
	@JoinColumn(name = "doc_id", referencedColumnName = "ID", insertable = false, updatable = false)
	private HrsUserAbsent hrsUserAbsent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDocId() {
		return docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	public Integer getArcRecordId() {
		return arcRecordId;
	}

	public void setArcRecordId(Integer arcRecordId) {
		this.arcRecordId = arcRecordId;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getSignNote() {
		return signNote;
	}

	public void setSignNote(String signNote) {
		this.signNote = signNote;
	}

	public Integer getStepId() {
		return stepId;
	}

	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public ArcRecords getArcRecords() {
		return arcRecords;
	}

	public void setArcRecords(ArcRecords arcRecords) {
		this.arcRecords = arcRecords;
	}

	public HrsUserAbsent getHrsUserAbsent() {
		return hrsUserAbsent;
	}

	public void setHrsUserAbsent(HrsUserAbsent hrsUserAbsent) {
		this.hrsUserAbsent = hrsUserAbsent;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

}

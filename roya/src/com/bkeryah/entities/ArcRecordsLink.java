package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

	@Entity
	@Table(name = "ARC_RECORD_LINK")
	public class ArcRecordsLink {

		@Id
		@GenericGenerator(name = "generator", strategy = "increment")
		@GeneratedValue(generator = "generator")
		@Column(name = "ID")
		private int id;

		@Column(name = "ARC_RECORD_ID_PAERNT")
		private Integer arcRecordParentId;

		@Column(name = "ARC_RECORD_ID_CHILD")
		private Integer arcRrecordChildId;

		@Column(name = "MODEL_TYPE")
		private Integer modelType;
		
		@Column(name = "stepId")
		private Integer stepId;
		
		
	

//		@JoinColumn(name = "ARC_RECORD_ID_PAERNT", referencedColumnName = "ID", insertable = false, updatable = false)
//		private ArcRecords arcRecordParent;
//
//		@OneToOne
//		@JoinColumn(name = "ARC_RECORD_ID_CHILD", referencedColumnName = "ID", insertable = false, updatable = false)
//		private ArcRecords arcRecordChild;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

	
		

		

		public Integer getArcRecordParentId() {
			return arcRecordParentId;
		}

		public Integer getArcRrecordChildId() {
			return arcRrecordChildId;
		}

		public void setArcRecordParentId(Integer arcRecordParentId) {
			this.arcRecordParentId = arcRecordParentId;
		}

		public void setArcRrecordChildId(Integer arcRrecordChildId) {
			this.arcRrecordChildId = arcRrecordChildId;
		}

		public Integer getModelType() {
			return modelType;
		}

		public void setModelType(Integer modelType) {
			this.modelType = modelType;
		}

		public Integer getStepId() {
			return stepId;
		}

		public void setStepId(Integer stepId) {
			this.stepId = stepId;
		}

	

	}


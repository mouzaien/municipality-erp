package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "WRK_APPLICATION_EXTENSION")
public class WrkApplicationExtension implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private WrkApplicationId id;
	@Column(name = "USER_DEP_JOB", nullable = true)
	private Integer applicationUserDeptJob;
	@Column(name = "datenumber", nullable = true)
	private Integer dateNumber;
	public WrkApplicationExtension() {

	}
	public Integer getApplicationUserDeptJob() {
		return applicationUserDeptJob;
	}

	public void setApplicationUserDeptJob(Integer applicationUserDeptJob) {
		this.applicationUserDeptJob = applicationUserDeptJob;
	}

	public Integer getDateNumber() {
		return dateNumber;
	}

	public void setDateNumber(Integer dateNumber) {
		this.dateNumber = dateNumber;
	}

}

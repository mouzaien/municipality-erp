package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="XXX_HR_TITLES")
public class HRTitles {

	@Id
	@Column(name ="TITLE_CODE")
	private String titleCode;
		
	@Column(name ="TITLE_EN_NAME")
	private String titleEnName;
		
	@Column(name ="TITLE_AR_NAME")
	private String titleArName;

	public String getTitleCode() {
		return titleCode;
	}

	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}

	public String getTitleEnName() {
		return titleEnName;
	}

	public void setTitleEnName(String titleEnName) {
		this.titleEnName = titleEnName;
	}

	public String getTitleArName() {
		return titleArName;
	}

	public void setTitleArName(String titleArName) {
		this.titleArName = titleArName;
	}

}

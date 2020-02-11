package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="XXX_HR_CONTACTS")
public class HRContacts {


	@Id
	@Column(name ="CONTACT_CODE")
	private String contactCode;
		
	@Column(name ="CONTACT_EN_NAME")
	private String contactEnName;
		
	@Column(name ="CONTACT_AR_NAME")
	private String contactArName;

	public String getContactCode() {
		return contactCode;
	}

	public void setContactCode(String contactCode) {
		this.contactCode = contactCode;
	}

	public String getContactEnName() {
		return contactEnName;
	}

	public void setContactEnName(String contactEnName) {
		this.contactEnName = contactEnName;
	}

	public String getContactArName() {
		return contactArName;
	}

	public void setContactArName(String contactArName) {
		this.contactArName = contactArName;
	}
}

package com.bkeryah.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PAY_BANKS")
public class PayBank {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;

	@Column(name = "Name")
	private String name;

	@Column(name = "BANK_PO_BOX")
	private String postalCode;

	@Column(name = "BANK_ZIP_CODE")
	private String zipCode;

	@Column(name = "BANK_TEL_NO")
	private String telephone;

	@Column(name = "BANK_FAX_NO")
	private String faxNumber;

	@Column(name = "BANK_CITY")
	private String city;

	@Column(name = "BANK_URL")
	private String url;

	@Column(name = "BANK_SWIFT")
	private String swiftCode;

	@OneToMany(mappedBy = "bank", fetch = FetchType.LAZY)
	private List<HrsMasterFile> hrsMasterFiles;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSwiftCode() {
		return swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	// @ManyToOne(fetch =FetchType.LAZY)
	// @JoinColumn(name="CREATED_BY" ,referencedColumnName="USER_ID" ,insertable
	// = false,updatable = false)
	// private ArcUsers arcUser ;
	// @Column(name = "APP_TYPE")
	// private Integer applicationType;
	// @ManyToOne(fetch =FetchType.LAZY)
	// @JoinColumn(name="APP_TYPE" ,referencedColumnName="ID" ,insertable =
	// false,updatable = false)
	// private ArcApplicationType arcApplicationType ;
	// @Column(name = "INCOME_NO")
	// private String incomeNo;
	// @Column(name = "INCOME_H_DATE")
	// private String incomeHDate;
	// @Column(name = "FILES_NO")
	// private Integer filesNo;
	// @Column(name = "LETTER_FROM")
	// private Integer letterFrom;
	// @ManyToOne(fetch =FetchType.LAZY)
	// @JoinColumn(name="LETTER_FROM" ,referencedColumnName="ID" ,insertable =
	// false,updatable = false)
	// private WrkLetterFrom wrkLetterFrom ;

}

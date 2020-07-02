package com.bkeryah.penalties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "lic_trd_master_file")
public class LicTrdMasterFile {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "LIC_NO")
	private String licNo;
	@Column(name = "TRD_NAME")
	private String trdName;
	@Column(name = "LIC_DT_BGN_H")
	private String licBeginDate;
	@Column(name = "LIC_DT_END_H")
	private String licEndDate;
	@Column(name = "AQR_OWNER_NAME")
	private String licOwnerName;
	@Column(name = "MHL_ADDRS")
	private String licAdress;
	@Column(name = "APL_OWNER")
	private String aplOwner;
	@Column(name = "MHL_ID")
	private String mhlId;
	@Column(name = "phone_number")
	private String phoneNumber;
	@Column(name = "MARKET_SPACE")
	private String marketSpace;
	@Column(name = "longitude")
	private double longitude;
	@Column(name = "latitude")
	private double latitude;
	@Column(name = "street")
	private Integer street;
	@Column(name = "district")
	private Integer district;
	@Column(name = "IS_ACTIVE_Y_N")
	private char activationStatus;
	@Column(name = "MAIN_ACTV")
	private Integer activtyType;// نوع النشاط
	@Column(name = "VISIT_NAME")
	private Integer visitName;// اسم الزيارة
	@Column(name = "LIC_CITY")
	private Integer city;
	@Column(name = "BUILDING_NUMBER")
	private Integer buildingNumber;
	@Column(name = "EL_AMAANA")
	private Integer amaana;
	@Column(name = "LIC_SECTION")
	private Integer licSection;
	@Column(name = "LIC_DEPARTMENT")
	private Integer licDeparment;

	public String getMhlId() {
		return mhlId;
	}

	public void setMhlId(String mhlId) {
		this.mhlId = mhlId;
	}

	@Transient
	private String activity;// نوع النشاط

	public LicTrdMasterFile() {
		super();

	}

	public LicTrdMasterFile(Integer id, String lic_no, String trd_name) {
		this.id = id;
		this.licNo = lic_no;
		this.trdName = trd_name;
	}

	public LicTrdMasterFile(Integer id, String licNo, String trdName, String licEndDate, String licOwnerName,
			String licAdress) {
		super();
		this.id = id;
		this.licNo = licNo;
		this.trdName = trdName;
		this.licEndDate = licEndDate;
		this.licOwnerName = licOwnerName;
		this.licAdress = licAdress;
	}

	public String getLicEndDate() {
		return licEndDate;
	}

	public void setLicEndDate(String licEndDate) {
		this.licEndDate = licEndDate;
	}

	public String getLicOwnerName() {
		return licOwnerName;
	}

	public void setLicOwnerName(String licOwnerName) {
		this.licOwnerName = licOwnerName;
	}

	public String getLicAdress() {
		return licAdress;
	}

	public void setLicAdress(String licAdress) {
		this.licAdress = licAdress;
	}

	public String getLicNo() {
		return licNo;
	}

	public void setLicNo(String licNo) {
		this.licNo = licNo;
	}

	public String getTrdName() {
		return trdName;
	}

	public void setTrdName(String trdName) {
		this.trdName = trdName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.trdName;
	}

	@Override
	public boolean equals(Object obj) {
		return ((LicTrdMasterFile) obj).getId().equals(this.getId());
	}

	public String getLicBeginDate() {
		return licBeginDate;
	}

	public void setLicBeginDate(String licBeginDate) {
		this.licBeginDate = licBeginDate;
	}

	public String getAplOwner() {
		return aplOwner;
	}

	public void setAplOwner(String aplOwner) {
		this.aplOwner = aplOwner;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMarketSpace() {
		return marketSpace;
	}

	public void setMarketSpace(String marketSpace) {
		this.marketSpace = marketSpace;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public Integer getStreet() {
		return street;
	}

	public void setStreet(Integer street) {
		this.street = street;
	}

	public Integer getDistrict() {
		return district;
	}

	public void setDistrict(Integer district) {
		this.district = district;
	}

	public char getActivationStatus() {
		return activationStatus;
	}

	public void setActivationStatus(char activationStatus) {
		this.activationStatus = activationStatus;
	}

	public Integer getActivtyType() {
		return activtyType;
	}

	public void setActivtyType(Integer activtyType) {
		this.activtyType = activtyType;
	}

	public Integer getVisitName() {
		return visitName;
	}

	public void setVisitName(Integer visitName) {
		this.visitName = visitName;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getBuildingNumber() {
		return buildingNumber;
	}

	public void setBuildingNumber(Integer buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	public Integer getAmaana() {
		return amaana;
	}

	public void setAmaana(Integer amaana) {
		this.amaana = amaana;
	}

	public Integer getLicSection() {
		return licSection;
	}

	public void setLicSection(Integer licSection) {
		this.licSection = licSection;
	}

	public Integer getLicDeparment() {
		return licDeparment;
	}

	public void setLicDeparment(Integer licDeparment) {
		this.licDeparment = licDeparment;
	}

}

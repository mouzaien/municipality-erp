package com.bkeryah.penalties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "lic_trd_master_file")
public class LicTrdMasterFile {

	@Id
	@Column(name = "ID")
	private Integer id;
	@Column(name = "LIC_NO")
	private String licNo;
	@Column(name = "TRD_NAME")
	private String trdName;
    @Column(name="LIC_DT_BGN_H")
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
    public String getMhlId() {
		return mhlId;
	}

	public void setMhlId(String mhlId) {
		this.mhlId = mhlId;
	}
	@Transient
    private String activity;
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
}

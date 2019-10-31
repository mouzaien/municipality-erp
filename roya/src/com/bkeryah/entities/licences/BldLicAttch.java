package com.bkeryah.entities.licences;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

@Entity
@Table(name = "BLD_LIC_ATTACHMENT")
public class BldLicAttch {
	@Id
	@Column(name = "LIC_ATT_ID")
	private Integer licAttId;
	@Column(name = "LIC_ATT_H_DATE")
	private String licAttHDate;
	@Column(name = "LIC_ATT_G_DATE")
	private Date licAttGDate;
	@Column(name = "LIC_ATT_APL_OWNER")
	private String licAttAplOwner;
	@Column(name = "LIC_ATT_APL_MOBILE")
	private String licAttAplMobile;
	@Column(name = "LIC_ATT_TYP")
	private Integer licAttTyp;
	@Column(name = "LIC_ATT_BLD_LIC_NO")
	private String licAttBldLicNo;
	@Column(name = "LIC_ATT_BLD_LIC_DATE")
	private String licAttBldLicDate;
	@Column(name = "LIC_ATT_BLD_LIC_SOURCE")
	private String licAttBldLicSource;
	@Column(name = "LIC_ATT_SAK_NO")
	private String licAttSakNo;
	@Column(name = "LIC_ATT_SAK_DATE")
	private String licAttSakDate;
	@Column(name = "LIC_ATT_SAK_SOURCE")
	private String licAttSakSource;
	@Column(name = "LIC_ATT_ENGNEERING_OFFICE_ID")
	private Integer licAttEngneeringOfficeId;
	@Column(name = "LIC_ATT_NOTE")
	private String licAttNote;
	@Column(name = "LIC_ATT_NUMBER")
	private String licAttNumber;
	@Column(name = "LIC_ATT_ISSUE_DATE")
	private String licAttIssueDate;
	@Column(name = "LIC_ATT_END_DATE")
	private String licAttEndDate;
	@Column(name = "LIC_ATT_LAND_AREA")
	private Integer licAttLandArea;
	@Column(name = "LIC_ATT_ORGINIAL_BLD_AREA")
	private Integer licAttOrginialBldArea;
	@Column(name = "LIC_ATT_TOTAL_AREA_W_ATT")
	private Integer licAttTotalAreaWAtt;
	@Column(name = "LIC_ATT_HEIGHT")
	private Integer licAttHeight;
	@Column(name = "LIC_ATT_AREA")
	private Integer licAttArea;
	@Column(name = "LIC_ATT_BLD_TYP")
	private Integer licAttBldTyp;
	@Column(name = "LIC_ATT_BLD_USE")
	private Integer licAttBldUse;
	@Column(name = "LIC_ATT_OUTER_FINISH")
	private String licAttOuterFinish;
	@Column(name = "LIC_ATT_NORTH_REBOUND")
	private String licAttNorthRebound;
	@Column(name = "LIC_ATT_EAST_REBOUND")
	private String licAttEastRebound;
	@Column(name = "LIC_ATT_SOUTH_REBOUND")
	private String licAttSouthRebound;
	@Column(name = "LIC_ATT_WEST_REBOUND")
	private String licAttWestRebound;
	@Column(name = "LIC_ATT_GENERAL_KROKI")
	private byte[] licAttGeneralKroki;
	@Column(name = "LIC_ATT_LAND_KROKI")
	private byte[] licAttLandKroki;
	@Column(name = "LIC_ATT_WATCHER")
	private Integer licAttWatcher;
	@Column(name = "LIC_ATT_WROTE_BY")
	private Integer licAttWroteBy;
	@Column(name = "LIC_ATT_WROTE_IN")
	private String licAttWroteIn;
	@Column(name = "LIC_ATT_MARKED_BY")
	private Integer licAttMarkedBy;
	@Column(name = "LIC_ATT_MARKED_IN")
	private String licAttMarkedIn;
	@Column(name = "LIC_ATT_SIGNED_BY")
	private Integer licAttSignedBy;
	@Column(name = "LIC_ATT_SIGNED_IN")
	private String licAttSignedIn;
	@Column(name = "LIC_ATT_BILL_NUMBER")
	private String licAttBillNumber;
	@Column(name = "LIC_ATT_STS")
	private Integer licAttSts;
	@Column(name = "LIC_ATT_PLN")
	private String licAttPln;
	@Column(name = "LIC_ATT_ADDRESS")
	private String licAttAddress;
	@Column(name = "LIC_ATT_REQ_TYPE")
	private String licAttReqType;
	@OneToMany(mappedBy = "bldLicAttch", fetch=FetchType.EAGER)
	private List<BldLicPcs> pieces;
	@Transient
	private String piecesNumbers;

	public BldLicAttch() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BldLicAttch(Integer licAttId, String licAttHDate, Date licAttGDate, String licAttAplOwner,
			String licAttAplMobile, Integer licAttTyp, String licAttBldLicNo, String licAttBldLicDate,
			String licAttBldLicSource, String licAttSakNo, String licAttSakDate, String licAttSakSource,
			Integer licAttEngneeringOfficeId, String licAttNote, String licAttNumber, String licAttIssueDate,
			String licAttEndDate, Integer licAttLandArea, Integer licAttOrginialBldArea, Integer licAttTotalAreaWAtt,
			Integer licAttHeight, Integer licAttArea, Integer licAttBldTyp, Integer licAttBldUse,
			String licAttOuterFinish, String licAttNorthRebound, String licAttEastRebound, String licAttSouthRebound,
			String licAttWestRebound, byte[] licAttGeneralKroki, byte[] licAttLandKroki, Integer licAttWatcher,
			Integer licAttWroteBy, String licAttWroteIn, Integer licAttMarkedBy, String licAttMarkedIn,
			Integer licAttSignedBy, String licAttSignedIn, String licAttBillNumber, Integer licAttSts, String licAttPln,
			String licAttAddress, String licAttReqType) {
		super();
		this.licAttId = licAttId;
		this.licAttHDate = licAttHDate;
		this.licAttGDate = licAttGDate;
		this.licAttAplOwner = licAttAplOwner;
		this.licAttAplMobile = licAttAplMobile;
		this.licAttTyp = licAttTyp;
		this.licAttBldLicNo = licAttBldLicNo;
		this.licAttBldLicDate = licAttBldLicDate;
		this.licAttBldLicSource = licAttBldLicSource;
		this.licAttSakNo = licAttSakNo;
		this.licAttSakDate = licAttSakDate;
		this.licAttSakSource = licAttSakSource;
		this.licAttEngneeringOfficeId = licAttEngneeringOfficeId;
		this.licAttNote = licAttNote;
		this.licAttNumber = licAttNumber;
		this.licAttIssueDate = licAttIssueDate;
		this.licAttEndDate = licAttEndDate;
		this.licAttLandArea = licAttLandArea;
		this.licAttOrginialBldArea = licAttOrginialBldArea;
		this.licAttTotalAreaWAtt = licAttTotalAreaWAtt;
		this.licAttHeight = licAttHeight;
		this.licAttArea = licAttArea;
		this.licAttBldTyp = licAttBldTyp;
		this.licAttBldUse = licAttBldUse;
		this.licAttOuterFinish = licAttOuterFinish;
		this.licAttNorthRebound = licAttNorthRebound;
		this.licAttEastRebound = licAttEastRebound;
		this.licAttSouthRebound = licAttSouthRebound;
		this.licAttWestRebound = licAttWestRebound;
		this.licAttGeneralKroki = licAttGeneralKroki;
		this.licAttLandKroki = licAttLandKroki;
		this.licAttWatcher = licAttWatcher;
		this.licAttWroteBy = licAttWroteBy;
		this.licAttWroteIn = licAttWroteIn;
		this.licAttMarkedBy = licAttMarkedBy;
		this.licAttMarkedIn = licAttMarkedIn;
		this.licAttSignedBy = licAttSignedBy;
		this.licAttSignedIn = licAttSignedIn;
		this.licAttBillNumber = licAttBillNumber;
		this.licAttSts = licAttSts;
		this.licAttPln = licAttPln;
		this.licAttAddress = licAttAddress;
		this.licAttReqType = licAttReqType;
	}

	public Integer getLicAttId() {
		return licAttId;
	}

	public void setLicAttId(Integer licAttId) {
		this.licAttId = licAttId;
	}

	public String getLicAttHDate() {
		return licAttHDate;
	}

	public void setLicAttHDate(String licAttHDate) {
		this.licAttHDate = licAttHDate;
	}

	public Date getLicAttGDate() {
		return licAttGDate;
	}

	public void setLicAttGDate(Date licAttGDate) {
		this.licAttGDate = licAttGDate;
	}

	public String getLicAttAplOwner() {
		return licAttAplOwner;
	}

	public void setLicAttAplOwner(String licAttAplOwner) {
		this.licAttAplOwner = licAttAplOwner;
	}

	public String getLicAttAplMobile() {
		return licAttAplMobile;
	}

	public void setLicAttAplMobile(String licAttAplMobile) {
		this.licAttAplMobile = licAttAplMobile;
	}

	public Integer getLicAttTyp() {
		return licAttTyp;
	}

	public void setLicAttTyp(Integer licAttTyp) {
		this.licAttTyp = licAttTyp;
	}

	public String getLicAttBldLicNo() {
		return licAttBldLicNo;
	}

	public void setLicAttBldLicNo(String licAttBldLicNo) {
		this.licAttBldLicNo = licAttBldLicNo;
	}

	public String getLicAttBldLicDate() {
		return licAttBldLicDate;
	}

	public void setLicAttBldLicDate(String licAttBldLicDate) {
		this.licAttBldLicDate = licAttBldLicDate;
	}

	public String getLicAttBldLicSource() {
		return licAttBldLicSource;
	}

	public void setLicAttBldLicSource(String licAttBldLicSource) {
		this.licAttBldLicSource = licAttBldLicSource;
	}

	public String getLicAttSakNo() {
		return licAttSakNo;
	}

	public void setLicAttSakNo(String licAttSakNo) {
		this.licAttSakNo = licAttSakNo;
	}

	public String getLicAttSakDate() {
		return licAttSakDate;
	}

	public void setLicAttSakDate(String licAttSakDate) {
		this.licAttSakDate = licAttSakDate;
	}

	public String getLicAttSakSource() {
		return licAttSakSource;
	}

	public void setLicAttSakSource(String licAttSakSource) {
		this.licAttSakSource = licAttSakSource;
	}

	public Integer getLicAttEngneeringOfficeId() {
		return licAttEngneeringOfficeId;
	}

	public void setLicAttEngneeringOfficeId(Integer licAttEngneeringOfficeId) {
		this.licAttEngneeringOfficeId = licAttEngneeringOfficeId;
	}

	public String getLicAttNote() {
		return licAttNote;
	}

	public void setLicAttNote(String licAttNote) {
		this.licAttNote = licAttNote;
	}

	public String getLicAttNumber() {
		return licAttNumber;
	}

	public void setLicAttNumber(String licAttNumber) {
		this.licAttNumber = licAttNumber;
	}

	public String getLicAttIssueDate() {
		return licAttIssueDate;
	}

	public void setLicAttIssueDate(String licAttIssueDate) {
		this.licAttIssueDate = licAttIssueDate;
	}

	public String getLicAttEndDate() {
		return licAttEndDate;
	}

	public void setLicAttEndDate(String licAttEndDate) {
		this.licAttEndDate = licAttEndDate;
	}

	public Integer getLicAttLandArea() {
		return licAttLandArea;
	}

	public void setLicAttLandArea(Integer licAttLandArea) {
		this.licAttLandArea = licAttLandArea;
	}

	public Integer getLicAttOrginialBldArea() {
		return licAttOrginialBldArea;
	}

	public void setLicAttOrginialBldArea(Integer licAttOrginialBldArea) {
		this.licAttOrginialBldArea = licAttOrginialBldArea;
	}

	public Integer getLicAttTotalAreaWAtt() {
		return licAttTotalAreaWAtt;
	}

	public void setLicAttTotalAreaWAtt(Integer licAttTotalAreaWAtt) {
		this.licAttTotalAreaWAtt = licAttTotalAreaWAtt;
	}

	public Integer getLicAttHeight() {
		return licAttHeight;
	}

	public void setLicAttHeight(Integer licAttHeight) {
		this.licAttHeight = licAttHeight;
	}

	public Integer getLicAttArea() {
		return licAttArea;
	}

	public void setLicAttArea(Integer licAttArea) {
		this.licAttArea = licAttArea;
	}

	public Integer getLicAttBldTyp() {
		return licAttBldTyp;
	}

	public void setLicAttBldTyp(Integer licAttBldTyp) {
		this.licAttBldTyp = licAttBldTyp;
	}

	public Integer getLicAttBldUse() {
		return licAttBldUse;
	}

	public void setLicAttBldUse(Integer licAttBldUse) {
		this.licAttBldUse = licAttBldUse;
	}

	public String getLicAttOuterFinish() {
		return licAttOuterFinish;
	}

	public void setLicAttOuterFinish(String licAttOuterFinish) {
		this.licAttOuterFinish = licAttOuterFinish;
	}

	public String getLicAttNorthRebound() {
		return licAttNorthRebound;
	}

	public void setLicAttNorthRebound(String licAttNorthRebound) {
		this.licAttNorthRebound = licAttNorthRebound;
	}

	public String getLicAttEastRebound() {
		return licAttEastRebound;
	}

	public void setLicAttEastRebound(String licAttEastRebound) {
		this.licAttEastRebound = licAttEastRebound;
	}

	public String getLicAttSouthRebound() {
		return licAttSouthRebound;
	}

	public void setLicAttSouthRebound(String licAttSouthRebound) {
		this.licAttSouthRebound = licAttSouthRebound;
	}

	public String getLicAttWestRebound() {
		return licAttWestRebound;
	}

	public void setLicAttWestRebound(String licAttWestRebound) {
		this.licAttWestRebound = licAttWestRebound;
	}

	public byte[] getLicAttGeneralKroki() {
		return licAttGeneralKroki;
	}

	public void setLicAttGeneralKroki(byte[] licAttGeneralKroki) {
		this.licAttGeneralKroki = licAttGeneralKroki;
	}

	public byte[] getLicAttLandKroki() {
		return licAttLandKroki;
	}

	public void setLicAttLandKroki(byte[] licAttLandKroki) {
		this.licAttLandKroki = licAttLandKroki;
	}

	public Integer getLicAttWatcher() {
		return licAttWatcher;
	}

	public void setLicAttWatcher(Integer licAttWatcher) {
		this.licAttWatcher = licAttWatcher;
	}

	public Integer getLicAttWroteBy() {
		return licAttWroteBy;
	}

	public void setLicAttWroteBy(Integer licAttWroteBy) {
		this.licAttWroteBy = licAttWroteBy;
	}

	public String getLicAttWroteIn() {
		return licAttWroteIn;
	}

	public void setLicAttWroteIn(String licAttWroteIn) {
		this.licAttWroteIn = licAttWroteIn;
	}

	public Integer getLicAttMarkedBy() {
		return licAttMarkedBy;
	}

	public void setLicAttMarkedBy(Integer licAttMarkedBy) {
		this.licAttMarkedBy = licAttMarkedBy;
	}

	public String getLicAttMarkedIn() {
		return licAttMarkedIn;
	}

	public void setLicAttMarkedIn(String licAttMarkedIn) {
		this.licAttMarkedIn = licAttMarkedIn;
	}

	public Integer getLicAttSignedBy() {
		return licAttSignedBy;
	}

	public void setLicAttSignedBy(Integer licAttSignedBy) {
		this.licAttSignedBy = licAttSignedBy;
	}

	public String getLicAttSignedIn() {
		return licAttSignedIn;
	}

	public void setLicAttSignedIn(String licAttSignedIn) {
		this.licAttSignedIn = licAttSignedIn;
	}

	public String getLicAttBillNumber() {
		return licAttBillNumber;
	}

	public void setLicAttBillNumber(String licAttBillNumber) {
		this.licAttBillNumber = licAttBillNumber;
	}

	public Integer getLicAttSts() {
		return licAttSts;
	}

	public void setLicAttSts(Integer licAttSts) {
		this.licAttSts = licAttSts;
	}

	public String getLicAttPln() {
		return licAttPln;
	}

	public void setLicAttPln(String licAttPln) {
		this.licAttPln = licAttPln;
	}

	public String getLicAttAddress() {
		return licAttAddress;
	}

	public void setLicAttAddress(String licAttAddress) {
		this.licAttAddress = licAttAddress;
	}

	public String getLicAttReqType() {
		return licAttReqType;
	}

	public void setLicAttReqType(String licAttReqType) {
		this.licAttReqType = licAttReqType;
	}

	public List<BldLicPcs> getPieces() {
		return pieces;
	}

	public void setPieces(List<BldLicPcs> pieces) {
		this.pieces = pieces;
	}

	public String getPiecesNumbers() {
		piecesNumbers = "";
		if((pieces != null) && (!pieces.isEmpty())){
			for(int i = pieces.size()-1;i >= 0; i--)
				piecesNumbers += pieces.get(i).getId().getPieceId()+"/";
			piecesNumbers = piecesNumbers.substring(0, piecesNumbers.length()-1);
		}
		return piecesNumbers;
	}

	public void setPiecesNumbers(String piecesNumbers) {
		this.piecesNumbers = piecesNumbers;
	}

}
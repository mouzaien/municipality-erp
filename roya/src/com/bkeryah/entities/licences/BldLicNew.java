package com.bkeryah.entities.licences;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "BLD_LIC_NEW")
public class BldLicNew {
	@Id
	@Column(name = "LIC_NEW_ID")
	private Integer licNewId;
	@Column(name = "LIC_NEW_H_DATE")
	private String licNewHDate;
	@Column(name = "LIC_NEW_G_DATE")
	private Date licNewGDate;
	@Column(name = "LIC_NEW_APL_OWNER_ID")
	private String licNewAplOwnerId;
	@Column(name = "LIC_NEW_APL_MOBILE")
	private String licNewAplMobile;
	@Column(name = "LIC_NEW_SAK_NO")
	private String licNewSakNo;
	@Column(name = "LIC_NEW_SAK_DATE")
	private String licNewSakDate;
	@Column(name = "LIC_NEW_SAK_SOURCE")
	private String licNewSakSource;
	@Column(name = "LIC_NEW_PLN_NO")
	private String licNewPlnNo;
	@Column(name = "LIC_NEW_LAND_MAPPED_Y_N")
	private String licNewLandMappedYN;
	@Column(name = "LIC_NEW_NOTES")
	private String licNewNotes;
	@Column(name = "LIC_NEW_BLD_USE")
	private Long licNewBldUse;
	@Column(name = "LIC_NEW_STREET")
	private String licNewStreet;
	@Column(name = "LIC_NEW_BLD_DESC")
	private String licNewBldDesc;
	@Column(name = "LIC_NEW_BLD_TYP")
	private Integer licNewBldTyp;
	@Column(name = "LIC_NEW_ENGINEERING_OFFICE")
	private Integer licNewEngineeringOffice;
	@Column(name = "LIC_NEW_NUMBER")
	private String licNewNumber;
	@Column(name = "LIC_NEW_ISSUE_DATE")
	private String licNewIssueDate;
	@Column(name = "LIC_NEW_END_DATE")
	private String licNewEndDate;
	@Column(name = "LIC_NEW_DOC_TYPE")
	private String licNewDocType;
	@Column(name = "LIC_NEW_LAND_AREA")
	private Double licNewLandArea;
	@Column(name = "LIC_NEW_REDROOM_AREA")
	private Double licNewRedroomArea;
	@Column(name = "LIC_NEW_BLD_GROUND_AREA")
	private Double licNewBldGroundArea;
	@Column(name = "LIC_NEW_BLD_REPEATING_AREA")
	private Double licNewBldRepeatingArea;
	@Column(name = "LIC_NEW_BLD_REPEATING_NUM")
	private Long licNewBldRepeatingNum;
	@Column(name = "LIC_NEW_ATT_GROUND_AREA")
	private Double licNewAttGroundArea;
	@Column(name = "LIC_NEW_ATT_UPPER_AREA")
	private Double licNewAttUpperArea;
	@Column(name = "LIC_NEW_BLD_UNITS_COUNT")
	private Long licNewBldUnitsCount;
	@Column(name = "LIC_NEW_BLD_TOTAL_AREA")
	private Double licNewBldTotalArea;
	@Column(name = "LIC_NEW_FLOOR_HEIGHT")
	private Double licNewFloorHeight;
	@Column(name = "LIC_NEW_BLD_MAX_HEIGHT")
	private Double licNewBldMaxHeight;
	@Column(name = "LIC_NEW_WALL_TOTAL_LENGTH")
	private Double licNewWallTotalLength;
	@Column(name = "LIC_NEW_WALL_MAX_HEIGHT")
	private Double licNewWallMaxHeight;
	@Column(name = "LIC_NEW_PROM")
	private String licNewProm;
	@Column(name = "LIC_NEW_STREET_LEVEL")
	private String licNewStreetLevel;
	@Column(name = "LIC_NEW_BLD_PERCENTAGE")
	private String licNewBldPercentage;
	@Column(name = "LIC_NEW_FORNT_FINISH")
	private String licNewForntFinish;
	@Column(name = "LIC_NEW_BLD_GEOGRAPHIC")
	private Long licNewBldGeographic;
	@Column(name = "LIC_NEW_NORTH_BORDER")
	private String licNewNorthBorder;
	@Column(name = "LIC_NEW_EAST_BORDER")
	private String licNewEastBorder;
	@Column(name = "LIC_NEW_SOUTH_BORDER")
	private String licNewSouthBorder;
	@Column(name = "LIC_NEW_WEST_BORDER")
	private String licNewWestBorder;
	@Column(name = "LIC_NEW_NORTH_DIM")
	private String licNewNorthDim;
	@Column(name = "LIC_NEW_EAST_DIM")
	private String licNewEastDim;
	@Column(name = "LIC_NEW_SOUTH_DIM")
	private String licNewSouthDim;
	@Column(name = "LIC_NEW_WEST_DIM")
	private String licNewWestDim;
	@Column(name = "LIC_NEW_NORTH_REBOUND")
	private String licNewNorthRebound;
	@Column(name = "LIC_NEW_EAST_REBOUND")
	private String licNewEastRebound;
	@Column(name = "LIC_NEW_SOUTH_REBOUND")
	private String licNewSouthRebound;
	@Column(name = "LIC_NEW_WEST_REBOUND")
	private String licNewWestRebound;
	@Column(name = "LIC_NEW_NORTH_PROM")
	private String licNewNorthProm;
	@Column(name = "LIC_NEW_EAST_PROM")
	private String licNewEastProm;
	@Column(name = "LIC_NEW_SOUTH_PROM")
	private String licNewSouthProm;
	@Column(name = "LIC_NEW_WEST_PROM")
	private String licNewWestProm;
	@Column(name = "LIC_NEW_GENERAL_KROKI")
	private byte[] licNewGeneralKroki;
	@Column(name = "LIC_NEW_BLD_KROKI")
	private byte[] licNewBldKroki;
	@Column(name = "LIC_NEW_WROTE_BY")
	private Integer licNewWroteBy;
	@Column(name = "LIC_NEW_WROTE_IN")
	private String licNewWroteIn;
	@Column(name = "LIC_NEW_MARKED_BY")
	private Integer licNewMarkedBy;
	@Column(name = "LIC_NEW_MARKED_IN")
	private String licNewMarkedIn;
	@Column(name = "LIC_NEW_SIGNED_BY")
	private Integer licNewSignedBy;
	@Column(name = "LIC_NEW_SIGNED_IN")
	private String licNewSignedIn;
	@Column(name = "LIC_NEW_BILL_NUMBER")
	private String licNewBillNumber;
	@Column(name = "LIC_NEW_STS")
	private Integer licNewSts;
	@Column(name = "LIC_NEW_REQUEST_TYPE")
	private Integer licNewRequestType;
	@OneToMany(mappedBy = "bldLicNew", fetch=FetchType.EAGER)
	private List<BldLicPcs> pieces;
	@Transient
	private String piecesNumbers;

	public Integer getLicNewId() {
		return licNewId;
	}

	public void setLicNewId(Integer licNewId) {
		this.licNewId = licNewId;
	}

	public String getLicNewHDate() {
		return licNewHDate;
	}

	public void setLicNewHDate(String licNewHDate) {
		this.licNewHDate = licNewHDate;
	}

	public Date getLicNewGDate() {
		return licNewGDate;
	}

	public void setLicNewGDate(Date licNewGDate) {
		this.licNewGDate = licNewGDate;
	}

	public String getLicNewAplOwnerId() {
		return licNewAplOwnerId;
	}

	public void setLicNewAplOwnerId(String licNewAplOwnerId) {
		this.licNewAplOwnerId = licNewAplOwnerId;
	}

	public String getLicNewAplMobile() {
		return licNewAplMobile;
	}

	public void setLicNewAplMobile(String licNewAplMobile) {
		this.licNewAplMobile = licNewAplMobile;
	}

	public String getLicNewSakNo() {
		return licNewSakNo;
	}

	public void setLicNewSakNo(String licNewSakNo) {
		this.licNewSakNo = licNewSakNo;
	}

	public String getLicNewSakDate() {
		return licNewSakDate;
	}

	public void setLicNewSakDate(String licNewSakDate) {
		this.licNewSakDate = licNewSakDate;
	}

	public String getLicNewSakSource() {
		return licNewSakSource;
	}

	public void setLicNewSakSource(String licNewSakSource) {
		this.licNewSakSource = licNewSakSource;
	}

	public String getLicNewPlnNo() {
		return licNewPlnNo;
	}

	public void setLicNewPlnNo(String licNewPlnNo) {
		this.licNewPlnNo = licNewPlnNo;
	}

	public String getLicNewLandMappedYN() {
		return licNewLandMappedYN;
	}

	public void setLicNewLandMappedYN(String licNewLandMappedYN) {
		this.licNewLandMappedYN = licNewLandMappedYN;
	}

	public String getLicNewNotes() {
		return licNewNotes;
	}

	public void setLicNewNotes(String licNewNotes) {
		this.licNewNotes = licNewNotes;
	}

	public Long getLicNewBldUse() {
		return licNewBldUse;
	}

	public void setLicNewBldUse(Long licNewBldUse) {
		this.licNewBldUse = licNewBldUse;
	}

	public String getLicNewStreet() {
		return licNewStreet;
	}

	public void setLicNewStreet(String licNewStreet) {
		this.licNewStreet = licNewStreet;
	}

	public String getLicNewBldDesc() {
		return licNewBldDesc;
	}

	public void setLicNewBldDesc(String licNewBldDesc) {
		this.licNewBldDesc = licNewBldDesc;
	}

	public Integer getLicNewBldTyp() {
		return licNewBldTyp;
	}

	public void setLicNewBldTyp(Integer licNewBldTyp) {
		this.licNewBldTyp = licNewBldTyp;
	}

	public Integer getLicNewEngineeringOffice() {
		return licNewEngineeringOffice;
	}

	public void setLicNewEngineeringOffice(Integer licNewEngineeringOffice) {
		this.licNewEngineeringOffice = licNewEngineeringOffice;
	}

	public String getLicNewNumber() {
		return licNewNumber;
	}

	public void setLicNewNumber(String licNewNumber) {
		this.licNewNumber = licNewNumber;
	}

	public String getLicNewIssueDate() {
		return licNewIssueDate;
	}

	public void setLicNewIssueDate(String licNewIssueDate) {
		this.licNewIssueDate = licNewIssueDate;
	}

	public String getLicNewEndDate() {
		return licNewEndDate;
	}

	public void setLicNewEndDate(String licNewEndDate) {
		this.licNewEndDate = licNewEndDate;
	}

	public String getLicNewDocType() {
		return licNewDocType;
	}

	public void setLicNewDocType(String licNewDocType) {
		this.licNewDocType = licNewDocType;
	}

	public Double getLicNewLandArea() {
		return licNewLandArea;
	}

	public void setLicNewLandArea(Double licNewLandArea) {
		this.licNewLandArea = licNewLandArea;
	}

	public Double getLicNewRedroomArea() {
		return licNewRedroomArea;
	}

	public void setLicNewRedroomArea(Double licNewRedroomArea) {
		this.licNewRedroomArea = licNewRedroomArea;
	}

	public Double getLicNewBldGroundArea() {
		return licNewBldGroundArea;
	}

	public void setLicNewBldGroundArea(Double licNewBldGroundArea) {
		this.licNewBldGroundArea = licNewBldGroundArea;
	}

	public Double getLicNewBldRepeatingArea() {
		return licNewBldRepeatingArea;
	}

	public void setLicNewBldRepeatingArea(Double licNewBldRepeatingArea) {
		this.licNewBldRepeatingArea = licNewBldRepeatingArea;
	}

	public Long getLicNewBldRepeatingNum() {
		return licNewBldRepeatingNum;
	}

	public void setLicNewBldRepeatingNum(Long licNewBldRepeatingNum) {
		this.licNewBldRepeatingNum = licNewBldRepeatingNum;
	}

	public Double getLicNewAttGroundArea() {
		return licNewAttGroundArea;
	}

	public void setLicNewAttGroundArea(Double licNewAttGroundArea) {
		this.licNewAttGroundArea = licNewAttGroundArea;
	}

	public Double getLicNewAttUpperArea() {
		return licNewAttUpperArea;
	}

	public void setLicNewAttUpperArea(Double licNewAttUpperArea) {
		this.licNewAttUpperArea = licNewAttUpperArea;
	}

	public Long getLicNewBldUnitsCount() {
		return licNewBldUnitsCount;
	}

	public void setLicNewBldUnitsCount(Long licNewBldUnitsCount) {
		this.licNewBldUnitsCount = licNewBldUnitsCount;
	}

	public Double getLicNewBldTotalArea() {
		return licNewBldTotalArea;
	}

	public void setLicNewBldTotalArea(Double licNewBldTotalArea) {
		this.licNewBldTotalArea = licNewBldTotalArea;
	}

	public Double getLicNewFloorHeight() {
		return licNewFloorHeight;
	}

	public void setLicNewFloorHeight(Double licNewFloorHeight) {
		this.licNewFloorHeight = licNewFloorHeight;
	}

	public Double getLicNewBldMaxHeight() {
		return licNewBldMaxHeight;
	}

	public void setLicNewBldMaxHeight(Double licNewBldMaxHeight) {
		this.licNewBldMaxHeight = licNewBldMaxHeight;
	}

	public Double getLicNewWallTotalLength() {
		return licNewWallTotalLength;
	}

	public void setLicNewWallTotalLength(Double licNewWallTotalLength) {
		this.licNewWallTotalLength = licNewWallTotalLength;
	}

	public Double getLicNewWallMaxHeight() {
		return licNewWallMaxHeight;
	}

	public void setLicNewWallMaxHeight(Double licNewWallMaxHeight) {
		this.licNewWallMaxHeight = licNewWallMaxHeight;
	}

	public String getLicNewProm() {
		return licNewProm;
	}

	public void setLicNewProm(String licNewProm) {
		this.licNewProm = licNewProm;
	}

	public String getLicNewStreetLevel() {
		return licNewStreetLevel;
	}

	public void setLicNewStreetLevel(String licNewStreetLevel) {
		this.licNewStreetLevel = licNewStreetLevel;
	}

	public String getLicNewBldPercentage() {
		return licNewBldPercentage;
	}

	public void setLicNewBldPercentage(String licNewBldPercentage) {
		this.licNewBldPercentage = licNewBldPercentage;
	}

	public String getLicNewForntFinish() {
		return licNewForntFinish;
	}

	public void setLicNewForntFinish(String licNewForntFinish) {
		this.licNewForntFinish = licNewForntFinish;
	}

	public Long getLicNewBldGeographic() {
		return licNewBldGeographic;
	}

	public void setLicNewBldGeographic(Long licNewBldGeographic) {
		this.licNewBldGeographic = licNewBldGeographic;
	}

	public String getLicNewNorthBorder() {
		return licNewNorthBorder;
	}

	public void setLicNewNorthBorder(String licNewNorthBorder) {
		this.licNewNorthBorder = licNewNorthBorder;
	}

	public String getLicNewEastBorder() {
		return licNewEastBorder;
	}

	public void setLicNewEastBorder(String licNewEastBorder) {
		this.licNewEastBorder = licNewEastBorder;
	}

	public String getLicNewSouthBorder() {
		return licNewSouthBorder;
	}

	public void setLicNewSouthBorder(String licNewSouthBorder) {
		this.licNewSouthBorder = licNewSouthBorder;
	}

	public String getLicNewWestBorder() {
		return licNewWestBorder;
	}

	public void setLicNewWestBorder(String licNewWestBorder) {
		this.licNewWestBorder = licNewWestBorder;
	}

	public String getLicNewNorthDim() {
		return licNewNorthDim;
	}

	public void setLicNewNorthDim(String licNewNorthDim) {
		this.licNewNorthDim = licNewNorthDim;
	}

	public String getLicNewEastDim() {
		return licNewEastDim;
	}

	public void setLicNewEastDim(String licNewEastDim) {
		this.licNewEastDim = licNewEastDim;
	}

	public String getLicNewSouthDim() {
		return licNewSouthDim;
	}

	public void setLicNewSouthDim(String licNewSouthDim) {
		this.licNewSouthDim = licNewSouthDim;
	}

	public String getLicNewWestDim() {
		return licNewWestDim;
	}

	public void setLicNewWestDim(String licNewWestDim) {
		this.licNewWestDim = licNewWestDim;
	}

	public String getLicNewNorthRebound() {
		return licNewNorthRebound;
	}

	public void setLicNewNorthRebound(String licNewNorthRebound) {
		this.licNewNorthRebound = licNewNorthRebound;
	}

	public String getLicNewEastRebound() {
		return licNewEastRebound;
	}

	public void setLicNewEastRebound(String licNewEastRebound) {
		this.licNewEastRebound = licNewEastRebound;
	}

	public String getLicNewSouthRebound() {
		return licNewSouthRebound;
	}

	public void setLicNewSouthRebound(String licNewSouthRebound) {
		this.licNewSouthRebound = licNewSouthRebound;
	}

	public String getLicNewWestRebound() {
		return licNewWestRebound;
	}

	public void setLicNewWestRebound(String licNewWestRebound) {
		this.licNewWestRebound = licNewWestRebound;
	}

	public String getLicNewNorthProm() {
		return licNewNorthProm;
	}

	public void setLicNewNorthProm(String licNewNorthProm) {
		this.licNewNorthProm = licNewNorthProm;
	}

	public String getLicNewEastProm() {
		return licNewEastProm;
	}

	public void setLicNewEastProm(String licNewEastProm) {
		this.licNewEastProm = licNewEastProm;
	}

	public String getLicNewSouthProm() {
		return licNewSouthProm;
	}

	public void setLicNewSouthProm(String licNewSouthProm) {
		this.licNewSouthProm = licNewSouthProm;
	}

	public String getLicNewWestProm() {
		return licNewWestProm;
	}

	public void setLicNewWestProm(String licNewWestProm) {
		this.licNewWestProm = licNewWestProm;
	}

	public byte[] getLicNewGeneralKroki() {
		return licNewGeneralKroki;
	}

	public void setLicNewGeneralKroki(byte[] licNewGeneralKroki) {
		this.licNewGeneralKroki = licNewGeneralKroki;
	}

	public byte[] getLicNewBldKroki() {
		return licNewBldKroki;
	}

	public void setLicNewBldKroki(byte[] licNewBldKroki) {
		this.licNewBldKroki = licNewBldKroki;
	}

	public Integer getLicNewWroteBy() {
		return licNewWroteBy;
	}

	public void setLicNewWroteBy(Integer licNewWroteBy) {
		this.licNewWroteBy = licNewWroteBy;
	}

	public String getLicNewWroteIn() {
		return licNewWroteIn;
	}

	public void setLicNewWroteIn(String licNewWroteIn) {
		this.licNewWroteIn = licNewWroteIn;
	}

	public Integer getLicNewMarkedBy() {
		return licNewMarkedBy;
	}

	public void setLicNewMarkedBy(Integer licNewMarkedBy) {
		this.licNewMarkedBy = licNewMarkedBy;
	}

	public String getLicNewMarkedIn() {
		return licNewMarkedIn;
	}

	public void setLicNewMarkedIn(String licNewMarkedIn) {
		this.licNewMarkedIn = licNewMarkedIn;
	}

	public Integer getLicNewSignedBy() {
		return licNewSignedBy;
	}

	public void setLicNewSignedBy(Integer licNewSignedBy) {
		this.licNewSignedBy = licNewSignedBy;
	}

	public String getLicNewSignedIn() {
		return licNewSignedIn;
	}

	public void setLicNewSignedIn(String licNewSignedIn) {
		this.licNewSignedIn = licNewSignedIn;
	}

	public String getLicNewBillNumber() {
		return licNewBillNumber;
	}

	public void setLicNewBillNumber(String licNewBillNumber) {
		this.licNewBillNumber = licNewBillNumber;
	}

	public Integer getLicNewSts() {
		return licNewSts;
	}

	public void setLicNewSts(Integer licNewSts) {
		this.licNewSts = licNewSts;
	}

	public Integer getLicNewRequestType() {
		return licNewRequestType;
	}

	public void setLicNewRequestType(Integer licNewRequestType) {
		this.licNewRequestType = licNewRequestType;
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
//			for(BldLicPcs pc : pieces)
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

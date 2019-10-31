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
@Table(name = "BLD_LIC_HANGOVER")
public class BldLicHangover {
	@Id
	@Column(name = "LIC_HANGOVER_ID")
	private Integer licHangoverId;
	@Column(name = "LIC_HANGOVER_H_DATE")
	private String licHangoverHDate;
	@Column(name = "LIC_HANGOVER_G_DATE")
	private Date licHangoverGDate;
	@Column(name = "LIC_HANGOVER_SAK_NO")
	private String licHangoverSakNo;
	@Column(name = "LIC_HANGOVER_SAK_DATE")
	private String licHangoverSakDate;
	@Column(name = "LIC_HANGOVER_SAK_SOURCE")
	private String licHangoverSakSource;
	@Column(name = "LIC_HANGOVER_BLD_LIC_NO")
	private String licHangoverBldLicNo;
	@Column(name = "LIC_HANGOVER_BLD_LIC_DATE")
	private String licHangoverBldLicDate;
	@Column(name = "LIC_HANGOVER_BLD_LIC_SOURCE")
	private String licHangoverBldLicSource;
	@Column(name = "LIC_HANGOVER_PLN_NO")
	private String licHangoverPlnNo;
	@Column(name = "LIC_HANGOVER_APL_OWNER")
	private String licHangoverAplOwner;
	@Column(name = "LIC_HANGOVER_APL_MOBILE")
	private String licHangoverAplMobile;
	@Column(name = "LIC_HANGOVER_BLD_TYP")
	private Integer licHangoverBldTyp;
	@Column(name = "LIC_HANGOVER_BLD_USE")
	private Integer licHangoverBldUse;
	@Column(name = "LIC_HANGOVER_BLD_HEIGHT")
	private Integer licHangoverBldHeight;
	@Column(name = "LIC_HANGOVER_WALL_HEIGHT")
	private Integer licHangoverWallHeight;
	@Column(name = "LIC_HANGOVER_FLOOR_COUNT")
	private Integer licHangoverFloorCount;
	@Column(name = "LIC_HANGOVER_FRONT_FINISH")
	private String licHangoverFrontFinish;
	@Column(name = "LIC_HANGOVER_NOTES")
	private String licHangoverNotes;
	@Column(name = "LIC_HANGOVER_NUMBER")
	private String licHangoverNumber;
	@Column(name = "LIC_HANGOVER_ISSUE_DATE")
	private String licHangoverIssueDate;
	@Column(name = "LIC_HANGOVER_END_DATE")
	private String licHangoverEndDate;
	@Column(name = "LIC_HANGOVER_PREVIEW_DATE")
	private String licHangoverPreviewDate;
	@Column(name = "LIC_HANGOVER_WATCHER")
	private Integer licHangoverWatcher;
	@Column(name = "LIC_HANGOVER_WROTE_BY")
	private Integer licHangoverWroteBy;
	@Column(name = "LIC_HANGOVER_WROTE_IN")
	private String licHangoverWroteIn;
	@Column(name = "LIC_HANGOVER_MARKED_BY")
	private Integer licHangoverMarkedBy;
	@Column(name = "LIC_HANGOVER_MARKED_IN")
	private String licHangoverMarkedIn;
	@Column(name = "LIC_HANGOVER_SIGNED_BY")
	private Integer licHangoverSignedBy;
	@Column(name = "LIC_HANGOVER_SIGNED_IN")
	private String licHangoverSignedIn;
	@Column(name = "LIC_HANGOVER_BILL_NUMBER")
	private String licHangoverBillNumber;
	@Column(name = "LIC_HANGOVER_STS")
	private Integer licHangoverSts;
	@Column(name = "LIC_HANGOVER_REQUEST_TYPE")
	private String licHangoverRequestType;
	@OneToMany(mappedBy = "bldLicHangover", fetch=FetchType.EAGER)
	private List<BldLicPcs> pieces;
	@Transient
	private String piecesNumbers;

	public Integer getLicHangoverId() {
		return licHangoverId;
	}

	public void setLicHangoverId(Integer licHangoverId) {
		this.licHangoverId = licHangoverId;
	}

	public String getLicHangoverHDate() {
		return licHangoverHDate;
	}

	public void setLicHangoverHDate(String licHangoverHDate) {
		this.licHangoverHDate = licHangoverHDate;
	}

	public Date getLicHangoverGDate() {
		return licHangoverGDate;
	}

	public void setLicHangoverGDate(Date licHangoverGDate) {
		this.licHangoverGDate = licHangoverGDate;
	}

	public String getLicHangoverSakNo() {
		return licHangoverSakNo;
	}

	public void setLicHangoverSakNo(String licHangoverSakNo) {
		this.licHangoverSakNo = licHangoverSakNo;
	}

	public String getLicHangoverSakDate() {
		return licHangoverSakDate;
	}

	public void setLicHangoverSakDate(String licHangoverSakDate) {
		this.licHangoverSakDate = licHangoverSakDate;
	}

	public String getLicHangoverSakSource() {
		return licHangoverSakSource;
	}

	public void setLicHangoverSakSource(String licHangoverSakSource) {
		this.licHangoverSakSource = licHangoverSakSource;
	}

	public String getLicHangoverBldLicNo() {
		return licHangoverBldLicNo;
	}

	public void setLicHangoverBldLicNo(String licHangoverBldLicNo) {
		this.licHangoverBldLicNo = licHangoverBldLicNo;
	}

	public String getLicHangoverBldLicDate() {
		return licHangoverBldLicDate;
	}

	public void setLicHangoverBldLicDate(String licHangoverBldLicDate) {
		this.licHangoverBldLicDate = licHangoverBldLicDate;
	}

	public String getLicHangoverBldLicSource() {
		return licHangoverBldLicSource;
	}

	public void setLicHangoverBldLicSource(String licHangoverBldLicSource) {
		this.licHangoverBldLicSource = licHangoverBldLicSource;
	}

	public String getLicHangoverPlnNo() {
		return licHangoverPlnNo;
	}

	public void setLicHangoverPlnNo(String licHangoverPlnNo) {
		this.licHangoverPlnNo = licHangoverPlnNo;
	}

	public String getLicHangoverAplOwner() {
		return licHangoverAplOwner;
	}

	public void setLicHangoverAplOwner(String licHangoverAplOwner) {
		this.licHangoverAplOwner = licHangoverAplOwner;
	}

	public String getLicHangoverAplMobile() {
		return licHangoverAplMobile;
	}

	public void setLicHangoverAplMobile(String licHangoverAplMobile) {
		this.licHangoverAplMobile = licHangoverAplMobile;
	}

	public Integer getLicHangoverBldTyp() {
		return licHangoverBldTyp;
	}

	public void setLicHangoverBldTyp(Integer licHangoverBldTyp) {
		this.licHangoverBldTyp = licHangoverBldTyp;
	}

	public Integer getLicHangoverBldUse() {
		return licHangoverBldUse;
	}

	public void setLicHangoverBldUse(Integer licHangoverBldUse) {
		this.licHangoverBldUse = licHangoverBldUse;
	}

	public Integer getLicHangoverBldHeight() {
		return licHangoverBldHeight;
	}

	public void setLicHangoverBldHeight(Integer licHangoverBldHeight) {
		this.licHangoverBldHeight = licHangoverBldHeight;
	}

	public Integer getLicHangoverWallHeight() {
		return licHangoverWallHeight;
	}

	public void setLicHangoverWallHeight(Integer licHangoverWallHeight) {
		this.licHangoverWallHeight = licHangoverWallHeight;
	}

	public Integer getLicHangoverFloorCount() {
		return licHangoverFloorCount;
	}

	public void setLicHangoverFloorCount(Integer licHangoverFloorCount) {
		this.licHangoverFloorCount = licHangoverFloorCount;
	}

	public String getLicHangoverFrontFinish() {
		return licHangoverFrontFinish;
	}

	public void setLicHangoverFrontFinish(String licHangoverFrontFinish) {
		this.licHangoverFrontFinish = licHangoverFrontFinish;
	}

	public String getLicHangoverNotes() {
		return licHangoverNotes;
	}

	public void setLicHangoverNotes(String licHangoverNotes) {
		this.licHangoverNotes = licHangoverNotes;
	}

	public String getLicHangoverNumber() {
		return licHangoverNumber;
	}

	public void setLicHangoverNumber(String licHangoverNumber) {
		this.licHangoverNumber = licHangoverNumber;
	}

	public String getLicHangoverIssueDate() {
		return licHangoverIssueDate;
	}

	public void setLicHangoverIssueDate(String licHangoverIssueDate) {
		this.licHangoverIssueDate = licHangoverIssueDate;
	}

	public String getLicHangoverEndDate() {
		return licHangoverEndDate;
	}

	public void setLicHangoverEndDate(String licHangoverEndDate) {
		this.licHangoverEndDate = licHangoverEndDate;
	}

	public String getLicHangoverPreviewDate() {
		return licHangoverPreviewDate;
	}

	public void setLicHangoverPreviewDate(String licHangoverPreviewDate) {
		this.licHangoverPreviewDate = licHangoverPreviewDate;
	}

	public Integer getLicHangoverWatcher() {
		return licHangoverWatcher;
	}

	public void setLicHangoverWatcher(Integer licHangoverWatcher) {
		this.licHangoverWatcher = licHangoverWatcher;
	}

	public Integer getLicHangoverWroteBy() {
		return licHangoverWroteBy;
	}

	public void setLicHangoverWroteBy(Integer licHangoverWroteBy) {
		this.licHangoverWroteBy = licHangoverWroteBy;
	}

	public String getLicHangoverWroteIn() {
		return licHangoverWroteIn;
	}

	public void setLicHangoverWroteIn(String licHangoverWroteIn) {
		this.licHangoverWroteIn = licHangoverWroteIn;
	}

	public Integer getLicHangoverMarkedBy() {
		return licHangoverMarkedBy;
	}

	public void setLicHangoverMarkedBy(Integer licHangoverMarkedBy) {
		this.licHangoverMarkedBy = licHangoverMarkedBy;
	}

	public String getLicHangoverMarkedIn() {
		return licHangoverMarkedIn;
	}

	public void setLicHangoverMarkedIn(String licHangoverMarkedIn) {
		this.licHangoverMarkedIn = licHangoverMarkedIn;
	}

	public Integer getLicHangoverSignedBy() {
		return licHangoverSignedBy;
	}

	public void setLicHangoverSignedBy(Integer licHangoverSignedBy) {
		this.licHangoverSignedBy = licHangoverSignedBy;
	}

	public String getLicHangoverSignedIn() {
		return licHangoverSignedIn;
	}

	public void setLicHangoverSignedIn(String licHangoverSignedIn) {
		this.licHangoverSignedIn = licHangoverSignedIn;
	}

	public String getLicHangoverBillNumber() {
		return licHangoverBillNumber;
	}

	public void setLicHangoverBillNumber(String licHangoverBillNumber) {
		this.licHangoverBillNumber = licHangoverBillNumber;
	}

	public Integer getLicHangoverSts() {
		return licHangoverSts;
	}

	public void setLicHangoverSts(Integer licHangoverSts) {
		this.licHangoverSts = licHangoverSts;
	}

	public String getLicHangoverRequestType() {
		return licHangoverRequestType;
	}

	public void setLicHangoverRequestType(String licHangoverRequestType) {
		this.licHangoverRequestType = licHangoverRequestType;
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
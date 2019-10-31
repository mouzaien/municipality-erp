package com.bkeryah.penalties;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "REQ_FINES_SETUP")
public class ReqFinesSetup {
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ")
	@SequenceGenerator(name = "SEQ", sequenceName = "REQ_FINES_SETUP2_SEQ")
	@Id
	@Column(name = "ID")
	private Integer id;
	@Column(name = "FINE_CODE")
	private String fineCode;
	@Column(name = "FINE_DESC")
	private String fineDesc;
	@Column(name = "FINE_DAYS_FROM")
	private Integer fineDaysFrom;
	@Column(name = "FINE_DAYS_TO")
	private Integer fineDaysTo;
	@Column(name = "FINE_PRIMARY_VALUE")
	private Integer finePrimaryValue;
	@Column(name = "FINE_ADJUSTED_VALUE")
	private Integer fineAdjustedValue;
	@Column(name = "FINE_MINIMUM_VALUE")
	private Integer fineMinimumValue;
	@Column(name = "FINE_SUPERMUM_VALUE")
	private Integer fineSupermumValue;
	@Column(name = "FINE_ACTION")
	private String fineAction;
	@Column(name = "ORDERFINE")
	private Integer orderfine;
	@Column(name = "FINEPRC")
	private Integer fineprc;
	@Column(name = "FINE_CLASS")
	private Integer fineClass;

	@Transient
	private String shortDesc;
	@Transient
	private Integer fineNbr;
	@Transient
	private Integer fineValue;
	@Transient
	private Integer fineValSum;
	@Transient
	private boolean visible = true;
	@ManyToMany(mappedBy="finesList")
	private Set<FineSection> sectionsList;

	public String getShortDesc() {
		if (fineDesc.length() > 50)
			shortDesc = fineDesc.substring(0, 50)+"...";
		else
			shortDesc = fineDesc;
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public Integer getFineNbr() {
		return fineNbr;
	}

	public void setFineNbr(Integer fineNbr) {
		this.fineNbr = fineNbr;
	}

	public Integer getFineValue() {
		return fineValue;
	}

	public void setFineValue(Integer fineValue) {
		this.fineValue = fineValue;
	}

	public Integer getFineValSum() {
		return fineValSum;
	}

	public void setFineValSum(Integer fineValSum) {
		this.fineValSum = fineValSum;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFineCode() {
		return fineCode;
	}

	public void setFineCode(String fineCode) {
		this.fineCode = fineCode;
	}

	public String getFineDesc() {
		return fineDesc;
	}

	public void setFineDesc(String fineDesc) {
		this.fineDesc = fineDesc;
	}

	public Integer getFineDaysFrom() {
		return fineDaysFrom;
	}

	public void setFineDaysFrom(Integer fineDaysFrom) {
		this.fineDaysFrom = fineDaysFrom;
	}

	public Integer getFineDaysTo() {
		return fineDaysTo;
	}

	public void setFineDaysTo(Integer fineDaysTo) {
		this.fineDaysTo = fineDaysTo;
	}

	public Integer getFinePrimaryValue() {
		return finePrimaryValue;
	}

	public void setFinePrimaryValue(Integer finePrimaryValue) {
		this.finePrimaryValue = finePrimaryValue;
	}

	public Integer getFineAdjustedValue() {
		return fineAdjustedValue;
	}

	public void setFineAdjustedValue(Integer fineAdjustedValue) {
		this.fineAdjustedValue = fineAdjustedValue;
	}

	public Integer getFineMinimumValue() {
		return fineMinimumValue;
	}

	public void setFineMinimumValue(Integer fineMinimumValue) {
		this.fineMinimumValue = fineMinimumValue;
	}

	public Integer getFineSupermumValue() {
		return fineSupermumValue;
	}

	public void setFineSupermumValue(Integer fineSupermumValue) {
		this.fineSupermumValue = fineSupermumValue;
	}

	public String getFineAction() {
		return fineAction;
	}

	public void setFineAction(String fineAction) {
		this.fineAction = fineAction;
	}

	public Integer getOrderfine() {
		return orderfine;
	}

	public void setOrderfine(Integer orderfine) {
		this.orderfine = orderfine;
	}

	public Integer getFineprc() {
		return fineprc;
	}

	public void setFineprc(Integer fineprc) {
		this.fineprc = fineprc;
	}

	public Integer getFineClass() {
		return fineClass;
	}

	public void setFineClass(Integer fineClass) {
		this.fineClass = fineClass;
	}

	@Override
	public String toString() {
		return this.fineDesc;
	}

	@Override
	public boolean equals(Object obj) {
		return ((ReqFinesSetup) obj).getFineCode().equals(this.getFineCode());
	}

	public Set<FineSection> getSectionsList() {
		return sectionsList;
	}

	public void setSectionsList(Set<FineSection> sectionsList) {
		this.sectionsList = sectionsList;
	}
}
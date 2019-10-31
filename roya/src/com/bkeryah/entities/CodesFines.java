package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "CODES_FINES")
public class CodesFines {
	@Id
	@Column(name = "FINE_CODE")
	private String fineCode;
	@Column(name = "FINE_DESC")
	private String fineDesc;
	@Column(name = "FINE_MINIMUM_VALUE")
	private Integer fineMinimumValue;
	@Column(name = "FINE_SUPERMUM_VALUE")
	private Integer fineSupermumValue;
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

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
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
		if (fineValue != null && fineNbr != null)
			fineValSum = fineValue * fineNbr;
		return fineValSum;
	}

	public void setFineValSum(Integer fineValSum) {
		this.fineValSum = fineValSum;
	}

	public String getShortDesc() {
		if (fineDesc.length() > 50)
			shortDesc = fineDesc.substring(0, 50);
		else
			shortDesc = fineDesc;
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
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

	@Override
	public boolean equals(Object obj) {
		return ((CodesFines) obj).getFineCode().equals(this.getFineCode());
	}
}

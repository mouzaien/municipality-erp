package com.bkeryah.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ARC_ATTACH")
public class ArcAttach {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "course_seq")
	// @SequenceGenerator(name = "course_seq", sequenceName = "ATT_SEQ",
	// allocationSize = 1)
	@Column(name = "ID")
	private int id;
	@Column(name = "ATT_NAME")
	private String attachName;
	@Temporal(TemporalType.DATE)
	@Column(name = "ATT_DATE")
	private Date attachDate;
	@Column(name = "ATT_OWNER")
	private Integer attachOwner;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ATT_OWNER", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private ArcUsers arcUser;

	@Column(name = "ATT_NODE")
	private Integer atachNode;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ATT_NODE", referencedColumnName = "STRUCT_ID", insertable = false, updatable = false)
	private ArcDocumentStruct arcDocumentStruct;

	@Column(name = "ATT_SIZE")
	private Double attachSize;
	@Column(name = "ATT_DESC")
	private String attachDesc;
	@Column(name = "ATT_TYPE")
	private Integer attachType;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ATT_TYPE", referencedColumnName = "ID", insertable = false, updatable = false)
	private ArcFileTypes arcFileType;

	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "TEXT_1")
	private String text1;
	@Column(name = "TEXT_2")
	private String text2;
	@Column(name = "TEXT_3")
	private String text3;
	@Column(name = "SIGNED_BY", nullable = true)
	private Integer SignedBy;
	@Column(name = "TASHEER_BY", nullable = true)
	private Integer tasheerBy;
	@Column(name = "ATT_CATEGORY")
	private String attachCategory;

	@Transient
	private String attachLink;

	// @OneToMany(mappedBy = "attachment")
	// private Set<ArcRecAtt> arcRecAtts ;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	public Date getAttachDate() {
		return attachDate;
	}

	public void setAttachDate(Date attachDate) {
		this.attachDate = attachDate;
	}

	public Integer getAttachOwner() {
		return attachOwner;
	}

	public void setAttachOwner(Integer attachOwner) {
		this.attachOwner = attachOwner;
	}

	public ArcUsers getArcUser() {
		return arcUser;
	}

	public void setArcUser(ArcUsers arcUser) {
		this.arcUser = arcUser;
	}

	public Integer getAtachNode() {
		return atachNode;
	}

	public void setAtachNode(Integer atachNode) {
		this.atachNode = atachNode;
	}

	public ArcDocumentStruct getArcDocumentStruct() {
		return arcDocumentStruct;
	}

	public void setArcDocumentStruct(ArcDocumentStruct arcDocumentStruct) {
		this.arcDocumentStruct = arcDocumentStruct;
	}

	public Double getAttachSize() {
		return attachSize;
	}

	public void setAttachSize(Double attachSize) {
		this.attachSize = attachSize;
	}

	public String getAttachDesc() {
		return attachDesc;
	}

	public void setAttachDesc(String attachDesc) {
		this.attachDesc = attachDesc;
	}

	public Integer getAttachType() {
		return attachType;
	}

	public void setAttachType(Integer attachType) {
		this.attachType = attachType;
	}

	public ArcFileTypes getArcFileType() {
		return arcFileType;
	}

	public void setArcFileType(ArcFileTypes arcFileType) {
		this.arcFileType = arcFileType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public String getText3() {
		return text3;
	}

	public void setText3(String text3) {
		this.text3 = text3;
	}

	public Integer getSignedBy() {
		return SignedBy;
	}

	public void setSignedBy(Integer signedBy) {
		SignedBy = signedBy;
	}

	public Integer getTasheerBy() {
		return tasheerBy;
	}

	public void setTasheerBy(Integer tasheerBy) {
		this.tasheerBy = tasheerBy;
	}

	public String getAttachCategory() {
		return attachCategory;
	}

	public void setAttachCategory(String attachCategory) {
		this.attachCategory = attachCategory;
	}

	public String getAttachLink() {
		if (attachName.equalsIgnoreCase("309")) {
			return text1;
		} else {
			return "http://localhost:8080/BKERY/1/" + attachName;
		}
	}

	public void setAttachLink(String attachLink) {
		this.attachLink = attachLink;
	}

}

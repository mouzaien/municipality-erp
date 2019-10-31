package com.bkeryah.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Arc_Document_Struct")
public class ArcDocumentStruct {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "STRUCT_ID")
	private int structId;
	@Column(name = "Struct_DESC")
	private String structDescription ;
	@Column(name = "PARENT_ID")
	private Integer parentId;
	@Column(name = "STRUCT_DIRECTORY")
	private String structDirectory;
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_IN")
	private Date createdIn;
	@Column(name = "CREATED_BY")
	private Integer createdBy;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="PARENT_ID", insertable = false, updatable = false)
	private ArcDocumentStruct parent;

	@OneToMany(mappedBy="parent")
	private Set<ArcDocumentStruct> children = new HashSet<>();
	
//	@OneToMany(mappedBy="arcDocumentStruct")
//	private Set<ArcAttach> attachmants;

	public int getStructId() {
		return structId;
	}

	public void setStructId(int structId) {
		this.structId = structId;
	}

	public String getStructDescription() {
		return structDescription;
	}

	public void setStructDescription(String structDescription) {
		this.structDescription = structDescription;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getStructDirectory() {
		return structDirectory;
	}

	public void setStructDirectory(String structDirectory) {
		this.structDirectory = structDirectory;
	}

	public Date getCreatedIn() {
		return createdIn;
	}

	public void setCreatedIn(Date createdIn) {
		this.createdIn = createdIn;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

//	public Set<ArcAttach> getAttachmants() {
//		return attachmants;
//	}
//
//	public void setAttachmants(Set<ArcAttach> attachmants) {
//		this.attachmants = attachmants;
//	}

	public ArcDocumentStruct getParent() {
		return parent;
	}

	public void setParent(ArcDocumentStruct parent) {
		this.parent = parent;
	}

	public Set<ArcDocumentStruct> getChildren() {
		return children;
	}

	public void setChildren(Set<ArcDocumentStruct> children) {
		this.children = children;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }
	    ArcDocumentStruct docStruct = (ArcDocumentStruct) obj;
	    return (docStruct.getStructId() == structId);
	}

	@Override
	public int hashCode() {
	    return structId;
	}
	
}

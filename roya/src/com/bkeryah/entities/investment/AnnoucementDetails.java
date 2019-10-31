package com.bkeryah.entities.investment;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ANNOUCEMENT_DETAILS")
public class AnnoucementDetails {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "ANNOUCEMENT_ID")
	private Integer annoucementId;
	@Column(name = "REAL_STATE_ID")
	private Integer realStateId;

	@Column(name = "AREA")
	private String area;
	@Column(name = "NAME")
	private String name;
	@Column(name = "COPY_VALUE")
	private Integer copyValue;
	@Column(name = "STATUS")
	private Integer status;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ANNOUCEMENT_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private Announcement announcement;
	@OneToMany(fetch = FetchType.LAZY)
	private List<Tender> tenderList;
//
	@Formula("(select name from REAL_ESTATE w where w.id = REAL_STATE_ID)")
	private String realStateName;
//	@Transient
//	private String realStateName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAnnoucementId() {
		return annoucementId;
	}

	public void setAnnoucementId(Integer annoucementId) {
		this.annoucementId = annoucementId;
	}

	public Integer getRealStateId() {
		return realStateId;
	}

	public void setRealStateId(Integer realStateId) {
		this.realStateId = realStateId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCopyValue() {
		return copyValue;
	}

	public void setCopyValue(Integer copyValue) {
		this.copyValue = copyValue;
	}

	public Announcement getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
	}

	public List<Tender> getTenderList() {
		return tenderList;
	}

	public void setTenderList(List<Tender> tenderList) {
		this.tenderList = tenderList;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRealStateName() {
		return realStateName;
	}

	public void setRealStateName(String realStateName) {
		this.realStateName = realStateName;
	}

//	public String getRealStateName() {
//		realStateName = realState.getFullName();
//		return realStateName;
//	}
//
//	public void setRealStateName(String realStateName) {
//		this.realStateName = realStateName;
//	}
//
//	public RealEstate getRealState() {
//		return realState;
//	}
//
//	public void setRealState(RealEstate realState) {
//		this.realState = realState;
//	}

}

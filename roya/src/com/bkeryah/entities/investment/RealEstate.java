package com.bkeryah.entities.investment;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "REAL_ESTATE")
public class RealEstate {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "LAND_AREA")
	private Double landArea;
	@Column(name = "BOOK_PRICE")
	private Integer bookPrice;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "NUM_REAL_ESTATE")
	private String numRealEstate;
	@Column(name = "NUM_PLAN")
	private String numPlan;
	@Column(name = "ACTIVITY_TYPE_ID")
	private String activityTypeId;
	@Column(name = "COMPONENTS")
	private String components;
	@Column(name = "STREET")
	private String street;
	@Column(name = "NORTH_LIMIT")
	private String northLimit;
	@Column(name = "NORTH_LENGTH")
	private Double northLength;
	@Column(name = "SOUTH_LIMIT")
	private String southLimit;
	@Column(name = "SOUTH_LENGTH")
	private Double southLength;
	@Column(name = "WEST_LIMIT")
	private String westLimit;
	@Column(name = "WEST_LENGTH")
	private Double westLength;
	@Column(name = "EAST_LIMIT")
	private String eastLimit;
	@Column(name = "EAST_LENGTH")
	private Double eastLength;
	@Column(name = "SITE_LYPE_ID")
	private String siteTypeId;
	@Column(name = "BUILDING_AREA")
	private String buildingArea;
	@Column(name = "NUM_FLOORS")
	private Integer numFloors;
	@Column(name = "BUILD_TYPE")
	private Integer buildTypeId;
	@Column(name = "SITE_TYPE_NB")
	private Integer siteTypeNB;
	@ManyToOne
	@JoinColumn(name = "ACTIVITY_TYPE_ID", referencedColumnName = "CONT_TYPE_CODE", insertable = false, updatable = false)
	private ContractType activityType;
	@ManyToOne
	@JoinColumn(name = "SITE_LYPE_ID", referencedColumnName = "SITE_CODE", insertable = false, updatable = false)
	private SiteType siteType;
	@Transient
	private String fullName;
	@OneToMany(fetch= FetchType.LAZY, mappedBy="realEstate")
	private Set<ContractDirect> contractDirectSet;
//	@OneToMany(fetch= FetchType.LAZY)
//	private List<Contract> contractList ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLandArea() {
		return landArea;
	}

	public void setLandArea(Double landArea) {
		this.landArea = landArea;
	}

	public Integer getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(Integer bookPrice) {
		this.bookPrice = bookPrice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNumRealEstate() {
		return numRealEstate;
	}

	public void setNumRealEstate(String numRealEstate) {
		this.numRealEstate = numRealEstate;
	}

	public String getNumPlan() {
		return numPlan;
	}

	public void setNumPlan(String numPlan) {
		this.numPlan = numPlan;
	}

	public String getActivityTypeId() {
		return activityTypeId;
	}

	public void setActivityTypeId(String activityTypeId) {
		this.activityTypeId = activityTypeId;
	}

	public String getComponents() {
		return components;
	}

	public void setComponents(String components) {
		this.components = components;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNorthLimit() {
		return northLimit;
	}

	public void setNorthLimit(String northLimit) {
		this.northLimit = northLimit;
	}

	public Double getNorthLength() {
		return northLength;
	}

	public void setNorthLength(Double northLength) {
		this.northLength = northLength;
	}

	public String getSouthLimit() {
		return southLimit;
	}

	public void setSouthLimit(String southLimit) {
		this.southLimit = southLimit;
	}

	public Double getSouthLength() {
		return southLength;
	}

	public void setSouthLength(Double southLength) {
		this.southLength = southLength;
	}

	public String getWestLimit() {
		return westLimit;
	}

	public void setWestLimit(String westLimit) {
		this.westLimit = westLimit;
	}

	public Double getWestLength() {
		return westLength;
	}

	public void setWestLength(Double westLength) {
		this.westLength = westLength;
	}

	public String getEastLimit() {
		return eastLimit;
	}

	public void setEastLimit(String eastLimit) {
		this.eastLimit = eastLimit;
	}

	public Double getEastLength() {
		return eastLength;
	}

	public void setEastLength(Double eastLength) {
		this.eastLength = eastLength;
	}

	public String getSiteTypeId() {
		return siteTypeId;
	}

	public void setSiteTypeId(String siteTypeId) {
		this.siteTypeId = siteTypeId;
	}

	public String getBuildingArea() {
		return buildingArea;
	}

	public void setBuildingArea(String buildingArea) {
		this.buildingArea = buildingArea;
	}

	public Integer getNumFloors() {
		return numFloors;
	}

	public void setNumFloors(Integer numFloors) {
		this.numFloors = numFloors;
	}

	public Integer getBuildTypeId() {
		return buildTypeId;
	}

	public void setBuildTypeId(Integer buildTypeId) {
		this.buildTypeId = buildTypeId;
	}

	public String getFullName() {
		fullName = numRealEstate+" - "+((siteType != null)?siteType.getName():"")+" - "+((activityType != null)?activityType.getName():"")+" - "+street;
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public ContractType getActivityType() {
		return activityType;
	}

	public void setActivityType(ContractType activityType) {
		this.activityType = activityType;
	}

	public SiteType getSiteType() {
		return siteType;
	}

	public void setSiteType(SiteType siteType) {
		this.siteType = siteType;
	}


//	public List<Contract> getContractList() {
//		return contractList;
//	}
//
//	public void setContractList(List<Contract> contractList) {
//		this.contractList = contractList;
//	}

	public Integer getSiteTypeNB() {
		return siteTypeNB;
	}

	public void setSiteTypeNB(Integer siteTypeNB) {
		this.siteTypeNB = siteTypeNB;
	}

	public Set<ContractDirect> getContractDirectSet() {
		return contractDirectSet;
	}

	public void setContractDirectSet(Set<ContractDirect> contractDirectSet) {
		this.contractDirectSet = contractDirectSet;
	}

}
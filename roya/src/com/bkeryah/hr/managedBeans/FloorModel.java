package com.bkeryah.hr.managedBeans;

public class FloorModel {
	private Integer hrsCommpactPerfId;
	private Integer catFloorId;
	private Integer floorId;

	private Integer relativeImportance;
	private Integer evaluation;
	private String floorDescription;
	private String catFloorDesc;

	public String getCatFloorDesc() {
		return catFloorDesc;
	}

	public void setCatFloorDesc(String catFloorDesc) {
		this.catFloorDesc = catFloorDesc;
	}

	public String getFloorDescription() {
		return floorDescription;
	}

	public void setFloorDescription(String floorDescription) {
		this.floorDescription = floorDescription;
	}

	public Integer getHrsCommpactPerfId() {
		return hrsCommpactPerfId;
	}

	public void setHrsCommpactPerfId(Integer hrsCommpactPerfId) {
		this.hrsCommpactPerfId = hrsCommpactPerfId;
	}

	public Integer getCatFloorId() {
		return catFloorId;
	}

	public void setCatFloorId(Integer catFloorId) {
		this.catFloorId = catFloorId;
	}

	public Integer getFloorId() {
		return floorId;
	}

	public void setFloorId(Integer floorId) {
		this.floorId = floorId;
	}

	public Integer getRelativeImportance() {
		return relativeImportance;
	}

	public void setRelativeImportance(Integer relativeImportance) {
		this.relativeImportance = relativeImportance;
	}

	public Integer getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Integer evaluation) {
		this.evaluation = evaluation;
	}

}

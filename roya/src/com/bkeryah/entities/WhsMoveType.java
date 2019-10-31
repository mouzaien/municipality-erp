package com.bkeryah.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "whs_move_typ")
public class WhsMoveType {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "MOVE_TYP_NO")
	private Integer moveTypeId;
	@Column(name = "MOVE_TYP_NAME")
	private String moveTypeName;

	public WhsMoveType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WhsMoveType(Integer moveTypeId, String moveTypeName) {
		super();
		this.moveTypeId = moveTypeId;
		this.moveTypeName = moveTypeName;
	}

	public Integer getMoveTypeId() {
		return moveTypeId;
	}

	public void setMoveTypeId(Integer moveTypeId) {
		this.moveTypeId = moveTypeId;
	}

	public String getMoveTypeName() {
		return moveTypeName;
	}

	public void setMoveTypeName(String moveTypeName) {
		this.moveTypeName = moveTypeName;
	}

}

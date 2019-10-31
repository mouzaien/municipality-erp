package com.bkeryah.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WHS_STOCK_IN_OUT_TYPE")
public class StockInOutType implements Serializable{

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer stockInOutTypeId;
	@Column(name = "name")
	private String stockInOutTypeName;
	@Column(name = "stock_Typ")
	private Integer sIntegertocktype;//1 for entry 2 for out 
	
	
	
	public StockInOutType() {
		super();
		// TODO Auto-generated constructor stub
	}



	public StockInOutType(Integer stockInOutTypeId, String stockInOutTypeName, Integer sIntegertocktype) {
		super();
		this.stockInOutTypeId = stockInOutTypeId;
		this.stockInOutTypeName = stockInOutTypeName;
		this.sIntegertocktype = sIntegertocktype;
	}



	public Integer getStockInOutTypeId() {
		return stockInOutTypeId;
	}



	public void setStockInOutTypeId(Integer stockInOutTypeId) {
		this.stockInOutTypeId = stockInOutTypeId;
	}



	public String getStockInOutTypeName() {
		return stockInOutTypeName;
	}



	public void setStockInOutTypeName(String stockInOutTypeName) {
		this.stockInOutTypeName = stockInOutTypeName;
	}



	public Integer getsIntegertocktype() {
		return sIntegertocktype;
	}



	public void setsIntegertocktype(Integer sIntegertocktype) {
		this.sIntegertocktype = sIntegertocktype;
	}
	
	
	
	
	
	
}

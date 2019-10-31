package com.bkeryah.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "BILL_ISSUE_DIG")
public class BillIssueDig implements Serializable {

	/**
	 * 
	 */
	//PAY_bill_details_seq.nextval
	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
//	@SequenceGenerator(name = "G1", sequenceName = "PAY_bill_details_seq" ,allocationSize=1)
	@Column(name = "ID")
	private Integer id;
	@Column(name = "name")
	private String name;
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
	

}

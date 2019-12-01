package com.bkeryah.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "reward")
public class RewardInfo implements Serializable{
	
	
		@Id
		@GenericGenerator(name = "generator", strategy = "increment")
		@GeneratedValue(generator = "generator")
		@Column(name = "id")
		private Integer id;
		
		@Column(name = "reward_month",nullable = true)
		private Integer month;
		@Column(name = "reward_year", nullable = true)
		private Integer year;
		@Column(name = "amount", nullable = false)
		private Integer amount;
		@Column(name = "reward_type")
		private String type;
		@Column(name = "employee_no",nullable = false)
		private Integer emp_no;
		@Column(name = "master_empno",nullable = false)
		private Integer master_empno;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Integer getMonth() {
			return month;
		}
		public void setMonth(Integer month) {
			this.month = month;
		}
		public Integer getYear() {
			return year;
		}
		public void setYear(Integer year) {
			this.year = year;
		}
		public Integer getAmount() {
			return amount;
		}
		public void setAmount(Integer amount) {
			this.amount = amount;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Integer getEmp_no() {
			return emp_no;
		}
		public void setEmp_no(Integer emp_no) {
			this.emp_no = emp_no;
		}
		public Integer getMaster_empno() {
			return master_empno;
		}
		public void setMaster_empno(Integer master_empno) {
			this.master_empno = master_empno;
		}

}

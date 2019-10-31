package com.bkeryah.hr.entities;

import java.text.DecimalFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "HRS_EMP_OVRTM")
public class HrsEmpOvertime {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "OVRTMSEQ")
	private Integer id;
	@Column(name = "EMPNO")
	private Integer employeeNumber;
	@Column(name = "MONTH")
	private Integer monthOvertime;
	@Column(name = "YEAR")
	private Integer yearOverTime;
	@Column(name = "NDAY")
	private Integer normalDays;
	@Column(name = "HDAY")
	private Integer holidays;
	@Column(name = "EDAY")
	private Integer feastDays;
	@Column(name = "NHOURS")
	private Double normalHours;
	@Column(name = "HHOURS")
	private Double holidaysHours;
	@Column(name = "EHOURS")
	private Double feastHours;
	@Column(name = "NVAL")
	private Double normalValue;
	@Column(name = "HVAL")
	private Double holidaysValue;
	@Column(name = "EVAL")
	private Double feastValue;
	@Column(name = "OTOTAL")
	private Double deservedTotlal;
	@Column(name = "CIN")
	private Date createDate;
	@Column(name = "CBY")
	private Integer createdBy;
	@Column(name = "PCAT")
	private Integer category;
	@Column(name = "FRMDATE")
	private String fromDate;
	@Column(name = "TODATE")
	private String toDate;
	@Column(name = "trans")
	private Double trans;
	@Formula("(select w.FST_NAME_AR || ' ' || w.SND_NAME_AR ||' ' || w.TRD_NAME_AR ||' ' ||w.FTH_NAME_AR from hrs_master_file w where w.empno = EMPNO)")
	private String empName;
	@Formula("(select w.BSCSAL from hrs_emp_historical w where w.empno = EMPNO and w.flg=1)")
	private Double basicSalry;
	@Formula("(select w.TRANS from hrs_emp_historical w where w.empno = EMPNO and w.flg=1)")
	private Double moneyForTrans;
	@Formula("(select NVL(jobs.TITLE, mf.natno) from HRS_EMP_HISTORICAL jh, HRS_GOV_JOB_4 jobs, hrs_master_file mf  where jh.empno = EMPNO and jh.JOBCOD = jobs.id (+)  and  jh.flg=1 and  mf.empno = jh.empno)")
	private String jobDesc;
	@Transient
	private String transMoney;
	@Transient
	private String salary;
	@Transient
	private Double salaryNet;
	@Transient
	private String total;

	@Transient
	private Date fromGrigDate;
	@Transient
	private Date toGrigDate;

	private static DecimalFormat format = new DecimalFormat(".00");

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(Integer employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public Integer getMonthOvertime() {
		return monthOvertime;
	}

	public void setMonthOvertime(Integer monthOvertime) {
		this.monthOvertime = monthOvertime;
	}

	public Integer getYearOverTime() {
		return yearOverTime;
	}

	public void setYearOverTime(Integer yearOverTime) {
		this.yearOverTime = yearOverTime;
	}

	public Integer getNormalDays() {
		if (normalDays == null)
			normalDays = 0;
		return normalDays;
	}

	public void setNormalDays(Integer normalDays) {
		this.normalDays = normalDays;
	}

	public Integer getHolidays() {
		if (holidays == null)
			holidays = 0;
		return holidays;
	}

	public void setHolidays(Integer holidays) {
		this.holidays = holidays;
	}

	public Integer getFeastDays() {
		if (feastDays == null)
			feastDays = 0;
		return feastDays;
	}

	public void setFeastDays(Integer feastDays) {
		this.feastDays = feastDays;
	}

	public Double getNormalHours() {
		if (normalHours == null)
			normalHours = 0d;
		return normalHours;
	}

	public void setNormalHours(Double normalHours) {
		this.normalHours = normalHours;
	}

	public Double getHolidaysHours() {
		if (holidaysHours == null)
			holidaysHours = 0d;
		return holidaysHours;
	}

	public void setHolidaysHours(Double holidaysHours) {
		this.holidaysHours = holidaysHours;
	}

	public Double getFeastHours() {
		if (feastHours == null)
			feastHours = 0d;
		return feastHours;
	}

	public void setFeastHours(Double feastHours) {
		this.feastHours = feastHours;
	}

	public Double getNormalValue() {
		return normalValue;
	}

	public void setNormalValue(Double normalValue) {
		this.normalValue = normalValue;
	}

	public Double getHolidaysValue() {
		return holidaysValue;
	}

	public void setHolidaysValue(Double holidaysValue) {
		this.holidaysValue = holidaysValue;
	}

	public Double getFeastValue() {
		return feastValue;
	}

	public void setFeastValue(Double feastValue) {
		this.feastValue = feastValue;
	}

	public Double getDeservedTotlal() {
		return deservedTotlal;
	}

	public void setDeservedTotlal(Double deservedTotlal) {
		this.deservedTotlal = deservedTotlal;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Double getTrans() {
		if (normalDays == null)
			normalDays = 0;
		if (holidays == null)
			holidays = 0;
		if (feastDays == null)
			feastDays = 0;
		trans = (moneyForTrans / 30) * (normalDays + holidays + feastDays);
		return trans;
	}

	public void setTrans(Double trans) {
		this.trans = trans;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Double getBasicSalry() {
		return basicSalry;
	}

	public void setBasicSalry(Double basicSalry) {
		this.basicSalry = basicSalry;
	}

	public Double getMoneyForTrans() {
		return moneyForTrans;
	}

	public void setMoneyForTrans(Double moneyForTrans) {
		this.moneyForTrans = moneyForTrans;
	}

	private Double getAllHours() {
		Double monthHours = 0d;
		if (normalDays != null && normalDays > 0) {
			monthHours = (double) (normalDays * getNormalHours());
		} else
			monthHours = getNormalHours();
		if (feastDays != null && feastDays > 0) {
			monthHours = monthHours + (feastDays * getFeastHours());
		} else
			monthHours = monthHours + getFeastHours();
		if (holidays != null && holidays > 0) {
			monthHours = monthHours + (holidays * getHolidaysHours());
		} else
			monthHours = monthHours + getHolidaysHours();
		return monthHours;
	}

	public String getSalary() {
		if (basicSalry == null)
			basicSalry = 0d;
		salaryNet = basicSalry * getAllHours() / 155;
		salary = format.format(salaryNet);
		return salary;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public String getTotal() {
		Double stotal = Double.parseDouble(getSalary()) + Double.parseDouble(getTransMoney());
		return format.format(stotal);
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getTransMoney() {
		if (normalDays == null)
			normalDays = 0;
		if (holidays == null)
			holidays = 0;
		if (feastDays == null)
			feastDays = 0;
		if (moneyForTrans == null)
			moneyForTrans = 0d;
		trans = (moneyForTrans / 30) * (normalDays + holidays + feastDays);
		transMoney = format.format(trans);
		return transMoney;
	}

	public void setTransMoney(String transMoney) {
		this.transMoney = transMoney;
	}

	@Override
	public boolean equals(Object obj) {
		return this.id.equals(((HrsEmpOvertime) obj).getId());
	}

	public Date getFromGrigDate() {
		return fromGrigDate;
	}

	public void setFromGrigDate(Date fromGrigDate) {
		this.fromGrigDate = fromGrigDate;
	}

	public Date getToGrigDate() {
		return toGrigDate;
	}

	public void setToGrigDate(Date toGrigDate) {
		this.toGrigDate = toGrigDate;
	}

}

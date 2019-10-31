package com.bkeryah.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import utilities.MyConstants;
import utilities.Utils;

@Entity
@Table(name = "HRS_EMP_VCTON")
public class HrEmployeeVacation {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "VACSEQ")
	private Integer id;

	@Column(name = "EMPNO")
	private Integer employeeNumber;

	@Column(name = "VACATYP")
	private Integer vacationType;

	@Column(name = "DIRECT_FLAG") // zero or one
	private Integer haveInitiation=0;

	@Column(name = "VACATIONREF")
	private Integer extendedVacationId;

	@Column(name = "VACAPRD")
	private Integer vacationPeriod;

	@Column(name = "VACASTRT")
	private String higriVacationStart;

	@Column(name = "EXCNO")
	private String excuseNumber;

	@Column(name = "VACAEND")
	private String HigriVacationEnd;

	@Column(name = "EXCDT")
	private String excuseDateHigri;

	@Column(name = "EXCYEAR")
	private Integer excuseYearHigri;

	@Column(name = "DRCTDT")
	private String DRCTDT;

	@Column(name = "PROCTYP")
	private Integer procType;

	@Column(name = "PAYTYP")
	private Integer payType;

	@Column(name = "VCALC")
	private Integer vactionCalc;

	@Column(name = "PAYSTS")
	private Integer paySts;

	@Column(name = "VACWRKFROM")
	private String HDatevacationFrom;

	@Column(name = "VACWRKTO")
	private String HDateVacationTo;

	@Column(name = "CBY")
	private Integer createdById;

	@Column(name = "CIN")
	private Date createdIn;

	@Column(name = "VAC_ACCPT_Y_N")
	private String vacationStatus = "N";

	@Column(name = "JOB")
	private String employeeJob;

	@Column(name = "RANK")
	private Integer employeeRank;

	@Column(name = "JOBNO")
	private Integer employeeJobNum;

	@Column(name = "BASCAL")
	private Integer basicSalary;
	
	@Column(name = "VACVAL")
	private Double costVacation;

	@Column(name = "NOTE")
	private String note;

	@OneToOne(mappedBy = "Vacation")
	private EmployeeInitiation employeeInitiation;
	
	@Column(name = "HOSPITAL")
	private String hospital;
	
//	 @ManyToOne(fetch= FetchType.EAGER)
//	 @JoinColumn(name = "EMPNO", referencedColumnName = "EMPNO", insertable =false, updatable = false)
//	 private HrsVacationCalc vacationCalc;
	
	@Transient
	private int usedForcedPeriod;
	@Transient
	private int remainForcedPeriod;

	@Transient
	private int usedNormalPeriod;
	//total from recruitment
	@Transient
	private int totalVacationPeriod;
	//used from recruitment
	@Transient
	private int usedTotalVacationPeriod;
	//remain from recruitment
	@Transient
	private int remainTotalVacationPeriod;
	// taken in year from today
	@Transient
	private int usedNormalYearlyPeriod;
	// remain in year from today
	@Transient
	private int remainNormalYearlyPeriod;
	@Transient
	private String lastVacationDate;
	@Transient
	private Integer lastVacationPeriod;
	@Transient
	private String lastVacationFrom;
	@Transient
	private String lastVacationTo;
	@Transient
	private String lastVacationStart;
	@Transient
	private String lastVacationEnd;
	@Transient
	private String vactionCalcLabel;
	
	@Transient
	private int vacationPeriodThisYear;
	@Transient
	private int periodLessFive;
	
	public HrEmployeeVacation() {

	}	
	
	public HrEmployeeVacation( HrEmployeeVacation employeeVacation) {
		super();
		this.id = employeeVacation.getId();
		this.employeeNumber = employeeVacation.getEmployeeNumber();
		this.vacationType = employeeVacation.getVacationType();
		this.haveInitiation = employeeVacation.getHaveInitiation();
		this.extendedVacationId = employeeVacation.getExtendedVacationId();
		this.vacationPeriod = employeeVacation.getVacationPeriod();
		this.higriVacationStart = employeeVacation.getHigriVacationStart();
		this.excuseNumber = employeeVacation.getExcuseNumber();
		this.HigriVacationEnd = employeeVacation.getHigriVacationEnd();
		this.excuseDateHigri = employeeVacation.getExcuseDateHigri();
		this.excuseYearHigri = employeeVacation.getExcuseYearHigri();
		this.DRCTDT = employeeVacation.getDRCTDT();
		this.procType = employeeVacation.getProcType();
		this.payType = employeeVacation.getPayType();
		this.vactionCalc = employeeVacation.getVactionCalc();
		this.paySts = employeeVacation.getPaySts();
		this.HDatevacationFrom = employeeVacation.getHDatevacationFrom();
		this.HDateVacationTo = employeeVacation.getHDateVacationTo();
		this.createdById = employeeVacation.getCreatedById();
		this.createdIn = employeeVacation.getCreatedIn();
		this.vacationStatus = employeeVacation.getVacationStatus();
		this.employeeJob = employeeVacation.getEmployeeJob();
		this.employeeRank = employeeVacation.getEmployeeRank();
		this.employeeJobNum = employeeVacation.getEmployeeJobNum();
		this.basicSalary = employeeVacation.getBasicSalary();
		this.costVacation = employeeVacation.getCostVacation();
		this.note = employeeVacation.getNote();
		this.employeeInitiation = employeeVacation.getEmployeeInitiation();
		this.usedForcedPeriod = employeeVacation.getUsedForcedPeriod();
		this.remainForcedPeriod = employeeVacation.getRemainForcedPeriod();
		this.usedNormalPeriod = employeeVacation.getUsedNormalPeriod();
		this.totalVacationPeriod = employeeVacation.getTotalVacationPeriod();
		this.usedTotalVacationPeriod = employeeVacation.getUsedTotalVacationPeriod();
		this.remainTotalVacationPeriod = employeeVacation.getRemainTotalVacationPeriod();
		this.usedNormalYearlyPeriod = employeeVacation.getUsedNormalYearlyPeriod();
		this.remainNormalYearlyPeriod = employeeVacation.getRemainNormalYearlyPeriod();
		this.lastVacationDate = employeeVacation.getLastVacationDate();
		this.lastVacationPeriod = employeeVacation.getLastVacationPeriod();
		this.lastVacationFrom = employeeVacation.getLastVacationFrom();
		this.lastVacationTo = employeeVacation.getLastVacationTo();
		this.lastVacationStart = employeeVacation.getLastVacationStart();
		this.lastVacationEnd = employeeVacation.getLastVacationEnd();
		this.vacationPeriodThisYear = employeeVacation.getVacationPeriodThisYear();
	}

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

	public Integer getVacationType() {
		return vacationType;
	}

	public void setVacationType(Integer vacationType) {
		this.vacationType = vacationType;
	}

	public Integer getVacationPeriod() {
		return vacationPeriod;
	}

	public void setVacationPeriod(Integer vacationPeriod) {
		this.vacationPeriod = vacationPeriod;
	}

	public String getHigriVacationStart() {
		return higriVacationStart;
	}

	public void setHigriVacationStart(String higriVacationStart) {
		this.higriVacationStart = higriVacationStart;
	}

	public String getExcuseNumber() {
		return excuseNumber;
	}

	public void setExcuseNumber(String excuseNumber) {
		this.excuseNumber = excuseNumber;
	}

	public String getHigriVacationEnd() {
		return HigriVacationEnd;
	}

	public void setHigriVacationEnd(String higriVacationEnd) {
		HigriVacationEnd = higriVacationEnd;
	}

	public String getExcuseDateHigri() {
		return excuseDateHigri;
	}

	public void setExcuseDateHigri(String excuseDateHigri) {
		this.excuseDateHigri = excuseDateHigri;
	}

	public Integer getExcuseYearHigri() {
		return excuseYearHigri;
	}

	public void setExcuseYearHigri(Integer excuseYearHigri) {
		this.excuseYearHigri = excuseYearHigri;
	}

	public String getDRCTDT() {
		return DRCTDT;
	}

	public void setDRCTDT(String dRCTDT) {
		DRCTDT = dRCTDT;
	}

	public Integer getProcType() {
		return procType;
	}

	public void setProcType(Integer procType) {
		this.procType = procType;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getVactionCalc() {
		return vactionCalc;
	}

	public void setVactionCalc(Integer vactionCalc) {
		this.vactionCalc = vactionCalc;
	}

	public Integer getPaySts() {
		return paySts;
	}

	public void setPaySts(Integer paySts) {
		this.paySts = paySts;
	}

	public String getHDatevacationFrom() {
		return HDatevacationFrom;
	}

	public void setHDatevacationFrom(String hDatevacationFrom) {
		HDatevacationFrom = hDatevacationFrom;
	}

	public String getHDateVacationTo() {
		return HDateVacationTo;
	}

	public void setHDateVacationTo(String hDateVacationTo) {
		HDateVacationTo = hDateVacationTo;
	}

	public Integer getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Integer createdById) {
		this.createdById = createdById;
	}

	public Date getCreatedIn() {
		return createdIn;
	}

	public void setCreatedIn(Date createdIn) {
		this.createdIn = createdIn;
	}

	public String getVacationStatus() {
		return vacationStatus;
	}

	public void setVacationStatus(String vacationStatus) {
		this.vacationStatus = vacationStatus;
	}

	public String getEmployeeJob() {
		return employeeJob;
	}

	public void setEmployeeJob(String employeeJob) {
		this.employeeJob = employeeJob;
	}

	public Integer getEmployeeRank() {
		return employeeRank;
	}

	public void setEmployeeRank(Integer employeeRank) {
		this.employeeRank = employeeRank;
	}

	public Integer getEmployeeJobNum() {
		return employeeJobNum;
	}

	public void setEmployeeJobNum(Integer employeeJobNum) {
		this.employeeJobNum = employeeJobNum;
	}

	public Integer getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(Integer basicSalary) {
		this.basicSalary = basicSalary;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public EmployeeInitiation getEmployeeInitiation() {
		return employeeInitiation;
	}

	public void setEmployeeInitiation(EmployeeInitiation employeeInitiation) {
		this.employeeInitiation = employeeInitiation;
	}

	public int getUsedForcedPeriod() {
		return usedForcedPeriod;
	}

	public void setUsedForcedPeriod(int usedForcedPeriod) {
		this.usedForcedPeriod = usedForcedPeriod;
	}

	public int getRemainForcedPeriod() {
		return MyConstants.MAX_FORCED_VACATION - usedForcedPeriod;
	}

	public void setRemainForcedPeriod(int remainForcedPeriod) {
		this.remainForcedPeriod = remainForcedPeriod;
	}

	public int getUsedNormalPeriod() {
		return usedNormalPeriod;
	}

	public void setUsedNormalPeriod(int usedNormalPeriod) {
		this.usedNormalPeriod = usedNormalPeriod;
	}
	
	public Integer getHaveInitiation() {
		return haveInitiation;
	}

	public void setHaveInitiation(Integer haveInitiation) {
		this.haveInitiation = haveInitiation;
	}

	public Integer getExtendedVacationId() {
		return extendedVacationId;
	}

	public void setExtendedVacationId(Integer extendedVacationId) {
		this.extendedVacationId = extendedVacationId;
	}

	public Double getCostVacation() {
		return costVacation;
	}

	public void setCostVacation(Double costVacation) {
		this.costVacation = costVacation;
	}

	public int getTotalVacationPeriod() {
		return totalVacationPeriod;
	}

	public void setTotalVacationPeriod(int totalVacationPeriod) {
		this.totalVacationPeriod = totalVacationPeriod;
	}

	public int getUsedTotalVacationPeriod() {
		return usedTotalVacationPeriod;
	}

	public void setUsedTotalVacationPeriod(int usedTotalVacationPeriod) {
		this.usedTotalVacationPeriod = usedTotalVacationPeriod;
	}

	public int getRemainTotalVacationPeriod() {
		return remainTotalVacationPeriod;
	}

	public void setRemainTotalVacationPeriod(int remainTotalVacationPeriod) {
		this.remainTotalVacationPeriod = remainTotalVacationPeriod;
	}

	public int getUsedNormalYearlyPeriod() {
		return usedNormalYearlyPeriod;
	}

	public void setUsedNormalYearlyPeriod(int usedNormalYearlyPeriod) {
		this.usedNormalYearlyPeriod = usedNormalYearlyPeriod;
	}

	public int getRemainNormalYearlyPeriod() {
		return remainNormalYearlyPeriod;
	}

	public void setRemainNormalYearlyPeriod(int remainNormalYearlyPeriod) {
		this.remainNormalYearlyPeriod = remainNormalYearlyPeriod;
	}

	public String getLastVacationDate() {
		return lastVacationDate;
	}

	public void setLastVacationDate(String lastVacationDate) {
		this.lastVacationDate = lastVacationDate;
	}

	public Integer getLastVacationPeriod() {
		return lastVacationPeriod;
	}

	public void setLastVacationPeriod(Integer lastVacationPeriod) {
		this.lastVacationPeriod = lastVacationPeriod;
	}

	public String getLastVacationFrom() {
		return lastVacationFrom;
	}

	public void setLastVacationFrom(String lastVacationFrom) {
		this.lastVacationFrom = lastVacationFrom;
	}

	public String getLastVacationTo() {
		return lastVacationTo;
	}

	public void setLastVacationTo(String lastVacationTo) {
		this.lastVacationTo = lastVacationTo;
	}

	public String getLastVacationStart() {
		return lastVacationStart;
	}

	public void setLastVacationStart(String lastVacationStart) {
		this.lastVacationStart = lastVacationStart;
	}

	public String getLastVacationEnd() {
		return lastVacationEnd;
	}

	public void setLastVacationEnd(String lastVacationEnd) {
		this.lastVacationEnd = lastVacationEnd;
	}

	public String getVactionCalcLabel() {
		if(vactionCalc != null){
			if(vactionCalc == 1)
				vactionCalcLabel = Utils.loadMessagesFromFile("with.salary");
			else if(vactionCalc == 2)
				vactionCalcLabel = Utils.loadMessagesFromFile("without.salary");
		}
		return vactionCalcLabel;
	}

	public void setVactionCalcLabel(String vactionCalcLabel) {
		this.vactionCalcLabel = vactionCalcLabel;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public int getVacationPeriodThisYear() {
		return vacationPeriodThisYear;
	}

	public void setVacationPeriodThisYear(int vacationPeriodThisYear) {
		this.vacationPeriodThisYear = vacationPeriodThisYear;
	}

	public int getPeriodLessFive() {
		return periodLessFive;
	}

	public void setPeriodLessFive(int periodLessFive) {
		this.periodLessFive = periodLessFive;
	}


//	public HrsVacationCalc getVacationCalc() {
//		return vacationCalc;
//	}
//
//	public void setVacationCalc(HrsVacationCalc vacationCalc) {
//		this.vacationCalc = vacationCalc;
//	}


	
}

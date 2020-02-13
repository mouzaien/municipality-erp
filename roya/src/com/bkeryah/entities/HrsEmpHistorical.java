package com.bkeryah.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "HRS_EMP_HISTORICAL")
public class HrsEmpHistorical implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// @Id
	// @GenericGenerator(name = "generator", strategy = "increment")
	// @GeneratedValue(generator = "generator")
	@EmbeddedId
	private HrsEmpHistoricalId id;

	// private Integer empno;
	// @Id
	// @Column(name = "SERIAL")
	// private Integer stepId;

	@Column(name = "CATCOD")
	private Integer CATegoryId;
	@Column(name = "INCOMENO")
	private Integer incomNo;
	@Column(name = "INCOMEDT")
	private String incomDate;
	@Column(name = "EXCNO")
	private String executeNo;
	@Column(name = "EXCDT")
	private String executeDate;
	@Column(name = "EXCSRC")
	private String EXeCuteSouRCe;
	@Column(name = "CLSSCOD")
	private Integer classNumber;
	@Column(name = "RANKCOD")
	private Integer rankNumber;
	@Column(name = "DEPTJOB")
	private String deptJob;
	@Column(name = "BSCSAL")
	private Integer basicSalary;
	@Column(name = "TRANS")
	private Integer transferSalary;
	@Column(name = "MANDIN")
	private Integer mandateInner;
	@Column(name = "MANDout")
	private Integer mandateOuter;
	// نقل/ترقيه/كف يد/نقل بين الادارات...........
	@Column(name = "RECTYPE")
	private Integer RecordType;
	@Column(name = "CBY")
	private Integer createdBy;
	@Column(name = "USER_DEPT_ID")
	private Integer userDept;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CBY", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private ArcUsers arcUser;

	@Column(name = "CIN")
	private Date createDate;
	@Column(name = "OLDRANKCOD")
	private Integer oldRanknumber;
	@Column(name = "OLDCLSSCOD")
	private Integer oldClassnumber;

	@Column(name = "JOBCOD")
	private String jobcode;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "JOBCOD", referencedColumnName = "ID", insertable = false, updatable = false)
	private HrsGovJob4 govJob4;

	@Column(name = "OLDJOBCOD")
	private String oldJobCode;
	@Column(name = "DEPTCOD")
	private String departmentnumber;
	@Column(name = "OLDDEPTCOD")
	private String oldDepartmentnumber;
	@Column(name = "INCOMEYEAR")
	private Integer incomYear;
	@Column(name = "JOBNO")
	private Integer jobNumber;
	@Column(name = "OLDJOBNO")
	private String oldJobNumber;
	@Column(name = "JOBYEAR")
	private String jobYear;
	@Column(name = "OLDJOBYEAR")
	private String oldJobYear;
	@Column(name = "FLG")
	private int flag;
	@Column(name = "OLDBSCSAL")
	private Integer oldBasicSalary;
	@Column(name = "oldTRANS")
	private Integer oldTransferSalary;
	@Column(name = "oldMANDIN")
	private Integer oldMandateInner;
	@Column(name = "oldMANDout")
	private Integer oldMandateOuter;
	@Transient
	private String empName;
	@Transient
	private String nationality;
	@Transient
	private String nationalId;
	@Transient
	private String rankName;
	@Transient
	private String oldJobName;

	@Transient
	private Date incomDateGrig;
	@Transient
	private Date executeDateGrig;
	@Transient
	private String incomDateStringSort;
	@Transient
	private String executeDateStringSort;

	// Nationalities
	@Formula("(select n.NAME from SYS002 n where  n.ID = CATCOD)")
	private String CATegoryName;

	///// name
	@Formula("(select n.name from SYS038 n where  n.ID = RECTYPE)")
	private String recNum; 
	//ربط wrkdept>>
//	@Formula("(select u.DEPT_NAME from WRK_DEPT u where u.EMPNO = DEPT_ID )")
//	private String deptName;
	
	// rankNumber
	// @Formula("(select n.ID from SYS038 n where n.EXCSRC = NAME)")
	// private String exeCuteNum;

	// @Formula("(select u.DEPT_NAME from WRK_DEPT u where u.EMPNO = DEPT_ID )")
	// private String deptName;
	// public Integer getEmpno() {
	// return empno;
	// }
	//
	// public void setEmpno(Integer empno) {
	// this.empno = empno;
	// }
	//
	// public Integer getStepId() {
	// return stepId;
	// }
	//
	// public void setStepId(Integer stepId) {
	// this.stepId = stepId;
	// }

	public Integer getCATegoryId() {
		return CATegoryId;
	}

	public void setCATegoryId(Integer cATegoryId) {
		CATegoryId = cATegoryId;
	}

	public Integer getIncomNo() {
		return incomNo;
	}

	public void setIncomNo(Integer incomNo) {
		this.incomNo = incomNo;
	}

	public String getIncomDate() {
		return incomDate;
	}

	public void setIncomDate(String incomDate) {
		this.incomDate = incomDate;
	}

	public String getExecuteNo() {
		return executeNo;
	}

	public void setExecuteNo(String executeNo) {
		this.executeNo = executeNo;
	}

	public String getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(String executeDate) {
		this.executeDate = executeDate;
	}

	public String getEXeCuteSouRCe() {
		return EXeCuteSouRCe;
	}

	public void setEXeCuteSouRCe(String eXeCuteSouRCe) {
		EXeCuteSouRCe = eXeCuteSouRCe;
	}

	public Integer getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(Integer classNumber) {
		this.classNumber = classNumber;
	}

	public Integer getRankNumber() {
		return rankNumber;
	}

	public void setRankNumber(Integer rankNumber) {
		this.rankNumber = rankNumber;
	}

	public String getDeptJob() {
		return deptJob;
	}

	public void setDeptJob(String deptJob) {
		this.deptJob = deptJob;
	}

	public Integer getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(Integer basicSalary) {
		this.basicSalary = basicSalary;
	}

	public Integer getTransferSalary() {
		return transferSalary;
	}

	public void setTransferSalary(Integer transferSalary) {
		this.transferSalary = transferSalary;
	}

	public Integer getMandateInner() {
		return mandateInner;
	}

	public void setMandateInner(Integer mandateInner) {
		this.mandateInner = mandateInner;
	}

	public Integer getMandateOuter() {
		return mandateOuter;
	}

	public void setMandateOuter(Integer mandateOuter) {
		this.mandateOuter = mandateOuter;
	}

	public Integer getRecordType() {
		return RecordType;
	}

	public void setRecordType(Integer recordType) {
		RecordType = recordType;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public ArcUsers getArcUser() {
		return arcUser;
	}

	public void setArcUser(ArcUsers arcUser) {
		this.arcUser = arcUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getOldRanknumber() {
		return oldRanknumber;
	}

	public void setOldRanknumber(Integer oldRanknumber) {
		this.oldRanknumber = oldRanknumber;
	}

	public Integer getOldClassnumber() {
		return oldClassnumber;
	}

	public void setOldClassnumber(Integer oldClassnumber) {
		this.oldClassnumber = oldClassnumber;
	}

	public String getJobcode() {
		return jobcode;
	}

	public void setJobcode(String jobcode) {
		this.jobcode = jobcode;
	}

	public String getOldJobCode() {
		return oldJobCode;
	}

	public void setOldJobCode(String oldJobCode) {
		this.oldJobCode = oldJobCode;
	}

	public String getDepartmentnumber() {
		return departmentnumber;
	}

	public void setDepartmentnumber(String departmentnumber) {
		this.departmentnumber = departmentnumber;
	}

	public String getOldDepartmentnumber() {
		return oldDepartmentnumber;
	}

	public void setOldDepartmentnumber(String oldDepartmentnumber) {
		this.oldDepartmentnumber = oldDepartmentnumber;
	}

	public Integer getIncomYear() {
		return incomYear;
	}

	public void setIncomYear(Integer incomYear) {
		this.incomYear = incomYear;
	}

	public Integer getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(Integer jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getOldJobNumber() {
		return oldJobNumber;
	}

	public void setOldJobNumber(String oldJobNumber) {
		this.oldJobNumber = oldJobNumber;
	}

	public String getJobYear() {
		return jobYear;
	}

	public void setJobYear(String jobYear) {
		this.jobYear = jobYear;
	}

	public String getOldJobYear() {
		return oldJobYear;
	}

	public void setOldJobYear(String oldJobYear) {
		this.oldJobYear = oldJobYear;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Integer getOldBasicSalary() {
		return oldBasicSalary;
	}

	public void setOldBasicSalary(Integer oldBasicSalary) {
		this.oldBasicSalary = oldBasicSalary;
	}

	public Integer getOldTransferSalary() {
		return oldTransferSalary;
	}

	public void setOldTransferSalary(Integer oldTransferSalary) {
		this.oldTransferSalary = oldTransferSalary;
	}

	public Integer getOldMandateInner() {
		return oldMandateInner;
	}

	public void setOldMandateInner(Integer oldMandateInner) {
		this.oldMandateInner = oldMandateInner;
	}

	public Integer getOldMandateOuter() {
		return oldMandateOuter;
	}

	public void setOldMandateOuter(Integer oldMandateOuter) {
		this.oldMandateOuter = oldMandateOuter;
	}

	public HrsGovJob4 getGovJob4() {
		return govJob4;
	}

	public void setGovJob4(HrsGovJob4 govJob4) {
		this.govJob4 = govJob4;
	}

	public HrsEmpHistoricalId getId() {
		return id;
	}

	public void setId(HrsEmpHistoricalId id) {
		this.id = id;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public String getOldJobName() {
		return oldJobName;
	}

	public void setOldJobName(String oldJobName) {
		this.oldJobName = oldJobName;
	}

	public Date getIncomDateGrig() {
		return incomDateGrig;
	}

	public void setIncomDateGrig(Date incomDateGrig) {
		this.incomDateGrig = incomDateGrig;
	}

	public Date getExecuteDateGrig() {
		return executeDateGrig;
	}

	public void setExecuteDateGrig(Date executeDateGrig) {
		this.executeDateGrig = executeDateGrig;
	}

	public String getIncomDateStringSort() {
		return incomDateStringSort;
	}

	public void setIncomDateStringSort(String incomDateStringSort) {
		this.incomDateStringSort = incomDateStringSort;
	}

	public String getExecuteDateStringSort() {
		return executeDateStringSort;
	}

	public void setExecuteDateStringSort(String executeDateStringSort) {
		this.executeDateStringSort = executeDateStringSort;
	}

	public String getCATegoryName() {
		return CATegoryName;
	}

	public void setCATegoryName(String cATegoryName) {
		CATegoryName = cATegoryName;
	}

	public String getRecNum() {
		return recNum;
	}

	public void setRecNum(String recNum) {
		this.recNum = recNum;
	}

	public Integer getUserDept() {
		return userDept;
	}

	public void setUserDept(Integer userDept) {
		this.userDept = userDept;
	}

	// public String getExeCuteNum() {
	// return exeCuteNum;
	// }
	//
	// public void setExeCuteNum(String exeCuteNum) {
	// this.exeCuteNum = exeCuteNum;
	// }

}

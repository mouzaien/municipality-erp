package com.bkeryah.entities.investment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

import utilities.ContractOperationEnum;
import utilities.Utils;

@Entity
@Table(name = "CONTRACT_DIRECT")
public class ContractDirect implements Comparable<ContractDirect> {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "CONTRACT_NUM")
	private Integer contractNum;
	@Column(name = "CONTRACT_DATE")
	private Date contractDate;
	@Column(name = "START_DATE")
	private String startDate;
	@Column(name = "END_DATE")
	private String endDate;
	@Column(name = "REAL_ESTATE_ID")
	private Integer realEstateId;
	@Column(name = "INVESTOR_ID")
	private Integer investorId;
	@Column(name = "INV_REPRES_NAME")
	private String invRepresentName;
	@Column(name = "INV_REPRES_FUNCT")
	private String invRepresentFunct;
	@Column(name = "INV_REPRES_NAT_ID")
	private Long invRepresentNatId;
	@Column(name = "INV_REPRES_ID_PLACE")
	private String invRepresentIdPlace;
	@Column(name = "INV_REPRES_ID_DATE")
	private String invRepresentIdDate;
	@Column(name = "CONTRACT_TYPE_ID")
	private Integer contractTypeId;
	@ManyToOne
	@JoinColumn(name = "REAL_ESTATE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private RealEstate realEstate;
	@ManyToOne
	@JoinColumn(name = "INVESTOR_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private Investor investor;
	@Column(name = "CONTRACT_YEARS")
	private Integer contractYears;
	@Column(name = "ANNUAL_RENT")
	private Double annualRent;
	@Column(name = "PROC_CONST_PERIOD")
	private Integer processPeriod;
	@Column(name = "CONTRACT_INTRO")
	private String introduction;
	@Column(name = "CANCEL_REASON_ID")
	private Integer cancelReasonId;
	// 1: new contract, 2: renew, 3:cancel
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "OBSERVATION")
	private String observation;

	@Column(name = "CONTRACT_MAIN_CATG")
	private Integer contractMaincatgId;
	@Column(name = "CONTRACT_SUB_CATG")
	private Integer contractSubcatgId;
	@Column(name = "CONTRACT_STATUS")
	private Integer contractStatId;

	// @Formula("(select cb.bill_id from contract_bills cb where cb.contract_id
	// = id and status = 0)")
	// private Integer billNo;
	@Transient
	private List<ContractOperationEnum> actionsList = new ArrayList<>();
	@Transient
	private int operationId;
	@Transient
	private String statusName;
	@Formula("(select SUM(b.PAY_AMOUNT) from PAY_LIC_BILLS b where b.BILL_STATUS = 1 and b.LIC_NO = ID and b.LIC_TYPE= 'I')")
	private Double payedBills;
	@Transient
	private Integer payStatus;
	@Transient
	private String payStatusName;
	@Transient
	private Integer finished;

	@Transient
	private String startContDate;
	@Transient
	private String endContDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getContractNum() {
		return contractNum;
	}

	public void setContractNum(Integer contractNum) {
		this.contractNum = contractNum;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getInvRepresentName() {
		return invRepresentName;
	}

	public void setInvRepresentName(String invRepresentName) {
		this.invRepresentName = invRepresentName;
	}

	public String getInvRepresentFunct() {
		return invRepresentFunct;
	}

	public void setInvRepresentFunct(String invRepresentFunct) {
		this.invRepresentFunct = invRepresentFunct;
	}

	public Long getInvRepresentNatId() {
		return invRepresentNatId;
	}

	public void setInvRepresentNatId(Long invRepresentNatId) {
		this.invRepresentNatId = invRepresentNatId;
	}

	public String getInvRepresentIdPlace() {
		return invRepresentIdPlace;
	}

	public void setInvRepresentIdPlace(String invRepresentIdPlace) {
		this.invRepresentIdPlace = invRepresentIdPlace;
	}

	public String getInvRepresentIdDate() {
		return invRepresentIdDate;
	}

	public void setInvRepresentIdDate(String invRepresentIdDate) {
		this.invRepresentIdDate = invRepresentIdDate;
	}

	public Integer getContractYears() {
		return contractYears;
	}

	public void setContractYears(Integer contractYears) {
		this.contractYears = contractYears;
	}

	public Double getAnnualRent() {
		return annualRent;
	}

	public void setAnnualRent(Double annualRent) {
		this.annualRent = annualRent;
	}

	public Integer getProcessPeriod() {
		return processPeriod;
	}

	public void setProcessPeriod(Integer processPeriod) {
		this.processPeriod = processPeriod;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public Integer getRealEstateId() {
		return realEstateId;
	}

	public void setRealEstateId(Integer realEstateId) {
		this.realEstateId = realEstateId;
	}

	public Integer getInvestorId() {
		return investorId;
	}

	public void setInvestorId(Integer investorId) {
		this.investorId = investorId;
	}

	public RealEstate getRealEstate() {
		return realEstate;
	}

	public void setRealEstate(RealEstate realEstate) {
		this.realEstate = realEstate;
	}

	public Investor getInvestor() {
		return investor;
	}

	public void setInvestor(Investor investor) {
		this.investor = investor;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public List<ContractOperationEnum> getActionsList() {
		return actionsList;
	}

	public void setActionsList(List<ContractOperationEnum> actionsList) {
		this.actionsList = actionsList;
	}

	public int getOperationId() {
		return operationId;
	}

	public void setOperationId(int operationId) {
		this.operationId = operationId;
	}

	public Integer getStatus() {
		payStatus = isPayed();
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public int compareTo(ContractDirect obj) {
		if (this.id > obj.id)
			return -1;
		else if (this.id < obj.id)
			return 1;
		else
			return 0;
	}
	// public Integer getBillNo() {
	// return billNo;
	// }

	public String getObservation() {
		return observation;
	}

	// public void setBillNo(Integer billNo) {
	// this.billNo = billNo;
	// }
	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Integer getCancelReasonId() {
		return cancelReasonId;
	}

	public void setCancelReasonId(Integer cancelReasonId) {
		this.cancelReasonId = cancelReasonId;
	}

	public String getStatusName() {
		switch (status) {
		case 1:
			statusName = Utils.loadMessagesFromFile("new");
			break;
		case 2:
			statusName = Utils.loadMessagesFromFile("renew");
			break;
		case 3:
			statusName = Utils.loadMessagesFromFile("canceled");
			break;
		default:
			break;
		}
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Integer getContractTypeId() {
		return contractTypeId;
	}

	public void setContractTypeId(Integer contractTypeId) {
		this.contractTypeId = contractTypeId;
	}

	public Double getPayedBills() {
		return payedBills;
	}

	public void setPayedBills(Double payedBills) {
		this.payedBills = payedBills;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	private Integer isPayed() {
		if ((payedBills == null) || (payedBills == 0))
			return 0;
		Integer days = (int) (Utils.calculateDiffDaysHijriDate(startDate, endDate) - processPeriod);
		Integer contrYears = (int) (days / 360);
		if (payedBills < (contrYears * annualRent))
			return 0;
		return 1;
	}

	public String getPayStatusName() {
		if (payStatus == null)
			payStatus = isPayed();
		switch (payStatus) {
		case 0:
			payStatusName = Utils.loadMessagesFromFile("not.paid");
			break;
		case 1:
			payStatusName = Utils.loadMessagesFromFile("paid");
			break;
		default:
			break;
		}
		return payStatusName;
	}

	public Integer getFinished() {
		return finished;
	}

	public void setFinished(Integer finished) {
		this.finished = finished;
	}

	public Integer getContractMaincatgId() {
		return contractMaincatgId;
	}

	public void setContractMaincatgId(Integer contractMaincatgId) {
		this.contractMaincatgId = contractMaincatgId;
	}

	public Integer getContractSubcatgId() {
		return contractSubcatgId;
	}

	public void setContractSubcatgId(Integer contractSubcatgId) {
		this.contractSubcatgId = contractSubcatgId;
	}

	public Integer getContractStatId() {
		return contractStatId;
	}

	public void setContractStatId(Integer contractStatId) {
		this.contractStatId = contractStatId;
	}

	public String getStartContDate() {
		try {
			Date date = Utils.convertHDateToGDate(getStartDate());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			startContDate = sdf.format(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return startContDate;
	}

	public void setStartContDate(String startContDate) {
		this.startContDate = startContDate;
	}

	public String getEndContDate() {
		try {
			Date date = Utils.convertHDateToGDate(getEndDate());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			endContDate = sdf.format(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return endContDate;
	}

	public void setEndContDate(String endContDate) {
		this.endContDate = endContDate;
	}

}
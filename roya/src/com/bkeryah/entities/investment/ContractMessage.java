package com.bkeryah.entities.investment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "CONTRACT_MESSAGE")
public class ContractMessage {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "CONTRACT_ID")
	private Integer contractId;
	@Column(name = "DATE_OF_SEND_H")
	private String sendDateH;
	@Column(name = "DATE_OF_SEND_G")
	private Date sendDateG;
	@Column(name = "PHONE_NO")
	private String phoneNo;
	@Column(name = "BILL_NO")
	private Long billNo;
	@Column(name = "FEES_ID")
	private Integer feesId;
	@Column(name = "TRADE_RECORD")
	private Long treadeRecord;
	@Column(name = "MESSAGE_TEXT")
	private String messageTxt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public String getSendDateH() {
		return sendDateH;
	}

	public void setSendDateH(String sendDateH) {
		this.sendDateH = sendDateH;
	}

	public Date getSendDateG() {
		return sendDateG;
	}

	public void setSendDateG(Date sendDateG) {
		this.sendDateG = sendDateG;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Long getBillNo() {
		return billNo;
	}

	public void setBillNo(Long billNo) {
		this.billNo = billNo;
	}

	public Integer getFeesId() {
		return feesId;
	}

	public void setFeesId(Integer feesId) {
		this.feesId = feesId;
	}

	public Long getTreadeRecord() {
		return treadeRecord;
	}

	public void setTreadeRecord(Long treadeRecord) {
		this.treadeRecord = treadeRecord;
	}

	public String getMessageTxt() {
		return messageTxt;
	}

	public void setMessageTxt(String messageTxt) {
		this.messageTxt = messageTxt;
	}
}

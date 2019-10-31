package com.bkeryah.penalties;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "FINE_SECTIONS")
public class FineSection implements Serializable {
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "ORDERITEM")
	private Integer orderItem;
	@OneToMany(mappedBy = "fineSection", fetch=FetchType.LAZY)
	private List<NotifFinesDetails> notifFinesDetailsList;
	@ManyToMany(cascade = {
			javax.persistence.CascadeType.ALL })
	@JoinTable(name = "REQ_FINE_SECTIONS", joinColumns = {
			@JoinColumn(name = "SECTIONID", referencedColumnName = "ORDERITEM") }, inverseJoinColumns = {
					@JoinColumn(name = "FINEID", referencedColumnName = "ID") })
	private List<ReqFinesSetup> finesList;
	@Transient
	private ReqFinesSetup selectedFine = new ReqFinesSetup();
//	@Formula("(select rd.FINE_CODE from REQ_FINES_DETAILS rd, NOTIF_FINES_DETAILS nd,fine_sections fs,REQ_FINE_SECTIONS rfs,REQ_FINES_SETUP rs where ND.ORDER_ID=ORDERITEM and ORDERITEM=RFS.SECTIONID and RFS.FINEID=RS.ID and RS.FINE_CODE=RD.ID)")
//	private Integer selectedFineId;
	
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
	public Integer getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(Integer orderItem) {
		this.orderItem = orderItem;
	}
	public List<NotifFinesDetails> getNotifFinesDetailsList() {
		return notifFinesDetailsList;
	}
	public void setNotifFinesDetailsList(List<NotifFinesDetails> notifFinesDetailsList) {
		this.notifFinesDetailsList = notifFinesDetailsList;
	}
	public List<ReqFinesSetup> getFinesList() {
		return finesList;
	}
	public void setFinesList(List<ReqFinesSetup> finesList) {
		this.finesList = finesList;
	}
	public ReqFinesSetup getSelectedFine() {
		return selectedFine;
	}
	public void setSelectedFine(ReqFinesSetup selectedFine) {
		this.selectedFine = selectedFine;
	}
	
	public void loadFineSetup(){
		//notifFinesMaster.getNotifFinesDetailsList().stream().filter(det -> det.getFineSection().getSelectedFine().getId().equals(details.getFineSection().getSelectedFine().getId())).findFirst();
	}
//	public Integer getSelectedFineId() {
//		return selectedFineId;
//	}
//	public void setSelectedFineId(Integer selectedFineId) {
//		this.selectedFineId = selectedFineId;
//	}

}
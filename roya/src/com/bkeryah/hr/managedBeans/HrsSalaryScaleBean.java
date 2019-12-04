package com.bkeryah.hr.managedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.RowEditEvent;
import com.bkeryah.entities.HrsSalaryScale;
import com.bkeryah.entities.HrsSalaryScaleDgrs;
import com.bkeryah.hr.entities.HrsSalaryScaleOrder;
import com.bkeryah.service.IDataAccessService;
import utilities.MsgEntry;

@ManagedBean
@ViewScoped
public class HrsSalaryScaleBean {
	@ManagedProperty(value = "#{dataAccessService}")
	private IDataAccessService dataAccessService;
	private List<HrsSalaryScale> salaryScaleList;
	private List<HrsSalaryScale> filtredSalaryScaleList;
	private List<HrsSalaryScaleOrder> salaryScaleOrderList;
	private List<HrsSalaryScaleDgrs> salaryScaleDgrsList;
	private HrsSalaryScale hrsSalaryScaleSelected;
	private Integer orderId;
	private String orderSrc;
	private String orderDate;
	private String orderStrtdate;

	public List<HrsSalaryScaleDgrs> getSalaryScaleDgrsList() {
		return salaryScaleDgrsList;
	}

	public void setSalaryScaleDgrsList(List<HrsSalaryScaleDgrs> salaryScaleDgrsList) {
		this.salaryScaleDgrsList = salaryScaleDgrsList;
	}

	public HrsSalaryScale getHrsSalaryScaleSelected() {
		return hrsSalaryScaleSelected;
	}

	public void setHrsSalaryScaleSelected(HrsSalaryScale hrsSalaryScaleSelected) {
		this.hrsSalaryScaleSelected = hrsSalaryScaleSelected;
		if (this.hrsSalaryScaleSelected != null)
			salaryScaleDgrsList = dataAccessService.loadAllSalayScaleDgrs(orderId,
					this.hrsSalaryScaleSelected.getId().getRankCode());
	}

	public String getOrderSrc() {
		return orderSrc;
	}

	public void setOrderSrc(String orderSrc) {
		this.orderSrc = orderSrc;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderStrtdate() {
		return orderStrtdate;
	}

	public void setOrderStrtdate(String orderStrtdate) {
		this.orderStrtdate = orderStrtdate;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public List<HrsSalaryScaleOrder> getSalaryScaleOrderList() {
		return salaryScaleOrderList;
	}

	public void setSalaryScaleOrderList(List<HrsSalaryScaleOrder> salaryScaleOrderList) {
		this.salaryScaleOrderList = salaryScaleOrderList;
	}

	public List<HrsSalaryScale> getFiltredSalaryScaleList() {
		return filtredSalaryScaleList;
	}

	public void setFiltredSalaryScaleList(List<HrsSalaryScale> filtredSalaryScaleList) {
		this.filtredSalaryScaleList = filtredSalaryScaleList;
	}

	@PostConstruct
	public void init() {
		salaryScaleList = dataAccessService.loadAllSalayScales(orderId);
		salaryScaleOrderList = dataAccessService.loadAllSalayScaleOrders();
		updateOrder();
	}

	public void updateOrder() {
		for (HrsSalaryScaleOrder order : salaryScaleOrderList) {
			if (order.getId().equals(orderId)) {
				orderSrc = order.getDescsource();
				orderDate = order.getDesdate();
				orderStrtdate = order.getStrtdate();
				salaryScaleList = dataAccessService.loadAllSalayScales(orderId);
			}
		}

	}

	public String loadSelectedScale() {
		salaryScaleDgrsList = dataAccessService.loadAllSalayScaleDgrs(orderId,
				this.hrsSalaryScaleSelected.getId().getRankCode());
		return "";
	}

	public void onRowEdit(RowEditEvent event) {
		HrsSalaryScale salaryScale = ((HrsSalaryScale) event.getObject());
		dataAccessService.updateObject(salaryScale);
	}

	public void onRowCancel(RowEditEvent event) {
		MsgEntry.addAcceptFlashInfoMessage("تم إلغاء العملية");

	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

	public List<HrsSalaryScale> getSalaryScaleList() {
		return salaryScaleList;
	}

	public void setSalaryScaleList(List<HrsSalaryScale> salaryScaleList) {
		this.salaryScaleList = salaryScaleList;
	}

}

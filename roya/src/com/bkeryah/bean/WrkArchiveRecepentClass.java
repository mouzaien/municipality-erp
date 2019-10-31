package com.bkeryah.bean;

public class WrkArchiveRecepentClass {
	private String recepiantId;
	private String recepiantTitle;
	public String getRecepiantId() {
		return recepiantId;
	}
	public void setRecepiantId(String recepiantId) {
		this.recepiantId = recepiantId;
	}
	public String getRecepiantTitle() {
		return recepiantTitle;
	}
	public void setRecepiantTitle(String recepiantTitle) {
		this.recepiantTitle = recepiantTitle;
	}
	public WrkArchiveRecepentClass(String recepiantId, String recepiantTitle) {
		super();
		this.recepiantId = recepiantId;
		this.recepiantTitle = recepiantTitle;
	}
	public WrkArchiveRecepentClass() {
		super();
	}
	
	
	
	
}

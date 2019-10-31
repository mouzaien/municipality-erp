package com.bkeryah.shared.beans;

import java.util.ArrayList;
import java.util.List;

public class HijriDayRow {
	private int id;
	private String name;
	private List<HijriDay> lstDays = new ArrayList<>();

	public List<HijriDay> getLstDays() {
		return lstDays;
	}

	public void setLstDays(List<HijriDay> lstDays) {
		this.lstDays = lstDays;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

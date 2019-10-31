package com.bkeryah.penalties;

public class ActivityType {
	private Integer id;
	private String name;
	private String fname;

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public ActivityType(Integer id, String name) {
		this.id = id;
		this.name = name;
		this.fname=name;
	}

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
	@Override
	public String toString() {
		return this.name;
	}
}

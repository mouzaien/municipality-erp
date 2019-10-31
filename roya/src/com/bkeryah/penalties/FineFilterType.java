package com.bkeryah.penalties;

public class FineFilterType {
	private Integer id;
	private String name;

	public FineFilterType(Integer id, String name) {
		this.id = id;
		this.name = name;
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

	@Override
	public boolean equals(Object obj) {
		return ((FineFilterType) obj).getId().equals(this.getId());
	}
}

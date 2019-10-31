package com.bkeryah.bean;

public class RecordTypeClass {
	String recordId;
	String screenId;
	
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getScreenId() {
		return screenId;
	}
	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}
	public RecordTypeClass(String recordId, String screenId) {
		super();
		this.recordId = recordId;
		this.screenId = screenId;
	}

	
	
}

package com.bkeryah.model;

import java.io.InputStream;

public class AttachmentModel {

	private String realName;
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	private String attachRealName;
	private InputStream attachStream;
	private double attachSize;
	private String attachExt;
	public String getAttachRealName() {
		return attachRealName;
	}
	public void setAttachRealName(String attachRealName) {
		this.attachRealName = attachRealName;
	}
	public InputStream getAttachStream() {
		return attachStream;
	}
	public void setAttachStream(InputStream attachStream) {
		this.attachStream = attachStream;
	}
	public double getAttachSize() {
		return attachSize;
	}
	public void setAttachSize(double attachSize) {
		this.attachSize = attachSize;
	}
	public String getAttachExt() {
		return attachExt;
	}
	public void setAttachExt(String attachExt) {
		this.attachExt = attachExt;
	}
	
	@Override
	public boolean equals(Object obj) {
	return this.getAttachRealName().equals(((AttachmentModel)obj).getAttachRealName());
	}
	
}

package com.bkeryah.bean;

import java.io.Serializable;
import java.util.List;

public class StoreArtsRequestModel implements Serializable {

	private Integer NUM;
	private String CODE;
	private String NAME;
	private String STRNAME;
	private Integer AVAILABLE;
	private Integer RESERVED;

	public Integer getNUM() {
		return NUM;
	}

	public void setNUM(Integer nUM) {
		NUM = nUM;
	}

	public String getCODE() {
		return CODE;
	}

	public void setCODE(String cODE) {
		CODE = cODE;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getSTRNAME() {
		return STRNAME;
	}

	public void setSTRNAME(String sTRNAME) {
		STRNAME = sTRNAME;
	}

	public Integer getAVAILABLE() {
		return AVAILABLE;
	}

	public void setAVAILABLE(Integer aVAILABLE) {
		AVAILABLE = aVAILABLE;
	}

	public Integer getRESERVED() {
		return RESERVED;
	}

	public void setRESERVED(Integer rESERVED) {
		RESERVED = rESERVED;
	}

}

package com.bkeryah.managedBean;

import java.util.HashMap;
import java.util.Map;

import utilities.Utils;

public class BarcodePrinter {
	protected String printBarcodeReport(Integer NewArcRecordId, Integer outOrIntNo) {
		String reportName = "/reports/inbarcode.jasper";
		if (outOrIntNo.equals(2))
			reportName = "/reports/outbarcode.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p1", NewArcRecordId);
		parameters.put("compName", Utils.loadMessagesFromFile("comp.name"));
		Utils.printPdfReport(reportName, parameters);
		return "";
	}
}

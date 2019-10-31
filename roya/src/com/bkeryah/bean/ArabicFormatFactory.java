/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.bean;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import net.sf.jasperreports.engine.util.DefaultFormatFactory;
/**
 *
 * @author IbrahimDarwiesh
 */
public class ArabicFormatFactory extends DefaultFormatFactory {
    
        @Override
	public NumberFormat createNumberFormat(String pattern, Locale locale) {
		NumberFormat numberFormat = super.createNumberFormat(pattern, locale);
		if (locale.getLanguage().equals("ar")) {
			DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
			DecimalFormatSymbols symbols = decimalFormat.getDecimalFormatSymbols();
			symbols.setZeroDigit('\u0660');
			decimalFormat.setDecimalFormatSymbols(symbols);
		}
		return numberFormat;
	}

}

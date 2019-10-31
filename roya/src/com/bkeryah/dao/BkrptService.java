/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author IbrahimDarwiesh
 */
public class BkrptService extends HttpServlet {

    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = DataBaseConnectionClass.getConnection();
        request.setCharacterEncoding("UTF-8");
        String reportName = request.getParameter("rpt");
        Map parm = new HashMap();
        parm = new DataAccessImpl().findAllReportParameter(request.getQueryString());
        System.err.println(parm.size());
        InputStream input = new FileInputStream(new File(getServletConfig().getServletContext().getRealPath("/rpt/" + reportName + ".jrxml")));
        JasperDesign design = null;
        try {
            design = JRXmlLoader.load(input);
        } catch (JRException e) {
        }
        JasperReport report = null;
        try {
            report = JasperCompileManager.compileReport(design);
            report = (JasperReport) JRLoader.loadObject(input);
            report.setWhenNoDataType(WhenNoDataTypeEnum.NO_DATA_SECTION);
        } catch (JRException e) {
        }
        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(report, parm, conn);
            jasperPrint.setLocaleCode(Locale.getDefault().toString());
        } catch (JRException e) {
        }
        OutputStream ouputStream = response.getOutputStream();

        response.setContentType("application/pdf");
        response.setHeader("Cache-Control", "max-age=0");
        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
        try {
            exporter.exportReport();
        } catch (JRException e) {
            throw new ServletException(e);
        } finally {
            if (ouputStream != null) {
                try {
                    ouputStream.flush();
                    ouputStream.close();

                } catch (IOException ex) {
                    throw (ex);
                }
            }
        }
    }
}

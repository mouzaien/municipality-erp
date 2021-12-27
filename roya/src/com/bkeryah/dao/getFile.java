/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkeryah.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;

import oracle.jdbc.OracleTypes;

/**
 *
 * @author Ibrahim Darwiesh
 */
public class getFile extends HttpServlet {

    Connection connection = DataBaseConnectionClass.getConnection();
    DataAccess dataAccess = new DataAccessImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        InputStream sFile;
        try {
            String attid = request.getParameter("fileid");
            attid = this.dataAccess.decrypt(attid);
            String sql = "{ call new_pkg_webkit.PRC_GET_FILE_BY_fileid(?,?)}";
            try {
                CallableStatement callableStatement = connection.prepareCall(sql);
                callableStatement.setString(1, attid);
                callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
                callableStatement.executeUpdate();
                /*
                 this is the result set of files by query specified ....
                 change this to get files 
                 */

                ResultSet rs = (ResultSet) callableStatement.getObject(2);
                if (rs.next()) {
                    byte[] bytearray = new byte[1048576];
                    int size = 0;
                    sFile = rs.getBinaryStream("ATTACHMENT_FILE");
                    response.reset();

                    response.setContentType("text/html; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(rs.getString("ATTACHMENT_FILE_REAL_NAME"), "UTF-8") + "\"");
                    response.setHeader("cache-control", "no-cache");
                    while ((size = sFile.read(bytearray)) != -1) {
                        response.getOutputStream().write(bytearray, 0, size);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
           // LogManager.getLogger(getFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

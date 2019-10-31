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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author darwiesh
 */
@WebServlet(name = "usersign", urlPatterns = {"/usersign"})
public class usersign extends HttpServlet {

	
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    Connection connection = DataBaseConnectionClass.getConnection();
    DataAccess dataAccess = new DataAccessImpl();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        InputStream sFile;
        try {
            String uid = request.getParameter("uid");
            String styp = request.getParameter("styp");
           // uid=this.dataAccess.decrypt(uid);
            String sql = "{ call new_pkg_webkit.PRC_GET_USER_WRK_PROFILE_REC(?,?)}";  
            //FOR SIGN
            if(Integer.parseInt(styp) == 1){
                
                CallableStatement callableStatement = connection.prepareCall(sql);
                callableStatement.setString(1, uid);
                callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
                callableStatement.executeUpdate();
                ResultSet rs = (ResultSet) callableStatement.getObject(2);
                if (rs.next()) {
                   
                    byte[] bytearray = new byte[1048576];
                    int size = 0;
                    sFile = rs.getBinaryStream("SIGN");
                    response.reset();

                    
                    response.setContentType("image/jpeg");
                    response.setCharacterEncoding("UTF-8");
                   // response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(rs.getString("ATTACHMENT_FILE_REAL_NAME"), "UTF-8") + "\"");
                    response.setHeader("cache-control", "no-cache");
                    while ((size = sFile.read(bytearray)) != -1) {
                        response.getOutputStream().write(bytearray, 0, size);
                    }
                }
            }
            //For TA'shera
            if(Integer.parseInt(styp) == 2){
                 
                CallableStatement callableStatement = connection.prepareCall(sql);
                callableStatement.setString(1, uid);
                callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
                callableStatement.executeUpdate();
                ResultSet rs = (ResultSet) callableStatement.getObject(2);
                if (rs.next()) {
                    byte[] bytearray = new byte[1048576];
                    int size = 0;
                    sFile = rs.getBinaryStream("SIGN2");
                    response.reset();

                    
                    response.setContentType("image/jpeg");
                    response.setCharacterEncoding("UTF-8");
                   // response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(rs.getString("ATTACHMENT_FILE_REAL_NAME"), "UTF-8") + "\"");
                    response.setHeader("cache-control", "no-cache");
                    while ((size = sFile.read(bytearray)) != -1) {
                        response.getOutputStream().write(bytearray, 0, size);
                    }
                }
            }
            //for sign about 
            if(Integer.parseInt(styp) == 3){
                 
                CallableStatement callableStatement = connection.prepareCall(sql);
                callableStatement.setString(1, uid);
                callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
                callableStatement.executeUpdate();
                ResultSet rs = (ResultSet) callableStatement.getObject(2);
                if (rs.next()) {
                    byte[] bytearray = new byte[1048576];
                    int size = 0;
                    sFile = rs.getBinaryStream("SIGN3");
                    response.reset();

                    
                    response.setContentType("image/jpeg");
                    response.setCharacterEncoding("UTF-8");
                   // response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(rs.getString("ATTACHMENT_FILE_REAL_NAME"), "UTF-8") + "\"");
                    response.setHeader("cache-control", "no-cache");
                    while ((size = sFile.read(bytearray)) != -1) {
                        response.getOutputStream().write(bytearray, 0, size);
                    }
                }
            }
        } catch (Exception e) {
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
        processRequest(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

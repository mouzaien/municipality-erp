package com.bkeryah.testssss;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bkeryah.licences.AbstractExecutor;
import com.bkeryah.licences.BkServicesFactory;

import flexjson.JSONSerializer;

public class BkeryahServices extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		BkServicesFactory factory = BkServicesFactory.getInstance();
		AbstractExecutor executor = factory.getExecutor(request);
		Object result = executor.execute();
		PrintWriter out = response.getWriter();
		JSONSerializer serializer = new JSONSerializer();
		String sb = serializer.exclude("*.class").deepSerialize(result);
		out.print(sb);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			doGet(request, response);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
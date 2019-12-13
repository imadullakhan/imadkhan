package com.techouts.aemindustry.core.Registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.commons.datasource.poolservice.DataSourcePool;

@Component(service = Servlet.class, property = { "sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=/bin/path/demodropdown", "sling.servlet.selectors=" + "sample",
		"sling.core.servletName=" + "service.pid"

})
public class DynamicDropDown extends SlingAllMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Reference
	public DataBaseConnection dataconnection;

	public static DataBaseConnection dataconnection2;

	public void init() {
		dataconnection2 = dataconnection;
	}

	@Reference
	private DataSourcePool source;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

		PrintWriter pw = response.getWriter();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {

			conn = dataconnection.getConnection();

			if (conn != null) {
				pw.println("Connction EStablished...");
			} else {
				pw.println("Connection Null...!");
			}

			ps = conn.prepareStatement("Select * from dropdown");

			rs = ps.executeQuery();
			List<String> countries = new ArrayList<String>();

			while (rs.next()) {

				pw.println(rs.getString(2) + "<br><br>");

				countries.add(rs.getString(2));

			}

			for (String string : countries) {
				// pw.println(string + "<br><br>");
			}

			/*
			 * request.setAttribute("Countries", countries); RequestDispatcher
			 * rd = request.getRequestDispatcher("/bin/path/demodropdown");
			 * rd.forward(request, response);
			 */
			// response.sendRedirect("/bin/path/dropDown");

		} catch (Exception e) {

			e.printStackTrace();
			pw.println("EXCEPTION" + e);

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}

			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

}

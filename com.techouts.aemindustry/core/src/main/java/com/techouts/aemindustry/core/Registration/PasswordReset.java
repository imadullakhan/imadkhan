package com.techouts.aemindustry.core.Registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.commons.datasource.poolservice.DataSourcePool;

@Component(service = Servlet.class, property = { "sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=/bin/path/resetpassword", "sling.servlet.selectors=" + "sample",
		"sling.core.servletName=" + "service.pid"

})
public class PasswordReset extends SlingAllMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Reference
	private DataSourcePool source;

	private static final String query = "SELECT COUNT(*) FROM regisertable WHERE email=?";

	private static final String RESET_PASS = "UPDATE regisertable SET password=? WHERE email=?";

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);

	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter pw = response.getWriter();
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;

		String email = null, password = null, cpassword = null;

		int result = 0;

		response.setContentType("text/html");

		// Read the Data From User Html
		email = request.getParameter("email");
		password = request.getParameter("password");
		cpassword = request.getParameter("cpassword");

		// Validate the Password

		if (password == null || cpassword == null || password.length() == 0 || cpassword.length() == 0) {
			pw.println("<h2 style='color:red;text-align:center;'>Password or confirm password required</h2>");
			return;
		}

		if (!(password.equals(cpassword))) {
			pw.println("<h2 style='color:red;text-align:center;'>Password and confirm password does not match</h2>");
			return;
		}

		try {

			conn = getConnection();
			ps = conn.prepareStatement(query);

			// Set a value to a query Param
			ps.setString(1, email);
			// send and execute SQL Query
			rs = ps.executeQuery();
			// get the no of count

			while (rs.next()) {
				// pw.println("Resultsss" + rs.getString(1));
				result = rs.getInt(1);

			}

			// process the result

			if (result >= 0) {

				// pw.println("Results" + result);

				ps1 = conn.prepareStatement(RESET_PASS);
				// set Values to Query String
				ps1.setString(1, password);
				ps1.setString(2, email);

				// send and execute Query
				result = ps1.executeUpdate();

				// process the results
				if (result == 0) {
					pw.println("<h2>Reset Failled This Email Id is dosent Exist...!</h2>");
					pw.println(
							"<a href = '/content/aemindustry/en/register.html' style='width:80px;height:80px;padding:12px;background-color:teal;color:#fff;text-decoration:none;border-radius:5px;'>Register Here</a>");
				} else {
					pw.println("<h2>Password reset successfully</h2>");
					pw.println(
							"<a href = '/content/aemindustry/en/Login.html' style='width:80px;height:80px;padding:12px;background-color:teal;color:#fff;text-decoration:none;border-radius:5px;'>Login Here</a>");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			pw.println("<h2>" + e.toString() + "</h1>");
		} finally {

			try {
				rs.close();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}

			try {
				ps.close();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}

			try {
				if (ps1 != null) {
					ps1.close();
				}
			} catch (SQLException e1) {

				e1.printStackTrace();
			}

			try {
				conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

		// close Stream
		pw.close();

	}// doPost Closed.

	private Connection getConnection() {
		DataSource dataSource = null;
		Connection con = null;
		try {
			// Inject the DataSourcePool right here!
			dataSource = (DataSource) source.getDataSource("studentaemindustry");
			con = dataSource.getConnection();

			return con;

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

}

package com.techouts.aemindustry.core.Registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.sling.api.servlets.HttpConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.commons.datasource.poolservice.DataSourcePool;

@Component(service = Servlet.class, property = { "sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=/bin/path/imadlogin", "sling.servlet.selectors=" + "sample",
		"sling.core.servletName=" + "service.pid"

})
public class UserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Reference
	private DataSourcePool source;

	@Reference
	private UserDao userDao;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter pw = resp.getWriter();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		resp.setContentType("text/html");

		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String address = req.getParameter("address");
		String contact = req.getParameter("contact");

		Users user = new Users();

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		user.setContact(contact);
		user.setAddress(address);

		con = getConnection();

		try {
			userDao.registerEmployee(user);

			ps = con.prepareStatement("Select Count(*) from regisertable where email=?");
			ps.setString(1, email);
			rs = ps.executeQuery();
			int Result = 0;

			while (rs.next()) {

				Result = rs.getInt(1);

			}

			/*
			 * if (Result >= 1) {
			 * 
			 * pw.println(
			 * "<h3> This Email Id is already Present Please Try with Some diffrent Email-Id! </h3>"
			 * + email);
			 * 
			 * pw.println(
			 * "<a href = '/content/aemindustry/en/register.html' style='width:80px;height:80px;padding:12px;background-color:teal;color:#fff;text-decoration:none;border-radius:5px;'>Register Here</a>"
			 * );
			 * 
			 * // pw.println("<a href = '/content/aemindustry/en/Login.html' //
			 * style='width:80px;height:80px;padding:12px;background-color:teal;
			 * color:#fff;text-decoration:none;border-radius:5px;'>Login //
			 * Here</a>");
			 * 
			 * } else {
			 * 
			 * 
			 * if (Result > 0) { pw.println(
			 * "<h3> Data Stored SuccessFully And Email Id is Uniqe...! </h3>" +
			 * firstName); pw.println(
			 * "<a href = '/content/aemindustry/en/Login.html' style='width:80px;height:80px;padding:12px;background-color:teal;color:#fff;text-decoration:none;border-radius:5px;'>Login Here</a>"
			 * ); } else {
			 * 
			 * pw.println("User Registration is Failed...!"); pw.println(
			 * "<a href = '/content/aemindustry/en/register.html' style='width:80px;height:80px;padding:12px;background-color:teal;color:#fff;text-decoration:none;border-radius:5px;'>Register Here</a>"
			 * );
			 * 
			 * }
			 * 
			 * }
			 */
		} catch (Exception e) {
			pw.println("Exception" + e);
			pw.println(e.getMessage());
			e.printStackTrace();
		}

		pw.println("<h3>" + "Data Stored SuccessFully..." + firstName + " " + email + "</h3>");

		pw.println(
				"<a href = '/content/aemindustry/en/Login.html' style='width:80px;height:80px;padding:12px;background-color:teal;color:#fff;text-decoration:none;border-radius:5px;'>Login Here</a>");

		// resp.sendRedirect("/content/aemindustry/en/home.html");

		// close the stream
		pw.close();

		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			ps.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

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
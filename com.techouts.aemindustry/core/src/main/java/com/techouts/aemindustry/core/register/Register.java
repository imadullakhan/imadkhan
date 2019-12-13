package com.techouts.aemindustry.core.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

/*@SlingServlet(paths={"/bin/path/aemindustry"}, methods={"GET"})
@Properties({ @Property(name="service.pid", value = "com.techouts.aemindustry.core.register.Register"),
	 @Property(name = "service.description", value = "Hey Data Stored"),})	
*/
@Component(service = Servlet.class, property = { "sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=/bin/path/aemindustry", "sling.servlet.selectors=" + "sample",
		"sling.core.servletName=" + "service.pid"

})

public class Register extends SlingAllMethodsServlet {

	@Reference
	private DataSourcePool source;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		Statement stmt = null;

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		pw.println("Hi How are You!");
		ResultSet rs = null;

		try {
			pw.println("Hi Fine !");

			// Load the Driver
			// Class.forName("com.mysql.cj.jdbc.Driver");
			pw.println("Hi ok !");
			// Create the Connection With Data Base
			Connection con = getConnection();
			// stmt = con.createStatement();
			pw.println("Hi ok2!");
			if (con != null) {
				pw.println("Data Conncetion Established" + con);
			} else {
				pw.println("Conncetion IS Null");
			}

			PreparedStatement ps = con.prepareStatement("select * from aemdatabase");

			rs = ps.executeQuery();

			while (rs.next()) {

				pw.println("<h2>" + rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + "</h2>");
				pw.println("<h2>Your Successfully Registerd</h2>");
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			pw.println("Exception" + e);
		}
		finally {
			
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
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

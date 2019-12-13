package com.techouts.aemindustry.core.register;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.Servlet;
import javax.sql.DataSource;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.commons.datasource.poolservice.DataSourcePool;

/*@SlingServlet(paths = { "/bin/path" }, methods = {"GET"})
	@Properties({ @Property(name = "service.pid", value = "com.mpurpose.mpurpose.core.servlets.JdbcDataBaseConnection"),
			@Property(name = "service.description", value = "This data is stored inside of data Example"), })
*/
@Component(service = Servlet.class,
property={
		"sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=/bin/connection",
		"sling.servlet.selectors="+ "sample",
		"sling.core.servletName="+ "service.pid"
       
}
)

	public class ConncetionClass extends SlingAllMethodsServlet {
		
		@Override
		protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
				throws javax.servlet.ServletException, java.io.IOException {
			Connection conn = null;
			   Statement stmt = null;
			 
			try{
				
				response.setContentType("text/html");
				response.getWriter().print("<h1>Entered</h1>");
				
				 	conn = getConnection();
				 	stmt = conn.createStatement();
				   
				 	String sql = "INSERT INTO Customer VALUES ('salmann', 'khann', 'Mumbaii', 1234567897)";
			        stmt.executeUpdate(sql);
			    }catch(SQLException se){
			    	System.out.println("Exception"+se);
			        //Handle errors for JDBC
			        se.printStackTrace();
				   
				   
			    }
		}

		@Reference
		private DataSourcePool source;

		private Connection getConnection() {
			DataSource dataSource = null;
			Connection con = null;
			try {
				// Inject the DataSourcePool right here!
				dataSource = (DataSource) source.getDataSource("customer.data");
				con = dataSource.getConnection();
				return con;

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
	    
	    }	



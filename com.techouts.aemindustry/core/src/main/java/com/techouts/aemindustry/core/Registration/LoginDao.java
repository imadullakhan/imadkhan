package com.techouts.aemindustry.core.Registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.commons.datasource.poolservice.DataSourcePool;

@Component(service = LoginDao.class)
public class LoginDao {

	@Reference
	private DataSourcePool source;

	public boolean validate(Users user) throws Exception {
		boolean status = false;

		Connection conn = null;
		ResultSet rs = null;

		conn = getConnection();

		// Step 2:Create a statement using connection object
		String query = "select * from regisertable where username = ? and password = ?";

		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, user.getUsername());
		ps.setString(2, user.getPassword());

		rs = ps.executeQuery();

		status = rs.next();

		// Close The Stream
		rs.close();
		ps.close();
		// Conncetion Closed
		conn.close();

		return status;
	}

	private Connection getConnection() throws Exception {

		DataSource dataSource = null;
		Connection con = null;

		// Inject the DataSourcePool right here!
		dataSource = (DataSource) source.getDataSource("studentaemindustry");
		con = dataSource.getConnection();

		return con;

	}

}

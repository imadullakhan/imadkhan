package com.techouts.aemindustry.core.Registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.commons.datasource.poolservice.DataSourcePool;

@Component(service = UserDao.class)
public class UserDao {

	@Reference
	private DataSourcePool source;

	public int registerEmployee(Users users) throws Exception {
		String INSERT_USERS_SQL = "INSERT INTO regisertable"
				+ "  (first_name, last_name, username, email, password, address, contact) VALUES "
				+ " (?, ?, ?, ?, ?,?, ?)";

		int result = 0;
		Connection con = null;

		con = getConnection();

		PreparedStatement preparedStatement = con.prepareStatement(INSERT_USERS_SQL);
		// preparedStatement.setInt(1, ++i);
		preparedStatement.setString(1, users.getFirstName());
		preparedStatement.setString(2, users.getLastName());
		preparedStatement.setString(3, users.getUsername());
		preparedStatement.setString(4, users.getEmail());
		preparedStatement.setString(5, users.getPassword());
		preparedStatement.setString(6, users.getAddress());
		preparedStatement.setString(7, users.getContact());
		result = preparedStatement.executeUpdate();

		// Close The Stream
		con.close();

		return result;

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

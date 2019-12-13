package com.techouts.aemindustry.core.Registration;

import java.sql.Connection;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.commons.datasource.poolservice.DataSourcePool;

@Component(service = DataBaseConnection.class)
public class DataBaseConnection {

	@Reference
	private DataSourcePool source;

	public Connection getConnection() {
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

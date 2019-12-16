package com.techouts.aemindustry.core.practice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.iterators.TransformIterator;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;

import com.adobe.cq.sightly.WCMUsePojo;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.techouts.aemindustry.core.Registration.DataBaseConnection;
import com.techouts.aemindustry.core.Registration.DynamicDropDown;

/**
 * @author ImadullaKhan
 *
 */

public class SqlDataSourceCountry extends WCMUsePojo {

	private DataBaseConnection dataconnection = DynamicDropDown.dataconnection2;
	// private DataBaseConnection dataconnection;

	@Override
	public void activate() throws Exception {
		final ResourceResolver resolver = getResource().getResourceResolver();
		/*
		 * final Map<String, String> count = new LinkedHashMap<String,
		 * String>(); count.put("dummy", "imad"); count.put("dummy1", "imad");
		 * count.put("dummy2", "imad");
		 */
		final Map<String, String> count = new LinkedHashMap<String, String>();

		try {

			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			conn = dataconnection.getConnection();

			if (conn != null) {
				System.out.println("Connection Established");
				System.out.println("Connction EStablished...");
			} else {
				System.out.println("Connection Null...!");

			}

			ps = conn.prepareStatement("Select contries from dropdown");

			rs = ps.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString(1) + "<br><br>");

				// Creating the Map instance to insert the countries
				count.put(rs.getString(1), rs.getString(1));

			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		@SuppressWarnings("unchecked")

		// Creating the Datasource Object for populating the drop-down control.
		DataSource ds = new SimpleDataSource(new TransformIterator(count.keySet().iterator(), new Transformer() {

			@Override
			// Transforms the input object into output object
			public Object transform(Object o) {
				String fruit = (String) o;

				// Allocating memory to Map
				ValueMap vm = new ValueMapDecorator(new HashMap<String, Object>());

				// Populate the Map
				vm.put("value", fruit);
				vm.put("text", count.get(fruit));

				return new ValueMapResource(resolver, new ResourceMetadata(), "nt:unstructured", vm);
			}
		}));

		this.getRequest().setAttribute(DataSource.class.getName(), ds);

	}

}
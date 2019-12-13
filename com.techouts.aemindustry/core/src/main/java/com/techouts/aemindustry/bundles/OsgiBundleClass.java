package com.techouts.aemindustry.bundles;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = Servlet.class, property = { "sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=/bin/path/osgibundle", "sling.servlet.selectors=" + "sample"
})
public class OsgiBundleClass extends SlingAllMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Reference
	ConfigurationAdmin admin;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

		Dictionary<String, Object> dictonary;

		Configuration config = admin.getConfiguration("com.techouts.aemindustry.bundles.MySimpleServiceImpl.config");

		dictonary = config.getProperties();

		Enumeration<String> keys = dictonary.keys();

		while (keys.hasMoreElements()) {
			String key = keys.nextElement();

			// response.getWriter().println("dictionary" + dictionary);

			response.getWriter().println("Keys-->" + keys + "Values-->" + dictonary.get(key) + "<br>");
			// response.getWriter().print("datasource.svc.properties"+
			// dictionary.get("datasource.svc.properties")+"<br>");
		}

	}

}

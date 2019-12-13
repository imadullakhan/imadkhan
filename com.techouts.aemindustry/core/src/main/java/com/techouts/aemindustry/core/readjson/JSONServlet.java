package com.techouts.aemindustry.core.readjson;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(service= Servlet.class, property={ Constants.SERVICE_DESCRIPTION + "=JSON Servlet to read the data from the external webservice",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/readjson" })

public class JSONServlet extends SlingSafeMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String URL = "https://jsonplaceholder.typicode.com/todos/";
	
	private static final Logger log = LoggerFactory.getLogger(JSONServlet.class);
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			 {
		
		try{
		
		log.info("Reading the data From Webservices");
		
		String responseString = Network.readJson(URL);
		
		response.getWriter().println(responseString);
		
		}
		catch(Exception e){
			log.error(e.getMessage(), e);
		}
		
	}

}

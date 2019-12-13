package com.techouts.aemindustry.core.readjson;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import com.day.cq.commons.jcr.JcrConstants;


@Component(service=Servlet.class,
          property={
        Constants.SERVICE_DESCRIPTION + "=Simple Demo Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.resourceTypes="+ "demoproject/components/structure/page",
        "sling.servlet.extensions=" + "txt"
})
public class SimpleServlet extends SlingSafeMethodsServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws ServletException, IOException {
		
		final Resource resource = request.getResource();
		
		response.setContentType("text/plain");
		response.getWriter().write("Title= "+ resource.getValueMap().get(JcrConstants.JCR_TITLE));
		
	}

}

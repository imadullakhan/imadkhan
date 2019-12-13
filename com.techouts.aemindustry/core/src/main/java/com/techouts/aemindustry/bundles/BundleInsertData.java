package com.techouts.aemindustry.bundles;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
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
		"sling.servlet.paths=/bin/path/datainsert", "sling.servlet.selectors=" + "sample"
})
public class BundleInsertData extends SlingAllMethodsServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Reference
	ConfigurationAdmin admin;
	
	//This is comment
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			{
		
		response.setContentType("text/html");
		
		PrintWriter pw = response.getWriter();
		
						
		Configuration config = admin.getConfiguration("com.techouts.aemindustry.bundles.MySimpleServiceImpl");
		Dictionary<String, Object> dictonary = config.getProperties();
		String[] multival = {"Hi", "Hello", "How are You"};
		pw.println(dictonary);
		dictonary.put("configValue", "AEMTask");
		dictonary.put("getStringValues", multival);
		dictonary.put("getNumberValue", "9742");
		config.update(dictonary);
		dictonary = config.getProperties();
		//pw.println(dictonary);
		pw.println(Arrays.toString((String[])dictonary.get("getStringValues")));
		
		Enumeration<String> keys = dictonary.keys();
		
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
		response.getWriter().println("Keys--->"+key+" Values=="+ dictonary.get(key)+ "<br>" );
		
		
		
		}
		
			
		
	}
	
	

}

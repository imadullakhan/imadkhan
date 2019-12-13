/*package com.techouts.aemindustry.core.integration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Dictionary;

import javax.servlet.ServletException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpHost;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(name = "com.techouts.aemindustry.core.integration.GetDocusignAccountInfo", label = "AEM Docusign - GetDocusignAccountInfo", immediate = true, metatype = true)
@Service
@Properties({ @Property(name = "service.description", value = "Get Docusign Account Info"),
    @Property(name = "service.vendor", value = "AEM Quickstart"),
    @Property(name = "docuserviceurl", value = "https://demo.docusign.net/restapi/v2/login_information"),
    @Property(name = "docuusername", value = "xxxxx@gmail.com"),
    @Property(name = "docupassword", value = "XXXXXX"), 
    @Property(name = "sling.servlet.paths", value = "/services/docusign/GetDocusignAccountInfo", propertyPrivate = true),
    @Property(name = "sling.servlet.methods", value = "POST"),
    @Property(name = "integratorkey", value = "xxxxxx-xxxxxx-xxxxxx-xxxxxx-xxxxxx") })
public class GetDocusignAccountInfo extends SlingAllMethodsServlet implements Serializable {
	
	*//**
	 * 
	 *//*
	private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory.getLogger(GetDocusignAccountInfo.class);
	
	private String serviceUrl;
    private String serviceBaseUrl;
    private String userName;
    private String password;
    private String integratorkey;
    private String proxyurl;
    private String proxyport;
    private String accountid;
    
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
    		throws ServletException, IOException {
    	
    	doPost(request, response);
    }
    
    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
    		throws ServletException, IOException {
    	
    	log.info("doPost Started");
        log.info("serviceUrl :" + serviceUrl);
        log.info("userName" + userName);
        log.info("password " + password);
        log.info("integratorkey:" + integratorkey);
        log.info("serviceBaseUrl: "+serviceBaseUrl);
        log.info("accountid: "+accountid);
        
        JSONObject obj = new JSONObject();
        String finalUrl=null;
        try {
            obj.put("Username", userName);
            obj.put("Password", password);
            obj.put("IntegratorKey", integratorkey);
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet(serviceUrl);
            HttpHost proxy = new HttpHost(proxyurl, Integer.parseInt(proxyport));
            httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            getRequest.addHeader("X-DocuSign-Authentication", obj.toString());
            HttpResponse responseHttp = httpClient.execute(getRequest);
            log.info("Status Code" + response.getStatusLine().getStatusCode());
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            log.info("Docusgin Account details: "+result);
            
            response.setContentType("text/html");
            response.getWriter().write(result.toString());

        } catch (JSONException e) {
            log.error("Json Exception details :"+ e.toString());
        } catch (Exception e) {
            log.error("Exception details :"+ e.toString());
        }
        log.info("doPost Ended");

    	
    }
    
    
    @Activate
    protected void activate(ComponentContext context) throws Exception {
        log.info("activate method called");
        @SuppressWarnings("rawtypes")
        Dictionary properties = context.getProperties();
        serviceUrl = (String) properties.get("docuserviceurl");     
        userName = (String) properties.get("docuusername");
        password = (String) properties.get("docupassword");
        integratorkey = (String) properties.get("integratorkey");

    }


}
*/
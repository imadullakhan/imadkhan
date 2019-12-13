package com.techouts.aemindustry.core.groups;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.PropertyType;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*@Service(value = Servlet.class)
@Component(immediate = true, metatype = true)
@Properties({
  @Property(name = "sling.servlet.paths", value = "com.techouts.aemindustry.core.groups.SimpleGroupUserAEM"),
  @Property(name = "service.description", value = "SimpleUserGroup"),
  @Property(name = "label", value = "SimpleUserGroup") })*/


@Component(service=Servlet.class, immediate = true,

property={
		"sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=/bin/aemusergroups",
		"sling.servlet.selectors="+ "sample"
      
}
)
public class SimpleGroupUserAEM extends SlingAllMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Session adminSession;
	  
	 @Reference
	 ResourceResolverFactory resolverFactory;
	   
	 
	 private static Logger log = LoggerFactory.getLogger(SimpleUserGroup.class);
	 
	 @Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		 
		 ResourceResolver resolver = null;   
		  String groupName= request.getParameter("groupName");
		  String userName=request.getParameter("userName");
		 // String password="sampleUser";
		  
		  String password = request.getParameter("password");		  
		  String firstname = request.getParameter("firstname");
		  String lastname =request.getParameter("lastname");
		  String email = request.getParameter("email");
		  
		  
		  
		  try {
			  
			    Map<String, Object> authInfoParam = new HashMap<String, Object>();
			    authInfoParam.put(ResourceResolverFactory.SUBSERVICE, "readservice");
			    resolver = resolverFactory.getServiceResourceResolver(authInfoParam);
			  
			          
			       
			         log.info("*** IN SERVLET *** GroupName is "+groupName);
			        //Invoke the adaptTo method to create a Session used to create a QueryManager
			         resolver = resolverFactory.getServiceResourceResolver(authInfoParam);
			         adminSession = resolver.adaptTo(Session.class);
			            
			          
			         final UserManager userManager= resolver.adaptTo(UserManager.class);
			                 
			         // Create a Group
			         Group group= null;
			         if (userManager.getAuthorizable(groupName) == null) {
			             //adminResolver.refresh();
			             group = userManager.createGroup(groupName,new SimplePrincipal(groupName),"/home/groups/test");
			              
			             ValueFactory valueFactory = adminSession.getValueFactory();
			             Value groupNameValue = valueFactory.createValue(groupName, PropertyType.STRING);
			             group.setProperty("./profile/givenName", groupNameValue);
			             //adminResolver.commit();
			             log.info("----------------------------------------> {} Group successfully created.",group.getID());
			         } else {
			             log.info("----------------------------------------> Group already exist..");
			         }
			 
			         // Create a User
			         User user = null;
			         if (userManager.getAuthorizable(userName) == null) {
			             //adminResolver.refresh();
			             user=userManager.createUser(userName, password,new SimplePrincipal(userName),"/home/users/test");
			              
			             ValueFactory valueFactory = adminSession.getValueFactory();
			             Value firstNameValue = valueFactory.createValue(firstname, PropertyType.STRING);
			             user.setProperty("./profile/givenName", firstNameValue);
			              
			             Value lastNameValue = valueFactory.createValue(lastname, PropertyType.STRING);
			             user.setProperty("./profile/familyName", lastNameValue);
			              
			             Value emailValue = valueFactory.createValue(email, PropertyType.STRING);
			             user.setProperty("./profile/email", emailValue);
			             //adminResolver.commit();
			             log.info("----------------------------------------> {} User successfully created.",user.getID());
			         } else {
			             log.info("----------------------------------------> User already exist..");
			         }
			 
			         // Add Users to Group
			         Group addUserToGroup = (Group)(userManager.getAuthorizable(groupName));
			         addUserToGroup.addMember(userManager.getAuthorizable(userName));
			         adminSession.save();
			 
			     }catch (Exception e) {
			    	 
			         log.info("----------------------------------------> Not able to perform User Management..");
			         log.info("----------------------------------------> Exception.." + e.getMessage());
			         response.getWriter().write(e.getMessage()+"<br>");
			         response.getWriter().write("AEM User WAS NOT successfully created.."); 
			     } finally {
			         if (adminSession != null && adminSession.isLive()) {
			             adminSession.logout();
			         }
			         if (resolver != null)
			             resolver.close();
			         response.getWriter().write("AEM User "+userName +" successfully created.."); 
			     }
			 }
			  
			 private static class SimplePrincipal implements Principal {
			     protected final String name;
			 
			     public SimplePrincipal(String name) {
			         if (name.compareTo("")==0) {
			             throw new IllegalArgumentException("Principal name cannot be blank.");
			         }
			         this.name = name;
			     }
			 
			     public String getName() {
			         return name;
			     }
			 
			     @Override
			     public int hashCode() {
			         return name.hashCode();
			     }
			 
			     @Override
			     public boolean equals(Object obj) {
			         if (obj instanceof Principal) {
			             return name.equals(((Principal) obj).getName());
			         }
			         return false;
			     }
			 }
		
		 
	}
	


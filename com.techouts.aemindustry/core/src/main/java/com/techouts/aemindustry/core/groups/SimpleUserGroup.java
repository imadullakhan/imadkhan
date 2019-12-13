package com.techouts.aemindustry.core.groups;

import java.io.IOException;
import java.security.Principal;

import javax.jcr.PropertyType;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Service;
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

@Service(value = Servlet.class)
/*@Component(immediate = true, metatype = true)
@Properties({
	@Property(name = "sling.servlet.paths", value = "com.techouts.aemindustry.core.groups.SimpleUserGroup"),
	@Property(name = "service.description", value = "SimpleUserGroup"),
	"sling.servlet.paths=/bin/path111",
	@Property(name = "label", value = "SimpleUserGroup")
})*/

@Component(service=Servlet.class, immediate = true,

property={
		"sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=/bin/usersgroup",
		"sling.servlet.selectors="+ "sample"
       
}
)
public class SimpleUserGroup extends SlingAllMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Reference
	ResourceResolverFactory resourceResolverFactory;
	
	private static final Logger log = LoggerFactory.getLogger(SimpleUserGroup.class);
	
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
	
		
		String groupName = request.getParameter("groupName");
		String userName = request.getParameter("userName");
		String password = "sampleUser";
		
		ResourceResolver adminResolver = null;
		Session adminSession = null;
		
		try{
			
			adminResolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
			adminSession = adminResolver.adaptTo(Session.class);
			final UserManager userManager = adminResolver.adaptTo(UserManager.class);
			
			if(null == userManager.getAuthorizable(groupName)){
			
			Group group = userManager.createGroup(groupName,new SimplePrincipal(groupName),"/home/groups/test");
			
			Value value=adminSession.getValueFactory().createValue("Sample Group", PropertyType.STRING);
	         group.setProperty("./profile/givenName", value);
	          
	         value=adminSession.getValueFactory().createValue("Test Group", PropertyType.STRING);
	         group.setProperty("./profile/aboutMe", value);
	          
	         value=adminSession.getValueFactory().createValue("abc@gmail.com", PropertyType.STRING);
	         group.setProperty("./profile/email", value);   
			
			}else{
				response.getWriter().write("Group Alredy Exist...");
			}
			
			if(userManager.getAuthorizable(userName)== null){
				
				User user=userManager.createUser(userName, password,new SimplePrincipal(userName),"/home/users/test");
		         Value value=adminSession.getValueFactory().createValue("Issac", PropertyType.STRING);
		         user.setProperty("./profile/familyName", value);
		         
		         value=adminSession.getValueFactory().createValue("Albin", PropertyType.STRING);
		         user.setProperty("./profile/givenName", value);
		         
		         value=adminSession.getValueFactory().createValue("Test User", PropertyType.STRING);
		         user.setProperty("./profile/aboutMe", value);
		         
		         value=adminSession.getValueFactory().createValue("albin@gmail.com", PropertyType.STRING);
		         user.setProperty("./profile/email", value); 
				
			}else{
				response.getWriter().write("This User is Already Present ...");
			}
			
			Group group = (Group) userManager.getAuthorizable(groupName);
			group.addMember(userManager.getAuthorizable(userName));
			
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().write("Not able to perform User Management.." +resourceResolverFactory);
		}finally {
			if(adminResolver != null)
				adminResolver.close();
			response.getWriter().write("AEM User Successfully Created..");
		}
		
		
	}
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
		
	}
	
	private static class SimplePrincipal implements Principal {
        protected final String name;
 
        public SimplePrincipal(String name) {
            if (StringUtils.isBlank(name)) {
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

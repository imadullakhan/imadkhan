package com.techouts.aemindustry.core.extra;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.jcr.Session;

import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables=Resource.class)
public class UserGroupClass    {
	
	private static final Logger log = LoggerFactory.getLogger(UserGroupClass.class);
	
	private List<String> users, groups;
	
	private Session session;
	
	@Reference
	private ResourceResolver resourceResolver;
	
	@Activate
	public void activate() throws Exception{
		
		
		try {
			log.info("----------< Processing starts >----------");
		
              
		
		session = resourceResolver.adaptTo(Session.class);
		
		UserManager userManager = ((JackrabbitSession) session).getUserManager();
		
		Iterator<Authorizable> userIterator = userManager.findAuthorizables("jcr:primaryType", "rep:User");
		Iterator<Authorizable> groupIterator = userManager.findAuthorizables("jcr:primaryType", "rep:Group");
		
		users = new LinkedList<>();
		groups = new LinkedList<>();
		
		while (userIterator.hasNext()){
			log.info("Getting User info");
			
			Authorizable user = userIterator.next();
			
			if(!user.isGroup()){
				log.info("User found: {}", user.getID());
				users.add(user.getID());
			}
		}
		
		while (groupIterator.hasNext()){
			log.info("Getting Grop info");
			
			Authorizable group = groupIterator.next();
			
			if(!group.isGroup()){
				log.info("Grop Found: {}", group.getID());
				groups.add(group.getID());
			}
		}		
		
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}

	
	



	public List<String> getUsers() {
		return users;
	}

	public List<String> getGroups() {
		return groups;
	}

	

}

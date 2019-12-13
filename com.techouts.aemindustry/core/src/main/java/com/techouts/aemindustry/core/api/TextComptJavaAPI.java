/*package com.techouts.aemindustry.core.api;

import javax.jcr.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;

public class TextComptJavaAPI extends WCMUsePojo {

	private static final Logger log  = LoggerFactory.getLogger(TextComptJavaAPI.class);
	
	private TextComponent2Model model;
	
	@Override
	public void activate() throws Exception{
		
		try{
			
			log.info("Text Component 2.0 backend logic starts");
			
			model  = new TextComponent2Model();
			
			Node node = getResource().adaptTo(Node.class);
			
			if(node.hasProperty("title")){			
				String title = node.getProperty("title").getString();				
				model.setTitle(title);
			}
			
			if(node.hasProperty("description")){
				String desc = node.getProperty("description").getString();
				model.setDescription(desc);
			}
			
			if(node.hasProperty("place")){
				String plce = node.getProperty("place").getString();
				model.setPlace(plce);
			}
			
		    }catch (Exception e) {
			log.error(e.getMessage(), e);	// TODO: handle exception
			}
					
	}
	
	public TextComponent2Model getModel(){
		return model;
	}
	
	
}
*/
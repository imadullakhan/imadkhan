package com.techouts.aemindustry.core.utils;

import java.util.Iterator;

import org.apache.sling.api.resource.Resource;

import com.adobe.cq.sightly.WCMUsePojo;

public class MultifieldUtils extends  WCMUsePojo {
	
	Iterator<Resource> childs;
	
	
	
	@Override
	public void activate() throws Exception {
		String multifieldNodeName = get("multifieldNodeName", String.class);
		Resource multiFieldResource = getResource().getChild(multifieldNodeName);
		childs = multiFieldResource.listChildren();
	}
	
	public Iterator<Resource> getChilds() {
		return childs;
	}
	 

}

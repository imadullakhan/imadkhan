package com.techouts.aemindustry.core.utils;

import java.util.Iterator;

import org.apache.sling.jcr.api.SlingRepository;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;

public class TopNavigation extends WCMUsePojo {

	Iterator<Page> childs;
	SlingRepository  slingRepository;

	@Override
	public void activate() throws Exception {

		Page rootPage = getCurrentPage().getAbsoluteParent(2);
		childs = rootPage.listChildren();
		slingRepository = getSlingScriptHelper().getService(SlingRepository.class);
	}

	public Iterator<Page> getChilds() {
		return childs;
	} 

}

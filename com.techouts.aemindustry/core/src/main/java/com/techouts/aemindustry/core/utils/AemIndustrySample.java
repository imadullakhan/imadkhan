package com.techouts.aemindustry.core.utils;

import com.adobe.cq.sightly.WCMUsePojo;

public class AemIndustrySample extends WCMUsePojo {
	
	@Override
	public void activate() throws Exception {
		System.out.println("Activate() is called when object of use class is being created...!!!");
		
	}
	public String wish(){
		return "Hello, Welcome To Sightly Website";
	}
	

}

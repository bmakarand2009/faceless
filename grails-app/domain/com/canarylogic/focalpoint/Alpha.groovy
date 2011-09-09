package com.canarylogic.focalpoint

import java.util.Date;

class Alpha {
	Date dateCreated, lastUpdated
	
	String pkey//can be a emailaddr 
	String c1;
	String c2;
	
	Client parent
	static hasMany=[child:ChildAlpha]
	
	
    static constraints = {
		pkey(unique:'parent',nullable:false)
		c1(nullable:true)
		c2(nullable:true)
	}
    
	public String getFieldVal(String fieldName){
		if(fieldName =="id") return id
		else if(fieldName == "version") return version
		else if(fieldName == "c1") return c1
		else if(fieldName == "c2")	return c2
		else if(fieldName == "pkey") return pkey
		
		
		
	}
}

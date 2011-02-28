package com.canarylogic.focalpoint

class Alpha {

	String c1;
	String c2;

	
	
    static constraints = {
	}
    
	public String getFieldVal(String fieldName){
		if(fieldName =="id") return id
		else if(fieldName == "version") return version
		else if(fieldName == "c1") return c1
		else if(fieldName == "c2")	return c2
		
		
		
	}
}

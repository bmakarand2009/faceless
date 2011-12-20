package com.canarylogic.focalpoint.contacts

import com.canarylogic.sing.Client;

class VendorsMo {

    Date dateCreated, lastUpdated
	
	String pkey//can be a emailaddr 
	String c1;
	String c2;
	String c3;
	String c4;
	String c5;
	String c6;
	String c7;
	String c8;
	String c9;
	String c10;
	String c11;
	String c12;
	
	static hasMany=[child1:ChildVendorsMo]
	
	
	Client parent
	
	
    static constraints = {
		pkey(unique:'parent',nullable:false)
		c1(nullable:true)
		c2(nullable:true)
		c3(nullable:true)
		c4(nullable:true)
		c5(nullable:true)
		c6(nullable:true)
		c7(nullable:true)
		c8(nullable:true)
		c9(nullable:true)
		c10(nullable:true)
		c11(nullable:true)
		c12(nullable:true)
	}
    
	public String getFieldVal(String fieldName){
		if(fieldName =="id") return id
		else if(fieldName == "version") return version
		else if(fieldName == "pkey") return pkey
		else if(fieldName == "c1") return c1
		else if(fieldName == "c2")	return c2
		else if(fieldName == "c3")	return c3
		else if(fieldName == "c4")	return c4
		else if(fieldName == "c5")	return c5
		else if(fieldName == "c6")	return c6
		else if(fieldName == "c7")	return c7
		else if(fieldName == "c8")	return c8
		else if(fieldName == "c9")	return c9
		else if(fieldName == "c10")	return c10
		else if(fieldName == "c11")	return c11
		else if(fieldName == "c12")	return c12
	}
}

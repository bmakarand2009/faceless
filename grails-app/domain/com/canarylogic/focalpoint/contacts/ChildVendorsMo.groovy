package com.canarylogic.focalpoint.contacts

class ChildVendorsMo {

	static belongsTo = [parent:VendorsMo]
	String pkey//can be a emailaddr
	
	String c1;
	String c2;
	String c3;
	String c4;
	String c5;
	String c6;
    static constraints = {
		pkey(unique:'parent',nullable:false)
		
		c1(nullable:true)
		c2(nullable:true)
		c3(nullable:true)
		c4(nullable:true)
		c5(nullable:true)
		c6(nullable:true)
    }
}

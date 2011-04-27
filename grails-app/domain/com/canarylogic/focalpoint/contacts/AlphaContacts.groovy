package com.canarylogic.focalpoint.contacts

import java.util.Date;
import com.canarylogic.focalpoint.Groups;
import com.canarylogic.focalpoint.Client;
class AlphaContacts{
	//BaseFieldd
	Date dateCreated, lastUpdated
	String createdBy
	String updatedBy
	Groups owner
	Client parent
	
	
	//spefic ones
	String emailAddr
	String sourceType='NA' //can be web,manual,old,imported
	
	
	String c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18
	String descField1,descField2
	
    static constraints = {
		sourceType(inList:['WEBSITE','MANUAL','PREEXISTING','OTHER_SOURCE','NA'])
		emailAddr(unique:'parent',email:true)
		
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
		c13(nullable:true)
		c14(nullable:true)
		c15(nullable:true)
		c16(nullable:true)
		c17(nullable:true)
		c18(nullable:true)
		descField1(nullable:true,maxSize:1000000)
		descField2(nullable:true,maxSize:1000000)
		
	}
	
}

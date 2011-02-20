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
	String c1
	String c2
	
    static constraints = {
		sourceType(inList:['WEBSITE','MANUAL','PREEXISTING','OTHER_SOURCE','NA'])
		emailAddr(unique:'parent',email:true)
		
		c1(nullable:true)
		c2(nullable:true)		
    }
	
}

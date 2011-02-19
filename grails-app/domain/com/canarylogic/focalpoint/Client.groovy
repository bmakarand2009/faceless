package com.canarylogic.focalpoint

import java.io.Serializable;

class Client  implements Serializable {
	
	String orgId
	String orgName
	
	static hasMany=[roles:Role,groups:Groups]
	
    static constraints = {
		orgId(blank:false,unique:true)
		orgName(blank:false,length:5)
    }
	
	
}

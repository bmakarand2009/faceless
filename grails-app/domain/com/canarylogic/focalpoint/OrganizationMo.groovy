package com.canarylogic.focalpoint

class OrganizationMo {
	
	String orgId
	String orgName
	
	static hasMany=[roles:RoleMo,groups:GroupMo]
	
    static constraints = {
    }
}

package com.canarylogic.focalpoint

class GroupMo {
	
	String grpName;
	String grpDesc;

	static hasMany = [users:UserMo]	
	static belongsTo = [parent:OrganizationMo]
	
    static constraints = {
		grpName(unique:'parent')
	}
	
}

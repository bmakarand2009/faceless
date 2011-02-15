package com.canarylogic.focalpoint

class RoleMo {

	boolean isAccess
	boolean isDelete
	boolean isUpdate
	boolean isSelfGroup
	
	String roleName
	String roleDesc
		
	static belongsTo = [parent:OrganizationMo]
		
	static hasMany =[users:UserMo]
    static constraints = {
		roleName(unique:'parent')
    }
	
}

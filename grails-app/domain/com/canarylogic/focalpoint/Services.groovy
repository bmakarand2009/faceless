package com.canarylogic.focalpoint

class Services {
	String serviceName
	
	
	boolean isAccess
	boolean isUpdate
	boolean isDelete
	boolean isSelfGroup

	static belongsTo = [role:Role]
	
    static constraints = {
		serviceName(inList:['CANDIDATE_SERVICE'], unique:'role')
		role(nullable:true)

    }
}

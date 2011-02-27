package com.canarylogic.focalpoint

import com.canarylogic.base.*;
class Services {
	
	public static String IS_ACCESS="isAccess"
	public static String IS_CREATE="isCreate"
	public static String IS_UPDATE="isUpdate"
	public static String IS_DELETE="isDelete" 
	
	String serviceName
	
	
	boolean isAccess
	boolean isUpdate
	boolean isDelete
	boolean isCreate
	boolean isSelfGroup

	static belongsTo = [role:Role]
	
    static constraints = {
		serviceName(inList:['candidate'], unique:'role')
		role(nullable:true)
    }
	
	public boolean isAuthroized(String privName) {
		if(privName.equals(IS_ACCESS)) 	return isAccess
		else if(privName.equals(IS_UPDATE)) return isUpdate
		else if(privName.equals(IS_DELETE)) return isDelete
		else if(privName.equals(IS_CREATE)) return isCreate
		else return false
	}
	
	static findServicesByUser(String userId,String appId, String serviceName) {
		User curUser = User.findUser(userId, appId)
		if(!curUser) throw new RestException(ExMessages.AUTHENCIATION_FAILED,"No valid user found for $userId with $appId")

		Role curRole = curUser.findRole()
		if(!curRole)
			throw new RestException(ExMessages.AUTHENCIATION_FAILED,"No Role defined for $userId")
		Services servicePriv = Services.findByServiceNameAndRole(serviceName,curRole)
		return servicePriv
	}
	
	def assignRole(String roleName, boolean isFlush=false) {
		Role aRole = Role.findByRoleName(roleName)
		if(!aRole) return null
		this.role = aRole
		save(flush:isFlush)
	}
}

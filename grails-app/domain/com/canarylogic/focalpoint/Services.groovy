package com.canarylogic.focalpoint

class Services {
	
	public static String IS_ACCESS="isAccess"
	public static String IS_UPDATE="isUpdate"
	public static String IS_DELETE="isDelete" 
	
	String serviceName
	
	
	boolean isAccess
	boolean isUpdate
	boolean isDelete
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
		else return false
	}
	
}

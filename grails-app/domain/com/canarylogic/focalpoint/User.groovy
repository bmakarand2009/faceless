package com.canarylogic.focalpoint

class User {

    String username
	String password
	boolean enabled=true;
	boolean accountExpired=false;
	boolean accountLocked=false;
	boolean passwordExpired=false;

	static hasMany =[groups:Groups]
	static belongsTo = Groups
	
	static constraints = {
		username blank: false, unique: true, email:true
		password blank: false
		groups(minSize:1)
		
	}

	static mapping = {
		password column: '`password`'
	}

	Role findRole() {
		def role = UserRole.findByUser(this).role
		return role
	}
	
	def assignRole(String roleName,String orgId, boolean isflush=false) {
		def client = Client.findByOrgId(orgId)
		if(!client) return null
		Role role = Role.findByRoleNameAndParent(roleName,client)
		UserRole.create (this, role,isflush)
	}
	
	
    
    String toString(){
        username
    }
	
	def create(String groupName,String orgId, boolean isFlush=true) {
		def client = Client.findByOrgId(orgId)
		if(!client) return null		
		Groups grp = Groups.findByGrpNameAndParent(groupName,client)
		if(!grp) return null
		save(flush:isFlush)
		grp.addToUsers(this)
		grp.save(flush:isFlush)
		
	}
}

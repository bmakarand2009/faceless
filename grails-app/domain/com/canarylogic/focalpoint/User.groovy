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
	}

	static mapping = {
		password column: '`password`'
	}

	Role findRole() {
		def role = UserRole.findByUser(this).role
		return role
	}
	
	def assignRole(String roleName) {
		Role role = Role.findByRoleName(roleName)
		UserRole.create (this, role)
	}
	
	
    
    String toString(){
        username
    }
	
	def createUser(String groupName) {
		Groups grp = Groups.findByGrpName(groupName)
		if(!grp) return null
		if(!this.groups) groups = []	
		groups.add()
	}
}

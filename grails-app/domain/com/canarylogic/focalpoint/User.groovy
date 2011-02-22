package com.canarylogic.focalpoint
import com.canarylogic.focalpoint.utils.EncryptionUtils;

class User {

    String username
	String password
	boolean enabled=true;
	boolean accountExpired=false;
	boolean accountLocked=false;
	boolean passwordExpired=false;


	static hasMany =[groups:Groups]
	static belongsTo = Groups
	Client parent	
	
	
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
	
	def assignRole(String roleName, boolean isflush=false) {
		Role role = Role.findByRoleNameAndParent(roleName,parent)
		UserRole.create (this, role,isflush)
	}
	
	static User findUser(String userId, String orgId) {
		User user
		Client parent = Client.findByOrgId(orgId)
		if(parent)
			user = User.findByUsernameAndParent(userId,parent)
			
		return user
	}
    
    String toString(){
        username
    }
	
	def assignGroup(String groupName, boolean isFlush=true) {
		def client = parent
		if(!client) return null		
		Groups grp = Groups.findByGrpNameAndParent(groupName,client)
		if(!grp) return null	
		save(flush:isFlush)
		grp.addToUsers(this)
		grp.save(flush:isFlush)
		
	}
}

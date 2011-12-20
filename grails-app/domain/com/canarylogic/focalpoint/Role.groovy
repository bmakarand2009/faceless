package com.canarylogic.focalpoint

import com.canarylogic.sing.Client;

class Role  implements Serializable {
    
	String roleName
	String label
	
	static hasMany=[services:Services]
	
	static belongsTo = [parent:Client]
	
	static constraints = {
		roleName blank: false, unique: 'parent' 
		label blank:false
	}

	String toString(){
		label
	}
	
	
	Role addRole(String orgId) {
		def client =  Client.findByOrgId(orgId)
		if(!client) return null
		this.parent = client
		save(flush: true, insert: true)
	}
	
	

	
	
}

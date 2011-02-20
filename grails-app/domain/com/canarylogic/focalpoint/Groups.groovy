package com.canarylogic.focalpoint

import java.io.Serializable;

class Groups implements Serializable {
	
	String grpName;
	String grpLabel;

	static hasMany = [users:User]	
	static belongsTo = [parent:Client]
	
	
    static constraints = {
		grpName(unique:'parent')
	}
	
	Groups createGroup(String orgId) {
		def client =  Client.findByOrgId(orgId)
		if(!client) return null
		this.parent = client
		save(flush: true, insert: true)
	}
	

}
